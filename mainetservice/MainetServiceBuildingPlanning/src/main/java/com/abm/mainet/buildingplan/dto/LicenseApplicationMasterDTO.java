package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.abm.mainet.buildingplan.domain.LicenseApplicationLandAcquisitionDetEntity;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandDet;
import com.abm.mainet.buildingplan.domain.LicenseApplicationLandSchedule;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class LicenseApplicationMasterDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long tcpLicMstrId;
	private List<LicenseApplicationLandDetDTO> licenseApplicationLandDetDTOList = new ArrayList<LicenseApplicationLandDetDTO>();
	private LicenseApplicationLandScheduleDTO licenseApplicationLandScheduleDTO = new LicenseApplicationLandScheduleDTO();
	private List<LicenseApplicationLandAcquisitionDetailDTO> licenseApplicationLandAcquisitionDetDTOList = new ArrayList<LicenseApplicationLandAcquisitionDetailDTO>();
	private List<LicenseApplicationPurposeTypeDetDTO> licenseApplicationPurposeTypeDetDTOList = new ArrayList<LicenseApplicationPurposeTypeDetDTO>();
	private List<LicenseApplicationFeeMasDTO> feeMasterDto = new ArrayList<LicenseApplicationFeeMasDTO>();
	private LicenseApplicationLandCategoryDTO landCategoryDTO = new LicenseApplicationLandCategoryDTO();
	private Long applicationNo;
	private String name;
	private String address;
	private String email;
	private String authPName;
	private BigInteger authPMobile;
	private String authPEmail;
	private String authPPAN;
	private String authPDSC;
	private String authPBR;
	private String docBR;
	private String docCAQD;
	private String docCOEQD;
	private Long appPAppType;
	private Long appPLicPurposeId;
	private Long appPLicPurposeId1;
	private Long appPLicPurposeId2;
	private Long appPLicPurposeId3;
	private Long appPLicPurposeId4;
	private Long khrsDist;
	private Long khrsDevPlan;
	private Long khrsZone;
	private Long khrsSec;
	private String khrsThesil;
	private String khrsRevEst;
	private String khrsHadbast;
	private String khrsMustil;
	private String khrsKilla;
	private String min;
	private String tcpNameOfLO;
	private String khrsDevCollab;
	private String khrsDevComName;
	private Date khrsColabRegDate;
	private String khrsCollabAggRevo;
	private String khrsAuthSignLO;
	private String khrsAurSignDev;
	private String khrsRegAuth;
	private String khrsCollabDoc;
	private String khrsBRLODoc;
	private String khrsBRDevDoc;
	private String khrsCollabAggDoc;
	private Long khrsLandTypeId;
	private String ciConsType;
	private Long ciNonConsTypeId;
	private BigDecimal ciConsTypeKanal;
	private BigDecimal ciConsTypeKanalTot;
	private BigDecimal ciConsTypeMarla;
	private BigDecimal ciConsTypeMarlaTot;
	private BigDecimal ciConsTypeSarsai;
	private BigDecimal ciConsTypeSarsaiTot;
	private BigDecimal ciConsTypeTotArea;
	private String ciAquStatus;
	private BigDecimal ciTotArea;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private BigInteger modifiedBy;
	private Date modifiedDate;
	private String isDeleted;
	private String lgIpMac;
	private String draftFlag;
	private Long ddz;
	private Long ddz1;
	private Long ddz2;	
	private Long ddz3;
	private Long ddz4;
	private Long ddz5;
	private Long langId;
	private List<DocumentDetailsVO> applicantCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> landScheduleCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> landDetailsCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> purposeAttachments = new ArrayList<>();
	/*private List<AttachDocs> purposeAttachDocsList = new ArrayList<>();*/
	private Map<Long, Double> feeIds = new HashMap<>(0);
	private BigDecimal licenseFee;
	private BigDecimal scrutinyFee;
	private BigDecimal totalFees;
	private String transNo;
	private String transData;
	private String diaryNo;
	private String caseNno;
	private String applicationNoEService;
	private List<DocumentDetailsVO> encumbranceCheckListDoc = new ArrayList<>();	
	private List<DocumentDetailsVO> courtOrdersLandCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> insolvencyLandCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> shajraAppLandCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> releaseOrderCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> usageAllotteesCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> accessNHSRCheckListDoc = new ArrayList<>();
	private List<DocumentDetailsVO> existingApproachCheckListDoc = new ArrayList<>();	
	private List<DocumentDetailsVO> DGPSCheckListDoc = new ArrayList<>();
	private String workflowStatus;
	private String licenseFeeStr;
	private String scrutinyFeeStr;
	private String totalFeesStr;

	public Long getTcpLicMstrId() {
		return tcpLicMstrId;
	}

	public void setTcpLicMstrId(Long tcpLicMstrId) {
		this.tcpLicMstrId = tcpLicMstrId;
	}

	public List<LicenseApplicationLandDetDTO> getLicenseApplicationLandDetDTOList() {
		return licenseApplicationLandDetDTOList;
	}

	public void setLicenseApplicationLandDetDTOList(List<LicenseApplicationLandDetDTO> licenseApplicationLandDetDTOList) {
		this.licenseApplicationLandDetDTOList = licenseApplicationLandDetDTOList;
	}

	public LicenseApplicationLandScheduleDTO getLicenseApplicationLandScheduleDTO() {
		return licenseApplicationLandScheduleDTO;
	}

	public void setLicenseApplicationLandScheduleDTO(LicenseApplicationLandScheduleDTO licenseApplicationLandScheduleDTO) {
		this.licenseApplicationLandScheduleDTO = licenseApplicationLandScheduleDTO;
	}

	public List<LicenseApplicationLandAcquisitionDetailDTO> getLicenseApplicationLandAcquisitionDetDTOList() {
		return licenseApplicationLandAcquisitionDetDTOList;
	}

	public void setLicenseApplicationLandAcquisitionDetDTOList(
			List<LicenseApplicationLandAcquisitionDetailDTO> licenseApplicationLandAcquisitionDetDTOList) {
		this.licenseApplicationLandAcquisitionDetDTOList = licenseApplicationLandAcquisitionDetDTOList;
	}

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthPName() {
		return authPName;
	}

	public void setAuthPName(String authPName) {
		this.authPName = authPName;
	}

	public BigInteger getAuthPMobile() {
		return authPMobile;
	}

	public void setAuthPMobile(BigInteger authPMobile) {
		this.authPMobile = authPMobile;
	}

	public String getAuthPEmail() {
		return authPEmail;
	}

	public void setAuthPEmail(String authPEmail) {
		this.authPEmail = authPEmail;
	}

	public String getAuthPPAN() {
		return authPPAN;
	}

	public void setAuthPPAN(String authPPAN) {
		this.authPPAN = authPPAN;
	}

	public String getAuthPDSC() {
		return authPDSC;
	}

	public void setAuthPDSC(String authPDSC) {
		this.authPDSC = authPDSC;
	}

	public String getAuthPBR() {
		return authPBR;
	}

	public void setAuthPBR(String authPBR) {
		this.authPBR = authPBR;
	}

	public String getDocBR() {
		return docBR;
	}

	public void setDocBR(String docBR) {
		this.docBR = docBR;
	}

	public String getDocCAQD() {
		return docCAQD;
	}

	public void setDocCAQD(String docCAQD) {
		this.docCAQD = docCAQD;
	}

	public String getDocCOEQD() {
		return docCOEQD;
	}

	public void setDocCOEQD(String docCOEQD) {
		this.docCOEQD = docCOEQD;
	}

	public Long getAppPAppType() {
		return appPAppType;
	}

	public void setAppPAppType(Long appPAppType) {
		this.appPAppType = appPAppType;
	}

	public Long getAppPLicPurposeId() {
		return appPLicPurposeId;
	}

	public void setAppPLicPurposeId(Long appPLicPurposeId) {
		this.appPLicPurposeId = appPLicPurposeId;
	}

	public Long getAppPLicPurposeId1() {
		return appPLicPurposeId1;
	}

	public void setAppPLicPurposeId1(Long appPLicPurposeId1) {
		this.appPLicPurposeId1 = appPLicPurposeId1;
	}

	public Long getAppPLicPurposeId2() {
		return appPLicPurposeId2;
	}

	public void setAppPLicPurposeId2(Long appPLicPurposeId2) {
		this.appPLicPurposeId2 = appPLicPurposeId2;
	}

	public Long getAppPLicPurposeId3() {
		return appPLicPurposeId3;
	}

	public void setAppPLicPurposeId3(Long appPLicPurposeId3) {
		this.appPLicPurposeId3 = appPLicPurposeId3;
	}

	public Long getAppPLicPurposeId4() {
		return appPLicPurposeId4;
	}

	public void setAppPLicPurposeId4(Long appPLicPurposeId4) {
		this.appPLicPurposeId4 = appPLicPurposeId4;
	}

	public Long getKhrsDist() {
		return khrsDist;
	}

	public void setKhrsDist(Long khrsDist) {
		this.khrsDist = khrsDist;
	}

	public Long getKhrsDevPlan() {
		return khrsDevPlan;
	}

	public void setKhrsDevPlan(Long khrsDevPlan) {
		this.khrsDevPlan = khrsDevPlan;
	}

	public Long getKhrsZone() {
		return khrsZone;
	}

	public void setKhrsZone(Long khrsZone) {
		this.khrsZone = khrsZone;
	}

	public Long getKhrsSec() {
		return khrsSec;
	}

	public void setKhrsSec(Long khrsSec) {
		this.khrsSec = khrsSec;
	}

	public String getKhrsThesil() {
		return khrsThesil;
	}

	public void setKhrsThesil(String khrsThesil) {
		this.khrsThesil = khrsThesil;
	}

	public String getKhrsRevEst() {
		return khrsRevEst;
	}

	public void setKhrsRevEst(String khrsRevEst) {
		this.khrsRevEst = khrsRevEst;
	}

	public String getKhrsHadbast() {
		return khrsHadbast;
	}

	public void setKhrsHadbast(String khrsHadbast) {
		this.khrsHadbast = khrsHadbast;
	}

	public String getKhrsMustil() {
		return khrsMustil;
	}

	public void setKhrsMustil(String khrsMustil) {
		this.khrsMustil = khrsMustil;
	}

	public String getKhrsKilla() {
		return khrsKilla;
	}

	public void setKhrsKilla(String khrsKilla) {
		this.khrsKilla = khrsKilla;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getTcpNameOfLO() {
		return tcpNameOfLO;
	}

	public void setTcpNameOfLO(String tcpNameOfLO) {
		this.tcpNameOfLO = tcpNameOfLO;
	}

	public String getKhrsDevCollab() {
		return khrsDevCollab;
	}

	public void setKhrsDevCollab(String khrsDevCollab) {
		this.khrsDevCollab = khrsDevCollab;
	}

	public String getKhrsDevComName() {
		return khrsDevComName;
	}

	public void setKhrsDevComName(String khrsDevComName) {
		this.khrsDevComName = khrsDevComName;
	}

	public Date getKhrsColabRegDate() {
		return khrsColabRegDate;
	}

	public void setKhrsColabRegDate(Date khrsColabRegDate) {
		this.khrsColabRegDate = khrsColabRegDate;
	}

	public String getKhrsCollabAggRevo() {
		return khrsCollabAggRevo;
	}

	public void setKhrsCollabAggRevo(String khrsCollabAggRevo) {
		this.khrsCollabAggRevo = khrsCollabAggRevo;
	}

	public String getKhrsAuthSignLO() {
		return khrsAuthSignLO;
	}

	public void setKhrsAuthSignLO(String khrsAuthSignLO) {
		this.khrsAuthSignLO = khrsAuthSignLO;
	}

	public String getKhrsAurSignDev() {
		return khrsAurSignDev;
	}

	public void setKhrsAurSignDev(String khrsAurSignDev) {
		this.khrsAurSignDev = khrsAurSignDev;
	}

	public String getKhrsRegAuth() {
		return khrsRegAuth;
	}

	public void setKhrsRegAuth(String khrsRegAuth) {
		this.khrsRegAuth = khrsRegAuth;
	}

	public String getKhrsCollabDoc() {
		return khrsCollabDoc;
	}

	public void setKhrsCollabDoc(String khrsCollabDoc) {
		this.khrsCollabDoc = khrsCollabDoc;
	}

	public String getKhrsBRLODoc() {
		return khrsBRLODoc;
	}

	public void setKhrsBRLODoc(String khrsBRLODoc) {
		this.khrsBRLODoc = khrsBRLODoc;
	}

	public String getKhrsBRDevDoc() {
		return khrsBRDevDoc;
	}

	public void setKhrsBRDevDoc(String khrsBRDevDoc) {
		this.khrsBRDevDoc = khrsBRDevDoc;
	}

	public String getKhrsCollabAggDoc() {
		return khrsCollabAggDoc;
	}

	public void setKhrsCollabAggDoc(String khrsCollabAggDoc) {
		this.khrsCollabAggDoc = khrsCollabAggDoc;
	}

	public Long getKhrsLandTypeId() {
		return khrsLandTypeId;
	}

	public void setKhrsLandTypeId(Long khrsLandTypeId) {
		this.khrsLandTypeId = khrsLandTypeId;
	}

	public String getCiConsType() {
		return ciConsType;
	}

	public void setCiConsType(String ciConsType) {
		this.ciConsType = ciConsType;
	}

	public Long getCiNonConsTypeId() {
		return ciNonConsTypeId;
	}

	public void setCiNonConsTypeId(Long ciNonConsTypeId) {
		this.ciNonConsTypeId = ciNonConsTypeId;
	}

	public BigDecimal getCiConsTypeKanal() {
		return ciConsTypeKanal;
	}

	public void setCiConsTypeKanal(BigDecimal ciConsTypeKanal) {
		this.ciConsTypeKanal = ciConsTypeKanal;
	}

	public BigDecimal getCiConsTypeKanalTot() {
		return ciConsTypeKanalTot;
	}

	public void setCiConsTypeKanalTot(BigDecimal ciConsTypeKanalTot) {
		this.ciConsTypeKanalTot = ciConsTypeKanalTot;
	}

	public BigDecimal getCiConsTypeMarla() {
		return ciConsTypeMarla;
	}

	public void setCiConsTypeMarla(BigDecimal ciConsTypeMarla) {
		this.ciConsTypeMarla = ciConsTypeMarla;
	}

	public BigDecimal getCiConsTypeMarlaTot() {
		return ciConsTypeMarlaTot;
	}

	public void setCiConsTypeMarlaTot(BigDecimal ciConsTypeMarlaTot) {
		this.ciConsTypeMarlaTot = ciConsTypeMarlaTot;
	}

	public BigDecimal getCiConsTypeSarsai() {
		return ciConsTypeSarsai;
	}

	public void setCiConsTypeSarsai(BigDecimal ciConsTypeSarsai) {
		this.ciConsTypeSarsai = ciConsTypeSarsai;
	}

	public BigDecimal getCiConsTypeSarsaiTot() {
		return ciConsTypeSarsaiTot;
	}

	public void setCiConsTypeSarsaiTot(BigDecimal ciConsTypeSarsaiTot) {
		this.ciConsTypeSarsaiTot = ciConsTypeSarsaiTot;
	}

	public BigDecimal getCiConsTypeTotArea() {
		return ciConsTypeTotArea;
	}

	public void setCiConsTypeTotArea(BigDecimal ciConsTypeTotArea) {
		this.ciConsTypeTotArea = ciConsTypeTotArea;
	}

	public String getCiAquStatus() {
		return ciAquStatus;
	}

	public void setCiAquStatus(String ciAquStatus) {
		this.ciAquStatus = ciAquStatus;
	}

	public BigDecimal getCiTotArea() {
		return ciTotArea;
	}

	public void setCiTotArea(BigDecimal ciTotArea) {
		this.ciTotArea = ciTotArea;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
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

	public Long getDdz() {
		return ddz;
	}

	public void setDdz(Long ddz) {
		this.ddz = ddz;
	}

	public Long getDdz1() {
		return ddz1;
	}

	public void setDdz1(Long ddz1) {
		this.ddz1 = ddz1;
	}

	public Long getDdz2() {
		return ddz2;
	}

	public void setDdz2(Long ddz2) {
		this.ddz2 = ddz2;
	}

	public Long getDdz3() {
		return ddz3;
	}

	public void setDdz3(Long ddz3) {
		this.ddz3 = ddz3;
	}

	public List<LicenseApplicationPurposeTypeDetDTO> getLicenseApplicationPurposeTypeDetDTOList() {
		return licenseApplicationPurposeTypeDetDTOList;
	}

	public void setLicenseApplicationPurposeTypeDetDTOList(List<LicenseApplicationPurposeTypeDetDTO> licenseApplicationPurposeTypeDetDTOList) {
		this.licenseApplicationPurposeTypeDetDTOList = licenseApplicationPurposeTypeDetDTOList;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public List<LicenseApplicationFeeMasDTO> getFeeMasterDto() {
		return feeMasterDto;
	}

	public void setFeeMasterDto(List<LicenseApplicationFeeMasDTO> feeMasterDto) {
		this.feeMasterDto = feeMasterDto;
	}

	public List<DocumentDetailsVO> getApplicantCheckListDoc() {
		return applicantCheckListDoc;
	}

	public void setApplicantCheckListDoc(List<DocumentDetailsVO> applicantCheckListDoc) {
		this.applicantCheckListDoc = applicantCheckListDoc;
	}

	public List<DocumentDetailsVO> getLandScheduleCheckListDoc() {
		return landScheduleCheckListDoc;
	}

	public void setLandScheduleCheckListDoc(List<DocumentDetailsVO> landScheduleCheckListDoc) {
		this.landScheduleCheckListDoc = landScheduleCheckListDoc;
	}

	public List<DocumentDetailsVO> getLandDetailsCheckListDoc() {
		return landDetailsCheckListDoc;
	}

	public void setLandDetailsCheckListDoc(List<DocumentDetailsVO> landDetailsCheckListDoc) {
		this.landDetailsCheckListDoc = landDetailsCheckListDoc;
	}

	public Long getDdz4() {
		return ddz4;
	}

	public void setDdz4(Long ddz4) {
		this.ddz4 = ddz4;
	}

	public Long getDdz5() {
		return ddz5;
	}

	public void setDdz5(Long ddz5) {
		this.ddz5 = ddz5;
	}

	public List<DocumentDetailsVO> getPurposeAttachments() {
		return purposeAttachments;
	}

	public void setPurposeAttachments(List<DocumentDetailsVO> purposeAttachments) {
		this.purposeAttachments = purposeAttachments;
	}

	/*public List<AttachDocs> getPurposeAttachDocsList() {
		return purposeAttachDocsList;
	}

	public void setPurposeAttachDocsList(List<AttachDocs> purposeAttachDocsList) {
		this.purposeAttachDocsList = purposeAttachDocsList;
	}*/

	public Map<Long, Double> getFeeIds() {
		return feeIds;
	}

	public void setFeeIds(Map<Long, Double> feeIds) {
		this.feeIds = feeIds;
	}

	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}

	public BigDecimal getScrutinyFee() {
		return scrutinyFee;
	}

	public void setScrutinyFee(BigDecimal scrutinyFee) {
		this.scrutinyFee = scrutinyFee;
	}

	public BigDecimal getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(BigDecimal totalFees) {
		this.totalFees = totalFees;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public String getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(String diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getCaseNno() {
		return caseNno;
	}

	public void setCaseNno(String caseNno) {
		this.caseNno = caseNno;
	}

	public List<DocumentDetailsVO> getEncumbranceCheckListDoc() {
		return encumbranceCheckListDoc;
	}

	public void setEncumbranceCheckListDoc(List<DocumentDetailsVO> encumbranceCheckListDoc) {
		this.encumbranceCheckListDoc = encumbranceCheckListDoc;
	}

	public List<DocumentDetailsVO> getCourtOrdersLandCheckListDoc() {
		return courtOrdersLandCheckListDoc;
	}

	public void setCourtOrdersLandCheckListDoc(List<DocumentDetailsVO> courtOrdersLandCheckListDoc) {
		this.courtOrdersLandCheckListDoc = courtOrdersLandCheckListDoc;
	}

	public List<DocumentDetailsVO> getInsolvencyLandCheckListDoc() {
		return insolvencyLandCheckListDoc;
	}

	public void setInsolvencyLandCheckListDoc(List<DocumentDetailsVO> insolvencyLandCheckListDoc) {
		this.insolvencyLandCheckListDoc = insolvencyLandCheckListDoc;
	}

	public List<DocumentDetailsVO> getShajraAppLandCheckListDoc() {
		return shajraAppLandCheckListDoc;
	}

	public void setShajraAppLandCheckListDoc(List<DocumentDetailsVO> shajraAppLandCheckListDoc) {
		this.shajraAppLandCheckListDoc = shajraAppLandCheckListDoc;
	}

	public List<DocumentDetailsVO> getReleaseOrderCheckListDoc() {
		return releaseOrderCheckListDoc;
	}

	public void setReleaseOrderCheckListDoc(List<DocumentDetailsVO> releaseOrderCheckListDoc) {
		this.releaseOrderCheckListDoc = releaseOrderCheckListDoc;
	}

	public List<DocumentDetailsVO> getUsageAllotteesCheckListDoc() {
		return usageAllotteesCheckListDoc;
	}

	public void setUsageAllotteesCheckListDoc(List<DocumentDetailsVO> usageAllotteesCheckListDoc) {
		this.usageAllotteesCheckListDoc = usageAllotteesCheckListDoc;
	}

	public List<DocumentDetailsVO> getAccessNHSRCheckListDoc() {
		return accessNHSRCheckListDoc;
	}

	public void setAccessNHSRCheckListDoc(List<DocumentDetailsVO> accessNHSRCheckListDoc) {
		this.accessNHSRCheckListDoc = accessNHSRCheckListDoc;
	}

	public List<DocumentDetailsVO> getExistingApproachCheckListDoc() {
		return existingApproachCheckListDoc;
	}

	public void setExistingApproachCheckListDoc(List<DocumentDetailsVO> existingApproachCheckListDoc) {
		this.existingApproachCheckListDoc = existingApproachCheckListDoc;
	}

	public List<DocumentDetailsVO> getDGPSCheckListDoc() {
		return DGPSCheckListDoc;
	}

	public void setDGPSCheckListDoc(List<DocumentDetailsVO> dGPSCheckListDoc) {
		DGPSCheckListDoc = dGPSCheckListDoc;
	}

	public String getApplicationNoEService() {
		return applicationNoEService;
	}

	public void setApplicationNoEService(String applicationNoEService) {
		this.applicationNoEService = applicationNoEService;
	}

	public LicenseApplicationLandCategoryDTO getLandCategoryDTO() {
		return landCategoryDTO;
	}

	public void setLandCategoryDTO(LicenseApplicationLandCategoryDTO landCategoryDTO) {
		this.landCategoryDTO = landCategoryDTO;
	}

	public String getWorkflowStatus() {
		return workflowStatus;
	}

	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}

	public String getLicenseFeeStr() {
		return licenseFeeStr;
	}

	public void setLicenseFeeStr(String licenseFeeStr) {
		this.licenseFeeStr = licenseFeeStr;
	}

	public String getScrutinyFeeStr() {
		return scrutinyFeeStr;
	}

	public void setScrutinyFeeStr(String scrutinyFeeStr) {
		this.scrutinyFeeStr = scrutinyFeeStr;
	}

	public String getTotalFeesStr() {
		return totalFeesStr;
	}

	public void setTotalFeesStr(String totalFeesStr) {
		this.totalFeesStr = totalFeesStr;
	}

	
}
