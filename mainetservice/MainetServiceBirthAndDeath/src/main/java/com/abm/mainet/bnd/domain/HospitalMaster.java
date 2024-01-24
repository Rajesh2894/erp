package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_HOSP_INST")
public class HospitalMaster implements Serializable {

	
	private static final long serialVersionUID = 4904163124181302322L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "HI_ID", nullable = false, precision = 10)
	private Long hiId;

	@Column(name = "AGENCY_ID", precision = 10)
	private Long agencyId;

	@Column(name = "AUTH_BY", precision = 10)
	private Long authBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AUTH_DATE")
	private Date authDate;

	@Column(name = "AUTH_STATUS", length = 2)
	private String authStatus;

	@Column(name = "CPD_TYPE_ID", nullable = false, precision = 10)
	private Long cpdTypeId;

	@Column(name = "FILE_NAME", length = 400)
	private String fileName;

	@Column(name = "FILE_PATH", length = 400)
	private String filePath;

	@Column(name = "HI_ADDR", length = 200)
	private String hiAddr;

	@Column(name = "HI_ADDR_MAR", length = 400)
	private String hiAddrMar;

	@Column(name = "HI_CODE", length = 3)
	private String hiCode;

	@Column(name = "HI_NAME", length = 200)
	private String hiName;

	@Column(name = "HI_NAME_MAR", length = 400)
	private String hiNameMar;

	@Column(name = "HI_STATUS", nullable = false, length = 2)
	private String hiStatus;

	@Column(name = "LANG_ID", nullable = false, precision = 10)
	private Long langId;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date lmoddate;

	@Column(nullable = false, precision = 10)
	private Long orgid;

	@Column(name = "USER_ID", nullable = false, precision = 10)
	private Long userId;

	public HospitalMaster() {
	}

	public Long getAgencyId() {
		return this.agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Long getAuthBy() {
		return this.authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public Date getAuthDate() {
		return this.authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

	public String getAuthStatus() {
		return this.authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Long getCpdTypeId() {
		return this.cpdTypeId;
	}

	public void setCpdTypeId(Long cpdTypeId) {
		this.cpdTypeId = cpdTypeId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getHiAddr() {
		return this.hiAddr;
	}

	public void setHiAddr(String hiAddr) {
		this.hiAddr = hiAddr;
	}

	public String getHiAddrMar() {
		return this.hiAddrMar;
	}

	public void setHiAddrMar(String hiAddrMar) {
		this.hiAddrMar = hiAddrMar;
	}

	public String getHiCode() {
		return this.hiCode;
	}

	public void setHiCode(String hiCode) {
		this.hiCode = hiCode;
	}

	public Long getHiId() {
		return this.hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public String getHiName() {
		return this.hiName;
	}

	public void setHiName(String hiName) {
		this.hiName = hiName;
	}

	public String getHiNameMar() {
		return this.hiNameMar;
	}

	public void setHiNameMar(String hiNameMar) {
		this.hiNameMar = hiNameMar;
	}

	public String getHiStatus() {
		return this.hiStatus;
	}

	public void setHiStatus(String hiStatus) {
		this.hiStatus = hiStatus;
	}

	public Long getLangId() {
		return this.langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getLmoddate() {
		return this.lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getOrgid() {
		return this.orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public static String[] getPkValues() {
		return new String[] { "HD", "TB_HOSP_INST", "HI_ID" };
	}
	
}