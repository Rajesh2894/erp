
package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ritesh.patil
 *
 */
public class EstateBookingDTO implements Serializable {

    private static final long serialVersionUID = 3757153222814077702L;
    private Long id;
    private Long applicationId;
    private String bookingNo;
    private Date bookingDate;
    private Date toDate;
    private Date fromDate;
    private Double amount;
    private Double securityAmount;
    private Long propId;
    private Long shiftId;
    private Long purpose;
    private Boolean termAccepted;
    private Boolean payFlag;
    private String bookingStatus;
    private String year;
    private String month;
    private Double discountAmount;
    private Long orgId;
    private long langId;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUp;
    private String shiftName;
    private Long noOfInvitess;
    private String reasonOfFreezing;
    private String cancelReason;
    private Date cancelDate;
    private Long vendorId;
    private String individualBookingNo;
    private Boolean statusFlag;
    private Long dateDiff;
    private String sortCode;
    private Long comN1;
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the bookingNo
     */
    public String getBookingNo() {
        return bookingNo;
    }

    /**
     * @param bookingNo the bookingNo to set
     */
    public void setBookingNo(final String bookingNo) {
        this.bookingNo = bookingNo;
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(final Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    /**
     * @return the securityAmount
     */
    public Double getSecurityAmount() {
        return securityAmount;
    }

    /**
     * @param securityAmount the securityAmount to set
     */
    public void setSecurityAmount(final Double securityAmount) {
        this.securityAmount = securityAmount;
    }

    /**
     * @return the propId
     */
    public Long getPropId() {
        return propId;
    }

    /**
     * @param propId the propId to set
     */
    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    /**
     * @return the shiftId
     */
    public Long getShiftId() {
        return shiftId;
    }

    /**
     * @param shiftId the shiftId to set
     */
    public void setShiftId(final Long shiftId) {
        this.shiftId = shiftId;
    }

    /**
	     * @return the purpose
     */
    public Long getPurpose() {
        return purpose;
    }

    /**
     * @param purpose the purpose to set
     */
    public void setPurpose(final Long purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the termAccepted
     */
    public Boolean getTermAccepted() {
        return termAccepted;
    }

    /**
     * @param termAccepted the termAccepted to set
     */
    public void setTermAccepted(final Boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    /**
     * @return the payFlag
     */
    public Boolean getPayFlag() {
        return payFlag;
    }

    /**
     * @param payFlag the payFlag to set
     */
    public void setPayFlag(final Boolean payFlag) {
        this.payFlag = payFlag;
    }

    /**
     * @return the bookingStatus
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * @param bookingStatus the bookingStatus to set
     */
    public void setBookingStatus(final String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(final String month) {
        this.month = month;
    }

    /**
     * @return the discountAmount
     */
    public Double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount the discountAmount to set
     */
    public void setDiscountAmount(final Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the langId
     */
    public long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final long langId) {
        this.langId = langId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
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
     * @return the lgIpMacUp
     */
    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    /**
     * @param lgIpMacUp the lgIpMacUp to set
     */
    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    /**
     * @return the shiftName
     */
    public String getShiftName() {
        return shiftName;
    }

    /**
     * @param shiftName the shiftName to set
     */
    public void setShiftName(final String shiftName) {
        this.shiftName = shiftName;
    }

    public Long getNoOfInvitess() {
        return noOfInvitess;
    }

    public void setNoOfInvitess(Long noOfInvitess) {
        this.noOfInvitess = noOfInvitess;
    }

    public String getReasonOfFreezing() {
        return reasonOfFreezing;
    }

    public void setReasonOfFreezing(String reasonOfFreezing) {
        this.reasonOfFreezing = reasonOfFreezing;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

	public String getIndividualBookingNo() {
		return individualBookingNo;
	}

	public void setIndividualBookingNo(String individualBookingNo) {
		this.individualBookingNo = individualBookingNo;
	}

	public Boolean getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(Boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

    public Long getDateDiff() {
        return dateDiff;
    }

    public void setDateDiff(Long dateDiff) {
        this.dateDiff = dateDiff;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public Long getComN1() {
        return comN1;
    }

    public void setComN1(Long comN1) {
        this.comN1 = comN1;
    }

   

	
    
}