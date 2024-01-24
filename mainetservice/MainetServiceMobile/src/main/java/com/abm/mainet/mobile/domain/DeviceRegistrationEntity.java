package com.abm.mainet.mobile.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author umashanker.kanaujiya
 *
 */
@Entity
@Table(name = "TB_MOB_APP_DEVICE_REG")
public class DeviceRegistrationEntity implements Serializable {

    private static final long serialVersionUID = 2269053168596764908L;

    @Id
    @GenericGenerator(name = "generator", strategy = "increment")
    @GeneratedValue(generator = "generator")
    @Column(name = "sr_id")
    private Long Id;

    @Column(name = "dev_id", insertable = true, length = 50, nullable = false, unique = true)
    private String devId;

    @Column(name = "reg_id", insertable = true, length = 50, nullable = false, unique = true)
    private String regId;

    @Column(name = "user_id", insertable = true, length = 50, nullable = false, unique = true)
    private Long userId;

    @Column(name = "org_id", nullable = false)
    private Long orgId;

    @Column(name = "LANG_ID", nullable = false)
    private Long langid;

    @Column(name = "reg_date", nullable = false)
    private Date regDate;

    @Column(name = "lg_ip_mac")
    private String lgipmac;

    @Column(name = "active_flag")
    private char activeFlag;

    @Column(name = "lmoddate", nullable = false)
    private Date lmoddate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "updated_by", unique = true)
    private Long updatedBy;

    /**
     * @return the id
     */
    public Long getId() {
        return Id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        Id = id;
    }

    /**
     * @return the devId
     */
    public String getDevId() {
        return devId;
    }

    /**
     * @param devId the devId to set
     */
    public void setDevId(final String devId) {
        this.devId = devId;
    }

    /**
     * @return the regId
     */
    public String getRegId() {
        return regId;
    }

    /**
     * @param regId the regId to set
     */
    public void setRegId(final String regId) {
        this.regId = regId;
    }

    /**
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the langid
     */
    public Long getLangid() {
        return langid;
    }

    /**
     * @param langid the langid to set
     */
    public void setLangid(final Long langid) {
        this.langid = langid;
    }

    /**
     * @return the lgipmac
     */
    public String getLgipmac() {
        return lgipmac;
    }

    /**
     * @param lgipmac the lgipmac to set
     */
    public void setLgipmac(final String lgipmac) {
        this.lgipmac = lgipmac;
    }

    /**
     * @return the lmoddate
     */
    public Date getLmoddate() {
        return lmoddate;
    }

    /**
     * @param lmoddate the lmoddate to set
     */
    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the regDate
     */
    public Date getRegDate() {
        return regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(final Date regDate) {
        this.regDate = regDate;
    }

    /**
     * @return the activeFlag
     */
    public char getActiveFlag() {
        return activeFlag;
    }

    /**
     * @param activeFlag the activeFlag to set
     */
    public void setActiveFlag(final char activeFlag) {
        this.activeFlag = activeFlag;
    }

}
