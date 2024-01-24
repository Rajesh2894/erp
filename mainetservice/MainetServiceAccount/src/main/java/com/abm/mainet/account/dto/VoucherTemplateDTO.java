
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;

import com.abm.mainet.common.utility.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 *
 */
public class VoucherTemplateDTO implements Serializable {

    private static final long serialVersionUID = -508996601829530254L;
    // below fields being used in jQGrid/to show description
    private long gridId;
    private String templateTypeDesc;
    private String voucherTypeDesc;
    private String templateForDesc;
    private String statusDesc;
    private String financialYearDesc;
    private String departmentDesc;
    private String accountTypeDesc;
    private String drCrDesc;
    private String modeDesc;
    private String acHeadDesc;
    // lookUps used in jsp
    private List<LookUp> templateLookUps;
    private Map<Long, String> financialYearMap;
    private List<LookUp> vouchertTypeLookUps;
    private List<LookUp> templateForLookUps;
    private List<LookUp> statusLookUps;
    private List<LookUp> accountTypeLookUps;
    private List<LookUp> modeLookUps;
    private List<LookUp> departmentLookUps;
    private List<LookUp> debitCreditLookUps;
    private List<LookUp> budgetCodeLookUps;
    // below fields are being used for path binding
    private Long templateType;
    private String templateTypeCode;
    private Long voucherType;
    private Long templateFor;
    private String templateForCode;
    private Long status;
    private Long financialYear;
    private Long department;
    private Long accountType;
    private Long mode;
    private Long debitCredit;
    private Long sacHeadId;
    // some common fields required while insert/update
    private Long createdBy;
    private Long updatedBy;
    private Date updatedDate;
    private Long langId;
    // below field is being used to serialize table rows data
    private List<VoucherTemplateDTO> mappingDetails;
    private long currentFYearId;
    private long templateId;
    private long templateIdDet;
    private String lookupdesc;
    @JsonIgnore
    @Size(max = 100)
    private String ipMacAddress;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;

    public String getLookupdesc() {
        return lookupdesc;
    }

    public void setLookupdesc(final String lookupdesc) {
        this.lookupdesc = lookupdesc;
    }

    public long getGridId() {
        return gridId;
    }

    public void setGridId(final long gridId) {
        this.gridId = gridId;
    }

    public Long getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final Long templateType) {
        this.templateType = templateType;
    }

    public Long getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final Long voucherType) {
        this.voucherType = voucherType;
    }

    public Long getTemplateFor() {
        return templateFor;
    }

    public void setTemplateFor(final Long templateFor) {
        this.templateFor = templateFor;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    public List<LookUp> getTemplateLookUps() {
        return templateLookUps;
    }

    public void setTemplateLookUps(final List<LookUp> templateLookUps) {
        this.templateLookUps = templateLookUps;
    }

    public Map<Long, String> getFinancialYearMap() {
        return financialYearMap;
    }

    public void setFinancialYearMap(final Map<Long, String> financialYearMap) {
        this.financialYearMap = financialYearMap;
    }

    public List<LookUp> getVouchertTypeLookUps() {
        return vouchertTypeLookUps;
    }

    public void setVouchertTypeLookUps(final List<LookUp> vouchertTypeLookUps) {
        this.vouchertTypeLookUps = vouchertTypeLookUps;
    }

    public List<LookUp> getTemplateForLookUps() {
        return templateForLookUps;
    }

    public void setTemplateForLookUps(final List<LookUp> templateForLookUps) {
        this.templateForLookUps = templateForLookUps;
    }

    public List<LookUp> getStatusLookUps() {
        return statusLookUps;
    }

    public void setStatusLookUps(final List<LookUp> statusLookUps) {
        this.statusLookUps = statusLookUps;
    }

    public List<LookUp> getAccountTypeLookUps() {
        return accountTypeLookUps;
    }

    public void setAccountTypeLookUps(final List<LookUp> accountTypeLookUps) {
        this.accountTypeLookUps = accountTypeLookUps;
    }

    public List<LookUp> getModeLookUps() {
        return modeLookUps;
    }

    public void setModeLookUps(final List<LookUp> modeLookUps) {
        this.modeLookUps = modeLookUps;
    }

    public List<LookUp> getDepartmentLookUps() {
        return departmentLookUps;
    }

    public void setDepartmentLookUps(final List<LookUp> departmentLookUps) {
        this.departmentLookUps = departmentLookUps;
    }

    public List<LookUp> getDebitCreditLookUps() {
        return debitCreditLookUps;
    }

    public void setDebitCreditLookUps(final List<LookUp> debitCreditLookUps) {
        this.debitCreditLookUps = debitCreditLookUps;
    }

    public List<LookUp> getBudgetCodeLookUps() {
        return budgetCodeLookUps;
    }

    public void setBudgetCodeLookUps(final List<LookUp> budgetCodeLookUps) {
        this.budgetCodeLookUps = budgetCodeLookUps;
    }

    public String getTemplateTypeDesc() {
        return templateTypeDesc;
    }

    public void setTemplateTypeDesc(final String templateTypeDesc) {
        this.templateTypeDesc = templateTypeDesc;
    }

    public String getVoucherTypeDesc() {
        return voucherTypeDesc;
    }

    public void setVoucherTypeDesc(final String voucherTypeDesc) {
        this.voucherTypeDesc = voucherTypeDesc;
    }

    public String getTemplateForDesc() {
        return templateForDesc;
    }

    public void setTemplateForDesc(final String templateForDesc) {
        this.templateForDesc = templateForDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(final String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Long getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(final Long financialYear) {
        this.financialYear = financialYear;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(final Long department) {
        this.department = department;
    }

    public Long getAccountType() {
        return accountType;
    }

    public void setAccountType(final Long accountType) {
        this.accountType = accountType;
    }

    public Long getMode() {
        return mode;
    }

    public void setMode(final Long mode) {
        this.mode = mode;
    }

    public Long getDebitCredit() {
        return debitCredit;
    }

    public void setDebitCredit(final Long debitCredit) {
        this.debitCredit = debitCredit;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public List<VoucherTemplateDTO> getMappingDetails() {
        return mappingDetails;
    }

    public void setMappingDetails(final List<VoucherTemplateDTO> mappingDetails) {
        this.mappingDetails = mappingDetails;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public long getCurrentFYearId() {
        return currentFYearId;
    }

    public void setCurrentFYearId(final long currentFYearId) {
        this.currentFYearId = currentFYearId;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final long templateId) {
        this.templateId = templateId;
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

    public String getAccountTypeDesc() {
        return accountTypeDesc;
    }

    public void setAccountTypeDesc(final String accountTypeDesc) {
        this.accountTypeDesc = accountTypeDesc;
    }

    public String getDrCrDesc() {
        return drCrDesc;
    }

    public void setDrCrDesc(final String drCrDesc) {
        this.drCrDesc = drCrDesc;
    }

    public String getModeDesc() {
        return modeDesc;
    }

    public void setModeDesc(final String modeDesc) {
        this.modeDesc = modeDesc;
    }

    public String getTemplateTypeCode() {
        return templateTypeCode;
    }

    public void setTemplateTypeCode(final String templateTypeCode) {
        this.templateTypeCode = templateTypeCode;
    }

    public String getAcHeadDesc() {
        return acHeadDesc;
    }

    public void setAcHeadDesc(final String acHeadDesc) {
        this.acHeadDesc = acHeadDesc;
    }

    public String getTemplateForCode() {
        return templateForCode;
    }

    public void setTemplateForCode(final String templateForCode) {
        this.templateForCode = templateForCode;
    }

    public long getTemplateIdDet() {
        return templateIdDet;
    }

    public void setTemplateIdDet(final long templateIdDet) {
        this.templateIdDet = templateIdDet;
    }

    public String getIpMacAddress() {
        return ipMacAddress;
    }

    public void setIpMacAddress(String ipMacAddress) {
        this.ipMacAddress = ipMacAddress;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

}
