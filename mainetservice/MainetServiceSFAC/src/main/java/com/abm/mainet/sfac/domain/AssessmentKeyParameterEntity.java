/**
 * 
 */
package com.abm.mainet.sfac.domain;

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
@Table(name = "TB_SFAC_ASSESSMENT_KEY_PARAM")
public class AssessmentKeyParameterEntity {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_KID", nullable = false)
	private Long assKId;

	@ManyToOne
	@JoinColumn(name = "ASS_ID", referencedColumnName = "ASS_ID")
	private AssessmentMasterEntity masterEntity;

	@Column(name = "KEY_PARAMETER")
	private Long keyParameter;

	@Column(name = "KEY_PARAMETER_DESC", length = 100)
	private String keyParameterDesc;

	@Column(name = "WEIGHTAGE")
	private BigDecimal Weightage;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ASS_STATUS")
	private String assStatus;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "keyMasterEntity", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	private List<AssessmentSubParameterEntity> subMasterEntity = new ArrayList<>();

	/**
	 * @return the assKId
	 */
	public Long getAssKId() {
		return assKId;
	}

	/**
	 * @param assKId the assKId to set
	 */
	public void setAssKId(Long assKId) {
		this.assKId = assKId;
	}

	/**
	 * @return the masterEntity
	 */
	public AssessmentMasterEntity getMasterEntity() {
		return masterEntity;
	}

	/**
	 * @param masterEntity the masterEntity to set
	 */
	public void setMasterEntity(AssessmentMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
	}

	/**
	 * @return the keyParameter
	 */
	public Long getKeyParameter() {
		return keyParameter;
	}

	/**
	 * @param keyParameter the keyParameter to set
	 */
	public void setKeyParameter(Long keyParameter) {
		this.keyParameter = keyParameter;
	}

	/**
	 * @return the keyParameterDesc
	 */
	public String getKeyParameterDesc() {
		return keyParameterDesc;
	}

	/**
	 * @param keyParameterDesc the keyParameterDesc to set
	 */
	public void setKeyParameterDesc(String keyParameterDesc) {
		this.keyParameterDesc = keyParameterDesc;
	}

	/**
	 * @return the weightage
	 */
	public BigDecimal getWeightage() {
		return Weightage;
	}

	/**
	 * @param weightage the weightage to set
	 */
	public void setWeightage(BigDecimal weightage) {
		Weightage = weightage;
	}

	/**
	 * @return the assStatus
	 */
	public String getAssStatus() {
		return assStatus;
	}

	/**
	 * @param assStatus the assStatus to set
	 */
	public void setAssStatus(String assStatus) {
		this.assStatus = assStatus;
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
	 * @return the subMasterEntity
	 */
	public List<AssessmentSubParameterEntity> getSubMasterEntity() {
		return subMasterEntity;
	}

	/**
	 * @param subMasterEntity the subMasterEntity to set
	 */
	public void setSubMasterEntity(List<AssessmentSubParameterEntity> subMasterEntity) {
		this.subMasterEntity = subMasterEntity;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_ASSESSMENT_KEY_PARAM", "ASS_KID" };
	}
}
