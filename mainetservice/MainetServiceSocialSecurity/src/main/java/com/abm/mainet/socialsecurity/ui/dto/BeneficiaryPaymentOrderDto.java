package com.abm.mainet.socialsecurity.ui.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;

public class BeneficiaryPaymentOrderDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4852075937444654063L;
    private Long rtgsTransId;
    private Long year;
    private Long month;
    private String beneficiaryName;
    private String beneficiaryNumber;
    private BigDecimal amount;
    private String bankName;
    private String ifscCode;
    private Long accountNumber;
    private boolean checkBox;
    private String remark;
    private String branchName;
    private List<BeneficiaryPaymentOrderDto> dtoList = new ArrayList<>();
    private Date date;
    private String departmentName;
    private Long orgId;
    private Long empId;
    private String ipAddress;
    private Long langId;
    private Long locId;
    private String workOrderNumber;
    private Date workOrdrerDate;
    private Long schemeId;
    private Long paymentScheduleId;
    private String applicationNumber;
    private Long bankId;
    private Date createdDate;
    private ApplicantDetailDTO applicant = new ApplicantDetailDTO();
    private List<DocumentDetailsVO> documentList;
    private String rtgsStatus;
    private String accPostBatchInd;
    private String billNumber;
    private Date lastCertificateDate; // Added by rahul.chaubey

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Long getMonth() {
        return month;
    }

    public void setMonth(Long month) {
        this.month = month;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public List<BeneficiaryPaymentOrderDto> getDtoList() {
        return dtoList;
    }

    public void setDtoList(List<BeneficiaryPaymentOrderDto> dtoList) {
        this.dtoList = dtoList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Date getWorkOrdrerDate() {
        return workOrdrerDate;
    }

    public void setWorkOrdrerDate(Date workOrdrerDate) {
        this.workOrdrerDate = workOrdrerDate;
    }

    public Long getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(Long schemeId) {
        this.schemeId = schemeId;
    }

    public Long getPaymentScheduleId() {
        return paymentScheduleId;
    }

    public void setPaymentScheduleId(Long paymentScheduleId) {
        this.paymentScheduleId = paymentScheduleId;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBeneficiaryNumber() {
        return beneficiaryNumber;
    }

    public void setBeneficiaryNumber(String beneficiaryNumber) {
        this.beneficiaryNumber = beneficiaryNumber;
    }

    public Long getRtgsTransId() {
        return rtgsTransId;
    }

    public void setRtgsTransId(Long rtgsTransId) {
        this.rtgsTransId = rtgsTransId;
    }

    public ApplicantDetailDTO getApplicant() {
        return applicant;
    }

    public void setApplicant(ApplicantDetailDTO applicant) {
        this.applicant = applicant;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public List<DocumentDetailsVO> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<DocumentDetailsVO> documentList) {
        this.documentList = documentList;
    }

    public String getRtgsStatus() {
        return rtgsStatus;
    }

    public void setRtgsStatus(String rtgsStatus) {
        this.rtgsStatus = rtgsStatus;
    }

    public String getAccPostBatchInd() {
        return accPostBatchInd;
    }

    public void setAccPostBatchInd(String accPostBatchInd) {
        this.accPostBatchInd = accPostBatchInd;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

	public Date getLastCertificateDate() {
		return lastCertificateDate;
	}

	public void setLastCertificateDate(Date lastCertificateDate) {
		this.lastCertificateDate = lastCertificateDate;
	}

     
    
}
