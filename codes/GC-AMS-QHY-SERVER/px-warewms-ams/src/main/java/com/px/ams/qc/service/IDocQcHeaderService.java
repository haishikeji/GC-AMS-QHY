package com.px.ams.qc.service;

import java.util.List;

import com.px.ams.qc.domain.DocQcHeader;
import com.px.ams.qc.domain.vo.DocQcListVo;
import com.px.common.core.domain.AjaxResult;

/**
 * 质检Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IDocQcHeaderService {
    /**
     * 查询质检
     *
     * @param qcno 质检主键
     * @return 质检
     */
    DocQcHeader selectDocQcHeaderByQcno(String qcno);

    /**
     * 查询质检列表
     *
     * @param docQcHeader 质检
     * @return 质检集合
     */
    List<DocQcHeader> selectDocQcHeaderList(DocQcHeader docQcHeader);

    /**
     * 查询质检
     *
     * @param docQcHeader 质检
     * @return 质检集合
     */
    DocQcHeader selectDocQcHeaderByModel(DocQcHeader docQcHeader);

    /**
     * 新增质检
     *
     * @param docQcHeader 质检
     * @return 结果
     */
    int insertDocQcHeader(DocQcHeader docQcHeader);

    /**
     * 修改质检
     *
     * @param docQcHeader 质检
     * @return 结果
     */
    int updateDocQcHeader(DocQcHeader docQcHeader);

    /**
     * 批量删除质检
     *
     * @param qcnos 需要删除的质检主键集合
     * @return 结果
     */
    int deleteDocQcHeaderByQcnos(String[] qcnos);

    /**
     * 删除质检信息
     *
     * @param qcno 质检主键
     * @return 结果
     */
    int deleteDocQcHeaderByQcno(String qcno);

    /**
     * 获得质检列表（根据库存）
     *
     * @return
     */
    List<DocQcListVo> getQcListByInvLotLocId(String sku);

    /**
     * 获取库位列表根据批号
     *
     * @param lotNumber
     * @return
     */
    List<DocQcListVo> getLocationListByLotNumber(String lotNumber);

    /**
     * 质检提交
     *
     * @param sku
     * @param batchNo
     * @param status
     * @return
     */
    AjaxResult qcSubmit(String sku, String batchNo, String status);
}
