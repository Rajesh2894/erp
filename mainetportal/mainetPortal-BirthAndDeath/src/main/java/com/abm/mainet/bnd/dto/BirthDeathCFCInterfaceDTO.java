package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

public class BirthDeathCFCInterfaceDTO implements Serializable {

	private static final long serialVersionUID = -4100833798784520499L;

	private long bdCfcIntfId;
	private Long orgId;
	private Long apmApplicationId;
	private Long smServiceId;
	private Long bdRequestId;
	private Long userId;
	private int langId;
	private Date lmodDate;
	private String lParameter;
	private Long copies;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	public long getBdCfcIntfId() {
		return bdCfcIntfId;
	}

	public void setBdCfcIntfId(long bdCfcIntfId) {
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

}
