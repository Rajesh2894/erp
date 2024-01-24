package com.abm.mainet.council.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author israt.ali
 * @since 26 April 2019
 */
@Entity
@Table(name = "TB_CMT_COUNCIL_AGENDA_MAST")
public class CouncilAgendaMasterEntity implements Serializable {

    private static final long serialVersionUID = 3912374371307571350L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "AGENDA_ID")
    private Long agendaId;

    @Temporal(TemporalType.DATE)
    @Column(name = "AGENDA_DATE", nullable = false)
    private Date agendaDate;

    @Column(name = "AGENDA_NO", nullable = false)
    private String agendaNo;

    @Column(name = "COMMITTEE_TYPE_ID", nullable = false)
    private Long committeeTypeId;

    @Column(name = "AGENDA_STATUS", length = 100, nullable = false)
    private String agendaStatus;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true, updatable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
    }

    public Date getAgendaDate() {
        return agendaDate;
    }

    public void setAgendaDate(Date agendaDate) {
        this.agendaDate = agendaDate;
    }

    public String getAgendaNo() {
        return agendaNo;
    }

    public void setAgendaNo(String agendaNo) {
        this.agendaNo = agendaNo;
    }

    public Long getCommitteeTypeId() {
        return committeeTypeId;
    }

    public void setCommitteeTypeId(Long committeeTypeId) {
        this.committeeTypeId = committeeTypeId;
    }

    public String getAgendaStatus() {
        return agendaStatus;
    }

    public void setAgendaStatus(String agendaStatus) {
        this.agendaStatus = agendaStatus;
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

    public static String[] getPkValues() {
        return new String[] { "CMT", "TB_CMT_COUNCIL_AGENDA_MAST", "AGENDA_ID" };
    }

}
