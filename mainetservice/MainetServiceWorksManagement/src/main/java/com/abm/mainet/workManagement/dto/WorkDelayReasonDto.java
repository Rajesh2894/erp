package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sadik.shaikh
 *
 */
public class WorkDelayReasonDto implements Serializable {

	private static final long serialVersionUID = 5479603370231866176L;

	private Long delResId;

	private Long projId;

	private Long workId;

	private Date dateOccurance;

	private Date dateClearance;

	private Long period;

	private String overPeriod;

	private Double weigtHind;

	private Double dayHindrnc;

	private String remark;

	private String siteEngId;

	private String contEmpName;

	private String status;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private Long updatedBy;

	private Date updatedDate;

	private String natOfHindrnc;

	private String flagForHist;

	private String projName;

	private String workName;

	private String dateClearanceDesc;
	
	private String dateOccuranceDesc;
	
	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getFlagForHist() {
		return flagForHist;
	}

	public void setFlagForHist(String flagForHist) {
		this.flagForHist = flagForHist;
	}

	public Long getDelResId() {
		return delResId;
	}

	public void setDelResId(Long delResId) {
		this.delResId = delResId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getWorkId() {
		return workId;
	}

	public void setWorkId(Long workId) {
		this.workId = workId;
	}

	public Date getDateOccurance() {
		return dateOccurance;
	}

	public void setDateOccurance(Date dateOccurance) {
		this.dateOccurance = dateOccurance;
	}

	public Date getDateClearance() {
		return dateClearance;
	}

	public void setDateClearance(Date dateClearance) {
		this.dateClearance = dateClearance;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public String getOverPeriod() {
		return overPeriod;
	}

	public void setOverPeriod(String overPeriod) {
		this.overPeriod = overPeriod;
	}

	public Double getWeigtHind() {
		return weigtHind;
	}

	public void setWeigtHind(Double weigtHind) {
		this.weigtHind = weigtHind;
	}

	public Double getDayHindrnc() {
		return dayHindrnc;
	}

	public void setDayHindrnc(Double dayHindrnc) {
		this.dayHindrnc = dayHindrnc;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSiteEngId() {
		return siteEngId;
	}

	public void setSiteEngId(String siteEngId) {
		this.siteEngId = siteEngId;
	}

	public String getContEmpName() {
		return contEmpName;
	}

	public void setContEmpName(String contEmpName) {
		this.contEmpName = contEmpName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public String getNatOfHindrnc() {
		return natOfHindrnc;
	}

	public void setNatOfHindrnc(String natOfHindrnc) {
		this.natOfHindrnc = natOfHindrnc;
	}

	public String getDateClearanceDesc() {
		return dateClearanceDesc;
	}

	public void setDateClearanceDesc(String dateClearanceDesc) {
		this.dateClearanceDesc = dateClearanceDesc;
	}

	public String getDateOccuranceDesc() {
		return dateOccuranceDesc;
	}

	public void setDateOccuranceDesc(String dateOccuranceDesc) {
		this.dateOccuranceDesc = dateOccuranceDesc;
	}

	
}
