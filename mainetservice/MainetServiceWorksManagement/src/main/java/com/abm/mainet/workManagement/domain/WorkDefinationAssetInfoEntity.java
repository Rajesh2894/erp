package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_WMS_WORKDEF_ASSET_INFO")
public class WorkDefinationAssetInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WORK_ASSETID", nullable = false)
    private Long workAssetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_ID", nullable = false)
    private WorkDefinationEntity workDefEntity;

    @Column(name = "ASSET_ID", nullable = true)
    private Long assetId;

    @Column(name = "ASSET_CODE", length = 50)
    private String assetCode;

    @Column(name = "ASSET_NAME", length = 50)
    private String assetName;

    @Column(name = "ASSET_CATEGORY", length = 50)
    private String assetCategory;

    @Column(name = "ASSET_DEPARTMENT", length = 50)
    private String assetDepartment;

    @Column(name = "ASSET_LOCATION", length = 50)
    private String assetLocation;

    @Column(name = "ASSET_STATUS", length = 50)
    private String assetStatus;
    
    @Column(name = "ASSET_PURPOSE", length = 200)
    private String assetPurpose;
    
    @Temporal(TemporalType.DATE)
	@Column(name = "ASSET_AQUI_DATE", nullable = true)
	private Date assetAquiDate;
    
    @Column(name = "ASSET_LENGTH")
	private BigDecimal assetLength;
    
    @Column(name = "ASSET_BREADTH")
	private BigDecimal assetBreadth;
    
    @Column(name = "ASSET_WIDTH")
	private BigDecimal assetWidth;
    
    @Column(name = "ASSET_HEIGHT")
	private BigDecimal assetHeight;
    
    @Column(name = "ASSET_PLOT_AREA")
	private BigDecimal assetPlotArea;
    
    @Column(name = "ASSET_CAAREA")
	private BigDecimal assetCaArea;
    
    @Column(name = "ASSET_AQUI_COST")
	private BigDecimal assetAquiCost;
    
    @Column(name = "ASSET_NOFLOOR", nullable = true)
    private Long assetNoOfFloors;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "ASSET_REC_STATUS")
    private String assetRecStatus;

    public WorkDefinationEntity getWorkDefEntity() {
        return workDefEntity;
    }

    public void setWorkDefEntity(WorkDefinationEntity workDefEntity) {
        this.workDefEntity = workDefEntity;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public Long getWorkAssetId() {
        return workAssetId;
    }

    public void setWorkAssetId(Long workAssetId) {
        this.workAssetId = workAssetId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getAssetDepartment() {
        return assetDepartment;
    }

    public void setAssetDepartment(String assetDepartment) {
        this.assetDepartment = assetDepartment;
    }

    public String getAssetLocation() {
        return assetLocation;
    }

    public void setAssetLocation(String assetLocation) {
        this.assetLocation = assetLocation;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetRecStatus() {
        return assetRecStatus;
    }

    public void setAssetRecStatus(String assetRecStatus) {
        this.assetRecStatus = assetRecStatus;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_WORKDEF_ASSET_INFO", "WORK_ASSETID" };
    }

	public String getAssetPurpose() {
		return assetPurpose;
	}

	public void setAssetPurpose(String assetPurpose) {
		this.assetPurpose = assetPurpose;
	}

	public Date getAssetAquiDate() {
		return assetAquiDate;
	}

	public void setAssetAquiDate(Date assetAquiDate) {
		this.assetAquiDate = assetAquiDate;
	}

	public BigDecimal getAssetLength() {
		return assetLength;
	}

	public void setAssetLength(BigDecimal assetLength) {
		this.assetLength = assetLength;
	}

	public BigDecimal getAssetBreadth() {
		return assetBreadth;
	}

	public void setAssetBreadth(BigDecimal assetBreadth) {
		this.assetBreadth = assetBreadth;
	}

	public BigDecimal getAssetWidth() {
		return assetWidth;
	}

	public void setAssetWidth(BigDecimal assetWidth) {
		this.assetWidth = assetWidth;
	}

	public BigDecimal getAssetHeight() {
		return assetHeight;
	}

	public void setAssetHeight(BigDecimal assetHeight) {
		this.assetHeight = assetHeight;
	}

	public BigDecimal getAssetPlotArea() {
		return assetPlotArea;
	}

	public void setAssetPlotArea(BigDecimal assetPlotArea) {
		this.assetPlotArea = assetPlotArea;
	}

	public BigDecimal getAssetAquiCost() {
		return assetAquiCost;
	}

	public void setAssetAquiCost(BigDecimal assetAquiCost) {
		this.assetAquiCost = assetAquiCost;
	}

	public Long getAssetNoOfFloors() {
		return assetNoOfFloors;
	}

	public void setAssetNoOfFloors(Long assetNoOfFloors) {
		this.assetNoOfFloors = assetNoOfFloors;
	}

	public BigDecimal getAssetCaArea() {
		return assetCaArea;
	}

	public void setAssetCaArea(BigDecimal assetCaArea) {
		this.assetCaArea = assetCaArea;
	}

}
