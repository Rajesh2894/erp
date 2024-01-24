package com.abm.mainet.rnl.domain;

import java.util.Date;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 * @since 05 june 2019
 * 
 * Municipal(Estate) Property Amenity entity Created .
 */

@Entity
@Table(name = "TB_RL_PROPTY_AMINITYFACILITY")
public class EstatePropertyAminity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROP_AMID")
    private Long propAmenityId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID")
    private EstatePropertyEntity estatePropertyMasterAmenities;

    @Column(name = "PROP_AMTFACT")
    private Long propAmtFacility;

    @Column(name = "PROP_QUANTITY")
    private Long propQuantity;

    @Column(name = "PROP_TYPE")
    private String propType;

    @Column(name = "PROP_AMIFAC_STATUS")
    private String amiFacStatus;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "LANGID")
    private long langId;

    @Column(name = "CREATED_BY")
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
    
    @Column(name = "REMARKS")
    private String remark;
    

    public Long getPropAmenityId() {
        return propAmenityId;
    }

    public void setPropAmenityId(Long propAmenityId) {
        this.propAmenityId = propAmenityId;
    }

    public EstatePropertyEntity getEstatePropertyMasterAmenities() {
        return estatePropertyMasterAmenities;
    }

    public void setEstatePropertyMasterAmenities(EstatePropertyEntity estatePropertyMasterAmenities) {
        this.estatePropertyMasterAmenities = estatePropertyMasterAmenities;
    }

    public Long getPropAmtFacility() {
        return propAmtFacility;
    }

    public void setPropAmtFacility(Long propAmtFacility) {
        this.propAmtFacility = propAmtFacility;
    }

    public Long getPropQuantity() {
        return propQuantity;
    }

    public void setPropQuantity(Long propQuantity) {
        this.propQuantity = propQuantity;
    }

    public String getPropType() {
        return propType;
    }

    public void setPropType(String propType) {
        this.propType = propType;
    }

    public String getAmiFacStatus() {
        return amiFacStatus;
    }

    public void setAmiFacStatus(String amiFacStatus) {
        this.amiFacStatus = amiFacStatus;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public long getLangId() {
        return langId;
    }

    public void setLangId(long langId) {
        this.langId = langId;
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

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }
    
    public String getremark() {
        return remark;
    }

    public void setremark(String remark) {
        this.remark = remark;
    }

   
    

    public String[] getPkValues() {
        return new String[] { "RL", "TB_RL_PROPTY_AMINITYFACILITY", "PROP_AMID" };
    }
    
}
