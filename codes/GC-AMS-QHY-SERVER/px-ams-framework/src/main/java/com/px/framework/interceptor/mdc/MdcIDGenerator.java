package com.px.framework.interceptor.mdc;

import cn.hutool.core.util.NumberUtil;

public class MdcIDGenerator {


    public static String generateId() {
        return NumberUtil.toStr(System.nanoTime(), NumberUtil.toStr(Thread.currentThread().getId()));
    }
}
