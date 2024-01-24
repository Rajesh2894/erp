package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MBCHEKLIST_MAST_HIST")
public class MbCheckListMastHist implements Serializable {

	private static final long serialVersionUID = -8788036696666952536L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBC_ID_H", nullable = false)
	private Long mbchId;

	@Column(name = "MBC_ID")
	private Long mbcId;

	@Column(name = "MB_ID")
	private Long mbId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MBC_INSPECTION_DT", nullable = true)
	private Date mbcInspectionDate;

	@Column(name = "MBC_DRAWINGNO", nullable = true)
	private String mbcDrawingNo;

	@Column(name = "LOC_ID", nullable = true)
	private Long locId;

	@Column(name = "MBC_CHKID", nullable = true)
	private String mbcCheckId;

	@Column(name = "H_STATUS")
	private String hStatus;

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
	private String igIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String igIpMacUpd;

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MBCHEKLIST_MAST_HIST", "MBC_ID_H" };
	}

	public Long getMbchId() {
		return mbchId;
	}

	public void setMbchId(Long mbchId) {
		this.mbchId = mbchId;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
	}

	public Long getMbId() {
		return mbId;
	}

	public void setMbId(Long mbId) {
		this.mbId = mbId;
	}

	public Date getMbcInspectionDate() {
		return mbcInspectionDate;
	}

	public void setMbcInspectionDate(Date mbcInspectionDate) {
		this.mbcInspectionDate = mbcInspectionDate;
	}

	public String getMbcDrawingNo() {
		return mbcDrawingNo;
	}

	public void setMbcDrawingNo(String mbcDrawingNo) {
		this.mbcDrawingNo = mbcDrawingNo;
	}

	public Long getLocId() {
		return locId;
	}

	public void setLocId(Long locId) {
		this.locId = locId;
	}

	public String getMbcCheckId() {
		return mbcCheckId;
	}

	public void setMbcCheckId(String mbcCheckId) {
		this.mbcCheckId = mbcCheckId;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
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

	public String getIgIpMac() {
		return igIpMac;
	}

	public void setIgIpMac(String igIpMac) {
		this.igIpMac = igIpMac;
	}

	public String getIgIpMacUpd() {
		return igIpMacUpd;
	}

	public void setIgIpMacUpd(String igIpMacUpd) {
		this.igIpMacUpd = igIpMacUpd;
	}
}
