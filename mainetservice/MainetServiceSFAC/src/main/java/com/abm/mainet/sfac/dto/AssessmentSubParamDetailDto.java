/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class AssessmentSubParamDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9049635245412560588L;

	private Long assSpdId;

	@JsonIgnore
	private Long AssessmentSubParameterDto;

	private Long condition;

	private String conditionDesc;

	private BigDecimal subWeightage;

	private BigDecimal overallScore;

	private BigDecimal score;

	private Long regiFpoCriteria;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;
	
	
	/**
	 * @return the assSpdId
	 */
	public Long getAssSpdId() {
		return assSpdId;
	}

	/**
	 * @param assSpdId the assSpdId to set
	 */
	public void setAssSpdId(Long assSpdId) {
		this.assSpdId = assSpdId;
	}

	/**
	 * @return the assessmentSubParameterDto
	 */
	public Long getAssessmentSubParameterDto() {
		return AssessmentSubParameterDto;
	}

	/**
	 * @param assessmentSubParameterDto the assessmentSubParameterDto to set
	 */
	public void setAssessmentSubParameterDto(Long assessmentSubParameterDto) {
		AssessmentSubParameterDto = assessmentSubParameterDto;
	}

	/**
	 * @return the condition
	 */
	public Long getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Long condition) {
		this.condition = condition;
	}

	/**
	 * @return the conditionDesc
	 */
	public String getConditionDesc() {
		return conditionDesc;
	}

	/**
	 * @param conditionDesc the conditionDesc to set
	 */
	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	/**
	 * @return the overallScore
	 */
	public BigDecimal getOverallScore() {
		return overallScore;
	}

	/**
	 * @return the subWeightage
	 */
	public BigDecimal getSubWeightage() {
		return subWeightage;
	}

	/**
	 * @param subWeightage the subWeightage to set
	 */
	public void setSubWeightage(BigDecimal subWeightage) {
		this.subWeightage = subWeightage;
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
	 * @return the regiFpoCriteria
	 */
	public Long getRegiFpoCriteria() {
		return regiFpoCriteria;
	}

	/**
	 * @param regiFpoCriteria the regiFpoCriteria to set
	 */
	public void setRegiFpoCriteria(Long regiFpoCriteria) {
		this.regiFpoCriteria = regiFpoCriteria;
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

	
}
