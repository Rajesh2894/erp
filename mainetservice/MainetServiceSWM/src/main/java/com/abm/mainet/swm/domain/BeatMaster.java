package com.abm.mainet.swm.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the tb_sw_root_mast database table.
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
@Entity
@Table(name = "TB_SW_BEAT_MAST")
public class BeatMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BEAT_ID")
    private Long beatId;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "BEAT_DISTANCE")
    private BigDecimal beatDistance;

    @Column(name = "BEAT_DISTANCE_UNIT")
    private Long beatDistanceUnit;

    @Column(name = "BEAT_END_POINT")
    private Long beatEndPoint;

    @Column(name = "BEAT_NAME")
    private String beatName;

    @Column(name = "BEAT_NAME_REG")
    private String beatNameReg;

    @Column(name = "BEAT_NO")
    private String beatNo;

    @Column(name = "BEAT_START_POINT")
    private Long beatStartPoint;

    @Column(name = "BEAT_VE_TYPE")
    private Long beatVeType;

    @Column(name = "BEAT_Active")
    private String beatActive;

    @Column(name = "MRF_ID", nullable = false)
    private Long mrfId;

    @Column(name = "BEAT_DIST_DES", nullable = false, precision = 10, scale = 2)
    private BigDecimal beatDistDes;

    @Column(name = "BEAT_AREATYPE")
    private Long areaType;

    @Column(name = "BEAT_ASSUME_WETQTY")
    private Long wetAssQty;

    @Column(name = "BEAT_ASSUME_DRYQTY")
    private Long dryAssQty;

    @Column(name = "BEAT_ASSUME_OTHERQTY")
    private Long hazAssQty;

    @Column(name = "BEAT_COLL_UNITCNT")
    private Long collCount;
    
    @Column(name = "BEAT_POPULATION")
    private Long beatPopulation;
    
    @Column(name = "BEAT_RESIDENTIAL")
    private Long beatResCount;
    
    @Column(name = "BEAT_COMMERTIAL")
    private Long beatComCount;
    
    @Column(name = "BEAT_INDUSTRIAL")
    private Long beatIndCount;
    
    @Column(name = "BEAT_ANIMAL_CNT")
    private Long animalCount;
    
    @Column(name = "BEAT_ESTDECOM")
    private Long decompHouse;

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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    
    @OneToMany(mappedBy = "tbSwBeatMaster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BeatDetail> tbSwBeatDetail;
    
    public List<BeatDetail> getTbSwBeatDetail() {
        return tbSwBeatDetail;
    }

    public void setTbSwBeatDetail(List<BeatDetail> tbSwBeatDetail) {
        this.tbSwBeatDetail = tbSwBeatDetail;
    }

    public Long getBeatId() {
        return beatId;
    }

    public void setBeatId(Long beatId) {
        this.beatId = beatId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public BigDecimal getBeatDistance() {
        return beatDistance;
    }

    public void setBeatDistance(BigDecimal beatDistance) {
        this.beatDistance = beatDistance;
    }

    public Long getBeatDistanceUnit() {
        return beatDistanceUnit;
    }

    public void setBeatDistanceUnit(Long beatDistanceUnit) {
        this.beatDistanceUnit = beatDistanceUnit;
    }

    public Long getBeatEndPoint() {
        return beatEndPoint;
    }

    public void setBeatEndPoint(Long beatEndPoint) {
        this.beatEndPoint = beatEndPoint;
    }

    public String getBeatName() {
        return beatName;
    }

    public void setBeatName(String beatName) {
        this.beatName = beatName;
    }

    public String getBeatNameReg() {
        return beatNameReg;
    }

    public void setBeatNameReg(String beatNameReg) {
        this.beatNameReg = beatNameReg;
    }

    public String getBeatNo() {
        return beatNo;
    }

    public void setBeatNo(String beatNo) {
        this.beatNo = beatNo;
    }

    public Long getBeatStartPoint() {
        return beatStartPoint;
    }

    public void setBeatStartPoint(Long beatStartPoint) {
        this.beatStartPoint = beatStartPoint;
    }

    public Long getBeatVeType() {
        return beatVeType;
    }

    public void setBeatVeType(Long beatVeType) {
        this.beatVeType = beatVeType;
    }

    public String getBeatActive() {
        return beatActive;
    }

    public void setBeatActive(String beatActive) {
        this.beatActive = beatActive;
    }

    public BigDecimal getBeatDistDes() {
        return beatDistDes;
    }

    public void setBeatDistDes(BigDecimal beatDistDes) {
        this.beatDistDes = beatDistDes;
    }

    public Long getAreaType() {
        return areaType;
    }

    public void setAreaType(Long areaType) {
        this.areaType = areaType;
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

    public Long getWetAssQty() {
        return wetAssQty;
    }

    public void setWetAssQty(Long wetAssQty) {
        this.wetAssQty = wetAssQty;
    }

    public Long getDryAssQty() {
        return dryAssQty;
    }

    public void setDryAssQty(Long dryAssQty) {
        this.dryAssQty = dryAssQty;
    }

    public Long getHazAssQty() {
        return hazAssQty;
    }

    public void setHazAssQty(Long hazAssQty) {
        this.hazAssQty = hazAssQty;
    }

    public Long getCollCount() {
        return collCount;
    }

    public void setCollCount(Long collCount) {
        this.collCount = collCount;
    }

    public Long getMrfId() {
        return mrfId;
    }

    public void setMrfId(Long mrfId) {
        this.mrfId = mrfId;
    }

    
    public Long getBeatPopulation() {
        return beatPopulation;
    }

    public void setBeatPopulation(Long beatPopulation) {
        this.beatPopulation = beatPopulation;
    }

    public Long getBeatResCount() {
        return beatResCount;
    }

    public void setBeatResCount(Long beatResCount) {
        this.beatResCount = beatResCount;
    }

    public Long getBeatComCount() {
        return beatComCount;
    }

    public void setBeatComCount(Long beatComCount) {
        this.beatComCount = beatComCount;
    }

    public Long getBeatIndCount() {
        return beatIndCount;
    }

    public void setBeatIndCount(Long beatIndCount) {
        this.beatIndCount = beatIndCount;
    }
    
    
    public Long getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(Long animalCount) {
        this.animalCount = animalCount;
    }

    public Long getDecompHouse() {
        return decompHouse;
    }

    public void setDecompHouse(Long decompHouse) {
        this.decompHouse = decompHouse;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_BEAT_MAST", "BEAT_ID" };
    }

}