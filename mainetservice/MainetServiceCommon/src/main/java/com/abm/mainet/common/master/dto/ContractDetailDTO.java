package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author apurva.salgaonkar
 *
 */
public class ContractDetailDTO implements Serializable {

	private static final long serialVersionUID = -3350954284865356030L;

	private long contdId;
	private ContractMastDTO contId;
	private Date contFromDate;
	private Date contToDate;
	private Double contAmount;
	private Double contSecAmount;
	private String contSecRecNo;
	private Date contSecRecDate;
	private Long contPayPeriod;
	private Long contInstallmentPeriod;
	private String contEntryType;
	private String contdActive;
	private Long orgId;
	private Long createdBy;
	private int langId;
	private Date lmodDate;
	private Long updatedBy;
	private Date updatedDate;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMac;
	@JsonIgnore
	@Size(max = 100)
	private String lgIpMacUpd;
	private Long contDefectLiabilityPer;
	private Long contDefectLiabilityUnit;
	private Long contTimeExtPer;
	private Long contTimeExtUnit;
	private Long contTimeExtEmpId;
	private Long contractId;

	private Long contToPeriod;
	private BigDecimal contAddPerSecurityDet;
	private BigDecimal contOtherDepositDet;
	private BigDecimal contractAmt;

	private Long contDepTyp;
	private Long contDepBankID;
	private Long contDepModeId;
	private Date contDepDueDt;
	private String contDepPrtcl;
	private Long contToPeriodUnit;
	private String defectLiaPeriodUnitDesc;
	private Date bgValidDt;
	private String agreementAmount;
	
	public Date getBgValidDt() {
		return bgValidDt;
	}

	public void setBgValidDt(Date bgValidDt) {
		this.bgValidDt = bgValidDt;
	}

	public String getDefectLiaPeriodUnitDesc() {
		return defectLiaPeriodUnitDesc;
	}

	public void setDefectLiaPeriodUnitDesc(String defectLiaPeriodUnitDesc) {
		this.defectLiaPeriodUnitDesc = defectLiaPeriodUnitDesc;
	}

	public Long getContToPeriodUnit() {
		return contToPeriodUnit;
	}

	public void setContToPeriodUnit(Long contToPeriodUnit) {
		this.contToPeriodUnit = contToPeriodUnit;
	}

	public Long getContDepTyp() {
		return contDepTyp;
	}

	public void setContDepTyp(Long contDepTyp) {
		this.contDepTyp = contDepTyp;
	}

	public Long getContDepBankID() {
		return contDepBankID;
	}

	public void setContDepBankID(Long contDepBankID) {
		this.contDepBankID = contDepBankID;
	}

	public Long getContDepModeId() {
		return contDepModeId;
	}

	public void setContDepModeId(Long contDepModeId) {
		this.contDepModeId = contDepModeId;
	}

	public Date getContDepDueDt() {
		return contDepDueDt;
	}

	public void setContDepDueDt(Date contDepDueDt) {
		this.contDepDueDt = contDepDueDt;
	}

	public String getContDepPrtcl() {
		return contDepPrtcl;
	}

	public void setContDepPrtcl(String contDepPrtcl) {
		this.contDepPrtcl = contDepPrtcl;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}

	/**
	 * @return the contdId
	 */
	public long getContdId() {
		return contdId;
	}

	/**
	 * @param contdId the contdId to set
	 */
	public void setContdId(final long contdId) {
		this.contdId = contdId;
	}

	/**
	 * @return the contId
	 */
	public ContractMastDTO getContId() {
		return contId;
	}

	/**
	 * @param contId the contId to set
	 */
	public void setContId(final ContractMastDTO contId) {
		this.contId = contId;
	}

	/**
	 * @return the contFromDate
	 */
	public Date getContFromDate() {
		return contFromDate;
	}

	/**
	 * @param contFromDate the contFromDate to set
	 */
	public void setContFromDate(final Date contFromDate) {
		this.contFromDate = contFromDate;
	}

	/**
	 * @return the contToDate
	 */
	public Date getContToDate() {
		return contToDate;
	}

	/**
	 * @param contToDate the contToDate to set
	 */
	public void setContToDate(final Date contToDate) {
		this.contToDate = contToDate;
	}

	/**
	 * @return the contAmount
	 */
	public Double getContAmount() {
		return contAmount;
	}

	/**
	 * @param contAmount the contAmount to set
	 */
	public void setContAmount(final Double contAmount) {
		this.contAmount = contAmount;
	}

	/**
	 * @return the contSecAmount
	 */
	public Double getContSecAmount() {
		return contSecAmount;
	}

	/**
	 * @param contSecAmount the contSecAmount to set
	 */
	public void setContSecAmount(final Double contSecAmount) {
		this.contSecAmount = contSecAmount;
	}

	/**
	 * @return the contSecRecNo
	 */
	public String getContSecRecNo() {
		return contSecRecNo;
	}

	/**
	 * @param contSecRecNo the contSecRecNo to set
	 */
	public void setContSecRecNo(final String contSecRecNo) {
		this.contSecRecNo = contSecRecNo;
	}

	/**
	 * @return the contSecRecDate
	 */
	public Date getContSecRecDate() {
		return contSecRecDate;
	}

	/**
	 * @param contSecRecDate the contSecRecDate to set
	 */
	public void setContSecRecDate(final Date contSecRecDate) {
		this.contSecRecDate = contSecRecDate;
	}

	/**
	 * @return the contPayPeriod
	 */
	public Long getContPayPeriod() {
		return contPayPeriod;
	}

	/**
	 * @param contPayPeriod the contPayPeriod to set
	 */
	public void setContPayPeriod(final Long contPayPeriod) {
		this.contPayPeriod = contPayPeriod;
	}

	/**
	 * @return the contInstallmentPeriod
	 */
	public Long getContInstallmentPeriod() {
		return contInstallmentPeriod;
	}

	/**
	 * @param contInstallmentPeriod the contInstallmentPeriod to set
	 */
	public void setContInstallmentPeriod(final Long contInstallmentPeriod) {
		this.contInstallmentPeriod = contInstallmentPeriod;
	}

	/**
	 * @return the contEntryType
	 */
	public String getContEntryType() {
		return contEntryType;
	}

	/**
	 * @param contEntryType the contEntryType to set
	 */
	public void setContEntryType(final String contEntryType) {
		this.contEntryType = contEntryType;
	}

	/**
	 * @return the contdActive
	 */
	public String getContdActive() {
		return contdActive;
	}

	/**
	 * @param contdActive the contdActive to set
	 */
	public void setContdActive(final String contdActive) {
		this.contdActive = contdActive;
	}

	/**
	 * @return the orgId
	 */
	public Long getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(final Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdBy
	 */
	public Long getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(final Long createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the langId
	 */
	public int getLangId() {
		return langId;
	}

	/**
	 * @param langId the langId to set
	 */
	public void setLangId(final int langId) {
		this.langId = langId;
	}

	/**
	 * @return the lmodDate
	 */
	public Date getLmodDate() {
		return lmodDate;
	}

	/**
	 * @param lmodDate the lmodDate to set
	 */
	public void setLmodDate(final Date lmodDate) {
		this.lmodDate = lmodDate;
	}

	/**
	 * @return the updatedBy
	 */
	public Long getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(final Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the updatedDate
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}

	/**
	 * @param updatedDate the updatedDate to set
	 */
	public void setUpdatedDate(final Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	/**
	 * @return the lgIpMac
	 */
	public String getLgIpMac() {
		return lgIpMac;
	}

	/**
	 * @param lgIpMac the lgIpMac to set
	 */
	public void setLgIpMac(final String lgIpMac) {
		this.lgIpMac = lgIpMac;
	}

	/**
	 * @return the lgIpMacUpd
	 */
	public String getLgIpMacUpd() {
		return lgIpMacUpd;
	}

	/**
	 * @param lgIpMacUpd the lgIpMacUpd to set
	 */
	public void setLgIpMacUpd(final String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	public Long getContDefectLiabilityPer() {
		return contDefectLiabilityPer;
	}

	public void setContDefectLiabilityPer(Long contDefectLiabilityPer) {
		this.contDefectLiabilityPer = contDefectLiabilityPer;
	}

	public Long getContDefectLiabilityUnit() {
		return contDefectLiabilityUnit;
	}

	public void setContDefectLiabilityUnit(Long contDefectLiabilityUnit) {
		this.contDefectLiabilityUnit = contDefectLiabilityUnit;
	}

	public Long getContTimeExtPer() {
		return contTimeExtPer;
	}

	public void setContTimeExtPer(Long contTimeExtPer) {
		this.contTimeExtPer = contTimeExtPer;
	}

	public Long getContTimeExtUnit() {
		return contTimeExtUnit;
	}

	public void setContTimeExtUnit(Long contTimeExtUnit) {
		this.contTimeExtUnit = contTimeExtUnit;
	}

	public Long getContTimeExtEmpId() {
		return contTimeExtEmpId;
	}

	public void setContTimeExtEmpId(Long contTimeExtEmpId) {
		this.contTimeExtEmpId = contTimeExtEmpId;
	}

	public Long getContToPeriod() {
		return contToPeriod;
	}

	public void setContToPeriod(Long contToPeriod) {
		this.contToPeriod = contToPeriod;
	}

	public BigDecimal getContAddPerSecurityDet() {
		return contAddPerSecurityDet;
	}

	public void setContAddPerSecurityDet(BigDecimal contAddPerSecurityDet) {
		this.contAddPerSecurityDet = contAddPerSecurityDet;
	}

	public BigDecimal getContOtherDepositDet() {
		return contOtherDepositDet;
	}

	public void setContOtherDepositDet(BigDecimal contOtherDepositDet) {
		this.contOtherDepositDet = contOtherDepositDet;
	}

	public BigDecimal getContractAmt() {
		return contractAmt;
	}

	public void setContractAmt(BigDecimal contractAmt) {
		this.contractAmt = contractAmt;
	}

	public String getAgreementAmount() {
		return agreementAmount;
	}

	public void setAgreementAmount(String agreementAmount) {
		this.agreementAmount = agreementAmount;
	}

}
