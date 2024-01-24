package com.abm.mainet.property.dto;

import java.io.Serializable;

public class AmalgamationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parentPropNo;

    private String amalgmatedPropNo;

    private String parentOldPropNo;

    private String amalgmatedOldPropNo;

    public String getParentPropNo() {
        return parentPropNo;
    }

    public void setParentPropNo(String parentPropNo) {
        this.parentPropNo = parentPropNo;
    }

    public String getAmalgmatedPropNo() {
        return amalgmatedPropNo;
    }

    public void setAmalgmatedPropNo(String amalgmatedPropNo) {
        this.amalgmatedPropNo = amalgmatedPropNo;
    }

    public String getParentOldPropNo() {
        return parentOldPropNo;
    }

    public void setParentOldPropNo(String parentOldPropNo) {
        this.parentOldPropNo = parentOldPropNo;
    }

    public String getAmalgmatedOldPropNo() {
        return amalgmatedOldPropNo;
    }

    public void setAmalgmatedOldPropNo(String amalgmatedOldPropNo) {
        this.amalgmatedOldPropNo = amalgmatedOldPropNo;
    }

}
