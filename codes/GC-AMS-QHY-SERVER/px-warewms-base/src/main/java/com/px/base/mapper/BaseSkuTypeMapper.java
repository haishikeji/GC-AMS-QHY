package com.px.base.mapper;

import java.util.List;
import com.px.base.domain.BaseSkuType;

/**
 * 物料分类Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface BaseSkuTypeMapper 
{
    /**
     * 查询物料分类
     * 
     * @param itemCode 物料分类主键
     * @return 物料分类
     */
     BaseSkuType selectBaseSkuTypeByItemCode(String itemCode);

    /**
     * 查询物料分类列表
     * 
     * @param baseSkuType 物料分类
     * @return 物料分类集合
     */
     List<BaseSkuType> selectBaseSkuTypeList(BaseSkuType baseSkuType);

    /**
     * 新增物料分类
     * 
     * @param baseSkuType 物料分类
     * @return 结果
     */
     int insertBaseSkuType(BaseSkuType baseSkuType);

    /**
     * 修改物料分类
     * 
     * @param baseSkuType 物料分类
     * @return 结果
     */
     int updateBaseSkuType(BaseSkuType baseSkuType);

    /**
     * 删除物料分类
     * 
     * @param itemCode 物料分类主键
     * @return 结果
     */
     int deleteBaseSkuTypeByItemCode(String itemCode);

    /**
     * 批量删除物料分类
     * 
     * @param itemCodes 需要删除的数据主键集合
     * @return 结果
     */
     int deleteBaseSkuTypeByItemCodes(String[] itemCodes);
}
