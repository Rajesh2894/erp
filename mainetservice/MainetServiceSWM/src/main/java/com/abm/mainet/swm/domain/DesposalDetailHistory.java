package com.abm.mainet.swm.domain;

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
 * @author Ajay.Kumar
 *
 */
@Entity
@Table(name = "TB_SW_DISPOSAL_DET_HIST")
public class DesposalDetailHistory implements Serializable {

    private static final long serialVersionUID = -2383751528499771812L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "DED_ID_H")
    private Long dedIdH;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "DE_ID")
    private Long deId;

    @Column(name = "DE_WEST_TYPE")
    private Long deWestType;

    @Column(name = "DED_ID")
    private Long dedId;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "DE_ACTIVE", nullable = false, length = 1)
    private String deActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public DesposalDetailHistory() {
    }

    public Long getDedIdH() {
        return this.dedIdH;
    }

    public void setDedIdH(Long dedIdH) {
        this.dedIdH = dedIdH;
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

    public Long getDeId() {
        return this.deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Long getDeWestType() {
        return this.deWestType;
    }

    public void setDeWestType(Long deWestType) {
        this.deWestType = deWestType;
    }

    public Long getDedId() {
        return this.dedId;
    }

    public void setDedId(Long dedId) {
        this.dedId = dedId;
    }

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_DESPOSAL_DET_HIST", "DED_ID_H" };
    }
}