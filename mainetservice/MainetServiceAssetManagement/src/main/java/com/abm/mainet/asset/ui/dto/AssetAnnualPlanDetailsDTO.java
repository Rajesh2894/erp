package com.abm.mainet.asset.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AssetAnnualPlanDetailsDTO implements Serializable {

    private static final long serialVersionUID = 7092388226620297762L;

    private Long astAnnualPlanDetId;

    private AssetAnnualPlanDTO assetAnnualPlanDTO;

    private Long astClass;

    private String astDesc;

    private BigDecimal astQty;

    private Long orgId;

    private Date createdDate;

    private Long createdBy;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    public Long getAstAnnualPlanDetId() {
        return astAnnualPlanDetId;
    }

    public void setAstAnnualPlanDetId(Long astAnnualPlanDetId) {
        this.astAnnualPlanDetId = astAnnualPlanDetId;
    }

    public AssetAnnualPlanDTO getAssetAnnualPlanDTO() {
        return assetAnnualPlanDTO;
    }

    public void setAssetAnnualPlanDTO(AssetAnnualPlanDTO assetAnnualPlanDTO) {
        this.assetAnnualPlanDTO = assetAnnualPlanDTO;
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

    @Override
    public String toString() {
        return "AssetAnnualPlanDetailsDTO [astAnnualPlanDetId=" + astAnnualPlanDetId + ", assetAnnualPlanDTO="
                + assetAnnualPlanDTO + ", astClass=" + astClass + ", astDesc=" + astDesc + ", astQty=" + astQty + ", orgId="
                + orgId + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy
                + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

}
