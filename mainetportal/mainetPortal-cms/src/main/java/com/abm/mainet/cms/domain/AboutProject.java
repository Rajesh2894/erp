
package com.abm.mainet.cms.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Manish.Gawali
 *
 */
@Entity
@Table(name = "TB_EIP_PROJECTINFO")
public class AboutProject extends BaseEntity {

    private static final long serialVersionUID = -5516315930504629000L;

    private long id;
    private String descTitleEng;
    private String descTitleReg;
    private String descInfoEng;
    private String descInfoReg;
    private Organisation orgId;
    private Employee userId;
    private int langId;
    private Date lmodDate;
    private Employee updatedBy;
    private Date updatedDate;
    private String lgIpMac;
    private String lgIpMacUpd;
    private String isDeleted;
    private String chekkerflag;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INFO_ID", nullable = false)
    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

    @Column(name = "TTL_DESC_EN", nullable = false)
    public String getDescTitleEng() {
        if (descTitleEng != null) {
            return descTitleEng.trim();
        } else {
            return descTitleEng;
        }
    }

    public void setDescTitleEng(final String descTitleEng) {
        this.descTitleEng = descTitleEng;
    }

    // ----------------Getter,Setter for Title Description in regional language-----------------
    @Column(name = "TTL_DESC_REG", nullable = false)
    public String getDescTitleReg() {
        if (descTitleReg != null) {
            return descTitleReg.trim();
        } else {
            return descTitleReg;
        }
    }

    public void setDescTitleReg(final String descTitleReg) {
        this.descTitleReg = descTitleReg;
    }

    // ----------------Getter,Setter for Project Description in English-----------------

    @Column(name = "INFO_DESC_EN")
    public String getDescInfoEng() {
        return descInfoEng;
    }

    public void setDescInfoEng(final String descInfoEng) {
        this.descInfoEng = descInfoEng;
    }

    // ----------------Getter,Setter for Project Description in Regional Language-----------------

    @Column(name = "INFO_DESC_REG")
    public String getDescInfoReg() {
        return descInfoReg;
    }

    public void setDescInfoReg(final String descInfoReg) {
        this.descInfoReg = descInfoReg;
    }

    @Override
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation organisation) {
        orgId = organisation;
    }

    @Override
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee employee) {
        userId = employee;
    }

    @Override
    @Column(name = "LANG_ID", nullable = false)
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int i) {
        langId = i;
    }

    @Override
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)

    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    @Column(name = "UPDATED_DATE")
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    @Column(name = "LG_IP_MAC")
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    @Column(name = "LG_IP_MAC_UPD")
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    @Column(name = "ISDELETED", nullable = false)
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    @Column(name = "CREATED_DATE", nullable = false)
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    @Transient
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_PROJECTINFO", "INFO_ID" };
    }

}