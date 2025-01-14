package com.px.ams.box.service.impl;

import java.util.List;

import com.px.ams.box.form.AddBoxForm;
import com.px.common.core.domain.AjaxResult;
import com.px.common.utils.DateUtils;
import com.px.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.ams.box.mapper.WmsBoxInfoMapper;
import com.px.ams.box.domain.WmsBoxInfo;
import com.px.ams.box.service.IWmsBoxInfoService;

/**
 * 容器管理Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class WmsBoxInfoServiceImpl implements IWmsBoxInfoService {
    @Autowired
    private WmsBoxInfoMapper wmsBoxInfoMapper;

    /**
     * 查询容器管理
     *
     * @param id 容器管理主键
     * @return 容器管理
     */
    @Override
    public WmsBoxInfo selectWmsBoxInfoById(Long id) {
        return wmsBoxInfoMapper.selectWmsBoxInfoById(id);
    }

    /**
     * 查询容器管理列表
     *
     * @param wmsBoxInfo 容器管理
     * @return 容器管理
     */
    @Override
    public List<WmsBoxInfo> selectWmsBoxInfoList(WmsBoxInfo wmsBoxInfo) {
        return wmsBoxInfoMapper.selectWmsBoxInfoList(wmsBoxInfo);
    }

    @Override
    public WmsBoxInfo selectWmsBoxInfoByBoxNo(String boxNo) {
        WmsBoxInfo wmsBoxInfo = new WmsBoxInfo();
        wmsBoxInfo.setBoxNo(boxNo);
        List<WmsBoxInfo> wmsBoxInfoList = this.selectWmsBoxInfoList(wmsBoxInfo);
        if (wmsBoxInfoList != null && wmsBoxInfoList.size() >= 0) {
            return wmsBoxInfoList.get(0);
        }
        return null;
    }

    /**
     * 查询容器管理
     *
     * @param wmsBoxInfo 容器管理
     * @return 容器管理
     */
    @Override
    public WmsBoxInfo selectWmsBoxInfoByModel(WmsBoxInfo wmsBoxInfo) {
        List<WmsBoxInfo> list = wmsBoxInfoMapper.selectWmsBoxInfoList(wmsBoxInfo);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增容器管理
     *
     * @param wmsBoxInfo 容器管理
     * @return 结果
     */
    @Override
    public int insertWmsBoxInfo(WmsBoxInfo wmsBoxInfo) {
        wmsBoxInfo.setCreateTime(DateUtils.getNowDate());
        return wmsBoxInfoMapper.insertWmsBoxInfo(wmsBoxInfo);
    }

    /**
     * 修改容器管理
     *
     * @param wmsBoxInfo 容器管理
     * @return 结果
     */
    @Override
    public int updateWmsBoxInfo(WmsBoxInfo wmsBoxInfo) {
        wmsBoxInfo.setUpdateTime(DateUtils.getNowDate());
        return wmsBoxInfoMapper.updateWmsBoxInfo(wmsBoxInfo);
    }

    /**
     * 批量删除容器管理
     *
     * @param ids 需要删除的容器管理主键
     * @return 结果
     */
    @Override
    public int deleteWmsBoxInfoByIds(Long[] ids) {
        return wmsBoxInfoMapper.deleteWmsBoxInfoByIds(ids);
    }

    /**
     * 删除容器管理信息
     *
     * @param id 容器管理主键
     * @return 结果
     */
    @Override
    public int deleteWmsBoxInfoById(Long id) {
        return wmsBoxInfoMapper.deleteWmsBoxInfoById(id);
    }

    @Override
    public AjaxResult addBoxInfo(AddBoxForm addBoxForm) {
        WmsBoxInfo wmsBoxInfo = wmsBoxInfoMapper.selectWmsBoxInfoByBoxNo(addBoxForm.getBoxNo());
        if (wmsBoxInfo != null) {
            //TODO 移动库存
            return AjaxResult.error("该容器已存在");
        } else {
            WmsBoxInfo boxInfo = new WmsBoxInfo();
            boxInfo.setBoxNo(addBoxForm.getBoxNo());
            if (!StringUtils.isEmpty(addBoxForm.getLocationId())) {
                boxInfo.setLocationId(Long.parseLong(addBoxForm.getLocationId()));
            }
            boxInfo.setBoxState(1);
            boxInfo.setIsEmpty("Y");
            boxInfo.setIsFull("N");
            boxInfo.setBoxType("pallet");
            int result = wmsBoxInfoMapper.insertWmsBoxInfo(boxInfo);
            if (result > 0) {
                return AjaxResult.success("操作成功");
            } else {
                return AjaxResult.error("操作失败");
            }
        }

    }

    @Override
    public WmsBoxInfo selectByLocationId(String locationId) {
        WmsBoxInfo wmsBoxInfo = wmsBoxInfoMapper.selectByLocationId(locationId);
        return wmsBoxInfo;
    }

    @Override
    public int updateLocationBind(Long locationFrom, Long locationTo, String isFull) {
        return wmsBoxInfoMapper.updateLocationBind(locationFrom, locationTo, isFull);
    }
}
