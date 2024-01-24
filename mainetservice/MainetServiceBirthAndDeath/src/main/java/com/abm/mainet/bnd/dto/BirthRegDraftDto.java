package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

public class BirthRegDraftDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Long brDraftId;
	private Long orgId;
	private String adCourtNm;
	private Date adOrderDate;
	private String adOrderNo;
	private String adRemarks;
	private String applicantEmail;
	private String applicantFname;
	private String applicantLname;
	private String applicantMname;
	private String applicantMobilno;
	private Long applicantTitle;
	private String bplNo;
	private String brBirthaddr;
	private String brBirthaddrMar;
	private String brBirthplace;
	private String brBirthplaceMar;
	private String brBirthplaceType;
	private Double brBirthwt;
	private String brCertNo;
	private String brChildname;
	private String brChildnameMar;
	private Date brDob;
	private Long brId;
	private String brFdeathCause;
	private String brInformantAddr;
	private String brInformantAddrMar;
	private String brInformantName;
	private String brInformantNameMar;
	private Long brPregDuratn;
	private Long brRefId;
	private Date brRegdate;
	private String brRegNo;
	private String brRemarks;
	private String brSex;
	private String brStatus;
	private Long cpdAttntypeId;
	private Long cpdDelmethId;
	private Long cpdDistrictId;
	private Long cpdFEducnId;
	private Long cpdFOccuId;
	private Long cpdMEducnId;
	private Long cpdMOccuId;
	private Long cpdNationltyId;
	private Long cpdRefTypeId;
	private Long cpdReligionId;
	private Long cpdResId;
	private Long cpdStateId;
	private Long cpdTalukaId;
	private String draftStatus;
	private Long hiId;
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private Long modeCpdId;
	private Long noCopies;
	private String otherReligion;
	private String pdAddress;
	private String pdAddressFlag;
	private String pdAddressMar;
	private Long pdAgeAtBirth;
	private Long pdAgeAtMarry;
	private String pdFathername;
	private String pdFathernameMar;
	private Long pdId;
	private Long pdLiveChildn;
	private String pdMotherAdd;
	private String pdMothername;
	private String pdMothernameMar;
	private String pdParaddress;
	private String pdParaddressMar;
	private Long pdRegUnitId;
	private String pdUidF;
	private String pdUidM;
	private Long smDraftId;
	private Long smServiceId;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private Long wardid;
	private Long applnId;
	private String statusFlag;
	private String cpdDesc;
	private String brBirthMark;
	private String motheraddressMar;

	public Long getBrDraftId() {
		return brDraftId;
	}
	public void setBrDraftId(Long brDraftId) {
		this.brDraftId = brDraftId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWardid() {
		return wardid;
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
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public String getCpdDesc() {
		return cpdDesc;
	}
	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
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
 
	
	

}
