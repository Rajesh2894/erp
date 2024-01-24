package com.abm.mainet.materialmgmt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndentProcessDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long indentid;

	private String indentno;

	private Date indentdate;

	private Long indenter;

	private Long reportingmgr;

	private String reportingMgrName;
	
	private Long desgId;

	private String desgName;

	private String beneficiary;

	private Long storeid;

	private String deliveryat;

	private Date expecteddate;

	private Long issuedby;

	private Date issuedDate;

	private String status;

	private Long orgid;

	private Long user_id;

	private Long langId;

	private Date lmoddate;

	private Long updatedBy;

	private Date updatedDate;

	private String lgIpMac;

	private String lgIpMacUpd;

	private String wfFlag;

	private Long deptId;
	
	private String deptName;

	private Long location;

	private String locationName;

	private String storeDesc;

	private String indenterName;

	IndentProcessItemDto indentProcessItemDto = new IndentProcessItemDto();
	
	private List<IndentProcessItemDto> item = new ArrayList<>();

    private String removeFileById;
	
	public Long getIndentid() {
		return indentid;
	}

	public void setIndentid(Long indentid) {
		this.indentid = indentid;
	}

	public String getIndentno() {
		return indentno;
	}

	public void setIndentno(String indentno) {
		this.indentno = indentno;
	}

	public Date getIndentdate() {
		return indentdate;
	}

	public void setIndentdate(Date indentdate) {
		this.indentdate = indentdate;
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

	public String getDeliveryat() {
		return deliveryat;
	}

	public void setDeliveryat(String deliveryat) {
		this.deliveryat = deliveryat;
	}

	public Date getExpecteddate() {
		return expecteddate;
	}

	public void setExpecteddate(Date expecteddate) {
		this.expecteddate = expecteddate;
	}

	public Long getIssuedby() {
		return issuedby;
	}

	public void setIssuedby(Long issuedby) {
		this.issuedby = issuedby;
	}

	public Date getIssuedDate() {
		return issuedDate;
	}

	public void setIssuedDate(Date issuedDate) {
		this.issuedDate = issuedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getWf_flag() {
		return wfFlag;
	}

	public void setWf_flag(String wfFlag) {
		this.wfFlag = wfFlag;
	}

	public List<IndentProcessItemDto> getItem() {
		return item;
	}

	public void setItem(List<IndentProcessItemDto> item) {
		this.item = item;
	}

	public Long getDeptId() {
		return deptId;
	}

	public String getReportingMgrName() {
		return reportingMgrName;
	}

	public void setReportingMgrName(String reportingMgrName) {
		this.reportingMgrName = reportingMgrName;
	}

	public String getDesgName() {
		return desgName;
	}

	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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

	public IndentProcessItemDto getIndentProcessItemDto() {
		return indentProcessItemDto;
	}

	public void setIndentProcessItemDto(IndentProcessItemDto indentProcessItemDto) {
		this.indentProcessItemDto = indentProcessItemDto;
	}

	public String getIndenterName() {
		return indenterName;
	}

	public void setIndenterName(String indenterName) {
		this.indenterName = indenterName;
	}


	public String getRemoveFileById() {
		return removeFileById;
	}

	public void setRemoveFileById(String removeFileById) {
		this.removeFileById = removeFileById;
	}

	@Override
	public String toString() {
		return "StoreIndentDTO [indentid=" + indentid + ", indentno=" + indentno + ", indentdate=" + indentdate
				+ ", indenter=" + indenter + ", reportingmgr=" + reportingmgr + ", beneficiary=" + beneficiary
				+ ", storeid=" + storeid + ", deliveryat=" + deliveryat + ", expecteddate=" + expecteddate
				+ ",  issuedby=" + issuedby + ", issuedDate=" + issuedDate + ", status=" + status + ", orgid=" + orgid
				+ ", user_id=" + user_id + ", langId=" + langId + ", lmoddate=" + lmoddate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", wfFlag="
				+ wfFlag + ", deptId=" + deptId + ",desgId=" + desgId + ",location=" + location + "]";
	}

}
