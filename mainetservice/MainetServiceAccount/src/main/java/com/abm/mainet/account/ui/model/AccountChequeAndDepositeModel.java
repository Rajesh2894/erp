
package com.abm.mainet.account.ui.model;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author prasant.sahu
 *
 */
@Component
@Scope("session")
public class AccountChequeAndDepositeModel {

    private Long depositeType;
    private Date fromDate;
    private Date toDate;

    private boolean isViewMode;

    private Long depTypeDemandDraft;
    private Long depTypeCheque;
    private Long depTypePayOrder;
    private String feeMode;

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the depositeType
     */
    public Long getDepositeType() {
        return depositeType;
    }

    /**
     * @param depositeType the depositeType to set
     */
    public void setDepositeType(final Long depositeType) {
        this.depositeType = depositeType;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the isViewMode
     */
    public boolean isViewMode() {
        return isViewMode;
    }

    /**
     * @param isViewMode the isViewMode to set
     */
    public void setViewMode(final boolean isViewMode) {
        this.isViewMode = isViewMode;
    }

    /**
     * @return the depTypeDemandDraft
     */
    public Long getDepTypeDemandDraft() {
        return depTypeDemandDraft;
    }

    /**
     * @param depTypeDemandDraft the depTypeDemandDraft to set
     */
    public void setDepTypeDemandDraft(final Long depTypeDemandDraft) {
        this.depTypeDemandDraft = depTypeDemandDraft;
    }

    /**
     * @return the depTypeCheque
     */
    public Long getDepTypeCheque() {
        return depTypeCheque;
    }

    /**
     * @param depTypeCheque the depTypeCheque to set
     */
    public void setDepTypeCheque(final Long depTypeCheque) {
        this.depTypeCheque = depTypeCheque;
    }

    /**
     * @return the depTypePayOrder
     */
    public Long getDepTypePayOrder() {
        return depTypePayOrder;
    }

    /**
     * @param depTypePayOrder the depTypePayOrder to set
     */
    public void setDepTypePayOrder(final Long depTypePayOrder) {
        this.depTypePayOrder = depTypePayOrder;
    }

    /**
     * @return the feeMode
     */
    public String getFeeMode() {
        return feeMode;
    }

    /**
     * @param feeMode the feeMode to set
     */
    public void setFeeMode(final String feeMode) {
        this.feeMode = feeMode;
    }

}
