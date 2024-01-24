package com.abm.mainet.cms.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author swapnil.shirke
 */
@Entity
@Table(name = "TB_EIP_NEWS")
public class News extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5622067976624511208L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "NEWS_ID", nullable = false, precision = 12, scale = 0)
    private long newsId;

    @Column(name = "DURATION", nullable = true, length = 3)
    private Long duration;
    @Column(name = "IMAGE_NAME", nullable = true, length = 50)
    private String imageName;

    @Column(name = "START_DATE", nullable = true)
    private Date startDate;
    @Column(name = "END_DATE", nullable = true)
    private Date endDate;
    @Column(name = "NEWS_DATE", nullable = true)
    private Date newsDate;

    @Column(name = "CPD_SECTION", nullable = true, length = 12)
    private Long cpdSection;

    @Column(name = "SHORT_DESC_EN", nullable = true, length = 250)
    private String shortDescEn;

    @Column(name = "SHORT_DESC_REG", nullable = true, length = 1000)
    private String shortDescReg;

    @Column(name = "LONGDESC_EN", nullable = true, length = 2000)
    private String longDescEn;

    @Column(name = "LONGDESC_REG", nullable = true, length = 4000)
    private String longDescReg;

    @Column(name = "ISDELETED", nullable = false, length = 1)
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    @ForeignKey(name = "FK_EIP_PM_ORG_ID")
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", nullable = false, precision = 12, scale = 0)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    @ForeignKey(name = "FK_CHG_UPD_EMPID")
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true, length = 100)
    private String lgIpMacUpd;

    /**
     * @return the newsId
     */
    public long getNewsId() {
        return newsId;
    }

    /**
     * @param newsId the newsId to set
     */
    public void setNewsId(final long newsId) {
        this.newsId = newsId;
    }

    /**
     * @return the duration
     */
    public Long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(final Long duration) {
        this.duration = duration;
    }

    /**
     * @return the imageName
     */
    public String getImageName() {
        return imageName;
    }

    /**
     * @param imageName the imageName to set
     */
    public void setImageName(final String imageName) {
        this.imageName = imageName;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the newsDate
     */
    public Date getNewsDate() {
        return newsDate;
    }

    /**
     * @param newsDate the newsDate to set
     */
    public void setNewsDate(final Date newsDate) {
        this.newsDate = newsDate;
    }

    /**
     * @return the cpdSection
     */
    public Long getCpdSection() {
        return cpdSection;
    }

    /**
     * @param cpdSection the cpdSection to set
     */
    public void setCpdSection(final Long cpdSection) {
        this.cpdSection = cpdSection;
    }

    /**
     * @return the shortDescEn
     */
    public String getShortDescEn() {
        return shortDescEn;
    }

    /**
     * @param shortDescEn the shortDescEn to set
     */
    public void setShortDescEn(final String shortDescEn) {
        this.shortDescEn = shortDescEn;
    }

    /**
     * @return the shortDescReg
     */
    public String getShortDescReg() {
        return shortDescReg;
    }

    /**
     * @param shortDescReg the shortDescReg to set
     */
    public void setShortDescReg(final String shortDescReg) {
        this.shortDescReg = shortDescReg;
    }

    /**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {

        return getNewsId();

    }

    /**
     * @return the longDescEn
     */
    public String getLongDescEn() {
        return longDescEn;
    }

    /**
     * @param longDescEn the longDescEn to set
     */
    public void setLongDescEn(final String longDescEn) {
        this.longDescEn = longDescEn;
    }

    /**
     * @return the longDescReg
     */
    public String getLongDescReg() {
        return longDescReg;
    }

    /**
     * @param longDescReg the longDescReg to set
     */
    public void setLongDescReg(final String longDescReg) {
        this.longDescReg = longDescReg;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_NEWS", "NEWS_ID" };
    }

}
