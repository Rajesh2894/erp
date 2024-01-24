package com.abm.mainet.common.integration.dms.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_DMS_METADATA_RETEN_DET")
public class DocRetentionDetEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DMS_DET_ID", nullable = false)
	private Long dmsDetId;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "DMS_ID")
	private DocRetentionEntity docRetentionEntity;

	@Column(name = "MT_KEY")
	private String mtKey;

	@Column(name = "MT_VAL")
	private String mtVal;

	@Column(name = "DOC_DESC")
	private String docDesc;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORGID")
	private Organisation orgId;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CREATED_BY")
	private Employee userId;

	@Column(name = "CREATED_DATE")
	private Date lmodDate;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "UPDATED_BY")
	private Employee updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getDmsDetId() {
		return dmsDetId;
	}

	public void setDmsDetId(Long dmsDetId) {
		this.dmsDetId = dmsDetId;
	}

	public String getMtKey() {
		return mtKey;
	}

	public void setMtKey(String mtKey) {
		this.mtKey = mtKey;
	}

	public String getMtVal() {
		return mtVal;
	}

	public void setMtVal(String mtVal) {
		this.mtVal = mtVal;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public Organisation getOrgId() {
		return orgId;
	}

	public void setOrgId(Organisation orgId) {
		this.orgId = orgId;
	}

	public Employee getUserId() {
		return userId;
	}

	public void setUserId(Employee userId) {
		this.userId = userId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
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

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLangId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLangId(int langId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getIsDeleted() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIsDeleted(String isDeleted) {
		// TODO Auto-generated method stub

	}

	public String[] getPkValues() {
		return new String[] { "CFC", "TB_DMS_METADATA_RETEN_DET", "DMS_DET_ID" };
	}

	public DocRetentionEntity getDocRetentionEntity() {
		return docRetentionEntity;
	}

	public void setDocRetentionEntity(DocRetentionEntity docRetentionEntity) {
		this.docRetentionEntity = docRetentionEntity;
	}

}
