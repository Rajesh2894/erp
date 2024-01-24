package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
@JsonDeserialize
public class LinkCcnDTO implements Serializable {

	private static final long serialVersionUID = 7894731950871074003L;

	private TbCsmrInfoDTO csIdn;

	private String lcOldccn;

	private Long lcOldccnsize;

	private Long lcOldtaps;

	private long lcId;

	private long orgIds;

	private long userIds;

	private int langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String isDeleted;

	private String ccnOutStandingAmt;

	public String getLcOldccn() {
		return lcOldccn;
	}

	public void setLcOldccn(final String lcOldccn) {
		this.lcOldccn = lcOldccn;
	}

	public Long getLcOldccnsize() {
		return lcOldccnsize;
	}

	public void setLcOldccnsize(final Long lcOldccnsize) {
		this.lcOldccnsize = lcOldccnsize;
	}

	public Long getLcOldtaps() {
		return lcOldtaps;
	}

	public void setLcOldtaps(final Long lcOldtaps) {
		this.lcOldtaps = lcOldtaps;
	}

	public long getLcId() {
		return lcId;
	}

	public void setLcId(final long lcId) {
		this.lcId = lcId;
	}

	public long getOrgIds() {
		return orgIds;
	}

	public void setOrgIds(final long orgIds) {
		this.orgIds = orgIds;
	}

	public long getUserIds() {
		return userIds;
	}

	public void setUserIds(final long userIds) {
		this.userIds = userIds;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(final int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public TbCsmrInfoDTO getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final TbCsmrInfoDTO csIdn) {
		this.csIdn = csIdn;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(final String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCcnOutStandingAmt() {
		return ccnOutStandingAmt;
	}

	public void setCcnOutStandingAmt(String ccnOutStandingAmt) {
		this.ccnOutStandingAmt = ccnOutStandingAmt;
	}

}
