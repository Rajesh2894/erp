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
 * Municipal(Estate) Property Event entity Created .
 */

@Entity
@Table(name = "TB_RL_PROPTY_EVENT")
public class EstatePropertyEvent {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PROP_EVID")
    private Long propEventId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PROP_ID")
    private EstatePropertyEntity estatePropertyMasterEvent;

    @Column(name = "PROP_EVENT")
    private Long propEvent;

    @Column(name = "PROP_ANLLOW")
    private String propAllowFlag;

    @Column(name = "PROP_EV_STATUS")
    private String eventStatus;

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

    public Long getPropEventId() {
        return propEventId;
    }

    public void setPropEventId(Long propEventId) {
        this.propEventId = propEventId;
    }

    public EstatePropertyEntity getEstatePropertyMasterEvent() {
        return estatePropertyMasterEvent;
    }

    public void setEstatePropertyMasterEvent(EstatePropertyEntity estatePropertyMasterEvent) {
        this.estatePropertyMasterEvent = estatePropertyMasterEvent;
    }

    public Long getPropEvent() {
        return propEvent;
    }

    public void setPropEvent(Long propEvent) {
        this.propEvent = propEvent;
    }

    public String getPropAllowFlag() {
        return propAllowFlag;
    }

    public void setPropAllowFlag(String propAllowFlag) {
        this.propAllowFlag = propAllowFlag;
    }

    public String getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(String eventStatus) {
        this.eventStatus = eventStatus;
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
        return new String[] { "RL", "TB_RL_PROPTY_EVENT", "PROP_EVID" };
    }

}
