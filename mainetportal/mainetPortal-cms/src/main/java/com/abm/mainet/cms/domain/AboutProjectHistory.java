
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
 * @author vishwajeet.kumar
 *
 */
@Entity
@Table(name = "TB_EIP_PROJECTINFO_HIST")
public class AboutProjectHistory extends BaseEntity {

    private static final long serialVersionUID = -5516315930504629000L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "INFO_ID_H", nullable = false)
    private long abProId;

    @Column(name = "INFO_ID", nullable = false)
    private long id;

    @Column(name = "TTL_DESC_EN", nullable = false)
    private String descTitleEng;

    @Column(name = "TTL_DESC_REG", nullable = false)
    private String descTitleReg;

    @Column(name = "INFO_DESC_EN")
    private String descInfoEng;

    @Column(name = "INFO_DESC_REG")
    private String descInfoReg;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "UPDATED_BY", nullable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "ISDELETED", nullable = false)
    private String isDeleted;

    @Column(name = "CHEKER_FLAG", length = 1, nullable = false)
    private String chekkerflag;

    @Column(name = "H_STATUS", length = 1)
    private String status;

    public long getAbProId() {
        return abProId;
    }

    public void setAbProId(long abProId) {
        this.abProId = abProId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getChekkerflag() {
        return chekkerflag;
    }

    public void setChekkerflag(final String chekkerflag) {
        this.chekkerflag = chekkerflag;
    }

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

    public String getDescInfoEng() {
        return descInfoEng;
    }

    public void setDescInfoEng(final String descInfoEng) {
        this.descInfoEng = descInfoEng;
    }

    // ----------------Getter,Setter for Project Description in Regional Language-----------------

    public String getDescInfoReg() {
        return descInfoReg;
    }

    public void setDescInfoReg(final String descInfoReg) {
        this.descInfoReg = descInfoReg;
    }

    @Override

    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation organisation) {
        orgId = organisation;
    }

    @Override

    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee employee) {
        userId = employee;
    }

    @Override

    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int i) {
        langId = i;
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

    @Override

    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
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
    @Transient
    public String[] getPkValues() {

        return new String[] { "ONL", "TB_EIP_PROJECTINFO_HIST", "INFO_ID_H" };
    }

}