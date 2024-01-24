package com.abm.mainet.care.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class DepartmentComplaintDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long deptCompId;
    private Long orgId;
    private String status;
    private DepartmentDTO department;
    private List<DepartmentComplaintTypeDTO> complaintTypes = new ArrayList<>();

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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public List<DepartmentComplaintTypeDTO> getComplaintTypes() {
        return complaintTypes;
    }

    public void setComplaintTypes(final List<DepartmentComplaintTypeDTO> complaintTypes) {
        this.complaintTypes = complaintTypes;
    }

    public DepartmentComplaintDTO addComplaintType(final DepartmentComplaintTypeDTO dct) {
        complaintTypes.add(dct);
        return this;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(final DepartmentDTO department) {
        this.department = department;
    }

}
