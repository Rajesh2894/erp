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

@Entity
@Table(name = "Tb_SFAC_CBBO_FILED_STAFF_DET")
public class CBBOFiledStaffDetailsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5483555443165913490L;
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FSD_ID", nullable = false)
	private Long fsdId;

	@Column(name = "CBBO_ID")
	private Long cbboId;
	
	
	@Column(name = "CBBO_EXPERT_NAME")
	private String cbboExpertName;
	
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "CONTACT_NO")
	private Long contactNo;
	
	@Column(name = "SDB1")
	private Long sdb1;

	@Column(name = "SDB2")
	private Long sdb2;

	@Column(name = "SDB3")
	private Long sdb3;
	
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
	
	
	
	
	public Long getFsdId() {
		return fsdId;
	}




	public void setFsdId(Long fsdId) {
		this.fsdId = fsdId;
	}




	public Long getCbboId() {
		return cbboId;
	}




	public void setCbboId(Long cbboId) {
		this.cbboId = cbboId;
	}




	public String getCbboExpertName() {
		return cbboExpertName;
	}




	public void setCbboExpertName(String cbboExpertName) {
		this.cbboExpertName = cbboExpertName;
	}




	public String getEmailId() {
		return emailId;
	}




	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}




	public Long getContactNo() {
		return contactNo;
	}




	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}




	public Long getSdb1() {
		return sdb1;
	}




	public void setSdb1(Long sdb1) {
		this.sdb1 = sdb1;
	}




	public Long getSdb2() {
		return sdb2;
	}




	public void setSdb2(Long sdb2) {
		this.sdb2 = sdb2;
	}




	public Long getSdb3() {
		return sdb3;
	}




	public void setSdb3(Long sdb3) {
		this.sdb3 = sdb3;
	}




	public Long getOrgId() {
		return orgId;
	}




	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}




	public Date getCreatedDate() {
		return createdDate;
	}




	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}




	public Long getCreatedBy() {
		return createdBy;
	}




	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}




	public Date getUpdatedDate() {
		return updatedDate;
	}




	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}




	public Long getUpdatedBy() {
		return updatedBy;
	}




	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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




	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_SFAC_CBBO_FILED_STAFF_DET", "FSD_ID" };
	}
	
}
