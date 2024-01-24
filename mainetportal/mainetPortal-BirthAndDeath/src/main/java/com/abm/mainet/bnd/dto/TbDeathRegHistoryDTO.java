package com.abm.mainet.bnd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class TbDeathRegHistoryDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long drhId;
	private Long drId;
	private Long orgId;
	private Long authBy;
	private Date authDate;
	private String authFlag;
	private String authRemark;
	private String bcrFlag;
	private String bplNo;
	private String ceAddr;
	private String ceAddrMar;
	private String ceFlag;
	private Long ceId;
	private String ceName;
	private String ceNameMar;
	private short certNoCopies;
	private Long codUserWardid;
	private Long cpdAgeperiodId;
	private Long cpdAttntypeId;
	private Long cpdDeathcauseId;
	private String cpdDeathplaceType;
	private Long cpdDistrictId;
	private Long cpdEducationId;
	private Long cpdMaritalStatId;
	private Long cpdNationalityId;
	private Long cpdOccupationId;
	private Long cpdRegUnit;
	private Long cpdReligionId;
	private Long cpdResId;
	private Long cpdStateId;
	private Long cpdTalukaId;
	private String dcUid;
	private String drAddress;
	private String drCertNo;
	private String drCorrectionFlg;
	private Date drCorrnDate;
	private String drDcaddrAtdeath;
	private String drDcaddrAtdeathMar;
	private String drDeathaddr;
	private String drDeathplace;
	private String drDeceasedaddr;
	private Long drDeceasedage;
	private String drDeceasedname;
	private Date drDod;
	private String drFirNumber;
	private String drFlag;
	private String drInformantAddr;
	private String drInformantName;
	private short drManualCertno;
	private String drMarDeathaddr;
	private String drMarDeathplace;
	private String drMarDeceasedaddr;
	private String drMarDeceasedname;
	private String drMarInformantAddr;
	private String drMarInformantName;
	private String drMarMotherName;
	private String drMarRelativeName;
	private String drMotherName;
	private String drPoliceStation;
	private Date drRegdate;
	private String drRegno;
	private String drRelPreg;
	private String drReldativeName;
	private String drRemarks;
	private String drSex;
	private String drStatus;
	private Date drSuppdate;
	private String drSuppno;
	private String drSupptime;
	private String drTime;
	private String drUdeathReg;
	private String fileName;
	private String hRId;
	private Long hiId;
	private String hrReg;
	private int langId;
	private String lgIpMac;
	private String lgIpMacUpd;
	private Date lmoddate;
	private String otherReligion;
	private String pdUidF;
	private String pdUidM;
	private String pgflag;
	private Date regAplDate;
	private String unauthRFlg;
	private Long updatedBy;
	private Date updatedDate;
	private Long wardid;
	public Long getDrhId() {
		return drhId;
	}
	public void setDrhId(Long drhId) {
		this.drhId = drhId;
	}
	public Long getDrId() {
		return drId;
	}
	public void setDrId(Long drId) {
		this.drId = drId;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getAuthBy() {
		return authBy;
	}
	public void setAuthBy(Long authBy) {
		this.authBy = authBy;
	}
	public Date getAuthDate() {
		return authDate;
	}
	public void setAuthDate(Date authDate) {
		this.authDate = authDate;
	}
	public String getAuthFlag() {
		return authFlag;
	}
	public void setAuthFlag(String authFlag) {
		this.authFlag = authFlag;
	}
	public String getAuthRemark() {
		return authRemark;
	}
	public void setAuthRemark(String authRemark) {
		this.authRemark = authRemark;
	}
	public String getBcrFlag() {
		return bcrFlag;
	}
	public void setBcrFlag(String bcrFlag) {
		this.bcrFlag = bcrFlag;
	}
	public String getBplNo() {
		return bplNo;
	}
	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}
	public String getCeAddr() {
		return ceAddr;
	}
	public void setCeAddr(String ceAddr) {
		this.ceAddr = ceAddr;
	}
	public String getCeAddrMar() {
		return ceAddrMar;
	}
	public void setCeAddrMar(String ceAddrMar) {
		this.ceAddrMar = ceAddrMar;
	}
	public String getCeFlag() {
		return ceFlag;
	}
	public void setCeFlag(String ceFlag) {
		this.ceFlag = ceFlag;
	}
	public Long getCeId() {
		return ceId;
	}
	public void setCeId(Long ceId) {
		this.ceId = ceId;
	}
	public String getCeName() {
		return ceName;
	}
	public void setCeName(String ceName) {
		this.ceName = ceName;
	}
	public String getCeNameMar() {
		return ceNameMar;
	}
	public void setCeNameMar(String ceNameMar) {
		this.ceNameMar = ceNameMar;
	}
	public short getCertNoCopies() {
		return certNoCopies;
	}
	public void setCertNoCopies(short certNoCopies) {
		this.certNoCopies = certNoCopies;
	}
	public Long getCodUserWardid() {
		return codUserWardid;
	}
	public void setCodUserWardid(Long codUserWardid) {
		this.codUserWardid = codUserWardid;
	}
	public Long getCpdAgeperiodId() {
		return cpdAgeperiodId;
	}
	public void setCpdAgeperiodId(Long cpdAgeperiodId) {
		this.cpdAgeperiodId = cpdAgeperiodId;
	}
	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}
	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}
	public Long getCpdDeathcauseId() {
		return cpdDeathcauseId;
	}
	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
	}
	public String getCpdDeathplaceType() {
		return cpdDeathplaceType;
	}
	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}
	public Long getCpdDistrictId() {
		return cpdDistrictId;
	}
	public void setCpdDistrictId(Long cpdDistrictId) {
		this.cpdDistrictId = cpdDistrictId;
	}
	public Long getCpdEducationId() {
		return cpdEducationId;
	}
	public void setCpdEducationId(Long cpdEducationId) {
		this.cpdEducationId = cpdEducationId;
	}
	public Long getCpdMaritalStatId() {
		return cpdMaritalStatId;
	}
	public void setCpdMaritalStatId(Long cpdMaritalStatId) {
		this.cpdMaritalStatId = cpdMaritalStatId;
	}
	public Long getCpdNationalityId() {
		return cpdNationalityId;
	}
	public void setCpdNationalityId(Long cpdNationalityId) {
		this.cpdNationalityId = cpdNationalityId;
	}
	public Long getCpdOccupationId() {
		return cpdOccupationId;
	}
	public void setCpdOccupationId(Long cpdOccupationId) {
		this.cpdOccupationId = cpdOccupationId;
	}
	public Long getCpdRegUnit() {
		return cpdRegUnit;
	}
	public void setCpdRegUnit(Long cpdRegUnit) {
		this.cpdRegUnit = cpdRegUnit;
	}
	public Long getCpdReligionId() {
		return cpdReligionId;
	}
	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}
	public Long getCpdResId() {
		return cpdResId;
	}
	public void setCpdResId(Long cpdResId) {
		this.cpdResId = cpdResId;
	}
	public Long getCpdStateId() {
		return cpdStateId;
	}
	public void setCpdStateId(Long cpdStateId) {
		this.cpdStateId = cpdStateId;
	}
	public Long getCpdTalukaId() {
		return cpdTalukaId;
	}
	public void setCpdTalukaId(Long cpdTalukaId) {
		this.cpdTalukaId = cpdTalukaId;
	}
	public String getDcUid() {
		return dcUid;
	}
	public void setDcUid(String dcUid) {
		this.dcUid = dcUid;
	}
	public String getDrAddress() {
		return drAddress;
	}
	public void setDrAddress(String drAddress) {
		this.drAddress = drAddress;
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
	public Long getDrDeceasedage() {
		return drDeceasedage;
	}
	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
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
	public String getDrFirNumber() {
		return drFirNumber;
	}
	public void setDrFirNumber(String drFirNumber) {
		this.drFirNumber = drFirNumber;
	}
	public String getDrFlag() {
		return drFlag;
	}
	public void setDrFlag(String drFlag) {
		this.drFlag = drFlag;
	}
	public String getDrInformantAddr() {
		return drInformantAddr;
	}
	public void setDrInformantAddr(String drInformantAddr) {
		this.drInformantAddr = drInformantAddr;
	}
	public String getDrInformantName() {
		return drInformantName;
	}
	public void setDrInformantName(String drInformantName) {
		this.drInformantName = drInformantName;
	}
	public short getDrManualCertno() {
		return drManualCertno;
	}
	public void setDrManualCertno(short drManualCertno) {
		this.drManualCertno = drManualCertno;
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
	public String getDrMarInformantAddr() {
		return drMarInformantAddr;
	}
	public void setDrMarInformantAddr(String drMarInformantAddr) {
		this.drMarInformantAddr = drMarInformantAddr;
	}
	public String getDrMarInformantName() {
		return drMarInformantName;
	}
	public void setDrMarInformantName(String drMarInformantName) {
		this.drMarInformantName = drMarInformantName;
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
	public String getDrPoliceStation() {
		return drPoliceStation;
	}
	public void setDrPoliceStation(String drPoliceStation) {
		this.drPoliceStation = drPoliceStation;
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
	public String getDrRelPreg() {
		return drRelPreg;
	}
	public void setDrRelPreg(String drRelPreg) {
		this.drRelPreg = drRelPreg;
	}
	public String getDrReldativeName() {
		return drReldativeName;
	}
	public void setDrReldativeName(String drReldativeName) {
		this.drReldativeName = drReldativeName;
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
	public Date getDrSuppdate() {
		return drSuppdate;
	}
	public void setDrSuppdate(Date drSuppdate) {
		this.drSuppdate = drSuppdate;
	}
	public String getDrSuppno() {
		return drSuppno;
	}
	public void setDrSuppno(String drSuppno) {
		this.drSuppno = drSuppno;
	}
	public String getDrSupptime() {
		return drSupptime;
	}
	public void setDrSupptime(String drSupptime) {
		this.drSupptime = drSupptime;
	}
	public String getDrTime() {
		return drTime;
	}
	public void setDrTime(String drTime) {
		this.drTime = drTime;
	}
	public String getDrUdeathReg() {
		return drUdeathReg;
	}
	public void setDrUdeathReg(String drUdeathReg) {
		this.drUdeathReg = drUdeathReg;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String gethRId() {
		return hRId;
	}
	public void sethRId(String hRId) {
		this.hRId = hRId;
	}
	public Long getHiId() {
		return hiId;
	}
	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}
	public String getHrReg() {
		return hrReg;
	}
	public void setHrReg(String hrReg) {
		this.hrReg = hrReg;
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
	public String getOtherReligion() {
		return otherReligion;
	}
	public void setOtherReligion(String otherReligion) {
		this.otherReligion = otherReligion;
	}
	public String getPdUidF() {
		return pdUidF;
	}
	public void setPdUidF(String pdUidF) {
		this.pdUidF = pdUidF;
	}
	public String getPdUidM() {
		return pdUidM;
	}
	public void setPdUidM(String pdUidM) {
		this.pdUidM = pdUidM;
	}
	public String getPgflag() {
		return pgflag;
	}
	public void setPgflag(String pgflag) {
		this.pgflag = pgflag;
	}
	public Date getRegAplDate() {
		return regAplDate;
	}
	public void setRegAplDate(Date regAplDate) {
		this.regAplDate = regAplDate;
	}
	public String getUnauthRFlg() {
		return unauthRFlg;
	}
	public void setUnauthRFlg(String unauthRFlg) {
		this.unauthRFlg = unauthRFlg;
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
	public Long getWardid() {
		return wardid;
	}
	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}
	
}
