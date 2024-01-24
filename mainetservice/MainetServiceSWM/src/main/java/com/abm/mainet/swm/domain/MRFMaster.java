package com.abm.mainet.swm.domain;

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
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_MRF_MAST")
public class MRFMaster implements Serializable {
    private static final long serialVersionUID = 7076293652991467345L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MRF_ID", unique = true, nullable = false)
    private Long mrfId;

    @Column(name = "MRF_PLANTID")
    private String mrfPlantId;

    @Column(name = "MRF_PLATNAME")
    private String mrfPlantName;

    @Column(name = "MRF_CATEGORY")
    private Long mrfCategory;

    @Column(name = "MRF_DATEOFOPEN")
    private Date mrfDateOpen;

    @Column(name = "MRF_DCENTRALISED", nullable = false, length = 1)
    private String mrfDecentralised;

    @Column(name = "MRF_OWNERSHIP", nullable = false, length = 1)
    private String mrfOwnerShip;

    @Column(name = "LOC_ID")
    private Long locId;

    @Column(name = "MRF_PLANTCAP")
    private BigDecimal mrfPlantCap;

    @Column(name = "MRF_ISINTEGRATED_PLANT", nullable = false, length = 1)
    private String mrfIsIntegratedPlant;

    @Column(name = "MRF_INTEGRATED_PLANTID")
    private String mrfIntegratedPlantId;

    @Column(name = "MRF_ISRDF", nullable = false, length = 1)
    private String mrfIsrdf;

    @Column(name = "MRF_RDFQTY")
    private BigDecimal mrfRdfqrt;

    @Column(name = "MRF_ISCTC", nullable = false, length = 1)
    private String mrfIsctc;

    @Column(name = "MRF_ISAGRE_INTEGRATED")
    private Long mrfIsagreIntegrated;

    @Column(name = "PROJ_CODE")
    private String projCode;

    @Column(name = "PROJ_COST")
    private BigDecimal pojCost;

    @Column(name = "PROJ_PROGRESS")
    private String projProgress;

    @Column(name = "ASSET_CODE")
    private String assetCode;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @OneToMany(mappedBy = "mrfVEId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MRFVehicleDetail> tbSwMrfVechicleDet;

    @OneToMany(mappedBy = "mrfEMPId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MRFEmployeeDetail> tbSwMrfEmployeeDet;
    
    @Column(name = "PM_PROP_NO", length = 20, nullable = true)
	private String propertyNo;

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    public String getMrfPlantId() {
        return mrfPlantId;
    }

    public void setMrfPlantId(String mrfPlantId) {
        this.mrfPlantId = mrfPlantId;
    }

    public String getMrfPlantName() {
        return mrfPlantName;
    }

    public void setMrfPlantName(String mrfPlantName) {
        this.mrfPlantName = mrfPlantName;
    }

    public Long getMrfCategory() {
        return mrfCategory;
    }

    public void setMrfCategory(Long mrfCategory) {
        this.mrfCategory = mrfCategory;
    }

    public Date getMrfDateOpen() {
        return mrfDateOpen;
    }

    public void setMrfDateOpen(Date mrfDateOpen) {
        this.mrfDateOpen = mrfDateOpen;
    }

    public String getMrfDecentralised() {
        return mrfDecentralised;
    }

    public void setMrfDecentralised(String mrfDecentralised) {
        this.mrfDecentralised = mrfDecentralised;
    }

    public String getMrfOwnerShip() {
        return mrfOwnerShip;
    }

    public void setMrfOwnerShip(String mrfOwnerShip) {
        this.mrfOwnerShip = mrfOwnerShip;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public BigDecimal getMrfPlantCap() {
        return mrfPlantCap;
    }

    public void setMrfPlantCap(BigDecimal mrfPlantCap) {
        this.mrfPlantCap = mrfPlantCap;
    }

    public String getMrfIsIntegratedPlant() {
        return mrfIsIntegratedPlant;
    }

    public void setMrfIsIntegratedPlant(String mrfIsIntegratedPlant) {
        this.mrfIsIntegratedPlant = mrfIsIntegratedPlant;
    }

    public String getMrfIntegratedPlantId() {
        return mrfIntegratedPlantId;
    }

    public void setMrfIntegratedPlantId(String mrfIntegratedPlantId) {
        this.mrfIntegratedPlantId = mrfIntegratedPlantId;
    }

    public String getMrfIsrdf() {
        return mrfIsrdf;
    }

    public void setMrfIsrdf(String mrfIsrdf) {
        this.mrfIsrdf = mrfIsrdf;
    }

    public BigDecimal getMrfRdfqrt() {
        return mrfRdfqrt;
    }

    public void setMrfRdfqrt(BigDecimal mrfRdfqrt) {
        this.mrfRdfqrt = mrfRdfqrt;
    }

    public String getMrfIsctc() {
        return mrfIsctc;
    }

    public void setMrfIsctc(String mrfIsctc) {
        this.mrfIsctc = mrfIsctc;
    }

    public Long getMrfIsagreIntegrated() {
        return mrfIsagreIntegrated;
    }

    public void setMrfIsagreIntegrated(Long mrfIsagreIntegrated) {
        this.mrfIsagreIntegrated = mrfIsagreIntegrated;
    }

    public String getProjCode() {
        return projCode;
    }

    public void setProjCode(String projCode) {
        this.projCode = projCode;
    }

    public BigDecimal getPojCost() {
        return pojCost;
    }

    public void setPojCost(BigDecimal pojCost) {
        this.pojCost = pojCost;
    }

    public String getProjProgress() {
        return projProgress;
    }

    public void setProjProgress(String projProgress) {
        this.projProgress = projProgress;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public List<MRFVehicleDetail> getTbSwMrfVechicleDet() {
        return tbSwMrfVechicleDet;
    }

    public void setTbSwMrfVechicleDet(List<MRFVehicleDetail> tbSwMrfVechicleDet) {
        this.tbSwMrfVechicleDet = tbSwMrfVechicleDet;
    }

    public List<MRFEmployeeDetail> getTbSwMrfEmployeeDet() {
        return tbSwMrfEmployeeDet;
    }

    public void setTbSwMrfEmployeeDet(List<MRFEmployeeDetail> tbSwMrfEmployeeDet) {
        this.tbSwMrfEmployeeDet = tbSwMrfEmployeeDet;
    }
    
    public String getPropertyNo() {
		return propertyNo;
	}

	public void setPropertyNo(String propertyNo) {
		this.propertyNo = propertyNo;
	}

	public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_MRF_MAST", "MRF_ID" };
    }
}
