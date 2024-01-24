package com.abm.mainet.common.domain;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author kiran.kavali
 * @since 23 Jan 2015
 */
@Entity
@Table(name = "TB_GROUP_MAST")
public class GroupMaster extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GM_ID", precision = 0, scale = 0, nullable = false)
    private long gmId;

    @Column(name = "GR_CODE", length = 20, nullable = true)
    private String grCode;

    @Column(name = "GR_NAME", length = 500, nullable = true)
    private String grName;

    @Column(name = "GR_NAME_REG", length = 500, nullable = true)
    private String grNameReg;

    @Column(name = "GR_DESC_ENG", length = 500, nullable = true)
    private String grDescEng;

    @Column(name = "GR_DESC_REG", length = 1000, nullable = true)
    private String grDescReg;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "ORG_SPECIFIC", length = 1, nullable = true)
    private String orgSpecific;

    @Column(name = "GR_STATUS", length = 1, nullable = true)
    private String grStatus;

    @Column(name = "LANG_ID", precision = 0, scale = 0, nullable = true)
    private int langId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "ENTRY_DATE", nullable = true)
    private Date entryDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Set<RoleEntitlement> entitlements = new LinkedHashSet<>();

    public Set<RoleEntitlement> getEntitlements() {
        return entitlements;
    }

    public void setEntitlements(final Set<RoleEntitlement> entitlements) {
        this.entitlements = entitlements;
    }

    public long getGmId() {
        return gmId;
    }

    public void setGmId(final long gmId) {
        this.gmId = gmId;
    }

    public String getGrCode() {
        return grCode;
    }

    public void setGrCode(final String grCode) {
        this.grCode = grCode;
    }

    public String getGrName() {
        return grName;
    }

    public void setGrName(final String grName) {
        this.grName = grName;
    }

    public String getGrNameReg() {
        return grNameReg;
    }

    public void setGrNameReg(final String grNameReg) {
        this.grNameReg = grNameReg;
    }

    public String getGrDescEng() {
        return grDescEng;
    }

    public void setGrDescEng(final String grDescEng) {
        this.grDescEng = grDescEng;
    }

    public String getGrDescReg() {
        return grDescReg;
    }

    public void setGrDescReg(final String grDescReg) {
        this.grDescReg = grDescReg;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public String getOrgSpecific() {
        return orgSpecific;
    }

    public void setOrgSpecific(final String orgSpecific) {
        this.orgSpecific = orgSpecific;
    }

    public String getGrStatus() {
        return grStatus;
    }

    public void setGrStatus(final String grStatus) {
        this.grStatus = grStatus;
    }

    @Override
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(final Date entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {
        return getGmId();
    }

    @Override
    public Date getLmodDate() {
        return null;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {

    }

    @Override
    public String getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    @Override
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_GROUP_MAST", "GM_ID" };
    }
}