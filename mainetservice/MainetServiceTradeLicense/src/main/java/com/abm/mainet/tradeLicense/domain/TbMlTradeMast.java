package com.abm.mainet.tradeLicense.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Jugnu.Pandey
 * @since 07 Dec 2018
 */
@Entity
@Table(name = "TB_ML_TRADE_MAST")
public class TbMlTradeMast implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4589206275885772695L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")

	@Column(name = "TRD_ID", nullable = false)
	private Long trdId;

	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

	@Column(name = "TRD_TYPE", nullable = false)
	private Long trdType;

	@Column(name = "TRD_LICTYPE", nullable = false)
	private Long trdLictype;

	@Column(name = "TRD_BUSNM", nullable = false, length = 50)
	private String trdBusnm;

	@Column(name = "TRD_BUSADD", nullable = false, length = 200)
	private String trdBusadd;

	@Column(name = "TRD_OLDLICNO", nullable = false, length = 100)
	private String trdOldlicno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICFROM_DATE")
	private Date trdLicfromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICTO_DATE")
	private Date trdLictoDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "agreement_from_date")
	private Date agreementFromDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "agreement_to_date")
	private Date agreementToDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRD_LICISDATE")
	private Date trdLicisdate;

	@Column(name = "TRD_WARD1", nullable = false)
	private Long trdWard1;

	@Column(name = "TRD_WARD2")
	private Long trdWard2;

	@Column(name = "TRD_WARD3")
	private Long trdWard3;

	@Column(name = "TRD_WARD4")
	private Long trdWard4;

	@Column(name = "TRD_WARD5")
	private Long trdWard5;

	@Column(name = "TRD_FTYPE", nullable = false)
	private Long trdFtype;

	@Column(name = "TRD_FAREA", nullable = false, length = 20)
	private String trdFarea;

	@Column(name = "TRD_ETY", length = 2)
	private String trdEty;

	@Column(name = "TRD_STATUS", length = 10)
	private Long trdStatus;

	@Column(name = "CAN_REMARK")
	private String canRemark;

	@Column(name = "TRD_LICNO", length = 2)
	private String trdLicno;

	@Column(name = "TRD_EWARD1")
	private Long trdEWard1;

	@Column(name = "TRD_EWARD2")
	private Long trdEWard2;

	@Column(name = "TRD_EWARD3")
	private Long trdEWard3;

	@Column(name = "TRD_EWARD4")
	private Long trdEWard4;

	@Column(name = "TRD_EWARD5")
	private Long trdEWard5;

	@Column(name = "ORGID", nullable = false)
	private Long orgid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private Long createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "LG_IP_MAC", nullable = false, length = 100)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100)
	private String lgIpMacUpd;

	@Column(name = "PM_PROPNO")
	private String pmPropNo;

	@Column(name = "TRD_OWNERNM", length = 100)
	private String trdOwnerNm;

	@Column(name = "TRD_FLAT_NO", length = 250)
	private String trdFlatNo;

	@Column(name = "TRD_MNTINC", precision = 10, scale = 2)
	private BigDecimal trdMonthlyIncome;

	@Column(name = "TRD_REG")
	private String trdReg;

	@Column(name = "TRD_INVAMT", precision = 10, scale = 2)
	private BigDecimal trdInvestmentAmount;

	@Column(name = "TRD_EXP")
	private String trdExperience;

	@Column(name = "TRD_REGNO")
	private String trdRegNo;

	@Column(name = "TRD_BANKID")
	private Long trdBankId;

	@Column(name = "TRD_ACCTNO ")
	private Long trdAcctNo;

	@Column(name = "TRD_MODE")
	private Long trdMode;

	@Column(name = "TRD_COMODITY")
	private String trdComodity;

	@Column(name = "TRD_ROUNDARE")
	private String trdRoundArea;

	@Column(name = "TRD_LOCID")
	private Long trdLocId;

	@Column(name = "TRD_RATIONNO")
	private String trdRationNo;

	@Column(name = "TRD_RATIONAUTH")
	private String trdRationAuth;

	@Column(name = "TRD_OTHCAT")
	private Long trdOthCatType;

	@Column(name = "DUE_ON_PROPERTY")
	private BigDecimal dueOnProperty;

	@Column(name = "DP_ZONE")
	private Long dpZone;

	@Column(name = "BUILDING_PERMISSION_NO")
	private String buldingPermissionNo;

	@Column(name = "FIRE_NOC_APPLICABLE", length = 1)
	private String fireNonApplicable;

	@Column(name = "TRD_FRE_NOC")
	private String fireNOCNo;

	@Column(name = "TRD_CANCEL_REASON")
	private String cancelReason;

	@Column(name = "TRD_CANCEL_BY")
	private String cancelBy;

	@Column(name = "CANCEL_DATE")
	private Date cancelDate;
	
	@Column(name = "Trans_Mode_TYPE")
	private String transferMode;
	
	@Column(name = "LAND_TYPE")
	private Long landType;

	@Column(name = "PM_SURVEY_NUMBER")
	private String surveyNumber;

	@Column(name = "PM_KHATA_NO")
	private String partNo;

	@Column(name = "PM_PLOT_NO")
	private String plotNo;

	@Column(name = "PM_ROAD_TYPE")
	private Long propLvlRoadType;

	@Column(name = "PM_AREA_NAME")
	private String villageName;
	
	@Column(name = "PM_PLOT_AREA")
	private Double assPlotArea;
	
	@Column(name = "TRD_REN_CYCLE")
	private Long renewCycle;
	
	@Column(name = "PROP_OWNER_ADDRS")
	private String propertyOwnerAddress;
	
	@Column(name = "DUE_ON_WATER")
	private BigDecimal dueOnWater;
	
	@Column(name = "PINCODE", precision = 6, scale = 0, nullable = true)
	private Long pincode;
	
	@Column(name = "LAND_MARK",length = 250,nullable = true)
	private String landMark;
	
	@Column(name = "DIRECTORS_NAME_ADDRES", length = 500,nullable = true)
	private String directorsNameAndAdd;
	
	@Column(name = "TRD_BUS_NATURE", nullable = true, length = 250)
	private String bussinessNature;
	
	@Column(name = "SOURCE",length = 10, nullable = true)
	private String source;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMlItemDetail> itemDetails = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMlOwnerDetail> ownerDetails = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMlOwnerFamilyDetail> ownerFamilyDetails = new ArrayList<>();

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "masterTradeId", cascade = CascadeType.ALL)
	private List<TbMtlInspectionReg> inspectionDetails = new ArrayList<>();

	public Long getTrdId() {
		return trdId;
	}

	public void setTrdId(Long trdId) {
		this.trdId = trdId;
	}

	public Long getTrdType() {
		return trdType;
	}

	public void setTrdType(Long trdType) {
		this.trdType = trdType;
	}

	public Long getTrdLictype() {
		return trdLictype;
	}

	public void setTrdLictype(Long trdLictype) {
		this.trdLictype = trdLictype;
	}

	public String getTrdBusnm() {
		return trdBusnm;
	}

	public void setTrdBusnm(String trdBusnm) {
		this.trdBusnm = trdBusnm;
	}

	public String getTrdBusadd() {
		return trdBusadd;
	}

	public void setTrdBusadd(String trdBusadd) {
		this.trdBusadd = trdBusadd;
	}

	public String getTrdOldlicno() {
		return trdOldlicno;
	}

	public void setTrdOldlicno(String trdOldlicno) {
		this.trdOldlicno = trdOldlicno;
	}

	public Date getTrdLicfromDate() {
		return trdLicfromDate;
	}

	public void setTrdLicfromDate(Date trdLicfromDate) {
		this.trdLicfromDate = trdLicfromDate;
	}

	public Date getTrdLictoDate() {
		return trdLictoDate;
	}

	public void setTrdLictoDate(Date trdLictoDate) {
		this.trdLictoDate = trdLictoDate;
	}

	public Date getTrdLicisdate() {
		return trdLicisdate;
	}

	public void setTrdLicisdate(Date trdLicisdate) {
		this.trdLicisdate = trdLicisdate;
	}

	public Long getTrdWard1() {
		return trdWard1;
	}

	public void setTrdWard1(Long trdWard1) {
		this.trdWard1 = trdWard1;
	}

	public Long getTrdWard2() {
		return trdWard2;
	}

	public void setTrdWard2(Long trdWard2) {
		this.trdWard2 = trdWard2;
	}

	public Long getTrdWard3() {
		return trdWard3;
	}

	public void setTrdWard3(Long trdWard3) {
		this.trdWard3 = trdWard3;
	}

	public Long getTrdWard4() {
		return trdWard4;
	}

	public void setTrdWard4(Long trdWard4) {
		this.trdWard4 = trdWard4;
	}

	public Long getTrdWard5() {
		return trdWard5;
	}

	public void setTrdWard5(Long trdWard5) {
		this.trdWard5 = trdWard5;
	}

	public Long getTrdFtype() {
		return trdFtype;
	}

	public void setTrdFtype(Long trdFtype) {
		this.trdFtype = trdFtype;
	}

	public String getTrdFarea() {
		return trdFarea;
	}

	public void setTrdFarea(String trdFarea) {
		this.trdFarea = trdFarea;
	}

	public String getTrdLicno() {
		return trdLicno;
	}

	public void setTrdLicno(String trdLicno) {
		this.trdLicno = trdLicno;
	}

	public String getTrdEty() {
		return trdEty;
	}

	public void setTrdEty(String trdEty) {
		this.trdEty = trdEty;
	}

	public Long getOrgid() {
		return orgid;
	}

	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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

	public List<TbMlItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<TbMlItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public List<TbMlOwnerFamilyDetail> getOwnerFamilyDetails() {
		return ownerFamilyDetails;
	}

	public void setOwnerFamilyDetails(List<TbMlOwnerFamilyDetail> ownerFamilyDetails) {
		this.ownerFamilyDetails = ownerFamilyDetails;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public List<TbMlOwnerDetail> getOwnerDetails() {
		return ownerDetails;
	}

	public void setOwnerDetails(List<TbMlOwnerDetail> ownerDetails) {
		this.ownerDetails = ownerDetails;
	}
	

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getLandMark() {
		return landMark;
	}

	public void setLandMark(String landMark) {
		this.landMark = landMark;
	}

	public String getDirectorsNameAndAdd() {
		return directorsNameAndAdd;
	}

	public void setDirectorsNameAndAdd(String directorsNameAndAdd) {
		this.directorsNameAndAdd = directorsNameAndAdd;
	}


	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getPkValues() {
		return new String[] { "ML", "TB_ML_TRADE_MAST", "TRD_ID" };
	}

	public String getPmPropNo() {
		return pmPropNo;
	}

	public void setPmPropNo(String pmPropNo) {
		this.pmPropNo = pmPropNo;
	}

	public String getTrdOwnerNm() {
		return trdOwnerNm;
	}

	public void setTrdOwnerNm(String trdOwnerNm) {
		this.trdOwnerNm = trdOwnerNm;
	}

	public String getTrdFlatNo() {
		return trdFlatNo;
	}

	public void setTrdFlatNo(String trdFlatNo) {
		this.trdFlatNo = trdFlatNo;
	}

	public Date getAgreementFromDate() {
		return agreementFromDate;
	}

	public void setAgreementFromDate(Date agreementFromDate) {
		this.agreementFromDate = agreementFromDate;
	}

	public Date getAgreementToDate() {
		return agreementToDate;
	}

	public void setAgreementToDate(Date agreementToDate) {
		this.agreementToDate = agreementToDate;
	}

	public Long getTrdStatus() {
		return trdStatus;
	}

	public void setTrdStatus(Long trdStatus) {
		this.trdStatus = trdStatus;
	}

	public String getCanRemark() {
		return canRemark;
	}

	public void setCanRemark(String canRemark) {
		this.canRemark = canRemark;
	}

	public BigDecimal getTrdMonthlyIncome() {
		return trdMonthlyIncome;
	}

	public void setTrdMonthlyIncome(BigDecimal trdMonthlyIncome) {
		this.trdMonthlyIncome = trdMonthlyIncome;
	}

	public String getTrdReg() {
		return trdReg;
	}

	public void setTrdReg(String trdReg) {
		this.trdReg = trdReg;
	}

	public BigDecimal getTrdInvestmentAmount() {
		return trdInvestmentAmount;
	}

	public void setTrdInvestmentAmount(BigDecimal trdInvestmentAmount) {
		this.trdInvestmentAmount = trdInvestmentAmount;
	}

	public String getTrdExperience() {
		return trdExperience;
	}

	public void setTrdExperience(String trdExperience) {
		this.trdExperience = trdExperience;
	}

	public String getTrdRegNo() {
		return trdRegNo;
	}

	public void setTrdRegNo(String trdRegNo) {
		this.trdRegNo = trdRegNo;
	}

	public Long getTrdBankId() {
		return trdBankId;
	}

	public void setTrdBankId(Long trdBankId) {
		this.trdBankId = trdBankId;
	}

	public Long getTrdAcctNo() {
		return trdAcctNo;
	}

	public void setTrdAcctNo(Long trdAcctNo) {
		this.trdAcctNo = trdAcctNo;
	}

	public Long getTrdMode() {
		return trdMode;
	}

	public void setTrdMode(Long trdMode) {
		this.trdMode = trdMode;
	}

	public String getTrdComodity() {
		return trdComodity;
	}

	public void setTrdComodity(String trdComodity) {
		this.trdComodity = trdComodity;
	}

	public String getTrdRoundArea() {
		return trdRoundArea;
	}

	public void setTrdRoundArea(String trdRoundArea) {
		this.trdRoundArea = trdRoundArea;
	}

	public Long getTrdLocId() {
		return trdLocId;
	}

	public void setTrdLocId(Long trdLocId) {
		this.trdLocId = trdLocId;
	}

	public String getTrdRationNo() {
		return trdRationNo;
	}

	public void setTrdRationNo(String trdRationNo) {
		this.trdRationNo = trdRationNo;
	}

	public String getTrdRationAuth() {
		return trdRationAuth;
	}

	public void setTrdRationAuth(String trdRationAuth) {
		this.trdRationAuth = trdRationAuth;
	}

	public Long getTrdEWard1() {
		return trdEWard1;
	}

	public void setTrdEWard1(Long trdEWard1) {
		this.trdEWard1 = trdEWard1;
	}

	public Long getTrdEWard2() {
		return trdEWard2;
	}

	public void setTrdEWard2(Long trdEWard2) {
		this.trdEWard2 = trdEWard2;
	}

	public Long getTrdEWard3() {
		return trdEWard3;
	}

	public void setTrdEWard3(Long trdEWard3) {
		this.trdEWard3 = trdEWard3;
	}

	public Long getTrdEWard4() {
		return trdEWard4;
	}

	public void setTrdEWard4(Long trdEWard4) {
		this.trdEWard4 = trdEWard4;
	}

	public Long getTrdEWard5() {
		return trdEWard5;
	}

	public void setTrdEWard5(Long trdEWard5) {
		this.trdEWard5 = trdEWard5;
	}

	public Long getTrdOthCatType() {
		return trdOthCatType;
	}

	public void setTrdOthCatType(Long trdOthCatType) {
		this.trdOthCatType = trdOthCatType;
	}

	public BigDecimal getDueOnProperty() {
		return dueOnProperty;
	}

	public void setDueOnProperty(BigDecimal dueOnProperty) {
		this.dueOnProperty = dueOnProperty;
	}

	public Long getDpZone() {
		return dpZone;
	}

	public void setDpZone(Long dpZone) {
		this.dpZone = dpZone;
	}

	public String getFireNonApplicable() {
		return fireNonApplicable;
	}

	public void setFireNonApplicable(String fireNonApplicable) {
		this.fireNonApplicable = fireNonApplicable;
	}

	public String getBuldingPermissionNo() {
		return buldingPermissionNo;
	}

	public void setBuldingPermissionNo(String buldingPermissionNo) {
		this.buldingPermissionNo = buldingPermissionNo;
	}

	public String getFireNOCNo() {
		return fireNOCNo;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(String cancelBy) {
		this.cancelBy = cancelBy;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public void setFireNOCNo(String fireNOCNo) {
		this.fireNOCNo = fireNOCNo;
	}

	public List<TbMtlInspectionReg> getInspectionDetails() {
		return inspectionDetails;
	}

	public void setInspectionDetails(List<TbMtlInspectionReg> inspectionDetails) {
		this.inspectionDetails = inspectionDetails;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	public Long getLandType() {
		return landType;
	}

	public void setLandType(Long landType) {
		this.landType = landType;
	}

	public String getSurveyNumber() {
		return surveyNumber;
	}

	public void setSurveyNumber(String surveyNumber) {
		this.surveyNumber = surveyNumber;
	}

	public String getPartNo() {
		return partNo;
	}

	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}

	public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public Long getPropLvlRoadType() {
		return propLvlRoadType;
	}

	public void setPropLvlRoadType(Long propLvlRoadType) {
		this.propLvlRoadType = propLvlRoadType;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Double getAssPlotArea() {
		return assPlotArea;
	}

	public void setAssPlotArea(Double assPlotArea) {
		this.assPlotArea = assPlotArea;
	}

	public Long getRenewCycle() {
		return renewCycle;
	}

	public void setRenewCycle(Long renewCycle) {
		this.renewCycle = renewCycle;
	}

	public String getPropertyOwnerAddress() {
		return propertyOwnerAddress;
	}

	public BigDecimal getDueOnWater() {
		return dueOnWater;
	}

	public void setPropertyOwnerAddress(String propertyOwnerAddress) {
		this.propertyOwnerAddress = propertyOwnerAddress;
	}

	public void setDueOnWater(BigDecimal dueOnWater) {
		this.dueOnWater = dueOnWater;
	}
	
	public String getBussinessNature() {
		return bussinessNature;
	}

	public void setBussinessNature(String bussinessNature) {
		this.bussinessNature = bussinessNature;
	}
	
	

}