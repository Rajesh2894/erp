package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_collectionmaster_hist")
public class CFCCollectionMasterHistEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5924151806974004337L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_cm_collnid", nullable = false)
	private Long HCmCollnid;

	@Column(name = "cm_collnid", nullable = false)
	private Long cmCollnid;

	@Column(name = "cm_collncentreno", nullable = false)
	private String cmCollncentreno;

	@Column(name = "cm_description", nullable = false)
	private String cmDescription;

	@Column(name = "dwz_id", nullable = false)
	private Long dwzId;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "createdDate", nullable = false)
	private Date creationDate;

	@Column(name = "createdBy", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "lgIpMac", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "lgIpMacUpd", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "updatedBy", nullable = true)
	private Long updatedBy;

	@Column(name = "updatedDate", nullable = true)
	private Date updatedDate;

	@Column(name = "H_STATUS", length = 100, nullable = true)
	private String hSTATUS;
	
	public static String[] getPkValues() {
		return new String[] { "CFC", "tb_collectionmaster_hist", "H_cm_collnid" };
	}

	public Long getHCmCollnid() {
		return HCmCollnid;
	}

	public void setHCmCollnid(Long hCmCollnid) {
		HCmCollnid = hCmCollnid;
	}

	public Long getCmCollnid() {
		return cmCollnid;
	}

	public void setCmCollnid(Long cmCollnid) {
		this.cmCollnid = cmCollnid;
	}

	public String getCmCollncentreno() {
		return cmCollncentreno;
	}

	public void setCmCollncentreno(String cmCollncentreno) {
		this.cmCollncentreno = cmCollncentreno;
	}

	public String getCmDescription() {
		return cmDescription;
	}

	public void setCmDescription(String cmDescription) {
		this.cmDescription = cmDescription;
	}

	public Long getDwzId() {
		return dwzId;
	}

	public void setDwzId(Long dwzId) {
		this.dwzId = dwzId;
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

	public String gethSTATUS() {
		return hSTATUS;
	}

	public void sethSTATUS(String hSTATUS) {
		this.hSTATUS = hSTATUS;
	}

}
