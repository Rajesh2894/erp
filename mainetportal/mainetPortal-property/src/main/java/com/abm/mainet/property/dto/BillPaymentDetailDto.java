package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.TbBillMas;

public class BillPaymentDetailDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6941978333435703058L;

    private String address;
    private Long pinCode;
    private Long deptId;
    private String propNo;
    private Long applNo;
    private Long serviceId;
    private String location;
    private String primaryOwnerName;
    private String primaryOwnerMobNo;
    private double totalPayableAmt;
    private double totalPaidAmt;
    private double advanceAmt;
    private double totalSubcharge;
    private Long billNo;
    private Date billDate;
    private Long receiptNo;
    private String redirectCheck;// used for redirect to self assessment purpose
    private List<String> otherOwnerList = new ArrayList<>();
    private List<BillDisplayDto> billDisList = new ArrayList<>();
    private Long orgId;
    private String partialAdvancePayStatus;
    private Long ward1;
    private Long ward2;
    private Long ward3;
    private Long ward4;
    private Long ward5;
    private String oldpropno;
    private Map<String, Date> billDetails = null;// <bill number,bill date> for revenue receipt
    private String usageType1;
    private Double pdRv;
    private String ownerFullName;
    private String plotNo;
    private BigDecimal totalPenalty;
    
    private BigDecimal totalArrearAmt = new BigDecimal(0);
    private BigDecimal totalCurrentAmt = new BigDecimal(0);
    private BigDecimal totalBalAmt = new BigDecimal(0);
    private BigDecimal totalRebate = new BigDecimal(0);
    
    private String mobileNo;
    private String emailId;
    private ProvisionalAssesmentOwnerDtlDto ownerDtlDto;
    private ProvisionalAssesmentMstDto assmtDto;
    private double halfPaymentRebate;
    private double halfOutstandingAmount;
    private double fullOutstandingAmount;
    private double actualRebate;
    private double balanceOutstandingAmount;
    private String errorMsg;
    private String parentPropNo;
    private String parentPropName;
    private double actualHalfOutstandingAmount;
    private double actualFullOutstandingAmount;
    private double mosPayableAmount;
    private String uniquePropertyId;
    private String lastBillYear;
    private String newHouseNo;
    private List<TbBillMas> billMasList = new ArrayList<TbBillMas>();
    private String currentBillGenFlag;
    private String serviceShortCode;
    private double paidAmountInCurrentYear;
    private double currentActaulAmount;
    private double currentYearBillPaidAmount;
    private double currentYearPenaltyBalance;
    private double penaltyDiscount;
       
    public String getPlotNo() {
		return plotNo;
	}

	public void setPlotNo(String plotNo) {
		this.plotNo = plotNo;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPinCode() {
        return pinCode;
    }

    public void setPinCode(Long pinCode) {
        this.pinCode = pinCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrimaryOwnerMobNo() {
        return primaryOwnerMobNo;
    }

    public void setPrimaryOwnerMobNo(String primaryOwnerMobNo) {
        this.primaryOwnerMobNo = primaryOwnerMobNo;
    }

    public List<String> getOtherOwnerList() {
        return otherOwnerList;
    }

    public void setOtherOwnerList(List<String> otherOwnerList) {
        this.otherOwnerList = otherOwnerList;
    }

    public String getPrimaryOwnerName() {
        return primaryOwnerName;
    }

    public void setPrimaryOwnerName(String primaryOwnerName) {
        this.primaryOwnerName = primaryOwnerName;
    }

    public List<BillDisplayDto> getBillDisList() {
        return billDisList;
    }

    public void setBillDisList(List<BillDisplayDto> billDisList) {
        this.billDisList = billDisList;
    }

    public double getTotalPayableAmt() {
        return totalPayableAmt;
    }

    public void setTotalPayableAmt(double totalPayableAmt) {
        this.totalPayableAmt = totalPayableAmt;
    }

    public Long getBillNo() {
        return billNo;
    }

    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Long getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(Long receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public double getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(double advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public Long getApplNo() {
        return applNo;
    }

    public void setApplNo(Long applNo) {
        this.applNo = applNo;
    }

    public double getTotalPaidAmt() {
        return totalPaidAmt;
    }

    public void setTotalPaidAmt(double totalPaidAmt) {
        this.totalPaidAmt = totalPaidAmt;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getRedirectCheck() {
        return redirectCheck;
    }

    public void setRedirectCheck(String redirectCheck) {
        this.redirectCheck = redirectCheck;
    }

    public String getPartialAdvancePayStatus() {
        return partialAdvancePayStatus;
    }

    public void setPartialAdvancePayStatus(String partialAdvancePayStatus) {
        this.partialAdvancePayStatus = partialAdvancePayStatus;
    }

    public Long getWard1() {
        return ward1;
    }

    public void setWard1(Long ward1) {
        this.ward1 = ward1;
    }

    public Long getWard2() {
        return ward2;
    }

    public void setWard2(Long ward2) {
        this.ward2 = ward2;
    }

    public Long getWard3() {
        return ward3;
    }

    public void setWard3(Long ward3) {
        this.ward3 = ward3;
    }

    public Long getWard4() {
        return ward4;
    }

    public void setWard4(Long ward4) {
        this.ward4 = ward4;
    }

    public Long getWard5() {
        return ward5;
    }

    public void setWard5(Long ward5) {
        this.ward5 = ward5;
    }

    public double getTotalSubcharge() {
        return totalSubcharge;
    }

    public void setTotalSubcharge(double totalSubcharge) {
        this.totalSubcharge = totalSubcharge;
    }

    public String getOldpropno() {
        return oldpropno;
    }

    public void setOldpropno(String oldpropno) {
        this.oldpropno = oldpropno;
    }

    public Map<String, Date> getBillDetails() {
        return billDetails;
    }

    public void setBillDetails(Map<String, Date> billDetails) {
        this.billDetails = billDetails;
    }

    public String getUsageType1() {
        return usageType1;
    }

    public void setUsageType1(String usageType1) {
        this.usageType1 = usageType1;
    }

	public Double getPdRv() {
		return pdRv;
	}

	public void setPdRv(Double pdRv) {
		this.pdRv = pdRv;
	}

	public String getOwnerFullName() {
		return ownerFullName;
	}

	public void setOwnerFullName(String ownerFullName) {
		this.ownerFullName = ownerFullName;
	}
	
	public BigDecimal getTotalPenalty() {
		return totalPenalty;
	}

	public void setTotalPenalty(BigDecimal totalPenalty) {
		this.totalPenalty = totalPenalty;
	}

	public BigDecimal getTotalArrearAmt() {
		return totalArrearAmt;
	}

	public void setTotalArrearAmt(BigDecimal totalArrearAmt) {
		this.totalArrearAmt = totalArrearAmt;
	}

	public BigDecimal getTotalCurrentAmt() {
		return totalCurrentAmt;
	}

	public void setTotalCurrentAmt(BigDecimal totalCurrentAmt) {
		this.totalCurrentAmt = totalCurrentAmt;
	}

	public BigDecimal getTotalBalAmt() {
		return totalBalAmt;
	}

	public void setTotalBalAmt(BigDecimal totalBalAmt) {
		this.totalBalAmt = totalBalAmt;
	}

	public BigDecimal getTotalRebate() {
		return totalRebate;
	}

	public void setTotalRebate(BigDecimal totalRebate) {
		this.totalRebate = totalRebate;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public ProvisionalAssesmentOwnerDtlDto getOwnerDtlDto() {
		return ownerDtlDto;
	}

	public void setOwnerDtlDto(ProvisionalAssesmentOwnerDtlDto ownerDtlDto) {
		this.ownerDtlDto = ownerDtlDto;
	}

	public ProvisionalAssesmentMstDto getAssmtDto() {
		return assmtDto;
	}

	public void setAssmtDto(ProvisionalAssesmentMstDto assmtDto) {
		this.assmtDto = assmtDto;
	}

	public double getHalfPaymentRebate() {
		return halfPaymentRebate;
	}

	public void setHalfPaymentRebate(double halfPaymentRebate) {
		this.halfPaymentRebate = halfPaymentRebate;
	}

	public double getHalfOutstandingAmount() {
		return halfOutstandingAmount;
	}

	public void setHalfOutstandingAmount(double halfOutstandingAmount) {
		this.halfOutstandingAmount = halfOutstandingAmount;
	}

	public double getFullOutstandingAmount() {
		return fullOutstandingAmount;
	}

	public void setFullOutstandingAmount(double fullOutstandingAmount) {
		this.fullOutstandingAmount = fullOutstandingAmount;
	}

	public double getActualRebate() {
		return actualRebate;
	}

	public void setActualRebate(double actualRebate) {
		this.actualRebate = actualRebate;
	}

	public double getBalanceOutstandingAmount() {
		return balanceOutstandingAmount;
	}

	public void setBalanceOutstandingAmount(double balanceOutstandingAmount) {
		this.balanceOutstandingAmount = balanceOutstandingAmount;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
	}

	public String getParentPropName() {
		return parentPropName;
	}

	public void setParentPropName(String parentPropName) {
		this.parentPropName = parentPropName;
	}

	public double getActualHalfOutstandingAmount() {
		return actualHalfOutstandingAmount;
	}

	public void setActualHalfOutstandingAmount(double actualHalfOutstandingAmount) {
		this.actualHalfOutstandingAmount = actualHalfOutstandingAmount;
	}

	public double getActualFullOutstandingAmount() {
		return actualFullOutstandingAmount;
	}

	public void setActualFullOutstandingAmount(double actualFullOutstandingAmount) {
		this.actualFullOutstandingAmount = actualFullOutstandingAmount;
	}

	public double getMosPayableAmount() {
		return mosPayableAmount;
	}

	public void setMosPayableAmount(double mosPayableAmount) {
		this.mosPayableAmount = mosPayableAmount;
	}

	public String getUniquePropertyId() {
		return uniquePropertyId;
	}

	public void setUniquePropertyId(String uniquePropertyId) {
		this.uniquePropertyId = uniquePropertyId;
	}

	public String getLastBillYear() {
		return lastBillYear;
	}

	public void setLastBillYear(String lastBillYear) {
		this.lastBillYear = lastBillYear;
	}

	public String getNewHouseNo() {
		return newHouseNo;
	}

	public void setNewHouseNo(String newHouseNo) {
		this.newHouseNo = newHouseNo;
	}

	public List<TbBillMas> getBillMasList() {
		return billMasList;
	}

	public void setBillMasList(List<TbBillMas> billMasList) {
		this.billMasList = billMasList;
	}

	public String getCurrentBillGenFlag() {
		return currentBillGenFlag;
	}

	public void setCurrentBillGenFlag(String currentBillGenFlag) {
		this.currentBillGenFlag = currentBillGenFlag;
	}

	public String getServiceShortCode() {
		return serviceShortCode;
	}

	public void setServiceShortCode(String serviceShortCode) {
		this.serviceShortCode = serviceShortCode;
	}

	public double getPaidAmountInCurrentYear() {
		return paidAmountInCurrentYear;
	}

	public void setPaidAmountInCurrentYear(double paidAmountInCurrentYear) {
		this.paidAmountInCurrentYear = paidAmountInCurrentYear;
	}

	public double getCurrentActaulAmount() {
		return currentActaulAmount;
	}

	public void setCurrentActaulAmount(double currentActaulAmount) {
		this.currentActaulAmount = currentActaulAmount;
	}

	public double getCurrentYearBillPaidAmount() {
		return currentYearBillPaidAmount;
	}

	public void setCurrentYearBillPaidAmount(double currentYearBillPaidAmount) {
		this.currentYearBillPaidAmount = currentYearBillPaidAmount;
	}

	public double getCurrentYearPenaltyBalance() {
		return currentYearPenaltyBalance;
	}

	public void setCurrentYearPenaltyBalance(double currentYearPenaltyBalance) {
		this.currentYearPenaltyBalance = currentYearPenaltyBalance;
	}

	public double getPenaltyDiscount() {
		return penaltyDiscount;
	}

	public void setPenaltyDiscount(double penaltyDiscount) {
		this.penaltyDiscount = penaltyDiscount;
	}
	
	
}
