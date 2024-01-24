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
 * The persistent class for the tb_sw_contvend_mapping_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 29-May-2018
 */
@Entity
@Table(name = "TB_SW_CONTVEND_MAPPING_HIST")
public class VendorContractMappingHistory implements Serializable {

    private static final long serialVersionUID = -6165842880528306008L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MAP_ID_H", unique = true, nullable = false)
    private Long mapIdH;

    @Column(name = "H_STATUS", length = 1)
    private String hStatus;

    private Long mapId;

    @Column(name = "COD_WARD1")
    private Long codWard1;

    @Column(name = "COD_WARD2")
    private Long codWard2;

    @Column(name = "COD_WARD3")
    private Long codWard3;

    @Column(name = "COD_WARD4")
    private Long codWard4;

    @Column(name = "COD_WARD5")
    private Long codWard5;

    @Column(name = "CONT_ID")
    private Long contId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "MAP_GARBAGE")
    private Long mapGarbage;

    @Column(name = "MAP_GARBAGE_UNIT")
    private Long mapGarbageUnit;

    @Column(name = "MAP_TASK_ID", length = 50)
    private String mapTaskId;

    @Column(name = "MAP_WASTETYPE")
    private Long mapWastetype;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public VendorContractMappingHistory() {
    }

    public Long getMapIdH() {
        return this.mapIdH;
    }

    public void setMapIdH(Long mapIdH) {
        this.mapIdH = mapIdH;
    }

    public Long getContId() {
        return this.contId;
    }

    public void setContId(Long contId) {
        this.contId = contId;
    }

    public Long getCreatedBy() {
        return this.createdBy;
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

    public Long getMapId() {
        return this.mapId;
    }

    public void setMapId(Long mapId) {
        this.mapId = mapId;
    }

    public String getMapTaskId() {
        return this.mapTaskId;
    }

    public void setMapTaskId(String mapTaskId) {
        this.mapTaskId = mapTaskId;
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

    public Long getCodWard1() {
        return codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
    }

    public Long getMapGarbage() {
        return mapGarbage;
    }

    public void setMapGarbage(Long mapGarbage) {
        this.mapGarbage = mapGarbage;
    }

    public Long getMapGarbageUnit() {
        return mapGarbageUnit;
    }

    public void setMapGarbageUnit(Long mapGarbageUnit) {
        this.mapGarbageUnit = mapGarbageUnit;
    }

    public Long getMapWastetype() {
        return mapWastetype;
    }

    public void setMapWastetype(Long mapWastetype) {
        this.mapWastetype = mapWastetype;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_CONTVEND_MAPPING_HIST", "MAP_ID_H" };
    }
}