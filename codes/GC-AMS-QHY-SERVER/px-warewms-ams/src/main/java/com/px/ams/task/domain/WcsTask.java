package com.px.ams.task.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * AGV任务对象 wcs_task
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class WcsTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** AGV任务主键 */
    private String taskNo;

    /** AGV任务类型 */
    private String businessType;

    /** 任务类型，历史原因需要存任务类型的字段 */
    private String whTypeFrom;

    /** 任务关联单号1 */
    private String whNoFrom;

    /** 任务关联单号类型2 */
    private String whTypeTo;

    /** 任务关联单号2 */
    private String whNoTo;

    /** 调用系统名称 */
    @Excel(name = "调用系统名称")
    private String sysName;

    /** 调用设备名称 */
    @Excel(name = "调用设备名称")
    private String deviceName;

    /** 批号 */
    private String batchNo;

    /** 批号2 */
    private Long batchOrderNo;

    /** 起始点位 */
    @Excel(name = "起始点位")
    private String locationFrom;

    /** 起始区域 */
    @Excel(name = "起始区域")
    private String areaFrom;

    /** 终点点位 */
    @Excel(name = "终点点位")
    private String locationTo;

    /** 终点区域 */
    @Excel(name = "终点区域")
    private String areaTo;

    /** 优先级（用于任务排序与ndc不同，10间隔中间插队） */
    @Excel(name = "优先级", readConverterExp = "用=于任务排序与ndc不同，10间隔中间插队")
    private Long priority;

    /** ִ状态9:(队列等待) 0:(下达AMS成功) */
    @Excel(name = "ִ状态9:(队列等待) 0:(下达AMS成功)")
    private Long state;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    /** 编辑人 */
    private Long updateUser;

    /** 编辑时间 */
    private Date updateDate;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String taskType;

    /** 仓库 */
    @Excel(name = "仓库")
    private String shopId;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 父任务单号 */
    @Excel(name = "父任务单号")
    private String parentTask;

    /** 拓展属性 */
    private String extParam;

    /** 拓展字段1 */
    private String ext1;

    /** 拓展字段2 */
    private String ext2;

    /** 拓展字段3 */
    private String ext3;

    /** $column.columnComment */
    private String ext4;

    /** $column.columnComment */
    private String ext5;

    /** $column.columnComment */
    private String ext6;

    /** $column.columnComment */
    private String ext7;

    /** $column.columnComment */
    private String ext8;

    /** AGV接收车号 */
    @Excel(name = "AGV接收车号")
    private String agvNo;

    /** 是否有前置任务，有必须等待前置任务完成 */
    private String beforeTask;

    public void setTaskNo(String taskNo) 
    {
        this.taskNo = taskNo;
    }

    public String getTaskNo() 
    {
        return taskNo;
    }
    public void setBusinessType(String businessType) 
    {
        this.businessType = businessType;
    }

    public String getBusinessType() 
    {
        return businessType;
    }
    public void setWhTypeFrom(String whTypeFrom) 
    {
        this.whTypeFrom = whTypeFrom;
    }

    public String getWhTypeFrom() 
    {
        return whTypeFrom;
    }
    public void setWhNoFrom(String whNoFrom) 
    {
        this.whNoFrom = whNoFrom;
    }

    public String getWhNoFrom() 
    {
        return whNoFrom;
    }
    public void setWhTypeTo(String whTypeTo) 
    {
        this.whTypeTo = whTypeTo;
    }

    public String getWhTypeTo() 
    {
        return whTypeTo;
    }
    public void setWhNoTo(String whNoTo) 
    {
        this.whNoTo = whNoTo;
    }

    public String getWhNoTo() 
    {
        return whNoTo;
    }
    public void setSysName(String sysName) 
    {
        this.sysName = sysName;
    }

    public String getSysName() 
    {
        return sysName;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setBatchNo(String batchNo) 
    {
        this.batchNo = batchNo;
    }

    public String getBatchNo() 
    {
        return batchNo;
    }
    public void setBatchOrderNo(Long batchOrderNo) 
    {
        this.batchOrderNo = batchOrderNo;
    }

    public Long getBatchOrderNo() 
    {
        return batchOrderNo;
    }
    public void setLocationFrom(String locationFrom) 
    {
        this.locationFrom = locationFrom;
    }

    public String getLocationFrom() 
    {
        return locationFrom;
    }
    public void setAreaFrom(String areaFrom) 
    {
        this.areaFrom = areaFrom;
    }

    public String getAreaFrom() 
    {
        return areaFrom;
    }
    public void setLocationTo(String locationTo) 
    {
        this.locationTo = locationTo;
    }

    public String getLocationTo() 
    {
        return locationTo;
    }
    public void setAreaTo(String areaTo) 
    {
        this.areaTo = areaTo;
    }

    public String getAreaTo() 
    {
        return areaTo;
    }
    public void setPriority(Long priority) 
    {
        this.priority = priority;
    }

    public Long getPriority() 
    {
        return priority;
    }
    public void setState(Long state) 
    {
        this.state = state;
    }

    public Long getState() 
    {
        return state;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setCreateDate(Date createDate) 
    {
        this.createDate = createDate;
    }

    public Date getCreateDate() 
    {
        return createDate;
    }
    public void setUpdateUser(Long updateUser) 
    {
        this.updateUser = updateUser;
    }

    public Long getUpdateUser() 
    {
        return updateUser;
    }
    public void setUpdateDate(Date updateDate) 
    {
        this.updateDate = updateDate;
    }

    public Date getUpdateDate() 
    {
        return updateDate;
    }
    public void setTaskType(String taskType) 
    {
        this.taskType = taskType;
    }

    public String getTaskType() 
    {
        return taskType;
    }
    public void setShopId(String shopId) 
    {
        this.shopId = shopId;
    }

    public String getShopId() 
    {
        return shopId;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setParentTask(String parentTask) 
    {
        this.parentTask = parentTask;
    }

    public String getParentTask() 
    {
        return parentTask;
    }
    public void setExtParam(String extParam) 
    {
        this.extParam = extParam;
    }

    public String getExtParam() 
    {
        return extParam;
    }
    public void setExt1(String ext1) 
    {
        this.ext1 = ext1;
    }

    public String getExt1() 
    {
        return ext1;
    }
    public void setExt2(String ext2) 
    {
        this.ext2 = ext2;
    }

    public String getExt2() 
    {
        return ext2;
    }
    public void setExt3(String ext3) 
    {
        this.ext3 = ext3;
    }

    public String getExt3() 
    {
        return ext3;
    }
    public void setExt4(String ext4) 
    {
        this.ext4 = ext4;
    }

    public String getExt4() 
    {
        return ext4;
    }
    public void setExt5(String ext5) 
    {
        this.ext5 = ext5;
    }

    public String getExt5() 
    {
        return ext5;
    }
    public void setExt6(String ext6) 
    {
        this.ext6 = ext6;
    }

    public String getExt6() 
    {
        return ext6;
    }
    public void setExt7(String ext7) 
    {
        this.ext7 = ext7;
    }

    public String getExt7() 
    {
        return ext7;
    }
    public void setExt8(String ext8) 
    {
        this.ext8 = ext8;
    }

    public String getExt8() 
    {
        return ext8;
    }
    public void setAgvNo(String agvNo) 
    {
        this.agvNo = agvNo;
    }

    public String getAgvNo() 
    {
        return agvNo;
    }
    public void setBeforeTask(String beforeTask) 
    {
        this.beforeTask = beforeTask;
    }

    public String getBeforeTask() 
    {
        return beforeTask;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("taskNo", getTaskNo())
            .append("businessType", getBusinessType())
            .append("whTypeFrom", getWhTypeFrom())
            .append("whNoFrom", getWhNoFrom())
            .append("whTypeTo", getWhTypeTo())
            .append("whNoTo", getWhNoTo())
            .append("sysName", getSysName())
            .append("deviceName", getDeviceName())
            .append("batchNo", getBatchNo())
            .append("batchOrderNo", getBatchOrderNo())
            .append("locationFrom", getLocationFrom())
            .append("areaFrom", getAreaFrom())
            .append("locationTo", getLocationTo())
            .append("areaTo", getAreaTo())
            .append("priority", getPriority())
            .append("state", getState())
            .append("remark", getRemark())
            .append("createUser", getCreateUser())
            .append("createDate", getCreateDate())
            .append("updateUser", getUpdateUser())
            .append("updateDate", getUpdateDate())
            .append("taskType", getTaskType())
            .append("shopId", getShopId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("parentTask", getParentTask())
            .append("extParam", getExtParam())
            .append("ext1", getExt1())
            .append("ext2", getExt2())
            .append("ext3", getExt3())
            .append("ext4", getExt4())
            .append("ext5", getExt5())
            .append("ext6", getExt6())
            .append("ext7", getExt7())
            .append("ext8", getExt8())
            .append("agvNo", getAgvNo())
            .append("beforeTask", getBeforeTask())
            .toString();
    }
}
