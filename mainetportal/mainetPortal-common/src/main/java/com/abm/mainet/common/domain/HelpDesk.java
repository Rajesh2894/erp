package com.abm.mainet.common.domain;

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

@Entity
@Table(name = "TB_HELPDESK_MAS")
public class HelpDesk implements Serializable {

    private static final long serialVersionUID = 8400242269547625384L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "HELP_ID")
    private Long helpId;

    @Column(name = "HELP_EMPID")
    private Long helpEmpid;

    @Temporal(TemporalType.DATE)
    @Column(name = "HELP_ENDDT")
    private Date helpEnddt;

    @Column(name = "HELP_ESTTIME")
    private String helpEsttime;

    @Column(name = "HELP_NAME")
    private String helpName;

    @Column(name = "HELP_NOTE")
    private String helpNote;

    @Column(name = "HELP_PID")
    private Long helpPid;

    @Column(name = "HELP_PRIORITY")
    private Long helpPriority;

    @Temporal(TemporalType.DATE)
    @Column(name = "HELP_STARTDT")
    private Date helpStartdt;

    @Column(name = "HELP_STATUS")
    private Long helpStatus;

    @Column(name = "HELP_TYPE")
    private Long helpType;

    @Column(name = "HELP_WATCHER")
    private String helpWatcher;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public Long getHelpId() {
        return helpId;
    }

    public void setHelpId(Long helpId) {
        this.helpId = helpId;
    }

    public Long getHelpEmpid() {
        return helpEmpid;
    }

    public void setHelpEmpid(Long helpEmpid) {
        this.helpEmpid = helpEmpid;
    }

    public Date getHelpEnddt() {
        return helpEnddt;
    }

    public void setHelpEnddt(Date helpEnddt) {
        this.helpEnddt = helpEnddt;
    }

    public String getHelpEsttime() {
        return helpEsttime;
    }

    public void setHelpEsttime(String helpEsttime) {
        this.helpEsttime = helpEsttime;
    }

    public String getHelpName() {
        return helpName;
    }

    public void setHelpName(String helpName) {
        this.helpName = helpName;
    }

    public String getHelpNote() {
        return helpNote;
    }

    public void setHelpNote(String helpNote) {
        this.helpNote = helpNote;
    }

    public Long getHelpPid() {
        return helpPid;
    }

    public void setHelpPid(Long helpPid) {
        this.helpPid = helpPid;
    }

    public Long getHelpPriority() {
        return helpPriority;
    }

    public void setHelpPriority(Long helpPriority) {
        this.helpPriority = helpPriority;
    }

    public Date getHelpStartdt() {
        return helpStartdt;
    }

    public void setHelpStartdt(Date helpStartdt) {
        this.helpStartdt = helpStartdt;
    }

    public Long getHelpStatus() {
        return helpStatus;
    }

    public void setHelpStatus(Long helpStatus) {
        this.helpStatus = helpStatus;
    }

    public Long getHelpType() {
        return helpType;
    }

    public void setHelpType(Long helpType) {
        this.helpType = helpType;
    }

    public String getHelpWatcher() {
        return helpWatcher;
    }

    public void setHelpWatcher(String helpWatcher) {
        this.helpWatcher = helpWatcher;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public String[] getPkValues() {
        return new String[] { "AUT", "TB_HELPDESK_MAS", "HELP_ID" };
    }

}
