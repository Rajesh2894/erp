
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.AccountBudgetOpenBalanceMasterDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetOpenBalanceBean implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";

    private AccountBudgetOpenBalanceMasterDto dto;

    private Long opnId;

    private Date opnEntrydate;

    private Long fundId;

    private Long functionId;

    private Long fieldId;

    private Long pacHeadId;

    private Long sacHeadId;

    private String acHeadCode;

    private String openbalAmt;

    private String cpdIdDrcr;

    private String cpdIdDrCrDesc;

    private Long faYearid;

    private Long orgid;

    private Long userId;

    private int langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;

    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    private Long fi04N1;

    @Size(max = 200)
    private String fi04V1;

    private Date fi04D1;

    @Size(max = 1)
    private String fi04Lo1;

    private String tempDate;

    private String fundCode;

    private String fieldCode;

    private String functionCode;

    private String functionCompisteCode;

    private String primaryHeadCompositeCode;

    private String primaryAcHeadCode;

    private String primaryAcCodeHeadDesc;

    private String primaryAcCodeHeadCompcode;

    private String accountHeads;

    private String sacHeadCode;

    private String sacHeadDesc;

    private String hasError;

    private String formattedCurrency;

    private Long secondaryId;

    private String flagFlzd;

    private String flagFlzdDup;

    private int index;

    private String financialYearDesc;

    private String status;

    private String opnBalType;

    private String opnBalTypeDesc;

    private Long drTypeValue;

    private Long crTypeValue;

    @Transient
    private String uploadFileName;

    private List<AccountBudgetOpenBalanceMasterDto> bugReappMasterDtoList = new ArrayList<>();

    /**
     * @return the dto
     */
    public AccountBudgetOpenBalanceMasterDto getDto() {
        return dto;
    }

    /**
     * @param dto the dto to set
     */
    public void setDto(final AccountBudgetOpenBalanceMasterDto dto) {
        this.dto = dto;
    }

    /**
     * @return the secondaryId
     */
    public Long getSecondaryId() {
        return secondaryId;
    }

    /**
     * @param secondaryId the secondaryId to set
     */
    public void setSecondaryId(final Long secondaryId) {
        this.secondaryId = secondaryId;
    }

    /**
     * @return the formattedCurrency
     */
    public String getFormattedCurrency() {
        return formattedCurrency;
    }

    /**
     * @param formattedCurrency the formattedCurrency to set
     */
    public void setFormattedCurrency(final String formattedCurrency) {
        this.formattedCurrency = formattedCurrency;
    }

    /**
     * @return the hasError
     */
    public String getHasError() {
        return hasError;
    }

    /**
     * @param hasError the hasError to set
     */
    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public void setOpnId(final Long opnId) {
        this.opnId = opnId;
    }

    public Long getOpnId() {
        return opnId;
    }

    public String getAlreadyExists() {
        return alreadyExists;
    }

    public void setAlreadyExists(final String alreadyExists) {
        this.alreadyExists = alreadyExists;
    }

    public void setOpnEntrydate(final Date opnEntrydate) {
        this.opnEntrydate = opnEntrydate;
    }

    public Date getOpnEntrydate() {
        return opnEntrydate;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setOpenbalAmt(final String openbalAmt) {
        this.openbalAmt = openbalAmt;
    }

    public String getOpenbalAmt() {
        return openbalAmt;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
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

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
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

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(final String fundCode) {
        this.fundCode = fundCode;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(final String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    public String getPrimaryAcHeadCode() {
        return primaryAcHeadCode;
    }

    public void setPrimaryAcHeadCode(final String primaryAcHeadCode) {
        this.primaryAcHeadCode = primaryAcHeadCode;
    }

    public String getCpdIdDrcr() {
        return cpdIdDrcr;
    }

    public void setCpdIdDrcr(final String cpdIdDrcr) {
        this.cpdIdDrcr = cpdIdDrcr;
    }

    public String getTempDate() {
        return tempDate;
    }

    public void setTempDate(final String tempDate) {
        this.tempDate = tempDate;
    }

    public String getSacHeadCode() {
        return sacHeadCode;
    }

    public void setSacHeadCode(final String sacHeadCode) {
        this.sacHeadCode = sacHeadCode;
    }

    /**
     * @return the primaryAcCodeHeadDesc
     */
    public String getPrimaryAcCodeHeadDesc() {
        return primaryAcCodeHeadDesc;
    }

    /**
     * @param primaryAcCodeHeadDesc the primaryAcCodeHeadDesc to set
     */
    public void setPrimaryAcCodeHeadDesc(
            final String primaryAcCodeHeadDesc) {
        this.primaryAcCodeHeadDesc = primaryAcCodeHeadDesc;
    }

    /**
     * @return the primaryAcCodeHeadCompcode
     */
    public String getPrimaryAcCodeHeadCompcode() {
        return primaryAcCodeHeadCompcode;
    }

    /**
     * @param primaryAcCodeHeadCompcode the primaryAcCodeHeadCompcode to set
     */
    public void setPrimaryAcCodeHeadCompcode(
            final String primaryAcCodeHeadCompcode) {
        this.primaryAcCodeHeadCompcode = primaryAcCodeHeadCompcode;
    }

    /**
     * @return the sacHeadDesc
     */
    public String getSacHeadDesc() {
        return sacHeadDesc;
    }

    /**
     * @param sacHeadDesc the sacHeadDesc to set
     */
    public void setSacHeadDesc(final String sacHeadDesc) {
        this.sacHeadDesc = sacHeadDesc;
    }

    /**
     * @return the bugReappMasterDtoList
     */
    public List<AccountBudgetOpenBalanceMasterDto> getBugReappMasterDtoList() {
        return bugReappMasterDtoList;
    }

    /**
     * @param bugReappMasterDtoList the bugReappMasterDtoList to set
     */
    public void setBugReappMasterDtoList(
            final List<AccountBudgetOpenBalanceMasterDto> bugReappMasterDtoList) {
        this.bugReappMasterDtoList = bugReappMasterDtoList;
    }

    /**
     * @return the flagFlzd
     */
    public String getFlagFlzd() {
        return flagFlzd;
    }

    /**
     * @param flagFlzd the flagFlzd to set
     */
    public void setFlagFlzd(final String flagFlzd) {
        this.flagFlzd = flagFlzd;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * @return the accountHeads
     */
    public String getAccountHeads() {
        return accountHeads;
    }

    /**
     * @param accountHeads the accountHeads to set
     */
    public void setAccountHeads(final String accountHeads) {
        this.accountHeads = accountHeads;
    }

    /**
     * @return the flagFlzdDup
     */
    public String getFlagFlzdDup() {
        return flagFlzdDup;
    }

    /**
     * @param flagFlzdDup the flagFlzdDup to set
     */
    public void setFlagFlzdDup(final String flagFlzdDup) {
        this.flagFlzdDup = flagFlzdDup;
    }

    public String getFinancialYearDesc() {
        return financialYearDesc;
    }

    public void setFinancialYearDesc(final String financialYearDesc) {
        this.financialYearDesc = financialYearDesc;
    }

    public String getCpdIdDrCrDesc() {
        return cpdIdDrCrDesc;
    }

    public void setCpdIdDrCrDesc(final String cpdIdDrCrDesc) {
        this.cpdIdDrCrDesc = cpdIdDrCrDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getOpnBalType() {
        return opnBalType;
    }

    public void setOpnBalType(final String opnBalType) {
        this.opnBalType = opnBalType;
    }

    public Long getDrTypeValue() {
        return drTypeValue;
    }

    public void setDrTypeValue(final Long drTypeValue) {
        this.drTypeValue = drTypeValue;
    }

    public Long getCrTypeValue() {
        return crTypeValue;
    }

    public void setCrTypeValue(final Long crTypeValue) {
        this.crTypeValue = crTypeValue;
    }

    public String getOpnBalTypeDesc() {
        return opnBalTypeDesc;
    }

    public void setOpnBalTypeDesc(final String opnBalTypeDesc) {
        this.opnBalTypeDesc = opnBalTypeDesc;
    }

    public String getFunctionCompisteCode() {
        return functionCompisteCode;
    }

    public void setFunctionCompisteCode(final String functionCompisteCode) {
        this.functionCompisteCode = functionCompisteCode;
    }

    public String getPrimaryHeadCompositeCode() {
        return primaryHeadCompositeCode;
    }

    public void setPrimaryHeadCompositeCode(final String primaryHeadCompositeCode) {
        this.primaryHeadCompositeCode = primaryHeadCompositeCode;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(final String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    @Override
    public String toString() {
        return "AccountBudgetOpenBalanceBean [alreadyExists=" + alreadyExists + ", dto=" + dto + ", opnId=" + opnId
                + ", opnEntrydate=" + opnEntrydate + ", fundId=" + fundId + ", functionId=" + functionId + ", fieldId="
                + fieldId + ", pacHeadId=" + pacHeadId + ", sacHeadId=" + sacHeadId + ", acHeadCode=" + acHeadCode
                + ", openbalAmt=" + openbalAmt + ", cpdIdDrcr=" + cpdIdDrcr + ", cpdIdDrCrDesc=" + cpdIdDrCrDesc
                + ", faYearid=" + faYearid + ", orgid=" + orgid + ", userId=" + userId + ", langId=" + langId
                + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac="
                + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1 + ", fi04D1="
                + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", tempDate=" + tempDate + ", fundCode=" + fundCode + ", fieldCode="
                + fieldCode + ", functionCode=" + functionCode + ", functionCompisteCode=" + functionCompisteCode
                + ", primaryHeadCompositeCode=" + primaryHeadCompositeCode + ", primaryAcHeadCode=" + primaryAcHeadCode
                + ", primaryAcCodeHeadDesc=" + primaryAcCodeHeadDesc + ", primaryAcCodeHeadCompcode="
                + primaryAcCodeHeadCompcode + ", accountHeads=" + accountHeads + ", sacHeadCode=" + sacHeadCode
                + ", sacHeadDesc=" + sacHeadDesc + ", hasError=" + hasError + ", formattedCurrency=" + formattedCurrency
                + ", secondaryId=" + secondaryId + ", flagFlzd=" + flagFlzd + ", flagFlzdDup=" + flagFlzdDup
                + ", index=" + index + ", financialYearDesc=" + financialYearDesc + ", status=" + status
                + ", opnBalType=" + opnBalType + ", opnBalTypeDesc=" + opnBalTypeDesc + ", drTypeValue=" + drTypeValue
                + ", crTypeValue=" + crTypeValue + ", bugReappMasterDtoList=" + bugReappMasterDtoList + "]";
    }

}
