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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 * @Since 10-Apr-2018
 */
@Entity
@Table(name = "TB_WMS_TENDER_MAST")
public class TenderMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4712796159420986769L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "TND_ID", nullable = false)
	private Long tndId;

	@ManyToOne
	@JoinColumn(name = "PROJ_ID", referencedColumnName = "PROJ_ID", nullable = false)
	private TbWmsProjectMaster projMasEntity;

	@Column(name = "TND_CATEGORY", nullable = false)
	private Long tenderCategory;

	@Column(name = "TND_RSO_NO", length = 40, nullable = true)
	private String resolutionNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "TND_RSO_DATE", nullable = true)
	private Date resolutionDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TND_PUBLISH_DATE", nullable = true)
	private Date publishDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TND_PREBID_MEETINGDT", nullable = true)
	private Date preBidMeetDate;

	@Column(name = "TND_MEETING_LOC", length = 100, nullable = true)
	private String tenderMeetingLoc;

	@Temporal(TemporalType.DATE)
	@Column(name = "TND_ISSUE_FROMDATE", nullable = true)
	private Date issueFromDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "TND_ISSUE_TODATE", nullable = true)
	private Date issueToDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TND_TECOPEN_DATE", nullable = true)
	private Date technicalOpenDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TND_FINOPEN_DATE", nullable = true)
	private Date financialeOpenDate;

	@Column(name = "TND_INITIATIONNO", length = 50, nullable = true)
	private String initiationNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "TND_INITIATIONDATE", nullable = true)
	private Date initiationDate;

	@Column(name = "TND_NO", length = 40, nullable = true)
	private String tenderNo;

	@Temporal(TemporalType.DATE)
	@Column(name = "TND_DATE", nullable = true)
	private Date tenderDate;

	@Column(name = "TND_EMD_AMOUNT")
	private BigDecimal tenderEmdAmt;

	@Column(name = "TND_STATUS", nullable = false)
	private String status;

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

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "TND_ESTAMT")
	private BigDecimal tenderTotalEstiAmount;

	@Column(name = "TND_VALIDITY_DAY")
	private Long tndValidityDay;
	
	@Column(name = "ISD_AMT")
	private BigDecimal tenderBankAmt;
	
	@Column(name = "TND_PROVAMT")
	private BigDecimal tenderProvAmt;
	
	@Column(name = "RMD_AMT")
	private BigDecimal tenderSecAmt;
	
	@Column(name = "WORK_DEV_PER")
	private BigDecimal deviationPercent;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tenderMasEntity", cascade = CascadeType.ALL)
	private List<TenderWorkEntity> tenderWorkList = new ArrayList<>();

	@Column(name = "TND_REF_NO")
	private Long tndRefNo;

	@JsonIgnore
	@OneToMany(mappedBy = "tenderMasterEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<BIDMasterEntity> bidMAsterEntities = new ArrayList<BIDMasterEntity>();

	public List<BIDMasterEntity> getBidMAsterEntities() {
		return bidMAsterEntities;
	}

	public void setBidMAsterEntities(List<BIDMasterEntity> bidMAsterEntities) {
		this.bidMAsterEntities = bidMAsterEntities;
	}

	public Long getTndId() {
		return tndId;
	}

	public void setTndId(Long tndId) {
		this.tndId = tndId;
	}

	public TbWmsProjectMaster getProjMasEntity() {
		return projMasEntity;
	}

	public void setProjMasEntity(TbWmsProjectMaster projMasEntity) {
		this.projMasEntity = projMasEntity;
	}

	public Long getTenderCategory() {
		return tenderCategory;
	}

	public void setTenderCategory(Long tenderCategory) {
		this.tenderCategory = tenderCategory;
	}

	public String getResolutionNo() {
		return resolutionNo;
	}

	public void setResolutionNo(String resolutionNo) {
		this.resolutionNo = resolutionNo;
	}

	public Date getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	public Date getIssueFromDate() {
		return issueFromDate;
	}

	public void setIssueFromDate(Date issueFromDate) {
		this.issueFromDate = issueFromDate;
	}

	public Date getIssueToDate() {
		return issueToDate;
	}

	public void setIssueToDate(Date issueToDate) {
		this.issueToDate = issueToDate;
	}

	public Date getTechnicalOpenDate() {
		return technicalOpenDate;
	}

	public void setTechnicalOpenDate(Date technicalOpenDate) {
		this.technicalOpenDate = technicalOpenDate;
	}

	public Date getFinancialeOpenDate() {
		return financialeOpenDate;
	}

	public void setFinancialeOpenDate(Date financialeOpenDate) {
		this.financialeOpenDate = financialeOpenDate;
	}

	public String getInitiationNo() {
		return initiationNo;
	}

	public void setInitiationNo(String initiationNo) {
		this.initiationNo = initiationNo;
	}

	public Date getInitiationDate() {
		return initiationDate;
	}

	public void setInitiationDate(Date initiationDate) {
		this.initiationDate = initiationDate;
	}

	public String getTenderNo() {
		return tenderNo;
	}

	public void setTenderNo(String tenderNo) {
		this.tenderNo = tenderNo;
	}

	public Date getTenderDate() {
		return tenderDate;
	}

	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}

	public BigDecimal getTenderEmdAmt() {
		return tenderEmdAmt;
	}

	public void setTenderEmdAmt(BigDecimal tenderEmdAmt) {
		this.tenderEmdAmt = tenderEmdAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public BigDecimal getTenderTotalEstiAmount() {
		return tenderTotalEstiAmount;
	}

	public void setTenderTotalEstiAmount(BigDecimal tenderTotalEstiAmount) {
		this.tenderTotalEstiAmount = tenderTotalEstiAmount;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Long getTndValidityDay() {
		return tndValidityDay;
	}

	public void setTndValidityDay(Long tndValidityDay) {
		this.tndValidityDay = tndValidityDay;
	}

	public List<TenderWorkEntity> getTenderWorkList() {
		return tenderWorkList;
	}

	public void setTenderWorkList(List<TenderWorkEntity> tenderWorkList) {
		this.tenderWorkList = tenderWorkList;
	}

	public Date getPreBidMeetDate() {
		return preBidMeetDate;
	}

	public void setPreBidMeetDate(Date preBidMeetDate) {
		this.preBidMeetDate = preBidMeetDate;
	}

	public String getTenderMeetingLoc() {
		return tenderMeetingLoc;
	}

	public void setTenderMeetingLoc(String tenderMeetingLoc) {
		this.tenderMeetingLoc = tenderMeetingLoc;
	}

	public Long getTndRefNo() {
		return tndRefNo;
	}

	public void setTndRefNo(Long tndRefNo) {
		this.tndRefNo = tndRefNo;
	}

	public BigDecimal getTenderBankAmt() {
		return tenderBankAmt;
	}

	public void setTenderBankAmt(BigDecimal tenderBankAmt) {
		this.tenderBankAmt = tenderBankAmt;
	}

	public BigDecimal getTenderProvAmt() {
		return tenderProvAmt;
	}

	public void setTenderProvAmt(BigDecimal tenderProvAmt) {
		this.tenderProvAmt = tenderProvAmt;
	}

	public BigDecimal getTenderSecAmt() {
		return tenderSecAmt;
	}

	public void setTenderSecAmt(BigDecimal tenderSecAmt) {
		this.tenderSecAmt = tenderSecAmt;
	}

	public BigDecimal getDeviationPercent() {
		return deviationPercent;
	}

	public void setDeviationPercent(BigDecimal deviationPercent) {
		this.deviationPercent = deviationPercent;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_TENDER_MAST", "TND_ID" };
	}

}
