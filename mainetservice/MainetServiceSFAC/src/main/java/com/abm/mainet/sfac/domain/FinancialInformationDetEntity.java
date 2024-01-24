/**
 * 
 */
package com.abm.mainet.sfac.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
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
@Table(name = "Tb_Sfac_Financial_Info_Detail")
public class FinancialInformationDetEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8208588856231010739L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "FIN_ID", nullable = false)
	private Long finId;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "FPM_ID", referencedColumnName = "FPM_ID")
	private FPOProfileManagementMaster fpoProfileMgmtMaster;

	@Column(name = "FINANCIAL_YEAR")
	private Long financialYear;

	@Column(name = "REVENUE")
	private BigDecimal revenue;

	@Column(name = "BUSINESS_ACTIVITIES")
	private String businessActivities;

	@Column(name = "NO_BENEFICIARY_FARMERS")
	private Long noBeneficiaryFarmers;

	@Column(name = "NET_PROFIT")
	private BigDecimal netProfit;

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
	 * @return the finId
	 */
	public Long getFinId() {
		return finId;
	}

	/**
	 * @param finId the finId to set
	 */
	public void setFinId(Long finId) {
		this.finId = finId;
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
	 * @return the revenue
	 */
	public BigDecimal getRevenue() {
		return revenue;
	}

	/**
	 * @param revenue the revenue to set
	 */
	public void setRevenue(BigDecimal revenue) {
		this.revenue = revenue;
	}

	/**
	 * @return the businessActivities
	 */
	public String getBusinessActivities() {
		return businessActivities;
	}

	/**
	 * @param businessActivities the businessActivities to set
	 */
	public void setBusinessActivities(String businessActivities) {
		this.businessActivities = businessActivities;
	}

	/**
	 * @return the noBeneficiaryFarmers
	 */
	public Long getNoBeneficiaryFarmers() {
		return noBeneficiaryFarmers;
	}

	/**
	 * @param noBeneficiaryFarmers the noBeneficiaryFarmers to set
	 */
	public void setNoBeneficiaryFarmers(Long noBeneficiaryFarmers) {
		this.noBeneficiaryFarmers = noBeneficiaryFarmers;
	}

	/**
	 * @return the netProfit
	 */
	public BigDecimal getNetProfit() {
		return netProfit;
	}

	/**
	 * @param netProfit the netProfit to set
	 */
	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
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
		return new String[] { "SFAC", "Tb_Sfac_Financial_Info_Detail", "FIN_ID" };
	}
}
