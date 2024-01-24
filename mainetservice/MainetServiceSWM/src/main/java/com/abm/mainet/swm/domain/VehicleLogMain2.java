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
@Table(name = "TB_SW_VECHILELOG2")
public class VehicleLogMain2 implements Serializable {
    private static final long serialVersionUID = -5829768580476737336L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VEL2_ID")
    private Long vel2Id;

    @Column(name = "VEL2_TRANDATE")
    private Date vel2Trandate;

    @Column(name = "VE_VETYPE")
    private Long vehType;

    @Column(name = "VE_NO")
    private String vehNo;

    @Column(name = "VEL2_ARRIVALS")
    private String vel2Arrivals;

    @Column(name = "VEL2_DEPARTURE")
    private String vel2Departure;

    @Column(name = "VEL2_G1")
    private String vel2G1;

    @Column(name = "VEL2_G2")
    private String vel2G2;

    @Column(name = "VEL2_G3")
    private String vel2G3;

    @Column(name = "VEL2_G4")
    private String vel2G4;

    @Column(name = " VEL2_G5")
    private String vel2G5;

    @Column(name = "VEL2_G6")
    private String vel2G6;

    @Column(name = "VEL2_G7")
    private char vel2G7;

    @Column(name = "VEL2_G8")
    private BigDecimal vel2G8;

    @Column(name = "VEL2_G9")
    private String vel2G9;

    @Column(name = "VEL2_G10")
    private String vel2G10;

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

    public Long getVel2Id() {
        return vel2Id;
    }

    public void setVel2Id(Long vel2Id) {
        this.vel2Id = vel2Id;
    }

    public Date getVel2Trandate() {
        return vel2Trandate;
    }

    public void setVel2Trandate(Date vel2Trandate) {
        this.vel2Trandate = vel2Trandate;
    }

    public Long getVehType() {
        return vehType;
    }

    public void setVehType(Long vehType) {
        this.vehType = vehType;
    }

    public String getVehNo() {
        return vehNo;
    }

    public void setVehNo(String vehNo) {
        this.vehNo = vehNo;
    }

    public String getVel2Arrivals() {
        return vel2Arrivals;
    }

    public void setVel2Arrivals(String vel2Arrivals) {
        this.vel2Arrivals = vel2Arrivals;
    }

    public String getVel2Departure() {
        return vel2Departure;
    }

    public void setVel2Departure(String vel2Departure) {
        this.vel2Departure = vel2Departure;
    }

    public String getVel2G1() {
        return vel2G1;
    }

    public void setVel2G1(String vel2g1) {
        vel2G1 = vel2g1;
    }

    public String getVel2G2() {
        return vel2G2;
    }

    public void setVel2G2(String vel2g2) {
        vel2G2 = vel2g2;
    }

    public String getVel2G3() {
        return vel2G3;
    }

    public void setVel2G3(String vel2g3) {
        vel2G3 = vel2g3;
    }

    public String getVel2G4() {
        return vel2G4;
    }

    public void setVel2G4(String vel2g4) {
        vel2G4 = vel2g4;
    }

    public String getVel2G5() {
        return vel2G5;
    }

    public void setVel2G5(String vel2g5) {
        vel2G5 = vel2g5;
    }

    public String getVel2G6() {
        return vel2G6;
    }

    public void setVel2G6(String vel2g6) {
        vel2G6 = vel2g6;
    }

    public char getVel2G7() {
        return vel2G7;
    }

    public void setVel2G7(char vel2g7) {
        vel2G7 = vel2g7;
    }

    public BigDecimal getVel2G8() {
        return vel2G8;
    }

    public void setVel2G8(BigDecimal vel2g8) {
        vel2G8 = vel2g8;
    }

    public String getVel2G9() {
        return vel2G9;
    }

    public void setVel2G9(String vel2g9) {
        vel2G9 = vel2g9;
    }

    public String getVel2G10() {
        return vel2G10;
    }

    public void setVel2G10(String vel2g10) {
        vel2G10 = vel2g10;
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
        return new String[] { "SWM", "TB_SW_VECHILELOG2", "VEL2_ID" };
    }
}
