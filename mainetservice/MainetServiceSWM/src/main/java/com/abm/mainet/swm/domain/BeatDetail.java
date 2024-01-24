/**
 * 
 */
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
 * @author cherupelli.srikanth
 *
 */
@Entity
@Table(name="TB_SW_BEAT_DET")
public class BeatDetail implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name="BEATD_ID")
    private Long beatDetId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BEAT_ID")
    private BeatMaster tbSwBeatMaster;
    
    @Column(name="BEAT_ARE_TYPE")
    private String beatAreaType;
    
    
    @Column(name="BEAT_ARE_NAME") 
    private String beatAreaName;
    
    @Column(name="BEAT_HOUSEHOLD") 
    private Long beatHouseHold;
    
    @Column(name="BEAT_SHOP")
    private Long beatShop;
    
    @Column(name="ORGID")
    private Long orgId;
    
    @Column(name="CREATED_BY")
    private Long createdBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
    private Date createdDate;
    
    @Column(name="UPDATED_BY")
    private Long updatedBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="UPDATED_DATE")
    private Date updatedDate;
    
    
    @Column(name="LG_IP_MAC")
    private String lpIpMac;
    
    @Column(name="LG_IP_MAC_UPD")
    private String lpIpMAcUpd;
    
   
   

    public Long getBeatDetId() {
        return beatDetId;
    }

    public void setBeatDetId(Long beatDetId) {
        this.beatDetId = beatDetId;
    }

    public BeatMaster getTbSwBeatMaster() {
        return tbSwBeatMaster;
    }

    public void setTbSwBeatMaster(BeatMaster tbSwBeatMaster) {
        this.tbSwBeatMaster = tbSwBeatMaster;
    }

    public String getBeatAreaType() {
        return beatAreaType;
    }

    public void setBeatAreaType(String beatAreaType) {
        this.beatAreaType = beatAreaType;
    }

    public String getBeatAreaName() {
        return beatAreaName;
    }

    public void setBeatAreaName(String beatAreaName) {
        this.beatAreaName = beatAreaName;
    }

    public Long getBeatHouseHold() {
        return beatHouseHold;
    }

    public void setBeatHouseHold(Long beatHouseHold) {
        this.beatHouseHold = beatHouseHold;
    }

    public Long getBeatShop() {
        return beatShop;
    }

    public void setBeatShop(Long beatShop) {
        this.beatShop = beatShop;
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

    public String getLpIpMac() {
        return lpIpMac;
    }

    public void setLpIpMac(String lpIpMac) {
        this.lpIpMac = lpIpMac;
    }

    public String getLpIpMAcUpd() {
        return lpIpMAcUpd;
    }

    public void setLpIpMAcUpd(String lpIpMAcUpd) {
        this.lpIpMAcUpd = lpIpMAcUpd;
    }
    
    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_BEAT_DET", "BEATD_ID" };
    }
}
