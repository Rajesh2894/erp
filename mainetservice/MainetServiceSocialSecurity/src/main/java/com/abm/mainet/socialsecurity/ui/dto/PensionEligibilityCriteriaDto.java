/**
 * 
 */
package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author satish.rathore
 *
 */
public class PensionEligibilityCriteriaDto implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1564519941490405121L;
	private Long schmeEligibiltyId;
	private Long factorApplicableId;
	private String factorApplicableDesc;
	private Long criteriaId;
	private String criteriaDesc;
	private Boolean checkBox;
	private String rangeFrom;
	private String rangeTo;
	private Long paySchedule;
	private BigDecimal amt;
	private Long batchId;
	private BigDecimal amtq;
	private String payScheduleDesc;
	private BigDecimal subSchemeAmt;
	private Long beneficiaryCount;
	private BigDecimal beneficiaryAmt;
	
	public String getPayScheduleDesc() {
		return payScheduleDesc;
	}
	public void setPayScheduleDesc(String payScheduleDesc) {
		this.payScheduleDesc = payScheduleDesc;
	}
	public Long getFactorApplicableId() {
		return factorApplicableId;
	}
	public void setFactorApplicableId(Long factorApplicableId) {
		this.factorApplicableId = factorApplicableId;
	}
	public String getFactorApplicableDesc() {
		return factorApplicableDesc;
	}
	public void setFactorApplicableDesc(String factorApplicableDesc) {
		this.factorApplicableDesc = factorApplicableDesc;
	}
	public Long getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}
	public String getCriteriaDesc() {
		return criteriaDesc;
	}
	public void setCriteriaDesc(String criteriaDesc) {
		this.criteriaDesc = criteriaDesc;
	}
	
	public Boolean getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(Boolean checkBox) {
		this.checkBox = checkBox;
	}
	public String getRangeFrom() {
		return rangeFrom;
	}
	public void setRangeFrom(String rangeFrom) {
		this.rangeFrom = rangeFrom;
	}
	public String getRangeTo() {
		return rangeTo;
	}
	public void setRangeTo(String rangeTo) {
		this.rangeTo = rangeTo;
	}
	public Long getPaySchedule() {
		return paySchedule;
	}
	public void setPaySchedule(Long paySchedule) {
		this.paySchedule = paySchedule;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
	
	public Long getSchmeEligibiltyId() {
		return schmeEligibiltyId;
	}
	public void setSchmeEligibiltyId(Long schmeEligibiltyId) {
		this.schmeEligibiltyId = schmeEligibiltyId;
	}
	
	public BigDecimal getSubSchemeAmt() {
		return subSchemeAmt;
	}
	public void setSubSchemeAmt(BigDecimal subSchemeAmt) {
		this.subSchemeAmt = subSchemeAmt;
	}
	/**
     * @return the amtq
     */
    public BigDecimal getAmtq() {
        return amtq;
    }
    /**
     * @param amtq the amtq to set
     */
    public void setAmtq(BigDecimal amtq) {
        this.amtq = amtq;
    }
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PensionEligibilityCriteriaDto other = (PensionEligibilityCriteriaDto) obj;
		if (batchId == null) {
			if (other.batchId != null)
				return false;
		} else if (!batchId.equals(other.batchId))
			return false;
		return true;
	}
	
	
	public Long getBeneficiaryCount() {
		return beneficiaryCount;
	}
	public void setBeneficiaryCount(Long beneficiaryCount) {
		this.beneficiaryCount = beneficiaryCount;
	}
	public BigDecimal getBeneficiaryAmt() {
		return beneficiaryAmt;
	}
	public void setBeneficiaryAmt(BigDecimal beneficiaryAmt) {
		this.beneficiaryAmt = beneficiaryAmt;
	}

}
