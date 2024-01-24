/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author pooja.maske
 *
 */
public class FPOAdministrativeDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -83802637816570282L;

	private Long adId;

	@JsonIgnore
	private FPOMasterDto masterDto;

	private Long dsgId;

	private Long titleId;

	private String name;

	private String emailId;

	private String contactNo;

	private Date dateOfJoining;

	private Long nameOfBoard;

	private String din;

	private Long orgId;

	private Date createdDate;

	private Long createdBy;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long applicationId;

	private String appStatus;

	private String designation;

	private String titleDesc;

	private String dofJoiningDesc;

	private String nameOfBoardDesc;

	/**
	 * @return the adId
	 */
	public Long getAdId() {
		return adId;
	}

	/**
	 * @param adId the adId to set
	 */
	public void setAdId(Long adId) {
		this.adId = adId;
	}

	/**
	 * @return the masterDto
	 */
	public FPOMasterDto getMasterDto() {
		return masterDto;
	}

	/**
	 * @param masterDto the masterDto to set
	 */
	public void setMasterDto(FPOMasterDto masterDto) {
		this.masterDto = masterDto;
	}

	/**
	 * @return the dsgId
	 */
	public Long getDsgId() {
		return dsgId;
	}

	/**
	 * @param dsgId the dsgId to set
	 */
	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
	}

	/**
	 * @return the titleId
	 */
	public Long getTitleId() {
		return titleId;
	}

	/**
	 * @param titleId the titleId to set
	 */
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the contactNo
	 */
	public String getContactNo() {
		return contactNo;
	}

	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	/**
	 * @return the dateOfJoining
	 */
	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	/**
	 * @param dateOfJoining the dateOfJoining to set
	 */
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
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
	 * @return the nameOfBoard
	 */
	public Long getNameOfBoard() {
		return nameOfBoard;
	}

	/**
	 * @param nameOfBoard the nameOfBoard to set
	 */
	public void setNameOfBoard(Long nameOfBoard) {
		this.nameOfBoard = nameOfBoard;
	}

	/**
	 * @return the din
	 */
	public String getDin() {
		return din;
	}

	/**
	 * @param din the din to set
	 */
	public void setDin(String din) {
		this.din = din;
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
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}

	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * @return the titleDesc
	 */
	public String getTitleDesc() {
		return titleDesc;
	}

	/**
	 * @param titleDesc the titleDesc to set
	 */
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	/**
	 * @return the dofJoiningDesc
	 */
	public String getDofJoiningDesc() {
		return dofJoiningDesc;
	}

	/**
	 * @param dofJoiningDesc the dofJoiningDesc to set
	 */
	public void setDofJoiningDesc(String dofJoiningDesc) {
		this.dofJoiningDesc = dofJoiningDesc;
	}

	/**
	 * @return the nameOfBoardDesc
	 */
	public String getNameOfBoardDesc() {
		return nameOfBoardDesc;
	}

	/**
	 * @param nameOfBoardDesc the nameOfBoardDesc to set
	 */
	public void setNameOfBoardDesc(String nameOfBoardDesc) {
		this.nameOfBoardDesc = nameOfBoardDesc;
	}

}
