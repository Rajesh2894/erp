package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
public class VehicleFuelReconciationDetDTO implements Serializable {

    private static final long serialVersionUID = 8448299276838853684L;

    private Long inrecdId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private VehicleFuelReconciationDTO tbSwVehiclefuelInrec;

    private Long vefId;

    private Date fromDate;

    private Date toDate;

    private String inrecdActive;

    private Long veVetype;

    private Long veId;

    private String veNo;

    private Long adviceNumber;

    private Date adviceDate;

    private BigDecimal vefRmamount;

    private Date supplyDate;

    private Long pfuId;
    private String itemDesc;
    private String driverName;
    private BigDecimal vefdCost;
    private BigDecimal vefdQuantity;

    private Long puFuid;

    private BigDecimal sumOfAmount;;

    private BigDecimal totalAmount;

    public BigDecimal getVefdCost() {
        return vefdCost;
    }

    public void setVefdCost(BigDecimal vefdCost) {
        this.vefdCost = vefdCost;
    }

    public BigDecimal getVefdQuantity() {
        return vefdQuantity;
    }

    public void setVefdQuantity(BigDecimal vefdQuantity) {
        this.vefdQuantity = vefdQuantity;
    }

    public VehicleFuelReconciationDetDTO() {
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Long getPfuId() {
        return pfuId;
    }

    public void setPfuId(Long pfuId) {
        this.pfuId = pfuId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getSupplyDate() {
        return supplyDate;
    }

    public void setSupplyDate(Date supplyDate) {
        this.supplyDate = supplyDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Long getInrecdId() {
        return this.inrecdId;
    }

    public void setInrecdId(Long inrecdId) {
        this.inrecdId = inrecdId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public VehicleFuelReconciationDTO getTbSwVehiclefuelInrec() {
        return this.tbSwVehiclefuelInrec;
    }

    public void setTbSwVehiclefuelInrec(VehicleFuelReconciationDTO tbSwVehiclefuelInrec) {
        this.tbSwVehiclefuelInrec = tbSwVehiclefuelInrec;
    }

    public Long getVefId() {
        return vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
    }

    public String getInrecdActive() {
        return inrecdActive;
    }

    public void setInrecdActive(String inrecdActive) {
        this.inrecdActive = inrecdActive;
    }

    public Long getVeVetype() {
        return veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public Long getAdviceNumber() {
        return adviceNumber;
    }

    public void setAdviceNumber(Long adviceNumber) {
        this.adviceNumber = adviceNumber;
    }

    public Date getAdviceDate() {
        return adviceDate;
    }

    public void setAdviceDate(Date adviceDate) {
        this.adviceDate = adviceDate;
    }

    public BigDecimal getVefRmamount() {
        return vefRmamount;
    }

    public void setVefRmamount(BigDecimal vefRmamount) {
        this.vefRmamount = vefRmamount;
    }

    public BigDecimal getSumOfAmount() {
        return sumOfAmount;
    }

    public void setSumOfAmount(BigDecimal sumOfAmount) {
        this.sumOfAmount = sumOfAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getPuFuid() {
        return puFuid;
    }

    public void setPuFuid(Long puFuid) {
        this.puFuid = puFuid;
    }

}