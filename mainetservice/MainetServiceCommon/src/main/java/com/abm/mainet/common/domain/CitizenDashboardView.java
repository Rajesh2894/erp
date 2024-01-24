package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author hiren.poriya
 * @Since 22-Jan-2018
 */
@Entity
@Table(name = "VW_CITIZEN_DASHBOARD")
public class CitizenDashboardView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "APM_APPLICATION_ID")
    private String apmApplicationId;

    @Column(name = "SM_SERVICE_NAME")
    private String smServiceName;

    @Column(name = "SM_SERVICE_NAME_MAR")
    private String smServiceNameMar;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "SM_SHORTDESC")
    private String smShortdesc;

    @Column(name = "LAST_DECISION")
    private String lastDecision;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APM_APPLICATION_DATE")
    private Date apmApplicationDate;

    @Column(name = "DP_DEPTDESC", length = 800)
    private String dpDeptdesc;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "APA_MOBILNO")
    private String apmMobileNo;

    @Column(name = "TASKID")
    private Long taskId;

    @Column(name = "TASK_SLA_DURATION")
    private Long taskSlaDurationInMS;

    @Column(name = "TASK_STATUS")
    private String taskStatus;

    @Column(name = "WFTASK_ACTORID")
    private String actorId;

    @Column(name = "EVENT_ID")
    private Long serviceEventId;

    @Column(name = "SMFNAME")
    private String serviceEventName;

    @Column(name = "SMFNAME_MAR")
    private String serviceEventNameReg;

    @Column(name = "SMFACTION")
    private String serviceEventUrl;

    @Column(name = "SERVICETYPE")
    private String ServiceType;

    @Column(name = "DP_DEPTCODE")
    private String dpDeptcode;

    @Column(name = "SM_SERDUR")
    private Long smServiceDuration;
    
    @Column(name = "DP_NAME_MAR", length = 800)
    private String dpNameMar;
    /*Earlier getting both referenceId & applicationId from same column(apm_application_id) 
     * Now we have added one more column reference_id and now we are getting both values in respective columns.*/
    @Column(name = "Reference_Id")
    private String referenceId;

	public String getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(String apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getSmServiceName() {
        return smServiceName;
    }

    public void setSmServiceName(String smServiceName) {
        this.smServiceName = smServiceName;
    }

    public String getSmServiceNameMar() {
        return smServiceNameMar;
    }

    public void setSmServiceNameMar(String smServiceNameMar) {
        this.smServiceNameMar = smServiceNameMar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSmShortdesc() {
        return smShortdesc;
    }

    public void setSmShortdesc(String smShortdesc) {
        this.smShortdesc = smShortdesc;
    }

    public String getLastDecision() {
        return lastDecision;
    }

    public void setLastDecision(String lastDecision) {
        this.lastDecision = lastDecision;
    }

    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    public void setApmApplicationDate(Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    public String getDpDeptdesc() {
        return dpDeptdesc;
    }

    public void setDpDeptdesc(String dpDeptdesc) {
        this.dpDeptdesc = dpDeptdesc;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getApmMobileNo() {
        return apmMobileNo;
    }

    public void setApmMobileNo(String apmMobileNo) {
        this.apmMobileNo = apmMobileNo;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getTaskSlaDurationInMS() {
        return taskSlaDurationInMS;
    }

    public void setTaskSlaDurationInMS(Long taskSlaDurationInMS) {
        this.taskSlaDurationInMS = taskSlaDurationInMS;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getActorId() {
        return actorId;
    }

    public void setActorId(String actorId) {
        this.actorId = actorId;
    }

    public Long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(Long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public String getServiceEventName() {
        return serviceEventName;
    }

    public void setServiceEventName(String serviceEventName) {
        this.serviceEventName = serviceEventName;
    }

    public String getServiceEventNameReg() {
        return serviceEventNameReg;
    }

    public void setServiceEventNameReg(String serviceEventNameReg) {
        this.serviceEventNameReg = serviceEventNameReg;
    }

    public String getServiceEventUrl() {
        return serviceEventUrl;
    }

    public void setServiceEventUrl(String serviceEventUrl) {
        this.serviceEventUrl = serviceEventUrl;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getDpDeptcode() {
        return dpDeptcode;
    }

    public void setDpDeptcode(String dpDeptcode) {
        this.dpDeptcode = dpDeptcode;
    }

    public Long getSmServiceDuration() {
        return smServiceDuration;
    }

    public void setSmServiceDuration(Long smServiceDuration) {
        this.smServiceDuration = smServiceDuration;
    }
    public String getDpNameMar() {
		return dpNameMar;
	}

	public void setDpNameMar(String dpNameMar) {
		this.dpNameMar = dpNameMar;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
}
