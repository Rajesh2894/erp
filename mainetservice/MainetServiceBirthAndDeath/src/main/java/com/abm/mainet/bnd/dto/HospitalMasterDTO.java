package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.NamedQuery;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;


@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
@NamedQuery(name="TbHospInst.findAll", query="SELECT t FROM TbHospInst t")
public class HospitalMasterDTO implements Serializable {
	
	private static final long serialVersionUID = -2498307488575123462L;
	private Long agencyId;
	private Long authBy;
	private Date authDate;
	private String authStatus;
	private Long cpdTypeId;
	private String fileName;
	private String filePath;
	private String hiAddr;
	private String hiAddrMar;
	private String hiCode;
	private String hiName;
	private String hiNameMar;
	private String hiStatus;
	private int langId;
	private Long hiId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private Long orgid;
	private Long userId;
	private String cpdDesc;
	private String cpdDescMar;
	
	

	public String getCpdDesc() {
		return cpdDesc;
	}
	public void setCpdDesc(String cpdDesc) {
		this.cpdDesc = cpdDesc;
	}
	public String getCpdDescMar() {
		return cpdDescMar;
	}
	public void setCpdDescMar(String cpdDescMar) {
		this.cpdDescMar = cpdDescMar;
	}
	
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public Long getAuthBy() {
		return authBy;
	}
	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}
	public Date getAuthDate() {
		return authDate;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public Long getCpdTypeId() {
		return cpdTypeId;
	}
	public void setCpdTypeId(Long cpdTypeId) {
		this.cpdTypeId = cpdTypeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getHiAddr() {
		return hiAddr;
	}
	public void setHiAddr(String hiAddr) {
		this.hiAddr = hiAddr;
	}
	public String getHiAddrMar() {
		return hiAddrMar;
	}
	public void setHiAddrMar(String hiAddrMar) {
		this.hiAddrMar = hiAddrMar;
	}
	public String getHiCode() {
		return hiCode;
	}
	public void setHiCode(String hiCode) {
		this.hiCode = hiCode;
	}
	public String getHiName() {
		return hiName;
	}
	public void setHiName(String hiName) {
		this.hiName = hiName;
	}
	public String getHiNameMar() {
		return hiNameMar;
	}
	public void setHiNameMar(String hiNameMar) {
		this.hiNameMar = hiNameMar;
	}
	public String getHiStatus() {
		return hiStatus;
	}
	public void setHiStatus(String hiStatus) {
		this.hiStatus = hiStatus;
	}
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
	}
	public Long getHiId() {
		return hiId;
	}
	public void setHiId(Long hiId) {
		this.hiId = hiId;
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
	public Date getLmoddate() {
		return lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	public Long getOrgid() {
		return orgid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public static Long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}