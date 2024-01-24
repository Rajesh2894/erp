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
public class RetirementDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2745125974876417310L;
    private Long retireId;
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
    private String soldToName;
    private String soldToAddress;
    private String remarks;
    private BigDecimal currentValue = BigDecimal.ONE;
    @NotNull(message = "{asset.retire.valid.dispMethod}")
    private Long dispositionMethod;
    @NotNull(message = "{asset.retire.valid.dispDate}")
    private Date dispositionDate;
    private Date docDate;
    private Date postDate;
    private BigDecimal amount;
    private Long payMode;
    private Long chartOfAccount;
    private Long capitalGain;
    private BigDecimal capitalTax;
    private Long capitalChartOfAccount;
    @NotNull
    private Long orgId;
    private Date creationDate;
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String disOrderNumber;
    @NotNull
    private Date nonfucDate;
    private Date DisOrderDate;
    private Long bankId;
    private Long chequeNo;
    private Date chequeDate;
    private String assetCode;
    private String deptCode;

    /**
     * @return the retireId
     */
    public Long getRetireId() {
        return retireId;
    }

    /**
     * @param retireId the retireId to set
     */
    public void setRetireId(Long retireId) {
        this.retireId = retireId;
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
     * @return the dispositionMethod
     */
    public Long getDispositionMethod() {
        return dispositionMethod;
    }

    /**
     * @param dispositionMethod the dispositionMethod to set
     */
    public void setDispositionMethod(Long dispositionMethod) {
        this.dispositionMethod = dispositionMethod;
    }

    /**
     * @param departmentDesc the departmentDesc to set
     */
    public void setDepartmentDesc(String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    /**
     * @return the dispositionDate
     */
    public Date getDispositionDate() {
        return dispositionDate;
    }

    /**
     * @param dispositionDate the dispositionDate to set
     */
    public void setDispositionDate(Date dispositionDate) {
        this.dispositionDate = dispositionDate;
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
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

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
     * @return the capitalGain
     */
    public Long getCapitalGain() {
        return capitalGain;
    }

    /**
     * @param capitalGain the capitalGain to set
     */
    public void setCapitalGain(Long capitalGain) {
        this.capitalGain = capitalGain;
    }

    /**
     * @return the capitalTax
     */
    public BigDecimal getCapitalTax() {
        return capitalTax;
    }

    /**
     * @param capitalTax the capitalTax to set
     */
    public void setCapitalTax(BigDecimal capitalTax) {
        this.capitalTax = capitalTax;
    }

    /**
     * @return the capitalChartOfAccount
     */
    public Long getCapitalChartOfAccount() {
        return capitalChartOfAccount;
    }

    /**
     * @param capitalChartOfAccount the capitalChartOfAccount to set
     */
    public void setCapitalChartOfAccount(Long capitalChartOfAccount) {
        this.capitalChartOfAccount = capitalChartOfAccount;
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

    public Long getPayMode() {
        return payMode;
    }

    public void setPayMode(Long payMode) {
        this.payMode = payMode;
    }

    public String getSoldToName() {
        return soldToName;
    }

    public void setSoldToName(String soldToName) {
        this.soldToName = soldToName;
    }

    public String getSoldToAddress() {
        return soldToAddress;
    }

    public void setSoldToAddress(String soldToAddress) {
        this.soldToAddress = soldToAddress;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getNonfucDate() {
        return nonfucDate;
    }

    public void setNonfucDate(Date nonfucDate) {
        this.nonfucDate = nonfucDate;
    }

    public Date getDisOrderDate() {
        return DisOrderDate;
    }

    public void setDisOrderDate(Date disOrderDate) {
        DisOrderDate = disOrderDate;
    }

    public String getDisOrderNumber() {
        return disOrderNumber;
    }

    public void setDisOrderNumber(String disOrderNumber) {
        this.disOrderNumber = disOrderNumber;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Long getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(Long chequeNo) {
        this.chequeNo = chequeNo;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Override
    public String toString() {
        return "RetirementDTO [retireId=" + retireId + ", assetId=" + assetId + ", serialNo=" + serialNo + ", assetClass="
                + assetClass + ", assetClassDesc=" + assetClassDesc + ", description=" + description + ", costCenter="
                + costCenter + ", costCenterDesc=" + costCenterDesc + ", location=" + location + ", locationDesc=" + locationDesc
                + ", department=" + department + ", departmentDesc=" + departmentDesc + ", soldToName=" + soldToName
                + ", soldToAddress=" + soldToAddress + ", remarks=" + remarks + ", currentValue=" + currentValue
                + ", dispositionMethod=" + dispositionMethod + ", dispositionDate=" + dispositionDate + ", docDate=" + docDate
                + ", postDate=" + postDate + ", amount=" + amount + ", payMode=" + payMode + ", chartOfAccount=" + chartOfAccount
                + ", capitalGain=" + capitalGain + ", capitalTax=" + capitalTax + ", capitalChartOfAccount="
                + capitalChartOfAccount + ", orgId=" + orgId + ", creationDate=" + creationDate + ", createdBy=" + createdBy
                + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd="
                + lgIpMacUpd + ", disOrderNumber=" + disOrderNumber + ", nonfucDate=" + nonfucDate + ", DisOrderDate="
                + DisOrderDate + ", bankId=" + bankId + ", chequeNo=" + chequeNo + ", chequeDate=" + chequeDate + ", assetCode="
                + assetCode + ", deptCode=" + deptCode + "]";
    }

}
