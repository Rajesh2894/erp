package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Anwarul.Hassan
 * @since 25-Oct-2019
 */
@Entity
@Table(name = "TB_ADH_INESPDET")
public class InspectionEntryDetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INES_DETID")
    private Long inesDetId;

    // @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INES_ID")
    private InspectionEntryEntity inspectionEntryEntity;

    @Column(name = "REMK_ID")
    private Long remarkId;

    @Column(name = "REMK_STATID")
    private Long remarkStatusId;

    @Column(name = "OBSERVATION")
    private String observation;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getInesDetId() {
        return inesDetId;
    }

    public void setInesDetId(Long inesDetId) {
        this.inesDetId = inesDetId;
    }

    public InspectionEntryEntity getInspectionEntryEntity() {
        return inspectionEntryEntity;
    }

    public void setInspectionEntryEntity(InspectionEntryEntity inspectionEntryEntity) {
        this.inspectionEntryEntity = inspectionEntryEntity;
    }

    public Long getRemarkId() {
        return remarkId;
    }

    public void setRemarkId(Long remarkId) {
        this.remarkId = remarkId;
    }

    public Long getRemarkStatusId() {
        return remarkStatusId;
    }

    public void setRemarkStatusId(Long remarkStatusId) {
        this.remarkStatusId = remarkStatusId;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
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

    public String[] getPkValues() {
        return new String[] { "ADH", "TB_ADH_INESPDET", "INES_DETID" };
    }
}
