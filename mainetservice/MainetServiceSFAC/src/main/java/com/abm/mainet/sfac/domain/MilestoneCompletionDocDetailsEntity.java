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

@Entity
@Table(name = "TB_SFAC_MILESTONE_COMP_DOC_DET")
public class MilestoneCompletionDocDetailsEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -283735780378728873L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MSCD_ID")
	private Long mscdId;
	
	@ManyToOne
	@JoinColumn(name = "MSC_ID", referencedColumnName = "MSC_ID")
	private MilestoneCompletionMasterEntity milestoneCompletionMasterEntity;
	
	@Column(name = "DOC_DESC")
	private String docDescription;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true, updatable = true)
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	
	
	


	public Long getMscdId() {
		return mscdId;
	}



	public void setMscdId(Long mscdId) {
		this.mscdId = mscdId;
	}



	public MilestoneCompletionMasterEntity getMilestoneCompletionMasterEntity() {
		return milestoneCompletionMasterEntity;
	}



	public void setMilestoneCompletionMasterEntity(MilestoneCompletionMasterEntity milestoneCompletionMasterEntity) {
		this.milestoneCompletionMasterEntity = milestoneCompletionMasterEntity;
	}



	public String getDocDescription() {
		return docDescription;
	}



	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
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



	public Long getUpdatedBy() {
		return updatedBy;
	}



	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}



	public Date getCreatedDate() {
		return createdDate;
	}



	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_MILESTONE_COMP_DOC_DET", "MSCD_ID" };
	}

}
