package com.abm.mainet.legal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_LGL_LEGAL_OPINION")
public class LegalOpinionEntity {
	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "ID", unique = true, nullable = false)
	Long id;
	@Column(name ="CSE_ID")
	Long cseId;
	@Column(name ="LOC_ID")
	Long locId;
	@Column(name ="DEPT_ID")
	Long opniondeptId;
	@Column(name ="MATTER_OF_DISPUTE")
	String matterOfDispute;
	@Column(name ="OPINION")
	String opinion;
	@Column(name ="SECTION_ACT_APPLIED")
	String sectionActApplied;
	@Column(name ="REMARK")
	String remark;
	@Column(name ="DEPARTMENT_REMARK")
	String deptRemark;
	@Column(name ="APM_APPLICATION_ID")
	Long apmApplicationId;
	@Column(name ="SM_SERVICE_ID")
	Long smServiceId;
	@Column(name ="ORGID")
	Long orgId;
	@Column(name ="CREATED_BY")
	Long createdBy;
	@Column(name ="CREATED_DATE")
	Date createdDate;
	@Column(name ="UPDATED_BY")
	Long updatedBy;
	@Column(name ="UPDATED_DATE")
	Date updateddate;
	@Column(name ="LG_IP_MAC")
	String ipMacAdrress;
	@Column(name ="LG_IP_MAC_UPD")
	String updatedIpMac;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCseId() {
		return cseId;
	}

	public void setCseId(Long cseId) {
		this.cseId = cseId;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	
	public Long getOpniondeptId() {
		return opniondeptId;
	}

	public void setOpniondeptId(Long opniondeptId) {
		this.opniondeptId = opniondeptId;
	}

	public String getMatterOfDispute() {
		return matterOfDispute;
	}

	public void setMatterOfDispute(String matterOfDispute) {
		this.matterOfDispute = matterOfDispute;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getSectionActApplied() {
		return sectionActApplied;
	}

	public void setSectionActApplied(String sectionActApplied) {
		this.sectionActApplied = sectionActApplied;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDeptRemark() {
		return deptRemark;
	}

	public void setDeptRemark(String deptRemark) {
		this.deptRemark = deptRemark;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public Date getUpdateddate() {
		return updateddate;
	}

	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	public String getIpMacAdrress() {
		return ipMacAdrress;
	}

	public void setIpMacAdrress(String ipMacAdrress) {
		this.ipMacAdrress = ipMacAdrress;
	}

	public String getUpdatedIpMac() {
		return updatedIpMac;
	}

	public void setUpdatedIpMac(String updatedIpMac) {
		this.updatedIpMac = updatedIpMac;
	}
	public String[] getPkValues() {
		return new String[] { "LGL", "TB_LGL_LEGAL_OPINION", "ID" };
	}
}
