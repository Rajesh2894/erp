/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;

/**
 * @author pooja.maske
 *
 */

public class AssessmentMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4751078979349087323L;

	private Long assId;

	private Long applicationId;

	private String remark;

	private String assessmentNo;

	private String assStatus;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private int langId;

	private String userName;

	private String email;

	private String mobileNo;

	private Long iaId;

	private String iaName;

	private Long finYrId;

	private Long cbboId;

	private String cbboName;

	private String alcYearDesc;

	List<AssessmentKeyParameterDto> assementKeyParamDtoList = new ArrayList<>();

	

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private Long fpoRegCount;

	private Long fpoRegPendCount;

	private Long fpoAllcTarget;

	private String cbboUniqueId;

	private String assessmentDate;

	private Date assDate;

	private String status;

	private Long totalFpoFullFilling;

	private BigDecimal totalOverallScore;

	private BigDecimal totalScore;

	/**
	 * @return the assId
	 */
	public Long getAssId() {
		return assId;
	}

	/**
	 * @param assId the assId to set
	 */
	public void setAssId(Long assId) {
		this.assId = assId;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the assessmentNo
	 */
	public String getAssessmentNo() {
		return assessmentNo;
	}

	/**
	 * @param assessmentNo the assessmentNo to set
	 */
	public void setAssessmentNo(String assessmentNo) {
		this.assessmentNo = assessmentNo;
	}

	/**
	 * @return the assStatus
	 */
	public String getAssStatus() {
		return assStatus;
	}

	/**
	 * @param assStatus the assStatus to set
	 */
	public void setAssStatus(String assStatus) {
		this.assStatus = assStatus;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the assementKeyParamDtoList
	 */
	public List<AssessmentKeyParameterDto> getAssementKeyParamDtoList() {
		return assementKeyParamDtoList;
	}

	/**
	 * @param assementKeyParamDtoList the assementKeyParamDtoList to set
	 */
	public void setAssementKeyParamDtoList(List<AssessmentKeyParameterDto> assementKeyParamDtoList) {
		this.assementKeyParamDtoList = assementKeyParamDtoList;
	}


	/**
	 * @return the langId
	 */
	public int getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(int langId) {
		this.langId = langId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the iaId
	 */
	public Long getIaId() {
		return iaId;
	}

	/**
	 * @param iaId the iaId to set
	 */
	public void setIaId(Long iaId) {
		this.iaId = iaId;
	}

	/**
	 * @return the iaName
	 */
	public String getIaName() {
		return iaName;
	}

	/**
	 * @param iaName the iaName to set
	 */
	public void setIaName(String iaName) {
		this.iaName = iaName;
	}

	/**
	 * @return the finYrId
	 */
	public Long getFinYrId() {
		return finYrId;
	}

	/**
	 * @param finYrId the finYrId to set
	 */
	public void setFinYrId(Long finYrId) {
		this.finYrId = finYrId;
	}

	/**
	 * @return the applicantDetailDto
	 */
	public ApplicantDetailDTO getApplicantDetailDto() {
		return applicantDetailDto;
	}

	/**
	 * @param applicantDetailDto the applicantDetailDto to set
	 */
	public void setApplicantDetailDto(ApplicantDetailDTO applicantDetailDto) {
		this.applicantDetailDto = applicantDetailDto;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
	}

	/**
	 * @return the alcYearDesc
	 */
	public String getAlcYearDesc() {
		return alcYearDesc;
	}

	/**
	 * @param alcYearDesc the alcYearDesc to set
	 */
	public void setAlcYearDesc(String alcYearDesc) {
		this.alcYearDesc = alcYearDesc;
	}

	/**
	 * @return the fpoRegCount
	 */
	public Long getFpoRegCount() {
		return fpoRegCount;
	}

	/**
	 * @param fpoRegCount the fpoRegCount to set
	 */
	public void setFpoRegCount(Long fpoRegCount) {
		this.fpoRegCount = fpoRegCount;
	}

	/**
	 * @return the fpoRegPendCount
	 */
	public Long getFpoRegPendCount() {
		return fpoRegPendCount;
	}

	/**
	 * @param fpoRegPendCount the fpoRegPendCount to set
	 */
	public void setFpoRegPendCount(Long fpoRegPendCount) {
		this.fpoRegPendCount = fpoRegPendCount;
	}

	/**
	 * @return the fpoAllcTarget
	 */
	public Long getFpoAllcTarget() {
		return fpoAllcTarget;
	}

	/**
	 * @param fpoAllcTarget the fpoAllcTarget to set
	 */
	public void setFpoAllcTarget(Long fpoAllcTarget) {
		this.fpoAllcTarget = fpoAllcTarget;
	}

	/**
	 * @return the cbboUniqueId
	 */
	public String getCbboUniqueId() {
		return cbboUniqueId;
	}

	/**
	 * @param cbboUniqueId the cbboUniqueId to set
	 */
	public void setCbboUniqueId(String cbboUniqueId) {
		this.cbboUniqueId = cbboUniqueId;
	}

	/**
	 * @return the assessmentDate
	 */
	public String getAssessmentDate() {
		return assessmentDate;
	}

	/**
	 * @param assessmentDate the assessmentDate to set
	 */
	public void setAssessmentDate(String assessmentDate) {
		this.assessmentDate = assessmentDate;
	}

	/**
	 * @return the assDate
	 */
	public Date getAssDate() {
		return assDate;
	}

	/**
	 * @param assDate the assDate to set
	 */
	public void setAssDate(Date assDate) {
		this.assDate = assDate;
	}

	/**
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the totalFpoFullFilling
	 */
	public Long getTotalFpoFullFilling() {
		return totalFpoFullFilling;
	}

	/**
	 * @param totalFpoFullFilling the totalFpoFullFilling to set
	 */
	public void setTotalFpoFullFilling(Long totalFpoFullFilling) {
		this.totalFpoFullFilling = totalFpoFullFilling;
	}

	/**
	 * @return the totalOverallScore
	 */
	public BigDecimal getTotalOverallScore() {
		return totalOverallScore;
	}

	/**
	 * @param totalOverallScore the totalOverallScore to set
	 */
	public void setTotalOverallScore(BigDecimal totalOverallScore) {
		this.totalOverallScore = totalOverallScore;
	}

	/**
	 * @return the totalScore
	 */
	public BigDecimal getTotalScore() {
		return totalScore;
	}

	/**
	 * @param totalScore the totalScore to set
	 */
	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

}
