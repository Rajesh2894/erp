/**
 *
 */
package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prasad.kancharla
 *
 */
public class AccountBudgetCodeBean implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    private String alreadyExists = "N";
    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    private Long prBudgetCodeid;

    private String prBudgetCode;

    @NotNull
    private Long cpdBugtypeId;

    private Long dpDeptid;

    private Long cpdBugsubtypeId;

    private Long fundId;

    private Long functionId;

    private Long fieldId;

    private Long sacHeadId;

    private String fundCode;

    private String fieldCode;

    private String functionCode;

    private String sacHeadCode;

    private String accountHeads;

    private String dpDeptName;

    private String cpdIdStatusFlag;

    private String cpdIdStatusFlagDup;

    @NotNull
    private Long orgid;

    @NotNull
    private Long userId;

    // @NotNull
    private Long langId;

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

    private String hasError;

    private String fundFlagDup;

    private String fieldFlagDup;

    private Long pacHeadId;

    private String pacHeadCode;

    private List<AccountBudgetCodeDto> budgCodeMasterDtoList = new ArrayList<>();

    // view Mode Data All Fields

    private String cpdBugtypeDesc;

    private String cpdBugsubtypeDesc;

    private String departmentDesc;

    private String activeId;

    private String objectHeadType;
    @Transient
    private String uploadFileName;
    
    private String successFlag;
    
    

    public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
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
     * @return the cpdBugtypeId
     */
    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    /**
     * @param cpdBugtypeId the cpdBugtypeId to set
     */
    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
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
     * @return the fieldId
     */
    public Long getFieldId() {
        return fieldId;
    }

    /**
     * @param fieldId the fieldId to set
     */
    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * @return the sacHeadId
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * @param sacHeadId the sacHeadId to set
     */
    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the cpdIdStatusFlag
     */
    public String getCpdIdStatusFlag() {
        return cpdIdStatusFlag;
    }

    /**
     * @param cpdIdStatusFlag the cpdIdStatusFlag to set
     */
    public void setCpdIdStatusFlag(final String cpdIdStatusFlag) {
        this.cpdIdStatusFlag = cpdIdStatusFlag;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @return the lmoddate
     */
    public Date getLmoddate() {
        return lmoddate;
    }

    /**
     * @param lmoddate the lmoddate to set
     */
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
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
     * @return the budgCodeMasterDtoList
     */
    public List<AccountBudgetCodeDto> getBudgCodeMasterDtoList() {
        return budgCodeMasterDtoList;
    }

    /**
     * @param budgCodeMasterDtoList the budgCodeMasterDtoList to set
     */
    public void setBudgCodeMasterDtoList(
            final List<AccountBudgetCodeDto> budgCodeMasterDtoList) {
        this.budgCodeMasterDtoList = budgCodeMasterDtoList;
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
     * @return the fundFlagDup
     */
    public String getFundFlagDup() {
        return fundFlagDup;
    }

    /**
     * @param fundFlagDup the fundFlagDup to set
     */
    public void setFundFlagDup(final String fundFlagDup) {
        this.fundFlagDup = fundFlagDup;
    }

    /**
     * @return the fieldFlagDup
     */
    public String getFieldFlagDup() {
        return fieldFlagDup;
    }

    /**
     * @param fieldFlagDup the fieldFlagDup to set
     */
    public void setFieldFlagDup(final String fieldFlagDup) {
        this.fieldFlagDup = fieldFlagDup;
    }

    /**
     * @return the cpdIdStatusFlagDup
     */
    public String getCpdIdStatusFlagDup() {
        return cpdIdStatusFlagDup;
    }

    /**
     * @param cpdIdStatusFlagDup the cpdIdStatusFlagDup to set
     */
    public void setCpdIdStatusFlagDup(final String cpdIdStatusFlagDup) {
        this.cpdIdStatusFlagDup = cpdIdStatusFlagDup;
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

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public String getObjectHeadType() {
        return objectHeadType;
    }

    public void setObjectHeadType(final String objectHeadType) {
        this.objectHeadType = objectHeadType;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public String getPacHeadCode() {
        return pacHeadCode;
    }

    public void setPacHeadCode(final String pacHeadCode) {
        this.pacHeadCode = pacHeadCode;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(prBudgetCodeid);
        sb.append("|");
        sb.append(prBudgetCode);
        sb.append("|");
        sb.append(fundId);
        sb.append("|");
        sb.append(functionId);
        sb.append("|");
        sb.append(fieldId);
        sb.append("|");
        sb.append(sacHeadId);
        sb.append("|");
        sb.append(dpDeptid);
        sb.append("|");
        sb.append(cpdBugtypeId);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
        sb.append("|");
        sb.append(cpdIdStatusFlag);
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

    public String getActiveId() {
        return activeId;
    }

    public void setActiveId(final String activeId) {
        this.activeId = activeId;
    }

	public String getSuccessFlag() {
		return successFlag;
	}

	public void setSuccessFlag(String successFlag) {
		this.successFlag = successFlag;
	}

}