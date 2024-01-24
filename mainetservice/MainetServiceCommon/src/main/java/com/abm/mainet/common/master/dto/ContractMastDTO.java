package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;

import com.abm.mainet.common.utility.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author apurva.salgaonkar
 *
 */
public class ContractMastDTO implements Serializable {

	private static final long serialVersionUID = 8048810962323004383L;

	private long contId;
	private Long contDept;
	private String contNo;
	private Date contDate;
	private String contTndNo;
	private Date contTndDate;
	private String contRsoNo;
	private Date contRsoDate;
	private String resoDate;
	private Long contType;
	private String contMode;
	private String contPayType;
	private String contRenewal;
	private String contActive;
	private String contCloseFlag;
	private String contAutStatus;
	private Long contAutBy;
	private Date contAutDate;
	private String contTerminatBy;
	private Date contTerminationDate;
	private String loaNo;
	private Long tndDeptId;
	private String tndDeptCode;
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
	private Long taxId;
	private String tndDate;
	private String contractType;
	private List<LookUp> taxCodeList = new ArrayList<>();

	private List<ContractDetailDTO> contractDetailList = new ArrayList<>();

	private List<ContractPart1DetailDTO> contractPart1List = new ArrayList<>();

	private List<ContractPart2DetailDTO> contractPart2List = new ArrayList<>();

	private List<ContractTermsDetailDTO> contractTermsDetailList = new ArrayList<>();

	private List<ContractInstalmentDetailDTO> contractInstalmentDetailList = new ArrayList<>();

	private String histFlag;

	private String contToPeriodUnit;

	private String contMapFlag;
	
	private String propertyNo;
	
	private String propName;
	
	private Long propId;
	
	private Date contCommDate;
	
	private Long apprApplicable;
	private Long apprType;
	private Double apprAmt;
	private Long leavyPenalty;
	private Long penaltyMode;
	private Double penaltyAmt;
        private Double contBalanceAmt;
        private Long contInstallmentSched;//BSC prefix
        
    private String totalBalanceAmount;
    
    private Long status;
    private Date suspendDate;
    private String reason;

	public String getContMapFlag() {
		return contMapFlag;
	}

	public void setContMapFlag(String contMapFlag) {
		this.contMapFlag = contMapFlag;
	}

	public String getContToPeriodUnit() {
		return contToPeriodUnit;
	}

	public void setContToPeriodUnit(String contToPeriodUnit) {
		this.contToPeriodUnit = contToPeriodUnit;
	}

	public String getHistFlag() {
		return histFlag;
	}

	public void setHistFlag(String histFlag) {
		this.histFlag = histFlag;
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

	public Long getTndDeptId() {
		return tndDeptId;
	}

	public void setTndDeptId(Long tndDeptId) {
		this.tndDeptId = tndDeptId;
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

	/**
	 * @return the contractDetailList
	 */
	public List<ContractDetailDTO> getContractDetailList() {
		return contractDetailList;
	}

	/**
	 * @param contractDetailList the contractDetailList to set
	 */
	public void setContractDetailList(final List<ContractDetailDTO> contractDetailList) {
		this.contractDetailList = contractDetailList;
	}

	/**
	 * @return the contractPart1List
	 */
	public List<ContractPart1DetailDTO> getContractPart1List() {
		return contractPart1List;
	}

	/**
	 * @param contractPart1List the contractPart1List to set
	 */
	public void setContractPart1List(final List<ContractPart1DetailDTO> contractPart1List) {
		this.contractPart1List = contractPart1List;
	}

	/**
	 * @return the contractPart2List
	 */
	public List<ContractPart2DetailDTO> getContractPart2List() {
		return contractPart2List;
	}

	/**
	 * @param contractPart2List the contractPart2List to set
	 */
	public void setContractPart2List(final List<ContractPart2DetailDTO> contractPart2List) {
		this.contractPart2List = contractPart2List;
	}

	/**
	 * @return the contractTermsDetailList
	 */
	public List<ContractTermsDetailDTO> getContractTermsDetailList() {
		return contractTermsDetailList;
	}

	/**
	 * @param contractTermsDetailList the contractTermsDetailList to set
	 */
	public void setContractTermsDetailList(final List<ContractTermsDetailDTO> contractTermsDetailList) {
		this.contractTermsDetailList = contractTermsDetailList;
	}

	/**
	 * @return the contractInstalmentDetailList
	 */
	public List<ContractInstalmentDetailDTO> getContractInstalmentDetailList() {
		return contractInstalmentDetailList;
	}

	/**
	 * @param contractInstalmentDetailList the contractInstalmentDetailList to set
	 */
	public void setContractInstalmentDetailList(final List<ContractInstalmentDetailDTO> contractInstalmentDetailList) {
		this.contractInstalmentDetailList = contractInstalmentDetailList;
	}

	public Long getTaxId() {
		return taxId;
	}

	public void setTaxId(final Long taxId) {
		this.taxId = taxId;
	}

	public String getTndDate() {
		return tndDate;
	}

	public void setTndDate(String tndDate) {
		this.tndDate = tndDate;
	}

	public List<LookUp> getTaxCodeList() {
		return taxCodeList;
	}

	public void setTaxCodeList(final List<LookUp> taxCodeList) {
		this.taxCodeList = taxCodeList;
	}

	public String getTndDeptCode() {
		return tndDeptCode;
	}

	public void setTndDeptCode(String tndDeptCode) {
		this.tndDeptCode = tndDeptCode;
	}

	public String getResoDate() {
		return resoDate;
	}

	public void setResoDate(String resoDate) {
		this.resoDate = resoDate;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

        public String getPropertyNo() {
            return propertyNo;
        }
    
        public void setPropertyNo(String propertyNo) {
            this.propertyNo = propertyNo;
        }
    
        public String getPropName() {
            return propName;
        }
    
        public void setPropName(String propName) {
            this.propName = propName;
        }

        public Long getPropId() {
            return propId;
        }

        public void setPropId(Long propId) {
            this.propId = propId;
        }

		public Long getApprApplicable() {
			return apprApplicable;
		}

		public Long getApprType() {
			return apprType;
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

		public Double getApprAmt() {
			return apprAmt;
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

				public Date getContCommDate() {
					return contCommDate;
				}

				public void setContCommDate(Date contCommDate) {
					this.contCommDate = contCommDate;
				}

				public String getTotalBalanceAmount() {
					return totalBalanceAmount;
				}

				public void setTotalBalanceAmount(String totalBalanceAmount) {
					this.totalBalanceAmount = totalBalanceAmount;
				}

				public Long getStatus() {
					return status;
				}

				public void setStatus(Long status) {
					this.status = status;
				}

				public Date getSuspendDate() {
					return suspendDate;
				}

				public void setSuspendDate(Date suspendDate) {
					this.suspendDate = suspendDate;
				}

				public String getReason() {
					return reason;
				}

				public void setReason(String reason) {
					this.reason = reason;
				}
}
