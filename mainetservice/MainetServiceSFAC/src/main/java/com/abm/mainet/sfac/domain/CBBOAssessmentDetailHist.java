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
@Table(name = "TB_SFAC_ASS_CBBO_DET_HIST")
public class CBBOAssessmentDetailHist implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5791227655369044422L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ASS_CBBO_ID_H", unique = true, nullable = false)
	private Long assCbboIdH;

	@Column(name = "ASS_CBBO_ID")
	private Long assCbboId;

	@ManyToOne
	@JoinColumn(name = "ASS_ID_H", referencedColumnName = "ASS_ID_H")
	private AssessmentMasterHist masterHistEntity;

	@Column(name = "CBBO_ID")
	private Long cbboId;

	@Column(name = "CBBO_NAME")
	private String cbboName;

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
	 * @return the assCbboIdH
	 */
	public Long getAssCbboIdH() {
		return assCbboIdH;
	}

	/**
	 * @param assCbboIdH the assCbboIdH to set
	 */
	public void setAssCbboIdH(Long assCbboIdH) {
		this.assCbboIdH = assCbboIdH;
	}

	/**
	 * @return the assCbboId
	 */
	public Long getAssCbboId() {
		return assCbboId;
	}

	/**
	 * @param assCbboId the assCbboId to set
	 */
	public void setAssCbboId(Long assCbboId) {
		this.assCbboId = assCbboId;
	}

	/**
	 * @return the masterHistEntity
	 */
	public AssessmentMasterHist getMasterHistEntity() {
		return masterHistEntity;
	}

	/**
	 * @param masterHistEntity the masterHistEntity to set
	 */
	public void setMasterHistEntity(AssessmentMasterHist masterHistEntity) {
		this.masterHistEntity = masterHistEntity;
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
	 * @return the cbboId
	 */
	public Long getCbboId() {
		return cbboId;
	}

	/**
	 * @param cbboId the cbboId to set
	 */
	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}

	/**
	 * @return the cbboName
	 */
	public String getCbboName() {
		return cbboName;
	}

	/**
	 * @param cbboName the cbboName to set
	 */
	public void setCbboName(String cbboName) {
		this.cbboName = cbboName;
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

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_ASS_CBBO_DET_HIST", "ASS_CBBO_ID_H" };
	}
}
