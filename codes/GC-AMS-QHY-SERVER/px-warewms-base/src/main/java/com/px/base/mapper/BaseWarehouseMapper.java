package com.px.base.mapper;

import java.util.List;
import com.px.base.domain.BaseWarehouse;

/**
 * 仓库信息Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface BaseWarehouseMapper 
{
    /**
     * 查询仓库信息
     * 
     * @param warehouseId 仓库信息主键
     * @return 仓库信息
     */
     BaseWarehouse selectBaseWarehouseByWarehouseId(Long warehouseId);

    /**
     * 查询仓库信息列表
     * 
     * @param baseWarehouse 仓库信息
     * @return 仓库信息集合
     */
     List<BaseWarehouse> selectBaseWarehouseList(BaseWarehouse baseWarehouse);

    /**
     * 新增仓库信息
     * 
     * @param baseWarehouse 仓库信息
     * @return 结果
     */
     int insertBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 修改仓库信息
     * 
     * @param baseWarehouse 仓库信息
     * @return 结果
     */
     int updateBaseWarehouse(BaseWarehouse baseWarehouse);

    /**
     * 删除仓库信息
     * 
     * @param warehouseId 仓库信息主键
     * @return 结果
     */
     int deleteBaseWarehouseByWarehouseId(Long warehouseId);

    /**
     * 批量删除仓库信息
     * 
     * @param warehouseIds 需要删除的数据主键集合
     * @return 结果
     */
     int deleteBaseWarehouseByWarehouseIds(Long[] warehouseIds);
}
