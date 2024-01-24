/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_Sfac_DPR_Info_Detail")
public class DPRInfoDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208588856231010739L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DPR_ID", nullable = false)
	private Long dprId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;

	

	@Column(name = "DPR_REC_DT")
	private Date dprRecDt;

	@Column(name = "DPR_REVIEWER")
	private String dprReviewer;

	@Column(name = "DPR_SCORE")
	private Long dprScore;

	@Column(name = "DPR_REV_SUBM_DT")
	private Date dprRevSubmDt;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;



	public Long getDprId() {
		return dprId;
	}



	public void setDprId(Long dprId) {
		this.dprId = dprId;
	}



	public FPOProfileManagementMaster getFpoProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}



	public void setFpoProfileMgmtMaster(FPOProfileManagementMaster fpoProfileMgmtMaster) {
		this.fpoProfileMgmtMaster = fpoProfileMgmtMaster;
	}



	public Date getDprRecDt() {
		return dprRecDt;
	}



	public void setDprRecDt(Date dprRecDt) {
		this.dprRecDt = dprRecDt;
	}



	public String getDprReviewer() {
		return dprReviewer;
	}



	public void setDprReviewer(String dprReviewer) {
		this.dprReviewer = dprReviewer;
	}



	public Long getDprScore() {
		return dprScore;
	}



	public void setDprScore(Long dprScore) {
		this.dprScore = dprScore;
	}



	public Date getDprRevSubmDt() {
		return dprRevSubmDt;
	}



	public void setDprRevSubmDt(Date dprRevSubmDt) {
		this.dprRevSubmDt = dprRevSubmDt;
	}



	public Long getOrgId() {
		return orgId;
	}



	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}



	public Long getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}



	public Date getUpdatedDate() {
		return updatedDate;
	}



	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}



	public Long getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "SFAC", "Tb_Sfac_DPR_Info_Detail", "DPR_ID" };
	}
}
