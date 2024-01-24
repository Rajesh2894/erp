package com.abm.mainet.common.entitlement.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 */
@Entity
@Table(name = "ROLE_ENTITLEMENT")
public class RoleEntitlement implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ROLE_ET_ID", precision = 0, scale = 0, nullable = false)
    private Long roleEtId;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORG_ID", nullable = false, updatable = false)
    private Organisation organisation;

    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID")
    private GroupMaster groupMaster;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SMFID")
    private SystemModuleFunction entitle;

    @Column(name = "ET_PARENT_ID")
    private Long parentId;

    @Column(name = "BU_ADD")
    private String add;
    @Column(name = "BU_DELETE")
    private String delete;
    @Column(name = "BU_EDIT")
    private String update;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false)
    private Department department;

    @Transient
    private Boolean lastNode;
    
    @Column(name = "CREATED_DATE")
    private Date createdDate;

	@Column(name = "CREATED_BY")
    private Long createdBy;
    
    

    public Long getRoleEtId() {
        return roleEtId;
    }

    public void setRoleEtId(final Long roleEtId) {
        this.roleEtId = roleEtId;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public GroupMaster getGroupMaster() {
        return groupMaster;
    }

    public void setGroupMaster(final GroupMaster groupMaster) {
        this.groupMaster = groupMaster;
    }

    public SystemModuleFunction getEntitle() {
        return entitle;
    }

    public void setEntitle(final SystemModuleFunction entitle) {
        this.entitle = entitle;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(final Organisation organisation) {
        this.organisation = organisation;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(final String isActive) {
        this.isActive = isActive;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(final Long parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(final Department department) {
        this.department = department;
    }

    public String[] getPkValues() {
        return new String[] { "AUT", "ROLE_ENTITLEMENT",
                "ROLE_ET_ID" };
    }

    public Boolean getLastNode() {
        return lastNode;
    }

    public void setLastNode(final Boolean lastNode) {
        this.lastNode = lastNode;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(final String add) {
        this.add = add;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(final String delete) {
        this.delete = delete;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(final String update) {
        this.update = update;
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
}
