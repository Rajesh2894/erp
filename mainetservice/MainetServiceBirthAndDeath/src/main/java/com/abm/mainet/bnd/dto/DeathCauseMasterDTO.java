package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;



public class DeathCauseMasterDTO implements Serializable{
	
	private static final long serialVersionUID = 5762117346016771991L;

	private long			 dcmId;
	private Long			 orgId;
	private String			 dcDesc;
	private Long			 dcParentid;
	private Long			 userId;
	private int			    langId;
	private Date			 lmodDate;
	private String			 dcSrno;
	private String			 lgIpMac;
	private String			 lgIpMacUpd;
	private String			 dcDescReg;
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
