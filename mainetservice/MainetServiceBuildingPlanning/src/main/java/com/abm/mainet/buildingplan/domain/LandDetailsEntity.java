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
@Table(name = "tb_lic_perf_det")
public class LandDetailsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "dt_id")
	private Long dtId;

	@Column(name = "CFC_APPLICATION_ID")
	private String cfcApplicationId;

	@Column(name = "SL_LABEL_ID")
	private Long slLabelId;

	@Column(name = "GM_ID")
	private Long gmId;

	@Column(name = "DSGID")
	private Long dsgId;

	@Column(name = "level")
	private Long level;

	@Column(name = "Village")
	private String Village;

	@Column(name = "Rectangle_Number")
	private Long rectangleNumber;

	@Column(name = "Killa_No")
	private Long killaNO;

	@Column(name = "Consolidation_Type")
	private String consolidationType;

	@Column(name = "Area")
	private String area;

	@Column(name = "Kanal_Bigha")
	private String kanalBigha;

	@Column(name = "Marla_Biswa")
	private String marlaBiswa;

	@Column(name = "Sarsai_Biswansi")
	private String sarsaiBiswansi;

	@Column(name = "Patwari_Field_NCZ")
	private String patwariFieldNcz;

	@Column(name = "Ownership_Verified")
	private String ownershipVerified;

	@Column(name = "Changed_Area")
	private String changedArea;

	@Column(name = "Include_Exclude_Area")
	private String includeExcludeArea;

	@Column(name = "Total_Area")
	private String totalArea;

	@Column(name = "Remarks")
	private String remarks;

	@Column(name = "Total_Area_in_acres")
	private Double totalAreaInAcres;

	@Column(name = "Total_Area_By_role")
	private Double totalAreaByRole;

	@Column(name = "Total_Area_By_Role1")
	private Double totalAreaByRole1;

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

	public Long getDtId() {
		return dtId;
	}

	public void setDtId(Long dtId) {
		this.dtId = dtId;
	}

	public String getCfcApplicationId() {
		return cfcApplicationId;
	}

	public void setCfcApplicationId(String cfcApplicationId) {
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

	public Long getDsgId() {
		return dsgId;
	}

	public void setDsgId(Long dsgId) {
		this.dsgId = dsgId;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public String getVillage() {
		return Village;
	}

	public void setVillage(String village) {
		Village = village;
	}

	public Long getRectangleNumber() {
		return rectangleNumber;
	}

	public void setRectangleNumber(Long rectangleNumber) {
		this.rectangleNumber = rectangleNumber;
	}

	public Long getKillaNO() {
		return killaNO;
	}

	public void setKillaNO(Long killaNO) {
		this.killaNO = killaNO;
	}

	public String getConsolidationType() {
		return consolidationType;
	}

	public void setConsolidationType(String consolidationType) {
		this.consolidationType = consolidationType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getKanalBigha() {
		return kanalBigha;
	}

	public void setKanalBigha(String kanalBigha) {
		this.kanalBigha = kanalBigha;
	}

	public String getMarlaBiswa() {
		return marlaBiswa;
	}

	public void setMarlaBiswa(String marlaBiswa) {
		this.marlaBiswa = marlaBiswa;
	}

	public String getSarsaiBiswansi() {
		return sarsaiBiswansi;
	}

	public void setSarsaiBiswansi(String sarsaiBiswansi) {
		this.sarsaiBiswansi = sarsaiBiswansi;
	}

	public String getPatwariFieldNcz() {
		return patwariFieldNcz;
	}

	public void setPatwariFieldNcz(String patwariFieldNcz) {
		this.patwariFieldNcz = patwariFieldNcz;
	}

	public String getOwnershipVerified() {
		return ownershipVerified;
	}

	public void setOwnershipVerified(String ownershipVerified) {
		this.ownershipVerified = ownershipVerified;
	}

	public String getChangedArea() {
		return changedArea;
	}

	public void setChangedArea(String changedArea) {
		this.changedArea = changedArea;
	}

	public String getIncludeExcludeArea() {
		return includeExcludeArea;
	}

	public void setIncludeExcludeArea(String includeExcludeArea) {
		this.includeExcludeArea = includeExcludeArea;
	}

	public String getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(String totalArea) {
		this.totalArea = totalArea;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Double getTotalAreaInAcres() {
		return totalAreaInAcres;
	}

	public void setTotalAreaInAcres(Double totalAreaInAcres) {
		this.totalAreaInAcres = totalAreaInAcres;
	}

	public Double getTotalAreaByRole() {
		return totalAreaByRole;
	}

	public void setTotalAreaByRole(Double totalAreaByRole) {
		this.totalAreaByRole = totalAreaByRole;
	}

	public Double getTotalAreaByRole1() {
		return totalAreaByRole1;
	}

	public void setTotalAreaByRole1(Double totalAreaByRole1) {
		this.totalAreaByRole1 = totalAreaByRole1;
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

}
