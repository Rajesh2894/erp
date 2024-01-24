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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BIRTHREG")
public class BirthRegistrationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BR_ID", nullable = false, precision = 12)
	private Long brId;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="brId")
	@JoinColumn(name = "BR_ID")
	private ParentDetail parentDetail = new ParentDetail();

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "WARDID", precision = 12, scale = 0, nullable = true)
	private Long wardid;

	@Column(name = "BR_REGNO", length = 12, nullable = true)
	private String brRegNo;

	@Column(name = "BR_REF_ID", nullable = false, updatable = false)

	private Long brRefId;

	@Temporal(TemporalType.DATE)
	@Column(name = "BR_REGDATE", nullable = false)
	private Date brRegDate;

	@Column(name = "BR_CHILDNAME", length = 200, nullable = true)
	private String brChildName;

	@Temporal(TemporalType.DATE)
	@Column(name = "BR_DOB", nullable = true)
	private Date brDob;

	@Column(name = "BR_SEX", length = 1, nullable = false)
	private String brSex;

	@Column(name = "BR_BIRTHPLACE", length = 100, nullable = true)
	private String brBirthPlace;

	@Column(name = "BR_BIRTHADDR", length = 400, nullable = true)
	private String brBirthAddr;

	@Column(name = "BR_INFORMANT_NAME", length = 200, nullable = true)
	private String brInformantName;

	@Column(name = "BR_INFORMANT_ADDR", length = 400, nullable = true)
	private String brInformantAddr;

	@Column(name = "BR_BIRTHWT", precision = 5, scale = 3, nullable = true)
	private Double BrBirthWt;

	@Column(name = "BR_ADOPTION_FLG", nullable = true)
	private String brAdopFlg;

	@Column(name = "BR_ADOPN_DATE", nullable = true)
	private Date brAdopDate;

	@Column(name = "BR_CORRECTION_FLG", length = 1, nullable = true)
	private String brCorrectionFlg;

	@Column(name = "BR_CORRN_DATE", nullable = true)
	private Date brCorrnDate;

	@Column(name = "CPD_REF_TYPE_ID", nullable = true, updatable = false, length = 2)
	private Long cpdRefTypeId;

	@Column(name = "BR_PREG_DURATN", precision = 3, scale = 0, nullable = true)
	private Long brPregDuratn;

	@Column(name = "BR_FDEATH_CAUSE", length = 1000, nullable = true)
	private String brFdeathCause;

	@Column(name = "CPD_ATTNTYPE_ID", nullable = false, updatable = false)
	private Long cpdAttntypeId;

	@Column(name = "CPD_DELMETH_ID", nullable = false, updatable = false)
	private Long cpdDelMethId;

	@Column(name = "BR_CERT_NO", length = 12, nullable = true)
	private String brCertNo;

	@Column(name = "HI_ID", precision = 12, scale = 0, nullable = true)
	private Long hiId;

	@Column(name = "BR_REMARKS", length = 4000, nullable = true)
	private String brRemarks;

	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	private int langId;

	@Column(name = "LMODDATE", nullable = true)
	private Date lmodDate;

	@Column(name = "BR_STATUS", nullable = true, length = 1)
	private String brStatus;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "BR_SUPPNO", length = 30, nullable = true)
	private String brSuppNo;

	@Column(name = "BR_TIME", length = 30, nullable = true)
	private String brTime;

	@Column(name = "BR_SUPPDATE", nullable = true)
	private Date brSuppdate;

	@Column(name = "BR_SUPPTIME", length = 200, nullable = true)
	private String brSuppTime;

	@Column(name = "BR_BIRTH_MARK", length = 400, nullable = true)
	private String brBirthMark;

	@Column(name = "BR_CHILDNAME_MAR", length = 1000, nullable = true)
	private String brChildNameMar;

	@Column(name = "BR_BIRTHPLACE_MAR", length = 200, nullable = true)
	private String brBirthPlaceMar;

	@Column(name = "BR_BIRTHADDR_MAR", length = 1000, nullable = true)
	private String brBirthAddrMar;

	@Column(name = "BR_INFORMANT_NAME_MAR", length = 400, nullable = true)
	private String brInformantNameMar;

	@Column(name = "BR_INFORMANT_ADDR_MAR", length = 1000, nullable = true)
	private String brInformantAddrMar;

	@Column(name = "BR_STILLBIRTH_FLG", length = 1, nullable = true)
	private String brStillBirthFlg;

	@Column(name = "BR_STILLBIRTH_DATE", nullable = true)
	private Date brStillBirthDate;

	@Column(name = "BR_BIRTHPLACE_TYPE", length = 100, nullable = true)
	private String brBirthPlaceType;

	@Column(name = "BR_HOSPITAL", length = 2, nullable = true)
	private String brHospital;

	@Column(name = "H_R_ID", length = 100, nullable = true)
	private String hRId;

	@Column(name = "FILE_NAME", length = 100, nullable = true)
	private String fileName;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "BR_FLAG", length = 1, nullable = true)
	private String brFlag;

	@Column(name = "BR_ORPHAN_REG", length = 1, nullable = true)
	private String brOrphanReg;

	@Column(name = "BR_POLICE_STATION", length = 600, nullable = true)
	private String brPoliceStation;

	@Column(name = "BR_ADDRESS", length = 1000, nullable = true)
	private String brAddress;

	@Column(name = "BR_FIR_NUMBER", length = 200, nullable = true)
	private String brFirNumber;

	@Column(name = "COD_USER_WARDID", precision = 12, scale = 0, nullable = true)
	private Long codUserWardId;

	@Column(name = "UPLD_ID", precision = 15, scale = 0, nullable = true)
	private Long upldId;

	@Column(name = "BR_MANUAL_CERTNO", precision = 3, scale = 0, nullable = true)
	private Long brManualCertNo;

	@Column(name = "BCR_FLAG", length = 1, nullable = true)
	private String bcrFlag;

	@Column(name = "HR_REG", length = 1, nullable = true)
	private String hrReg;

	@Column(name = "AUTH_BY", precision = 0, scale = 0, nullable = true)
	private Long authBy;

	@Column(name = "AUTH_DATE", nullable = true)
	private Date authDate;

	@Column(name = "AUTH_FLAG", length = 1, nullable = true)
	private String authFlag;

	@Column(name = "AUTH_REMARK", length = 1000, nullable = true)
	private String authRemark;

	@Column(name = "PGFLAG", nullable = true, length = 1)
	private String pgFlg;

	@Column(name = "CERT_NO_COPIES", precision = 3, scale = 0, nullable = true)
	private Long noOfCopies;

	@Column(name = "UNAUTH_R_FLG", nullable = true, length = 1)
	private String unAuthRflg;

	@Column(name = "REG_APL_DATE", nullable = true, updatable = false)
	private Date regAplDate;

	@Transient
	private Long applnId;
	
	@Column(name = "WF_STATUS", nullable = false)
	private String birthWFStatus;

	public Long getBrId() {
		return brId;
	}

	public void setBrId(Long brId) {
		this.brId = brId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getWardid() {
		return wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public String getBrRegNo() {
		return brRegNo;
	}

	public void setBrRegNo(String brRegNo) {
		this.brRegNo = brRegNo;
	}

	public Long getBrRefId() {
		return brRefId;
	}

	public void setBrRefId(Long brRefId) {
		this.brRefId = brRefId;
	}

	public Date getBrRegDate() {
		return brRegDate;
	}

	public void setBrRegDate(Date brRegDate) {
		this.brRegDate = brRegDate;
	}

	public String getBrChildName() {
		return brChildName;
	}

	public void setBrChildName(String brChildName) {
		this.brChildName = brChildName;
	}

	public Date getBrDob() {
		return brDob;
	}

	public void setBrDob(Date brDob) {
		this.brDob = brDob;
	}

	public String getBrSex() {
		return brSex;
	}

	public void setBrSex(String brSex) {
		this.brSex = brSex;
	}

	public String getBrBirthPlace() {
		return brBirthPlace;
	}

	public void setBrBirthPlace(String brBirthPlace) {
		this.brBirthPlace = brBirthPlace;
	}

	public String getBrBirthAddr() {
		return brBirthAddr;
	}

	public void setBrBirthAddr(String brBirthAddr) {
		this.brBirthAddr = brBirthAddr;
	}

	public String getBrInformantName() {
		return brInformantName;
	}

	public void setBrInformantName(String brInformantName) {
		this.brInformantName = brInformantName;
	}

	public String getBrInformantAddr() {
		return brInformantAddr;
	}

	public void setBrInformantAddr(String brInformantAddr) {
		this.brInformantAddr = brInformantAddr;
	}

	public Double getBrBirthWt() {
		return BrBirthWt;
	}

	public void setBrBirthWt(Double brBirthWt) {
		BrBirthWt = brBirthWt;
	}

	public String getBrAdopFlg() {
		return brAdopFlg;
	}

	public void setBrAdopFlg(String brAdopFlg) {
		this.brAdopFlg = brAdopFlg;
	}

	public Date getBrAdopDate() {
		return brAdopDate;
	}

	public void setBrAdopDate(Date brAdopDate) {
		this.brAdopDate = brAdopDate;
	}

	public String getBrCorrectionFlg() {
		return brCorrectionFlg;
	}

	public void setBrCorrectionFlg(String brCorrectionFlg) {
		this.brCorrectionFlg = brCorrectionFlg;
	}

	public Date getBrCorrnDate() {
		return brCorrnDate;
	}

	public void setBrCorrnDate(Date brCorrnDate) {
		this.brCorrnDate = brCorrnDate;
	}

	public Long getCpdRefTypeId() {
		return cpdRefTypeId;
	}

	public void setCpdRefTypeId(Long cpdRefTypeId) {
		this.cpdRefTypeId = cpdRefTypeId;
	}

	public Long getBrPregDuratn() {
		return brPregDuratn;
	}

	public void setBrPregDuratn(Long brPregDuratn) {
		this.brPregDuratn = brPregDuratn;
	}

	public String getBrFdeathCause() {
		return brFdeathCause;
	}

	public void setBrFdeathCause(String brFdeathCause) {
		this.brFdeathCause = brFdeathCause;
	}

	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}

	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}

	public Long getCpdDelMethId() {
		return cpdDelMethId;
	}

	public void setCpdDelMethId(Long cpdDelMethId) {
		this.cpdDelMethId = cpdDelMethId;
	}

	public String getBrCertNo() {
		return brCertNo;
	}

	public void setBrCertNo(String brCertNo) {
		this.brCertNo = brCertNo;
	}

	public Long getHiId() {
		return hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public String getBrRemarks() {
		return brRemarks;
	}

	public void setBrRemarks(String brRemarks) {
		this.brRemarks = brRemarks;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public String getBrStatus() {
		return brStatus;
	}

	public void setBrStatus(String brStatus) {
		this.brStatus = brStatus;
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

	public String getBrSuppNo() {
		return brSuppNo;
	}

	public void setBrSuppNo(String brSuppNo) {
		this.brSuppNo = brSuppNo;
	}

	public String getBrTime() {
		return brTime;
	}

	public void setBrTime(String brTime) {
		this.brTime = brTime;
	}

	public Date getBrSuppdate() {
		return brSuppdate;
	}

	public void setBrSuppdate(Date brSuppdate) {
		this.brSuppdate = brSuppdate;
	}

	public String getBrSuppTime() {
		return brSuppTime;
	}

	public void setBrSuppTime(String brSuppTime) {
		this.brSuppTime = brSuppTime;
	}

	public String getBrBirthMark() {
		return brBirthMark;
	}

	public void setBrBirthMark(String brBirthMark) {
		this.brBirthMark = brBirthMark;
	}

	public String getBrChildNameMar() {
		return brChildNameMar;
	}

	public void setBrChildNameMar(String brChildNameMar) {
		this.brChildNameMar = brChildNameMar;
	}

	public String getBrBirthPlaceMar() {
		return brBirthPlaceMar;
	}

	public void setBrBirthPlaceMar(String brBirthPlaceMar) {
		this.brBirthPlaceMar = brBirthPlaceMar;
	}

	public String getBrBirthAddrMar() {
		return brBirthAddrMar;
	}

	public void setBrBirthAddrMar(String brBirthAddrMar) {
		this.brBirthAddrMar = brBirthAddrMar;
	}

	public String getBrInformantNameMar() {
		return brInformantNameMar;
	}

	public void setBrInformantNameMar(String brInformantNameMar) {
		this.brInformantNameMar = brInformantNameMar;
	}

	public String getBrInformantAddrMar() {
		return brInformantAddrMar;
	}

	public void setBrInformantAddrMar(String brInformantAddrMar) {
		this.brInformantAddrMar = brInformantAddrMar;
	}

	public String getBrStillBirthFlg() {
		return brStillBirthFlg;
	}

	public void setBrStillBirthFlg(String brStillBirthFlg) {
		this.brStillBirthFlg = brStillBirthFlg;
	}

	public Date getBrStillBirthDate() {
		return brStillBirthDate;
	}

	public void setBrStillBirthDate(Date brStillBirthDate) {
		this.brStillBirthDate = brStillBirthDate;
	}

	public String getBrBirthPlaceType() {
		return brBirthPlaceType;
	}

	public void setBrBirthPlaceType(String brBirthPlaceType) {
		this.brBirthPlaceType = brBirthPlaceType;
	}

	public String getBrHospital() {
		return brHospital;
	}

	public void setBrHospital(String brHospital) {
		this.brHospital = brHospital;
	}

	public String gethRId() {
		return hRId;
	}

	public void sethRId(String hRId) {
		this.hRId = hRId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getBrFlag() {
		return brFlag;
	}

	public void setBrFlag(String brFlag) {
		this.brFlag = brFlag;
	}

	public String getBrOrphanReg() {
		return brOrphanReg;
	}

	public void setBrOrphanReg(String brOrphanReg) {
		this.brOrphanReg = brOrphanReg;
	}

	public String getBrPoliceStation() {
		return brPoliceStation;
	}

	public void setBrPoliceStation(String brPoliceStation) {
		this.brPoliceStation = brPoliceStation;
	}

	public String getBrAddress() {
		return brAddress;
	}

	public void setBrAddress(String brAddress) {
		this.brAddress = brAddress;
	}

	public String getBrFirNumber() {
		return brFirNumber;
	}

	public void setBrFirNumber(String brFirNumber) {
		this.brFirNumber = brFirNumber;
	}

	public Long getCodUserWardId() {
		return codUserWardId;
	}

	public void setCodUserWardId(Long codUserWardId) {
		this.codUserWardId = codUserWardId;
	}

	public Long getUpldId() {
		return upldId;
	}

	public void setUpldId(Long upldId) {
		this.upldId = upldId;
	}

	public Long getBrManualCertNo() {
		return brManualCertNo;
	}

	public void setBrManualCertNo(Long brManualCertNo) {
		this.brManualCertNo = brManualCertNo;
	}

	public String getBcrFlag() {
		return bcrFlag;
	}

	public void setBcrFlag(String bcrFlag) {
		this.bcrFlag = bcrFlag;
	}

	public String getHrReg() {
		return hrReg;
	}

	public void setHrReg(String hrReg) {
		this.hrReg = hrReg;
	}

	public Long getAuthBy() {
		return authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getAuthFlag() {
		return authFlag;
	}

	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public String getPgFlg() {
		return pgFlg;
	}

	public void setPgFlg(String pgFlg) {
		this.pgFlg = pgFlg;
	}

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getUnAuthRflg() {
		return unAuthRflg;
	}

	public void setUnAuthRflg(String unAuthRflg) {
		this.unAuthRflg = unAuthRflg;
	}

	public Date getRegAplDate() {
		return regAplDate;
	}

	public void setRegAplDate(Date regAplDate) {
		this.regAplDate = regAplDate;
	}

	public ParentDetail getParentDetail() {
		return parentDetail;
	}

	public void setParentDetail(ParentDetail parentDetail) {
		this.parentDetail = parentDetail;
	}

	public String getBirthWFStatus() {
		return birthWFStatus;
	}

	public void setBirthWFStatus(String birthWFStatus) {
		this.birthWFStatus = birthWFStatus;
	}
       public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "TB_BIRTHREG", "BR_ID" };
	}

}
