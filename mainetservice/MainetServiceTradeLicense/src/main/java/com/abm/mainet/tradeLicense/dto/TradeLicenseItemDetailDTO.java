package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import org.codehaus.jackson.annotate.JsonIgnore;

public class TradeLicenseItemDetailDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long triId;
    @JsonIgnore
    private TradeMasterDetailDTO masterTradeId;
    private Long triCod1;
    private Long triCod2;
    private Long triCod3;
    private Long triCod4;
    private Long triCod5;
    private BigDecimal triRate;
    private BigDecimal triPenalty;
    private Long orgid;
    private Long createdBy;
    private Date createdDate;
    private Long updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String itemCategory1;
    private String itemCategory2;
    private String itemCategory3;
    private String itemCategory4;
    private String itemCategory5;
    private boolean selectedItems;
    private String triStatus;
    private Long triCategory1;
    private Long triCategory2;
    private Long triCategory3;
    private Long triCategory4;
    private Long triCategory5;
    private BigDecimal trdQuantity;
    private BigDecimal trdUnit;   
    private Long apmApplicationId;
    private BigDecimal depositAmt; 
	private BigDecimal licenseFee;


    public Long getTriId() {
        return triId;
    }

    public void setTriId(Long triId) {
        this.triId = triId;
    }

    public TradeMasterDetailDTO getMasterTradeId() {
        return masterTradeId;
    }

    public void setMasterTradeId(TradeMasterDetailDTO masterTradeId) {
        this.masterTradeId = masterTradeId;
    }

    public Long getTriCod1() {
        return triCod1;
    }

    public void setTriCod1(Long triCod1) {
        this.triCod1 = triCod1;
    }

    public Long getTriCod2() {
        return triCod2;
    }

    public void setTriCod2(Long triCod2) {
        this.triCod2 = triCod2;
    }

    public Long getTriCod3() {
        return triCod3;
    }

    public void setTriCod3(Long triCod3) {
        this.triCod3 = triCod3;
    }

    public Long getTriCod4() {
        return triCod4;
    }

    public void setTriCod4(Long triCod4) {
        this.triCod4 = triCod4;
    }

    public Long getTriCod5() {
        return triCod5;
    }

    public void setTriCod5(Long triCod5) {
        this.triCod5 = triCod5;
    }

    public BigDecimal getTriRate() {
        return triRate;
    }

    public void setTriRate(BigDecimal triRate) {
        this.triRate = triRate;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getItemCategory1() {
        return itemCategory1;
    }

    public void setItemCategory1(String itemCategory1) {
        this.itemCategory1 = itemCategory1;
    }

    public String getItemCategory2() {
        return itemCategory2;
    }

    public void setItemCategory2(String itemCategory2) {
        this.itemCategory2 = itemCategory2;
    }

    public String getItemCategory3() {
        return itemCategory3;
    }

    public void setItemCategory3(String itemCategory3) {
        this.itemCategory3 = itemCategory3;
    }

    public String getItemCategory4() {
        return itemCategory4;
    }

    public void setItemCategory4(String itemCategory4) {
        this.itemCategory4 = itemCategory4;
    }

    public String getItemCategory5() {
        return itemCategory5;
    }

    public void setItemCategory5(String itemCategory5) {
        this.itemCategory5 = itemCategory5;
    }

    public boolean isSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(boolean selectedItems) {
        this.selectedItems = selectedItems;
    }

    public String getTriStatus() {
        return triStatus;
    }

    public void setTriStatus(String triStatus) {
        this.triStatus = triStatus;
    }

    public Long getTriCategory1() {
        return triCategory1;
    }

    public Long getTriCategory2() {
        return triCategory2;
    }

    public Long getTriCategory3() {
        return triCategory3;
    }

    public Long getTriCategory4() {
        return triCategory4;
    }

    public Long getTriCategory5() {
        return triCategory5;
    }

    public void setTriCategory1(Long triCategory1) {
        this.triCategory1 = triCategory1;
    }

    public void setTriCategory2(Long triCategory2) {
        this.triCategory2 = triCategory2;
    }

    public void setTriCategory3(Long triCategory3) {
        this.triCategory3 = triCategory3;
    }

    public void setTriCategory4(Long triCategory4) {
        this.triCategory4 = triCategory4;
    }

    public void setTriCategory5(Long triCategory5) {
        this.triCategory5 = triCategory5;
    }

    public BigDecimal getTrdQuantity() {
        return trdQuantity;
    }

    public BigDecimal getTrdUnit() {
        return trdUnit;
    }

    public void setTrdQuantity(BigDecimal trdQuantity) {
        this.trdQuantity = trdQuantity;
    }

    public void setTrdUnit(BigDecimal trdUnit) {
        this.trdUnit = trdUnit;
    }

	public BigDecimal getTriPenalty() {
		return triPenalty;
	}

	public void setTriPenalty(BigDecimal triPenalty) {
		this.triPenalty = triPenalty;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public BigDecimal getDepositAmt() {
		return depositAmt;
	}

	public void setDepositAmt(BigDecimal depositAmt) {
		this.depositAmt = depositAmt;
	}

	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}



	

	
	
}
