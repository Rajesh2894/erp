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
@Table(name="TB_DEATHCAUSE_MAS")
public class DeathCauseMaster implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5762117346016771991L;

	@Id
	@GenericGenerator(name="MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator="MyCustomGenerator")
	@Column(name = "DCM_ID", precision = 22, scale = 0, nullable = false)
	//comments : Generated Id
	private long			 dcmId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long			 orgId;


	@Column(name = "DC_DESC", length = 300, nullable = false)
	//comments : Description of death cause
	private String			 dcDesc;

	@Column(name = "DC_PARENTID", precision = 22, scale = 0, nullable = true)
	//comments : Parent Death cause Id
	private Long			 dcParentid;

	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long			 userId;

	@Column(name = "LANG_ID", precision = 22, scale = 0, nullable = false)
	//comments : Language Identity
	private int			 langId;

	@Column(name = "LMODDATE", nullable = false)
	//comments : Last Modification Date
	private Date			 lmodDate;

	@Column(name = "DC_SRNO", length = 5, nullable = true)
	//comments : Not in use
	private String			 dcSrno;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	//comments : Client Machines Login Name | IP Address | Physical Address
	private String			 lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	//comments : Updated Client Machines Login Name | IP Address | Physical Address
	private String			 lgIpMacUpd;

	@Column(name = "DC_DESC_REG", length = 300, nullable = true)
	//comments : Description of death cause in regional
	private String			 dcDescReg;
	
	@Column(name = "STATUS", length = 1, nullable = true)
	private String			 status;

	public long getDcmId() {
		return dcmId;
	}

	public void setDcmId(long dcmId) {
		this.dcmId = dcmId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getDcDesc() {
		return dcDesc;
	}

	public void setDcDesc(String dcDesc) {
		this.dcDesc = dcDesc;
	}

	public Long getDcParentid() {
		return dcParentid;
	}

	public void setDcParentid(Long dcParentid) {
		this.dcParentid = dcParentid;
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

	public String getDcSrno() {
		return dcSrno;
	}

	public void setDcSrno(String dcSrno) {
		this.dcSrno = dcSrno;
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

	public String getDcDescReg() {
		return dcDescReg;
	}

	public void setDcDescReg(String dcDescReg) {
		this.dcDescReg = dcDescReg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	

	}


	
		
	
	


