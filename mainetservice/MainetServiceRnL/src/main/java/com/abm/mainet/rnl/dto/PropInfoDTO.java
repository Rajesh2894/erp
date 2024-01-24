package com.abm.mainet.rnl.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PropInfoDTO implements Serializable {

    private static final long serialVersionUID = 1422347284006383111L;

    private String propName;
    private String applicantName;
    private String areaName;
    private String city;
    private String pinCode;
    private String bookingPuprpose;
    private Date toDate;
    private Date fromDate;
    private String dayPeriod;
    private String categoryName;
    private String contactNo;
    private String category;
    private String bookingId;
    private String orgName;
    private String BookingNo;
    private BigDecimal amount;
    private String applicationId;
    private String receiptNo;
    private String paymentModedesc;
    private Date BookingDate;
    private String BookingStatus;
    private String propId;

    public String getPropName() {
        return propName;
    }

    public void setPropName(final String propName) {
        this.propName = propName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public String getBookingPuprpose() {
        return bookingPuprpose;
    }

    public void setBookingPuprpose(final String bookingPuprpose) {
        this.bookingPuprpose = bookingPuprpose;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getDayPeriod() {
        return dayPeriod;
    }

    public void setDayPeriod(final String dayPeriod) {
        this.dayPeriod = dayPeriod;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(final String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(final String areaName) {
        this.areaName = areaName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(final String pinCode) {
        this.pinCode = pinCode;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(final String bookingId) {
        this.bookingId = bookingId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(final String orgName) {
        this.orgName = orgName;
    }

	public String getBookingNo() {
		return BookingNo;
	}

	public void setBookingNo(String bookingNo) {
		BookingNo = bookingNo;
	}

	

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPaymentModedesc() {
		return paymentModedesc;
	}

	public void setPaymentModedesc(String paymentModedesc) {
		this.paymentModedesc = paymentModedesc;
	}

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingStatus() {
        return BookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        BookingStatus = bookingStatus;
    }

	public String getPropId() {
		return propId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}
    
    
	
	
    
}
