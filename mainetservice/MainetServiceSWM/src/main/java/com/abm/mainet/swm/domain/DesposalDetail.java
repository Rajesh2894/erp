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
 * The persistent class for the tb_sw_desposal_det database table.
 * 
 */
@Entity
@Table(name = "TB_SW_DISPOSAL_DET")
public class DesposalDetail implements Serializable {

    private static final long serialVersionUID = 1892558024215086295L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DED_ID")
    private Long dedId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "DE_WEST_TYPE")
    private Long deWestType;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "DE_ACTIVE", nullable = false, length = 1)
    private String deActive;

    // bi-directional many-to-one association to TbSwDesposalMast
    @ManyToOne
    @JoinColumn(name = "DE_ID")
    private DisposalMaster tbSwDesposalMast;

    public DesposalDetail() {
    }

    public Long getDedId() {
        return this.dedId;
    }

    public void setDedId(Long dedId) {
        this.dedId = dedId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getDeWestType() {
        return this.deWestType;
    }

    public void setDeWestType(Long deWestType) {
        this.deWestType = deWestType;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public DisposalMaster getTbSwDesposalMast() {
        return this.tbSwDesposalMast;
    }

    public void setTbSwDesposalMast(DisposalMaster tbSwDesposalMast) {
        this.tbSwDesposalMast = tbSwDesposalMast;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_DISPOSAL_DET", "DED_ID" };
    }

}