package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ritesh.patil
 *
 */
public class DepartmentComplaintBean implements Serializable {

    private static final long serialVersionUID = -6484547859585857264L;

    private Long deptCompId;
    private Long orgId;
    private Long deptId;
    private String status;
    private Long updatedBy;
    private Date updatedDate;
    private Date createDate;
    private Long createdBy;
    private String hiddeValue;
    private String deptName;

    public String getHiddeValue() {
        return hiddeValue;
    }

    public void setHiddeValue(final String hiddeValue) {
        this.hiddeValue = hiddeValue;
    }

    private List<ComplaintTypeBean> complaintTypesList = new ArrayList<>();

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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
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

    public List<ComplaintTypeBean> getComplaintTypesList() {
        return complaintTypesList;
    }

    public void setComplaintTypesList(
            final List<ComplaintTypeBean> complaintTypesList) {
        this.complaintTypesList = complaintTypesList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

}
