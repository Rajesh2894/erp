package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 * @since 19 Jan 2017
 */
@Entity
@Table(name = "TB_ULB_BANK")
public class UlbBankMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ULB_BANKID", precision = 12, scale = 0, nullable = false)
    private Long ulbBankId;

    @Column(name = "BANKID", precision = 12, scale = 0, nullable = true)
    private Long bankId;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = false)
    private Long langId;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "FI04_N1", precision = 15, scale = 0, nullable = true)
    private Long fi04N1;

    @Column(name = "FI04_V1", length = 100, nullable = true)
    private String fi04V1;

    @Column(name = "FI04_D1", nullable = true)
    private Date fi04D1;

    @Column(name = "FI04_LO1", length = 1, nullable = true)
    private String fi04Lo1;

    @Column(name = "BMSTATUS")
    private String bmStatus;

    @Column(name = "CPD_ID_BANKTYPE", nullable = false)
    private Long cpdIdBankType;

    /*
     * @OneToMany(mappedBy = "ulbBankId", targetEntity = BankAccountMasterEntity.class, fetch = FetchType.LAZY, cascade =
     * CascadeType.ALL) private List<BankAccountMasterEntity> bankAccountMasterEntities;
     */

    public Long getUlbBankId() {
        return ulbBankId;
    }

    public void setUlbBankId(final Long ulbBankId) {
        this.ulbBankId = ulbBankId;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
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

    public Date getLmodDate() {
        return lmodDate;
    }

    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
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

    public Long getLangId() {
        return langId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
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

    public String getBmStatus() {
        return bmStatus;
    }

    public void setBmStatus(final String bmStatus) {
        this.bmStatus = bmStatus;
    }

    public Long getCpdIdBankType() {
        return cpdIdBankType;
    }

    public void setCpdIdBankType(final Long cpdIdBankType) {
        this.cpdIdBankType = cpdIdBankType;
    }

    /*
     * public List<BankAccountMasterEntity> getBankAccountMasterEntities() { return bankAccountMasterEntities; }
     */
    /*
     * public void setBankAccountMasterEntities(final List<BankAccountMasterEntity> bankAccountMasterEntities) {
     * this.bankAccountMasterEntities = bankAccountMasterEntities; }
     */

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UlbBankMasterEntity [ulbBankId=");
        builder.append(ulbBankId);
        builder.append(", bankId=");
        builder.append(bankId);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", lmodDate=");
        builder.append(lmodDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", langId=");
        builder.append(langId);
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

    public String[] getPkValues() {
        return new String[] { "AC", "TB_ULB_BANK", "ULB_BANKID" };
    }

}