package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MBCHEKLIST_MAST")
public class MbCheckListMast implements Serializable {

	private static final long serialVersionUID = -3620736561990268242L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBC_ID", nullable = false)
	private Long mbcId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MBC_INSPECTION_DT", nullable = true)
	private Date mbcInspectionDate;

	@Column(name = "MBC_DRAWINGNO", nullable = true)
	private String mbcDrawingNo;

	@Column(name = "LOC_ID", nullable = true)
	private Long locId;

	@Column(name = "MBC_CHKID", nullable = true)
	private String mbcCheckId;

	@OneToOne
	@JoinColumn(name = "MB_ID", referencedColumnName = "MB_ID", nullable = false)
	private MeasurementBookMaster mbMaster;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mbChkListMaster", cascade = CascadeType.ALL)
	private List<MbCheckListDetail> mbCheckListDetail = new ArrayList<>();

	@Column(name = "ORGID", nullable = false)
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

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
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

	public MeasurementBookMaster getMbMaster() {
		return mbMaster;
	}

	public void setMbMaster(MeasurementBookMaster mbMaster) {
		this.mbMaster = mbMaster;
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

	public List<MbCheckListDetail> getMbCheckListDetail() {
		return mbCheckListDetail;
	}

	public void setMbCheckListDetail(List<MbCheckListDetail> mbCheckListDetail) {
		this.mbCheckListDetail = mbCheckListDetail;
	}

	public String[] getPkValues() {
		return new String[] { "WMS", "TB_WMS_MBCHEKLIST_MAST", "MBC_ID" };
	}

}
