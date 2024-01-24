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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deptCompId == null) ? 0 : deptCompId.hashCode());
        result = prime * result + ((orgId == null) ? 0 : orgId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DepartmentComplaintDTO other = (DepartmentComplaintDTO) obj;
        if (deptCompId == null) {
            if (other.deptCompId != null)
                return false;
        } else if (!deptCompId.equals(other.deptCompId))
            return false;
        if (orgId == null) {
            if (other.orgId != null)
                return false;
        } else if (!orgId.equals(other.orgId))
            return false;
        return true;
    }

}
