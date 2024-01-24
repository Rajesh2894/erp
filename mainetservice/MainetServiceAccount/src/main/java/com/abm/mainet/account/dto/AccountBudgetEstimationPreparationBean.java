
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetEstimationPreparationBean implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long bugestId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private String prBudgetCode;

    private Long prBudgetCodeid;

    private Long faYearid;

    private Long cpdBugtypeId;

    private Long cpdBugsubtypeId;

    private Long cpdBugsubtypeIdExp;

    private Long dpDeptid;

    private Long dpDeptidExp;

    private Long currentYearBugAmt;

    private Long lastYearCount;

    private Long lastYearCountDup;

    private BigDecimal estimateForNextyear;

    private String estimateForNextyearDup;

    private BigDecimal apprBugStandCom;

    private String apprBugStandComDup;

    private BigDecimal finalizedBugGenBody;
    
    private String finalizedBugGenBodyDup;

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

    private Long fieldId;
    
    private Long fundId;

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

    private String tempDate;

    private int index;

    private String accountHeads;

    // view Data Showing All Fields

    private String financialYearDesc;

    private String nextFinancialYearDesc;

    private String departmentDesc;

    private String cpdBugtypeDesc;

    private String cpdBugsubtypeDesc;

    private String cpdBugtypeIdHidden;

    private Long nextFaYearid;
    
    
    //new Required ment 
    private Long actualTaskId;
    private String transactionNo;
    private String checkerAuthorization;
    private Long workFlowLevel1;
    private Long workFlowLevel2;
    private Long workFlowLevel3;
    private Long workFlowLevel4;
    private Long workFlowLevel5;
    
    private String remark;
    
    
    //New field added for budget summary in Bulk Edit page
    
    private String estimateForNextyearRevAvg;
    private String apprBugStandComRevAvg;
    private String finalizedBugGenBodRevAvg;
    private String estimateForNextyearExpAvg;
    private String apprBugStandComExpAvg;
    private String finalizedBugGenBodExpAvg;
    
    

    private List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList = new ArrayList<>();

    private List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList = new ArrayList<>();

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setBugestId(final Long bugestId) {
        this.bugestId = bugestId;
    }

    public Long getBugestId() {
        return bugestId;
    }

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

    public void setEstimateForNextyear(final BigDecimal estimateForNextyear) {
        this.estimateForNextyear = estimateForNextyear;
    }

    public BigDecimal getEstimateForNextyear() {
        return estimateForNextyear;
    }

    public void setApprBugStandCom(final BigDecimal apprBugStandCom) {
        this.apprBugStandCom = apprBugStandCom;
    }

    public BigDecimal getApprBugStandCom() {
        return apprBugStandCom;
    }

    public void setFinalizedBugGenBody(final BigDecimal finalizedBugGenBody) {
        this.finalizedBugGenBody = finalizedBugGenBody;
    }

    public BigDecimal getFinalizedBugGenBody() {
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

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
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
     * @return the lastYearCountDup
     */
    public Long getLastYearCountDup() {
        return lastYearCountDup;
    }

    /**
     * @param lastYearCountDup the lastYearCountDup to set
     */
    public void setLastYearCountDup(final Long lastYearCountDup) {
        this.lastYearCountDup = lastYearCountDup;
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

    /**
     * @return the apprBugStandComDup
     */
    public String getApprBugStandComDup() {
        return apprBugStandComDup;
    }

    /**
     * @param apprBugStandComDup the apprBugStandComDup to set
     */
    public void setApprBugStandComDup(final String apprBugStandComDup) {
        this.apprBugStandComDup = apprBugStandComDup;
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

    public Long getNextFaYearid() {
        return nextFaYearid;
    }

    public void setNextFaYearid(Long nextFaYearid) {
        this.nextFaYearid = nextFaYearid;
    }

    public String getNextFinancialYearDesc() {
        return nextFinancialYearDesc;
    }

    public void setNextFinancialYearDesc(String nextFinancialYearDesc) {
        this.nextFinancialYearDesc = nextFinancialYearDesc;
    }

    public String getFinalizedBugGenBodyDup() {
		return finalizedBugGenBodyDup;
	}

	public void setFinalizedBugGenBodyDup(String finalizedBugGenBodyDup) {
		this.finalizedBugGenBodyDup = finalizedBugGenBodyDup;
	}

	
	public String getTransactionNo() {
		return transactionNo;
	}

	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}

	public Long getActualTaskId() {
		return actualTaskId;
	}

	public Long getWorkFlowLevel1() {
		return workFlowLevel1;
	}

	public Long getWorkFlowLevel2() {
		return workFlowLevel2;
	}

	public Long getWorkFlowLevel3() {
		return workFlowLevel3;
	}

	public Long getWorkFlowLevel4() {
		return workFlowLevel4;
	}

	public Long getWorkFlowLevel5() {
		return workFlowLevel5;
	}

	public void setActualTaskId(Long actualTaskId) {
		this.actualTaskId = actualTaskId;
	}

	public void setWorkFlowLevel1(Long workFlowLevel1) {
		this.workFlowLevel1 = workFlowLevel1;
	}

	public void setWorkFlowLevel2(Long workFlowLevel2) {
		this.workFlowLevel2 = workFlowLevel2;
	}

	public void setWorkFlowLevel3(Long workFlowLevel3) {
		this.workFlowLevel3 = workFlowLevel3;
	}

	public void setWorkFlowLevel4(Long workFlowLevel4) {
		this.workFlowLevel4 = workFlowLevel4;
	}

	public void setWorkFlowLevel5(Long workFlowLevel5) {
		this.workFlowLevel5 = workFlowLevel5;
	}
	
	public String getCheckerAuthorization() {
		return checkerAuthorization;
	}

	public void setCheckerAuthorization(String checkerAuthorization) {
		this.checkerAuthorization = checkerAuthorization;
	}
	
	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	public String getEstimateForNextyearRevAvg() {
		return estimateForNextyearRevAvg;
	}

	public void setEstimateForNextyearRevAvg(String estimateForNextyearRevAvg) {
		this.estimateForNextyearRevAvg = estimateForNextyearRevAvg;
	}

	public String getApprBugStandComRevAvg() {
		return apprBugStandComRevAvg;
	}

	public void setApprBugStandComRevAvg(String apprBugStandComRevAvg) {
		this.apprBugStandComRevAvg = apprBugStandComRevAvg;
	}

	public String getFinalizedBugGenBodRevAvg() {
		return finalizedBugGenBodRevAvg;
	}

	public void setFinalizedBugGenBodRevAvg(String finalizedBugGenBodRevAvg) {
		this.finalizedBugGenBodRevAvg = finalizedBugGenBodRevAvg;
	}

	public String getEstimateForNextyearExpAvg() {
		return estimateForNextyearExpAvg;
	}

	public void setEstimateForNextyearExpAvg(String estimateForNextyearExpAvg) {
		this.estimateForNextyearExpAvg = estimateForNextyearExpAvg;
	}

	public String getApprBugStandComExpAvg() {
		return apprBugStandComExpAvg;
	}

	public void setApprBugStandComExpAvg(String apprBugStandComExpAvg) {
		this.apprBugStandComExpAvg = apprBugStandComExpAvg;
	}

	public String getFinalizedBugGenBodExpAvg() {
		return finalizedBugGenBodExpAvg;
	}

	public void setFinalizedBugGenBodExpAvg(String finalizedBugGenBodExpAvg) {
		this.finalizedBugGenBodExpAvg = finalizedBugGenBodExpAvg;
	}

	@Override
    public String toString() {
        return "AccountBudgetEstimationPreparationBean [alreadyExists=" + alreadyExists + ", bugestId=" + bugestId
                + ", prBudgetCode=" + prBudgetCode + ", prBudgetCodeid=" + prBudgetCodeid + ", faYearid=" + faYearid
                + ", cpdBugtypeId=" + cpdBugtypeId + ", cpdBugsubtypeId=" + cpdBugsubtypeId + ", cpdBugsubtypeIdExp="
                + cpdBugsubtypeIdExp + ", dpDeptid=" + dpDeptid + ", dpDeptidExp=" + dpDeptidExp
                + ", currentYearBugAmt=" + currentYearBugAmt + ", lastYearCount=" + lastYearCount
                + ", lastYearCountDup=" + lastYearCountDup + ", estimateForNextyear=" + estimateForNextyear
                + ", estimateForNextyearDup=" + estimateForNextyearDup + ", apprBugStandCom=" + apprBugStandCom
                + ", apprBugStandComDup=" + apprBugStandComDup + ", finalizedBugGenBody=" + finalizedBugGenBody
                + ", orgid=" + orgid + ", langId=" + langId + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac
                + ", lgIpMacUpd=" + lgIpMacUpd + ", fieldId=" + fieldId + ", fi04V1=" + fi04V1 + ", fi04D1=" + fi04D1
                + ", fi04Lo1=" + fi04Lo1 + ", fundCode=" + fundCode + ", fieldCode=" + fieldCode + ", functionCode="
                + functionCode + ", primaryAcHeadCode=" + primaryAcHeadCode + ", sacHeadCode=" + sacHeadCode
                + ", hasError=" + hasError + ", secondaryId=" + secondaryId + ", headDesc=" + headDesc
                + ", formattedCurrency=" + formattedCurrency + ", formattedCurrency1=" + formattedCurrency1
                + ", orginalEstamt=" + orginalEstamt + ", revisedEstamt=" + revisedEstamt + ", tempDate=" + tempDate
                + ", index=" + index + ", accountHeads=" + accountHeads + ", financialYearDesc=" + financialYearDesc
                + ", nextFinancialYearDesc=" + nextFinancialYearDesc + ", departmentDesc=" + departmentDesc
                + ", cpdBugtypeDesc=" + cpdBugtypeDesc + ", cpdBugsubtypeDesc=" + cpdBugsubtypeDesc
                + ", cpdBugtypeIdHidden=" + cpdBugtypeIdHidden + ", nextFaYearid=" + nextFaYearid
                + ", bugprojRevBeanList=" + bugprojRevBeanList + ", bugprojExpBeanList=" + bugprojExpBeanList + "]";
    }

	

}
