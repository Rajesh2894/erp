package com.abm.mainet.water.dto;

import java.util.Date;

/**
 * @author Lalit.Prusti
 * @since 28 Jun 2016
 */
public class DemandNoticeResponseDTO {

    private boolean selected;
    private Long connectionId;
    private Long noticeId;
    private Long billId;
    private String connectionNo;
    private String custName;
    private String custAddress;
    private Double billAmount;
    private Date billDueDate;
    private long userId;
    private long orgId;
    private long langId;
    private Long noticeNo;
    private Long noticeType;
    private Date noticeDate;
    private Date outstandangFrom;
    private Date outstandangTo;
    private String noticeTypeDesc;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(final boolean selected) {
        this.selected = selected;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(final Long connectionId) {
        this.connectionId = connectionId;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(final Long billId) {
        this.billId = billId;
    }

    public String getConnectionNo() {
        return connectionNo;
    }

    public void setConnectionNo(final String connectionNo) {
        this.connectionNo = connectionNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(final String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(final String custAddress) {
        this.custAddress = custAddress;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(final Double billAmount) {
        this.billAmount = billAmount;
    }

    public Date getBillDueDate() {
        return billDueDate;
    }

    public void setBillDueDate(final Date billDueDate) {
        this.billDueDate = billDueDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(final long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(final long langId) {
        this.langId = langId;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(final Long noticeId) {
        this.noticeId = noticeId;
    }

    public Long getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(final Long noticeNo) {
        this.noticeNo = noticeNo;
    }

    public Date getOutstandangFrom() {
        return outstandangFrom;
    }

    public void setOutstandangFrom(final Date outstandangFrom) {
        this.outstandangFrom = outstandangFrom;
    }

    public Date getOutstandangTo() {
        return outstandangTo;
    }

    public void setOutstandangTo(final Date outstandangTo) {
        this.outstandangTo = outstandangTo;
    }

    public Long getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(final Long noticeType) {
        this.noticeType = noticeType;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(final Date noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getNoticeTypeDesc() {
        return noticeTypeDesc;
    }

    public void setNoticeTypeDesc(final String noticeTypeDesc) {
        this.noticeTypeDesc = noticeTypeDesc;
    }

}