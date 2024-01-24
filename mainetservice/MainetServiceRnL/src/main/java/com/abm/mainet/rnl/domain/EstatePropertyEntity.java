package com.abm.mainet.rnl.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 *
 * Municipal(Estate) Property Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_PROPERTY_MAS")
public class EstatePropertyEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROP_ID", nullable = false)
    private Long propId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ES_ID")
    private EstateEntity estateEntity;

    @Column(name = "PROP_NO")
    private String assesmentPropId;

    /**
     * Property Number (Auto generated)('PT'+Estate No+sequence no)
     */
    @Column(name = "PM_PROPNO", nullable = false)
    private String code;

    @Column(name = "PM_OLDPROPNO")
    private String oldPropNo;

    @Column(name = "PROP_NAME")
    private String name;

    @Column(name = "PROP_UNIT_NUMBER")
    private Integer unitNo;

    @Column(name = "PROP_OCCUPANCY")
    private Integer occupancy;

    @Column(name = "PROP_USAGE")
    private Integer usage;

    @Column(name = "PROP_FLOOR")
    private Integer floor;

    @Column(name = "PROP_ROADTYPE")
    private Integer roadType;

    @Column(name = "PROP_GIS_ID")
    private String gisId;

    @Column(name = "PROP_COURT_CASE")
    private Character courtCase;

    @Column(name = "PROP_STOP_BILLING")
    private Character stopBilling;

    @Column(name = "PROP_STATUS")
    private Character status;

    @Column(name = "PROP_TOTAL_AREA", precision = 20, scale = 2)
    private Double totalArea;

    @Column(name = "PROP_FLG")
    private Character flag;

    @Column(name = "PROP_SECURITY_DEP", precision = 20, scale = 2)
    private Integer securityDeposite;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "LANGID")
    private long langId;

    @Column(name = "USER_ID")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "PROP_LATITUDE")
    private String propLatitude;

    @Column(name = "PROP_LONGITUDE")
    private String propLongitude;

    @Column(name = "PROP_CAPACITY")
    private Long propCapacity;

    @Column(name = "PROP_NODAY_ALLOW")
    private Long propNoDaysAllow;

    @Column(name = "PROP_MAIN_BY")
    private Long propMaintainBy;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "estatePropertyEntity")
    @Where(clause = "PROP_ACTIVE = 'Y'")
    private List<EstatePropertyDetails> estatePropertyDetails = Collections.emptyList();

    @Column(name = "PM_PRMSTID", length = 12)
    private Long prmstId;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID", referencedColumnName = "PROP_ID")
    @Where(clause = "PROP_AMIFAC_STATUS = 'Y'")
    private List<EstatePropertyAminity> estateAmenities = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID", referencedColumnName = "PROP_ID")
    @Where(clause = "PROP_EV_STATUS = 'Y'")
    private List<EstatePropertyEvent> estatePropEvent = new ArrayList<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID", referencedColumnName = "PROP_ID")
    @Where(clause = "PROP_SHIF_STATUS = 'Y'")
    private List<EstatePropertyShift> estatePropShift = new ArrayList<>();

    public Long getPropId() {
        return propId;
    }

    public void setPropId(final Long propId) {
        this.propId = propId;
    }

    public EstateEntity getEstateEntity() {
        return estateEntity;
    }

    public void setEstateEntity(final EstateEntity estateEntity) {
        this.estateEntity = estateEntity;
    }

    public String getAssesmentPropId() {
        return assesmentPropId;
    }

    public void setAssesmentPropId(final String assesmentPropId) {
        this.assesmentPropId = assesmentPropId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getOldPropNo() {
        return oldPropNo;
    }

    public void setOldPropNo(final String oldPropNo) {
        this.oldPropNo = oldPropNo;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(final Integer unitNo) {
        this.unitNo = unitNo;
    }

    public Integer getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(final Integer occupancy) {
        this.occupancy = occupancy;
    }

    public Integer getUsage() {
        return usage;
    }

    public void setUsage(final Integer usage) {
        this.usage = usage;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(final Integer floor) {
        this.floor = floor;
    }

    public Integer getRoadType() {
        return roadType;
    }

    public void setRoadType(final Integer roadType) {
        this.roadType = roadType;
    }

    public String getGisId() {
        return gisId;
    }

    public void setGisId(final String gisId) {
        this.gisId = gisId;
    }

    public Character getCourtCase() {
        return courtCase;
    }

    public void setCourtCase(final Character courtCase) {
        this.courtCase = courtCase;
    }

    public Character getStopBilling() {
        return stopBilling;
    }

    public void setStopBilling(final Character stopBilling) {
        this.stopBilling = stopBilling;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(final Character status) {
        this.status = status;
    }

   
    public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}

	public Character getFlag() {
        return flag;
    }

    public void setFlag(final Character flag) {
        this.flag = flag;
    }

    public Integer getSecurityDeposite() {
        return securityDeposite;
    }

    public void setSecurityDeposite(final Integer securityDeposite) {
        this.securityDeposite = securityDeposite;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(final long langId) {
        this.langId = langId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public List<EstatePropertyDetails> getEstatePropertyDetails() {
        return estatePropertyDetails;
    }

    public void setEstatePropertyDetails(
            final List<EstatePropertyDetails> estatePropertyDetails) {
        this.estatePropertyDetails = estatePropertyDetails;
    }

    public String getPropLatitude() {
        return propLatitude;
    }

    public void setPropLatitude(final String propLatitude) {
        this.propLatitude = propLatitude;
    }

    public String getPropLonitude() {
        return propLongitude;
    }

    public void setPropLonitude(final String propLonitude) {
        propLongitude = propLonitude;
    }

    public Long getPrmstId() {
        return prmstId;
    }

    public void setPrmstId(final Long prmstId) {
        this.prmstId = prmstId;
    }

    public String getPropLongitude() {
        return propLongitude;
    }

    public void setPropLongitude(final String propLongitude) {
        this.propLongitude = propLongitude;
    }

    public Long getPropCapacity() {
        return propCapacity;
    }

    public void setPropCapacity(Long propCapacity) {
        this.propCapacity = propCapacity;
    }

    public Long getPropNoDaysAllow() {
        return propNoDaysAllow;
    }

    public void setPropNoDaysAllow(Long propNoDaysAllow) {
        this.propNoDaysAllow = propNoDaysAllow;
    }

    public Long getPropMaintainBy() {
        return propMaintainBy;
    }

    public void setPropMaintainBy(Long propMaintainBy) {
        this.propMaintainBy = propMaintainBy;
    }

    public List<EstatePropertyAminity> getEstateAmenities() {
        return estateAmenities;
    }

    public void setEstateAmenities(List<EstatePropertyAminity> estateAmenities) {
        this.estateAmenities = estateAmenities;
    }

    public List<EstatePropertyEvent> getEstatePropEvent() {
        return estatePropEvent;
    }

    public void setEstatePropEvent(List<EstatePropertyEvent> estatePropEvent) {
        this.estatePropEvent = estatePropEvent;
    }

    public List<EstatePropertyShift> getEstatePropShift() {
        return estatePropShift;
    }

    public void setEstatePropShift(List<EstatePropertyShift> estatePropShift) {
        this.estatePropShift = estatePropShift;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.PropMaster.TB_RL_PROPERTY_MAS,
                MainetConstants.RnLDetailEntity.PROP_ID };
    }

}
