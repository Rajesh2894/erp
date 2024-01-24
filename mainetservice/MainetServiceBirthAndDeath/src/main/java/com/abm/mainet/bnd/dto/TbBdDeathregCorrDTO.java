package com.abm.mainet.bnd.dto;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.abm.mainet.bnd.domain.DeceasedMasterCorrection;
import com.abm.mainet.bnd.domain.MedicalMasterCorrection;

public class TbBdDeathregCorrDTO implements Serializable{
	
	private static final long serialVersionUID = 8536617778323821216L;
	private Long drCorrId;
	private Long drId;
	private Long orgId;
	private String drCertNo;
	private String drRegno;
	private Date drRegdate;
	//General Details
	private Date drDod;
	private String drSex;
	private String drDeceasedname;
	private String drMarDeceasedname;
	private String drMotherName;
	private String drMarMotherName;
	private String drDeceasedaddr;
	private String drMarDeceasedaddr;
	private String drDcaddrAtdeath;
	private String drDcaddrAtdeathMar;
	private String cpdDeathplaceType;
	private String drDeathplace;
	private String drMarDeathplace;
	private String drDeathaddr;
	private String drMarDeathaddr;
	
	private Long apmApplicationId;
	private Long codUserWardid;
	private double corrAuthBy;
	private Date corrAuthDate;
	private String corrAuthFlag;
	private String corrAuthRemark;
	private String drCorrectionFlg;
	private Date drCorrnDate;
	private String drMarRelativeName;
	private String drRelativeName;
	private String drRemarks;
	private String drStatus;
	private Long hiId;
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private String reqFlag;
	private Long smServiceId;
	private Long updatedBy;
	private Date updatedDate;
	private Long userId;
	private Long wardid;
	private Date regAplDate;
	private String deathRegstatus;
	private String deathRegremark;
	
	private String DeathWfStatus;
	private Long alreayIssuedCopy;
	private String mcOthercond;
	private String mcMdSuprName;
	private Long cpdAgeperiodId;
	private Long cpdReligionId;
	private Long cpdMaritalStatId;
	private Long cpdOccupationId;
	private String drInformantName;
	private String drInformantAddr;
	private Long drDeceasedage;
	private Long cpdDeathcauseId;
	
	private String corrCategory;
	
	private MedicalMasterCorrection  medicalMasterCorrection = new MedicalMasterCorrection();
	
	private DeceasedMasterCorrection  deceasedMasterCorrection= new DeceasedMasterCorrection();
	
	//getter setter
	
	public Long getDrCorrId() {
		return drCorrId;
	}
	public void setDrCorrId(Long drCorrId) {
		this.drCorrId = drCorrId;
	}
	public Long getDrId() {
		return drId;
	}
	public void setDrId(Long drId) {
		this.drId = drId;
	}
	public Long getApmApplicationId() {
		return apmApplicationId;
	}
	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}
	public Long getCodUserWardid() {
		return codUserWardid;
	}
	public void setCodUserWardid(Long codUserWardid) {
		this.codUserWardid = codUserWardid;
	}
	public double getCorrAuthBy() {
		return corrAuthBy;
	}
	public void setCorrAuthBy(double corrAuthBy) {
		this.corrAuthBy = corrAuthBy;
	}
	public Date getCorrAuthDate() {
		return corrAuthDate;
	}
	public void setCorrAuthDate(Date corrAuthDate) {
		this.corrAuthDate = corrAuthDate;
	}
	public String getCorrAuthFlag() {
		return corrAuthFlag;
	}
	public void setCorrAuthFlag(String corrAuthFlag) {
		this.corrAuthFlag = corrAuthFlag;
	}
	public String getCorrAuthRemark() {
		return corrAuthRemark;
	}
	public void setCorrAuthRemark(String corrAuthRemark) {
		this.corrAuthRemark = corrAuthRemark;
	}
	public String getCpdDeathplaceType() {
		return cpdDeathplaceType;
	}
	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}
	public String getDrCertNo() {
		return drCertNo;
	}
	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
	}
	public String getDrCorrectionFlg() {
		return drCorrectionFlg;
	}
	public void setDrCorrectionFlg(String drCorrectionFlg) {
		this.drCorrectionFlg = drCorrectionFlg;
	}
	public Date getDrCorrnDate() {
		return drCorrnDate;
	}
	public void setDrCorrnDate(Date drCorrnDate) {
		this.drCorrnDate = drCorrnDate;
	}
	public String getDrDcaddrAtdeath() {
		return drDcaddrAtdeath;
	}
	public void setDrDcaddrAtdeath(String drDcaddrAtdeath) {
		this.drDcaddrAtdeath = drDcaddrAtdeath;
	}
	public String getDrDcaddrAtdeathMar() {
		return drDcaddrAtdeathMar;
	}
	public void setDrDcaddrAtdeathMar(String drDcaddrAtdeathMar) {
		this.drDcaddrAtdeathMar = drDcaddrAtdeathMar;
	}
	public String getDrDeathaddr() {
		return drDeathaddr;
	}
	public void setDrDeathaddr(String drDeathaddr) {
		this.drDeathaddr = drDeathaddr;
	}
	public String getDrDeathplace() {
		return drDeathplace;
	}
	public void setDrDeathplace(String drDeathplace) {
		this.drDeathplace = drDeathplace;
	}
	public String getDrDeceasedaddr() {
		return drDeceasedaddr;
	}
	public void setDrDeceasedaddr(String drDeceasedaddr) {
		this.drDeceasedaddr = drDeceasedaddr;
	}
	public String getDrDeceasedname() {
		return drDeceasedname;
	}
	public void setDrDeceasedname(String drDeceasedname) {
		this.drDeceasedname = drDeceasedname;
	}
	public Date getDrDod() {
		return drDod;
	}
	public void setDrDod(Date drDod) {
		this.drDod = drDod;
	}
	public String getDrMarDeathaddr() {
		return drMarDeathaddr;
	}
	public void setDrMarDeathaddr(String drMarDeathaddr) {
		this.drMarDeathaddr = drMarDeathaddr;
	}
	public String getDrMarDeathplace() {
		return drMarDeathplace;
	}
	public void setDrMarDeathplace(String drMarDeathplace) {
		this.drMarDeathplace = drMarDeathplace;
	}
	public String getDrMarDeceasedaddr() {
		return drMarDeceasedaddr;
	}
	public void setDrMarDeceasedaddr(String drMarDeceasedaddr) {
		this.drMarDeceasedaddr = drMarDeceasedaddr;
	}
	public String getDrMarDeceasedname() {
		return drMarDeceasedname;
	}
	public void setDrMarDeceasedname(String drMarDeceasedname) {
		this.drMarDeceasedname = drMarDeceasedname;
	}
	public String getDrMarMotherName() {
		return drMarMotherName;
	}
	public void setDrMarMotherName(String drMarMotherName) {
		this.drMarMotherName = drMarMotherName;
	}
	public String getDrMarRelativeName() {
		return drMarRelativeName;
	}
	public void setDrMarRelativeName(String drMarRelativeName) {
		this.drMarRelativeName = drMarRelativeName;
	}
	public String getDrMotherName() {
		return drMotherName;
	}
	public void setDrMotherName(String drMotherName) {
		this.drMotherName = drMotherName;
	}
	public Date getDrRegdate() {
		return drRegdate;
	}
	public void setDrRegdate(Date drRegdate) {
		this.drRegdate = drRegdate;
	}
	public String getDrRegno() {
		return drRegno;
	}
	public void setDrRegno(String drRegno) {
		this.drRegno = drRegno;
	}
	public String getDrRelativeName() {
		return drRelativeName;
	}
	public void setDrRelativeName(String drRelativeName) {
		this.drRelativeName = drRelativeName;
	}
	public String getDrRemarks() {
		return drRemarks;
	}
	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}
	public String getDrSex() {
		return drSex;
	}
	public void setDrSex(String drSex) {
		this.drSex = drSex;
	}
	public String getDrStatus() {
		return drStatus;
	}
	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
	}
	public Long getHiId() {
		return hiId;
	}
	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}
	public int getLangId() {
		return langId;
	}
	public void setLangId(int langId) {
		this.langId = langId;
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
	public Date getLmoddate() {
		return lmoddate;
	}
	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}
	public String getReqFlag() {
		return reqFlag;
	}
	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}
	public Long getSmServiceId() {
		return smServiceId;
	}
	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWardid() {
		return wardid;
	}
	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}
	public MedicalMasterCorrection getMedicalMasterCorrection() {
		return medicalMasterCorrection;
	}
	public void setMedicalMasterCorrection(MedicalMasterCorrection medicalMasterCorrection) {
		this.medicalMasterCorrection = medicalMasterCorrection;
	}
	public DeceasedMasterCorrection getDeceasedMasterCorrection() {
		return deceasedMasterCorrection;
	}
	public void setDeceasedMasterCorrection(DeceasedMasterCorrection deceasedMasterCorrection) {
		this.deceasedMasterCorrection = deceasedMasterCorrection;
	}
	
	public String getDeathRegstatus() {
		return deathRegstatus;
	}
	public void setDeathRegstatus(String deathRegstatus) {
		this.deathRegstatus = deathRegstatus;
	}
	public String getDeathRegremark() {
		return deathRegremark;
	}
	public void setDeathRegremark(String deathRegremark) {
		this.deathRegremark = deathRegremark;
	}
	public String getDeathWfStatus() {
		return DeathWfStatus;
	}
	public void setDeathWfStatus(String deathWfStatus) {
		DeathWfStatus = deathWfStatus;
	}
	public Long getAlreayIssuedCopy() {
		return alreayIssuedCopy;
	}
	public void setAlreayIssuedCopy(Long alreayIssuedCopy) {
		this.alreayIssuedCopy = alreayIssuedCopy;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Date getRegAplDate() {
		return regAplDate;
	}
	public void setRegAplDate(Date regAplDate) {
		this.regAplDate = regAplDate;
	}
	public String getMcOthercond() {
		return mcOthercond;
	}
	public void setMcOthercond(String mcOthercond) {
		this.mcOthercond = mcOthercond;
	}
	public String getMcMdSuprName() {
		return mcMdSuprName;
	}
	public void setMcMdSuprName(String mcMdSuprName) {
		this.mcMdSuprName = mcMdSuprName;
	}
	public Long getCpdAgeperiodId() {
		return cpdAgeperiodId;
	}
	public void setCpdAgeperiodId(Long cpdAgeperiodId) {
		this.cpdAgeperiodId = cpdAgeperiodId;
	}
	public Long getCpdReligionId() {
		return cpdReligionId;
	}
	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}
	public Long getCpdMaritalStatId() {
		return cpdMaritalStatId;
	}
	public void setCpdMaritalStatId(Long cpdMaritalStatId) {
		this.cpdMaritalStatId = cpdMaritalStatId;
	}
	public Long getCpdOccupationId() {
		return cpdOccupationId;
	}
	public void setCpdOccupationId(Long cpdOccupationId) {
		this.cpdOccupationId = cpdOccupationId;
	}
	public String getDrInformantName() {
		return drInformantName;
	}
	public void setDrInformantName(String drInformantName) {
		this.drInformantName = drInformantName;
	}
	public String getDrInformantAddr() {
		return drInformantAddr;
	}
	public void setDrInformantAddr(String drInformantAddr) {
		this.drInformantAddr = drInformantAddr;
	}
	public Long getDrDeceasedage() {
		return drDeceasedage;
	}
	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
	}
	public Long getCpdDeathcauseId() {
		return cpdDeathcauseId;
	}
	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
	}
	public String getCorrCategory() {
		return corrCategory;
	}
	public void setCorrCategory(String corrCategory) {
		this.corrCategory = corrCategory;
	}
	
}
