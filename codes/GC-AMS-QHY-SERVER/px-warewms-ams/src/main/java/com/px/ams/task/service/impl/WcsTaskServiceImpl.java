package com.px.ams.task.service.impl;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import com.px.ams.agv.ndc.domain.AmsTask;
import com.px.ams.agv.ndc.service.IAmsTaskService;
import com.px.ams.box.service.IWmsBoxInfoService;
import com.px.ams.config.domain.FlowConfigEvent;
import com.px.ams.config.service.IFlowConfigEventService;
import com.px.ams.inv.domain.InvLotLocId;
import com.px.ams.inv.mapper.InvLotLocIdMapper;
import com.px.ams.inv.service.IInvLotAttService;
import com.px.ams.inv.service.IInvLotLocIdService;
import com.px.ams.task.dto.WcsTaskLocationDTO;
import com.px.base.constant.Constant;
import com.px.base.domain.BaseLocationInfo;
import com.px.base.service.IBaseLocationInfoService;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.redis.RedisCache;
import com.px.common.utils.StringUtils;
import com.px.common.utils.spring.SpringUtils;
import com.px.common.utils.uuid.SnowflakeIdWorker;
import com.px.hard.modbus.tcp.AutoDoorClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.ams.task.mapper.WcsTaskMapper;
import com.px.ams.task.domain.WcsTask;
import com.px.ams.task.service.IWcsTaskService;
import org.springframework.transaction.annotation.Transactional;

/**
 * AGV任务Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Slf4j
@Service
public class WcsTaskServiceImpl implements IWcsTaskService {

    @Autowired
    private WcsTaskMapper wcsTaskMapper;
    @Autowired
    private IBaseLocationInfoService baseLocationInfoService;
    @Autowired
    private InvLotLocIdMapper invLotLocIdMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IWmsBoxInfoService wmsBoxInfoService;
    @Autowired
    private IFlowConfigEventService flowConfigEventService;
    @Autowired
    private IWcsTaskService wcsTaskService;
    @Autowired
    private IInvLotLocIdService invLotLocIdService;
    @Autowired
    private IInvLotAttService iInvLotAttService;
    @Autowired
    private IAmsTaskService iAmsTaskService;
    @Autowired
    private AutoDoorClient autoDoorClient;

    //任务状态翻译
    private String codeConvert(int code) {
        switch (code) {
            case 1:
                return "开始";
            case 2:
                return "完成";
            case 3:
                return "开始取货";
            case 4:
                return "取货完成";
            case 5:
                return "开始卸货";
            case 6:
                return "卸货完成";
            case 7:
                return "取消";
            case 8:
                return "异常";
        }
        return "";
    }

    /**
     * 查询AGV任务
     *
     * @param taskNo AGV任务主键
     * @return AGV任务
     */
    @Override
    public WcsTask selectWcsTaskByTaskNo(String taskNo) {
        return wcsTaskMapper.selectWcsTaskByTaskNo(taskNo);
    }

    /**
     * 查询AGV任务列表
     *
     * @param wcsTask AGV任务
     * @return AGV任务
     */
    @Override
    public List<WcsTask> selectWcsTaskList(WcsTask wcsTask) {
        return wcsTaskMapper.selectWcsTaskListVO(wcsTask);
    }

    @Override
    public List<WcsTask> selectWcsUnallocated(Long warehouseId) {
        return wcsTaskMapper.selectWcsUnallocated(warehouseId);
    }

    /**
     * 查询AGV任务
     *
     * @param wcsTask AGV任务
     * @return AGV任务
     */
    @Override
    public WcsTask selectWcsTaskByModel(WcsTask wcsTask) {
        List<WcsTask> list = wcsTaskMapper.selectWcsTaskList(wcsTask);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增AGV任务
     *
     * @param wcsTask AGV任务
     * @return 结果
     */
    @Override
    public int insertWcsTask(WcsTask wcsTask) {
        return wcsTaskMapper.insertWcsTask(wcsTask);
    }

    /**
     * 修改AGV任务
     *
     * @param wcsTask AGV任务
     * @return 结果
     */
    @Override
    public int updateWcsTask(WcsTask wcsTask) {
        return wcsTaskMapper.updateWcsTask(wcsTask);
    }

    /**
     * 批量删除AGV任务
     *
     * @param taskNos 需要删除的AGV任务主键
     * @return 结果
     */
    @Override
    public int deleteWcsTaskByTaskNos(String[] taskNos) {
        return wcsTaskMapper.deleteWcsTaskByTaskNos(taskNos);
    }

    /**
     * 增加充电任务（TS触发触发任务反馈）
     *
     * @param agvNo        车号
     * @param chargingSite 充电桩号
     * @param index        TS任务号
     * @return
     */
    @Override
    public AjaxResult addChargingTask(Integer agvNo, Integer chargingSite, Integer index) {
        WcsTask wcsTask = new WcsTask();
        String taskNo = SnowflakeIdWorker.generateId().toString();
        wcsTask.setTaskNo(taskNo);
        wcsTask.setBusinessType(Constant.TASK_BUSINESS_TYPE.TASK_POWER.getValue());
        wcsTask.setShopId(Constant.WAREHOUSE_ID.toString());
        wcsTask.setLocationFrom("998");
        wcsTask.setLocationTo("997");
        wcsTask.setDeviceName(agvNo.toString());
        wcsTask.setState(Constant.TASK_STS.TASK2.getValue().longValue());
        wcsTask.setPriority(1L);
        wcsTask.setTaskType("POWER");
        wcsTask.setAgvNo(agvNo.toString());//车号
        wcsTask.setRemark("充电任务!");
        wcsTask.setExt1(chargingSite.toString());
        wcsTask.setExt2(index.toString());
        wcsTask.setCreateDate(new Date());
        wcsTask.setCreateUser("TS");
        int i = wcsTaskService.insertWcsTask(wcsTask);
        if (i <= 0) {
            return AjaxResult.error("生成充电WcsTask任务失败!");
        }
        return AjaxResult.success("", taskNo);
    }

    /**
     * 删除AGV任务信息
     *
     * @param taskNo AGV任务主键
     * @return 结果
     */
    @Override
    public int deleteWcsTaskByTaskNo(String taskNo) {
        return wcsTaskMapper.deleteWcsTaskByTaskNo(taskNo);
    }

    @Override
    public void callBackPickup(WcsTask wcsTask) {

    }

    @Transactional
    @Override
    public void callBackTaskComplete(WcsTask wcsTask) {
        BaseLocationInfo bt = null;
        BaseLocationInfo bf = null;

        //设置目标库位为占用状态
        if (!StringUtils.isEmpty(wcsTask.getLocationTo())) {
            bt = baseLocationInfoService.selectBaseLocationInfoById(Long.parseLong(wcsTask.getLocationTo()));
            if (bt != null) {
                bt.setIsEmpty("N");
                bt.setStockStatus("00");
                baseLocationInfoService.updateBaseLocationInfo(bt);
            }
        }

        // 释放起始库位
        if (!StringUtils.isEmpty(wcsTask.getLocationFrom())) {
            bf = baseLocationInfoService.selectBaseLocationInfoById(Long.parseLong(wcsTask.getLocationFrom()));
            if (bf != null) {
                bf.setIsEmpty("Y");
                bf.setStockStatus("00");
                baseLocationInfoService.updateBaseLocationInfo(bf);
            }
            baseLocationInfoService.updateLocationStockStatus(Long.parseLong(wcsTask.getLocationFrom()), "00");
        }

        //释放redis锁
        if (!StringUtils.isEmpty(wcsTask.getExt8())) {
            redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()));
        }


        if (!StringUtils.isEmpty(wcsTask.getLocationFrom()) && !StringUtils.isEmpty(wcsTask.getLocationTo())) {
            invLotLocIdMapper.updateInvLotLocIdLocationId(Long.parseLong(wcsTask.getLocationFrom()), Long.parseLong(wcsTask.getLocationTo()));
            //更新托盘绑定关系
            wmsBoxInfoService.updateLocationBind(Long.parseLong(wcsTask.getLocationFrom()), Long.parseLong(wcsTask.getLocationTo()), null);
        }

        this.eventCallBack("543", wcsTask);

        //修改任务状态
        wcsTask.setState(2L);
        wcsTaskMapper.updateWcsTask(wcsTask);
    }

    @Transactional
    @Override
    public AjaxResult cancelTask(WcsTask wcsTask) {

        String taskNo = wcsTask.getTaskNo();
        // 任务状态: 队列等待 直接删除即可 其他状态 需要请求AMS删除
        if (wcsTask.getState() == Constant.TASK_STS.TASK9.getValue().longValue()
                || wcsTask.getState() == Constant.TASK_STS.TASK10.getValue().longValue()) {
            return callBack(wcsTask.getTaskNo(), 7, null);
        } else {
            //请求ams 取消任务
            AmsTask amsTaskQuery = new AmsTask();
            amsTaskQuery.setTaskNo(taskNo);
            AmsTask amsTask = iAmsTaskService.selectAmsTaskByModel(amsTaskQuery);
            if (amsTask == null) {
                return AjaxResult.error("amsTask没有对应任务！");
            }
            if (null != amsTask.getEvent() && amsTask.getEvent() == 85) {
                return AjaxResult.error("任务已取消！");
            }
            AmsTask amsTaskUpdate = new AmsTask();
            amsTaskUpdate.setId(amsTask.getId());
            amsTaskUpdate.setIsDelete(1);
            amsTaskUpdate.setAciAccept(0);
            iAmsTaskService.updateAmsTask(amsTaskUpdate);
        }
        return AjaxResult.success("取消任务指令已下发！");
    }

    @Override
    public void invDisposition(WcsTask wcsTask, String result) {
        BaseLocationInfo bf = baseLocationInfoService.selectBaseLocationInfoById(Long.parseLong(wcsTask.getLocationFrom()));
        BaseLocationInfo bt = baseLocationInfoService.selectBaseLocationInfoById(Long.parseLong(wcsTask.getLocationTo()));

        if (result.equals("disFrom")) {
            wcsTask.setState(11L);
            wcsTaskMapper.updateWcsTask(wcsTask);
            invLotLocIdMapper.updateInvLotLocIdLocationId(Long.parseLong(wcsTask.getLocationTo()), Long.parseLong(wcsTask.getLocationFrom()));
        } else if (result.equals("disTo")) {
            wcsTask.setState(12L);
            wcsTaskMapper.updateWcsTask(wcsTask);
            invLotLocIdMapper.updateInvLotLocIdLocationId(Long.parseLong(wcsTask.getLocationFrom()), Long.parseLong(wcsTask.getLocationTo()));
        } else {
            invLotLocIdMapper.clearInvByLocationId(bf.getId());
        }
    }

    @Override
    public void eventCallBack(String code, WcsTask wcsTask) {
        if (!StringUtils.isEmpty(wcsTask.getExt7())) {
            FlowConfigEvent flowConfigEvent = new FlowConfigEvent();
            flowConfigEvent.setEventCode(code);
            List<FlowConfigEvent> flowConfigEvents = flowConfigEventService.selectFlowConfigEventList(flowConfigEvent);
            for (FlowConfigEvent event : flowConfigEvents) {
                if (event.getAccessType().equals("local")) {
                    Object bean = SpringUtils.getBean(event.getAccessUrl());
                    try {
                        Method method = bean.getClass().getMethod(event.getAccessMethod(), new Class[]{String.class});
                        Object result = method.invoke(bean, wcsTask.getTaskNo(), wcsTask.getExtParam());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }
        }
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<WcsTaskLocationDTO> selectTaskByColNo(String colNo, Long colIndex, Long zoneId, Long warehouseId) {
        return wcsTaskMapper.selectTaskByColNo(colNo, colIndex, zoneId, warehouseId);
    }

    @Override
    public List<WcsTaskLocationDTO> selectTaskByColNoAfter(String colNo, Long colIndex, Long zoneId, Long warehouseId) {
        return wcsTaskMapper.selectTaskByColNoAfter(colNo, colIndex, zoneId, warehouseId);
    }

    @Transactional
    @Override
    public synchronized AjaxResult callBack(String taskNo, Integer state, Integer carNo) {
        String updateBy = "NDC";
        log.info("agv回传------------->" + taskNo + "," + state);
        WcsTask wcsTask = wcsTaskService.selectWcsTaskByTaskNo(taskNo);
        if (wcsTask == null) {
            log.info("agv回传没有任务记录------------->");
            return AjaxResult.error("agv回传没有任务记录------------->");
        }

        if (state > 1 && state != 7) {
            wcsTask.setAgvNo(carNo + "");
        }

        if (wcsTask.getState() == 2) {
            log.info("已完成的任务无需反馈");
            return AjaxResult.error("已完成的任务无需反馈");
        }
        if (wcsTask.getState() == 7 && state == 7) {
            log.info("已取消的任务无需重复取消");
            return AjaxResult.error("已取消的任务无需重复取消");
        }
        //转发类型任务才回调
        if (wcsTask.getTaskType().equals(Constant.TASK_TYPE.FORWARD.getValue())) {
            try {
                switch (state) {
                    case 1:
                        wcsTask.setStartTime(new Date());
                        break;
                    case 2://完成
                        if (wcsTask.getState() != 4
                                && (StringUtils.isEmpty(wcsTask.getBeforeTask()) || !wcsTask.getBeforeTask().equals("1"))) {
                            log.info("任务未取货，不能直接完成！" + taskNo);
                            return AjaxResult.error("任务未取货，不能直接完成！" + taskNo);
                        }
                        if (wcsTask.getState() == 2) {
                            log.info("不能重复放货，" + taskNo);
                            return AjaxResult.error("不能重复放货，" + taskNo);
                        }
                        if (wcsTask.getState() == 6) {
                            // 修改任务状态
                            wcsTask.setState(state.longValue());
                            wcsTask.setUpdateDate(new Date());
                            wcsTaskService.updateWcsTask(wcsTask);
                            return AjaxResult.error("不能重复放货，" + taskNo);
                        }
                        wcsTask.setEndTime(new Date());
                        // 解锁终点库位
                        baseLocationInfoService.unLockLocationStockStatus(Long.parseLong(wcsTask.getLocationTo())
                                , Constant.WAREHOUSE_ID, updateBy);
                        // 将中间缓存位库存移动到终点
                        moveMiddleCacheToDestination(wcsTask);
                        //释放redis锁
                        if (!StringUtils.isEmpty(wcsTask.getExt8())) {
                            redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()));
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        if (wcsTask.getState() == 4) {
                            log.info("不能重复取货，" + taskNo);
                            return AjaxResult.error("不能重复取货，" + taskNo);
                        }
                        if (wcsTask.getBeforeTask() != null && wcsTask.getBeforeTask().equals("1")) {
                            log.info("不能重复取货，" + taskNo);
                            return AjaxResult.error("不能重复取货，" + taskNo);
                        }
                        // 标记已经取货完成
                        wcsTask.setBeforeTask("1");
                        // 解锁起始库位
                        baseLocationInfoService.unLockLocationStockStatus(Long.parseLong(wcsTask.getLocationFrom())
                                , Constant.WAREHOUSE_ID, updateBy);
                        // 将起始点库存移动到中间缓存位
                        moveStartingPointToMiddleCache(wcsTask);

                        //释放redis锁
                        if (!StringUtils.isEmpty(wcsTask.getExt8())) {
                            redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()), wcsTask.getLocationFrom());
                        }
                        break;
                    case 5:
                        break;
                    case 6://卸货
                        if (wcsTask.getState() != 4
                                && (StringUtils.isEmpty(wcsTask.getBeforeTask())|| !wcsTask.getBeforeTask().equals("1"))) {
                            log.info("任务未取货，不能直接完成！" + taskNo);
                            return AjaxResult.error("任务未取货，不能直接完成！" + taskNo);
                        }
                        if (wcsTask.getState() == 2) {
                            log.info("不能重复放货，" + taskNo);
                            return AjaxResult.error("不能重复放货，" + taskNo);
                        }
                        if (wcsTask.getState() == 6) {
                            return AjaxResult.error("不能重复放货，" + taskNo);
                        }
                        wcsTask.setEndTime(new Date());
                        // 解锁终点库位
                        baseLocationInfoService.unLockLocationStockStatus(Long.parseLong(wcsTask.getLocationTo())
                                , Constant.WAREHOUSE_ID, updateBy);
                        // 将中间缓存位库存移动到终点
                        moveMiddleCacheToDestination(wcsTask);
                        //释放redis锁
                        if (!StringUtils.isEmpty(wcsTask.getExt8())) {
                            redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()));
                        }
                        break;
                    case 7://取消
                        wcsTask.setEndTime(new Date());
                        //如果取到货就只解锁终点库位
                        if (wcsTask.getState() == 4 || wcsTask.getState() == 5) {
                            // 解锁终点库位
                            baseLocationInfoService.unLockLocationStockStatus(Long.parseLong(wcsTask.getLocationTo())
                                    , Constant.WAREHOUSE_ID, updateBy);
                        } else {
                            // 解锁终点和起始库位
                            baseLocationInfoService.unLockLocationStockStatus(Long.parseLong(wcsTask.getLocationFrom()), Long.parseLong(wcsTask.getLocationTo())
                                    , Constant.WAREHOUSE_ID, updateBy);
                        }
                        //释放redis锁
                        if (!StringUtils.isEmpty(wcsTask.getExt8())) {
                            redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()));
                        }
                        // 取消任务删除中间缓存库存
//                        cancelTaskDelMiddleCache(wcsTask);
                        break;
                    case 8:
                        log.error("任务异常状态------------" + wcsTask.getTaskNo());
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                log.info(wcsTask.getTaskNo() + "-----" + "回调状态:" + state + "异常", e);
                wcsTask.setRemark("回调状态:" + state + "异常");
                //释放redis锁
                if (!StringUtils.isEmpty(wcsTask.getExt8())) {
                    redisCache.unlockCacheObject(Long.parseLong(wcsTask.getExt8()));
                }
                throw new RuntimeException();
            }
        } else {

        }
        wcsTask.setState(state.longValue());
        wcsTask.setUpdateDate(new Date());
        wcsTaskService.updateWcsTask(wcsTask);
        return AjaxResult.success("操作成功！");
    }

    /**
     * 将起点库存放到中间缓存位置
     *
     * @param wcsTask
     * @return
     */
    @Override
    @Transactional
    public AjaxResult moveStartingPointToMiddleCache(WcsTask wcsTask) {
        //起点位置
        Long locationFrom = Long.valueOf(wcsTask.getLocationFrom());
        //中间缓存位置
        Long middleCache = Constant.LOC_MIDDLE_CACHE;
        //任务号
        String taskNo = wcsTask.getTaskNo();
        //检验起点库位库存
        List<InvLotLocId> invLotLocIdList = invLotLocIdService.selectInvLotLocIdList(locationFrom);
        if (invLotLocIdList.size() > 0) {
            // 转移库存
            invLotLocIdMapper.moveInvByLocationId(locationFrom, middleCache, taskNo);
        }
        // 修改起始库位状态为空闲无货
        baseLocationInfoService.updateLocationIdleAndEmpty(locationFrom, Constant.WAREHOUSE_ID, wcsTask.getUpdateBy());

        return AjaxResult.success();
    }

    /**
     * 将中间缓存位库存放到终点位置
     *
     * @param wcsTask
     * @return
     */
    @Override
    @Transactional
    public AjaxResult moveMiddleCacheToDestination(WcsTask wcsTask) {
        //终点位置
        Long locationTo = Long.valueOf(wcsTask.getLocationTo());
        //中间缓存位置
        Long middleCache = Constant.LOC_MIDDLE_CACHE;
        //任务号
        String taskNo = wcsTask.getTaskNo();
        //检验起点库位库存
        InvLotLocId invLotLocIdQuery = new InvLotLocId();
        invLotLocIdQuery.setLocationId(middleCache.toString());
        invLotLocIdQuery.setTraceid(taskNo);
        List<InvLotLocId> invLotLocIdList = invLotLocIdService.selectInvLotLocIdList(invLotLocIdQuery);
        if (invLotLocIdList.size() > 0) {
            // 转移库存
            invLotLocIdMapper.moveInvByTraceId(middleCache, locationTo, taskNo);
        }
        //修改目标库位状态为占用
        baseLocationInfoService.occupyLocation(locationTo, Constant.WAREHOUSE_ID, wcsTask.getUpdateBy());

        return AjaxResult.success();
    }


    /**
     * 将起始点库存放到终点位置
     *
     * @param wcsTask
     * @return
     */
    @Override
    @Transactional
    public AjaxResult moveStartingPointToDestination(WcsTask wcsTask) {
        //起始位置
        Long locationFrom = Long.valueOf(wcsTask.getLocationFrom());
        //终点位置
        Long locationTo = Long.valueOf(wcsTask.getLocationTo());

        //任务号
        String taskNo = wcsTask.getTaskNo();
        //检验起点库位库存
        InvLotLocId invLotLocIdQuery = new InvLotLocId();
        invLotLocIdQuery.setLocationId(locationFrom.toString());
        invLotLocIdQuery.setTraceid(taskNo);
        List<InvLotLocId> invLotLocIdList = invLotLocIdService.selectInvLotLocIdList(invLotLocIdQuery);
        if (invLotLocIdList.size() > 0) {
            // 转移库存
            invLotLocIdMapper.moveInvByTraceId(locationFrom, locationTo, taskNo);
        }
        //修改目标库位状态为占用
        baseLocationInfoService.occupyLocation(locationTo, Constant.WAREHOUSE_ID, wcsTask.getUpdateBy());

        return AjaxResult.success();
    }

    /**
     * 修改库存出入库标记
     *
     * @param wcsTask
     * @return
     */
    @Transactional
    public void updateInvAsnSoMarking(WcsTask wcsTask) {
        BaseLocationInfo baseLocationFrom = baseLocationInfoService.selectBaseLocationInfoByIdOrNo(wcsTask.getLocationFrom(), Constant.WAREHOUSE_ID);
        BaseLocationInfo baseLocationTo = baseLocationInfoService.selectBaseLocationInfoByIdOrNo(wcsTask.getLocationTo(), Constant.WAREHOUSE_ID);
        // 目标点为接驳位才进行判断
        if (baseLocationTo.getZoneId().compareTo(Constant.ZONE_TYPE.TRANSIT.getValue()) != 0) {
            return;
        }
        BaseLocationInfo baseLocationInfo = new BaseLocationInfo();
        // 判断起始库区是不是属于仓储区
        if (baseLocationFrom.getZoneId().compareTo(Constant.ZONE_TYPE.STORAGE.getValue()) == 0) {
            // 是仓储区修改库存标记为出库
            // 判断质量状态-如果是不合格品需要拉到不合格区
            baseLocationInfo.setUserdefine1((StringUtils.isNotEmpty(wcsTask.getExt6())
                    && wcsTask.getExt6().equals("BHG")) ?
                    "SO_BHG" : "SO_HG");
        } else {
            // 不是仓储区修改库存标记为入库
            baseLocationInfo.setUserdefine1("ASN");
        }
        baseLocationInfo.setId(Long.valueOf(wcsTask.getLocationTo()));
        baseLocationInfoService.updateBaseLocationInfo(baseLocationInfo);
    }


    /**
     * 取消任务删除中间缓存库存
     *
     * @param wcsTask
     * @return
     */
    public AjaxResult cancelTaskDelMiddleCache(WcsTask wcsTask) {
        // 取货完成之后卸货之前取消的任务才需要删除中间缓存库存
        if (wcsTask.getState() != 4 && wcsTask.getState() != 5) {
            return AjaxResult.success();
        }
        String taskNo = wcsTask.getTaskNo();
        Long middleCache = Constant.LOC_MIDDLE_CACHE;
        InvLotLocId invLotLocIdQuery = new InvLotLocId();
        invLotLocIdQuery.setLocationId(middleCache.toString());
        invLotLocIdQuery.setTraceid(taskNo);
        List<InvLotLocId> invLotLocIdList = invLotLocIdService.selectInvLotLocIdList(invLotLocIdQuery);
        if (invLotLocIdList.size() > 0) {
            for (InvLotLocId lotLocId : invLotLocIdList) {
                // todo 是否删除批次表
//                iInvLotAttService.deleteInvLotAttByLotnum(lotLocId.getLotnum());
                invLotLocIdService.deleteInvLotLocIdById(Long.valueOf(lotLocId.getLocationId()));
            }
        }
        return AjaxResult.success();
    }
}
