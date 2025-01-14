package com.px.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 库位信息对象 base_location_info
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class BaseLocationInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    private Long warehouseId;

    /**
     * 区域id
     */
    @Excel(name = "区域id")
    private Long zoneId;

    /**
     * 区域id
     */
    @Excel(name = "库区名称")
    private String zoneName;

    /**
     * 库位编码
     */
    @Excel(name = "库位编码")
    private String locationNo;

    /**
     * 条形码
     */
    private String locationBarcode;

    /**
     * 排号
     */
    @Excel(name = "排号")
    private String rowNo;

    /**
     * 排序号
     */
    @Excel(name = "排序号")
    private Long rowIndex;

    /**
     * 列号
     */
    @Excel(name = "列号")
    private String colNo;

    /**
     * 列序号
     */
    @Excel(name = "列序号")
    private Long colIndex;

    /**
     * 层号
     */
    @Excel(name = "层号")
    private String shiftNo;

    /**
     * 层序号
     */
    @Excel(name = "层序号")
    private Long shiftIndex;

    /**
     * 储位状态(00)空闲,(10)占用,(20)完成,(90)合格,(99)不合格(80封存)
     */
    private String stockStatus;

    /**
     * 是否为空(Y空/N非空)
     */
    @Excel(name = "是否为空")
    private String isEmpty;

    /**
     * 指定产品
     */
    @Excel(name = "绑定物料类型（id）")
    private String bindSku;

    /**
     * 储位类型（1货架2地堆3流离货架）
     */
    @Excel(name = "储位类型(1=货架,2=地堆)")
    private String locationType;

    /**
     * 盘架编号
     */
    private String rackId;

    /**
     * AGV点位
     */
    @Excel(name = "AGV点位")
    private Long agvStation;

    /**
     * 0默认(没有任何含义),1允许堆叠
     */
    private String foldedTag;

    /**
     * 叠空托盘上限（当folded_tag为1时有效）
     */
    private Long foldedCount;

    private Double heightLimit;

    private Double weightLimit;

    private String userdefine1;

    private String userdefine2;

    private String userdefine3;

    private String userdefine4;

    private String userdefine5;

    private String userdefine6;

    private String userdefine7;

    private String userdefine8;

    private String userdefine9;
    @Excel(name = "绑定物料编号")
    private String userdefine10;

    private String orderByClause;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setLocationNo(String locationNo) {
        this.locationNo = locationNo;
    }

    public String getLocationNo() {
        return locationNo;
    }

    public void setLocationBarcode(String locationBarcode) {
        this.locationBarcode = locationBarcode;
    }

    public String getLocationBarcode() {
        return locationBarcode;
    }

    public void setRowNo(String rowNo) {
        this.rowNo = rowNo;
    }

    public String getRowNo() {
        return rowNo;
    }

    public void setRowIndex(Long rowIndex) {
        this.rowIndex = rowIndex;
    }

    public Long getRowIndex() {
        return rowIndex;
    }

    public void setColNo(String colNo) {
        this.colNo = colNo;
    }

    public String getColNo() {
        return colNo;
    }

    public void setColIndex(Long colIndex) {
        this.colIndex = colIndex;
    }

    public Long getColIndex() {
        return colIndex;
    }

    public void setShiftNo(String shiftNo) {
        this.shiftNo = shiftNo;
    }

    public String getShiftNo() {
        return shiftNo;
    }

    public void setShiftIndex(Long shiftIndex) {
        this.shiftIndex = shiftIndex;
    }

    public Long getShiftIndex() {
        return shiftIndex;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setIsEmpty(String isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getIsEmpty() {
        return isEmpty;
    }

    public void setBindSku(String bindSku) {
        this.bindSku = bindSku;
    }

    public String getBindSku() {
        return bindSku;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setRackId(String rackId) {
        this.rackId = rackId;
    }

    public String getRackId() {
        return rackId;
    }

    public void setAgvStation(Long agvStation) {
        this.agvStation = agvStation;
    }

    public Long getAgvStation() {
        return agvStation;
    }

    public void setFoldedTag(String foldedTag) {
        this.foldedTag = foldedTag;
    }

    public String getFoldedTag() {
        return foldedTag;
    }

    public void setFoldedCount(Long foldedCount) {
        this.foldedCount = foldedCount;
    }

    public Long getFoldedCount() {
        return foldedCount;
    }

    public void setUserdefine1(String userdefine1) {
        this.userdefine1 = userdefine1;
    }

    public String getUserdefine1() {
        return userdefine1;
    }

    public void setUserdefine2(String userdefine2) {
        this.userdefine2 = userdefine2;
    }

    public String getUserdefine2() {
        return userdefine2;
    }

    public void setUserdefine3(String userdefine3) {
        this.userdefine3 = userdefine3;
    }

    public String getUserdefine3() {
        return userdefine3;
    }

    public void setUserdefine4(String userdefine4) {
        this.userdefine4 = userdefine4;
    }

    public String getUserdefine4() {
        return userdefine4;
    }

    public void setUserdefine5(String userdefine5) {
        this.userdefine5 = userdefine5;
    }

    public String getUserdefine5() {
        return userdefine5;
    }

    public void setUserdefine6(String userdefine6) {
        this.userdefine6 = userdefine6;
    }

    public String getUserdefine6() {
        return userdefine6;
    }

    public void setUserdefine7(String userdefine7) {
        this.userdefine7 = userdefine7;
    }

    public String getUserdefine7() {
        return userdefine7;
    }

    public void setUserdefine8(String userdefine8) {
        this.userdefine8 = userdefine8;
    }

    public String getUserdefine8() {
        return userdefine8;
    }

    public void setUserdefine9(String userdefine9) {
        this.userdefine9 = userdefine9;
    }

    public String getUserdefine9() {
        return userdefine9;
    }

    public void setUserdefine10(String userdefine10) {
        this.userdefine10 = userdefine10;
    }

    public String getUserdefine10() {
        return userdefine10;
    }

    public Double getHeightLimit() {
        return heightLimit;
    }

    public void setHeightLimit(Double heightLimit) {
        this.heightLimit = heightLimit;
    }

    public Double getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(Double weightLimit) {
        this.weightLimit = weightLimit;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("warehouseId", getWarehouseId())
                .append("zoneId", getZoneId())
                .append("locationNo", getLocationNo())
                .append("locationBarcode", getLocationBarcode())
                .append("rowNo", getRowNo())
                .append("rowIndex", getRowIndex())
                .append("colNo", getColNo())
                .append("colIndex", getColIndex())
                .append("shiftNo", getShiftNo())
                .append("shiftIndex", getShiftIndex())
                .append("stockStatus", getStockStatus())
                .append("isEmpty", getIsEmpty())
                .append("bindSku", getBindSku())
                .append("locationType", getLocationType())
                .append("rackId", getRackId())
                .append("agvStation", getAgvStation())
                .append("foldedTag", getFoldedTag())
                .append("foldedCount", getFoldedCount())
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
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}
