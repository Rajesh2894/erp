package com.abm.mainet.rnl.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class TenantMasterGrid implements Serializable {

    private static final long serialVersionUID = -1512268635405775138L;
    private Long id;
    private String code;
    private String typeName;
    private Integer type;
    private String fName;
    private String lName;
    private String mobileNo;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(final Integer type) {
        this.type = type;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(final String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(final String lName) {
        this.lName = lName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

}
