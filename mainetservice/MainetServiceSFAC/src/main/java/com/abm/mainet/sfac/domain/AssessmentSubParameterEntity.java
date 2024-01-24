/**
 * 
 */
package com.abm.mainet.sfac.domain;

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
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_SFAC_ASSESSMENT_SUB_PARAM")
public class AssessmentSubParameterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4000998889553626050L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_SID", nullable = false)
	private Long assSId;

	@ManyToOne
	@JoinColumn(name = "ASS_KID", referencedColumnName = "ASS_KID")
	private AssessmentKeyParameterEntity keyMasterEntity;

	@Column(name = "SUB_PARAMETER")
	private Long subParameter;

	@Column(name = "SUB_PARAMETER_DESC", length = 500)
	private String subParameterDesc;

	@Column(name = "SUB_WEIGHTAGE")
	private Long subWeightage;

	@Column(name = "MEANS_OF_VERIFICATION")
	private Long meansOfVerification;

	@Column(name = "MEANS_OF_VERIFICATION_DESC", length = 500)
	private String meansOfVerificationDesc;

	@Column(name = "PENDING")
	private Long pending;

	@Column(name = "APPROVED")
	private Long approved;

	@Column(name = "OVERALL_SCORE")
	private BigDecimal overallScore;

	@Column(name = "SCORE")
	private BigDecimal score;

	@Column(name = "YES_NO")
	private String yesNo;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ASS_STATUS")
	private Long assStatus;

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

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "assSubParamEntity",  cascade = CascadeType.ALL)
	List<AssessmentSubParameterDetail> assSubParamDetailList = new ArrayList<>();

	/**
	 * @return the keyMasterEntity
	 */
	public AssessmentKeyParameterEntity getKeyMasterEntity() {
		return keyMasterEntity;
	}

	/**
	 * @param keyMasterEntity the keyMasterEntity to set
	 */
	public void setKeyMasterEntity(AssessmentKeyParameterEntity keyMasterEntity) {
		this.keyMasterEntity = keyMasterEntity;
	}

	/**
	 * @return the subParameter
	 */
	public Long getSubParameter() {
		return subParameter;
	}

	/**
	 * @param subParameter the subParameter to set
	 */
	public void setSubParameter(Long subParameter) {
		this.subParameter = subParameter;
	}

	/**
	 * @return the subWeightage
	 */
	public Long getSubWeightage() {
		return subWeightage;
	}

	/**
	 * @param subWeightage the subWeightage to set
	 */
	public void setSubWeightage(Long subWeightage) {
		this.subWeightage = subWeightage;
	}

	/**
	 * @return the meansOfVerification
	 */
	public Long getMeansOfVerification() {
		return meansOfVerification;
	}

	/**
	 * @param meansOfVerification the meansOfVerification to set
	 */
	public void setMeansOfVerification(Long meansOfVerification) {
		this.meansOfVerification = meansOfVerification;
	}

	/**
	 * @return the pending
	 */
	public Long getPending() {
		return pending;
	}

	/**
	 * @param pending the pending to set
	 */
	public void setPending(Long pending) {
		this.pending = pending;
	}

	/**
	 * @return the approved
	 */
	public Long getApproved() {
		return approved;
	}

	/**
	 * @param approved the approved to set
	 */
	public void setApproved(Long approved) {
		this.approved = approved;
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
	 * @return the yesNo
	 */
	public String getYesNo() {
		return yesNo;
	}

	/**
	 * @param yesNo the yesNo to set
	 */
	public void setYesNo(String yesNo) {
		this.yesNo = yesNo;
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

	/**
	 * @return the subParameterDesc
	 */
	public String getSubParameterDesc() {
		return subParameterDesc;
	}

	/**
	 * @param subParameterDesc the subParameterDesc to set
	 */
	public void setSubParameterDesc(String subParameterDesc) {
		this.subParameterDesc = subParameterDesc;
	}

	/**
	 * @return the meansOfVerificationDesc
	 */
	public String getMeansOfVerificationDesc() {
		return meansOfVerificationDesc;
	}

	/**
	 * @param meansOfVerificationDesc the meansOfVerificationDesc to set
	 */
	public void setMeansOfVerificationDesc(String meansOfVerificationDesc) {
		this.meansOfVerificationDesc = meansOfVerificationDesc;
	}

	/**
	 * @return the assSId
	 */
	public Long getAssSId() {
		return assSId;
	}

	/**
	 * @param assSId the assSId to set
	 */
	public void setAssSId(Long assSId) {
		this.assSId = assSId;
	}

	/**
	 * @return the assStatus
	 */
	public Long getAssStatus() {
		return assStatus;
	}

	/**
	 * @param assStatus the assStatus to set
	 */
	public void setAssStatus(Long assStatus) {
		this.assStatus = assStatus;
	}

	/**
	 * @return the assSubParamDetailList
	 */
	public List<AssessmentSubParameterDetail> getAssSubParamDetailList() {
		return assSubParamDetailList;
	}

	/**
	 * @param assSubParamDetailList the assSubParamDetailList to set
	 */
	public void setAssSubParamDetailList(List<AssessmentSubParameterDetail> assSubParamDetailList) {
		this.assSubParamDetailList = assSubParamDetailList;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_ASSESSMENT_SUB_PARAM", "ASS_SID" };
	}

}
