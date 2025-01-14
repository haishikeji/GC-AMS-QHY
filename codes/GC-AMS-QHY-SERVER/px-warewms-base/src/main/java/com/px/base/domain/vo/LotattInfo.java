package com.px.base.domain.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andy.qu
 * Date: 2022/9/28
 * Description:
 */
public class LotattInfo {
    private String sku;
    private Float qty;
    private LotattVO lotattVO;
    private Map<String, String> attMap;

    public void initLotatt() {
        attMap = new HashMap<>();
        if (lotattVO == null) {
            lotattVO = new LotattVO();
        }
        this.attMap.put("lotatt01", lotattVO.getLotatt01());
        this.attMap.put("lotatt02", lotattVO.getLotatt02());
        this.attMap.put("lotatt03", lotattVO.getLotatt03());
        this.attMap.put("lotatt04", lotattVO.getLotatt04());
        this.attMap.put("lotatt05", lotattVO.getLotatt05());
        this.attMap.put("lotatt06", lotattVO.getLotatt06());
        this.attMap.put("lotatt07", lotattVO.getLotatt07());
        this.attMap.put("lotatt08", lotattVO.getLotatt08());
        this.attMap.put("lotatt09", lotattVO.getLotatt09());
        this.attMap.put("lotatt10", lotattVO.getLotatt10());
        this.attMap.put("lotatt11", lotattVO.getLotatt11());
        this.attMap.put("lotatt12", lotattVO.getLotatt12());
        this.attMap.put("lotatt13", lotattVO.getLotatt13());
        this.attMap.put("lotatt14", lotattVO.getLotatt14());
        this.attMap.put("lotatt15", lotattVO.getLotatt15());
        this.attMap.put("lotatt16", lotattVO.getLotatt16());
        this.attMap.put("lotatt17", lotattVO.getLotatt17());
        this.attMap.put("lotatt18", lotattVO.getLotatt18());
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public LotattVO getLotattVO() {
        return lotattVO;
    }

    public void setLotattVO(LotattVO lotattVO) {
        this.lotattVO = lotattVO;
    }

    public Map<String, String> getAttMap() {
        return attMap;
    }

    public void setAttMap(Map<String, String> attMap) {
        this.attMap = attMap;
    }
}