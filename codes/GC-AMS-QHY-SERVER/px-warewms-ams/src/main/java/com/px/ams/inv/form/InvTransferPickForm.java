package com.px.ams.inv.form;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class InvTransferPickForm {
    @NotEmpty(message = "起始托盘号不能为空")
    private String palletNoFrom;
    @NotEmpty(message = "目标托盘号不能为空")
    private String palletNoTo;
    @NotEmpty(message = "sn不能为空")
    private String sn;
    @NotEmpty(message = "数量不能为空")
    private String qty;
    @NotEmpty
    private String asnNo;
}
