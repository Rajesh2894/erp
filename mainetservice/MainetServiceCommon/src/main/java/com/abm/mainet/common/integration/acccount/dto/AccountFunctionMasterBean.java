/*
 * Created on 6 Jun 2016 ( Time 15:24:49 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountFunctionMasterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parentLevel;
    private String parentCode;
    private String parentDesc;
    private String parentFinalCode;

    private Long codconfigId;

    private Integer uniqueNumber;

    private Long functionStatusCpdId;

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

    private Long functionId;

    private Long codcofdetId;

    private String functionCode;

    private String functionDesc;

    private AccountFunctionMasterBean functionParentId;

    private String functionCompcode;

    private Long orgid;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private String isConfiguration;

    private String hasError;

    private String defaultOrgFlag;

    private List<AccountFunctionDto> listDto;

    private Long edittedFunctionId;

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
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
     * @return the functionStatusCpdId
     */
    public Long getFunctionStatusCpdId() {
        return functionStatusCpdId;
    }

    /**
     * @param functionStatusCpdId the functionStatusCpdId to set
     */
    public void setFunctionStatusCpdId(final Long functionStatusCpdId) {
        this.functionStatusCpdId = functionStatusCpdId;
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

    public Long getFunctionId() {
        return functionId;
    }

    public void setCodcofdetId(final Long codcofdetId) {
        this.codcofdetId = codcofdetId;
    }

    public Long getCodcofdetId() {
        return codcofdetId;
    }

    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionDesc(final String functionDesc) {
        this.functionDesc = functionDesc;
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionParentId(final AccountFunctionMasterBean functionParentId) {
        this.functionParentId = functionParentId;
    }

    public AccountFunctionMasterBean getFunctionParentId() {
        return functionParentId;
    }

    public void setFunctionCompcode(final String functionCompcode) {
        this.functionCompcode = functionCompcode;
    }

    public String getFunctionCompcode() {
        return functionCompcode;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the codconfigId
     */
    public Long getCodconfigId() {
        return codconfigId;
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

    /**
     * @param codconfigId the codconfigId to set
     */
    public void setCodconfigId(final Long codconfigId) {
        this.codconfigId = codconfigId;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(functionId);
        sb.append("|");
        sb.append(codcofdetId);
        sb.append("|");
        sb.append(functionCode);
        sb.append("|");
        sb.append(functionDesc);
        sb.append("|");
        sb.append(functionParentId);
        sb.append("|");
        sb.append(functionCompcode);
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
     * @return the listDto
     */
    public List<AccountFunctionDto> getListDto() {
        return listDto;
    }

    /**
     * @param listDto the listDto to set
     */
    public void setListDto(final List<AccountFunctionDto> listDto) {
        this.listDto = listDto;
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
     * @param parentFinalCode the parentFinalCode to set
     */
    public void setParentFinalCode(final String parentFinalCode) {
        this.parentFinalCode = parentFinalCode;
    }

    /**
     * @return the isConfiguration
     */
    public String getIsConfiguration() {
        return isConfiguration;
    }

    /**
     * @param isConfiguration the isConfiguration to set
     */
    public void setIsConfiguration(final String isConfiguration) {
        this.isConfiguration = isConfiguration;
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

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public String getDefaultOrgFlag() {
        return defaultOrgFlag;
    }

    public void setDefaultOrgFlag(final String defaultOrgFlag) {
        this.defaultOrgFlag = defaultOrgFlag;
    }

    public Long getEdittedFunctionId() {
        return edittedFunctionId;
    }

    public void setEdittedFunctionId(final Long edittedFunctionId) {
        this.edittedFunctionId = edittedFunctionId;
    }

}
