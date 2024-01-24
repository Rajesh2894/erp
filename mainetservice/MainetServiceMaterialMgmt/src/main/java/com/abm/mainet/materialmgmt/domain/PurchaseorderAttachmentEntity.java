package com.abm.mainet.materialmgmt.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MM_PURCHASEORDER_ATTACHMENT")
public class PurchaseorderAttachmentEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "podocid")
	private Long podocId;
	
	@ManyToOne
    @JoinColumn(name = "poid", nullable = false)
    private PurchaseOrderEntity purchaseOrderEntity;

	@Column(name = "ATD_PATH")
	private String atdPath;

	@Column(name = "ATD_FNAME")
	private String atdFname;

	@Column(name = "description")
	private String description;

	@Column(name = "ATD_BY")
	private String atdBy;

	@Column(name = "ATD_FROM_PATH")
	private String atdFromPath;

	@Column(name = "ATD_STATUS")
	private char atdStatus;

	@Column(name = "ATD_SRNO")
	private int atdSrNo;

	@Column(name = "DMS_DOC_ID")
	private String dmsDocId;

	@Column(name = "DMS_FOLDER_PATH")
	private String dmsFolderPath;

	@Column(name = "DMS_DOC_NAME")
	private String dmsDocName;

	@Column(name = "DMS_DOC_VERSION")
	private String dmsDocVersion;

	@Column(name = "ORGID")
	private Long orgId;

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
	private String LgIpMacUpd;
	
	public PurchaseorderAttachmentEntity() {
		
	}

	public Long getPodocId() {
		return podocId;
	}

	public void setPodocId(Long podocId) {
		this.podocId = podocId;
	}
	
	public String getAtdPath() {
		return atdPath;
	}

	public void setAtdPath(String atdPath) {
		this.atdPath = atdPath;
	}

	public String getAtdFname() {
		return atdFname;
	}

	public void setAtdFname(String atdFname) {
		this.atdFname = atdFname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAtdBy() {
		return atdBy;
	}

	public void setAtdBy(String atdBy) {
		this.atdBy = atdBy;
	}

	public String getAtdFromPath() {
		return atdFromPath;
	}

	public void setAtdFromPath(String atdFromPath) {
		this.atdFromPath = atdFromPath;
	}

	public char getAtdStatus() {
		return atdStatus;
	}

	public void setAtdStatus(char atdStatus) {
		this.atdStatus = atdStatus;
	}

	public int getAtdSrNo() {
		return atdSrNo;
	}

	public void setAtdSrNo(int atdSrNo) {
		this.atdSrNo = atdSrNo;
	}

	public String getDmsDocId() {
		return dmsDocId;
	}

	public void setDmsDocId(String dmsDocId) {
		this.dmsDocId = dmsDocId;
	}

	public String getDmsFolderPath() {
		return dmsFolderPath;
	}

	public void setDmsFolderPath(String dmsFolderPath) {
		this.dmsFolderPath = dmsFolderPath;
	}

	public String getDmsDocName() {
		return dmsDocName;
	}

	public void setDmsDocName(String dmsDocName) {
		this.dmsDocName = dmsDocName;
	}

	public String getDmsDocVersion() {
		return dmsDocVersion;
	}

	public void setDmsDocVersion(String dmsDocVersion) {
		this.dmsDocVersion = dmsDocVersion;
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
		return LgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		LgIpMacUpd = lgIpMacUpd;
	}
	
	public PurchaseOrderEntity getPurchaseOrderEntity() {
		return purchaseOrderEntity;
	}

	public void setPurchaseOrderEntity(PurchaseOrderEntity purchaseOrderEntity) {
		this.purchaseOrderEntity = purchaseOrderEntity;
	}

	public String[] getPkValues() {

        return new String[] { "MMM", "MM_PURCHASEORDER_ATTACHMENT", "podocid" };
    }
	
}
