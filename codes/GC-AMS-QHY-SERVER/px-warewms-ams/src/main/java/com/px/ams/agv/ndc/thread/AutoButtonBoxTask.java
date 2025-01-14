package com.px.ams.agv.ndc.thread;

import com.px.ams.business.IBusinessService;
import com.px.ams.config.domain.dto.AgvCallDTO;
import com.px.ams.config.domain.dto.AgvCallItemDTO;
import com.px.ams.config.domain.dto.LotattDTO;
import com.px.base.constant.Constant;
import com.px.common.core.domain.AjaxResult;
import com.px.hard.modbus.tcp.AutoDoorClient;
import com.px.hard.modbus.tcp.ButtonBoxClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AutoButtonBoxTask {

    @Autowired(required = false)
    private ButtonBoxClient buttonBoxClient;
    @Autowired
    private IBusinessService businessService;
    @Autowired
    private AutoDoorClient autoDoorClient;


    /**
     * 1.读取按钮是否被按下
     * 2.根据按下的按钮触发模具搬运任务
     * 3.任务生成成功，按钮盒灭灯，并且清除按下标记
     */
    public void run() {
        Boolean readBtn01 = buttonBoxClient.readBtn01();
        Boolean readBtn02 = buttonBoxClient.readBtn02();
        if (readBtn01) {
            buttonTask01();
        }
        if (readBtn02) {
            buttonTask02();
        }
    }

    /**
     * 按钮1业务（硫化任务）
     */
    public void buttonTask01() {
        log.info("按钮1已经被按下");
        // 下发模具搬运任务
        AgvCallDTO agvCallDTO = new AgvCallDTO();
        agvCallDTO.setWarehouseId(Constant.WAREHOUSE_ID);
        agvCallDTO.setLocationFrom("");
        List<AgvCallItemDTO> agvCallItemDTOList = new ArrayList<>();
        AgvCallItemDTO agvCallItemDTO = new AgvCallItemDTO();
        agvCallItemDTO.setSku("硫化模具");
        LotattDTO lotattDTO = new LotattDTO();
        lotattDTO.setLotatt01("硫化模具");
        agvCallItemDTO.setLotattDTO(lotattDTO);
        agvCallItemDTOList.add(agvCallItemDTO);
        agvCallDTO.setAgvCallItemDTOList(agvCallItemDTOList);
        AjaxResult ajaxResult = businessService.agvCall(1l, agvCallDTO);
        if (!ajaxResult.isSuccess()) {
            log.error("下发模具搬运任务（硫化）失败：" + ajaxResult.getMsg());
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 清除按下标记
        buttonBoxClient.clearBtn01();
        // 灭灯
        buttonBoxClient.writeLamp01Death();
        log.info("按钮1已经灭灯");
        // 关闭墙外同时 打开墙里第二层自动门
        autoDoorClient.sendOpen(AutoDoorClient.AUTO_DOOR_NO.C);
    }

    /**
     * 按钮2业务（冲边任务）
     */
    public void buttonTask02() {
        log.info("按钮2已经被按下");
        // 下发模具搬运任务
        AgvCallDTO agvCallDTO = new AgvCallDTO();
        agvCallDTO.setWarehouseId(Constant.WAREHOUSE_ID);
        agvCallDTO.setLocationFrom("");
        List<AgvCallItemDTO> agvCallItemDTOList = new ArrayList<>();
        AgvCallItemDTO agvCallItemDTO = new AgvCallItemDTO();
        agvCallItemDTO.setSku("冲边模具");
        LotattDTO lotattDTO = new LotattDTO();
        lotattDTO.setLotatt01("冲边模具");
        agvCallItemDTO.setLotattDTO(lotattDTO);
        agvCallItemDTOList.add(agvCallItemDTO);
        agvCallDTO.setAgvCallItemDTOList(agvCallItemDTOList);
        AjaxResult ajaxResult = businessService.agvCall(1l, agvCallDTO);
        if (!ajaxResult.isSuccess()) {
            log.error("下发模具搬运任务（冲边）失败：" + ajaxResult.getMsg());
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 清除按下标记
        buttonBoxClient.clearBtn02();
        // 灭灯
        buttonBoxClient.writeLamp02Death();
        log.info("按钮2已经灭灯");
        // 关闭墙外同时 打开墙里第二层自动门
        autoDoorClient.sendOpen(AutoDoorClient.AUTO_DOOR_NO.C);
    }
}
