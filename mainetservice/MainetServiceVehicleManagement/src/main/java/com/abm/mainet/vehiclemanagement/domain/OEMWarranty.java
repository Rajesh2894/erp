package com.abm.mainet.vehiclemanagement.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "tb_vm_OEM_Warranty")
public class OEMWarranty implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6820877902035117528L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "OEM_ID", unique = true, nullable = false)
	private Long oemId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "LG_IP_MAC", length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

    private Long orgid;

    @Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "VE_NO")
	private Long veId;

	@Column(name = "VE_VETYPE")
	private Long vehicleType;
	
	@Column(name = "DEPT_ID")
	private Long department;


	@Column(name = "Remarks")
	private String remarks;

	@Temporal(TemporalType.DATE)
	@Column(name = "maintanceDate", nullable = false)
	private Date maintanceDate;
	
//	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy="brId")
//	@JoinColumn(name = "BR_ID")
//	private ParentDetail parentDetail = new ParentDetail();

	// bi-directional many-to-one association to TbSwVehicleScheddet
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tboemwarranty", cascade = CascadeType.ALL)
//	@JoinColumn(name = "OEM_ID")
	//@JsonIgnore
	private List<OEMWarrantyDetails> tbvmoemwrrantydetails;
	
	@Column(name = "REF_NO")
	private String refNo;

	public OEMWarranty() {
	}

	public Long getOemId() {
		return oemId;
	}

	public void setOemId(Long oemId) {
		this.oemId = oemId;
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

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
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

	public Long getVeId() {
		return veId;
	}

	public void setVeId(Long veId) {
		this.veId = veId;
	}

	public Long getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(Long vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<OEMWarrantyDetails> getTbvmoemwrrantydetails() {
		return tbvmoemwrrantydetails;
	}

	public void setTbvmoemwrrantydetails(List<OEMWarrantyDetails> tbvmoemwrrantydetails) {
		this.tbvmoemwrrantydetails = tbvmoemwrrantydetails;
	}

	
	public String[] getPkValues() {

		return new String[] { "VM", "tb_vm_OEM_Warranty", "OEM_ID" };
	}

	public Date getMaintanceDate() {
		return maintanceDate;
	}

	public void setMaintanceDate(Date maintanceDate) {
		this.maintanceDate = maintanceDate;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	

}











