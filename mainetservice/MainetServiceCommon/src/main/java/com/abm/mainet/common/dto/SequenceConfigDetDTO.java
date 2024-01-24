package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.SequenceConfigMasterEntity;

/**
 * @author sadik.shaikh
 *
 */
public class SequenceConfigDetDTO implements Serializable{
	
	
	private static final long serialVersionUID = -3552254602633549915L;

	private Long seqDetId;

	private SequenceConfigMasterEntity seqConfigMasterEntity;

	private String seqFactId;

	private String prefixId;

	private Long seqOrder;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;
	
	private String lgIpMacUpd;
	
	private Long updatedBy;
	
	private Date updatedDate;

	
	public Long getSeqDetId() {
		return seqDetId;
	}

	public void setSeqDetId(Long seqDetId) {
		this.seqDetId = seqDetId;
	}

	public SequenceConfigMasterEntity getSeqConfigMasterEntity() {
		return seqConfigMasterEntity;
	}

	public void setSeqConfigMasterEntity(SequenceConfigMasterEntity seqConfigMasterEntity) {
		this.seqConfigMasterEntity = seqConfigMasterEntity;
	}

	public String getSeqFactId() {
		return seqFactId;
	}

	public void setSeqFactId(String seqFactId) {
		this.seqFactId = seqFactId;
	}

	

	public String getPrefixId() {
		return prefixId;
	}

	public void setPrefixId(String prefixId) {
		this.prefixId = prefixId;
	}

	public Long getSeqOrder() {
		return seqOrder;
	}

	public void setSeqOrder(Long seqOrder) {
		this.seqOrder = seqOrder;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	@Override
	public String toString() {
		return "SequenceConfigDetDTO [seqDetId=" + seqDetId + ", seqConfigMasterEntity=" + seqConfigMasterEntity
				+ ", seqFactId=" + seqFactId + ", prefixId=" + prefixId + ", seqOrder=" + seqOrder + ", orgId=" + orgId
				+ ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

	

	
	

	
	
	
	
	
	
	
}
