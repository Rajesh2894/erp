/**
 * 
 */
package com.abm.mainet.common.domain;

import java.io.Serializable;
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

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author divya.marshettiwar
 *
 */
@Entity
@Table(name = "tb_contract_instalment_detail_hist")
public class ContractInstalmentDetailHistEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CONIT_ID_H", precision = 12, scale = 0, nullable = false)
	private long conitIdH;
	
	@Column(name = "CONIT_ID", precision = 12, scale = 0, nullable = false)
	private long conitId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONT_ID", nullable = false)
	private ContractMastEntity contId;
	
	@Column(name = "CONIT_AMT_TYPE", precision = 12, scale = 0, nullable = false)
	private Long conitAmtType;
	
	@Column(name = "CONIT_VALUE", precision = 15, scale = 2, nullable = false)
	private Double conitValue;
	
	@Column(name = "CONIT_DUE_DATE", nullable = false)
	private Date conitDueDate;
	
	@Column(name = "CONIT_MILESTONE", length = 500, nullable = true)
	private String conitMilestone;

	@Column(name = "CONTT_ACTIVE", length = 1, nullable = false)
	private String conttActive;

	@Column(name = "CONIT_PR_FLAG", length = 1, nullable = true)
	private String conitPrFlag;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "CONIT_AMT", precision = 15, scale = 2, nullable = false)
	private Double conitAmount;

	@Column(name = "TAX_ID", nullable = false)
	private Long taxId;

	@Column(name = "CONIT_START_DATE", nullable = false)
	private Date conitStartDate;
	
	@Column(name = "H_STATUS", length = 1, nullable = true)
    private String hStatus;
	
	public String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, "tb_contract_instalment_detail_hist","CONIT_ID_H" };
	}

	public long getConitIdH() {
		return conitIdH;
	}

	public void setConitIdH(long conitIdH) {
		this.conitIdH = conitIdH;
	}

	public long getConitId() {
		return conitId;
	}

	public void setConitId(long conitId) {
		this.conitId = conitId;
	}

	public ContractMastEntity getContId() {
		return contId;
	}

	public void setContId(ContractMastEntity contId) {
		this.contId = contId;
	}

	public Long getConitAmtType() {
		return conitAmtType;
	}

	public void setConitAmtType(Long conitAmtType) {
		this.conitAmtType = conitAmtType;
	}

	public Double getConitValue() {
		return conitValue;
	}

	public void setConitValue(Double conitValue) {
		this.conitValue = conitValue;
	}

	public Date getConitDueDate() {
		return conitDueDate;
	}

	public void setConitDueDate(Date conitDueDate) {
		this.conitDueDate = conitDueDate;
	}

	public String getConitMilestone() {
		return conitMilestone;
	}

	public void setConitMilestone(String conitMilestone) {
		this.conitMilestone = conitMilestone;
	}

	public String getConttActive() {
		return conttActive;
	}

	public void setConttActive(String conttActive) {
		this.conttActive = conttActive;
	}

	public String getConitPrFlag() {
		return conitPrFlag;
	}

	public void setConitPrFlag(String conitPrFlag) {
		this.conitPrFlag = conitPrFlag;
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

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
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

	public Double getConitAmount() {
		return conitAmount;
	}

	public void setConitAmount(Double conitAmount) {
		this.conitAmount = conitAmount;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(Long taxId) {
		this.taxId = taxId;
	}

	public Date getConitStartDate() {
		return conitStartDate;
	}

	public void setConitStartDate(Date conitStartDate) {
		this.conitStartDate = conitStartDate;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
}
