package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_WORKDEFINATION_WARDZONE_DET")
public class WorkDefinationWardZoneDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5182519088736178336L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "WARDZONE_ID", nullable = false)
    private Long wardZoneId;

    @Column(name = "COD_ID1", nullable = true)
    private Long codId1;

    @Column(name = "COD_ID2", nullable = true)
    private Long codId2;

    @Column(name = "COD_ID3", nullable = true)
    private Long codId3;

    @Column(name = "COD_ID4", nullable = true)
    private Long codId4;

    @Column(name = "COD_ID5", nullable = true)
    private Long codId5;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_ID", nullable = false)
    private WorkDefinationEntity workDefEntity;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    public Long getWardZoneId() {
        return wardZoneId;
    }

    public void setWardZoneId(Long wardZoneId) {
        this.wardZoneId = wardZoneId;
    }

    public Long getCodId1() {
        return codId1;
    }

    public void setCodId1(Long codId1) {
        this.codId1 = codId1;
    }

    public Long getCodId2() {
        return codId2;
    }

    public void setCodId2(Long codId2) {
        this.codId2 = codId2;
    }

    public Long getCodId3() {
        return codId3;
    }

    public void setCodId3(Long codId3) {
        this.codId3 = codId3;
    }

    public Long getCodId4() {
        return codId4;
    }

    public void setCodId4(Long codId4) {
        this.codId4 = codId4;
    }

    public Long getCodId5() {
        return codId5;
    }

    public void setCodId5(Long codId5) {
        this.codId5 = codId5;
    }

    public WorkDefinationEntity getWorkDefEntity() {
        return workDefEntity;
    }

    public void setWorkDefEntity(WorkDefinationEntity workDefEntity) {
        this.workDefEntity = workDefEntity;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_WORKDEFINATION_WARDZONE_DET", "WARDZONE_ID" };
    }

}
