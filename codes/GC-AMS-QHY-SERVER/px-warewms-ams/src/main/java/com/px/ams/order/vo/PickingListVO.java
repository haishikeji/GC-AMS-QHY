package com.px.ams.order.vo;

import lombok.Data;

@Data
public class PickingListVO {
    private String orderNo;
    private String sku;
    private Double pickQty;
    private Double toBePickQty;
}
