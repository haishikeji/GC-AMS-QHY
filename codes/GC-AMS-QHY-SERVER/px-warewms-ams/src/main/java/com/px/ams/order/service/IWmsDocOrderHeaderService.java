package com.px.ams.order.service;

import java.util.List;

import com.px.ams.order.domain.WmsDocOrderDetails;
import com.px.ams.order.domain.WmsDocOrderHeader;
import com.px.ams.order.form.*;
import com.px.ams.order.vo.CheckOutVO;
import com.px.ams.order.vo.PickingListVO;
import com.px.ams.order.vo.StockOrderVO;
import com.px.common.core.domain.AjaxResult;

/**
 * 出库单头Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IWmsDocOrderHeaderService {
    /**
     * 查询出库单头
     *
     * @param orderNo 出库单头主键
     * @return 出库单头
     */
    WmsDocOrderHeader selectWmsDocOrderHeaderByOrderNo(String orderNo);

    /**
     * 查询出库单头列表
     *
     * @param wmsDocOrderHeader 出库单头
     * @return 出库单头集合
     */
    List<WmsDocOrderHeader> selectWmsDocOrderHeaderList(WmsDocOrderHeader wmsDocOrderHeader);

    /**
     * 查询出库单头
     *
     * @param wmsDocOrderHeader 出库单头
     * @return 出库单头集合
     */
    WmsDocOrderHeader selectWmsDocOrderHeaderByModel(WmsDocOrderHeader wmsDocOrderHeader);

    /**
     * 新增出库单头
     *
     * @param wmsDocOrderHeader 出库单头
     * @return 结果
     */
    AjaxResult insertWmsDocOrderHeader(WmsDocOrderHeader wmsDocOrderHeader);

    /**
     * 修改出库单头
     *
     * @param wmsDocOrderHeader 出库单头
     * @return 结果
     */
    int updateWmsDocOrderHeader(WmsDocOrderHeader wmsDocOrderHeader);

    /**
     * 批量删除出库单头
     *
     * @param orderNos 需要删除的出库单头主键集合
     * @return 结果
     */
    int deleteWmsDocOrderHeaderByOrderNos(String[] orderNos);

    /**
     * 删除出库单头信息
     *
     * @param orderNo 出库单头主键
     * @return 结果
     */
    int deleteWmsDocOrderHeaderByOrderNo(String orderNo);

    /**
     * 初始化表体
     *
     * @param orderNo
     * @return
     */
    List<WmsDocOrderDetails> initOrderDetails(String orderNo);

    /**
     * 更新订单明细备货数
     *
     * @param orderNo     订单号
     * @param orderLoneNo 订单明细
     * @param qty         数量
     * @param status      状态
     * @return
     */
    int updateOrderDetailsStock(String orderNo, Long orderLoneNo, Double qty, String status);

    /**
     * 拣货分配
     *
     * @param orderNo
     * @return
     */
    AjaxResult doAllocation(String orderNo);

    /**
     * 多单分配
     *
     * @param orderNoList
     * @return
     */
    AjaxResult doAllocation(List<String> orderNoList);

    /**
     * 拣货分配(按明细分配)
     *
     * @param details
     * @return
     */
    AjaxResult doAllocationDetails(WmsDocOrderHeader header, WmsDocOrderDetails details);

    /**
     * 查询拣货列表
     *
     * @param palletNo
     * @return
     */
    List<PickingListVO> pickingList(String palletNo);

    /**
     * 拣货分拣
     *
     * @param pickForm
     * @return
     */
    AjaxResult toPick(PickForm pickForm);

    /**
     * 出库list
     *
     * @param orderNoList
     * @return
     */
    List<CheckOutVO> checkOutList(List<String> orderNoList);

    /**
     * 分拣出库
     *
     * @return
     */
    AjaxResult doCheckout(CheckOutForm checkOutForm);

    /**
     * 发运
     *
     * @param orderNo
     * @return
     */
    AjaxResult doShipped(String orderNo);

    /**
     * 查询待出库明细
     *
     * @param palletNo
     * @return
     */
    List<StockOrderVO> selectStockOrder(String palletNo);

    /**
     * 新增出库单明细
     *
     * @param wmsDocOrderDetails
     * @return
     */
    int insertWmsDocOrderDetails(WmsDocOrderDetails wmsDocOrderDetails);

    /**
     * 检查关单
     *
     * @param asnNo
     */
    void checkStatusClose(String asnNo, String status);

    /**
     * 理货
     *
     * @param arrangeStockForm
     * @return
     */
    AjaxResult arrangeStock(ArrangeStockForm arrangeStockForm);

    /**
     * 库内理货单生成
     *
     * @param invTallyForm
     * @return
     */
    AjaxResult invTally(InvTallyForm invTallyForm);

    /**
     * 取消订单
     * @param header
     * @return
     */
    AjaxResult cancelOrder(WmsDocOrderHeader header);

    /**
     * 拣货出去回调
     *
     * @param taskNo
     */
    void checkOutCallback(String taskNo, String extParam);
}
