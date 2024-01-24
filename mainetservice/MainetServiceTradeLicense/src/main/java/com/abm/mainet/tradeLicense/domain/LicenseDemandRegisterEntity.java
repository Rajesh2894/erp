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
@Table(name = "TN_ML_LIC_DEMAND_REG")
public class LicenseDemandRegisterEntity {
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "Lic_DMND_ID", nullable = false)
	private Long licDemandId;

	@Column(name = "TRD_BUSNM", nullable = false, length = 50)
	private String trdBusnm;

	@Column(name = "TRD_BUSADD", nullable = false, length = 200)
	private String trdBusadd;

	@Column(name = "TRD_OWNER_NAME", nullable = false, length = 100)
	private String trdOwnerName;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICTO_DATE")
	private Date trdLictoDate;
	@Column(name = "TRD_LICNO", nullable = false, length = 100)
	private String trdOldlicno;
	@Column(name = "Lic_EXPTED_REN_YR", nullable = false)
	private Long licexpRenYr;
	@Column(name = "Lic_PEND_REN_YR", nullable = false)
	private Long licPendRenYr;
	@Column(name = "CURR_YEAR_LIC_FEE", nullable = false)
	private Long currYrLicFee;
	@Column(name = "CURR_YEAR_LATE_FEE", nullable = false)
	private Long currYrLateFee;
	@Column(name = "TOT_REN_FEE", nullable = false)
	private Long totRenFee;
	@Column(name = "TOT_LATE_FEE", nullable = false)
	private Long totLateFee;
	@Column(name = "TOT_PEND_FEE", nullable = false)
	private Long totPendFee;
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
		return new String[] { "ML", "TN_ML_LIC_DEMAND_REG", "Lic_DMND_ID" };
	}

	public Date getTrdLictoDate() {
		return trdLictoDate;
	}

	public void setTrdLictoDate(Date trdLictoDate) {
		this.trdLictoDate = trdLictoDate;
	}

	public Long getLicDemandId() {
		return licDemandId;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public String getTrdOwnerName() {
		return trdOwnerName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public Long getLicexpRenYr() {
		return licexpRenYr;
	}

	public Long getLicPendRenYr() {
		return licPendRenYr;
	}

	public Long getCurrYrLicFee() {
		return currYrLicFee;
	}

	public Long getCurrYrLateFee() {
		return currYrLateFee;
	}

	public Long getTotRenFee() {
		return totRenFee;
	}

	public Long getTotLateFee() {
		return totLateFee;
	}

	public Long getTotPendFee() {
		return totPendFee;
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

	public void setLicDemandId(Long licDemandId) {
		this.licDemandId = licDemandId;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public void setTrdOwnerName(String trdOwnerName) {
		this.trdOwnerName = trdOwnerName;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public void setLicexpRenYr(Long licexpRenYr) {
		this.licexpRenYr = licexpRenYr;
	}

	public void setLicPendRenYr(Long licPendRenYr) {
		this.licPendRenYr = licPendRenYr;
	}

	public void setCurrYrLicFee(Long currYrLicFee) {
		this.currYrLicFee = currYrLicFee;
	}

	public void setCurrYrLateFee(Long currYrLateFee) {
		this.currYrLateFee = currYrLateFee;
	}

	public void setTotRenFee(Long totRenFee) {
		this.totRenFee = totRenFee;
	}

	public void setTotLateFee(Long totLateFee) {
		this.totLateFee = totLateFee;
	}

	public void setTotPendFee(Long totPendFee) {
		this.totPendFee = totPendFee;
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
