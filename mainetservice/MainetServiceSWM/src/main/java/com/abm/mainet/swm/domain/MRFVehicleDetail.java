package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_MRF_VECHICLE_DET")
public class MRFVehicleDetail implements Serializable {
    private static final long serialVersionUID = 1000510954846121653L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MRFV_ID", unique = true, nullable = false)
    private Long mrfvId;

    @ManyToOne
    @JoinColumn(name = "MRF_ID", nullable = false)
    private MRFMaster mrfVEId;

    @Column(name = "VE_VETYPE")
    private Long veVeType;

    @Column(name = "MRFV_AVALCNT")
    private Long mrfvAvalCnt;

    @Column(name = "MRFV_REQCNT")
    private Long mrfvReqCnt;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public Long getMrfvId() {
        return mrfvId;
    }

    public void setMrfvId(Long mrfvId) {
        this.mrfvId = mrfvId;
    }

    public MRFMaster getMrfVEId() {
        return mrfVEId;
    }

    public void setMrfVEId(MRFMaster mrfVEId) {
        this.mrfVEId = mrfVEId;
    }

    public Long getVeVeType() {
        return veVeType;
    }

    public void setVeVeType(Long veVeType) {
        this.veVeType = veVeType;
    }

    public Long getMrfvAvalCnt() {
        return mrfvAvalCnt;
    }

    public void setMrfvAvalCnt(Long mrfvAvalCnt) {
        this.mrfvAvalCnt = mrfvAvalCnt;
    }

    public Long getMrfvReqCnt() {
        return mrfvReqCnt;
    }

    public void setMrfvReqCnt(Long mrfvReqCnt) {
        this.mrfvReqCnt = mrfvReqCnt;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String[] getPkValues() {
        return new String[] { "SWM", "TB_SW_MRF_VECHICLE_DET", "MRFV_ID" };
    }

}
