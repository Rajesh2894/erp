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

import com.abm.mainet.common.entitlement.domain.RoleEntitlement;
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
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GM_ID", precision = 0, scale = 0, nullable = false)
    private long gmId;

    @Column(name = "GR_CODE", length = 20, nullable = false)
    private String grCode;

    @Column(name = "GR_DESC_ENG", length = 500, nullable = false)
    private String grDescEng;

    @Column(name = "GR_DESC_REG", length = 1000, nullable = false)
    private String grDescReg;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "GR_STATUS", length = 1, nullable = false)
    private String grStatus;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date entryDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = true, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ROLE_ID")
    private Set<RoleEntitlement> entitlements = new LinkedHashSet<>();
    
    @Column(name = "DP_DEPTID")
    private Long dpDeptId;
    

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

    public String getGrStatus() {
        return grStatus;
    }

    public void setGrStatus(final String grStatus) {
        this.grStatus = grStatus;
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

    public Long getDpDeptId() {
		return dpDeptId;
	}

	public void setDpDeptId(Long dpDeptId) {
		this.dpDeptId = dpDeptId;
	}
	
    @Override
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_GROUP_MAST", "GM_ID" };
    }

	@Override
	public int getLangId() {
		return 0;
	}

	@Override
	public void setLangId(int langId) {		
	}
}