package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeptReturnDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long dreturnid;
	private String dreturnno;
	private Date dreturndate;
	private Long indenter;
	private Long reportingmgr;
	private String beneficiary;
	private Long storeid;
	private Character previndent;
	private Long indentid;
	private String noting;
	private Character status;
	private Long orgid;
	private Long userid;
	private Long langId;
	private Date lmoddate;
	private Long deptId;
	private Long updatedBy;
	private String desgName;
	private Date updatedDate;
	private String reportingMgrName;
	private Long desgId;
	private String lgIpMac;
	private Long location;
	private String locationName;
	private String storeName;
	private String storeDesc;
	private String lgIpMacUpd;
	private String wfFlag;
	private String deptName;
	private Date fromDate;
	private Date toDate;
	private String indentNo;
	
	private List<DeptItemDetailsDTO> deptItemDetailsDTOList = new ArrayList<>();

	public Long getDreturnid() {
		return dreturnid;
	}

	public void setDreturnid(Long dreturnid) {
		this.dreturnid = dreturnid;
	}

	public String getDreturnno() {
		return dreturnno;
	}

	public void setDreturnno(String dreturnno) {
		this.dreturnno = dreturnno;
	}

	public Date getDreturndate() {
		return dreturndate;
	}

	public void setDreturndate(Date dreturndate) {
		this.dreturndate = dreturndate;
	}

	public Long getIndenter() {
		return indenter;
	}

	public void setIndenter(Long indenter) {
		this.indenter = indenter;
	}

	public Long getReportingmgr() {
		return reportingmgr;
	}

	public void setReportingmgr(Long reportingmgr) {
		this.reportingmgr = reportingmgr;
	}

	public String getBeneficiary() {
		return beneficiary;
	}

	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Character getPrevindent() {
		return previndent;
	}

	public void setPrevindent(Character previndent) {
		this.previndent = previndent;
	}

	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public String getNoting() {
		return noting;
	}

	public void setNoting(String noting) {
		this.noting = noting;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	
	public Long getLangId() {
		return langId;
	}

	public void setLangId(Long langId) {
		this.langId = langId;
	}

	public Date getLmoddate() {
		return lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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

	public String getWfFlag() {
		return wfFlag;
	}

	public void setWfFlag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDesgName() {
		return desgName;
	}

	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}

	public String getReportingMgrName() {
		return reportingMgrName;
	}

	public void setReportingMgrName(String reportingMgrName) {
		this.reportingMgrName = reportingMgrName;
	}

	public Long getDesgId() {
		return desgId;
	}

	public void setDesgId(Long desgId) {
		this.desgId = desgId;
	}

	public Long getLocation() {
		return location;
	}

	public void setLocation(Long location) {
		this.location = location;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public List<DeptItemDetailsDTO> getDeptItemDetailsDTOList() {
		return deptItemDetailsDTOList;
	}

	public void setDeptItemDetailsDTOList(List<DeptItemDetailsDTO> deptItemDetailsDTOList) {
		this.deptItemDetailsDTOList = deptItemDetailsDTOList;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getIndentNo() {
		return indentNo;
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
	}


}
