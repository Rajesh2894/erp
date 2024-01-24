package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class PurchaseorderAttachmentDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long podocId;
	private Long poId;
	private String atdPath;
	private String atdFname;
	private String description;
	private String atdBy;
	private String atdFromPath;
	private char atdStatus;
	private int atdSrNo;
	private String dmsDocId;
	private String dmsFolderPath;
	private String dmsDocName;
	private String dmsDocVersion;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String LgIpMacUpd;
	
	private List<AttachDocs> attachDocsList = new ArrayList<>();
	private List<DocumentDetailsVO> attachments = new ArrayList<>();

	
	public Long getPodocId() {
		return podocId;
	}
	public void setPodocId(Long podocId) {
		this.podocId = podocId;
	}
	public Long getPoId() {
		return poId;
	}
	public void setPoId(Long poId) {
		this.poId = poId;
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
	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}
	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}
	public List<DocumentDetailsVO> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DocumentDetailsVO> attachments) {
		this.attachments = attachments;
	}
	
}
