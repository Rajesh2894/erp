/**
 * 
 */
package com.abm.mainet.water.dto;

import java.util.Date;

/**
 * @author cherupelli.srikanth
 *
 */
public class WaterPenaltyDto {

    private Long penaltyId;
    
     private String connNo;
     
     private double actualAmount;
     
     private double pendingAmount;
     
     private Long finYearId;
     
     private char activeFlag;
     
     private Long orgId;
     
     private Long cretedBy;
     
     private Date createddate;
     
     private Long updatedBy;
     
     private Date updatedDate;
     
     private String lgIpmac;
     
     private String lgIpMacUpd;
     
     private Long taxId;
     
     private Date surchargeFromDate;
     
     private Date surchargeToDate;

     private double actualArrrearAmount;
     
     private Long bmIdNo;
     
     private Double billGenAmount;
     
     private Long currBmIdNo;
     
     private double arrearPenalty;
     
     private double currentPenalty;
     
    public Long getPenaltyId() {
        return penaltyId;
    }

    public void setPenaltyId(Long penaltyId) {
        this.penaltyId = penaltyId;
    }

    public String getConnNo() {
        return connNo;
    }

    public void setConnNo(String connNo) {
        this.connNo = connNo;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public double getPendingAmount() {
        return pendingAmount;
    }

    public void setPendingAmount(double pendingAmount) {
        this.pendingAmount = pendingAmount;
    }

    public Long getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(Long finYearId) {
        this.finYearId = finYearId;
    }

    public char getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(char activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCretedBy() {
        return cretedBy;
    }

    public void setCretedBy(Long cretedBy) {
        this.cretedBy = cretedBy;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
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

    public String getLgIpmac() {
        return lgIpmac;
    }

    public void setLgIpmac(String lgIpmac) {
        this.lgIpmac = lgIpmac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Date getSurchargeFromDate() {
        return surchargeFromDate;
    }

    public void setSurchargeFromDate(Date surchargeFromDate) {
        this.surchargeFromDate = surchargeFromDate;
    }

    public Date getSurchargeToDate() {
        return surchargeToDate;
    }

    public void setSurchargeToDate(Date surchargeToDate) {
        this.surchargeToDate = surchargeToDate;
    }

    public double getActualArrrearAmount() {
        return actualArrrearAmount;
    }

    public void setActualArrrearAmount(double actualArrrearAmount) {
        this.actualArrrearAmount = actualArrrearAmount;
    }

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}

	public Double getBillGenAmount() {
		return billGenAmount;
	}

	public void setBillGenAmount(Double billGenAmount) {
		this.billGenAmount = billGenAmount;
	}

	public Long getCurrBmIdNo() {
		return currBmIdNo;
	}

	public void setCurrBmIdNo(Long currBmIdNo) {
		this.currBmIdNo = currBmIdNo;
	}

	public double getArrearPenalty() {
		return arrearPenalty;
	}

	public void setArrearPenalty(double arrearPenalty) {
		this.arrearPenalty = arrearPenalty;
	}

	public double getCurrentPenalty() {
		return currentPenalty;
	}

	public void setCurrentPenalty(double currentPenalty) {
		this.currentPenalty = currentPenalty;
	}
	
	
}
