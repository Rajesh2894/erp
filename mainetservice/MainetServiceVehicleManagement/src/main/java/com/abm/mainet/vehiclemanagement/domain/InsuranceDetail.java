package com.abm.mainet.vehiclemanagement.domain;

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
@Table(name="tb_vm_insurance_detail")
public class InsuranceDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5577076535547703433L;
    
	
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "insurance_Id", unique = true, nullable = false)
	private Long insuranceDetId;

	@Column(name = "dept_id")
	private Long department;

	@Column(name = "vehicle_Type")
	private Long vehicleType;

	@Column(name = "vehicle_No")
	private Long veId;

	@Column(name = "insured_by")
	private Long insuredBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "issue_date")
	private Date issueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "insured_amt")
	private Double insuredAmount;

	@Column(name = "Policy_No")
	private String policyNo; 
	
	private Long orgid;
	
	@Column(name = "created_by", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	private Date createdDate;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "lg_ip_mac", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd", length = 100)
	private String lgIpMacUpd;

	public Long getInsuranceDetId() {
		return insuranceDetId;
	}

	public void setInsuranceDetId(Long insuranceDetId) {
		this.insuranceDetId = insuranceDetId;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public Long getInsuredBy() {
		return insuredBy;
	}

	public void setInsuredBy(Long insuredBy) {
		this.insuredBy = insuredBy;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getInsuredAmount() {
		return insuredAmount;
	}

	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
		return new String[] { "VM", "tb_vm_insurance_detail", "insurance_Id" };
	}
	
	
	
}






