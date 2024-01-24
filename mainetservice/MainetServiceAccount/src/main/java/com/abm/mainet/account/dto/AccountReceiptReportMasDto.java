package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Null;

import org.springframework.format.annotation.DateTimeFormat;

import com.abm.mainet.common.integration.acccount.dto.TbSrcptFeesDetBean;
import com.abm.mainet.common.integration.acccount.dto.TbSrcptModesDetBean;
import com.fasterxml.jackson.annotation.JsonBackReference;

public class AccountReceiptReportMasDto implements Serializable {

    private static final long serialVersionUID = -839628558077781435L;
    private Long rmRcptid;
    private Long rmRcptno;
    private String rmDatetemp;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String rmDate;
    private String fYear;
    private String cpdFeemodeDesc;
    private String cpdFeemodeCode;
    private String vmVendorIdDesc;
    private String departmentDesc;
    private String rdAmount;
    private Long apmApplicationId;
    private String rdChequeddno;
    @Null
    private String rdChequedddatetemp;
    private String baAccountNo;
    private String baAccountName;
    private String cbBankidDesc;
    private String rmNarration;
    private String amountInWords;
    private String OrganizationName;
    private String rmAmount;
    private Long budgetCodeId;

    private String Branch;
    private String advanceFlag;
    private int index;

    private String cashCollectNo;
    private String counterNo;

    private String manualReceiptNo;
    
    private String rmReceiptNo;
    
    private String empName;
    
    private String receiptTime;

    @JsonBackReference
    private TbSrcptModesDetBean receiptModeDetail = new TbSrcptModesDetBean();
    @JsonBackReference
    private List<TbSrcptFeesDetBean> receiptFeeDetail = new ArrayList<>();

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(final Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(final Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public String getRmDatetemp() {
        return rmDatetemp;
    }

    public void setRmDatetemp(final String rmDatetemp) {
        this.rmDatetemp = rmDatetemp;
    }

    public String getRmDate() {
        return rmDate;
    }

    public void setRmDate(final String rmDate) {
        this.rmDate = rmDate;
    }

    public TbSrcptModesDetBean getReceiptModeDetail() {
        return receiptModeDetail;
    }

    public void setReceiptModeDetail(final TbSrcptModesDetBean receiptModeDetail) {
        this.receiptModeDetail = receiptModeDetail;
    }

    public String getfYear() {
        return fYear;
    }

    public void setfYear(final String fYear) {
        this.fYear = fYear;
    }

    public String getCpdFeemodeDesc() {
        return cpdFeemodeDesc;
    }

    public void setCpdFeemodeDesc(final String cpdFeemodeDesc) {
        this.cpdFeemodeDesc = cpdFeemodeDesc;
    }

    public String getCpdFeemodeCode() {
        return cpdFeemodeCode;
    }

    public void setCpdFeemodeCode(final String cpdFeemodeCode) {
        this.cpdFeemodeCode = cpdFeemodeCode;
    }

    public String getVmVendorIdDesc() {
        return vmVendorIdDesc;
    }

    public void setVmVendorIdDesc(final String vmVendorIdDesc) {
        this.vmVendorIdDesc = vmVendorIdDesc;
    }

    public String getDepartmentDesc() {
        return departmentDesc;
    }

    public void setDepartmentDesc(final String departmentDesc) {
        this.departmentDesc = departmentDesc;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public String getRdChequeddno() {
        return rdChequeddno;
    }

    public void setRdChequeddno(final String rdChequeddno) {
        this.rdChequeddno = rdChequeddno;
    }

    public String getRdChequedddatetemp() {
        return rdChequedddatetemp;
    }

    public void setRdChequedddatetemp(final String rdChequedddatetemp) {
        this.rdChequedddatetemp = rdChequedddatetemp;
    }

    public String getBaAccountNo() {
        return baAccountNo;
    }

    public void setBaAccountNo(final String baAccountNo) {
        this.baAccountNo = baAccountNo;
    }

    public String getBaAccountName() {
        return baAccountName;
    }

    public void setBaAccountName(final String baAccountName) {
        this.baAccountName = baAccountName;
    }

    public String getCbBankidDesc() {
        return cbBankidDesc;
    }

    public void setCbBankidDesc(final String cbBankidDesc) {
        this.cbBankidDesc = cbBankidDesc;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(final String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(final String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public String getOrganizationName() {
        return OrganizationName;
    }

    public void setOrganizationName(final String organizationName) {
        OrganizationName = organizationName;
    }

    public List<TbSrcptFeesDetBean> getReceiptFeeDetail() {
        return receiptFeeDetail;
    }

    public void setReceiptFeeDetail(final List<TbSrcptFeesDetBean> receiptFeeDetail) {
        this.receiptFeeDetail = receiptFeeDetail;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(final String branch) {
        Branch = branch;
    }

    public String getAdvanceFlag() {
        return advanceFlag;
    }

    public void setAdvanceFlag(final String advanceFlag) {
        this.advanceFlag = advanceFlag;
    }

    public String getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(final String rmAmount) {
        this.rmAmount = rmAmount;
    }

    public String getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(final String rdAmount) {
        this.rdAmount = rdAmount;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public String getCashCollectNo() {
        return cashCollectNo;
    }

    public void setCashCollectNo(final String cashCollectNo) {
        this.cashCollectNo = cashCollectNo;
    }

    public String getCounterNo() {
        return counterNo;
    }

    public void setCounterNo(final String counterNo) {
        this.counterNo = counterNo;
    }

    public String getManualReceiptNo() {
        return manualReceiptNo;
    }

    public void setManualReceiptNo(String manualReceiptNo) {
        this.manualReceiptNo = manualReceiptNo;
    }

	public String getRmReceiptNo() {
		return rmReceiptNo;
	}

	public void setRmReceiptNo(String rmReceiptNo) {
		this.rmReceiptNo = rmReceiptNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getReceiptTime() {
		return receiptTime;
	}

	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}

}
