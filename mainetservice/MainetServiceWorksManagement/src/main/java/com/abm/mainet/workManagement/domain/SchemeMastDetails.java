package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author vishwajeet.kumar
 *
 */

@Entity
@Table(name = "TB_WMS_SCHEME_DET")
public class SchemeMastDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SCHD_ID")
    private Long schDetId;

    @Column(name = "SCHD_SPONSORED_BY")
    private String schDSpon;

    @Column(name = "SCHD_SHARING_PER")
    private BigDecimal schSharPer;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date CreatedDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE ")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SCH_ID")
    private SchemeMaster wmsSchemeMaster;

    @Column(name = "SCHD_ACTIVE")
    private String schDActive;

    public Long getSchDetId() {
        return schDetId;
    }

    public void setSchDetId(Long schDetId) {
        this.schDetId = schDetId;
    }

    public String getSchDSpon() {
        return schDSpon;
    }

    public void setSchDSpon(String schDSpon) {
        this.schDSpon = schDSpon;
    }

    public BigDecimal getSchSharPer() {
        return schSharPer;
    }

    public void setSchSharPer(BigDecimal schSharPer) {
        this.schSharPer = schSharPer;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(Date createdDate) {
        CreatedDate = createdDate;
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

    public String getSchDActive() {
        return schDActive;
    }

    public void setSchDActive(String schDActive) {
        this.schDActive = schDActive;
    }

    public SchemeMaster getWmsSchemeMaster() {
        return wmsSchemeMaster;
    }

    public void setWmsSchemeMaster(SchemeMaster wmsSchemeMaster) {
        this.wmsSchemeMaster = wmsSchemeMaster;
    }

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_SCHEME_DET", "SCHD_ID" };
    }
}
