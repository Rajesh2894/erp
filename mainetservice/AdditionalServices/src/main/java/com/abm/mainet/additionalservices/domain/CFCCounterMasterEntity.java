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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_countermaster")
public class CFCCounterMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6354435258819217366L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "cu_counterid", nullable = false)
	private Long cuCounterid;

	@OneToMany(mappedBy = "cfcCounterMasterEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CFCCounterScheduleEntity> cfcCounterScheduleEntities;

	@Column(name = "cu_countcentreno", nullable = false)
	private String cuCountcentreno;

	@Column(name = "cu_description", nullable = false)
	private String cuDescription;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cm_collnid", nullable = false)
	private CFCCollectionMasterEntity cfcCollectionMasterEntity;

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

	public static String[] getPkValues() {
		return new String[] { "CFC", "tb_countermaster", "cu_counterid" };
	}

	public Long getCuCounterid() {
		return cuCounterid;
	}

	public void setCuCounterid(Long cuCounterid) {
		this.cuCounterid = cuCounterid;
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

	public CFCCollectionMasterEntity getCfcCollectionMasterEntity() {
		return cfcCollectionMasterEntity;
	}

	public void setCfcCollectionMasterEntity(CFCCollectionMasterEntity cfcCollectionMasterEntity) {
		this.cfcCollectionMasterEntity = cfcCollectionMasterEntity;
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

	public List<CFCCounterScheduleEntity> getCfcCounterScheduleEntities() {
		return cfcCounterScheduleEntities;
	}

	public void setCfcCounterScheduleEntities(List<CFCCounterScheduleEntity> cfcCounterScheduleEntities) {
		this.cfcCounterScheduleEntities = cfcCounterScheduleEntities;
	}

}
