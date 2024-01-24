
package com.abm.mainet.bnd.dto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

public class TbDeathregDTO implements Serializable {

	private static final long serialVersionUID = 1952094878888159022L;

	
	private Long drId;
    private Long drDraftId;
	private Long orgId;
	private Date drDod;
	private String drSex;
	private String drSexMar;
	private String year;
	private Long drDeceasedage;
	private Long cpdNationalityId;
	private String drDeceasedname;
	private String drMarDeceasedname;
	private String drRelativeName;
	private String drMarRelativeName;
	private String drMotherName;
	private String drMarMotherName;
	private String drDeceasedaddr;
	private String drMarDeceasedaddr;
	private String drDcaddrAtdeath;
	private String drDcaddrAtdeathMar;
	private String cpdDeathplaceType;
	private String drDeathplace;
	private String drMarDeathplace;
	private String drDeathaddr;
	private String drMarDeathaddr;
	private String statusFlag;
	// Informnat Details
	private String drInformantName;
	private String drMarInformantName;
	private String drInformantAddr;
	private String drMarInformantAddr;

	// Other details
	private Long cpdReligionId;
	private String otherReligion;
	private Long cpdEducationId;
	private Long cpdMaritalStatId;
	private Long cpdOccupationId;
	private Long cpdRegUnit;
	private Long cpdAttntypeId;

	// cemetry Details
	private Long ceId;
	private String ceName;
	private String ceNameMar;
	private String ceAddr;
	private String ceAddrMar;
	private String ceFlag;

	// medicle certificate details
	private Long cpdDeathcauseId;

	// death correction details
	private String drCertNo;
	private String drRegno;
	private Date drRegdate;
	private Long authBy;
	private Date authDate;
	private String authFlag;
	private String authRemark;
	private String bcrFlag;
	private String bplNo;
	private short certNoCopies;
	private Long codUserWardid;
	private Long cpdAgeperiodId;
	private Long cpdDistrictId;
	private Long cpdResId;
	private Long cpdStateId;
	private Long cpdTalukaId;
	private String dcUid;
	private String drCorrectionFlg;
	private Date drCorrnDate;
	private String drFirNumber;
	private String drFlag;
	private Long drManualCertno;
	private String drPoliceStation;
	private String drRelPreg;
	private String drRemarks;
	private Date regAplDate;
	private String drStatus;
	private Date drSuppdate;
	private String drSuppno;
	private String drSupptime;
	private String drTime;
	private String drUdeathReg;
	private String fileName;
	private String hRId;
	private Long hiId;
	private String hrReg;
	
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private String pdUidF;
	private String pdUidM;
	private String pgflag;
	private String unauthRFlg;
	private Long updatedBy;
	private Date updatedDate;
	private Long upldId;
	
	private Long userId;
	private Long wardid;
	private String drAddress;

	private Long alreayIssuedCopy;
	private Long numberOfCopies;
	private Double amount;
	private String paymentMode;
	private String applicationNo;
	private Long applicationId;
	private Long apmApplicationId;
	private Long deptId;
	private Long serviceId;
	private boolean freeService;
	private String cpdDesc;
	//@JsonIgnore
	private Long applnId;
	private String deathRegstatus;
	private String deathRegremark;
	private String DeathWFStatus;
	private List<String>  corrCategory;
	@JsonIgnore
	private ParentDetailDTO parentDetailDTO = new ParentDetailDTO();
	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();
	private MedicalMasterDTO medicalMasterDto= new MedicalMasterDTO();
	private DeceasedMasterDTO deceasedMasterDTO = new DeceasedMasterDTO();
	@JsonIgnore
	private TbDeathRegHistoryDTO tbDeathRegHistoryDTO;
	
	private BirthDeathCFCInterfaceDTO BirthDeathCFCInterfaceDTO = new BirthDeathCFCInterfaceDTO();
	
	private List<DocumentDetailsVO> uploadDocument;
	
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	@JsonIgnore
	private List<TbDeathRegdraftDto> tbDeathRegdraftDto= new ArrayList<>();
	
	private RequestDTO requestDTO = new RequestDTO();
	
	private String orgShortNm;
	private String orgCode;
	private String agePeriodUnit;
	private String deathplaceType;
	private String hospital;
	private String religion;
	private String education;
	private String maritalStatus;
	private String occupation;
	private String registrationUnit;
	private String attentiontype;
	private String cemetery;
	private String deathcause;
	private String deathManner;
	private String checkStatus; 
	private String chargeStatus;
	private String amountChargeStatus;
	private String mobileNo;
	private String appDateOfDeath;
	private String appDateOfRegistration;
	private String newDate;
	private String pdRegUnitCode;
	
	@JsonIgnore
	private String dateOfDeath;
	
	@JsonIgnore
	private String regDate;
	
	private long receiptNo;
	private Date receiptDate;
	
	private Long bndDw1;
	private Long bndDw2;
	private Long bndDw3;
	private Long bndDw4;
	private Long bndDw5;
	private Long drSexId;
	
	public long getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(long receiptNo) {
		this.receiptNo = receiptNo;
	}
	public Date getReceiptDate() {
		return receiptDate;
	}
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}
	public List<TbDeathRegdraftDto> getTbDeathRegdraftDto() {
		return tbDeathRegdraftDto;
	}
	public void setTbDeathRegdraftDto(List<TbDeathRegdraftDto> tbDeathRegdraftDto) {
		this.tbDeathRegdraftDto = tbDeathRegdraftDto;
	}
	public String getStatusFlag() {
		return statusFlag;
	}
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	public Long getDrId() {
		return drId;
	}
	public void setDrId(Long drId) {
		this.drId = drId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Date getDrDod() {
		return drDod;
	}
	public void setDrDod(Date drDod) {
		this.drDod = drDod;
	}
	public String getDrSex() {
		return drSex;
	}
	public void setDrSex(String drSex) {
		this.drSex = drSex;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public Long getDrDeceasedage() {
		return drDeceasedage;
	}
	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
	}
	public Long getCpdNationalityId() {
		return cpdNationalityId;
	}
	public void setCpdNationalityId(Long cpdNationalityId) {
		this.cpdNationalityId = cpdNationalityId;
	}
	public String getDrDeceasedname() {
		return drDeceasedname;
	}
	public void setDrDeceasedname(String drDeceasedname) {
		this.drDeceasedname = drDeceasedname;
	}
	public String getDrMarDeceasedname() {
		return drMarDeceasedname;
	}
	public void setDrMarDeceasedname(String drMarDeceasedname) {
		this.drMarDeceasedname = drMarDeceasedname;
	}
	public String getDrRelativeName() {
		return drRelativeName;
	}
	public void setDrRelativeName(String drRelativeName) {
		this.drRelativeName = drRelativeName;
	}
	public String getDrMarRelativeName() {
		return drMarRelativeName;
	}
	public void setDrMarRelativeName(String drMarRelativeName) {
		this.drMarRelativeName = drMarRelativeName;
	}
	public String getDrMotherName() {
		return drMotherName;
	}
	public void setDrMotherName(String drMotherName) {
		this.drMotherName = drMotherName;
	}
	public String getDrMarMotherName() {
		return drMarMotherName;
	}
	public void setDrMarMotherName(String drMarMotherName) {
		this.drMarMotherName = drMarMotherName;
	}
	public String getDrDeceasedaddr() {
		return drDeceasedaddr;
	}
	public void setDrDeceasedaddr(String drDeceasedaddr) {
		this.drDeceasedaddr = drDeceasedaddr;
	}
	public String getDrMarDeceasedaddr() {
		return drMarDeceasedaddr;
	}
	public void setDrMarDeceasedaddr(String drMarDeceasedaddr) {
		this.drMarDeceasedaddr = drMarDeceasedaddr;
	}
	public String getDrDcaddrAtdeath() {
		return drDcaddrAtdeath;
	}
	public void setDrDcaddrAtdeath(String drDcaddrAtdeath) {
		this.drDcaddrAtdeath = drDcaddrAtdeath;
	}
	public String getDrDcaddrAtdeathMar() {
		return drDcaddrAtdeathMar;
	}
	public void setDrDcaddrAtdeathMar(String drDcaddrAtdeathMar) {
		this.drDcaddrAtdeathMar = drDcaddrAtdeathMar;
	}
	public String getCpdDeathplaceType() {
		return cpdDeathplaceType;
	}
	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}
	public String getDrDeathplace() {
		return drDeathplace;
	}
	public void setDrDeathplace(String drDeathplace) {
		this.drDeathplace = drDeathplace;
	}
	public String getDrMarDeathplace() {
		return drMarDeathplace;
	}
	public void setDrMarDeathplace(String drMarDeathplace) {
		this.drMarDeathplace = drMarDeathplace;
	}
	public String getDrDeathaddr() {
		return drDeathaddr;
	}
	public void setDrDeathaddr(String drDeathaddr) {
		this.drDeathaddr = drDeathaddr;
	}
	public String getDrMarDeathaddr() {
		return drMarDeathaddr;
	}
	public void setDrMarDeathaddr(String drMarDeathaddr) {
		this.drMarDeathaddr = drMarDeathaddr;
	}
	public String getDrInformantName() {
		return drInformantName;
	}
	public void setDrInformantName(String drInformantName) {
		this.drInformantName = drInformantName;
	}
	public String getDrMarInformantName() {
		return drMarInformantName;
	}
	public void setDrMarInformantName(String drMarInformantName) {
		this.drMarInformantName = drMarInformantName;
	}
	public String getDrInformantAddr() {
		return drInformantAddr;
	}
	public void setDrInformantAddr(String drInformantAddr) {
		this.drInformantAddr = drInformantAddr;
	}
	public String getDrMarInformantAddr() {
		return drMarInformantAddr;
	}
	public void setDrMarInformantAddr(String drMarInformantAddr) {
		this.drMarInformantAddr = drMarInformantAddr;
	}
	public Long getCpdReligionId() {
		return cpdReligionId;
	}
	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}
	public String getOtherReligion() {
		return otherReligion;
	}
	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}
	public Long getCpdEducationId() {
		return cpdEducationId;
	}
	public void setCpdEducationId(Long cpdEducationId) {
		this.cpdEducationId = cpdEducationId;
	}
	public Long getCpdMaritalStatId() {
		return cpdMaritalStatId;
	}
	public void setCpdMaritalStatId(Long cpdMaritalStatId) {
		this.cpdMaritalStatId = cpdMaritalStatId;
	}
	public Long getCpdOccupationId() {
		return cpdOccupationId;
	}
	public void setCpdOccupationId(Long cpdOccupationId) {
		this.cpdOccupationId = cpdOccupationId;
	}
	public Long getCpdRegUnit() {
		return cpdRegUnit;
	}
	public void setCpdRegUnit(Long cpdRegUnit) {
		this.cpdRegUnit = cpdRegUnit;
	}
	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}
	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}
	public Long getCeId() {
		return ceId;
	}
	public void setCeId(Long ceId) {
		this.ceId = ceId;
	}
	public String getCeName() {
		return ceName;
	}
	public void setCeName(String ceName) {
		this.ceName = ceName;
	}
	public String getCeNameMar() {
		return ceNameMar;
	}
	public void setCeNameMar(String ceNameMar) {
		this.ceNameMar = ceNameMar;
	}
	public String getCeAddr() {
		return ceAddr;
	}
	public void setCeAddr(String ceAddr) {
		this.ceAddr = ceAddr;
	}
	public String getCeAddrMar() {
		return ceAddrMar;
	}
	public void setCeAddrMar(String ceAddrMar) {
		this.ceAddrMar = ceAddrMar;
	}
	public String getCeFlag() {
		return ceFlag;
	}
	public void setCeFlag(String ceFlag) {
		this.ceFlag = ceFlag;
	}
	public Long getCpdDeathcauseId() {
		return cpdDeathcauseId;
	}
	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
	}
	public String getDrCertNo() {
		return drCertNo;
	}
	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
	}
	public String getDrRegno() {
		return drRegno;
	}
	public void setDrRegno(String drRegno) {
		this.drRegno = drRegno;
	}
	public Date getDrRegdate() {
		return drRegdate;
	}
	public void setDrRegdate(Date drRegdate) {
		this.drRegdate = drRegdate;
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
	public String getBcrFlag() {
		return bcrFlag;
	}
	public void setBcrFlag(String bcrFlag) {
		this.bcrFlag = bcrFlag;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public short getCertNoCopies() {
		return certNoCopies;
	}
	public void setCertNoCopies(short certNoCopies) {
		this.certNoCopies = certNoCopies;
	}
	public Long getCodUserWardid() {
		return codUserWardid;
	}
	public void setCodUserWardid(Long codUserWardid) {
		this.codUserWardid = codUserWardid;
	}
	public Long getCpdAgeperiodId() {
		return cpdAgeperiodId;
	}
	public void setCpdAgeperiodId(Long cpdAgeperiodId) {
		this.cpdAgeperiodId = cpdAgeperiodId;
	}
	public Long getCpdDistrictId() {
		return cpdDistrictId;
	}
	public void setCpdDistrictId(Long cpdDistrictId) {
		this.cpdDistrictId = cpdDistrictId;
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
	public String getDcUid() {
		return dcUid;
	}
	public void setDcUid(String dcUid) {
		this.dcUid = dcUid;
	}
	public String getDrCorrectionFlg() {
		return drCorrectionFlg;
	}
	public void setDrCorrectionFlg(String drCorrectionFlg) {
		this.drCorrectionFlg = drCorrectionFlg;
	}
	public Date getDrCorrnDate() {
		return drCorrnDate;
	}
	public void setDrCorrnDate(Date drCorrnDate) {
		this.drCorrnDate = drCorrnDate;
	}
	public String getDrFirNumber() {
		return drFirNumber;
	}
	public void setDrFirNumber(String drFirNumber) {
		this.drFirNumber = drFirNumber;
	}
	public String getDrFlag() {
		return drFlag;
	}
	public void setDrFlag(String drFlag) {
		this.drFlag = drFlag;
	}
	
	public Long getDrManualCertno() {
		return drManualCertno;
	}
	public void setDrManualCertno(Long drManualCertno) {
		this.drManualCertno = drManualCertno;
	}
	public String getDrPoliceStation() {
		return drPoliceStation;
	}
	public void setDrPoliceStation(String drPoliceStation) {
		this.drPoliceStation = drPoliceStation;
	}
	public String getDrRelPreg() {
		return drRelPreg;
	}
	public void setDrRelPreg(String drRelPreg) {
		this.drRelPreg = drRelPreg;
	}
	public String getDrRemarks() {
		return drRemarks;
	}
	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}
	public Date getRegAplDate() {
		return regAplDate;
	}
	public void setRegAplDate(Date regAplDate) {
		this.regAplDate = regAplDate;
	}
	public String getDrStatus() {
		return drStatus;
	}
	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
	}
	public Date getDrSuppdate() {
		return drSuppdate;
	}
	public void setDrSuppdate(Date drSuppdate) {
		this.drSuppdate = drSuppdate;
	}
	public String getDrSuppno() {
		return drSuppno;
	}
	public void setDrSuppno(String drSuppno) {
		this.drSuppno = drSuppno;
	}
	public String getDrSupptime() {
		return drSupptime;
	}
	public void setDrSupptime(String drSupptime) {
		this.drSupptime = drSupptime;
	}
	public String getDrTime() {
		return drTime;
	}
	public void setDrTime(String drTime) {
		this.drTime = drTime;
	}
	public String getDrUdeathReg() {
		return drUdeathReg;
	}
	public void setDrUdeathReg(String drUdeathReg) {
		this.drUdeathReg = drUdeathReg;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String gethRId() {
		return hRId;
	}
	public void sethRId(String hRId) {
		this.hRId = hRId;
	}
	public Long getHiId() {
		return hiId;
	}
	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}
	public String getHrReg() {
		return hrReg;
	}
	public void setHrReg(String hrReg) {
		this.hrReg = hrReg;
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
	public String getPgflag() {
		return pgflag;
	}
	public void setPgflag(String pgflag) {
		this.pgflag = pgflag;
	}
	public String getUnauthRFlg() {
		return unauthRFlg;
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
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public Long getUpldId() {
		return upldId;
	}
	public void setUpldId(Long upldId) {
		this.upldId = upldId;
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
	public String getDrAddress() {
		return drAddress;
	}
	public void setDrAddress(String drAddress) {
		this.drAddress = drAddress;
	}
	public Long getAlreayIssuedCopy() {
		return alreayIssuedCopy;
	}
	public void setAlreayIssuedCopy(Long alreayIssuedCopy) {
		this.alreayIssuedCopy = alreayIssuedCopy;
	}
	public Long getNumberOfCopies() {
		return numberOfCopies;
	}
	public void setNumberOfCopies(Long numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Long getApmApplicationId() {
		return apmApplicationId;
	}
	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public boolean isFreeService() {
		return freeService;
	}
	public void setFreeService(boolean freeService) {
		this.freeService = freeService;
	}
	public ParentDetailDTO getParentDetailDTO() {
		return parentDetailDTO;
	}
	public void setParentDetailDTO(ParentDetailDTO parentDetailDTO) {
		this.parentDetailDTO = parentDetailDTO;
	}
	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}
	public void setApplicantDTO(ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
	}
	public MedicalMasterDTO getMedicalMasterDto() {
		return medicalMasterDto;
	}
	public void setMedicalMasterDto(MedicalMasterDTO medicalMasterDto) {
		this.medicalMasterDto = medicalMasterDto;
	}
	public DeceasedMasterDTO getDeceasedMasterDTO() {
		return deceasedMasterDTO;
	}
	public void setDeceasedMasterDTO(DeceasedMasterDTO deceasedMasterDTO) {
		this.deceasedMasterDTO = deceasedMasterDTO;
	}
	public BirthDeathCFCInterfaceDTO getBirthDeathCFCInterfaceDTO() {
		return BirthDeathCFCInterfaceDTO;
	}
	public void setBirthDeathCFCInterfaceDTO(BirthDeathCFCInterfaceDTO birthDeathCFCInterfaceDTO) {
		BirthDeathCFCInterfaceDTO = birthDeathCFCInterfaceDTO;
	}
	public List<DocumentDetailsVO> getUploadDocument() {
		return uploadDocument;
	}
	public void setUploadDocument(List<DocumentDetailsVO> uploadDocument) {
		this.uploadDocument = uploadDocument;
	}
	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}
	public String getCpdDesc() {
		return cpdDesc;
	}
	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
	}
	public Long getApplnId() {
		return applnId;
	}
	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}
	public TbDeathRegHistoryDTO getTbDeathRegHistoryDTO() {
		return tbDeathRegHistoryDTO;
	}
	public void setTbDeathRegHistoryDTO(TbDeathRegHistoryDTO tbDeathRegHistoryDTO) {
		this.tbDeathRegHistoryDTO = tbDeathRegHistoryDTO;
	}
	
	public String getDeathRegstatus() {
		return deathRegstatus;
	}
	public void setDeathRegstatus(String deathRegstatus) {
		this.deathRegstatus = deathRegstatus;
	}
	public String getDeathRegremark() {
		return deathRegremark;
	}
	public void setDeathRegremark(String deathRegremark) {
		this.deathRegremark = deathRegremark;
	}
	
	public String getDeathWFStatus() {
		return DeathWFStatus;
	}
	public void setDeathWFStatus(String deathWFStatus) {
		DeathWFStatus = deathWFStatus;
	}
	public String getDrSexMar() {
		return drSexMar;
	}
	public void setDrSexMar(String drSexMar) {
		this.drSexMar = drSexMar;
	}
	
       public Long getDrDraftId() {
		return drDraftId;
	}
	public void setDrDraftId(Long drDraftId) {
		this.drDraftId = drDraftId;
	}
	public String getOrgShortNm() {
		return orgShortNm;
	}
	public void setOrgShortNm(String orgShortNm) {
		this.orgShortNm = orgShortNm;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getAgePeriodUnit() {
		return agePeriodUnit;
	}
	public void setAgePeriodUnit(String agePeriodUnit) {
		this.agePeriodUnit = agePeriodUnit;
	}
	public String getDeathplaceType() {
		return deathplaceType;
	}
	public void setDeathplaceType(String deathplaceType) {
		this.deathplaceType = deathplaceType;
	}
	public String getHospital() {
		return hospital;
	}
	public void setHospital(String hospital) {
		this.hospital = hospital;
	}
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getRegistrationUnit() {
		return registrationUnit;
	}
	public void setRegistrationUnit(String registrationUnit) {
		this.registrationUnit = registrationUnit;
	}
	public String getAttentiontype() {
		return attentiontype;
	}
	public void setAttentiontype(String attentiontype) {
		this.attentiontype = attentiontype;
	}
	public String getCemetery() {
		return cemetery;
	}
	public void setCemetery(String cemetery) {
		this.cemetery = cemetery;
	}
	public String getDeathcause() {
		return deathcause;
	}
	public void setDeathcause(String deathcause) {
		this.deathcause = deathcause;
	}
	public String getDeathManner() {
		return deathManner;
	}
	public void setDeathManner(String deathManner) {
		this.deathManner = deathManner;
	}
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public String getChargeStatus() {
		return chargeStatus;
	}
	public void setChargeStatus(String chargeStatus) {
		this.chargeStatus = chargeStatus;
	}
	public String getAmountChargeStatus() {
		return amountChargeStatus;
	}
	public void setAmountChargeStatus(String amountChargeStatus) {
		this.amountChargeStatus = amountChargeStatus;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public RequestDTO getRequestDTO() {
		return requestDTO;
	}
	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	public List<String> getCorrCategory() {
		return corrCategory;
	}
	public void setCorrCategory(List<String> corrCategory) {
		this.corrCategory = corrCategory;
	}
	public String getDateOfDeath() {
		return dateOfDeath;
	}
	public void setDateOfDeath(String dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getAppDateOfDeath() {
		return appDateOfDeath;
	}
	public void setAppDateOfDeath(String appDateOfDeath) {
		this.appDateOfDeath = appDateOfDeath;
	}
	public String getAppDateOfRegistration() {
		return appDateOfRegistration;
	}
	public void setAppDateOfRegistration(String appDateOfRegistration) {
		this.appDateOfRegistration = appDateOfRegistration;
	}
	public String getNewDate() {
		return newDate;
	}
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}
	
	public String getPdRegUnitCode() {
		return pdRegUnitCode;
	}
	public void setPdRegUnitCode(String pdRegUnitCode) {
		this.pdRegUnitCode = pdRegUnitCode;
	}
	public Long getBndDw1() {
		return bndDw1;
	}
	public void setBndDw1(Long bndDw1) {
		this.bndDw1 = bndDw1;
	}
	public Long getBndDw2() {
		return bndDw2;
	}
	public void setBndDw2(Long bndDw2) {
		this.bndDw2 = bndDw2;
	}
	public Long getBndDw3() {
		return bndDw3;
	}
	public void setBndDw3(Long bndDw3) {
		this.bndDw3 = bndDw3;
	}
	public Long getBndDw4() {
		return bndDw4;
	}
	public void setBndDw4(Long bndDw4) {
		this.bndDw4 = bndDw4;
	}
	public Long getBndDw5() {
		return bndDw5;
	}
	public void setBndDw5(Long bndDw5) {
		this.bndDw5 = bndDw5;
	}
	public Long getDrSexId() {
		return drSexId;
	}
	public void setDrSexId(Long drSexId) {
		this.drSexId = drSexId;
	}
	
	

}
