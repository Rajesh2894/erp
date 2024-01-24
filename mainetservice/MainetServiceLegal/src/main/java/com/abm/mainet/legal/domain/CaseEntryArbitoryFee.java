/**
 * 
 */
package com.abm.mainet.legal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 *
 */
@Entity
@Table(name = "TB_LGL_ARBITORY_FEE")
public class CaseEntryArbitoryFee {


    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ARB_ID")
    private Long arbitoryId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CSE_ID", nullable = false)
    private CaseEntry tbCaseEntry;

    @Column(name = "JUDGE_ID")
    private Long judgeName;

    @Column(name = "ARB_FEEID")
    private Long arbitoryfeeType;

    @Column(name = "ARB_AMT")
    private Double arbitoryAmount;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lpipmac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    public Long getArbitoryId() {
	return arbitoryId;
    }

    public void setArbitoryId(Long arbitoryId) {
	this.arbitoryId = arbitoryId;
    }

    public Long getJudgeName() {
	return judgeName;
    }

    public void setJudgeName(Long judgeName) {
	this.judgeName = judgeName;
    }

    public Long getArbitoryfeeType() {
	return arbitoryfeeType;
    }

    public void setArbitoryfeeType(Long arbitoryfeeType) {
	this.arbitoryfeeType = arbitoryfeeType;
    }

    public Double getArbitoryAmount() {
	return arbitoryAmount;
    }

    public void setArbitoryAmount(Double arbitoryAmount) {
	this.arbitoryAmount = arbitoryAmount;
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

    public String getLpipmac() {
	return lpipmac;
    }

    public void setLpipmac(String lpipmac) {
	this.lpipmac = lpipmac;
    }

    public String getLgIpMacUpd() {
	return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
    }

    
    public CaseEntry getTbCaseEntry() {
        return tbCaseEntry;
    }

    public void setTbCaseEntry(CaseEntry tbCaseEntry) {
        this.tbCaseEntry = tbCaseEntry;
    }

    public String[] getPkValues() {
	return new String[] { "LGL", "TB_LGL_ARBITORY_FEE", "ARB_ID" };

    }
}
