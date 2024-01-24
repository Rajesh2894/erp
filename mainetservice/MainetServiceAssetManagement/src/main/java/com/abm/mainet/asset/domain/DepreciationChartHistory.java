/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_AST_CHART_OF_DEPRETN_HIST")
public class DepreciationChartHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 830926022500561238L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ASSET_DEPRETN_HIST_ID", nullable = false)
    private Long astderphisId;

    @Column(name = "ASSET_DEPRETN_ID", nullable = false)
    private Long assetDepretnId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "ORIGINAL_USEFUL_LIFE", nullable = true)
    private BigDecimal originalUsefulLife;

    @Column(name = "REMARK", nullable = true)
    private String remark;

    @Column(name = "CHART_OF_DEPRETN", nullable = false)
    private Long chartOfDepretn;

    @Column(name = "SALVAGE_VALUE", nullable = false)
    private BigDecimal salvageValue;

    @Column(name = "H_STATUS", nullable = true)
    private String historyFlag;

    @Column(name = "ACCU_DEPR_DATE", nullable = false)
    private Date accumulDeprDate;
    @Column(name = "ACCU_DEPR_AMT", nullable = false)
    private BigDecimal accumDepreAmount;
    @Column(name = "ACCU_DEPR_AC", nullable = false)
    private String accumuDepAc;

    @Column(name = "DEPR_APPLICABLE", nullable = false)
    private String deprApplicable;

    public Long getAstderphisId() {
        return astderphisId;
    }

    public void setAstderphisId(Long astderphisId) {
        this.astderphisId = astderphisId;
    }

    public Long getAssetDepretnId() {
        return assetDepretnId;
    }

    public void setAssetDepretnId(Long assetDepretnId) {
        this.assetDepretnId = assetDepretnId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public BigDecimal getOriginalUsefulLife() {
        return originalUsefulLife;
    }

    public void setOriginalUsefulLife(BigDecimal originalUsefulLife) {
        this.originalUsefulLife = originalUsefulLife;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getChartOfDepretn() {
        return chartOfDepretn;
    }

    public void setChartOfDepretn(Long chartOfDepretn) {
        this.chartOfDepretn = chartOfDepretn;
    }

    public BigDecimal getSalvageValue() {
        return salvageValue;
    }

    public void setSalvageValue(BigDecimal salvageValue) {
        this.salvageValue = salvageValue;
    }

    public String getHistoryFlag() {
        return historyFlag;
    }

    public void setHistoryFlag(String historyFlag) {
        this.historyFlag = historyFlag;
    }

    public Date getAccumulDeprDate() {
        return accumulDeprDate;
    }

    public void setAccumulDeprDate(Date accumulDeprDate) {
        this.accumulDeprDate = accumulDeprDate;
    }

    public BigDecimal getAccumDepreAmount() {
        return accumDepreAmount;
    }

    public void setAccumDepreAmount(BigDecimal accumDepreAmount) {
        this.accumDepreAmount = accumDepreAmount;
    }

    public String getAccumuDepAc() {
        return accumuDepAc;
    }

    public void setAccumuDepAc(String accumuDepAc) {
        this.accumuDepAc = accumuDepAc;
    }

    public String getDeprApplicable() {
        return deprApplicable;
    }

    public void setDeprApplicable(String deprApplicable) {
        this.deprApplicable = deprApplicable;
    }

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_CHART_OF_DEPRETN_HIST",
                "ASSET_DEPRETN_HIST_ID" };
    }
}
