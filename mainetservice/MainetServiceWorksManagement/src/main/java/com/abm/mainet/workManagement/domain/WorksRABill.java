package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_RABILL")
public class WorksRABill implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3668199876064822178L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RA_ID", nullable = false)
    private Long raId;

    @Column(name = "WORK_ID", nullable = false)
    private Long workId;

    @Column(name = "RA_TAXAMT", nullable = true)
    private BigDecimal raTaxAmt;

    @Column(name = "RA_STATUS", nullable = false)
    private String raStatus;

    @Column(name = "RA_REMARK", nullable = true)
    private String raRemark;

    @Column(name = "RA_PAIDAMT", nullable = true)
    private BigDecimal raPaidAmt;

    @Column(name = "RA_GENEDATE", nullable = false)
    private Date raGeneratedDate;

    @Column(name = "RA_BILLNO", nullable = true)
    private String raBillno;

    @Column(name = "RA_BILLDT", nullable = true)
    private Date raBillDate;

    @Column(name = "RA_BILLAMT", nullable = false)
    private BigDecimal raBillAmt;

    @Column(name = "PROJ_ID", nullable = false)
    private Long projId;

    @Column(name = "RA_MBAMT", nullable = true)
    private BigDecimal raMbAmount;

    @Column(name = "RA_MBID", nullable = true)
    private String raMbIds;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "worksRABill", cascade = CascadeType.ALL)
    private List<WmsRaBillTaxDetails> raBillTaxDetails = new ArrayList<>();

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = false)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "RA_CODE")
    private String raCode;
    
    @Column(name = "RA_SERIALNO")
    private Long raSerialNo;
    
    @Column(name = "RA_BILLTYPE", nullable = true)
    private String raBillType;

    public Long getRaId() {
        return raId;
    }

    public void setRaId(Long raId) {
        this.raId = raId;
    }

    public Long getWorkId() {
        return workId;
    }

    public void setWorkId(Long workId) {
        this.workId = workId;
    }

    public BigDecimal getRaTaxAmt() {
        return raTaxAmt;
    }

    public void setRaTaxAmt(BigDecimal raTaxAmt) {
        this.raTaxAmt = raTaxAmt;
    }

    public String getRaStatus() {
        return raStatus;
    }

    public void setRaStatus(String raStatus) {
        this.raStatus = raStatus;
    }

    public String getRaRemark() {
        return raRemark;
    }

    public void setRaRemark(String raRemark) {
        this.raRemark = raRemark;
    }

    public BigDecimal getRaPaidAmt() {
        return raPaidAmt;
    }

    public void setRaPaidAmt(BigDecimal raPaidAmt) {
        this.raPaidAmt = raPaidAmt;
    }

    public Date getRaGeneratedDate() {
        return raGeneratedDate;
    }

    public void setRaGeneratedDate(Date raGeneratedDate) {
        this.raGeneratedDate = raGeneratedDate;
    }

    public String getRaBillno() {
        return raBillno;
    }

    public void setRaBillno(String raBillno) {
        this.raBillno = raBillno;
    }

    public Date getRaBillDate() {
        return raBillDate;
    }

    public void setRaBillDate(Date raBillDate) {
        this.raBillDate = raBillDate;
    }

    public BigDecimal getRaBillAmt() {
        return raBillAmt;
    }

    public void setRaBillAmt(BigDecimal raBillAmt) {
        this.raBillAmt = raBillAmt;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public BigDecimal getRaMbAmount() {
        return raMbAmount;
    }

    public void setRaMbAmount(BigDecimal raMbAmount) {
        this.raMbAmount = raMbAmount;
    }

    public Long getOrgId() {
        return orgId;
    }

    public List<WmsRaBillTaxDetails> getRaBillTaxDetails() {
        return raBillTaxDetails;
    }

    public void setRaBillTaxDetails(List<WmsRaBillTaxDetails> raBillTaxDetails) {
        this.raBillTaxDetails = raBillTaxDetails;
    }

    public String getRaMbIds() {
        return raMbIds;
    }

    public void setRaMbIds(String raMbIds) {
        this.raMbIds = raMbIds;
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

    public String getRaCode() {
        return raCode;
    }

    public void setRaCode(String raCode) {
        this.raCode = raCode;
    }

    public Long getRaSerialNo() {
		return raSerialNo;
	}

	public void setRaSerialNo(Long raSerialNo) {
		this.raSerialNo = raSerialNo;
	}

	public String getRaBillType() {
		return raBillType;
	}

	public void setRaBillType(String raBillType) {
		this.raBillType = raBillType;
	}

	public String[] getPkValues() {
        return new String[] { "WMS", "TB_WMS_RABILL", "RA_ID" };
    }

}
