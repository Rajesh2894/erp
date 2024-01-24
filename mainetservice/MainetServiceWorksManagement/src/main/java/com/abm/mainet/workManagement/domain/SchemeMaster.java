package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vishwajeet.Kumar
 *
 */

@Entity
@Table(name = "TB_WMS_SCHEME_MAST")
public class SchemeMaster implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "SCH_ID", nullable = false)
	private Long wmSchId;

	@Column(name = "SCH_CODE", nullable = true)
	private String wmSchCode;

	@Column(name = "SCH_NAME_ENG", nullable = false)
	private String wmSchNameEng;

	@Column(name = "SCH_NAME_REG ", nullable = false)
	private String wmSchNameReg;

	@Column(name = "SCH_DESCRIPTION", nullable = true)
	private String wmSchDesc;

	@Column(name = "SCH_START_DATE", nullable = true)
	private Date wmSchStrDate;

	@Column(name = "SCH_END_DATE", nullable = true)
	private Date wmSchEndDate;

	@Column(name = "SCH_FUND", nullable = true)
	private Long wmSchFund;

	@Column(name = "SCH_FUNDNAME", nullable = true)
	private String schFundName;

	@Column(name = "SCH_PROJECTED_REVENU", nullable = true)
	private BigDecimal wmSchPRev;

	@Column(name = "SCH_SCODID1", nullable = true)
	private Long wmSchCodeId1;

	@Column(name = "SCH_SCODID2", nullable = true)
	private Long wmSchCodeId2;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wmsSchemeMaster", cascade = CascadeType.ALL)
	@Where(clause = "SCHD_ACTIVE='Y'")
	private List<SchemeMastDetails> mastDetailsEntity = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SCH_ID", referencedColumnName = "SCH_ID")
	@Where(clause = "PROJ_ACTIVE='Y'")
	private List<TbWmsProjectMaster> schemeToProjectlist = new ArrayList<>();

	@Column(name = "SCH_ACTIVE")
	private String schemeActive;

	public String getSchemeActive() {
		return schemeActive;
	}

	public void setSchemeActive(String schemeActive) {
		this.schemeActive = schemeActive;
	}

	public List<SchemeMastDetails> getMastDetailsEntity() {
		return mastDetailsEntity;
	}

	public void setMastDetailsEntity(List<SchemeMastDetails> mastDetailsEntity) {
		this.mastDetailsEntity = mastDetailsEntity;
	}

	public Long getWmSchId() {
		return wmSchId;
	}

	public void setWmSchId(Long wmSchId) {
		this.wmSchId = wmSchId;
	}

	public String getWmSchCode() {
		return wmSchCode;
	}

	public void setWmSchCode(String wmSchCode) {
		this.wmSchCode = wmSchCode;
	}

	public String getWmSchNameEng() {
		return wmSchNameEng;
	}

	public void setWmSchNameEng(String wmSchNameEng) {
		this.wmSchNameEng = wmSchNameEng;
	}

	public String getWmSchNameReg() {
		return wmSchNameReg;
	}

	public void setWmSchNameReg(String wmSchNameReg) {
		this.wmSchNameReg = wmSchNameReg;
	}

	public String getWmSchDesc() {
		return wmSchDesc;
	}

	public void setWmSchDesc(String wmSchDesc) {
		this.wmSchDesc = wmSchDesc;
	}

	public Date getWmSchStrDate() {
		return wmSchStrDate;
	}

	public void setWmSchStrDate(Date wmSchStrDate) {
		this.wmSchStrDate = wmSchStrDate;
	}

	public Date getWmSchEndDate() {
		return wmSchEndDate;
	}

	public void setWmSchEndDate(Date wmSchEndDate) {
		this.wmSchEndDate = wmSchEndDate;
	}

	public Long getWmSchFund() {
		return wmSchFund;
	}

	public void setWmSchFund(Long wmSchFund) {
		this.wmSchFund = wmSchFund;
	}

	public BigDecimal getWmSchPRev() {
		return wmSchPRev;
	}

	public void setWmSchPRev(BigDecimal wmSchPRev) {
		this.wmSchPRev = wmSchPRev;
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

	public List<TbWmsProjectMaster> getSchemeToProjectlist() {
		return schemeToProjectlist;
	}

	public void setSchemeToProjectlist(List<TbWmsProjectMaster> schemeToProjectlist) {
		this.schemeToProjectlist = schemeToProjectlist;
	}

	public String getSchFundName() {
		return schFundName;
	}

	public void setSchFundName(String schFundName) {
		this.schFundName = schFundName;
	}

	public Long getWmSchCodeId1() {
		return wmSchCodeId1;
	}

	public void setWmSchCodeId1(Long wmSchCodeId1) {
		this.wmSchCodeId1 = wmSchCodeId1;
	}

	public Long getWmSchCodeId2() {
		return wmSchCodeId2;
	}

	public void setWmSchCodeId2(Long wmSchCodeId2) {
		this.wmSchCodeId2 = wmSchCodeId2;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_SCHEME_MAST", "SCH_ID" };
	}

}
