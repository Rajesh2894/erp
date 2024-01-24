package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Vivek.Kumar
 * @since 17 Jan 2017
 */
@Entity
@Table(name = "TB_AC_VOUCHERTEMPLATE_MAS")
public class VoucherTemplateMasterEntity implements Serializable {

    private static final long serialVersionUID = -6192443187061562136L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TEMPLATE_ID", precision = 12, scale = 0, nullable = false)
    // comments : Primary Key
    private long templateId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "templateId", cascade = CascadeType.ALL)
    private List<VoucherTemplateDetailEntity> templateDetailEntities = new ArrayList<>(0);

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

    public long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(final long templateId) {
        this.templateId = templateId;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
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

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String[] getPkValues() {
        return new String[] { "COM", "TB_AC_VOUCHERTEMPLATE_MAS", "TEMPLATE_ID" };
    }

    public List<VoucherTemplateDetailEntity> getTemplateDetailEntities() {
        return templateDetailEntities;
    }

    public void setTemplateDetailEntities(final List<VoucherTemplateDetailEntity> templateDetailEntities) {
        this.templateDetailEntities = templateDetailEntities;
    }

    public Long getTemplateType() {
        return templateType;
    }

    public void setTemplateType(final Long templateType) {
        this.templateType = templateType;
    }

    public Long getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(final Long financialYear) {
        this.financialYear = financialYear;
    }

    public Long getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(final Long voucherType) {
        this.voucherType = voucherType;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(final Long department) {
        this.department = department;
    }

    public Long getTemplateFor() {
        return templateFor;
    }

    public void setTemplateFor(final Long templateFor) {
        this.templateFor = templateFor;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(final Long status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("VoucherTemplateMasterEntity [templateId=");
        builder.append(templateId);
        builder.append(", templateType=");
        builder.append(templateType);
        builder.append(", financialYear=");
        builder.append(financialYear);
        builder.append(", voucherType=");
        builder.append(voucherType);
        builder.append(", department=");
        builder.append(department);
        builder.append(", templateFor=");
        builder.append(templateFor);
        builder.append(", status=");
        builder.append(status);
        builder.append(", orgid=");
        builder.append(orgid);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", langId=");
        builder.append(langId);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append(", fi04N1=");
        builder.append(fi04N1);
        builder.append(", fi04V1=");
        builder.append(fi04V1);
        builder.append(", fi04D1=");
        builder.append(fi04D1);
        builder.append(", fi04Lo1=");
        builder.append(fi04Lo1);
        builder.append("]");
        return builder.toString();
    }

}