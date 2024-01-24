package com.abm.mainet.tradeLicense.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "TB_MTL_INSPECTION_REG")
public class TbMtlInspectionReg {

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")

	@Column(name = "IN_ID", nullable = false)
	private Long inId;

	@ManyToOne
	@JoinColumn(name = "TRD_ID", referencedColumnName = "TRD_ID")
	private TbMlTradeMast masterTradeId;

	@Column(name = "IN_NO", nullable = false)
	private Long inspNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IN_DATE")
	private Date inspDate;

	@Column(name = "IN_INPRESENCE", nullable = false, length = 200)
	private String inspectorName;

	@Column(name = "IN_DESCRIPTION", nullable = false, length = 200)
	private String description;

	@Column(name = "IN_REMARK", nullable = false, length = 200)
	private String remark;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;
	

	public Long getInId() {
		return inId;
	}

	public void setInId(Long inId) {
		this.inId = inId;
	}
	
	public TbMlTradeMast getMasterTradeId() {
		return masterTradeId;
	}


	public void setMasterTradeId(TbMlTradeMast masterTradeId) {
		this.masterTradeId = masterTradeId;
	}

	

	public Long getInspNo() {
		return inspNo;
	}

	public void setInspNo(Long inspNo) {
		this.inspNo = inspNo;
	}

	public Date getInspDate() {
		return inspDate;
	}

	public void setInspDate(Date inspDate) {
		this.inspDate = inspDate;
	}

	public String getInspectorName() {
		return inspectorName;
	}

	public void setInspectorName(String inspectorName) {
		this.inspectorName = inspectorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
		return new String[] { "ML", "TB_MTL_INSPECTION_REG", "IN_ID" };
	}

}
