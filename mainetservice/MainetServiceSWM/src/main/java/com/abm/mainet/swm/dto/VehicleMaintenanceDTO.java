package com.abm.mainet.swm.dto;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 22-May-2018
 */

public class VehicleMaintenanceDTO implements Serializable {

    private static final long serialVersionUID = -4355472406567771987L;

    private Long vemId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Long veId;

    private Long veVetype;

    private BigDecimal vemCostincurred;

    private Date vemDate;

    private Date fromDate;

    private Date toDate;

    private Date vemNextDueDate;

    private Long vemDowntime;

    private Long vemDowntimeunit;

    private Long vemMetype;

    private Long vemReading;

    private String vemReason;

    private Date vemReceiptdate;

    private Long vemReceiptno;

    private String remark;

    private Date vemEstDowntime;

    private Long vemExpHead;

    private String veNo;

    private File vemFile;

    private String veName;

    private BigDecimal totalCost;

    private String mantenanceDate;

    private Long expenditureId;

    private String ExpenditureHead;

    private Long vendorId;

    private String vendorName;

    private String dedAcHead;

    private Long dedAcHeadId;

    private BigDecimal dedAmt;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    private List<VehicleMaintenanceDTO> vehicleMaintenanceList;

    public File getVemFile() {
        return vemFile;
    }

    public void setVemFile(File vemFile) {
        this.vemFile = vemFile;
    }

    public String getVeNo() {
        return veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public Date getVemEstDowntime() {
        return vemEstDowntime;
    }

    public void setVemEstDowntime(Date vemEstDowntime) {
        this.vemEstDowntime = vemEstDowntime;
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

    public Long getVemId() {
        return vemId;
    }

    public void setVemId(Long vemId) {
        this.vemId = vemId;
    }

    public Date getVemNextDueDate() {
        return vemNextDueDate;
    }

    public void setVemNextDueDate(Date vemNextDueDate) {
        this.vemNextDueDate = vemNextDueDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public Long getVeVetype() {
        return this.veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public BigDecimal getVemCostincurred() {
        return this.vemCostincurred;
    }

    public void setVemCostincurred(BigDecimal vemCostincurred) {
        this.vemCostincurred = vemCostincurred;
    }

    public Date getVemDate() {
        return this.vemDate;
    }

    public void setVemDate(Date vemDate) {
        this.vemDate = vemDate;
    }

    public Long getVemDowntime() {
        return this.vemDowntime;
    }

    public void setVemDowntime(Long vemDowntime) {
        this.vemDowntime = vemDowntime;
    }

    public Long getVemDowntimeunit() {
        return this.vemDowntimeunit;
    }

    public void setVemDowntimeunit(Long vemDowntimeunit) {
        this.vemDowntimeunit = vemDowntimeunit;
    }

    public Long getVemMetype() {
        return this.vemMetype;
    }

    public void setVemMetype(Long vemMetype) {
        this.vemMetype = vemMetype;
    }

    public Long getVemReading() {
        return this.vemReading;
    }

    public void setVemReading(Long vemReading) {
        this.vemReading = vemReading;
    }

    public String getVemReason() {
        return this.vemReason;
    }

    public void setVemReason(String vemReason) {
        this.vemReason = vemReason;
    }

    public Date getVemReceiptdate() {
        return this.vemReceiptdate;
    }

    public void setVemReceiptdate(Date vemReceiptdate) {
        this.vemReceiptdate = vemReceiptdate;
    }

    public Long getVemReceiptno() {
        return this.vemReceiptno;
    }

    public void setVemReceiptno(Long vemReceiptno) {
        this.vemReceiptno = vemReceiptno;
    }

    public Long getVemExpHead() {
        return vemExpHead;
    }

    public void setVemExpHead(Long vemExpHead) {
        this.vemExpHead = vemExpHead;
    }

    public String getVeName() {
        return veName;
    }

    public void setVeName(String veName) {
        this.veName = veName;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public List<VehicleMaintenanceDTO> getVehicleMaintenanceList() {
        return vehicleMaintenanceList;
    }

    public void setVehicleMaintenanceList(List<VehicleMaintenanceDTO> vehicleMaintenanceList) {
        this.vehicleMaintenanceList = vehicleMaintenanceList;
    }

    public String getMantenanceDate() {
        return mantenanceDate;
    }

    public void setMantenanceDate(String mantenanceDate) {
        this.mantenanceDate = mantenanceDate;
    }

    public Long getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(Long expenditureId) {
        this.expenditureId = expenditureId;
    }

    public String getExpenditureHead() {
        return ExpenditureHead;
    }

    public void setExpenditureHead(String expenditureHead) {
        ExpenditureHead = expenditureHead;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getDedAcHead() {
        return dedAcHead;
    }

    public void setDedAcHead(String dedAcHead) {
        this.dedAcHead = dedAcHead;
    }

    public Long getDedAcHeadId() {
        return dedAcHeadId;
    }

    public void setDedAcHeadId(Long dedAcHeadId) {
        this.dedAcHeadId = dedAcHeadId;
    }

    public BigDecimal getDedAmt() {
        return dedAmt;
    }

    public void setDedAmt(BigDecimal dedAmt) {
        this.dedAmt = dedAmt;
    }

}