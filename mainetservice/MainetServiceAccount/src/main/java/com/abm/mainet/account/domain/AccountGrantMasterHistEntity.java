package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_AC_GRNTMST_HIST")
public class AccountGrantMasterHistEntity implements Serializable {
	private static final long serialVersionUID = 4808074265480858221L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_GRT_ID", precision = 12, scale = 0, nullable = false)
	private Long grnthId;

	@Column(name = "GRT_ID", precision = 12, scale = 0, nullable = false)
	private Long grntId;

	@Column(name = "GRT_NO", precision = 20, scale = 0, nullable = false)
	private String grtNo;

	@Column(name = "GRT_DT", nullable = false)
	private Date grtDate;

	@Column(name = "GRT_NAME", length = 200, nullable = false)
	private String grtName;

	@Column(name = "GRT_TYPE", length = 1, nullable = false)
	private String grtType;

	@Column(name = "FROM_PERD", precision = 4, scale = 0, nullable = true)
	private Long fromPerd;

	@Column(name = "TO_PERD", precision = 4, scale = 0, nullable = true)
	private Long toPerd;

	@Column(name = "SANCTION_NO", length = 500, nullable = false)
	private String sactNo;

	@Column(name = "SANCTION_AUTH", length = 500, nullable = false)
	private String sanctionAuth;

	@Column(name = "SANCTION_DT", nullable = false)
	private Date sanctionDate;

	@Column(name = "SANCTION_AMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal santionAmt;

	@Column(name = "RECVED_AMT", precision = 12, scale = 2, nullable = false)
	private BigDecimal receivedAmt;

	@Column(name = "GRT_NATURE", length = 500, nullable = false)
	private String grtNature;

	@Column(name = "FUND_ID", precision = 12, scale = 0, nullable = false)
	private Long fundId;

	@Column(name = "UC_STATUS", length = 1, nullable = false)
	private String usStatus;

	@Column(name = "LANG_ID", precision = 2, scale = 0, nullable = false)
	private Long langId;

	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "H_STATUS", length = 2)
	private Character hStatus;

	public static String[] getPkValues() {
		return new String[] { "AC", "TB_AC_GRNTMST_HIST", "H_GRT_ID" };
	}

	public Long getGrnthId() {
		return grnthId;
	}

	public void setGrnthId(Long grnthId) {
		this.grnthId = grnthId;
	}

	public Long getGrntId() {
		return grntId;
	}

	public void setGrntId(Long grntId) {
		this.grntId = grntId;
	}

	public String getGrtNo() {
		return grtNo;
	}

	public void setGrtNo(String grtNo) {
		this.grtNo = grtNo;
	}

	public Date getGrtDate() {
		return grtDate;
	}

	public void setGrtDate(Date grtDate) {
		this.grtDate = grtDate;
	}

	public String getGrtName() {
		return grtName;
	}

	public void setGrtName(String grtName) {
		this.grtName = grtName;
	}

	public String getGrtType() {
		return grtType;
	}

	public void setGrtType(String grtType) {
		this.grtType = grtType;
	}

	public Long getFromPerd() {
		return fromPerd;
	}

	public void setFromPerd(Long fromPerd) {
		this.fromPerd = fromPerd;
	}

	public Long getToPerd() {
		return toPerd;
	}

	public void setToPerd(Long toPerd) {
		this.toPerd = toPerd;
	}

	public String getSactNo() {
		return sactNo;
	}

	public void setSactNo(String sactNo) {
		this.sactNo = sactNo;
	}

	public String getSanctionAuth() {
		return sanctionAuth;
	}

	public void setSanctionAuth(String sanctionAuth) {
		this.sanctionAuth = sanctionAuth;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public BigDecimal getSantionAmt() {
		return santionAmt;
	}

	public void setSantionAmt(BigDecimal santionAmt) {
		this.santionAmt = santionAmt;
	}

	public BigDecimal getReceivedAmt() {
		return receivedAmt;
	}

	public void setReceivedAmt(BigDecimal receivedAmt) {
		this.receivedAmt = receivedAmt;
	}

	public String getGrtNature() {
		return grtNature;
	}

	public void setGrtNature(String grtNature) {
		this.grtNature = grtNature;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public String getUsStatus() {
		return usStatus;
	}

	public void setUsStatus(String usStatus) {
		this.usStatus = usStatus;
	}

	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
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

	public Character gethStatus() {
		return hStatus;
	}

	public void sethStatus(Character hStatus) {
		this.hStatus = hStatus;
	}

}
