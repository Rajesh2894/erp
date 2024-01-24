package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;


@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class BirthRegistrationCorrDTO implements Serializable {

	private static final long serialVersionUID = -4100833798784520499L;

	// Fields As Per Birth Registration Entity
	private Long brId;
	private Long orgId;
	private Long wardid;
	private String brRegNo;
	private Long brRefId;
	private Date brRegDate;
	private Date brRegDate1;
	private String brChildName;
	private Date brDob;
	private String brSex;
	private String brBirthPlace;
	private String brBirthAddr;
	private String brInformantName;
	private String brInformantAddr;
	private Double BrBirthWt;
	private String brAdopFlg;
	private Date brAdopDate;
	private String brCorrectionFlg;
	private Date brCorrnDate;
	private Long cpdRefTypeId;
	private Long brPregDuratn;
	private String brFdeathCause;
	private Long cpdAttntypeId;
	private Long cpdDelMethId;
	private String brCertNo;
	private Long hiId;
	private String brRemarks;
	private Long userId;
	private int langId;
	private Date lmodDate;
	private String brStatus;
	private Long updatedBy;
	private Date updatedDate;
	private String brSuppNo;
	private String brTime;
	private Date brSuppdate;
	private String brSuppTime;
	private String brBirthMark;
	private String brChildNameMar;
	private String brBirthPlaceMar;
	private String brBirthAddrMar;
	private String brInformantNameMar;
	private String brInformantAddrMar;
	private String brStillBirthFlg;
	private Date brStillBirthDate;
	private String brBirthPlaceType;
	private String brHospital;
	private String hRId;
	private String fileName;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String brFlag;
	private String brOrphanReg;
	private String brPoliceStation;
	private String brAddress;
	private String brFirNumber;
	private Long codUserWardId;
	private Long upldId;
	private Long brManualCertNo;
	private String bcrFlag;
	private String hrReg;
	private Long authBy;
	private Date authDate;
	private String authFlag;
	private String authRemark;
	private String pgFlg;
	private Long noOfCopies;
	private Long alreayIssuedCopy;
	private String unAuthRflg;
	private Date regAplDate;
	private String year;
	private String applicationId;
	private Long apmApplicationId;
	private Long deptId;
	private Long serviceId;
	private boolean freeService;
	//parents details
	private String pdFathername;
	private String pdFathernameMar;
	private String pdMothername;
	private String pdMothernameMar;
	private String pdParaddress;
	private String pdParaddressMar;
	
	private Long cpdReligionId;
	private Long cpdFEducnId;
	private Long cpdMEducnId;
	private Long cpdFOccuId;
	private Long cpdMOccuId;
	private Long pdAgeAtBirth;
	private Long pdLiveChildn;

	private List<DocumentDetailsVO> uploadDocument;
	private ApplicantDetailDTO applicantDetailsDto;
	private Long amount;
	private String paymentMode;
	private List<DocumentDetailsVO> documentList = new ArrayList<>();

	private ParentDetailDTO parentDetailDTO = new ParentDetailDTO();
	
	private String birthRegstatus;
	private String birthRegremark;
	
	private String BirthWfStatus;	
	private String  corrCategory;
	private Long pdRegUnitId;
	private Long bndDw1;
	private Long bndDw2;
	private Long bndDw3;
	private Long bndDw4;
	private Long bndDw5;

	// Getter And Setters Per Birth Registration Entity
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

	public Date getBrRegDate1() {
		return brRegDate1;
	}

	public void setBrRegDate1(Date brRegDate1) {
		this.brRegDate1 = brRegDate1;
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

	// Getter And Setters As Per Parent Detail Entity
	public ParentDetailDTO getParentDetailDTO() {
		return parentDetailDTO;
	}

	public void setParentDetailDTO(ParentDetailDTO parentDetailDTO) {
		this.parentDetailDTO = parentDetailDTO;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	public List<DocumentDetailsVO> getUploadDocument() {
		return uploadDocument;
	}

	public void setUploadDocument(List<DocumentDetailsVO> uploadDocument) {
		this.uploadDocument = uploadDocument;
	}

	public ApplicantDetailDTO getApplicantDetailsDto() {
		return applicantDetailsDto;
	}

	public void setApplicantDetailsDto(ApplicantDetailDTO applicantDetailsDto) {
		this.applicantDetailsDto = applicantDetailsDto;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Long getAlreayIssuedCopy() {
		return alreayIssuedCopy;
	}

	public void setAlreayIssuedCopy(Long alreayIssuedCopy) {
		this.alreayIssuedCopy = alreayIssuedCopy;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getBirthRegstatus() {
		return birthRegstatus;
	}

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public String getBirthRegremark() {
		return birthRegremark;
	}

	public void setBirthRegremark(String birthRegremark) {
		this.birthRegremark = birthRegremark;
	}

	public String getBirthWfStatus() {
		return BirthWfStatus;
	}

	public void setBirthWfStatus(String birthWfStatus) {
		BirthWfStatus = birthWfStatus;
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

	public Long getCpdReligionId() {
		return cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}

	public Long getCpdFEducnId() {
		return cpdFEducnId;
	}

	public void setCpdFEducnId(Long cpdFEducnId) {
		this.cpdFEducnId = cpdFEducnId;
	}

	public Long getCpdMEducnId() {
		return cpdMEducnId;
	}

	public void setCpdMEducnId(Long cpdMEducnId) {
		this.cpdMEducnId = cpdMEducnId;
	}

	public Long getCpdFOccuId() {
		return cpdFOccuId;
	}

	public void setCpdFOccuId(Long cpdFOccuId) {
		this.cpdFOccuId = cpdFOccuId;
	}

	public Long getCpdMOccuId() {
		return cpdMOccuId;
	}

	public void setCpdMOccuId(Long cpdMOccuId) {
		this.cpdMOccuId = cpdMOccuId;
	}

	public Long getPdAgeAtBirth() {
		return pdAgeAtBirth;
	}

	public void setPdAgeAtBirth(Long pdAgeAtBirth) {
		this.pdAgeAtBirth = pdAgeAtBirth;
	}

	public Long getPdLiveChildn() {
		return pdLiveChildn;
	}

	public void setPdLiveChildn(Long pdLiveChildn) {
		this.pdLiveChildn = pdLiveChildn;
	}

	public String getCorrCategory() {
		return corrCategory;
	}

	public void setCorrCategory(String corrCategory) {
		this.corrCategory = corrCategory;
	}
	
	public Long getPdRegUnitId() {
		return pdRegUnitId;
	}

	public void setPdRegUnitId(Long pdRegUnitId) {
		this.pdRegUnitId = pdRegUnitId;
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
	
	

}
