package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;

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

    public List<WaterReconnectionResponseDTO> getResponseDTOs() {
        return responseDTOs;
    }

    public void setResponseDTOs(final List<WaterReconnectionResponseDTO> responseDTOs) {
        this.responseDTOs = responseDTOs;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(final String consumerName) {
        this.consumerName = consumerName;
    }

    public Long getTarrifCategoryId() {
        return tarrifCategoryId;
    }

    public void setTarrifCategoryId(final Long tarrifCategoryId) {
        this.tarrifCategoryId = tarrifCategoryId;
    }

    public Long getPremiseTypeId() {
        return premiseTypeId;
    }

    public void setPremiseTypeId(final Long premiseTypeId) {
        this.premiseTypeId = premiseTypeId;
    }

    public Long getConnectionSize() {
        return connectionSize;
    }

    public void setConnectionSize(final Long connectionSize) {
        this.connectionSize = connectionSize;
    }

    public Long getDiscMethodId() {
        return discMethodId;
    }

    public void setDiscMethodId(final Long discMethodId) {
        this.discMethodId = discMethodId;
    }

    public Long getDiscType() {
        return discType;
    }

    public void setDiscType(final Long discType) {
        this.discType = discType;
    }

    public String getDiscDate() {
        return discDate;
    }

    public void setDiscDate(final String discDate) {
        this.discDate = discDate;
    }

    public String getDiscRemarks() {
        return discRemarks;
    }

    public void setDiscRemarks(final String discRemarks) {
        this.discRemarks = discRemarks;
    }

    public String getTarrifCategory() {
        return tarrifCategory;
    }

    public void setTarrifCategory(final String tarrifCategory) {
        this.tarrifCategory = tarrifCategory;
    }

    public String getPremiseType() {
        return premiseType;
    }

    public void setPremiseType(final String premiseType) {
        this.premiseType = premiseType;
    }

    public String getDiscMethod() {
        return discMethod;
    }

    public void setDiscMethod(final String discMethod) {
        this.discMethod = discMethod;
    }

    public String getDisconnectionType() {
        return disconnectionType;
    }

    public void setDisconnectionType(final String disconnectionType) {
        this.disconnectionType = disconnectionType;
    }

    public String getPlumberLicNo() {
        return plumberLicNo;
    }

    public void setPlumberLicNo(final String plumberLicNo) {
        this.plumberLicNo = plumberLicNo;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(final List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getBPLFlag() {
        return BPLFlag;
    }

    public void setBPLFlag(final String bPLFlag) {
        BPLFlag = bPLFlag;
    }

    public String getBPLNo() {
        return BPLNo;
    }

    public void setBPLNo(final String bPLNo) {
        BPLNo = bPLNo;
    }

    public String getPlumber() {
        return plumber;
    }

    public void setPlumber(final String plumber) {
        this.plumber = plumber;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public List<DocumentDetailsVO> getUploadedDocList() {
        return uploadedDocList;
    }

    public void setUploadedDocList(final List<DocumentDetailsVO> uploadedDocList) {
        this.uploadedDocList = uploadedDocList;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(final String pincode) {
        this.pincode = pincode;
    }

    public String getAadharNo() {
        return aadharNo;
    }

    public void setAadharNo(final String aadharNo) {
        this.aadharNo = aadharNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    public Long getApplicantTitle() {
        return applicantTitle;
    }

    public void setApplicantTitle(final Long applicantTitle) {
        this.applicantTitle = applicantTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public Date getDiscAppDate() {
        return discAppDate;
    }

    public void setDiscAppDate(final Date discAppDate) {
        this.discAppDate = discAppDate;
    }

}
