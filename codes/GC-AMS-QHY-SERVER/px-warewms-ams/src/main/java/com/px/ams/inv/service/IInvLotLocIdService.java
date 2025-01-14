package com.px.ams.inv.service;

import java.math.BigDecimal;
import java.util.List;

import com.px.ams.config.domain.dto.AgvCallDTO;
import com.px.ams.config.domain.dto.LotattDTO;
import com.px.ams.inv.domain.InvLotAtt;
import com.px.ams.inv.domain.InvLotLocId;
import com.px.ams.inv.domain.form.InvLocIdSearchFrom;
import com.px.ams.inv.domain.form.InvLotLocIdAdjForm;
import com.px.ams.inv.domain.form.InvLotLocIdMoveForm;
import com.px.ams.inv.domain.vo.InvLotLocIdLotattVO;
import com.px.common.core.domain.AjaxResult;

/**
 * 库位库存信息Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IInvLotLocIdService {
    /**
     * 查询库位库存信息
     *
     * @param lotnum 库位库存信息主键
     * @return 库位库存信息
     */
    List<InvLotLocId> selectInvLotLocIdByLotnum(String lotnum);

    /**
     * 根据主键查询
     *
     * @param locationId
     * @param customerId
     * @param sku
     * @param lotnum
     * @return
     */
    InvLotLocId selectInvLotLocIdById(String locationId, String customerId, String sku, String lotnum);

    /**
     * 查询库位库存信息列表
     *
     * @param locationId
     * @return
     */
    List<InvLotLocId> selectInvLotLocIdList(Long locationId);

    /**
     * 查询库位库存信息列表
     *
     * @param invLotLocId 库位库存信息
     * @return 库位库存信息集合
     */
    List<InvLotLocId> selectInvLotLocIdList(InvLotLocId invLotLocId);

    /**
     * 查询物料批次信息列表
     *
     * @param invLocIdSearchFrom
     * @return
     */
    List<InvLotLocIdLotattVO> selectInvLocIdLotattList(InvLocIdSearchFrom invLocIdSearchFrom);

    /**
     * 查询库位库存信息
     *
     * @param invLotLocId 库位库存信息
     * @return 库位库存信息集合
     */
    InvLotLocId selectInvLotLocIdByModel(InvLotLocId invLotLocId);

    /**
     * 库存调整
     *
     * @return
     */
    AjaxResult adj(InvLotLocIdAdjForm invLotLocIdAdjForm);

    /**
     * 库存移动
     *
     * @return
     */
    AjaxResult move(InvLotLocIdMoveForm invLotLocIdMoveForm);


    /**
     * 库存移动（直接转移库存）
     *
     * @param invLotLocIdMoveForm
     * @return
     */
    AjaxResult moveDirect(InvLotLocIdMoveForm invLotLocIdMoveForm);

    /**
     * 库存移动（根据托盘
     *
     * @param palletNo
     * @param locationTo
     * @param isCallCar
     * @param reason
     * @param warehouseId
     * @param updateBy
     * @return
     */
    AjaxResult movePallet(String palletNo, String locationTo, String isCallCar, String reason
            , Long warehouseId, String updateBy);

    /**
     * 库存清理
     *
     * @return
     */
    AjaxResult clear(String locationId, Long warehouseId, String updateBy);

    /**
     * 库位任务状态调整
     *
     * @return
     */
    AjaxResult adjLocationStockStatus(String locationId, Long warehouseId, String updateBy);

    /**
     * 库存是否有货状态调整
     *
     * @return
     */
    AjaxResult adjLocationIsEmpty(String locationId, Long warehouseId, String updateBy);

    /**
     * 新增库位库存信息
     *
     * @param invLotLocId 库位库存信息
     * @return 结果
     */
    int insertInvLotLocId(InvLotLocId invLotLocId);

    /**
     * 修改库位库存信息
     *
     * @param invLotLocId 库位库存信息
     * @return 结果
     */
    int updateInvLotLocId(InvLotLocId invLotLocId);

    /**
     * 批量删除库位库存信息
     *
     * @param lotnums 需要删除的库位库存信息主键集合
     * @return 结果
     */
    int deleteInvLotLocIdByLotnums(String[] lotnums);

    /**
     * 删除库位库存信息信息
     *
     * @param lotnum 库位库存信息主键
     * @return 结果
     */
    int deleteInvLotLocIdByLotnum(String lotnum);

    /**
     * 删除库位库存信息信息
     *
     * @param locId 库位库存信息主键
     * @return 结果
     */
    int deleteInvLotLocIdById(long locId);

    /**
     * 初始化库存
     *
     * @param locationId
     * @param agvCallDTO
     * @return
     */
    String initInv(String locationId, AgvCallDTO agvCallDTO);

    /**
     * 根据库位id查询库位库存包含批次属性
     *
     * @param locationId
     * @return
     */
    List<InvLotLocIdLotattVO> selectInvLocIdLotattByLocationId(Long locationId);

    /**
     * 库存转移
     *
     * @param locationFrom 起始点位
     * @param palletNo     托盘编号
     * @param locationTo   目标点位
     * @return
     */
    int stockTransfer(Long locationFrom, String palletNo, Long locationTo);

    /**
     * 库存按数量转移（根据托盘编号）
     *
     * @param palletNoTo 目标托盘
     * @param qty        需要拣出的数量
     * @param fromQtyAtt 起始库位库存的批次属性
     * @return
     */
    AjaxResult invTransferPick(LotattDTO fromQtyAtt, BigDecimal qty, String palletNoTo);

    /**
     * 根据物料编号和批次属性查询库存数量
     *
     * @param sku
     * @param lotattDTO
     * @return
     */
    Double queryInvBySku(String sku, LotattDTO lotattDTO, List<String> zoneIdList);

    /**
     * 根据出库单明细查询库存
     *
     * @param lotnum
     * @param sku
     * @param location
     * @param customerId
     * @return
     */
    List<InvLotLocId> queryInvOrderBy(String lotnum, String sku, String location, String customerId, LotattDTO lotattDTO);

    /**
     * 根据托盘号查询库存
     *
     * @param invLotAtt
     * @return
     */
    List<InvLotLocId> queryInvByInvLotatt(InvLotAtt invLotAtt);

    /**
     * 查询已备货的满托库存
     *
     * @param orderNo
     * @param sku
     * @return
     */
    List<InvLotLocId> queryInvByFull(String orderNo, String sku);

    /**
     * 根据托盘解绑库存
     *
     * @param palletNo
     * @return
     */
    AjaxResult unLockInvByPalletNo(String palletNo, String sn);

    /**
     * 库存转移部分
     *
     * @param locationFrom
     * @param locationTo
     * @param lotnum
     * @param qty
     * @param boxNo
     * @return
     */
    AjaxResult stockTransferPick(Long locationFrom, Long locationTo, String lotnum, BigDecimal qty, String boxNo);

    /**
     * 保存或者更新
     *
     * @param invLotLocId
     * @return
     */
    AjaxResult saveOrUpdate(InvLotLocId invLotLocId);

    /**
     * 验证库存质检状态是否可出库
     * @param locationId
     * @return true 代表质检没有问题，可以出库
     */
    boolean verifyInventoryCanOutbound(String locationId);
}
