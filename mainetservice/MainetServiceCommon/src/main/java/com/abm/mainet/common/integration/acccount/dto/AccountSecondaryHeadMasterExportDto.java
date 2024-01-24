package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

public class AccountSecondaryHeadMasterExportDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String primaryHead;
	private String ledgerType;
	private String function;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((ledgerType == null) ? 0 : ledgerType.hashCode());
		result = prime * result + ((primaryHead == null) ? 0 : primaryHead.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountSecondaryHeadMasterExportDto other = (AccountSecondaryHeadMasterExportDto) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (ledgerType == null) {
			if (other.ledgerType != null)
				return false;
		} else if (!ledgerType.equals(other.ledgerType))
			return false;
		if (primaryHead == null) {
			if (other.primaryHead != null)
				return false;
		} else if (!primaryHead.equals(other.primaryHead))
			return false;
		return true;
	}
	private String description;
	private String oldSachHeadCode;
	private Long orgid;
	private Long userId;
	private Long langId;
	private Date lmoddate;
	private String field;
	private String fund;
	@Size(max = 100)
    private String lgIpMac;
	private String secondaryStatus;
	

	public String getPrimaryHead() {
		return primaryHead;
	}
	public void setPrimaryHead(String primaryHead) {
		this.primaryHead = primaryHead;
	}
	public String getLedgerType() {
		return ledgerType;
	}
	public void setLedgerType(String ledgerType) {
		this.ledgerType = ledgerType;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public Long getLangId() {
		return langId;
	}
	public void setLangId(Long langId) {
		this.langId = langId;
	}
	public Date getLmoddate() {
		return lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	public String getLgIpMac() {
		return lgIpMac;
	}
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}
	public String getSecondaryStatus() {
		return secondaryStatus;
	}
	public void setSecondaryStatus(String secondaryStatus) {
		this.secondaryStatus = secondaryStatus;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getOldSachHeadCode() {
		return oldSachHeadCode;
	}
	public void setOldSachHeadCode(String oldSachHeadCode) {
		this.oldSachHeadCode = oldSachHeadCode;
	}
	
}
