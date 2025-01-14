package com.px.ams.order.form;

import lombok.Data;

@Data
public class ArrangeStockForm {
    //private String allocationId;
    private String orderNo;
    private String palletNoFrom;
    private String palletNoTo;
    //箱号
    private String boxNo;
    private String sn;
    private String qty;
    private String isFull;
}
