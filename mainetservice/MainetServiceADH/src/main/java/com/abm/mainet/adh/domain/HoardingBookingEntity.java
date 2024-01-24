package com.abm.mainet.adh.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_ADH_HOARDING_BOOKING")
public class HoardingBookingEntity implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3808859615959587811L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "HRD_BKID", length = 12, nullable = false)
	private Long adhHrdBKId;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ADH_ID", nullable = false)
	private NewAdvertisementApplication newAdvertisement;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HRD_ID", nullable = false)
	private HoardingMasterEntity hoardingMasterEntity;

	@Column(name = "ORGID", length = 12, nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", length = 12, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", length = 12, nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	/**
	 * @return the adhHrdBKId
	 */
	public Long getAdhHrdBKId() {
		return adhHrdBKId;
	}

	/**
	 * @param adhHrdBKId the adhHrdBKId to set
	 */
	public void setAdhHrdBKId(Long adhHrdBKId) {
		this.adhHrdBKId = adhHrdBKId;
	}

	/**
	 * @return the newAdvertisement
	 */
	public NewAdvertisementApplication getNewAdvertisement() {
		return newAdvertisement;
	}

	/**
	 * @param newAdvertisement the newAdvertisement to set
	 */
	public void setNewAdvertisement(NewAdvertisementApplication newAdvertisement) {
		this.newAdvertisement = newAdvertisement;
	}

	/**
	 * @return the hoardingMasterEntity
	 */
	public HoardingMasterEntity getHoardingMasterEntity() {
		return hoardingMasterEntity;
	}

	/**
	 * @param hoardingMasterEntity the hoardingMasterEntity to set
	 */
	public void setHoardingMasterEntity(HoardingMasterEntity hoardingMasterEntity) {
		this.hoardingMasterEntity = hoardingMasterEntity;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getPkValues() {
		return new String[] { "ADH", "TB_ADH_HOARDING_BOOKING", "HRD_BKID" };
	}

}
