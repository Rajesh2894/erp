package com.abm.mainet.buildingplan.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_BPMS_LIC_MSTR_HIST")
public class LicenseApplicationMasterHist implements Serializable {

	private static final long serialVersionUID = -4172501212383484097L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "H_TCP_LIC_MSTR_ID", nullable = false)
	private Long tcpLicMstrHistId;
	
	@Column(name = "TCP_LIC_MSTR_ID")
	private Long tcpLicMstrId;

	@OneToMany(mappedBy = "licenseApplicationMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LicenseApplicationLandDetHist> licenseApplicationLandDetList;
	
	@OneToMany(mappedBy = "licenseApplicationMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LicenseApplicationPurposeTypeDetHistEntity> licenseApplicationPurposeTypeDetEntityList;

	@OneToOne(mappedBy = "licenseApplicationMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private LicenseApplicationLandScheduleHist licenseApplicationLandSchedule;

	@OneToMany(mappedBy = "licenseApplicationMaster", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<LicenseApplicationLandAcquisitionDetHistEntity> licenseApplicationLandAcquisitionDetEntityList;

	@Column(name = "APPLICATION_NO",  nullable = true)
	private Long applicationNo;
	
	@Column(name = "APPLICATION_NO_E_SERVICE", nullable = true)
	private String applicationNoEService;

	@Column(name = "trans_no",  nullable = true)
	private String transNo;
	
	@Column(name = "trans_data", nullable = true)
	private String transData;

	@Column(name = "diary_no",  nullable = true)
	private String diaryNo;

	@Column(name = "case_no", nullable = true)
	private String caseNno;

	@Column(name = "NAME", length = 45, nullable = true)
	private String name;

	@Column(name = "ADDRESS", columnDefinition = "LONGTEXT", nullable = true)
	private String address;

	@Column(name = "EMAIL",nullable = true)
	private String email;

	@Column(name = "AUTH_P_NAME", nullable = true)
	private String authPName;

	@Column(name = "AUTH_P_MOBILE", nullable = true)
	private BigInteger authPMobile;

	@Column(name = "AUTH_P_EMAIL", nullable = true)
	private String authPEmail;

	@Column(name = "AUTH_P_PAN", nullable = true)
	private String authPPAN;

	@Column(name = "AUTH_P_DSC", nullable = true)
	private String authPDSC;

	@Column(name = "AUTH_P_BR", nullable = true)
	private String authPBR;

	@Column(name = "DOC_BR", nullable = true)
	private String docBR;

	@Column(name = "DOC_CAQD", nullable = true)
	private String docCAQD;

	@Column(name = "DOC_COEQD", nullable = true)
	private String docCOEQD;

	@Column(name = "APP_P_APP_TYPE", nullable = true)
	private Long appPAppType;

	@Column(name = "APP_P_LIC_PURPOSE_ID", nullable = true)
	private Long appPLicPurposeId;

	@Column(name = "KHRS_DIST", nullable = true)
	private Long khrsDist;

	@Column(name = "KHRS_DEV_PLAN", nullable = true)
	private Long khrsDevPlan;

	@Column(name = "KHRS_ZONE", nullable = true)
	private Long khrsZone;

	@Column(name = "KHRS_SEC", nullable = true)
	private Long khrsSec;

	@Column(name = "KHRS_THESIL", nullable = true)
	private String khrsThesil;

	@Column(name = "KHRS_REV_EST", nullable = true)
	private String khrsRevEst;

	@Column(name = "KHRS_HADBAST", nullable = true)
	private String khrsHadbast;

	@Column(name = "KHRS_MUSTIL", nullable = true)
	private String khrsMustil;

	@Column(name = "KHRS_KILLA", nullable = true)
	private String khrsKilla;

	@Column(name = "MIN",  nullable = true)
	private String min;

	@Column(name = "TCP_NAME_OF_LO", nullable = true)
	private String tcpNameOfLO;

	@Column(name = "KHRS_DEV_COLLAB",  nullable = true)
	private String khrsDevCollab;

	@Column(name = "KHRS_DEV_COM_NAME", nullable = true)
	private String khrsDevComName;

	@Column(name = "KHRS_COLAB_REG_DATE")
	@Temporal(TemporalType.DATE)
	private Date khrsColabRegDate;

	@Column(name = "KHRS_COLLAB_AGG_REVO", nullable = true)
	private String khrsCollabAggRevo;

	@Column(name = "KHRS_AUTH_SIGN_LO", nullable = true)
	private String khrsAuthSignLO;

	@Column(name = "KHRS_AUR_SIGN_DEV", nullable = true)
	private String khrsAurSignDev;

	@Column(name = "KHRS_REG_AUTH",  nullable = true)
	private String khrsRegAuth;
	
	
	@Column(name = "KHRS_LAND_TYPE_ID", nullable = true)
	private Long khrsLandTypeId;

	@Column(name = "CI_CONS_TYPE", nullable = true)
	private String ciConsType;

	@Column(name = "CI_NON_CONS_TYPE_ID", nullable = true)
	private Long ciNonConsTypeId;

	@Column(name = "CI_CONS_TYPE_KANAL", nullable = true)
	private BigDecimal ciConsTypeKanal;

	@Column(name = "CI_CONS_TYPE_KANAL_TOT", nullable = true)
	private BigDecimal ciConsTypeKanalTot;

	@Column(name = "CI_CONS_TYPE_MARLA",  nullable = true)
	private BigDecimal ciConsTypeMarla;

	@Column(name = "CI_CONS_TYPE_MARLA_TOT", nullable = true)
	private BigDecimal ciConsTypeMarlaTot;

	@Column(name = "CI_CONS_TYPE_SARSAI", nullable = true)
	private BigDecimal ciConsTypeSarsai;

	@Column(name = "CI_CONS_TYPE_SARSAI_TOT", nullable = true)
	private BigDecimal ciConsTypeSarsaiTot;

	@Column(name = "CI_CONS_TYPE_TOT_AREA",nullable = true)
	private BigDecimal ciConsTypeTotArea;

	@Column(name = "CI_AQU_STATUS",nullable = true)
	private String ciAquStatus;

	@Column(name = "CI_TOT_AREA",  nullable = true)
	private BigDecimal ciTotArea;

	@Column(name = "ORGID")
	private Long orgId;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.DATE)
	private Date createdDate;

	@Column(name = "MODIFIED_BY")
	private BigInteger modifiedBy;

	@Column(name = "MODIFIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;

	@Column(name = "IS_DELETED", nullable = true)
	private String isDeleted;

	@Column(name = "LG_IP_MAC", nullable = true)
	private String lgIpMac;

	@Column(name = "DRAFT_FLAG", nullable = true)
	private String draftFlag;
	
	@Column(name = "scruitny_fee")
	private BigDecimal scrutinyFee;
	
	@Column(name = "Licence_fee")
	private BigDecimal licenseFee;
	
	@Column(name = "total_payable")
	private BigDecimal totalFees;
	
	@Column(name = "LOA_DC_Remark")
	private String loaDcRemark;
	
	@Column(name = "H_STATUS")
	private String hStatus;

	public Long getTcpLicMstrId() {
		return tcpLicMstrId;
	}

	public void setTcpLicMstrId(Long tcpLicMstrId) {
		this.tcpLicMstrId = tcpLicMstrId;
	}

	public Long getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(Long applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthPName() {
		return authPName;
	}

	public void setAuthPName(String authPName) {
		this.authPName = authPName;
	}

	public BigInteger getAuthPMobile() {
		return authPMobile;
	}

	public void setAuthPMobile(BigInteger authPMobile) {
		this.authPMobile = authPMobile;
	}

	public String getAuthPEmail() {
		return authPEmail;
	}

	public void setAuthPEmail(String authPEmail) {
		this.authPEmail = authPEmail;
	}

	public String getAuthPPAN() {
		return authPPAN;
	}

	public void setAuthPPAN(String authPPAN) {
		this.authPPAN = authPPAN;
	}

	public String getAuthPDSC() {
		return authPDSC;
	}

	public void setAuthPDSC(String authPDSC) {
		this.authPDSC = authPDSC;
	}

	public String getAuthPBR() {
		return authPBR;
	}

	public void setAuthPBR(String authPBR) {
		this.authPBR = authPBR;
	}

	public String getDocBR() {
		return docBR;
	}

	public void setDocBR(String docBR) {
		this.docBR = docBR;
	}

	public String getDocCAQD() {
		return docCAQD;
	}

	public void setDocCAQD(String docCAQD) {
		this.docCAQD = docCAQD;
	}

	public String getDocCOEQD() {
		return docCOEQD;
	}

	public void setDocCOEQD(String docCOEQD) {
		this.docCOEQD = docCOEQD;
	}

	public Long getAppPAppType() {
		return appPAppType;
	}

	public void setAppPAppType(Long appPAppType) {
		this.appPAppType = appPAppType;
	}

	public Long getAppPLicPurposeId() {
		return appPLicPurposeId;
	}

	public void setAppPLicPurposeId(Long appPLicPurposeId) {
		this.appPLicPurposeId = appPLicPurposeId;
	}

	public Long getKhrsDist() {
		return khrsDist;
	}

	public void setKhrsDist(Long khrsDist) {
		this.khrsDist = khrsDist;
	}

	public Long getKhrsDevPlan() {
		return khrsDevPlan;
	}

	public void setKhrsDevPlan(Long khrsDevPlan) {
		this.khrsDevPlan = khrsDevPlan;
	}

	public Long getKhrsZone() {
		return khrsZone;
	}

	public void setKhrsZone(Long khrsZone) {
		this.khrsZone = khrsZone;
	}

	public Long getKhrsSec() {
		return khrsSec;
	}

	public void setKhrsSec(Long khrsSec) {
		this.khrsSec = khrsSec;
	}

	public String getKhrsThesil() {
		return khrsThesil;
	}

	public void setKhrsThesil(String khrsThesil) {
		this.khrsThesil = khrsThesil;
	}

	public String getKhrsRevEst() {
		return khrsRevEst;
	}

	public void setKhrsRevEst(String khrsRevEst) {
		this.khrsRevEst = khrsRevEst;
	}

	public String getKhrsHadbast() {
		return khrsHadbast;
	}

	public void setKhrsHadbast(String khrsHadbast) {
		this.khrsHadbast = khrsHadbast;
	}

	public String getKhrsMustil() {
		return khrsMustil;
	}

	public void setKhrsMustil(String khrsMustil) {
		this.khrsMustil = khrsMustil;
	}

	public String getKhrsKilla() {
		return khrsKilla;
	}

	public void setKhrsKilla(String khrsKilla) {
		this.khrsKilla = khrsKilla;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getTcpNameOfLO() {
		return tcpNameOfLO;
	}

	public void setTcpNameOfLO(String tcpNameOfLO) {
		this.tcpNameOfLO = tcpNameOfLO;
	}

	public String getKhrsDevCollab() {
		return khrsDevCollab;
	}

	public void setKhrsDevCollab(String khrsDevCollab) {
		this.khrsDevCollab = khrsDevCollab;
	}

	public String getKhrsDevComName() {
		return khrsDevComName;
	}

	public void setKhrsDevComName(String khrsDevComName) {
		this.khrsDevComName = khrsDevComName;
	}

	public Date getKhrsColabRegDate() {
		return khrsColabRegDate;
	}

	public void setKhrsColabRegDate(Date khrsColabRegDate) {
		this.khrsColabRegDate = khrsColabRegDate;
	}

	public String getKhrsCollabAggRevo() {
		return khrsCollabAggRevo;
	}

	public void setKhrsCollabAggRevo(String khrsCollabAggRevo) {
		this.khrsCollabAggRevo = khrsCollabAggRevo;
	}

	public String getKhrsAuthSignLO() {
		return khrsAuthSignLO;
	}

	public void setKhrsAuthSignLO(String khrsAuthSignLO) {
		this.khrsAuthSignLO = khrsAuthSignLO;
	}

	public String getKhrsAurSignDev() {
		return khrsAurSignDev;
	}

	public void setKhrsAurSignDev(String khrsAurSignDev) {
		this.khrsAurSignDev = khrsAurSignDev;
	}

	public String getKhrsRegAuth() {
		return khrsRegAuth;
	}

	public void setKhrsRegAuth(String khrsRegAuth) {
		this.khrsRegAuth = khrsRegAuth;
	}

	public Long getKhrsLandTypeId() {
		return khrsLandTypeId;
	}

	public void setKhrsLandTypeId(Long khrsLandTypeId) {
		this.khrsLandTypeId = khrsLandTypeId;
	}

	public String getCiConsType() {
		return ciConsType;
	}

	public void setCiConsType(String ciConsType) {
		this.ciConsType = ciConsType;
	}

	public Long getCiNonConsTypeId() {
		return ciNonConsTypeId;
	}

	public void setCiNonConsTypeId(Long ciNonConsTypeId) {
		this.ciNonConsTypeId = ciNonConsTypeId;
	}

	public BigDecimal getCiConsTypeKanal() {
		return ciConsTypeKanal;
	}

	public void setCiConsTypeKanal(BigDecimal ciConsTypeKanal) {
		this.ciConsTypeKanal = ciConsTypeKanal;
	}

	public BigDecimal getCiConsTypeKanalTot() {
		return ciConsTypeKanalTot;
	}

	public void setCiConsTypeKanalTot(BigDecimal ciConsTypeKanalTot) {
		this.ciConsTypeKanalTot = ciConsTypeKanalTot;
	}

	public BigDecimal getCiConsTypeMarla() {
		return ciConsTypeMarla;
	}

	public void setCiConsTypeMarla(BigDecimal ciConsTypeMarla) {
		this.ciConsTypeMarla = ciConsTypeMarla;
	}

	public BigDecimal getCiConsTypeMarlaTot() {
		return ciConsTypeMarlaTot;
	}

	public void setCiConsTypeMarlaTot(BigDecimal ciConsTypeMarlaTot) {
		this.ciConsTypeMarlaTot = ciConsTypeMarlaTot;
	}

	public BigDecimal getCiConsTypeSarsai() {
		return ciConsTypeSarsai;
	}

	public void setCiConsTypeSarsai(BigDecimal ciConsTypeSarsai) {
		this.ciConsTypeSarsai = ciConsTypeSarsai;
	}

	public BigDecimal getCiConsTypeSarsaiTot() {
		return ciConsTypeSarsaiTot;
	}

	public void setCiConsTypeSarsaiTot(BigDecimal ciConsTypeSarsaiTot) {
		this.ciConsTypeSarsaiTot = ciConsTypeSarsaiTot;
	}

	public BigDecimal getCiConsTypeTotArea() {
		return ciConsTypeTotArea;
	}

	public void setCiConsTypeTotArea(BigDecimal ciConsTypeTotArea) {
		this.ciConsTypeTotArea = ciConsTypeTotArea;
	}

	public String getCiAquStatus() {
		return ciAquStatus;
	}

	public void setCiAquStatus(String ciAquStatus) {
		this.ciAquStatus = ciAquStatus;
	}

	public BigDecimal getCiTotArea() {
		return ciTotArea;
	}

	public void setCiTotArea(BigDecimal ciTotArea) {
		this.ciTotArea = ciTotArea;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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

	public BigInteger getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(BigInteger modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getLgIpMac() {
		return lgIpMac;
	}

	public void setLgIpMac(String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	public String getDraftFlag() {
		return draftFlag;
	}

	public void setDraftFlag(String draftFlag) {
		this.draftFlag = draftFlag;
	}
	
	public BigDecimal getScrutinyFee() {
		return scrutinyFee;
	}

	public void setScrutinyFee(BigDecimal scrutinyFee) {
		this.scrutinyFee = scrutinyFee;
	}

	public BigDecimal getLicenseFee() {
		return licenseFee;
	}

	public void setLicenseFee(BigDecimal licenseFee) {
		this.licenseFee = licenseFee;
	}

	public BigDecimal getTotalFees() {
		return totalFees;
	}

	public void setTotalFees(BigDecimal totalFees) {
		this.totalFees = totalFees;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public String getTransData() {
		return transData;
	}

	public void setTransData(String transData) {
		this.transData = transData;
	}

	public String getDiaryNo() {
		return diaryNo;
	}

	public void setDiaryNo(String diaryNo) {
		this.diaryNo = diaryNo;
	}

	public String getCaseNno() {
		return caseNno;
	}

	public void setCaseNno(String caseNno) {
		this.caseNno = caseNno;
	}

	public String getApplicationNoEService() {
		return applicationNoEService;
	}

	public void setApplicationNoEService(String applicationNoEService) {
		this.applicationNoEService = applicationNoEService;
	}

	public String getLoaDcRemark() {
		return loaDcRemark;
	}

	public void setLoaDcRemark(String loaDcRemark) {
		this.loaDcRemark = loaDcRemark;
	}

	public Long getTcpLicMstrHistId() {
		return tcpLicMstrHistId;
	}

	public void setTcpLicMstrHistId(Long tcpLicMstrHistId) {
		this.tcpLicMstrHistId = tcpLicMstrHistId;
	}

	public List<LicenseApplicationLandDetHist> getLicenseApplicationLandDetList() {
		return licenseApplicationLandDetList;
	}

	public void setLicenseApplicationLandDetList(List<LicenseApplicationLandDetHist> licenseApplicationLandDetList) {
		this.licenseApplicationLandDetList = licenseApplicationLandDetList;
	}

	public List<LicenseApplicationPurposeTypeDetHistEntity> getLicenseApplicationPurposeTypeDetEntityList() {
		return licenseApplicationPurposeTypeDetEntityList;
	}

	public void setLicenseApplicationPurposeTypeDetEntityList(
			List<LicenseApplicationPurposeTypeDetHistEntity> licenseApplicationPurposeTypeDetEntityList) {
		this.licenseApplicationPurposeTypeDetEntityList = licenseApplicationPurposeTypeDetEntityList;
	}

	public LicenseApplicationLandScheduleHist getLicenseApplicationLandSchedule() {
		return licenseApplicationLandSchedule;
	}

	public void setLicenseApplicationLandSchedule(LicenseApplicationLandScheduleHist licenseApplicationLandSchedule) {
		this.licenseApplicationLandSchedule = licenseApplicationLandSchedule;
	}

	public List<LicenseApplicationLandAcquisitionDetHistEntity> getLicenseApplicationLandAcquisitionDetEntityList() {
		return licenseApplicationLandAcquisitionDetEntityList;
	}

	public void setLicenseApplicationLandAcquisitionDetEntityList(
			List<LicenseApplicationLandAcquisitionDetHistEntity> licenseApplicationLandAcquisitionDetEntityList) {
		this.licenseApplicationLandAcquisitionDetEntityList = licenseApplicationLandAcquisitionDetEntityList;
	}

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
	}

	public String[] getPkValues() {
		return new String[] { "NL", "TB_BPMS_LIC_MSTR_HIST", "H_TCP_LIC_MSTR_ID" };
	}

}
