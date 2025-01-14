package com.px.ams.business.domain;

import com.px.ams.config.domain.dto.OutWarehouseDTO;
import com.px.base.domain.BaseLocationInfo;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2022/9/30
 * Description:
 */
@Data
public class FilterLockInvLocationDTO {
    private BaseLocationInfo locationInfo;
    private OutWarehouseDTO outWarehouseDTO;
}