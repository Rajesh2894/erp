package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class PensionSchemeMasterDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3337806617014632572L;
	/* factor applicable */
	private Long schmeMsId;
	private Long serviceId;
	private String objOfschme;
	private Long orgId;
	private Date createdDate;
	private Long createdBy;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private String isSchmeActive;
	private String resolutionNo;
	private Date resolutionDate;
	private String familyDetReq;
	private String lifeCertificateReq;
	private List<DocumentDetailsVO> resolutionDoc = new ArrayList<DocumentDetailsVO>();
	
	public boolean checkBox;

	private List<PensionEligibilityCriteriaDto> pensioneligibilityList = new ArrayList<>();
	private List<PensionSourceOfFundDto> pensionSourceFundList = new ArrayList<>();
	private List<SubSchemeDetailsDto> subSchemeDetailsDtoList = new ArrayList<>();
	private List<List<PensionEligibilityCriteriaDto>> saveDataList = new ArrayList<>();
	
	private String status;
	
	private Long paySchedule;
	private BigDecimal amt;

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getObjOfschme() {
		return objOfschme;
	}

	public void setObjOfschme(String objOfschme) {
		this.objOfschme = objOfschme;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public List<List<PensionEligibilityCriteriaDto>> getSaveDataList() {
		return saveDataList;
	}

	public void setSaveDataList(List<List<PensionEligibilityCriteriaDto>> saveDataList) {
		this.saveDataList = saveDataList;
	}

	public String getIsSchmeActive() {
		return isSchmeActive;
	}

	public void setIsSchmeActive(String isSchmeActive) {
		this.isSchmeActive = isSchmeActive;
	}

	public List<PensionEligibilityCriteriaDto> getPensioneligibilityList() {
		return pensioneligibilityList;
	}

	public void setPensioneligibilityList(List<PensionEligibilityCriteriaDto> pensioneligibilityList) {
		this.pensioneligibilityList = pensioneligibilityList;
	}

	public List<PensionSourceOfFundDto> getPensionSourceFundList() {
		return pensionSourceFundList;
	}

	public void setPensionSourceFundList(List<PensionSourceOfFundDto> pensionSourceFundList) {
		this.pensionSourceFundList = pensionSourceFundList;
	}

	public Long getSchmeMsId() {
		return schmeMsId;
	}

	public void setSchmeMsId(Long schmeMsId) {
		this.schmeMsId = schmeMsId;
	}

	public boolean isCheckBox() {
		return checkBox;
	}

	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResolutionNo() {
		return resolutionNo;
	}

	public void setResolutionNo(String resolutionNo) {
		this.resolutionNo = resolutionNo;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public List<DocumentDetailsVO> getResolutionDoc() {
		return resolutionDoc;
	}

	public void setResolutionDoc(List<DocumentDetailsVO> resolutionDoc) {
		this.resolutionDoc = resolutionDoc;
	}

	public String getFamilyDetReq() {
		return familyDetReq;
	}

	public void setFamilyDetReq(String familyDetReq) {
		this.familyDetReq = familyDetReq;
	}

	public String getLifeCertificateReq() {
		return lifeCertificateReq;
	}

	public void setLifeCertificateReq(String lifeCertificateReq) {
		this.lifeCertificateReq = lifeCertificateReq;
	}

	public Long getPaySchedule() {
		return paySchedule;
	}

	public void setPaySchedule(Long paySchedule) {
		this.paySchedule = paySchedule;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public List<SubSchemeDetailsDto> getSubSchemeDetailsDtoList() {
		return subSchemeDetailsDtoList;
	}

	public void setSubSchemeDetailsDtoList(List<SubSchemeDetailsDto> subSchemeDetailsDtoList) {
		this.subSchemeDetailsDtoList = subSchemeDetailsDtoList;
	}

}
