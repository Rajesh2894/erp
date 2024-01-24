/**
 * 
 */
package com.abm.mainet.workManagement.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.workManagement.domain.MeasurementBookMaster;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
public class MeasurementBookTaxDetailsDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1152838127737145935L;

	private Long mbTaxId;
	
	private Long mbId;
	
	private Long taxId;

	private MeasurementBookMaster mbMaster;

	private TbTaxMasEntity tbTaxMaster;

	private String mbTaxType;

	private BigDecimal mbTaxFact;
	
	private BigDecimal mbTaxValue;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	public Long getMbTaxId() {
		return mbTaxId;
	}

	public void setMbTaxId(Long mbTaxId) {
		this.mbTaxId = mbTaxId;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
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
}
