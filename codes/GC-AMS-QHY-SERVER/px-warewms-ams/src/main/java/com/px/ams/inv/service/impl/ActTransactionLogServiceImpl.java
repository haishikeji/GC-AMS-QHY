package com.px.ams.inv.service.impl;

import java.util.List;

import com.px.base.utils.IdSequenceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.ams.inv.mapper.ActTransactionLogMapper;
import com.px.ams.inv.domain.ActTransactionLog;
import com.px.ams.inv.service.IActTransactionLogService;

/**
 * 库存事务Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class ActTransactionLogServiceImpl implements IActTransactionLogService {
    @Autowired
    private ActTransactionLogMapper actTransactionLogMapper;
    @Autowired
    private IdSequenceUtils idSequenceUtils;

    /**
     * 查询库存事务
     *
     * @param transactionId 库存事务主键
     * @return 库存事务
     */
    @Override
    public ActTransactionLog selectActTransactionLogByTransactionId(String transactionId) {
        return actTransactionLogMapper.selectActTransactionLogByTransactionId(transactionId);
    }

    /**
     * 查询库存事务列表
     *
     * @param actTransactionLog 库存事务
     * @return 库存事务
     */
    @Override
    public List<ActTransactionLog> selectActTransactionLogList(ActTransactionLog actTransactionLog) {
        return actTransactionLogMapper.selectActTransactionLogList(actTransactionLog);
    }

    /**
     * 查询库存事务
     *
     * @param actTransactionLog 库存事务
     * @return 库存事务
     */
    @Override
    public ActTransactionLog selectActTransactionLogByModel(ActTransactionLog actTransactionLog) {
        List<ActTransactionLog> list = actTransactionLogMapper.selectActTransactionLogList(actTransactionLog);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 新增库存事务
     *
     * @param actTransactionLog 库存事务
     * @return 结果
     */
    @Override
    public int insertActTransactionLog(ActTransactionLog actTransactionLog) {
        return actTransactionLogMapper.insertActTransactionLog(actTransactionLog);
    }

    /**
     * 修改库存事务
     *
     * @param actTransactionLog 库存事务
     * @return 结果
     */
    @Override
    public int updateActTransactionLog(ActTransactionLog actTransactionLog) {
        return actTransactionLogMapper.updateActTransactionLog(actTransactionLog);
    }

    /**
     * 批量删除库存事务
     *
     * @param transactionIds 需要删除的库存事务主键
     * @return 结果
     */
    @Override
    public int deleteActTransactionLogByTransactionIds(String[] transactionIds) {
        return actTransactionLogMapper.deleteActTransactionLogByTransactionIds(transactionIds);
    }

    /**
     * 删除库存事务信息
     *
     * @param transactionId 库存事务主键
     * @return 结果
     */
    @Override
    public int deleteActTransactionLogByTransactionId(String transactionId) {
        return actTransactionLogMapper.deleteActTransactionLogByTransactionId(transactionId);
    }
}
