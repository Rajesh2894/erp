package com.abm.mainet.common.dto;

import java.io.Serializable;

public class WaterNoDueDto implements Serializable{

	private static final long serialVersionUID = -2155247272559640865L;
	private String waterConNo;
	private String propNo;
	private Double waterDueAmt;
	private Double propDueAmt;
	private Long appNo;
	public String getWaterConNo() {
		return waterConNo;
	}
	public void setWaterConNo(String waterConNo) {
		this.waterConNo = waterConNo;
	}
	public String getPropNo() {
		return propNo;
	}
	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}
	public Double getWaterDueAmt() {
		return waterDueAmt;
	}
	public void setWaterDueAmt(Double waterDueAmt) {
		this.waterDueAmt = waterDueAmt;
	}
	public Double getPropDueAmt() {
		return propDueAmt;
	}
	public void setPropDueAmt(Double propDueAmt) {
		this.propDueAmt = propDueAmt;
	}
	public Long getAppNo() {
		return appNo;
	}
	public void setAppNo(Long appNo) {
		this.appNo = appNo;
	}
	
	
	
}
