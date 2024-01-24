package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.RequestDTO;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class NewWaterConnectionReqDTO extends RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String isConsumer;
	private String isBillingAddressSame;
	private Long pinCode;
	private Long billingPinCode;
	private String billingAdharNo;
	private String existingConsumerNumber;
	private String consumerNo;
	private String existingPropertyNo;
	private String propertyNo;
	private String flatNo;
	private String consumerType;
	private String plumberName;
	private Long orgId;
	private Long userId;
	private Long langId;
	private Long deptId;
	private String lgIpMac;
	private Long serviceId;
	private String isULBRegisterd;
	private String applicantType;
	private boolean isFree;
	private Double charges;

	private String payMode;
	private boolean isScrutinyApplicable;

    private boolean isPaymentModeOnline;
    private String propertyOutStanding;
    private String houseNo;
    private List<DocumentDetailsVO> attachments;
    private Long applicationId;
    
    private String ward;
    private String category;
    private String subCategory;
    private String tarrifCategory;
    private String connSize;

	public boolean isPaymentModeOnline() {
		return isPaymentModeOnline;
	}

	public void setPaymentModeOnline(final boolean isPaymentModeOnline) {
		this.isPaymentModeOnline = isPaymentModeOnline;
	}

	/**
	 * @return the isFree
	 */
	@Override
	public boolean isFree() {
		return isFree;
	}

	/**
	 * @param isFree
	 *            the isFree to set
	 */
	@Override
	public void setFree(final boolean isFree) {
		this.isFree = isFree;
	}

	/**
	 * @return the charges
	 */
	public Double getCharges() {
		return charges;
	}

	/**
	 * @param charges
	 *            the charges to set
	 */
	public void setCharges(final Double charges) {
		this.charges = charges;
	}

	/**
	 * @return the applicantType
	 */
	public String getApplicantType() {
		return applicantType;
	}

	/**
	 * @param applicantType
	 *            the applicantType to set
	 */
	public void setApplicantType(final String applicantType) {
		this.applicantType = applicantType;
	}

	/**
	 * @return the csmrrCmd
	 */
	public TbCsmrrCmdDTO getCsmrrCmd() {
		return csmrrCmd;
	}

	/**
	 * @param csmrrCmd
	 *            the csmrrCmd to set
	 */
	public void setCsmrrCmd(final TbCsmrrCmdDTO csmrrCmd) {
		this.csmrrCmd = csmrrCmd;
	}

	private ApplicantDetailDTO applicantDTO = new ApplicantDetailDTO();

	private List<AdditionalOwnerInfoDTO> ownerList = new ArrayList<>();

	private List<TbKLinkCcnDTO> linkDetails = new ArrayList<>();

	private TbCsmrInfoDTO csmrInfo = new TbCsmrInfoDTO();

	private List<DocumentDetailsVO> documentList = new ArrayList<>();

	private TbCsmrrCmdDTO csmrrCmd = new TbCsmrrCmdDTO();

	/**
	 * @return the documentList
	 */
	@Override
	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}

	/**
	 * @param documentList
	 *            the documentList to set
	 */
	@Override
	public void setDocumentList(final List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}

	public String getIsConsumer() {
		return isConsumer;
	}

	public void setIsConsumer(final String isConsumer) {
		this.isConsumer = isConsumer;
	}

	public String getExistingConsumerNumber() {
		return existingConsumerNumber;
	}

	public void setExistingConsumerNumber(final String existingConsumerNumber) {
		this.existingConsumerNumber = existingConsumerNumber;
	}

	public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(final String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(final String consumerType) {
		this.consumerType = consumerType;
	}

	public Long getBillingPinCode() {
		return billingPinCode;
	}

	public void setBillingPinCode(final Long billingPinCode) {
		this.billingPinCode = billingPinCode;
	}

	public String getBillingAdharNo() {
		return billingAdharNo;
	}

	public void setBillingAdharNo(final String billingAdharNo) {
		this.billingAdharNo = billingAdharNo;
	}

	public String getIsBillingAddressSame() {
		return isBillingAddressSame;
	}

	public void setIsBillingAddressSame(final String isBillingAddressSame) {
		this.isBillingAddressSame = isBillingAddressSame;
	}

	public String getConsumerNo() {
		return consumerNo;
	}

	public void setConsumerNo(final String consumerNo) {
		this.consumerNo = consumerNo;
	}

	public String getExistingPropertyNo() {
		return existingPropertyNo;
	}

	public void setExistingPropertyNo(final String existingPropertyNo) {
		this.existingPropertyNo = existingPropertyNo;
	}

	public List<AdditionalOwnerInfoDTO> getOwnerList() {
		return ownerList;
	}

	public void setOwnerList(final List<AdditionalOwnerInfoDTO> ownerList) {
		this.ownerList = ownerList;
	}

	public String getPlumberName() {
		return plumberName;
	}

	public void setPlumberName(final String plumberName) {
		this.plumberName = plumberName;
	}

	@Override
	public Long getOrgId() {
		return orgId;
	}

	@Override
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	@Override
	public Long getUserId() {
		return userId;
	}

	@Override
	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	@Override
	public Long getLangId() {
		return langId;
	}

	@Override
	public void setLangId(final Long langId) {
		this.langId = langId;
	}

	@Override
	public Long getDeptId() {
		return deptId;
	}

	@Override
	public void setDeptId(final Long deptId) {
		this.deptId = deptId;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	@Override
	public Long getServiceId() {
		return serviceId;
	}

	@Override
	public void setServiceId(final Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getIsULBRegisterd() {
		return isULBRegisterd;
	}

	public void setIsULBRegisterd(final String isULBRegisterd) {
		this.isULBRegisterd = isULBRegisterd;
	}

	public ApplicantDetailDTO getApplicantDTO() {
		return applicantDTO;
	}

	public void setApplicantDTO(final ApplicantDetailDTO applicantDTO) {
		this.applicantDTO = applicantDTO;
	}

	public List<TbKLinkCcnDTO> getLinkDetails() {
		return linkDetails;
	}

	public void setLinkDetails(final List<TbKLinkCcnDTO> linkDetails) {
		this.linkDetails = linkDetails;
	}

	public TbCsmrInfoDTO getCsmrInfo() {
		return csmrInfo;
	}

	public void setCsmrInfo(final TbCsmrInfoDTO csmrInfo) {
		this.csmrInfo = csmrInfo;

	}

	public Long getPinCode() {
		return pinCode;
	}

	public void setPinCode(final Long pinCode) {
		this.pinCode = pinCode;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(final String payMode) {
		this.payMode = payMode;
	}

	public boolean isScrutinyApplicable() {
		return isScrutinyApplicable;
	}

	public void setScrutinyApplicable(final boolean isScrutinyApplicable) {
		this.isScrutinyApplicable = isScrutinyApplicable;
	}

	public String getPropertyOutStanding() {
		return propertyOutStanding;
	}

	public void setPropertyOutStanding(String propertyOutStanding) {
		this.propertyOutStanding = propertyOutStanding;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public List<DocumentDetailsVO> getUploadDocument() {
		return attachments;
	}

	public void setUploadDocument(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
		
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getTarrifCategory() {
		return tarrifCategory;
	}

	public void setTarrifCategory(String tarrifCategory) {
		this.tarrifCategory = tarrifCategory;
	}

	public String getConnSize() {
		return connSize;
	}

	public void setConnSize(String connSize) {
		this.connSize = connSize;
	}
	
}
