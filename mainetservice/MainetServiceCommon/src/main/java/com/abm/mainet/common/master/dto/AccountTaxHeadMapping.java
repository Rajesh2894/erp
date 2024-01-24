package com.abm.mainet.common.master.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 * @since 20 Aug 2016
 * @comment Table is used to store mapping between tax
 */
@Entity
@Table(name = "TB_AC_TAX_HEADMAPPING")
public class AccountTaxHeadMapping implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2722152652278167639L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "TAXHEAD_ID", precision = 12, scale = 0, nullable = false)
    private long taxheadId;

    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = true)
    private Long taxId;

    @Column(name = "FUND_ID", precision = 12, scale = 0, nullable = true)
    // comments : Fund Master Reference key --TB_AC_FUND_MASTER
    private Long fundId;

    @Column(name = "FUNCTION_ID", precision = 12, scale = 0, nullable = true)
    // comments : Function Master Reference key --TB_AC_FUNCTION_MASTER
    private Long functionId;

    @Column(name = "PAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    // comments : Primary head Master Reference key -TB_AC_PRIMARYHEAD_MASTER
    private Long pacHeadId;

    @Column(name = "SAC_HEAD_ID", precision = 12, scale = 0, nullable = true)
    // comments : Secondary Master Reference key -- tb_ac_secondaryhead_master
    private Long sacHeadId;

    @Column(name = "PAC_HEAD_ID_LIB", precision = 12, scale = 0, nullable = true)
    private Long pacHeadIdLib;

    @Column(name = "SAC_HEAD_ID_LIB", precision = 12, scale = 0, nullable = true)
    private Long sacHeadIdLib;

    @Column(name = "ORGID", precision = 4, scale = 0, nullable = false)
    // comments : Organisation id
    private Long orgid;

    @Column(name = "USER_ID", precision = 7, scale = 0, nullable = false)
    // comments : User Identity
    private Long userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    // comments : Language Identity
    private Long langId;

    @Column(name = "LMODDATE", nullable = false)
    // comments : Last Modification Date
    private Date lmoddate;

    @Column(name = "UPDATED_BY", precision = 7, scale = 0, nullable = true)
    // comments : User id who update the data
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Date on which data is going to update
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine?s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine?s Login Name | IP Address | Physical
    // Address
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

    public long getTaxheadId() {
        return taxheadId;
    }

    public void setTaxheadId(final long taxheadId) {
        this.taxheadId = taxheadId;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(final Long taxId) {
        this.taxId = taxId;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(final Long fundId) {
        this.fundId = fundId;
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(final Long functionId) {
        this.functionId = functionId;
    }

    public Long getPacHeadId() {
        return pacHeadId;
    }

    public void setPacHeadId(final Long pacHeadId) {
        this.pacHeadId = pacHeadId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getPacHeadIdLib() {
        return pacHeadIdLib;
    }

    public void setPacHeadIdLib(final Long pacHeadIdLib) {
        this.pacHeadIdLib = pacHeadIdLib;
    }

    public Long getSacHeadIdLib() {
        return sacHeadIdLib;
    }

    public void setSacHeadIdLib(final Long sacHeadIdLib) {
        this.sacHeadIdLib = sacHeadIdLib;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
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
        return new String[] { "AC", "TB_AC_TAX_HEADMAPPING", "TAXHEAD_ID" };
    }

}