package com.abm.mainet.rts.dto;

import java.io.Serializable;
import com.abm.mainet.common.dto.ChargeDetailDTO;
 

public class MediaChargeAmountDTO extends ChargeDetailDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6050377113623965675L;
    private Double freeCopy;
    private Double quantity;
    private String mediaType;
    private Long taxId;
    private double total;
    private double newTotal;
    private String remarks;
    private String editableLoiFlag;
    
    private long chargeCode;
    private double chargeAmount;
    private String chargeDescEng;
    private String chargeDescReg;
    private String TaxCode;
    private double percentageRate;
    private double editedAmount;

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

	public String getEditableLoiFlag() {
		return editableLoiFlag;
	}

	public void setEditableLoiFlag(String editableLoiFlag) {
		this.editableLoiFlag = editableLoiFlag;
	}

	public long getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(long chargeCode) {
		this.chargeCode = chargeCode;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getChargeDescEng() {
		return chargeDescEng;
	}

	public void setChargeDescEng(String chargeDescEng) {
		this.chargeDescEng = chargeDescEng;
	}

	public String getChargeDescReg() {
		return chargeDescReg;
	}

	public void setChargeDescReg(String chargeDescReg) {
		this.chargeDescReg = chargeDescReg;
	}

	public String getTaxCode() {
		return TaxCode;
	}

	public void setTaxCode(String taxCode) {
		TaxCode = taxCode;
	}

	public double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(double percentageRate) {
		this.percentageRate = percentageRate;
	}

	public double getEditedAmount() {
		return editedAmount;
	}

	public void setEditedAmount(double editedAmount) {
		this.editedAmount = editedAmount;
	}
	
	
	
	
}
