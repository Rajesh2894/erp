package com.abm.mainet.bnd.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_PARENTDETAIL_HIST")
public class ParentDetailHistory implements Serializable {

	private static final long serialVersionUID = -2331696691351443402L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "PD_HIST_ID", nullable = false, precision = 12)
	private Long pdHiId;
	
	@Column(name = "ORGID", nullable = false, updatable = false)
	private Long orgId;

	@Column(name = "PD_FATHERNAME", length = 200, nullable = true)
	private String pdFathername;

	@Column(name = "PD_MOTHERNAME", length = 200, nullable = true)
	private String pdMothername;

	@Column(name = "PD_ADDRESS", length = 200, nullable = true)
	private String pdAddress;

	@Column(name = "CPD_DISTRICT_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdId3;

	@Column(name = "CPD_STATE_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdId2;

	@Column(name = "CPD_RELIGION_ID", precision = 12, scale = 0, nullable = false)
	private Long cpdReligionId;

	@Column(name = "CPD_NATIONLTY_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdId1;

	@Column(name = "CPD_F_EDUCN_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdFEducnId;

	@Column(name = "CPD_M_EDUCN_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdMEducnId;

	@Column(name = "CPD_F_OCCU_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdFOccuId;

	@Column(name = "CPD_M_OCCU_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdMOccuId;

	@Column(name = "PD_AGE_AT_MARRY", precision = 12, scale = 0, nullable = true)
	private Long pdAgeAtMarry;

	@Column(name = "PD_AGE_AT_BIRTH", precision = 2, scale = 0, nullable = true)
	private Long pdAgeAtBirth;

	@Column(name = "PD_LIVE_CHILDN", precision = 2, scale = 0, nullable = true)
	private Long pdLiveChildn;

	@Column(name = "BR_ID", nullable = true)
	private Long pdBrId;

	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	private int langId;

	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@Column(name = "CPD_RES_ID", precision = 12, scale = 0, nullable = false)
	private Long cpdId5;

	@Column(name = "PD_STATUS", length = 1, nullable = true)
	private String pdStatus;

	@Column(name = "PD_GFATHERNAME", length = 60, nullable = true)
	private String pdGfathername;

	@Column(name = "PD_GMOTHERNAME", length = 60, nullable = true)
	private String pdGmothername;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "PD_FATHERNAME_MAR", length = 500, nullable = true)
	private String pdFathernameMar;

	@Column(name = "PD_MOTHERNAME_MAR", length = 500, nullable = true)
	private String pdMothernameMar;

	@Column(name = "PD_ADDRESS_MAR", length = 500, nullable = true)
	private String pdAddressMar;

	@Column(name = "CPD_TALUKA_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdId4;

	@Column(name = "PD_REG_UNIT_ID", precision = 12, scale = 0, nullable = true)
	private Long pdRegUnitId;

	@Column(name = "H_R_ID", length = 50, nullable = true)
	private String hRId;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "PD_PARADDRESS", length = 200, nullable = true)
	private String pdParaddress;

	@Column(name = "PD_PARADDRESS_MAR", length = 500, nullable = true)
	private String pdParaddressMar;

	@Column(name = "UPLD_ID", precision = 15, scale = 0, nullable = true)
	private Long upldId;

	@Column(name = "OTHER_RELIGION", length = 500, nullable = true)
	private String otherReligion;

	@Column(name = "PD_UID_F", length = 12, nullable = true)
	private String pdUidF;

	@Column(name = "PD_UID_M", length = 12, nullable = true)
	private String pdUidM;

	@Column(name = "BPL_NO", length = 16, nullable = true)
	private String bplNo;

	@Column(name = "PD_MOTHER_ADD", length = 500, nullable = true)
	private String motheraddress;

	@Column(name = "PD_ADDRESS_FLAG", length = 1, nullable = true)
	private String addressflag;
	
	@Column(name = "ACTION", nullable = true, updatable = false)
	private String action;

	public Long getPdHiId() {
		return pdHiId;
	}

	public void setPdHiId(Long pdHiId) {
		this.pdHiId = pdHiId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getPdFathername() {
		return pdFathername;
	}

	public void setPdFathername(String pdFathername) {
		this.pdFathername = pdFathername;
	}

	public String getPdMothername() {
		return pdMothername;
	}

	public void setPdMothername(String pdMothername) {
		this.pdMothername = pdMothername;
	}

	public String getPdAddress() {
		return pdAddress;
	}

	public void setPdAddress(String pdAddress) {
		this.pdAddress = pdAddress;
	}

	public Long getCpdId3() {
		return cpdId3;
	}

	public void setCpdId3(Long cpdId3) {
		this.cpdId3 = cpdId3;
	}

	public Long getCpdId2() {
		return cpdId2;
	}

	public void setCpdId2(Long cpdId2) {
		this.cpdId2 = cpdId2;
	}

	public Long getCpdReligionId() {
		return cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
	}

	public Long getCpdId1() {
		return cpdId1;
	}

	public void setCpdId1(Long cpdId1) {
		this.cpdId1 = cpdId1;
	}

	public Long getCpdFEducnId() {
		return cpdFEducnId;
	}

	public void setCpdFEducnId(Long cpdFEducnId) {
		this.cpdFEducnId = cpdFEducnId;
	}

	public Long getCpdMEducnId() {
		return cpdMEducnId;
	}

	public void setCpdMEducnId(Long cpdMEducnId) {
		this.cpdMEducnId = cpdMEducnId;
	}

	public Long getCpdFOccuId() {
		return cpdFOccuId;
	}

	public void setCpdFOccuId(Long cpdFOccuId) {
		this.cpdFOccuId = cpdFOccuId;
	}

	public Long getCpdMOccuId() {
		return cpdMOccuId;
	}

	public void setCpdMOccuId(Long cpdMOccuId) {
		this.cpdMOccuId = cpdMOccuId;
	}

	public Long getPdAgeAtMarry() {
		return pdAgeAtMarry;
	}

	public void setPdAgeAtMarry(Long pdAgeAtMarry) {
		this.pdAgeAtMarry = pdAgeAtMarry;
	}

	public Long getPdAgeAtBirth() {
		return pdAgeAtBirth;
	}

	public void setPdAgeAtBirth(Long pdAgeAtBirth) {
		this.pdAgeAtBirth = pdAgeAtBirth;
	}

	public Long getPdLiveChildn() {
		return pdLiveChildn;
	}

	public void setPdLiveChildn(Long pdLiveChildn) {
		this.pdLiveChildn = pdLiveChildn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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

	public Long getCpdId5() {
		return cpdId5;
	}

	public void setCpdId5(Long cpdId5) {
		this.cpdId5 = cpdId5;
	}

	public String getPdStatus() {
		return pdStatus;
	}

	public void setPdStatus(String pdStatus) {
		this.pdStatus = pdStatus;
	}

	public String getPdGfathername() {
		return pdGfathername;
	}

	public void setPdGfathername(String pdGfathername) {
		this.pdGfathername = pdGfathername;
	}

	public String getPdGmothername() {
		return pdGmothername;
	}

	public void setPdGmothername(String pdGmothername) {
		this.pdGmothername = pdGmothername;
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

	public String getPdFathernameMar() {
		return pdFathernameMar;
	}

	public void setPdFathernameMar(String pdFathernameMar) {
		this.pdFathernameMar = pdFathernameMar;
	}

	public String getPdMothernameMar() {
		return pdMothernameMar;
	}

	public void setPdMothernameMar(String pdMothernameMar) {
		this.pdMothernameMar = pdMothernameMar;
	}

	public String getPdAddressMar() {
		return pdAddressMar;
	}

	public void setPdAddressMar(String pdAddressMar) {
		this.pdAddressMar = pdAddressMar;
	}

	public Long getCpdId4() {
		return cpdId4;
	}

	public void setCpdId4(Long cpdId4) {
		this.cpdId4 = cpdId4;
	}

	public Long getPdRegUnitId() {
		return pdRegUnitId;
	}

	public void setPdRegUnitId(Long pdRegUnitId) {
		this.pdRegUnitId = pdRegUnitId;
	}

	public String gethRId() {
		return hRId;
	}

	public void sethRId(String hRId) {
		this.hRId = hRId;
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

	public void setLgIpMacUpd(String lgIpMacUpd){
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public String getPdParaddress() {
		return pdParaddress;
	}

	public void setPdParaddress(String pdParaddress) {
		this.pdParaddress = pdParaddress;
	}

	public String getPdParaddressMar() {
		return pdParaddressMar;
	}

	public void setPdParaddressMar(String pdParaddressMar) {
		this.pdParaddressMar = pdParaddressMar;
	}

	public Long getUpldId() {
		return upldId;
	}

	public void setUpldId(Long upldId) {
		this.upldId = upldId;
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

	public String getBplNo() {
		return bplNo;
	}

	public void setBplNo(String bplNo) {
		this.bplNo = bplNo;
	}

	public String getMotheraddress() {
		return motheraddress;
	}

	public void setMotheraddress(String motheraddress) {
		this.motheraddress = motheraddress;
	}

	public String getAddressflag() {
		return addressflag;
	}

	public void setAddressflag(String addressflag) {
		this.addressflag = addressflag;
	}

	public Long getPdBrId() {
		return pdBrId;
	}

	public void setPdBrId(Long pdBrId) {
		this.pdBrId = pdBrId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String[] getPkValues() {
		return new String[] { "HD", "TB_PARENTDETAIL_HIST", "PD_HIST_ID" };
	}

}