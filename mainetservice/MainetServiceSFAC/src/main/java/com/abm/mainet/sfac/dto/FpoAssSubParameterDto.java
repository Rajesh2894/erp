/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class FpoAssSubParameterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -449922753243846770L;

	private Long assSId;

	@JsonIgnore
	private FpoAssKeyParameterDto fpoAssKeyParameterDto;

	private Long subParameter;

	private String subParameterDesc;

	private Long applicationId;

	private Long assStatus;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	List<FpoAssSubParamDetailDto> fpoSubParamDetailDtoList = new ArrayList<>();

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
	 * @return the fpoAssKeyParameterDto
	 */
	public FpoAssKeyParameterDto getFpoAssKeyParameterDto() {
		return fpoAssKeyParameterDto;
	}

	/**
	 * @param fpoAssKeyParameterDto the fpoAssKeyParameterDto to set
	 */
	public void setFpoAssKeyParameterDto(FpoAssKeyParameterDto fpoAssKeyParameterDto) {
		this.fpoAssKeyParameterDto = fpoAssKeyParameterDto;
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
	 * @return the assStatus
	 */
	public Long getAssStatus() {
		return assStatus;
	}

	/**
	 * @param assStatus the assStatus to set
	 */
	public void setAssStatus(Long assStatus) {
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
	 * @return the fpoSubParamDetailDtoList
	 */
	public List<FpoAssSubParamDetailDto> getFpoSubParamDetailDtoList() {
		return fpoSubParamDetailDtoList;
	}

	/**
	 * @param fpoSubParamDetailDtoList the fpoSubParamDetailDtoList to set
	 */
	public void setFpoSubParamDetailDtoList(List<FpoAssSubParamDetailDto> fpoSubParamDetailDtoList) {
		this.fpoSubParamDetailDtoList = fpoSubParamDetailDtoList;
	}

}
