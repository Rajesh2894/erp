/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
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
@Table(name = "TB_SFAC_FPO_ASS_SUB_PARAM_HIST")
public class FPOAssessmentSubParamHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3119896039379885665L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_SID_H", unique = true, nullable = false)
	private Long assSIdH;

	@Column(name = "ASS_SID")
	private Long assSId;

	@ManyToOne
	@JoinColumn(name = "ASS_KID_H", referencedColumnName = "ASS_KID_H")
	private FPOAssessmentKeyParamHistory fpoKeyParamHistEntity;

	@Column(name = "SUB_PARAMETER")
	private Long subParameter;

	@Column(name = "SUB_PARAMETER_DESC", length = 500)
	private String subParameterDesc;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ASS_STATUS")
	private Long assStatus;

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
	 * @return the assSIdH
	 */
	public Long getAssSIdH() {
		return assSIdH;
	}

	/**
	 * @param assSIdH the assSIdH to set
	 */
	public void setAssSIdH(Long assSIdH) {
		this.assSIdH = assSIdH;
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
	 * @return the fpoKeyParamHistEntity
	 */
	public FPOAssessmentKeyParamHistory getFpoKeyParamHistEntity() {
		return fpoKeyParamHistEntity;
	}

	/**
	 * @param fpoKeyParamHistEntity the fpoKeyParamHistEntity to set
	 */
	public void setFpoKeyParamHistEntity(FPOAssessmentKeyParamHistory fpoKeyParamHistEntity) {
		this.fpoKeyParamHistEntity = fpoKeyParamHistEntity;
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
		return new String[] { "SFAC", "TB_SFAC_FPO_ASS_SUB_PARAM_HIST", "ASS_SID_H" };
	}

}
