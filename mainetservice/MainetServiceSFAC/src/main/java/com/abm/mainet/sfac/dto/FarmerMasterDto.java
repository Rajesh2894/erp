package com.abm.mainet.sfac.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author pooja.maske
 * @since 21 September 2022
 */

public class FarmerMasterDto implements Serializable {

	private static final long serialVersionUID = -1424049675337460090L;

	private Long frmId;

	private String frmFPORegNo;

	private String frmName;

	private Long frmType;

	private Long frmGender;

	private Long frmReservation;

	private String frmFatherName;

	private String frmMotherName;

	private Long frmSDB1;

	private Long frmSDB2;

	private Long frmSDB3;

	private Long frmSDB4;

	private Long frmSDB5;

	private Long frmMobNo;

	private Long frmAadharNo;

	private String frmVoterCardNo;

	private Long frmRationCardNo;

	private BigDecimal frmEquityShare;

	private Long frmLandDet;

	private Long frmLandUnit;

	private BigDecimal frmTotalEquity;

	private Long orgId;

	private Long createdBy;

	private Date createdDate;

	private Date updatedDate;

	private Long updatedBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private int langId;

	private String fpoName;

	private String address;

	private String pinCode;

	private Date dateOfBirth;

	private Long landOwned;

	private Long landLeased;

	private List<String> fileList;

	private String fPhoto;

	private String deleteFlag;

	private String filePath;

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
	 * @return the langId
	 */
	public int getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(int langId) {
		this.langId = langId;
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
	 * @return the fileList
	 */
	public List<String> getFileList() {
		return fileList;
	}

	/**
	 * @param fileList the fileList to set
	 */
	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
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

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
