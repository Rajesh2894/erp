/**
 * 
 */
package com.abm.mainet.additionalservices.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.ArrayList;
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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

/**
 * @author divya.marshettiwar
 *
 */
@Entity
@Table(name = "Tb_Echallan_Mst")
public class EChallanMasterEntity implements Serializable{

	private static final long serialVersionUID = -8110120305519049170L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CHLN_ID", nullable = true)
	private Long challanId;
	
	@OneToMany(mappedBy = "challanMaster", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Where(clause = "Status!='N'")
	private List<EChallanItemDetailsEntity> challanItemDetails = new ArrayList<>();
	
	@Column(name = "CHLN_NO")
	private String challanNo;
	
	@Column(name = "CHALLAN_DATE", nullable = true)
	private Date challanDate;
	
	@Column(name = "CHLN_TYPE")
	private String challanType;
	
	@Column(name = "CHLN_DESC", nullable = true)
	private String challanDesc;
	
	@Column(name = "CHLN_AMT", nullable = true)
	private Double challanAmt;
	
	@Column(name = "DUE_DATE", nullable = true)
	private Date dueDate;
	
	@Column(name = "OFFN_NAME")
	private String offenderName;
	
	@Column(name = "OFFN_MOBNO")
	private String offenderMobNo;
	
	@Column(name = "OFFN_EMAIL", nullable = true)
	private String offenderEmail;
	
	@Column(name = "EVIDENCE_IMG", nullable = true)
	private Blob evidenceImg;
	
	@Column(name = "RAID_NO", nullable = true)
	private String raidNo;
	
	@Column(name = "REFERENCE_NO", nullable = true)
	private String referenceNo;
	
	@Column(name = "REMARK", nullable = true)
	private String remark;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "From_Area")
	private String fromArea;
	
	@Column(name = "TO_Area")
	private String toArea;
	
	@Column(name = "Officer_OnSite")
	private String officerOnsite;
	
	@Column(name = "Locality")
	private String locality;
	
	@Column(name = "ORGID")
	private Long orgid;
	
	@Column(name = "CREATED_BY", nullable = true)
	private Long createdBy;
	
	@Column(name = "CREATED_Date")
	private Date createdDate;
	
	@Column(name = "LG_IP_MAC")
	private String lgIpMac;
	
	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;
	
	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;
	
	@Column(name = "LG_IP_MAC_UPD", nullable = true)
	private String lgIpMacUpd;
	
	public static String[] getPkValues() {
		return new String[] { "ENC", "Tb_Echallan_Mst", "CHLN_ID" };
	}

	public Long getChallanId() {
		return challanId;
	}

	public void setChallanId(Long challanId) {
		this.challanId = challanId;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public Date getChallanDate() {
		return challanDate;
	}

	public void setChallanDate(Date challanDate) {
		this.challanDate = challanDate;
	}

	public String getChallanType() {
		return challanType;
	}

	public void setChallanType(String challanType) {
		this.challanType = challanType;
	}

	public String getChallanDesc() {
		return challanDesc;
	}

	public void setChallanDesc(String challanDesc) {
		this.challanDesc = challanDesc;
	}

	public Double getChallanAmt() {
		return challanAmt;
	}

	public void setChallanAmt(Double challanAmt) {
		this.challanAmt = challanAmt;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getOffenderName() {
		return offenderName;
	}

	public void setOffenderName(String offenderName) {
		this.offenderName = offenderName;
	}

	public String getOffenderMobNo() {
		return offenderMobNo;
	}

	public void setOffenderMobNo(String offenderMobNo) {
		this.offenderMobNo = offenderMobNo;
	}
	
	public String getOffenderEmail() {
		return offenderEmail;
	}

	public void setOffenderEmail(String offenderEmail) {
		this.offenderEmail = offenderEmail;
	}

	public Blob getEvidenceImg() {
		return evidenceImg;
	}

	public void setEvidenceImg(Blob evidenceImg) {
		this.evidenceImg = evidenceImg;
	}

	public String getRaidNo() {
		return raidNo;
	}

	public void setRaidNo(String raidNo) {
		this.raidNo = raidNo;
	}
	
	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFromArea() {
		return fromArea;
	}

	public void setFromArea(String fromArea) {
		this.fromArea = fromArea;
	}

	public String getToArea() {
		return toArea;
	}

	public void setToArea(String toArea) {
		this.toArea = toArea;
	}

	public String getOfficerOnsite() {
		return officerOnsite;
	}

	public void setOfficerOnsite(String officerOnsite) {
		this.officerOnsite = officerOnsite;
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

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
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

	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public List<EChallanItemDetailsEntity> getChallanItemDetails() {
		return challanItemDetails;
	}

	public void setChallanItemDetails(List<EChallanItemDetailsEntity> challanItemDetails) {
		this.challanItemDetails = challanItemDetails;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}
	
	
}
