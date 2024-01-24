package com.abm.mainet.vehiclemanagement.dto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the tb_sw_contvend_mapping database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 29-May-2018
 */
public class VendorContractMappingDTO implements Serializable {

    private static final long serialVersionUID = 7056799913956545511L;

    private Long mapId;

    private Long codWard1;

    private Long codWard2;

    private Long codWard3;

    private Long codWard4;

    private Long codWard5;

    private Long contId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long mapGarbage;

    private Long mapGarbageUnit;

    private String mapTaskId;

    private Long mapWastetype;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String vendorNam;

    private String vendorAddress;

    private String representedBy;

    private String designation;

    private String tendorNo;

    private String resolutionNo;

    private String resolutionDate;

    private String contractFromDate;

    private String contractToDate;

    private String contractAmt;

    private String witnessName;

    private String vendorWitness;

    private String services;

    private Long beatId;

    private Long empCount;

    private BigDecimal contractAmount;

    private Long vehicleCount;

    public Long getMapId() {
        return this.mapId;
    }

    public String getVendorNam() {
        return vendorNam;
    }

    public void setVendorNam(String vendorNam) {
        this.vendorNam = vendorNam;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public Long getCodWard1() {
        return this.codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return this.codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return this.codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return this.codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return this.codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
    }

    public Long getContId() {
        return this.contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
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

    public Long getMapGarbage() {
        return this.mapGarbage;
    }

    public void setMapGarbage(Long mapGarbage) {
        this.mapGarbage = mapGarbage;
    }

    public Long getMapGarbageUnit() {
        return mapGarbageUnit;
    }

    public void setMapGarbageUnit(Long mapGarbageUnit) {
        this.mapGarbageUnit = mapGarbageUnit;
    }

    public String getMapTaskId() {
        return this.mapTaskId;
    }

    public void setMapTaskId(String mapTaskId) {
        this.mapTaskId = mapTaskId;
    }

    public Long getMapWastetype() {
        return this.mapWastetype;
    }

    public void setMapWastetype(Long mapWastetype) {
        this.mapWastetype = mapWastetype;
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

    public String getRepresentedBy() {
        return representedBy;
    }

    public void setRepresentedBy(String representedBy) {
        this.representedBy = representedBy;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTendorNo() {
        return tendorNo;
    }

    public void setTendorNo(String tendorNo) {
        this.tendorNo = tendorNo;
    }

    public String getResolutionNo() {
        return resolutionNo;
    }

    public void setResolutionNo(String resolutionNo) {
        this.resolutionNo = resolutionNo;
    }

    public String getContractFromDate() {
        return contractFromDate;
    }

    public void setContractFromDate(String contractFromDate) {
        this.contractFromDate = contractFromDate;
    }

    public String getContractToDate() {
        return contractToDate;
    }

    public void setContractToDate(String contractToDate) {
        this.contractToDate = contractToDate;
    }

    public String getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(String contractAmt) {
        this.contractAmt = contractAmt;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getWitnessName() {
        return witnessName;
    }

    public void setWitnessName(String witnessName) {
        this.witnessName = witnessName;
    }

    public String getVendorWitness() {
        return vendorWitness;
    }

    public void setVendorWitness(String vendorWitness) {
        this.vendorWitness = vendorWitness;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Long getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Long empCount) {
        this.empCount = empCount;
    }

    public Long getVehicleCount() {
        return vehicleCount;
    }

    public void setVehicleCount(Long vehicleCount) {
        this.vehicleCount = vehicleCount;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

}