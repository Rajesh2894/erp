package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;

public class BirthCertificateDTO implements Serializable {

	private static final long serialVersionUID = -4100833798784520499L;

	private Long brRId;
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
	private String brBirthAddr;
	private String brBirthAddrMar;
	private String brBirthPlace;
	private String brBirthPlaceMar;
	private String brBirthPlaceType;
	private Double brBirthwt;
	private String brCertNo;
	private String brChildName;
	private String brChildNameMar;
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
	private String hiId;
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
	private Long deptId;
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
	private Long apmApplicationId;
	private String applicationId;
	private Long noOfCopies;
	private Double amount;
	private String birthWfStatus;
	private String birthRegstatus;
	private String birthRegremark;
	private List<DocumentDetailsVO> uploadDocument;
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String chargesStatus;
	private List<ChargeDetailDTO> chargesInfo;
	private String applicantAddress;
	private RequestDTO requestDTO = new RequestDTO();
	private ChallanReceiptPrintDTO receiptDTO = null;
	private Long chargeApplicableAt;
	private Date brAcgd;
	private Long birthPtiNo;
	private String brDownloadPath;
	public String getBrDownloadPath() {
		return brDownloadPath;
	}

	public void setBrDownloadPath(String brDownloadPath) {
		this.brDownloadPath = brDownloadPath;
	}

	public Date getBrAcgd() {
		return brAcgd;
	}

	public void setBrAcgd(Date brAcgd) {
		this.brAcgd = brAcgd;
	}

	public Long getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	public void setChargeApplicableAt(Long chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
	}

	public ChallanReceiptPrintDTO getReceiptDTO() {
		return receiptDTO;
	}

	public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
		this.receiptDTO = receiptDTO;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	private String chargesAmount;

	public List<ChargeDetailDTO> getChargesInfo() {
		return chargesInfo;
	}

	public void setChargesInfo(List<ChargeDetailDTO> chargesInfo) {
		this.chargesInfo = chargesInfo;
	}

	public String getChargesAmount() {
		return chargesAmount;
	}

	public void setChargesAmount(String chargesAmount) {
		this.chargesAmount = chargesAmount;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public String getChargesStatus() {
		return chargesStatus;
	}

	public void setChargesStatus(String chargesStatus) {
		this.chargesStatus = chargesStatus;
	}

	public String getBirthRegremark() {
		return birthRegremark;
	}

	public void setBirthRegremark(String birthRegremark) {
		this.birthRegremark = birthRegremark;
	}

	public String getBirthRegstatus() {
		return birthRegstatus;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public String getBirthWfStatus() {
		return birthWfStatus;
	}

	public void setBirthWfStatus(String birthWfStatus) {
		this.birthWfStatus = birthWfStatus;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getBrRId() {
		return brRId;
	}

	public String getBrBirthAddr() {
		return brBirthAddr;
	}

	public void setBrBirthAddr(String brBirthAddr) {
		this.brBirthAddr = brBirthAddr;
	}

	public String getBrBirthAddrMar() {
		return brBirthAddrMar;
	}

	public List<DocumentDetailsVO> getUploadDocument() {
		return uploadDocument;
	}

	public void setUploadDocument(List<DocumentDetailsVO> uploadDocument) {
		this.uploadDocument = uploadDocument;
	}

	public void setBrBirthAddrMar(String brBirthAddrMar) {
		this.brBirthAddrMar = brBirthAddrMar;
	}

	public String getBrBirthPlace() {
		return brBirthPlace;
	}

	public void setBrBirthPlace(String brBirthPlace) {
		this.brBirthPlace = brBirthPlace;
	}

	public String getBrBirthPlaceMar() {
		return brBirthPlaceMar;
	}

	public void setBrBirthPlaceMar(String brBirthPlaceMar) {
		this.brBirthPlaceMar = brBirthPlaceMar;
	}

	public String getBrBirthPlaceType() {
		return brBirthPlaceType;
	}

	public void setBrBirthPlaceType(String brBirthPlaceType) {
		this.brBirthPlaceType = brBirthPlaceType;
	}

	public String getBrChildName() {
		return brChildName;
	}

	public void setBrChildName(String brChildName) {
		this.brChildName = brChildName;
	}

	public String getBrChildNameMar() {
		return brChildNameMar;
	}

	public void setBrChildNameMar(String brChildNameMar) {
		this.brChildNameMar = brChildNameMar;
	}

	public void setBrRId(Long brRId) {
		this.brRId = brRId;
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

	public String getHiId() {
		return hiId;
	}

	public void setHiId(String hiId) {
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

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Long getBirthPtiNo() {
		return birthPtiNo;
	}

	public void setBirthPtiNo(Long birthPtiNo) {
		this.birthPtiNo = birthPtiNo;
	}
	
}
