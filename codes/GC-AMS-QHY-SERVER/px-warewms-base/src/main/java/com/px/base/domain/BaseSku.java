package com.px.base.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 物料信息对象 base_sku
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class BaseSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 货主编号 */
    private String customerId;

    /** 产品编号 */
    private String sku;

    /** 产品类型 */
    @Excel(name = "产品类型")
    private String skuType;

    /** 规格 */
    @Excel(name = "规格")
    private String specs;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 描述1 */
    @Excel(name = "描述1")
    private String desc1;

    /** 描述2 */
    @Excel(name = "描述2")
    private String desc2;

    /** 自赋码1 */
    @Excel(name = "自赋码1")
    private String selfCode1;

    /** 自赋码2 */
    @Excel(name = "自赋码2")
    private String selfCode2;

    /** 是否危险品 */
    @Excel(name = "是否危险品")
    private String hazardFlag;

    /** 包装 */
    @Excel(name = "包装")
    private String packId;

    /** 皮重 */
    @Excel(name = "皮重")
    private BigDecimal tare;

    /** 毛重 */
    @Excel(name = "毛重")
    private BigDecimal grossweight;

    /** 净重 */
    @Excel(name = "净重")
    private BigDecimal netweight;

    /** 是否激活 */
    @Excel(name = "是否激活")
    private String activeFlag;

    /** 上架库位 */
    @Excel(name = "上架库位")
    private String putawayLocation;

    /** 上架区域 */
    private String putawayZone;

    /** 上架规则 */
    private String putawayRule;

    /** 高 */
    @Excel(name = "高")
    private BigDecimal skuHigh;

    /** 长 */
    @Excel(name = "长")
    private BigDecimal skuLength;

    /** 宽 */
    @Excel(name = "宽")
    private BigDecimal skuWidth;

    /** 库存上限 */
    @Excel(name = "库存上限")
    private BigDecimal qtymax;

    /** 库存下限 */
    @Excel(name = "库存下限")
    private BigDecimal qtymin;

    /** 默认收货单位 */
    @Excel(name = "默认收货单位")
    private String defaultReceivingUom;

    /** 默认发货单位 */
    @Excel(name = "默认发货单位")
    private String defaultShipmentUom;

    /** 质检规则 */
    @Excel(name = "质检规则")
    private String qcRule;

    /** 质检周期 */
    @Excel(name = "质检周期")
    private BigDecimal qcTime;

    /** 有效期(天) */
    @Excel(name = "有效期(天)")
    private Long shelflife;

    /** 冷链标志 */
    @Excel(name = "冷链标志")
    private String coldFlag;

    /** 灭菌标志 */
    @Excel(name = "灭菌标志")
    private String sterilizationFlag;

    /** 养护周期(天) */
    @Excel(name = "养护周期(天)")
    private BigDecimal curingCycle;

    /** 生产企业 */
    @Excel(name = "生产企业")
    private String manufacturer;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine1;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine2;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine3;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine4;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine5;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine6;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine7;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine8;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine9;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String userdefine10;

    /** 是否检测效期 */
    @Excel(name = "是否检测效期")
    private String expirationDateCheck;

    /** 是否允许超收 */
    @Excel(name = "是否允许超收")
    private String overchargeCheck;

    /** 是否需要质检 */
    @Excel(name = "是否需要质检")
    private String qcCheck;

    public void setCustomerId(String customerId) 
    {
        this.customerId = customerId;
    }

    public String getCustomerId() 
    {
        return customerId;
    }
    public void setSku(String sku) 
    {
        this.sku = sku;
    }

    public String getSku() 
    {
        return sku;
    }
    public void setSkuType(String skuType) 
    {
        this.skuType = skuType;
    }

    public String getSkuType() 
    {
        return skuType;
    }
    public void setSpecs(String specs) 
    {
        this.specs = specs;
    }

    public String getSpecs() 
    {
        return specs;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setDesc1(String desc1) 
    {
        this.desc1 = desc1;
    }

    public String getDesc1() 
    {
        return desc1;
    }
    public void setDesc2(String desc2) 
    {
        this.desc2 = desc2;
    }

    public String getDesc2() 
    {
        return desc2;
    }
    public void setSelfCode1(String selfCode1) 
    {
        this.selfCode1 = selfCode1;
    }

    public String getSelfCode1() 
    {
        return selfCode1;
    }
    public void setSelfCode2(String selfCode2) 
    {
        this.selfCode2 = selfCode2;
    }

    public String getSelfCode2() 
    {
        return selfCode2;
    }
    public void setHazardFlag(String hazardFlag) 
    {
        this.hazardFlag = hazardFlag;
    }

    public String getHazardFlag() 
    {
        return hazardFlag;
    }
    public void setPackId(String packId) 
    {
        this.packId = packId;
    }

    public String getPackId() 
    {
        return packId;
    }
    public void setTare(BigDecimal tare) 
    {
        this.tare = tare;
    }

    public BigDecimal getTare() 
    {
        return tare;
    }
    public void setGrossweight(BigDecimal grossweight) 
    {
        this.grossweight = grossweight;
    }

    public BigDecimal getGrossweight() 
    {
        return grossweight;
    }
    public void setNetweight(BigDecimal netweight) 
    {
        this.netweight = netweight;
    }

    public BigDecimal getNetweight() 
    {
        return netweight;
    }
    public void setActiveFlag(String activeFlag) 
    {
        this.activeFlag = activeFlag;
    }

    public String getActiveFlag() 
    {
        return activeFlag;
    }
    public void setPutawayLocation(String putawayLocation) 
    {
        this.putawayLocation = putawayLocation;
    }

    public String getPutawayLocation() 
    {
        return putawayLocation;
    }
    public void setPutawayZone(String putawayZone) 
    {
        this.putawayZone = putawayZone;
    }

    public String getPutawayZone() 
    {
        return putawayZone;
    }
    public void setPutawayRule(String putawayRule) 
    {
        this.putawayRule = putawayRule;
    }

    public String getPutawayRule() 
    {
        return putawayRule;
    }
    public void setSkuHigh(BigDecimal skuHigh) 
    {
        this.skuHigh = skuHigh;
    }

    public BigDecimal getSkuHigh() 
    {
        return skuHigh;
    }
    public void setSkuLength(BigDecimal skuLength) 
    {
        this.skuLength = skuLength;
    }

    public BigDecimal getSkuLength() 
    {
        return skuLength;
    }
    public void setSkuWidth(BigDecimal skuWidth) 
    {
        this.skuWidth = skuWidth;
    }

    public BigDecimal getSkuWidth() 
    {
        return skuWidth;
    }
    public void setQtymax(BigDecimal qtymax) 
    {
        this.qtymax = qtymax;
    }

    public BigDecimal getQtymax() 
    {
        return qtymax;
    }
    public void setQtymin(BigDecimal qtymin) 
    {
        this.qtymin = qtymin;
    }

    public BigDecimal getQtymin() 
    {
        return qtymin;
    }
    public void setDefaultReceivingUom(String defaultReceivingUom) 
    {
        this.defaultReceivingUom = defaultReceivingUom;
    }

    public String getDefaultReceivingUom() 
    {
        return defaultReceivingUom;
    }
    public void setDefaultShipmentUom(String defaultShipmentUom) 
    {
        this.defaultShipmentUom = defaultShipmentUom;
    }

    public String getDefaultShipmentUom() 
    {
        return defaultShipmentUom;
    }
    public void setQcRule(String qcRule) 
    {
        this.qcRule = qcRule;
    }

    public String getQcRule() 
    {
        return qcRule;
    }
    public void setQcTime(BigDecimal qcTime) 
    {
        this.qcTime = qcTime;
    }

    public BigDecimal getQcTime() 
    {
        return qcTime;
    }
    public void setShelflife(Long shelflife) 
    {
        this.shelflife = shelflife;
    }

    public Long getShelflife() 
    {
        return shelflife;
    }
    public void setColdFlag(String coldFlag) 
    {
        this.coldFlag = coldFlag;
    }

    public String getColdFlag() 
    {
        return coldFlag;
    }
    public void setSterilizationFlag(String sterilizationFlag) 
    {
        this.sterilizationFlag = sterilizationFlag;
    }

    public String getSterilizationFlag() 
    {
        return sterilizationFlag;
    }
    public void setCuringCycle(BigDecimal curingCycle) 
    {
        this.curingCycle = curingCycle;
    }

    public BigDecimal getCuringCycle() 
    {
        return curingCycle;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setUserdefine1(String userdefine1) 
    {
        this.userdefine1 = userdefine1;
    }

    public String getUserdefine1() 
    {
        return userdefine1;
    }
    public void setUserdefine2(String userdefine2) 
    {
        this.userdefine2 = userdefine2;
    }

    public String getUserdefine2() 
    {
        return userdefine2;
    }
    public void setUserdefine3(String userdefine3) 
    {
        this.userdefine3 = userdefine3;
    }

    public String getUserdefine3() 
    {
        return userdefine3;
    }
    public void setUserdefine4(String userdefine4) 
    {
        this.userdefine4 = userdefine4;
    }

    public String getUserdefine4() 
    {
        return userdefine4;
    }
    public void setUserdefine5(String userdefine5) 
    {
        this.userdefine5 = userdefine5;
    }

    public String getUserdefine5() 
    {
        return userdefine5;
    }
    public void setUserdefine6(String userdefine6) 
    {
        this.userdefine6 = userdefine6;
    }

    public String getUserdefine6() 
    {
        return userdefine6;
    }
    public void setUserdefine7(String userdefine7) 
    {
        this.userdefine7 = userdefine7;
    }

    public String getUserdefine7() 
    {
        return userdefine7;
    }
    public void setUserdefine8(String userdefine8) 
    {
        this.userdefine8 = userdefine8;
    }

    public String getUserdefine8() 
    {
        return userdefine8;
    }
    public void setUserdefine9(String userdefine9) 
    {
        this.userdefine9 = userdefine9;
    }

    public String getUserdefine9() 
    {
        return userdefine9;
    }
    public void setUserdefine10(String userdefine10) 
    {
        this.userdefine10 = userdefine10;
    }

    public String getUserdefine10() 
    {
        return userdefine10;
    }
    public void setExpirationDateCheck(String expirationDateCheck) 
    {
        this.expirationDateCheck = expirationDateCheck;
    }

    public String getExpirationDateCheck() 
    {
        return expirationDateCheck;
    }
    public void setOverchargeCheck(String overchargeCheck) 
    {
        this.overchargeCheck = overchargeCheck;
    }

    public String getOverchargeCheck() 
    {
        return overchargeCheck;
    }
    public void setQcCheck(String qcCheck) 
    {
        this.qcCheck = qcCheck;
    }

    public String getQcCheck() 
    {
        return qcCheck;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("customerId", getCustomerId())
            .append("sku", getSku())
            .append("skuType", getSkuType())
            .append("specs", getSpecs())
            .append("model", getModel())
            .append("desc1", getDesc1())
            .append("desc2", getDesc2())
            .append("selfCode1", getSelfCode1())
            .append("selfCode2", getSelfCode2())
            .append("hazardFlag", getHazardFlag())
            .append("packId", getPackId())
            .append("tare", getTare())
            .append("grossweight", getGrossweight())
            .append("netweight", getNetweight())
            .append("activeFlag", getActiveFlag())
            .append("putawayLocation", getPutawayLocation())
            .append("putawayZone", getPutawayZone())
            .append("putawayRule", getPutawayRule())
            .append("skuHigh", getSkuHigh())
            .append("skuLength", getSkuLength())
            .append("skuWidth", getSkuWidth())
            .append("qtymax", getQtymax())
            .append("qtymin", getQtymin())
            .append("defaultReceivingUom", getDefaultReceivingUom())
            .append("defaultShipmentUom", getDefaultShipmentUom())
            .append("qcRule", getQcRule())
            .append("qcTime", getQcTime())
            .append("shelflife", getShelflife())
            .append("coldFlag", getColdFlag())
            .append("sterilizationFlag", getSterilizationFlag())
            .append("curingCycle", getCuringCycle())
            .append("manufacturer", getManufacturer())
            .append("userdefine1", getUserdefine1())
            .append("userdefine2", getUserdefine2())
            .append("userdefine3", getUserdefine3())
            .append("userdefine4", getUserdefine4())
            .append("userdefine5", getUserdefine5())
            .append("userdefine6", getUserdefine6())
            .append("userdefine7", getUserdefine7())
            .append("userdefine8", getUserdefine8())
            .append("userdefine9", getUserdefine9())
            .append("userdefine10", getUserdefine10())
            .append("expirationDateCheck", getExpirationDateCheck())
            .append("overchargeCheck", getOverchargeCheck())
            .append("qcCheck", getQcCheck())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
