package com.px.ams.qc.mapper;

import java.util.List;

import com.px.ams.qc.domain.DocQcDetails;

/**
 * 质检Mapper接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface DocQcDetailsMapper {
    /**
     * 查询质检
     *
     * @param qcno     质检主键
     * @param qclineno 质检主键
     * @return 质检
     */
    DocQcDetails selectDocQcDetailsByQcno(String qcno, String qclineno);

    /**
     * 查询质检列表
     *
     * @param docQcDetails 质检
     * @return 质检集合
     */
    List<DocQcDetails> selectDocQcDetailsList(DocQcDetails docQcDetails);

    /**
     * 新增质检
     *
     * @param docQcDetails 质检
     * @return 结果
     */
    int insertDocQcDetails(DocQcDetails docQcDetails);

    /**
     * 修改质检
     *
     * @param docQcDetails 质检
     * @return 结果
     */
    int updateDocQcDetails(DocQcDetails docQcDetails);

    /**
     * 删除质检
     *
     * @param qcno 质检主键
     * @return 结果
     */
    int deleteDocQcDetailsByQcno(String qcno);

    /**
     * 批量删除质检
     *
     * @param qcnos 需要删除的数据主键集合
     * @return 结果
     */
    int deleteDocQcDetailsByQcnos(String[] qcnos);
}
