package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;

public class LicenseApplicationLandAcquisitionDetailDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long licAcquDetId;
    private LicenseApplicationMasterDTO licenseApplicationMaster;
    private String district;
    private String devPlan;
    private String zone;
    private String sector;
    private String thesil;
    private String revEstate;
    private Long hadbastNo;
    private Long rectangleNo;
    private String khasraNo;
    private String min;
    private String landOwnerName;
    private String landType;
    private String chInfo;
    private Long mustilCh;
    private String khasaraCh;
    private String landOwnerMUJAM;
    private String devColab;
    private String devCompName;
    private Date collabAgrDate;
    private String collabAgrRev;
    private String authSignLO;
    private String authSignDev;
    private String regAuth;
    private String collabDec;
    private String brLO;
    private String brDev;
    private String collabAgrDoc;
    private String consolType;
    private BigDecimal kanal;
    private BigDecimal marla;
    private BigDecimal sarsai;
    private BigDecimal bigha;
    private BigDecimal biswa;
    private BigDecimal biswansi;
    private String acqStat;
    private BigDecimal consolTotArea;
    private BigDecimal nonConsolTotArea;
    private Date createDate;
    private Date modifiedDate;
    private Long createdBy;
    private Long modifiedBy;
    private String actionPerformed;
    private String isDeleted;
    private String bArea;
    private String cBigha;
    private String cBiswa;
    private String cBiswansi;
    private String ownershipVerified;
    private String includeExcludeArea;
    private BigDecimal totalArea;
    private String remarks;
    private String type;
    private Long slLabelId;
    private Long level;
    private Long gmId;
    private String patwariFieldNcz;
    private BigDecimal stotalArea;
    private BigDecimal pTotalArea;
    private String decision;
    private String resolutionComments;
    
	public Long getLicAcquDetId() {
		return licAcquDetId;
	}
	public void setLicAcquDetId(Long licAcquDetId) {
		this.licAcquDetId = licAcquDetId;
	}
	public LicenseApplicationMasterDTO getLicenseApplicationMaster() {
		return licenseApplicationMaster;
	}
	public void setLicenseApplicationMaster(LicenseApplicationMasterDTO licenseApplicationMaster) {
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
	public BigDecimal getKanal() {
		return kanal;
	}
	public void setKanal(BigDecimal kanal) {
		this.kanal = kanal;
	}
	public BigDecimal getMarla() {
		return marla;
	}
	public void setMarla(BigDecimal marla) {
		this.marla = marla;
	}
	public BigDecimal getSarsai() {
		return sarsai;
	}
	public void setSarsai(BigDecimal sarsai) {
		this.sarsai = sarsai;
	}
	public BigDecimal getBigha() {
		return bigha;
	}
	public void setBigha(BigDecimal bigha) {
		this.bigha = bigha;
	}
	public BigDecimal getBiswa() {
		return biswa;
	}
	public void setBiswa(BigDecimal biswa) {
		this.biswa = biswa;
	}
	public BigDecimal getBiswansi() {
		return biswansi;
	}
	public void setBiswansi(BigDecimal biswansi) {
		this.biswansi = biswansi;
	}
	public BigDecimal getNonConsolTotArea() {
		return nonConsolTotArea;
	}
	public void setNonConsolTotArea(BigDecimal nonConsolTotArea) {
		this.nonConsolTotArea = nonConsolTotArea;
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
	public String getbArea() {
		return bArea;
	}
	public void setbArea(String bArea) {
		this.bArea = bArea;
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
	public BigDecimal getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(BigDecimal totalArea) {
		this.totalArea = totalArea;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSlLabelId() {
		return slLabelId;
	}
	public void setSlLabelId(Long slLabelId) {
		this.slLabelId = slLabelId;
	}
	public Long getLevel() {
		return level;
	}
	public void setLevel(Long level) {
		this.level = level;
	}
	public Long getGmId() {
		return gmId;
	}
	public void setGmId(Long gmId) {
		this.gmId = gmId;
	}
	public String getPatwariFieldNcz() {
		return patwariFieldNcz;
	}
	public void setPatwariFieldNcz(String patwariFieldNcz) {
		this.patwariFieldNcz = patwariFieldNcz;
	}
	public BigDecimal getStotalArea() {
		return stotalArea;
	}
	public void setStotalArea(BigDecimal stotalArea) {
		this.stotalArea = stotalArea;
	}
	public BigDecimal getpTotalArea() {
		return pTotalArea;
	}
	public void setpTotalArea(BigDecimal pTotalArea) {
		this.pTotalArea = pTotalArea;
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

}
