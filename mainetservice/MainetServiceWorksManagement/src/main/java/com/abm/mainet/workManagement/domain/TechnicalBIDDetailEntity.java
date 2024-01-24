package com.abm.mainet.workManagement.domain;

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
@Table(name = "tb_wms_bid_tech_det")
public class TechnicalBIDDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 826587347476353048L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "tech_bid_id", nullable = false)
	private Long techBidId;

	@ManyToOne
	@JoinColumn(name = "bid_Id")
	private BIDMasterEntity bidMasterEntity;

	@Column(name = "param_desc_id", nullable = true)
	private String paramDescId;

	@Column(name = "mark", nullable = true)
	private Long mark;

	@Column(name = "obtained", nullable = true)
	private Long obtained;

	@Column(name = "weightage", nullable = true)
	private BigDecimal weightage;

	@Column(name = "final_marks", nullable = true)
	private Long finalMark;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	@Column(name = "EVALUATION", nullable = false)
	private String evaluation;
	
	@Column(name = "CRITERIA", nullable = false)
	private String criteria;
	
	@Column(name = "ACCEPTREJECT", nullable = false)
	private String acceptreject;
	
	@Column(name = "TECH_FLAG", nullable = true)
	private Character  technicalflag;

	public Long getTechBidId() {
		return techBidId;
	}

	public void setTechBidId(Long techBidId) {
		this.techBidId = techBidId;
	}

	public String getParamDescId() {
		return paramDescId;
	}

	public void setParamDescId(String paramDescId) {
		this.paramDescId = paramDescId;
	}

	public Long getMark() {
		return mark;
	}

	public void setMark(Long mark) {
		this.mark = mark;
	}

	public Long getObtained() {
		return obtained;
	}

	public void setObtained(Long obtained) {
		this.obtained = obtained;
	}

	public BigDecimal getWeightage() {
		return weightage;
	}

	public void setWeightage(BigDecimal weightage) {
		this.weightage = weightage;
	}

	public Long getFinalMark() {
		return finalMark;
	}

	public void setFinalMark(Long finalMark) {
		this.finalMark = finalMark;
	}

	public BIDMasterEntity getBidMasterEntity() {
		return bidMasterEntity;
	}

	public void setBidMasterEntity(BIDMasterEntity bidMasterEntity) {
		this.bidMasterEntity = bidMasterEntity;
	}

	public Long getOrgId() {
		return orgId;
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

	public String getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}

	public String getCriteria() {
		return criteria;
	}

	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}

	public String getAcceptreject() {
		return acceptreject;
	}

	public void setAcceptreject(String acceptreject) {
		this.acceptreject = acceptreject;
	}
	

	public Character getTechnicalflag() {
		return technicalflag;
	}

	public void setTechnicalflag(Character technicalflag) {
		this.technicalflag = technicalflag;
	}

	public static String[] getPkValues() {
		return new String[] { "COM", "tb_wms_bid_tech_det", "tech_bid_id" };
	}
}
