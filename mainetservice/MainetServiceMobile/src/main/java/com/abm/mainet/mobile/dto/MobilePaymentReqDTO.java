package com.abm.mainet.mobile.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class MobilePaymentReqDTO extends CommonAppRequestDTO implements Serializable {

    private static final long serialVersionUID = 2764162072024925876L;
    private String applicantName;
    private String mobileNo;
    private String email;
    private Long bankId;
    private String serviceShortName;
    private String referenceId;
    private BigDecimal dueAmt;
    private String securityId;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;
    private String udf6;
    private String udf7;
    private String challanServiceType;
    private Map<Long, Double> feeIds;
    private Boolean documentUploaded;
    private Long txnId;

    @NotNull
    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    @NotNull
    @NotEmpty
    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    @NotNull
    @NotEmpty
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    @NotNull
    @NotEmpty
    public String getServiceShortName() {
        return serviceShortName;
    }

    public void setServiceShortName(final String serviceShortName) {
        this.serviceShortName = serviceShortName;
    }

    /**
     * @return the securityId
     */
    public String getSecurityId() {
        return securityId;
    }

    /**
     * @param securityId the securityId to set
     */
    public void setSecurityId(final String securityId) {
        this.securityId = securityId;
    }

    /**
     * @return the udf1
     */
    public String getUdf1() {
        return udf1;
    }

    /**
     * @param udf1 the udf1 to set
     */
    public void setUdf1(final String udf1) {
        this.udf1 = udf1;
    }

    /**
     * @return the udf2
     */
    public String getUdf2() {
        return udf2;
    }

    /**
     * @param udf2 the udf2 to set
     */
    public void setUdf2(final String udf2) {
        this.udf2 = udf2;
    }

    /**
     * @return the udf3
     */
    public String getUdf3() {
        return udf3;
    }

    /**
     * @param udf3 the udf3 to set
     */
    public void setUdf3(final String udf3) {
        this.udf3 = udf3;
    }

    /**
     * @return the udf4
     */
    public String getUdf4() {
        return udf4;
    }

    /**
     * @param udf4 the udf4 to set
     */
    public void setUdf4(final String udf4) {
        this.udf4 = udf4;
    }

    /**
     * @return the udf5
     */
    public String getUdf5() {
        return udf5;
    }

    /**
     * @param udf5 the udf5 to set
     */
    public void setUdf5(final String udf5) {
        this.udf5 = udf5;
    }

    /**
     * @return the udf6
     */
    public String getUdf6() {
        return udf6;
    }

    /**
     * @param udf6 the udf6 to set
     */
    public void setUdf6(final String udf6) {
        this.udf6 = udf6;
    }

    /**
     * @return the udf7
     */
    public String getUdf7() {
        return udf7;
    }

    /**
     * @param udf7 the udf7 to set
     */
    public void setUdf7(final String udf7) {
        this.udf7 = udf7;
    }

    /**
     * @return the challanServiceType
     */
    public String getChallanServiceType() {
        return challanServiceType;
    }

    /**
     * @param challanServiceType the challanServiceType to set
     */
    public void setChallanServiceType(String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    /**
     * @return the feeIds
     */
    public Map<Long, Double> getFeeIds() {
        return feeIds;
    }

    /**
     * @param feeIds the feeIds to set
     */
    public void setFeeIds(Map<Long, Double> feeIds) {
        this.feeIds = feeIds;
    }

    /**
     * @return the documentUploaded
     */
    public Boolean getDocumentUploaded() {
        return documentUploaded;
    }

    /**
     * @param documentUploaded the documentUploaded to set
     */
    public void setDocumentUploaded(Boolean documentUploaded) {
        this.documentUploaded = documentUploaded;
    }

    /**
     * @return the txnId
     */
    public Long getTxnId() {
        return txnId;
    }

    /**
     * @param txnId the txnId to set
     */
    public void setTxnId(Long txnId) {
        this.txnId = txnId;
    }

    @NotNull
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @NotNull
    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
        if (dueAmt != null) {
            this.dueAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MobilePaymentReqDTO [applicantName=" + applicantName + ", mobileNo=" + mobileNo + ", email=" + email
                + ", bankId=" + bankId + ", serviceShortName=" + serviceShortName + ", referenceId=" + referenceId
                + ", dueAmt=" + dueAmt + ", securityId=" + securityId + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3="
                + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6 + ", udf7=" + udf7
                + ", challanServiceType=" + challanServiceType + ", feeIds=" + feeIds + ", documentUploaded="
                + documentUploaded + ", txnId=" + txnId + "]";
    }

}
