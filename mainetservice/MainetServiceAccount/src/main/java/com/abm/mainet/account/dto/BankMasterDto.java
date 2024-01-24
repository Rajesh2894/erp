
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author hiren.poriya
 *
 */
public class BankMasterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bankId;
    private String bank;
    private String ifsc;
    private String micr;
    private String branch;
    private String address;
    private String contact;
    private String city;
    private String district;
    private String state;
    private Long createdBy;
    private Date lmodDate;
    private Long updatedBy;
    private Date updatedDate;
    private Long langId;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMac;
    @JsonIgnore
    @Size(max = 100)
    private String lgIpMacUpd;
    private Long fi04N1;
    private String fi04V1;
    private Date fi04D1;
    private String fi04Lo1;

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

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

}
