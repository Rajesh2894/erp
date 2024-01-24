/**
 * 
 */
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

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_FPO_ASS_SUB_PARAM_DET_HIST")
public class FPOAssessmentSubParamDetailHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234834978935829764L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_SP_ID_H", nullable = false)
	private Long assSpdIdH;

	@Column(name = "ASS_SP_ID")
	private Long assSpdId;

	@ManyToOne
	@JoinColumn(name = "ASS_SID_H", referencedColumnName = "ASS_SID_H")
	private FPOAssessmentSubParamHistory fpoSubParamHistEntity;

	@Column(name = "CONDITIONS")
	private Long condition;

	@Column(name = "CONDITION_DESC")
	private String conditionDesc;

	@Column(name = "SUB_WEIGHTAGE")
	private BigDecimal subWeightage;

	@Column(name = "MARKS_AWARDED")
	private Long marksAwarded;

	@Column(name = "SCORE")
	private BigDecimal score;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "H_STATUS", length = 1)
	private String historyStatus;

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

	/**
	 * @return the assSpdIdH
	 */
	public Long getAssSpdIdH() {
		return assSpdIdH;
	}

	/**
	 * @param assSpdIdH the assSpdIdH to set
	 */
	public void setAssSpdIdH(Long assSpdIdH) {
		this.assSpdIdH = assSpdIdH;
	}

	/**
	 * @return the assSpdId
	 */
	public Long getAssSpdId() {
		return assSpdId;
	}

	/**
	 * @param assSpdId the assSpdId to set
	 */
	public void setAssSpdId(Long assSpdId) {
		this.assSpdId = assSpdId;
	}

	/**
	 * @return the fpoSubParamHistEntity
	 */
	public FPOAssessmentSubParamHistory getFpoSubParamHistEntity() {
		return fpoSubParamHistEntity;
	}

	/**
	 * @param fpoSubParamHistEntity the fpoSubParamHistEntity to set
	 */
	public void setFpoSubParamHistEntity(FPOAssessmentSubParamHistory fpoSubParamHistEntity) {
		this.fpoSubParamHistEntity = fpoSubParamHistEntity;
	}

	/**
	 * @return the condition
	 */
	public Long getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	public void setCondition(Long condition) {
		this.condition = condition;
	}

	/**
	 * @return the conditionDesc
	 */
	public String getConditionDesc() {
		return conditionDesc;
	}

	/**
	 * @param conditionDesc the conditionDesc to set
	 */
	public void setConditionDesc(String conditionDesc) {
		this.conditionDesc = conditionDesc;
	}

	/**
	 * @return the subWeightage
	 */
	public BigDecimal getSubWeightage() {
		return subWeightage;
	}

	/**
	 * @param subWeightage the subWeightage to set
	 */
	public void setSubWeightage(BigDecimal subWeightage) {
		this.subWeightage = subWeightage;
	}

	/**
	 * @return the marksAwarded
	 */
	public Long getMarksAwarded() {
		return marksAwarded;
	}

	/**
	 * @param marksAwarded the marksAwarded to set
	 */
	public void setMarksAwarded(Long marksAwarded) {
		this.marksAwarded = marksAwarded;
	}

	/**
	 * @return the score
	 */
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the applicationId
	 */
	public Long getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the historyStatus
	 */
	public String getHistoryStatus() {
		return historyStatus;
	}

	/**
	 * @param historyStatus the historyStatus to set
	 */
	public void setHistoryStatus(String historyStatus) {
		this.historyStatus = historyStatus;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_FPO_ASS_SUB_PARAM_DET_HIST", "ASS_SP_ID_H" };
	}

}
