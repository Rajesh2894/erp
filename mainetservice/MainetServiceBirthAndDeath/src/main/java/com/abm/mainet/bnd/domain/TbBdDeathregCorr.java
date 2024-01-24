package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

@Entity
@Table(name = "tb_bd_deathreg_corr")
@NamedQuery(name = "TbBdDeathregCorr.findAll", query = "SELECT t FROM TbBdDeathregCorr t")
public class TbBdDeathregCorr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "dr_corr_id", nullable = false)
	private Long drCorrId;

	@Column(nullable = false)
	private Long orgId;

	@Column(name = "apm_application_id")
	private Long apmApplicationId;

	@Column(name = "cod_user_wardid")
	private Long codUserWardid;

	@Column(name = "corr_auth_by")
	private double corrAuthBy;

	@Column(name = "corr_auth_date")
	private Date corrAuthDate;

	@Column(name = "corr_auth_flag", length = 1)
	private String corrAuthFlag;

	@Column(name = "corr_auth_remark", length = 500)
	private String corrAuthRemark;

	@Column(name = "cpd_deathplace_type", length = 10)
	private String cpdDeathplaceType;

	@Column(name = "dr_cert_no", length = 12)
	private String drCertNo;

	@Column(name = "dr_correction_flg", length = 1)
	private String drCorrectionFlg;

	@Column(name = "dr_corrn_date")
	private Date drCorrnDate;

	@Column(name = "dr_dcaddr_atdeath", length = 200)
	private String drDcaddrAtdeath;

	@Column(name = "dr_dcaddr_atdeath_mar", length = 500)
	private String drDcaddrAtdeathMar;

	@Column(name = "dr_deathaddr", length = 200)
	private String drDeathaddr;

	@Column(name = "dr_deathplace", length = 300)
	private String drDeathplace;

	@Column(name = "dr_deceasedaddr", length = 200)
	private String drDeceasedaddr;

	@Column(name = "dr_deceasedname", length = 200)
	private String drDeceasedname;

	@Temporal(TemporalType.DATE)
	@Column(name = "dr_dod", nullable = false)
	private Date drDod;

	@Column(name = "dr_id", nullable = false)
	private Long drId;

	@Column(name = "dr_mar_deathaddr", length = 200)
	private String drMarDeathaddr;

	@Column(name = "dr_mar_deathplace", length = 200)
	private String drMarDeathplace;

	@Column(name = "dr_mar_deceasedaddr", length = 200)
	private String drMarDeceasedaddr;

	@Column(name = "dr_mar_deceasedname", length = 200)
	private String drMarDeceasedname;

	@Column(name = "dr_mar_mother_name", length = 200)
	private String drMarMotherName;

	@Column(name = "dr_mar_relative_name", length = 200)
	private String drMarRelativeName;

	@Column(name = "dr_mother_name", length = 100)
	private String drMotherName;

	@Column(name = "dr_regdate", nullable = false)
	private Date drRegdate;

	@Column(name = "dr_regno", length = 12)
	private String drRegno;

	@Column(name = "dr_relative_name", length = 200)
	private String drRelativeName;

	@Column(name = "dr_remarks", length = 2000)
	private String drRemarks;

	@Column(name = "dr_sex", nullable = false)
	private String drSex;

	@Column(name = "dr_status", length = 1)
	private String drStatus;

	@Column(name = "hi_id")
	private Long hiId;

	@Column(name = "CE_ID")
	private Long ceId;
	
	@Column(name = "CE_NAME", length = 100)
	private String ceName;

	@Column(name = "CE_NAME_MAR", length = 100)
	private String ceNameMar;
	
	@Column(name = "CE_ADDR", length = 200, nullable = true)
	private String ceAddr;

	@Column(name = "CE_ADDR_MAR", length = 200)
	private String ceAddrMar;

	@Column(name = "CE_FLAG", length = 1)
	private String ceFlag;
	
	@Column(name = "lang_id", nullable = false)
	private int langId;

	@Column(name = "lg_ip_mac", length = 100)
	private String lgIpMac;

	@Column(name = "lg_ip_mac_upd", length = 100)
	private String lgIpMacUpd;

	@Column(name = "lmoddate")
	private Date lmoddate;

	@Column(name = "req_flag", length = 1)
	private String reqFlag;

	@Column(name = "sm_service_id")
	private Long smServiceId;

	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "WF_STATUS", nullable = false)
	private String DeathWFStatus;
	
	private Long wardid;
	
	@Column(name = "DR_DECEASEDAGE", precision = 10, scale = 2)
	private Long drDeceasedage;
	
	@Column(name = "CPD_AGEPERIOD_ID")
	private Long cpdAgeperiodId;
	
	@Column(name = "CPD_RELIGION_ID")
	private Long cpdReligionId;
	
	@Column(name = "CPD_MARITAL_STAT_ID")
	private Long cpdMaritalStatId;
	
	@Column(name = "CPD_OCCUPATION_ID")
	private Long cpdOccupationId;
	
	@Column(name = "CPD_DEATHCAUSE_ID", nullable = false)
	private Long cpdDeathcauseId;
	
	@Column(name = "DR_INFORMANT_ADDR", length = 200)
	private String drInformantAddr;

	@Column(name = "DR_INFORMANT_NAME", length = 200)
	private String drInformantName;
	
	@Column(name = "MC_OTHERCOND", length = 200, nullable = true)
	// comments :Other Conditions
	private String mcOthercond;
	
	@Column(name = "MC_MD_SUPR_NAME", length = 50, nullable = false)
	// comments :Medical Superintendent Name
	private String mcMdSuprName;
	
	@Column(name = "DR_CORR_CATEGORY", length = 400, nullable = true)
	private String corrCategory;
	
	@Column(name = "PD_REG_UNIT_ID", precision = 12, scale = 0, nullable = true)
	private Long pdRegUnitId;

	public Long getDrCorrId() {
		return drCorrId;
	}
	

	public void setDrCorrId(Long drCorrId) {
		this.drCorrId = drCorrId;
	}

	public TbBdDeathregCorr() {
	}

	public double getCorrAuthBy() {
		return this.corrAuthBy;
	}

	public void setCorrAuthBy(double corrAuthBy) {
		this.corrAuthBy = corrAuthBy;
	}

	public Date getCorrAuthDate() {
		return this.corrAuthDate;
	}

	public void setCorrAuthDate(Date corrAuthDate) {
		this.corrAuthDate = corrAuthDate;
	}

	public String getCorrAuthFlag() {
		return this.corrAuthFlag;
	}

	public void setCorrAuthFlag(String corrAuthFlag) {
		this.corrAuthFlag = corrAuthFlag;
	}

	public String getCorrAuthRemark() {
		return this.corrAuthRemark;
	}

	public void setCorrAuthRemark(String corrAuthRemark) {
		this.corrAuthRemark = corrAuthRemark;
	}

	public String getCpdDeathplaceType() {
		return this.cpdDeathplaceType;
	}

	public void setCpdDeathplaceType(String cpdDeathplaceType) {
		this.cpdDeathplaceType = cpdDeathplaceType;
	}

	public String getDrCertNo() {
		return this.drCertNo;
	}

	public void setDrCertNo(String drCertNo) {
		this.drCertNo = drCertNo;
	}

	public String getDrCorrectionFlg() {
		return this.drCorrectionFlg;
	}

	public void setDrCorrectionFlg(String drCorrectionFlg) {
		this.drCorrectionFlg = drCorrectionFlg;
	}

	public Date getDrCorrnDate() {
		return this.drCorrnDate;
	}

	public void setDrCorrnDate(Date drCorrnDate) {
		this.drCorrnDate = drCorrnDate;
	}

	public String getDrDcaddrAtdeath() {
		return this.drDcaddrAtdeath;
	}

	public void setDrDcaddrAtdeath(String drDcaddrAtdeath) {
		this.drDcaddrAtdeath = drDcaddrAtdeath;
	}

	public String getDrDcaddrAtdeathMar() {
		return this.drDcaddrAtdeathMar;
	}

	public void setDrDcaddrAtdeathMar(String drDcaddrAtdeathMar) {
		this.drDcaddrAtdeathMar = drDcaddrAtdeathMar;
	}

	public String getDrDeathaddr() {
		return this.drDeathaddr;
	}

	public void setDrDeathaddr(String drDeathaddr) {
		this.drDeathaddr = drDeathaddr;
	}

	public String getDrDeathplace() {
		return this.drDeathplace;
	}

	public void setDrDeathplace(String drDeathplace) {
		this.drDeathplace = drDeathplace;
	}

	public String getDrDeceasedaddr() {
		return this.drDeceasedaddr;
	}

	public void setDrDeceasedaddr(String drDeceasedaddr) {
		this.drDeceasedaddr = drDeceasedaddr;
	}

	public String getDrDeceasedname() {
		return this.drDeceasedname;
	}

	public void setDrDeceasedname(String drDeceasedname) {
		this.drDeceasedname = drDeceasedname;
	}

	public Date getDrDod() {
		return this.drDod;
	}

	public void setDrDod(Date drDod) {
		this.drDod = drDod;
	}

	public String getDrMarDeathaddr() {
		return this.drMarDeathaddr;
	}

	public void setDrMarDeathaddr(String drMarDeathaddr) {
		this.drMarDeathaddr = drMarDeathaddr;
	}

	public String getDrMarDeathplace() {
		return this.drMarDeathplace;
	}

	public void setDrMarDeathplace(String drMarDeathplace) {
		this.drMarDeathplace = drMarDeathplace;
	}

	public String getDrMarDeceasedaddr() {
		return this.drMarDeceasedaddr;
	}

	public void setDrMarDeceasedaddr(String drMarDeceasedaddr) {
		this.drMarDeceasedaddr = drMarDeceasedaddr;
	}

	public String getDrMarDeceasedname() {
		return this.drMarDeceasedname;
	}

	public void setDrMarDeceasedname(String drMarDeceasedname) {
		this.drMarDeceasedname = drMarDeceasedname;
	}

	public String getDrMarMotherName() {
		return this.drMarMotherName;
	}

	public void setDrMarMotherName(String drMarMotherName) {
		this.drMarMotherName = drMarMotherName;
	}

	public String getDrMarRelativeName() {
		return this.drMarRelativeName;
	}

	public void setDrMarRelativeName(String drMarRelativeName) {
		this.drMarRelativeName = drMarRelativeName;
	}

	public String getDrMotherName() {
		return this.drMotherName;
	}

	public void setDrMotherName(String drMotherName) {
		this.drMotherName = drMotherName;
	}

	public Date getDrRegdate() {
		return this.drRegdate;
	}

	public void setDrRegdate(Date drRegdate) {
		this.drRegdate = drRegdate;
	}

	public String getDrRegno() {
		return this.drRegno;
	}

	public void setDrRegno(String drRegno) {
		this.drRegno = drRegno;
	}

	public String getDrRelativeName() {
		return this.drRelativeName;
	}

	public void setDrRelativeName(String drRelativeName) {
		this.drRelativeName = drRelativeName;
	}

	public String getDrRemarks() {
		return this.drRemarks;
	}

	public void setDrRemarks(String drRemarks) {
		this.drRemarks = drRemarks;
	}

	public String getDrSex() {
		return this.drSex;
	}

	public void setDrSex(String drSex) {
		this.drSex = drSex;
	}

	public String getDrStatus() {
		return this.drStatus;
	}

	public void setDrStatus(String drStatus) {
		this.drStatus = drStatus;
	}

	public int getLangId() {
		return this.langId;
	}

	public void setLangId(int langId) {
		this.langId = langId;
	}

	public String getLgIpMac() {
		return this.lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getLgIpMacUpd() {
		return this.lgIpMacUpd;
	}

	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Date getLmoddate() {
		return this.lmoddate;
	}

	public void setLmoddate(Date lmoddate) {
		this.lmoddate = lmoddate;
	}

	public String getReqFlag() {
		return this.reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return this.updatedDate;
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

	public Long getDrId() {
		return drId;
	}

	public void setDrId(Long drId) {
		this.drId = drId;
	}

	public Long getHiId() {
		return hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getWardid() {
		return wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	
	public String getDeathWFStatus() {
		return DeathWFStatus;
	}


	public void setDeathWFStatus(String deathWFStatus) {
		DeathWFStatus = deathWFStatus;
	}


	public Long getDrDeceasedage() {
		return drDeceasedage;
	}


	public void setDrDeceasedage(Long drDeceasedage) {
		this.drDeceasedage = drDeceasedage;
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


	public Long getCpdDeathcauseId() {
		return cpdDeathcauseId;
	}


	public void setCpdDeathcauseId(Long cpdDeathcauseId) {
		this.cpdDeathcauseId = cpdDeathcauseId;
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

	public String getCorrCategory() {
		return corrCategory;
	}

	public void setCorrCategory(String corrCategory) {
		this.corrCategory = corrCategory;
	}

	public Long getPdRegUnitId() {
		return pdRegUnitId;
	}


	public void setPdRegUnitId(Long pdRegUnitId) {
		this.pdRegUnitId = pdRegUnitId;
	}


	public String[] getPkValues() {
		return new String[] { "HD", "tb_bd_deathreg_corr", "dr_corr_id" };
	}
	
}