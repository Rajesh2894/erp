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
 * The persistent class for the tb_sw_root_det_hist database table.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 07-May-2018
 */
@Entity
@Table(name = "TB_SW_ROUTE_DET_HIST")
public class RouteDetailsHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ROD_ID_H")
    private Long rodIdH;

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

    @Column(name = "RO_REFID")
    private String referenceNo;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "RO_COLL_LATITUDE")
    private String roCollLatitude;

    @Column(name = "RO_COLL_LONGITUDE")
    private String roCollLongitude;

    @Column(name = "RO_COLL_POINTADD")
    private String roCollPointadd;

    @Column(name = "RO_COLL_POINTNAME")
    private String roCollPointname;

    @Column(name = "RO_COLL_TYPE")
    private Long roCollType;

    @Column(name = "RO_ID")
    private Long roId;

    @Column(name = "RO_SEQ_NO")
    private Long roSeqNo;

    @Column(name = "ROD_ID")
    private Long rodId;

    @Column(name = "RO_ASSUM_QUANTITY")
    private Long roAssumQuantity;

    @Column(name = "RO_COLL_ACTIVE", length = 1)
    private String roCollActive;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    public RouteDetailsHistory() {
    }

    public Long getRodIdH() {
        return this.rodIdH;
    }

    public void setRodIdH(Long rodIdH) {
        this.rodIdH = rodIdH;
    }

    public Long getCodWard1() {
        return this.codWard1;
    }

    public void setCodWard1(Long codWard1) {
        this.codWard1 = codWard1;
    }

    public Long getCodWard2() {
        return this.codWard2;
    }

    public void setCodWard2(Long codWard2) {
        this.codWard2 = codWard2;
    }

    public Long getCodWard3() {
        return this.codWard3;
    }

    public void setCodWard3(Long codWard3) {
        this.codWard3 = codWard3;
    }

    public Long getCodWard4() {
        return this.codWard4;
    }

    public void setCodWard4(Long codWard4) {
        this.codWard4 = codWard4;
    }

    public Long getCodWard5() {
        return this.codWard5;
    }

    public void setCodWard5(Long codWard5) {
        this.codWard5 = codWard5;
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

    public String getHStatus() {
        return this.hStatus;
    }

    public void setHStatus(String hStatus) {
        this.hStatus = hStatus;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
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

    public String getRoCollLatitude() {
        return this.roCollLatitude;
    }

    public void setRoCollLatitude(String roCollLatitude) {
        this.roCollLatitude = roCollLatitude;
    }

    public String getRoCollLongitude() {
        return this.roCollLongitude;
    }

    public void setRoCollLongitude(String roCollLongitude) {
        this.roCollLongitude = roCollLongitude;
    }

    public String getRoCollPointadd() {
        return this.roCollPointadd;
    }

    public void setRoCollPointadd(String roCollPointadd) {
        this.roCollPointadd = roCollPointadd;
    }

    public String getRoCollPointname() {
        return this.roCollPointname;
    }

    public void setRoCollPointname(String roCollPointname) {
        this.roCollPointname = roCollPointname;
    }

    public Long getRoCollType() {
        return this.roCollType;
    }

    public void setRoCollType(Long roCollType) {
        this.roCollType = roCollType;
    }

    public Long getRoId() {
        return this.roId;
    }

    public void setRoId(Long roId) {
        this.roId = roId;
    }

    public Long getRoSeqNo() {
        return this.roSeqNo;
    }

    public void setRoSeqNo(Long roSeqNo) {
        this.roSeqNo = roSeqNo;
    }

    public Long getRodId() {
        return this.rodId;
    }

    public void setRodId(Long rodId) {
        this.rodId = rodId;
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

    public Long getRoAssumQuantity() {
        return roAssumQuantity;
    }

    public void setRoAssumQuantity(Long roAssumQuantity) {
        this.roAssumQuantity = roAssumQuantity;
    }

    public String getRoCollActive() {
        return roCollActive;
    }

    public void setRoCollActive(String roCollActive) {
        this.roCollActive = roCollActive;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_ROUTE_DET_HIST", "ROD_ID_H" };
    }

}