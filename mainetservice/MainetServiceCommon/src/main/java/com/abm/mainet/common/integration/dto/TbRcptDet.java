/**
 * Created on 15 Nov 2021
 */
package com.abm.mainet.common.integration.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author rishikesh.kamerkar
 *
 */
public class TbRcptDet implements Serializable{

    private static final long serialVersionUID = 4420133689880510912L;
    
    
    @NotNull
    private long rcptId;

    private long rcptNo;

    @NotNull
    private Date rcptDate;
    
    private double rcptAmount;
    
    private String rcptFrom;
    
    private String rcptYear;
    
    private String taxDescription;
    private Double dueAmount;
    private Double receivedAmount;
    private String errorMessage;
    
	public long getRcptId() {
		return rcptId;
	}

	public void setRcptId(long rcptId) {
		this.rcptId = rcptId;
	}

	public long getRcptNo() {
		return rcptNo;
	}

	public void setRcptNo(long rcptNo) {
		this.rcptNo = rcptNo;
	}
	
	public double getRcptAmount() {
		return rcptAmount;
	}

	public void setRcptAmount(double rcptAmount) {
		this.rcptAmount = rcptAmount;
	}

	public String getRcptYear() {
		return rcptYear;
	}

	public void setRcptYear(String rcptYear) {
		this.rcptYear = rcptYear;
	}

	public Date getRcptDate() {
		return rcptDate;
	}

	public void setRcptDate(Date rcptDate) {
		this.rcptDate = rcptDate;
	}

	public String getRcptFrom() {
		return rcptFrom;
	}

	public void setRcptFrom(String rcptFrom) {
		this.rcptFrom = rcptFrom;
	}

	public String getTaxDescription() {
		return taxDescription;
	}

	public void setTaxDescription(String taxDescription) {
		this.taxDescription = taxDescription;
	}

	public Double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(Double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Double getReceivedAmount() {
		return receivedAmount;
	}

	public void setReceivedAmount(Double receivedAmount) {
		this.receivedAmount = receivedAmount;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
