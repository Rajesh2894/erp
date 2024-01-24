package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
public class PumpMasterDTO implements Serializable {

    private static final long serialVersionUID = 3915073710665097271L;

    private Long puId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long vendorId;

    private String puAddress;

    private String puPumpname;

    private String vendorName;

    private Long puPutype;

    private Long updatedBy;

    private Date updatedDate;

    private String puActive;

    private List<PumpFuelDetailsDTO> tbSwPumpFuldets = new ArrayList<>();

    public PumpMasterDTO() {
    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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

    public String getPuActive() {
        return puActive;
    }

    public void setPuActive(String puActive) {
        this.puActive = puActive;
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

    public String getPuAddress() {
        return this.puAddress;
    }

    public void setPuAddress(String puAddress) {
        this.puAddress = puAddress;
    }

    public String getPuPumpname() {
        return this.puPumpname;
    }

    public void setPuPumpname(String puPumpname) {
        this.puPumpname = puPumpname;
    }

    public Long getPuPutype() {
        return this.puPutype;
    }

    public void setPuPutype(Long puPutype) {
        this.puPutype = puPutype;
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

    public List<PumpFuelDetailsDTO> getTbSwPumpFuldets() {
        return this.tbSwPumpFuldets;
    }

    public void setTbSwPumpFuldets(List<PumpFuelDetailsDTO> tbSwPumpFuldets) {
        this.tbSwPumpFuldets = tbSwPumpFuldets;
    }

    public PumpFuelDetailsDTO addTbSwPumpFuldet(PumpFuelDetailsDTO tbSwPumpFuldet) {
        getTbSwPumpFuldets().add(tbSwPumpFuldet);
        tbSwPumpFuldet.setTbSwPumpMast(this);

        return tbSwPumpFuldet;
    }

    public PumpFuelDetailsDTO removeTbSwPumpFuldet(PumpFuelDetailsDTO tbSwPumpFuldet) {
        getTbSwPumpFuldets().remove(tbSwPumpFuldet);
        tbSwPumpFuldet.setTbSwPumpMast(null);

        return tbSwPumpFuldet;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}