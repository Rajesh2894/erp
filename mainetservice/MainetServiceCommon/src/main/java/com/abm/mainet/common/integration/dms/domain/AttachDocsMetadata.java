package com.abm.mainet.common.integration.dms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_ATTACH_DOCUMENT_METADATA")
public class AttachDocsMetadata {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MD_ID", nullable = false)
	private Long mdId;

	@Column(name = "DMS_DOC_ID")
	private String dmsDocId;

	@Column(name = "ATD_IDF_ID")
	private String idfId;

	@Column(name = "DEPT_ID")
	private String deptId;

	@Column(name = "MT_KEY")
	private String mtKey;

	@Column(name = "MT_VAL")
	private String mtVal;

	@Column(name = "DOC_DATE")
	private Date docDate;

	@Column(name = "DOC_TYPE")
	private String docType;

	@Column(name = "IS_ACTIVE")
	private String isActive;

	@Column(name = "ORGID")
	private Long orgid;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;

	public Long getMdId() {
		return mdId;
	}

	public void setMdId(Long mdId) {
		this.mdId = mdId;
	}

	public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

	public String getIdfId() {
		return idfId;
	}

	public void setIdfId(String idfId) {
		this.idfId = idfId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getMtKey() {
		return mtKey;
	}

	public void setMtKey(String mtKey) {
		this.mtKey = mtKey;
	}

	public String getMtVal() {
		return mtVal;
	}

	public void setMtVal(String mtVal) {
		this.mtVal = mtVal;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

		return new String[] { "CFC", "TB_ATTACH_DOCUMENT_METADATA", "MD_ID" };
	}
}
