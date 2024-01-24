package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class IndentIssueDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long indissuemid;

	private Long indentid;

	private Long itemid;

	private Long inditemid;

	private Long binlocation;

	private String itemno;

	private Long issueqty;

	private String isreturned;

	private Long dreturnitem;

	private Long returnqty;

	private String Status;

	private Long ORGID;

	private Long USER_ID;

	private Long LANGID;

	private Date lmoddate;

	private Long updatedBy;

	private Date updatedDate;

	private String LG_IP_MAC;

	private String LG_IP_MAC_UPD;

	private Long availableQty;
	private String uomDesc;
	private String itemDesc;
	
	private List<String> itemNumberList = new ArrayList<>();

	
	public List<String> getItemNumberList() {
		return itemNumberList;
	}

	public void setItemNumberList(List<String> itemNumberList) {
		this.itemNumberList = itemNumberList;
	}

	public Long getIndissuemid() {
		return indissuemid;
	}

	public void setIndissuemid(Long indissuemid) {
		this.indissuemid = indissuemid;
	}

	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public Long getInditemid() {
		return inditemid;
	}

	public void setInditemid(Long inditemid) {
		this.inditemid = inditemid;
	}

	public Long getBinlocation() {
		return binlocation;
	}

	public void setBinlocation(Long binlocation) {
		this.binlocation = binlocation;
	}

	public String getItemno() {
		return itemno;
	}

	public void setItemno(String itemno) {
		this.itemno = itemno;
	}

	public Long getIssueqty() {
		return issueqty;
	}

	public void setIssueqty(Long issueqty) {
		this.issueqty = issueqty;
	}

	public String getIsreturned() {
		return isreturned;
	}

	public void setIsreturned(String isreturned) {
		this.isreturned = isreturned;
	}

	public Long getDreturnitem() {
		return dreturnitem;
	}

	public void setDreturnitem(Long dreturnitem) {
		this.dreturnitem = dreturnitem;
	}

	public Long getReturnqty() {
		return returnqty;
	}

	public void setReturnqty(Long returnqty) {
		this.returnqty = returnqty;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Long getORGID() {
		return ORGID;
	}

	public void setORGID(Long oRGID) {
		ORGID = oRGID;
	}

	public Long getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(Long uSER_ID) {
		USER_ID = uSER_ID;
	}

	public Long getLANGID() {
		return LANGID;
	}

	public void setLANGID(Long lANGID) {
		LANGID = lANGID;
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

	public String getLG_IP_MAC() {
		return LG_IP_MAC;
	}

	public void setLG_IP_MAC(String lG_IP_MAC) {
		LG_IP_MAC = lG_IP_MAC;
	}

	public String getLG_IP_MAC_UPD() {
		return LG_IP_MAC_UPD;
	}

	public void setLG_IP_MAC_UPD(String lG_IP_MAC_UPD) {
		LG_IP_MAC_UPD = lG_IP_MAC_UPD;
	}

	public Long getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Long availableQty) {
		this.availableQty = availableQty;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
	
	
}
