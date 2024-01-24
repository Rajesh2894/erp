package com.abm.mainet.account.dto;

import java.io.Serializable;

/**
 *
 * @author Vivek.Kumar
 * @since 18 May 2017
 */
public class VoucherReversalDTO implements Serializable {

    private static final long serialVersionUID = -756367945190519393L;
    private Long transactionTypeId;
    private String transactionNo;
    private String transactionDate;
    private String transactionAmount;
    private String narration;
    private String approvalOrderNo;
    private Long approvedBy;
    private String transactionType;
    private long primaryKey;
    private long id;
    private String checkedIds;
    private String reversedOrNot;
    private long updatedBy;
    private String viewURL;
    private int langId;
    private String payModeType;
    private Long dpDeptid;
    private Long orgId;
    private Long filedId;
    private String EntryType;
    private String ipMac;

    public Long getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(final Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(final String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(final String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getApprovalOrderNo() {
        return approvalOrderNo;
    }

    public void setApprovalOrderNo(final String approvalOrderNo) {
        this.approvalOrderNo = approvalOrderNo;
    }

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(final Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(final String transactionType) {
        this.transactionType = transactionType;
    }

    public long getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(final long primaryKey) {
        this.primaryKey = primaryKey;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getCheckedIds() {
        return checkedIds;
    }

    public void setCheckedIds(final String checkedIds) {
        this.checkedIds = checkedIds;
    }

    public String getReversedOrNot() {
        return reversedOrNot;
    }

    public void setReversedOrNot(final String reversedOrNot) {
        this.reversedOrNot = reversedOrNot;
    }

    public long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getViewURL() {
        return viewURL;
    }

    public void setViewURL(final String viewURL) {
        this.viewURL = viewURL;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public String getPayModeType() {
        return payModeType;
    }

    public void setPayModeType(final String payModeType) {
        this.payModeType = payModeType;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getIpMac() {
		return ipMac;
	}

	public void setIpMac(String ipMac) {
		this.ipMac = ipMac;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getFiledId() {
		return filedId;
	}

	public void setFiledId(Long filedId) {
		this.filedId = filedId;
	}

	public String getEntryType() {
		return EntryType;
	}

	public void setEntryType(String entryType) {
		EntryType = entryType;
	}

	@Override
    public String toString() {
        return "VoucherReversalDTO [transactionTypeId=" + transactionTypeId + ", transactionNo=" + transactionNo
                + ", transactionDate=" + transactionDate + ", transactionAmount=" + transactionAmount + ", narration="
                + narration + ", approvalOrderNo=" + approvalOrderNo + ", approvedBy=" + approvedBy
                + ", transactionType=" + transactionType + ", primaryKey=" + primaryKey + ", id=" + id + ", checkedIds="
                + checkedIds + ", reversedOrNot=" + reversedOrNot + ", updatedBy=" + updatedBy + ", viewURL=" + viewURL
                + ", langId=" + langId + ", payModeType=" + payModeType + ", dpDeptid=" + dpDeptid + "]";
    }

}
