package com.abm.mainet.bnd.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_MEDICALCERT")
public class MedicalMaster implements Serializable {

	private static final long serialVersionUID = 2653422207551743807L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MC_ID", precision = 12, scale = 0, nullable = false)
	// comments : Medical Certificate id
	private Long mcId;
	
	/*@Column(name = "DR_ID")
	private Long drId;*/
	
	@OneToOne(fetch = FetchType.LAZY) //,cascade = {CascadeType.ALL})
	@JoinColumn(name = "DR_ID",  nullable = true)
	//comments : Death Register id
	private TbDeathreg			 drId;
	

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "MC_WARD_ID", precision = 12, scale = 0, nullable = true)
	// comments : Ward No:
	private Long mcWardId;

	@Column(name = "MC_DEATHCAUSE", length = 200, nullable = true)
	// comments : Cause of Death
	private String mcDeathcause;

	@Column(name = "MC_OTHERCOND", length = 200, nullable = true)
	// comments :Other Conditions
	private String mcOthercond;

	@Column(name = "MC_INTERONSET", precision = 10, scale = 0, nullable = true)
	// comments : Interval Onset
	private Long mcInteronset;

	@Column(name = "MC_DEATH_MANNER", length = 50, nullable = true)
	// comments : Death Manner
	private String mcDeathManner;

	@Column(name = "MC_PREGN_ASSOC", length = 1, nullable = true)
	// comments :Associated with Pregnancy ?
	private String mcPregnAssoc;

	@Column(name = "MC_DELIVERY", length = 1, nullable = true)
	// comments : Delivered birth?
	private String mcDelivery;

	@Column(name = "MC_MDATTND_NAME", length = 50, nullable = true)
	// comments :Medical Attendant Name
	private String mcMdattndName;

	@Column(name = "MC_VERIFN_DATE", nullable = true)
	// comments : Postmortern date Date
	private Date mcVerifnDate;

	@Column(name = "MC_MD_SUPR_NAME", length = 50, nullable = false)
	// comments :Medical Superintendent Name
	private String mcMdSuprName;

	@Column(name = "USER_ID", nullable = false)
	private Long userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	// comments : Language Identity
	private int langId;

	@Column(name = "LMODDATE")//todo false
	// comments : Last Modification Date
	private Date lmoddate;

	@Column(name = "DCM_ID", precision = 12, scale = 0, nullable = true)
	// comments : Death cause id
	private Long dcmId;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true) // comments : Updated date
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	// comments : Client MachineÂ’s Login Name | IP Address | Physical Address
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	// comments :Updated Client MachineÂ’s Login Name | IP Address | Physical Address
	private String lgIpMacUpd;

	@Column(name = "MED_CERT", length = 1, nullable = true)
	// comments : Medically Certified or not Certified
	private String medCert;

	@Column(name = "CPDID_MC_DEATH_MANNER", precision = 12, scale = 0, nullable = true)
	private Long cpdidMcDeathManner;

	@Column(name = "UPLD_ID", precision = 15, scale = 0, nullable = true)
	// comments : Upload Id 
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

	
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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

	
	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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

	
	

	/*public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}*/

	public TbDeathreg getDrId() {
		return drId;
	}

	public void setDrId(TbDeathreg drId) {
		this.drId = drId;
	}

	public Long getUpldId() {
		return upldId;
	}

	public void setUpldId(Long upldId) {
		this.upldId = upldId;
	}

	public String[] getPkValues() {
		return new String[] { "HD", "TB_MEDICALCERT", "MC_ID" };
	}

}
