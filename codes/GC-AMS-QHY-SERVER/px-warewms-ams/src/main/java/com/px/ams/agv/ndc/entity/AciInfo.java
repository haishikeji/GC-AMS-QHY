package com.px.ams.agv.ndc.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2019/9/17
 */
public class AciInfo {
    private static List<Order> orders = new ArrayList<Order>();
    private static List<Messageb> messagebs = new ArrayList<Messageb>();
    private static List<CarStatus> carStatus = new ArrayList<CarStatus>();
    private static boolean needUpdate = false;
    private static boolean sendTaskOrder = false;

    public static boolean isNeedUpdate() {
        return needUpdate;
    }

    public static void setNeedUpdate(boolean needUpdate) {
        AciInfo.needUpdate = needUpdate;
    }

    public static boolean isSendTaskOrder() {
        return sendTaskOrder;
    }

    public static void setSendTaskOrder(boolean sendTaskOrder) {
        AciInfo.sendTaskOrder = sendTaskOrder;
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void setOrders(List<Order> orders) {
        AciInfo.orders = orders;
    }

    public static List<Messageb> getMessagebs() {
        return messagebs;
    }

    public static void setMessagebs(List<Messageb> messagebs) {
        AciInfo.messagebs = messagebs;
    }

    public static List<CarStatus> getCarStatus() {
        return carStatus;
    }

    public static void setCarStatus(List<CarStatus> carStatus) {
        AciInfo.carStatus = carStatus;
    }
}