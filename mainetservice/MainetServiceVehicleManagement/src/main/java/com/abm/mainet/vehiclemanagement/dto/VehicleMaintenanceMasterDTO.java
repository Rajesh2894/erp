package com.abm.mainet.vehiclemanagement.dto;

import java.io.Serializable;
import java.util.Date;

public class VehicleMaintenanceMasterDTO implements Serializable {

    private static final long serialVersionUID = 2013128551832682207L;

    private Long veMeId;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private String veMeActive;

    private Long veMainday;

    private Long veMainUnit;

    private Long veDowntime;

    private Long veDowntimeUnit;

    private Long veVetype;
    
    private Long veId;
    	
    private String veNo;
    
    private Long veMainKM;
	
    private Long veMainUnitKM;

    private Long veBufferTime;
	
    private Long veBufferTimeUnit;

    private String vehicleIdList;
    
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

	public String getVeNo() {
		return veNo;
	}

	public void setVeNo(String veNo) {
		this.veNo = veNo;
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

	public String getVehicleIdList() {
		return vehicleIdList;
	}

	public void setVehicleIdList(String vehicleIdList) {
		this.vehicleIdList = vehicleIdList;
	}


}
