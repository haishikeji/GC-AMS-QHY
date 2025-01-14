package com.px.base.mapper;

import java.util.List;
import com.px.base.domain.BaseLocationZone;

/**
 * 库区Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface BaseLocationZoneMapper 
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
     * 删除库区
     * 
     * @param zoneId 库区主键
     * @return 结果
     */
     int deleteBaseLocationZoneByZoneId(Long zoneId);

    /**
     * 批量删除库区
     * 
     * @param zoneIds 需要删除的数据主键集合
     * @return 结果
     */
     int deleteBaseLocationZoneByZoneIds(Long[] zoneIds);
}
