
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasad.kancharla
 *
 */
public class AccountBudgetProjectedExpenditureDto implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";
    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long prExpenditureid;

    private String prExpBudgetCode;

    private Long prExpenditureidExp;

    private Long prExpenditureidExpDynamic;

    private Long dpDeptid;

    private String dpDeptName;

    private Long cpdBugsubtypeId;
    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @NotNull
    private Long faYearid;

    private Long prBudgetCodeid;

    private String orginalEstamt;

    private Long revisedEstamt1;

    @NotNull
    private String expenditureAmt;

    private String revisedEstamt;

    private String prBalanceAmt;

    private Long prBalanceAmtAS;

    private Long transferAmountO;
    private Long newOrgExpAmountO;
    private Long totalTransAmountO;

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

    private String pacHeadCodeOpenBalanceMaster;

    private String sacHeadCodeOpenBalanceMaster;

    private List<String> faYearidOrgAmt = new ArrayList<>();

    private List<String> atualOfLastFaYearsList = new ArrayList<>();

    private String faYearidOrg;

    private String atualOfLastFaYears;

    private String curFinYear;

    private BigDecimal estimateForNextyear;

    private BigDecimal apprBugStandCom;

    private BigDecimal finalizedBugGenBody;

    private BigDecimal revisedAmount;

    private String curFinYearfrmdate;

    private String curFinYeartodate;

    private BigDecimal actualTillNovAmount;

    private BigDecimal budgetedFromDecAmount;

    private String curNextFinYear;

    private BigDecimal lastYearCountDupOne;

    private BigDecimal lastYearCountDupTwo;

    private BigDecimal lastYearCountDupThree;

    private String accountHeads;

    private Long ExpAllocation;

    private BigDecimal ExpAmount;

    private String ExpBudgetControlDate;
    
    private BigDecimal curYrSpamt;
    private BigDecimal nxtYrSpamt;

    /**
     * @return the prExpenditureidExp
     */
    public Long getPrExpenditureidExp() {
        return prExpenditureidExp;
    }

    /**
     * @param prExpenditureidExp the prExpenditureidExp to set
     */
    public void setPrExpenditureidExp(final Long prExpenditureidExp) {
        this.prExpenditureidExp = prExpenditureidExp;
    }

    /**
     * @return the transferAmountO
     */
    public Long getTransferAmountO() {
        return transferAmountO;
    }

    /**
     * @param transferAmountO the transferAmountO to set
     */
    public void setTransferAmountO(final Long transferAmountO) {
        this.transferAmountO = transferAmountO;
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
     * @return the prExpenditureid
     */
    public Long getPrExpenditureid() {
        return prExpenditureid;
    }

    /**
     * @param prExpenditureid the prExpenditureid to set
     */
    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    /**
     * @return the expenditureAmt
     */
    public String getExpenditureAmt() {
        return expenditureAmt;
    }

    /**
     * @param expenditureAmt the expenditureAmt to set
     */
    public void setExpenditureAmt(final String expenditureAmt) {
        this.expenditureAmt = expenditureAmt;
    }

    /**
     * @return the prBalanceAmt
     */
    public String getPrBalanceAmt() {
        return prBalanceAmt;
    }

    /**
     * @param prBalanceAmt the prBalanceAmt to set
     */
    public void setPrBalanceAmt(final String prBalanceAmt) {
        this.prBalanceAmt = prBalanceAmt;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    /**
     * @return the prExpenditureidExpDynamic
     */
    public Long getPrExpenditureidExpDynamic() {
        return prExpenditureidExpDynamic;
    }

    /**
     * @param prExpenditureidExpDynamic the prExpenditureidExpDynamic to set
     */
    public void setPrExpenditureidExpDynamic(
            final Long prExpenditureidExpDynamic) {
        this.prExpenditureidExpDynamic = prExpenditureidExpDynamic;
    }

    /**
     * @return the newOrgExpAmountO
     */
    public Long getNewOrgExpAmountO() {
        return newOrgExpAmountO;
    }

    /**
     * @param newOrgExpAmountO the newOrgExpAmountO to set
     */
    public void setNewOrgExpAmountO(final Long newOrgExpAmountO) {
        this.newOrgExpAmountO = newOrgExpAmountO;
    }

    /**
     * @return the totalTransAmountO
     */
    public Long getTotalTransAmountO() {
        return totalTransAmountO;
    }

    /**
     * @param totalTransAmountO the totalTransAmountO to set
     */
    public void setTotalTransAmountO(final Long totalTransAmountO) {
        this.totalTransAmountO = totalTransAmountO;
    }

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
     * @return the prBalanceAmtAS
     */
    public Long getPrBalanceAmtAS() {
        return prBalanceAmtAS;
    }

    /**
     * @param prBalanceAmtAS the prBalanceAmtAS to set
     */
    public void setPrBalanceAmtAS(final Long prBalanceAmtAS) {
        this.prBalanceAmtAS = prBalanceAmtAS;
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
    public void setActualTillNovAmount(final BigDecimal actualTillNovAmount) {
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
     * @return the prExpBudgetCode
     */
    public String getPrExpBudgetCode() {
        return prExpBudgetCode;
    }

    /**
     * @param prExpBudgetCode the prExpBudgetCode to set
     */
    public void setPrExpBudgetCode(final String prExpBudgetCode) {
        this.prExpBudgetCode = prExpBudgetCode;
    }

    /**
     * @return the expAllocation
     */
    public Long getExpAllocation() {
        return ExpAllocation;
    }

    /**
     * @param expAllocation the expAllocation to set
     */
    public void setExpAllocation(final Long expAllocation) {
        ExpAllocation = expAllocation;
    }

    /**
     * @return the expAmount
     */
    public BigDecimal getExpAmount() {
        return ExpAmount;
    }

    /**
     * @param expAmount the expAmount to set
     */
    public void setExpAmount(final BigDecimal expAmount) {
        ExpAmount = expAmount;
    }

    /**
     * @return the expBudgetControlDate
     */
    public String getExpBudgetControlDate() {
        return ExpBudgetControlDate;
    }

    /**
     * @param expBudgetControlDate the expBudgetControlDate to set
     */
    public void setExpBudgetControlDate(
            final String expBudgetControlDate) {
        ExpBudgetControlDate = expBudgetControlDate;
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

	public BigDecimal getCurYrSpamt() {
		return curYrSpamt;
	}

	public void setCurYrSpamt(BigDecimal curYrSpamt) {
		this.curYrSpamt = curYrSpamt;
	}

	public BigDecimal getNxtYrSpamt() {
		return nxtYrSpamt;
	}

	public void setNxtYrSpamt(BigDecimal nxtYrSpamt) {
		this.nxtYrSpamt = nxtYrSpamt;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(prExpenditureid);
        sb.append("|");
        sb.append(prExpBudgetCode);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(prBudgetCodeid);
        sb.append("|");
        sb.append(orginalEstamt);
        sb.append("|");
        sb.append(revisedEstamt);
        sb.append("|");
        sb.append(expenditureAmt);
        sb.append("|");
        sb.append(prBalanceAmt);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
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

}
