package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class CFCNursingHomeInfoDTO implements Serializable {

	private static final long serialVersionUID = 5055032995976426497L;
	private Long hospId;
	private Long apmApplicationId;
	private Long hospitalType;
	private String doctorName;
	private Long mtrntyBedCount;
	private Long otherBedCount;
	private Long nursingBedCount;
	private Long totalBedCount;
	private String nameAddClinic;
	private String nameAddHospital;
	private Long contactNoClinic;
	private Long contactNoHospital;
	private String eduEligibility;
	private String regNo;
	private Date regDate;
	private Long doctorCount;
	private Long nurseCount;
	private Long empCount;
	private Long securityCount;
	private Double yearsOfOperation;
	private String regMNP;
	private String abortionCntrFlag;
	private String visitingDoctorName;
	private Long deptId;
	private String regNotaFiberDeseaseFlag;

	private String regBranchANdState;
	private Date businessStartDate;
	private String medicalProfType;
	private Double complYears;

	private Long orgId;
	private Date creationDate;
	private Long createdBy;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long updatedBy;
	private Date updatedDate;
	private List<DocumentDetailsVO> attachments = new ArrayList<>();
	private List<DocumentDetailsVO> documentList;
	private List<CFCNursingHomeInfoDetailDTO> cfcHospitalInfoDetailDTOs = new ArrayList<CFCNursingHomeInfoDetailDTO>();

	private Long cfcWard1;
	private Long cfcWard2;
	private Long cfcWard3;
	private Long cfcWard4;
	private Long cfcWard5;
	private String birthRegstatus;
	private String birthRegremark;
	private Long smServiceId;

	public String getRegBranchANdState() {
		return regBranchANdState;
	}

	public void setRegBranchANdState(String regBranchANdState) {
		this.regBranchANdState = regBranchANdState;
	}

	public Date getBusinessStartDate() {
		return businessStartDate;
	}

	public void setBusinessStartDate(Date businessStartDate) {
		this.businessStartDate = businessStartDate;
	}

	public String getMedicalProfType() {
		return medicalProfType;
	}

	public void setMedicalProfType(String medicalProfType) {
		this.medicalProfType = medicalProfType;
	}

	public Double getComplYears() {
		return complYears;
	}

	public void setComplYears(Double complYears) {
		this.complYears = complYears;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
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

	public void setBirthRegstatus(String birthRegstatus) {
		this.birthRegstatus = birthRegstatus;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}

	public Long getCfcWard1() {
		return cfcWard1;
	}

	public void setCfcWard1(Long cfcWard1) {
		this.cfcWard1 = cfcWard1;
	}

	public Long getCfcWard2() {
		return cfcWard2;
	}

	public void setCfcWard2(Long cfcWard2) {
		this.cfcWard2 = cfcWard2;
	}

	public Long getCfcWard3() {
		return cfcWard3;
	}

	public void setCfcWard3(Long cfcWard3) {
		this.cfcWard3 = cfcWard3;
	}

	public Long getCfcWard4() {
		return cfcWard4;
	}

	public void setCfcWard4(Long cfcWard4) {
		this.cfcWard4 = cfcWard4;
	}

	public Long getCfcWard5() {
		return cfcWard5;
	}

	public void setCfcWard5(Long cfcWard5) {
		this.cfcWard5 = cfcWard5;
	}

	public List<CFCNursingHomeInfoDetailDTO> getCfcHospitalInfoDetailDTOs() {
		return cfcHospitalInfoDetailDTOs;
	}

	public void setCfcHospitalInfoDetailDTOs(List<CFCNursingHomeInfoDetailDTO> cfcHospitalInfoDetailDTOs) {
		this.cfcHospitalInfoDetailDTOs = cfcHospitalInfoDetailDTOs;
	}

	public Long getHospId() {
		return hospId;
	}

	public void setHospId(Long hospId) {
		this.hospId = hospId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(Long hospitalType) {
		this.hospitalType = hospitalType;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public Long getMtrntyBedCount() {
		return mtrntyBedCount;
	}

	public void setMtrntyBedCount(Long mtrntyBedCount) {
		this.mtrntyBedCount = mtrntyBedCount;
	}

	public Long getOtherBedCount() {
		return otherBedCount;
	}

	public void setOtherBedCount(Long otherBedCount) {
		this.otherBedCount = otherBedCount;
	}

	public Long getNursingBedCount() {
		return nursingBedCount;
	}

	public void setNursingBedCount(Long nursingBedCount) {
		this.nursingBedCount = nursingBedCount;
	}

	public Long getTotalBedCount() {
		return totalBedCount;
	}

	public void setTotalBedCount(Long totalBedCount) {
		this.totalBedCount = totalBedCount;
	}

	public String getNameAddClinic() {
		return nameAddClinic;
	}

	public void setNameAddClinic(String nameAddClinic) {
		this.nameAddClinic = nameAddClinic;
	}

	public String getNameAddHospital() {
		return nameAddHospital;
	}

	public void setNameAddHospital(String nameAddHospital) {
		this.nameAddHospital = nameAddHospital;
	}

	public Long getContactNoClinic() {
		return contactNoClinic;
	}

	public void setContactNoClinic(Long contactNoClinic) {
		this.contactNoClinic = contactNoClinic;
	}

	public Long getContactNoHospital() {
		return contactNoHospital;
	}

	public void setContactNoHospital(Long contactNoHospital) {
		this.contactNoHospital = contactNoHospital;
	}

	public String getEduEligibility() {
		return eduEligibility;
	}

	public void setEduEligibility(String eduEligibility) {
		this.eduEligibility = eduEligibility;
	}

	public String getRegNo() {
		return regNo;
	}

	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Long getDoctorCount() {
		return doctorCount;
	}

	public void setDoctorCount(Long doctorCount) {
		this.doctorCount = doctorCount;
	}

	public Long getNurseCount() {
		return nurseCount;
	}

	public void setNurseCount(Long nurseCount) {
		this.nurseCount = nurseCount;
	}

	public Long getEmpCount() {
		return empCount;
	}

	public void setEmpCount(Long empCount) {
		this.empCount = empCount;
	}

	public Long getSecurityCount() {
		return securityCount;
	}

	public void setSecurityCount(Long securityCount) {
		this.securityCount = securityCount;
	}

	public Double getYearsOfOperation() {
		return yearsOfOperation;
	}

	public void setYearsOfOperation(Double yearsOfOperation) {
		this.yearsOfOperation = yearsOfOperation;
	}

	public String getRegMNP() {
		return regMNP;
	}

	public void setRegMNP(String regMNP) {
		this.regMNP = regMNP;
	}

	public String getAbortionCntrFlag() {
		return abortionCntrFlag;
	}

	public void setAbortionCntrFlag(String abortionCntrFlag) {
		this.abortionCntrFlag = abortionCntrFlag;
	}

	public String getVisitingDoctorName() {
		return visitingDoctorName;
	}

	public void setVisitingDoctorName(String visitingDoctorName) {
		this.visitingDoctorName = visitingDoctorName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getRegNotaFiberDeseaseFlag() {
		return regNotaFiberDeseaseFlag;
	}

	public void setRegNotaFiberDeseaseFlag(String regNotaFiberDeseaseFlag) {
		this.regNotaFiberDeseaseFlag = regNotaFiberDeseaseFlag;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

}
