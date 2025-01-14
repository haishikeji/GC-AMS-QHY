package com.px.ams.asn.vo;

import com.px.ams.asn.domain.WmsDocAsnDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WmsDocAsnDetailsVO extends WmsDocAsnDetails {
    private String skuName;
    private String lineStatusName;
}
