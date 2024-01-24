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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "TB_SFAC_CB_MASTER")
public class CbMasterEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8730743546235245611L;

	@Id
	@Column(name = "CB_ID")
	private Long cbId;

	@Column(name = "CBBO_NAME")
	private String cbboName;

	@Column(name = "CBBO_UNIQUE_ID")
	private String cbboUniqueId;

	@Column(name = "PAN_NO")
	private String panNo;

	@Column(name = "SDB1")
	private Long sdb1;

	@Column(name = "CBBO_EMPANELMENT_YEAR")
	private Long cbboEmpanelmentYear;

	@Column(name = "IA_ID")
	private Long ia_id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CB_REG_DT")
	private Date cbRegDate;

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
	 * @return the cbId
	 */
	public Long getCbId() {
		return cbId;
	}

	/**
	 * @param cbId the cbId to set
	 */
	public void setCbId(Long cbId) {
		this.cbId = cbId;
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
	 * @return the cbboUniqueId
	 */
	public String getCbboUniqueId() {
		return cbboUniqueId;
	}

	/**
	 * @param cbboUniqueId the cbboUniqueId to set
	 */
	public void setCbboUniqueId(String cbboUniqueId) {
		this.cbboUniqueId = cbboUniqueId;
	}

	/**
	 * @return the panNo
	 */
	public String getPanNo() {
		return panNo;
	}

	/**
	 * @param panNo the panNo to set
	 */
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	/**
	 * @return the sdb1
	 */
	public Long getSdb1() {
		return sdb1;
	}

	/**
	 * @param sdb1 the sdb1 to set
	 */
	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}

	/**
	 * @return the cbboEmpanelmentYear
	 */
	public Long getCbboEmpanelmentYear() {
		return cbboEmpanelmentYear;
	}

	/**
	 * @param cbboEmpanelmentYear the cbboEmpanelmentYear to set
	 */
	public void setCbboEmpanelmentYear(Long cbboEmpanelmentYear) {
		this.cbboEmpanelmentYear = cbboEmpanelmentYear;
	}

	/**
	 * @return the ia_id
	 */
	public Long getIa_id() {
		return ia_id;
	}

	/**
	 * @param ia_id the ia_id to set
	 */
	public void setIa_id(Long ia_id) {
		this.ia_id = ia_id;
	}

	/**
	 * @return the cbRegDate
	 */
	public Date getCbRegDate() {
		return cbRegDate;
	}

	/**
	 * @param cbRegDate the cbRegDate to set
	 */
	public void setCbRegDate(Date cbRegDate) {
		this.cbRegDate = cbRegDate;
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
		return new String[] { "SFAC", "TB_SFAC_CB_MASTER", "CB_ID" };
	}

}
