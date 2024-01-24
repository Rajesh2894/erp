package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class WaterReconnectionRequestDTO implements Serializable {

    private static final long serialVersionUID = -6406728282024862487L;

    private Long consumerIdNo;

    private Long consumerConnNo;

    private Date reconnectionDate;

    private String reconnectionStatus;

    private String reconnectionGranted;

    private Long reconnectionMethod;

    private Long plumberId;

    private Long applicationId;

    private String remarks;

    private Date reconnExecutionDate;

    private boolean physicalConnFlag;

    private Long orgId;

    private Long userId;

    private Long langId;

    private Long deptId;

    private String lgIpMac;

    private double amount;

    private int documentSize;

    private String onlineOfflineCheck;

    private ApplicantDetailDTO applicant;

    private List<String> fileList;

    private String connectionNo;
    private String consumerName;
    private Long tarrifCategoryId;
    private Long premiseTypeId;
    private Long connectionSize;
    private Long discMethodId;
    private Long discType;
    private String discDate;
    private String discRemarks;
    private String tarrifCategory;
    private String premiseType;
    private String discMethod;
    private String disconnectionType;
    private String plumberLicNo;
    private List<DocumentDetailsVO> documentList;
    private String BPLFlag;
    private String BPLNo;
    private String plumber;
    private Long serviceId;
    private List<DocumentDetailsVO> uploadedDocList;
    private String mobileNo;
    private String emailId;
    private String pincode;
    private String aadharNo;
    private String areaName;
    private Long applicantTitle;
    private String firstName;
    private String lastName;
    private Date discAppDate;
    private String serviceCode;

    private List<WaterReconnectionResponseDTO> responseDTOs = new ArrayList<>();

    public Long getConsumerIdNo() {
        return consumerIdNo;
    }

    public void setConsumerIdNo(final Long consumerIdNo) {
        this.consumerIdNo = consumerIdNo;
    }

    public Long getConsumerConnNo() {
        return consumerConnNo;
    }

    public void setConsumerConnNo(final Long consumerConnNo) {
        this.consumerConnNo = consumerConnNo;
    }

    public Date getReconnectionDate() {
        return reconnectionDate;
    }

    public void setReconnectionDate(final Date reconnectionDate) {
        this.reconnectionDate = reconnectionDate;
    }

    public String getReconnectionStatus() {
        return reconnectionStatus;
    }

    public void setReconnectionStatus(final String reconnectionStatus) {
        this.reconnectionStatus = reconnectionStatus;
    }

    public String getReconnectionGranted() {
        return reconnectionGranted;
    }

    public void setReconnectionGranted(final String reconnectionGranted) {
        this.reconnectionGranted = reconnectionGranted;
    }

    public Long getReconnectionMethod() {
        return reconnectionMethod;
    }

    public void setReconnectionMethod(final Long reconnectionMethod) {
        this.reconnectionMethod = reconnectionMethod;
    }

    public Long getPlumberId() {
        return plumberId;
    }

    public void setPlumberId(final Long plumberId) {
        this.plumberId = plumberId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(final String remarks) {
        this.remarks = remarks;
    }

    public Date getReconnExecutionDate() {
        return reconnExecutionDate;
    }

    public void setReconnExecutionDate(final Date reconnExecutionDate) {
        this.reconnExecutionDate = reconnExecutionDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(final Long deptId) {
        this.deptId = deptId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public boolean isPhysicalConnFlag() {
        return physicalConnFlag;
    }

    public void setPhysicalConnFlag(final boolean physicalConnFlag) {
        this.physicalConnFlag = physicalConnFlag;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public int getDocumentSize() {
        return documentSize;
    }

    public void setDocumentSize(final int documentSize) {
        this.documentSize = documentSize;
    }

    public String getOnlineOfflineCheck() {
        return onlineOfflineCheck;
    }

    public void setOnlineOfflineCheck(final String onlineOfflineCheck) {
        this.onlineOfflineCheck = onlineOfflineCheck;
    }

    public ApplicantDetailDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(final ApplicantDetailDTO applicant) {
        this.applicant = applicant;
    }

    public List<String> getFileList() {
        return fileList;
    }

    public void setFileList(final List<String> fileList) {
        this.fileList = fileList;
    }

    /**
     * @return the responseDTOs
     */
    public List<WaterReconnectionResponseDTO> getResponseDTOs() {
        return responseDTOs;
    }

    /**
     * @param responseDTOs the responseDTOs to set
     */
    public void setResponseDTOs(final List<WaterReconnectionResponseDTO> responseDTOs) {
        this.responseDTOs = responseDTOs;
    }

    /**
     * @return the connectionNo
     */
    public String getConnectionNo() {
        return connectionNo;
    }

    /**
     * @param connectionNo the connectionNo to set
     */
    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    /**
     * @return the consumerName
     */
    public String getConsumerName() {
        return consumerName;
    }

    /**
     * @param consumerName the consumerName to set
     */
    public void setConsumerName(final String consumerName) {
        this.consumerName = consumerName;
    }

    /**
     * @return the tarrifCategoryId
     */
    public Long getTarrifCategoryId() {
        return tarrifCategoryId;
    }

    /**
     * @param tarrifCategoryId the tarrifCategoryId to set
     */
    public void setTarrifCategoryId(final Long tarrifCategoryId) {
        this.tarrifCategoryId = tarrifCategoryId;
    }

    /**
     * @return the premiseTypeId
     */
    public Long getPremiseTypeId() {
        return premiseTypeId;
    }

    /**
     * @param premiseTypeId the premiseTypeId to set
     */
    public void setPremiseTypeId(final Long premiseTypeId) {
        this.premiseTypeId = premiseTypeId;
    }

    /**
     * @return the connectionSize
     */
    public Long getConnectionSize() {
        return connectionSize;
    }

    /**
     * @param connectionSize the connectionSize to set
     */
    public void setConnectionSize(final Long connectionSize) {
        this.connectionSize = connectionSize;
    }

    /**
     * @return the discMethodId
     */
    public Long getDiscMethodId() {
        return discMethodId;
    }

    /**
     * @param discMethodId the discMethodId to set
     */
    public void setDiscMethodId(final Long discMethodId) {
        this.discMethodId = discMethodId;
    }

    /**
     * @return the discType
     */
    public Long getDiscType() {
        return discType;
    }

    /**
     * @param discType the discType to set
     */
    public void setDiscType(final Long discType) {
        this.discType = discType;
    }

    /**
     * @return the discDate
     */
    public String getDiscDate() {
        return discDate;
    }

    /**
     * @param discDate the discDate to set
     */
    public void setDiscDate(final String discDate) {
        this.discDate = discDate;
    }

    /**
     * @return the discRemarks
     */
    public String getDiscRemarks() {
        return discRemarks;
    }

    /**
     * @param discRemarks the discRemarks to set
     */
    public void setDiscRemarks(final String discRemarks) {
        this.discRemarks = discRemarks;
    }

    /**
     * @return the tarrifCategory
     */
    public String getTarrifCategory() {
        return tarrifCategory;
    }

    /**
     * @param tarrifCategory the tarrifCategory to set
     */
    public void setTarrifCategory(final String tarrifCategory) {
        this.tarrifCategory = tarrifCategory;
    }

    /**
     * @return the premiseType
     */
    public String getPremiseType() {
        return premiseType;
    }

    /**
     * @param premiseType the premiseType to set
     */
    public void setPremiseType(final String premiseType) {
        this.premiseType = premiseType;
    }

    /**
     * @return the discMethod
     */
    public String getDiscMethod() {
        return discMethod;
    }

    /**
     * @param discMethod the discMethod to set
     */
    public void setDiscMethod(final String discMethod) {
        this.discMethod = discMethod;
    }

    /**
     * @return the disconnectionType
     */
    public String getDisconnectionType() {
        return disconnectionType;
    }

    /**
     * @param disconnectionType the disconnectionType to set
     */
    public void setDisconnectionType(final String disconnectionType) {
        this.disconnectionType = disconnectionType;
    }

    /**
     * @return the plumberLicNo
     */
    public String getPlumberLicNo() {
        return plumberLicNo;
    }

    /**
     * @param plumberLicNo the plumberLicNo to set
     */
    public void setPlumberLicNo(final String plumberLicNo) {
        this.plumberLicNo = plumberLicNo;
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
    public void setDocumentList(final List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    /**
     * @return the bPLFlag
     */
    public String getBPLFlag() {
        return BPLFlag;
    }

    /**
     * @param bPLFlag the bPLFlag to set
     */
    public void setBPLFlag(final String bPLFlag) {
        BPLFlag = bPLFlag;
    }

    /**
     * @return the bPLNo
     */
    public String getBPLNo() {
        return BPLNo;
    }

    /**
     * @param bPLNo the bPLNo to set
     */
    public void setBPLNo(final String bPLNo) {
        BPLNo = bPLNo;
    }

    /**
     * @return the plumber
     */
    public String getPlumber() {
        return plumber;
    }

    /**
     * @param plumber the plumber to set
     */
    public void setPlumber(final String plumber) {
        this.plumber = plumber;
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
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the uploadedDocList
     */
    public List<DocumentDetailsVO> getUploadedDocList() {
        return uploadedDocList;
    }

    /**
     * @param uploadedDocList the uploadedDocList to set
     */
    public void setUploadedDocList(final List<DocumentDetailsVO> uploadedDocList) {
        this.uploadedDocList = uploadedDocList;
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
    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
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
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the pincode
     */
    public String getPincode() {
        return pincode;
    }

    /**
     * @param pincode the pincode to set
     */
    public void setPincode(final String pincode) {
        this.pincode = pincode;
    }

    /**
     * @return the aadharNo
     */
    public String getAadharNo() {
        return aadharNo;
    }

    /**
     * @param aadharNo the aadharNo to set
     */
    public void setAadharNo(final String aadharNo) {
        this.aadharNo = aadharNo;
    }

    /**
     * @return the areaName
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * @param areaName the areaName to set
     */
    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    /**
     * @return the applicantTitle
     */
    public Long getApplicantTitle() {
        return applicantTitle;
    }

    /**
     * @param applicantTitle the applicantTitle to set
     */
    public void setApplicantTitle(final Long applicantTitle) {
        this.applicantTitle = applicantTitle;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the discAppDate
     */
    public Date getDiscAppDate() {
        return discAppDate;
    }

    /**
     * @param discAppDate the discAppDate to set
     */
    public void setDiscAppDate(final Date discAppDate) {
        this.discAppDate = discAppDate;
    }

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
    
    

}
