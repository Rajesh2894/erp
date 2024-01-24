package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_bpms_app_purpose")
public class LicenseApplicationPurposeTypeDetEntity implements Serializable {

	private static final long serialVersionUID = -4172501212383484097L;	
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "purposeId", nullable = false)
    private Long purposeId;
	
	@ManyToOne
	@JoinColumn(name = "TCP_LIC_MSTR_ID")
    private LicenseApplicationMaster licenseApplicationMaster;

    @Column(name = "Purpose")
    private Long applicationPurpose1;

    @Column(name = "Subpurpose1")
    private Long applicationPurpose2;

    @Column(name = "Subpurpose2")
    private Long applicationPurpose3;

    @Column(name = "FAR")
    private Long far;

    @Column(name = "Area")
    private Double area;

    @Column(name = "orgid")
    private Long orgId;

    @Column(name = "createdBy")
    private Long createdBy;

    @Column(name = "createdDate")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "updatedBy")
    private Long updatedBy;

    @Column(name = "updatedDate")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    @Column(name = "lgIpMac")
    private String lgIpMac;

    @Column(name = "gIpMacUpd")
    private String gIpMacUpd;

	public Long getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(Long purposeId) {
		this.purposeId = purposeId;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public Long getApplicationPurpose1() {
		return applicationPurpose1;
	}

	public void setApplicationPurpose1(Long applicationPurpose1) {
		this.applicationPurpose1 = applicationPurpose1;
	}

	public Long getApplicationPurpose2() {
		return applicationPurpose2;
	}

	public void setApplicationPurpose2(Long applicationPurpose2) {
		this.applicationPurpose2 = applicationPurpose2;
	}

	public Long getApplicationPurpose3() {
		return applicationPurpose3;
	}

	public void setApplicationPurpose3(Long applicationPurpose3) {
		this.applicationPurpose3 = applicationPurpose3;
	}

	public Long getFar() {
		return far;
	}

	public void setFar(Long far) {
		this.far = far;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
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

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getgIpMacUpd() {
		return gIpMacUpd;
	}

	public void setgIpMacUpd(String gIpMacUpd) {
		this.gIpMacUpd = gIpMacUpd;
	}
	
	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_app_purpose", "purposeId" };
	}
    
    
}
