/**
 * 
 */
package com.abm.mainet.legal.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cherupelli.srikanth
 *
 */
public class CaseEntryArbitoryFeeDto implements Serializable{


    /**
     * 
     */
    private static final long serialVersionUID = 5413377661513188088L;

    private Long arbitoryId;

    private Long judgeName;

    private Long arbitoryfeeType;

    private Double arbitoryAmount;

    private Long orgId;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lpipmac;

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

}
