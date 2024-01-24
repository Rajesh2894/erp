package com.abm.mainet.cms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author rajdeep.sinha
 * @since 01 Jul 2014
 */
@Entity
@Table(name = "TB_EIP_ANNOUNCEMENT_LANDING")
public class EIPAnnouncementLanding extends BaseEntity {
    public long getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(final long announceId) {
        this.announceId = announceId;
    }

    public String getAnnounceDescEng() {
        return announceDescEng;
    }

    public void setAnnounceDescEng(final String announceDescEng) {
        this.announceDescEng = announceDescEng;
    }

    public String getAnnounceDescReg() {
        return announceDescReg;
    }

    public void setAnnounceDescReg(final String announceDescReg) {
        this.announceDescReg = announceDescReg;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(final String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    private static final long serialVersionUID = -2143924637941054688L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ANNOUNCE_ID", precision = 12, scale = 0, nullable = false)
    private long announceId;

    @Column(name = "ANNOUNCE_DESC_ENG", length = 2000, nullable = false)
    private String announceDescEng;

    @Column(name = "ANNOUNCE_DESC_REG", length = 2000, nullable = false)
    private String announceDescReg;

    @Column(name = "ATTACHMENT", length = 100, nullable = true)
    private String attachment;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = true)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Override
    public long getId() {

        return announceId;
    }

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_ANNOUNCEMENT_LANDING", "ANNOUNCE_ID" };
    }

}