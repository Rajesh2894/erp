package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class PlumberLicenseRequestDTO implements Serializable {

	private static final long serialVersionUID = -6658412878000776494L;

	private Long plumberId;
	private String plumberLicenceNo;
	private String plumberFName;
	private String plumberMName;
	private String plumberLName;
	private String plumberFullName;
	private Date plumAppDate;
	private String plumSex;
	private String plumDOB;
	private String plumContactPersonName;
	private String plumLicIssueFlag;
	private String plumLicIssueDate;
	private Date plumLicFromDate;
	private Date plumLicToDate;
	private Long userId;
	private Long langId;
	private Date lmoddate;
	private Long updatedBy;
	private Date updatedDate;
	private String plumInterviewRemark;
	private String plumInterviewClr;
	private String plumLicType;
	private String plumFatherName;
	private String plumContactNo;
	private String plumCpdTitle;
	private String plumAddress;
	private Date plumInterviewDate;
	private String plumOldLicNo;
	private String ported;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String plumEntryFlag;
	private List<String> fileList;
	private Long applicationId;
	private Long serviceId;
	private List<String> fileCheckList;
	private List<DocumentDetailsVO> documentList;
	private String plumberImage;
	private Long orgId;
	private Long deptId;
	private int uploadedDocSize;
	private ApplicantDetailDTO applicant;
	private List<PlumberQualificationDTO> plumberQualificationDTOList;
	private List<PlumberExperienceDTO> plumberExperienceDTOList;
	private String imageByteCode;
	private double amount;
	private String licFromDate;
	private String licToDate;
	private boolean dupStatus;
	private Long receiptNo;
	private Date plumRenewFromDate;
	private Date plumRenewToDate;
	private String plumImagePath;

	private String flag;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getPlumberId() {
		return plumberId;
	}

	public void setPlumberId(final Long plumberId) {
		this.plumberId = plumberId;
	}

	public String getPlumberLicenceNo() {
		return plumberLicenceNo;
	}

	public void setPlumberLicenceNo(final String plumberLicenceNo) {
		this.plumberLicenceNo = plumberLicenceNo;
	}

	public String getPlumberFName() {
		return plumberFName;
	}

	public void setPlumberFName(final String plumberFName) {
		this.plumberFName = plumberFName;
	}

	public String getPlumberMName() {
		return plumberMName;
	}

	public void setPlumberMName(final String plumberMName) {
		this.plumberMName = plumberMName;
	}

	public String getPlumberLName() {
		return plumberLName;
	}

	public void setPlumberLName(final String plumberLName) {
		this.plumberLName = plumberLName;
	}

	public String getPlumberFullName() {
		return plumberFullName;
	}

	public void setPlumberFullName(final String plumberFullName) {
		this.plumberFullName = plumberFullName;
	}

	public Date getPlumAppDate() {
		return plumAppDate;
	}

	public void setPlumAppDate(final Date plumAppDate) {
		this.plumAppDate = plumAppDate;
	}

	public String getPlumSex() {
		return plumSex;
	}

	public void setPlumSex(final String plumSex) {
		this.plumSex = plumSex;
	}

	public String getPlumDOB() {
		return plumDOB;
	}

	public void setPlumDOB(final String plumDOB) {
		this.plumDOB = plumDOB;
	}

	public String getPlumContactPersonName() {
		return plumContactPersonName;
	}

	public void setPlumContactPersonName(final String plumContactPersonName) {
		this.plumContactPersonName = plumContactPersonName;
	}

	public String getPlumLicIssueFlag() {
		return plumLicIssueFlag;
	}

	public void setPlumLicIssueFlag(final String plumLicIssueFlag) {
		this.plumLicIssueFlag = plumLicIssueFlag;
	}

	public String getPlumLicIssueDate() {
		return plumLicIssueDate;
	}

	public void setPlumLicIssueDate(final String plumLicIssueDate) {
		this.plumLicIssueDate = plumLicIssueDate;
	}

	public Date getPlumLicFromDate() {
		return plumLicFromDate;
	}

	public void setPlumLicFromDate(final Date plumLicFromDate) {
		this.plumLicFromDate = plumLicFromDate;
	}

	public Date getPlumLicToDate() {
		return plumLicToDate;
	}

	public void setPlumLicToDate(final Date plumLicToDate) {
		this.plumLicToDate = plumLicToDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(final Long langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(final Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getPlumInterviewRemark() {
		return plumInterviewRemark;
	}

	public void setPlumInterviewRemark(final String plumInterviewRemark) {
		this.plumInterviewRemark = plumInterviewRemark;
	}

	public String getPlumInterviewClr() {
		return plumInterviewClr;
	}

	public void setPlumInterviewClr(final String plumInterviewClr) {
		this.plumInterviewClr = plumInterviewClr;
	}

	public String getPlumLicType() {
		return plumLicType;
	}

	public void setPlumLicType(final String plumLicType) {
		this.plumLicType = plumLicType;
	}

	public String getPlumFatherName() {
		return plumFatherName;
	}

	public void setPlumFatherName(final String plumFatherName) {
		this.plumFatherName = plumFatherName;
	}

	public String getPlumContactNo() {
		return plumContactNo;
	}

	public void setPlumContactNo(final String plumContactNo) {
		this.plumContactNo = plumContactNo;
	}

	public String getPlumCpdTitle() {
		return plumCpdTitle;
	}

	public void setPlumCpdTitle(final String plumCpdTitle) {
		this.plumCpdTitle = plumCpdTitle;
	}

	public String getPlumAddress() {
		return plumAddress;
	}

	public void setPlumAddress(final String plumAddress) {
		this.plumAddress = plumAddress;
	}

	public Date getPlumInterviewDate() {
		return plumInterviewDate;
	}

	public void setPlumInterviewDate(final Date plumInterviewDate) {
		this.plumInterviewDate = plumInterviewDate;
	}

	public String getPlumOldLicNo() {
		return plumOldLicNo;
	}

	public void setPlumOldLicNo(final String plumOldLicNo) {
		this.plumOldLicNo = plumOldLicNo;
	}

	public String getPorted() {
		return ported;
	}

	public void setPorted(final String ported) {
		this.ported = ported;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String getPlumEntryFlag() {
		return plumEntryFlag;
	}

	public void setPlumEntryFlag(final String plumEntryFlag) {
		this.plumEntryFlag = plumEntryFlag;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(final List<String> fileList) {
		this.fileList = fileList;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(final Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<String> getFileCheckList() {
		return fileCheckList;
	}

	public void setFileCheckList(final List<String> fileCheckList) {
		this.fileCheckList = fileCheckList;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(final List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getPlumberImage() {
		return plumberImage;
	}

	public void setPlumberImage(final String plumberImage) {
		this.plumberImage = plumberImage;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public int getUploadedDocSize() {
		return uploadedDocSize;
	}

	public void setUploadedDocSize(final int uploadedDocSize) {
		this.uploadedDocSize = uploadedDocSize;
	}

	public ApplicantDetailDTO getApplicant() {
		return applicant;
	}

	public void setApplicant(final ApplicantDetailDTO applicant) {
		this.applicant = applicant;
	}

	public List<PlumberQualificationDTO> getPlumberQualificationDTOList() {
		return plumberQualificationDTOList;
	}

	public void setPlumberQualificationDTOList(final List<PlumberQualificationDTO> plumberQualificationDTOList) {
		this.plumberQualificationDTOList = plumberQualificationDTOList;
	}

	public List<PlumberExperienceDTO> getPlumberExperienceDTOList() {
		return plumberExperienceDTOList;
	}

	public void setPlumberExperienceDTOList(final List<PlumberExperienceDTO> plumberExperienceDTOList) {
		this.plumberExperienceDTOList = plumberExperienceDTOList;
	}

	public String getImageByteCode() {
		return imageByteCode;
	}

	public void setImageByteCode(final String imageByteCode) {
		this.imageByteCode = imageByteCode;
	}

	public String getLicFromDate() {
		return licFromDate;
	}

	public void setLicFromDate(String licFromDate) {
		this.licFromDate = licFromDate;
	}

	public String getLicToDate() {
		return licToDate;
	}

	public void setLicToDate(String licToDate) {
		this.licToDate = licToDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public boolean isDupStatus() {
		return dupStatus;
	}

	public void setDupStatus(boolean dupStatus) {
		this.dupStatus = dupStatus;
	}

	public Date getPlumRenewFromDate() {
		return plumRenewFromDate;
	}

	public void setPlumRenewFromDate(Date plumRenewFromDate) {
		this.plumRenewFromDate = plumRenewFromDate;
	}

	public Date getPlumRenewToDate() {
		return plumRenewToDate;
	}

	public void setPlumRenewToDate(Date plumRenewToDate) {
		this.plumRenewToDate = plumRenewToDate;
	}

	public Long getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(Long receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPlumImagePath() {
		return plumImagePath;
	}

	public void setPlumImagePath(String plumImagePath) {
		this.plumImagePath = plumImagePath;
	}

}
