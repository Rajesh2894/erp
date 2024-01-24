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
@Table(name = "TB_BD_BIRTHREG_CORR_HIST")
public class BirthRegistrationCorrectionHistory implements Serializable {

	private static final long serialVersionUID = 6300329475683142838L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "BR_CORR_HI_ID", precision = 0, scale = 0, nullable = false)
	private long brCorrHiId;

	@Column(name = "ORGID", nullable = true, updatable = false)
	private Long orgId;

	@Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
	private Long apmApplicationId;

	@Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
	private Long smServiceId;

	@Column(name = "BR_ID", precision = 12, scale = 0, nullable = false)
	private Long brId;

	@Column(name = "WARDID", precision = 12, scale = 0, nullable = true)
	private Long wardid;

	@Column(name = "BR_REGNO", length = 12, nullable = true)
	private String brRegno;

	@Column(name = "BR_REGDATE", nullable = false)
	private Date brRegdate;

	@Column(name = "BR_DOB", nullable = true)
	private Date brDob;

	@Column(name = "BR_SEX", length = 5, nullable = false)
	private String brSex;

	@Column(name = "BR_CHILDNAME", length = 200, nullable = true)
	private String brChildname;

	@Column(name = "BR_CHILDNAME_MAR", length = 500, nullable = true)
	private String brChildnameMar;

	@Column(name = "BR_BIRTHPLACE_TYPE", length = 1, nullable = true)
	private String brBirthplaceType;

	@Column(name = "HI_ID", precision = 0, scale = 0, nullable = true)
	private Long hiId;

	@Column(name = "BR_BIRTHPLACE", length = 100, nullable = true)
	private String brBirthplace;

	@Column(name = "BR_BIRTHPLACE_MAR", length = 100, nullable = true)
	private String brBirthplaceMar;

	@Column(name = "BR_BIRTHADDR", length = 200, nullable = true)
	private String brBirthaddr;

	@Column(name = "BR_BIRTHADDR_MAR", length = 500, nullable = true)
	private String brBirthaddrMar;

	@Column(name = "PD_ID", precision = 0, scale = 0, nullable = true)
	private Long pdId;

	@Column(name = "PD_FATHERNAME", length = 200, nullable = true)
	private String pdFathername;

	@Column(name = "PD_FATHERNAME_MAR", length = 500, nullable = true)
	private String pdFathernameMar;

	@Column(name = "PD_MOTHERNAME", length = 200, nullable = true)
	private String pdMothername;

	@Column(name = "PD_MOTHERNAME_MAR", length = 500, nullable = true)
	private String pdMothernameMar;

	@Column(name = "PD_ADDRESS", length = 200, nullable = true)
	private String pdAddress;

	@Column(name = "PD_ADDRESS_MAR", length = 500, nullable = true)
	private String pdAddressMar;

	@Column(name = "PD_PARADDRESS", length = 200, nullable = true)
	private String pdParaddress;

	@Column(name = "PD_PARADDRESS_MAR", length = 500, nullable = true)
	private String pdParaddressMar;

	@Column(name = "BR_REMARKS", length = 2000, nullable = true)
	private String brRemarks;

	@Column(name = "BR_CORRN_DATE", nullable = true)
	private Date brCorrnDate;

	@Column(name = "CPD_ATTNTYPE_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdAttntypeId;

	@Column(name = "CPD_DELMETH_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdDelmethId;

	@Column(name = "BR_CERT_NO", length = 12, nullable = true)
	private String brCertNo;

	@Column(name = "USER_ID", nullable = false, updatable = false)
	private Long userId;

	@Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
	private int langId;

	@Column(name = "LMODDATE", nullable = false)
	private Date lmodDate;

	@Column(name = "BR_STATUS", length = 1, nullable = true)
	private String brStatus;

	@Column(name = "UPDATED_BY", nullable = false, updatable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = true)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "COD_USER_WARDID", precision = 12, scale = 0, nullable = true)
	private Long codUserWardid;

	@Column(name = "CORR_AUTH_BY", precision = 0, scale = 0, nullable = true)
	private Long corrAuthBy;

	@Column(name = "CORR_AUTH_DATE", nullable = true)
	private Date corrAuthDate;

	@Column(name = "CORR_AUTH_FLAG", length = 1, nullable = true)
	private String corrAuthFlag;

	@Column(name = "CORR_AUTH_REMARK", length = 500, nullable = true)
	private String corrAuthRemark;

	@Column(name = "AD_COURT_NM", length = 100, nullable = false)
	private String adCourtNm;

	@Column(name = "AD_ORDER_NO", length = 50, nullable = false)
	private String adOrderNo;

	@Column(name = "AD_ORDER_DATE", nullable = false)
	private Date adOrderDate;

	@Column(name = "AD_REMARKS", length = 2000, nullable = true)
	private String adRemarks;

	@Column(name = "REQ_FLAG", length = 1, nullable = true)
	private String reqFlag;

	@Column(name = "SERVICE_FLAG", length = 1, nullable = true)
	private String serviceFlag;

	@Column(name = "ACTION", length = 10, nullable = false)
	private String action;
	
	@Column(name = "CPD_RELIGION_ID", precision = 12, scale = 0, nullable = false)
	private Long cpdReligionId;
	
	@Column(name = "CPD_F_EDUCN_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdFEducnId;

	@Column(name = "CPD_M_EDUCN_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdMEducnId;

	@Column(name = "CPD_F_OCCU_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdFOccuId;

	@Column(name = "CPD_M_OCCU_ID", precision = 12, scale = 0, nullable = true)
	private Long cpdMOccuId;
	
	@Column(name = "PD_AGE_AT_BIRTH", precision = 2, scale = 0, nullable = true)
	private Long pdAgeAtBirth;

	@Column(name = "PD_LIVE_CHILDN", precision = 2, scale = 0, nullable = true)
	private Long pdLiveChildn;
	
	@Column(name = "BR_INFORMANT_NAME", length = 200, nullable = true)
	private String brInformantName;

	@Column(name = "BR_INFORMANT_ADDR", length = 400, nullable = true)
	private String brInformantAddr;
	
	@Column(name = "BR_CORR_CATEGORY", length = 400, nullable = true)
	private String corrCategory;
	
	@Column(name = "PD_REG_UNIT_ID", precision = 12, scale = 0, nullable = true)
	private Long pdRegUnitId;

	public long getBrCorrHiId() {
		return brCorrHiId;
	}

	public void setBrCorrHiId(long brCorrHiId) {
		this.brCorrHiId = brCorrHiId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Long getSmServiceId() {
		return smServiceId;
	}

	public void setSmServiceId(Long smServiceId) {
		this.smServiceId = smServiceId;
	}

	public Long getBrId() {
		return brId;
	}

	public void setBrId(Long brId) {
		this.brId = brId;
	}

	public Long getWardid() {
		return wardid;
	}

	public void setWardid(Long wardid) {
		this.wardid = wardid;
	}

	public String getBrRegno() {
		return brRegno;
	}

	public void setBrRegno(String brRegno) {
		this.brRegno = brRegno;
	}

	public Date getBrRegdate() {
		return brRegdate;
	}

	public void setBrRegdate(Date brRegdate) {
		this.brRegdate = brRegdate;
	}

	public Date getBrDob() {
		return brDob;
	}

	public void setBrDob(Date brDob) {
		this.brDob = brDob;
	}

	public String getBrSex() {
		return brSex;
	}

	public void setBrSex(String brSex) {
		this.brSex = brSex;
	}

	public String getBrChildname() {
		return brChildname;
	}

	public void setBrChildname(String brChildname) {
		this.brChildname = brChildname;
	}

	public String getBrChildnameMar() {
		return brChildnameMar;
	}

	public void setBrChildnameMar(String brChildnameMar) {
		this.brChildnameMar = brChildnameMar;
	}

	public String getBrBirthplaceType() {
		return brBirthplaceType;
	}

	public void setBrBirthplaceType(String brBirthplaceType) {
		this.brBirthplaceType = brBirthplaceType;
	}

	public Long getHiId() {
		return hiId;
	}

	public void setHiId(Long hiId) {
		this.hiId = hiId;
	}

	public String getBrBirthplace() {
		return brBirthplace;
	}

	public void setBrBirthplace(String brBirthplace) {
		this.brBirthplace = brBirthplace;
	}

	public String getBrBirthplaceMar() {
		return brBirthplaceMar;
	}

	public void setBrBirthplaceMar(String brBirthplaceMar) {
		this.brBirthplaceMar = brBirthplaceMar;
	}

	public String getBrBirthaddr() {
		return brBirthaddr;
	}

	public void setBrBirthaddr(String brBirthaddr) {
		this.brBirthaddr = brBirthaddr;
	}

	public String getBrBirthaddrMar() {
		return brBirthaddrMar;
	}

	public void setBrBirthaddrMar(String brBirthaddrMar) {
		this.brBirthaddrMar = brBirthaddrMar;
	}

	public Long getPdId() {
		return pdId;
	}

	public void setPdId(Long pdId) {
		this.pdId = pdId;
	}

	public String getPdFathername() {
		return pdFathername;
	}

	public void setPdFathername(String pdFathername) {
		this.pdFathername = pdFathername;
	}

	public String getPdFathernameMar() {
		return pdFathernameMar;
	}

	public void setPdFathernameMar(String pdFathernameMar) {
		this.pdFathernameMar = pdFathernameMar;
	}

	public String getPdMothername() {
		return pdMothername;
	}

	public void setPdMothername(String pdMothername) {
		this.pdMothername = pdMothername;
	}

	public String getPdMothernameMar() {
		return pdMothernameMar;
	}

	public void setPdMothernameMar(String pdMothernameMar) {
		this.pdMothernameMar = pdMothernameMar;
	}

	public String getPdAddress() {
		return pdAddress;
	}

	public void setPdAddress(String pdAddress) {
		this.pdAddress = pdAddress;
	}

	public String getPdAddressMar() {
		return pdAddressMar;
	}

	public void setPdAddressMar(String pdAddressMar) {
		this.pdAddressMar = pdAddressMar;
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

	public String getBrRemarks() {
		return brRemarks;
	}

	public void setBrRemarks(String brRemarks) {
		this.brRemarks = brRemarks;
	}

	public Date getBrCorrnDate() {
		return brCorrnDate;
	}

	public void setBrCorrnDate(Date brCorrnDate) {
		this.brCorrnDate = brCorrnDate;
	}

	public Long getCpdAttntypeId() {
		return cpdAttntypeId;
	}

	public void setCpdAttntypeId(Long cpdAttntypeId) {
		this.cpdAttntypeId = cpdAttntypeId;
	}

	public Long getCpdDelmethId() {
		return cpdDelmethId;
	}

	public void setCpdDelmethId(Long cpdDelmethId) {
		this.cpdDelmethId = cpdDelmethId;
	}

	public String getBrCertNo() {
		return brCertNo;
	}

	public void setBrCertNo(String brCertNo) {
		this.brCertNo = brCertNo;
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

	public String getBrStatus() {
		return brStatus;
	}

	public void setBrStatus(String brStatus) {
		this.brStatus = brStatus;
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

	public Long getCodUserWardid() {
		return codUserWardid;
	}

	public void setCodUserWardid(Long codUserWardid) {
		this.codUserWardid = codUserWardid;
	}

	public Long getCorrAuthBy() {
		return corrAuthBy;
	}

	public void setCorrAuthBy(Long corrAuthBy) {
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

	public String getAdCourtNm() {
		return adCourtNm;
	}

	public void setAdCourtNm(String adCourtNm) {
		this.adCourtNm = adCourtNm;
	}

	public String getAdOrderNo() {
		return adOrderNo;
	}

	public void setAdOrderNo(String adOrderNo) {
		this.adOrderNo = adOrderNo;
	}

	public Date getAdOrderDate() {
		return adOrderDate;
	}

	public void setAdOrderDate(Date adOrderDate) {
		this.adOrderDate = adOrderDate;
	}

	public String getAdRemarks() {
		return adRemarks;
	}

	public void setAdRemarks(String adRemarks) {
		this.adRemarks = adRemarks;
	}

	public String getReqFlag() {
		return reqFlag;
	}

	public void setReqFlag(String reqFlag) {
		this.reqFlag = reqFlag;
	}

	public String getServiceFlag() {
		return serviceFlag;
	}

	public void setServiceFlag(String serviceFlag) {
		this.serviceFlag = serviceFlag;
	}
   
	
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getCpdReligionId() {
		return cpdReligionId;
	}

	public void setCpdReligionId(Long cpdReligionId) {
		this.cpdReligionId = cpdReligionId;
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

	public String getBrInformantName() {
		return brInformantName;
	}

	public void setBrInformantName(String brInformantName) {
		this.brInformantName = brInformantName;
	}

	public String getBrInformantAddr() {
		return brInformantAddr;
	}

	public void setBrInformantAddr(String brInformantAddr) {
		this.brInformantAddr = brInformantAddr;
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
		return new String[] { "HD", "TB_BD_BIRTHREG_CORR_HIST", "BR_CORR_HI_ID" };
	}

}
