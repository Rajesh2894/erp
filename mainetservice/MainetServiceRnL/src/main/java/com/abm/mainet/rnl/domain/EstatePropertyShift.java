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
 * @since 5 june 2019
 * 
 * Municipal(Estate) Property Shift entity Created .
 */
@Entity
@Table(name = "TB_RL_PROPTY_SHIFT")
public class EstatePropertyShift {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROP_SHFID")
    private Long propShifId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID")
    private EstatePropertyEntity estatePropertyMasterShift;

    @Column(name = "PROP_SHFT")
    private Long propShift;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROP_FROMTIME")
    private Date propFromTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROP_TOTIME")
    private Date propToTime;

    @Column(name = "PROP_SHIF_STATUS")
    private String shiftStatus;

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

    public Long getPropShifId() {
        return propShifId;
    }

    public void setPropShifId(Long propShifId) {
        this.propShifId = propShifId;
    }

    public EstatePropertyEntity getEstatePropertyMasterShift() {
        return estatePropertyMasterShift;
    }

    public void setEstatePropertyMasterShift(EstatePropertyEntity estatePropertyMasterShift) {
        this.estatePropertyMasterShift = estatePropertyMasterShift;
    }

    public Long getPropShift() {
        return propShift;
    }

    public void setPropShift(Long propShift) {
        this.propShift = propShift;
    }

    public Date getPropFromTime() {
        return propFromTime;
    }

    public void setPropFromTime(Date propFromTime) {
        this.propFromTime = propFromTime;
    }

    public Date getPropToTime() {
        return propToTime;
    }

    public void setPropToTime(Date propToTime) {
        this.propToTime = propToTime;
    }

    public String getShiftStatus() {
        return shiftStatus;
    }

    public void setShiftStatus(String shiftStatus) {
        this.shiftStatus = shiftStatus;
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

    public String[] getPkValues() {
        return new String[] { "RL", "TB_RL_PROPTY_SHIFT", "PROP_SHFID" };
    }
}
