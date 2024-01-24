package com.abm.mainet.rnl.dto;

import java.io.Serializable;

/**
 * @author ritesh.patil
 *
 */
public class TenantOwnerMaster implements Serializable {

    private static final long serialVersionUID = -829910523542332054L;
    private Long tntOwnerId;
    private Long tenantId;
    private Long title;
    private String fName;
    private String mName;
    private String lName;
    private String mobileNumber;
    private String emailId;
    private String panNumber;
    private Integer aadharNumber;
    private Long orgId;
    private Long updatedBy;
    private Long createdBy;
    private int langId;

    public Long getTntOwnerId() {
        return tntOwnerId;
    }

    public void setTntOwnerId(final Long tntOwnerId) {
        this.tntOwnerId = tntOwnerId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(final Long title) {
        this.title = title;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(final String fName) {
        this.fName = fName;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(final String mName) {
        this.mName = mName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(final String lName) {
        this.lName = lName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(final String panNumber) {
        this.panNumber = panNumber;
    }

    public Integer getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(final Integer aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }
}
