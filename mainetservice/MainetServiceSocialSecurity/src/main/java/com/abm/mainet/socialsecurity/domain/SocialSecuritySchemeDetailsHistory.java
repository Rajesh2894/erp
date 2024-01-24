/**
 * 
 */
package com.abm.mainet.socialsecurity.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author satish.rathore
 *
 */
@Entity
@Table(name = "TB_SWD_SCHEME_DET_HIST")
public class SocialSecuritySchemeDetailsHistory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1109860985038930231L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SDSCHD_HIST_ID", nullable = false)
    private Long schemeDtlHisId;

    @Column(name = "SDSCHD_ID", nullable = false)
    private Long schemeDtlId;

    @Column(name = "SDSCH_ID", nullable = false)
    private Long schemeMstId;

    @Column(name = "SDSCH_SPONSORED_BY", nullable = false)
    private String sponserBy;

    @Column(name = "SDSCH_SHARING_PER", nullable = false)
    private BigDecimal sharingAmt;

    @Column(name = "SDSCH_ACTIVE", nullable = false)
    private String isschemeDetActive;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    public Long getSchemeDtlHisId() {
        return schemeDtlHisId;
    }

    public void setSchemeDtlHisId(Long schemeDtlHisId) {
        this.schemeDtlHisId = schemeDtlHisId;
    }

    public Long getSchemeDtlId() {
        return schemeDtlId;
    }

    public void setSchemeDtlId(Long schemeDtlId) {
        this.schemeDtlId = schemeDtlId;
    }

    public Long getSchemeMstId() {
        return schemeMstId;
    }

    public void setSchemeMstId(Long schemeMstId) {
        this.schemeMstId = schemeMstId;
    }

    public String getSponserBy() {
        return sponserBy;
    }

    public void setSponserBy(String sponserBy) {
        this.sponserBy = sponserBy;
    }

    public BigDecimal getSharingAmt() {
        return sharingAmt;
    }

    public void setSharingAmt(BigDecimal sharingAmt) {
        this.sharingAmt = sharingAmt;
    }

    public String getIsschemeDetActive() {
        return isschemeDetActive;
    }

    public void setIsschemeDetActive(String isschemeDetActive) {
        this.isschemeDetActive = isschemeDetActive;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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
        return new String[] { "SWD", "TB_SWD_SCHEME_DET_HIST", "SDSCHD_HIST_ID" };
    }

}
