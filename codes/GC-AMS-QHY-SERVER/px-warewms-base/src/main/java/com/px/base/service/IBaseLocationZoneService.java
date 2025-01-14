package com.px.base.service;

import java.util.List;
import com.px.base.domain.BaseLocationZone;
import com.px.base.domain.vo.BasLocationTreeSelectVO;
import com.px.base.domain.vo.TreeSelectVO;

/**
 * 库区Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface IBaseLocationZoneService 
{
    /**
     * 查询库区
     * 
     * @param zoneId 库区主键
     * @return 库区
     */
     BaseLocationZone selectBaseLocationZoneByZoneId(Long zoneId);

    /**
     * 查询库区列表
     * 
     * @param baseLocationZone 库区
     * @return 库区集合
     */
     List<BaseLocationZone> selectBaseLocationZoneList(BaseLocationZone baseLocationZone);

    /**
     * 新增库区
     * 
     * @param baseLocationZone 库区
     * @return 结果
     */
     int insertBaseLocationZone(BaseLocationZone baseLocationZone);

    /**
     * 修改库区
     * 
     * @param baseLocationZone 库区
     * @return 结果
     */
     int updateBaseLocationZone(BaseLocationZone baseLocationZone);

    /**
     * 批量删除库区
     * 
     * @param zoneIds 需要删除的库区主键集合
     * @return 结果
     */
     int deleteBaseLocationZoneByZoneIds(Long[] zoneIds);

    /**
     * 删除库区信息
     * 
     * @param zoneId 库区主键
     * @return 结果
     */
     int deleteBaseLocationZoneByZoneId(Long zoneId);

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
}
