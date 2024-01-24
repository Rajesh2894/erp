package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PortalDepartmentDTO implements Serializable {

    private static final long serialVersionUID = -2059692481646888764L;

    private long departmentid;
    private String departmentCode;
    private String departmentName;

    List<PortalQuickServiceDTO> childDTO = new ArrayList<>();

    public List<PortalQuickServiceDTO> getChildDTO() {
        return childDTO;
    }

    public void addChildDTO(PortalQuickServiceDTO subDTO) {
        if (null != subDTO) {
            childDTO.add(subDTO);
            /*
             * if(subDTO.getServiceCount() != null && !subDTO.getServiceCount().isEmpty()) { deptCount =
             * String.valueOf(Long.parseLong(deptCount) + Long.parseLong(subDTO.getServiceCount())); }
             */
        }
    }

    public long getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(long departmentid) {
        this.departmentid = departmentid;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

}
