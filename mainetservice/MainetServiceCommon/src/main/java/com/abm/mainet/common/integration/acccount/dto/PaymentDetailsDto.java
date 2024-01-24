package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tejas.kotekar
 *
 */
public class PaymentDetailsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String billNumber;
    private Long billTypeId;
    private String billDate;
    private String amount;
    private String deductions;
    private String netPayable;
    private BigDecimal paymentAmount;
    private String paymentAmountDesc;
    private BigDecimal paymentAmt;
    private String accountHead;
    private String accountCode;
    private String vendorName;
    private String paymentNo;
    private String paymentDate;
    private Long bchId;
    private Long bdhId;
    private Long bmId;
    private Long paymentId;
    private String paymentIdFlagExist;
    private String tdsPaymentAmt;
    private boolean vendorDetCheckFlag;
    
    private String bankName;
    private String branchName;
    private String bankAccountNumber;
    private String ifscCode;
    
    private String functionDesc;
    private String functionaryDesc;
    
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(final String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(final String billDate) {
        this.billDate = billDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(final String amount) {
        this.amount = amount;
    }

    public String getDeductions() {
        return deductions;
    }

    public void setDeductions(final String deductions) {
        this.deductions = deductions;
    }

    public String getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(final String netPayable) {
        this.netPayable = netPayable;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(final BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentAmountDesc() {
        return paymentAmountDesc;
    }

    public void setPaymentAmountDesc(final String paymentAmountDesc) {
        this.paymentAmountDesc = paymentAmountDesc;
    }

    public BigDecimal getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(String accountHead) {
        this.accountHead = accountHead;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public Long getBchId() {
        return bchId;
    }

    public void setBchId(Long bchId) {
        this.bchId = bchId;
    }

    public Long getBdhId() {
        return bdhId;
    }

    public void setBdhId(Long bdhId) {
        this.bdhId = bdhId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public boolean isVendorDetCheckFlag() {
        return vendorDetCheckFlag;
    }

    public void setVendorDetCheckFlag(boolean vendorDetCheckFlag) {
        this.vendorDetCheckFlag = vendorDetCheckFlag;
    }

    public Long getBmId() {
        return bmId;
    }

    public void setBmId(Long bmId) {
        this.bmId = bmId;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentIdFlagExist() {
        return paymentIdFlagExist;
    }

    public void setPaymentIdFlagExist(String paymentIdFlagExist) {
        this.paymentIdFlagExist = paymentIdFlagExist;
    }

    public String getTdsPaymentAmt() {
        return tdsPaymentAmt;
    }

    public void setTdsPaymentAmt(String tdsPaymentAmt) {
        this.tdsPaymentAmt = tdsPaymentAmt;
    }

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getFunctionDesc() {
		return functionDesc;
	}

	public void setFunctionDesc(String functionDesc) {
		this.functionDesc = functionDesc;
	}

	public String getFunctionaryDesc() {
		return functionaryDesc;
	}

	public void setFunctionaryDesc(String functionaryDesc) {
		this.functionaryDesc = functionaryDesc;
	}

	public Long getBillTypeId() {
		return billTypeId;
	}

	public void setBillTypeId(Long billTypeId) {
		this.billTypeId = billTypeId;
	}

}
