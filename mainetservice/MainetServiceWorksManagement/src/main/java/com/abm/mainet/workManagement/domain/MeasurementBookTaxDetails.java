/**
 * 
 */
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

import com.abm.mainet.common.domain.TbTaxMasEntity;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MEASUREMENTBOOK_TAXD")
public class MeasurementBookTaxDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3411393295684752794L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MB_TAXID", nullable = false)
	private Long mbTaxId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MB_ID")
	private MeasurementBookMaster mbMaster;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAX_ID")
	private TbTaxMasEntity tbTaxMaster;

	@Column(name = "MB_TAXTYPE", nullable = false)
	private String mbTaxType;

	@Column(name = "MB_TAXFACT", nullable = false)
	private BigDecimal mbTaxFact;
	
	@Column(name = "MB_TAXVALUE", nullable = true)
	private BigDecimal mbTaxValue;

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

	public Long getMbTaxId() {
		return mbTaxId;
	}

	public void setMbTaxId(Long mbTaxId) {
		this.mbTaxId = mbTaxId;
	}

	public MeasurementBookMaster getMbMaster() {
		return mbMaster;
	}

	public void setMbMaster(MeasurementBookMaster mbMaster) {
		this.mbMaster = mbMaster;
	}

	public TbTaxMasEntity getTbTaxMaster() {
		return tbTaxMaster;
	}

	public void setTbTaxMaster(TbTaxMasEntity tbTaxMaster) {
		this.tbTaxMaster = tbTaxMaster;
	}

	public String getMbTaxType() {
		return mbTaxType;
	}

	public void setMbTaxType(String mbTaxType) {
		this.mbTaxType = mbTaxType;
	}

	public BigDecimal getMbTaxFact() {
		return mbTaxFact;
	}

	public void setMbTaxFact(BigDecimal mbTaxFact) {
		this.mbTaxFact = mbTaxFact;
	}

	public BigDecimal getMbTaxValue() {
		return mbTaxValue;
	}

	public void setMbTaxValue(BigDecimal mbTaxValue) {
		this.mbTaxValue = mbTaxValue;
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
	
	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MEASUREMENTBOOK_TAXD", "MB_TAXID" };
	}
}
