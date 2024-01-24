package com.abm.mainet.water.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jeetendra.Pal
 * @since 22 Jun 2016
 * @comment This table is used to store acadamic qualification details of the
 *          plumber
 */

public class PlumberQualificationDTO implements Serializable {

	private static final long serialVersionUID = -6040396882305266610L;

	private long plumQualId;

	private Long plumId;

	private Long plumQualification;

	private Date plumPassMonth;

	private Date plumPassYear;

	private String plumPercentGrade;

	private String plumInstituteName;

	private String plumInstituteAddress;

	private Long orgId;

	private Long userId;

	private int langId;

	private Date lmodDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	public long getPlumQualId() {
		return plumQualId;
	}

	public void setPlumQualId(final long plumQualId) {
		this.plumQualId = plumQualId;
	}

	public Long getPlumId() {
		return plumId;
	}

	public void setPlumId(final Long plumId) {
		this.plumId = plumId;
	}

	public String getPlumPercentGrade() {
		return plumPercentGrade;
	}

	public void setPlumPercentGrade(final String plumPercentGrade) {
		this.plumPercentGrade = plumPercentGrade;
	}

	public String getPlumInstituteName() {
		return plumInstituteName;
	}

	public void setPlumInstituteName(final String plumInstituteName) {
		this.plumInstituteName = plumInstituteName;
	}

	public String getPlumInstituteAddress() {
		return plumInstituteAddress;
	}

	public void setPlumInstituteAddress(final String plumInstituteAddress) {
		this.plumInstituteAddress = plumInstituteAddress;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
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

	public Long getPlumQualification() {
		return plumQualification;
	}

	public void setPlumQualification(Long plumQualification) {
		this.plumQualification = plumQualification;
	}

	public Date getPlumPassMonth() {
		return plumPassMonth;
	}

	public void setPlumPassMonth(Date plumPassMonth) {
		this.plumPassMonth = plumPassMonth;
	}

	public Date getPlumPassYear() {
		return plumPassYear;
	}

	public void setPlumPassYear(Date plumPassYear) {
		this.plumPassYear = plumPassYear;
	}

}