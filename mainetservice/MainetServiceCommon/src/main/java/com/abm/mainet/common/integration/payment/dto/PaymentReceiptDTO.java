/**
 *
 */
package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author umashanker.kanaujiya
 * @version 1.0.0
 *
 */
public class PaymentReceiptDTO implements Serializable {

    private static final long serialVersionUID = -2411312387404976293L;
    private long orgId;
    private long empId;
    private String firstName;
    private String mobileNo;
    private String email;
    private String productinfo;
    private String transactionId;
    private String serviceType;
    private String applicationId;
    private double amount;
    private double discount;
    private double netAmount;
    private String bankRefNo;
    private long trackId;
    private String status;
    private Date paymentDateTime;
    private String errorCode;
    private String errorMsg;
    private String labelName;
    private int langId;
    private String securityAmt;
    private String licenseFee;
    private String amountStr;
    private String finYr;
    private String caseId;
    private String appId;
    private String remark;
	/**
     * 
     */
    private String bankName;
    private String chequeNo;
    private String chequeDate=MainetConstants.BLANK;
    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(final String labelName) {
        this.labelName = labelName;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public long getEmpId() {
        return empId;
    }

    public void setEmpId(final long empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(final String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(final String productinfo) {
        this.productinfo = productinfo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(final String transactionId) {
        this.transactionId = transactionId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(final double amount) {
        this.amount = amount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(final double discount) {
        this.discount = discount;
    }

    public double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(final double netAmount) {
        this.netAmount = netAmount;
    }

    public String getBankRefNo() {
        return bankRefNo;
    }

    public void setBankRefNo(final String bankRefNo) {
        this.bankRefNo = bankRefNo;
    }

    public long getTrackId() {
        return trackId;
    }

    public void setTrackId(final long trackId) {
        this.trackId = trackId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(final Date paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

  

	public String getSecurityAmt() {
		return securityAmt;
	}

	public void setSecurityAmt(String securityAmt) {
		this.securityAmt = securityAmt;
	}

	public String getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(String licenseFee) {
		this.licenseFee = licenseFee;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getFinYr() {
		return finYr;
	}

	public void setFinYr(String finYr) {
		this.finYr = finYr;
	}


	public String getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(String chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
    public String toString() {
        return "PaymentReceiptDTO [orgId=" + orgId + ", empId=" + empId + ", firstName=" + firstName + ", mobileNo=" + mobileNo
                + ", email=" + email
                + ", productinfo=" + productinfo + ", transactionId=" + transactionId + ", serviceType=" + serviceType
                + ", applicationId="
                + applicationId + ", amount=" + amount + ", discount=" + discount + ", netAmount=" + netAmount + ", bankRefNo="
                + bankRefNo
                + ", trackId=" + trackId + ", status=" + status + ", paymentDateTime=" + paymentDateTime + ", errorCode="
                + errorCode + ", errorMsg="
                + errorMsg + ", langId=" + langId + "]";
    }

}
