package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_DECEASED_TEMP")
public class DeceasedMasterTemp implements Serializable{


	private static final long serialVersionUID = 67162209823655236L;
	
	@Id
	@Column(name = "DEC_ID", precision = 12,  nullable = false)
	//comments : Deceased id
	private Long			 decId;

	/*@Column(name = "DR_ID")
	private Long drId;*/
	
	@OneToOne(fetch = FetchType.LAZY) //,cascade = {CascadeType.ALL})
	@JoinColumn(name = "DR_ID",  nullable = true)
	private TbDeathregTemp			 drId;
	
	
	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long			 orgId;
	
	@Column(name = "USER_ID", nullable = false)
	private Long			 userId;
	
	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long		 	 updatedBy;
	
	@Column(name = "DEC_SMOKER", length = 1, nullable = true)
	//comments : Whether the person was a smoker?
	private String			 decSmoker;

	@Column(name = "DEC_SMOKER_YR", precision = 3, scale = 0, nullable = true)
	//comments : No. of years he smoked?
	private Long			 decSmokerYr;

	@Column(name = "DEC_CHEWTB", length = 1, nullable = true)
	//comments : Whether used to chew tobacco?
	private String			 decChewtb;

	@Column(name = "DEC_CHEWTB_YR", precision = 3, scale = 0, nullable = true)
	//comments : No. of years he chewed tobacco?
	private Long			 decChewtbYr;

	@Column(name = "DEC_CHEWARAC", length = 1, nullable = true)
	//comments : Whether used to chew Aracanut
	private String			 decChewarac;

	@Column(name = "DEC_CHEWARAC_YR", precision = 3, scale = 0, nullable = true)
	//comments : No. of years he chewed aracanut?
	private Long			 decChewaracYr;

	@Column(name = "DEC_ALCOHOLIC", length = 1, nullable = true)
	//comments : Was an alcoholic?
	private String			 decAlcoholic;

	@Column(name = "DEC_ALCOHOLIC_YR", precision = 3, scale = 0, nullable = true)
	//comments : No. of years he was alcoholic?
	private Long			 decAlcoholicYr;

	
	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	//comments : Language id
	private int			 langId;

	@Column(name = "LMODDATE", nullable = true)//
	//comments : Last Modification Date
	private Date			 lmoddate;

	@Column(name = "DEC_REMARKS", length = 200, nullable = true)
	//comments : Remarks
	private String			 decRemarks;


	@Column(name = "UPDATED_DATE", nullable = true)
	//comments : Updated date
	private Date			 updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	//comments : Client MachineÂ’s Login Name | IP Address | Physical Address
	private String			 lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	//comments : Updated Client MachineÂ’s Login Name | IP Address | Physical Address
	private String			 lgIpMacUpd;

	

	public Long getDecId() {
		return decId;
	}

	public void setDecId(Long decId) {
		this.decId = decId;
	}

	public TbDeathregTemp getDrId() {
		return drId;
	}

	public void setDrId(TbDeathregTemp drId) {
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

	public String getDecChewtb() {
		return decChewtb;
	}

	public void setDecChewtb(String decChewtb) {
		this.decChewtb = decChewtb;
	}

	public Long getDecChewtbYr() {
		return decChewtbYr;
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


	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
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
	
	
	public String[] getPkValues() {
        return new String[] { "HD", "TB_DECEASED_TEMP", "DEC_ID" };
    }
}
