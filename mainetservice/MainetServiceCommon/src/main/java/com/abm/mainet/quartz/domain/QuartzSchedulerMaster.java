package com.abm.mainet.quartz.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vivek.Kumar
 * @since 04 May 2015
 */
@Entity
@Table(name = "TB_COM_JOB_MAS")
public class QuartzSchedulerMaster extends BaseEntity {

    private static final long serialVersionUID = -1444594593063448723L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CJ_ID", precision = 12, scale = 0, nullable = false)
    private long jobId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = false)
    private Long dpDeptid;

    @Column(name = "CPD_ID_BJO", precision = 12, scale = 0, nullable = false)
    private Long cpdIdBjo;

    @Column(name = "CPD_ID_BFR", precision = 12, scale = 0, nullable = false)
    private Long cpdIdBfr;

    @Column(name = "CJ_REPEAT", length = 1, nullable = false)
    private String cjRepeat;

    @Column(name = "CJ_DESC", length = 500, nullable = false)
    private String jobProcName;

    @Column(name = "CJ_CLASSNAME", length = 1000, nullable = false)
    private String jobClassName;

    @Column(name = "CJ_FUNNAME", length = 100, nullable = false)
    private String jobFuncName;

    @Column(name = "CJ_DATE", nullable = true)
    private Date cjDate;

    @Column(name = "CJ_INTERVAL", length = 500, nullable = true)
    private String cronExpression;

    @Column(name = "STATUS", length = 1, nullable = false)
    private String status;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Transient
    private String startAtTime;

    @Transient
    private String startOnDate_monthly;

    @Transient
    private String startOnDate_yearly;

    @Transient
    private String startAtTime_Daily;

    @Transient
    private String hiddenJobFrequencyType;

    @Transient
    private String hiddenJobCode;

    @Transient
    private String departmentForQuartz;

    @Transient
    private String departmentName;		// used to show in grid

    
    @Transient
    @JsonProperty
    private String jobName;	// used to show in grid

    
    @Transient
    @JsonProperty
    private String jobFrequency;	// used to show in grid

    @Transient
    @JsonProperty
    private String jobStatus;	// used to show in grid

    @Transient
    private String invokeOnEditOrSubmit;  // used to Schedule job on fresh entry or edit through master form
    
    @Transient
    private String repeatMinTime; // used to capture repeated minute for execution of scheduler
    
    @Transient
    private String repeatHour; // used to capture repeated Hour for execution of scheduler

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getCpdIdBjo() {
        return cpdIdBjo;
    }

    public void setCpdIdBjo(final Long cpdIdBjo) {
        this.cpdIdBjo = cpdIdBjo;
    }

    public Long getCpdIdBfr() {
        return cpdIdBfr;
    }

    public void setCpdIdBfr(final Long cpdIdBfr) {
        this.cpdIdBfr = cpdIdBfr;
    }

    public String getCjRepeat() {
        return cjRepeat;
    }

    public void setCjRepeat(final String cjRepeat) {
        this.cjRepeat = cjRepeat;
    }

    public Date getCjDate() {
        return cjDate;
    }

    public void setCjDate(final Date cjDate) {
        this.cjDate = cjDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }


    public String getJobProcName() {
        return jobProcName;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public String getJobFuncName() {
        return jobFuncName;
    }

    public void setJobFuncName(final String jobFuncName) {
        this.jobFuncName = jobFuncName;
    }

    public void setJobClassName(final String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public void setJobProcName(final String jobProcName) {
        this.jobProcName = jobProcName;
    }


    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public String getStartAtTime() {
        return startAtTime;
    }

    public void setStartAtTime(final String startAtTime) {
        this.startAtTime = startAtTime;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public long getId() {

        return getJobId();
    }

    @Override
    public String getLgIpMac() {
        return null;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {

    }

    @Override
    public String getLgIpMacUpd() {
        return null;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {

    }

    @Override
    public String getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(final long jobId) {
        this.jobId = jobId;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(final String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getStartAtTime_Daily() {
        return startAtTime_Daily;
    }

    public void setStartAtTime_Daily(final String startAtTime_Daily) {
        this.startAtTime_Daily = startAtTime_Daily;
    }

    public String getHiddenJobFrequencyType() {
        return hiddenJobFrequencyType;
    }

    public void setHiddenJobFrequencyType(final String hiddenJobFrequencyType) {
        this.hiddenJobFrequencyType = hiddenJobFrequencyType;
    }

    public String getHiddenJobCode() {
        return hiddenJobCode;
    }

    public void setHiddenJobCode(final String hiddenJobCode) {
        this.hiddenJobCode = hiddenJobCode;
    }

    public String getStartOnDate_monthly() {
        return startOnDate_monthly;
    }

    public void setStartOnDate_monthly(final String startOnDate_monthly) {
        this.startOnDate_monthly = startOnDate_monthly;
    }

    public String getStartOnDate_yearly() {
        return startOnDate_yearly;
    }

    public void setStartOnDate_yearly(final String startOnDate_yearly) {
        this.startOnDate_yearly = startOnDate_yearly;
    }

    public String getDepartmentForQuartz() {
        return departmentForQuartz;
    }

    public void setDepartmentForQuartz(final String departmentForQuartz) {
        this.departmentForQuartz = departmentForQuartz;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(final String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(final String jobName) {
        this.jobName = jobName;
    }

    public String getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequency(final String jobFrequency) {
        this.jobFrequency = jobFrequency;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(final String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getInvokeOnEditOrSubmit() {
        return invokeOnEditOrSubmit;
    }

    public void setInvokeOnEditOrSubmit(final String invokeOnEditOrSubmit) {
        this.invokeOnEditOrSubmit = invokeOnEditOrSubmit;
    }
    

    public String getRepeatMinTime() {
		return repeatMinTime;
	}

	public void setRepeatMinTime(String repeatMinTime) {
		this.repeatMinTime = repeatMinTime;
	}

	public String getRepeatHour() {
		return repeatHour;
	}

	public void setRepeatHour(String repeatHour) {
		this.repeatHour = repeatHour;
	}

	public String[] getPkValues() {

           return new String[] { "AUT", "TB_COM_JOB_MAS", "CJ_ID" };
       }

	@Override
	public int getLangId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLangId(int langId) {
		// TODO Auto-generated method stub
		
	}

}