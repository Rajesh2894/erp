package com.abm.mainet.rts.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;



public class RtsExternalServicesDTO implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4602715521484073401L;
	
	private Long rId;
	private Long orgId;
	private String applicantEmail;
	private String applicantName;
	private String applicantMobilno;
	private String applicantAddress;
	private String purpose;
	private String propertyNo;
	private Long serviceId;
	private Long applicationId;
	private Long noOfCopies;
	private Long updatedBy;
	private Date updatedDate;
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private Long modeCpdId;
	private Long userId;
	private RequestDTO requestDTO = new RequestDTO();
	private List<DocumentDetailsVO> uploadDocument;
	
	private Double amount;
	private Long chargeApplicableAt;
	private String status;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getrId() {
		return rId;
	}
	public void setrId(Long rId) {
		this.rId = rId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getApplicantEmail() {
		return applicantEmail;
	}
	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantMobilno() {
		return applicantMobilno;
	}
	public void setApplicantMobilno(String applicantMobilno) {
		this.applicantMobilno = applicantMobilno;
	}
	public String getApplicantAddress() {
		return applicantAddress;
	}
	public void setApplicantAddress(String applicantAddress) {
		this.applicantAddress = applicantAddress;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getPropertyNo() {
		return propertyNo;
	}
	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Long getNoOfCopies() {
		return noOfCopies;
	}
	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
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
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
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
	public Date getLmoddate() {
		return lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	public Long getModeCpdId() {
		return modeCpdId;
	}
	public void setModeCpdId(Long modeCpdId) {
		this.modeCpdId = modeCpdId;
	}
	public RequestDTO getRequestDTO() {
		return requestDTO;
	}
	public void setRequestDTO(RequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}
	public List<DocumentDetailsVO> getUploadDocument() {
		return uploadDocument;
	}
	public void setUploadDocument(List<DocumentDetailsVO> uploadDocument) {
		this.uploadDocument = uploadDocument;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Long getChargeApplicableAt() {
		return chargeApplicableAt;
	}
	public void setChargeApplicableAt(Long chargeApplicableAt) {
		this.chargeApplicableAt = chargeApplicableAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
