package com.abm.mainet.swm.domain;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_MRF_EMPLOYEE_DET")
public class MRFEmployeeDetail implements Serializable {
    private static final long serialVersionUID = -2479970211332719304L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MRFE_ID", unique = true, nullable = false)
    private Long mrfEId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MRF_ID")
    private MRFMaster mrfEMPId;

    @Column(name = "DSGID")
    private Long dsgId;

    @Column(name = "MRFE_AVALCNT")
    private Long mrfeAvalCnt;

    @Column(name = "MRFE_REQCNT")
    private Long mrfeReqCnt;

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

    public Long getMrfEId() {
        return mrfEId;
    }

    public void setMrfEId(Long mrfEId) {
        this.mrfEId = mrfEId;
    }

    public MRFMaster getMrfEMPId() {
        return mrfEMPId;
    }

    public void setMrfEMPId(MRFMaster mrfEMPId) {
        this.mrfEMPId = mrfEMPId;
    }

    public Long getDsgId() {
        return dsgId;
    }

    public void setDsgId(Long dsgId) {
        this.dsgId = dsgId;
    }

    public Long getMrfeAvalCnt() {
        return mrfeAvalCnt;
    }

    public void setMrfeAvalCnt(Long mrfeAvalCnt) {
        this.mrfeAvalCnt = mrfeAvalCnt;
    }

    public Long getMrfeReqCnt() {
        return mrfeReqCnt;
    }

    public void setMrfeReqCnt(Long mrfeReqCnt) {
        this.mrfeReqCnt = mrfeReqCnt;
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
        return new String[] { "SWM", "TB_SW_MRF_EMPLOYEE_DET", "MRFE_ID" };
    }
}
