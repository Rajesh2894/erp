package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SFAC_ABS_SUB_PARAM_DET")
public class AuditBalanceSheetSubParameterDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4603555995520256368L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ABS_SP_ID", nullable = false)
	private Long absSpdId;

	@ManyToOne
	@JoinColumn(name = "ABSS_ID", referencedColumnName = "ABSS_ID")
	AuditBalanceSheetSubParameterEntity absSubParamEntity;

	@Column(name = "CONDITIONS")
	private Long condition;

	@Column(name = "CONDITION_DESC")
	private String conditionDesc;

	@Column(name = "CRP_AMOUNT")
	private BigDecimal cprAmount;

	@Column(name = "PRP_AMOUNT")
	private BigDecimal prpAmount;

	

	@Column(name = "ORGID", nullable = false)
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

	

	public Long getAbsSpdId() {
		return absSpdId;
	}

	public void setAbsSpdId(Long absSpdId) {
		this.absSpdId = absSpdId;
	}

	public AuditBalanceSheetSubParameterEntity getAbsSubParamEntity() {
		return absSubParamEntity;
	}

	public void setAbsSubParamEntity(AuditBalanceSheetSubParameterEntity absSubParamEntity) {
		this.absSubParamEntity = absSubParamEntity;
	}

	public Long getCondition() {
		return condition;
	}

	public void setCondition(Long condition) {
		this.condition = condition;
	}

	public String getConditionDesc() {
		return conditionDesc;
	}

	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	public BigDecimal getCprAmount() {
		return cprAmount;
	}

	public void setCprAmount(BigDecimal cprAmount) {
		this.cprAmount = cprAmount;
	}

	public BigDecimal getPrpAmount() {
		return prpAmount;
	}

	public void setPrpAmount(BigDecimal prpAmount) {
		this.prpAmount = prpAmount;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "SFAC", "TB_SFAC_ABS_SUB_PARAM_DET", "ABS_SP_ID" };
	}


}
