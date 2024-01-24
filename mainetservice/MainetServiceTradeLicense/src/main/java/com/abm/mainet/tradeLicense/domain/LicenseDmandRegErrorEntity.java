package com.abm.mainet.tradeLicense.domain;

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
@Table(name = "TB_ML_LIC_DMND_ERR")
public class LicenseDmandRegErrorEntity {
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "Lic_DMND_ERR_ID", nullable = false)
	private Long licDemandErrId;

	@Column(name = "TRD_ERR_MSG")
	private String trderrMsg;
	@Column(name = "TRD_LICNO")
	private String trdLicNo;
	@Column(name = "ORGID")
	private Long orgId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	public String[] getPkValues() {
		return new String[] { "ML", "TB_ML_LIC_DMND_ERR", "Lic_DMND_ERR_ID" };
	}

	public Long getLicDemandErrId() {
		return licDemandErrId;
	}

	public String getTrderrMsg() {
		return trderrMsg;
	}

	public String getTrdLicNo() {
		return trdLicNo;
	}

	public Long getOrgId() {
		return orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLicDemandErrId(Long licDemandErrId) {
		this.licDemandErrId = licDemandErrId;
	}

	public void setTrderrMsg(String trderrMsg) {
		this.trderrMsg = trderrMsg;
	}

	public void setTrdLicNo(String trdLicNo) {
		this.trdLicNo = trdLicNo;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}
}
