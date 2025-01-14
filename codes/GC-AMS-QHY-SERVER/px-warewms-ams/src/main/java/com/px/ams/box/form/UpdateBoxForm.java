package com.px.ams.box.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateBoxForm {
    @NotEmpty(message = "托盘编号不能为空")
    private String palletNo;
    @NotEmpty(message = "是否满拖不能为空")
    private String fullTag;
}
