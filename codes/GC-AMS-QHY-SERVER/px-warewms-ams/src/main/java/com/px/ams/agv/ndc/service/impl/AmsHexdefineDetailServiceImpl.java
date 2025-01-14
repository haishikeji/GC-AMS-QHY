package com.px.ams.agv.ndc.service.impl;

import java.util.List;

import com.px.ams.agv.ndc.common.ValType;
import com.px.ams.agv.ndc.domain.AmsHexdefineDetail;
import com.px.ams.agv.ndc.domain.AmsTask;
import com.px.ams.agv.ndc.mapper.AmsHexdefineDetailMapper;
import com.px.ams.agv.ndc.service.IAmsHexdefineDetailService;
import com.px.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务定义（lp0-lp30）Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class AmsHexdefineDetailServiceImpl implements IAmsHexdefineDetailService
{
    @Autowired
    private AmsHexdefineDetailMapper amsHexdefineDetailMapper;

    /**
     * 查询任务定义（lp0-lp30）
     *
     * @param id 任务定义（lp0-lp30）主键
     * @return 任务定义（lp0-lp30）
     */
    @Override
    public AmsHexdefineDetail selectAmsHexdefineDetailById(Long id)
    {
        return amsHexdefineDetailMapper.selectAmsHexdefineDetailById(id);
    }

    @Override
    public List<AmsHexdefineDetail> selectAmsHexdefineDetailByBusinessType(String businessType) {
        return amsHexdefineDetailMapper.selectAmsHexdefineDetailByBusinessType(businessType);
    }

    /**
     * 查询任务定义（lp0-lp30）列表
     *
     * @param amsHexdefineDetail 任务定义（lp0-lp30）
     * @return 任务定义（lp0-lp30）
     */
    @Override
    public List<AmsHexdefineDetail> selectAmsHexdefineDetailList(AmsHexdefineDetail amsHexdefineDetail)
    {
        return amsHexdefineDetailMapper.selectAmsHexdefineDetailList(amsHexdefineDetail);
    }

    /**
     * 查询任务定义（lp0-lp30）
     *
     * @param amsHexdefineDetail 任务定义（lp0-lp30）
     * @return 任务定义（lp0-lp30）
     */
    @Override
    public AmsHexdefineDetail selectAmsHexdefineDetailByModel(AmsHexdefineDetail amsHexdefineDetail)
    {
        List<AmsHexdefineDetail> list = amsHexdefineDetailMapper.selectAmsHexdefineDetailList(amsHexdefineDetail);
        if (list!=null && list.size()>0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增任务定义（lp0-lp30）
     *
     * @param amsHexdefineDetail 任务定义（lp0-lp30）
     * @return 结果
     */
    @Override
    public int insertAmsHexdefineDetail(AmsHexdefineDetail amsHexdefineDetail)
    {
        return amsHexdefineDetailMapper.insertAmsHexdefineDetail(amsHexdefineDetail);
    }

    /**
     * 修改任务定义（lp0-lp30）
     *
     * @param amsHexdefineDetail 任务定义（lp0-lp30）
     * @return 结果
     */
    @Override
    public int updateAmsHexdefineDetail(AmsHexdefineDetail amsHexdefineDetail)
    {
        return amsHexdefineDetailMapper.updateAmsHexdefineDetail(amsHexdefineDetail);
    }

    /**
     * 批量删除任务定义（lp0-lp30）
     *
     * @param ids 需要删除的任务定义（lp0-lp30）主键
     * @return 结果
     */
    @Override
    public int deleteAmsHexdefineDetailByIds(Long[] ids)
    {
        return amsHexdefineDetailMapper.deleteAmsHexdefineDetailByIds(ids);
    }

    /**
     * 删除任务定义（lp0-lp30）信息
     *
     * @param id 任务定义（lp0-lp30）主键
     * @return 结果
     */
    @Override
    public int deleteAmsHexdefineDetailById(Long id)
    {
        return amsHexdefineDetailMapper.deleteAmsHexdefineDetailById(id);
    }

    @Override
    public String concatOrder(AmsTask amsTask, List<AmsHexdefineDetail> amsHexdefineDetails) {
        StringBuffer sb = new StringBuffer();
        for (AmsHexdefineDetail basTask : amsHexdefineDetails) {
            //System.out.println(basTask.getColName());
            //字符串直接取数据库保存值
            if (basTask.getColType() == ValType.TEXT.getCode()) {
                //如果是数字需要进行转换
                if (basTask.getValType() == 2) {
                    if (StringUtils.isEmpty(amsTask.getStr(basTask.getColVal()))) {
                        sb.append(String.format("%04X", Integer.parseInt("0")));
                    }else {
                        sb.append(String.format("%04X", Integer.parseInt(amsTask.getStr(basTask.getColVal()))));
                    }
                } else {
                    sb.append(basTask.getColVal());
                }
            } else {
                //如果是绑定值需要从任务表中获取，如果是数字
                if (basTask.getValType() == 1) {
                    //如果是数字需要进行转换，Priority为2位其余4位
                    if (basTask.getColName().equals("Priority")) {
                        sb.append(String.format("%02X", Integer.parseInt(amsTask.getStr(basTask.getColVal()))+ 0x80));
                    }else {
                        if (StringUtils.isEmpty(amsTask.getStr(basTask.getColVal()))) {
                            sb.append(String.format("%04X", Integer.parseInt("0")));
                        }else {
                            sb.append(String.format("%04X", Integer.parseInt(amsTask.getStr(basTask.getColVal()))));
                        }
                    }
                } else {
                    if (StringUtils.isEmpty(amsTask.getStr(basTask.getColVal()))) {
                        sb.append("0000");
                    }else {
                        sb.append(amsTask.getStr(basTask.getColVal()));
                    }
                }
            }
        }
        return sb.toString();
    }
}
