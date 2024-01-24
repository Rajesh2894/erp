package com.abm.mainet.cfc.scrutiny.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tb_scrutiny_values_hist")
public class TbScrutinyLabelValueHistEntity implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_SLLABLEID")
	private Long hSllableId;

	@Column(name = "SL_LABEL_ID", nullable = false)
	private Long slLabelId;

	@Column(name = "CFC_APPLICATION_ID", nullable = false)
	private Long saApplicationId;

	@Column(name = "SV_VALUE", length = 400, nullable = true)
	private String svValue;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGID", nullable = false, updatable = false)
	private Organisation orgId;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, updatable = false)
	private Employee userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	private int langId;

	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
	private Employee updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "levels", precision = 7, scale = 0, nullable = false)
	private Long levels;

	@Column(name = "H_STATUS", length = 400, nullable = true)
	private String hStatus;

	@Column(name = "REMARK", nullable = true)
	private String resolutionComments;
	
	@Column(name = "TASK_ID")
	private Long taskId;

	public Long gethSllableId() {
		return hSllableId;
	}

	public void sethSllableId(Long hSllableId) {
		this.hSllableId = hSllableId;
	}

	public Long getSlLabelId() {
		return slLabelId;
	}

	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}

	public Long getSaApplicationId() {
		return saApplicationId;
	}

	public void setSaApplicationId(Long saApplicationId) {
		this.saApplicationId = saApplicationId;
	}

	public String getSvValue() {
		return svValue;
	}

	public void setSvValue(String svValue) {
		this.svValue = svValue;
	}

	public Organisation getOrgId() {
		return orgId;
	}

	public void setOrgId(Organisation orgId) {
		this.orgId = orgId;
	}

	public Employee getUserId() {
		return userId;
	}

	public void setUserId(Employee userId) {
		this.userId = userId;
	}

	public int getLangId() {
		return langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public Date getLmodDate() {
		return lmodDate;
	}

	public void setLmodDate(Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	public Employee getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Employee updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getLevels() {
		return levels;
	}

	public void setLevels(Long levels) {
		this.levels = levels;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String[] getPkValues() {
		return new String[] { "COM", "tb_scrutiny_values_hist", "H_SLLABLEID" };
	}

}
