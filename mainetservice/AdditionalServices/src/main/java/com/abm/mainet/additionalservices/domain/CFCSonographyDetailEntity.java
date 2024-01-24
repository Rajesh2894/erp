package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */
@Entity
@Table(name = "TB_CFC_SONOGRAPHY_DETAIL")
public class CFCSonographyDetailEntity implements Serializable{
	
	
	private static final long serialVersionUID = 7562277970138598674L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "REG_DET_ID", nullable = false)
	private Long regDetId;

	@ManyToOne
	@JoinColumn(name = "REG_ID", nullable = false)
	private CFCSonographyMasterEntity cfcSonographyMasEntity;
	
	@Column(name = "FACILITIES_AT_CENTER", nullable = true)
	private Long facilityCenter;
	
	@Column(name = "FACILITIES_FOR_TEST", nullable = true)
	private Long facilityTest;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date creationDate;

	@Column(name = "CREATED_BY", updatable = false, nullable = false)
	private Long createdBy;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	 
	public Long getRegDetId() {
		return regDetId;
	}


	public void setRegDetId(Long regDetId) {
		this.regDetId = regDetId;
	}


	public CFCSonographyMasterEntity getCfcSonographyMasEntity() {
		return cfcSonographyMasEntity;
	}


	public void setCfcSonographyMasEntity(CFCSonographyMasterEntity cfcSonographyMasEntity) {
		this.cfcSonographyMasEntity = cfcSonographyMasEntity;
	}


	public Long getFacilityCenter() {
		return facilityCenter;
	}


	public void setFacilityCenter(Long facilityCenter) {
		this.facilityCenter = facilityCenter;
	}


	public Long getFacilityTest() {
		return facilityTest;
	}


	public void setFacilityTest(Long facilityTest) {
		this.facilityTest = facilityTest;
	}


	public Long getOrgId() {
		return orgId;
	}


	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}


	public String getLgIpMac() {
		return lgIpMac;
	}


	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}


	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}


	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}


	public Long getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}


	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public static String[] getPkValues() {
		return new String[] { "CFC", "TB_CFC_SONOGRAPHY_DETAIL", "REG_DET_ID" };
	}


}
