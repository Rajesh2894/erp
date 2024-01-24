/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_AST_REVAL")
public class AssetRevaluation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4249661153634953160L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "REVAL_ID", nullable = false)
	private Long revaluationId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
	private AssetInformation assetId;

	@Column(name = "DOC_DATE", nullable = false)
	private Date docDate;

	@Column(name = "POST_DATE", nullable = false)
	private Date postDate;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "REVAL_FLAG", nullable = false)
	private String revalFlag;
	
	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;
	
	@Column(name = "REMARKS", nullable = true)
	private String remarks;
	
	@Column(name = "REVAL_TYPE", nullable = false)
	private Long impType;
	
	@Column(name = "IMPRO_COST", nullable = true)
	private BigDecimal impCost;
	
	@Column(name = "IMPRO_DESC", nullable = true)
	private String impDesc;

	@Column(name = "CREATION_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	@Column(name = "UPD_USEFULL_LIFE", nullable = true)
	private BigDecimal updUsefulLife;
	@Column(name = "ORIG_USEFULL_LIFE", nullable = true)
	private BigDecimal origUsefulLife;
	@Column(name = "PAYMENT_ADV_NO", nullable = true)
	private String payAdviceNo;
	
	@Column(name = "AUTH_STATUS", nullable = true)
    private String authStatus;
    @Column(name = "AUTH_BY", nullable = true)
    private Long authBy;
    @Column(name = "AUTH_DATE", nullable = true)
    private Date authDate;
	
	public Long getRevaluationId() {
		return revaluationId;
	}

	public void setRevaluationId(Long revaluationId) {
		this.revaluationId = revaluationId;
	}

	public AssetInformation getAssetId() {
		return assetId;
	}

	public void setAssetId(AssetInformation assetId) {
		this.assetId = assetId;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public Long getOrgId() {
		return orgId;
	}

	public String getRevalFlag() {
		return revalFlag;
	}

	public void setRevalFlag(String revalFlag) {
		this.revalFlag = revalFlag;
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
		return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_REVAL", "REVAL_ID" };
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getImpType() {
		return impType;
	}

	public void setImpType(Long revalType) {
		this.impType = revalType;
	}

	public BigDecimal getImpCost() {
		return impCost;
	}

	public void setImpCost(BigDecimal impCost) {
		this.impCost = impCost;
	}

	public String getImpDesc() {
		return impDesc;
	}

	public void setImpDesc(String impDesc) {
		this.impDesc = impDesc;
	}

	public BigDecimal getUpdUsefulLife() {
		return updUsefulLife;
	}

	public void setUpdUsefulLife(BigDecimal updUsefulLife) {
		this.updUsefulLife = updUsefulLife;
	}

	public BigDecimal getOrigUsefulLife() {
		return origUsefulLife;
	}

	public void setOrigUsefulLife(BigDecimal origUsefulLife) {
		this.origUsefulLife = origUsefulLife;
	}

	public String getPayAdviceNo() {
		return payAdviceNo;
	}

	public void setPayAdviceNo(String payAdviceNo) {
		this.payAdviceNo = payAdviceNo;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	public Long getAuthBy() {
		return authBy;
	}

	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}

	public Date getAuthDate() {
		return authDate;
	}

	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}

}
