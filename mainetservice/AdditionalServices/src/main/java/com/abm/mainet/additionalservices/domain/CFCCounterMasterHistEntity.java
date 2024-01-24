package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_countermaster_hist")
public class CFCCounterMasterHistEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5522350737383382940L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_cu_counterid", nullable = false)
	private Long HCuCounterid;

	@Column(name = "cu_counterid", nullable = false)
	private Long cuCounterid;

	@Column(name = "cm_collnid", nullable = false)
	private Long cmCollnid;

	@Column(name = "cu_countcentreno", nullable = false)
	private String cuCountcentreno;

	@Column(name = "cu_description", nullable = false)
	private String cuDescription;

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
		return new String[] { "CFC", "tb_countermaster_hist", "H_cu_counterid" };
	}

	public Long getHCuCounterid() {
		return HCuCounterid;
	}

	public void setHCuCounterid(Long hCuCounterid) {
		HCuCounterid = hCuCounterid;
	}

	public Long getCuCounterid() {
		return cuCounterid;
	}

	public void setCuCounterid(Long cuCounterid) {
		this.cuCounterid = cuCounterid;
	}

	public Long getCmCollnid() {
		return cmCollnid;
	}

	public void setCmCollnid(Long cmCollnid) {
		this.cmCollnid = cmCollnid;
	}

	public String getCuCountcentreno() {
		return cuCountcentreno;
	}

	public void setCuCountcentreno(String cuCountcentreno) {
		this.cuCountcentreno = cuCountcentreno;
	}

	public String getCuDescription() {
		return cuDescription;
	}

	public void setCuDescription(String cuDescription) {
		this.cuDescription = cuDescription;
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
