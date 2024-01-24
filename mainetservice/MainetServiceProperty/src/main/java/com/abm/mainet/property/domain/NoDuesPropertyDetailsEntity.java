package com.abm.mainet.property.domain;

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
@Table(name = "TB_AS_NO_DUES_PROP_DETAILS")
public class NoDuesPropertyDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "PROPDET_ID")
	private Long propDetId;

	@Column(name = "PROP_NO")
	private String propNo;

	@Column(name = "FLAT_NO")
	private String flatNo;

	@Column(name = "FY_ID")
	private Long finacialYearId;

	@Column(name = "OWNER_NAME")
	private String ownerName;

	@Column(name = "OWNER_ADDRESS")
	private String ownerAddress;

	@Column(name = "APPLICATION_ID")
	private Long applicationId;

	@Column(name = "NO_OF_COPIES")
	private Long noOfCopies;

	@Column(name = "ISDELETED")
	private String isDeleted;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "TOTALOUTSATANDINGAMT")
	private double totalOutsatandingAmt;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	@Column(name = "OUTWARD_NO")
	private String outwardNo;
	
	public Long getPropDetId() {
		return propDetId;
	}

	public void setPropDetId(Long propDetId) {
		this.propDetId = propDetId;
	}

	public String getPropNo() {
		return propNo;
	}

	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}

	public Long getFinacialYearId() {
		return finacialYearId;
	}

	public void setFinacialYearId(Long finacialYearId) {
		this.finacialYearId = finacialYearId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(String ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getNoOfCopies() {
		return noOfCopies;
	}

	public void setNoOfCopies(Long noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public double getTotalOutsatandingAmt() {
		return totalOutsatandingAmt;
	}

	public void setTotalOutsatandingAmt(double totalOutsatandingAmt) {
		this.totalOutsatandingAmt = totalOutsatandingAmt;
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
	
	public String getOutwardNo() {
		return outwardNo;
	}

	public void setOutwardNo(String outwardNo) {
		this.outwardNo = outwardNo;
	}
	
	public String[] getPkValues() {
		return new String[] { "AS", "TB_AS_NO_DUES_PROP_DETAILS", "PROPDET_ID" };

	}
}
