/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class AssessmentKeyParameterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1119405325901550635L;

	private Long assKId;

	@JsonIgnore
	private Long AssessmentMasterDto;

	private Long keyParameter;

	private String keyParameterDesc;

	private BigDecimal Weightage;

	private String assStatus;

	private Long applicationId;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	List<AssessmentSubParameterDto> assSubParamDtoList = new ArrayList<>();

	/**
	 * @return the assKId
	 */
	public Long getAssKId() {
		return assKId;
	}

	/**
	 * @param assKId the assKId to set
	 */
	public void setAssKId(Long assKId) {
		this.assKId = assKId;
	}

	/**
	 * @return the assessmentMasterDto
	 */
	public Long getAssessmentMasterDto() {
		return AssessmentMasterDto;
	}

	/**
	 * @param assessmentMasterDto the assessmentMasterDto to set
	 */
	public void setAssessmentMasterDto(Long assessmentMasterDto) {
		AssessmentMasterDto = assessmentMasterDto;
	}

	/**
	 * @return the keyParameter
	 */
	public Long getKeyParameter() {
		return keyParameter;
	}

	/**
	 * @param keyParameter the keyParameter to set
	 */
	public void setKeyParameter(Long keyParameter) {
		this.keyParameter = keyParameter;
	}

	/**
	 * @return the keyParameterDesc
	 */
	public String getKeyParameterDesc() {
		return keyParameterDesc;
	}

	/**
	 * @param keyParameterDesc the keyParameterDesc to set
	 */
	public void setKeyParameterDesc(String keyParameterDesc) {
		this.keyParameterDesc = keyParameterDesc;
	}

	/**
	 * @return the weightage
	 */
	public BigDecimal getWeightage() {
		return Weightage;
	}

	/**
	 * @param weightage the weightage to set
	 */
	public void setWeightage(BigDecimal weightage) {
		Weightage = weightage;
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
	 * @return the assSubParamDtoList
	 */
	public List<AssessmentSubParameterDto> getAssSubParamDtoList() {
		return assSubParamDtoList;
	}

	/**
	 * @param assSubParamDtoList the assSubParamDtoList to set
	 */
	public void setAssSubParamDtoList(List<AssessmentSubParameterDto> assSubParamDtoList) {
		this.assSubParamDtoList = assSubParamDtoList;
	}

}
