package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the TB_SW_VEHICLE_MAINTENANCE database table.
 * 
 */
@Entity
@Table(name = "TB_VM_VEHICLE_MAINTENANCE")
public class VehicleMaintenanceMast implements Serializable {

    private static final long serialVersionUID = 1987549425934170852L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VE_MEID", unique = true, nullable = false)
    private Long veMeId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_MEACTIVE")
    private String veMeActive;

    @Column(name = "VE_MAINDAY", nullable = false)
    private Long veMainday;

    @Column(name = "VE_MAINUNIT", nullable = false)
    private Long veMainUnit;

    @Column(name = "VE_DOWNTIME", nullable = false)
    private Long veDowntime;

    @Column(name = "VE_DOWNTIMEUNIT", nullable = false)
    private Long veDowntimeUnit;

    @Column(name = "VE_VETYPE", nullable = false)
    private Long veVetype;
    
	@Column(name = "VE_ID")
	private Long veId;
	
	@Column(name = "VE_MAINKM", nullable = false)
    private Long veMainKM;
	
	@Column(name = "VE_MAINKM_UNIT", nullable = false)
    private Long veMainUnitKM;

	@Column(name = "VE_BUFFER_TIME", nullable = false)
    private Long veBufferTime;
	
	@Column(name = "VE_BUFFER_TIME_UNIT", nullable = false)
    private Long veBufferTimeUnit;
   

    public Long getVeDowntime() {
        return veDowntime;
    }

    public void setVeDowntime(Long veDowntime) {
        this.veDowntime = veDowntime;
    }

    public Long getVeDowntimeUnit() {
        return veDowntimeUnit;
    }

    public void setVeDowntimeUnit(Long veDowntimeUnit) {
        this.veDowntimeUnit = veDowntimeUnit;
    }

    public Long getVeMeId() {
        return veMeId;
    }

    public void setVeMeId(Long veMeId) {
        this.veMeId = veMeId;
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

    public String getVeMeActive() {
        return veMeActive;
    }

    public void setVeMeActive(String veMeActive) {
        this.veMeActive = veMeActive;
    }

    public Long getVeMainday() {
        return veMainday;
    }

    public void setVeMainday(Long veMainday) {
        this.veMainday = veMainday;
    }

    public Long getVeMainUnit() {
        return veMainUnit;
    }

    public void setVeMainUnit(Long veMainUnit) {
        this.veMainUnit = veMainUnit;
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

	public Long getVeMainKM() {
		return veMainKM;
	}

	public void setVeMainKM(Long veMainKM) {
		this.veMainKM = veMainKM;
	}

	public Long getVeMainUnitKM() {
		return veMainUnitKM;
	}

	public void setVeMainUnitKM(Long veMainUnitKM) {
		this.veMainUnitKM = veMainUnitKM;
	}

	public Long getVeBufferTime() {
		return veBufferTime;
	}

	public void setVeBufferTime(Long veBufferTime) {
		this.veBufferTime = veBufferTime;
	}

	public Long getVeBufferTimeUnit() {
		return veBufferTimeUnit;
	}

	public void setVeBufferTimeUnit(Long veBufferTimeUnit) {
		this.veBufferTimeUnit = veBufferTimeUnit;
	}

	public String[] getPkValues() {

        return new String[] { "VM", "TB_VM_VEHICLE_MAINTENANCE", "VE_MEID" };
    }
}
