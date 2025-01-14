package com.px.base.service.impl;

import java.util.Date;
import java.util.List;

import cn.hutool.core.date.DateUtil;
import com.px.base.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.base.mapper.BaseIdsequenceMapper;
import com.px.base.domain.BaseIdsequence;
import com.px.base.service.IBaseIdsequenceService;

/**
 * 主键生成规则Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class BaseIdsequenceServiceImpl implements IBaseIdsequenceService {
    @Autowired
    private BaseIdsequenceMapper baseIdsequenceMapper;

    /**
     * 查询主键生成规则
     *
     * @param idName 主键生成规则主键
     * @return 主键生成规则
     */
    @Override
    public BaseIdsequence selectBaseIdsequenceByIdName(String idName) {
        return baseIdsequenceMapper.selectBaseIdsequenceByIdName(idName);
    }

    /**
     * 查询主键生成规则列表
     *
     * @param baseIdsequence 主键生成规则
     * @return 主键生成规则
     */
    @Override
    public List<BaseIdsequence> selectBaseIdsequenceList(BaseIdsequence baseIdsequence) {
        return baseIdsequenceMapper.selectBaseIdsequenceList(baseIdsequence);
    }

    /**
     * 查询主键生成规则
     *
     * @param baseIdsequence 主键生成规则
     * @return 主键生成规则
     */
    @Override
    public BaseIdsequence selectBaseIdsequenceByModel(BaseIdsequence baseIdsequence) {
        List<BaseIdsequence> list = baseIdsequenceMapper.selectBaseIdsequenceList(baseIdsequence);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增主键生成规则
     *
     * @param baseIdsequence 主键生成规则
     * @return 结果
     */
    @Override
    public int insertBaseIdsequence(BaseIdsequence baseIdsequence) {
        return baseIdsequenceMapper.insertBaseIdsequence(baseIdsequence);
    }

    /**
     * 修改主键生成规则
     *
     * @param baseIdsequence 主键生成规则
     * @return 结果
     */
    @Override
    public int updateBaseIdsequence(BaseIdsequence baseIdsequence) {
        return baseIdsequenceMapper.updateBaseIdsequence(baseIdsequence);
    }

    /**
     * 批量删除主键生成规则
     *
     * @param idNames 需要删除的主键生成规则主键
     * @return 结果
     */
    @Override
    public int deleteBaseIdsequenceByIdNames(String[] idNames) {
        return baseIdsequenceMapper.deleteBaseIdsequenceByIdNames(idNames);
    }

    /**
     * 删除主键生成规则信息
     *
     * @param idName 主键生成规则主键
     * @return 结果
     */
    @Override
    public int deleteBaseIdsequenceByIdName(String idName) {
        return baseIdsequenceMapper.deleteBaseIdsequenceByIdName(idName);
    }

    @Override
    public int updateByVersion(String idName, String dateStr, Long idSequence, int nextId) {
        return baseIdsequenceMapper.updateByVersion(idName, dateStr, idSequence, nextId);
    }

    @Override
    public String getCurrent(String idName) {
        BaseIdsequence sysIdsequence = baseIdsequenceMapper.selectBaseIdsequenceByIdName(idName);
        String prefix;
        String dateStr = "";
        if (!StringUtils.isEmpty(sysIdsequence.getDateFormat()) && (sysIdsequence.getDateFormat().equals("YYYYMMDD") || sysIdsequence.getDateFormat().equals("YYMMDD"))) {
            prefix = sysIdsequence.getPrefix() + DateUtil.format(new Date(), "yMMdd");
            dateStr = DateUtil.format(new Date(), "yMMdd");
        } else {
            prefix = sysIdsequence.getPrefix();
        }
        int index = 0;
        if (sysIdsequence.getDateFormat() == null || sysIdsequence.getDateFormat().equals("") || dateStr.equals(sysIdsequence.getDateMax())) {
            index = sysIdsequence.getIdSequence() == null ? 0 : sysIdsequence.getIdSequence().intValue();
        }
        String resultNo = "";
        if (!StringUtils.isEmpty(prefix)) {
            resultNo = prefix + CommonUtils.lpad((index + 1) + "", sysIdsequence.getLength().intValue(), "0");
        } else {
            resultNo = (index + 1) + "";
        }
        return resultNo;
    }
}
