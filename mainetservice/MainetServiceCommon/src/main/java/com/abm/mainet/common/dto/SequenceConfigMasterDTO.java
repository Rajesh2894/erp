package com.abm.mainet.common.dto;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SequenceConfigMasterDTO implements Serializable {

	private static final long serialVersionUID = 3187464753630069617L;

	private Long seqConfigId;

	private List<SequenceConfigDetDTO> configDetDTOs = new ArrayList<>();

	private Date fromDate;

	private Date endDate;

	private Long seqName;

	private Long catId;

	private Long deptId;

	private Long seqType;

	private Long seqSep;

	private Long seqLength;

	private String custSeq;

	private Long seqFrmNo;

	private String seqStatus;

	private Long orgId;

	private Date creationDate;

	private Long createdBy;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String deptName;

	private String seqTypeName;

	private String catName;

	private Long updatedBy;

	private Date updatedDate;

	private String seqtbName;

	private SequenceConfigDetDTO configDetDTO;

	private String editFlag;

	public SequenceConfigDetDTO getConfigDetDTO() {
		return configDetDTO;
	}

	public void setConfigDetDTO(SequenceConfigDetDTO configDetDTO) {
		this.configDetDTO = configDetDTO;
	}

	public String getSeqtbName() {
		return seqtbName;
	}

	public void setSeqtbName(String seqtbName) {
		this.seqtbName = seqtbName;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public String getSeqTypeName() {
		return seqTypeName;
	}

	public void setSeqTypeName(String seqTypeName) {
		this.seqTypeName = seqTypeName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Long getSeqConfigId() {
		return seqConfigId;
	}

	public void setSeqConfigId(Long seqConfigId) {
		this.seqConfigId = seqConfigId;
	}

	public List<SequenceConfigDetDTO> getConfigDetDTOs() {
		return configDetDTOs;
	}

	public void setConfigDetDTOs(List<SequenceConfigDetDTO> configDetDTOs) {
		this.configDetDTOs = configDetDTOs;
	}

	public Long getSeqName() {
		return seqName;
	}

	public void setSeqName(Long seqName) {
		this.seqName = seqName;
	}

	public Long getCatId() {
		return catId;
	}

	public void setCatId(Long catId) {
		this.catId = catId;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public Long getSeqType() {
		return seqType;
	}

	public void setSeqType(Long seqType) {
		this.seqType = seqType;
	}

	public Long getSeqSep() {
		return seqSep;
	}

	public void setSeqSep(Long seqSep) {
		this.seqSep = seqSep;
	}

	public Long getSeqLength() {
		return seqLength;
	}

	public void setSeqLength(Long seqLength) {
		this.seqLength = seqLength;
	}

	public String getCustSeq() {
		return custSeq;
	}

	public void setCustSeq(String custSeq) {
		this.custSeq = custSeq;
	}

	public Long getSeqFrmNo() {
		return seqFrmNo;
	}

	public void setSeqFrmNo(Long seqFrmNo) {
		this.seqFrmNo = seqFrmNo;
	}

	public String getSeqStatus() {
		return seqStatus;
	}

	public void setSeqStatus(String seqStatus) {
		this.seqStatus = seqStatus;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String getEditFlag() {
		return editFlag;
	}

	public void setEditFlag(String editFlag) {
		this.editFlag = editFlag;
	}

	@Override
	public String toString() {
		return "SequenceConfigMasterDTO [seqConfigId=" + seqConfigId + ", configDetDTOs=" + configDetDTOs
				+ ", fromDate=" + fromDate + ", endDate=" + endDate + ", seqName=" + seqName + ", catId=" + catId
				+ ", deptId=" + deptId + ", seqType=" + seqType + ", seqSep=" + seqSep + ", seqLength=" + seqLength
				+ ", custSeq=" + custSeq + ", seqFrmNo=" + seqFrmNo + ", seqStatus=" + seqStatus + ", orgId=" + orgId
				+ ", creationDate=" + creationDate + ", createdBy=" + createdBy + ", lgIpMac=" + lgIpMac
				+ ", lgIpMacUpd=" + lgIpMacUpd + ", deptName=" + deptName + ", seqTypeName=" + seqTypeName
				+ ", catName=" + catName + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + "]";
	}

}
