package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author kavali.kiran
 * @since 23 Jun 2014
 */
@Entity
@Table(name = "TB_OFFLINE_MAS")
public class OfflineMaster extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "OFL_ID", precision = 12, scale = 0, nullable = false)
    // comments : OFFLINE Master Primary Key Id
    private long oflId;

    @Column(name = "CHALLAN_NO", precision = 12, scale = 0, nullable = false)
    // comments : challan number
    private Long challanNo;

    @Column(name = "SERVICE_ID", precision = 12, scale = 0, nullable = false)
    // comments : service id from service master
    private Long serviceId;

    @Column(name = "OFL_PAYMENT_MODE", precision = 12, scale = 0, nullable = false)
    // comments : offline payment mode from OFL prefix
    private long oflPaymentMode;

    @Column(name = "APPL_NO", precision = 12, scale = 0, nullable = false)
    // comments : application number
    private Long applNo;

    @Column(name = "DD_NO", length = 100, nullable = true)
    // comments : demand draft number
    private String ddNo;

    @Column(name = "DD_DATE", nullable = true)
    // comments : demand draft date
    private Date ddDate;

    @Column(name = "PO_NO", length = 100, nullable = true)
    // comments : postal order number
    private String poNo;

    @Column(name = "PO_DATE", nullable = true)
    // comments : postal order date
    private Date poDate;

    @Column(name = "BANKA_ACC_ID", precision = 12, scale = 0, nullable = true)
    // comments : bank account id for OFL @ Bank
    private Long bankaAccId;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    // comments : Record Deletion flag - value N non-deleted record and Y- deleted record
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    // comments : Organization Id.
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    // comments : User Id
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    // comments : Language Id
    private int langId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Created Date
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = false)
    // comments : Modified By
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Modification Date
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    @Column(name = "MOBILE_NO", length = 100, nullable = true)
    private String mobileNumber;

    @Column(name = "EMAIL_ID", length = 100, nullable = true)
    private String emailId;

    @Column(name = "APPLICANTNAME", length = 100, nullable = true)
    private String applicantName;

    @Transient
    private String offlinePaymentText;

    @Transient
    private String onlineOfflineCheck;

    @Transient
    private Long cbBankId;		// holds IFSC code

    @Transient
    private Long bmBankAccountId;	// holds Account No

    @Transient
    private Long payModeIn;		// holds Payment Mode

    @Transient
    private String bmDrawOn;		// Bank name

    @Transient
    private Long bmChqDDNo;		// hold cheque No

    @Transient
    private Date bmChqDDDate;	// hold cheque date

    @Transient
    private String amountToPay;

    @Transient
    private String hiddencbBankId;

    @Transient
    private String hideBankId;

    @Transient
    private String hidebmBankAccountId;

    @Transient
    private String hidebmChqDDNo;

    @Transient
    private String hidebmChqDDDate;

    /**
     * @return the oflId
     */
    public long getOflId() {
        return oflId;
    }

    /**
     * @param oflId the oflId to set
     */
    public void setOflId(final long oflId) {
        this.oflId = oflId;
    }

    /**
     * @return the challanNo
     */
    public Long getChallanNo() {
        return challanNo;
    }

    /**
     * @param challanNo the challanNo to set
     */
    public void setChallanNo(final Long challanNo) {
        this.challanNo = challanNo;
    }

    /**
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the oflPaymentMode
     */
    public long getOflPaymentMode() {
        return oflPaymentMode;
    }

    /**
     * @param oflPaymentMode the oflPaymentMode to set
     */
    public void setOflPaymentMode(final long oflPaymentMode) {
        this.oflPaymentMode = oflPaymentMode;
    }

    /**
     * @return the applNo
     */
    public Long getApplNo() {
        return applNo;
    }

    /**
     * @param applNo the applNo to set
     */
    public void setApplNo(final Long applNo) {
        this.applNo = applNo;
    }

    /**
     * @return the ddNo
     */
    public String getDdNo() {
        return ddNo;
    }

    /**
     * @param ddNo the ddNo to set
     */
    public void setDdNo(final String ddNo) {
        this.ddNo = ddNo;
    }

    /**
     * @return the ddDate
     */
    public Date getDdDate() {
        return ddDate;
    }

    /**
     * @param ddDate the ddDate to set
     */
    public void setDdDate(final Date ddDate) {
        this.ddDate = ddDate;
    }

    /**
     * @return the poNo
     */
    public String getPoNo() {
        return poNo;
    }

    /**
     * @param poNo the poNo to set
     */
    public void setPoNo(final String poNo) {
        this.poNo = poNo;
    }

    /**
     * @return the poDate
     */
    public Date getPoDate() {
        return poDate;
    }

    /**
     * @param poDate the poDate to set
     */
    public void setPoDate(final Date poDate) {
        this.poDate = poDate;
    }

    /**
     * @return the bankaAccId
     */
    public Long getBankaAccId() {
        return bankaAccId;
    }

    /**
     * @param bankaAccId the bankaAccId to set
     */
    public void setBankaAccId(final Long bankaAccId) {
        this.bankaAccId = bankaAccId;
    }

    /**
     * @return the isDeleted
     */
    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted the isDeleted to set
     */
    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    @Override
    public long getId() {
        return getOflId();
    }

    /**
     * @param offlinePaymentText the offlinePaymentText to set
     */
    public void setOfflinePaymentText(final String offlinePaymentText) {
        this.offlinePaymentText = offlinePaymentText;
    }

    /**
     * @return the offlinePaymentText
     */
    public String getOfflinePaymentText() {
        return offlinePaymentText;
    }

    /**
     * @return the onlineOfflineCheck
     */
    public String getOnlineOfflineCheck() {
        return onlineOfflineCheck;
    }

    /**
     * @param onlineOfflineCheck the onlineOfflineCheck to set
     */
    public void setOnlineOfflineCheck(final String onlineOfflineCheck) {
        this.onlineOfflineCheck = onlineOfflineCheck;
    }

    /**
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(final String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the applicantName
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * @param applicantName the applicantName to set
     */
    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public String getPaymentMode() {
        return getNonHierarchicalLookUpObject(getOflPaymentMode()).getLookUpCode();
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "COM", "TB_OFFLINE_MAS", "OFL_ID" };
    }

    /**
     * @return the cbBankId
     */
    public Long getCbBankId() {
        return cbBankId;
    }

    /**
     * @param cbBankId the cbBankId to set
     */
    public void setCbBankId(final Long cbBankId) {
        this.cbBankId = cbBankId;
    }

    /**
     * @return the bmDrawOn
     */
    public String getBmDrawOn() {
        return bmDrawOn;
    }

    /**
     * @param bmDrawOn the bmDrawOn to set
     */
    public void setBmDrawOn(final String bmDrawOn) {
        this.bmDrawOn = bmDrawOn;
    }

    /**
     * @return the bmChqDDNo
     */
    public Long getBmChqDDNo() {
        return bmChqDDNo;
    }

    /**
     * @param bmChqDDNo the bmChqDDNo to set
     */
    public void setBmChqDDNo(final Long bmChqDDNo) {
        this.bmChqDDNo = bmChqDDNo;
    }

    /**
     * @return the bmChqDDDate
     */
    public Date getBmChqDDDate() {
        return bmChqDDDate;
    }

    /**
     * @param bmChqDDDate the bmChqDDDate to set
     */
    public void setBmChqDDDate(final Date bmChqDDDate) {
        this.bmChqDDDate = bmChqDDDate;
    }

    public Long getPayModeIn() {
        return payModeIn;
    }

    public void setPayModeIn(final Long payModeIn) {
        this.payModeIn = payModeIn;
    }

    public Long getBmBankAccountId() {
        return bmBankAccountId;
    }

    public void setBmBankAccountId(final Long bmBankAccountId) {
        this.bmBankAccountId = bmBankAccountId;
    }

    public String getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(final String amountToPay) {
        this.amountToPay = amountToPay;
    }

    public String getHiddencbBankId() {
        return hiddencbBankId;
    }

    public void setHiddencbBankId(final String hiddencbBankId) {
        this.hiddencbBankId = hiddencbBankId;
    }

    public String getHideBankId() {
        return hideBankId;
    }

    public void setHideBankId(final String hideBankId) {
        this.hideBankId = hideBankId;
    }

    public String getHidebmBankAccountId() {
        return hidebmBankAccountId;
    }

    public void setHidebmBankAccountId(final String hidebmBankAccountId) {
        this.hidebmBankAccountId = hidebmBankAccountId;
    }

    public String getHidebmChqDDNo() {
        return hidebmChqDDNo;
    }

    public void setHidebmChqDDNo(final String hidebmChqDDNo) {
        this.hidebmChqDDNo = hidebmChqDDNo;
    }

    public String getHidebmChqDDDate() {
        return hidebmChqDDDate;
    }

    public void setHidebmChqDDDate(final String hidebmChqDDDate) {
        this.hidebmChqDDDate = hidebmChqDDDate;
    }

}