/**
 * 
 */
package com.abm.mainet.common.workflow.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;

/**
 * @author hiren.poriya
 *
 */
@Entity
@Table(name = "TB_WORKFLOW_DET")
public class WorkflowDet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WFD_ID")
    private Long wfdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WF_ID", nullable = false)
    private WorkflowMas wfId;

    @OneToOne
    @JoinColumn(name = "SERVICE_EVENT_ID", referencedColumnName = "SERVICE_EVENT_ID")
    private ServicesEventEntity servicesEventEntity;

    @ManyToOne
    @JoinColumn(name = "WFD_ORGID", referencedColumnName = "ORGID")
    private Organisation eventOrganisation;

    @ManyToOne
    @JoinColumn(name = "DP_DEPTID", referencedColumnName = "DP_DEPTID")
    private Department eventDepartment;

    @Column(name = "WFD_RE_TYPE")
    private String roleType;

    @Column(name = "WFD_SLA")
    private String sla;

    @Column(name = "WFD_UNIT")
    private Long unit;

    @Column(name = "WFD_STATUS")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ORGID", referencedColumnName = "ORGID")
    private Organisation currentOrganisation;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "WFD_APPR_COUNT")
    private int apprCount;

    @Column(name = "WFD_EMPROLE")
    private String roleOrEmpIds;

    @Column(name = "SLA_CAL")
    private Long slaCal;

    public Long getWfdId() {
        return wfdId;
    }

    public void setWfdId(Long wfdId) {
        this.wfdId = wfdId;
    }

    public ServicesEventEntity getServicesEventEntity() {
        return servicesEventEntity;
    }

    public void setServicesEventEntity(ServicesEventEntity servicesEventEntity) {
        this.servicesEventEntity = servicesEventEntity;
    }

    public Organisation getEventOrganisation() {
        return eventOrganisation;
    }

    public void setEventOrganisation(Organisation eventOrganisation) {
        this.eventOrganisation = eventOrganisation;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getSla() {
        return sla;
    }

    public long getSlaPrimitiveLongValue() {
        return (sla != null) ? Long.parseLong(sla) : 0;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Organisation getCurrentOrganisation() {
        return currentOrganisation;
    }

    public void setCurrentOrganisation(Organisation currentOrganisation) {
        this.currentOrganisation = currentOrganisation;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public int getApprCount() {
        return apprCount;
    }

    public void setApprCount(int apprCount) {
        this.apprCount = apprCount;
    }

    public Department getEventDepartment() {
        return eventDepartment;
    }

    public void setEventDepartment(Department eventDepartment) {
        this.eventDepartment = eventDepartment;
    }

    public WorkflowMas getWfId() {
        return wfId;
    }

    public void setWfId(WorkflowMas wfId) {
        this.wfId = wfId;
    }

    public String getRoleOrEmpIds() {
        return roleOrEmpIds;
    }

    public void setRoleOrEmpIds(String roleOrEmpIds) {
        this.roleOrEmpIds = roleOrEmpIds;
    }

    public Long getSlaCal() {
        return slaCal;
    }

    public void setSlaCal(Long slaCal) {
        this.slaCal = slaCal;
    }

    public long getSlaCalPrimitiveLongValue() {
        return (slaCal != null) ? slaCal : 0;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_WORKFLOW_DET", "WFD_ID" };
    }
}
