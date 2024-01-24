
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

public class AccountFundMasterBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2543171072171278599L;

    private String parentLevel;
    private String parentCode;
    private String parentDesc;
    private String parentFinalCode;

    private Long parentLevelCode;
    private Long childLevelCode;

    // used for view to show after validation
    private String childLevel;
    private String childCode;
    private String childDesc;
    private String childFinalCode;
    private String childParentLevel;
    private String childParentCode;

    private boolean newRecord;
    private String editedDataChildLevel;
    private String editedDataChildParentLevel;
    private String editedDataChildCode;
    private String editedDataChildParentCode;
    private String editedDataChildDesc;
    private String editedDataChildCompositeCode;
    private Long editedChildStatus;

    private Integer uniqueNumber;

    private Long codconfigId;

    private String alreadyExists = "N";

    private Long codingDigits;

    private Long fundId;

    private Long codcofdetId;

    private String fundCode;

    private String fundDesc;

    private String fundCompositecode;

    private Long orgid;

    private Long userId;

    private Long langId;

    private AccountFundMasterBean fundParentId;

    private Date lmoddate;

    private Long fundStatusCpdId;

    private String defaultOrgFlag;

    /**
     * @return the codconfigId
     */
    public Long getCodconfigId() {
        return codconfigId;
    }

    /**
     * @param codconfigId the codconfigId to set
     */
    public void setCodconfigId(final Long codconfigId) {
        this.codconfigId = codconfigId;
    }

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    private String lgIpMacUpd;

    private String modeView = "";

    @JsonManagedReference
    private List<BankAccountMasterDto> bankAccounts = new ArrayList<>();

    /**
     * @return the modeView
     */
    public String getModeView() {
        return modeView;
    }

    /**
     * @param modeView the modeView to set
     */
    public void setModeView(final String modeView) {
        this.modeView = modeView;
    }

    private List<AccountFundDto> listDto;

    /**
     * @return the listDto
     */
    public List<AccountFundDto> getListDto() {
        return listDto;
    }

    /**
     * @param listDto the listDto to set
     */
    public void setListDto(final List<AccountFundDto> listDto) {
        this.listDto = listDto;
    }

    private String hasError;

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @return the editedChildStatus
     */
    public Long getEditedChildStatus() {
        return editedChildStatus;
    }

    /**
     * @param editedChildStatus the editedChildStatus to set
     */
    public void setEditedChildStatus(final Long editedChildStatus) {
        this.editedChildStatus = editedChildStatus;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFundId() {
        return fundId;
    }

    /**
     * @return the parentLevel
     */
    public String getParentLevel() {
        return parentLevel;
    }

    /**
     * @param parentLevel the parentLevel to set
     */
    public void setParentLevel(final String parentLevel) {
        this.parentLevel = parentLevel;
    }

    /**
     * @return the parentLevelCode
     */
    public Long getParentLevelCode() {
        return parentLevelCode;
    }

    /**
     * @param parentLevelCode the parentLevelCode to set
     */
    public void setParentLevelCode(final Long parentLevelCode) {
        this.parentLevelCode = parentLevelCode;
    }

    /**
     * @return the parentCode
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * @param parentCode the parentCode to set
     */
    public void setParentCode(final String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * @return the parentDesc
     */
    public String getParentDesc() {
        return parentDesc;
    }

    /**
     * @param parentDesc the parentDesc to set
     */
    public void setParentDesc(final String parentDesc) {
        this.parentDesc = parentDesc;
    }

    /**
     * @return the parentFinalCode
     */
    public String getParentFinalCode() {
        return parentFinalCode;
    }

    /**
     * @return the fundStatusCpdId
     */
    public Long getFundStatusCpdId() {
        return fundStatusCpdId;
    }

    /**
     * @param fundStatusCpdId the fundStatusCpdId to set
     */
    public void setFundStatusCpdId(final Long fundStatusCpdId) {
        this.fundStatusCpdId = fundStatusCpdId;
    }

    /**
     * @param parentFinalCode the parentFinalCode to set
     */
    public void setParentFinalCode(final String parentFinalCode) {
        this.parentFinalCode = parentFinalCode;
    }

    public void setCodcofdetId(final Long codcofdetId) {
        this.codcofdetId = codcofdetId;
    }

    public Long getCodcofdetId() {
        return codcofdetId;
    }

    public void setFundCode(final String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundDesc(final String fundDesc) {
        this.fundDesc = fundDesc;
    }

    public String getFundDesc() {
        return fundDesc;
    }

    public void setFundCompositecode(final String fundCompositecode) {
        this.fundCompositecode = fundCompositecode;
    }

    public String getFundCompositecode() {
        return fundCompositecode;
    }

    public void setFundParentId(final AccountFundMasterBean fundParentId) {
        this.fundParentId = fundParentId;
    }

    public AccountFundMasterBean getFundParentId() {
        return fundParentId;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @return the alreadyExists
     */
    public String getAlreadyExists() {
        return alreadyExists;
    }

    /**
     * @param alreadyExists the alreadyExists to set
     */
    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    /**
     * @return the codingDigits
     */
    public Long getCodingDigits() {
        return codingDigits;
    }

    /**
     * @param codingDigits the codingDigits to set
     */
    public void setCodingDigits(final Long codingDigits) {
        this.codingDigits = codingDigits;
    }

    /**
     * @return the childLevelCode
     */
    public Long getChildLevelCode() {
        return childLevelCode;
    }

    /**
     * @param childLevelCode the childLevelCode to set
     */
    public void setChildLevelCode(final Long childLevelCode) {
        this.childLevelCode = childLevelCode;
    }

    /**
     * @return the uniqueNumber
     */
    public Integer getUniqueNumber() {
        return uniqueNumber;
    }

    /**
     * @param uniqueNumber the uniqueNumber to set
     */
    public void setUniqueNumber(final Integer uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(fundId);
        sb.append("|");
        sb.append(codcofdetId);
        sb.append("|");
        sb.append(fundCode);
        sb.append("|");
        sb.append(fundDesc);
        sb.append("|");
        sb.append(fundCompositecode);
        sb.append("|");
        sb.append(fundParentId);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(userId);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(lmoddate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        return sb.toString();
    }

    /**
     * @return the bankAccounts
     */
    public List<BankAccountMasterDto> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * @param bankAccounts the bankAccounts to set
     */
    public void setBankAccounts(final List<BankAccountMasterDto> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    /**
     * @return the childLevel
     */
    public String getChildLevel() {
        return childLevel;
    }

    /**
     * @param childLevel the childLevel to set
     */
    public void setChildLevel(final String childLevel) {
        this.childLevel = childLevel;
    }

    /**
     * @return the childCode
     */
    public String getChildCode() {
        return childCode;
    }

    /**
     * @param childCode the childCode to set
     */
    public void setChildCode(final String childCode) {
        this.childCode = childCode;
    }

    /**
     * @return the childDesc
     */
    public String getChildDesc() {
        return childDesc;
    }

    /**
     * @param childDesc the childDesc to set
     */
    public void setChildDesc(final String childDesc) {
        this.childDesc = childDesc;
    }

    /**
     * @return the childFinalCode
     */
    public String getChildFinalCode() {
        return childFinalCode;
    }

    /**
     * @param childFinalCode the childFinalCode to set
     */
    public void setChildFinalCode(final String childFinalCode) {
        this.childFinalCode = childFinalCode;
    }

    /**
     * @return the childParentLevel
     */
    public String getChildParentLevel() {
        return childParentLevel;
    }

    /**
     * @param childParentLevel the childParentLevel to set
     */
    public void setChildParentLevel(final String childParentLevel) {
        this.childParentLevel = childParentLevel;
    }

    /**
     * @return the childParentCode
     */
    public String getChildParentCode() {
        return childParentCode;
    }

    /**
     * @param childParentCode the childParentCode to set
     */
    public void setChildParentCode(final String childParentCode) {
        this.childParentCode = childParentCode;
    }

    /**
     * @return the newRecord
     */
    public boolean isNewRecord() {
        return newRecord;
    }

    /**
     * @param newRecord the newRecord to set
     */
    public void setNewRecord(final boolean newRecord) {
        this.newRecord = newRecord;
    }

    /**
     * @return the editedDataChildLevel
     */
    public String getEditedDataChildLevel() {
        return editedDataChildLevel;
    }

    /**
     * @param editedDataChildLevel the editedDataChildLevel to set
     */
    public void setEditedDataChildLevel(final String editedDataChildLevel) {
        this.editedDataChildLevel = editedDataChildLevel;
    }

    /**
     * @return the editedDataChildParentLevel
     */
    public String getEditedDataChildParentLevel() {
        return editedDataChildParentLevel;
    }

    /**
     * @param editedDataChildParentLevel the editedDataChildParentLevel to set
     */
    public void setEditedDataChildParentLevel(final String editedDataChildParentLevel) {
        this.editedDataChildParentLevel = editedDataChildParentLevel;
    }

    /**
     * @return the editedDataChildCode
     */
    public String getEditedDataChildCode() {
        return editedDataChildCode;
    }

    /**
     * @param editedDataChildCode the editedDataChildCode to set
     */
    public void setEditedDataChildCode(final String editedDataChildCode) {
        this.editedDataChildCode = editedDataChildCode;
    }

    /**
     * @return the editedDataChildParentCode
     */
    public String getEditedDataChildParentCode() {
        return editedDataChildParentCode;
    }

    /**
     * @param editedDataChildParentCode the editedDataChildParentCode to set
     */
    public void setEditedDataChildParentCode(final String editedDataChildParentCode) {
        this.editedDataChildParentCode = editedDataChildParentCode;
    }

    /**
     * @return the editedDataChildDesc
     */
    public String getEditedDataChildDesc() {
        return editedDataChildDesc;
    }

    /**
     * @param editedDataChildDesc the editedDataChildDesc to set
     */
    public void setEditedDataChildDesc(final String editedDataChildDesc) {
        this.editedDataChildDesc = editedDataChildDesc;
    }

    /**
     * @return the editedDataChildCompositeCode
     */
    public String getEditedDataChildCompositeCode() {
        return editedDataChildCompositeCode;
    }

    /**
     * @param editedDataChildCompositeCode the editedDataChildCompositeCode to set
     */
    public void setEditedDataChildCompositeCode(final String editedDataChildCompositeCode) {
        this.editedDataChildCompositeCode = editedDataChildCompositeCode;
    }

    public String getDefaultOrgFlag() {
        return defaultOrgFlag;
    }

    public void setDefaultOrgFlag(final String defaultOrgFlag) {
        this.defaultOrgFlag = defaultOrgFlag;
    }

}
