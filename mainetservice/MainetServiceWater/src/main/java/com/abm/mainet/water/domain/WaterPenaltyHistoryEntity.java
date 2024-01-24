package com.abm.mainet.water.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 *@since 27 july 2020
 */

@Entity
@Table(name = "TB_WT_PENALTY_HIST")
public class WaterPenaltyHistoryEntity {

	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_WPENALTY_ID")
	private Long penaltyHisId;
	 
	@Column(name = "WPENALTY_ID")
    private Long penaltyId;
	
	@Column(name = "CS_IDN")
    private String connNo;

    @Column(name = "ACTUAL_AMOUNT")
    private double actualAmount;

    @Column(name = "PENDING_AMOUNT")
    private double pendingAmount;

    @Column(name = "FIN_YEARID")
    private Long finYearId;

    @Column(name = "ACTIVE_FLAG")
    private char activeFlag;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long cretedBy;

    @Column(name = "CREATED_DATE")
    private Date createddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpmac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;
    
    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "SUR_FROM_DATE")
    private Date surchargeFromDate;
    
    @Column(name = "SUR_TO_DATE")
    private Date surchargeToDate;

    @Column(name = "ACTUAL_ARREAR_AMOUNT")
    private double actualArrrearAmount;
    
   @Column(name = "BM_IDNO")
    private Long bmIdNo;
   
    @Column(name = "bill_gen_amount")
    private double billGenAmount;
    
    @Column(name = "H_Status")
    private String hStatus;
    
    @Column(name = "curr_bm_idno")
    private Long currBmIdNo;

	public Long getPenaltyHisId() {
		return penaltyHisId;
	}

	public void setPenaltyHisId(Long penaltyHisId) {
		this.penaltyHisId = penaltyHisId;
	}

	public Long getPenaltyId() {
		return penaltyId;
	}

	public void setPenaltyId(Long penaltyId) {
		this.penaltyId = penaltyId;
	}

	public String getConnNo() {
		return connNo;
	}

	public void setConnNo(String connNo) {
		this.connNo = connNo;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public double getPendingAmount() {
		return pendingAmount;
	}

	public void setPendingAmount(double pendingAmount) {
		this.pendingAmount = pendingAmount;
	}

	public Long getFinYearId() {
		return finYearId;
	}

	public void setFinYearId(Long finYearId) {
		this.finYearId = finYearId;
	}

	public char getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(char activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCretedBy() {
		return cretedBy;
	}

	public void setCretedBy(Long cretedBy) {
		this.cretedBy = cretedBy;
	}

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
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

	public String getLgIpmac() {
		return lgIpmac;
	}

	public void setLgIpmac(String lgIpmac) {
		this.lgIpmac = lgIpmac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Date getSurchargeFromDate() {
		return surchargeFromDate;
	}

	public void setSurchargeFromDate(Date surchargeFromDate) {
		this.surchargeFromDate = surchargeFromDate;
	}

	public Date getSurchargeToDate() {
		return surchargeToDate;
	}

	public void setSurchargeToDate(Date surchargeToDate) {
		this.surchargeToDate = surchargeToDate;
	}

	public double getActualArrrearAmount() {
		return actualArrrearAmount;
	}

	public void setActualArrrearAmount(double actualArrrearAmount) {
		this.actualArrrearAmount = actualArrrearAmount;
	}

	public Long getBmIdNo() {
		return bmIdNo;
	}

	public void setBmIdNo(Long bmIdNo) {
		this.bmIdNo = bmIdNo;
	}

	public double getBillGenAmount() {
		return billGenAmount;
	}

	public void setBillGenAmount(double billGenAmount) {
		this.billGenAmount = billGenAmount;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
	public Long getCurrBmIdNo() {
		return currBmIdNo;
	}

	public void setCurrBmIdNo(Long currBmIdNo) {
		this.currBmIdNo = currBmIdNo;
	}

	public String[] getPkValues() {
		return new String[] { "WT", "TB_WT_PENALTY_HIST", "H_WPENALTY_ID" };
	}

}
