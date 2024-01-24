package com.abm.mainet.common.domain;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.ManyToOne;

import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SEQ_CONFIGDET")
public class SequenceConfigDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5373558652277713732L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SEQ_DETID", nullable = false, unique = true)
	private Long seqDetId;

	@ManyToOne
	@JoinColumn(name = "SEQ_CONFID", nullable = false)
	private SequenceConfigMasterEntity seqConfigMasterEntity;

	@Column(name = "SEQ_FACTID", nullable = true)
	private String seqFactId;

	@Column(name = "PREFIX_ID", nullable = true)
	private String prefixId;

	@Column(name = "SEQ_ORDER", nullable = true)
	private Long seqOrder;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
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

	public static String[] getPkValues() {
		return new String[] { "COM", "TB_SEQ_CONFIGDET", "SEQ_DETID" };
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
		return "SequenceConfigDetEntity [seqDetId=" + seqDetId + ", seqConfigMasterEntity=" + seqConfigMasterEntity
				+ ", seqFactId=" + seqFactId + ", prefixId=" + prefixId + ", seqOrder=" + seqOrder + ", orgId=" + orgId
				+ ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

}
