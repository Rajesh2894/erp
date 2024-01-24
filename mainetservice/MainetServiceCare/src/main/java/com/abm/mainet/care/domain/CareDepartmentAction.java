package com.abm.mainet.care.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "TB_CARE_DEPARTMENT_ACTION")
@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class CareDepartmentAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "CARE_DEP_ACTID")
    private Long id;

    @Column(name = "COD_ID_OPER_LEVEL1")
    private Long codIdOperLevel1;

    @Column(name = "COD_ID_OPER_LEVEL2")
    private Long codIdOperLevel2;

    @Column(name = "COD_ID_OPER_LEVEL3")
    private Long codIdOperLevel3;

    @Column(name = "COD_ID_OPER_LEVEL4")
    private Long codIdOperLevel4;

    @Column(name = "COD_ID_OPER_LEVEL5")
    private Long codIdOperLevel5;

    @Column(name = "DP_DEPTID")
    private Long department;

    @Column(name = "DEPT_COMP_ID")
    private Long departmentComplaint;

    @Column(name = "COMP_SUBTYPE_ID")
    private Long complaintType;

    @Column(name = "FORWARDTO_EMPID")
    private String forwardToEmployee;

    @Transient
    private String forwardToEmployeeType;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "UPDATED_DATE")
    private Date modifiedDate;

    @Column(name = "UPDATED_BY")
    private Long modifiedBy;

    @Column(name = "WORKFLOW_ACT_ID")
    private Long workflowActionId;
    
    @Column(name = "RSN_TO_FORWD_ID")
    private Long reasonToForwardId;

    @ManyToOne
    @JoinColumn(name = "CARE_REQ_ID", referencedColumnName = "CARE_REQ_ID")
    @JsonIgnore
    private CareRequest careRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodIdOperLevel1() {
        return codIdOperLevel1;
    }

    public void setCodIdOperLevel1(Long codIdOperLevel1) {
        this.codIdOperLevel1 = codIdOperLevel1;
    }

    public Long getCodIdOperLevel2() {
        return codIdOperLevel2;
    }

    public void setCodIdOperLevel2(Long codIdOperLevel2) {
        this.codIdOperLevel2 = codIdOperLevel2;
    }

    public Long getCodIdOperLevel3() {
        return codIdOperLevel3;
    }

    public void setCodIdOperLevel3(Long codIdOperLevel3) {
        this.codIdOperLevel3 = codIdOperLevel3;
    }

    public Long getCodIdOperLevel4() {
        return codIdOperLevel4;
    }

    public void setCodIdOperLevel4(Long codIdOperLevel4) {
        this.codIdOperLevel4 = codIdOperLevel4;
    }

    public Long getCodIdOperLevel5() {
        return codIdOperLevel5;
    }

    public void setCodIdOperLevel5(Long codIdOperLevel5) {
        this.codIdOperLevel5 = codIdOperLevel5;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getDepartmentComplaint() {
        return departmentComplaint;
    }

    public void setDepartmentComplaint(Long departmentComplaint) {
        this.departmentComplaint = departmentComplaint;
    }

    public Long getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Long complaintType) {
        this.complaintType = complaintType;
    }

    public String getForwardToEmployee() {
        return forwardToEmployee;
    }

    public void setForwardToEmployee(String forwardToEmployee) {
        this.forwardToEmployee = forwardToEmployee;
    }

    public String getForwardToEmployeeType() {
        return forwardToEmployeeType;
    }

    public void setForwardToEmployeeType(String forwardToEmployeeType) {
        this.forwardToEmployeeType = forwardToEmployeeType;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public CareRequest getCareRequest() {
        return careRequest;
    }

    public void setCareRequest(CareRequest careRequest) {
        this.careRequest = careRequest;
    }

    public Long getWorkflowActionId() {
        return workflowActionId;
    }

    public void setWorkflowActionId(Long workflowActionId) {
        this.workflowActionId = workflowActionId;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_CARE_DEPARTMENT_ACTION", "CARE_DEP_ACTID" };
    }

	public Long getReasonToForwardId() {
		return reasonToForwardId;
	}

	public void setReasonToForwardId(Long reasonToForwardId) {
		this.reasonToForwardId = reasonToForwardId;
	}

}
