package com.px.ams.task.dto;

import com.px.ams.task.domain.WcsTask;
import lombok.Data;

@Data
public class WcsTaskLocationDTO extends WcsTask {
    private String colNo;
    private Integer colIndex;
}
