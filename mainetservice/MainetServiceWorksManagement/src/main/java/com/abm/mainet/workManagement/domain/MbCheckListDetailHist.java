package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Saiprasad.Vengurlekar
 *
 */
@Entity
@Table(name = "TB_WMS_MBCHEKLIST_DETAIL_HIST")
public class MbCheckListDetailHist implements Serializable {

	private static final long serialVersionUID = -3544155576836381792L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBCD_ID_H", nullable = false)
	private Long mbcdhId;

	@Column(name = "MBCD_ID")
	private Long mbcdId;

	@Column(name = "MBC_ID")
	private Long mbcId;

	@Column(name = "SL_LABEL_ID", nullable = true)
	private Long slLabelId;

	@Column(name = "LEVELS", nullable = true)
	private Long levels;

	@Column(name = "MBCD_VALUE", nullable = true)
	private String mbcdValue;

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
		return new String[] { "WMS", "TB_WMS_MBCHEKLIST_DETAIL_HIST", "MBCD_ID_H" };
	}

	public Long getMbcdhId() {
		return mbcdhId;
	}

	public void setMbcdhId(Long mbcdhId) {
		this.mbcdhId = mbcdhId;
	}

	public Long getMbcdId() {
		return mbcdId;
	}

	public void setMbcdId(Long mbcdId) {
		this.mbcdId = mbcdId;
	}

	public Long getMbcId() {
		return mbcId;
	}

	public void setMbcId(Long mbcId) {
		this.mbcId = mbcId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getLevels() {
		return levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public String getMbcdValue() {
		return mbcdValue;
	}

	public void setMbcdValue(String mbcdValue) {
		this.mbcdValue = mbcdValue;
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
