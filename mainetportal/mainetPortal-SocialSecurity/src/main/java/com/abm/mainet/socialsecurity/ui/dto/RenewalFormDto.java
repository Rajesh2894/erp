/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;

/**
 * @author priti.singh
 *
 */
public class RenewalFormDto implements Serializable {

	private static final long serialVersionUID = -3337806617014632572L;

	private Long applicationId;
	
	private String beneficiaryno;
	private String nameofApplicant;
	private Long selectSchemeName;
	 private String selectSchemeNamedesc;
	private Date lastDateofLifeCerti;
	private Date validtoDate;
	
	private Long serviceId;
	private Long orgId;
	private Long deptId;
	private Long langId;
	private Long referenceNo;
	private String ulbName;
	private String serviceCode;
	private Boolean statusFlag;
	private Long masterAppId;

	private List<DocumentDetailsVO> documentList;
	private ApplicantDetailDTO applicant = new ApplicantDetailDTO();
	private List<DocumentDetailsVO> uploaddoc;

	private String lgIpMac;
	private String lgIpMacUpd;

	private Date createdDate;
	private Long createdBy;
	private Long applicableServiceId;
	private Long mobNum;

	private Long contactNumber;
	private Long pinCode;
	private String bplNo;
	private String isBplApplicable;
	private String bplfamily;
	private String benefnoNname;

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
	 * @return the nameofApplicant
	 */
	public String getNameofApplicant() {
		return nameofApplicant;
	}

	/**
	 * @param nameofApplicant the nameofApplicant to set
	 */
	public void setNameofApplicant(String nameofApplicant) {
		this.nameofApplicant = nameofApplicant;
	}

	/**
	 * @return the selectSchemeName
	 */
	public Long getSelectSchemeName() {
		return selectSchemeName;
	}

	/**
	 * @param selectSchemeName the selectSchemeName to set
	 */
	public void setSelectSchemeName(Long selectSchemeName) {
		this.selectSchemeName = selectSchemeName;
	}
	
	

	public String getSelectSchemeNamedesc() {
		return selectSchemeNamedesc;
	}

	public void setSelectSchemeNamedesc(String selectSchemeNamedesc) {
		this.selectSchemeNamedesc = selectSchemeNamedesc;
	}

	/**
	 * @return the lastDateofLifeCerti
	 */
	public Date getLastDateofLifeCerti() {
		return lastDateofLifeCerti;
	}

	/**
	 * @param lastDateofLifeCerti the lastDateofLifeCerti to set
	 */
	public void setLastDateofLifeCerti(Date lastDateofLifeCerti) {
		this.lastDateofLifeCerti = lastDateofLifeCerti;
	}

	/**
	 * @return the validtoDate
	 */
	public Date getValidtoDate() {
		return validtoDate;
	}

	/**
	 * @param validtoDate the validtoDate to set
	 */
	public void setValidtoDate(Date validtoDate) {
		this.validtoDate = validtoDate;
	}

	/**
	 * @return the serviceId
	 */
	public Long getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
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
	 * @return the deptId
	 */
	public Long getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId the deptId to set
	 */
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return the langId
	 */
	public Long getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(Long langId) {
		this.langId = langId;
	}

	/**
	 * @return the referenceNo
	 */
	public Long getReferenceNo() {
		return referenceNo;
	}

	/**
	 * @param referenceNo the referenceNo to set
	 */
	public void setReferenceNo(Long referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * @return the ulbName
	 */
	public String getUlbName() {
		return ulbName;
	}

	/**
	 * @param ulbName the ulbName to set
	 */
	public void setUlbName(String ulbName) {
		this.ulbName = ulbName;
	}

	/**
	 * @return the serviceCode
	 */
	public String getServiceCode() {
		return serviceCode;
	}

	/**
	 * @param serviceCode the serviceCode to set
	 */
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	/**
	 * @return the statusFlag
	 */
	public Boolean getStatusFlag() {
		return statusFlag;
	}

	/**
	 * @param statusFlag the statusFlag to set
	 */
	public void setStatusFlag(Boolean statusFlag) {
		this.statusFlag = statusFlag;
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
	 * @return the masterAppId
	 */
	public Long getMasterAppId() {
		return masterAppId;
	}

	/**
	 * @param masterAppId the masterAppId to set
	 */
	public void setMasterAppId(Long masterAppId) {
		this.masterAppId = masterAppId;
	}

	/**
	 * @return the applicant
	 */
	public ApplicantDetailDTO getApplicant() {
		return applicant;
	}

	/**
	 * @param applicant the applicant to set
	 */
	public void setApplicant(ApplicantDetailDTO applicant) {
		this.applicant = applicant;
	}

	/**
	 * @return the applicableServiceId
	 */
	public Long getApplicableServiceId() {
		return applicableServiceId;
	}

	/**
	 * @param applicableServiceId the applicableServiceId to set
	 */
	public void setApplicableServiceId(Long applicableServiceId) {
		this.applicableServiceId = applicableServiceId;
	}

	/**
	 * @return the mobNum
	 */
	public Long getMobNum() {
		return mobNum;
	}

	/**
	 * @param mobNum the mobNum to set
	 */
	public void setMobNum(Long mobNum) {
		this.mobNum = mobNum;
	}

	/**
	 * @return the contactNumber
	 */
	public Long getContactNumber() {
		return contactNumber;
	}

	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	/**
	 * @return the pinCode
	 */
	public Long getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(Long pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the bplNo
	 */
	public String getBplNo() {
		return bplNo;
	}

	/**
	 * @param bplNo the bplNo to set
	 */
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	/**
	 * @return the isBplApplicable
	 */
	public String getIsBplApplicable() {
		return isBplApplicable;
	}

	/**
	 * @param isBplApplicable the isBplApplicable to set
	 */
	public void setIsBplApplicable(String isBplApplicable) {
		this.isBplApplicable = isBplApplicable;
	}

	/**
	 * @return the bplfamily
	 */
	public String getBplfamily() {
		return bplfamily;
	}

	/**
	 * @param bplfamily the bplfamily to set
	 */
	public void setBplfamily(String bplfamily) {
		this.bplfamily = bplfamily;
	}

	/**
	 * @return the uploaddoc
	 */
	public List<DocumentDetailsVO> getUploaddoc() {
		return uploaddoc;
	}

	/**
	 * @param uploaddoc the uploaddoc to set
	 */
	public void setUploaddoc(List<DocumentDetailsVO> uploaddoc) {
		this.uploaddoc = uploaddoc;
	}

	public String getBeneficiaryno() {
		return beneficiaryno;
	}

	public void setBeneficiaryno(String beneficiaryno) {
		this.beneficiaryno = beneficiaryno;
	}

	public String getBenefnoNname() {
		return benefnoNname;
	}

	public void setBenefnoNname(String benefnoNname) {
		this.benefnoNname = benefnoNname;
	}

	

	
}
