package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class BankMasterDTO  implements Serializable{
	

	    /**
	 * 
	 */
	private static final long serialVersionUID = 8689552963833822004L;

		private String alreadyExists = "N";

	    private String hasError;
	    // ----------------------------------------------------------------------
	    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
	    // ----------------------------------------------------------------------
	    private Long bankId;

	    private String bank;

	    private String bankCode;

	    private String ifsc;

	    private String micr;

	    private String branch;

	    private String address;

	    private String contact;

	    private String city;

	    private String district;

	    private String state;

	    private Long createdBy;

	    private Date lmodDate;

	    private Long updatedBy;

	    private Date updatedDate;

	    private Long langId;

	    @JsonIgnore
	    @Size(max=100)
	    private String lgIpMac;
	    
	    @JsonIgnore
	    @Size(max=100)
	    private String lgIpMacUpd;

	    private Long fi04N1;

	    private String fi04V1;

	    private Date fi04D1;

	    private String bankStatus;
	    
	    @Transient
	    private String uploadFileName;

		public String getAlreadyExists() {
			return alreadyExists;
		}

		public void setAlreadyExists(String alreadyExists) {
			this.alreadyExists = alreadyExists;
		}

		public String getHasError() {
			return hasError;
		}

		public void setHasError(String hasError) {
			this.hasError = hasError;
		}

		public Long getBankId() {
			return bankId;
		}

		public void setBankId(Long bankId) {
			this.bankId = bankId;
		}

		public String getBank() {
			return bank;
		}

		public void setBank(String bank) {
			this.bank = bank;
		}

		public String getBankCode() {
			return bankCode;
		}

		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}

		public String getIfsc() {
			return ifsc;
		}

		public void setIfsc(String ifsc) {
			this.ifsc = ifsc;
		}

		public String getMicr() {
			return micr;
		}

		public void setMicr(String micr) {
			this.micr = micr;
		}

		public String getBranch() {
			return branch;
		}

		public void setBranch(String branch) {
			this.branch = branch;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getContact() {
			return contact;
		}

		public void setContact(String contact) {
			this.contact = contact;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
		}

		public Long getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(Long createdBy) {
			this.createdBy = createdBy;
		}

		public Date getLmodDate() {
			return lmodDate;
		}

		public void setLmodDate(Date lmodDate) {
			this.lmodDate = lmodDate;
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

		public Long getLangId() {
			return langId;
		}

		public void setLangId(Long langId) {
			this.langId = langId;
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

		public Long getFi04N1() {
			return fi04N1;
		}

		public void setFi04N1(Long fi04n1) {
			fi04N1 = fi04n1;
		}

		public String getFi04V1() {
			return fi04V1;
		}

		public void setFi04V1(String fi04v1) {
			fi04V1 = fi04v1;
		}

		public Date getFi04D1() {
			return fi04D1;
		}

		public void setFi04D1(Date fi04d1) {
			fi04D1 = fi04d1;
		}

		public String getBankStatus() {
			return bankStatus;
		}

		public void setBankStatus(String bankStatus) {
			this.bankStatus = bankStatus;
		}

		public String getUploadFileName() {
			return uploadFileName;
		}

		public void setUploadFileName(String uploadFileName) {
			this.uploadFileName = uploadFileName;
		}

	    // generated setters & getters
	    


}
