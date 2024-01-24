package com.abm.mainet.bnd.domain;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name="tb_deceased_history")
public class DeceasedMasterHistory implements Serializable
{
	
	private static final long serialVersionUID = 67162209823655236L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DEC_HI_ID", precision = 12, scale = 0, nullable = false)
	//comments : Deceased id
	private Long			 decHiId;
	

	@Column(name = "DR_HI_ID")
	private Long drHiId;
	
	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long			 orgId;
	
	@Column(name = "USER_ID", nullable = false, updatable = false)
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

	@Column(name = "LMODDATE", nullable = false)//
	//comments : Last Modification Date
	private Date			 lmodDate;

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

	@Column(name = "ACTION")
	private String			 action;
	
	
	public Long getDecHiId() {
		return decHiId;
	}

	public void setDecHiId(Long decHiId) {
		this.decHiId = decHiId;
	}

	public Long getDrHiId() {
		return drHiId;
	}

	public void setDrHiId(Long drHiId) {
		this.drHiId = drHiId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
	
	
	public String[] getPkValues() {
        return new String[] { "HD", "tb_deceased_history", "DEC_HI_ID" };
    }
	
	
}
