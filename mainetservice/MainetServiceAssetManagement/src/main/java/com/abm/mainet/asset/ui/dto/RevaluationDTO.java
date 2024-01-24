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
public class RevaluationDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2745125974876417310L;
    private Long revaluationId;
    private Long assetId;
    private String serialNo;
    private Long assetClass;
    private String assetClassDesc;
    private String description;
    private String costCenter;
    private String costCenterDesc;
    private Long location;
    private String locationDesc;
    private Long department;
    private String departmentDesc;
    private String remarks;
    private BigDecimal currentValue;
    private Date docDate;
    private Date postDate;
    @NotNull
    private BigDecimal newAmount;
    private Long payMode;
    private Long revalType;
    private Long impType;
    private BigDecimal impCost;
    private String impDesc;
    private Long chartOfAccount;
    @NotNull
    private Long orgId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private BigDecimal updUsefulLife;
    private BigDecimal origUsefulLife;
    private String payAdviceNo;

    /**
     * @return the RevaluationId
     */
    public Long getRevaluationId() {
        return revaluationId;
    }

    /**
     * @param revaluationId the revaluationId to set
     */
    public void setRevaluationId(Long revaluationId) {
        this.revaluationId = revaluationId;
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
     * @return the assetClassDesc
     */
    public String getAssetClassDesc() {
        return assetClassDesc;
    }

    /**
     * @param assetClassDesc the assetClassDesc to set
     */
    public void setAssetClassDesc(String assetClassDesc) {
        this.assetClassDesc = assetClassDesc;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * @return the costCenterDesc
     */
    public String getCostCenterDesc() {
        return costCenterDesc;
    }

    /**
     * @param costCenterDesc the costCenterDesc to set
     */
    public void setCostCenterDesc(String costCenterDesc) {
        this.costCenterDesc = costCenterDesc;
    }

    /**
     * @return the location
     */
    public Long getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Long location) {
        this.location = location;
    }

    /**
     * @return the locationDesc
     */
    public String getLocationDesc() {
        return locationDesc;
    }

    /**
     * @param locationDesc the locationDesc to set
     */
    public void setLocationDesc(String locationDesc) {
        this.locationDesc = locationDesc;
    }

    /**
     * @return the department
     */
    public Long getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(Long department) {
        this.department = department;
    }

    /**
     * @return the departmentDesc
     */
    public String getDepartmentDesc() {
        return departmentDesc;
    }

    /**
     * @param departmentDesc the departmentDesc to set
     */
    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    /**
     * @return the docDate
     */
    public Date getDocDate() {
        return docDate;
    }

    /**
     * @param docDate the docDate to set
     */
    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    /**
     * @return the postDate
     */
    public Date getPostDate() {
        return postDate;
    }

    /**
     * @param postDate the postDate to set
     */
    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    /**
     * @return the amount
     */
    public BigDecimal getNewAmount() {
        return newAmount;
    }

    /**
     * @param amount the amount to set
     */
    public void setNewAmount(BigDecimal amount) {
        this.newAmount = amount;
    }

    /**
     * @return the chartOfAccount
     */
    public Long getChartOfAccount() {
        return chartOfAccount;
    }

    /**
     * @param chartOfAccount the chartOfAccount to set
     */
    public void setChartOfAccount(Long chartOfAccount) {
        this.chartOfAccount = chartOfAccount;
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
        return "RevaluationDTO [revaluationId=" + revaluationId + ", assetId=" + assetId + ", assetClass=" + assetClass
                + ", assetClassDesc=" + assetClassDesc + ", description=" + description + ", costCenter=" + costCenter
                + ", costCenterDesc=" + costCenterDesc + ", location=" + location + ", locationDesc=" + locationDesc
                + ", department=" + department + ", departmentDesc=" + departmentDesc
                + ", docDate=" + docDate + ", postDate="
                + postDate + ", amount=" + newAmount + ", chartOfAccount=" + chartOfAccount
                + ", capitalGain=" + ", payMode=" + payMode + ", orgId=" + orgId + ", remarks="
                + remarks + ", creationDate=" + creationDate + ", createdBy="
                + createdBy + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + "]";
    }

    public Long getPayMode() {
        return payMode;
    }

    public void setPayMode(Long payMode) {
        this.payMode = payMode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public Long getRevalType() {
        return revalType;
    }

    public void setRevalType(Long revalType) {
        this.revalType = revalType;
    }

    public String getImpDesc() {
        return impDesc;
    }

    public void setImpDesc(String impDesc) {
        this.impDesc = impDesc;
    }

    public void setImpCost(BigDecimal impCost) {
        this.impCost = impCost;
    }

    public BigDecimal getImpCost() {
        return impCost;
    }

    public BigDecimal getUpdUsefulLife() {
        return updUsefulLife;
    }

    public void setUpdUsefulLife(BigDecimal updUsefulLife) {
        this.updUsefulLife = updUsefulLife;
    }

    public BigDecimal getOrigUsefulLife() {
        return origUsefulLife;
    }

    public void setOrigUsefulLife(BigDecimal origUsefulLife) {
        this.origUsefulLife = origUsefulLife;
    }

    public String getPayAdviceNo() {
        return payAdviceNo;
    }

    public void setPayAdviceNo(String payAdviceNo) {
        this.payAdviceNo = payAdviceNo;
    }

    public Long getImpType() {
        return impType;
    }

    public void setImpType(Long impType) {
        this.impType = impType;
    }

}
