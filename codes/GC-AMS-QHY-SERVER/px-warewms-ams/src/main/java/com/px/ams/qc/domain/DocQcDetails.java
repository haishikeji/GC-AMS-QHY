package com.px.ams.qc.domain;

import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 质检对象 wms_doc_qc_details
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class DocQcDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String qcno;

    /** $column.columnComment */
    private String qclineno;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String linestatus;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal palineno;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String customerid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String sku;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String lotnum;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal paqtyExpected;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal qcqtyExpected;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private BigDecimal qcqtyCompleted;

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
    private String qcdescr;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String qcresult;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String filecontent;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String notes;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date addtime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String addwho;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date edittime;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String editwho;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String packid;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String transactionid;

    public void setQcno(String qcno)
    {
        this.qcno = qcno;
    }

    public String getQcno()
    {
        return qcno;
    }
    public void setQclineno(String qclineno)
    {
        this.qclineno = qclineno;
    }

    public String getQclineno()
    {
        return qclineno;
    }
    public void setLinestatus(String linestatus)
    {
        this.linestatus = linestatus;
    }

    public String getLinestatus()
    {
        return linestatus;
    }
    public void setPalineno(BigDecimal palineno)
    {
        this.palineno = palineno;
    }

    public BigDecimal getPalineno()
    {
        return palineno;
    }
    public void setCustomerid(String customerid)
    {
        this.customerid = customerid;
    }

    public String getCustomerid()
    {
        return customerid;
    }
    public void setSku(String sku)
    {
        this.sku = sku;
    }

    public String getSku()
    {
        return sku;
    }
    public void setLotnum(String lotnum)
    {
        this.lotnum = lotnum;
    }

    public String getLotnum()
    {
        return lotnum;
    }
    public void setPaqtyExpected(BigDecimal paqtyExpected)
    {
        this.paqtyExpected = paqtyExpected;
    }

    public BigDecimal getPaqtyExpected()
    {
        return paqtyExpected;
    }
    public void setQcqtyExpected(BigDecimal qcqtyExpected)
    {
        this.qcqtyExpected = qcqtyExpected;
    }

    public BigDecimal getQcqtyExpected()
    {
        return qcqtyExpected;
    }
    public void setQcqtyCompleted(BigDecimal qcqtyCompleted)
    {
        this.qcqtyCompleted = qcqtyCompleted;
    }

    public BigDecimal getQcqtyCompleted()
    {
        return qcqtyCompleted;
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
    public void setQcdescr(String qcdescr)
    {
        this.qcdescr = qcdescr;
    }

    public String getQcdescr()
    {
        return qcdescr;
    }
    public void setQcresult(String qcresult)
    {
        this.qcresult = qcresult;
    }

    public String getQcresult()
    {
        return qcresult;
    }
    public void setFilecontent(String filecontent)
    {
        this.filecontent = filecontent;
    }

    public String getFilecontent()
    {
        return filecontent;
    }
    public void setNotes(String notes)
    {
        this.notes = notes;
    }

    public String getNotes()
    {
        return notes;
    }
    public void setAddtime(Date addtime)
    {
        this.addtime = addtime;
    }

    public Date getAddtime()
    {
        return addtime;
    }
    public void setAddwho(String addwho)
    {
        this.addwho = addwho;
    }

    public String getAddwho()
    {
        return addwho;
    }
    public void setEdittime(Date edittime)
    {
        this.edittime = edittime;
    }

    public Date getEdittime()
    {
        return edittime;
    }
    public void setEditwho(String editwho)
    {
        this.editwho = editwho;
    }

    public String getEditwho()
    {
        return editwho;
    }
    public void setPackid(String packid)
    {
        this.packid = packid;
    }

    public String getPackid()
    {
        return packid;
    }
    public void setTransactionid(String transactionid)
    {
        this.transactionid = transactionid;
    }

    public String getTransactionid()
    {
        return transactionid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("qcno", getQcno())
            .append("qclineno", getQclineno())
            .append("linestatus", getLinestatus())
            .append("palineno", getPalineno())
            .append("customerid", getCustomerid())
            .append("sku", getSku())
            .append("lotnum", getLotnum())
            .append("paqtyExpected", getPaqtyExpected())
            .append("qcqtyExpected", getQcqtyExpected())
            .append("qcqtyCompleted", getQcqtyCompleted())
            .append("userdefine1", getUserdefine1())
            .append("userdefine2", getUserdefine2())
            .append("userdefine3", getUserdefine3())
            .append("userdefine4", getUserdefine4())
            .append("userdefine5", getUserdefine5())
            .append("qcdescr", getQcdescr())
            .append("qcresult", getQcresult())
            .append("filecontent", getFilecontent())
            .append("notes", getNotes())
            .append("addtime", getAddtime())
            .append("addwho", getAddwho())
            .append("edittime", getEdittime())
            .append("editwho", getEditwho())
            .append("packid", getPackid())
            .append("transactionid", getTransactionid())
            .toString();
    }
}
