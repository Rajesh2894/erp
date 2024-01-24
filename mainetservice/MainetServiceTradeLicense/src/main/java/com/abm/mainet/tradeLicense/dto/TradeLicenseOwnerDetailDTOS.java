package com.abm.mainet.tradeLicense.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TradeLicenseOwnerDetailDTOS implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonIgnore
	private Long troId;
	private String troName;
	private String troAddress;
	private String troEmailid;
	private String troMobileno;
	private Long troAdhno;
	@JsonIgnore
	private String troPr;
	private String troEducation;
	private Long troCast;
	private Long troAge;
	private String troTitle;
	private String troMname;
	private String troGen;
	

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


	public Long getTroAge() {
		return troAge;
	}

	public void setTroAge(Long troAge) {
		this.troAge = troAge;
	}

	public String getTroTitle() {
		return troTitle;
	}

	public void setTroTitle(String troTitle) {
		this.troTitle = troTitle;
	}

}
