package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;

public class DeptItemDetailsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long dreturnsdetid;
	private Long dreturnid;  
	private Long orgid;
	private Long userid;
	private Long langId;
	private Date lmoddate;
	private Character status;
	private Long updatedBy;
	private Date updatedDate;
	private Long desgId;
	private String lgIpMac;
	private String uomDesc;
	private String itemDesc;
	private String storeDesc;
	private Long issueqty;
	private Long returnqty;
	private Long itemcondition;
	private Long reasonforreturn;
	private Character disposalflag;
	private Long binlocation;
	private Long itemid;
	private Long primId;
	private String lgIpMacUpd;
	private String itemno;
	private String wfFlag;
	private Long preReceivedQty;

	private DeptReturnDTO deptReturnDTO;

	public Long getDreturnid() {
		return dreturnid;
	}

	public void setDreturnid(Long dreturnid) {
		this.dreturnid = dreturnid;
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


	public Long getDesgId() {
		return desgId;
	}

	public void setDesgId(Long desgId) {
		this.desgId = desgId;
	}

	public String getStoreDesc() {
		return storeDesc;
	}

	public void setStoreDesc(String storeDesc) {
		this.storeDesc = storeDesc;
	}

	public String getUomDesc() {
		return uomDesc;
	}

	public void setUomDesc(String uomDesc) {
		this.uomDesc = uomDesc;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public Long getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(Long issueqty) {
		this.issueqty = issueqty;
	}

	public Long getReturnqty() {
		return returnqty;
	}

	public void setReturnqty(Long returnqty) {
		this.returnqty = returnqty;
	}

	public Long getItemcondition() {
		return itemcondition;
	}

	public void setItemcondition(Long itemcondition) {
		this.itemcondition = itemcondition;
	}

	public Long getReasonforreturn() {
		return reasonforreturn;
	}

	public void setReasonforreturn(Long reasonforreturn) {
		this.reasonforreturn = reasonforreturn;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Character getDisposalflag() {
		return disposalflag;
	}

	public void setDisposalflag(Character disposalflag) {
		this.disposalflag = disposalflag;
	}

	public Long getBinlocation() {
		return binlocation;
	}

	public void setBinlocation(Long binlocation) {
		this.binlocation = binlocation;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public DeptReturnDTO getDeptReturnDTO() {
		return deptReturnDTO;
	}

	public void setDeptReturnDTO(DeptReturnDTO deptReturnDTO) {
		this.deptReturnDTO = deptReturnDTO;
	}

	public Long getDreturnsdetid() {
		return dreturnsdetid;
	}

	public void setDreturnsdetid(Long dreturnsdetid) {
		this.dreturnsdetid = dreturnsdetid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getPrimId() {
		return primId;
	}

	public void setPrimId(Long primId) {
		this.primId = primId;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public Long getPreReceivedQty() {
		return preReceivedQty;
	}

	public void setPreReceivedQty(Long preReceivedQty) {
		this.preReceivedQty = preReceivedQty;
	}
	

}
