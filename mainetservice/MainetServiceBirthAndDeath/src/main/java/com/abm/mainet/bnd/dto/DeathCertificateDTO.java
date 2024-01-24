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

public class DeathCertificateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Date drDod;
	private Date drCertiGen;
	private String drSex;
	private String drDeceasedname;
	private String drMarDeceasedname;
	private String drRelativeName;
	private String drMarRelativeName;
	private String drMotherName;
	private String drMarMotherName;
	private String drDeceasedaddr;
	private String drMarDeceasedaddr;
	private String drMarDeathaddr;
	private String drDcaddrAtdeath;
	private String drDcaddrAtdeathMar;
	private String drDeathplace;
	private String drMarDeathplace;
	private Long demandedCopies;
	private Long orgId;
	private Long userId;
	private Long applnId;
	private int langId;
	
	private Long drRId;
	private String drMarInformantAddr;
	private String drMarInformantName;
	private String drInformantAddr;
	private String drInformantName;
	private String applicantEmail;
	private String applicantFname;
	private String applicantLname;
	private String applicantMname;
	private String applicantMobilno;
	private Long applicantTitle;
	private String bplNo;
	private String ceAddr;
	private String ceAddrMar;
	private String ceFlag;
	private Long ceId;
	private String ceName;
	private String ceNameMar;
	private Long cpdAgeperiodId;
	private Long cpdAttntypeId;
	private Long cpdDeathcauseId;
	private String cpdDeathplaceType;
	private Long cpdDistrictId;
	private Long cpdEducationId;
	private Long cpdMaritalStatId;
	private Long cpdNationalityId;
	private Long cpdOccupationId;
	private Long cpdRegUnit;
	private Long cpdReligionId;
	private Long cpdResId;
	private Long cpdStateId;
	private Long cpdTalukaId;
	private Long cpdidMcDeathManner;
	private String dcUid;
	private Long dcmId;
	private String decAlcoholic;
	private Long decAlcoholicYr;
	private String decChewarac;
	private Long decChewaracYr;
	private String decChewtb;
	private Long decChewtbYr;
	private String decSmoker;
	private Long decSmokerYr;
	private String drCertNo;
	private String drDeathaddr;
	private Long drDeceasedage;
	private Date drRegdate;
	private String drRegno;
	private String drRemarks;
	private String drStatus;
	private String draftStatus;
	private Long hiId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private String mcDeathManner;
	private String mcDeathcause;
	private String mcDelivery;
	private Long mcInteronset;
	private String mcMdSuprName;
	private String mcMdattndName;
	private String mcOthercond;
	private String mcPregnAssoc;
	private Date mcVerifnDate;
	private Long mcWardId;
	private String medCert;
	private Long modeCpdId;
	private String otherReligion;
	private String pdUidF;
	private String pdUidM;
	private Long smDraftId;
	private Long smServiceId;
	private Long updatedBy;
	private Date updatedDate;
	private Long wardid;
	private String wfStatus;
	private String checkStatus;
	private String chargeStatus;
	private Long issuedCopies;
	private double amount;
	private Long applicationNo;
	private String authRemark;
	private String deathRegstatus;
	private List<DocumentDetailsVO> uploadDocument;
	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	private String chargesStatus;
    private List<ChargeDetailDTO> chargesInfo;
	private String chargesAmount;
	private ChallanReceiptPrintDTO receiptDTO = null;
	private String applicantAddress;
	private String drHubandWfeNme;
	private Long deathPtiNo;
	private String downloadPath;
	
	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public Long getDeathPtiNo() {
		return deathPtiNo;
	}

	public void setDeathPtiNo(Long deathPtiNo) {
		this.deathPtiNo = deathPtiNo;
	}

	public Date getDrCertiGen() {
		return drCertiGen;
	}

	public void setDrCertiGen(Date drCertiGen) {
		this.drCertiGen = drCertiGen;
	}

	public String getDrHubandWfeNme() {
		return drHubandWfeNme;
	}

	public void setDrHubandWfeNme(String drHubandWfeNme) {
		this.drHubandWfeNme = drHubandWfeNme;
	}

	private RequestDTO requestDTO = new RequestDTO();
	
	private Long chargeApplicableAt;
	
	public Long getChargeApplicableAt() {
		return chargeApplicableAt;
	}

	public void setChargeApplicableAt(Long chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
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

	public Long getCpdAgeperiodId() {
		return cpdAgeperiodId;
	}

	public void setCpdAgeperiodId(Long cpdAgeperiodId) {
		this.cpdAgeperiodId = cpdAgeperiodId;
	}

	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}

	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}

	public Long getCpdDeathcauseId() {
		return cpdDeathcauseId;
	}

	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
	}

	public String getCpdDeathplaceType() {
		return cpdDeathplaceType;
	}

	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}

	public Long getCpdDistrictId() {
		return cpdDistrictId;
	}

	public void setCpdDistrictId(Long cpdDistrictId) {
		this.cpdDistrictId = cpdDistrictId;
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

	public Long getCpdNationalityId() {
		return cpdNationalityId;
	}

	public void setCpdNationalityId(Long cpdNationalityId) {
		this.cpdNationalityId = cpdNationalityId;
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

	public Long getCpdidMcDeathManner() {
		return cpdidMcDeathManner;
	}

	public void setCpdidMcDeathManner(Long cpdidMcDeathManner) {
		this.cpdidMcDeathManner = cpdidMcDeathManner;
	}

	public String getDcUid() {
		return dcUid;
	}

	public void setDcUid(String dcUid) {
		this.dcUid = dcUid;
	}

	public Long getDcmId() {
		return dcmId;
	}

	public void setDcmId(Long dcmId) {
		this.dcmId = dcmId;
	}

	public String getDecAlcoholic() {
		return decAlcoholic;
	}

	public void setDecAlcoholic(String decAlcoholic) {
		this.decAlcoholic = decAlcoholic;
	}

	public Long getDecAlcoholicYr() {
		return decAlcoholicYr;
	}

	public void setDecAlcoholicYr(Long decAlcoholicYr) {
		this.decAlcoholicYr = decAlcoholicYr;
	}

	public String getDecChewarac() {
		return decChewarac;
	}

	public void setDecChewarac(String decChewarac) {
		this.decChewarac = decChewarac;
	}

	public Long getDecChewaracYr() {
		return decChewaracYr;
	}

	public void setDecChewaracYr(Long decChewaracYr) {
		this.decChewaracYr = decChewaracYr;
	}

	public String getDecChewtb() {
		return decChewtb;
	}

	public void setDecChewtb(String decChewtb) {
		this.decChewtb = decChewtb;
	}

	public Long getDecChewtbYr() {
		return decChewtbYr;
	}

	public void setDecChewtbYr(Long decChewtbYr) {
		this.decChewtbYr = decChewtbYr;
	}

	public String getDecSmoker() {
		return decSmoker;
	}

	public void setDecSmoker(String decSmoker) {
		this.decSmoker = decSmoker;
	}

	public Long getDecSmokerYr() {
		return decSmokerYr;
	}

	public void setDecSmokerYr(Long decSmokerYr) {
		this.decSmokerYr = decSmokerYr;
	}

	public String getDrCertNo() {
		return drCertNo;
	}

	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
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

	public String getDrDeathaddr() {
		return drDeathaddr;
	}

	public void setDrDeathaddr(String drDeathaddr) {
		this.drDeathaddr = drDeathaddr;
	}

	public String getDrDeathplace() {
		return drDeathplace;
	}

	public void setDrDeathplace(String drDeathplace) {
		this.drDeathplace = drDeathplace;
	}

	public String getDrDeceasedaddr() {
		return drDeceasedaddr;
	}

	public void setDrDeceasedaddr(String drDeceasedaddr) {
		this.drDeceasedaddr = drDeceasedaddr;
	}

	public Long getDrDeceasedage() {
		return drDeceasedage;
	}

	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
	}

	public String getDrDeceasedname() {
		return drDeceasedname;
	}

	public void setDrDeceasedname(String drDeceasedname) {
		this.drDeceasedname = drDeceasedname;
	}

	public Date getDrDod() {
		return drDod;
	}

	public void setDrDod(Date drDod) {
		this.drDod = drDod;
	}

	public String getDrInformantAddr() {
		return drInformantAddr;
	}

	public void setDrInformantAddr(String drInformantAddr) {
		this.drInformantAddr = drInformantAddr;
	}

	public String getDrInformantName() {
		return drInformantName;
	}

	public void setDrInformantName(String drInformantName) {
		this.drInformantName = drInformantName;
	}

	public String getDrMarDeathaddr() {
		return drMarDeathaddr;
	}

	public void setDrMarDeathaddr(String drMarDeathaddr) {
		this.drMarDeathaddr = drMarDeathaddr;
	}

	public String getDrMarDeathplace() {
		return drMarDeathplace;
	}

	public void setDrMarDeathplace(String drMarDeathplace) {
		this.drMarDeathplace = drMarDeathplace;
	}

	public String getDrMarDeceasedaddr() {
		return drMarDeceasedaddr;
	}

	public void setDrMarDeceasedaddr(String drMarDeceasedaddr) {
		this.drMarDeceasedaddr = drMarDeceasedaddr;
	}

	public String getDrMarDeceasedname() {
		return drMarDeceasedname;
	}

	public void setDrMarDeceasedname(String drMarDeceasedname) {
		this.drMarDeceasedname = drMarDeceasedname;
	}

	public String getDrMarInformantAddr() {
		return drMarInformantAddr;
	}

	public void setDrMarInformantAddr(String drMarInformantAddr) {
		this.drMarInformantAddr = drMarInformantAddr;
	}

	public String getDrMarInformantName() {
		return drMarInformantName;
	}

	public void setDrMarInformantName(String drMarInformantName) {
		this.drMarInformantName = drMarInformantName;
	}

	public String getDrMarMotherName() {
		return drMarMotherName;
	}

	public void setDrMarMotherName(String drMarMotherName) {
		this.drMarMotherName = drMarMotherName;
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

	public Date getDrRegdate() {
		return drRegdate;
	}

	public void setDrRegdate(Date drRegdate) {
		this.drRegdate = drRegdate;
	}

	public String getDrRegno() {
		return drRegno;
	}

	public void setDrRegno(String drRegno) {
		this.drRegno = drRegno;
	}

	public String getDrRelativeName() {
		return drRelativeName;
	}

	public void setDrRelativeName(String drRelativeName) {
		this.drRelativeName = drRelativeName;
	}

	public String getDrRemarks() {
		return drRemarks;
	}

	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}

	public String getDrSex() {
		return drSex;
	}

	public void setDrSex(String drSex) {
		this.drSex = drSex;
	}

	public String getDrStatus() {
		return drStatus;
	}

	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
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

	public String getMcDeathManner() {
		return mcDeathManner;
	}

	public void setMcDeathManner(String mcDeathManner) {
		this.mcDeathManner = mcDeathManner;
	}

	public String getMcDeathcause() {
		return mcDeathcause;
	}

	public void setMcDeathcause(String mcDeathcause) {
		this.mcDeathcause = mcDeathcause;
	}

	public String getMcDelivery() {
		return mcDelivery;
	}

	public void setMcDelivery(String mcDelivery) {
		this.mcDelivery = mcDelivery;
	}

	public Long getMcInteronset() {
		return mcInteronset;
	}

	public void setMcInteronset(Long mcInteronset) {
		this.mcInteronset = mcInteronset;
	}

	public String getMcMdSuprName() {
		return mcMdSuprName;
	}

	public void setMcMdSuprName(String mcMdSuprName) {
		this.mcMdSuprName = mcMdSuprName;
	}

	public String getMcMdattndName() {
		return mcMdattndName;
	}

	public void setMcMdattndName(String mcMdattndName) {
		this.mcMdattndName = mcMdattndName;
	}

	public String getMcOthercond() {
		return mcOthercond;
	}

	public void setMcOthercond(String mcOthercond) {
		this.mcOthercond = mcOthercond;
	}

	public String getMcPregnAssoc() {
		return mcPregnAssoc;
	}

	public void setMcPregnAssoc(String mcPregnAssoc) {
		this.mcPregnAssoc = mcPregnAssoc;
	}

	public Date getMcVerifnDate() {
		return mcVerifnDate;
	}

	public void setMcVerifnDate(Date mcVerifnDate) {
		this.mcVerifnDate = mcVerifnDate;
	}

	public Long getMcWardId() {
		return mcWardId;
	}

	public void setMcWardId(Long mcWardId) {
		this.mcWardId = mcWardId;
	}

	public String getMedCert() {
		return medCert;
	}

	public void setMedCert(String medCert) {
		this.medCert = medCert;
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

	public String getOtherReligion() {
		return otherReligion;
	}

	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
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

	public String getWfStatus() {
		return wfStatus;
	}

	public void setWfStatus(String wfStatus) {
		this.wfStatus = wfStatus;
	}

	public Long getApplnId() {
		return applnId;
	}

	public void setApplnId(Long applnId) {
		this.applnId = applnId;
	}

	public Long getDrRId() {
		return drRId;
	}

	public void setDrRId(Long drRId) {
		this.drRId = drRId;
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

	public Long getDemandedCopies() {
		return demandedCopies;
	}

	public void setDemandedCopies(Long demandedCopies) {
		this.demandedCopies = demandedCopies;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
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

	public String getAuthRemark() {
		return authRemark;
	}

	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	public String getDeathRegstatus() {
		return deathRegstatus;
	}

	public void setDeathRegstatus(String deathRegstatus) {
		this.deathRegstatus = deathRegstatus;
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

	public ChallanReceiptPrintDTO getReceiptDTO() {
		return receiptDTO;
	}

	public void setReceiptDTO(ChallanReceiptPrintDTO receiptDTO) {
		this.receiptDTO = receiptDTO;
	}

	public String getApplicantAddress() {
		return applicantAddress;
	}

	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}

	public RequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	
}
