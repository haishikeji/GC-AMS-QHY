package com.px.ams.order.mapper;

import java.util.List;

import com.px.ams.order.domain.WmsDocOrderDetails;
import com.px.ams.order.vo.StockOrderVO;
import org.apache.ibatis.annotations.Param;

/**
 * 出库单Mapper接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface WmsDocOrderDetailsMapper {
    /**
     * 查询出库单
     *
     * @param orderNo 出库单主键
     * @return 出库单
     */
    List<WmsDocOrderDetails> selectWmsDocOrderDetailsByOrderNo(String orderNo);

    /**
     * 查询出库单列表
     *
     * @param wmsDocOrderDetails 出库单
     * @return 出库单集合
     */
    List<WmsDocOrderDetails> selectWmsDocOrderDetailsList(WmsDocOrderDetails wmsDocOrderDetails);

    /**
     * 新增出库单
     *
     * @param wmsDocOrderDetails 出库单
     * @return 结果
     */
    int insertWmsDocOrderDetails(WmsDocOrderDetails wmsDocOrderDetails);

    /**
     * 修改出库单
     *
     * @param wmsDocOrderDetails 出库单
     * @return 结果
     */
    int updateWmsDocOrderDetails(WmsDocOrderDetails wmsDocOrderDetails);

    /**
     * 删除出库单
     *
     * @param orderNo 出库单主键
     * @return 结果
     */
    int deleteWmsDocOrderDetailsByOrderNo(String orderNo);

    /**
     * 批量删除出库单
     *
     * @param orderNos 需要删除的数据主键集合
     * @return 结果
     */
    int deleteWmsDocOrderDetailsByOrderNos(String[] orderNos);

    /**
     * 更新备货数量
     *
     * @param orderNo
     * @param orderLoneNo
     * @param sku
     * @param qty
     * @return
     */
    int updateOrderDetailsStock(@Param("orderNo") String orderNo, @Param("orderLoneNo") Long orderLoneNo, @Param("qty") Double qty, @Param("status") String status);

    /**
     * 查询待出库记录
     *
     * @param palletNo 托盘编号
     * @return
     */
    List<StockOrderVO> selectStockOrder(@Param("palletNo") String palletNo);

    /**
     * 根据单号行号查询明细
     *
     * @param orderNo
     * @param orderLoneNo
     * @return
     */
    WmsDocOrderDetails selectWmsDocOrderDetailsByNoLineNo(@Param("orderNo") String orderNo, @Param("orderLineNo") Long orderLoneNo);

    /**
     * 删除出库单明细
     * @param orderNo
     * @param orderLoneNo
     * @return
     */
    int deleteWmsDocAsnDetailsByOrderNoLineNo(@Param("orderNo") String orderNo, @Param("orderLineNo") Long orderLineNo);
}
