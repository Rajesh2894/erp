package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @author Jeetendra.Pal
 *
 */
@Entity
@Table(name = "TB_WMS_RABILL_TAXD")
public class WmsRaBillTaxDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3668199876064822178L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "RA_TAXID", nullable = false)
	private Long raTaxId;

	@Column(name = "TAX_ID", nullable = true)
	private Long taxId;

	@Column(name = "RA_TAXVALUE", nullable = true)
	private BigDecimal raTaxValue;

	@Column(name = "RA_TAXTYPE", nullable = true)
	private String raTaxType;

	@Column(name = "RA_TAXFACT", nullable = true)
	private Long raTaxFact;
	
	@Column(name = "RA_TAXPER", nullable = true)
	private BigDecimal raTaxPercent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RA_ID", nullable = true)
	private WorksRABill worksRABill;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "RA_REMARK ", nullable = true)
	private String raRemark;

	public Long getRaTaxId() {
		return raTaxId;
	}

	public void setRaTaxId(Long raTaxId) {
		this.raTaxId = raTaxId;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public BigDecimal getRaTaxPercent() {
		return raTaxPercent;
	}

	public void setRaTaxPercent(BigDecimal raTaxPercent) {
		this.raTaxPercent = raTaxPercent;
	}

	public BigDecimal getRaTaxValue() {
		return raTaxValue;
	}

	public void setRaTaxValue(BigDecimal raTaxValue) {
		this.raTaxValue = raTaxValue;
	}

	public String getRaTaxType() {
		return raTaxType;
	}

	public void setRaTaxType(String raTaxType) {
		this.raTaxType = raTaxType;
	}

	public Long getRaTaxFact() {
		return raTaxFact;
	}

	public void setRaTaxFact(Long raTaxFact) {
		this.raTaxFact = raTaxFact;
	}

	public WorksRABill getWorksRABill() {
		return worksRABill;
	}

	public void setWorksRABill(WorksRABill worksRABill) {
		this.worksRABill = worksRABill;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public String getRaRemark() {
		return raRemark;
	}

	public void setRaRemark(String raRemark) {
		this.raRemark = raRemark;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_RABILL_TAXD", "RA_TAXID" };
	}

}
