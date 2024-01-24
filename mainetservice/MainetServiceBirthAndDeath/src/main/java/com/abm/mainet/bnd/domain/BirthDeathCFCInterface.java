package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BD_CFC_INTERFACE")
public class BirthDeathCFCInterface implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BD_CFC_INTF_ID", precision = 12, scale = 0, nullable = false)
	private Long bdCfcIntfId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
	private Long apmApplicationId;

	@Column(name = "SM_SERVICE_ID")//, precision = 12, scale = 0, nullable = false
	private Long smServiceId;

	@Column(name = "BD_REQUEST_ID", precision = 12, scale = 0, nullable = false)
	private Long bdRequestId;

	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	private int langId;

	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@Column(name = "L_PARAMETER", length = 1000, nullable = true)
	private String lParameter;

	@Column(name = "COPIES", precision = 3, scale = 0, nullable = true)
	private Long copies;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	public Long getBdCfcIntfId() {
		return bdCfcIntfId;
	}

	public void setBdCfcIntfId(Long bdCfcIntfId) {
		this.bdCfcIntfId = bdCfcIntfId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getBdRequestId() {
		return bdRequestId;
	}

	public void setBdRequestId(Long bdRequestId) {
		this.bdRequestId = bdRequestId;
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

	public String getlParameter() {
		return lParameter;
	}

	public void setlParameter(String lParameter) {
		this.lParameter = lParameter;
	}

	public Long getCopies() {
		return copies;
	}

	public void setCopies(Long copies) {
		this.copies = copies;
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
	
	public String[] getPkValues() {
		return new String[] { "HD", "TB_BD_CFC_INTERFACE", "BD_CFC_INTF_ID" };
	}

}