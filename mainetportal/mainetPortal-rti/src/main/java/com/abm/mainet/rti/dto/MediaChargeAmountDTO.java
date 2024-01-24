package com.abm.mainet.rti.dto;

import java.io.Serializable;

import com.abm.mainet.common.dto.ChargeDetailDTO;

public class MediaChargeAmountDTO extends ChargeDetailDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -8940050414996152215L;
    private Double freeCopy;
    private Double quantity;
    private String mediaType;
    private Long taxId;
    private double total;
    private double newTotal;
    private String remarks;

    public Double getFreeCopy() {
        return freeCopy;
    }

    public void setFreeCopy(Double freeCopy) {
        this.freeCopy = freeCopy;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getNewTotal() {
		return newTotal;
	}

	public void setNewTotal(double newTotal) {
		this.newTotal = newTotal;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
    
}
