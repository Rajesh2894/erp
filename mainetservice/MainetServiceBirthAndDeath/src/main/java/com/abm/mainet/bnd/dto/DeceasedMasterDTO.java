package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.bnd.domain.TbDeathreg;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class DeceasedMasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861271056301152387L;

	private Long decId;
	private Long drId;
	@JsonIgnore
	private Long orgId;
	private Long userId;
	@JsonIgnore
	private Long updatedBy;
	private String decSmoker;
	@JsonIgnore
	private Long decSmokerYr;
	private String decChewtb;
	@JsonIgnore
	private Long decChewtbYr;
	private String decChewarac;
	@JsonIgnore
	private Long decChewaracYr;
	private String decAlcoholic;
	@JsonIgnore
	private Long decAlcoholicYr;
	private int langId;
	@JsonIgnore
	private Date lmodDate;
	@JsonIgnore
	private String decRemarks;
	@JsonIgnore
	private Date updatedDate;
	@JsonIgnore
	private String lgIpMac;
	@JsonIgnore
	private String lgIpMacUpd;

	public Long getDecId() {
		return decId;
	}

	public void setDecId(Long decId) {
		this.decId = decId;
	}

	
	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getDecSmoker() {
		return decSmoker;
	}

	public void setDecSmoker(String decSmoker) {
		this.decSmoker = decSmoker;
	}

	public Long getDecSmokerYr() {
		return decSmokerYr;
	}

	public void setDecSmokerYr(Long decSmokerYr) {
		this.decSmokerYr = decSmokerYr;
	}

	public Long getDecChewtbYr() {
		return decChewtbYr;
	}

	public String getDecChewtb() {
		return decChewtb;
	}

	public void setDecChewtb(String decChewtb) {
		this.decChewtb = decChewtb;
	}

	public void setDecChewtbYr(Long decChewtbYr) {
		this.decChewtbYr = decChewtbYr;
	}

	public String getDecChewarac() {
		return decChewarac;
	}

	public void setDecChewarac(String decChewarac) {
		this.decChewarac = decChewarac;
	}

	public Long getDecChewaracYr() {
		return decChewaracYr;
	}

	public void setDecChewaracYr(Long decChewaracYr) {
		this.decChewaracYr = decChewaracYr;
	}

	public String getDecAlcoholic() {
		return decAlcoholic;
	}

	public void setDecAlcoholic(String decAlcoholic) {
		this.decAlcoholic = decAlcoholic;
	}

	public Long getDecAlcoholicYr() {
		return decAlcoholicYr;
	}

	public void setDecAlcoholicYr(Long decAlcoholicYr) {
		this.decAlcoholicYr = decAlcoholicYr;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public String getDecRemarks() {
		return decRemarks;
	}

	public void setDecRemarks(String decRemarks) {
		this.decRemarks = decRemarks;
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

}