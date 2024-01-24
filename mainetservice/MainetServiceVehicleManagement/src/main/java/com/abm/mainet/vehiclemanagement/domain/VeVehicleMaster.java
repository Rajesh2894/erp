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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_sw_vehicle_mast database table.
 *
 */
@Entity 
@Table(name = "TB_VEHICLE_MAST")
public class VeVehicleMaster implements Serializable {

    private static final long serialVersionUID = -7440983637756226440L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VE_ID", unique = true, nullable = false)
    private Long veId;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "VE_CAPACITY", nullable = false, precision = 10, scale = 2)
    private BigDecimal veCapacity;
    
    
    @Column(name = "capacity_unit")
    private Long veCaunit;

    @Column(name = "VE_CHASIS_SRNO", nullable = false, length = 20)
    private String veChasisSrno;

    @Column(name = "VE_ENG_SRNO", nullable = false, length = 20)
    private String veEngSrno;

    @Column(name = "VE_FLAG", nullable = false, length = 1)
    private String veFlag;

    @Column(name = "VE_GPS_DEVICEID", length = 15)
    private String veGpsDeviceid;

    @Column(name = "VE_MODEL", nullable = false, length = 200)
    private String veModel;

    @Temporal(TemporalType.DATE)
    @Column(name = "VE_PUR_DATE")
    private Date vePurDate;

    @Column(name = "VE_PUR_PRICE", precision = 10, scale = 2)
    private BigDecimal vePurPrice;

    @Column(name = "VE_PUR_SOURCE", length = 200)
    private String vePurSource;

    @Column(name = "VE_NO", nullable = false, length = 15)
    private String veNo;
    
    @Column(name = "VE_REG_NO", nullable = false, length = 15)
    private String veRegNo;

    @Column(name = "VE_REMARKS", length = 200)
    private String veRemarks;

    @Temporal(TemporalType.DATE)
    @Column(name = "VE_RENT_FROMDATE")
    private Date veRentFromdate;

    @Temporal(TemporalType.DATE)
    @Column(name = "VE_RENT_TODATE")
    private Date veRentTodate;

    @Column(name = "VE_RENTAMT", precision = 10, scale = 2)
    private BigDecimal veRentamt;

    @Column(name = "VE_STD_WEIGHT", nullable = false, precision = 10, scale = 2)
    private BigDecimal veStdWeight;

    @Column(name = "VE_VETYPE", nullable = false)
    private Long veVetype;

    @Column(name = "VM_VENDORID")
    private Long vmVendorid;

    @Column(name = "VE_ACTIVE")
    private String veActive;

    @Column(name = "ASSET_ID")
    private Long assetId;

    @Column(name = "CONT_ID")
    private Long contId;

    @Column(name = "ASSET_NO", length = 200)
    private String assetNo;
    
    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "deployed_location_id")
    private Long locId;
    
    @Column(name = "vehicle_driver")
    private Long driverName;
    
    @Column(name = "vehicle_assigned_to")
    private Long assignedTo;
    
    
    @Column(name = "vehicle_purpose")
    private String  purpose;
    
    
    @Column(name = "fuel_Type")
	private Long fuelType;
    
    @Column(name = "VE_FUEL_CAPACITY", nullable = true, precision = 10, scale = 2)
    private BigDecimal veFuelCapacity;
    
    @Column(name = "PUR_REF_NO")
    private String vePurRefNo;
    
    @Column(name = "VEH_MAIN_BY")
    private Long vehMaintainBy;
        
    @Column(name = "MNT_VENDORID")
    private Long maintVendorid;

	// bi-directional many-to-one association to tbSwVehicleMasterDet
    @OneToMany(mappedBy = "tbSwVehicleMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<VehicleMasterDetails> tbSwVehicleMasterdets;

    public VeVehicleMaster() {
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

    public Long getFuelType() {
		return fuelType;
	}

	public void setFuelType(Long fuelType) {
		this.fuelType = fuelType;
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

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetNo() {
        return assetNo;
    }

    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }

    public List<VehicleMasterDetails> getTbSwVehicleMasterdets() {
        return tbSwVehicleMasterdets;
    }

    public void setTbSwVehicleMasterdets(List<VehicleMasterDetails> tbSwVehicleMasterdets) {
        this.tbSwVehicleMasterdets = tbSwVehicleMasterdets;
    }

    public String getVeRegNo() {
        return veRegNo;
    }

    public void setVeRegNo(String veRegNo) {
        this.veRegNo = veRegNo;
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

	
	
	public Long getVeCaunit() {
		return veCaunit;
	}

	public void setVeCaunit(Long veCaunit) {
		this.veCaunit = veCaunit;
	}
	
	

	public BigDecimal getVeFuelCapacity() {
		return veFuelCapacity;
	}

	public void setVeFuelCapacity(BigDecimal veFuelCapacity) {
		this.veFuelCapacity = veFuelCapacity;
	}

	public String[] getPkValues() {

        return new String[] { "VM", "TB_VEHICLE_MAST", "VE_ID" };
    }

	public String getVePurRefNo() {
		return vePurRefNo;
	}

	public void setVePurRefNo(String vePurRefNo) {
		this.vePurRefNo = vePurRefNo;
	}

	public Long getVehMaintainBy() {
		return vehMaintainBy;
	}

	public void setVehMaintainBy(Long vehMaintainBy) {
		this.vehMaintainBy = vehMaintainBy;
	}

	public Long getMaintVendorid() {
		return maintVendorid;
	}

	public void setMaintVendorid(Long maintVendorid) {
		this.maintVendorid = maintVendorid;
	}

}