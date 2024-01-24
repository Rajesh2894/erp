package com.abm.mainet.swm.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Lalit.Prusti
 *
 * Created Date : 15-Jun-2018
 */
public class SanitationStaffTargetDetDTO implements Serializable {

    private static final long serialVersionUID = -8946327585949201240L;

    private Long sandId;

    private Long codWast1;

    private Long codWast2;

    private Long codWast3;

    private Long codWast4;

    private Long codWast5;

    private Long createdBy;

    private Date createdDate;

    private Long deId;

    private Long vehicleId;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long roId;

    private Long sanId;

    private Long sandVolume;

    private Long updatedBy;

    private Date updatedDate;

    private String vehNumber;

    private String routeName;
    private String employeeName;
    private String disposalSite;

    private BigDecimal volume;

    private SanitationStaffTargetDTO sanitationStaffTarget;

    public SanitationStaffTargetDetDTO() {
    }

    public Long getSandId() {
        return this.sandId;
    }

    public void setSandId(Long sandId) {
        this.sandId = sandId;
    }

    public Long getCodWast1() {
        return this.codWast1;
    }

    public void setCodWast1(Long codWast1) {
        this.codWast1 = codWast1;
    }

    public Long getCodWast2() {
        return this.codWast2;
    }

    public void setCodWast2(Long codWast2) {
        this.codWast2 = codWast2;
    }

    public Long getCodWast3() {
        return this.codWast3;
    }

    public void setCodWast3(Long codWast3) {
        this.codWast3 = codWast3;
    }

    public Long getCodWast4() {
        return this.codWast4;
    }

    public void setCodWast4(Long codWast4) {
        this.codWast4 = codWast4;
    }

    public Long getCodWast5() {
        return this.codWast5;
    }

    public void setCodWast5(Long codWast5) {
        this.codWast5 = codWast5;
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

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
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

    public Long getRoId() {
        return this.roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
    }

    public Long getSanId() {
        return this.sanId;
    }

    public void setSanId(Long sanId) {
        this.sanId = sanId;
    }

    public Long getSandVolume() {
        return this.sandVolume;
    }

    public void setSandVolume(Long sandVolume) {
        this.sandVolume = sandVolume;
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

    public String getVehNumber() {
        return vehNumber;
    }

    public void setVehNumber(String vehNumber) {
        this.vehNumber = vehNumber;
    }

    public SanitationStaffTargetDTO getSanitationStaffTarget() {
        return sanitationStaffTarget;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDisposalSite() {
        return disposalSite;
    }

    public void setDisposalSite(String disposalSite) {
        this.disposalSite = disposalSite;
    }

    public void setSanitationStaffTarget(
            SanitationStaffTargetDTO sanitationStaffTarget) {
        this.sanitationStaffTarget = sanitationStaffTarget;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

}