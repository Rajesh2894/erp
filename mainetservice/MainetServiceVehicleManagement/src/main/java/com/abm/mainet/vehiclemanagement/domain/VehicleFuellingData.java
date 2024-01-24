package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_vehiclefuel_mast database table.
 *
 * @author niraj.kumar
 *
 * Created Date : 25-May-2018
 */
@Entity
@Table(name = "TB_VM_VEHICLEFUEL_MAST")
public class VehicleFuellingData implements Serializable {

    private static final long serialVersionUID = 5503948952345312596L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEHF_ID", unique = true, nullable = false)
    private Long vefId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "PU_ID")
    private Long puId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_ID")
    private Long veId;

    @Column(name = "VE_VETYPE")
    private Long veVetype;

    @Temporal(TemporalType.DATE)
    @Column(name = "VEF_DMDATE")
    private Date vefDmdate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VEF_DATE")
    private Date vefDate;

    @Column(name = "VEF_DMNO")
    private String vefDmno;

    @Column(name = "VEF_READING")
    private Long vefReading;

    @Temporal(TemporalType.DATE)
    @Column(name = "VEF_RECEIPTDATE")
    private Date vefReceiptdate;

    @Column(name = "VEF_RECEIPTNO")
    private Long vefReceiptno;

    @Column(name = "VEF_RMAMOUNT", precision = 10, scale = 2)
    private BigDecimal vefRmamount;

    @Column(name = "VEF_DRIVERNAME", length = 50)
    private String driverName;
    
    @OneToMany(mappedBy = "tbSwVehiclefuelMast", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<VehicleFuellingDet> tbSwVehiclefuelDets;

    public VehicleFuellingData() { }

    public VehicleFuellingDet addTbSwVehiclefuelDet(final VehicleFuellingDet tbSwVehiclefuelDet) {
        getTbSwVehiclefuelDets().add(tbSwVehiclefuelDet);
        tbSwVehiclefuelDet.setTbSwVehiclefuelMast(this);

        return tbSwVehiclefuelDet;
    }

    public VehicleFuellingDet removeTbSwVehiclefuelDet(final VehicleFuellingDet tbSwVehiclefuelDet) {
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

    public String getVefDmno() {
        return vefDmno;
    }

    public void setVefDmno(String vefDmno) {
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

    public List<VehicleFuellingDet> getTbSwVehiclefuelDets() {
        return tbSwVehiclefuelDets;
    }

    public void setTbSwVehiclefuelDets(List<VehicleFuellingDet> tbSwVehiclefuelDets) {
        this.tbSwVehiclefuelDets = tbSwVehiclefuelDets;
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

    public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_VEHICLEFUEL_MAST", "VEHF_ID" };
    }
}