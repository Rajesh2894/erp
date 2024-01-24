package com.abm.mainet.cms.dto;

/**
 * @author narendra.korapati
 *
 */
public class TraficUpdateDTO {
	
	private String signalSCN;
	private String shortDescription;
	private Double congestion;
	private String status;
	private Integer isActive;
	private String modeType;
	private String updateDate;
	
	
	
	public String getSignalSCN() {
		return signalSCN;
	}
	public void setSignalSCN(String signalSCN) {
		this.signalSCN = signalSCN;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	public Double getCongestion() {
		return congestion;
	}
	public void setCongestion(Double congestion) {
		this.congestion = congestion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Integer getisActive() {
		return isActive;
	}
	public void setisActive(Integer isActive) {
		this.isActive=isActive;
	}
	
	public String getmodeType() {
		return modeType;
	}
	public void setmodeType(String modeType) {
		this.modeType=modeType;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate=updateDate;
	}
}
