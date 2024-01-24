package com.abm.mainet.water.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Jeetendra.Pal
 * @since 19 Jan 2016
 * @comment This table is used to store link between the old CCN CCN.
 */
@Entity
@Table(name = "TB_WT_OLD_NEW_CCN_LINK")
public class TbKLinkCcn implements Serializable {
    private static final long serialVersionUID = 5217074219062110984L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "LC_ID", precision = 12, scale = 0, nullable = false)
    private long lcId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CS_IDN", nullable = false)
    private TbKCsmrInfoMH csIdn;

    @Column(name = "LC_OLDCCN", length = 30, nullable = true)
    private String lcOldccn;

    @Column(name = "LC_OLDCCNSIZE", precision = 10, scale = 0, nullable = true)
    private Long lcOldccnsize;

    @Column(name = "LC_OLDTAPS", precision = 3, scale = 0, nullable = true)
    private Long lcOldtaps;

    @Column(name = "ORGID", nullable = false, updatable = false)
    private long orgIds;

    @Column(name = "USER_ID", nullable = false, updatable = false)
    private long userIds;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    public long getLcId() {
        return lcId;
    }

    public void setLcId(final long lcId) {
        this.lcId = lcId;
    }

    public String getLcOldccn() {
        return lcOldccn;
    }

    public void setLcOldccn(final String lcOldccn) {
        this.lcOldccn = lcOldccn;
    }

    public Long getLcOldccnsize() {
        return lcOldccnsize;
    }

    public void setLcOldccnsize(final Long lcOldccnsize) {
        this.lcOldccnsize = lcOldccnsize;
    }

    public Long getLcOldtaps() {
        return lcOldtaps;
    }

    public void setLcOldtaps(final Long lcOldtaps) {
        this.lcOldtaps = lcOldtaps;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Employee updatedBy) {
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

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public long getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(final long orgIds) {
        this.orgIds = orgIds;
    }

    public long getUserIds() {
        return userIds;
    }

    public void setUserIds(final long userIds) {
        this.userIds = userIds;
    }

    public String[] getPkValues() {
        return new String[] { "WT", "TB_WT_OLD_NEW_CCN_LINK", "LC_ID" };
    }

    public TbKCsmrInfoMH getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final TbKCsmrInfoMH csIdn) {
        this.csIdn = csIdn;
    }

    /**
     * @return the isDeleted
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

}