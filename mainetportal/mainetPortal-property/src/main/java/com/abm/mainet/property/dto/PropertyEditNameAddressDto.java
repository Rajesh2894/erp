package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.DocumentDetailsVO;


public class PropertyEditNameAddressDto implements Serializable{


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private long enaId;

	private String propNo;

	private String flatNo;

	private Long applicationId;

	private Long serviceId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;
	
	private String lgIpMac;

	private String lgIpMacUpd;

	private long orgId;

	private String ownerName;
	
	private String ownerNameReg;
	
	private String mobileno;
	
	private String eMail;
	
	private Long addharno;

	private String panno;

	private Long propertyShare;

	private String address;
	
	private Long pincode;
	
	private String remarks;

	private String authStatus;
	
	private Long deptId;
	
	private int langId;
	
	private List<DocumentDetailsVO> docs = new ArrayList<>(0);
	
	private ProvisionalAssesmentMstDto provisionalAssesmentMstDto;
	
	private List<Long> finYearList = new ArrayList<>(0);
	
	private String serviceShortCode;
	
	private Map<String, Long> flatWiseAppIdmap = new LinkedHashMap<>();
	
	private Map<String, String> flatWiseMap = new LinkedHashMap<>();
	
	private String referenceId;

	public long getEnaId() {
		return enaId;
	}

	public void setEnaId(long enaId) {
		this.enaId = enaId;
	}

	public String getPropNo() {
		return propNo;
	}

	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerNameReg() {
		return ownerNameReg;
	}

	public void setOwnerNameReg(String ownerNameReg) {
		this.ownerNameReg = ownerNameReg;
	}

	public String getMobileno() {
		return mobileno;
	}

	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Long getAddharno() {
		return addharno;
	}

	public void setAddharno(Long addharno) {
		this.addharno = addharno;
	}

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public Long getPropertyShare() {
		return propertyShare;
	}

	public void setPropertyShare(Long propertyShare) {
		this.propertyShare = propertyShare;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public List<DocumentDetailsVO> getDocs() {
		return docs;
	}

	public void setDocs(List<DocumentDetailsVO> docs) {
		this.docs = docs;
	}

	public ProvisionalAssesmentMstDto getProvisionalAssesmentMstDto() {
		return provisionalAssesmentMstDto;
	}

	public void setProvisionalAssesmentMstDto(ProvisionalAssesmentMstDto provisionalAssesmentMstDto) {
		this.provisionalAssesmentMstDto = provisionalAssesmentMstDto;
	}

	public List<Long> getFinYearList() {
		return finYearList;
	}

	public void setFinYearList(List<Long> finYearList) {
		this.finYearList = finYearList;
	}

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	public Map<String, Long> getFlatWiseAppIdmap() {
		return flatWiseAppIdmap;
	}

	public void setFlatWiseAppIdmap(Map<String, Long> flatWiseAppIdmap) {
		this.flatWiseAppIdmap = flatWiseAppIdmap;
	}

	public Map<String, String> getFlatWiseMap() {
		return flatWiseMap;
	}

	public void setFlatWiseMap(Map<String, String> flatWiseMap) {
		this.flatWiseMap = flatWiseMap;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}


}
