
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetoryRevisionBean implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long bugrevId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long faYearid;

    private Long cpdBugtypeId;

    private Long cpdBugsubtypeId;

    private Long dpDeptid;

    private Long dpDeptidExp;

    private Long cpdBugsubtypeIdExp;

    private String prBudgetCode;

    private Long prBudgetCodeid;

    private Long currentYearBugAmt;

    private Long lastYearCount;

    private Long estimateForNextyear;

    private String estimateForNextyearDup;

    private Long apprBugStandCom;

    private Long finalizedBugGenBody;

    private Long orgid;

    private int langId;

    private Long createdBy;

    private Date createdDate;

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

    private String fundCode;

    private String fieldCode;

    private String functionCode;

    private String primaryAcHeadCode;

    private String sacHeadCode;

    private String hasError;

    private Long secondaryId;

    private String headDesc;

    private String formattedCurrency;

    private String formattedCurrency1;

    private String orginalEstamt;

    private String revisedEstamt;

    private BigDecimal revisedAmount;

    private String revisedAmountDup;

    private String tempDate;

    private int index;

    private String accountHeads;

    // view Data Showing All Fields

    private String financialYearDesc;

    private String departmentDesc;

    private String cpdBugtypeDesc;

    private String cpdBugsubtypeDesc;

    private String cpdBugtypeIdHidden;

    private List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList = new ArrayList<>();

    private List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList = new ArrayList<>();

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------

    /**
     * @return the currentYearBugAmt
     */
    public Long getCurrentYearBugAmt() {
        return currentYearBugAmt;
    }

    /**
     * @param currentYearBugAmt the currentYearBugAmt to set
     */
    public void setCurrentYearBugAmt(final Long currentYearBugAmt) {
        this.currentYearBugAmt = currentYearBugAmt;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
    }

    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    public void setCpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setLastYearCount(final Long lastYearCount) {
        this.lastYearCount = lastYearCount;
    }

    public Long getLastYearCount() {
        return lastYearCount;
    }

    public void setEstimateForNextyear(final Long estimateForNextyear) {
        this.estimateForNextyear = estimateForNextyear;
    }

    public Long getEstimateForNextyear() {
        return estimateForNextyear;
    }

    public void setApprBugStandCom(final Long apprBugStandCom) {
        this.apprBugStandCom = apprBugStandCom;
    }

    public Long getApprBugStandCom() {
        return apprBugStandCom;
    }

    public void setFinalizedBugGenBody(final Long finalizedBugGenBody) {
        this.finalizedBugGenBody = finalizedBugGenBody;
    }

    public Long getFinalizedBugGenBody() {
        return finalizedBugGenBody;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

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
     * @return the fundCode
     */
    public String getFundCode() {
        return fundCode;
    }

    /**
     * @param fundCode the fundCode to set
     */
    public void setFundCode(final String fundCode) {
        this.fundCode = fundCode;
    }

    /**
     * @return the fieldCode
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * @param fieldCode the fieldCode to set
     */
    public void setFieldCode(final String fieldCode) {
        this.fieldCode = fieldCode;
    }

    /**
     * @return the functionCode
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * @param functionCode the functionCode to set
     */
    public void setFunctionCode(final String functionCode) {
        this.functionCode = functionCode;
    }

    /**
     * @return the primaryAcHeadCode
     */
    public String getPrimaryAcHeadCode() {
        return primaryAcHeadCode;
    }

    /**
     * @param primaryAcHeadCode the primaryAcHeadCode to set
     */
    public void setPrimaryAcHeadCode(final String primaryAcHeadCode) {
        this.primaryAcHeadCode = primaryAcHeadCode;
    }

    /**
     * @return the sacHeadCode
     */
    public String getSacHeadCode() {
        return sacHeadCode;
    }

    /**
     * @param sacHeadCode the sacHeadCode to set
     */
    public void setSacHeadCode(final String sacHeadCode) {
        this.sacHeadCode = sacHeadCode;
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
     * @return the headDesc
     */
    public String getHeadDesc() {
        return headDesc;
    }

    /**
     * @param headDesc the headDesc to set
     */
    public void setHeadDesc(final String headDesc) {
        this.headDesc = headDesc;
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
     * @return the formattedCurrency1
     */
    public String getFormattedCurrency1() {
        return formattedCurrency1;
    }

    /**
     * @param formattedCurrency1 the formattedCurrency1 to set
     */
    public void setFormattedCurrency1(final String formattedCurrency1) {
        this.formattedCurrency1 = formattedCurrency1;
    }

    /**
     * @return the orginalEstamt
     */
    public String getOrginalEstamt() {
        return orginalEstamt;
    }

    /**
     * @param orginalEstamt the orginalEstamt to set
     */
    public void setOrginalEstamt(final String orginalEstamt) {
        this.orginalEstamt = orginalEstamt;
    }

    /**
     * @return the revisedEstamt
     */
    public String getRevisedEstamt() {
        return revisedEstamt;
    }

    /**
     * @param revisedEstamt the revisedEstamt to set
     */
    public void setRevisedEstamt(final String revisedEstamt) {
        this.revisedEstamt = revisedEstamt;
    }

    /**
     * @return the tempDate
     */
    public String getTempDate() {
        return tempDate;
    }

    /**
     * @param tempDate the tempDate to set
     */
    public void setTempDate(final String tempDate) {
        this.tempDate = tempDate;
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
     * @return the bugprojRevBeanList
     */
    public List<AccountBudgetProjectedRevenueEntryBean> getBugprojRevBeanList() {
        return bugprojRevBeanList;
    }

    /**
     * @param bugprojRevBeanList the bugprojRevBeanList to set
     */
    public void setBugprojRevBeanList(
            final List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList) {
        this.bugprojRevBeanList = bugprojRevBeanList;
    }

    /**
     * @return the bugprojExpBeanList
     */
    public List<AccountBudgetProjectedExpenditureBean> getBugprojExpBeanList() {
        return bugprojExpBeanList;
    }

    /**
     * @param bugprojExpBeanList the bugprojExpBeanList to set
     */
    public void setBugprojExpBeanList(
            final List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList) {
        this.bugprojExpBeanList = bugprojExpBeanList;
    }

    /**
     * @return the estimateForNextyearDup
     */
    public String getEstimateForNextyearDup() {
        return estimateForNextyearDup;
    }

    /**
     * @param estimateForNextyearDup the estimateForNextyearDup to set
     */
    public void setEstimateForNextyearDup(
            final String estimateForNextyearDup) {
        this.estimateForNextyearDup = estimateForNextyearDup;
    }

    /**
     * @return the bugrevId
     */
    public Long getBugrevId() {
        return bugrevId;
    }

    /**
     * @param bugrevId the bugrevId to set
     */
    public void setBugrevId(final Long bugrevId) {
        this.bugrevId = bugrevId;
    }

    /**
     * @return the revisedAmount
     */
    public BigDecimal getRevisedAmount() {
        return revisedAmount;
    }

    /**
     * @param revisedAmount the revisedAmount to set
     */
    public void setRevisedAmount(final BigDecimal revisedAmount) {
        this.revisedAmount = revisedAmount;
    }

    /**
     * @return the revisedAmountDup
     */
    public String getRevisedAmountDup() {
        return revisedAmountDup;
    }

    /**
     * @param revisedAmountDup the revisedAmountDup to set
     */
    public void setRevisedAmountDup(final String revisedAmountDup) {
        this.revisedAmountDup = revisedAmountDup;
    }

    /**
     * @return the dpDeptidExp
     */
    public Long getDpDeptidExp() {
        return dpDeptidExp;
    }

    /**
     * @param dpDeptidExp the dpDeptidExp to set
     */
    public void setDpDeptidExp(final Long dpDeptidExp) {
        this.dpDeptidExp = dpDeptidExp;
    }

    /**
     * @return the cpdBugsubtypeIdExp
     */
    public Long getCpdBugsubtypeIdExp() {
        return cpdBugsubtypeIdExp;
    }

    /**
     * @param cpdBugsubtypeIdExp the cpdBugsubtypeIdExp to set
     */
    public void setCpdBugsubtypeIdExp(final Long cpdBugsubtypeIdExp) {
        this.cpdBugsubtypeIdExp = cpdBugsubtypeIdExp;
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
     * @return the prBudgetCode
     */
    public String getPrBudgetCode() {
        return prBudgetCode;
    }

    /**
     * @param prBudgetCode the prBudgetCode to set
     */
    public void setPrBudgetCode(final String prBudgetCode) {
        this.prBudgetCode = prBudgetCode;
    }

    /**
     * @return the prBudgetCodeid
     */
    public Long getPrBudgetCodeid() {
        return prBudgetCodeid;
    }

    /**
     * @param prBudgetCodeid the prBudgetCodeid to set
     */
    public void setPrBudgetCodeid(final Long prBudgetCodeid) {
        this.prBudgetCodeid = prBudgetCodeid;
    }

    public String getFinancialYearDesc() {
        return financialYearDesc;
    }

    public void setFinancialYearDesc(final String financialYearDesc) {
        this.financialYearDesc = financialYearDesc;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public String getCpdBugtypeDesc() {
        return cpdBugtypeDesc;
    }

    public void setCpdBugtypeDesc(final String cpdBugtypeDesc) {
        this.cpdBugtypeDesc = cpdBugtypeDesc;
    }

    public String getCpdBugsubtypeDesc() {
        return cpdBugsubtypeDesc;
    }

    public void setCpdBugsubtypeDesc(final String cpdBugsubtypeDesc) {
        this.cpdBugsubtypeDesc = cpdBugsubtypeDesc;
    }

    public String getCpdBugtypeIdHidden() {
        return cpdBugtypeIdHidden;
    }

    public void setCpdBugtypeIdHidden(final String cpdBugtypeIdHidden) {
        this.cpdBugtypeIdHidden = cpdBugtypeIdHidden;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(bugrevId);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(cpdBugtypeId);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
        sb.append("|");
        sb.append(dpDeptid);
        sb.append("|");
        sb.append(prBudgetCodeid);
        sb.append("|");
        sb.append(lastYearCount);
        sb.append("|");
        sb.append(estimateForNextyear);
        sb.append("|");
        sb.append(apprBugStandCom);
        sb.append("|");
        sb.append(finalizedBugGenBody);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
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

}
