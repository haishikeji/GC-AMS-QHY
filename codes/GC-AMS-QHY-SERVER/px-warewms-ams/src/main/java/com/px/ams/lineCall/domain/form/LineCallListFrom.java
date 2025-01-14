package com.px.ams.lineCall.domain.form;

import java.io.Serializable;
import java.util.List;

public class LineCallListFrom implements Serializable {
    //编号
    private Long id;
    //物料
    private String sku;
    //数量
    private Double qty;
    //重量
    private Double weight;
    //供应商
    private String supplier;
    //起始（库位或者区域）
    private String locationFrom;
    //目标（库位或者区域）
    private String locationTo;
    //托盘号
    private String palletNo;
    //批次属性
    private List<LineCallDetailsForm> lineCallDetailsFormList;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
        this.qty = qty;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getLocationFrom() {
        return locationFrom;
    }

    public void setLocationFrom(String locationFrom) {
        this.locationFrom = locationFrom;
    }

    public String getLocationTo() {
        return locationTo;
    }

    public void setLocationTo(String locationTo) {
        this.locationTo = locationTo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPalletNo() {
        return palletNo;
    }

    public void setPalletNo(String palletNo) {
        this.palletNo = palletNo;
    }

    public List<LineCallDetailsForm> getLineCallDetailsFormList() {
        return lineCallDetailsFormList;
    }

    public void setLineCallDetailsFormList(List<LineCallDetailsForm> lineCallDetailsFormList) {
        this.lineCallDetailsFormList = lineCallDetailsFormList;
    }
}
