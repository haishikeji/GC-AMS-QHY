package com.px.ams.asn.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 入库单明细对象 wms_doc_asn_details
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class WmsDocAsnDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 行号 */
    private Long asnLineNo;

    /** 入库单号 */
    private String asnNo;

    /** 产品 */
    @Excel(name = "产品")
    private String sku;

    /** 预期件数 */
    @Excel(name = "预期件数")
    private BigDecimal expectedQty;

    /** 预期数量_EA */
    @Excel(name = "预期数量_EA")
    private BigDecimal expectedQtyEach;

    /** 收货件数 */
    @Excel(name = "收货件数")
    private BigDecimal receivedQty;

    /** 收货数量 */
    @Excel(name = "收货数量")
    private BigDecimal receivedQtyEach;

    /** 行状态(00新建,10上架) */
    @Excel(name = "行状态(00新建,10上架)")
    private String lineStatus;

    /** 冻结原因 */
    @Excel(name = "冻结原因")
    private String holdRejectreason;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    /** 包装 */
    @Excel(name = "包装")
    private String packId;

    /** 指定库区 */
    @Excel(name = "指定库区")
    private String locationZone;

    /** 批号 */
    @Excel(name = "批号")
    private String lotatt01;

    /** 版本号 */
    @Excel(name = "版本号")
    private String lotatt02;

    /** 生产日期 */
    @Excel(name = "生产日期")
    private String lotatt03;

    /** 有效期 */
    @Excel(name = "有效期")
    private String lotatt04;

    /** 入库单号 */
    @Excel(name = "入库单号")
    private String lotatt05;

    /** poNo */
    @Excel(name = "poNo")
    private String lotatt06;

    /** 质量状态 */
    @Excel(name = "质量状态")
    private String lotatt07;

    /** 供应商 */
    @Excel(name = "供应商")
    private String lotatt08;

    /**  */
    @Excel(name = "")
    private String lotatt09;

    /** 检验日期 */
    @Excel(name = "检验日期")
    private String lotatt10;

    /**  */
    @Excel(name = "")
    private String lotatt11;

    /**  */
    @Excel(name = "")
    private String lotatt12;

    /**  */
    @Excel(name = "")
    private String lotatt13;

    /**  */
    @Excel(name = "")
    private String lotatt14;

    /**  */
    @Excel(name = "")
    private String lotatt15;

    /**  */
    @Excel(name = "")
    private String lotatt16;

    /**  */
    @Excel(name = "")
    private String lotatt17;

    /**  */
    @Excel(name = "")
    private String lotatt18;

    /** 指定库位 */
    @Excel(name = "指定库位")
    private Long locationId;

    /** sap_doucment_id */
    @Excel(name = "sap_doucment_id")
    private String documentId;

    /** 归还人 */
    @Excel(name = "归还人")
    private String dEdi01;

    /**  */
    @Excel(name = "")
    private String dEdi02;

    /**  */
    @Excel(name = "")
    private String dEdi03;

    /**  */
    @Excel(name = "")
    private String dEdi04;

    /**  */
    @Excel(name = "")
    private String dEdi05;

    /**  */
    @Excel(name = "")
    private String dEdi06;

    /**  */
    @Excel(name = "")
    private String dEdi07;

    /**  */
    @Excel(name = "")
    private String dEdi08;

    /**  */
    @Excel(name = "")
    private String dEdi09;

    /**  */
    @Excel(name = "")
    private String dEdi10;

    /**  */
    @Excel(name = "")
    private String dEdi11;

    /**  */
    @Excel(name = "")
    private String dEdi12;

    /**  */
    @Excel(name = "")
    private String dEdi13;

    /**  */
    @Excel(name = "")
    private String dEdi14;

    /**  */
    @Excel(name = "")
    private String dEdi15;

    public void setAsnLineNo(Long asnLineNo) 
    {
        this.asnLineNo = asnLineNo;
    }

    public Long getAsnLineNo() 
    {
        return asnLineNo;
    }
    public void setAsnNo(String asnNo) 
    {
        this.asnNo = asnNo;
    }

    public String getAsnNo() 
    {
        return asnNo;
    }
    public void setSku(String sku) 
    {
        this.sku = sku;
    }

    public String getSku() 
    {
        return sku;
    }
    public void setExpectedQty(BigDecimal expectedQty) 
    {
        this.expectedQty = expectedQty;
    }

    public BigDecimal getExpectedQty() 
    {
        return expectedQty;
    }
    public void setExpectedQtyEach(BigDecimal expectedQtyEach) 
    {
        this.expectedQtyEach = expectedQtyEach;
    }

    public BigDecimal getExpectedQtyEach() 
    {
        return expectedQtyEach;
    }
    public void setReceivedQty(BigDecimal receivedQty) 
    {
        this.receivedQty = receivedQty;
    }

    public BigDecimal getReceivedQty() 
    {
        return receivedQty;
    }
    public void setReceivedQtyEach(BigDecimal receivedQtyEach) 
    {
        this.receivedQtyEach = receivedQtyEach;
    }

    public BigDecimal getReceivedQtyEach() 
    {
        return receivedQtyEach;
    }
    public void setLineStatus(String lineStatus) 
    {
        this.lineStatus = lineStatus;
    }

    public String getLineStatus() 
    {
        return lineStatus;
    }
    public void setHoldRejectreason(String holdRejectreason) 
    {
        this.holdRejectreason = holdRejectreason;
    }

    public String getHoldRejectreason() 
    {
        return holdRejectreason;
    }
    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }
    public void setPackId(String packId) 
    {
        this.packId = packId;
    }

    public String getPackId() 
    {
        return packId;
    }
    public void setLocationZone(String locationZone) 
    {
        this.locationZone = locationZone;
    }

    public String getLocationZone() 
    {
        return locationZone;
    }
    public void setLotatt01(String lotatt01) 
    {
        this.lotatt01 = lotatt01;
    }

    public String getLotatt01() 
    {
        return lotatt01;
    }
    public void setLotatt02(String lotatt02) 
    {
        this.lotatt02 = lotatt02;
    }

    public String getLotatt02() 
    {
        return lotatt02;
    }
    public void setLotatt03(String lotatt03) 
    {
        this.lotatt03 = lotatt03;
    }

    public String getLotatt03() 
    {
        return lotatt03;
    }
    public void setLotatt04(String lotatt04) 
    {
        this.lotatt04 = lotatt04;
    }

    public String getLotatt04() 
    {
        return lotatt04;
    }
    public void setLotatt05(String lotatt05) 
    {
        this.lotatt05 = lotatt05;
    }

    public String getLotatt05() 
    {
        return lotatt05;
    }
    public void setLotatt06(String lotatt06) 
    {
        this.lotatt06 = lotatt06;
    }

    public String getLotatt06() 
    {
        return lotatt06;
    }
    public void setLotatt07(String lotatt07) 
    {
        this.lotatt07 = lotatt07;
    }

    public String getLotatt07() 
    {
        return lotatt07;
    }
    public void setLotatt08(String lotatt08) 
    {
        this.lotatt08 = lotatt08;
    }

    public String getLotatt08() 
    {
        return lotatt08;
    }
    public void setLotatt09(String lotatt09) 
    {
        this.lotatt09 = lotatt09;
    }

    public String getLotatt09() 
    {
        return lotatt09;
    }
    public void setLotatt10(String lotatt10) 
    {
        this.lotatt10 = lotatt10;
    }

    public String getLotatt10() 
    {
        return lotatt10;
    }
    public void setLotatt11(String lotatt11) 
    {
        this.lotatt11 = lotatt11;
    }

    public String getLotatt11() 
    {
        return lotatt11;
    }
    public void setLotatt12(String lotatt12) 
    {
        this.lotatt12 = lotatt12;
    }

    public String getLotatt12() 
    {
        return lotatt12;
    }
    public void setLotatt13(String lotatt13) 
    {
        this.lotatt13 = lotatt13;
    }

    public String getLotatt13() 
    {
        return lotatt13;
    }
    public void setLotatt14(String lotatt14) 
    {
        this.lotatt14 = lotatt14;
    }

    public String getLotatt14() 
    {
        return lotatt14;
    }
    public void setLotatt15(String lotatt15) 
    {
        this.lotatt15 = lotatt15;
    }

    public String getLotatt15() 
    {
        return lotatt15;
    }
    public void setLotatt16(String lotatt16) 
    {
        this.lotatt16 = lotatt16;
    }

    public String getLotatt16() 
    {
        return lotatt16;
    }
    public void setLotatt17(String lotatt17) 
    {
        this.lotatt17 = lotatt17;
    }

    public String getLotatt17() 
    {
        return lotatt17;
    }
    public void setLotatt18(String lotatt18) 
    {
        this.lotatt18 = lotatt18;
    }

    public String getLotatt18() 
    {
        return lotatt18;
    }
    public void setLocationId(Long locationId) 
    {
        this.locationId = locationId;
    }

    public Long getLocationId() 
    {
        return locationId;
    }
    public void setDocumentId(String documentId) 
    {
        this.documentId = documentId;
    }

    public String getDocumentId() 
    {
        return documentId;
    }
    public void setdEdi01(String dEdi01) 
    {
        this.dEdi01 = dEdi01;
    }

    public String getdEdi01() 
    {
        return dEdi01;
    }
    public void setdEdi02(String dEdi02) 
    {
        this.dEdi02 = dEdi02;
    }

    public String getdEdi02() 
    {
        return dEdi02;
    }
    public void setdEdi03(String dEdi03) 
    {
        this.dEdi03 = dEdi03;
    }

    public String getdEdi03() 
    {
        return dEdi03;
    }
    public void setdEdi04(String dEdi04) 
    {
        this.dEdi04 = dEdi04;
    }

    public String getdEdi04() 
    {
        return dEdi04;
    }
    public void setdEdi05(String dEdi05) 
    {
        this.dEdi05 = dEdi05;
    }

    public String getdEdi05() 
    {
        return dEdi05;
    }
    public void setdEdi06(String dEdi06) 
    {
        this.dEdi06 = dEdi06;
    }

    public String getdEdi06() 
    {
        return dEdi06;
    }
    public void setdEdi07(String dEdi07) 
    {
        this.dEdi07 = dEdi07;
    }

    public String getdEdi07() 
    {
        return dEdi07;
    }
    public void setdEdi08(String dEdi08) 
    {
        this.dEdi08 = dEdi08;
    }

    public String getdEdi08() 
    {
        return dEdi08;
    }
    public void setdEdi09(String dEdi09) 
    {
        this.dEdi09 = dEdi09;
    }

    public String getdEdi09() 
    {
        return dEdi09;
    }
    public void setdEdi10(String dEdi10) 
    {
        this.dEdi10 = dEdi10;
    }

    public String getdEdi10() 
    {
        return dEdi10;
    }
    public void setdEdi11(String dEdi11) 
    {
        this.dEdi11 = dEdi11;
    }

    public String getdEdi11() 
    {
        return dEdi11;
    }
    public void setdEdi12(String dEdi12) 
    {
        this.dEdi12 = dEdi12;
    }

    public String getdEdi12() 
    {
        return dEdi12;
    }
    public void setdEdi13(String dEdi13) 
    {
        this.dEdi13 = dEdi13;
    }

    public String getdEdi13() 
    {
        return dEdi13;
    }
    public void setdEdi14(String dEdi14) 
    {
        this.dEdi14 = dEdi14;
    }

    public String getdEdi14() 
    {
        return dEdi14;
    }
    public void setdEdi15(String dEdi15) 
    {
        this.dEdi15 = dEdi15;
    }

    public String getdEdi15() 
    {
        return dEdi15;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("asnLineNo", getAsnLineNo())
            .append("asnNo", getAsnNo())
            .append("sku", getSku())
            .append("expectedQty", getExpectedQty())
            .append("expectedQtyEach", getExpectedQtyEach())
            .append("receivedQty", getReceivedQty())
            .append("receivedQtyEach", getReceivedQtyEach())
            .append("lineStatus", getLineStatus())
            .append("holdRejectreason", getHoldRejectreason())
            .append("notes", getNotes())
            .append("packId", getPackId())
            .append("locationZone", getLocationZone())
            .append("lotatt01", getLotatt01())
            .append("lotatt02", getLotatt02())
            .append("lotatt03", getLotatt03())
            .append("lotatt04", getLotatt04())
            .append("lotatt05", getLotatt05())
            .append("lotatt06", getLotatt06())
            .append("lotatt07", getLotatt07())
            .append("lotatt08", getLotatt08())
            .append("lotatt09", getLotatt09())
            .append("lotatt10", getLotatt10())
            .append("lotatt11", getLotatt11())
            .append("lotatt12", getLotatt12())
            .append("lotatt13", getLotatt13())
            .append("lotatt14", getLotatt14())
            .append("lotatt15", getLotatt15())
            .append("lotatt16", getLotatt16())
            .append("lotatt17", getLotatt17())
            .append("lotatt18", getLotatt18())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("locationId", getLocationId())
            .append("documentId", getDocumentId())
            .append("dEdi01", getdEdi01())
            .append("dEdi02", getdEdi02())
            .append("dEdi03", getdEdi03())
            .append("dEdi04", getdEdi04())
            .append("dEdi05", getdEdi05())
            .append("dEdi06", getdEdi06())
            .append("dEdi07", getdEdi07())
            .append("dEdi08", getdEdi08())
            .append("dEdi09", getdEdi09())
            .append("dEdi10", getdEdi10())
            .append("dEdi11", getdEdi11())
            .append("dEdi12", getdEdi12())
            .append("dEdi13", getdEdi13())
            .append("dEdi14", getdEdi14())
            .append("dEdi15", getdEdi15())
            .toString();
    }
}
