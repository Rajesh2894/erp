package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetProjectedRevenueEntryBean implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";
    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long prProjectionid;

    private String prRevBudgetCode;

    private String prRevBudgetCodeDup;

    private Long prProjectionidRev;

    private Long prProjectionidRevDynamic;

    private AccountBudgetProjectedRevenueEntryBean revBean;

    private List<String> faYearidOrgAmt = new ArrayList<>();

    private List<String> atualOfLastFaYearsList = new ArrayList<>();

    private String curFinYear;

    private String curFinYearfrmdate;

    private String curFinYeartodate;

    private BigDecimal actualTillNovAmount;

    private Long dpDeptid;

    private Long cpdBugsubtypeId;

    private String dpDeptName;
    
    private Long budgetId;
    
    
	// ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    private Long faYearid;

    private String faYearidOrg;

    private String atualOfLastFaYears;

    private Long prBudgetCodeid;

    private String orginalEstamt;

    private String prProjected;

    private Long prProjectedRev;

    private String revisedEstamt;

    private Long revisedEstamt1;

    private String nxtYrOe;

    private Long nxtYrOe1;

    private String prCollected;

    private BigDecimal transferAmount;

    private BigDecimal newOrgRevAmount;
    
    
    private BigDecimal actualsCurrentYear;
    
    private BigDecimal expectedCurrentYear;
    
    
	
	

    @NotNull
    private Long orgid;

    @NotNull
    private Long userId;

    @NotNull
    private int langId;

    @NotNull
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

    private Long fundId;

    private Long functionId;

    private String fundCode;

    private String fieldCode;

    private String functionCode;

    private String primaryAcHeadCode;

    private String sacHeadCode;

    private String pacHeadCodeOpenBalanceMaster;

    private String sacHeadCodeOpenBalanceMaster;

    private String hasError;

    private Long secondaryId;

    private String headDesc;

    private String formattedCurrency;

    private String formattedCurrency1;

    private String formattedCurrency2;

    private String formattedCurrency3;

    private BigDecimal estimateForNextyear;

    private BigDecimal apprBugStandCom;

    private BigDecimal finalizedBugGenBody;

    private BigDecimal revisedAmount;
    
    
	private BigDecimal budgetedFromDecAmount;

    private String curNextFinYear;

    private BigDecimal lastYearCountDupOne;

    private BigDecimal lastYearCountDupTwo;

    private BigDecimal lastYearCountDupThree;

    private String accountHeads;

    private Long Allocation;

    private BigDecimal Amount;

    private String budgetControlDate;

    @Size(max = 400)
    private String remark;

    // view Data Showing All Fields

    private String financialYearDesc;

    private String departmentDesc;

    private String cpdBugsubtypeDesc;

    private Long paAdjidTr;

    @Transient
    private String uploadFileName;

    private Long fieldId;
    
    
 

    private Map<Long, String> budgetMapDynamic = new LinkedHashMap<>();

    private List<AccountBudgetProjectedRevenueEntryDto> bugRevenueMasterDtoList = new ArrayList<>();
    private String successFlag;

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
    
    public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

    /**
     * @return the revBean
     */
    public AccountBudgetProjectedRevenueEntryBean getRevBean() {
        return revBean;
    }

    /**
     * @param revBean the revBean to set
     */
    public void setRevBean(
            final AccountBudgetProjectedRevenueEntryBean revBean) {
        this.revBean = revBean;
    }

    /**
     * @return the newOrgRevAmount
     */
    public BigDecimal getNewOrgRevAmount() {
        return newOrgRevAmount;
    }

    /**
     * @param newOrgRevAmount the newOrgRevAmount to set
     */
    public void setNewOrgRevAmount(final BigDecimal newOrgRevAmount) {
        this.newOrgRevAmount = newOrgRevAmount;
    }

    /**
     * @return the transferAmount
     */
    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    /**
     * @param transferAmount the transferAmount to set
     */
    public void setTransferAmount(final BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
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
     * @return the prProjectionidRev
     */
    public Long getPrProjectionidRev() {
        return prProjectionidRev;
    }

    /**
     * @param prProjectionidRev the prProjectionidRev to set
     */
    public void setPrProjectionidRev(final Long prProjectionidRev) {
        this.prProjectionidRev = prProjectionidRev;
    }

    /**
     * @return the prProjectedRev
     */
    public Long getPrProjectedRev() {
        return prProjectedRev;
    }

    /**
     * @param prProjectedRev the prProjectedRev to set
     */
    public void setPrProjectedRev(final Long prProjectedRev) {
        this.prProjectedRev = prProjectedRev;
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
     * @return the faYearidOrg
     */
    public String getFaYearidOrg() {
        return faYearidOrg;
    }

    /**
     * @param faYearidOrg the faYearidOrg to set
     */
    public void setFaYearidOrg(final String faYearidOrg) {
        this.faYearidOrg = faYearidOrg;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
    }

    public Long getPrProjectionid() {
        return prProjectionid;
    }

    /**
     * @return the prProjectionidRevDynamic
     */
    public Long getPrProjectionidRevDynamic() {
        return prProjectionidRevDynamic;
    }

    /**
     * @param prProjectionidRevDynamic the prProjectionidRevDynamic to set
     */
    public void setPrProjectionidRevDynamic(
            final Long prProjectionidRevDynamic) {
        this.prProjectionidRevDynamic = prProjectionidRevDynamic;
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

    public void setOrginalEstamt(final String orginalEstamt) {
        this.orginalEstamt = orginalEstamt;
    }

    public String getOrginalEstamt() {
        return orginalEstamt;
    }

    public void setPrProjected(final String prProjected) {
        this.prProjected = prProjected;
    }

    public String getPrProjected() {
        return prProjected;
    }

    public void setRevisedEstamt(final String revisedEstamt) {
        this.revisedEstamt = revisedEstamt;
    }

    public String getRevisedEstamt() {
        return revisedEstamt;
    }

    public void setNxtYrOe(final String nxtYrOe) {
        this.nxtYrOe = nxtYrOe;
    }

    public String getNxtYrOe() {
        return nxtYrOe;
    }

    public void setPrCollected(final String prCollected) {
        this.prCollected = prCollected;
    }

    public String getPrCollected() {
        return prCollected;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    /**
     * @return the revisedEstamt1
     */
    public Long getRevisedEstamt1() {
        return revisedEstamt1;
    }

    /**
     * @param revisedEstamt1 the revisedEstamt1 to set
     */
    public void setRevisedEstamt1(final Long revisedEstamt1) {
        this.revisedEstamt1 = revisedEstamt1;
    }

    /**
     * @return the nxtYrOe1
     */
    public Long getNxtYrOe1() {
        return nxtYrOe1;
    }

    /**
     * @param nxtYrOe1 the nxtYrOe1 to set
     */
    public void setNxtYrOe1(final Long nxtYrOe1) {
        this.nxtYrOe1 = nxtYrOe1;
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
     * @return the faYearidOrgAmt
     */
    public List<String> getFaYearidOrgAmt() {
        return faYearidOrgAmt;
    }

    /**
     * @param faYearidOrgAmt the faYearidOrgAmt to set
     */
    public void setFaYearidOrgAmt(final List<String> faYearidOrgAmt) {
        this.faYearidOrgAmt = faYearidOrgAmt;
    }

    /**
     * @return the curFinYear
     */
    public String getCurFinYear() {
        return curFinYear;
    }

    /**
     * @param curFinYear the curFinYear to set
     */
    public void setCurFinYear(final String curFinYear) {
        this.curFinYear = curFinYear;
    }

    /**
     * @return the estimateForNextyear
     */
    public BigDecimal getEstimateForNextyear() {
        return estimateForNextyear;
    }

    /**
     * @param estimateForNextyear the estimateForNextyear to set
     */
    public void setEstimateForNextyear(final BigDecimal estimateForNextyear) {
        this.estimateForNextyear = estimateForNextyear;
    }

    /**
     * @return the apprBugStandCom
     */
    public BigDecimal getApprBugStandCom() {
        return apprBugStandCom;
    }

    /**
     * @param apprBugStandCom the apprBugStandCom to set
     */
    public void setApprBugStandCom(final BigDecimal apprBugStandCom) {
        this.apprBugStandCom = apprBugStandCom;
    }

    /**
     * @return the finalizedBugGenBody
     */
    public BigDecimal getFinalizedBugGenBody() {
        return finalizedBugGenBody;
    }

    /**
     * @param finalizedBugGenBody the finalizedBugGenBody to set
     */
    public void setFinalizedBugGenBody(final BigDecimal finalizedBugGenBody) {
        this.finalizedBugGenBody = finalizedBugGenBody;
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
     * @return the curFinYearfrmdate
     */
    public String getCurFinYearfrmdate() {
        return curFinYearfrmdate;
    }

    /**
     * @param curFinYearfrmdate the curFinYearfrmdate to set
     */
    public void setCurFinYearfrmdate(final String curFinYearfrmdate) {
        this.curFinYearfrmdate = curFinYearfrmdate;
    }

    /**
     * @return the curFinYeartodate
     */
    public String getCurFinYeartodate() {
        return curFinYeartodate;
    }

    /**
     * @param curFinYeartodate the curFinYeartodate to set
     */
    public void setCurFinYeartodate(final String curFinYeartodate) {
        this.curFinYeartodate = curFinYeartodate;
    }

    /**
     * @return the actualTillNovAmount
     */
    public BigDecimal getActualTillNovAmount() {
        return actualTillNovAmount;
    }

    /**
     * @param actualTillNovAmount the actualTillNovAmount to set
     */
    public void setActualTillNovAmount(
            final BigDecimal actualTillNovAmount) {
        this.actualTillNovAmount = actualTillNovAmount;
    }

    /**
     * @return the budgetedFromDecAmount
     */
    public BigDecimal getBudgetedFromDecAmount() {
        return budgetedFromDecAmount;
    }

    /**
     * @param budgetedFromDecAmount the budgetedFromDecAmount to set
     */
    public void setBudgetedFromDecAmount(
            final BigDecimal budgetedFromDecAmount) {
        this.budgetedFromDecAmount = budgetedFromDecAmount;
    }

    /**
     * @return the atualOfLastFaYearsList
     */
    public List<String> getAtualOfLastFaYearsList() {
        return atualOfLastFaYearsList;
    }

    /**
     * @param atualOfLastFaYearsList the atualOfLastFaYearsList to set
     */
    public void setAtualOfLastFaYearsList(
            final List<String> atualOfLastFaYearsList) {
        this.atualOfLastFaYearsList = atualOfLastFaYearsList;
    }

    /**
     * @return the atualOfLastFaYears
     */
    public String getAtualOfLastFaYears() {
        return atualOfLastFaYears;
    }

    /**
     * @param atualOfLastFaYears the atualOfLastFaYears to set
     */
    public void setAtualOfLastFaYears(final String atualOfLastFaYears) {
        this.atualOfLastFaYears = atualOfLastFaYears;
    }

    /**
     * @return the curNextFinYear
     */
    public String getCurNextFinYear() {
        return curNextFinYear;
    }

    /**
     * @param curNextFinYear the curNextFinYear to set
     */
    public void setCurNextFinYear(final String curNextFinYear) {
        this.curNextFinYear = curNextFinYear;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    /**
     * @return the dpDeptName
     */
    public String getDpDeptName() {
        return dpDeptName;
    }

    /**
     * @param dpDeptName the dpDeptName to set
     */
    public void setDpDeptName(final String dpDeptName) {
        this.dpDeptName = dpDeptName;
    }

    /**
     * @return the lastYearCountDupOne
     */
    public BigDecimal getLastYearCountDupOne() {
        return lastYearCountDupOne;
    }

    /**
     * @param lastYearCountDupOne the lastYearCountDupOne to set
     */
    public void setLastYearCountDupOne(final BigDecimal lastYearCountDupOne) {
        this.lastYearCountDupOne = lastYearCountDupOne;
    }

    /**
     * @return the lastYearCountDupTwo
     */
    public BigDecimal getLastYearCountDupTwo() {
        return lastYearCountDupTwo;
    }

    /**
     * @param lastYearCountDupTwo the lastYearCountDupTwo to set
     */
    public void setLastYearCountDupTwo(final BigDecimal lastYearCountDupTwo) {
        this.lastYearCountDupTwo = lastYearCountDupTwo;
    }

    /**
     * @return the lastYearCountDupThree
     */
    public BigDecimal getLastYearCountDupThree() {
        return lastYearCountDupThree;
    }

    /**
     * @param lastYearCountDupThree the lastYearCountDupThree to set
     */
    public void setLastYearCountDupThree(
            final BigDecimal lastYearCountDupThree) {
        this.lastYearCountDupThree = lastYearCountDupThree;
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
     * @return the prRevBudgetCode
     */
    public String getPrRevBudgetCode() {
        return prRevBudgetCode;
    }

    /**
     * @param prRevBudgetCode the prRevBudgetCode to set
     */
    public void setPrRevBudgetCode(final String prRevBudgetCode) {
        this.prRevBudgetCode = prRevBudgetCode;
    }

    /**
     * @return the allocation
     */
    public Long getAllocation() {
        return Allocation;
    }

    /**
     * @param allocation the allocation to set
     */
    public void setAllocation(final Long allocation) {
        Allocation = allocation;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return Amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(final BigDecimal amount) {
        Amount = amount;
    }

    /**
     * @return the budgetControlDate
     */
    public String getBudgetControlDate() {
        return budgetControlDate;
    }

    /**
     * @param budgetControlDate the budgetControlDate to set
     */
    public void setBudgetControlDate(final String budgetControlDate) {
        this.budgetControlDate = budgetControlDate;
    }

    /**
     * @return the cpdBugsubtypeId
     */
    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    /**
     * @param cpdBugsubtypeId the cpdBugsubtypeId to set
     */
    public void setCpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    /**
     * @return the bugRevenueMasterDtoList
     */
    public List<AccountBudgetProjectedRevenueEntryDto> getBugRevenueMasterDtoList() {
        return bugRevenueMasterDtoList;
    }

    /**
     * @param bugRevenueMasterDtoList the bugRevenueMasterDtoList to set
     */
    public void setBugRevenueMasterDtoList(
            final List<AccountBudgetProjectedRevenueEntryDto> bugRevenueMasterDtoList) {
        this.bugRevenueMasterDtoList = bugRevenueMasterDtoList;
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

    /**
     * @return the formattedCurrency2
     */
    public String getFormattedCurrency2() {
        return formattedCurrency2;
    }

    /**
     * @param formattedCurrency2 the formattedCurrency2 to set
     */
    public void setFormattedCurrency2(final String formattedCurrency2) {
        this.formattedCurrency2 = formattedCurrency2;
    }

    /**
     * @return the fundId
     */
    public Long getFundId() {
        return fundId;
    }

    /**
     * @param fundId the fundId to set
     */
    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    /**
     * @return the functionId
     */
    public Long getFunctionId() {
        return functionId;
    }

    /**
     * @param functionId the functionId to set
     */
    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    /**
     * @return the prRevBudgetCodeDup
     */
    public String getPrRevBudgetCodeDup() {
        return prRevBudgetCodeDup;
    }

    /**
     * @param prRevBudgetCodeDup the prRevBudgetCodeDup to set
     */
    public void setPrRevBudgetCodeDup(final String prRevBudgetCodeDup) {
        this.prRevBudgetCodeDup = prRevBudgetCodeDup;
    }

    /**
     * @return the remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark the remark to set
     */
    public void setRemark(final String remark) {
        this.remark = remark;
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

    public String getCpdBugsubtypeDesc() {
        return cpdBugsubtypeDesc;
    }

    public void setCpdBugsubtypeDesc(final String cpdBugsubtypeDesc) {
        this.cpdBugsubtypeDesc = cpdBugsubtypeDesc;
    }

    public Long getPaAdjidTr() {
        return paAdjidTr;
    }

    public void setPaAdjidTr(final Long paAdjidTr) {
        this.paAdjidTr = paAdjidTr;
    }

    public Map<Long, String> getBudgetMapDynamic() {
        return budgetMapDynamic;
    }

    public void setBudgetMapDynamic(final Map<Long, String> budgetMapDynamic) {
        this.budgetMapDynamic = budgetMapDynamic;
    }

    public String getFormattedCurrency3() {
        return formattedCurrency3;
    }

    public void setFormattedCurrency3(final String formattedCurrency3) {
        this.formattedCurrency3 = formattedCurrency3;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }
    
    public BigDecimal getActualsCurrentYear() {
		return actualsCurrentYear;
	}

	public void setActualsCurrentYear(BigDecimal actualsCurrentYear) {
		this.actualsCurrentYear = actualsCurrentYear;
	}

	public BigDecimal getExpectedCurrentYear() {
		return expectedCurrentYear;
	}

	public void setExpectedCurrentYear(BigDecimal expectedCurrentYear) {
		this.expectedCurrentYear = expectedCurrentYear;
	}


    public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

	@Override
    public String toString() {
        return "AccountBudgetProjectedRevenueEntryBean [alreadyExists=" + alreadyExists + ", prProjectionid="
                + prProjectionid + ", prRevBudgetCode=" + prRevBudgetCode + ", prRevBudgetCodeDup=" + prRevBudgetCodeDup
                + ", prProjectionidRev=" + prProjectionidRev + ", prProjectionidRevDynamic=" + prProjectionidRevDynamic
                + ", revBean=" + revBean + ", faYearidOrgAmt=" + faYearidOrgAmt + ", atualOfLastFaYearsList="
                + atualOfLastFaYearsList + ", curFinYear=" + curFinYear + ", curFinYearfrmdate=" + curFinYearfrmdate
                + ", curFinYeartodate=" + curFinYeartodate + ", actualTillNovAmount=" + actualTillNovAmount
                + ", dpDeptid=" + dpDeptid + ", cpdBugsubtypeId=" + cpdBugsubtypeId + ", dpDeptName=" + dpDeptName
                + ", faYearid=" + faYearid + ", faYearidOrg=" + faYearidOrg + ", atualOfLastFaYears="
                + atualOfLastFaYears + ", prBudgetCodeid=" + prBudgetCodeid + ", orginalEstamt=" + orginalEstamt
                + ", prProjected=" + prProjected + ", prProjectedRev=" + prProjectedRev + ", revisedEstamt="
                + revisedEstamt + ", revisedEstamt1=" + revisedEstamt1 + ", nxtYrOe=" + nxtYrOe + ", nxtYrOe1="
                + nxtYrOe1 + ", prCollected=" + prCollected + ", transferAmount=" + transferAmount
                + ", newOrgRevAmount=" + newOrgRevAmount + ", orgid=" + orgid + ", userId=" + userId + ", langId="
                + langId + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1
                + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", fundId=" + fundId + ", functionId=" + functionId
                + ", fundCode=" + fundCode + ", fieldCode=" + fieldCode + ", functionCode=" + functionCode
                + ", primaryAcHeadCode=" + primaryAcHeadCode + ", sacHeadCode=" + sacHeadCode
                + ", pacHeadCodeOpenBalanceMaster=" + pacHeadCodeOpenBalanceMaster + ", sacHeadCodeOpenBalanceMaster="
                + sacHeadCodeOpenBalanceMaster + ", hasError=" + hasError + ", secondaryId=" + secondaryId
                + ", headDesc=" + headDesc + ", formattedCurrency=" + formattedCurrency + ", formattedCurrency1="
                + formattedCurrency1 + ", formattedCurrency2=" + formattedCurrency2 + ", formattedCurrency3="
                + formattedCurrency3 + ", estimateForNextyear=" + estimateForNextyear + ", apprBugStandCom="
                + apprBugStandCom + ", finalizedBugGenBody=" + finalizedBugGenBody + ", revisedAmount=" + revisedAmount
                + ", budgetedFromDecAmount=" + budgetedFromDecAmount + ", curNextFinYear=" + curNextFinYear
                + ", lastYearCountDupOne=" + lastYearCountDupOne + ", lastYearCountDupTwo=" + lastYearCountDupTwo
                + ", lastYearCountDupThree=" + lastYearCountDupThree + ", accountHeads=" + accountHeads
                + ", Allocation=" + Allocation + ", Amount=" + Amount + ", budgetControlDate=" + budgetControlDate
                + ", remark=" + remark + ", financialYearDesc=" + financialYearDesc + ", departmentDesc="
                + departmentDesc + ", cpdBugsubtypeDesc=" + cpdBugsubtypeDesc + ", paAdjidTr=" + paAdjidTr
                + ", uploadFileName=" + uploadFileName + ", fieldId=" + fieldId + ", budgetMapDynamic="
                + budgetMapDynamic + ", bugRevenueMasterDtoList=" + bugRevenueMasterDtoList + "]";
    }

}
