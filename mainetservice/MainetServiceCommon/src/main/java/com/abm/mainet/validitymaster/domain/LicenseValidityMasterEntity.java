package com.abm.mainet.validitymaster.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 * @since 17 september 2019
 */
@Entity
@Table(name = "TB_ADH_LICENSE_VALIDATITY_MASTER")
public class LicenseValidityMasterEntity implements Serializable {

    private static final long serialVersionUID = 5790397953894983771L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LIC_ID")
    private Long licId;

    @Column(name = "DEPT_ID")
    private Long deptId;

    @Column(name = "SERVICE_ID")
    private Long serviceId;

    @Column(name = "LIC_TYPE")
    private Long licType;

    @Column(name = "LIC_DEPENDS_ON")
    private Long licDependsOn;

    @Column(name = "LIC_TENURE")
    private String licTenure;

    @Column(name = "UNIT")
    private Long unit;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;
    
    @Column(name = "TRI_COD1")
    private Long triCod1;
    
    @Column(name = "TRI_COD2")
    private Long triCod2;

    public Long getLicId() {
        return licId;
    }

    public void setLicId(Long licId) {
        this.licId = licId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getLicType() {
        return licType;
    }

    public void setLicType(Long licType) {
        this.licType = licType;
    }

    public Long getLicDependsOn() {
        return licDependsOn;
    }

    public void setLicDependsOn(Long licDependsOn) {
        this.licDependsOn = licDependsOn;
    }

    public String getLicTenure() {
        return licTenure;
    }

    public void setLicTenure(String licTenure) {
        this.licTenure = licTenure;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getTriCod2() {
		return triCod2;
	}

	public void setTriCod2(Long triCod2) {
		this.triCod2 = triCod2;
	}

	public Long getTriCod1() {
		return triCod1;
	}

	public void setTriCod1(Long triCod1) {
		this.triCod1 = triCod1;
	}

	public String[] getPkValues() {
        return new String[] { "COM", "TB_ADH_LICENSE_VALIDATITY_MASTER", "LIC_ID" };
    }
}
