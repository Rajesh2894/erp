package com.abm.mainet.tradeLicense.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RenewalHistroyDetails {
	private Long treId;
	private Long apmApplicationId;
	private Long trdId;
	private Date treLicfromDate;
	private Date treLictoDate;
	private String treStatus;
	private Long orgid;
	private Date createdDate;
	private Long createdBy;
	private Date updatedDate;
	private Long updatedBy;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long langId;
	private String renewalFromDateDesc;
	private String renewalTodDateDesc;
	private String serviceShrtCode;
	private Double totalAmt;
	private Map<String,Double> chargeDesc=new HashMap<String, Double>();
	private Long rcptNo;

	public Long getRcptNo() {
		return rcptNo;
	}

	public void setRcptNo(Long rcptNo) {
		this.rcptNo = rcptNo;
	}

	public Long getTreId() {
		return treId;
	}

	public void setTreId(Long treId) {
		this.treId = treId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public Date getTreLicfromDate() {
		return treLicfromDate;
	}

	public void setTreLicfromDate(Date treLicfromDate) {
		this.treLicfromDate = treLicfromDate;
	}

	public Date getTreLictoDate() {
		return treLictoDate;
	}

	public void setTreLictoDate(Date treLictoDate) {
		this.treLictoDate = treLictoDate;
	}

	public String getTreStatus() {
		return treStatus;
	}

	public void setTreStatus(String treStatus) {
		this.treStatus = treStatus;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public String getRenewalFromDateDesc() {
		return renewalFromDateDesc;
	}

	public Map<String, Double> getChargeDesc() {
		return chargeDesc;
	}

	public void setChargeDesc(Map<String, Double> chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	public String getServiceShrtCode() {
		return serviceShrtCode;
	}

	public void setServiceShrtCode(String serviceShrtCode) {
		this.serviceShrtCode = serviceShrtCode;
	}

	public void setRenewalFromDateDesc(String renewalFromDateDesc) {
		this.renewalFromDateDesc = renewalFromDateDesc;
	}

	public String getRenewalTodDateDesc() {
		return renewalTodDateDesc;
	}

	public void setRenewalTodDateDesc(String renewalTodDateDesc) {
		this.renewalTodDateDesc = renewalTodDateDesc;
	}

	public Double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}
}
