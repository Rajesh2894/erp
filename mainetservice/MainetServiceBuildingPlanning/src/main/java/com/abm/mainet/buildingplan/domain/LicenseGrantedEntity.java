package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_lic_perf_draw")
public class LicenseGrantedEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "d_id")
	private Long dId;

	@Column(name = "CFC_APPLICATION_ID")
	private Long cfcApplicationId;

	@Column(name = "SL_LABEL_ID")
	private Long slLabelId;

	@Column(name = "GM_ID")
	private Long gmId;

	@Column(name = "level")
	private Long level;

	@Column(name = "DSGID")
	private Long dsgId;

	@Column(name = "sequence")
	private Long sequence;

	@Column(name = "Category_of_Applied_Land")
	private String categoryOfAppliedLand;

	@Column(name = "Area")
	private Double area;

	@Column(name = "NPA")
	private Double npa;

	@Column(name = "Balance_NPA")
	private Double balanceNpa;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "status")
	private String status;

	@Column(name = "LG_IP_MAC")
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD")
	private String lgIpMacUpd;
	
	@Column(name = "TASK_ID")
	private Long taskId;

	public Long getdId() {
		return dId;
	}

	public void setdId(Long dId) {
		this.dId = dId;
	}

	public Long getCfcApplicationId() {
		return cfcApplicationId;
	}

	public void setCfcApplicationId(Long cfcApplicationId) {
		this.cfcApplicationId = cfcApplicationId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getGmId() {
		return gmId;
	}

	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Long getDsgId() {
		return dsgId;
	}

	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}

	public String getCategoryOfAppliedLand() {
		return categoryOfAppliedLand;
	}

	public void setCategoryOfAppliedLand(String categoryOfAppliedLand) {
		this.categoryOfAppliedLand = categoryOfAppliedLand;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getNpa() {
		return npa;
	}

	public void setNpa(Double npa) {
		this.npa = npa;
	}

	public Double getBalanceNpa() {
		return balanceNpa;
	}

	public void setBalanceNpa(Double balanceNpa) {
		this.balanceNpa = balanceNpa;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String[] getPkValues() {
		return new String[] { "ML", "tb_lic_perf_draw", "d_id" };
	}

}
