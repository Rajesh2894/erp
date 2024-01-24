package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;

public class LicenseApplicationLandDetDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long landDetId;
    private LicenseApplicationMasterDTO tcpLicMstrId;
    private String applicationNo;
    private String DGPS_point;
    private BigDecimal BCLTotApplArea;
    private String BCLPurName;
    private BigDecimal BCLArea;
    private BigDecimal BCLFAR;
    private String LPDLPPDF;
    private String LPDLPDXF;
    private String LPDUndertaking;
    private String LPDSEDP;
    private String LPDSESP;
    private String LPDExplanatoryNote;
    private String LPDGuideMap;
    private String LPDIndemnityBond;
    private String LDPOthDoc;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String isDeleted;
    
    
	public Long getLandDetId() {
		return landDetId;
	}
	public void setLandDetId(Long landDetId) {
		this.landDetId = landDetId;
	}
	public LicenseApplicationMasterDTO getTcpLicMstrId() {
		return tcpLicMstrId;
	}
	public void setTcpLicMstrId(LicenseApplicationMasterDTO tcpLicMstrId) {
		this.tcpLicMstrId = tcpLicMstrId;
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
	public String getLDPOthDoc() {
		return LDPOthDoc;
	}
	public void setLDPOthDoc(String lDPOthDoc) {
		LDPOthDoc = lDPOthDoc;
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
    

}
