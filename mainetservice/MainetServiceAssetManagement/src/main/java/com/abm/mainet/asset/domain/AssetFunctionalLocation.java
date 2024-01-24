package com.abm.mainet.asset.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="TB_AST_FUNC_LOCATION_MAS")
public class AssetFunctionalLocation {
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name="FUNC_LOCATION_ID",nullable = false)
	private Long funcLocationId;
	
	@Column(name="FUNC_LOCATION_CODE",nullable = false,length=500)
	private String funcLocationCode;
	
	@Column(name="DESCRIPTION",nullable = true,length=100)
	private String description;
	
	@OneToOne(cascade= {CascadeType.DETACH})
	@JoinColumn(name="FUNC_LOC_PARENT_ID")
	private AssetFunctionalLocation flcObject;
	
	@Column(name="START_POINT",nullable = true)
	private String startPoint;
	
	@Column(name="END_POINT",nullable = true)
	private String endPoint;
	
	@Column(name="UNIT",nullable = true)
	private Long unit;

	@Column(name = "orgId", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATION_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	

	public Long getFuncLocationId() {
		return funcLocationId;
	}

	public void setFuncLocationId(Long funcLocationId) {
		this.funcLocationId = funcLocationId;
	}

	public String getFuncLocationCode() {
		return funcLocationCode;
	}

	public void setFuncLocationCode(String funcLocationCode) {
		this.funcLocationCode = funcLocationCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
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

	public static String[] getPkValues() {
		return new String[] { "AST", "TB_AST_FUNC_LOCATION_MAS", "funcLocationCode" };
	}

	public AssetFunctionalLocation getFlcObject() {
		return flcObject;
	}

	public void setFlcObject(AssetFunctionalLocation flcObject) {
		this.flcObject = flcObject;
	}
	
	
}
