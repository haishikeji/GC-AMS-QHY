package com.px.ams.lineCall.service;

import com.px.ams.lineCall.domain.vo.LineCallVO;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2022/3/7
 */
public interface ILineCallService {
    /**
     * 线边呼叫
     * @param flowConfigId
     * @return
     */
    LineCallVO selectLineCall(Long flowConfigId,Long warehouseId);
}
