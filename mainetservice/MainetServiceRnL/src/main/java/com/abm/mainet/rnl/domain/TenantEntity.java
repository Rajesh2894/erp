package com.abm.mainet.rnl.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 * Tenant Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_TENANT_MAS")
public class TenantEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TNT_ID", nullable = false)
    private Long tntId;

    /**
     * tenant no. for Tenant No. ( Auto generated)('TEN'+'ORGID'+0000+Sequence number)
     */
    @Column(name = "TNT_CODE", nullable = false)
    private String code;

    @Column(name = "ORGID")
    private Long orgId;

    /**
     * Getting type value from Prefix Non hierarchical -->APT
     */
    @Column(name = "TNT_TYPE", nullable = false)
    private Long type;

    /**
     * Getting type value from Prefix Non hierarchical -->TTL
     */
    @Column(name = "CPD_ID_TTL", nullable = false)
    private Long title;

    @Column(name = "TNT_FNAME")
    private String fName;

    @Column(name = "TNT_MNAME")
    private String mName;

    @Column(name = "TNT_LNAME")
    private String lName;

    @Column(name = "TNT_ADDRESS")
    private String address1;

    @Column(name = "TNT_ADDRESS1")
    private String address2;

    @Column(name = "TNT_PINCODE")
    private Integer pinCode;

    @Column(name = "TNT_MOBILE_NO")
    private String mobileNumber;

    @Column(name = "TNT_EMAIL_ID")
    private String emailId;

    @Column(name = "TNT_PHONE_NO")
    private String phoneNumber;

    @Column(name = "TNT_FAX_NO")
    private String faxNumber;

    @Column(name = "TNT_PAN_NO")
    private String panNumber;

    @Column(name = "TNT_AADHAR_NO")
    private Integer aadharNumber;

    @Column(name = "USER_ID")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    /**
     * flag to identify whether the record is deleted or not. 'y' for deleted (inactive) and 'n' for not deleted (active) record.
     */
    @Column(name = "TNT_ACTIVE")
    private Character isActive;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "LANGID")
    private long langId;

    /**
     * flag to identify whether the record is O->original,S->Sub Tenat,d->uploaded (active) record.
     */
    @Column(name = "TNT_FLG")
    private Character tntFlag;

    @Column(name = "TNT_ORG_NAME")
    private String tntOrgName;

    @JsonIgnore
    @Where(clause = "TNT_ADD_ACTIVE = 'Y'")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tenantEntity", cascade = CascadeType.ALL)
    private List<TenantOwnerEntity> tenantOwnerList = new ArrayList<>();

    public Long getTntId() {
        return tntId;
    }

    public void setTntId(final Long tntId) {
        this.tntId = tntId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getType() {
        return type;
    }

    public void setType(final Long type) {
        this.type = type;
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

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    /*
     * public Integer getPinCode() { return pinCode; } public void setPinCode(Integer pinCode) { this.pinCode = pinCode; }
     */

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(final String faxNumber) {
        this.faxNumber = faxNumber;
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(final long langId) {
        this.langId = langId;
    }

    public Character getTntFlag() {
        return tntFlag;
    }

    public void setTntFlag(final Character tntFlag) {
        this.tntFlag = tntFlag;
    }

    public Integer getPinCode() {
        return pinCode;
    }

    public void setPinCode(final Integer pinCode) {
        this.pinCode = pinCode;
    }

    public String getTntOrgName() {
        return tntOrgName;
    }

    public void setTntOrgName(final String tntOrgName) {
        this.tntOrgName = tntOrgName;
    }

    public List<TenantOwnerEntity> getTenantOwnerList() {
        return tenantOwnerList;
    }

    public void setTenantOwnerList(
            final List<TenantOwnerEntity> tenantOwnerList) {
        this.tenantOwnerList = tenantOwnerList;
    }

    public String[] getPkValues() {
        return new String[] { "RL", "TB_RL_TENANT_MAS", "TNT_ID" };
    }
}
