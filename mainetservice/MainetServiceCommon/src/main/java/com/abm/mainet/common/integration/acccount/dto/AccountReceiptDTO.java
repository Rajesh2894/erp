package com.abm.mainet.common.integration.acccount.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.abm.mainet.common.utility.LookUp;
import com.fasterxml.jackson.annotation.JsonIgnore;
@XmlRootElement(name = "AccountReceiptDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountReceiptDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class AccountReceiptDTO implements Serializable {

    private static final long serialVersionUID = 7962955595884665363L;

    /**
     * Search Grid Data Area will be Showing Required All Fields.
     */
    @XmlElement(name="receiptId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long receiptId;
    @XmlElement(name="receiptNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String receiptNumber;
    @XmlElement(name="receiptNo",namespace="http://service.soap.account.mainet.abm.com/")
    private String receiptNo;
    @XmlElement(name="receiptDate",namespace="http://service.soap.account.mainet.abm.com/")
    private String receiptDate;
    @XmlElement(name="receiptAmount",namespace="http://service.soap.account.mainet.abm.com/")
    private String receiptAmount;
    @XmlElement(name="rmReceiptAmt",namespace="http://service.soap.account.mainet.abm.com/")
    private BigDecimal rmReceiptAmt;
    @XmlElement(name="receiptPayeeName",namespace="http://service.soap.account.mainet.abm.com/")
    private String receiptPayeeName;
    @XmlElement(name="statusId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long statusId;
    @XmlElement(name="vendorStatus",namespace="http://service.soap.account.mainet.abm.com/")
    private Long vendorStatus;
    @XmlElement(name="finYearId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long finYearId;
    @XmlElement(name="fieldId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long fieldId;
    @XmlElement(name="mobileNumber",namespace="http://service.soap.account.mainet.abm.com/")
    private String mobileNumber;
    @XmlElement(name="emailId",namespace="http://service.soap.account.mainet.abm.com/")
    private String emailId;
    /**
     * Store Create/Save Area Data will be Showing Required Fields.
     */
    @XmlElement(name="vmVendorId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long vmVendorId;
    @XmlElement(name="rmNarration",namespace="http://service.soap.account.mainet.abm.com/")
    private String rmNarration;
    @XmlElement(name="cpdFeemode",namespace="http://service.soap.account.mainet.abm.com/")
    private Long cpdFeemode;
    @XmlElement(name="baAccountId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long baAccountId;

    @XmlElement(name="vendorListDetail",namespace="http://service.soap.account.mainet.abm.com/")
    private List<VendorDTO> vendorListDetail;
    @XmlElement(name="departmentLookUp",namespace="http://service.soap.account.mainet.abm.com/")
    List<LookUp> departmentLookUp;
    @XmlElement(name="custBankLookUp",namespace="http://service.soap.account.mainet.abm.com/")
    List<LookUp> custBankLookUp;
    @XmlElement(name="payeeLookUp",namespace="http://service.soap.account.mainet.abm.com/")
    List<String> payeeLookUp;
    @XmlElement(name="bankAcList",namespace="http://service.soap.account.mainet.abm.com/")
    private Map<Long, String> bankAcList = new LinkedHashMap<>();
    @XmlElement(name="customerBankMap",namespace="http://service.soap.account.mainet.abm.com/")
    private Map<Long, String> customerBankMap = new LinkedHashMap<>();
    // private List<CustomerBankDTO> customerBankMap;
    @XmlElement(name="budgetHeadList",namespace="http://service.soap.account.mainet.abm.com/")
    private Map<Long, String> budgetHeadList = new LinkedHashMap<>();

    /**
     * Store All Area Data will be Showing Required Fields.
     */
    @XmlElement(name="orgId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long orgId;
    @XmlElement(name="createdBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long createdBy;
    @XmlElement(name="langId",namespace="http://service.soap.account.mainet.abm.com/")
    private int langId;
    @XmlElement(name="createdDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date createdDate;
    @XmlElement(name="updatedBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long updatedBy;
    @XmlElement(name="updatedDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date updatedDate;
    @Size(max = 100)
    @XmlElement(name="lgIpMac",namespace="http://service.soap.account.mainet.abm.com/")
    private String lgIpMac;
    @Size(max = 100)
    @XmlElement(name="lgIpMacUpd",namespace="http://service.soap.account.mainet.abm.com/")
    private String lgIpMacUpd;

    /**
     * Store View Area Data will be Showing Required Fields.
     */
    @XmlElement(name="receiptFeeDetail",namespace="http://service.soap.account.mainet.abm.com/")
    private List<SrcptFeesDetDTO> receiptFeeDetail;
    @XmlElement(name="receiptModeDetail",namespace="http://service.soap.account.mainet.abm.com/")
    private SrcptModesDetDTO receiptModeDetail;

    @XmlElement(name="feeAmountStr",namespace="http://service.soap.account.mainet.abm.com/")
    private String feeAmountStr;
    @XmlElement(name="vmVendorIdDesc",namespace="http://service.soap.account.mainet.abm.com/")
    private String vmVendorIdDesc;
    @XmlElement(name="dpDeptId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long dpDeptId;
    @XmlElement(name="status",namespace="http://service.soap.account.mainet.abm.com/")
    private String status;
    @XmlElement(name="templateExistsFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String templateExistsFlag;
    @XmlElement(name="budgetCodeStatus",namespace="http://service.soap.account.mainet.abm.com/")
    private String budgetCodeStatus;

    @XmlElement(name="recProperyTaxFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String recProperyTaxFlag;
    @XmlElement(name="recPropertytaxRefId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long recPropertytaxRefId;
    
    /**
     * financialYearId column is required to getting Voucher template form receipt entry to voucher posting in bill type is "C" - Current
     */
    @XmlElement(name="financialYearId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long financialYearId; //optional
    
    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(final Long receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(final String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(final String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(final String receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public String getReceiptPayeeName() {
        return receiptPayeeName;
    }

    public void setReceiptPayeeName(final String receiptPayeeName) {
        this.receiptPayeeName = receiptPayeeName;
    }

    public Long getVmVendorId() {
        return vmVendorId;
    }

    public void setVmVendorId(final Long vmVendorId) {
        this.vmVendorId = vmVendorId;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(final String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public Long getCpdFeemode() {
        return cpdFeemode;
    }

    public void setCpdFeemode(final Long cpdFeemode) {
        this.cpdFeemode = cpdFeemode;
    }

    public Long getBaAccountId() {
        return baAccountId;
    }

    public void setBaAccountId(final Long baAccountId) {
        this.baAccountId = baAccountId;
    }

    public List<LookUp> getDepartmentLookUp() {
        return departmentLookUp;
    }

    public void setDepartmentLookUp(final List<LookUp> departmentLookUp) {
        this.departmentLookUp = departmentLookUp;
    }

    public List<LookUp> getCustBankLookUp() {
        return custBankLookUp;
    }

    public void setCustBankLookUp(final List<LookUp> custBankLookUp) {
        this.custBankLookUp = custBankLookUp;
    }

    public List<String> getPayeeLookUp() {
        return payeeLookUp;
    }

    public void setPayeeLookUp(final List<String> payeeLookUp) {
        this.payeeLookUp = payeeLookUp;
    }

    public Map<Long, String> getBankAcList() {
        return bankAcList;
    }

    public void setBankAcList(final Map<Long, String> bankAcList) {
        this.bankAcList = bankAcList;
    }

    public Map<Long, String> getBudgetHeadList() {
        return budgetHeadList;
    }

    public void setBudgetHeadList(final Map<Long, String> budgetHeadList) {
        this.budgetHeadList = budgetHeadList;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getFeeAmountStr() {
        return feeAmountStr;
    }

    public void setFeeAmountStr(final String feeAmountStr) {
        this.feeAmountStr = feeAmountStr;
    }

    public String getVmVendorIdDesc() {
        return vmVendorIdDesc;
    }

    public void setVmVendorIdDesc(final String vmVendorIdDesc) {
        this.vmVendorIdDesc = vmVendorIdDesc;
    }

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(final Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public List<SrcptFeesDetDTO> getReceiptFeeDetail() {
        return receiptFeeDetail;
    }

    public void setReceiptFeeDetail(final List<SrcptFeesDetDTO> receiptFeeDetail) {
        this.receiptFeeDetail = receiptFeeDetail;
    }

    public SrcptModesDetDTO getReceiptModeDetail() {
        return receiptModeDetail;
    }

    public void setReceiptModeDetail(final SrcptModesDetDTO receiptModeDetail) {
        this.receiptModeDetail = receiptModeDetail;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(final Long statusId) {
        this.statusId = statusId;
    }

    public Long getVendorStatus() {
        return vendorStatus;
    }

    public void setVendorStatus(final Long vendorStatus) {
        this.vendorStatus = vendorStatus;
    }

    public Long getFinYearId() {
        return finYearId;
    }

    public void setFinYearId(final Long finYearId) {
        this.finYearId = finYearId;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    /*
     * public List<CustomerBankDTO> getcustomerBankMap() { return customerBankMap; } public void
     * setcustomerBankMap(List<CustomerBankDTO> customerBankMap) { this.customerBankMap = customerBankMap; }
     */
    public Map<Long, String> getcustomerBankMap() {
        return customerBankMap;
    }

    public void setcustomerBankMap(final Map<Long, String> customerBankMap) {
        this.customerBankMap = customerBankMap;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getTemplateExistsFlag() {
        return templateExistsFlag;
    }

    public void setTemplateExistsFlag(final String templateExistsFlag) {
        this.templateExistsFlag = templateExistsFlag;
    }

    public String getBudgetCodeStatus() {
        return budgetCodeStatus;
    }

    public void setBudgetCodeStatus(final String budgetCodeStatus) {
        this.budgetCodeStatus = budgetCodeStatus;
    }

    public List<VendorDTO> getVendorListDetail() {
        return vendorListDetail;
    }

    public void setVendorListDetail(final List<VendorDTO> vendorListDetail) {
        this.vendorListDetail = vendorListDetail;
    }

    public BigDecimal getRmReceiptAmt() {
        return rmReceiptAmt;
    }

    public void setRmReceiptAmt(BigDecimal rmReceiptAmt) {
        this.rmReceiptAmt = rmReceiptAmt;
    }

	public String getRecProperyTaxFlag() {
		return recProperyTaxFlag;
	}

	public void setRecProperyTaxFlag(String recProperyTaxFlag) {
		this.recProperyTaxFlag = recProperyTaxFlag;
	}

	public Long getRecPropertytaxRefId() {
		return recPropertytaxRefId;
	}

	public void setRecPropertytaxRefId(Long recPropertytaxRefId) {
		this.recPropertytaxRefId = recPropertytaxRefId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Long getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Long financialYearId) {
		this.financialYearId = financialYearId;
	}

	@Override
	public String toString() {
		return "AccountReceiptDTO [receiptId=" + receiptId + ", receiptNumber=" + receiptNumber + ", receiptNo="
				+ receiptNo + ", receiptDate=" + receiptDate + ", receiptAmount=" + receiptAmount + ", rmReceiptAmt="
				+ rmReceiptAmt + ", receiptPayeeName=" + receiptPayeeName + ", statusId=" + statusId + ", vendorStatus="
				+ vendorStatus + ", finYearId=" + finYearId + ", fieldId=" + fieldId + ", mobileNumber=" + mobileNumber
				+ ", emailId=" + emailId + ", vmVendorId=" + vmVendorId + ", rmNarration=" + rmNarration
				+ ", cpdFeemode=" + cpdFeemode + ", baAccountId=" + baAccountId + ", vendorListDetail="
				+ vendorListDetail + ", departmentLookUp=" + departmentLookUp + ", custBankLookUp=" + custBankLookUp
				+ ", payeeLookUp=" + payeeLookUp + ", bankAcList=" + bankAcList + ", customerBankMap=" + customerBankMap
				+ ", budgetHeadList=" + budgetHeadList + ", orgId=" + orgId + ", createdBy=" + createdBy + ", langId="
				+ langId + ", createdDate=" + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate
				+ ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", receiptFeeDetail=" + receiptFeeDetail
				+ ", receiptModeDetail=" + receiptModeDetail + ", feeAmountStr=" + feeAmountStr + ", vmVendorIdDesc="
				+ vmVendorIdDesc + ", dpDeptId=" + dpDeptId + ", status=" + status + ", templateExistsFlag="
				+ templateExistsFlag + ", budgetCodeStatus=" + budgetCodeStatus + ", recProperyTaxFlag="
				+ recProperyTaxFlag + ", recPropertytaxRefId=" + recPropertytaxRefId + ", financialYearId="
				+ financialYearId + "]";
	}

}
