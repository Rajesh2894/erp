package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_deathreg database table.
 * 
 */
@Entity
@Table(name = "tb_deathreg")
@NamedQuery(name = "TbDeathreg.findAll", query = "SELECT t FROM TbDeathreg t")
public class TbDeathreg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DR_ID", nullable = false)
	private Long drId;

	@Column(nullable = false)
	private Long orgId;

	@Column(name = "AUTH_BY")
	private Long authBy;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="drId")
	@JoinColumn(name = "DR_ID")
	private MedicalMaster medicalMaster = new MedicalMaster();

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="drId")
	@JoinColumn(name = "DR_ID")
	private DeceasedMaster deceasedMaster = new DeceasedMaster();

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUTH_DATE")
	private Date authDate;

	@Column(name = "AUTH_FLAG", length = 1)
	private String authFlag;

	@Column(name = "AUTH_REMARK", length = 500)
	private String authRemark;

	@Column(name = "BCR_FLAG", length = 1)
	private String bcrFlag;

	@Column(name = "BPL_NO", length = 16)
	private String bplNo;

	@Column(name = "CE_ADDR", length = 200, nullable = true)
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

	@Column(name = "CERT_NO_COPIES")
	private Long certNoCopies;

	@Column(name = "COD_USER_WARDID")
	private Long codUserWardid;

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

	@Column(name = "DC_UID", length = 12)
	private String dcUid;

	@Column(name = "DR_ADDRESS", length = 500)
	private String drAddress;

	@Column(name = "DR_CERT_NO", length = 12)
	private String drCertNo;

	@Column(name = "DR_CORRECTION_FLG", length = 1)
	private String drCorrectionFlg;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DR_CORRN_DATE")
	private Date drCorrnDate;

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
	@Column(name = "DR_DOD", nullable = false)
	private Date drDod;

	@Column(name = "DR_FIR_NUMBER", length = 100)
	private String drFirNumber;

	@Column(name = "DR_FLAG", length = 1)
	private String drFlag;

	@Column(name = "DR_INFORMANT_ADDR", length = 200)
	private String drInformantAddr;

	@Column(name = "DR_INFORMANT_NAME", length = 200)
	private String drInformantName;

	@Column(name = "DR_MANUAL_CERTNO")
	private Long drManualCertno;

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

	@Column(name = "DR_POLICE_STATION", length = 300)
	private String drPoliceStation;

	@Temporal(TemporalType.DATE)
	@Column(name = "DR_REGDATE", nullable = false)
	private Date drRegdate;

	@Column(name = "DR_REGNO", length = 12)
	private String drRegno;

	@Column(name = "DR_REL_PREG", length = 1)
	private String drRelPreg;

	@Column(name = "DR_RELATIVE_NAME", length = 200)
	private String drRelativeName;

	@Column(name = "DR_REMARKS", length = 2000)
	private String drRemarks;

	@Column(name = "DR_SEX", nullable = false)
	private String drSex;

	@Column(name = "DR_STATUS", length = 1)
	private String drStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DR_SUPPDATE")
	private Date drSuppdate;

	@Column(name = "DR_SUPPNO", length = 15)
	private String drSuppno;

	@Column(name = "DR_SUPPTIME", length = 15)
	private String drSupptime;

	@Column(name = "DR_TIME", length = 15)
	private String drTime;

	@Column(name = "DR_UDEATH_REG", length = 1)
	private String drUdeathReg;

	@Column(name = "FILE_NAME", length = 50)
	private String fileName;

	@Column(name = "H_R_ID", length = 50)
	private String hRId;

	@Column(name = "HI_ID")
	private Long hiId;

	@Column(name = "HR_REG", length = 1)
	private String hrReg;

	@Column(name = "LANG_ID", nullable = false)
	private int langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lmoddate")
	private Date lmoddate;

	@Column(name = "OTHER_RELIGION", length = 500)
	private String otherReligion;

	@Column(name = "PD_UID_F", length = 12)
	private String pdUidF;

	@Column(name = "PD_UID_M", length = 12)
	private String pdUidM;

	@Column(length = 1)
	private String pgflag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_APL_DATE", nullable = false)
	private Date regAplDate;

	@Column(name = "UNAUTH_R_FLG", length = 1)
	private String unauthRFlg;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPLD_ID")
	private Long upldId;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "WARDID")
	private Long wardid;

	@Transient
	private Long applnId;
	
	@Column(name = "WF_STATUS", nullable = false)
	private String DeathWFStatus;

	
	public String getDeathWFStatus() {
		return DeathWFStatus;
	}

	public void setDeathWFStatus(String deathWFStatus) {
		DeathWFStatus = deathWFStatus;
	}

	public Date getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getAuthFlag() {
		return this.authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getAuthRemark() {
		return this.authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public String getBcrFlag() {
		return this.bcrFlag;
	}

	public void setBcrFlag(String bcrFlag) {
		this.bcrFlag = bcrFlag;
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

	public Long getCertNoCopies() {
		return this.certNoCopies;
	}

	public void setCertNoCopies(Long certNoCopies) {
		this.certNoCopies = certNoCopies;
	}

	public Long getCodUserWardid() {
		return this.codUserWardid;
	}

	public void setCodUserWardid(Long codUserWardid) {
		this.codUserWardid = codUserWardid;
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

	public Long getAuthBy() {
		return authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getDcUid() {
		return this.dcUid;
	}

	public void setDcUid(String dcUid) {
		this.dcUid = dcUid;
	}

	public String getDrAddress() {
		return this.drAddress;
	}

	public void setDrAddress(String drAddress) {
		this.drAddress = drAddress;
	}

	public String getDrCertNo() {
		return this.drCertNo;
	}

	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
	}

	public String getDrCorrectionFlg() {
		return this.drCorrectionFlg;
	}

	public void setDrCorrectionFlg(String drCorrectionFlg) {
		this.drCorrectionFlg = drCorrectionFlg;
	}

	public Date getDrCorrnDate() {
		return this.drCorrnDate;
	}

	public void setDrCorrnDate(Date drCorrnDate) {
		this.drCorrnDate = drCorrnDate;
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

	public Long getDrDeceasedage() {
		return this.drDeceasedage;
	}

	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
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

	public String getDrFirNumber() {
		return this.drFirNumber;
	}

	public void setDrFirNumber(String drFirNumber) {
		this.drFirNumber = drFirNumber;
	}

	public String getDrFlag() {
		return this.drFlag;
	}

	public void setDrFlag(String drFlag) {
		this.drFlag = drFlag;
	}

	public Long getDrId() {
		return this.drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
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

	
	public Long getDrManualCertno() {
		return drManualCertno;
	}

	public void setDrManualCertno(Long drManualCertno) {
		this.drManualCertno = drManualCertno;
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

	public String getDrPoliceStation() {
		return this.drPoliceStation;
	}

	public void setDrPoliceStation(String drPoliceStation) {
		this.drPoliceStation = drPoliceStation;
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

	public String getDrRelPreg() {
		return this.drRelPreg;
	}

	public void setDrRelPreg(String drRelPreg) {
		this.drRelPreg = drRelPreg;
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

	public Date getDrSuppdate() {
		return this.drSuppdate;
	}

	public void setDrSuppdate(Date drSuppdate) {
		this.drSuppdate = drSuppdate;
	}

	public String getDrSuppno() {
		return this.drSuppno;
	}

	public void setDrSuppno(String drSuppno) {
		this.drSuppno = drSuppno;
	}

	public String getDrSupptime() {
		return this.drSupptime;
	}

	public void setDrSupptime(String drSupptime) {
		this.drSupptime = drSupptime;
	}

	public String getDrTime() {
		return this.drTime;
	}

	public void setDrTime(String drTime) {
		this.drTime = drTime;
	}

	public String getDrUdeathReg() {
		return this.drUdeathReg;
	}

	public void setDrUdeathReg(String drUdeathReg) {
		this.drUdeathReg = drUdeathReg;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getHRId() {
		return this.hRId;
	}

	public void setHRId(String hRId) {
		this.hRId = hRId;
	}

	public Long getHiId() {
		return this.hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public String getHrReg() {
		return this.hrReg;
	}

	public void setHrReg(String hrReg) {
		this.hrReg = hrReg;
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

	public String gethRId() {
		return hRId;
	}

	public void sethRId(String hRId) {
		this.hRId = hRId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public String getPgflag() {
		return this.pgflag;
	}

	public void setPgflag(String pgflag) {
		this.pgflag = pgflag;
	}

	public Date getRegAplDate() {
		return this.regAplDate;
	}

	public void setRegAplDate(Date regAplDate) {
		this.regAplDate = regAplDate;
	}

	public String getUnauthRFlg() {
		return this.unauthRFlg;
	}

	public void setUnauthRFlg(String unauthRFlg) {
		this.unauthRFlg = unauthRFlg;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpldId() {
		return this.upldId;
	}

	public void setUpldId(Long upldId) {
		this.upldId = upldId;
	}

	public Long getWardid() {
		return this.wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public MedicalMaster getMedicalMaster() {
		return medicalMaster;
	}

	public void setMedicalMaster(MedicalMaster medicalMaster) {
		this.medicalMaster = medicalMaster;
	}

	public DeceasedMaster getDeceasedMaster() {
		return deceasedMaster;
	}

	public void setDeceasedMaster(DeceasedMaster deceasedMaster) {
		this.deceasedMaster = deceasedMaster;
	}



	public String[] getPkValues() {
		return new String[] { "HD", "tb_deathreg", "DR_ID" };
	}

}