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
 * The persistent class for the tb_sw_population_mast database table.
 *
 */
@Entity
@Table(name = "TB_SW_POPULATION_MAST")
public class PopulationMaster implements Serializable {

    private static final long serialVersionUID = -8134091008333610161L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "POP_ID")
    private Long popId;

    @Column(name = "COD_DWZID1")
    private Long codDwzid1;

    @Column(name = "COD_DWZID2")
    private Long codDwzid2;

    @Column(name = "COD_DWZID3")
    private Long codDwzid3;

    @Column(name = "COD_DWZID4")
    private Long codDwzid4;

    @Column(name = "COD_DWZID5")
    private Long codDwzid5;

    private Long orgid;

    @Column(name = "POP_EST")
    private Long popEst;

    @Column(name = "POP_YEAR")
    private Long popYear;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "POP_ACTIVE")
    private String popActive;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public PopulationMaster() {
    }

    public Long getPopId() {
        return this.popId;
    }

    public void setPopId(Long popId) {
        this.popId = popId;
    }

    public Long getCodDwzid1() {
        return this.codDwzid1;
    }

    public void setCodDwzid1(Long codDwzid1) {
        this.codDwzid1 = codDwzid1;
    }

    public Long getCodDwzid2() {
        return this.codDwzid2;
    }

    public void setCodDwzid2(Long codDwzid2) {
        this.codDwzid2 = codDwzid2;
    }

    public Long getCodDwzid3() {
        return this.codDwzid3;
    }

    public void setCodDwzid3(Long codDwzid3) {
        this.codDwzid3 = codDwzid3;
    }

    public Long getCodDwzid4() {
        return this.codDwzid4;
    }

    public void setCodDwzid4(Long codDwzid4) {
        this.codDwzid4 = codDwzid4;
    }

    public Long getCodDwzid5() {
        return this.codDwzid5;
    }

    public void setCodDwzid5(Long codDwzid5) {
        this.codDwzid5 = codDwzid5;
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

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public String getPopActive() {
        return popActive;
    }

    public void setPopActive(String popActive) {
        this.popActive = popActive;
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

    public Long getPopEst() {
        return this.popEst;
    }

    public void setPopEst(Long popEst) {
        this.popEst = popEst;
    }

    public Long getPopYear() {
        return this.popYear;
    }

    public void setPopYear(Long popYear) {
        this.popYear = popYear;
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

        return new String[] { "SWM", "TB_SW_POPULATION_MAST", "POP_ID" };
    }

}