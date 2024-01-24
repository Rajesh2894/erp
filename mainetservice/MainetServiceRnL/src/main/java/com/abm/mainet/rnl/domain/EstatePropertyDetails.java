
package com.abm.mainet.rnl.domain;

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

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author ritesh.patil
 *
 *
 * Municipal(Estate) Property Details Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_PROPERTY_DTL")
public class EstatePropertyDetails {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROPD_ID", nullable = false)
    private Long propDetId;

    @JsonIgnore
    @JoinColumn(name = "PROP_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private EstatePropertyEntity estatePropertyEntity;

    @Column(name = "PROP_AREA_TYPE")
    private Integer areaType;

    @Column(name = "PROP_AREA", precision = 12, scale = 2)
    private Double area;

    @Column(name = "PROP_ACTIVE")
    private Character isActive;

    @Column(name = "PROP_AREA_METER", precision = 12, scale = 2)
    private Double areaMeter;
    
    @Column(name = "PROP_LENGTH", precision = 12, scale = 2)
    private Double length;
    
    @Column(name = "PROP_BREADTH", precision = 12, scale = 2)
    private Double breadth;
    
    @Column(name = "PROP_LEFT_AREA", precision = 12, scale = 2)
    private Double leftArea;
    
    @Column(name = "PROP_TOTAL_AREA", precision = 12, scale = 2)
    private Double totalArea;
   
    public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

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

    public Long getPropDetId() {
        return propDetId;
    }

    public void setPropDetId(final Long propDetId) {
        this.propDetId = propDetId;
    }

    public EstatePropertyEntity getEstatePropertyEntity() {
        return estatePropertyEntity;
    }

    public void setEstatePropertyEntity(
            final EstatePropertyEntity estatePropertyEntity) {
        this.estatePropertyEntity = estatePropertyEntity;
    }

    public Integer getAreaType() {
        return areaType;
    }

    public void setAreaType(final Integer areaType) {
        this.areaType = areaType;
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

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.RnLDetailEntity.TB_RL_PROPERTY,
                MainetConstants.RnLDetailEntity.PROPD_ID };
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(final Character isActive) {
        this.isActive = isActive;
    }

	public Double getAreaMeter() {
		return areaMeter;
	}

	public void setAreaMeter(Double areaMeter) {
		this.areaMeter = areaMeter;
	}
	
	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}
	
	public Double getBreadth() {
		return breadth;
	}

	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}

	public Double getLeftArea() {
		return leftArea;
	}

	public void setLeftArea(Double leftArea) {
		this.leftArea = leftArea;
	}

	public Double getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}
 
}
