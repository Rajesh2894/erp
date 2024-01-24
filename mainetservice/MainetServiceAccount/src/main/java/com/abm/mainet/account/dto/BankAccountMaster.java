
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.dto.BankAccountMasterDto;
import com.abm.mainet.common.integration.acccount.dto.SecondaryheadMaster;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
public class BankAccountMaster implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bankName;

    private Long bankType;

    private Long bmBankbranch;

    private String successFlag;

    private String alreadyExist;

    private boolean isTrasDone;

    private Long row;

    private Long fieldId;

    private Long pacHeadId;
    private Long fundId;

    private List<BankAccountMasterDto> listOfTbBankaccount = new ArrayList<>();

    private List<SecondaryheadMaster> listOfSecHeadMaster = new ArrayList<>();

    private Long ulbBankId;

    private Long secHeadCode;

    private Long accountId;

    private Long functionId;

    // for view

    private String viewBankTypeDesc;
    private String viewBranchDesc;

    private String viewAccountNo;
    private String viewAccountName;
    private String viewAccountType;
    private String viewOpenbalDate;
    private BigDecimal viewOpenBalAmt;
    private String viewAccountStatus;
    private Long orgId;
    @JsonIgnore
    @Size(max = 100)
    private String ipMacAddress;

    @Transient
    private String uploadFileName;

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public Long getBankType() {
        return bankType;
    }

    public void setBankType(final Long bankType) {
        this.bankType = bankType;
    }

    public Long getBmBankbranch() {
        return bmBankbranch;
    }

    public void setBmBankbranch(final Long bmBankbranch) {
        this.bmBankbranch = bmBankbranch;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(final String successFlag) {
        this.successFlag = successFlag;
    }

    public String getAlreadyExist() {
        return alreadyExist;
    }

    public void setAlreadyExist(final String alreadyExist) {
        this.alreadyExist = alreadyExist;
    }

    public boolean isTrasDone() {
        return isTrasDone;
    }

    public void setTrasDone(final boolean isTrasDone) {
        this.isTrasDone = isTrasDone;
    }

    public Long getRow() {
        return row;
    }

    public void setRow(final Long row) {
        this.row = row;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(final String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the ulbBankId
     */
    public Long getUlbBankId() {
        return ulbBankId;
    }

    /**
     * @param ulbBankId the ulbBankId to set
     */
    public void setUlbBankId(final Long ulbBankId) {
        this.ulbBankId = ulbBankId;
    }

    /**
     * @return the secHeadCode
     */
    public Long getSecHeadCode() {
        return secHeadCode;
    }

    /**
     * @param secHeadCode the secHeadCode to set
     */
    public void setSecHeadCode(final Long secHeadCode) {
        this.secHeadCode = secHeadCode;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(final Long accountId) {
        this.accountId = accountId;
    }

    public List<BankAccountMasterDto> getListOfTbBankaccount() {
        return listOfTbBankaccount;
    }

    public void setListOfTbBankaccount(final List<BankAccountMasterDto> listOfTbBankaccount) {
        this.listOfTbBankaccount = listOfTbBankaccount;
    }

    public List<SecondaryheadMaster> getListOfSecHeadMaster() {
        return listOfSecHeadMaster;
    }

    public void setListOfSecHeadMaster(final List<SecondaryheadMaster> listOfSecHeadMaster) {
        this.listOfSecHeadMaster = listOfSecHeadMaster;
    }

    public String getViewBankTypeDesc() {
        return viewBankTypeDesc;
    }

    public void setViewBankTypeDesc(final String viewBankTypeDesc) {
        this.viewBankTypeDesc = viewBankTypeDesc;
    }

    public String getViewBranchDesc() {
        return viewBranchDesc;
    }

    public void setViewBranchDesc(final String viewBranchDesc) {
        this.viewBranchDesc = viewBranchDesc;
    }

    public String getViewAccountNo() {
        return viewAccountNo;
    }

    public void setViewAccountNo(final String viewAccountNo) {
        this.viewAccountNo = viewAccountNo;
    }

    public String getViewAccountName() {
        return viewAccountName;
    }

    public void setViewAccountName(final String viewAccountName) {
        this.viewAccountName = viewAccountName;
    }

    public String getViewAccountType() {
        return viewAccountType;
    }

    public void setViewAccountType(final String viewAccountType) {
        this.viewAccountType = viewAccountType;
    }

    public String getViewOpenbalDate() {
        return viewOpenbalDate;
    }

    public void setViewOpenbalDate(final String viewOpenbalDate) {
        this.viewOpenbalDate = viewOpenbalDate;
    }

    public String getViewAccountStatus() {
        return viewAccountStatus;
    }

    public void setViewAccountStatus(final String viewAccountStatus) {
        this.viewAccountStatus = viewAccountStatus;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public BigDecimal getViewOpenBalAmt() {
        return viewOpenBalAmt;
    }

    public void setViewOpenBalAmt(final BigDecimal viewOpenBalAmt) {
        this.viewOpenBalAmt = viewOpenBalAmt;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getIpMacAddress() {
        return ipMacAddress;
    }

    public void setIpMacAddress(String ipMacAddress) {
        this.ipMacAddress = ipMacAddress;
    }

}
