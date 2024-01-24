/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetOpenBalanceMasterDto implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";

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

    private String primaryAcHeadCode;

    private String primaryAcCodeHeadDesc;

    private String primaryAcCodeHeadCompcode;

    private String sacHeadCode;

    private String sacHeadDesc;

    private String hasError;

    private String formattedCurrency;

    private String accountHeads;

    private Long secondaryId;

    private String pacHeadCodeOpenBalanceMaster;

    private String sacHeadCodeOpenBalanceMaster;

    private String pacSacHeadCodeOpenBalanceMaster;

    private String flagFlzd;

    private int index;

    private String excelFaYearid;
    private String excelCpdIdDrcr;
    private String excelFundId;
    private String excelFieldId;
    private String excelAccountHeads;
    private String excelOpenbalAmt;
    private String excelFlagFlzd;

    private String opnBalType;

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
     * @return the pacHeadCodeOpenBalanceMaster
     */
    public String getPacHeadCodeOpenBalanceMaster() {
        return pacHeadCodeOpenBalanceMaster;
    }

    /**
     * @param pacHeadCodeOpenBalanceMaster the pacHeadCodeOpenBalanceMaster to set
     */
    public void setPacHeadCodeOpenBalanceMaster(
            final String pacHeadCodeOpenBalanceMaster) {
        this.pacHeadCodeOpenBalanceMaster = pacHeadCodeOpenBalanceMaster;
    }

    /**
     * @return the sacHeadCodeOpenBalanceMaster
     */
    public String getSacHeadCodeOpenBalanceMaster() {
        return sacHeadCodeOpenBalanceMaster;
    }

    /**
     * @param sacHeadCodeOpenBalanceMaster the sacHeadCodeOpenBalanceMaster to set
     */
    public void setSacHeadCodeOpenBalanceMaster(
            final String sacHeadCodeOpenBalanceMaster) {
        this.sacHeadCodeOpenBalanceMaster = sacHeadCodeOpenBalanceMaster;
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
     * @return the pacSacHeadCodeOpenBalanceMaster
     */
    public String getPacSacHeadCodeOpenBalanceMaster() {
        return pacSacHeadCodeOpenBalanceMaster;
    }

    /**
     * @param pacSacHeadCodeOpenBalanceMaster the pacSacHeadCodeOpenBalanceMaster to set
     */
    public void setPacSacHeadCodeOpenBalanceMaster(
            final String pacSacHeadCodeOpenBalanceMaster) {
        this.pacSacHeadCodeOpenBalanceMaster = pacSacHeadCodeOpenBalanceMaster;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(opnId);
        sb.append("|");
        sb.append(opnEntrydate);
        sb.append("|");
        sb.append(fundId);
        sb.append("|");
        sb.append(functionId);
        sb.append("|");
        sb.append(fieldId);
        sb.append("|");
        sb.append(pacHeadId);
        sb.append("|");
        sb.append(sacHeadId);
        sb.append("|");
        sb.append(openbalAmt);
        sb.append("|");
        sb.append(cpdIdDrcr);
        sb.append("|");
        sb.append(faYearid);
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
        sb.append("|");
        sb.append(fi04N1);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append(fi04Lo1);
        return sb.toString();
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
     * @return the excelFaYearid
     */
    public String getExcelFaYearid() {
        return excelFaYearid;
    }

    /**
     * @param excelFaYearid the excelFaYearid to set
     */
    public void setExcelFaYearid(final String excelFaYearid) {
        this.excelFaYearid = excelFaYearid;
    }

    /**
     * @return the excelCpdIdDrcr
     */
    public String getExcelCpdIdDrcr() {
        return excelCpdIdDrcr;
    }

    /**
     * @param excelCpdIdDrcr the excelCpdIdDrcr to set
     */
    public void setExcelCpdIdDrcr(final String excelCpdIdDrcr) {
        this.excelCpdIdDrcr = excelCpdIdDrcr;
    }

    /**
     * @return the excelFundId
     */
    public String getExcelFundId() {
        return excelFundId;
    }

    /**
     * @param excelFundId the excelFundId to set
     */
    public void setExcelFundId(final String excelFundId) {
        this.excelFundId = excelFundId;
    }

    /**
     * @return the excelFieldId
     */
    public String getExcelFieldId() {
        return excelFieldId;
    }

    /**
     * @param excelFieldId the excelFieldId to set
     */
    public void setExcelFieldId(final String excelFieldId) {
        this.excelFieldId = excelFieldId;
    }

    /**
     * @return the excelAccountHeads
     */
    public String getExcelAccountHeads() {
        return excelAccountHeads;
    }

    /**
     * @param excelAccountHeads the excelAccountHeads to set
     */
    public void setExcelAccountHeads(final String excelAccountHeads) {
        this.excelAccountHeads = excelAccountHeads;
    }

    /**
     * @return the excelOpenbalAmt
     */
    public String getExcelOpenbalAmt() {
        return excelOpenbalAmt;
    }

    /**
     * @param excelOpenbalAmt the excelOpenbalAmt to set
     */
    public void setExcelOpenbalAmt(final String excelOpenbalAmt) {
        this.excelOpenbalAmt = excelOpenbalAmt;
    }

    /**
     * @return the excelFlagFlzd
     */
    public String getExcelFlagFlzd() {
        return excelFlagFlzd;
    }

    /**
     * @param excelFlagFlzd the excelFlagFlzd to set
     */
    public void setExcelFlagFlzd(final String excelFlagFlzd) {
        this.excelFlagFlzd = excelFlagFlzd;
    }

    public String getOpnBalType() {
        return opnBalType;
    }

    public void setOpnBalType(final String opnBalType) {
        this.opnBalType = opnBalType;
    }

    public String getCpdIdDrCrDesc() {
        return cpdIdDrCrDesc;
    }

    public void setCpdIdDrCrDesc(final String cpdIdDrCrDesc) {
        this.cpdIdDrCrDesc = cpdIdDrCrDesc;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(final String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

}
