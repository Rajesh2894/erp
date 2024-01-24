package com.abm.mainet.common.domain;

import java.io.Serializable;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Sadik.Shaikh
 * @comment Table for maintain history of Contract Creation,Contract
 *          renewal,contract revision
 */
@Entity
@Table(name = "TB_CONTRACT_MAST_HIST")
public class ContractMastHistEntity implements Serializable {

	private static final long serialVersionUID = -3084976237564092028L;

	@Id
	@GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
	@GeneratedValue(generator = "MyCustomGenerator")
	@Column(name = "CONT_ID_H", precision = 12, scale = 0, nullable = false)
	private long contIdH;

	@Column(name = "CONT_ID", precision = 12, scale = 0, nullable = false)
	private long contId;

	@Column(name = "CONT_DEPT", precision = 12, scale = 0, nullable = false)
	private Long contDept;

	@Column(name = "CONT_NO", length = 20, nullable = false)
	private String contNo;

	@Column(name = "CONT_DATE", nullable = false)
	private Date contDate;

	@Column(name = "CONT_TND_NO", length = 20, nullable = false)
	private String contTndNo;

	@Column(name = "CONT_TND_DATE", nullable = false)
	private Date contTndDate;

	@Column(name = "CONT_RSO_NO", length = 20, nullable = false)
	private String contRsoNo;

	@Column(name = "CONT_RSO_DATE", nullable = false)
	private Date contRsoDate;

	@Column(name = "CONT_TYPE", precision = 12, scale = 0, nullable = false)
	private Long contType;

	@Column(name = "CONT_MODE", length = 1, nullable = true)
	private String contMode;

	@Column(name = "CONT_PAY_TYPE", length = 1, nullable = true)
	private String contPayType;

	@Column(name = "CONT_RENEWAL", length = 1, nullable = true)
	private String contRenewal;

	@Column(name = "CONT_ACTIVE", length = 1, nullable = false)
	private String contActive;

	@Column(name = "CONT_CLOSE_FLAG", length = 1, nullable = false)
	private String contCloseFlag;

	@Column(name = "CONT_AUT_STATUS", length = 1, nullable = true)
	private String contAutStatus;

	@Column(name = "CONT_AUT_BY", precision = 12, scale = 0, nullable = true)
	private Long contAutBy;

	@Column(name = "CONT_AUT_DATE", nullable = true)
	private Date contAutDate;

	@Column(name = "CONT_TERMINAT_BY", length = 1, nullable = true)
	private String contTerminatBy;

	@Column(name = "CONT_TERMINATION_DATE", nullable = true)
	private Date contTerminationDate;

	@Column(name = "LOA_NO", nullable = true)
	private String loaNo;

	@Column(name = "ORGID", nullable = false)
	private Long orgId;

	@Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
	private Long createdBy;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date lmodDate;

	@Column(name = "UPDATED_BY", nullable = false)
	private Long updatedBy;

	@Column(name = "UPDATED_DATE", nullable = true)
	private Date updatedDate;

	@Column(name = "LG_IP_MAC", length = 100, nullable = false)
	private String lgIpMac;

	@Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
	private String lgIpMacUpd;

	@Column(name = "CONT_MAP_FLAG", length = 1, nullable = false)
	private String contMapFlag;

	
	@Column(name = "APPR_APPL_ID", nullable = false)
	private Long apprApplicable;
	
	@Column(name = "APPR_TYPE", nullable = false)
	private Long apprType;
	
	@Column(name = "APPR_AMT", nullable = false)
	private Double apprAmt;
	
	@Column(name = "LEAVY_PENALTY_ID", nullable = false)
	private Long leavyPenalty;
	@Column(name = "LEAVY_PENALTY_MODE", nullable = false)
	private Long penaltyMode;
	@Column(name = "LEAVY_PENALTY_AMT", nullable = false)
	private Double penaltyAmt;
	
	@Column(name = "CONT_BAL_AMT", nullable = false)
    private Double contBalanceAmt;
        
    @Column(name = "CONT_INST_SCHED", nullable = false)
    private Long contInstallmentSched;//BSC prefix
        
    @Column(name = "REMARK", length=1000, nullable = true)
    private String remark;
	/*
	 * //@OneToMany(fetch = FetchType.LAZY, mappedBy = "contId", cascade =
	 * CascadeType.ALL)
	 * 
	 * @Where(clause = "CONTD_ACTIVE = 'Y'") private List<ContractDetailEntity>
	 * contractDetailList = new ArrayList<>(0);
	 * 
	 * //@OneToMany(fetch = FetchType.LAZY, mappedBy = "contId", cascade =
	 * CascadeType.ALL)
	 * 
	 * @OrderBy("CONTP1_ID ASC") private List<ContractPart1DetailEntity>
	 * contractPart1List = new ArrayList<>(0);
	 * 
	 * //@OneToMany(fetch = FetchType.LAZY, mappedBy = "contId", cascade =
	 * CascadeType.ALL)
	 * 
	 * @OrderBy("CONTP2_ID ASC") private List<ContractPart2DetailEntity>
	 * contractPart2List = new ArrayList<>(0);
	 * 
	 * //@OneToMany(fetch = FetchType.LAZY, mappedBy = "contId", cascade =
	 * CascadeType.ALL)
	 * 
	 * @OrderBy("CONTT_ID ASC") private List<ContractTermsDetailEntity>
	 * contractTermsDetailList = new ArrayList<>(0);
	 * 
	 * //@OneToMany(fetch = FetchType.LAZY, mappedBy = "contId", cascade =
	 * CascadeType.ALL)
	 * 
	 * @OrderBy("CONIT_ID ASC") private List<ContractInstalmentDetailEntity>
	 * contractInstalmentDetailList = new ArrayList<>(0);
	 */

	@Column(name = "H_STATUS", nullable = true)
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getContIdH() {
		return contIdH;
	}

	public void setContIdH(long contIdH) {
		this.contIdH = contIdH;
	}

	public String[] getPkValues() {
		return new String[] { MainetConstants.CommonConstants.COM, "TB_CONTRACT_MAST_HIST", "CONT_ID_H" };
	}

	/**
	 * @return the contId
	 */
	public long getContId() {
		return contId;
	}

	/**
	 * @param contId the contId to set
	 */
	public void setContId(final long contId) {
		this.contId = contId;
	}

	/**
	 * @return the contDept
	 */
	public Long getContDept() {
		return contDept;
	}

	/**
	 * @param contDept the contDept to set
	 */
	public void setContDept(final Long contDept) {
		this.contDept = contDept;
	}

	/**
	 * @return the contNo
	 */
	public String getContNo() {
		return contNo;
	}

	/**
	 * @param contNo the contNo to set
	 */
	public void setContNo(final String contNo) {
		this.contNo = contNo;
	}

	/**
	 * @return the contDate
	 */
	public Date getContDate() {
		return contDate;
	}

	/**
	 * @param contDate the contDate to set
	 */
	public void setContDate(final Date contDate) {
		this.contDate = contDate;
	}

	/**
	 * @return the contTndNo
	 */
	public String getContTndNo() {
		return contTndNo;
	}

	/**
	 * @param contTndNo the contTndNo to set
	 */
	public void setContTndNo(final String contTndNo) {
		this.contTndNo = contTndNo;
	}

	/**
	 * @return the contTndDate
	 */
	public Date getContTndDate() {
		return contTndDate;
	}

	/**
	 * @param contTndDate the contTndDate to set
	 */
	public void setContTndDate(final Date contTndDate) {
		this.contTndDate = contTndDate;
	}

	/**
	 * @return the contRsoNo
	 */
	public String getContRsoNo() {
		return contRsoNo;
	}

	/**
	 * @param contRsoNo the contRsoNo to set
	 */
	public void setContRsoNo(final String contRsoNo) {
		this.contRsoNo = contRsoNo;
	}

	/**
	 * @return the contRsoDate
	 */
	public Date getContRsoDate() {
		return contRsoDate;
	}

	/**
	 * @param contRsoDate the contRsoDate to set
	 */
	public void setContRsoDate(final Date contRsoDate) {
		this.contRsoDate = contRsoDate;
	}

	/**
	 * @return the contType
	 */
	public Long getContType() {
		return contType;
	}

	/**
	 * @param contType the contType to set
	 */
	public void setContType(final Long contType) {
		this.contType = contType;
	}

	/**
	 * @return the contMode
	 */
	public String getContMode() {
		return contMode;
	}

	/**
	 * @param contMode the contMode to set
	 */
	public void setContMode(final String contMode) {
		this.contMode = contMode;
	}

	/**
	 * @return the contPayType
	 */
	public String getContPayType() {
		return contPayType;
	}

	/**
	 * @param contPayType the contPayType to set
	 */
	public void setContPayType(final String contPayType) {
		this.contPayType = contPayType;
	}

	/**
	 * @return the contRenewal
	 */
	public String getContRenewal() {
		return contRenewal;
	}

	/**
	 * @param contRenewal the contRenewal to set
	 */
	public void setContRenewal(final String contRenewal) {
		this.contRenewal = contRenewal;
	}

	/**
	 * @return the contActive
	 */
	public String getContActive() {
		return contActive;
	}

	/**
	 * @param contActive the contActive to set
	 */
	public void setContActive(final String contActive) {
		this.contActive = contActive;
	}

	/**
	 * @return the contCloseFlag
	 */
	public String getContCloseFlag() {
		return contCloseFlag;
	}

	/**
	 * @param contCloseFlag the contCloseFlag to set
	 */
	public void setContCloseFlag(final String contCloseFlag) {
		this.contCloseFlag = contCloseFlag;
	}

	/**
	 * @return the contAutStatus
	 */
	public String getContAutStatus() {
		return contAutStatus;
	}

	/**
	 * @param contAutStatus the contAutStatus to set
	 */
	public void setContAutStatus(final String contAutStatus) {
		this.contAutStatus = contAutStatus;
	}

	/**
	 * @return the contAutBy
	 */
	public Long getContAutBy() {
		return contAutBy;
	}

	/**
	 * @param contAutBy the contAutBy to set
	 */
	public void setContAutBy(final Long contAutBy) {
		this.contAutBy = contAutBy;
	}

	/**
	 * @return the contAutDate
	 */
	public Date getContAutDate() {
		return contAutDate;
	}

	/**
	 * @param contAutDate the contAutDate to set
	 */
	public void setContAutDate(final Date contAutDate) {
		this.contAutDate = contAutDate;
	}

	/**
	 * @return the contTerminatBy
	 */
	public String getContTerminatBy() {
		return contTerminatBy;
	}

	/**
	 * @param contTerminatBy the contTerminatBy to set
	 */
	public void setContTerminatBy(final String contTerminatBy) {
		this.contTerminatBy = contTerminatBy;
	}

	/**
	 * @return the contTerminationDate
	 */
	public Date getContTerminationDate() {
		return contTerminationDate;
	}

	/**
	 * @param contTerminationDate the contTerminationDate to set
	 */
	public void setContTerminationDate(final Date contTerminationDate) {
		this.contTerminationDate = contTerminationDate;
	}

	public String getLoaNo() {
		return loaNo;
	}

	public void setLoaNo(String loaNo) {
		this.loaNo = loaNo;
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

	/**
	 * @return the contractDetailList
	 */
	/*
	 * public List<ContractDetailEntity> getContractDetailList() { return
	 * contractDetailList; }
	 * 
	 *//**
		 * @param contractDetailList the contractDetailList to set
		 */
	/*
	 * public void setContractDetailList(final List<ContractDetailEntity>
	 * contractDetailList) { this.contractDetailList = contractDetailList; }
	 * 
	 *//**
		 * @return the contractPart1List
		 */
	/*
	 * public List<ContractPart1DetailEntity> getContractPart1List() { return
	 * contractPart1List; }
	 * 
	 *//**
		 * @param contractPart1List the contractPart1List to set
		 */
	/*
	 * public void setContractPart1List(final List<ContractPart1DetailEntity>
	 * contractPart1List) { this.contractPart1List = contractPart1List; }
	 * 
	 *//**
		 * @return the contractPart2List
		 */
	/*
	 * public List<ContractPart2DetailEntity> getContractPart2List() { return
	 * contractPart2List; }
	 * 
	 *//**
		 * @param contractPart2List the contractPart2List to set
		 */
	/*
	 * public void setContractPart2List(final List<ContractPart2DetailEntity>
	 * contractPart2List) { this.contractPart2List = contractPart2List; }
	 * 
	 *//**
		 * @return the contractTermsDetailList
		 */
	/*
	 * public List<ContractTermsDetailEntity> getContractTermsDetailList() { return
	 * contractTermsDetailList; }
	 * 
	 *//**
		 * @param contractTermsDetailList the contractTermsDetailList to set
		 */
	/*
	 * public void setContractTermsDetailList(final List<ContractTermsDetailEntity>
	 * contractTermsDetailList) { this.contractTermsDetailList =
	 * contractTermsDetailList; }
	 * 
	 *//**
		 * @return the contractInstalmentDetailList
		 */
	/*
	 * public List<ContractInstalmentDetailEntity> getContractInstalmentDetailList()
	 * { return contractInstalmentDetailList; }
	 * 
	 *//**
		 * @param contractInstalmentDetailList the contractInstalmentDetailList to set
		 *//*
			 * public void setContractInstalmentDetailList( final
			 * List<ContractInstalmentDetailEntity> contractInstalmentDetailList) {
			 * this.contractInstalmentDetailList = contractInstalmentDetailList; }
			 */

	/**
	 * @return the contMapFlag
	 */
	public String getContMapFlag() {
		return contMapFlag;
	}

	/**
	 * @param contMapFlag the contMapFlag to set
	 */
	public void setContMapFlag(final String contMapFlag) {
		this.contMapFlag = contMapFlag;
	}

	public Long getApprApplicable() {
		return apprApplicable;
	}

	public Long getApprType() {
		return apprType;
	}

	public Double getApprAmt() {
		return apprAmt;
	}

	public void setApprApplicable(Long apprApplicable) {
		this.apprApplicable = apprApplicable;
	}

	public void setApprType(Long apprType) {
		this.apprType = apprType;
	}

	public void setApprAmt(Double apprAmt) {
		this.apprAmt = apprAmt;
	}

	public Long getLeavyPenalty() {
		return leavyPenalty;
	}

	public Long getPenaltyMode() {
		return penaltyMode;
	}

	public Double getPenaltyAmt() {
		return penaltyAmt;
	}

	public void setLeavyPenalty(Long leavyPenalty) {
		this.leavyPenalty = leavyPenalty;
	}

	public void setPenaltyMode(Long penaltyMode) {
		this.penaltyMode = penaltyMode;
	}

	public void setPenaltyAmt(Double penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}

        
    
        public Double getContBalanceAmt() {
            return contBalanceAmt;
        }
    
        public void setContBalanceAmt(Double contBalanceAmt) {
            this.contBalanceAmt = contBalanceAmt;
        }

        public Long getContInstallmentSched() {
            return contInstallmentSched;
        }
    
        public void setContInstallmentSched(Long contInstallmentSched) {
            this.contInstallmentSched = contInstallmentSched;
        }

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

}