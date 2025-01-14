package com.px.base.service;

import com.px.base.domain.BaseLocationInfo;
import com.px.base.domain.dto.BaseLocationInfoSameColDTO;
import com.px.base.domain.vo.BasLocationTreeSelectVO;
import com.px.base.domain.vo.BaseLocationLotattListVO;
import com.px.base.domain.vo.BaseLocationLotattVO;
import com.px.base.domain.vo.TreeSelectVO;
import com.px.common.core.domain.AjaxResult;

import java.util.List;
import java.util.Map;

/**
 * 库位信息Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBaseLocationInfoService {
    /**
     * 查询库位信息
     *
     * @param id 库位信息主键
     * @return 库位信息
     */
    BaseLocationInfo selectBaseLocationInfoById(Long id);

    /**
     * 根据id或者编号查询库位信息
     *
     * @param idOrNo
     * @param warehouseId
     * @return
     */
    BaseLocationInfo selectBaseLocationInfoByIdOrNo(String idOrNo, Long warehouseId);


    /**
     * 根据条件查询可分配库位
     *
     * @param zoneId
     * @param bindSku
     * @param orderBy
     * @return
     */
    List<BaseLocationLotattVO> selectAllocatingLocationAccordingConditionsOrderBy(List<String> zoneId
            , String bindSku
            , String orderBy);

    /**
     * 根据条件查询可分配库存
     *
     * @param zoneId
     * @param lotatt
     * @param sku
     * @param orderBy
     * @return
     */
    List<BaseLocationLotattVO> selectAllocatingInventoryAccordingConditionsOrderBy(List<String> zoneId
            , Map<String, String> lotatt
            , String sku
            , String orderBy);

    /**
     * 查询库位信息列表
     *
     * @param baseLocationInfo 库位信息
     * @return 库位信息集合
     */
    List<BaseLocationInfo> selectBaseLocationInfoList(BaseLocationInfo baseLocationInfo);

    /**
     * 查询库位信息列表
     *
     * @param locId 库位ID
     * @return
     */
    List<BaseLocationInfo> selectBaseLocationInfoList(long locId);

    /**
     * 新增库位信息
     *
     * @param baseLocationInfo 库位信息
     * @return 结果
     */
    int insertBaseLocationInfo(BaseLocationInfo baseLocationInfo);

    /**
     * 修改库位信息
     *
     * @param baseLocationInfo 库位信息
     * @return 结果
     */
    int updateBaseLocationInfo(BaseLocationInfo baseLocationInfo);

    /**
     * 批量删除库位信息
     *
     * @param ids 需要删除的库位信息主键集合
     * @return 结果
     */
    int deleteBaseLocationInfoByIds(Long[] ids);

    /**
     * 删除库位信息信息
     *
     * @param id 库位信息主键
     * @return 结果
     */
    int deleteBaseLocationInfoById(Long id);

    /**
     * 构建前端所需要下拉树结构
     *
     * @return 下拉树结构列表
     */
    List<TreeSelectVO> buildLocationTreeSelect();

    /**
     * 构建前端所需要树结构
     *
     * @param zoneId 库位列表
     * @return 树结构列表
     */
    List<BasLocationTreeSelectVO> buildLocationTree(Long zoneId);

    /**
     * 检测是库位还是库区，由于下拉树库位和库区没有做区分所以用这个方法来判断下类型
     *
     * @param idOrNo      id或者编号
     * @param warehouseId 仓库id
     * @return
     */
    boolean checkIsLocation(String idOrNo, Long warehouseId);

    /**
     * 根据zoneId查询库位信息
     * 返回结果会根据排列层进行排序
     *
     * @param zoneId
     * @param warehouseId
     * @return
     */
    List<BaseLocationInfo> selectSortedLocatinListByZoneId(Long zoneId, Long warehouseId, String orderBy);

    /**
     * 根据zoneId查询库位信息
     * 返回结果会根据排列层进行排序，同库位多种物料的数据会合并
     *
     * @param locationId
     * @param warehouseId
     * @param lotatt
     * @param sku
     * @return
     */
    List<BaseLocationLotattListVO> selectSortedLocatinListByZoneId(List<Long> locationId, Long warehouseId, Map<String, String> lotatt, String sku);

    /**
     * 更新库位状态
     *
     * @param status
     * @return
     */
    int updateLocationStockStatus(Long id, String status);

    /**
     * 查询相邻可以存放的库位
     *
     * @param colNo
     * @param zoneId
     * @return
     */
    List<BaseLocationInfo> selectNeighborLocation(String colNo, Long zoneId);


    /**
     * 验证库位任务状态是否空闲
     *
     * @param locationId
     * @return true 空闲
     */
    Boolean verifyLocationIsIdle(String locationId, Long warehouseId);

    /**
     * 验证库位库存是否为占用
     *
     * @param locationId
     * @return true 占用
     */
    Boolean verifyLocationIsInStock(String locationId, Long warehouseId);

    /**
     * 锁定库位
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean lockLocationStockStatus(Long id, Long warehouseId, String updateBy);

    /**
     * 解锁库位
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean unLockLocationStockStatus(Long id, Long warehouseId, String updateBy);

    /**
     * 锁定库位（起始和目标）
     *
     * @param locationFromId
     * @param locationToId
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean lockLocationStockStatus(Long locationFromId, Long locationToId, Long warehouseId, String updateBy);

    /**
     * 解锁库位（起始和目标）
     *
     * @param locationFromId
     * @param locationToId
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean unLockLocationStockStatus(Long locationFromId, Long locationToId, Long warehouseId, String updateBy);

    /**
     * 占领库位状态（是否有货状态）
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean occupyLocation(Long id, Long warehouseId, String updateBy);

    /**
     * 放开占领库位状态（是否有货状态）
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean unOccupyLocation(Long id, Long warehouseId, String updateBy);

    /**
     * 修改库位状态为空闲无货
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean updateLocationIdleAndEmpty(Long id, Long warehouseId, String updateBy);

    /**
     * 修改库位状态为空闲有货
     *
     * @param id
     * @param warehouseId
     * @param updateBy
     * @return
     */
    boolean updateLocationIdleAndNoEmpty(Long id, Long warehouseId, String updateBy);

    /**
     * 查询同列库位是否有阻挡
     *
     * @param colNo
     * @param colIndex
     * @return
     */
    List<BaseLocationInfo> selectBeforeLocationByColNo(String colNo, Long colIndex);

    /**
     * 查询同列可用库位
     *
     * @param colNo
     * @param colIndex
     * @return
     */
    List<BaseLocationInfoSameColDTO> selectSameColCanToLoc(String colNo, Long colIndex);

    /**
     * 导入库位
     *
     * @param list
     * @param updateSupport
     * @param opname
     * @return
     */
    AjaxResult importLocation(List<BaseLocationInfo> list, boolean updateSupport, String opname);
}
