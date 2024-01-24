/**
 * 
 */
package com.abm.mainet.common.integration.payment.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.CommonChallanDTO;

/**
 * @author sarojkumar.yadav
 *
 */
public class PaymentRequestDTO implements Serializable {

    
	
	/**
     * 
     */
    private static final long serialVersionUID = -8781613379563781240L;

    private Long serviceId;
    private String applicationId;
    private String applicantName;
    private BigDecimal dueAmt;
    private String mobNo;
    private String email;

    private String serviceName;
    private String udf1;
    private String udf2;
    private String udf3;
    private String udf4;
    private String udf5;
    private String udf6;
    private String udf7;
    private String udf8;
    private String udf9;
    private String udf10;

    private List<String> errors;
    private String finalAmount;
    private BigDecimal validateAmount;

    private String key;
    private Long txnid;
    private String salt;
    private Long bankId;
    private String hash;
    private String controlUrl;
    private String pgUrl;
    private String successUrl;
    private String failUrl;
    private String cancelUrl;
    private String errorCause;
    private Map<Long, String> onlineBankList;
    private Long orgId;
    private Long empId;
    private String payRequestMsg;
    private String pgName;
    private String merchantId;
    private String schemeCode;
    private String production;
    private String requestType;
    private String chargeFlag;
    private CommonChallanDTO recieptDTO;

    private String payModeorType;
    private String challanServiceType;
    private String documentUploaded;
    private String feeIds;

    /****************/

    private String orderId;
    private String trnCurrency;
    private String trnRemarks;
    private String recurrPeriod;
    private String recurrDay;
    private String noOfRecurring;
    private String responseUrl;
    private String addField1;
    private String addField2;
    private String addField3;
    private String addField4;
    private String addField5;
    private String addField6;
    private String addField7;
    private String addField8;

	// attribute for Bill CLoud
    private String serviceID;// Merchant Service
    private String fundID;// Merchant Fund ID
    private String checksumKey;// Merchant Fund ID
    private boolean enableChildWindowPosting;
    private boolean  enablePaymentRetry;
    private long retryAttemptCount;
    private String txtPayCategory;
    private String TypeField1;//max length 1 fixed value is R
    private String TypeField2;//max length 1 fixed value is F
 	private String TypeField3;//value is fixed as NA
    private String securityId;
    private Integer langId;
    private String flatNo;
    private String login;
    private String password;
    private String custAccNo;
    private String prodid;
    private String bankIdStr;
    private String terminalId;
    private String mcc;
    private String ddo;
    private String dto;
    private String sto;
    private String deptCode;
    private String pinCode;
	private String schemeCount;
    private String address;
    private String cityName;
    private String paymentType;
    private Long eGrsPayMode1;
	private Long eGrsPayMode2;
	private Long eGrsPayMode3;
	private Long eGrsPayMode4;
	private Long eGrsPayMode5;
	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getMobNo() {
        return mobNo;
    }

    public void setMobNo(String mobNo) {
        this.mobNo = mobNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUdf1() {
        return udf1;
    }

    public void setUdf1(String udf1) {
        this.udf1 = udf1;
    }

    public String getUdf2() {
        return udf2;
    }

    public void setUdf2(String udf2) {
        this.udf2 = udf2;
    }

    public String getUdf3() {
        return udf3;
    }

    public void setUdf3(String udf3) {
        this.udf3 = udf3;
    }

    public String getUdf4() {
        return udf4;
    }

    public void setUdf4(String udf4) {
        this.udf4 = udf4;
    }

    public String getUdf5() {
        return udf5;
    }

    public void setUdf5(String udf5) {
        this.udf5 = udf5;
    }

    public String getUdf6() {
        return udf6;
    }

    public void setUdf6(String udf6) {
        this.udf6 = udf6;
    }

    public String getUdf7() {
        return udf7;
    }

    public void setUdf7(String udf7) {
        this.udf7 = udf7;
    }

    public String getUdf8() {
        return udf8;
    }

    public void setUdf8(String udf8) {
        this.udf8 = udf8;
    }

    public String getUdf9() {
        return udf9;
    }

    public void setUdf9(String udf9) {
        this.udf9 = udf9;
    }

    public String getUdf10() {
        return udf10;
    }

    public void setUdf10(String udf10) {
        this.udf10 = udf10;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getTxnid() {
        return txnid;
    }

    public void setTxnid(Long txnid) {
        this.txnid = txnid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getControlUrl() {
        return controlUrl;
    }

    public void setControlUrl(String controlUrl) {
        this.controlUrl = controlUrl;
    }

    public String getPgUrl() {
        return pgUrl;
    }

    public void setPgUrl(String pgUrl) {
        this.pgUrl = pgUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getCancelUrl() {
        return cancelUrl;
    }

    public void setCancelUrl(String cancelUrl) {
        this.cancelUrl = cancelUrl;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public Map<Long, String> getOnlineBankList() {
        return onlineBankList;
    }

    public void setOnlineBankList(Map<Long, String> onlineBankList) {
        this.onlineBankList = onlineBankList;
    }

    public String getPayRequestMsg() {
        return payRequestMsg;
    }

    public void setPayRequestMsg(String payRequestMsg) {
        this.payRequestMsg = payRequestMsg;
    }

    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getChargeFlag() {
        return chargeFlag;
    }

    public void setChargeFlag(String chargeFlag) {
        this.chargeFlag = chargeFlag;
    }

    public CommonChallanDTO getRecieptDTO() {
        return recieptDTO;
    }

    public void setRecieptDTO(CommonChallanDTO recieptDTO) {
        this.recieptDTO = recieptDTO;
    }

    public String getPayModeorType() {
        return payModeorType;
    }

    public void setPayModeorType(String payModeorType) {
        this.payModeorType = payModeorType;
    }

    public String getChallanServiceType() {
        return challanServiceType;
    }

    public void setChallanServiceType(String challanServiceType) {
        this.challanServiceType = challanServiceType;
    }

    public String getDocumentUploaded() {
        return documentUploaded;
    }

    public void setDocumentUploaded(String documentUploaded) {
        this.documentUploaded = documentUploaded;
    }

    public String getFeeIds() {
        return feeIds;
    }

    public void setFeeIds(String feeIds) {
        this.feeIds = feeIds;
    }

    public BigDecimal getDueAmt() {
        return dueAmt;
    }

    public void setDueAmt(BigDecimal dueAmt) {
        this.dueAmt = dueAmt;
        if (dueAmt != null) {
            this.dueAmt.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public BigDecimal getValidateAmount() {
        return validateAmount;
    }

    public void setValidateAmount(BigDecimal validateAmount) {
        this.validateAmount = validateAmount;
        if (validateAmount != null) {
            this.validateAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getAddField1() {
        return addField1;
    }

    public void setAddField1(String addField1) {
        this.addField1 = addField1;
    }

    public String getAddField2() {
        return addField2;
    }

    public void setAddField2(String addField2) {
        this.addField2 = addField2;
    }

    public String getAddField3() {
        return addField3;
    }

    public void setAddField3(String addField3) {
        this.addField3 = addField3;
    }

    public String getAddField4() {
        return addField4;
    }

    public void setAddField4(String addField4) {
        this.addField4 = addField4;
    }

    public String getAddField5() {
        return addField5;
    }

    public void setAddField5(String addField5) {
        this.addField5 = addField5;
    }

    public String getAddField6() {
        return addField6;
    }

    public void setAddField6(String addField6) {
        this.addField6 = addField6;
    }

    public String getAddField7() {
        return addField7;
    }

    public void setAddField7(String addField7) {
        this.addField7 = addField7;
    }

    public String getAddField8() {
        return addField8;
    }

    public void setAddField8(String addField8) {
        this.addField8 = addField8;
    }

    public String getTrnCurrency() {
        return trnCurrency;
    }

    public void setTrnCurrency(String trnCurrency) {
        this.trnCurrency = trnCurrency;
    }

    public String getTrnRemarks() {
        return trnRemarks;
    }

    public void setTrnRemarks(String trnRemarks) {
        this.trnRemarks = trnRemarks;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRecurrPeriod() {
        return recurrPeriod;
    }

    public void setRecurrPeriod(String recurrPeriod) {
        this.recurrPeriod = recurrPeriod;
    }

    public String getRecurrDay() {
        return recurrDay;
    }

    public void setRecurrDay(String recurrDay) {
        this.recurrDay = recurrDay;
    }

    public String getNoOfRecurring() {
        return noOfRecurring;
    }

    public void setNoOfRecurring(String noOfRecurring) {
        this.noOfRecurring = noOfRecurring;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponseUrl(String responseUrl) {
        this.responseUrl = responseUrl;
    }

    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getFundID() {
        return fundID;
    }

    public void setFundID(String fundID) {
        this.fundID = fundID;
    }

    public String getChecksumKey() {
        return checksumKey;
    }

    public void setChecksumKey(String checksumKey) {
        this.checksumKey = checksumKey;
    }


	public String getTypeField1() {
		return TypeField1;
	}

	public void setTypeField1(String typeField1) {
		TypeField1 = typeField1;
	}

	public boolean isEnableChildWindowPosting() {
		return enableChildWindowPosting;
	}

	public void setEnableChildWindowPosting(boolean enableChildWindowPosting) {
		this.enableChildWindowPosting = enableChildWindowPosting;
	}

	public boolean isEnablePaymentRetry() {
		return enablePaymentRetry;
	}

	public void setEnablePaymentRetry(boolean enablePaymentRetry) {
		this.enablePaymentRetry = enablePaymentRetry;
	}

	public long getRetryAttemptCount() {
		return retryAttemptCount;
	}

	public void setRetryAttemptCount(long retryAttemptCount) {
		this.retryAttemptCount = retryAttemptCount;
	}

	public String getTxtPayCategory() {
		return txtPayCategory;
	}

	public void setTxtPayCategory(String txtPayCategory) {
		this.txtPayCategory = txtPayCategory;
	}

	public String getTypeField2() {
		return TypeField2;
	}

	public void setTypeField2(String typeField2) {
		TypeField2 = typeField2;
	}

	public String getTypeField3() {
		return TypeField3;
	}

	public void setTypeField3(String typeField3) {
		TypeField3 = typeField3;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}
    public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCustAccNo() {
		return custAccNo;
	}

	public void setCustAccNo(String custAccNo) {
		this.custAccNo = custAccNo;
	}

	public String getProdid() {
		return prodid;
	}

	public void setProdid(String prodid) {
		this.prodid = prodid;
	}

	public String getBankIdStr() {
		return bankIdStr;
	}

	public void setBankIdStr(String bankIdStr) {
		this.bankIdStr = bankIdStr;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getDdo() {
		return ddo;
	}

	public void setDdo(String ddo) {
		this.ddo = ddo;
	}

	public String getDto() {
		return dto;
	}

	public void setDto(String dto) {
		this.dto = dto;
	}

	public String getSto() {
		return sto;
	}

	public void setSto(String sto) {
		this.sto = sto;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getSchemeCount() {
		return schemeCount;
	}

	public void setSchemeCount(String schemeCount) {
		this.schemeCount = schemeCount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long geteGrsPayMode1() {
		return eGrsPayMode1;
	}

	public void seteGrsPayMode1(Long eGrsPayMode1) {
		this.eGrsPayMode1 = eGrsPayMode1;
	}

	public Long geteGrsPayMode2() {
		return eGrsPayMode2;
	}

	public void seteGrsPayMode2(Long eGrsPayMode2) {
		this.eGrsPayMode2 = eGrsPayMode2;
	}

	public Long geteGrsPayMode3() {
		return eGrsPayMode3;
	}

	public void seteGrsPayMode3(Long eGrsPayMode3) {
		this.eGrsPayMode3 = eGrsPayMode3;
	}

	public Long geteGrsPayMode4() {
		return eGrsPayMode4;
	}

	public void seteGrsPayMode4(Long eGrsPayMode4) {
		this.eGrsPayMode4 = eGrsPayMode4;
	}

	public Long geteGrsPayMode5() {
		return eGrsPayMode5;
	}

	public void seteGrsPayMode5(Long eGrsPayMode5) {
		this.eGrsPayMode5 = eGrsPayMode5;
	}

	@Override
    public String toString() {
        return "PaymentRequestDTO [serviceId=" + serviceId + ", applicationId=" + applicationId + ", applicantName="
                + applicantName + ", dueAmt=" + dueAmt + ", mobNo=" + mobNo + ", email=" + email + ", serviceName=" + serviceName
                + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6
                + ", udf7=" + udf7 + ", udf8=" + udf8 + ", udf9=" + udf9 + ", udf10=" + udf10 + ", errors=" + errors
                + ", finalAmount=" + finalAmount + ", validateAmount=" + validateAmount + ", key=" + key + ", txnid=" + txnid
                + ", salt=" + salt + ", bankId=" + bankId + ", hash=" + hash + ", controlUrl=" + controlUrl + ", pgUrl=" + pgUrl
                + ", successUrl=" + successUrl + ", failUrl=" + failUrl + ", cancelUrl=" + cancelUrl + ", errorCause="
                + errorCause + ", onlineBankList=" + onlineBankList + ", orgId=" + orgId + ", empId=" + empId + ", payRequestMsg="
                + payRequestMsg + ", pgName=" + pgName + ", merchantId=" + merchantId + ", schemeCode=" + schemeCode
                + ", production=" + production + ", requestType=" + requestType + ", chargeFlag=" + chargeFlag + ", recieptDTO="
                + recieptDTO + ", payModeorType=" + payModeorType + ", challanServiceType=" + challanServiceType
                + ", documentUploaded=" + documentUploaded + ", feeIds=" + feeIds + ", orderId=" + orderId + ", trnCurrency="
                + trnCurrency + ", trnRemarks=" + trnRemarks + ", recurrPeriod=" + recurrPeriod + ", recurrDay=" + recurrDay
                + ", noOfRecurring=" + noOfRecurring + ", responseUrl=" + responseUrl + ", addField1=" + addField1
                + ", addField2=" + addField2 + ", addField3=" + addField3 + ", addField4=" + addField4 + ", addField5="
                + addField5 + ", addField6=" + addField6 + ", addField7=" + addField7 + ", addField8=" + addField8
                + ", serviceID=" + serviceID + ", fundID=" + fundID + ", checksumKey=" + checksumKey + "]";
    }

}
