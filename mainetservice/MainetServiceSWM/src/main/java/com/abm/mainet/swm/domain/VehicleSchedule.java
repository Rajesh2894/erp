package com.abm.mainet.swm.domain;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the TB_SW_VEHICLE_SCHEDULING database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 06-Jun-2018
 */
@Entity
@Table(name = "TB_SW_VEHICLE_SCHEDULING")
public class VehicleSchedule implements Serializable {

    private static final long serialVersionUID = 8239834529816213293L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VES_ID", unique = true, nullable = false)
    private Long vesId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "LATITUDE", length = 100)
    private String latitude;

    @Column(name = "LONGITUDE", length = 100)
    private String longitude;

    private Long orgid;

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
    @Column(name = "VES_FROMDT")
    private Date vesFromdt;

    @Column(name = "VES_REOCC", length = 1)
    private String vesReocc;

    @Temporal(TemporalType.DATE)
    @Column(name = "VES_TODT")
    private Date vesTodt;

    // bi-directional many-to-one association to TbSwVehicleScheddet
    @OneToMany(mappedBy = "tbSwVehicleScheduling", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VehicleScheduleDet> tbSwVehicleScheddets;
    
    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive;

    public VehicleSchedule() {
    }

    public Long getVesId() {
        return this.vesId;
    }

    public void setVesId(Long vesId) {
        this.vesId = vesId;
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

    public Date getVesFromdt() {
        return this.vesFromdt;
    }

    public void setVesFromdt(Date vesFromdt) {
        this.vesFromdt = vesFromdt;
    }

    public String getVesReocc() {
        return this.vesReocc;
    }

    public void setVesReocc(String vesReocc) {
        this.vesReocc = vesReocc;
    }

    public Date getVesTodt() {
        return this.vesTodt;
    }

    public void setVesTodt(Date vesTodt) {
        this.vesTodt = vesTodt;
    }

    public List<VehicleScheduleDet> getTbSwVehicleScheddets() {
        return this.tbSwVehicleScheddets;
    }

    public void setTbSwVehicleScheddets(List<VehicleScheduleDet> tbSwVehicleScheddets) {
        this.tbSwVehicleScheddets = tbSwVehicleScheddets;
    }

    public VehicleScheduleDet addTbSwVehicleScheddet(VehicleScheduleDet tbSwVehicleScheddet) {
        getTbSwVehicleScheddets().add(tbSwVehicleScheddet);
        tbSwVehicleScheddet.setTbSwVehicleScheduling(this);

        return tbSwVehicleScheddet;
    }

    public VehicleScheduleDet removeTbSwVehicleScheddet(VehicleScheduleDet tbSwVehicleScheddet) {
        getTbSwVehicleScheddets().remove(tbSwVehicleScheddet);
        tbSwVehicleScheddet.setTbSwVehicleScheduling(null);

        return tbSwVehicleScheddet;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_VEHICLE_SCHEDULING", "VES_ID" };
    }

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

}