package com.abm.mainet.vehicle.management.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class GenVehicleMasterDTO implements Serializable {

    private static final long serialVersionUID = -8653455374082796172L;

    private Long veId;
    
    private String department;

    private Long createdBy;

    private Date createdDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long orgid;

    private Long updatedBy;

    private Date updatedDate;

    private BigDecimal veCapacity;

    private Long veCaunit;

    private String veChasisSrno;

    private String veEngSrno;

    private String veFlag;

    private String veGpsDeviceid;

    private String veModel;

    private Date vePurDate;

    private BigDecimal vePurPrice;

    private String vePurSource;

    private String veNo;

    private String veRegNo;

    private String veRemarks;

    private Date veRentFromdate;

    private Date veRentTodate;

    private BigDecimal veRentamt;

    private BigDecimal veStdWeight;

    private Long veVetype;

    private Long veWeunit;

    private Long vmVendorid;

    private String veActive;

    private String searchMessage;

    private Long assetId;

    private String assetNo;

    private String fromDate;

    private String toDate;

    private String reporttype;

    private String vname;

    private Long contId;

    private String expenseType;

    private Long vendorId;

    private String vendorName;

    private String wasteType;

    private String wasteCapacity;

    private String flagMsg;

    private Long monthNo; 
    private Long fuelType;
	private String fuelTypeDesc;
    private Long vehicleCapacity;
    private String districtName;
    
    private Long deptId;
    private Long locId;
    private Long driverName;
    private Long assignedTo;
    private String purpose;

    private List<GenVehicleMasterDetDTO> tbSwVehicleMasterdets;

    public GenVehicleMasterDTO() {
    }

    
    
    public String getDepartment() {
		return department;
	}



	public void setDepartment(String department) {
		this.department = department;
	}



	public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
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

    public BigDecimal getVeCapacity() {
        return this.veCapacity;
    }

    public Long getContId() {
        return contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public void setVeCapacity(BigDecimal veCapacity) {
        this.veCapacity = veCapacity;
    }

    public Long getVeCaunit() {
        return this.veCaunit;
    }

    public void setVeCaunit(Long veCaunit) {
        this.veCaunit = veCaunit;
    }

    public String getVeChasisSrno() {
        return this.veChasisSrno;
    }

    public void setVeChasisSrno(String veChasisSrno) {
        this.veChasisSrno = veChasisSrno;
    }

    public String getVeEngSrno() {
        return this.veEngSrno;
    }

    public void setVeEngSrno(String veEngSrno) {
        this.veEngSrno = veEngSrno;
    }

    public String getVeFlag() {
        return this.veFlag;
    }

    public void setVeFlag(String veFlag) {
        this.veFlag = veFlag;
    }

    public String getVeGpsDeviceid() {
        return this.veGpsDeviceid;
    }

    public void setVeGpsDeviceid(String veGpsDeviceid) {
        this.veGpsDeviceid = veGpsDeviceid;
    }

    public String getVeModel() {
        return this.veModel;
    }

    public void setVeModel(String veModel) {
        this.veModel = veModel;
    }

    public Date getVePurDate() {
        return this.vePurDate;
    }

    public void setVePurDate(Date vePurDate) {
        this.vePurDate = vePurDate;
    }

    public BigDecimal getVePurPrice() {
        return this.vePurPrice;
    }

    public void setVePurPrice(BigDecimal vePurPrice) {
        this.vePurPrice = vePurPrice;
    }

    public String getVePurSource() {
        return this.vePurSource;
    }

    public void setVePurSource(String vePurSource) {
        this.vePurSource = vePurSource;
    }

    public String getVeNo() {
        return this.veNo;
    }

    public void setVeNo(String veNo) {
        this.veNo = veNo;
    }

    public String getVeRemarks() {
        return this.veRemarks;
    }

    public void setVeRemarks(String veRemarks) {
        this.veRemarks = veRemarks;
    }

    public Date getVeRentFromdate() {
        return this.veRentFromdate;
    }

    public void setVeRentFromdate(Date veRentFromdate) {
        this.veRentFromdate = veRentFromdate;
    }

    public Date getVeRentTodate() {
        return this.veRentTodate;
    }

    public void setVeRentTodate(Date veRentTodate) {
        this.veRentTodate = veRentTodate;
    }

    public BigDecimal getVeRentamt() {
        return this.veRentamt;
    }

    public void setVeRentamt(BigDecimal veRentamt) {
        this.veRentamt = veRentamt;
    }

    public BigDecimal getVeStdWeight() {
        return this.veStdWeight;
    }

    public void setVeStdWeight(BigDecimal veStdWeight) {
        this.veStdWeight = veStdWeight;
    }

    public Long getVeVetype() {
        return this.veVetype;
    }

    public void setVeVetype(Long veVetype) {
        this.veVetype = veVetype;
    }

    public Long getVeWeunit() {
        return this.veWeunit;
    }

    public void setVeWeunit(Long veWeunit) {
        this.veWeunit = veWeunit;
    }

    public Long getVmVendorid() {
        return this.vmVendorid;
    }

    public void setVmVendorid(Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public String getVeActive() {
        return veActive;
    }

    public void setVeActive(String veActive) {
        this.veActive = veActive;
    }

    public String getSearchMessage() {
        return searchMessage;
    }

    public void setSearchMessage(String searchMessage) {
        this.searchMessage = searchMessage;
    }

    public Long getAssetId() {
        return assetId;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public String getReporttype() {
        return reporttype;
    }

    public void setReporttype(String reporttype) {
        this.reporttype = reporttype;
    }

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getWasteCapacity() {
        return wasteCapacity;
    }

    public void setWasteCapacity(String wasteCapacity) {
        this.wasteCapacity = wasteCapacity;
    }




	public List<GenVehicleMasterDetDTO> getTbSwVehicleMasterdets() {
		return tbSwVehicleMasterdets;
	}



	public void setTbSwVehicleMasterdets(List<GenVehicleMasterDetDTO> tbSwVehicleMasterdets) {
		this.tbSwVehicleMasterdets = tbSwVehicleMasterdets;
	}



	public String getVeRegNo() {
        return veRegNo;
    }

    public void setVeRegNo(String veRegNo) {
        this.veRegNo = veRegNo;
    }

    public String getFlagMsg() {
        return flagMsg;
    }

    public void setFlagMsg(String flagMsg) {
        this.flagMsg = flagMsg;
    }

    public Long getMonthNo() {
        return monthNo;
    }

    public void setMonthNo(Long monthNo) {
        this.monthNo = monthNo;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }



	public Long getDeptId() {
		return deptId;
	}



	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}



	public Long getLocId() {
		return locId;
	}



	public void setLocId(Long locId) {
		this.locId = locId;
	}



	public Long getDriverName() {
		return driverName;
	}



	public void setDriverName(Long driverName) {
		this.driverName = driverName;
	}



	public Long getAssignedTo() {
		return assignedTo;
	}



	public void setAssignedTo(Long assignedTo) {
		this.assignedTo = assignedTo;
	}



	public String getPurpose() {
		return purpose;
	}



	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}



	public Long getFuelType() {
		return fuelType;
	}



	public void setFuelType(Long fuelType) {
		this.fuelType = fuelType;
	}



	public String getFuelTypeDesc() {
		return fuelTypeDesc;
	}



	public void setFuelTypeDesc(String fuelTypeDesc) {
		this.fuelTypeDesc = fuelTypeDesc;
	}



	public Long getVehicleCapacity() {
		return vehicleCapacity;
	}



	public void setVehicleCapacity(Long vehicleCapacity) {
		this.vehicleCapacity = vehicleCapacity;
	}
    
}