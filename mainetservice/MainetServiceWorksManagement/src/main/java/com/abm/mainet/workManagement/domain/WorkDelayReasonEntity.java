package com.abm.mainet.workManagement.domain;

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

/**
 * @author sadik.shaikh
 *
 */
@Entity
@Table(name = "tb_wms_delay_res")
public class WorkDelayReasonEntity implements Serializable{

	private static final long serialVersionUID = -5677564113146893293L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DEL_RESID", nullable = false)
	private Long delResId;
	
	@Column(name = "PROJ_ID", nullable = false)
	private Long projId;
	
	@Column(name = "WORK_ID", nullable = false)
	private Long workId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_OCCURENCE", nullable = false)
	private Date dateOccurance;
	
	@Column(name = "DATE_CLEARANCE", nullable = true)
	private Date dateClearance;
	
	@Column(name = "PERIOD", nullable = true)
	private Long period;
	
	@Column(name = "OVERLAPPING_PER", nullable = true)
	private String overPeriod;
	
	@Column(name = "WEIGHT_HINDERANCE", nullable = false)
	private Double weigtHind;
	
	@Column(name = "DAY_HINDERANCE", nullable = true)
	private Double dayHindrnc;
	
	@Column(name = "REMARK", nullable = false)
	private String remark;
	
	@Column(name = "SITE_ENGID", nullable = false)
	private String siteEngId;
	
	@Column(name = "CONT_EMPNAME", nullable = false)
	private String contEmpName;
	
	@Column(name = "STATUS", nullable = false)
	private String status;
	
	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	@Column(name = "NAT_HINDRNC", nullable = false)
	private String natOfHindrnc;

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
	
	public static String[] getPkValues() {
		return new String[] { "WMS", "tb_wms_delay_res", "DEL_RESID" };
	}
	
}
