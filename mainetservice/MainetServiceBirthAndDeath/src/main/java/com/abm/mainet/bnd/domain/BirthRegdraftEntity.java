package com.abm.mainet.bnd.domain;
import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * The persistent class for the tb_birthreg_draft database table.
 * 
 */
@Entity
@Table(name="tb_birthreg_draft")
@NamedQuery(name="BirthRegdraftEntity.findAll", query="SELECT t FROM BirthRegdraftEntity t")
public class BirthRegdraftEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="BR_DRAFT_ID", nullable=false)
	private Long brDraftId;
	
	@Column(nullable=false)
	private Long orgId;
	
	@Column(name="AD_COURT_NM", length=100)
	private String adCourtNm;

	@Temporal(TemporalType.DATE)
	@Column(name="AD_ORDER_DATE")
	private Date adOrderDate;

	@Column(name="AD_ORDER_NO", length=50)
	private String adOrderNo;

	@Column(name="AD_REMARKS", length=2000)
	private String adRemarks;

	@Column(name="APPLICANT_EMAIL", length=100)
	private String applicantEmail;

	@Column(name="APPLICANT_FNAME", length=100)
	private String applicantFname;

	@Column(name="APPLICANT_LNAME", length=100)
	private String applicantLname;

	@Column(name="APPLICANT_MNAME", length=100)
	private String applicantMname;

	@Column(name="APPLICANT_MOBILNO", length=20)
	private String applicantMobilno;

	@Column(name="APPLICANT_TITLE")
	private Long applicantTitle;

	@Column(name="BPL_NO", length=16)
	private String bplNo;

	@Column(name="BR_BIRTHADDR", length=200)
	private String brBirthaddr;

	@Column(name="BR_BIRTHADDR_MAR", length=500)
	private String brBirthaddrMar;

	@Column(name="BR_BIRTHPLACE", length=100)
	private String brBirthplace;

	@Column(name="BR_BIRTHPLACE_MAR", length=100)
	private String brBirthplaceMar;

	@Column(name="BR_BIRTHPLACE_TYPE", length=1)
	private String brBirthplaceType;

	@Column(name="BR_BIRTHWT", precision=10, scale=3)
	private Double brBirthwt;

	@Column(name="BR_CERT_NO", length=12)
	private String brCertNo;

	@Column(name="BR_CHILDNAME", length=200)
	private String brChildname;

	@Column(name="BR_CHILDNAME_MAR", length=500)
	private String brChildnameMar;

	@Temporal(TemporalType.DATE)
	@Column(name="BR_DOB")
	private Date brDob;

	@Column(name="BR_FDEATH_CAUSE", length=500)
	private String brFdeathCause;

	@Column(name="BR_ID")
	private Long brId;

	@Column(name="BR_INFORMANT_ADDR", length=200)
	private String brInformantAddr;

	@Column(name="BR_INFORMANT_ADDR_MAR", length=500)
	private String brInformantAddrMar;

	@Column(name="BR_INFORMANT_NAME", length=200)
	private String brInformantName;

	@Column(name="BR_INFORMANT_NAME_MAR", length=500)
	private String brInformantNameMar;

	@Column(name="BR_PREG_DURATN")
	private Long brPregDuratn;

	@Column(name="BR_REF_ID")
	private Long brRefId;

	@Temporal(TemporalType.DATE)
	@Column(name="BR_REGDATE", nullable=false)
	private Date brRegdate;

	@Column(name="BR_REGNO", length=12)
	private String brRegNo;

	@Column(name="BR_REMARKS", length=2000)
	private String brRemarks;

	@Column(name="BR_SEX", length=2)
	private String brSex;

	@Column(name="BR_STATUS", length=1)
	private String brStatus;

	@Column(name="CPD_ATTNTYPE_ID")
	private Long cpdAttntypeId;

	@Column(name="CPD_DELMETH_ID")
	private Long cpdDelmethId;

	@Column(name="CPD_DISTRICT_ID")
	private Long cpdDistrictId;

	@Column(name="CPD_F_EDUCN_ID")
	private Long cpdFEducnId;

	@Column(name="CPD_F_OCCU_ID")
	private Long cpdFOccuId;

	@Column(name="CPD_M_EDUCN_ID")
	private Long cpdMEducnId;

	@Column(name="CPD_M_OCCU_ID")
	private Long cpdMOccuId;

	@Column(name="CPD_NATIONLTY_ID")
	private Long cpdNationltyId;

	@Column(name="CPD_REF_TYPE_ID")
	private Long cpdRefTypeId;

	@Column(name="CPD_RELIGION_ID")
	private Long cpdReligionId;

	@Column(name="CPD_RES_ID")
	private Long cpdResId;

	@Column(name="CPD_STATE_ID")
	private Long cpdStateId;

	@Column(name="CPD_TALUKA_ID")
	private Long cpdTalukaId;

	@Column(name="DRAFT_STATUS", length=1)
	private String draftStatus;

	@Column(name="HI_ID")
	private Long hiId;

	@Column(name="LANG_ID", nullable=false)
	private int langId;

	@Column(name="LG_IP_MAC", length=100)
	private String lgIpMac;

	@Column(name="LG_IP_MAC_UPD", length=100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lmoddate;

	@Column(name="MODE_CPD_ID", precision=10)
	private Long modeCpdId;

	@Column(name="NO_COPIES")
	private Long noCopies;

	@Column(name="OTHER_RELIGION", length=500)
	private String otherReligion;

	@Column(name="PD_ADDRESS", length=200)
	private String pdAddress;

	@Column(name="PD_ADDRESS_FLAG", length=1)
	private String pdAddressFlag;

	@Column(name="PD_ADDRESS_MAR", length=500)
	private String pdAddressMar;

	@Column(name="PD_AGE_AT_BIRTH")
	private Long pdAgeAtBirth;

	@Column(name="PD_AGE_AT_MARRY")
	private Long pdAgeAtMarry;

	@Column(name="PD_FATHERNAME", length=200)
	private String pdFathername;

	@Column(name="PD_FATHERNAME_MAR", length=500)
	private String pdFathernameMar;

	@Column(name="PD_ID")
	private Long pdId;

	@Column(name="PD_LIVE_CHILDN")
	private Long pdLiveChildn;

	@Column(name="PD_MOTHER_ADD", length=500)
	private String pdMotherAdd;

	@Column(name="PD_MOTHERNAME", length=200)
	private String pdMothername;

	@Column(name="PD_MOTHERNAME_MAR", length=500)
	private String pdMothernameMar;

	@Column(name="PD_PARADDRESS", length=200)
	private String pdParaddress;

	@Column(name="PD_PARADDRESS_MAR", length=500)
	private String pdParaddressMar;

	@Column(name="PD_REG_UNIT_ID")
	private Long pdRegUnitId;

	@Column(name="PD_UID_F", length=12)
	private String pdUidF;

	@Column(name="PD_UID_M", length=12)
	private String pdUidM;

	@Column(name="SM_DRAFT_ID")
	private Long smDraftId;

	@Column(name="SM_SERVICE_ID")
	private Long smServiceId;

	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name="USER_ID", nullable=false)
	private Long userId;
	
	@Transient
	private Long applnId;

	private Long wardid;
	
	@Column(name = "BR_BIRTH_MARK",nullable = true)
	private String brBirthMark;
	
	@Column(name = "PD_MOTHER_ADD_MAR", length = 1000, nullable = true)
	private String motheraddressMar;

	public Long getBrDraftId() {
		return brDraftId;
	}

	public void setBrDraftId(Long brDraftId) {
		this.brDraftId = brDraftId;
	}

	public String getAdCourtNm() {
		return adCourtNm;
	}

	public void setAdCourtNm(String adCourtNm) {
		this.adCourtNm = adCourtNm;
	}

	public Date getAdOrderDate() {
		return adOrderDate;
	}

	public void setAdOrderDate(Date adOrderDate) {
		this.adOrderDate = adOrderDate;
	}

	public String getAdOrderNo() {
		return adOrderNo;
	}

	public void setAdOrderNo(String adOrderNo) {
		this.adOrderNo = adOrderNo;
	}

	public String getAdRemarks() {
		return adRemarks;
	}

	public void setAdRemarks(String adRemarks) {
		this.adRemarks = adRemarks;
	}

	public String getApplicantEmail() {
		return applicantEmail;
	}

	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}

	public String getApplicantFname() {
		return applicantFname;
	}

	public void setApplicantFname(String applicantFname) {
		this.applicantFname = applicantFname;
	}

	public String getApplicantLname() {
		return applicantLname;
	}

	public void setApplicantLname(String applicantLname) {
		this.applicantLname = applicantLname;
	}

	public String getApplicantMname() {
		return applicantMname;
	}

	public void setApplicantMname(String applicantMname) {
		this.applicantMname = applicantMname;
	}

	public String getApplicantMobilno() {
		return applicantMobilno;
	}

	public void setApplicantMobilno(String applicantMobilno) {
		this.applicantMobilno = applicantMobilno;
	}

	public Long getApplicantTitle() {
		return applicantTitle;
	}

	public void setApplicantTitle(Long applicantTitle) {
		this.applicantTitle = applicantTitle;
	}

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public String getBrBirthaddr() {
		return brBirthaddr;
	}

	public void setBrBirthaddr(String brBirthaddr) {
		this.brBirthaddr = brBirthaddr;
	}

	public String getBrBirthaddrMar() {
		return brBirthaddrMar;
	}

	public void setBrBirthaddrMar(String brBirthaddrMar) {
		this.brBirthaddrMar = brBirthaddrMar;
	}

	public String getBrBirthplace() {
		return brBirthplace;
	}

	public void setBrBirthplace(String brBirthplace) {
		this.brBirthplace = brBirthplace;
	}

	public String getBrBirthplaceMar() {
		return brBirthplaceMar;
	}

	public void setBrBirthplaceMar(String brBirthplaceMar) {
		this.brBirthplaceMar = brBirthplaceMar;
	}

	public String getBrBirthplaceType() {
		return brBirthplaceType;
	}

	public void setBrBirthplaceType(String brBirthplaceType) {
		this.brBirthplaceType = brBirthplaceType;
	}

	public Double getBrBirthwt() {
		return brBirthwt;
	}

	public void setBrBirthwt(Double brBirthwt) {
		this.brBirthwt = brBirthwt;
	}

	public String getBrCertNo() {
		return brCertNo;
	}

	public void setBrCertNo(String brCertNo) {
		this.brCertNo = brCertNo;
	}

	public String getBrChildname() {
		return brChildname;
	}

	public void setBrChildname(String brChildname) {
		this.brChildname = brChildname;
	}

	public String getBrChildnameMar() {
		return brChildnameMar;
	}

	public void setBrChildnameMar(String brChildnameMar) {
		this.brChildnameMar = brChildnameMar;
	}

	public Date getBrDob() {
		return brDob;
	}

	public void setBrDob(Date brDob) {
		this.brDob = brDob;
	}

	public String getBrFdeathCause() {
		return brFdeathCause;
	}

	public void setBrFdeathCause(String brFdeathCause) {
		this.brFdeathCause = brFdeathCause;
	}

	public Long getBrId() {
		return brId;
	}

	public void setBrId(Long brId) {
		this.brId = brId;
	}

	public String getBrInformantAddr() {
		return brInformantAddr;
	}

	public void setBrInformantAddr(String brInformantAddr) {
		this.brInformantAddr = brInformantAddr;
	}

	public String getBrInformantAddrMar() {
		return brInformantAddrMar;
	}

	public void setBrInformantAddrMar(String brInformantAddrMar) {
		this.brInformantAddrMar = brInformantAddrMar;
	}

	public String getBrInformantName() {
		return brInformantName;
	}

	public void setBrInformantName(String brInformantName) {
		this.brInformantName = brInformantName;
	}

	public String getBrInformantNameMar() {
		return brInformantNameMar;
	}

	public void setBrInformantNameMar(String brInformantNameMar) {
		this.brInformantNameMar = brInformantNameMar;
	}

	public Long getBrPregDuratn() {
		return brPregDuratn;
	}

	public void setBrPregDuratn(Long brPregDuratn) {
		this.brPregDuratn = brPregDuratn;
	}

	public Long getBrRefId() {
		return brRefId;
	}

	public void setBrRefId(Long brRefId) {
		this.brRefId = brRefId;
	}

	public Date getBrRegdate() {
		return brRegdate;
	}

	public void setBrRegdate(Date brRegdate) {
		this.brRegdate = brRegdate;
	}

	public String getBrRegNo() {
		return brRegNo;
	}

	public void setBrRegNo(String brRegNo) {
		this.brRegNo = brRegNo;
	}

	public String getBrRemarks() {
		return brRemarks;
	}

	public void setBrRemarks(String brRemarks) {
		this.brRemarks = brRemarks;
	}

	public String getBrSex() {
		return brSex;
	}

	public void setBrSex(String brSex) {
		this.brSex = brSex;
	}

	public String getBrStatus() {
		return brStatus;
	}

	public void setBrStatus(String brStatus) {
		this.brStatus = brStatus;
	}

	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}

	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}

	public Long getCpdDelmethId() {
		return cpdDelmethId;
	}

	public void setCpdDelmethId(Long cpdDelmethId) {
		this.cpdDelmethId = cpdDelmethId;
	}

	public Long getCpdDistrictId() {
		return cpdDistrictId;
	}

	public void setCpdDistrictId(Long cpdDistrictId) {
		this.cpdDistrictId = cpdDistrictId;
	}

	public Long getCpdFEducnId() {
		return cpdFEducnId;
	}

	public void setCpdFEducnId(Long cpdFEducnId) {
		this.cpdFEducnId = cpdFEducnId;
	}

	public Long getCpdFOccuId() {
		return cpdFOccuId;
	}

	public void setCpdFOccuId(Long cpdFOccuId) {
		this.cpdFOccuId = cpdFOccuId;
	}

	public Long getCpdMEducnId() {
		return cpdMEducnId;
	}

	public void setCpdMEducnId(Long cpdMEducnId) {
		this.cpdMEducnId = cpdMEducnId;
	}

	public Long getCpdMOccuId() {
		return cpdMOccuId;
	}

	public void setCpdMOccuId(Long cpdMOccuId) {
		this.cpdMOccuId = cpdMOccuId;
	}

	public Long getCpdNationltyId() {
		return cpdNationltyId;
	}

	public void setCpdNationltyId(Long cpdNationltyId) {
		this.cpdNationltyId = cpdNationltyId;
	}

	public Long getCpdRefTypeId() {
		return cpdRefTypeId;
	}

	public void setCpdRefTypeId(Long cpdRefTypeId) {
		this.cpdRefTypeId = cpdRefTypeId;
	}

	public Long getCpdReligionId() {
		return cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}

	public Long getCpdResId() {
		return cpdResId;
	}

	public void setCpdResId(Long cpdResId) {
		this.cpdResId = cpdResId;
	}

	public Long getCpdStateId() {
		return cpdStateId;
	}

	public void setCpdStateId(Long cpdStateId) {
		this.cpdStateId = cpdStateId;
	}

	public Long getCpdTalukaId() {
		return cpdTalukaId;
	}

	public void setCpdTalukaId(Long cpdTalukaId) {
		this.cpdTalukaId = cpdTalukaId;
	}

	public String getDraftStatus() {
		return draftStatus;
	}

	public void setDraftStatus(String draftStatus) {
		this.draftStatus = draftStatus;
	}

	public Long getHiId() {
		return hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
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

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getModeCpdId() {
		return modeCpdId;
	}

	public void setModeCpdId(Long modeCpdId) {
		this.modeCpdId = modeCpdId;
	}

	public Long getNoCopies() {
		return noCopies;
	}

	public void setNoCopies(Long noCopies) {
		this.noCopies = noCopies;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOtherReligion() {
		return otherReligion;
	}

	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}

	public String getPdAddress() {
		return pdAddress;
	}

	public void setPdAddress(String pdAddress) {
		this.pdAddress = pdAddress;
	}

	public String getPdAddressFlag() {
		return pdAddressFlag;
	}

	public void setPdAddressFlag(String pdAddressFlag) {
		this.pdAddressFlag = pdAddressFlag;
	}

	public String getPdAddressMar() {
		return pdAddressMar;
	}

	public void setPdAddressMar(String pdAddressMar) {
		this.pdAddressMar = pdAddressMar;
	}

	public Long getPdAgeAtBirth() {
		return pdAgeAtBirth;
	}

	public void setPdAgeAtBirth(Long pdAgeAtBirth) {
		this.pdAgeAtBirth = pdAgeAtBirth;
	}

	public Long getPdAgeAtMarry() {
		return pdAgeAtMarry;
	}

	public void setPdAgeAtMarry(Long pdAgeAtMarry) {
		this.pdAgeAtMarry = pdAgeAtMarry;
	}

	public String getPdFathername() {
		return pdFathername;
	}

	public void setPdFathername(String pdFathername) {
		this.pdFathername = pdFathername;
	}

	public String getPdFathernameMar() {
		return pdFathernameMar;
	}

	public void setPdFathernameMar(String pdFathernameMar) {
		this.pdFathernameMar = pdFathernameMar;
	}

	public Long getPdId() {
		return pdId;
	}

	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}

	public Long getPdLiveChildn() {
		return pdLiveChildn;
	}

	public void setPdLiveChildn(Long pdLiveChildn) {
		this.pdLiveChildn = pdLiveChildn;
	}

	public String getPdMotherAdd() {
		return pdMotherAdd;
	}

	public void setPdMotherAdd(String pdMotherAdd) {
		this.pdMotherAdd = pdMotherAdd;
	}

	public String getPdMothername() {
		return pdMothername;
	}

	public void setPdMothername(String pdMothername) {
		this.pdMothername = pdMothername;
	}

	public String getPdMothernameMar() {
		return pdMothernameMar;
	}

	public void setPdMothernameMar(String pdMothernameMar) {
		this.pdMothernameMar = pdMothernameMar;
	}

	public String getPdParaddress() {
		return pdParaddress;
	}

	public void setPdParaddress(String pdParaddress) {
		this.pdParaddress = pdParaddress;
	}

	public String getPdParaddressMar() {
		return pdParaddressMar;
	}

	public void setPdParaddressMar(String pdParaddressMar) {
		this.pdParaddressMar = pdParaddressMar;
	}

	public Long getPdRegUnitId() {
		return pdRegUnitId;
	}

	public void setPdRegUnitId(Long pdRegUnitId) {
		this.pdRegUnitId = pdRegUnitId;
	}

	public String getPdUidF() {
		return pdUidF;
	}

	public void setPdUidF(String pdUidF) {
		this.pdUidF = pdUidF;
	}

	public String getPdUidM() {
		return pdUidM;
	}

	public void setPdUidM(String pdUidM) {
		this.pdUidM = pdUidM;
	}

	public Long getSmDraftId() {
		return smDraftId;
	}

	public void setSmDraftId(Long smDraftId) {
		this.smDraftId = smDraftId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getWardid() {
		return wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
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

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}
	
	public String getBrBirthMark() {
		return brBirthMark;
	}
	public void setBrBirthMark(String brBirthMark) {
		this.brBirthMark = brBirthMark;
	}
	public String getMotheraddressMar() {
		return motheraddressMar;
	}

	public void setMotheraddressMar(String motheraddressMar) {
		this.motheraddressMar = motheraddressMar;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "tb_birthreg_draft", "BR_DRAFT_ID" };
	}

	
}