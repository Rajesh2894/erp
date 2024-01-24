package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_pro_factor_dtl_hist")
public class ProAssFactlHisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9004201358658473890L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "pro_assf_HIST_ID")
    private long proAssfHisId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "pro_ass_id")
    private Long proAssId;

    @Column(name = "pro_assd_id")
    private Long proAssdId;

    @Column(name = "pro_assf_active")
    private String assfActive;

    @Column(name = "pro_assf_factor")
    private Long assfFactor;

    @Column(name = "pro_assf_factor_id")
    private Long assfFactorId;

    @Column(name = "pro_assf_factor_value_id")
    private Long assfFactorValueId;

    @Column(name = "pro_assf_id")
    private Long proAssfId;

    @Column(name = "PRO_ASSO_END_DATE")
    private Date assoEndDate;

    @Column(name = "PRO_ASSO_START_DATE")
    private Date assoStartDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;
    
    @Column(name = "PRO_assf_factor_date")
    private Date factorDate;
    
    @Column(name = "PRO_assf_factor_remark")
    private String factorRemark;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_factor_dtl_hist", "pro_assf_HIST_ID" };

    }

    public long getProAssfHisId() {
        return proAssfHisId;
    }

    public void setProAssfHisId(long proAssfHisId) {
        this.proAssfHisId = proAssfHisId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getProAssId() {
        return proAssId;
    }

    public void setProAssId(Long proAssId) {
        this.proAssId = proAssId;
    }

    public Long getProAssdId() {
        return proAssdId;
    }

    public void setProAssdId(Long proAssdId) {
        this.proAssdId = proAssdId;
    }

    public String getAssfActive() {
        return assfActive;
    }

    public void setAssfActive(String assfActive) {
        this.assfActive = assfActive;
    }

    public Long getAssfFactor() {
        return assfFactor;
    }

    public void setAssfFactor(Long assfFactor) {
        this.assfFactor = assfFactor;
    }

    public Long getAssfFactorId() {
        return assfFactorId;
    }

    public void setAssfFactorId(Long assfFactorId) {
        this.assfFactorId = assfFactorId;
    }

    public Long getAssfFactorValueId() {
        return assfFactorValueId;
    }

    public void setAssfFactorValueId(Long assfFactorValueId) {
        this.assfFactorValueId = assfFactorValueId;
    }

    public Long getProAssfId() {
        return proAssfId;
    }

    public void setProAssfId(Long proAssfId) {
        this.proAssfId = proAssfId;
    }

    public Date getAssoEndDate() {
        return assoEndDate;
    }

    public void setAssoEndDate(Date assoEndDate) {
        this.assoEndDate = assoEndDate;
    }

    public Date getAssoStartDate() {
        return assoStartDate;
    }

    public void setAssoStartDate(Date assoStartDate) {
        this.assoStartDate = assoStartDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

	public Date getFactorDate() {
		return factorDate;
	}

	public void setFactorDate(Date factorDate) {
		this.factorDate = factorDate;
	}

	public String getFactorRemark() {
		return factorRemark;
	}

	public void setFactorRemark(String factorRemark) {
		this.factorRemark = factorRemark;
	}

    
}
