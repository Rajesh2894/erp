package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

import com.abm.mainet.common.domain.Employee;

/**
 * @author Jeetendra.Pal
 * @since 19 Jan 2016
 * @comment This table is used to store link between the old CCN CCN.
 */

public class TbKLinkCcnDTO implements Serializable {
	private static final long serialVersionUID = 5217074219062110984L;

	private long lcId;

	private TbCsmrInfoDTO csIdn;

	private String lcOldccn;

	private Long lcOldccnsize;

	private Long lcOldtaps;

	private long orgIds;

	private long userIds;

	private int langId;

	private Date lmodDate;

	private Employee updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String isDeleted;
	private String ccnOutStandingAmt;
	
	private Integer noOfFamilies;

	public long getLcId() {
		return lcId;
	}

	public void setLcId(final long lcId) {
		this.lcId = lcId;
	}

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

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(final Employee updatedBy) {
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

	public TbCsmrInfoDTO getCsIdn() {
		return csIdn;
	}

	public void setCsIdn(final TbCsmrInfoDTO csIdn) {
		this.csIdn = csIdn;
	}

	/**
	 * @return the isDeleted
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(final String isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final TbKLinkCcnDTO other = (TbKLinkCcnDTO) obj;
		if (lcId != other.lcId) {
			return false;
		}
		return true;
	}

	public String getCcnOutStandingAmt() {
		return ccnOutStandingAmt;
	}

	public void setCcnOutStandingAmt(String ccnOutStandingAmt) {
		this.ccnOutStandingAmt = ccnOutStandingAmt;
	}

	public Integer getNoOfFamilies() {
		return noOfFamilies;
	}

	public void setNoOfFamilies(Integer noOfFamilies) {
		this.noOfFamilies = noOfFamilies;
	}

	
	
	

}