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
@Table(name = "TB_SFAC_ASSESSMENT_SUB_PARAM_DET")
public class AssessmentSubParameterDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590829212454850837L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_SP_ID", nullable = false)
	private Long assSpdId;

	@ManyToOne
	@JoinColumn(name = "ASS_SID", referencedColumnName = "ASS_SID")
	AssessmentSubParameterEntity assSubParamEntity;

	@Column(name = "CONDITIONS")
	private Long condition;

	@Column(name = "CONDITION_DESC")
	private String conditionDesc;

	@Column(name = "SUB_WEIGHTAGE")
	private BigDecimal subWeightage;

	@Column(name = "OVERALL_SCORE")
	private BigDecimal overallScore;

	@Column(name = "SCORE")
	private BigDecimal score;

	@Column(name = "REG_FPO_CRITERIA")
	private Long regiFpoCriteria;

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
	 * @return the assSubParamEntity
	 */
	public AssessmentSubParameterEntity getAssSubParamEntity() {
		return assSubParamEntity;
	}

	/**
	 * @param assSubParamEntity the assSubParamEntity to set
	 */
	public void setAssSubParamEntity(AssessmentSubParameterEntity assSubParamEntity) {
		this.assSubParamEntity = assSubParamEntity;
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
	 * @return the overallScore
	 */
	public BigDecimal getOverallScore() {
		return overallScore;
	}

	/**
	 * @param overallScore the overallScore to set
	 */
	public void setOverallScore(BigDecimal overallScore) {
		this.overallScore = overallScore;
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
	 * @return the regiFpoCriteria
	 */
	public Long getRegiFpoCriteria() {
		return regiFpoCriteria;
	}

	/**
	 * @param regiFpoCriteria the regiFpoCriteria to set
	 */
	public void setRegiFpoCriteria(Long regiFpoCriteria) {
		this.regiFpoCriteria = regiFpoCriteria;
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
		return new String[] { "SFAC", "TB_SFAC_ASSESSMENT_SUB_PARAM_DET", "ASS_SP_ID" };
	}

}
