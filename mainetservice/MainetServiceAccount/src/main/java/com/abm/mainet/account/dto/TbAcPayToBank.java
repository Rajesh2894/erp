package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TbAcPayToBank implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long ptbId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private Long orgid;

    private Date ptbEntrydate;

    private String ptbEntrydatetemp;

    private Long ptbBankcode;

    private String ptbBankname;

    private String ptbBankbranch;

    private String ptbBankaddress;

    private Long userId;

    private Long langId;

    private Date lmoddate;

    private Long updatedBy;

    private Date updatedDate;

    private String ptbBsrcode;

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

    private Long vmVendorid;

    private Long bankId;

    private Long sacHeadId;

    private Long ptbTdsType;

    private String ptbStatus;

    private String ptbBankAcNo;

    private String acHeadCode;

    private String tdsTypeName;

    private String vendorCode;

    private String statusName;

    /**
     * @return the ptbEntrydatetemp
     */
    public String getPtbEntrydatetemp() {
        return ptbEntrydatetemp;
    }

    /**
     * @param ptbEntrydatetemp the ptbEntrydatetemp to set
     */
    public void setPtbEntrydatetemp(final String ptbEntrydatetemp) {
        this.ptbEntrydatetemp = ptbEntrydatetemp;
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setPtbId(final Long ptbId) {
        this.ptbId = ptbId;
    }

    public Long getPtbId() {
        return ptbId;
    }

    // ----------------------------------------------------------------------
    // GETTERS & SETTERS FOR FIELDS
    // ----------------------------------------------------------------------
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setPtbEntrydate(final Date ptbEntrydate) {
        this.ptbEntrydate = ptbEntrydate;
    }

    public Date getPtbEntrydate() {
        return ptbEntrydate;
    }

    public void setPtbBankcode(final Long ptbBankcode) {
        this.ptbBankcode = ptbBankcode;
    }

    public Long getPtbBankcode() {
        return ptbBankcode;
    }

    public void setPtbBankname(final String ptbBankname) {
        this.ptbBankname = ptbBankname;
    }

    public String getPtbBankname() {
        return ptbBankname;
    }

    public void setPtbBankbranch(final String ptbBankbranch) {
        this.ptbBankbranch = ptbBankbranch;
    }

    public String getPtbBankbranch() {
        return ptbBankbranch;
    }

    public void setPtbBankaddress(final String ptbBankaddress) {
        this.ptbBankaddress = ptbBankaddress;
    }

    public String getPtbBankaddress() {
        return ptbBankaddress;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    public Long getLangId() {
        return langId;
    }

    public void setLmoddate(final Date lmoddate) {
        this.lmoddate = lmoddate;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setPtbBsrcode(final String ptbBsrcode) {
        this.ptbBsrcode = ptbBsrcode;
    }

    public String getPtbBsrcode() {
        return ptbBsrcode;
    }

    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public void setFi04N1(final Long fi04N1) {
        this.fi04N1 = fi04N1;
    }

    public Long getFi04N1() {
        return fi04N1;
    }

    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }

    public Long getVmVendorid() {
        return vmVendorid;
    }

    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(final Long bankId) {
        this.bankId = bankId;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public Long getPtbTdsType() {
        return ptbTdsType;
    }

    public void setPtbTdsType(final Long ptbTdsType) {
        this.ptbTdsType = ptbTdsType;
    }

    public String getPtbStatus() {
        return ptbStatus;
    }

    public void setPtbStatus(final String ptbStatus) {
        this.ptbStatus = ptbStatus;
    }

    public String getPtbBankAcNo() {
        return ptbBankAcNo;
    }

    public void setPtbBankAcNo(final String ptbBankAcNo) {
        this.ptbBankAcNo = ptbBankAcNo;
    }

    public String getAcHeadCode() {
        return acHeadCode;
    }

    public void setAcHeadCode(final String acHeadCode) {
        this.acHeadCode = acHeadCode;
    }

    public String getTdsTypeName() {
        return tdsTypeName;
    }

    public void setTdsTypeName(final String tdsTypeName) {
        this.tdsTypeName = tdsTypeName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(final String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(final String statusName) {
        this.statusName = statusName;
    }

}
