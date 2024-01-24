package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_LAND_SCH_HIST")
public class LicenseApplicationLandScheduleHist implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_LAND_SCH_ID", nullable = false)
	private Long landSchHistId;
	
	@Column(name = "LAND_SCH_ID", nullable = false)
	private Long landSchId;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "TCP_LIC_MSTR_ID", unique = true)
    private LicenseApplicationMaster licenseApplicationMaster;

	@Column(name = "Land_application_no", nullable = true)
	private String landApplicationNo;
	
	@Column(name = "encumb", nullable = true)
	private String encumb;

	@Column(name = "encumb_remarks", nullable = true)
	private String encumbRemarks;

	@Column(name = "Exi_Litigation", nullable = true)
	private String exiLitigation;

	@Column(name = "Exi_Litigation_C_ord", nullable = true)
	private String exiLitigationCOrd;

	@Column(name = "Exi_Litigation_C_ord_rem_ase", columnDefinition = "TEXT", nullable = true)
	private String exiLitigationCOrdRemAse;

	@Column(name = "Insolv", nullable = true)
	private String insolv;

	@Column(name = "Insolv_remarks", columnDefinition = "TEXT", nullable = true)
	private String insolvRemarks;

	@Column(name = "SP_applied_land", nullable = true)
	private String spAppliedLand;

	@Column(name = "SP_applied_land_patwari", nullable = true)
	private String spAppliedLandPatwari;

	@Column(name = "SP_applied_land_boundary",nullable = true)
	private String spAppliedLandBoundary;

	@Column(name = "SP_rev_rasta", nullable = true)
	private String spRevRasta;

	@Column(name = "SP_rev_rasta_typ", nullable = true)
	private String spRevRastaTyp;

	@Column(name = "SP_rev_rasta_typ_con_id", nullable = true)
	private Integer spRevRastaTypConId;

	@Column(name = "SP_rev_rasta_typ_con_wid", precision = 2, scale = 0, nullable = true)
	private BigDecimal spRevRastaTypConWid;

	@Column(name = "SP_Watercourse", nullable = true)
	private String spWatercourse;

	@Column(name = "SP_Watercourse_typ_id", nullable = true)
	private Integer spWatercourseTypId;

	@Column(name = "SP_Watercourse_typ_wid", precision = 20, scale = 0, nullable = true)
	private BigDecimal spWatercourseTypWid;

	@Column(name = "SP_Watercourse_typ_rem", columnDefinition = "TEXT", nullable = true)
	private String spWatercourseTypRem;

	@Column(name = "SP_Acq_status", nullable = true)
	private String spAcqStatus;

	@Column(name = "SP_Acq_status_D4_not", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date spAcqStatusD4Not;

	@Column(name = "SP_Acq_status_D6_not", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date spAcqStatusD6Not;

	@Column(name = "SP_Acq_status_DAward", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date spAcqStatusDAward;

	@Column(name = "SP_Acq_status_Exclu_Aqu_pr", nullable = true)
	private String spAcqStatusExcluAquPr;

	@Column(name = "SP_Acq_status_Ld_comp", nullable = true)
	private String spAcqStatusLdComp;

	@Column(name = "SP_Acq_status_rel_stat",  nullable = true)
	private String spAcqStatusRelStat;

	@Column(name = "SP_Acq_status_rel_date", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date spAcqStatusRelDate;

	@Column(name = "SP_Acq_status_site_det", nullable = true)
	private String spAcqStatusSiteDet;

	@Column(name = "SP_Acq_status_rel_litig", nullable = true)
	private String spAcqStatusRelLitig;

	@Column(name = "SP_Acq_status_rel_CWP_SLP", nullable = true)
	private String spAcqStatusRelCwpSl;

	@Column(name = "SP_compact_block", nullable = true)
	private String spCompactBlock;

	@Column(name = "SP_compact_block_sep", nullable = true)
	private Long spCompactBlockSep;

	@Column(name = "SP_compact_block_pkt", nullable = true)
	private String spCompactBlockPkt;

	@Column(name = "exist_appr", nullable = true)
	private String existAppr;

	@Column(name = "pa_site_apr_SR", nullable = true)
	private String paSiteAprSR;

	@Column(name = "tb_bpms_land_schcol", nullable = true)
	private String tbBpmsLandSchcol;

	@Column(name = "a_pa_site_apr_SR_wid", precision = 20, scale = 5, nullable = true)
	private BigDecimal aPaSiteAprSrWid;

	@Column(name = "b_pa_site_apr_SR_acq", nullable = true)
	private String bPaSiteAprSrAcq;

	@Column(name = "c_pa_site_apr_SR_cons", nullable = true)
	private String cPaSiteAprSrCons;

	@Column(name = "d_pa_site_apr_SR_SRA", nullable = true)
	private String dPaSiteAprSrSRA;

	@Column(name = "e_pa_site_apr_SR_SRC", nullable = true)
	private String ePaSiteAprSrSRC;

	@Column(name = "pa_site_apr_SPR", nullable = true)
	private String paSiteAprSPR;

	@Column(name = "a_pa_site_apr_SPR_wid", nullable = true)
	private String aPaSiteAprSprWid;

	@Column(name = "b_pa_site_apr_SPR_acq", nullable = true)
	private String bPaSiteAprSprAcq;

	@Column(name = "c_pa_site_apr_SPR_cons", nullable = true)
	private String cPaSiteAprSprCons;

	@Column(name = "pa_site_apr_other", nullable = true)
	private String paSiteAprOther;

	@Column(name = "SC_vac", nullable = true)
	private String scVac;

	@Column(name = "SC_vac_remmark", columnDefinition = "LONGTEXT", nullable = true)
	private String scVacRemmark;

	@Column(name = "SC_HT_line", nullable = true)
	private String scHtLine;

	@Column(name = "SC_HT_line_remark", columnDefinition = "LONGTEXT", nullable = true)
	private String scHtLineRemark;

	@Column(name = "SC_IOC_Gas_Pline", nullable = true)
	private String scIocGasPline;

	@Column(name = "SC_IOC_Gas_Pline_remark", columnDefinition = "LONGTEXT", nullable = true)
	private String scIocGasPlineRemark;

	@Column(name = "SC_Nallah", nullable = true)
	private String scNallah;

	@Column(name = "SC_Nallah_remark", columnDefinition = "LONGTEXT", nullable = true)
	private String scNallahRemark;

	@Column(name = "SC_Utility_Line", nullable = true)
	private String scUtilityLine;

	@Column(name = "SC_Utility_Line_width", precision = 7, scale = 3, nullable = true)
	private BigDecimal scUtilityLineWidth;

	@Column(name = "SC_Utility_Line_remark", columnDefinition = "LONGTEXT", nullable = true)
	private String scUtilityLineRemark;

	@Column(name = "SC_Utility_Line_AL", nullable = true)
	private String scUtilityLineAL;

	@Column(name = "SC_Utility_Line_AL_remark", columnDefinition = "LONGTEXT", nullable = true)
	private String scUtilityLineALRemark;

	@Column(name = "SC_Other_feature", nullable = true)
	private String scOtherFeature;

	@Column(name = "SC_Other_feature_det", columnDefinition = "LONGTEXT", nullable = true)
	private String scOtherFeatureDet;

	@OneToOne
	@JoinColumn(name = "land_catogory_id", referencedColumnName = "land_catogory_id")
	private LicenseApplicationLandCategoryEntity licenseApplicationLandCategoryEntity;

	@Column(name = "created_by")
	private BigInteger createdBy;

	@Column(name = "Created_date")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "Modified_by")
	private BigInteger modifiedBy;

	@Column(name = "Modified_date")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "Is_deleted", nullable = true)
	private String isDeleted;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "Draft_flag", nullable = true)
	private String draftFlag;
	
	@Column(name = "accessNHSRFlag")
	private String accessNHSRFlag;
	
	@Column(name = "H_STATUS")
	private String hStatus;

	public Long getLandSchId() {
		return landSchId;
	}

	public void setLandSchId(Long landSchId) {
		this.landSchId = landSchId;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
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

	public LicenseApplicationLandCategoryEntity getLicenseApplicationLandCategoryEntity() {
		return licenseApplicationLandCategoryEntity;
	}

	public void setLicenseApplicationLandCategoryEntity(
			LicenseApplicationLandCategoryEntity licenseApplicationLandCategoryEntity) {
		this.licenseApplicationLandCategoryEntity = licenseApplicationLandCategoryEntity;
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
	
	public String getAccessNHSRFlag() {
		return accessNHSRFlag;
	}

	public void setAccessNHSRFlag(String accessNHSRFlag) {
		this.accessNHSRFlag = accessNHSRFlag;
	}
	

	public Long getLandSchHistId() {
		return landSchHistId;
	}

	public void setLandSchHistId(Long landSchHistId) {
		this.landSchHistId = landSchHistId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
		return new String[] { "NL", "TB_BPMS_LAND_SCH_HIST", "H_LAND_SCH_ID" };
	}
}
