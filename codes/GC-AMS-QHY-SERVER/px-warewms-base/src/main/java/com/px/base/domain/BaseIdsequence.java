package com.px.base.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 主键生成规则对象 base_idsequence
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class BaseIdsequence extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String idName;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String descr;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String idType;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long idSequence;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Long maxIdSequence;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String createUserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date editTime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String editUserId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String prefix;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal length;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dateFormat;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dateMax;

    /** $column.columnComment */
    private String warehouseid;

    public void setIdName(String idName) 
    {
        this.idName = idName;
    }

    public String getIdName() 
    {
        return idName;
    }
    public void setDescr(String descr) 
    {
        this.descr = descr;
    }

    public String getDescr() 
    {
        return descr;
    }
    public void setIdType(String idType) 
    {
        this.idType = idType;
    }

    public String getIdType() 
    {
        return idType;
    }
    public void setIdSequence(Long idSequence) 
    {
        this.idSequence = idSequence;
    }

    public Long getIdSequence() 
    {
        return idSequence;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setMaxIdSequence(Long maxIdSequence) 
    {
        this.maxIdSequence = maxIdSequence;
    }

    public Long getMaxIdSequence() 
    {
        return maxIdSequence;
    }
    public void setCreateUserId(String createUserId) 
    {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() 
    {
        return createUserId;
    }
    public void setEditTime(Date editTime) 
    {
        this.editTime = editTime;
    }

    public Date getEditTime() 
    {
        return editTime;
    }
    public void setEditUserId(String editUserId) 
    {
        this.editUserId = editUserId;
    }

    public String getEditUserId() 
    {
        return editUserId;
    }
    public void setPrefix(String prefix) 
    {
        this.prefix = prefix;
    }

    public String getPrefix() 
    {
        return prefix;
    }
    public void setLength(BigDecimal length) 
    {
        this.length = length;
    }

    public BigDecimal getLength() 
    {
        return length;
    }
    public void setDateFormat(String dateFormat) 
    {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() 
    {
        return dateFormat;
    }
    public void setDateMax(String dateMax) 
    {
        this.dateMax = dateMax;
    }

    public String getDateMax() 
    {
        return dateMax;
    }
    public void setWarehouseid(String warehouseid) 
    {
        this.warehouseid = warehouseid;
    }

    public String getWarehouseid() 
    {
        return warehouseid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("idName", getIdName())
            .append("descr", getDescr())
            .append("idType", getIdType())
            .append("idSequence", getIdSequence())
            .append("createDate", getCreateDate())
            .append("maxIdSequence", getMaxIdSequence())
            .append("createUserId", getCreateUserId())
            .append("editTime", getEditTime())
            .append("editUserId", getEditUserId())
            .append("prefix", getPrefix())
            .append("length", getLength())
            .append("dateFormat", getDateFormat())
            .append("dateMax", getDateMax())
            .append("warehouseid", getWarehouseid())
            .toString();
    }
}
