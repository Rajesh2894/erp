package com.abm.mainet.common.workflow.domain;

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
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_ROLE_DECISION_MST")
public class RoleDecisionMstEntity {
	

	   
    private static final long serialVersionUID = 2449967245624853410L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ROLE_DECISION_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long roleDecisionId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false, updatable = false)
    // comments : Department Id
    private Department deptId;
    
    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    // comments : Service_ID to identify for which Service this Event is mapped
    private Long smServiceId;
    
    @Column(name = "GM_ID",precision = 12, scale = 0, nullable = false)
    // comments : To identify for which role decision is mapped
    private Long gmId;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "CPD_ID", referencedColumnName = "CPD_ID", nullable = false)
    private TbComparamDetEntity cpdId;
    
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee createdBy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    // comments : Service Event Updated By which Employee
    private Employee updatedBy;
    
    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Service Event Updated Date
    private Date updatedDate;

    @Column(name = "IS_ACTIVE", length = 2, nullable = false)
    // comments : default value-N ,flag to identify whether Service Event is deleted or not , Y-deleted, N-not deleted
    private String isActive;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    // comments : Organization ID
    private Organisation orgId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Service Event Created Date
    private Date createdDate;

	public long getRoleDecisionId() {
		return roleDecisionId;
	}



	public void setRoleDecisionId(long roleDecisionId) {
		this.roleDecisionId = roleDecisionId;
	}



	public Department getDeptId() {
		return deptId;
	}



	public void setDeptId(Department deptId) {
		this.deptId = deptId;
	}



	public Long getSmServiceId() {
		return smServiceId;
	}



	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}



	public Long getGmId() {
		return gmId;
	}



	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}



	public TbComparamDetEntity getCpdId() {
		return cpdId;
	}



	public void setCpdId(TbComparamDetEntity cpdId) {
		this.cpdId = cpdId;
	}



	public Employee getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Employee createdBy) {
		this.createdBy = createdBy;
	}



	public Employee getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Employee updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public Organisation getOrgId() {
		return orgId;
	}



	public void setOrgId(Organisation orgId) {
		this.orgId = orgId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	

    public String[] getPkValues() {
        return new String[] { "COM", "TB_ROLE_DECISION_MST", "ROLE_DECISION_ID" };
    }



	public String getIsActive() {
		return isActive;
	}



	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


}
