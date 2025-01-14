package com.px.ams.order.service;

import java.math.BigDecimal;
import java.util.List;

import com.px.ams.order.domain.ActAllocationDetails;
import com.px.ams.order.domain.WmsDocOrderDetails;
import com.px.common.core.domain.AjaxResult;

/**
 * 分配查询Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IActAllocationDetailsService {
    /**
     * 查询分配查询
     *
     * @param allocationId 分配查询主键
     * @return 分配查询
     */
    ActAllocationDetails selectActAllocationDetailsByAllocationId(String allocationId);

    /**
     * 查询分配查询列表
     *
     * @param actAllocationDetails 分配查询
     * @return 分配查询集合
     */
    List<ActAllocationDetails> selectActAllocationDetailsList(ActAllocationDetails actAllocationDetails);

    /**
     * 查询分配查询
     *
     * @param actAllocationDetails 分配查询
     * @return 分配查询集合
     */
    ActAllocationDetails selectActAllocationDetailsByModel(ActAllocationDetails actAllocationDetails);

    /**
     * 新增分配查询
     *
     * @param actAllocationDetails 分配查询
     * @return 结果
     */
    int insertActAllocationDetails(ActAllocationDetails actAllocationDetails);

    /**
     * 修改分配查询
     *
     * @param actAllocationDetails 分配查询
     * @return 结果
     */
    int updateActAllocationDetails(ActAllocationDetails actAllocationDetails);

    /**
     * 批量删除分配查询
     *
     * @param allocationIds 需要删除的分配查询主键集合
     * @return 结果
     */
    int deleteActAllocationDetailsByAllocationIds(String[] allocationIds);

    /**
     * 删除分配查询信息
     *
     * @param allocationId 分配查询主键
     * @return 结果
     */
    int deleteActAllocationDetailsByAllocationId(String allocationId);

    /**
     * 添加分配明细
     *
     * @param lotnum
     * @param locationId
     * @param qty
     * @param orderDetails
     * @return
     */
    AjaxResult addActAllocationDetails(String lotnum, String locationId, BigDecimal qty, WmsDocOrderDetails orderDetails, String status);

    /**
     * 新增或者更新分配明细
     * @param actAllocationDetails
     * @return
     */
    AjaxResult addOrUpdateAllocationDetails(ActAllocationDetails actAllocationDetails);

    /**
     * 根据托盘编号查询分配明细
     *
     * @param palletNo
     * @return
     */
    List<ActAllocationDetails> queryAllocationDetailsByPalletNo(String palletNo);

    /**
     * 查询分配明细
     * @param orderNo
     * @return
     */
    List<ActAllocationDetails> queryAllocationDetailsByOrderNo(String orderNo);

    /**
     * 更新分配库位根据托盘编号
     * @param locationId
     * @param palletNo
     */
    void updateAllocationDetailsLocationIdByPalletNo(String locationId,String palletNo);
}
