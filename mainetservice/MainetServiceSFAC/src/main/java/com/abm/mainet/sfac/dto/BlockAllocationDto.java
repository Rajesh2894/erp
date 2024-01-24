/**
 * 
 */
package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * @author pooja.maske
 *
 */
public class BlockAllocationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4098126433192307925L;

	private Long blockId;

	private Long orgTypeId;

	private Long organizationNameId;

	private Long allocationYearId;

	private Long sdb1;

	private Long sdb2;

	private Long sdb3;

	private Long sdb4;

	private Long sdb5;

	private Long noOfFPOAllocated;

	private Long allocationCategory;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private int langId;

	private String orgName;
	private String state;
	private String district;
	private String block;
	private String alcYear;
	private Long NoofBlockAssigned;
	private String allcateg;

	private String changedBlock;

	private Long commodityType;

	private String reasonForBlockChange;

	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	private List<DocumentDetailsVO> documentList = new ArrayList<>();
	private Long applicationId;

	private String userName;
	private String email;
	private String mobileNo;

	private String status;

	private ApplicantDetailDTO applicantDetailDto = new ApplicantDetailDTO();

	private Long stateId;
	private Long distId;
	private Long blckId;
	private Long allCate;
	private Long allocationTarget;

	private String authRemark;
	
	private Long pendingBlockCount;

	private List<CFCAttachment> viewImg = new ArrayList<>();

	private List<BlockAllocationDetailDto> blockDetailDto = new ArrayList<>();

	private List<BlockTargetDetDto> targetDetDto = new ArrayList<>();

	/**
	 * @return the blockId
	 */
	public Long getBlockId() {
		return blockId;
	}

	/**
	 * @param blockId the blockId to set
	 */
	public void setBlockId(Long blockId) {
		this.blockId = blockId;
	}

	/**
	 * @return the organizationNameId
	 */
	public Long getOrganizationNameId() {
		return organizationNameId;
	}

	/**
	 * @param organizationNameId the organizationNameId to set
	 */
	public void setOrganizationNameId(Long organizationNameId) {
		this.organizationNameId = organizationNameId;
	}

	/**
	 * @return the allocationYearId
	 */
	public Long getAllocationYearId() {
		return allocationYearId;
	}

	/**
	 * @param allocationYearId the allocationYearId to set
	 */
	public void setAllocationYearId(Long allocationYearId) {
		this.allocationYearId = allocationYearId;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the sdb2
	 */
	public Long getSdb2() {
		return sdb2;
	}

	/**
	 * @param sdb2 the sdb2 to set
	 */
	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}

	/**
	 * @return the sdb3
	 */
	public Long getSdb3() {
		return sdb3;
	}

	/**
	 * @param sdb3 the sdb3 to set
	 */
	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}

	/**
	 * @return the sdb4
	 */
	public Long getSdb4() {
		return sdb4;
	}

	/**
	 * @param sdb4 the sdb4 to set
	 */
	public void setSdb4(Long sdb4) {
		this.sdb4 = sdb4;
	}

	/**
	 * @return the sdb5
	 */
	public Long getSdb5() {
		return sdb5;
	}

	/**
	 * @param sdb5 the sdb5 to set
	 */
	public void setSdb5(Long sdb5) {
		this.sdb5 = sdb5;
	}

	/**
	 * @return the noOfFPOAllocated
	 */
	public Long getNoOfFPOAllocated() {
		return noOfFPOAllocated;
	}

	/**
	 * @param noOfFPOAllocated the noOfFPOAllocated to set
	 */
	public void setNoOfFPOAllocated(Long noOfFPOAllocated) {
		this.noOfFPOAllocated = noOfFPOAllocated;
	}

	/**
	 * @return the allocationCategory
	 */
	public Long getAllocationCategory() {
		return allocationCategory;
	}

	/**
	 * @param allocationCategory the allocationCategory to set
	 */
	public void setAllocationCategory(Long allocationCategory) {
		this.allocationCategory = allocationCategory;
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
	 * @return the orgTypeId
	 */
	public Long getOrgTypeId() {
		return orgTypeId;
	}

	/**
	 * @param orgTypeId the orgTypeId to set
	 */
	public void setOrgTypeId(Long orgTypeId) {
		this.orgTypeId = orgTypeId;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the block
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * @param block the block to set
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * @return the alcYear
	 */
	public String getAlcYear() {
		return alcYear;
	}

	/**
	 * @param alcYear the alcYear to set
	 */
	public void setAlcYear(String alcYear) {
		this.alcYear = alcYear;
	}

	/**
	 * @return the noofBlockAssigned
	 */
	public Long getNoofBlockAssigned() {
		return NoofBlockAssigned;
	}

	/**
	 * @param noofBlockAssigned the noofBlockAssigned to set
	 */
	public void setNoofBlockAssigned(Long noofBlockAssigned) {
		NoofBlockAssigned = noofBlockAssigned;
	}

	/**
	 * @return the allcateg
	 */
	public String getAllcateg() {
		return allcateg;
	}

	/**
	 * @param allcateg the allcateg to set
	 */
	public void setAllcateg(String allcateg) {
		this.allcateg = allcateg;
	}

	/**
	 * @return the attachments
	 */
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}


	/**
	 * @return the changedBlock
	 */
	public String getChangedBlock() {
		return changedBlock;
	}

	/**
	 * @param changedBlock the changedBlock to set
	 */
	public void setChangedBlock(String changedBlock) {
		this.changedBlock = changedBlock;
	}

	/**
	 * @return the commodityType
	 */
	public Long getCommodityType() {
		return commodityType;
	}

	/**
	 * @param commodityType the commodityType to set
	 */
	public void setCommodityType(Long commodityType) {
		this.commodityType = commodityType;
	}

	/**
	 * @return the reasonForBlockChange
	 */
	public String getReasonForBlockChange() {
		return reasonForBlockChange;
	}

	/**
	 * @param reasonForBlockChange the reasonForBlockChange to set
	 */
	public void setReasonForBlockChange(String reasonForBlockChange) {
		this.reasonForBlockChange = reasonForBlockChange;
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
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the distId
	 */
	public Long getDistId() {
		return distId;
	}

	/**
	 * @param distId the distId to set
	 */
	public void setDistId(Long distId) {
		this.distId = distId;
	}

	/**
	 * @return the blckId
	 */
	public Long getBlckId() {
		return blckId;
	}

	/**
	 * @param blckId the blckId to set
	 */
	public void setBlckId(Long blckId) {
		this.blckId = blckId;
	}

	/**
	 * @return the allCate
	 */
	public Long getAllCate() {
		return allCate;
	}

	/**
	 * @param allCate the allCate to set
	 */
	public void setAllCate(Long allCate) {
		this.allCate = allCate;
	}

	/**
	 * @return the allocationTarget
	 */
	public Long getAllocationTarget() {
		return allocationTarget;
	}

	/**
	 * @param allocationTarget the allocationTarget to set
	 */
	public void setAllocationTarget(Long allocationTarget) {
		this.allocationTarget = allocationTarget;
	}

	/**
	 * @return the authRemark
	 */
	public String getAuthRemark() {
		return authRemark;
	}

	/**
	 * @param authRemark the authRemark to set
	 */
	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}

	/**
	 * @return the viewImg
	 */
	public List<CFCAttachment> getViewImg() {
		return viewImg;
	}

	/**
	 * @param viewImg the viewImg to set
	 */
	public void setViewImg(List<CFCAttachment> viewImg) {
		this.viewImg = viewImg;
	}

	/**
	 * @return the documentList
	 */
	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList the documentList to set
	 */
	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}



	/**
	 * @return the blockDetailDto
	 */
	public List<BlockAllocationDetailDto> getBlockDetailDto() {
		return blockDetailDto;
	}

	/**
	 * @param blockDetailDto the blockDetailDto to set
	 */
	public void setBlockDetailDto(List<BlockAllocationDetailDto> blockDetailDto) {
		this.blockDetailDto = blockDetailDto;
	}

	/**
	 * @return the targetDetDto
	 */
	public List<BlockTargetDetDto> getTargetDetDto() {
		return targetDetDto;
	}

	/**
	 * @param targetDetDto the targetDetDto to set
	 */
	public void setTargetDetDto(List<BlockTargetDetDto> targetDetDto) {
		this.targetDetDto = targetDetDto;
	}

	/**
	 * @return the pendingBlockCount
	 */
	public Long getPendingBlockCount() {
		return pendingBlockCount;
	}

	/**
	 * @param pendingBlockCount the pendingBlockCount to set
	 */
	public void setPendingBlockCount(Long pendingBlockCount) {
		this.pendingBlockCount = pendingBlockCount;
	}
	

}
