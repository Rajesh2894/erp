package com.abm.mainet.rts.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_rts_deathregcert")
@NamedQuery(name = "DeathCertificateEntity.findAll", query = "SELECT t FROM DeathCertificateEntity t")
public class DeathCertificateEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5769136608506303992L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DR_R_ID", nullable = false)
	private Long drRId;

	@Column(name = "APPLICANT_EMAIL", length = 100)
	private String applicantEmail;

	@Column(name = "APPLICANT_FNAME", length = 100)
	private String applicantFname;

	@Column(name = "APPLICANT_LNAME", length = 100)
	private String applicantLname;

	@Column(name = "APPLICANT_MNAME", length = 100)
	private String applicantMname;

	@Column(name = "APPLICANT_MOBILNO", length = 20)
	private String applicantMobilno;

	@Column(name = "APPLICANT_TITLE")
	private Long applicantTitle;

	@Column(name = "BPL_NO", length = 16)
	private String bplNo;

	@Column(name = "CE_ADDR", length = 200)
	private String ceAddr;

	@Column(name = "CE_ADDR_MAR", length = 200)
	private String ceAddrMar;

	@Column(name = "CE_FLAG", length = 1)
	private String ceFlag;

	@Column(name = "CE_ID")
	private Long ceId;

	@Column(name = "CE_NAME", length = 100)
	private String ceName;

	@Column(name = "CE_NAME_MAR", length = 100)
	private String ceNameMar;

	@Column(name = "CPD_AGEPERIOD_ID")
	private Long cpdAgeperiodId;

	@Column(name = "CPD_ATTNTYPE_ID")
	private Long cpdAttntypeId;

	@Column(name = "CPD_DEATHCAUSE_ID")
	private Long cpdDeathcauseId;

	@Column(name = "CPD_DEATHPLACE_TYPE", length = 10)
	private String cpdDeathplaceType;

	@Column(name = "CPD_DISTRICT_ID")
	private Long cpdDistrictId;

	@Column(name = "CPD_EDUCATION_ID")
	private Long cpdEducationId;

	@Column(name = "CPD_MARITAL_STAT_ID")
	private Long cpdMaritalStatId;

	@Column(name = "CPD_NATIONALITY_ID")
	private Long cpdNationalityId;

	@Column(name = "CPD_OCCUPATION_ID")
	private Long cpdOccupationId;

	@Column(name = "CPD_REG_UNIT")
	private Long cpdRegUnit;

	@Column(name = "CPD_RELIGION_ID")
	private Long cpdReligionId;

	@Column(name = "CPD_RES_ID")
	private Long cpdResId;

	@Column(name = "CPD_STATE_ID")
	private Long cpdStateId;

	@Column(name = "CPD_TALUKA_ID")
	private Long cpdTalukaId;

	@Column(name = "CPDID_MC_DEATH_MANNER")
	private Long cpdidMcDeathManner;

	@Column(name = "DC_UID", length = 12)
	private String dcUid;

	@Column(name = "DCM_ID")
	private Long dcmId;

	@Column(name = "DEC_ALCOHOLIC", length = 1)
	private String decAlcoholic;

	@Column(name = "DEC_ALCOHOLIC_YR")
	private Long decAlcoholicYr;

	@Column(name = "DEC_CHEWARAC", length = 1)
	private String decChewarac;

	@Column(name = "DEC_CHEWARAC_YR")
	private Long decChewaracYr;

	@Column(name = "DEC_CHEWTB", length = 1)
	private String decChewtb;

	@Column(name = "DEC_CHEWTB_YR")
	private Long decChewtbYr;

	@Column(name = "DEC_SMOKER", length = 1)
	private String decSmoker;

	@Column(name = "DEC_SMOKER_YR")
	private Long decSmokerYr;

	@Column(name = "DR_CERT_NO", length = 12)
	private String drCertNo;

	@Column(name = "DR_DCADDR_ATDEATH", length = 200)
	private String drDcaddrAtdeath;

	@Column(name = "DR_DCADDR_ATDEATH_MAR", length = 500)
	private String drDcaddrAtdeathMar;

	@Column(name = "DR_DEATHADDR", length = 200)
	private String drDeathaddr;

	@Column(name = "DR_DEATHPLACE", length = 300)
	private String drDeathplace;

	@Column(name = "DR_DECEASEDADDR", length = 200)
	private String drDeceasedaddr;

	@Column(name = "DR_DECEASEDAGE", precision = 10, scale = 2)
	private Long drDeceasedage;

	@Column(name = "DR_DECEASEDNAME", length = 200)
	private String drDeceasedname;

	@Temporal(TemporalType.DATE)
	@Column(name = "DR_DOD")
	private Date drDod;

	@Column(name = "DR_INFORMANT_ADDR", length = 200)
	private String drInformantAddr;

	@Column(name = "DR_INFORMANT_NAME", length = 200)
	private String drInformantName;

	@Column(name = "DR_MAR_DEATHADDR", length = 200)
	private String drMarDeathaddr;

	@Column(name = "DR_MAR_DEATHPLACE", length = 200)
	private String drMarDeathplace;

	@Column(name = "DR_MAR_DECEASEDADDR", length = 200)
	private String drMarDeceasedaddr;

	@Column(name = "DR_MAR_DECEASEDNAME", length = 200)
	private String drMarDeceasedname;

	@Column(name = "DR_MAR_INFORMANT_ADDR", length = 200)
	private String drMarInformantAddr;

	@Column(name = "DR_MAR_INFORMANT_NAME", length = 200)
	private String drMarInformantName;

	@Column(name = "DR_MAR_MOTHER_NAME", length = 200)
	private String drMarMotherName;

	@Column(name = "DR_MAR_RELATIVE_NAME", length = 200)
	private String drMarRelativeName;

	@Column(name = "DR_MOTHER_NAME", length = 100)
	private String drMotherName;

	@Temporal(TemporalType.DATE)
	@Column(name = "DR_REGDATE", nullable = false)
	private Date drRegdate;

	@Column(name = "DR_REGNO", length = 12)
	private String drRegno;

	@Column(name = "DR_RELATIVE_NAME", length = 200)
	private String drRelativeName;

	@Column(name = "DR_REMARKS", length = 2000)
	private String drRemarks;

	@Column(name = "DR_SEX")
	private String drSex;

	@Column(name = "DR_STATUS", length = 1)
	private String drStatus;

	@Column(name = "DRAFT_STATUS", length = 1)
	private String draftStatus;

	@Column(name = "HI_ID")
	private Long hiId;

	@Column(name = "LANG_ID", nullable = false)
	private int langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lmoddate")
	private Date lmoddate;

	@Column(name = "MC_DEATH_MANNER", length = 50)
	private String mcDeathManner;

	@Column(name = "MC_DEATHCAUSE", length = 200)
	private String mcDeathcause;

	@Column(name = "MC_DELIVERY", length = 1)
	private String mcDelivery;

	@Column(name = "MC_INTERONSET")
	private Long mcInteronset;

	@Column(name = "MC_MD_SUPR_NAME", length = 50)
	private String mcMdSuprName;

	@Column(name = "MC_MDATTND_NAME", length = 50)
	private String mcMdattndName;

	@Column(name = "MC_OTHERCOND", length = 200)
	private String mcOthercond;

	@Column(name = "MC_PREGN_ASSOC", length = 1)
	private String mcPregnAssoc;

	@Temporal(TemporalType.DATE)
	@Column(name = "MC_VERIFN_DATE")
	private Date mcVerifnDate;

	@Column(name = "MC_WARD_ID")
	private Long mcWardId;

	@Column(name = "MED_CERT", length = 1)
	private String medCert;

	@Column(name = "MODE_CPD_ID", precision = 10)
	private Long modeCpdId;

	@Column(name = "orgid", nullable = false)
	private Long orgId;

	@Column(name = "OTHER_RELIGION", length = 500)
	private String otherReligion;

	@Column(name = "PD_UID_F", length = 12)
	private String pdUidF;

	@Column(name = "PD_UID_M", length = 12)
	private String pdUidM;

	@Column(name = "SM_DRAFT_ID")
	private Long smDraftId;

	@Column(name = "SM_SERVICE_ID")
	private Long smServiceId;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "WARDID")
	private Long wardid;

	@Column(name = "WF_STATUS")
	private String wfStatus;

	@Transient
	private Long applnId;

	@Column(name = "DEMANDED_COPIES")
	private Long demandedCopies;
	
	@Column(name = "ISSUED_COPIES")
	private Long issuedCopies;
	
	@Column(name = "APPLICATION_NO")
	private Long applicationNo;

	public String getApplicantEmail() {
		return this.applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public String getApplicantFname() {
		return this.applicantFname;
	}

	public void setApplicantFname(String applicantFname) {
		this.applicantFname = applicantFname;
	}

	public String getApplicantLname() {
		return this.applicantLname;
	}

	public void setApplicantLname(String applicantLname) {
		this.applicantLname = applicantLname;
	}

	public String getApplicantMname() {
		return this.applicantMname;
	}

	public void setApplicantMname(String applicantMname) {
		this.applicantMname = applicantMname;
	}

	public String getApplicantMobilno() {
		return this.applicantMobilno;
	}

	public void setApplicantMobilno(String applicantMobilno) {
		this.applicantMobilno = applicantMobilno;
	}

	public Long getApplicantTitle() {
		return this.applicantTitle;
	}

	public void setApplicantTitle(Long applicantTitle) {
		this.applicantTitle = applicantTitle;
	}

	public String getBplNo() {
		return this.bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public String getCeAddr() {
		return this.ceAddr;
	}

	public void setCeAddr(String ceAddr) {
		this.ceAddr = ceAddr;
	}

	public String getCeAddrMar() {
		return this.ceAddrMar;
	}

	public void setCeAddrMar(String ceAddrMar) {
		this.ceAddrMar = ceAddrMar;
	}

	public String getCeFlag() {
		return this.ceFlag;
	}

	public void setCeFlag(String ceFlag) {
		this.ceFlag = ceFlag;
	}

	public Long getCeId() {
		return this.ceId;
	}

	public void setCeId(Long ceId) {
		this.ceId = ceId;
	}

	public String getCeName() {
		return this.ceName;
	}

	public void setCeName(String ceName) {
		this.ceName = ceName;
	}

	public String getCeNameMar() {
		return this.ceNameMar;
	}

	public void setCeNameMar(String ceNameMar) {
		this.ceNameMar = ceNameMar;
	}

	public Long getCpdAgeperiodId() {
		return this.cpdAgeperiodId;
	}

	public void setCpdAgeperiodId(Long cpdAgeperiodId) {
		this.cpdAgeperiodId = cpdAgeperiodId;
	}

	public Long getCpdAttntypeId() {
		return this.cpdAttntypeId;
	}

	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}

	public Long getCpdDeathcauseId() {
		return this.cpdDeathcauseId;
	}

	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
	}

	public String getCpdDeathplaceType() {
		return this.cpdDeathplaceType;
	}

	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}

	public Long getCpdDistrictId() {
		return this.cpdDistrictId;
	}

	public void setCpdDistrictId(Long cpdDistrictId) {
		this.cpdDistrictId = cpdDistrictId;
	}

	public Long getCpdEducationId() {
		return this.cpdEducationId;
	}

	public void setCpdEducationId(Long cpdEducationId) {
		this.cpdEducationId = cpdEducationId;
	}

	public Long getCpdMaritalStatId() {
		return this.cpdMaritalStatId;
	}

	public void setCpdMaritalStatId(Long cpdMaritalStatId) {
		this.cpdMaritalStatId = cpdMaritalStatId;
	}

	public Long getCpdNationalityId() {
		return this.cpdNationalityId;
	}

	public void setCpdNationalityId(Long cpdNationalityId) {
		this.cpdNationalityId = cpdNationalityId;
	}

	public Long getCpdOccupationId() {
		return this.cpdOccupationId;
	}

	public void setCpdOccupationId(Long cpdOccupationId) {
		this.cpdOccupationId = cpdOccupationId;
	}

	public Long getCpdRegUnit() {
		return this.cpdRegUnit;
	}

	public void setCpdRegUnit(Long cpdRegUnit) {
		this.cpdRegUnit = cpdRegUnit;
	}

	public Long getCpdReligionId() {
		return this.cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}

	public Long getCpdResId() {
		return this.cpdResId;
	}

	public void setCpdResId(Long cpdResId) {
		this.cpdResId = cpdResId;
	}

	public Long getCpdStateId() {
		return this.cpdStateId;
	}

	public void setCpdStateId(Long cpdStateId) {
		this.cpdStateId = cpdStateId;
	}

	public Long getCpdTalukaId() {
		return this.cpdTalukaId;
	}

	public void setCpdTalukaId(Long cpdTalukaId) {
		this.cpdTalukaId = cpdTalukaId;
	}

	public Long getCpdidMcDeathManner() {
		return this.cpdidMcDeathManner;
	}

	public void setCpdidMcDeathManner(Long cpdidMcDeathManner) {
		this.cpdidMcDeathManner = cpdidMcDeathManner;
	}

	public String getDcUid() {
		return this.dcUid;
	}

	public void setDcUid(String dcUid) {
		this.dcUid = dcUid;
	}

	public Long getDcmId() {
		return this.dcmId;
	}

	public void setDcmId(Long dcmId) {
		this.dcmId = dcmId;
	}

	public String getDecAlcoholic() {
		return this.decAlcoholic;
	}

	public void setDecAlcoholic(String decAlcoholic) {
		this.decAlcoholic = decAlcoholic;
	}

	public Long getDecAlcoholicYr() {
		return this.decAlcoholicYr;
	}

	public void setDecAlcoholicYr(Long decAlcoholicYr) {
		this.decAlcoholicYr = decAlcoholicYr;
	}

	public String getDecChewarac() {
		return this.decChewarac;
	}

	public void setDecChewarac(String decChewarac) {
		this.decChewarac = decChewarac;
	}

	public Long getDecChewaracYr() {
		return this.decChewaracYr;
	}

	public void setDecChewaracYr(Long decChewaracYr) {
		this.decChewaracYr = decChewaracYr;
	}

	public String getDecChewtb() {
		return this.decChewtb;
	}

	public void setDecChewtb(String decChewtb) {
		this.decChewtb = decChewtb;
	}

	public Long getDecChewtbYr() {
		return this.decChewtbYr;
	}

	public void setDecChewtbYr(Long decChewtbYr) {
		this.decChewtbYr = decChewtbYr;
	}

	public String getDecSmoker() {
		return this.decSmoker;
	}

	public void setDecSmoker(String decSmoker) {
		this.decSmoker = decSmoker;
	}

	public Long getDecSmokerYr() {
		return this.decSmokerYr;
	}

	public void setDecSmokerYr(Long decSmokerYr) {
		this.decSmokerYr = decSmokerYr;
	}

	public String getDrCertNo() {
		return this.drCertNo;
	}

	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
	}

	public String getDrDcaddrAtdeath() {
		return this.drDcaddrAtdeath;
	}

	public void setDrDcaddrAtdeath(String drDcaddrAtdeath) {
		this.drDcaddrAtdeath = drDcaddrAtdeath;
	}

	public String getDrDcaddrAtdeathMar() {
		return this.drDcaddrAtdeathMar;
	}

	public void setDrDcaddrAtdeathMar(String drDcaddrAtdeathMar) {
		this.drDcaddrAtdeathMar = drDcaddrAtdeathMar;
	}

	public String getDrDeathaddr() {
		return this.drDeathaddr;
	}

	public void setDrDeathaddr(String drDeathaddr) {
		this.drDeathaddr = drDeathaddr;
	}

	public String getDrDeathplace() {
		return this.drDeathplace;
	}

	public void setDrDeathplace(String drDeathplace) {
		this.drDeathplace = drDeathplace;
	}

	public String getDrDeceasedaddr() {
		return this.drDeceasedaddr;
	}

	public void setDrDeceasedaddr(String drDeceasedaddr) {
		this.drDeceasedaddr = drDeceasedaddr;
	}

	public String getDrDeceasedname() {
		return this.drDeceasedname;
	}

	public void setDrDeceasedname(String drDeceasedname) {
		this.drDeceasedname = drDeceasedname;
	}

	public Date getDrDod() {
		return this.drDod;
	}

	public void setDrDod(Date drDod) {
		this.drDod = drDod;
	}

	public String getDrInformantAddr() {
		return this.drInformantAddr;
	}

	public void setDrInformantAddr(String drInformantAddr) {
		this.drInformantAddr = drInformantAddr;
	}

	public String getDrInformantName() {
		return this.drInformantName;
	}

	public void setDrInformantName(String drInformantName) {
		this.drInformantName = drInformantName;
	}

	public String getDrMarDeathaddr() {
		return this.drMarDeathaddr;
	}

	public void setDrMarDeathaddr(String drMarDeathaddr) {
		this.drMarDeathaddr = drMarDeathaddr;
	}

	public String getDrMarDeathplace() {
		return this.drMarDeathplace;
	}

	public void setDrMarDeathplace(String drMarDeathplace) {
		this.drMarDeathplace = drMarDeathplace;
	}

	public String getDrMarDeceasedaddr() {
		return this.drMarDeceasedaddr;
	}

	public void setDrMarDeceasedaddr(String drMarDeceasedaddr) {
		this.drMarDeceasedaddr = drMarDeceasedaddr;
	}

	public String getDrMarDeceasedname() {
		return this.drMarDeceasedname;
	}

	public void setDrMarDeceasedname(String drMarDeceasedname) {
		this.drMarDeceasedname = drMarDeceasedname;
	}

	public String getDrMarInformantAddr() {
		return this.drMarInformantAddr;
	}

	public void setDrMarInformantAddr(String drMarInformantAddr) {
		this.drMarInformantAddr = drMarInformantAddr;
	}

	public String getDrMarInformantName() {
		return this.drMarInformantName;
	}

	public void setDrMarInformantName(String drMarInformantName) {
		this.drMarInformantName = drMarInformantName;
	}

	public String getDrMarMotherName() {
		return this.drMarMotherName;
	}

	public void setDrMarMotherName(String drMarMotherName) {
		this.drMarMotherName = drMarMotherName;
	}

	public String getDrMarRelativeName() {
		return this.drMarRelativeName;
	}

	public void setDrMarRelativeName(String drMarRelativeName) {
		this.drMarRelativeName = drMarRelativeName;
	}

	public String getDrMotherName() {
		return this.drMotherName;
	}

	public void setDrMotherName(String drMotherName) {
		this.drMotherName = drMotherName;
	}

	public Date getDrRegdate() {
		return this.drRegdate;
	}

	public void setDrRegdate(Date drRegdate) {
		this.drRegdate = drRegdate;
	}

	public String getDrRegno() {
		return this.drRegno;
	}

	public void setDrRegno(String drRegno) {
		this.drRegno = drRegno;
	}

	public String getDrRelativeName() {
		return this.drRelativeName;
	}

	public void setDrRelativeName(String drRelativeName) {
		this.drRelativeName = drRelativeName;
	}

	public String getDrRemarks() {
		return this.drRemarks;
	}

	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}

	public String getDrSex() {
		return this.drSex;
	}

	public void setDrSex(String drSex) {
		this.drSex = drSex;
	}

	public String getDrStatus() {
		return this.drStatus;
	}

	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
	}

	public String getDraftStatus() {
		return this.draftStatus;
	}

	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}

	public Long getHiId() {
		return this.hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public int getLangId() {
		return this.langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getLmoddate() {
		return this.lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public String getMcDeathManner() {
		return this.mcDeathManner;
	}

	public void setMcDeathManner(String mcDeathManner) {
		this.mcDeathManner = mcDeathManner;
	}

	public String getMcDeathcause() {
		return this.mcDeathcause;
	}

	public void setMcDeathcause(String mcDeathcause) {
		this.mcDeathcause = mcDeathcause;
	}

	public String getMcDelivery() {
		return this.mcDelivery;
	}

	public void setMcDelivery(String mcDelivery) {
		this.mcDelivery = mcDelivery;
	}

	public Long getMcInteronset() {
		return this.mcInteronset;
	}

	public void setMcInteronset(Long mcInteronset) {
		this.mcInteronset = mcInteronset;
	}

	public String getMcMdSuprName() {
		return this.mcMdSuprName;
	}

	public void setMcMdSuprName(String mcMdSuprName) {
		this.mcMdSuprName = mcMdSuprName;
	}

	public String getMcMdattndName() {
		return this.mcMdattndName;
	}

	public void setMcMdattndName(String mcMdattndName) {
		this.mcMdattndName = mcMdattndName;
	}

	public String getMcOthercond() {
		return this.mcOthercond;
	}

	public void setMcOthercond(String mcOthercond) {
		this.mcOthercond = mcOthercond;
	}

	public String getMcPregnAssoc() {
		return this.mcPregnAssoc;
	}

	public void setMcPregnAssoc(String mcPregnAssoc) {
		this.mcPregnAssoc = mcPregnAssoc;
	}

	public Date getMcVerifnDate() {
		return this.mcVerifnDate;
	}

	public void setMcVerifnDate(Date mcVerifnDate) {
		this.mcVerifnDate = mcVerifnDate;
	}

	public Long getMcWardId() {
		return this.mcWardId;
	}

	public void setMcWardId(Long mcWardId) {
		this.mcWardId = mcWardId;
	}

	public String getMedCert() {
		return this.medCert;
	}

	public void setMedCert(String medCert) {
		this.medCert = medCert;
	}

	public String getOtherReligion() {
		return this.otherReligion;
	}

	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}

	public String getPdUidF() {
		return this.pdUidF;
	}

	public void setPdUidF(String pdUidF) {
		this.pdUidF = pdUidF;
	}

	public String getPdUidM() {
		return this.pdUidM;
	}

	public void setPdUidM(String pdUidM) {
		this.pdUidM = pdUidM;
	}

	public double getSmDraftId() {
		return this.smDraftId;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getWardid() {
		return this.wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public Long getDrRId() {
		return drRId;
	}

	public void setDrRId(Long drRId) {
		this.drRId = drRId;
	}

	public Long getDrDeceasedage() {
		return drDeceasedage;
	}

	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
	}

	public Long getModeCpdId() {
		return modeCpdId;
	}

	public void setModeCpdId(Long modeCpdId) {
		this.modeCpdId = modeCpdId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setSmDraftId(Long smDraftId) {
		this.smDraftId = smDraftId;
	}

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public Long getDemandedCopies() {
		return demandedCopies;
	}

	public void setDemandedCopies(Long demandedCopies) {
		this.demandedCopies = demandedCopies;
	}

	public Long getIssuedCopies() {
		return issuedCopies;
	}

	public void setIssuedCopies(Long issuedCopies) {
		this.issuedCopies = issuedCopies;
	}

	
	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "tb_rts_deathregcert", "" };
	}

}
