package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_cfc_hospital_info")
public class CFCNursingHomeInfoEntity implements Serializable {

	private static final long serialVersionUID = 4708137746053888329L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "hsptl_id")
	private Long hospId;

	@OneToMany(mappedBy = "cfcHospitalInfoEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CFCNursingHomeDetailEntity> cfcHospitalDetInfoEntities = new ArrayList<>();

	@Column(name = "APM_APPLICATION_ID", nullable = false)
	private Long apmApplicationId;

	@Column(name = "hsptl_type", nullable = false)
	private Long hospitalType;

	@Column(name = "doct_name", nullable = false)
	private String doctorName;

	@Column(name = "mtrnty_bed_cnt", nullable = false)
	private Long mtrntyBedCount;

	@Column(name = "othr_bed_cnt", nullable = false)
	private Long otherBedCount;

	@Column(name = "nrsng_bed_cnt", nullable = false)
	private Long nursingBedCount;

	@Column(name = "totl_bed_cnt", nullable = false)
	private Long totalBedCount;

	@Column(name = "nm_add_clnc", nullable = false)
	private String nameAddClinic;

	@Column(name = "nm_add_hsptl", nullable = false)
	private String nameAddHospital;

	@Column(name = "cont_no_clinic", nullable = true)
	private Long contactNoClinic;

	@Column(name = "cont_no_hsptl", nullable = true)
	private Long contactNoHospital;

	@Column(name = "edu_elg", nullable = false)
	private String eduEligibility;

	@Column(name = "reg_no", nullable = false)
	private String regNo;

	@Column(name = "reg_date", nullable = false)
	private Date regDate;

	@Column(name = "doct_cnt", nullable = false)
	private Long doctorCount;

	@Column(name = "nurse_cnt", nullable = false)
	private Long nurseCount;

	@Column(name = "emp_cnt", nullable = false)
	private Long empCount;

	@Column(name = "sec_cnt", nullable = false)
	private Long securityCount;

	@Column(name = "yers_opertn", nullable = false)
	private Double yearsOfOperation;

	@Column(name = "reg_mnp", nullable = false)
	private String regMNP;

	@Column(name = "abrtn_cntr_flag", nullable = false)
	private String abortionCntrFlag;

	@Column(name = "vstng_doctr_nm", nullable = false)
	private String visitingDoctorName;

	@Column(name = "dept", nullable = false)
	private Long deptId;

	@Column(name = "reg_branch_nm", nullable = false)
	private String regBranchANdState;

	@Column(name = "business_strt_dt", nullable = false)
	private Date businessStartDate;

	@Column(name = "medical_prof_type", nullable = false)
	private String medicalProfType;

	@Column(name = "business_years", nullable = false)
	private Double complYears;

	@Column(name = "reg_nota_fbr_des", nullable = false)
	private String regNotaFiberDeseaseFlag;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	public static String[] getPkValues() {
		return new String[] { "CFC", "tb_cfc_hospital_info", "hsptl_id" };
	}

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

	public List<CFCNursingHomeDetailEntity> getCfcHospitalDetInfoEntities() {
		return cfcHospitalDetInfoEntities;
	}

	public void setCfcHospitalDetInfoEntities(List<CFCNursingHomeDetailEntity> cfcHospitalDetInfoEntities) {
		this.cfcHospitalDetInfoEntities = cfcHospitalDetInfoEntities;
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
