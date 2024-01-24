package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndentProcessItemDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long inditemid;

	private Long indentid;

	private Long storeid;

	private Long itemid;

	private String itemName;
	
	private BigDecimal quantity;

	private BigDecimal issuedqty;

	private String Remarks;

	private String Status;

	private String uom;

	private Long orgid;

	private Long user_id;

	private Long langId;

	private Date lmoddate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String managementCode;
	
	private String managementDesc;

	IndentIssueDto indentIssueDto = new IndentIssueDto();

	List<IndentIssueDto> indentIssueDtoList = new ArrayList<>();

	public Long getInditemid() {
		return inditemid;
	}

	public void setInditemid(Long inditemid) {
		this.inditemid = inditemid;
	}

	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public Long getStoreid() {
		return storeid;
	}

	public void setStoreid(Long storeid) {
		this.storeid = storeid;
	}

	public Long getItemid() {
		return itemid;
	}

	public void setItemid(Long itemid) {
		this.itemid = itemid;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getIssuedqty() {
		return issuedqty;
	}

	public void setIssuedqty(BigDecimal issuedqty) {
		this.issuedqty = issuedqty;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String Remarks) {
		this.Remarks = Remarks;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String Status) {
		this.Status = Status;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
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

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getManagementCode() {
		return managementCode;
	}

	public void setManagementCode(String managementCode) {
		this.managementCode = managementCode;
	}

	public String getManagementDesc() {
		return managementDesc;
	}

	public void setManagementDesc(String managementDesc) {
		this.managementDesc = managementDesc;
	}

	public IndentIssueDto getIndentIssueDto() {
		return indentIssueDto;
	}

	public void setIndentIssueDto(IndentIssueDto indentIssueDto) {
		this.indentIssueDto = indentIssueDto;
	}

	public List<IndentIssueDto> getIndentIssueDtoList() {
		return indentIssueDtoList;
	}

	public void setIndentIssueDtoList(List<IndentIssueDto> indentIssueDtoList) {
		this.indentIssueDtoList = indentIssueDtoList;
	}

}
