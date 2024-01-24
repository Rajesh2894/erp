package com.abm.mainet.materialmgmt.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "MM_BIN_DEFINITION_MAS")
public class BinDefMasEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BinDefId", nullable = false)
	private Long binDefId;

	@Column(name = "DefName", nullable = false)
	private String defName;

	@Column(name = "Description", nullable = false)
	private String description;

	@Column(name = "Priority", nullable = false)
	private Long priority;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "LANGID", nullable = false)
	private Long langId;

	@Temporal(TemporalType.DATE)
	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@JsonIgnore
	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@JsonIgnore
	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	public Long getBinDefId() {
		return binDefId;
	}

	public void setBinDefId(Long binDefId) {
		this.binDefId = binDefId;
	}

	public String getDefName() {
		return defName;
	}

	public void setDefName(String defName) {
		this.defName = defName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public static String[] getPkValues() {
		return new String[] { "MM", "MM_BIN_DEFINITION_MAS", "BINDEFID" };
	}

}
