package com.abm.mainet.vehiclemanagement.dto;

import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 16-Jun-2018
 */
public class VehicleFuelReconciationDTO implements Serializable {

    private static final long serialVersionUID = -8349219177314298123L;

    private Long inrecId;

    private Long createdBy;

    private Date createdDate;

    private Date inrecFromdt;

    private Date inrecTodt;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Long puId;

    private Long vendorId;

    private String puPumpname;

    private String puVendorname;

    private Long inrecdExpen;

    private BigDecimal inrecdInvamt;

    private Date inrecdInvdate;

    private Long inrecdInvno;

    private String puActive;

    private BigDecimal sum;

    private Long vefId;

    private File vemFile;

    private String expenditureHead;

    private Long expenditureId;

    private String dedAcHead;

    private Long dedAcHeadId;

    private BigDecimal dedAmt;

    /* private List<VehicleFuellingDTO> vehicleFuellingList; */

    private List<VehicleFuelReconciationDetDTO> tbSwVehiclefuelInrecDets;

    /*
     * private VehicleFuelReconciationDetDTO vehicleFuelReconciationDetDTO; public VehicleFuelReconciationDetDTO
     * getVehicleFuelReconciationDetDTO() { return vehicleFuelReconciationDetDTO; } public void
     * setVehicleFuelReconciationDetDTO(VehicleFuelReconciationDetDTO vehicleFuelReconciationDetDTO) {
     * this.vehicleFuelReconciationDetDTO = vehicleFuelReconciationDetDTO; }
     */

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
    }

    public String getPuVendorname() {
        return puVendorname;
    }

    public void setPuVendorname(String puVendorname) {
        this.puVendorname = puVendorname;
    }

    public VehicleFuelReconciationDTO() {
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getPuPumpname() {
        return puPumpname;
    }

    public void setPuPumpname(String puPumpname) {
        this.puPumpname = puPumpname;
    }

    /*
     * public List<VehicleFuellingDTO> getVehicleFuellingList() { return vehicleFuellingList; } public void
     * setVehicleFuellingList(List<VehicleFuellingDTO> vehicleFuellingList) { this.vehicleFuellingList = vehicleFuellingList; }
     */

    public Long getInrecId() {
        return this.inrecId;
    }

    public void setInrecId(Long inrecId) {
        this.inrecId = inrecId;
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

    public Date getInrecFromdt() {
        return this.inrecFromdt;
    }

    public void setInrecFromdt(Date inrecFromdt) {
        this.inrecFromdt = inrecFromdt;
    }

    public Date getInrecTodt() {
        return this.inrecTodt;
    }

    public void setInrecTodt(Date inrecTodt) {
        this.inrecTodt = inrecTodt;
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

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public List<VehicleFuelReconciationDetDTO> getTbSwVehiclefuelInrecDets() {
        return this.tbSwVehiclefuelInrecDets;
    }

    public void setTbSwVehiclefuelInrecDets(List<VehicleFuelReconciationDetDTO> tbSwVehiclefuelInrecDets) {
        this.tbSwVehiclefuelInrecDets = tbSwVehiclefuelInrecDets;
    }

    public VehicleFuelReconciationDetDTO addTbSwVehiclefuelInrecDet(VehicleFuelReconciationDetDTO tbSwVehiclefuelInrecDet) {
        getTbSwVehiclefuelInrecDets().add(tbSwVehiclefuelInrecDet);
        tbSwVehiclefuelInrecDet.setTbSwVehiclefuelInrec(this);

        return tbSwVehiclefuelInrecDet;
    }

    public VehicleFuelReconciationDetDTO removeTbSwVehiclefuelInrecDet(VehicleFuelReconciationDetDTO tbSwVehiclefuelInrecDet) {
        getTbSwVehiclefuelInrecDets().remove(tbSwVehiclefuelInrecDet);
        tbSwVehiclefuelInrecDet.setTbSwVehiclefuelInrec(null);

        return tbSwVehiclefuelInrecDet;
    }

    public File getVemFile() {
        return vemFile;
    }

    public void setVemFile(File vemFile) {
        this.vemFile = vemFile;
    }

    public Long getInrecdExpen() {
        return inrecdExpen;
    }

    public void setInrecdExpen(Long inrecdExpen) {
        this.inrecdExpen = inrecdExpen;
    }

    public BigDecimal getInrecdInvamt() {
        return inrecdInvamt;
    }

    public void setInrecdInvamt(BigDecimal inrecdInvamt) {
        this.inrecdInvamt = inrecdInvamt.setScale(2, RoundingMode.HALF_EVEN);
    }

    public Date getInrecdInvdate() {
        return inrecdInvdate;
    }

    public void setInrecdInvdate(Date inrecdInvdate) {
        this.inrecdInvdate = inrecdInvdate;
    }

    public Long getInrecdInvno() {
        return inrecdInvno;
    }

    public void setInrecdInvno(Long inrecdInvno) {
        this.inrecdInvno = inrecdInvno;
    }

    public Long getVefId() {
        return vefId;
    }

    public void setVefId(Long vefId) {
        this.vefId = vefId;
    }

    public String getExpenditureHead() {
        return expenditureHead;
    }

    public void setExpenditureHead(String expenditureHead) {
        this.expenditureHead = expenditureHead;
    }

    public Long getExpenditureId() {
        return expenditureId;
    }

    public void setExpenditureId(Long expenditureId) {
        this.expenditureId = expenditureId;
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