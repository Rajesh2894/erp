package com.abm.mainet.buildingplan.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_bpms_lic_Aqu_Det_hist")
public class LicenseApplicationLandAcquisitionDetHistEntity implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_LIC_AQU_DET_ID", nullable = false)
	private Long hLicAcquDetId;
	
	@Column(name = "LIC_AQU_DET_ID")
	private Long licAcquDetId;

	@ManyToOne
	@JoinColumn(name = "TCP_LIC_MSTR_ID")
	private LicenseApplicationMaster licenseApplicationMaster;
	

	@Column(name = "District")
	private String district;

	@Column(name = "Dev_plan")
	private String devPlan;

	@Column(name = "Zone")
	private String zone;

	@Column(name = "Sector")
	private String sector;

	@Column(name = "Thesil")
	private String thesil;

	@Column(name = "Rev_estate")
	private String revEstate;

	@Column(name = "Hadbast_no")
	private Long hadbastNo;

	@Column(name = "Rectangle_no")
	private Long rectangleNo;

	@Column(name = "Khasra_no")
	private String khasraNo;

	@Column(name = "Min")
	private String min;

	@Column(name = "Land_Owner_Name")
	private String landOwnerName;

	@Column(name = "Land_type")
	private String landType;

	@Column(name = "Ch_info")
	private String chInfo;

	@Column(name = "Mustil_ch")
	private Long mustilCh;

	@Column(name = "Khasara_ch")
	private String khasaraCh;

	@Column(name = "Land_owner_MU_JAM")
	private String landOwnerMUJAM;

	@Column(name = "dev_colab")
	private String devColab;

	@Column(name = "dev_comp_name")
	private String devCompName;

	@Column(name = "collab_agr_date")
	private Date collabAgrDate;

	@Column(name = "collab_agr_rev")
	private String collabAgrRev;

	@Column(name = "auth_sign_LO")
	private String authSignLO;

	@Column(name = "auth_sign_dev")
	private String authSignDev;

	@Column(name = "Reg_auth")
	private String regAuth;

	@Column(name = "Collab_dec")
	private String collabDec;

	@Column(name = "BR_LO")
	private String brLO;

	@Column(name = "BR_dev")
	private String brDev;

	@Column(name = "collab_agr_doc")
	private String collabAgrDoc;

	@Column(name = "consol_type")
	private String consolType;

	@Column(name = "kanal")
	private Long kanal;

	@Column(name = "Marla")
	private Long marla;

	@Column(name = "Sarsai")
	private Long sarsai;

	@Column(name = "Bigha")
	private String bigha;

	@Column(name = "Biswa")
	private String biswa;

	@Column(name = "Biswansi")
	private String biswansi;

	@Column(name = "acq_stat")
	private String acqStat;

	@Column(name = "consol_tot_area")
	private BigDecimal consolTotArea;

	@Column(name = "non_consol_tot_area")
	private String nonConsolTotArea;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "modified_by")
	private Long modifiedBy;

	@Column(name = "Action_performed")
	private String actionPerformed;

	@Column(name = "Is_deleted")
	private String isDeleted;
	
	@Column(name = "Kanal_Bigha")
	private String cBigha;
	
	@Column(name = "Marla_Biswa")
	private String cBiswa;
	
	@Column(name = "Sarsai_Biswansi")
	private String cBiswansi;
	
	@Column(name = "Ownership_Verified")
	private String ownershipVerified;
	
	@Column(name = "Include_Exclude_Area")
	private String includeExcludeArea;
	
	@Column(name = "Remarks")
	private String remarks;
	
	@Column(name = "Total_Area")
	private BigDecimal totalArea;
	
	@Column(name = "SL_LABEL_ID")
	private Long slLabelId;
	
	@Column(name = "GM_ID")
	private Long gmId;
	
	@Column(name = "level")
	private Long level;
	
	@Column(name = "Patwari_Field_NCZ")
	private String patwariFieldNcz;
	
	@Column(name = "H_STATUS")
	private String hStatus;
	
	@Column(name = "TASK_ID")
	private Long taskId;
	
	@Column(name = "SCRN_FLAG")
	private String scrnFlag;
	
	@Column(name = "FIELD_DECISION")
	private String decision;
	
	@Column(name = "FIELD_REMARK")
	private String resolutionComments;

	public Long gethLicAcquDetId() {
		return hLicAcquDetId;
	}

	public void sethLicAcquDetId(Long hLicAcquDetId) {
		this.hLicAcquDetId = hLicAcquDetId;
	}

	public Long getLicAcquDetId() {
		return licAcquDetId;
	}

	public void setLicAcquDetId(Long licAcquDetId) {
		this.licAcquDetId = licAcquDetId;
	}

	public LicenseApplicationMaster getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}

	public void setLicenseApplicationMaster(LicenseApplicationMaster licenseApplicationMaster) {
		this.licenseApplicationMaster = licenseApplicationMaster;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDevPlan() {
		return devPlan;
	}

	public void setDevPlan(String devPlan) {
		this.devPlan = devPlan;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getThesil() {
		return thesil;
	}

	public void setThesil(String thesil) {
		this.thesil = thesil;
	}

	public String getRevEstate() {
		return revEstate;
	}

	public void setRevEstate(String revEstate) {
		this.revEstate = revEstate;
	}

	public Long getHadbastNo() {
		return hadbastNo;
	}

	public void setHadbastNo(Long hadbastNo) {
		this.hadbastNo = hadbastNo;
	}

	public Long getRectangleNo() {
		return rectangleNo;
	}

	public void setRectangleNo(Long rectangleNo) {
		this.rectangleNo = rectangleNo;
	}

	public String getKhasraNo() {
		return khasraNo;
	}

	public void setKhasraNo(String khasraNo) {
		this.khasraNo = khasraNo;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getLandOwnerName() {
		return landOwnerName;
	}

	public void setLandOwnerName(String landOwnerName) {
		this.landOwnerName = landOwnerName;
	}

	public String getLandType() {
		return landType;
	}

	public void setLandType(String landType) {
		this.landType = landType;
	}

	public String getChInfo() {
		return chInfo;
	}

	public void setChInfo(String chInfo) {
		this.chInfo = chInfo;
	}

	public Long getMustilCh() {
		return mustilCh;
	}

	public void setMustilCh(Long mustilCh) {
		this.mustilCh = mustilCh;
	}

	public String getKhasaraCh() {
		return khasaraCh;
	}

	public void setKhasaraCh(String khasaraCh) {
		this.khasaraCh = khasaraCh;
	}

	public String getLandOwnerMUJAM() {
		return landOwnerMUJAM;
	}

	public void setLandOwnerMUJAM(String landOwnerMUJAM) {
		this.landOwnerMUJAM = landOwnerMUJAM;
	}

	public String getDevColab() {
		return devColab;
	}

	public void setDevColab(String devColab) {
		this.devColab = devColab;
	}

	public String getDevCompName() {
		return devCompName;
	}

	public void setDevCompName(String devCompName) {
		this.devCompName = devCompName;
	}

	public Date getCollabAgrDate() {
		return collabAgrDate;
	}

	public void setCollabAgrDate(Date collabAgrDate) {
		this.collabAgrDate = collabAgrDate;
	}

	public String getCollabAgrRev() {
		return collabAgrRev;
	}

	public void setCollabAgrRev(String collabAgrRev) {
		this.collabAgrRev = collabAgrRev;
	}

	public String getAuthSignLO() {
		return authSignLO;
	}

	public void setAuthSignLO(String authSignLO) {
		this.authSignLO = authSignLO;
	}

	public String getAuthSignDev() {
		return authSignDev;
	}

	public void setAuthSignDev(String authSignDev) {
		this.authSignDev = authSignDev;
	}

	public String getRegAuth() {
		return regAuth;
	}

	public void setRegAuth(String regAuth) {
		this.regAuth = regAuth;
	}

	public String getCollabDec() {
		return collabDec;
	}

	public void setCollabDec(String collabDec) {
		this.collabDec = collabDec;
	}

	public String getBrLO() {
		return brLO;
	}

	public void setBrLO(String brLO) {
		this.brLO = brLO;
	}

	public String getBrDev() {
		return brDev;
	}

	public void setBrDev(String brDev) {
		this.brDev = brDev;
	}

	public String getCollabAgrDoc() {
		return collabAgrDoc;
	}

	public void setCollabAgrDoc(String collabAgrDoc) {
		this.collabAgrDoc = collabAgrDoc;
	}

	public String getConsolType() {
		return consolType;
	}

	public void setConsolType(String consolType) {
		this.consolType = consolType;
	}

	public Long getKanal() {
		return kanal;
	}

	public void setKanal(Long kanal) {
		this.kanal = kanal;
	}

	public Long getMarla() {
		return marla;
	}

	public void setMarla(Long marla) {
		this.marla = marla;
	}

	public Long getSarsai() {
		return sarsai;
	}

	public void setSarsai(Long sarsai) {
		this.sarsai = sarsai;
	}

	public String getBigha() {
		return bigha;
	}

	public void setBigha(String bigha) {
		this.bigha = bigha;
	}

	public String getBiswa() {
		return biswa;
	}

	public void setBiswa(String biswa) {
		this.biswa = biswa;
	}

	public String getBiswansi() {
		return biswansi;
	}

	public void setBiswansi(String biswansi) {
		this.biswansi = biswansi;
	}

	public String getAcqStat() {
		return acqStat;
	}

	public void setAcqStat(String acqStat) {
		this.acqStat = acqStat;
	}

	public BigDecimal getConsolTotArea() {
		return consolTotArea;
	}

	public void setConsolTotArea(BigDecimal consolTotArea) {
		this.consolTotArea = consolTotArea;
	}

	public String getNonConsolTotArea() {
		return nonConsolTotArea;
	}

	public void setNonConsolTotArea(String nonConsolTotArea) {
		this.nonConsolTotArea = nonConsolTotArea;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(String actionPerformed) {
		this.actionPerformed = actionPerformed;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getcBigha() {
		return cBigha;
	}

	public void setcBigha(String cBigha) {
		this.cBigha = cBigha;
	}

	public String getcBiswa() {
		return cBiswa;
	}

	public void setcBiswa(String cBiswa) {
		this.cBiswa = cBiswa;
	}

	public String getcBiswansi() {
		return cBiswansi;
	}

	public void setcBiswansi(String cBiswansi) {
		this.cBiswansi = cBiswansi;
	}

	public String getOwnershipVerified() {
		return ownershipVerified;
	}

	public void setOwnershipVerified(String ownershipVerified) {
		this.ownershipVerified = ownershipVerified;
	}

	public String getIncludeExcludeArea() {
		return includeExcludeArea;
	}

	public void setIncludeExcludeArea(String includeExcludeArea) {
		this.includeExcludeArea = includeExcludeArea;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getTotalArea() {
		return totalArea;
	}

	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
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

	public String getPatwariFieldNcz() {
		return patwariFieldNcz;
	}

	public void setPatwariFieldNcz(String patwariFieldNcz) {
		this.patwariFieldNcz = patwariFieldNcz;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getScrnFlag() {
		return scrnFlag;
	}

	public void setScrnFlag(String scrnFlag) {
		this.scrnFlag = scrnFlag;
	}

	

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getResolutionComments() {
		return resolutionComments;
	}

	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}

	public String[] getPkValues() {
		return new String[] { "NL", "tb_bpms_lic_Aqu_Det_hist", "H_LIC_AQU_DET_ID" };
	}
	
	

}
