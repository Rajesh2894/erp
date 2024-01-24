package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "vw_ac_voucher_det")
public class ViewAccountVoucherEntryDetailsEntity {

	@Id
	@Column(name = "VOUDET_ID", precision = 12, scale = 0, nullable = false)
	private long voudetId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VOU_ID", nullable = true)
	private ViewAccountVoucherEntryEntity master;

	@Column(name = "SAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
	private Long sacHeadId;

	@Column(name = "VOUDET_AMT", precision = 15, scale = 2, nullable = true)
	private BigDecimal voudetAmt;

	/*@Column(name = "DRCR_CPD_ID", precision = 15, scale = 0, nullable = true)
	private Long drcrCpdId;*/

	/*@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;
	
	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;*/

	/*@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;*/

	public String[] getPkValues() {
		return new String[] { "AC", "TB_AC_VOUCHER_DET", "VOUDET_ID" };
	}

	/**
	 * @return the voudetId
	 */
	public long getVoudetId() {
		return voudetId;
	}

	/**
	 * @param voudetId
	 *            the voudetId to set
	 */
	public void setVoudetId(final long voudetId) {
		this.voudetId = voudetId;
	}

	/**
	 * @return the voudetAmt
	 */
	public BigDecimal getVoudetAmt() {
		return voudetAmt;
	}

	/**
	 * @param voudetAmt
	 *            the voudetAmt to set
	 */
	public void setVoudetAmt(final BigDecimal voudetAmt) {
		this.voudetAmt = voudetAmt;
	}

/*	*//**
	 * @return the drcrCpdId
	 *//*
	public Long getDrcrCpdId() {
		return drcrCpdId;
	}

	*//**
	 * @param drcrCpdId
	 *            the drcrCpdId to set
	 *//*
	public void setDrcrCpdId(final Long drcrCpdId) {
		this.drcrCpdId = drcrCpdId;
	}*/

	/**
	 * @return the orgId
	 */
/*	public Long getOrgId() {
		return orgId;
	}

	*//**
	 * @param orgId
	 *            the orgId to set
	 *//*
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}
*/
	public Long getSacHeadId() {
		return sacHeadId;
	}

	public void setSacHeadId(final Long sacHeadId) {
		this.sacHeadId = sacHeadId;
	}

	/*public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}*/
	
	

}
