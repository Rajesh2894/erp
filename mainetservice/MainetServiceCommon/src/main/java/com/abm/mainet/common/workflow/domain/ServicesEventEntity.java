package com.abm.mainet.common.workflow.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 * @since 23 November 2015
 */
@Entity
@Table(name = "TB_SERVICES_EVENT")
public class ServicesEventEntity implements Serializable {
   
    private static final long serialVersionUID = 2449967245624853410L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SERVICE_EVENT_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long serviceEventId;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    // comments : Service_ID to identify for which Service this Event is mapped
    private Long smServiceId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee createdBy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    // comments : Service Event Updated By which Employee
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Service Event Updated Date
    private Date updatedDate;

    @Column(name = "ISDELETED", length = 2, nullable = false)
    // comments : default value-N ,flag to identify whether Service Event is deleted or not , Y-deleted, N-not deleted
    private String isDeleted;


    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    // comments : Organization ID
    private Organisation orgId;


    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    // comments : Client Machine?s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine?s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Service Event Created Date
    private Date createdDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false, updatable = false)
    // comments : Department Id
    private Department deptId;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "EVENT_ID", referencedColumnName = "SMFID", nullable = false)
    private SystemModuleFunction systemModuleFunction;

    public long getServiceEventId() {
        return serviceEventId;
    }

    public void setServiceEventId(final long serviceEventId) {
        this.serviceEventId = serviceEventId;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public Employee getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Employee createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Organisation getOrgId() {
        return orgId;
    }

    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }


    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the deptId
     */
    public Department getDeptId() {
        return deptId;
    }

    /**
     * @param deptId the deptId to set
     */
    public void setDeptId(final Department deptId) {
        this.deptId = deptId;
    }

    /**
     * @return the systemModuleFunction
     */
    public SystemModuleFunction getSystemModuleFunction() {
        return systemModuleFunction;
    }

    /**
     * @param systemModuleFunction the systemModuleFunction to set
     */
    public void setSystemModuleFunction(final SystemModuleFunction systemModuleFunction) {
        this.systemModuleFunction = systemModuleFunction;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_SERVICES_EVENT", "SERVICE_EVENT_ID" };
    }

}