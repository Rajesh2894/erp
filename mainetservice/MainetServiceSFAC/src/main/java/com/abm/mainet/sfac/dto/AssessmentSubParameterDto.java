/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.aspose.slides.Collections.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class AssessmentSubParameterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 365699851082865130L;

	private Long assSId;

	@JsonIgnore
	private Long AssessmentKeyParameterDto;

	private Long subParameter;

	private Long subWeightage;

	private Long meansOfVerification;

	private Long pending;

	private Long approved;

	private BigDecimal overallScore;

	private BigDecimal score;

	private String yesNo;

	private Long applicationId;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String subParameterDesc;

	private String meansOfVerificationDesc;

	List<AssessmentSubParamDetailDto> assSubParamDetailDtoList = new ArrayList();

	/**
	 * @return the subParameterDesc
	 */
	public String getSubParameterDesc() {
		return subParameterDesc;
	}

	/**
	 * @param subParameterDesc the subParameterDesc to set
	 */
	public void setSubParameterDesc(String subParameterDesc) {
		this.subParameterDesc = subParameterDesc;
	}

	/**
	 * @return the meansOfVerificationDesc
	 */
	public String getMeansOfVerificationDesc() {
		return meansOfVerificationDesc;
	}

	/**
	 * @param meansOfVerificationDesc the meansOfVerificationDesc to set
	 */
	public void setMeansOfVerificationDesc(String meansOfVerificationDesc) {
		this.meansOfVerificationDesc = meansOfVerificationDesc;
	}

	/**
	 * @return the subParameter
	 */
	public Long getSubParameter() {
		return subParameter;
	}

	/**
	 * @param subParameter the subParameter to set
	 */
	public void setSubParameter(Long subParameter) {
		this.subParameter = subParameter;
	}

	/**
	 * @return the subWeightage
	 */
	public Long getSubWeightage() {
		return subWeightage;
	}

	/**
	 * @param subWeightage the subWeightage to set
	 */
	public void setSubWeightage(Long subWeightage) {
		this.subWeightage = subWeightage;
	}

	/**
	 * @return the meansOfVerification
	 */
	public Long getMeansOfVerification() {
		return meansOfVerification;
	}

	/**
	 * @param meansOfVerification the meansOfVerification to set
	 */
	public void setMeansOfVerification(Long meansOfVerification) {
		this.meansOfVerification = meansOfVerification;
	}

	/**
	 * @return the pending
	 */
	public Long getPending() {
		return pending;
	}

	/**
	 * @param pending the pending to set
	 */
	public void setPending(Long pending) {
		this.pending = pending;
	}

	/**
	 * @return the approved
	 */
	public Long getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Long approved) {
		this.approved = approved;
	}

	/**
	 * @return the overallScore
	 */
	public BigDecimal getOverallScore() {
		return overallScore;
	}

	/**
	 * @param overallScore the overallScore to set
	 */
	public void setOverallScore(BigDecimal overallScore) {
		this.overallScore = overallScore;
	}

	/**
	 * @return the score
	 */
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	/**
	 * @return the yesNo
	 */
	public String getYesNo() {
		return yesNo;
	}

	/**
	 * @param yesNo the yesNo to set
	 */
	public void setYesNo(String yesNo) {
		this.yesNo = yesNo;
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
	 * @return the assSId
	 */
	public Long getAssSId() {
		return assSId;
	}

	/**
	 * @param assSId the assSId to set
	 */
	public void setAssSId(Long assSId) {
		this.assSId = assSId;
	}

	/**
	 * @return the assessmentKeyParameterDto
	 */
	public Long getAssessmentKeyParameterDto() {
		return AssessmentKeyParameterDto;
	}

	/**
	 * @param assessmentKeyParameterDto the assessmentKeyParameterDto to set
	 */
	public void setAssessmentKeyParameterDto(Long assessmentKeyParameterDto) {
		AssessmentKeyParameterDto = assessmentKeyParameterDto;
	}

	/**
	 * @return the assSubParamDetailDtoList
	 */
	public List<AssessmentSubParamDetailDto> getAssSubParamDetailDtoList() {
		return assSubParamDetailDtoList;
	}

	/**
	 * @param assSubParamDetailDtoList the assSubParamDetailDtoList to set
	 */
	public void setAssSubParamDetailDtoList(List<AssessmentSubParamDetailDto> assSubParamDetailDtoList) {
		this.assSubParamDetailDtoList = assSubParamDetailDtoList;
	}

}
