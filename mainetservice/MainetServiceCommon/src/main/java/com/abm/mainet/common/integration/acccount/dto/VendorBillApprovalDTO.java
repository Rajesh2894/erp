package com.abm.mainet.common.integration.acccount.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.abm.mainet.common.utility.DepartmentLookUp;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author tejas.kotekar
 *
 */
@XmlRootElement(name = "VendorBillApprovalDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VendorBillApprovalDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class VendorBillApprovalDTO {

    // primary key
	@XmlElement(name="id",namespace="http://service.soap.account.mainet.abm.com/")
    private Long id;
	@XmlElement(name="orgId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long orgId;
	@XmlElement(name="superOrgId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long superOrgId;
	@XmlElement(name="languageId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long languageId;
	@XmlElement(name="fromDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String fromDate;
	@XmlElement(name="toDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String toDate;
	@XmlElement(name="billTypeId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long billTypeId;
	@XmlElement(name="billNo",namespace="http://service.soap.account.mainet.abm.com/")
    private String billNo;
	@XmlElement(name="departmentId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long departmentId;
	@XmlElement(name="vendorId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long vendorId;
	@XmlElement(name="billAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal billAmount;
	@XmlElement(name="billAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String billAmountStr;
	@XmlElement(name="deductions",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal deductions;
	@XmlElement(name="deductionsStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String deductionsStr;
	@XmlElement(name="netPayables",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal netPayables;
	@XmlElement(name="netPayablesStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String netPayablesStr;
    // Can be Authorized, Unauthorized, Rejected
	@XmlElement(name="authorizationStatus",namespace="http://service.soap.account.mainet.abm.com/")
    private String authorizationStatus;
	@XmlElement(name="authorizationMode",namespace="http://service.soap.account.mainet.abm.com/")
    private String authorizationMode;
	@XmlElement(name="lookUpList",namespace="http://service.soap.account.mainet.abm.com/")
    private List<LookUp> lookUpList;
	@XmlElement(name="departmentList",namespace="http://service.soap.account.mainet.abm.com/")
    private List<DepartmentLookUp> departmentList;
	@XmlElement(name="disallowedAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal disallowedAmount;
	@XmlElement(name="disallowedRemark",namespace="http://service.soap.account.mainet.abm.com/")
    private String disallowedRemark;
	@XmlElement(name="totalSanctionedAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal totalSanctionedAmount;
	@XmlElement(name="dedcutionAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String dedcutionAmountStr;
	@XmlElement(name="netPayableStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String netPayableStr;
	@XmlElement(name="sanctionedAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal sanctionedAmount;
	@XmlElement(name="billType",namespace="http://service.soap.account.mainet.abm.com/")
    // For view and Edit
    private String billType;
	@XmlElement(name="billEntryDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String billEntryDate;
	@XmlElement(name="vendorDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String vendorDesc;
	@XmlElement(name="invoiceAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal invoiceAmount;
	@XmlElement(name="invoiceAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String invoiceAmountStr;
	@XmlElement(name="invoiceNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String invoiceNumber;
	@XmlElement(name="invoiceDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String invoiceDate;
	@XmlElement(name="orderNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String orderNumber;
	@XmlElement(name="orderDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String orderDate;
	@XmlElement(name="resolutionNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String resolutionNumber;
	@XmlElement(name="resolutionDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String resolutionDate;
	@XmlElement(name="narration",namespace="http://service.soap.account.mainet.abm.com/")
    private String narration;
	@XmlElement(name="checkerAuthorization",namespace="http://service.soap.account.mainet.abm.com/")
    private Character checkerAuthorization;
	@XmlElement(name="checkerRemarks",namespace="http://service.soap.account.mainet.abm.com/")
    private String checkerRemarks;
	@XmlElement(name="authorizerEmployee",namespace="http://service.soap.account.mainet.abm.com/")
    private String authorizerEmployee;
	@XmlElement(name="sanctionedAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String sanctionedAmountStr;
	@XmlElement(name="actualAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String actualAmountStr;
	@XmlElement(name="disallowedAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String disallowedAmountStr;
	@XmlElement(name="budgetCodeId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long budgetCodeId;
	@XmlElement(name="expenditureExistsFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String expenditureExistsFlag;
	@XmlElement(name="salaryBillExitFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String salaryBillExitFlag;
	@XmlElement(name="billIntRefId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long billIntRefId;
	@XmlElement(name="createdBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long createdBy;
	@XmlElement(name="createdDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String createdDate;
    @Size(max = 100)
	@XmlElement(name="lgIpMacAddress",namespace="http://service.soap.account.mainet.abm.com/")
    private String lgIpMacAddress;
	@XmlElement(name="fieldId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long fieldId;
	//@XmlElement(name="fieldId",namespace="http://service.soap.account.mainet.abm.com/")
    @XmlTransient
	private Long fundId;
	@XmlElement(name="faYearid",namespace="http://service.soap.account.mainet.abm.com/")
	private Long faYearid;
	@XmlElement(name="expDetListDto",namespace="http://service.soap.account.mainet.abm.com/")
    private List<VendorBillExpDetailDTO> expDetListDto;
	@XmlElement(name="dedDetListDto",namespace="http://service.soap.account.mainet.abm.com/")
    private List<VendorBillDedDetailDTO> dedDetListDto;
	
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getSuperOrgId() {
        return superOrgId;
    }

    public void setSuperOrgId(final Long superOrgId) {
        this.superOrgId = superOrgId;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(final Long languageId) {
        this.languageId = languageId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }

    public Long getBillTypeId() {
        return billTypeId;
    }

    public void setBillTypeId(final Long billTypeId) {
        this.billTypeId = billTypeId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(final String billNo) {
        this.billNo = billNo;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(final Long vendorId) {
        this.vendorId = vendorId;
    }

    public BigDecimal getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(final BigDecimal billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillAmountStr() {
        return billAmountStr;
    }

    public void setBillAmountStr(final String billAmountStr) {
        this.billAmountStr = billAmountStr;
    }

    public BigDecimal getDeductions() {
        return deductions;
    }

    public void setDeductions(final BigDecimal deductions) {
        this.deductions = deductions;
    }

    public String getDeductionsStr() {
        return deductionsStr;
    }

    public void setDeductionsStr(final String deductionsStr) {
        this.deductionsStr = deductionsStr;
    }

    public BigDecimal getNetPayables() {
        return netPayables;
    }

    public void setNetPayables(final BigDecimal netPayables) {
        this.netPayables = netPayables;
    }

    public String getNetPayablesStr() {
        return netPayablesStr;
    }

    public void setNetPayablesStr(final String netPayablesStr) {
        this.netPayablesStr = netPayablesStr;
    }

    public String getAuthorizationMode() {
        return authorizationMode;
    }

    public void setAuthorizationMode(final String authorizationMode) {
        this.authorizationMode = authorizationMode;
    }

    public String getAuthorizationStatus() {
        return authorizationStatus;
    }

    public void setAuthorizationStatus(final String authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public List<LookUp> getLookUpList() {
        return lookUpList;
    }

    public void setLookUpList(final List<LookUp> lookUpList) {
        this.lookUpList = lookUpList;
    }

    public List<DepartmentLookUp> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(final List<DepartmentLookUp> departmentList) {
        this.departmentList = departmentList;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(final String billType) {
        this.billType = billType;
    }

    public String getBillEntryDate() {
        return billEntryDate;
    }

    public void setBillEntryDate(final String billEntryDate) {
        this.billEntryDate = billEntryDate;
    }

    public String getVendorDesc() {
        return vendorDesc;
    }

    public void setVendorDesc(final String vendorDesc) {
        this.vendorDesc = vendorDesc;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(final BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getInvoiceAmountStr() {
        return invoiceAmountStr;
    }

    public void setInvoiceAmountStr(final String invoiceAmountStr) {
        this.invoiceAmountStr = invoiceAmountStr;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(final String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(final String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(final String orderDate) {
        this.orderDate = orderDate;
    }

    public String getResolutionNumber() {
        return resolutionNumber;
    }

    public void setResolutionNumber(final String resolutionNumber) {
        this.resolutionNumber = resolutionNumber;
    }

    public String getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(final String resolutionDate) {
        this.resolutionDate = resolutionDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public Character getCheckerAuthorization() {
        return checkerAuthorization;
    }

    public void setCheckerAuthorization(final Character checkerAuthorization) {
        this.checkerAuthorization = checkerAuthorization;
    }

    public String getCheckerRemarks() {
        return checkerRemarks;
    }

    public void setCheckerRemarks(final String checkerRemarks) {
        this.checkerRemarks = checkerRemarks;
    }

    public String getAuthorizerEmployee() {
        return authorizerEmployee;
    }

    public void setAuthorizerEmployee(final String authorizerEmployee) {
        this.authorizerEmployee = authorizerEmployee;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLgIpMacAddress() {
        return lgIpMacAddress;
    }

    public void setLgIpMacAddress(final String lgIpMacAddress) {
        this.lgIpMacAddress = lgIpMacAddress;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public List<VendorBillExpDetailDTO> getExpDetListDto() {
        return expDetListDto;
    }

    public void setExpDetListDto(final List<VendorBillExpDetailDTO> expDetListDto) {
        this.expDetListDto = expDetListDto;
    }

    public List<VendorBillDedDetailDTO> getDedDetListDto() {
        return dedDetListDto;
    }

    public void setDedDetListDto(final List<VendorBillDedDetailDTO> dedDetListDto) {
        this.dedDetListDto = dedDetListDto;
    }

    public BigDecimal getDisallowedAmount() {
        return disallowedAmount;
    }

    public void setDisallowedAmount(final BigDecimal disallowedAmount) {
        this.disallowedAmount = disallowedAmount;
    }

    public String getDisallowedRemark() {
        return disallowedRemark;
    }

    public void setDisallowedRemark(final String disallowedRemark) {
        this.disallowedRemark = disallowedRemark;
    }

    public BigDecimal getTotalSanctionedAmount() {
        return totalSanctionedAmount;
    }

    public void setTotalSanctionedAmount(final BigDecimal totalSanctionedAmount) {
        this.totalSanctionedAmount = totalSanctionedAmount;
    }

    public String getSanctionedAmountStr() {
        return sanctionedAmountStr;
    }

    public void setSanctionedAmountStr(final String sanctionedAmountStr) {
        this.sanctionedAmountStr = sanctionedAmountStr;
    }

    public String getActualAmountStr() {
        return actualAmountStr;
    }

    public void setActualAmountStr(final String actualAmountStr) {
        this.actualAmountStr = actualAmountStr;
    }

    public String getDisallowedAmountStr() {
        return disallowedAmountStr;
    }

    public void setDisallowedAmountStr(final String disallowedAmountStr) {
        this.disallowedAmountStr = disallowedAmountStr;
    }

    public Long getBudgetCodeId() {
        return budgetCodeId;
    }

    public void setBudgetCodeId(final Long budgetCodeId) {
        this.budgetCodeId = budgetCodeId;
    }

    public String getDedcutionAmountStr() {
        return dedcutionAmountStr;
    }

    public void setDedcutionAmountStr(final String dedcutionAmountStr) {
        this.dedcutionAmountStr = dedcutionAmountStr;
    }

    public String getNetPayableStr() {
        return netPayableStr;
    }

    public void setNetPayableStr(final String netPayableStr) {
        this.netPayableStr = netPayableStr;
    }

    public BigDecimal getSanctionedAmount() {
        return sanctionedAmount;
    }

    public void setSanctionedAmount(final BigDecimal sanctionedAmount) {
        this.sanctionedAmount = sanctionedAmount;
    }

    public String getExpenditureExistsFlag() {
        return expenditureExistsFlag;
    }

    public void setExpenditureExistsFlag(final String expenditureExistsFlag) {
        this.expenditureExistsFlag = expenditureExistsFlag;
    }

	public String getSalaryBillExitFlag() {
		return salaryBillExitFlag;
	}

	public void setSalaryBillExitFlag(String salaryBillExitFlag) {
		this.salaryBillExitFlag = salaryBillExitFlag;
	}

	public Long getBillIntRefId() {
		return billIntRefId;
	}

	public void setBillIntRefId(Long billIntRefId) {
		this.billIntRefId = billIntRefId;
	}

	public Long getFaYearid() {
		return faYearid;
	}

	public void setFaYearid(Long faYearid) {
		this.faYearid = faYearid;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
    
}
