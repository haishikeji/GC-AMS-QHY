package com.px.ams.asn.form;

import lombok.Data;

@Data
public class UnlockForm {
    private String palletNo;
    private String sku;
    private String lotnum;
    private Double qty;
}
