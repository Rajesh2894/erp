/**
 * 
 */
package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 * @since 29 april 2021
 */

@Entity
@Table(name = "tb_as_exception")
public class PropertyBillExceptionEntity implements Serializable {
	private static final long serialVersionUID = 4854616467210010318L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "exceptionId")
	private Long exceptionId;

	@Column(name = "prop_no")
	private String propNo;

	@Column(name = "excep_reason")
	private String exceptionReason;

	@Column(name = "staus")
	private String status;

	@Column(name = "bill_type")
	private String billType;

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
	private String lgIpMacUpd;

	public Long getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(Long exceptionId) {
		this.exceptionId = exceptionId;
	}

	public String getPropNo() {
		return propNo;
	}

	public void setPropNo(String propNo) {
		this.propNo = propNo;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
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

	public String[] getPkValues() {
		return new String[] { "AS", "tb_as_exception", "exceptionId" };

	}
}
