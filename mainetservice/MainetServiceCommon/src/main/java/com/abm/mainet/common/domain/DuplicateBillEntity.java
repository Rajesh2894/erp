/**
 * 
 */
package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_DUP_BILL")
public class DuplicateBillEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -437789864762915145L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "DUP_BILLID", nullable = false)
	private Long dupBillId;

	@Column(name = "BM_ID", nullable = false)
	private Long bmId;

	@Column(name = "BM_YEAR", nullable = false)
	private Long bmYear;

	@Column(name = "BILL_DATE", nullable = true)
	private Date billDate;

	@Column(name = "BILL_FROMDT", nullable = true)
	private Date billFromDate;

	@Column(name = "BILL_TODATE", nullable = true)
	private Date billToDate;

	@Column(name = "SM_SHORTDESC", nullable = true)
	private String serviceCode;

	@Column(name = "DP_DEPTCODE", nullable = true)
	private String deptCode;

	@Column(name = "REF_ID", nullable = true)
	private String referenceId;

	@Column(name = "DUP_BILL", nullable = false)
	private String dupBillData;

	@Column(name = "ORGID", nullable = true)
	private Long orgId;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "UPDATED_BY", nullable = true)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", nullable = true)
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

	public static String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, "TB_DUP_BILL", "DUP_BILLID" };
	}

	
}
