/**
 * 
 */
package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author sarojkumar.yadav
 *
 */
public class CalculationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -284071083623305098L;
    private Long calcualtionId;
    @NotNull
    private Long assetId;
    private Long groupId;
    private Long assetClass;
    private Long method;
    private String accountCode;
    @NotNull
    private BigDecimal initialBookValue;
    @NotNull
    private Date initialBookDate;
    @NotNull
    private BigDecimal previousBookValue;
    @NotNull
    private Date previousBookDate;
    private BigDecimal currentBookValue;
    private Date currentBookDate;
    private Long bookFinYear;
    private BigDecimal rate;
    private BigDecimal life;
    private BigDecimal accumDeprValue;
    private BigDecimal deprValue;
    private String changetype;
    @NotNull
    private Long orgId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;

    /**
     * @return the calcualtionId
     */
    public Long getCalcualtionId() {
        return calcualtionId;
    }

    /**
     * @param calcualtionId the calcualtionId to set
     */
    public void setCalcualtionId(Long calcualtionId) {
        this.calcualtionId = calcualtionId;
    }

    /**
     * @return the assetId
     */
    public Long getAssetId() {
        return assetId;
    }

    /**
     * @param assetId the assetId to set
     */
    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    /**
     * @return the groupId
     */
    public Long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the assetClass
     */
    public Long getAssetClass() {
        return assetClass;
    }

    /**
     * @param assetClass the assetClass to set
     */
    public void setAssetClass(Long assetClass) {
        this.assetClass = assetClass;
    }

    /**
     * @return the method
     */
    public Long getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(Long method) {
        this.method = method;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    /**
     * @return the initialBookValue
     */
    public BigDecimal getInitialBookValue() {
        return initialBookValue;
    }

    /**
     * @param initialBookValue the initialBookValue to set
     */
    public void setInitialBookValue(BigDecimal initialBookValue) {
        this.initialBookValue = initialBookValue;
    }

    /**
     * @return the initialBookDate
     */
    public Date getInitialBookDate() {
        return initialBookDate;
    }

    /**
     * @param initialBookDate the initialBookDate to set
     */
    public void setInitialBookDate(Date initialBookDate) {
        this.initialBookDate = initialBookDate;
    }

    /**
     * @return the previousBookValue
     */
    public BigDecimal getPreviousBookValue() {
        return previousBookValue;
    }

    /**
     * @param previousBookValue the previousBookValue to set
     */
    public void setPreviousBookValue(BigDecimal previousBookValue) {
        this.previousBookValue = previousBookValue;
    }

    /**
     * @return the previousBookDate
     */
    public Date getPreviousBookDate() {
        return previousBookDate;
    }

    /**
     * @param previousBookDate the previousBookDate to set
     */
    public void setPreviousBookDate(Date previousBookDate) {
        this.previousBookDate = previousBookDate;
    }

    /**
     * @return the currentBookValue
     */
    public BigDecimal getCurrentBookValue() {
        return currentBookValue;
    }

    /**
     * @param currentBookValue the currentBookValue to set
     */
    public void setCurrentBookValue(BigDecimal currentBookValue) {
        this.currentBookValue = currentBookValue;
    }

    /**
     * @return the currentBookDate
     */
    public Date getCurrentBookDate() {
        return currentBookDate;
    }

    /**
     * @param currentBookDate the currentBookDate to set
     */
    public void setCurrentBookDate(Date currentBookDate) {
        this.currentBookDate = currentBookDate;
    }

    /**
     * @return the bookFinYear
     */
    public Long getBookFinYear() {
        return bookFinYear;
    }

    /**
     * @param bookFinYear the bookFinYear to set
     */
    public void setBookFinYear(Long bookFinYear) {
        this.bookFinYear = bookFinYear;
    }

    /**
     * @return the rate
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return the life
     */
    public BigDecimal getLife() {
        return life;
    }

    /**
     * @param life the life to set
     */
    public void setLife(BigDecimal life) {
        this.life = life;
    }

    /**
     * @return the accumDeprValue
     */
    public BigDecimal getAccumDeprValue() {
        return accumDeprValue;
    }

    /**
     * @param accumDeprValue the accumDeprValue to set
     */
    public void setAccumDeprValue(BigDecimal accumDeprValue) {
        this.accumDeprValue = accumDeprValue;
    }

    /**
     * @return the deprValue
     */
    public BigDecimal getDeprValue() {
        return deprValue;
    }

    /**
     * @param deprValue the deprValue to set
     */
    public void setDeprValue(BigDecimal deprValue) {
        this.deprValue = deprValue;
    }

    /**
     * @return the changetype
     */
    public String getChangetype() {
        return changetype;
    }

    /**
     * @param changetype the changetype to set
     */
    public void setChangetype(String changetype) {
        this.changetype = changetype;
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
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "CalculationDTO [calcualtionId=" + calcualtionId + ", assetId=" + assetId + ", groupId=" + groupId
                + ", assetClass=" + assetClass + ", method=" + method + ", accountCode=" + accountCode
                + ", initialBookValue=" + initialBookValue + ", initialBookDate=" + initialBookDate
                + ", previousBookValue=" + previousBookValue + ", previousBookDate=" + previousBookDate
                + ", currentBookValue=" + currentBookValue + ", currentBookDate=" + currentBookDate + ", bookFinYear="
                + bookFinYear + ", rate=" + rate + ", life=" + life + ", accumDeprValue=" + accumDeprValue
                + ", deprValue=" + deprValue + ", changetype=" + changetype + ", orgId=" + orgId + ", creationDate="
                + creationDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", updatedDate="
                + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

}
