package com.abm.mainet.account.dto;

import java.io.Serializable;

/**
 * @author tejas.kotekar
 *
 */
public class ChequeCancellationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long oldChequeBookDetId;
    private Long newChequeBookDetId;
    private Long bankId;
    private String cancellationDate;
    private String cancellationReason;
    private Long paymentId;
    private String issuanceDate;
    private Long orgId;
    private Long languageId;
    private String successfulFlag;

    public Long getOldChequeBookDetId() {
        return oldChequeBookDetId;
    }

    public void setOldChequeBookDetId(final Long oldChequeBookDetId) {
        this.oldChequeBookDetId = oldChequeBookDetId;
    }

    public Long getNewChequeBookDetId() {
        return newChequeBookDetId;
    }

    public void setNewChequeBookDetId(final Long newChequeBookDetId) {
        this.newChequeBookDetId = newChequeBookDetId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    public String getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(final String cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(final String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(final Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(final String issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    public String getSuccessfulFlag() {
        return successfulFlag;
    }

    public void setSuccessfulFlag(final String successfulFlag) {
        this.successfulFlag = successfulFlag;
    }

    @Override
    public String toString() {
        return "ChequeCancellationDto [oldChequeBookDetId=" + oldChequeBookDetId + ", newChequeBookDetId="
                + newChequeBookDetId + ", bankId=" + bankId + ", cancellationDate=" + cancellationDate
                + ", cancellationReason=" + cancellationReason + ", paymentId=" + paymentId + ", issuanceDate="
                + issuanceDate + ", orgId=" + orgId + ", languageId=" + languageId + ", successfulFlag="
                + successfulFlag + "]";
    }
}
