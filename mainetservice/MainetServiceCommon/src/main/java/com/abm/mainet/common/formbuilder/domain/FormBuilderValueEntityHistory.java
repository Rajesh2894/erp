package com.abm.mainet.common.formbuilder.domain;

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
 * The persistent class for the tb_scrutiny_values_hist database table.
 * 
 */
@Entity
@Table(name = "TB_FORMBUILDER_VALUES_HIST")
public class FormBuilderValueEntityHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "FORM_VID_H")
    private Long hSllableid;
    
    @Column(name = "FORM_ID", nullable = false)
    private Long formId;

    @Column(name = "FORM_VID")
    private Long saApplicationId;

    @Column(name = "FORM_LEVELS")
    private Long levels;

    @Column(name = "FORM_LABEL_ID")
    private Long slLabelId;

    @Column(name = "FORM_VALUE")
    private String svValue;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "ORGID")
    private Long orgid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date lmodDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "CREATED_BY")
    private Long userId;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public FormBuilderValueEntityHistory() {
    }

    public Long getSaApplicationId() {
        return saApplicationId;
    }

    public void setSaApplicationId(Long saApplicationId) {
        this.saApplicationId = saApplicationId;
    }

    public Long gethSllableid() {
        return hSllableid;
    }

    public void sethSllableid(Long hSllableid) {
        this.hSllableid = hSllableid;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public Long getLevels() {
        return this.levels;
    }

    public void setLevels(Long levels) {
        this.levels = levels;
    }

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getSlLabelId() {
        return this.slLabelId;
    }

    public void setSlLabelId(Long slLabelId) {
        this.slLabelId = slLabelId;
    }

    public String getSvValue() {
        return this.svValue;
    }

    public void setSvValue(String svValue) {
        this.svValue = svValue;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getUserId() {
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        return new String[] { "COM", "TB_FORMBUILDER_VALUES_HIST", "FORM_VID_H" };
    }
}