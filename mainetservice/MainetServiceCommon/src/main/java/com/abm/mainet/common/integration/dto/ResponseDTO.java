/**
 *
 */
package com.abm.mainet.common.integration.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * super class DTO made for WS call, to hold Web Service call result whether WS success or fail, this class must be inherit by
 * every module specific sub class Response DTO.
 * @author Vivek.Kumar
 * @since 26-Feb-2016
 */
public class ResponseDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -280623548748434176L;

    private String pinCode;
    private String email;
    private String phone;
    private String registrationNo;
    private Integer registrationYear;
    private Integer noOfCopies;
    private Long applicationNo;
    private Double amount;
    private Date applicationDate;

    // field, to identify whether WS Request fail or success
    private String status;

    // field ,in case error return by stored procedure call
    private String errorMsg;

    // fields, in case problem occurred during request processing
    private String errorCode;
    private String cause;  // possible cause of the error

    // being used for invalid input found during request processing
    private List<WebServiceResponseDTO> wsInputErrorList;

    // being used for no of document checklist need to upload
    private List<DocumentDetailsVO> checkList;

    // list of result for checkListgroup/applicable charges
    private List<String> ruleResults;

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(final String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Integer getRegistrationYear() {
        return registrationYear;
    }

    public void setRegistrationYear(final Integer registrationYear) {
        this.registrationYear = registrationYear;
    }

    public Integer getNoOfCopies() {
        return noOfCopies;
    }

    public void setNoOfCopies(final Integer noOfCopies) {
        this.noOfCopies = noOfCopies;
    }

    public Long getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(final Long applicationNo) {
        this.applicationNo = applicationNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(final String cause) {
        this.cause = cause;
    }

    public List<WebServiceResponseDTO> getWsInputErrorList() {
        return wsInputErrorList;
    }

    public void setWsInputErrorList(final List<WebServiceResponseDTO> wsInputErrorList) {
        this.wsInputErrorList = wsInputErrorList;
    }

    public List<DocumentDetailsVO> getCheckList() {
        return checkList;
    }

    public void setCheckList(final List<DocumentDetailsVO> checkList) {
        this.checkList = checkList;
    }

    /**
     * @return the applicationDate
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(final Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    /**
     * @return the ruleResults
     */
    public List<String> getRuleResults() {
        return ruleResults;
    }

    /**
     * @param ruleResults the ruleResults to set
     */
    public void setRuleResults(final List<String> ruleResults) {
        this.ruleResults = ruleResults;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ResponseDTO [pinCode=");
        builder.append(pinCode);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", registrationNo=");
        builder.append(registrationNo);
        builder.append(", registrationYear=");
        builder.append(registrationYear);
        builder.append(", noOfCopies=");
        builder.append(noOfCopies);
        builder.append(", applicationNo=");
        builder.append(applicationNo);
        builder.append(", amount=");
        builder.append(amount);
        builder.append(", applicationDate=");
        builder.append(applicationDate);
        builder.append(", status=");
        builder.append(status);
        builder.append(", errorMsg=");
        builder.append(errorMsg);
        builder.append(", errorCode=");
        builder.append(errorCode);
        builder.append(", cause=");
        builder.append(cause);
        builder.append(", wsInputErrorList=");
        builder.append(wsInputErrorList);
        builder.append(", checkList=");
        builder.append(checkList);
        builder.append(", ruleResults=");
        builder.append(ruleResults);
        builder.append("]");
        return builder.toString();
    }

}
