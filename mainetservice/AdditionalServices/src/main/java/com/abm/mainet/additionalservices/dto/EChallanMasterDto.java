/**
 * 
 */
package com.abm.mainet.additionalservices.dto;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

/**
 * @author divya.marshettiwar
 *
 */
public class EChallanMasterDto implements Serializable{

	private static final long serialVersionUID = 2504405199889968549L;

	private Long challanId;
	private String challanNo;
	private Date challanDate;
	private String challanType;
	private String challanDesc;
	private Double challanAmt;
	private Date dueDate;
	private String offenderName;
	private Long locationId;
	private String offenderMobNo;
	private String offenderEmail;
	private Blob evidenceImg;
	private String raidNo;
	private String referenceNo;
	private String remark;
	private String status;
	private String fromArea;
	private String toArea;
	private String officerOnsite;
	private Long orgid;
	private Long createdBy;
	private Date createdDate;
	private String lgIpMac;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMacUpd;
	private Date challanFromDate;
	private Date challanToDate;
	private String datechallan;
	private String locality;
	private Long langId;
	private Long serviceId;
	private Long deptId;
	
	private List<EChallanItemDetailsDto> echallanItemDetDto = new ArrayList<>();
	
	List<DocumentDetailsVO> docList = new ArrayList<>();
	private List<AttachDocs> fetchIntDocList = new ArrayList<>();
	
	public Long getChallanId() {
		return challanId;
	}
	public void setChallanId(Long challanId) {
		this.challanId = challanId;
	}
	public String getChallanNo() {
		return challanNo;
	}
	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}
	public Date getChallanDate() {
		return challanDate;
	}
	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}
	public String getChallanType() {
		return challanType;
	}
	public void setChallanType(String challanType) {
		this.challanType = challanType;
	}
	public String getChallanDesc() {
		return challanDesc;
	}
	public void setChallanDesc(String challanDesc) {
		this.challanDesc = challanDesc;
	}
	public Double getChallanAmt() {
		return challanAmt;
	}
	public void setChallanAmt(Double challanAmt) {
		this.challanAmt = challanAmt;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getOffenderName() {
		return offenderName;
	}
	public void setOffenderName(String offenderName) {
		this.offenderName = offenderName;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getOffenderMobNo() {
		return offenderMobNo;
	}
	public void setOffenderMobNo(String offenderMobNo) {
		this.offenderMobNo = offenderMobNo;
	}
	public String getOffenderEmail() {
		return offenderEmail;
	}
	public void setOffenderEmail(String offenderEmail) {
		this.offenderEmail = offenderEmail;
	}
	public Blob getEvidenceImg() {
		return evidenceImg;
	}
	public void setEvidenceImg(Blob evidenceImg) {
		this.evidenceImg = evidenceImg;
	}
	public String getRaidNo() {
		return raidNo;
	}
	public void setRaidNo(String raidNo) {
		this.raidNo = raidNo;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFromArea() {
		return fromArea;
	}
	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}
	public String getToArea() {
		return toArea;
	}
	public void setToArea(String toArea) {
		this.toArea = toArea;
	}
	public String getOfficerOnsite() {
		return officerOnsite;
	}
	public void setOfficerOnsite(String officerOnsite) {
		this.officerOnsite = officerOnsite;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getLgIpMac() {
		return lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}	
	public List<EChallanItemDetailsDto> getEchallanItemDetDto() {
		return echallanItemDetDto;
	}
	public void setEchallanItemDetDto(List<EChallanItemDetailsDto> echallanItemDetDto) {
		this.echallanItemDetDto = echallanItemDetDto;
	}
	public Date getChallanFromDate() {
		return challanFromDate;
	}
	public void setChallanFromDate(Date challanFromDate) {
		this.challanFromDate = challanFromDate;
	}
	public Date getChallanToDate() {
		return challanToDate;
	}
	public void setChallanToDate(Date challanToDate) {
		this.challanToDate = challanToDate;
	}
	public String getDatechallan() {
		return datechallan;
	}
	public void setDatechallan(String datechallan) {
		this.datechallan = datechallan;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public List<DocumentDetailsVO> getDocList() {
		return docList;
	}
	public void setDocList(List<DocumentDetailsVO> docList) {
		this.docList = docList;
	}
	public List<AttachDocs> getFetchIntDocList() {
		return fetchIntDocList;
	}
	public void setFetchIntDocList(List<AttachDocs> fetchIntDocList) {
		this.fetchIntDocList = fetchIntDocList;
	}
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public Long getServiceId() {
		return serviceId;
	}
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
}
