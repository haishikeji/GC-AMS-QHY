package com.px.ams.agv.ndc.service;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.px.ams.task.domain.WcsTask;
import com.px.ams.task.service.IWcsTaskService;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.redis.RedisCache;
import com.px.common.utils.StringUtils;
import com.px.hard.modbus.tcp.ChargingMachineClient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static com.px.ams.agv.ndc.service.ChargerButtService.CHARGER_ASSEMBLY_STATUS.*;
import static com.px.ams.agv.ndc.service.FeedbackTS.FEEDBACK_TS_STATUS.*;
import static com.px.hard.modbus.tcp.ChargingMachineClient.CHARGER_ADDRESS_MEANING.*;
import static com.px.hard.modbus.tcp.ChargingMachineClient.CHARGER_STATUS.*;

/**
 * 充电机对接
 *
 * @author 品讯科技
 * @version 1.0
 * @date 2024-08
 */
@Slf4j
@Service
public class ChargerButtService {

    @Autowired
    private IWcsTaskService wcsTaskService;
    @Autowired
    private IAmsTaskService amsTaskService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private FeedbackTS feedbackTS;
    @Autowired(required = false)
    private ChargingMachineClient chargingMachineClient;

    // 创建一个线程池
    private ExecutorService executorService = new ThreadPoolExecutor(2, 20
            , 60L, TimeUnit.SECONDS
            , new SynchronousQueue<Runnable>());

    /**
     * KEY前缀
     */
    public static final String KEY_PREFIX = "charging:agvNo:";

    /**
     * 充电桩站点号
     */
    public static final String KEY_CHARGING_SITE = "chargingSite";

    /**
     * TS充电任务号
     */
    public static final String KEY_AGV_INDEX = "index";

    /**
     * WcsTask任务号
     */
    public static final String KEY_TASK_NO = "taskNo";

    /**
     * AGV充电状态
     */
    public static final String KEY_AGV_STS = "status";

    /**
     * AGV充电状态
     */
    public enum AGV_CHARGING_STS {
        READY,
        START,
        STOP;
    }

    /**
     * 充电桩编号和从节点关系
     */
    public enum CHARGING_SLAVEID_MAPPING {
        /***
         * A
         */
        A(1004, 1),
        /***
         * B
         */
        B(2, 1),
        /***
         * 空
         */
        C(3, 1),
        /***
         * 无
         */
        D(4, 1);
        private Integer chargerNo;
        private Integer slaveId;

        CHARGING_SLAVEID_MAPPING(Integer chargerNo, Integer slaveId) {
            this.chargerNo = chargerNo;
            this.slaveId = slaveId;
        }

        public Integer getChargerNo() {
            return chargerNo;
        }

        public Integer getSlaveId() {
            return slaveId;
        }

        /**
         * 根据值获得枚举类型 switch
         *
         * @param chargerNo
         * @return
         */
        public static CHARGING_SLAVEID_MAPPING getByChargerNo(Integer chargerNo) {
            for (CHARGING_SLAVEID_MAPPING code : values()) {
                if (code.getChargerNo().equals(chargerNo)) {
                    return code;
                }
            }
            return null;
        }

        /**
         * 根据充电机编号拿到从节点ID
         *
         * @param chargerNo
         * @return
         */
        public static Integer getSlaveIdByChargerNo(Integer chargerNo) {
            CHARGING_SLAVEID_MAPPING mapping = getByChargerNo(chargerNo);
            if (mapping == null) {
                return null;
            }
            return mapping.getChargerNo();
        }

    }

    /**
     * AGV已经达到充电位置：
     * 1.确认几号充电机，查询并且判断充电机状态（确认信号：待机、在线、红外到位 > 发送启动指令 > 确认信号压紧）
     * 2.反馈TS是否能正常充电：通过给参数1改值来反馈(1表示让AGV退出充电点并重进，2表示充电机故障，3表示正常充电，4表示结束充电)
     * 3.给充电机发送开始放电信号
     * 4.整个充电过程需要每隔几秒查询充电机状态，根据充电机状态调度充电机
     * 5.查询到充电机状态为完成（确认信号：无压紧、无充电、归位）
     * 6.发送TS指令4（结束充电）
     * 故障处理：
     * 1.发送TS指令(充电机故障）
     * 2.发送停止充电机指令
     */
    public void theChargingPositionHasBeenReached(Integer index, Integer chargingSite, Integer agvNo) {
        if (chargingMachineClient == null) {
            log.info("充电机通讯开关关闭！");
            return;
        }
        String taskNo = "";
        // 充电机号
        Integer chargerNo = chargingSite;
        ChargingMachineClient.MACHINE_NO xx = ChargingMachineClient.MACHINE_NO.getByValue(chargerNo.toString());
        // 从Redis拿到充电信息
        Map<String, Object> agvInfoMap = redisCache.getCacheMap(KEY_PREFIX + agvNo);
        if (agvInfoMap == null) {
            return;
        }
        // 如果已经开始充电 不可重复请求
        if (agvInfoMap.get(KEY_AGV_STS) != null
                && agvInfoMap.get(KEY_AGV_STS).toString().equals(AGV_CHARGING_STS.START.toString())) {
            // 反馈TS正常充电
            this.feedbackTSLog(taskNo, index, NORMAL, agvNo + "AGV开始充电!", xx);
            log.info("充电机已经开始充电，不可重复请求！车号：{},充电机号：{}", agvNo, chargingSite);
            return;
        }
        // 如果是TS创建的充电任务 是没有对应的WcsTask任务 需要创建 并且设置到Redis
        // 不管是不是wms下发的充电任务，为了方便我们都重新生成一条wcstask任务
        if (agvInfoMap.get(KEY_TASK_NO) == null || StringUtils.isEmpty(agvInfoMap.get(KEY_TASK_NO).toString())) {
            AjaxResult ajaxResult = wcsTaskService.addChargingTask(agvNo, chargingSite, index);
            if (!ajaxResult.isSuccess()) {
                log.info(ajaxResult.getMsg());
                return;
            }
            taskNo = (String) ajaxResult.get("data");
            agvInfoMap.put(KEY_TASK_NO, taskNo);
            agvInfoMap.put(KEY_AGV_STS, AGV_CHARGING_STS.READY.toString());
            redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMap);
        } else {
            taskNo = agvInfoMap.get(KEY_TASK_NO).toString();
        }

        // WcsTask
        WcsTask wcsTask = wcsTaskService.selectWcsTaskByTaskNo(taskNo);

        // 从节点ID 充电机根据不同的ip地址区分，从节点地址默认都是1
        Integer slaveId = CHARGING_SLAVEID_MAPPING.getSlaveIdByChargerNo(chargingSite);
        // 确认是否故障
        boolean isFault = this.confirmChargerStatus(IS_FAULT, new Date(), true, xx);
        if (isFault) {
            // 反馈TS充电机故障
            this.feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机故障！", xx);
            return;
        }
        // 确认信号：在线，待机
        boolean isOnline = this.confirmChargerStatus(IS_ONLINE, new Date(), true, xx);
        if (!isOnline) {
            // 反馈TS充电机故障
            this.feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机离线！", xx);
            return;
        }
        // 确认信号：红外到位
        boolean isReady = this.confirmChargerStatus(IS_READY, new Date(), true, xx);
        if (!isReady) {
            // 反馈TS退出重入
            this.feedbackTSLog(taskNo, index, RE_ENTRY, agvNo + ",AGV退出充电点并重进！", xx);
            return;
        }
        // 充电机启动指令
        boolean isStart = this.operation(START, xx);
        if (!isStart) {
            // 反馈TS充电机故障
            feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机启动失败！", xx);
        }
        // 确认信号：压紧
        boolean isCompacting = this.confirmChargerStatus(IS_COMPACTING, new Date(), true, xx);
        if (!isCompacting) {
            // 反馈TS充电机故障 不能让AGV退出重进因为会有危险
            this.feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机压紧失败！", xx);
            return;
        }
        // 反馈TS正常充电
        this.feedbackTSLog(taskNo, index, NORMAL, agvNo + "AGV开始充电!", xx);
        // 充电机进行放电（不需要收到TS反馈，直接放电）
        boolean isDischarge = this.operation(DISCHARGE, xx);
        if (!isDischarge) {
            // 反馈TS充电机故障
            this.feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机放电失败!", xx);
        }

        // 设置Redis充电状态为开始
        Map<String, Object> agvInfoMapU = redisCache.getCacheMap(KEY_PREFIX + agvNo);
        if (agvInfoMapU != null) {
            agvInfoMapU.put(KEY_AGV_STS, AGV_CHARGING_STS.START.toString());
            redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMapU);
        }

        // 充电期间循环充电机状态
//        executorService.execute(new CyclicJudgmentOfChargerStatus(wcsTaskService, amsTaskService,
//                this, feedbackTS, chargingMachineClient, index, chargingSite, slaveId, agvNo, taskNo, xx));
    }

    /**
     * AGV充电任务被人为取消
     * 1.通知充电机结束充电，收回电推杆
     * 2.通过给参数1反馈一个4（表示结束充电），如果有先前收到过4003消息的标识，此操作可以省略
     * 3.下发一个TS101的任务，需要给两个参数，参数0给车号，参数1给一个0
     */
    public void theChargingTaskCanceledArtificially(Integer index, Integer chargingSite, Integer agvNo) {
        if (chargingMachineClient == null) {
            log.info("充电机通讯开关关闭！");
            return;
        }
        // 没有充电桩号没办法取消对应的充电机
        if (chargingSite == null) {
            log.info("没有充电桩号没办法取消对应的充电机！");
            return;
        }
        String taskNo = "";
        WcsTask wcsTask = null;
        // 充电机号
        Integer chargerNo = chargingSite;
        ChargingMachineClient.MACHINE_NO xx = ChargingMachineClient.MACHINE_NO.getByValue(chargerNo.toString());
        // 从节点ID
        Integer slaveId = CHARGING_SLAVEID_MAPPING.getSlaveIdByChargerNo(chargingSite);
        // 从Redis拿到充电信息
        Map<String, Object> agvInfoMap = redisCache.getCacheMap(KEY_PREFIX + agvNo);
        // 拿到对应WcsTask任务号
        if (agvInfoMap != null && agvInfoMap.get(KEY_TASK_NO) != null
                && StringUtils.isNotEmpty(agvInfoMap.get(KEY_TASK_NO).toString())) {
            taskNo = agvInfoMap.get(KEY_TASK_NO).toString();
            // WcsTask
            wcsTask = wcsTaskService.selectWcsTaskByTaskNo(taskNo);
        }

        // 停止充电机
        boolean isStop = this.operation(STOP, xx);
        if (!isStop) {
            // 反馈TS充电机故障
            feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机关闭失败！", xx);
        }
        // 确认信号：无压紧、无充电、归位
        boolean isEnd = confirmChargerStatus(IS_END, new Date(), true, xx);
        if (!isEnd) {
            // 反馈TS充电机故障
            this.feedbackTSLog(taskNo, index, FAULT, agvNo + ",确认信号：无压紧、无充电、归位，失败！", xx);
            return;
        }
        // 判断先前是否收到过4003消息的标识
//        if (wcsTask != null) {
//            boolean con = wcsTask.getExt4().equals("1") ? true : false;
//            if (!con) {
        // 反馈TS结束充电
        this.feedbackTSLog(taskNo, index, AGV_STOP, agvNo + ",AGV结束充电！", xx);
//            }
//        }
        // 下发一个TS101的任务
        feedbackTS.feedbackTS101(taskNo, index, agvNo);

        // 设置Redis充电状态为停止
        Map<String, Object> agvInfoMapU = redisCache.getCacheMap(KEY_PREFIX + agvNo);
        if (agvInfoMapU != null) {
            agvInfoMapU.put(KEY_AGV_STS, AGV_CHARGING_STS.STOP.toString());
            redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMapU);
        }

    }

    /**
     * AGV充电任务结束
     * 车辆电量到达充电任务的电量，但小于98%，且车辆当前无其他需执行任务时，充电任务结束，
     * 给你发一个4003的消息，此时你这边不需要做任何操作，因为充电任务结束，但车因为无其他任务，所以仍在原地充电。
     * 直到车体通过event给你发了一个4002的消息，此时直接下发一个ts101的任务即可。
     * 1.修改充电对应任务Wcstask已经收到4003消息标识
     */
    public void theChargingTaskItSOver(Integer index, Integer chargingSite, Integer agvNo) {
        String taskNo = "";
        WcsTask wcsTask;
        // 从redis拿到充电信息
        Map<String, Object> agvInfoMap = redisCache.getCacheMap(KEY_PREFIX + agvNo);
        // 拿到对应WcsTask任务号
        if (agvInfoMap != null && agvInfoMap.get(KEY_TASK_NO) != null
                && StringUtils.isNotEmpty(agvInfoMap.get(KEY_TASK_NO).toString())) {
            taskNo = agvInfoMap.get(KEY_TASK_NO).toString();
            // WcsTask
            wcsTask = wcsTaskService.selectWcsTaskByTaskNo(taskNo);
            if (wcsTask == null) {
                return;
            }
            wcsTask.setExt4("1");
            wcsTaskService.updateWcsTask(wcsTask);
        }
        // 设置Redis充电状态为停止
//        Map<String, Object> agvInfoMapU = redisCache.getCacheMap(KEY_PREFIX + agvNo);
//        if (agvInfoMapU != null) {
//            agvInfoMapU.put(KEY_AGV_STS, AGV_CHARGING_STS.STOP.toString());
//            redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMapU);
//        }
    }

    /**
     * 充电机操作
     *
     * @return
     * @see com.px.hard.modbus.tcp.ChargingMachineClient.CHARGER_ADDRESS_MEANING
     */
    private boolean operation(ChargingMachineClient.CHARGER_ADDRESS_MEANING op, ChargingMachineClient.MACHINE_NO xx) {
        boolean con = false;
        con = chargingMachineClient.operation(op, xx);
        // 如果失败 再重复三次
        if (!con) {
            for (int i = 0; i < 2; i++) {
                con = chargingMachineClient.operation(op, xx);
                if (con) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return con;
    }


    /**
     * 反馈TS：通过给参数address改值value来反馈
     * 并且打印日志
     *
     * @param taskNo
     * @param index
     * @param tsStatus
     * @param remarks
     */
    private void feedbackTSLog(String taskNo, Integer index
            , FeedbackTS.FEEDBACK_TS_STATUS tsStatus, String remarks, ChargingMachineClient.MACHINE_NO xx) {
        // 打印日志
        if (StringUtils.isNotEmpty(remarks)) {
            Map<String, Boolean> statusNameMapping = chargingMachineClient.getStatusNameMapping(xx);
            if (statusNameMapping != null) {
                log.info(remarks + JSONUtil.toJsonStr(statusNameMapping));
            }
        }
        // 反馈TS
        feedbackTS.feedbackTS(taskNo, index, tsStatus);
    }

    /**
     * 充电期间循环充电机状态 每隔3秒
     */
    @Data
    public class CyclicJudgmentOfChargerStatus implements Runnable {
        private IWcsTaskService wcsTaskService;
        private IAmsTaskService amsTaskService;
        private ChargerButtService chargerButtService;
        private FeedbackTS feedbackTS;
        private ChargingMachineClient chargingMachineClient;
        private Integer index;
        private Integer chargingSite;
        private Integer slaveId;
        private Integer agvNo;
        private String taskNo;
        private ChargingMachineClient.MACHINE_NO xx;

        public CyclicJudgmentOfChargerStatus(IWcsTaskService wcsTaskService, IAmsTaskService amsTaskService
                , ChargerButtService chargerButtService, FeedbackTS feedbackTS, ChargingMachineClient chargingMachineClient
                , Integer index, Integer chargingSite, Integer slaveId, Integer agvNo, String taskNo, ChargingMachineClient.MACHINE_NO xx) {
            this.wcsTaskService = wcsTaskService;
            this.amsTaskService = amsTaskService;
            this.chargerButtService = chargerButtService;
            this.feedbackTS = feedbackTS;
            this.chargingMachineClient = chargingMachineClient;
            this.index = index;
            this.chargingSite = chargingSite;
            this.slaveId = slaveId;
            this.agvNo = agvNo;
            this.taskNo = taskNo;
            this.xx = xx;
        }

        @Override
        public void run() {
            // 开始时间
            Date startTime = new Date();
            for (; ; ) {
                // 超过5小时 退出
                if (DateUtil.between(startTime, new Date(), DateUnit.HOUR) > 5) {
                    break;
                }
                // 充电任务已经结束（Redis拿到充电状态） 退出
                Map<String, Object> agvInfoMap = redisCache.getCacheMap(KEY_PREFIX + agvNo);
                if (agvInfoMap != null) {
                    String chargingStatus = (String) agvInfoMap.get(KEY_AGV_STS);
                    if (chargingStatus.equals(AGV_CHARGING_STS.STOP.toString())) {
                        break;
                    }
                }
                // 拿到WcsTask
                WcsTask wcsTask = wcsTaskService.selectWcsTaskByTaskNo(taskNo);

                // 查询充电机状态是否为完成（确认信号：无压紧、无充电、归位），充电机充电完成会自动停止放电 退回伸缩杆、归位
                boolean isEnd = chargerButtService.confirmChargerStatus(IS_END, new Date(), false, xx);
                if (isEnd) {
                    // 判断先前是否收到过4003消息的标识
                    if (wcsTask != null) {
                        boolean con = wcsTask.getExt4().equals("1") ? true : false;
                        if (!con) {
                            // 反馈TS结束充电
                            chargerButtService.feedbackTSLog(taskNo, index, AGV_STOP, agvNo + "AGV充电结束！", xx);
                        }
                    }
                    // 下发一个TS101的任务
                    feedbackTS.feedbackTS101(taskNo, index, agvNo);
                    // 设置Redis充电状态为停止
                    Map<String, Object> agvInfoMapU = redisCache.getCacheMap(KEY_PREFIX + agvNo);
                    if (agvInfoMapU != null) {
                        agvInfoMapU.put(KEY_AGV_STS, AGV_CHARGING_STS.STOP.toString());
                        redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMapU);
                    }
                    break;
                }
                // 查询充电机是否故障 关闭充电机 停止充电
                boolean isFault = chargerButtService.confirmChargerStatus(IS_FAULT, new Date(), false, xx);
                if (isFault) {
                    chargerButtService.operation(STOP, xx);
                    // 反馈TS充电机故障
                    chargerButtService.feedbackTSLog(taskNo, index, FAULT, agvNo + ",充电机故障！", xx);
                    // 设置Redis充电状态为停止
                    Map<String, Object> agvInfoMapU = redisCache.getCacheMap(KEY_PREFIX + agvNo);
                    if (agvInfoMapU != null) {
                        agvInfoMapU.put(KEY_AGV_STS, AGV_CHARGING_STS.STOP.toString());
                        redisCache.setCacheMap(KEY_PREFIX + agvNo, agvInfoMapU);
                    }
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 充电机组合状态
     */
    public enum CHARGER_ASSEMBLY_STATUS {
        /***
         * 确认信号：待机、在线
         */
        IS_ONLINE,
        /***
         * 确认信号：红外到位
         */
        IS_READY,
        /***
         * 确认信号：是否故障
         */
        IS_FAULT,
        /***
         * 确认信号：压紧
         */
        IS_COMPACTING,
        /***
         * 确认信号：无压紧、无充电、归位
         */
        IS_END;
    }

    /**
     * 确认充电机状态 递归
     * 再给充电机发送指令之后，充电机需要一定操作之后才能达到某种状态
     * 比如发送充电指令之后，充电机从伸出推杆到给压紧信号需要一定的时间
     * 所以需要重复轮询判断充电机的状态
     *
     * @param status
     * @param startTime   开始时间
     * @param isRecursion 是否递归
     * @return
     */
    private boolean confirmChargerStatus(CHARGER_ASSEMBLY_STATUS status, Date startTime
            , boolean isRecursion, ChargingMachineClient.MACHINE_NO xx) {
        boolean con = false;
        DateUnit unit = DateUnit.SECOND;
        int timeout = 1; // 超时时间
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        switch (status) {
            case IS_ONLINE:
                // 确认信号：待机、在线
                con = confirmChargerStatusIsOnline(xx);
                timeout = 10;
                break;
            case IS_READY:
                // 确认信号：红外到位
                con = confirmChargerStatusIsReady(xx);
                timeout = 10;
                break;
            case IS_FAULT:
                // 确认是否故障
                con = confirmChargerStatusIsFault(xx);
                if (!con) {
                    // 如果无故障 不需要重复检查
                    timeout = 1;
                }
                break;
            case IS_COMPACTING:
                // 确认信号：压紧
                con = confirmChargerStatusIsCompacting(xx);
                timeout = 20;
                break;
            case IS_END:
                // 确认信号：无压紧、无充电、归位
                con = confirmChargerStatusIsEnd(xx);
                timeout = 20;
                break;
            default:
                break;
        }
        if (!con && isRecursion) {
            if (DateUtil.between(startTime, new Date(), unit) > timeout) {
                return con;
            }
            // 递归
           return confirmChargerStatus(status, startTime, isRecursion, xx);
        }
        return con;
    }

    /**
     * 充电机状态
     * 确认信号：待机、在线
     *
     * @return true 正常
     */
    private boolean confirmChargerStatusIsOnline(ChargingMachineClient.MACHINE_NO xx) {
        boolean[] status = chargingMachineClient.getStatus(xx);
        if (status == null) {
            // 再去拿一次
            status = chargingMachineClient.getStatus(xx);
            if (status == null) {
                return false;
            }
        }
        // 待机
        boolean bit_04_0 = BIT_04_0.isTrue(status);
        // 在线
//        boolean bit_04_5 = BIT_04_5.isTrue(status);


        // 所有的是true 返回true
        boolean result = Stream.of(bit_04_0/*, bit_04_5*/).allMatch(t -> t == true);

        return result;
    }

    /**
     * 充电机状态
     * 确认信号：红外到位
     *
     * @return true 正常
     */
    private boolean confirmChargerStatusIsReady(ChargingMachineClient.MACHINE_NO xx) {
        boolean[] status = chargingMachineClient.getStatus(xx);
        if (status == null) {
            // 再去拿一次
            status = chargingMachineClient.getStatus(xx);
            if (status == null) {
                return false;
            }
        }
        // 红外到位
        boolean bit_06_7 = BIT_06_7.isTrue(status);


        // 所有的是true 返回true
        boolean result = Stream.of(bit_06_7).allMatch(t -> t == true);

        return result;
    }

    /**
     * 充电机状态
     * 确认是否故障
     *
     * @return true 有故障 false无故障
     */
    private boolean confirmChargerStatusIsFault(ChargingMachineClient.MACHINE_NO xx) {
        boolean[] status = chargingMachineClient.getStatus(xx);
        if (status == null) {
            // 再去拿一次
            status = chargingMachineClient.getStatus(xx);
            if (status == null) {
                return false;
            }
        }
        // 故障
        boolean bit_04_1 = BIT_04_1.isTrue(status);

        // 总故障
        boolean bit_05_0 = BIT_05_0.isTrue(status);
        // 过流
        boolean bit_05_1 = BIT_05_1.isTrue(status);
        // 过压
        boolean bit_05_2 = BIT_05_2.isTrue(status);
        // 短路
        boolean bit_05_3 = BIT_05_3.isTrue(status);
        // 电池未接
//        boolean bit_05_4 = BIT_05_4.isTrue(status);
        // 电池反接
        boolean bit_05_5 = BIT_05_5.isTrue(status);
        // 模块通信故障
        boolean bit_05_6 = BIT_05_6.isTrue(status);
        // Can通信超时
//        boolean bit_05_7 = BIT_05_7.isTrue(status);

        // 伸缩故障
        boolean bit_06_5 = BIT_06_5.isTrue(status);

        // 如果任何一个是true 返回true
        boolean result = Stream.of(bit_04_1
                , bit_05_0, bit_05_1, bit_05_2, bit_05_3, /*bit_05_4,*/ bit_05_5, bit_05_6/*, bit_05_7*/
                , bit_06_5).anyMatch(t -> t == true);

        return result;
    }

    /**
     * 充电机状态
     * 确认信号：压紧
     *
     * @return true 已压紧
     */
    private boolean confirmChargerStatusIsCompacting(ChargingMachineClient.MACHINE_NO xx) {
        boolean[] status = chargingMachineClient.getStatus(xx);
        if (status == null) {
            // 再去拿一次
            status = chargingMachineClient.getStatus(xx);
            if (status == null) {
                return false;
            }
        }
        // 压紧
        boolean bit_06_6 = BIT_06_6.isTrue(status);

        // 所有的是true 返回true
        boolean result = Stream.of(bit_06_6).allMatch(t -> t == true);

        return result;
    }

    /**
     * 充电机状态
     * 确认信号：无压紧、无充电、归位
     *
     * @return true 正常
     */
    private boolean confirmChargerStatusIsEnd(ChargingMachineClient.MACHINE_NO xx) {
        boolean[] status = chargingMachineClient.getStatus(xx);
        if (status == null) {
            // 再去拿一次
            status = chargingMachineClient.getStatus(xx);
            if (status == null) {
                return false;
            }
        }
        // 压紧
        boolean bit_06_6 = BIT_06_6.isTrue(status);
        // 进行
        boolean bit_04_2 = BIT_04_2.isTrue(status);
        // 归位 取反
        boolean bit_06_0 = !BIT_06_0.isTrue(status);


        // 所有的是false 返回true
        boolean result = Stream.of(bit_06_6, bit_04_2, bit_06_0).allMatch(t -> t == false);

        return result;
    }
}
