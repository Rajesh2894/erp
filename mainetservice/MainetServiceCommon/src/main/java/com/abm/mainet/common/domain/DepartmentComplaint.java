package com.abm.mainet.common.domain;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 */

@Entity
@Table(name = "TB_DEPT_COMPLAINT_TYPE")
public class DepartmentComplaint {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DEPT_COMP_ID", precision = 4, scale = 0, nullable = false)
    private Long deptCompId;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    private Long orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DEPT_ID")
    private Department department;

    @Column(name = "STATUS", length = 1, nullable = true)
    private String status;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "CREATE_DATE", length = 4, nullable = true)
    private Date createDate;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;
    
	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;

    @JsonIgnore
    @Where(clause = "STATUS = 'Y'")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "departmentComplaint")
    List<ComplaintType> complaintTypes = Collections.emptyList();

    public Long getDeptCompId() {
        return deptCompId;
    }

    public void setDeptCompId(final Long deptCompId) {
        this.deptCompId = deptCompId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(final Department department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public List<ComplaintType> getComplaintTypes() {
        return complaintTypes;
    }

    public void setComplaintTypes(
            final List<ComplaintType> complaintTypes) {
        this.complaintTypes = complaintTypes;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_DEPT_COMPLAINT_TYPE", "DEPT_COMP_ID" };
    }

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

}
