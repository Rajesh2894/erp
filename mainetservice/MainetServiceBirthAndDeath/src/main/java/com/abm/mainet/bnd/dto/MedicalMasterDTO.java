package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.abm.mainet.bnd.domain.TbDeathreg;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@NamedQuery(name = "MedicalMaster.findAll", query = "SELECT b FROM TB_MEDICALCERT b")
public class MedicalMasterDTO implements Serializable {

	private static final long serialVersionUID = -4100833798784520499L;
	
	private Long mcId;
	@JsonIgnore
	private Long orgId;
	@JsonIgnore
	private Long drId;
	@JsonIgnore
	private Long mcWardId;
	private String mcDeathcause;
	private String mcOthercond;
	private Long mcInteronset;
	private String mcDeathManner;
	private String mcPregnAssoc;
	@JsonIgnore
	private String mcDelivery;
	private String mcMdattndName;
	private Date mcVerifnDate;
	private String mcMdSuprName;
	@JsonIgnore
	private Long userId;
	@JsonIgnore
	private int langId;
	@JsonIgnore
	private Date lmodDate;
	@JsonIgnore
	private Long dcmId;
	@JsonIgnore
	private Long updatedBy;
	@JsonIgnore
	private Date updatedDate;
	@JsonIgnore
	private String lgIpMac;
	@JsonIgnore
	private String lgIpMacUpd;
	private String medCert;
	@JsonIgnore
	private Long cpdidMcDeathManner;
	@JsonIgnore
	private Long upldId;

	public Long getMcId() {
		return mcId;
	}

	public void setMcId(Long mcId) {
		this.mcId = mcId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	

	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getMcWardId() {
		return mcWardId;
	}

	public void setMcWardId(Long mcWardId) {
		this.mcWardId = mcWardId;
	}

	public String getMcDeathcause() {
		return mcDeathcause;
	}

	public void setMcDeathcause(String mcDeathcause) {
		this.mcDeathcause = mcDeathcause;
	}

	public String getMcOthercond() {
		return mcOthercond;
	}

	public void setMcOthercond(String mcOthercond) {
		this.mcOthercond = mcOthercond;
	}

	public Long getMcInteronset() {
		return mcInteronset;
	}

	public void setMcInteronset(Long mcInteronset) {
		this.mcInteronset = mcInteronset;
	}

	public String getMcDeathManner() {
		return mcDeathManner;
	}

	public void setMcDeathManner(String mcDeathManner) {
		this.mcDeathManner = mcDeathManner;
	}

	public String getMcPregnAssoc() {
		return mcPregnAssoc;
	}

	public void setMcPregnAssoc(String mcPregnAssoc) {
		this.mcPregnAssoc = mcPregnAssoc;
	}

	public String getMcDelivery() {
		return mcDelivery;
	}

	public void setMcDelivery(String mcDelivery) {
		this.mcDelivery = mcDelivery;
	}

	public String getMcMdattndName() {
		return mcMdattndName;
	}

	public void setMcMdattndName(String mcMdattndName) {
		this.mcMdattndName = mcMdattndName;
	}

	public Date getMcVerifnDate() {
		return mcVerifnDate;
	}

	public void setMcVerifnDate(Date mcVerifnDate) {
		this.mcVerifnDate = mcVerifnDate;
	}

	public String getMcMdSuprName() {
		return mcMdSuprName;
	}

	public void setMcMdSuprName(String mcMdSuprName) {
		this.mcMdSuprName = mcMdSuprName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Long getDcmId() {
		return dcmId;
	}

	public void setDcmId(Long dcmId) {
		this.dcmId = dcmId;
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

	public String getMedCert() {
		return medCert;
	}

	public void setMedCert(String medCert) {
		this.medCert = medCert;
	}

	public Long getCpdidMcDeathManner() {
		return cpdidMcDeathManner;
	}

	public void setCpdidMcDeathManner(Long cpdidMcDeathManner) {
		this.cpdidMcDeathManner = cpdidMcDeathManner;
	}

	public Long getUpldId() {
		return upldId;
	}

	public void setUpldId(Long upldId) {
		this.upldId = upldId;
	}

}
