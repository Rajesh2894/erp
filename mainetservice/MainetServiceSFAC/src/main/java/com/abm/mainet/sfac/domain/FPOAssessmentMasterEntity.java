/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "TB_SFAC_FPO_ASS_MASTER")
public class FPOAssessmentMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4514850035574357111L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_ID", nullable = false)
	private Long assId;

	@Column(name = "FPO_ID")
	private Long fpoId;

	@Column(name = "FPO_NAME")
	private String fpoName;

	@Column(name = "FIN_YR_ID")
	private Long finYrId;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "ASSESSMENT_NO")
	private String assessmentNo;

	@Column(name = "ASS_STATUS")
	private String assStatus;

	@Column(name = "REMARK")
	private String remark;

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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fpoMasterEntity", cascade = CascadeType.ALL)
	private List<FPOAssessmentKeyParamEntity> fpoAssKeyEntity = new ArrayList<>();

	/**
	 * @return the assId
	 */
	public Long getAssId() {
		return assId;
	}

	/**
	 * @param assId the assId to set
	 */
	public void setAssId(Long assId) {
		this.assId = assId;
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
	 * @return the assessmentNo
	 */
	public String getAssessmentNo() {
		return assessmentNo;
	}

	/**
	 * @param assessmentNo the assessmentNo to set
	 */
	public void setAssessmentNo(String assessmentNo) {
		this.assessmentNo = assessmentNo;
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
	 * @return the finYrId
	 */
	public Long getFinYrId() {
		return finYrId;
	}

	/**
	 * @param finYrId the finYrId to set
	 */
	public void setFinYrId(Long finYrId) {
		this.finYrId = finYrId;
	}

	/**
	 * @return the fpoId
	 */
	public Long getFpoId() {
		return fpoId;
	}

	/**
	 * @param fpoId the fpoId to set
	 */
	public void setFpoId(Long fpoId) {
		this.fpoId = fpoId;
	}

	/**
	 * @return the fpoName
	 */
	public String getFpoName() {
		return fpoName;
	}

	/**
	 * @param fpoName the fpoName to set
	 */
	public void setFpoName(String fpoName) {
		this.fpoName = fpoName;
	}

	/**
	 * @return the fpoAssKeyEntity
	 */
	public List<FPOAssessmentKeyParamEntity> getFpoAssKeyEntity() {
		return fpoAssKeyEntity;
	}

	/**
	 * @param fpoAssKeyEntity the fpoAssKeyEntity to set
	 */
	public void setFpoAssKeyEntity(List<FPOAssessmentKeyParamEntity> fpoAssKeyEntity) {
		this.fpoAssKeyEntity = fpoAssKeyEntity;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_FPO_ASS_MASTER", "ASS_ID" };
	}

}
