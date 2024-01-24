package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author apurva.salgaonkar
 * @since 19 Jan 2017
 * @comment Storing contract details for Contract Creation,Contract
 *          renewal,contract revision
 */
@Entity
@Table(name = "TB_CONTRACT_DETAIL")
public class ContractDetailEntity implements Serializable {
	private static final long serialVersionUID = 1124650702691199684L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CONTD_ID", precision = 12, scale = 0, nullable = false)
	private long contdId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CONT_ID", nullable = false)
	private ContractMastEntity contId;

	@Column(name = "CONT_FROM_DATE", nullable = false)
	private Date contFromDate;

	@Column(name = "CONT_TO_DATE", nullable = false)
	private Date contToDate;

	@Column(name = "CONT_AMOUNT", precision = 20, scale = 2, nullable = true)
	private Double contAmount;

	@Column(name = "CONT_SEC_AMOUNT", precision = 20, scale = 2, nullable = true)
	private Double contSecAmount;

	@Column(name = "CONT_SEC_REC_NO", length = 20, nullable = true)
	private String contSecRecNo;

	@Column(name = "CONT_SEC_REC_DATE", nullable = true)
	private Date contSecRecDate;

	@Column(name = "CONT_PAY_PERIOD", precision = 12, scale = 0, nullable = true)
	private Long contPayPeriod;

	@Column(name = "CONT_INSTALLMENT_PERIOD", precision = 12, scale = 0, nullable = false)
	private Long contInstallmentPeriod;

	@Column(name = "CONT_ENTRY_TYPE", length = 2, nullable = false)
	private String contEntryType;

	@Column(name = "CONTD_ACTIVE", length = 1, nullable = false)
	private String contdActive;

	@Column(name = "CONT_DEFECTLIABILITYPER", nullable = true)
	private Long contDefectLiabilityPer;

	@Column(name = "CONT_DEFECTLIABILITYUNI", nullable = true)
	private Long contDefectLiabilityUnit;

	@Column(name = "CONT_TIMEEXTPER", nullable = true)
	private Long contTimeExtPer;

	@Column(name = "CONT_TIMEEXTUNIT", nullable = true)
	private Long contTimeExtUnit;

	@Column(name = "CONT_TIMEEXTEMPID", nullable = true)
	private Long contTimeExtEmpId;

	@Column(name = "CONT_PERIOD", nullable = false)
	private Long contToPeriod;

	@Column(name = "CONT_PRDUNIT", nullable = true)
	private Long contToPeriodUnit;

	@Column(name = "CONT_ADDPER_SECURITYDE", nullable = true)
	private BigDecimal contAddPerSecurityDet;

	@Column(name = "CONT_OTHER_DEPDET", nullable = true)
	private BigDecimal contOtherDepositDet;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	@Column(name = "CONT_DEPTYP", nullable = true)
	private Long contDepTyp;

	@Column(name = "CONT_DEP_BANKID", nullable = true)
	private Long contDepBankID;

	@Column(name = "CONT_DEP_MODEID", nullable = true)
	private Long contDepModeId;

	@Column(name = "CONT_DEP_DUEDT", nullable = true)
	private Date contDepDueDt;

	@Column(name = "CONT_DEP_PRTCL ", nullable = true)
	private String contDepPrtcl;
	
	@Column(name = "CONT_BG_DATE", nullable = true)
	private Date bgValidDt;

	public Date getBgValidDt() {
		return bgValidDt;
	}

	public void setBgValidDt(Date bgValidDt) {
		this.bgValidDt = bgValidDt;
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

	public Long getContToPeriodUnit() {
		return contToPeriodUnit;
	}

	public void setContToPeriodUnit(Long contToPeriodUnit) {
		this.contToPeriodUnit = contToPeriodUnit;
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

	public Long getContToPeriod() {
		return contToPeriod;
	}

	public void setContToPeriod(Long contToPeriod) {
		this.contToPeriod = contToPeriod;
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

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	public String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, MainetConstants.RnLDetailEntity.TB_CONTRACT,
				MainetConstants.RnLDetailEntity.CONTD_ID };
	}

	/**
	 * @return the contId
	 */
	public ContractMastEntity getContId() {
		return contId;
	}

	/**
	 * @param contId the contId to set
	 */
	public void setContId(final ContractMastEntity contId) {
		this.contId = contId;
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

}