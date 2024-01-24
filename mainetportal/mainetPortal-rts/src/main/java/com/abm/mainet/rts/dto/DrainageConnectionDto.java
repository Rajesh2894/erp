package com.abm.mainet.rts.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

/**
 * @author rahul.chaubey
 * @since 09 March 2020
 */
public class DrainageConnectionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2509017622157931752L;



	private Long connectionId;

	private String drainageAddress;

	private String propertyIndexNo;

	private Long sizeOfConnection;

	private Long typeOfConnection;

	private Long ward;

	private Long applicantType;

	private Long apmApplicationId;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long serviceId;

	private Long langId;

	private String ulbName;

	private List<DocumentDetailsVO> documentList;

	private ApplicantDetailDTO applicant = new ApplicantDetailDTO();
	
	private RequestDTO reqDTO = new RequestDTO();

	private Map<Long, Double> chargesMap = new HashMap<>();
	 
	 private String checkListApplFlag;
	
	 private String applicationchargeApplFlag;

	 private CommonChallanDTO offlineDTO = new CommonChallanDTO();
	 
	 private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();
	 
	 private Long noOfFlat;

	public Long getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(Long connectionId) {
		this.connectionId = connectionId;
	}

	public String getDrainageAddress() {
		return drainageAddress;
	}

	public void setDrainageAddress(String drainageAddress) {
		this.drainageAddress = drainageAddress;
	}

	public String getPropertyIndexNo() {
		return propertyIndexNo;
	}

	public void setPropertyIndexNo(String propertyIndexNo) {
		this.propertyIndexNo = propertyIndexNo;
	}

	public Long getSizeOfConnection() {
		return sizeOfConnection;
	}

	public void setSizeOfConnection(Long sizeOfConnection) {
		this.sizeOfConnection = sizeOfConnection;
	}

	public Long getTypeOfConnection() {
		return typeOfConnection;
	}

	public void setTypeOfConnection(Long typeOfConnection) {
		this.typeOfConnection = typeOfConnection;
	}

	public Long getWard() {
		return ward;
	}

	public void setWard(Long ward) {
		this.ward = ward;
	}

	public Long getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(Long applicantType) {
		this.applicantType = applicantType;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public String getUlbName() {
		return ulbName;
	}

	public void setUlbName(String ulbName) {
		this.ulbName = ulbName;
	}


	public Map<Long, Double> getChargesMap() {
		return chargesMap;
	}

	public void setChargesMap(Map<Long, Double> chargesMap) {
		this.chargesMap = chargesMap;
	}

	public RequestDTO getReqDTO() {
		return reqDTO;
	}

	public void setReqDTO(RequestDTO reqDTO) {
		this.reqDTO = reqDTO;
	}

	public String getCheckListApplFlag() {
		return checkListApplFlag;
	}

	public void setCheckListApplFlag(String checkListApplFlag) {
		this.checkListApplFlag = checkListApplFlag;
	}

	public String getApplicationchargeApplFlag() {
		return applicationchargeApplFlag;
	}

	public void setApplicationchargeApplFlag(String applicationchargeApplFlag) {
		this.applicationchargeApplFlag = applicationchargeApplFlag;
	}

	public CommonChallanDTO getOfflineDTO() {
		return offlineDTO;
	}

	public void setOfflineDTO(CommonChallanDTO offlineDTO) {
		this.offlineDTO = offlineDTO;
	}

	public ApplicantDetailDTO getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDetailDTO applicant) {
		this.applicant = applicant;
	}

	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}

	public void setApplicantDTO(ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
	}

	public Long getNoOfFlat() {
		return noOfFlat;
	}

	public void setNoOfFlat(Long noOfFlat) {
		this.noOfFlat = noOfFlat;
	}

}
