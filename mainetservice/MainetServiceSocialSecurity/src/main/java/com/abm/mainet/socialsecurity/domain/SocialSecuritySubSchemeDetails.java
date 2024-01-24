package com.abm.mainet.socialsecurity.domain;

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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_SWD_SUB_SCHEME_DET")
public class SocialSecuritySubSchemeDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SUBSDSCHD_ID", nullable = false)
	private Long subSchemeDtlId;

	@ManyToOne
	@JoinColumn(name = "SDSCH_ID", nullable = false)
	private SocialSecuritySchemeMaster socialSecuritySchemeMaster;

	@Column(name = "SDSCH_SUB_SER_ID", nullable = true)
    private Long subSchemeName;
	
	@Column(name = "NO_BENEFICIARY", nullable = true)
    private Long noBeneficiary;
	
	@Column(name = "AMOUNT", nullable = true)
    private Long amount;
	
	@Column(name = "TOTAL_AMOUNT", nullable = true)
    private Long totalAmount;
	

	@Column(name = "SDSCH_ACTIVE", nullable = false)
	private String isschemeDetActive;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = true)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;

	
	public Long getSubSchemeDtlId() {
		return subSchemeDtlId;
	}

	public void setSubSchemeDtlId(Long subSchemeDtlId) {
		this.subSchemeDtlId = subSchemeDtlId;
	}

	public SocialSecuritySchemeMaster getSocialSecuritySchemeMaster() {
		return socialSecuritySchemeMaster;
	}

	public void setSocialSecuritySchemeMaster(SocialSecuritySchemeMaster socialSecuritySchemeMaster) {
		this.socialSecuritySchemeMaster = socialSecuritySchemeMaster;
	}

	public Long getSubSchemeName() {
		return subSchemeName;
	}

	public void setSubSchemeName(Long subSchemeName) {
		this.subSchemeName = subSchemeName;
	}

	public Long getNoBeneficiary() {
		return noBeneficiary;
	}

	public void setNoBeneficiary(Long noBeneficiary) {
		this.noBeneficiary = noBeneficiary;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getIsschemeDetActive() {
		return isschemeDetActive;
	}

	public void setIsschemeDetActive(String isschemeDetActive) {
		this.isschemeDetActive = isschemeDetActive;
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

	public static String[] getPkValues() {
		return new String[] { "SWD", "TB_SWD_SUB_SCHEME_DET", "SUBSDSCHD_ID" };
	}


}
