/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "Tb_Sfac_Equity_Grant_Det")
public class EquityGrantDetailEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612293773939767068L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "EGD_ID", nullable = false)
	private Long egdId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "EG_ID", referencedColumnName = "EG_ID")
	private EquityGrantMasterEntity masterEntity;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "DIN_NO")
	private Long dinNo;

	@Column(name = "QUALIFICATION")
	private String qualification;
	
	@Column(name = "AADHAAR_NO")
	private Long aadhaarNo;

	@Column(name = "TENURE")
	private Long tenure;

	@Column(name = "CONTACT_NO_ADDRESS")
	private String contactNoAddress;

	@Column(name = "LAND_HOLDING")
	private Long landHolding;
	
	@Column(name = "IS_BOM")
	private String isBOM;

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
	 * @return the egdId
	 */
	public Long getEgdId() {
		return egdId;
	}

	/**
	 * @param egdId the egdId to set
	 */
	public void setEgdId(Long egdId) {
		this.egdId = egdId;
	}

	/**
	 * @return the masterEntity
	 */
	public EquityGrantMasterEntity getMasterEntity() {
		return masterEntity;
	}

	/**
	 * @param masterEntity the masterEntity to set
	 */
	public void setMasterEntity(EquityGrantMasterEntity masterEntity) {
		this.masterEntity = masterEntity;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the dinNo
	 */
	public Long getDinNo() {
		return dinNo;
	}

	/**
	 * @param dinNo the dinNo to set
	 */
	public void setDinNo(Long dinNo) {
		this.dinNo = dinNo;
	}

	/**
	 * @return the qualification
	 */
	public String getQualification() {
		return qualification;
	}

	/**
	 * @param qualification the qualification to set
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	/**
	 * @return the tenure
	 */
	public Long getTenure() {
		return tenure;
	}

	/**
	 * @param tenure the tenure to set
	 */
	public void setTenure(Long tenure) {
		this.tenure = tenure;
	}

	/**
	 * @return the contactNoAddress
	 */
	public String getContactNoAddress() {
		return contactNoAddress;
	}

	/**
	 * @param contactNoAddress the contactNoAddress to set
	 */
	public void setContactNoAddress(String contactNoAddress) {
		this.contactNoAddress = contactNoAddress;
	}

	/**
	 * @return the landHolding
	 */
	public Long getLandHolding() {
		return landHolding;
	}

	/**
	 * @param landHolding the landHolding to set
	 */
	public void setLandHolding(Long landHolding) {
		this.landHolding = landHolding;
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
	
	
	

	public String getIsBOM() {
		return isBOM;
	}

	public void setIsBOM(String isBOM) {
		this.isBOM = isBOM;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "TB_SFAC_EQUITY_GRANT_DET", "EGD_ID" };
	}

	public Long getAadhaarNo() {
		return aadhaarNo;
	}

	public void setAadhaarNo(Long aadhaarNo) {
		this.aadhaarNo = aadhaarNo;
	}
	
	

}
