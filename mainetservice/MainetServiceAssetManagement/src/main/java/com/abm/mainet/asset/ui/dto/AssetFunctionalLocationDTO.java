package com.abm.mainet.asset.ui.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AssetFunctionalLocationMaster")
public class AssetFunctionalLocationDTO {

	private Long funcLocationId;

	private String funcLocationCode;

	private String description;

	private String startPoint;

	private String endPoint;

	private Long orgId;

	private Long unit;
	
	private String unitDesc;

	private Long parentId;
	
	private String parentCode;

	
	private Long parentDesc;


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

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUnit() {
		return unit;
	}

	public void setUnit(Long unit) {
		this.unit = unit;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUnitDesc() {
		return unitDesc;
	}

	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}

	public Long getParentDesc() {
		return parentDesc;
	}

	public void setParentDesc(Long parentDesc) {
		this.parentDesc = parentDesc;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}



}
