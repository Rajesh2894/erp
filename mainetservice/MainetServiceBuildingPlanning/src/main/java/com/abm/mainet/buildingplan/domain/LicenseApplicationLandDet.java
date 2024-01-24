package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_APP_LAND_DET")
public class LicenseApplicationLandDet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "LAND_DET_ID", nullable = false)
	private Long landDetId;

	@ManyToOne
	@JoinColumn(name = "TCP_LIC_MSTR_ID")
	private LicenseApplicationMaster licenseApplicationMaster;

	@Column(name = "application_no", nullable = true)
	private String applicationNo;

	@Column(name = "DGPS_point", nullable = true)
	private String DGPS_point;

	@Column(name = "BCL_tot_appl_area", precision = 20, scale = 2, nullable = true)
	private BigDecimal BCLTotApplArea;

	@Column(name = "BCL_pur_name", nullable = true)
	private String BCLPurName;

	@Column(name = "BCL_area", precision = 15, scale = 6, nullable = true)
	private BigDecimal BCLArea;

	@Column(name = "BCL_FAR", precision = 5, scale = 2, nullable = true)
	private BigDecimal BCLFAR;

	@Column(name = "LPD_LP_PDF", nullable = true)
	private String LPDLPPDF;

	@Column(name = "LPD_LP_DXF", nullable = true)
	private String LPDLPDXF;

	@Column(name = "LPD_Undertaking", nullable = true)
	private String LPDUndertaking;

	@Column(name = "LPD_SEDP", nullable = true)
	private String LPDSEDP;

	@Column(name = "LPD_SESP", nullable = true)
	private String LPDSESP;

	@Column(name = "LPD_Explanatory_Note")
	private String LPDExplanatoryNote;

	@Column(name = "LPD_Guide_Map")
	private String LPDGuideMap;

	@Column(name = "LPD_Indemnity_bond")
	private String LPDIndemnityBond;


	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "Created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "Modified_by")
	private String modifiedBy;

	@Column(name = "Modified_date")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "Is_deleted")
	private String isDeleted;

	public Long getLandDetId() {
		return landDetId;
	}

	public void setLandDetId(Long landDetId) {
		this.landDetId = landDetId;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getDGPS_point() {
		return DGPS_point;
	}

	public void setDGPS_point(String dGPS_point) {
		DGPS_point = dGPS_point;
	}

	public BigDecimal getBCLTotApplArea() {
		return BCLTotApplArea;
	}

	public void setBCLTotApplArea(BigDecimal bCLTotApplArea) {
		BCLTotApplArea = bCLTotApplArea;
	}

	public String getBCLPurName() {
		return BCLPurName;
	}

	public void setBCLPurName(String bCLPurName) {
		BCLPurName = bCLPurName;
	}

	public BigDecimal getBCLArea() {
		return BCLArea;
	}

	public void setBCLArea(BigDecimal bCLArea) {
		BCLArea = bCLArea;
	}

	public BigDecimal getBCLFAR() {
		return BCLFAR;
	}

	public void setBCLFAR(BigDecimal bCLFAR) {
		BCLFAR = bCLFAR;
	}

	public String getLPDLPPDF() {
		return LPDLPPDF;
	}

	public void setLPDLPPDF(String lPDLPPDF) {
		LPDLPPDF = lPDLPPDF;
	}

	public String getLPDLPDXF() {
		return LPDLPDXF;
	}

	public void setLPDLPDXF(String lPDLPDXF) {
		LPDLPDXF = lPDLPDXF;
	}

	public String getLPDUndertaking() {
		return LPDUndertaking;
	}

	public void setLPDUndertaking(String lPDUndertaking) {
		LPDUndertaking = lPDUndertaking;
	}

	public String getLPDSEDP() {
		return LPDSEDP;
	}

	public void setLPDSEDP(String lPDSEDP) {
		LPDSEDP = lPDSEDP;
	}

	public String getLPDSESP() {
		return LPDSESP;
	}

	public void setLPDSESP(String lPDSESP) {
		LPDSESP = lPDSESP;
	}

	public String getLPDExplanatoryNote() {
		return LPDExplanatoryNote;
	}

	public void setLPDExplanatoryNote(String lPDExplanatoryNote) {
		LPDExplanatoryNote = lPDExplanatoryNote;
	}

	public String getLPDGuideMap() {
		return LPDGuideMap;
	}

	public void setLPDGuideMap(String lPDGuideMap) {
		LPDGuideMap = lPDGuideMap;
	}

	public String getLPDIndemnityBond() {
		return LPDIndemnityBond;
	}

	public void setLPDIndemnityBond(String lPDIndemnityBond) {
		LPDIndemnityBond = lPDIndemnityBond;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String[] getPkValues() {
		return new String[] { "NL", "TB_BPMS_APP_LAND_DET", "LAND_DET_ID" };
	}

}
