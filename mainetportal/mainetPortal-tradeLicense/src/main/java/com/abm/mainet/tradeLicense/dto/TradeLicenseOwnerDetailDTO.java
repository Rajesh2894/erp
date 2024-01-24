package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abm.mainet.dms.domain.CFCAttachment;

public class TradeLicenseOwnerDetailDTO implements Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3556922444037154348L;
	private Long troId;
	@JsonIgnore
	private TradeMasterDetailDTO masterTradeId;
	private String troName;
	private String troAddress;
	private String troEmailid;
	private String troMobileno;
	private Long troAdhno;
	private String troPr;
	private String troEducation;
	private Long troCast;
	private Long troAge;
	private Long orgid;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Long troTitle;
	private String troMname;
	private String troGen;
	private Long apmApplicationId;
	private String ownFname;
	private String ownMname;
	private String ownLname;
	private String gurdFname;
	private String gurdMname;
	private String gurdLname;
	private List<CFCAttachment> viewImg = new ArrayList<>();

	public Long getTroId() {
		return troId;
	}

	public void setTroId(Long troId) {
		this.troId = troId;
	}

	public String getTroName() {
		return troName;
	}

	public String getTroEducation() {
		return troEducation;
	}

	public void setTroEducation(String troEducation) {
		this.troEducation = troEducation;
	}

	public Long getTroCast() {
		return troCast;
	}

	public void setTroCast(Long troCast) {
		this.troCast = troCast;
	}

	public void setTroName(String troName) {
		this.troName = troName;
	}

	public String getTroAddress() {
		return troAddress;
	}

	public void setTroAddress(String troAddress) {
		this.troAddress = troAddress;
	}

	public String getTroEmailid() {
		return troEmailid;
	}

	public void setTroEmailid(String troEmailid) {
		this.troEmailid = troEmailid;
	}

	public String getTroMobileno() {
		return troMobileno;
	}

	public void setTroMobileno(String troMobileno) {
		this.troMobileno = troMobileno;
	}

	public Long getTroAdhno() {
		return troAdhno;
	}

	public void setTroAdhno(Long troAdhno) {
		this.troAdhno = troAdhno;
	}

	public String getTroPr() {
		return troPr;
	}

	public void setTroPr(String troPr) {
		this.troPr = troPr;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public TradeMasterDetailDTO getMasterTradeId() {
		return masterTradeId;
	}

	public void setMasterTradeId(TradeMasterDetailDTO masterTradeId) {
		this.masterTradeId = masterTradeId;
	}

	public Long getTroTitle() {
		return troTitle;
	}

	public void setTroTitle(Long troTitle) {
		this.troTitle = troTitle;
	}

	public String getTroMname() {
		return troMname;
	}

	public void setTroMname(String troMname) {
		this.troMname = troMname;
	}

	public String getTroGen() {
		return troGen;
	}

	public void setTroGen(String troGen) {
		this.troGen = troGen;
	}

	public List<CFCAttachment> getViewImg() {
		return viewImg;
	}

	public void setViewImg(List<CFCAttachment> viewImg) {
		this.viewImg = viewImg;
	}

	public Long getTroAge() {
		return troAge;
	}

	public void setTroAge(Long troAge) {
		this.troAge = troAge;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public String getOwnFname() {
		return ownFname;
	}

	public void setOwnFname(String ownFname) {
		this.ownFname = ownFname;
	}

	public String getOwnMname() {
		return ownMname;
	}

	public void setOwnMname(String ownMname) {
		this.ownMname = ownMname;
	}

	public String getOwnLname() {
		return ownLname;
	}

	public void setOwnLname(String ownLname) {
		this.ownLname = ownLname;
	}

	public String getGurdFname() {
		return gurdFname;
	}

	public void setGurdFname(String gurdFname) {
		this.gurdFname = gurdFname;
	}

	public String getGurdMname() {
		return gurdMname;
	}

	public void setGurdMname(String gurdMname) {
		this.gurdMname = gurdMname;
	}

	public String getGurdLname() {
		return gurdLname;
	}

	public void setGurdLname(String gurdLname) {
		this.gurdLname = gurdLname;
	}
	
	

}
