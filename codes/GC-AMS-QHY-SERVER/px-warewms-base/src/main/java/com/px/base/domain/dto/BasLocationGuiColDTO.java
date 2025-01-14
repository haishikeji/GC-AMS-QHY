package com.px.base.domain.dto;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: kaka
 * Date: 2023/03/22
 */
@Data
public class BasLocationGuiColDTO {
    /**
     * 列号
     */
    private String colNo;
    /**
     * 列号对应的库存信息
     */
    private List<BaseLocationGuiDTO> baseLocationGuiDTOList = new LinkedList<>();

    public void addBaslocation(BaseLocationGuiDTO baseLocationGuiDTO) {
        baseLocationGuiDTOList.add(baseLocationGuiDTO);
    }
}
