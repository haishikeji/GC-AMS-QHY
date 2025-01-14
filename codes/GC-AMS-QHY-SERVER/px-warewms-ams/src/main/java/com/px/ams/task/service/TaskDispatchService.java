package com.px.ams.task.service;

import com.px.ams.task.domain.WcsTask;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2022/3/21
 */
@Component
public class TaskDispatchService {

    /**
     * 任务调度策略
     * @param wcsTask
     * @return
     */
    public boolean taskDispatchStack(WcsTask wcsTask) {
        return false;
    }
}