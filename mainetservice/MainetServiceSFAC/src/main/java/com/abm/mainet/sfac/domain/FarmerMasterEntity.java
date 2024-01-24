package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @since 22 September 2022
 */

@Entity
@Table(name = "Tb_SFAC_Farmer_Mast")
public class FarmerMasterEntity implements Serializable {

	private static final long serialVersionUID = 5703682708252951835L;
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FRM_ID", nullable = false)
	private Long frmId;

	@Column(name = "FRM_FPO_REG_NO", nullable = false)
	private String frmFPORegNo;

	@Column(name = "FRM_NAME", nullable = false)
	private String frmName;

	@Column(name = "FRM_TYPE", nullable = false)
	private Long frmType;

	@Column(name = "FRM_GENDER", nullable = false)
	private Long frmGender;

	@Column(name = "FRM_RESERVATION", nullable = false)
	private Long frmReservation;

	@Column(name = "FRM_FATHER_NAME", nullable = false)
	private String frmFatherName;

	@Column(name = "FRM_MOTHER_NAME", nullable = true)
	private String frmMotherName;

	@Column(name = "FRM_SDB1", nullable = true)
	private Long frmSDB1;

	@Column(name = "FRM_SDB2", nullable = true)
	private Long frmSDB2;

	@Column(name = "FRM_SDB3", nullable = true)
	private Long frmSDB3;

	@Column(name = "FRM_SDB4", nullable = true)
	private Long frmSDB4;

	@Column(name = "FRM_SDB5", nullable = true)
	private Long frmSDB5;

	@Column(name = "FRM_MOB_NO", nullable = false)
	private Long frmMobNo;

	@Column(name = "FRM_AADHAR_NO", nullable = true)
	private Long frmAadharNo;

	@Column(name = "FRM_VOTERCARD_NO", nullable = true)
	private String frmVoterCardNo;

	@Column(name = "FRM_RATIONCARD_NO", nullable = true)
	private Long frmRationCardNo;

	@Column(name = "FRM_EQUITY_SHARE", nullable = false)
	private BigDecimal frmEquityShare;

	@Column(name = "FRM_LAND_DET", nullable = true)
	private Long frmLandDet;

	@Column(name = "FRM_LAND_UNIT", nullable = true)
	private Long frmLandUnit;

	@Column(name = "FRM_TOTAL_EQUITY", nullable = true)
	private BigDecimal frmTotalEquity;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PIN_CODE")
	private String pinCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATE_OF_BIRTH", nullable = false)
	private Date dateOfBirth;

	@Column(name = "LAND_OWNED")
	private Long landOwned;

	@Column(name = "LAND_LEASED")
	private Long landLeased;

	@Column(name = "FRM_PHOTO")
	private String fPhoto;

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
	 * @return the frmId
	 */
	public Long getFrmId() {
		return frmId;
	}

	/**
	 * @param frmId the frmId to set
	 */
	public void setFrmId(Long frmId) {
		this.frmId = frmId;
	}

	/**
	 * @return the frmName
	 */
	public String getFrmName() {
		return frmName;
	}

	/**
	 * @param frmName the frmName to set
	 */
	public void setFrmName(String frmName) {
		this.frmName = frmName;
	}

	/**
	 * @return the frmType
	 */
	public Long getFrmType() {
		return frmType;
	}

	/**
	 * @param frmType the frmType to set
	 */
	public void setFrmType(Long frmType) {
		this.frmType = frmType;
	}

	/**
	 * @return the frmGender
	 */
	public Long getFrmGender() {
		return frmGender;
	}

	/**
	 * @param frmGender the frmGender to set
	 */
	public void setFrmGender(Long frmGender) {
		this.frmGender = frmGender;
	}

	/**
	 * @return the frmReservation
	 */
	public Long getFrmReservation() {
		return frmReservation;
	}

	/**
	 * @param frmReservation the frmReservation to set
	 */
	public void setFrmReservation(Long frmReservation) {
		this.frmReservation = frmReservation;
	}

	/**
	 * @return the frmFatherName
	 */
	public String getFrmFatherName() {
		return frmFatherName;
	}

	/**
	 * @param frmFatherName the frmFatherName to set
	 */
	public void setFrmFatherName(String frmFatherName) {
		this.frmFatherName = frmFatherName;
	}

	/**
	 * @return the frmMotherName
	 */
	public String getFrmMotherName() {
		return frmMotherName;
	}

	/**
	 * @param frmMotherName the frmMotherName to set
	 */
	public void setFrmMotherName(String frmMotherName) {
		this.frmMotherName = frmMotherName;
	}

	/**
	 * @return the frmSDB1
	 */
	public Long getFrmSDB1() {
		return frmSDB1;
	}

	/**
	 * @param frmSDB1 the frmSDB1 to set
	 */
	public void setFrmSDB1(Long frmSDB1) {
		this.frmSDB1 = frmSDB1;
	}

	/**
	 * @return the frmSDB2
	 */
	public Long getFrmSDB2() {
		return frmSDB2;
	}

	/**
	 * @param frmSDB2 the frmSDB2 to set
	 */
	public void setFrmSDB2(Long frmSDB2) {
		this.frmSDB2 = frmSDB2;
	}

	/**
	 * @return the frmSDB3
	 */
	public Long getFrmSDB3() {
		return frmSDB3;
	}

	/**
	 * @param frmSDB3 the frmSDB3 to set
	 */
	public void setFrmSDB3(Long frmSDB3) {
		this.frmSDB3 = frmSDB3;
	}

	/**
	 * @return the frmSDB4
	 */
	public Long getFrmSDB4() {
		return frmSDB4;
	}

	/**
	 * @param frmSDB4 the frmSDB4 to set
	 */
	public void setFrmSDB4(Long frmSDB4) {
		this.frmSDB4 = frmSDB4;
	}

	/**
	 * @return the frmSDB5
	 */
	public Long getFrmSDB5() {
		return frmSDB5;
	}

	/**
	 * @param frmSDB5 the frmSDB5 to set
	 */
	public void setFrmSDB5(Long frmSDB5) {
		this.frmSDB5 = frmSDB5;
	}

	/**
	 * @return the frmMobNo
	 */
	public Long getFrmMobNo() {
		return frmMobNo;
	}

	/**
	 * @param frmMobNo the frmMobNo to set
	 */
	public void setFrmMobNo(Long frmMobNo) {
		this.frmMobNo = frmMobNo;
	}

	/**
	 * @return the frmAadharNo
	 */
	public Long getFrmAadharNo() {
		return frmAadharNo;
	}

	/**
	 * @param frmAadharNo the frmAadharNo to set
	 */
	public void setFrmAadharNo(Long frmAadharNo) {
		this.frmAadharNo = frmAadharNo;
	}

	/**
	 * @return the frmVoterCardNo
	 */
	public String getFrmVoterCardNo() {
		return frmVoterCardNo;
	}

	/**
	 * @param frmVoterCardNo the frmVoterCardNo to set
	 */
	public void setFrmVoterCardNo(String frmVoterCardNo) {
		this.frmVoterCardNo = frmVoterCardNo;
	}

	/**
	 * @return the frmRationCardNo
	 */
	public Long getFrmRationCardNo() {
		return frmRationCardNo;
	}

	/**
	 * @param frmRationCardNo the frmRationCardNo to set
	 */
	public void setFrmRationCardNo(Long frmRationCardNo) {
		this.frmRationCardNo = frmRationCardNo;
	}

	/**
	 * @return the frmEquityShare
	 */
	public BigDecimal getFrmEquityShare() {
		return frmEquityShare;
	}

	/**
	 * @param frmEquityShare the frmEquityShare to set
	 */
	public void setFrmEquityShare(BigDecimal frmEquityShare) {
		this.frmEquityShare = frmEquityShare;
	}

	/**
	 * @return the frmLandDet
	 */
	public Long getFrmLandDet() {
		return frmLandDet;
	}

	/**
	 * @param frmLandDet the frmLandDet to set
	 */
	public void setFrmLandDet(Long frmLandDet) {
		this.frmLandDet = frmLandDet;
	}

	/**
	 * @return the frmLandUnit
	 */
	public Long getFrmLandUnit() {
		return frmLandUnit;
	}

	/**
	 * @param frmLandUnit the frmLandUnit to set
	 */
	public void setFrmLandUnit(Long frmLandUnit) {
		this.frmLandUnit = frmLandUnit;
	}

	/**
	 * @return the frmTotalEquity
	 */
	public BigDecimal getFrmTotalEquity() {
		return frmTotalEquity;
	}

	/**
	 * @param frmTotalEquity the frmTotalEquity to set
	 */
	public void setFrmTotalEquity(BigDecimal frmTotalEquity) {
		this.frmTotalEquity = frmTotalEquity;
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
	 * @return the frmFPORegNo
	 */
	public String getFrmFPORegNo() {
		return frmFPORegNo;
	}

	/**
	 * @param frmFPORegNo the frmFPORegNo to set
	 */
	public void setFrmFPORegNo(String frmFPORegNo) {
		this.frmFPORegNo = frmFPORegNo;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the landOwned
	 */
	public Long getLandOwned() {
		return landOwned;
	}

	/**
	 * @param landOwned the landOwned to set
	 */
	public void setLandOwned(Long landOwned) {
		this.landOwned = landOwned;
	}

	/**
	 * @return the landLeased
	 */
	public Long getLandLeased() {
		return landLeased;
	}

	/**
	 * @param landLeased the landLeased to set
	 */
	public void setLandLeased(Long landLeased) {
		this.landLeased = landLeased;
	}

	/**
	 * @return the fPhoto
	 */
	public String getfPhoto() {
		return fPhoto;
	}

	/**
	 * @param fPhoto the fPhoto to set
	 */
	public void setfPhoto(String fPhoto) {
		this.fPhoto = fPhoto;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_Farmer_Mast", "FRM_ID" };
	}

}
