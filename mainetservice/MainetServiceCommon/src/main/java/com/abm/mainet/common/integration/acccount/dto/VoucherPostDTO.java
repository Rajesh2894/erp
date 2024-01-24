package com.abm.mainet.common.integration.acccount.dto;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author Vivek.Kumar
 * @since 06 Feb 2017
 */
@XmlRootElement(name = "VoucherPostDTO", namespace = "http://service.soap.account.mainet.abm.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoucherPostDTO", namespace = "http://service.soap.account.mainet.abm.com/")
public class VoucherPostDTO {
    // voucherType,voucherSubTypeId, departmentId, voucherReferenceNo these all making surrogate key
    // receipt date/payment entry date //mandatory
	@XmlElement(name="voucherDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date voucherDate;
	
    // can be RV,PV,CV,JV //it is only used for account module not by external system
	@XmlElement(name="voucherType",namespace="http://service.soap.account.mainet.abm.com/")
    private String voucherType;
   
	// from TDP prefix Ex- Receipt, Deposit Slip, Bill Entry, Demand Posting and etc. //mandatory
	@XmlElement(name="voucherSubTypeId",namespace="http://service.soap.account.mainet.abm.com/" ,required=true)
    private Long voucherSubTypeId;
	
	//mandatory
	@XmlElement(name="departmentId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long departmentId;
   
	// like receipt No/bill no etc.//mandatory
	@XmlElement(name="voucherReferenceNo",namespace="http://service.soap.account.mainet.abm.com/")
    private String voucherReferenceNo;
	
	//mandatory
	@XmlElement(name="voucherReferenceDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date voucherReferenceDate; 
	
	//mandatory
	@XmlElement(name="narration",namespace="http://service.soap.account.mainet.abm.com/")
    private String narration;
	
	//optional
	@XmlElement(name="payerOrPayee",namespace="http://service.soap.account.mainet.abm.com/")
    private String payerOrPayee;
    
	// set location of logged-in employee from userSession //mandatory
	@XmlElement(name="fieldId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long fieldId;
	
	//mandatory
	@XmlElement(name="orgId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long orgId;
	
	//mandatory
	@XmlElement(name="createdBy",namespace="http://service.soap.account.mainet.abm.com/")
    private Long createdBy;
	//mandatory //In account module needs to set date
	@XmlElement(name="createdDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date createdDate;
	//optional //it is only used for account module.
	@XmlElement(name="langId",namespace="http://service.soap.account.mainet.abm.com/")
    private int langId; // optional
	//mandatory
    @Size(max = 100)
	@XmlElement(name="lgIpMac",namespace="http://service.soap.account.mainet.abm.com/")
    private String lgIpMac;

    //mandatory
    //Internal or External transaction Identification.
	@XmlElement(name="entryType",namespace="http://service.soap.account.mainet.abm.com/")
    private String entryType; 

	//optional
	//this is using only account voucher authorization forms 
	@XmlElement(name="billVouPostingDate",namespace="http://service.soap.account.mainet.abm.com/")
    private Date billVouPostingDate;

	//optional
	//this is using only account voucher authorization forms
	@XmlElement(name="payEntryMakerFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String payEntryMakerFlag;

	//optional
    /*
     * Y if authorization required else N It is used only by Account Department decision will be taken from AUT Prefix if any
     * voucher(RV,PV,CV etc) found in AUT then checker-maker is applicable else not applicable
     */
    @XmlElement(name="authFlag",namespace="http://service.soap.account.mainet.abm.com/")
    private String authFlag;

    //In future we can remove it.
    /**
     * set as PN-permanent, FYW-financial year wise if templateType is PN then no need to set financialYearId but if templateType
     * is FYW then financialYearId is mandatory to set
     */
    @XmlElement(name="templateType",namespace="http://service.soap.account.mainet.abm.com/")
    private String templateType; //optional only for account module use.
    
    //this is applicable for only receivable demand posting.
    @XmlElement(name="financialYearId",namespace="http://service.soap.account.mainet.abm.com/")
    private Long financialYearId; //optional

    //In future we can remove it.
    //optional - in property tax (GRP) it is mandatory -> based on the column the logic will be work.
    //@XmlElement(name="propertyIntFlag",namespace="http://service.soap.account.mainet.abm.com/")
    //private String propertyIntFlag; 
    
    //mandatory
    /**
     * this field is mandatory in case of RV,CV and PV posting so this id need to be send from caller in master form level end
     */
    //mandatory in all external call - in property tax (GRP) it is mandatory -> based on the column the logic will be work.
	@XmlElement(name="payModeId",namespace="http://service.soap.account.mainet.abm.com/") // not in asset management module
    private Long payModeId;
	
	//To Identify the voucher posting where both level sacheadId is required.
	@XmlElement(name="entryFlag",namespace="http://service.soap.account.mainet.abm.com/")
	private String entryFlag; //optional
	
    @XmlElement(name="voucherDetails",namespace="http://service.soap.account.mainet.abm.com/")
    private List<VoucherPostDetailDTO> voucherDetails;

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(final Date voucherDate) {
        this.voucherDate = voucherDate;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final String voucherType) {
        this.voucherType = voucherType;
    }

    public Long getVoucherSubTypeId() {
        return voucherSubTypeId;
    }

    public void setVoucherSubTypeId(final Long voucherSubTypeId) {
        this.voucherSubTypeId = voucherSubTypeId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(final Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getVoucherReferenceNo() {
        return voucherReferenceNo;
    }

    public void setVoucherReferenceNo(final String voucherReferenceNo) {
        this.voucherReferenceNo = voucherReferenceNo;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(final String narration) {
        this.narration = narration;
    }

    public String getPayerOrPayee() {
        return payerOrPayee;
    }

    public void setPayerOrPayee(final String payerOrPayee) {
        this.payerOrPayee = payerOrPayee;
    }

    public Long getFieldId() {
        return fieldId;
    }

    public void setFieldId(final Long fieldId) {
        this.fieldId = fieldId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getLangId() {
        return langId;
    }

    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getAuthFlag() {
        return authFlag;
    }

    public void setAuthFlag(final String authFlag) {
        this.authFlag = authFlag;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final String templateType) {
        this.templateType = templateType;
    }

    public Long getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(final Long financialYearId) {
        this.financialYearId = financialYearId;
    }

    public List<VoucherPostDetailDTO> getVoucherDetails() {
        return voucherDetails;
    }

    public void setVoucherDetails(final List<VoucherPostDetailDTO> voucherDetails) {
        this.voucherDetails = voucherDetails;
    }

    public Date getVoucherReferenceDate() {
        return voucherReferenceDate;
    }

    public void setVoucherReferenceDate(final Date voucherReferenceDate) {
        this.voucherReferenceDate = voucherReferenceDate;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(final String entryType) {
        this.entryType = entryType;
    }

    public Date getBillVouPostingDate() {
        return billVouPostingDate;
    }

    public void setBillVouPostingDate(Date billVouPostingDate) {
        this.billVouPostingDate = billVouPostingDate;
    }

    public String getPayEntryMakerFlag() {
        return payEntryMakerFlag;
    }

    public void setPayEntryMakerFlag(String payEntryMakerFlag) {
        this.payEntryMakerFlag = payEntryMakerFlag;
    }

	/*public String getPropertyIntFlag() {
		return propertyIntFlag;
	}

	public void setPropertyIntFlag(String propertyIntFlag) {
		this.propertyIntFlag = propertyIntFlag;
	}*/
	
	public Long getPayModeId() {
		return payModeId;
	}

	public void setPayModeId(Long payModeId) {
		this.payModeId = payModeId;
	}

	public String getEntryFlag() {
		return entryFlag;
	}

	public void setEntryFlag(String entryFlag) {
		this.entryFlag = entryFlag;
	}

	@Override
	public String toString() {
		return "VoucherPostDTO [voucherDate=" + voucherDate + ", voucherType=" + voucherType + ", voucherSubTypeId="
				+ voucherSubTypeId + ", departmentId=" + departmentId + ", voucherReferenceNo=" + voucherReferenceNo
				+ ", voucherReferenceDate=" + voucherReferenceDate + ", narration=" + narration + ", payerOrPayee="
				+ payerOrPayee + ", fieldId=" + fieldId + ", orgId=" + orgId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", langId=" + langId + ", lgIpMac=" + lgIpMac + ", entryType="
				+ entryType + ", billVouPostingDate=" + billVouPostingDate + ", payEntryMakerFlag=" + payEntryMakerFlag
				+ ", payModeId=" + payModeId + ", authFlag=" + authFlag + ", financialYearId=" + financialYearId
				+ ", entryFlag=" + entryFlag
				+ ", voucherDetails=" + voucherDetails + "]";
	}

}
