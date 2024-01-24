/**
 * 
 */
package com.abm.mainet.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sarojkumar.yadav
 *
 */
public class DuplicateBillDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989820440751386817L;

	private Long dupBillId;
	private Long bmId;
	private Long bmYear;
	private Date billDate;
	private Date billFromDate;
	private Date billToDate;
	private String serviceCode;
	private String deptCode;
	private String referenceId;
	private String dupBillData;
	private Long orgId;
	private Long createdBy;
	private Date createdDate;
	private Long updatedBy;
	private Date updatedDate;
	private String lgIpMac;
	private String lgIpMacUpd;

	public Long getDupBillId() {
		return dupBillId;
	}

	public void setDupBillId(Long dupBillId) {
		this.dupBillId = dupBillId;
	}

	public Long getBmId() {
		return bmId;
	}

	public void setBmId(Long bmId) {
		this.bmId = bmId;
	}

	public Long getBmYear() {
		return bmYear;
	}

	public void setBmYear(Long bmYear) {
		this.bmYear = bmYear;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDupBillData() {
		return dupBillData;
	}

	public void setDupBillData(String dupBillData) {
		this.dupBillData = dupBillData;
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
		return lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getBillFromDate() {
		return billFromDate;
	}

	public void setBillFromDate(Date billFromDate) {
		this.billFromDate = billFromDate;
	}

	public Date getBillToDate() {
		return billToDate;
	}

	public void setBillToDate(Date billToDate) {
		this.billToDate = billToDate;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

}
