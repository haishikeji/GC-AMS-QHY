package com.px.ams.asn.service;

import java.util.List;

import com.px.ams.asn.domain.WmsDocAsnDetails;
import com.px.ams.asn.domain.WmsDocAsnHeader;
import com.px.ams.asn.form.PaForm;
import com.px.ams.asn.form.StockForm;
import com.px.ams.asn.form.UnlockForm;
import com.px.ams.asn.vo.SearchStockVO;
import com.px.base.domain.vo.CodeSkuRelationshipVO;
import com.px.base.form.SnCheckForm;
import com.px.common.core.domain.AjaxResult;

/**
 * 入库单Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IWmsDocAsnHeaderService {
    /**
     * 查询入库单
     *
     * @param asnNo 入库单主键
     * @return 入库单
     */
    WmsDocAsnHeader selectWmsDocAsnHeaderByAsnNo(String asnNo);

    /**
     * 查询入库单列表
     *
     * @param wmsDocAsnHeader 入库单
     * @return 入库单集合
     */
    List<WmsDocAsnHeader> selectWmsDocAsnHeaderList(WmsDocAsnHeader wmsDocAsnHeader);

    /**
     * 查询入库单
     *
     * @param wmsDocAsnHeader 入库单
     * @return 入库单集合
     */
    WmsDocAsnHeader selectWmsDocAsnHeaderByModel(WmsDocAsnHeader wmsDocAsnHeader);

    /**
     * 新增入库单
     *
     * @param wmsDocAsnHeader 入库单
     * @return 结果
     */
    AjaxResult insertWmsDocAsnHeader(WmsDocAsnHeader wmsDocAsnHeader);

    /**
     * 修改入库单
     *
     * @param wmsDocAsnHeader 入库单
     * @return 结果
     */
    int updateWmsDocAsnHeader(WmsDocAsnHeader wmsDocAsnHeader);

    /**
     * 批量删除入库单
     *
     * @param asnNos 需要删除的入库单主键集合
     * @return 结果
     */
    int deleteWmsDocAsnHeaderByAsnNos(String[] asnNos);

    /**
     * 删除入库单信息
     *
     * @param asnNo 入库单主键
     * @return 结果
     */
    int deleteWmsDocAsnHeaderByAsnNo(String asnNo);

    /**
     * 插入表体
     *
     * @param wmsDocAsnDetails
     * @return
     */
    int insertWmsDocAsnDetails(WmsDocAsnDetails wmsDocAsnDetails);

    /**
     * 查询入库单明细
     *
     * @param asnNo
     * @return
     */
    List<WmsDocAsnDetails> queryWmsDocAsnDetails(String asnNo);

    /**
     * 查询入库单单行明细
     *
     * @param asnNo
     * @param asnLineNo
     * @return
     */
    WmsDocAsnDetails queryWmsDocAsnDetailsOne(String asnNo, int asnLineNo);

    /**
     * 码盘
     *
     * @param stockForm
     * @return
     */
    AjaxResult doStock(StockForm stockForm);

    /**
     * 上架
     *
     * @param paForm
     * @return
     */
    AjaxResult doPa(PaForm paForm);

    /**
     * 查询已码盘
     *
     * @param palletNo
     * @return
     */
    List<SearchStockVO> searchStock(String palletNo, String sku);

    /**
     * 解绑物料
     *
     * @param unlockForm
     * @return
     */
    AjaxResult unloadStock(UnlockForm unlockForm);

    /**
     * 检查关闭表头
     *
     * @param asnNo
     * @param asnLineNo
     * @return
     */
    int closeHeader(String asnNo);

    /**
     * @param asnNos
     * @return
     */
    List<WmsDocAsnDetails> selectStockingList(List<String> asnNos);

    /**
     * 条码检测
     *
     * @param snCheckForm
     * @return
     */
    CodeSkuRelationshipVO searchSn(SnCheckForm snCheckForm);
}
