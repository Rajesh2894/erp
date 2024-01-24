package com.abm.mainet.swm.domain;

import java.io.Serializable;
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
 * The persistent class for the tb_sw_wasteseg database table.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@Entity
@Table(name = "TB_SW_WASTESEG")
public class WastageSegregation implements Serializable {

    private static final long serialVersionUID = 3335108428921918905L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "GR_ID", unique = true, nullable = false)
    private Long grId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "GR_DATE", nullable = false)
    private Date grDate;

    @Column(name = "GR_TOTAL")
    private Long grTotal;

    @Column(name = "EMPID")
    private Long empId;

    @Column(name = "VE_ID")
    private Long venId;

    @Column(name = "LG_IP_MAC", nullable = false, length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(nullable = false)
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    // bi-directional many-to-one association to TbSwDisposalMast
    @Column(name = "MRF_ID", nullable = false)
    private Long deId;

    // bi-directional many-to-one association to TbSwWastesegDet
    @JsonIgnore
    @OneToMany(mappedBy = "tbSwWasteseg", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<WastageSegregationDetails> tbSwWastesegDets;

    public WastageSegregation() {
    }

    public Long getGrId() {
        return this.grId;
    }

    public void setGrId(Long grId) {
        this.grId = grId;
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

    public Date getGrDate() {
        return this.grDate;
    }

    public void setGrDate(Date grDate) {
        this.grDate = grDate;
    }

    public Long getGrTotal() {
        return this.grTotal;
    }

    public void setGrTotal(Long grTotal) {
        this.grTotal = grTotal;
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

    public Long getDeId() {
        return deId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getVenId() {
        return venId;
    }

    public void setVenId(Long venId) {
        this.venId = venId;
    }

    public void setDeId(Long deId) {
        this.deId = deId;
    }

    public List<WastageSegregationDetails> getTbSwWastesegDets() {
        return this.tbSwWastesegDets;
    }

    public void setTbSwWastesegDets(
            List<WastageSegregationDetails> tbSwWastesegDets) {
        this.tbSwWastesegDets = tbSwWastesegDets;
    }

    public WastageSegregationDetails addTbSwWastesegDet(
            WastageSegregationDetails tbSwWastesegDet) {
        getTbSwWastesegDets().add(tbSwWastesegDet);
        tbSwWastesegDet.setTbSwWasteseg(this);

        return tbSwWastesegDet;
    }

    public WastageSegregationDetails removeTbSwWastesegDet(
            WastageSegregationDetails tbSwWastesegDet) {
        getTbSwWastesegDets().remove(tbSwWastesegDet);
        tbSwWastesegDet.setTbSwWasteseg(null);

        return tbSwWastesegDet;
    }

    public String[] getPkValues() {

        return new String[] { "SWM", "TB_SW_WASTESEG", "GR_ID" };
    }
}