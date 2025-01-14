package com.px.ams.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WmsDocOrderDTO {
    private String orderNo;
    private Long orderLineNo;
    private BigDecimal qty;
}
