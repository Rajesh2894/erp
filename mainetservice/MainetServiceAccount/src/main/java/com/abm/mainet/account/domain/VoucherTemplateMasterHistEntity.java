package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_VOUCHERTEMPLATE_MAS_HIST")
public class VoucherTemplateMasterHistEntity implements Serializable {
    private static final long serialVersionUID = -8835376204295412827L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "H_TEMPLATE_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long htemplateId;
    @Column(name = "TEMPLATE_ID", precision = 12, scale = 0, nullable = false)
    private long templateId;

    @Column(name = "CPD_ID_MAPPING_TYPE", precision = 12, scale = 0, nullable = true)
    // comments : Permanent / Financial year wise prifix -"MTP"
    private Long templateType;

    @Column(name = "FA_YEARID", precision = 12, scale = 0, nullable = true)
    // comments : Financial Year ID
    private Long financialYear;

    @Column(name = "CPD_ID_VOUCHER_TYPE", precision = 12, scale = 0, nullable = true)
    // comments : Prefix "VOT"
    private Long voucherType;

    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    // comments : fk -TB_DEPARTMENT
    private Long department;

    @Column(name = "CPD_ID_TEMPLATE_FOR", precision = 12, scale = 0, nullable = true)
    // comments : Prefix "TDP"
    private Long templateFor;

    @Column(name = "CPD_ID_STATUS_FLG", precision = 12, scale = 0, nullable = false)
    // comments : Prefix - 'ACN'
    private Long status;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Organisation id
    private Long orgid;

    @Column(name = "CREATED_BY", precision = 12, scale = 0, nullable = false)
    // comments : User Identity
    private Long createdBy;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    // comments : Language Identity
    private Long langId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Last Modification Date
    private Date createdDate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = false)
    // comments : Client Machine?s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine?s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", precision = 15, scale = 0, nullable = true)
    // comments : Additional number FI04_N1 to be used in future
    private Long fi04N1;

    @Column(name = "FI04_V1", length = 100, nullable = true)
    // comments : Additional nvarchar2 FI04_V1 to be used in future
    private String fi04V1;

    @Column(name = "FI04_D1", nullable = true)
    // comments : Additional Date FI04_D1 to be used in future
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1, nullable = true)
    // comments : Additional Logical field FI04_LO1 to be used in future
    private String fi04Lo1;

    @Column(name = "H_STATUS", length = 1)
    private Character hStatus;

    public String[] getPkValues() {
        return new String[] { "COM", "TB_AC_VOUCHERTEMPLATE_MAS_HIST", "H_TEMPLATE_ID" };
    }

    public long getHtemplateId() {
        return htemplateId;
    }

    public void setHtemplateId(long htemplateId) {
        this.htemplateId = htemplateId;
    }

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateType() {
        return templateType;
    }

    public void setTemplateType(Long templateType) {
        this.templateType = templateType;
    }

    public Long getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Long financialYear) {
        this.financialYear = financialYear;
    }

    public Long getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(Long voucherType) {
        this.voucherType = voucherType;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long department) {
        this.department = department;
    }

    public Long getTemplateFor() {
        return templateFor;
    }

    public void setTemplateFor(Long templateFor) {
        this.templateFor = templateFor;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(Long langId) {
        this.langId = langId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public Character gethStatus() {
        return hStatus;
    }

    public void sethStatus(Character hStatus) {
        this.hStatus = hStatus;
    }

}
