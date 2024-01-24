package com.abm.mainet.rnl.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 * Tenant Owners For group entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_TENANT_ADD_OWNER")
public class TenantOwnerEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TNT_ADD_ID", nullable = false)
    private Long tntOwnerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TNT_ID")
    private TenantEntity tenantEntity;

    @Column(name = "CPD_ID_TTL", nullable = false)
    private Long title;

    @Column(name = "TNT_ADD_FNAME")
    private String fName;

    @Column(name = "TNT_ADD_MNAME")
    private String mName;

    @Column(name = "TNT_ADD_LNAME")
    private String lName;

    @Column(name = "TNT_ADD_MOBILE_NO")
    private String mobileNumber;

    @Column(name = "TNT_ADD_EMAIL_ID")
    private String emailId;

    @Column(name = "TNT_ADD_PAN_NO")
    private String panNumber;

    @Column(name = "TNT_ADD_AADHAR_NO")
    private Integer aadharNumber;

    @Column(name = "ORGID")
    private Long orgId;

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
    @Column(name = "TNT_ADD_ACTIVE")
    private Character isActive;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "LANGID")
    private long langId;

    public Long getTntOwnerId() {
        return tntOwnerId;
    }

    public void setTntOwnerId(final Long tntOwnerId) {
        this.tntOwnerId = tntOwnerId;
    }

    public TenantEntity getTenantEntity() {
        return tenantEntity;
    }

    public void setTenantEntity(final TenantEntity tenantEntity) {
        this.tenantEntity = tenantEntity;
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

    public String[] getPkValues() {
        return new String[] { "RL", "TB_RL_TENANT_ADD_OWNER", "TNT_ADD_ID" };
    }
}
