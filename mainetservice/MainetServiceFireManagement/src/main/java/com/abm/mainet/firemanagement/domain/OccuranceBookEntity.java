package com.abm.mainet.firemanagement.domain;

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
 * The persistent class for the tb_fm_occurance_book database table.
 * 
 */
@Entity
@Table(name = "tb_fm_occurance_book")
//@NamedQuery(name="TbFmOccuranceBook.findAll", query="SELECT t FROM TbFmOccuranceBook t")
public class OccuranceBookEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "occ_id")
	private Long occId;

	@Column(name = "cmplnt_no", length = 20)
	private String cmplntNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date date;

	@Column(name = "incident_desc", length = 500)
	private String incidentDesc;


	@Column(name = "operator_remarks", length = 300)
	private String operatorRemarks;

	private Long orgid;

	// @Temporal(TemporalType.TIMESTAMP)
	private String time;
	
	
	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
            
	
	
	public Long getOccId() {
		return occId;
	}

	public void setOccId(Long occId) {
		this.occId = occId;
	}

	public String getCmplntNo() {
		return cmplntNo;
	}

	public void setCmplntNo(String cmplntNo) {
		this.cmplntNo = cmplntNo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIncidentDesc() {
		return incidentDesc;
	}

	public void setIncidentDesc(String incidentDesc) {
		this.incidentDesc = incidentDesc;
	}

	public String getOperatorRemarks() {
		return operatorRemarks;
	}

	public void setOperatorRemarks(String operatorRemarks) {
		this.operatorRemarks = operatorRemarks;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getPkValues() {
		return new String[] { "FM", "tb_fm_occurance_book", "occ_id" };
	}

}