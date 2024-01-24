package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SW_VECHILELOG1")
public class VehicleLogMain implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEL1_ID")
    private Long vel1Id;

    @Column(name = "VEL1_TBEATPOPU")
    private Long beatPop;

    @Column(name = "VEL1_THOUSEEST")
    private BigDecimal noOfHouseEst;

    @Column(name = "VEL1_TANICOUNT")
    private BigDecimal animalCount;

    @Column(name = "VEL1_THOUSEESTC")
    private BigDecimal compHouse;

    @Column(name = "VEL1_MWCDRY")
    private BigDecimal wasDryColl;

    @Column(name = "VEL1_MWCWET")
    private BigDecimal wasWetColl;

    @Column(name = "VEL1_MWCHWZ")
    private BigDecimal wasHazColl;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    public VehicleLogMain() {

    }

    public Long getVel1Id() {
        return vel1Id;
    }

    public void setVel1Id(Long vel1Id) {
        this.vel1Id = vel1Id;
    }

    public Long getBeatPop() {
        return beatPop;
    }

    public void setBeatPop(Long beatPop) {
        this.beatPop = beatPop;
    }

    public BigDecimal getNoOfHouseEst() {
        return noOfHouseEst;
    }

    public void setNoOfHouseEst(BigDecimal noOfHouseEst) {
        this.noOfHouseEst = noOfHouseEst;
    }

    public BigDecimal getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(BigDecimal animalCount) {
        this.animalCount = animalCount;
    }

    public BigDecimal getCompHouse() {
        return compHouse;
    }

    public void setCompHouse(BigDecimal compHouse) {
        this.compHouse = compHouse;
    }

    public BigDecimal getWasDryColl() {
        return wasDryColl;
    }

    public void setWasDryColl(BigDecimal wasDryColl) {
        this.wasDryColl = wasDryColl;
    }

    public BigDecimal getWasWetColl() {
        return wasWetColl;
    }

    public void setWasWetColl(BigDecimal wasWetColl) {
        this.wasWetColl = wasWetColl;
    }

    public BigDecimal getWasHazColl() {
        return wasHazColl;
    }

    public void setWasHazColl(BigDecimal wasHazColl) {
        this.wasHazColl = wasHazColl;
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
        return new String[] { "SWM", "TB_SW_VECHILELOG1", "VEL1_ID" };
    }

}
