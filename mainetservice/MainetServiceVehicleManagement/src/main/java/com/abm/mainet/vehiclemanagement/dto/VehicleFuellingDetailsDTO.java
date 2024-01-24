package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
public class VehicleFuellingDetailsDTO implements Serializable {

    private static final long serialVersionUID = 9080962215072379751L;

    private Long vefdId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private BigDecimal vefdQuantity;

    private Long vefdUnit;

    private Long pfuId;

    private String vefdUnitDesc;

    private String itemDesc;
    
    private String pumpFuelName;
    
    private String fuelUnit;

    private BigDecimal vefdCost;

    private BigDecimal vefdTotalCost;

    private BigDecimal multi;

    private VehicleFuellingDTO tbSwVehiclefuelMast;

	private String isDeleted;

    public BigDecimal getVefdTotalCost() {
        return vefdTotalCost;
    }

    public void setVefdTotalCost(BigDecimal vefdTotalCost) {
        this.vefdTotalCost = vefdTotalCost;
    }

    public Long getVefdId() {
        return vefdId;
    }

    public void setVefdId(Long vefdId) {
        this.vefdId = vefdId;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public BigDecimal getVefdQuantity() {
        return vefdQuantity;
    }

    public void setVefdQuantity(BigDecimal vefdQuantity) {
        this.vefdQuantity = vefdQuantity;
    }

    public Long getVefdUnit() {
        return vefdUnit;
    }

    public void setVefdUnit(Long vefdUnit) {
        this.vefdUnit = vefdUnit;
    }

    public Long getPfuId() {
        return pfuId;
    }

    public void setPfuId(Long pfuId) {
        this.pfuId = pfuId;
    }

    public BigDecimal getVefdCost() {
        return vefdCost;
    }

    public void setVefdCost(BigDecimal vefdCost) {
        this.vefdCost = vefdCost;
    }

    public VehicleFuellingDTO getTbSwVehiclefuelMast() {
        return tbSwVehiclefuelMast;
    }

    public void setTbSwVehiclefuelMast(VehicleFuellingDTO tbSwVehiclefuelMast) {
        this.tbSwVehiclefuelMast = tbSwVehiclefuelMast;
    }

    public String getVefdUnitDesc() {
        return vefdUnitDesc;
    }

    public void setVefdUnitDesc(String vefdUnitDesc) {
        this.vefdUnitDesc = vefdUnitDesc;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public BigDecimal getMulti() {
        return multi;
    }

    public void setMulti(BigDecimal multi) {
        this.multi = multi;
    }

    public String getPumpFuelName() {
        return pumpFuelName;
    }

    public void setPumpFuelName(String pumpFuelName) {
        this.pumpFuelName = pumpFuelName;
    }

    public String getFuelUnit() {
        return fuelUnit;
    }

    public void setFuelUnit(String fuelUnit) {
        this.fuelUnit = fuelUnit;
    }
    
    public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}


}