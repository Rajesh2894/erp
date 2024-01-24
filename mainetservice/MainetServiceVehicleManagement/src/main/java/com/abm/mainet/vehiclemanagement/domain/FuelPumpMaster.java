package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_pump_mast database table.
 * 
 */
@Entity
@Table(name = "tb_vm_pump_mast")
public class FuelPumpMaster implements Serializable {

    private static final long serialVersionUID = 8798598065129918186L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FPM_ID")
    private Long puId;

    private Long orgid;

    @Column(name = "PU_ADDRESS")
    private String puAddress;

    @Column(name = "PU_PUMPNAME")
    private String puPumpname;

    @Column(name = "PU_PUTYPE")
    private Long puPutype;

    @Column(name = "PU_ACTIVE")
    private String puActive;

    @Column(name = "VM_VENDORID")
    private Long vendorId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // bi-directional many-to-one association to TbSwPumpFuldet
    @OneToMany(mappedBy = "tbSwPumpMast", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<FuelPumpMasterDetails> tbSwPumpFuldets;

    public FuelPumpMaster() {
    }

    public Long getPuId() {
        return puId;
    }

    public void setPuId(Long puId) {
        this.puId = puId;
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

    public List<FuelPumpMasterDetails> getTbSwPumpFuldets() {
        return this.tbSwPumpFuldets;
    }

    public void setTbSwPumpFuldets(List<FuelPumpMasterDetails> tbSwPumpFuldets) {
        this.tbSwPumpFuldets = tbSwPumpFuldets;
    }

    public FuelPumpMasterDetails addTbSwPumpFuldet(FuelPumpMasterDetails tbSwPumpFuldet) {
        getTbSwPumpFuldets().add(tbSwPumpFuldet);
        tbSwPumpFuldet.setTbSwPumpMast(this);

        return tbSwPumpFuldet;
    }

    public FuelPumpMasterDetails removeTbSwPumpFuldet(FuelPumpMasterDetails tbSwPumpFuldet) {
        getTbSwPumpFuldets().remove(tbSwPumpFuldet);
        tbSwPumpFuldet.setTbSwPumpMast(null);

        return tbSwPumpFuldet;
    }

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

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_POPULATION_MAST_HIST", "POP_ID_H" };
    }
}