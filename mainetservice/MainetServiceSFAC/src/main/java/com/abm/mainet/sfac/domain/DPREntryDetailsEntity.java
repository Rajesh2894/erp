package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

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

import com.ibm.icu.math.BigDecimal;

@Entity
@Table(name ="TB_SFAC_DPR_ENTRY_DET")
public class DPREntryDetailsEntity implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3608372931638636798L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DPRD_ID", nullable = false)
	private Long dprdId;
	
	@ManyToOne
	@JoinColumn(name = "DPR_ID", referencedColumnName = "DPR_ID")
	private DPREntryMasterEntity dprEntryMasterEntity;
	
	@Column(name = "DPR_SEC")
	private Long dprSection;
	
	@Column(name = "DPR_SCORE")
	private BigDecimal dprScore;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "DOC_STATUS")
	private String docStatus;
	
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

	public Long getDprdId() {
		return dprdId;
	}

	public void setDprdId(Long dprdId) {
		this.dprdId = dprdId;
	}

	public DPREntryMasterEntity getDprEntryMasterEntity() {
		return dprEntryMasterEntity;
	}

	public void setDprEntryMasterEntity(DPREntryMasterEntity dprEntryMasterEntity) {
		this.dprEntryMasterEntity = dprEntryMasterEntity;
	}

	public Long getDprSection() {
		return dprSection;
	}

	public void setDprSection(Long dprSection) {
		this.dprSection = dprSection;
	}

	public BigDecimal getDprScore() {
		return dprScore;
	}

	public void setDprScore(BigDecimal dprScore) {
		this.dprScore = dprScore;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
	
	
	
	public String getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_DPR_ENTRY_DET", "DPRD_ID" };
	}
	

}
