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

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the tb_sw_tripsheet database table.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 09-Jun-2018
 */
@Entity
@Table(name = "TB_SW_TRIPSHEET")
public class TripSheet implements Serializable {

    private static final long serialVersionUID = 215975203058966769L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TRIP_ID", unique = true, nullable = false)
    private Long tripId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    private Long orgid;

    @Column(name = "TRIP_DRIVERNAME", length = 100)
    private String tripDrivername;

    @Column(name = "TRIP_ENTWEIGHT")
    private BigDecimal tripEntweight;

    @Column(name = "TRIP_EXITWEIGHT")
    private BigDecimal tripExitweight;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRIP_INTIME")
    private Date tripIntime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRIP_OUTTIME")
    private Date tripOuttime;

    @Column(name = "TRIP_TOTALGARBAGE")
    private BigDecimal tripTotalgarbage;

    @Column(name = "TRIP_WESLIPNO", length = 50)
    private String tripWeslipno;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "VE_ID")
    private Long veId;

    @Column(name = "MRF_ID")
    private Long deId;

    @Column(name = "BEAT_ID")
    private Long beatNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "TRIP_DATE")
    private Date tripDate;

    @Column(name = "TRIP_DATA")
    private String tripData;

    @Column(name = "WAST_SAGRIGATED")
    private String wasteSeg;

    // bi-directional many-to-one association to TbSwTripsheetGdet
    @JsonIgnore
    @OneToMany(mappedBy = "tbSwTripsheet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TripSheetGarbageDet> tbSwTripsheetGdets;

    public TripSheet() {
    }

    public Long getTripId() {
        return this.tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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

    public Long getOrgid() {
        return this.orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getTripDrivername() {
        return this.tripDrivername;
    }

    public void setTripDrivername(String tripDrivername) {
        this.tripDrivername = tripDrivername;
    }

    public BigDecimal getTripEntweight() {
        return tripEntweight;
    }

    public void setTripEntweight(BigDecimal tripEntweight) {
        this.tripEntweight = tripEntweight;
    }

    public BigDecimal getTripExitweight() {
        return tripExitweight;
    }

    public void setTripExitweight(BigDecimal tripExitweight) {
        this.tripExitweight = tripExitweight;
    }

    public void setTripTotalgarbage(BigDecimal tripTotalgarbage) {
        this.tripTotalgarbage = tripTotalgarbage;
    }

    public Date getTripIntime() {
        return this.tripIntime;
    }

    public void setTripIntime(Date tripIntime) {
        this.tripIntime = tripIntime;
    }

    public Date getTripOuttime() {
        return this.tripOuttime;
    }

    public void setTripOuttime(Date tripOuttime) {
        this.tripOuttime = tripOuttime;
    }

    public Long getBeatNo() {
        return beatNo;
    }

    public void setBeatNo(Long beatNo) {
        this.beatNo = beatNo;
    }

    public String getTripWeslipno() {
        return this.tripWeslipno;
    }

    public void setTripWeslipno(String tripWeslipno) {
        this.tripWeslipno = tripWeslipno;
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

    public Long getVeId() {
        return this.veId;
    }

    public void setVeId(Long veId) {
        this.veId = veId;
    }

    public List<TripSheetGarbageDet> getTbSwTripsheetGdets() {
        return this.tbSwTripsheetGdets;
    }

    public void setTbSwTripsheetGdets(
            List<TripSheetGarbageDet> tbSwTripsheetGdets) {
        this.tbSwTripsheetGdets = tbSwTripsheetGdets;
    }

    public TripSheetGarbageDet addTbSwTripsheetGdet(
            TripSheetGarbageDet tbSwTripsheetGdet) {
        getTbSwTripsheetGdets().add(tbSwTripsheetGdet);
        tbSwTripsheetGdet.setTbSwTripsheet(this);

        return tbSwTripsheetGdet;
    }

    public TripSheetGarbageDet removeTbSwTripsheetGdet(
            TripSheetGarbageDet tbSwTripsheetGdet) {
        getTbSwTripsheetGdets().remove(tbSwTripsheetGdet);
        tbSwTripsheetGdet.setTbSwTripsheet(null);

        return tbSwTripsheetGdet;
    }

    public Long getDeId() {
        return deId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }

    public String getTripData() {
        return tripData;
    }

    public void setTripData(String tripData) {
        this.tripData = tripData;
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

    public String getWasteSeg() {
        return wasteSeg;
    }

    public void setWasteSeg(String wasteSeg) {
        this.wasteSeg = wasteSeg;
    }

    public BigDecimal getTripTotalgarbage() {
        return tripTotalgarbage;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_TRIPSHEET", "TRIP_ID" };
    }
}