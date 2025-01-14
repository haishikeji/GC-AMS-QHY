package com.px.base.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SnCheckForm {
    @NotEmpty(message = "条码不能为空")
    private String sn;
    private List<String> asnNoList;
}
