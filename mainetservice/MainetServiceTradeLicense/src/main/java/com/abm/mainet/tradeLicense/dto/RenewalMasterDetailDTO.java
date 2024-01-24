package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.util.Date;



public class RenewalMasterDetailDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private Long treId;
    private Long apmApplicationId;
    private Long trdId;
    private Date treLicfromDate;
    private Date treLictoDate ;
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
	private Long triCod1;
	private Long triCod2;

	private Long renewalPeriod;
    
	public Long getTreId() {
		return treId;
	}
	public Long getApmApplicationId() {
		return apmApplicationId;
	}
	public Long getTrdId() {
		return trdId;
	}
	public Date getTreLicfromDate() {
		return treLicfromDate;
	}
	public Date getTreLictoDate() {
		return treLictoDate;
	}
	public String getTreStatus() {
		return treStatus;
	}
	public Long getOrgid() {
		return orgid;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public Long getUpdatedBy() {
		return updatedBy;
	}
	public String getLgIpMac() {
		return lgIpMac;
	}
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}
	public void setTreId(Long treId) {
		this.treId = treId;
	}
	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}
	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}
	public void setTreLicfromDate(Date treLicfromDate) {
		this.treLicfromDate = treLicfromDate;
	}
	public void setTreLictoDate(Date treLictoDate) {
		this.treLictoDate = treLictoDate;
	}
	public void setTreStatus(String treStatus) {
		this.treStatus = treStatus;
	}

	
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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
	public String getRenewalTodDateDesc() {
		return renewalTodDateDesc;
	}
	public void setRenewalFromDateDesc(String renewalFromDateDesc) {
		this.renewalFromDateDesc = renewalFromDateDesc;
	}
	public void setRenewalTodDateDesc(String renewalTodDateDesc) {
		this.renewalTodDateDesc = renewalTodDateDesc;
	}
	public Long getRenewalPeriod() {
		return renewalPeriod;
	}
	public void setRenewalPeriod(Long renewalPeriod) {
		this.renewalPeriod = renewalPeriod;
	}
	public Long getTriCod1() {
		return triCod1;
	}
	public void setTriCod1(Long triCod1) {
		this.triCod1 = triCod1;
	}
	public Long getTriCod2() {
		return triCod2;
	}
	public void setTriCod2(Long triCod2) {
		this.triCod2 = triCod2;
	}
	

}
