
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountBudgetReappropriationMasterDto implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";
    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long paAdjid;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Date paEntrydate;

    private Long cpdBugtypeId;

    private Long cpdProvtypeId;

    private Long faYearid;

    private Long prProjectionid;

    private Long prExpenditureid;

    private Long fundId;

    private Long functionId;

    private Long fieldId;

    private Long pacHeadId;

    private Long sacHeadId;

    private Long provisionOldamt;

    private Long orgRevBalamt;

    private Long orginalEstamt1;

    private Long transferAmount;

    private Long totalTransAmount;

    private Long newOrgRevAmount;

    private String newOrgRevAmount1;

    private Long newOrgExpAmountO;
    private Long totalTransAmountO;
    private Long orginalEstamtExpO;

    private Long transferAmountO;

    private Long approvedBy;

    @Size(max = 400)
    private String remark;

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
    private String authFlag;

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

    private Long dpDeptid;

    private Long prBudgetCodeid;

    private String prBudgetCode;

    private String accountHeads;

    // view Data Showing All Fields

    private String financialYearDesc;

    private String departmentDesc;

    private String cpdBugtypeDesc;

    private String cpdBugsubtypeDesc;

    private String status;

    private String cpdBugtypeIdHidden;

    private List<AccountBudgetReappropriationMasterDto> bugReappMasterDtoList = new ArrayList<>();

    private List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList = new ArrayList<>();

    private List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList1 = new ArrayList<>();

    private List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList = new ArrayList<>();

    private List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList1 = new ArrayList<>();
    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------

    public void setPaAdjid(final Long paAdjid) {
        this.paAdjid = paAdjid;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return the orginalEstamt1
     */
    public Long getOrginalEstamt1() {
        return orginalEstamt1;
    }

    /**
     * @param orginalEstamt1 the orginalEstamt1 to set
     */
    public void setOrginalEstamt1(final Long orginalEstamt1) {
        this.orginalEstamt1 = orginalEstamt1;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * @return the totalTransAmount
     */
    public Long getTotalTransAmount() {
        return totalTransAmount;
    }

    /**
     * @param totalTransAmount the totalTransAmount to set
     */
    public void setTotalTransAmount(final Long totalTransAmount) {
        this.totalTransAmount = totalTransAmount;
    }

    public Long getPaAdjid() {
        return paAdjid;
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
     * @return the orginalEstamtExpO
     */
    public Long getOrginalEstamtExpO() {
        return orginalEstamtExpO;
    }

    /**
     * @param orginalEstamtExpO the orginalEstamtExpO to set
     */
    public void setOrginalEstamtExpO(final Long orginalEstamtExpO) {
        this.orginalEstamtExpO = orginalEstamtExpO;
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
     * @return the newOrgRevAmount1
     */
    public String getNewOrgRevAmount1() {
        return newOrgRevAmount1;
    }

    /**
     * @param newOrgRevAmount1 the newOrgRevAmount1 to set
     */
    public void setNewOrgRevAmount1(final String newOrgRevAmount1) {
        this.newOrgRevAmount1 = newOrgRevAmount1;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setPaEntrydate(final Date paEntrydate) {
        this.paEntrydate = paEntrydate;
    }

    public Date getPaEntrydate() {
        return paEntrydate;
    }

    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
    }

    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    public void setCpdProvtypeId(final Long cpdProvtypeId) {
        this.cpdProvtypeId = cpdProvtypeId;
    }

    public Long getCpdProvtypeId() {
        return cpdProvtypeId;
    }

    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    public Long getFaYearid() {
        return faYearid;
    }

    public void setPrProjectionid(final Long prProjectionid) {
        this.prProjectionid = prProjectionid;
    }

    public Long getPrProjectionid() {
        return prProjectionid;
    }

    public void setPrExpenditureid(final Long prExpenditureid) {
        this.prExpenditureid = prExpenditureid;
    }

    public Long getPrExpenditureid() {
        return prExpenditureid;
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

    public void setProvisionOldamt(final Long provisionOldamt) {
        this.provisionOldamt = provisionOldamt;
    }

    public Long getProvisionOldamt() {
        return provisionOldamt;
    }

    public void setOrgRevBalamt(final Long orgRevBalamt) {
        this.orgRevBalamt = orgRevBalamt;
    }

    public Long getOrgRevBalamt() {
        return orgRevBalamt;
    }

    public void setTransferAmount(final Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getTransferAmount() {
        return transferAmount;
    }

    public void setNewOrgRevAmount(final Long newOrgRevAmount) {
        this.newOrgRevAmount = newOrgRevAmount;
    }

    public Long getNewOrgRevAmount() {
        return newOrgRevAmount;
    }

    public void setApprovedBy(final Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setRemark(final String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
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

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(String authFlag) {
        this.authFlag = authFlag;
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
     * @return the serialversionuid
     */
    public static long getSerialversionuid() {
        return serialVersionUID;
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
     * @return the bugReappMasterDtoList
     */
    public List<AccountBudgetReappropriationMasterDto> getBugReappMasterDtoList() {
        return bugReappMasterDtoList;
    }

    /**
     * @param bugReappMasterDtoList the bugReappMasterDtoList to set
     */
    public void setBugReappMasterDtoList(
            final List<AccountBudgetReappropriationMasterDto> bugReappMasterDtoList) {
        this.bugReappMasterDtoList = bugReappMasterDtoList;
    }

    /**
     * @return the bugprojRevBeanList1
     */
    public List<AccountBudgetProjectedRevenueEntryBean> getBugprojRevBeanList1() {
        return bugprojRevBeanList1;
    }

    /**
     * @param bugprojRevBeanList1 the bugprojRevBeanList1 to set
     */
    public void setBugprojRevBeanList1(
            final List<AccountBudgetProjectedRevenueEntryBean> bugprojRevBeanList1) {
        this.bugprojRevBeanList1 = bugprojRevBeanList1;
    }

    /**
     * @return the bugprojExpBeanList1
     */
    public List<AccountBudgetProjectedExpenditureBean> getBugprojExpBeanList1() {
        return bugprojExpBeanList1;
    }

    /**
     * @param bugprojExpBeanList1 the bugprojExpBeanList1 to set
     */
    public void setBugprojExpBeanList1(
            final List<AccountBudgetProjectedExpenditureBean> bugprojExpBeanList1) {
        this.bugprojExpBeanList1 = bugprojExpBeanList1;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getPrBudgetCodeid() {
        return prBudgetCodeid;
    }

    public void setPrBudgetCodeid(final Long prBudgetCodeid) {
        this.prBudgetCodeid = prBudgetCodeid;
    }

    public String getPrBudgetCode() {
        return prBudgetCode;
    }

    public void setPrBudgetCode(final String prBudgetCode) {
        this.prBudgetCode = prBudgetCode;
    }

    public String getAccountHeads() {
        return accountHeads;
    }

    public void setAccountHeads(final String accountHeads) {
        this.accountHeads = accountHeads;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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
        sb.append(paAdjid);
        sb.append("|");
        sb.append(paEntrydate);
        sb.append("|");
        sb.append(cpdBugtypeId);
        sb.append("|");
        sb.append(cpdProvtypeId);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(prProjectionid);
        sb.append("|");
        sb.append(prExpenditureid);
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
        sb.append(provisionOldamt);
        sb.append("|");
        sb.append(orgRevBalamt);
        sb.append("|");
        sb.append(transferAmount);
        sb.append("|");
        sb.append(newOrgRevAmount);
        sb.append("|");
        sb.append(approvedBy);
        sb.append("|");
        sb.append(remark);
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
        sb.append(authFlag);
        return sb.toString();
    }

}
