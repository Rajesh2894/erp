/**
 * 
 */
package com.abm.mainet.common.integration.asset.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO class for Asset Depreciation Chart
 * 
 * @author sarojkumar.yadav
 *
 */
@XmlRootElement(name = "AssetDepreciationChart")
public class AssetDepreciationChartDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2557708262216988830L;

    private Long assetDeprChartId;
    private Long assetId;
    // @NotNull(message = "Salvage Value can not be null")
    private BigDecimal salvageValue;
    // @NotNull(message = "chart Of Depreciation can not be null")
    private Long chartOfDepre;
    private BigDecimal oriUseYear;
    private String remark;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private Date initialAccumulDeprDate;
    private BigDecimal initialAccumDepreAmount;
    private String accumuDepAc;
    private Boolean deprApplicable;
    private Date latestAccumulDeprDate;
    private BigDecimal latestAccumDepreAmount;

    /**
     * @return the assetDeprChartId
     */
    public Long getAssetDeprChartId() {
        return assetDeprChartId;
    }

    /**
     * @param assetDeprChartId the assetDeprChartId to set
     */
    public void setAssetDeprChartId(Long assetDeprChartId) {
        this.assetDeprChartId = assetDeprChartId;
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
     * @return the salvageValue
     */
    public BigDecimal getSalvageValue() {
        return salvageValue;
    }

    /**
     * @param salvageValue the salvageValue to set
     */
    public void setSalvageValue(BigDecimal salvageValue) {
        this.salvageValue = salvageValue;
    }

    /**
     * @return the chartOfDepre
     */
    public Long getChartOfDepre() {
        return chartOfDepre;
    }

    /**
     * @param chartOfDepre the chartOfDepre to set
     */
    public void setChartOfDepre(Long chartOfDepre) {
        this.chartOfDepre = chartOfDepre;
    }

    /**
     * @return the oriUseYear
     */
    public BigDecimal getOriUseYear() {
        return oriUseYear;
    }

    /**
     * @param oriUseYear the oriUseYear to set
     */
    public void setOriUseYear(BigDecimal oriUseYear) {
        this.oriUseYear = oriUseYear;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(String remark) {
        this.remark = remark;
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

    /**
     * @return the initialAccumulDeprDate
     */
    public Date getInitialAccumulDeprDate() {
        return initialAccumulDeprDate;
    }

    /**
     * @param initialAccumulDeprDate the initialAccumulDeprDate to set
     */
    public void setInitialAccumulDeprDate(Date initialAccumulDeprDate) {
        this.initialAccumulDeprDate = initialAccumulDeprDate;
    }

    /**
     * @return the initialAccumDepreAmount
     */
    public BigDecimal getInitialAccumDepreAmount() {
        return initialAccumDepreAmount;
    }

    /**
     * @param initialAccumDepreAmount the initialAccumDepreAmount to set
     */
    public void setInitialAccumDepreAmount(BigDecimal initialAccumDepreAmount) {
        this.initialAccumDepreAmount = initialAccumDepreAmount;
    }

    public String getAccumuDepAc() {
        return accumuDepAc;
    }

    public void setAccumuDepAc(String accumuDepAc) {
        this.accumuDepAc = accumuDepAc;
    }

    /**
     * @return the deprApplicable
     */
    public Boolean getDeprApplicable() {
        return deprApplicable;
    }

    /**
     * @param deprApplicable the deprApplicable to set
     */
    public void setDeprApplicable(Boolean deprApplicable) {
        this.deprApplicable = deprApplicable;
    }

    /**
     * @return the latestAccumulDeprDate
     */
    public Date getLatestAccumulDeprDate() {
        return latestAccumulDeprDate;
    }

    /**
     * @param latestAccumulDeprDate the latestAccumulDeprDate to set
     */
    public void setLatestAccumulDeprDate(Date latestAccumulDeprDate) {
        this.latestAccumulDeprDate = latestAccumulDeprDate;
    }

    /**
     * @return the latestAccumDepreAmount
     */
    public BigDecimal getLatestAccumDepreAmount() {
        return latestAccumDepreAmount;
    }

    /**
     * @param latestAccumDepreAmount the latestAccumDepreAmount to set
     */
    public void setLatestAccumDepreAmount(BigDecimal latestAccumDepreAmount) {
        this.latestAccumDepreAmount = latestAccumDepreAmount;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AssetDepreciationChartDTO [assetDeprChartId=" + assetDeprChartId + ", assetId=" + assetId
                + ", salvageValue=" + salvageValue + ", chartOfDepre=" + chartOfDepre + ", oriUseYear=" + oriUseYear
                + ", remark=" + remark + ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", updatedBy="
                + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd
                + ", initialAccumulDeprDate=" + initialAccumulDeprDate + ", initialAccumDepreAmount="
                + initialAccumDepreAmount + ", accumuDepAc=" + accumuDepAc + ", deprApplicable=" + deprApplicable
                + ", latestAccumulDeprDate=" + latestAccumulDeprDate + ", latestAccumDepreAmount="
                + latestAccumDepreAmount + "]";
    }

}
