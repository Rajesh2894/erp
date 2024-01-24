
package com.abm.mainet.account.dto;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prasant.sahu
 *
 */
public class AccountLedgerMasBean {

    private String field;
    private String fund;
    private String function;
    private String primary;
    private String secondary;
    private Map<Long, String> secondaryMap = new HashMap<>();
    private String feeAmount;

    private String selectDs;

    private String receiptIds;

    private Long rmRcptid;
    private Long rmRcptno;
    private String rmDate;
    private BigDecimal rmAmount;
    private String manualReceiptNo;
    private String rmReceiptNo;
    private Long deptId;

    /**
     * @return the selectDs
     */
    public String getSelectDs() {
        return selectDs;
    }

    /**
     * @param selectDs the selectDs to set
     */
    public void setSelectDs(final String selectDs) {
        this.selectDs = selectDs;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field the field to set
     */
    public void setField(final String field) {
        this.field = field;
    }

    /**
     * @return the fund
     */
    public String getFund() {
        return fund;
    }

    /**
     * @param fund the fund to set
     */
    public void setFund(final String fund) {
        this.fund = fund;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(final String function) {
        this.function = function;
    }

    /**
     * @return the primary
     */
    public String getPrimary() {
        return primary;
    }

    /**
     * @param primary the primary to set
     */
    public void setPrimary(final String primary) {
        this.primary = primary;
    }

    /**
     * @return the secondary
     */
    public String getSecondary() {
        return secondary;
    }

    /**
     * @param secondary the secondary to set
     */
    public void setSecondary(final String secondary) {
        this.secondary = secondary;
    }

    /**
     * @return the feeAmount
     */
    public String getFeeAmount() {
        return feeAmount;
    }

    /**
     * @param feeAmount the feeAmount to set
     */
    public void setFeeAmount(final String feeAmount) {
        this.feeAmount = feeAmount;
    }

    /**
     * @return the secondaryMap
     */
    public Map<Long, String> getSecondaryMap() {
        return secondaryMap;
    }

    /**
     * @param secondaryMap the secondaryMap to set
     */
    public void setSecondaryMap(final Map<Long, String> secondaryMap) {
        this.secondaryMap = secondaryMap;
    }

    /**
     * @return the receiptIds
     */
    public String getReceiptIds() {
        return receiptIds;
    }

    /**
     * @param receiptIds the receiptIds to set
     */
    public void setReceiptIds(final String receiptIds) {
        this.receiptIds = receiptIds;
    }

    public Long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(Long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public String getRmDate() {
        return rmDate;
    }

    public void setRmDate(String rmDate) {
        this.rmDate = rmDate;
    }

    public BigDecimal getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(BigDecimal rmAmount) {
        this.rmAmount = rmAmount;
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

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
