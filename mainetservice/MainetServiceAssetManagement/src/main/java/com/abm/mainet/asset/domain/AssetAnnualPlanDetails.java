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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

@Entity
@Table(name = "TB_AST_ANNUAL_PLAN_DET")
public class AssetAnnualPlanDetails implements Serializable {

    private static final long serialVersionUID = -6968089988824323692L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "AST_ANN_PLAN_DET_ID", nullable = false)
    private Long astAnnualPlanDetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AST_ANN_PLAN_ID", nullable = false)
    private AssetAnnualPlan assetAnnualPlan;

    @Column(name = "AST_CLASS", nullable = false)
    private Long astClass;

    @Column(name = "AST_DESC", length = 500, nullable = false)
    private String astDesc;

    @Column(name = "AST_QTY", precision = 10, scale = 2, nullable = false)
    private BigDecimal astQty;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getAstAnnualPlanDetId() {
        return astAnnualPlanDetId;
    }

    public void setAstAnnualPlanDetId(Long astAnnualPlanDetId) {
        this.astAnnualPlanDetId = astAnnualPlanDetId;
    }

    public AssetAnnualPlan getAssetAnnualPlan() {
        return assetAnnualPlan;
    }

    public void setAssetAnnualPlan(AssetAnnualPlan assetAnnualPlan) {
        this.assetAnnualPlan = assetAnnualPlan;
    }

    public Long getAstClass() {
        return astClass;
    }

    public void setAstClass(Long astClass) {
        this.astClass = astClass;
    }

    public String getAstDesc() {
        return astDesc;
    }

    public void setAstDesc(String astDesc) {
        this.astDesc = astDesc;
    }

    public BigDecimal getAstQty() {
        return astQty;
    }

    public void setAstQty(BigDecimal astQty) {
        this.astQty = astQty;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_ANNUAL_PLAN_DET",
                "AST_ANN_PLAN__DET_ID" };

    }

}
