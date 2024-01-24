package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LicenseApplicationLandScheduleDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long landSchId;
    private LicenseApplicationMasterDTO tcpLicMstrId;
    private List<LicenseApplicationLandSurroundingsDTO> licenseApplicationLandSurroundingsDtoList = new ArrayList<LicenseApplicationLandSurroundingsDTO>();
    private String landApplicationNo;
    private String encumb;
    private String encumbRemarks;
    private String exiLitigation;
    private String exiLitigationCOrd;
    private String exiLitigationCOrdRemAse;
    private String insolv;
    private String insolvRemarks;
    private String spAppliedLand;
    private String spAppliedLandPatwari;
    private String spAppliedLandBoundary;
    private String spRevRasta;
    private String spRevRastaTyp;
    private Integer spRevRastaTypConId;
    private BigDecimal spRevRastaTypConWid;
    private String spWatercourse;
    private Integer spWatercourseTypId;
    private BigDecimal spWatercourseTypWid;
    private String spWatercourseTypRem;
    private String spAcqStatus;
    private Date spAcqStatusD4Not;
    private Date spAcqStatusD6Not;
    private Date spAcqStatusDAward;
    private String spAcqStatusExcluAquPr;
    private String spAcqStatusLdComp;
    private String spAcqStatusRelStat;
    private Date spAcqStatusRelDate;
    private String spAcqStatusSiteDet;
    private String spAcqStatusRelLitig;
    private String spAcqStatusRelCwpSl;
    private String spCompactBlock;
    private Long spCompactBlockSep;
    private String spCompactBlockPkt;
    private String existAppr;
    private String paSiteAprSR;
    private String tbBpmsLandSchcol;
    private BigDecimal aPaSiteAprSrWid;
    private String bPaSiteAprSrAcq;
    private String cPaSiteAprSrCons;
    private String dPaSiteAprSrSRA;
    private String ePaSiteAprSrSRC;
    private String paSiteAprSPR;
    private String aPaSiteAprSprWid;
    private String bPaSiteAprSprAcq;
    private String cPaSiteAprSprCons;
    private String paSiteAprOther;
    private String scVac;
    private String scVacRemmark;
    private String scHtLine;
    private String scHtLineRemark;
    private String scIocGasPline;
    private String scIocGasPlineRemark;
    private String scNallah;
    private String scNallahRemark;
    private String scUtilityLine;
    private BigDecimal scUtilityLineWidth;
    private String scUtilityLineRemark;
    private String scUtilityLineAL;
    private String scUtilityLineALRemark;
    private String scOtherFeature;
    private String scOtherFeatureDet;
    private LicenseApplicationLandCategoryDTO licenseApplicationLandCategoryDTO;
    private Long tbBpmsLandCatogoryId;
    private BigInteger createdBy;
    private Date createdDate;
    private BigInteger modifiedBy;
    private Date modifiedDate;
    private String isDeleted;
    private String lgIpMac;
    private String draftFlag;
    private String developerConsentFlag;
    private String accessNHSRFlag;
    
    
	public Long getLandSchId() {
		return landSchId;
	}
	public void setLandSchId(Long landSchId) {
		this.landSchId = landSchId;
	}
	public LicenseApplicationMasterDTO getTcpLicMstrId() {
		return tcpLicMstrId;
	}
	public void setTcpLicMstrId(LicenseApplicationMasterDTO tcpLicMstrId) {
		this.tcpLicMstrId = tcpLicMstrId;
	}
	public String getLandApplicationNo() {
		return landApplicationNo;
	}
	public void setLandApplicationNo(String landApplicationNo) {
		this.landApplicationNo = landApplicationNo;
	}
	public String getEncumb() {
		return encumb;
	}
	public void setEncumb(String encumb) {
		this.encumb = encumb;
	}
	public String getEncumbRemarks() {
		return encumbRemarks;
	}
	public void setEncumbRemarks(String encumbRemarks) {
		this.encumbRemarks = encumbRemarks;
	}
	public String getExiLitigation() {
		return exiLitigation;
	}
	public void setExiLitigation(String exiLitigation) {
		this.exiLitigation = exiLitigation;
	}
	public String getExiLitigationCOrd() {
		return exiLitigationCOrd;
	}
	public void setExiLitigationCOrd(String exiLitigationCOrd) {
		this.exiLitigationCOrd = exiLitigationCOrd;
	}
	public String getExiLitigationCOrdRemAse() {
		return exiLitigationCOrdRemAse;
	}
	public void setExiLitigationCOrdRemAse(String exiLitigationCOrdRemAse) {
		this.exiLitigationCOrdRemAse = exiLitigationCOrdRemAse;
	}
	public String getInsolv() {
		return insolv;
	}
	public void setInsolv(String insolv) {
		this.insolv = insolv;
	}
	public String getInsolvRemarks() {
		return insolvRemarks;
	}
	public void setInsolvRemarks(String insolvRemarks) {
		this.insolvRemarks = insolvRemarks;
	}
	public String getSpAppliedLand() {
		return spAppliedLand;
	}
	public void setSpAppliedLand(String spAppliedLand) {
		this.spAppliedLand = spAppliedLand;
	}
	public String getSpAppliedLandPatwari() {
		return spAppliedLandPatwari;
	}
	public void setSpAppliedLandPatwari(String spAppliedLandPatwari) {
		this.spAppliedLandPatwari = spAppliedLandPatwari;
	}
	public String getSpAppliedLandBoundary() {
		return spAppliedLandBoundary;
	}
	public void setSpAppliedLandBoundary(String spAppliedLandBoundary) {
		this.spAppliedLandBoundary = spAppliedLandBoundary;
	}
	public String getSpRevRasta() {
		return spRevRasta;
	}
	public void setSpRevRasta(String spRevRasta) {
		this.spRevRasta = spRevRasta;
	}
	public String getSpRevRastaTyp() {
		return spRevRastaTyp;
	}
	public void setSpRevRastaTyp(String spRevRastaTyp) {
		this.spRevRastaTyp = spRevRastaTyp;
	}
	public Integer getSpRevRastaTypConId() {
		return spRevRastaTypConId;
	}
	public void setSpRevRastaTypConId(Integer spRevRastaTypConId) {
		this.spRevRastaTypConId = spRevRastaTypConId;
	}
	public BigDecimal getSpRevRastaTypConWid() {
		return spRevRastaTypConWid;
	}
	public void setSpRevRastaTypConWid(BigDecimal spRevRastaTypConWid) {
		this.spRevRastaTypConWid = spRevRastaTypConWid;
	}
	public String getSpWatercourse() {
		return spWatercourse;
	}
	public void setSpWatercourse(String spWatercourse) {
		this.spWatercourse = spWatercourse;
	}
	public Integer getSpWatercourseTypId() {
		return spWatercourseTypId;
	}
	public void setSpWatercourseTypId(Integer spWatercourseTypId) {
		this.spWatercourseTypId = spWatercourseTypId;
	}
	public BigDecimal getSpWatercourseTypWid() {
		return spWatercourseTypWid;
	}
	public void setSpWatercourseTypWid(BigDecimal spWatercourseTypWid) {
		this.spWatercourseTypWid = spWatercourseTypWid;
	}
	public String getSpWatercourseTypRem() {
		return spWatercourseTypRem;
	}
	public void setSpWatercourseTypRem(String spWatercourseTypRem) {
		this.spWatercourseTypRem = spWatercourseTypRem;
	}
	public String getSpAcqStatus() {
		return spAcqStatus;
	}
	public void setSpAcqStatus(String spAcqStatus) {
		this.spAcqStatus = spAcqStatus;
	}
	public Date getSpAcqStatusD4Not() {
		return spAcqStatusD4Not;
	}
	public void setSpAcqStatusD4Not(Date spAcqStatusD4Not) {
		this.spAcqStatusD4Not = spAcqStatusD4Not;
	}
	public Date getSpAcqStatusD6Not() {
		return spAcqStatusD6Not;
	}
	public void setSpAcqStatusD6Not(Date spAcqStatusD6Not) {
		this.spAcqStatusD6Not = spAcqStatusD6Not;
	}
	public Date getSpAcqStatusDAward() {
		return spAcqStatusDAward;
	}
	public void setSpAcqStatusDAward(Date spAcqStatusDAward) {
		this.spAcqStatusDAward = spAcqStatusDAward;
	}
	public String getSpAcqStatusExcluAquPr() {
		return spAcqStatusExcluAquPr;
	}
	public void setSpAcqStatusExcluAquPr(String spAcqStatusExcluAquPr) {
		this.spAcqStatusExcluAquPr = spAcqStatusExcluAquPr;
	}
	public String getSpAcqStatusLdComp() {
		return spAcqStatusLdComp;
	}
	public void setSpAcqStatusLdComp(String spAcqStatusLdComp) {
		this.spAcqStatusLdComp = spAcqStatusLdComp;
	}
	public String getSpAcqStatusRelStat() {
		return spAcqStatusRelStat;
	}
	public void setSpAcqStatusRelStat(String spAcqStatusRelStat) {
		this.spAcqStatusRelStat = spAcqStatusRelStat;
	}
	public Date getSpAcqStatusRelDate() {
		return spAcqStatusRelDate;
	}
	public void setSpAcqStatusRelDate(Date spAcqStatusRelDate) {
		this.spAcqStatusRelDate = spAcqStatusRelDate;
	}
	public String getSpAcqStatusSiteDet() {
		return spAcqStatusSiteDet;
	}
	public void setSpAcqStatusSiteDet(String spAcqStatusSiteDet) {
		this.spAcqStatusSiteDet = spAcqStatusSiteDet;
	}
	public String getSpAcqStatusRelLitig() {
		return spAcqStatusRelLitig;
	}
	public void setSpAcqStatusRelLitig(String spAcqStatusRelLitig) {
		this.spAcqStatusRelLitig = spAcqStatusRelLitig;
	}
	public String getSpAcqStatusRelCwpSl() {
		return spAcqStatusRelCwpSl;
	}
	public void setSpAcqStatusRelCwpSl(String spAcqStatusRelCwpSl) {
		this.spAcqStatusRelCwpSl = spAcqStatusRelCwpSl;
	}
	public String getSpCompactBlock() {
		return spCompactBlock;
	}
	public void setSpCompactBlock(String spCompactBlock) {
		this.spCompactBlock = spCompactBlock;
	}
	public Long getSpCompactBlockSep() {
		return spCompactBlockSep;
	}
	public void setSpCompactBlockSep(Long spCompactBlockSep) {
		this.spCompactBlockSep = spCompactBlockSep;
	}
	public String getSpCompactBlockPkt() {
		return spCompactBlockPkt;
	}
	public void setSpCompactBlockPkt(String spCompactBlockPkt) {
		this.spCompactBlockPkt = spCompactBlockPkt;
	}
	public String getExistAppr() {
		return existAppr;
	}
	public void setExistAppr(String existAppr) {
		this.existAppr = existAppr;
	}
	public String getPaSiteAprSR() {
		return paSiteAprSR;
	}
	public void setPaSiteAprSR(String paSiteAprSR) {
		this.paSiteAprSR = paSiteAprSR;
	}
	public String getTbBpmsLandSchcol() {
		return tbBpmsLandSchcol;
	}
	public void setTbBpmsLandSchcol(String tbBpmsLandSchcol) {
		this.tbBpmsLandSchcol = tbBpmsLandSchcol;
	}
	public BigDecimal getaPaSiteAprSrWid() {
		return aPaSiteAprSrWid;
	}
	public void setaPaSiteAprSrWid(BigDecimal aPaSiteAprSrWid) {
		this.aPaSiteAprSrWid = aPaSiteAprSrWid;
	}
	public String getbPaSiteAprSrAcq() {
		return bPaSiteAprSrAcq;
	}
	public void setbPaSiteAprSrAcq(String bPaSiteAprSrAcq) {
		this.bPaSiteAprSrAcq = bPaSiteAprSrAcq;
	}
	public String getcPaSiteAprSrCons() {
		return cPaSiteAprSrCons;
	}
	public void setcPaSiteAprSrCons(String cPaSiteAprSrCons) {
		this.cPaSiteAprSrCons = cPaSiteAprSrCons;
	}
	public String getdPaSiteAprSrSRA() {
		return dPaSiteAprSrSRA;
	}
	public void setdPaSiteAprSrSRA(String dPaSiteAprSrSRA) {
		this.dPaSiteAprSrSRA = dPaSiteAprSrSRA;
	}
	public String getePaSiteAprSrSRC() {
		return ePaSiteAprSrSRC;
	}
	public void setePaSiteAprSrSRC(String ePaSiteAprSrSRC) {
		this.ePaSiteAprSrSRC = ePaSiteAprSrSRC;
	}
	public String getPaSiteAprSPR() {
		return paSiteAprSPR;
	}
	public void setPaSiteAprSPR(String paSiteAprSPR) {
		this.paSiteAprSPR = paSiteAprSPR;
	}
	public String getaPaSiteAprSprWid() {
		return aPaSiteAprSprWid;
	}
	public void setaPaSiteAprSprWid(String aPaSiteAprSprWid) {
		this.aPaSiteAprSprWid = aPaSiteAprSprWid;
	}
	public String getbPaSiteAprSprAcq() {
		return bPaSiteAprSprAcq;
	}
	public void setbPaSiteAprSprAcq(String bPaSiteAprSprAcq) {
		this.bPaSiteAprSprAcq = bPaSiteAprSprAcq;
	}
	public String getcPaSiteAprSprCons() {
		return cPaSiteAprSprCons;
	}
	public void setcPaSiteAprSprCons(String cPaSiteAprSprCons) {
		this.cPaSiteAprSprCons = cPaSiteAprSprCons;
	}
	public String getPaSiteAprOther() {
		return paSiteAprOther;
	}
	public void setPaSiteAprOther(String paSiteAprOther) {
		this.paSiteAprOther = paSiteAprOther;
	}
	public String getScVac() {
		return scVac;
	}
	public void setScVac(String scVac) {
		this.scVac = scVac;
	}
	public String getScVacRemmark() {
		return scVacRemmark;
	}
	public void setScVacRemmark(String scVacRemmark) {
		this.scVacRemmark = scVacRemmark;
	}
	public String getScHtLine() {
		return scHtLine;
	}
	public void setScHtLine(String scHtLine) {
		this.scHtLine = scHtLine;
	}
	public String getScHtLineRemark() {
		return scHtLineRemark;
	}
	public void setScHtLineRemark(String scHtLineRemark) {
		this.scHtLineRemark = scHtLineRemark;
	}
	public String getScIocGasPline() {
		return scIocGasPline;
	}
	public void setScIocGasPline(String scIocGasPline) {
		this.scIocGasPline = scIocGasPline;
	}
	public String getScIocGasPlineRemark() {
		return scIocGasPlineRemark;
	}
	public void setScIocGasPlineRemark(String scIocGasPlineRemark) {
		this.scIocGasPlineRemark = scIocGasPlineRemark;
	}
	public String getScNallah() {
		return scNallah;
	}
	public void setScNallah(String scNallah) {
		this.scNallah = scNallah;
	}
	public String getScNallahRemark() {
		return scNallahRemark;
	}
	public void setScNallahRemark(String scNallahRemark) {
		this.scNallahRemark = scNallahRemark;
	}
	public String getScUtilityLine() {
		return scUtilityLine;
	}
	public void setScUtilityLine(String scUtilityLine) {
		this.scUtilityLine = scUtilityLine;
	}
	public BigDecimal getScUtilityLineWidth() {
		return scUtilityLineWidth;
	}
	public void setScUtilityLineWidth(BigDecimal scUtilityLineWidth) {
		this.scUtilityLineWidth = scUtilityLineWidth;
	}
	public String getScUtilityLineRemark() {
		return scUtilityLineRemark;
	}
	public void setScUtilityLineRemark(String scUtilityLineRemark) {
		this.scUtilityLineRemark = scUtilityLineRemark;
	}
	public String getScUtilityLineAL() {
		return scUtilityLineAL;
	}
	public void setScUtilityLineAL(String scUtilityLineAL) {
		this.scUtilityLineAL = scUtilityLineAL;
	}
	public String getScUtilityLineALRemark() {
		return scUtilityLineALRemark;
	}
	public void setScUtilityLineALRemark(String scUtilityLineALRemark) {
		this.scUtilityLineALRemark = scUtilityLineALRemark;
	}
	public String getScOtherFeature() {
		return scOtherFeature;
	}
	public void setScOtherFeature(String scOtherFeature) {
		this.scOtherFeature = scOtherFeature;
	}
	public String getScOtherFeatureDet() {
		return scOtherFeatureDet;
	}
	public void setScOtherFeatureDet(String scOtherFeatureDet) {
		this.scOtherFeatureDet = scOtherFeatureDet;
	}
	public LicenseApplicationLandCategoryDTO getLicenseApplicationLandCategoryDTO() {
		return licenseApplicationLandCategoryDTO;
	}
	public void setLicenseApplicationLandCategoryDTO(LicenseApplicationLandCategoryDTO licenseApplicationLandCategoryDTO) {
		this.licenseApplicationLandCategoryDTO = licenseApplicationLandCategoryDTO;
	}
	public Long getTbBpmsLandCatogoryId() {
		return tbBpmsLandCatogoryId;
	}
	public void setTbBpmsLandCatogoryId(Long tbBpmsLandCatogoryId) {
		this.tbBpmsLandCatogoryId = tbBpmsLandCatogoryId;
	}
	public BigInteger getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(BigInteger createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public BigInteger getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(BigInteger modifiedBy) {
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
	public String getLgIpMac() {
		return lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}
	public String getDraftFlag() {
		return draftFlag;
	}
	public void setDraftFlag(String draftFlag) {
		this.draftFlag = draftFlag;
	}
	public List<LicenseApplicationLandSurroundingsDTO> getLicenseApplicationLandSurroundingsDtoList() {
		return licenseApplicationLandSurroundingsDtoList;
	}
	public void setLicenseApplicationLandSurroundingsDtoList(List<LicenseApplicationLandSurroundingsDTO> licenseApplicationLandSurroundingsDtoList) {
		this.licenseApplicationLandSurroundingsDtoList = licenseApplicationLandSurroundingsDtoList;
	}
	public String getDeveloperConsentFlag() {
		return developerConsentFlag;
	}
	public void setDeveloperConsentFlag(String developerConsentFlag) {
		this.developerConsentFlag = developerConsentFlag;
	}
	public String getAccessNHSRFlag() {
		return accessNHSRFlag;
	}
	public void setAccessNHSRFlag(String accessNHSRFlag) {
		this.accessNHSRFlag = accessNHSRFlag;
	}
    
    
}
