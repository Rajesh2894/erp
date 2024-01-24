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
 * @since 18 Jan 2017
 */
@Entity
@Table(name = "TB_BANK_MASTER ")
public class BankMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BANKID", precision = 12, scale = 0, nullable = false)
    private Long bankId;

    @Column(name = "BANK", length = 500, nullable = true)
    private String bank;

    @Column(name = "IFSC", length = 11, nullable = true)
    private String ifsc;

    @Column(name = "MICR", length = 9, nullable = true)
    private String micr;

    @Column(name = "BRANCH", length = 200, nullable = true)
    private String branch;

    @Column(name = "ADDRESS", length = 1000, nullable = true)
    private String address;

    @Column(name = "CONTACT", length = 200, nullable = true)
    private String contact;

    @Column(name = "CITY", length = 100, nullable = true)
    private String city;

    @Column(name = "DISTRICT", length = 100, nullable = true)
    private String district;

    @Column(name = "STATE", length = 100, nullable = true)
    private String state;

    @Column(name = "CREATED_BY", precision = 7, scale = 0, nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date lmodDate;

    @Column(name = "UPDATED_BY", nullable = false, updatable = false)
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

    @Column(name = "BANK_STATUS", length = 1, nullable = true)
    private String bankStatus;

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(final String bank) {
        this.bank = bank;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(final String ifsc) {
        this.ifsc = ifsc;
    }

    public String getMicr() {
        return micr;
    }

    public void setMicr(final String micr) {
        this.micr = micr;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(final String branch) {
        this.branch = branch;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(final String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(final String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
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

    public String getBankStatus() {
        return bankStatus;
    }

    public void setBankStatus(final String bankStatus) {
        this.bankStatus = bankStatus;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("BankMasterEntity [bankId=");
        builder.append(bankId);
        builder.append(", bank=");
        builder.append(bank);
        builder.append(", ifsc=");
        builder.append(ifsc);
        builder.append(", micr=");
        builder.append(micr);
        builder.append(", branch=");
        builder.append(branch);
        builder.append(", address=");
        builder.append(address);
        builder.append(", contact=");
        builder.append(contact);
        builder.append(", city=");
        builder.append(city);
        builder.append(", district=");
        builder.append(district);
        builder.append(", state=");
        builder.append(state);
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
        builder.append(", =");
        builder.append(bankStatus);
        builder.append("]");
        return builder.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AC", "TB_BANK_MASTER", "BANKID" };
    }

}