package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_prop_factor")
public class PropertyFactorEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8742125472392385472L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PF_ID")
    private long pmAssfId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PD_ID", nullable = false)
    private PropertyDetEntity tbAsPrimaryDet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PM_PROPID", nullable = false)
    private PropertyMastEntity tbAsPrimaryMast;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "orgId")
    private Long orgId;

    @Column(name = "PF_ACTIVE")
    private String assfActive;

    @Column(name = "PF_FACTOR")
    private Long assfFactor;

    @Column(name = "PF_FACTOR_ID")
    private Long assfFactorId;

    @Column(name = "PF_FACTOR_VALUE_ID")
    private Long assfFactorValueId;

    @Column(name = "PF_END_DATE")
    private Date assoEndDate;

    @Column(name = "PF_START_DATE")
    private Date assoStartDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "PF_factor_date")
    private Date factorDate;
    
    @Column(name = "PF_factor_remark")
    private String factorRemark;

    public PropertyFactorEntity() {
        super();
    }

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_prop_factor", "PF_ID" };

    }

    public PropertyDetEntity getTbAsPrimaryDet() {
        return tbAsPrimaryDet;
    }

    public void setTbAsPrimaryDet(PropertyDetEntity tbAsPrimaryDet) {
        this.tbAsPrimaryDet = tbAsPrimaryDet;
    }

    public PropertyMastEntity getTbAsPrimaryMast() {
        return tbAsPrimaryMast;
    }

    public void setTbAsPrimaryMast(PropertyMastEntity tbAsPrimaryMast) {
        this.tbAsPrimaryMast = tbAsPrimaryMast;
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

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getPmAssfId() {
        return pmAssfId;
    }

    public void setPmAssfId(long pmAssfId) {
        this.pmAssfId = pmAssfId;
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
