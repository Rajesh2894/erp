/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author pooja.maske
 *
 */

@Entity
@Table(name = "Tb_Sfac_Credit_Info_Detail")
public class CreditInformationDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3479769797520571994L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CRD_ID", nullable = false)
	private Long crdId;

	@ManyToOne
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;
	
	

	@Column(name = "FINANCIAL_YEAR")
	private Long financialYear;

	@Column(name = "LOAN_APPLIED")
	private Long loanApplied;

	@Column(name = "AMT_OF_LOAN")
	private BigDecimal AmtOfLoanApplied;

	@Column(name = "PURPOSE_OF_LOAN")
	private String PurposeOfLoanApplied;

	@Column(name = "INSTITUTION_AVAILED")
	private String institutionAvailed;

	@Column(name = "LOAN_SANCTIONED")
	private BigDecimal LoanSanctioned;

	@Column(name = "LOAN_DISBURSED")
	private Long LOANDISBURSED;

	@Column(name = "LOAN_AMT_DISBURSED")
	private BigDecimal loanAmountDisbursed;

	@Column(name = "UTILIZATION_OF_LOAN")
	private String utilizationOfLoan;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

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

	/**
	 * @return the crdId
	 */
	public Long getCrdId() {
		return crdId;
	}

	/**
	 * @param crdId the crdId to set
	 */
	public void setCrdId(Long crdId) {
		this.crdId = crdId;
	}

	/**
	 * @return the financialYear
	 */
	public Long getFinancialYear() {
		return financialYear;
	}

	/**
	 * @param financialYear the financialYear to set
	 */
	public void setFinancialYear(Long financialYear) {
		this.financialYear = financialYear;
	}

	/**
	 * @return the loanApplied
	 */
	public Long getLoanApplied() {
		return loanApplied;
	}

	/**
	 * @param loanApplied the loanApplied to set
	 */
	public void setLoanApplied(Long loanApplied) {
		this.loanApplied = loanApplied;
	}

	/**
	 * @return the amtOfLoanApplied
	 */
	public BigDecimal getAmtOfLoanApplied() {
		return AmtOfLoanApplied;
	}

	/**
	 * @param amtOfLoanApplied the amtOfLoanApplied to set
	 */
	public void setAmtOfLoanApplied(BigDecimal amtOfLoanApplied) {
		AmtOfLoanApplied = amtOfLoanApplied;
	}

	/**
	 * @return the purposeOfLoanApplied
	 */
	public String getPurposeOfLoanApplied() {
		return PurposeOfLoanApplied;
	}

	/**
	 * @param purposeOfLoanApplied the purposeOfLoanApplied to set
	 */
	public void setPurposeOfLoanApplied(String purposeOfLoanApplied) {
		PurposeOfLoanApplied = purposeOfLoanApplied;
	}

	/**
	 * @return the institutionAvailed
	 */
	public String getInstitutionAvailed() {
		return institutionAvailed;
	}

	/**
	 * @param institutionAvailed the institutionAvailed to set
	 */
	public void setInstitutionAvailed(String institutionAvailed) {
		this.institutionAvailed = institutionAvailed;
	}

	/**
	 * @return the loanSanctioned
	 */
	public BigDecimal getLoanSanctioned() {
		return LoanSanctioned;
	}

	/**
	 * @param loanSanctioned the loanSanctioned to set
	 */
	public void setLoanSanctioned(BigDecimal loanSanctioned) {
		LoanSanctioned = loanSanctioned;
	}

	/**
	 * @return the lOANDISBURSED
	 */
	public Long getLOANDISBURSED() {
		return LOANDISBURSED;
	}

	/**
	 * @param lOANDISBURSED the lOANDISBURSED to set
	 */
	public void setLOANDISBURSED(Long lOANDISBURSED) {
		LOANDISBURSED = lOANDISBURSED;
	}

	/**
	 * @return the loanAmountDisbursed
	 */
	public BigDecimal getLoanAmountDisbursed() {
		return loanAmountDisbursed;
	}

	/**
	 * @param loanAmountDisbursed the loanAmountDisbursed to set
	 */
	public void setLoanAmountDisbursed(BigDecimal loanAmountDisbursed) {
		this.loanAmountDisbursed = loanAmountDisbursed;
	}

	/**
	 * @return the utilizationOfLoan
	 */
	public String getUtilizationOfLoan() {
		return utilizationOfLoan;
	}

	/**
	 * @param utilizationOfLoan the utilizationOfLoan to set
	 */
	public void setUtilizationOfLoan(String utilizationOfLoan) {
		this.utilizationOfLoan = utilizationOfLoan;
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
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
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
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
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
	public void setLgIpMac(String lgIpMac) {
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
	public void setLgIpMacUpd(String lgIpMacUpd) {
		this.lgIpMacUpd = lgIpMacUpd;
	}

	/**
	 * @return the fPOProfileMgmtMaster
	 */
	public FPOProfileManagementMaster getFPOProfileMgmtMaster() {
		return fpoProfileMgmtMaster;
	}

	/**
	 * @param fPOProfileMgmtMaster the fPOProfileMgmtMaster to set
	 */
	public void setFPOProfileMgmtMaster(FPOProfileManagementMaster fPOProfileMgmtMaster) {
		fpoProfileMgmtMaster = fPOProfileMgmtMaster;
	}

	public String[] getPkValues() {
		return new String[] { "SFAC", "Tb_Sfac_Credit_Info_Detail", "CRD_ID" };
	}

	
	
	

}
