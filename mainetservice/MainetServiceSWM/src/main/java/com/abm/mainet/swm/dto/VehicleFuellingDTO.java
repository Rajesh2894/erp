package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
public class VehicleFuellingDTO implements Serializable {

    private static final long serialVersionUID = 3185775321021159645L;

    private Long vefId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long puId;

    private Long updatedBy;

    private Date updatedDate;

    private Long veId;

    private Long veVetype;

    private Date vefDmdate;

    private Date vefDate;

    private Date fromDate;

    private Date toDate;

    private Long vefDmno;

    private Long vefReading;

    private Date vefReceiptdate;

    private Long vefReceiptno;

    private BigDecimal vefRmamount;

    private String veNo;

    private String puPumpname;

    private String driverName;

    private String vName;

    private String itemName;

    private BigDecimal totalCost;

    private Long seqNo;

    private BigDecimal quantity;

    private BigDecimal cost;

    private BigDecimal sumofItemCost;

    private String expensesDate;

    private String adviceDate;

    private Long quantityUnit;

    private List<VehicleFuellingDTO> vehicleFuellingList;

    private List<VehicleFuellingDetailsDTO> tbSwVehiclefuelDets;

    private String adviceNumber;

    public VehicleFuellingDTO() {
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

    public String getPuPumpname() {
        return puPumpname;
    }

    public void setPuPumpname(String puPumpname) {
        this.puPumpname = puPumpname;
    }

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public VehicleFuellingDetailsDTO addTbSwVehiclefuelDet(final VehicleFuellingDetailsDTO tbSwVehiclefuelDet) {
        getTbSwVehiclefuelDets().add(tbSwVehiclefuelDet);
        tbSwVehiclefuelDet.setTbSwVehiclefuelMast(this);

        return tbSwVehiclefuelDet;
    }

    public VehicleFuellingDetailsDTO removeTbSwVehiclefuelDet(final VehicleFuellingDetailsDTO tbSwVehiclefuelDet) {
        getTbSwVehiclefuelDets().remove(tbSwVehiclefuelDet);
        tbSwVehiclefuelDet.setTbSwVehiclefuelMast(null);

        return tbSwVehiclefuelDet;
    }

    public Long getVefId() {
        return vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
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

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
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

    public Long getVeId() {
        return veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getVeVetype() {
        return veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Date getVefDmdate() {
        return vefDmdate;
    }

    public void setVefDmdate(Date vefDmdate) {
        this.vefDmdate = vefDmdate;
    }

    public Long getVefDmno() {
        return vefDmno;
    }

    public void setVefDmno(Long vefDmno) {
        this.vefDmno = vefDmno;
    }

    public Long getVefReading() {
        return vefReading;
    }

    public void setVefReading(Long vefReading) {
        this.vefReading = vefReading;
    }

    public Date getVefReceiptdate() {
        return vefReceiptdate;
    }

    public void setVefReceiptdate(Date vefReceiptdate) {
        this.vefReceiptdate = vefReceiptdate;
    }

    public Long getVefReceiptno() {
        return vefReceiptno;
    }

    public void setVefReceiptno(Long vefReceiptno) {
        this.vefReceiptno = vefReceiptno;
    }

    public BigDecimal getVefRmamount() {
        return vefRmamount;
    }

    public void setVefRmamount(BigDecimal vefRmamount) {
        this.vefRmamount = vefRmamount;
    }

    public Date getVefDate() {
        return vefDate;
    }

    public void setVefDate(Date vefDate) {
        this.vefDate = vefDate;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public List<VehicleFuellingDetailsDTO> getTbSwVehiclefuelDets() {
        return tbSwVehiclefuelDets;
    }

    public void setTbSwVehiclefuelDets(List<VehicleFuellingDetailsDTO> tbSwVehiclefuelDets) {
        this.tbSwVehiclefuelDets = tbSwVehiclefuelDets;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public Long getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(Long seqNo) {
        this.seqNo = seqNo;
    }

    public String getvName() {
        return vName;
    }

    public void setvName(String vName) {
        this.vName = vName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getSumofItemCost() {
        return sumofItemCost;
    }

    public void setSumofItemCost(BigDecimal sumofItemCost) {
        this.sumofItemCost = sumofItemCost;
    }

    public List<VehicleFuellingDTO> getVehicleFuellingList() {
        return vehicleFuellingList;
    }

    public void setVehicleFuellingList(List<VehicleFuellingDTO> vehicleFuellingList) {
        this.vehicleFuellingList = vehicleFuellingList;
    }

    public String getExpensesDate() {
        return expensesDate;
    }

    public void setExpensesDate(String expensesDate) {
        this.expensesDate = expensesDate;
    }

    public String getAdviceDate() {
        return adviceDate;
    }

    public void setAdviceDate(String adviceDate) {
        this.adviceDate = adviceDate;
    }

    public Long getQuantityUnit() {
        return quantityUnit;
    }

    public void setQuantityUnit(Long quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

	public String getAdviceNumber() {
		return adviceNumber;
	}

	public void setAdviceNumber(String adviceNumber) {
		this.adviceNumber = adviceNumber;
	}
}