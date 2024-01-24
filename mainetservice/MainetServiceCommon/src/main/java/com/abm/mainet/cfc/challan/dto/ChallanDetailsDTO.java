package com.abm.mainet.cfc.challan.dto;

import java.io.Serializable;
import java.util.Date;

public class ChallanDetailsDTO implements Serializable {

    /**
     * lalit mohan
     */
    private static final long serialVersionUID = -6852121413517511224L;

    private long offlineId;
    private long challanId;
    private long smServiceId;
    private long challanNo;
    private long orgId;
    private String applicantName;
    private long applicationId;
    private Date challanDate;
    private double challanAmount;
    private String serviceName;
    private String serviceURL;
    private String serviceShortDesc;
    private long userId;
    private long masterServiceId;
    private long deptId;
    private Date challanExpierdDate;
    private String emailId;
    private String mobileNo;
    private String challanServiceType;
    private String address;
    private String loiNo;
    private String uniqueKey;
    private String paymentCategory;

    public Date getChallanExpierdDate() {
        return challanExpierdDate;
    }

    public void setChallanExpierdDate(
            final Date challanExpierdDate) {
        this.challanExpierdDate = challanExpierdDate;
    }

    public long getDeptId() {
        return deptId;
    }

    public void setDeptId(final long deptId) {
        this.deptId = deptId;
    }

    public long getOfflineId() {
        return offlineId;
    }

    public void setOfflineId(final long offlineId) {
        this.offlineId = offlineId;
    }

    public long getChallanId() {
        return challanId;
    }

    public void setChallanId(final long challanId) {
        this.challanId = challanId;
    }

    public long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public long getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(final long challanNo) {
        this.challanNo = challanNo;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(final Date challanDate) {
        this.challanDate = challanDate;
    }

    public double getChallanAmount() {
        return challanAmount;
    }

    public void setChallanAmount(final double challanAmount) {
        this.challanAmount = challanAmount;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceURL() {
        return serviceURL;
    }

    public void setServiceURL(final String serviceURL) {
        this.serviceURL = serviceURL;
    }

    public String getServiceShortDesc() {
        return serviceShortDesc;
    }

    public void setServiceShortDesc(final String serviceShortDesc) {
        this.serviceShortDesc = serviceShortDesc;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public long getMasterServiceId() {
        return masterServiceId;
    }

    public void setMasterServiceId(final long masterServiceId) {
        this.masterServiceId = masterServiceId;
    }

    @Override
    public String toString() {
        return "ChallanDetailsDTO [offlineId=" + offlineId
                + ", challanId=" + challanId
                + ", smServiceId=" + smServiceId
                + ", challanNo=" + challanNo
                + ", applicantName=" + applicantName
                + ", applicationId=" + applicationId
                + ", challanDate=" + challanDate
                + ", challanAmount=" + challanAmount
                + ", serviceName=" + serviceName
                + ", serviceURL=" + serviceURL
                + ", serviceShortDesc=" + serviceShortDesc
                + "]";
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getChallanServiceType() {
        return challanServiceType;
    }

    public void setChallanServiceType(
            final String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getLoiNo() {
        return loiNo;
    }

    public void setLoiNo(final String loiNo) {
        this.loiNo = loiNo;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(final String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(final String paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

}
