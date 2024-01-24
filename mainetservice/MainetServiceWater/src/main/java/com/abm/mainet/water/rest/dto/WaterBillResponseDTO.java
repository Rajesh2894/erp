package com.abm.mainet.water.rest.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.water.dto.WaterBillTaxDTO;

/**
 * @author Rahul.Yadav
 *
 */
public class WaterBillResponseDTO implements Serializable {

    private static final long serialVersionUID = 363628731673659628L;

    private double rebateAmount;

    private double excessAmount;

    private double surchargeAmount;

    private double balanceExcessAmount;

    private List<String> validationList = new ArrayList<>();
    
    public double getBalanceExcessAmount() {
        return balanceExcessAmount;
    }

    public void setBalanceExcessAmount(final double balanceExcessAmount) {
        this.balanceExcessAmount = balanceExcessAmount;
    }

    private Map<Long, Double> details = new HashMap<>(0);

    private Map<Long, Long> billdetailsId = new HashMap<>(0);

    private String status;

    private Long csIdn;

    private double totalPayableAmount;

    private long applicationNumber;

    private Long receiptNo;

    private List<WaterBillTaxDTO> taxes = new ArrayList<>(0);

    private ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

    private String ccnNumber;

    private String oldccnNumber;

    private String guardianName;

    private String billFrequencyIs;
    
    private double currSurchargeAmount;
    
    private double adjustmentEntry;

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Map<Long, Double> getDetails() {
        return details;
    }

    public void setDetails(final Map<Long, Double> details) {
        this.details = details;
    }

    public Long getCsIdn() {
        return csIdn;
    }

    public void setCsIdn(final Long csIdn) {
        this.csIdn = csIdn;
    }

    public double getTotalPayableAmount() {
        return totalPayableAmount;
    }

    public void setTotalPayableAmount(final double totalPayableAmount) {
        this.totalPayableAmount = totalPayableAmount;
    }

    public Map<Long, Long> getBilldetailsId() {
        return billdetailsId;
    }

    public void setBilldetailsId(final Map<Long, Long> billdetailsId) {
        this.billdetailsId = billdetailsId;
    }

    public long getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(final long applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public double getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(final double rebateAmount) {
        this.rebateAmount = rebateAmount;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(final Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public double getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(final double excessAmount) {
        this.excessAmount = excessAmount;
    }

    public List<WaterBillTaxDTO> getTaxes() {
        return taxes;
    }

    public void setTaxes(final List<WaterBillTaxDTO> taxes) {
        this.taxes = taxes;
    }

    public ApplicantDetailDTO getApplicantDto() {
        return applicantDto;
    }

    public void setApplicantDto(final ApplicantDetailDTO applicantDto) {
        this.applicantDto = applicantDto;
    }

    public double getSurchargeAmount() {
        return surchargeAmount;
    }

    public void setSurchargeAmount(double surchargeAmount) {
        this.surchargeAmount = surchargeAmount;
    }

    public String getCcnNumber() {
        return ccnNumber;
    }

    public void setCcnNumber(String ccnNumber) {
        this.ccnNumber = ccnNumber;
    }

    public String getOldccnNumber() {
        return oldccnNumber;
    }

    public void setOldccnNumber(String oldccnNumber) {
        this.oldccnNumber = oldccnNumber;
    }

    public List<String> getValidationList() {
        return validationList;
    }

    public void setValidationList(List<String> validationList) {
        this.validationList = validationList;
    }

    public String getGuardianName() {
        return guardianName;
    }

    public void setGuardianName(String guardianName) {
        this.guardianName = guardianName;
    }

    public String getBillFrequencyIs() {
        return billFrequencyIs;
    }

    public void setBillFrequencyIs(String billFrequencyIs) {
        this.billFrequencyIs = billFrequencyIs;
    }

	public double getCurrSurchargeAmount() {
		return currSurchargeAmount;
	}

	public void setCurrSurchargeAmount(double currSurchargeAmount) {
		this.currSurchargeAmount = currSurchargeAmount;
	}

	public double getAdjustmentEntry() {
		return adjustmentEntry;
	}

	public void setAdjustmentEntry(double adjustmentEntry) {
		this.adjustmentEntry = adjustmentEntry;
	}

}
