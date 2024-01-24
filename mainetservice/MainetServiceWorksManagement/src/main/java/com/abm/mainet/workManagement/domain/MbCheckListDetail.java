package com.abm.mainet.workManagement.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Entity
@Table(name = "TB_WMS_MBCHEKLIST_DETAIL")
public class MbCheckListDetail implements Serializable {

	private static final long serialVersionUID = -3620736561990268242L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "MBCD_ID", nullable = false)
	private Long mbcdId;

	@Column(name = "MBCD_VALUE", nullable = false)
	private String mbcdValue;

	@Column(name = "SL_LABEL_ID", nullable = false)
	private Long slLabelId;

	@Column(name = "LEVELS", nullable = false)
	private Long levels;

	@ManyToOne
	@JoinColumn(name = "MBC_ID", referencedColumnName = "MBC_ID", nullable = false)
	private MbCheckListMast mbChkListMaster;

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

	public Long getMbcdId() {
		return mbcdId;
	}

	public void setMbcdId(Long mbcdId) {
		this.mbcdId = mbcdId;
	}

	public String getMbcdValue() {
		return mbcdValue;
	}

	public void setMbcdValue(String mbcdValue) {
		this.mbcdValue = mbcdValue;
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

	public MbCheckListMast getMbChkListMaster() {
		return mbChkListMaster;
	}

	public void setMbChkListMaster(MbCheckListMast mbChkListMaster) {
		this.mbChkListMaster = mbChkListMaster;
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
		return new String[] { "WMS", "TB_WMS_MBCHEKLIST_DETAIL", "MBCD_ID" };
	}

}
