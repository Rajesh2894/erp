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
@Table(name = "tb_cemetery")
public class CemeteryMaster implements Serializable {

	private static final long serialVersionUID = 4904163124181302322L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CE_ID", nullable = false, precision = 10)
	private Long ceId;

	@Column(name = "AGENCY_ID", precision = 10)
	private Long agencyId;


	@Column(name = "CPD_TYPE_ID", nullable = false, precision = 10)
	private Long cpdTypeId;

	@Column(name = "CE_ADDR", length = 200)
	private String ceAddr;

	@Column(name = "CE_ADDR_MAR", length = 400)
	private String ceAddrMar;

	@Column(name = "CE_NAME", length = 200)
	private String ceName;

	@Column(name = "CE_NAME_MAR", length = 400)
	private String ceNameMar;

	@Column(name = "CE_STATUS", nullable = false, length = 2)
	private String ceStatus;

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
	
	
	@Column(name="UPDATED_BY")
	private Long updatedBy;

	@Column(name="UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "WARDID", precision = 12, scale = 0, nullable = true)
	private Long wardid;
	
	public Long getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Date getUpdatedDate() {
		return this.updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
		
	public CemeteryMaster() {
	}

	public Long getCeId() {
		return ceId;
	}

	public void setCeId(Long ceId) {
		this.ceId = ceId;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Long getCpdTypeId() {
		return cpdTypeId;
	}


	public void setCpdTypeId(Long cpdTypeId) {
		this.cpdTypeId = cpdTypeId;
	}

	public String getCeAddr() {
		return ceAddr;
	}

	public void setCeAddr(String ciAddr) {
		this.ceAddr = ciAddr;
	}

	public String getCeAddrMar() {
		return ceAddrMar;
	}

	public void setCeAddrMar(String ceAddrMar) {
		this.ceAddrMar = ceAddrMar;
	}
	
	public String getCeName() {
		return ceName;
	}

	public void setCeName(String ceName) {
		this.ceName = ceName;
	}

	public String getCeNameMar() {
		return ceNameMar;
	}

	public void setCeNameMar(String ceNameMar) {
		this.ceNameMar = ceNameMar;
	}

	public String getCeStatus() {
		return ceStatus;
	}

	public void setCeStatus(String ceStatus) {
		this.ceStatus = ceStatus;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Long getWardid() {
		return wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String[] getPkValues() {
		return new String[] { "HD", "tb_cemetery", "CE_ID" };
	}
	
	
}