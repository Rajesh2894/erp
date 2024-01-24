/**
 * 
 */
package com.abm.mainet.adh.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author cherupelli.srikanth
 * @since 02 august 2019
 */
@Entity
@Table(name = "TB_ADH_AGENCYMASTER_HIST")
public class AdvertiserMasterHistoryEntity {

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "AGN_ID_H")
    private Long agencyHistId;

    @Column(name = "AGN_ID")
    private Long agencyId;

    @Column(name = "AGN_LIC_NO", length = 40, nullable = false)
    private String agencyLicNo;

    @Column(name = "AGN_NAME", length = 200, nullable = false)
    private String agencyName;

    @Column(name = "AGN_CATEGORY", length = 11, nullable = false)
    private Long agencyCategory;

    @Column(name = "AGN_OWNERS", length = 200, nullable = false)
    private String agencyOwner;

    @Column(name = "AGN_EMAIL", length = 50, nullable = false)
    private String agencyEmail;

    @Column(name = "PAN_NUMBER", length = 20, nullable = true)
    private String panNumber;

    @Column(name = "UID_NO", length = 12, nullable = true)
    private Long uidNo;

    @Column(name = "GST_NO", length = 15, nullable = true)
    private String gstNo;

    @Column(name = "AGN_CONTACT_NO", length = 40, nullable = false)
    private String agencyContactNo;

    @Column(name = "AGN_ADD", length = 400, nullable = false)
    private String agencyAdd;

    @Column(name = "AGN_LIC_ISSUE_DATE", length = 40, nullable = false)
    private Date agencyLicIssueDate;

    @Column(name = "AGN_LIC_FROM_DATE", nullable = true)
    private Date agencyLicFromDate;

    @Column(name = "AGN_LIC_TO_DATE", nullable = true)
    private Date agencyLicToDate;

    @Column(name = "AGN_REMARK", length = 400, nullable = true)
    private String agencyRemark;

    @Column(name = "AGN_OLD_LIC_NO", length = 40, nullable = true)
    private String agencyOldLicNo;

    @Column(name = "APM_APPLICATION_ID", length = 16, nullable = true)
    private Long apmApplicationId;

    @Column(name = "AGN_STATUS", length = 1, nullable = false)
    private String agencyStatus;

    @Column(name = "ORGID", length = 12, nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", length = 12, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    private String lgIpMac;

    @Column(name = "LANG_ID", length = 12, nullable = false)
    private Long langId;

    @Column(name = "UPDATED_BY", length = 12, nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "cancellation_Date")
    private Date cancellationDate;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    public Long getAgencyId() {
	return agencyId;
    }

    public void setAgencyId(Long agencyId) {
	this.agencyId = agencyId;
    }

    public String getAgencyLicNo() {
	return agencyLicNo;
    }

    public void setAgencyLicNo(String agencyLicNo) {
	this.agencyLicNo = agencyLicNo;
    }

    public String getAgencyName() {
	return agencyName;
    }

    public void setAgencyName(String agencyName) {
	this.agencyName = agencyName;
    }

    public Long getAgencyCategory() {
	return agencyCategory;
    }

    public void setAgencyCategory(Long agencyCategory) {
	this.agencyCategory = agencyCategory;
    }

    public String getAgencyOwner() {
	return agencyOwner;
    }

    public void setAgencyOwner(String agencyOwner) {
	this.agencyOwner = agencyOwner;
    }

    public String getAgencyEmail() {
	return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
	this.agencyEmail = agencyEmail;
    }

    public String getPanNumber() {
	return panNumber;
    }

    public void setPanNumber(String panNumber) {
	this.panNumber = panNumber;
    }

    public Long getUidNo() {
	return uidNo;
    }

    public void setUidNo(Long uidNo) {
	this.uidNo = uidNo;
    }

    public String getGstNo() {
	return gstNo;
    }

    public void setGstNo(String gstNo) {
	this.gstNo = gstNo;
    }

    public String getAgencyContactNo() {
	return agencyContactNo;
    }

    public void setAgencyContactNo(String agencyContactNo) {
	this.agencyContactNo = agencyContactNo;
    }

    public String getAgencyAdd() {
	return agencyAdd;
    }

    public void setAgencyAdd(String agencyAdd) {
	this.agencyAdd = agencyAdd;
    }

    public Date getAgencyLicIssueDate() {
	return agencyLicIssueDate;
    }

    public void setAgencyLicIssueDate(Date agencyLicIssueDate) {
	this.agencyLicIssueDate = agencyLicIssueDate;
    }

    public Date getAgencyLicFromDate() {
	return agencyLicFromDate;
    }

    public void setAgencyLicFromDate(Date agencyLicFromDate) {
	this.agencyLicFromDate = agencyLicFromDate;
    }

    public Date getAgencyLicToDate() {
	return agencyLicToDate;
    }

    public void setAgencyLicToDate(Date agencyLicToDate) {
	this.agencyLicToDate = agencyLicToDate;
    }

    public String getAgencyRemark() {
	return agencyRemark;
    }

    public void setAgencyRemark(String agencyRemark) {
	this.agencyRemark = agencyRemark;
    }

    public String getAgencyOldLicNo() {
	return agencyOldLicNo;
    }

    public void setAgencyOldLicNo(String agencyOldLicNo) {
	this.agencyOldLicNo = agencyOldLicNo;
    }

    public Long getApmApplicationId() {
	return apmApplicationId;
    }

    public void setApmApplicationId(Long apmApplicationId) {
	this.apmApplicationId = apmApplicationId;
    }

    public String getAgencyStatus() {
	return agencyStatus;
    }

    public void setAgencyStatus(String agencyStatus) {
	this.agencyStatus = agencyStatus;
    }

    public Long getOrgId() {
	return orgId;
    }

    public void setOrgId(Long orgId) {
	this.orgId = orgId;
    }

    public Long getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getLgIpMac() {
	return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
	this.lgIpMac = lgIpMac;
    }

    public Long getLangId() {
	return langId;
    }

    public void setLangId(Long langId) {
	this.langId = langId;
    }

    public Long getUpdatedBy() {
	return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
	this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
	return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
	this.updatedDate = updatedDate;
    }

    public String getLgIpMacUpd() {
	return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
	this.lgIpMacUpd = lgIpMacUpd;
    }

    public String gethStatus() {
	return hStatus;
    }

    public Long getAgencyHistId() {
	return agencyHistId;
    }

    public void setAgencyHistId(Long agencyHistId) {
	this.agencyHistId = agencyHistId;
    }

    public void sethStatus(String hStatus) {
	this.hStatus = hStatus;
    }

    public Date getCancellationDate() {
	return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
	this.cancellationDate = cancellationDate;
    }

    public String getCancellationReason() {
	return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
	this.cancellationReason = cancellationReason;
    }

    public String[] getPkValues() {
	return new String[] { "ADH", "TB_ADH_AGENCYMASTER_HIST", "AGN_ID" };
    }

}
