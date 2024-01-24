
package com.abm.mainet.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AccountDepositBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------

    private Long depId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------

    private String depEntryDate;

    private Date depReceiptdt;

    private String depNo;

    private String depReceiptno;

    private Long dpDeptid;

    private Long cpdStatus;

    private String tempDate;

    private Long cpdSourceType;

    private Long vmVendorid;

    private Long cpdDepositType;

    private String cpdDepositTypeDup;

    private BigDecimal depAmount;

    private BigDecimal depRefundBal;

    private String depReceivedfrom;

    private String depNarration;

    private Long sacHeadId;

    private String dmFlag;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

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

    private String vendorName;

    private String depositAmount;

    private String balanceAmount;

    private String liveModeDate;

    private String statusCodeFlag;

    private String statusCodeValue;

    private String dep_del_flag;

    private String adv_del_flag;

    private List<AccountDepositDto> accountDepositDto = new ArrayList<>();

    private String hasError;

    @Transient
    private String uploadFileName;

    private TbServiceReceiptMasEntity tbServiceReceiptMas;
    
    private String defectLiablityDate;

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the depId
     */
    public Long getDepId() {
        return depId;
    }

    /**
     * @param depId the depId to set
     */
    public void setDepId(final Long depId) {
        this.depId = depId;
    }

    public Date getDepReceiptdt() {
        return depReceiptdt;
    }

    public String getDepEntryDate() {
        return depEntryDate;
    }

    public void setDepEntryDate(String depEntryDate) {
        this.depEntryDate = depEntryDate;
    }

    public void setDepReceiptdt(final Date depReceiptdt) {
        this.depReceiptdt = depReceiptdt;
    }

  

    public String getDepNo() {
		return depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}

	/**
     * @return the depReceiptno
     */
    public String getDepReceiptno() {
        return depReceiptno;
    }

    /**
     * @param depReceiptno the depReceiptno to set
     */
    public void setDepReceiptno(final String depReceiptno) {
        this.depReceiptno = depReceiptno;
    }

    /**
     * @return the depAmount
     */
    public BigDecimal getDepAmount() {
        return depAmount;
    }

    /**
     * @param depAmount the depAmount to set
     */
    public void setDepAmount(final BigDecimal depAmount) {
        this.depAmount = depAmount;
    }

    /**
     * @return the depRefundBal
     */
    public BigDecimal getDepRefundBal() {
        return depRefundBal;
    }

    /**
     * @param depRefundBal the depRefundBal to set
     */
    public void setDepRefundBal(final BigDecimal depRefundBal) {
        this.depRefundBal = depRefundBal;
    }

    /**
     * @return the depReceivedfrom
     */
    public String getDepReceivedfrom() {
        return depReceivedfrom;
    }

    /**
     * @param depReceivedfrom the depReceivedfrom to set
     */
    public void setDepReceivedfrom(final String depReceivedfrom) {
        this.depReceivedfrom = depReceivedfrom;
    }

    /**
     * @return the depNarration
     */
    public String getDepNarration() {
        return depNarration;
    }

    /**
     * @param depNarration the depNarration to set
     */
    public void setDepNarration(final String depNarration) {
        this.depNarration = depNarration;
    }

    /**
     * @return the sacHeadId
     */
    public Long getSacHeadId() {
        return sacHeadId;
    }

    /**
     * @param sacHeadId the sacHeadId to set
     */
    public void setSacHeadId(final Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    /**
     * @return the dmFlag
     */
    public String getDmFlag() {
        return dmFlag;
    }

    /**
     * @param dmFlag the dmFlag to set
     */
    public void setDmFlag(final String dmFlag) {
        this.dmFlag = dmFlag;
    }

    /**
     * @return the orgid
     */
    public Long getOrgid() {
        return orgid;
    }

    /**
     * @param orgid the orgid to set
     */
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the langId
     */
    public Long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final Long langId) {
        this.langId = langId;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    /**
     * @return the fi04N1
     */
    public Long getFi04N1() {
        return fi04N1;
    }

    /**
     * @param fi04n1 the fi04N1 to set
     */
    public void setFi04N1(final Long fi04n1) {
        fi04N1 = fi04n1;
    }

    /**
     * @return the fi04V1
     */
    public String getFi04V1() {
        return fi04V1;
    }

    /**
     * @param fi04v1 the fi04V1 to set
     */
    public void setFi04V1(final String fi04v1) {
        fi04V1 = fi04v1;
    }

    /**
     * @return the fi04D1
     */
    public Date getFi04D1() {
        return fi04D1;
    }

    /**
     * @param fi04d1 the fi04D1 to set
     */
    public void setFi04D1(final Date fi04d1) {
        fi04D1 = fi04d1;
    }

    /**
     * @return the fi04Lo1
     */
    public String getFi04Lo1() {
        return fi04Lo1;
    }

    /**
     * @param fi04Lo1 the fi04Lo1 to set
     */
    public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    @Override
    public String toString() {
        return "AccountDepositBean [depId=" + depId + ", depEntryDate=" + depEntryDate + ", depReceiptdt="
                + depReceiptdt + ", depNo=" + depNo + ", depReceiptno=" + depReceiptno + ", dpDeptid=" + dpDeptid
                + ", cpdStatus=" + cpdStatus + ", cpdSourceType=" + cpdSourceType + ", vmVendorid=" + vmVendorid
                + ", cpdDepositType=" + cpdDepositType + ", depAmount=" + depAmount + ", depRefundBal=" + depRefundBal
                + ", depReceivedfrom=" + depReceivedfrom + ", depNarration=" + depNarration + ", sacHeadId=" + sacHeadId
                + ", dmFlag=" + dmFlag + ", orgid=" + orgid + ", createdBy=" + createdBy + ", createdDate="
                + createdDate + ", updatedBy=" + updatedBy + ", updatedDate=" + updatedDate + ", langId=" + langId
                + ", lgIpMac=" + lgIpMac + ", lgIpMacUpd=" + lgIpMacUpd + ", fi04N1=" + fi04N1 + ", fi04V1=" + fi04V1
                + ", fi04D1=" + fi04D1 + ", fi04Lo1=" + fi04Lo1 + ", accountDepositDto=" + accountDepositDto
                + ", hasError=" + hasError + ", getDepId()=" + getDepId() + ", getDepEntryDate()=" + getDepEntryDate()
                + ", getDepReceiptdt()=" + getDepReceiptdt() + ", getDepNo()=" + getDepNo() + ", getDepReceiptno()="
                + getDepReceiptno() + ", getDepAmount()=" + getDepAmount() + ", getDepRefundBal()=" + getDepRefundBal()
                + ", getDepReceivedfrom()=" + getDepReceivedfrom() + ", getDepNarration()=" + getDepNarration()
                + ", getSacHeadId()=" + getSacHeadId() + ", getDmFlag()=" + getDmFlag() + ", getOrgid()=" + getOrgid()
                + ", getCreatedBy()=" + getCreatedBy() + ", getCreatedDate()=" + getCreatedDate() + ", getUpdatedBy()="
                + getUpdatedBy() + ", getUpdatedDate()=" + getUpdatedDate() + ", getLangId()=" + getLangId()
                + ", getLgIpMac()=" + getLgIpMac() + ", getLgIpMacUpd()=" + getLgIpMacUpd() + ", getFi04N1()="
                + getFi04N1() + ", getFi04V1()=" + getFi04V1() + ", getFi04D1()=" + getFi04D1() + ", getFi04Lo1()="
                + getFi04Lo1() + ", getAccountDepositDto()=" + getAccountDepositDto() + ", getHasError()="
                + getHasError() + ", getVmVendorid()=" + getVmVendorid() + ", getDpDeptid()=" + getDpDeptid()
                + ", getCpdStatus()=" + getCpdStatus() + ", getCpdSourceType()=" + getCpdSourceType()
                + ", getCpdDepositType()=" + getCpdDepositType() + ", getClass()=" + getClass() + ", hashCode()="
                + hashCode() + ", toString()=" + super.toString() + "]";
    }

    public List<AccountDepositDto> getAccountDepositDto() {
        return accountDepositDto;
    }

    public void setAccountDepositDto(final List<AccountDepositDto> accountDepositDto) {
        this.accountDepositDto = accountDepositDto;
    }

    public String getHasError() {
        return hasError;
    }

    public void setHasError(final String hasError) {
        this.hasError = hasError;
    }

    public Long getVmVendorid() {
        return vmVendorid;
    }

    public void setVmVendorid(final Long vmVendorid) {
        this.vmVendorid = vmVendorid;
    }

    public Long getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public Long getCpdStatus() {
        return cpdStatus;
    }

    public void setCpdStatus(final Long cpdStatus) {
        this.cpdStatus = cpdStatus;
    }

    public Long getCpdSourceType() {
        return cpdSourceType;
    }

    public void setCpdSourceType(final Long cpdSourceType) {
        this.cpdSourceType = cpdSourceType;
    }

    /**
     * @return the cpdDepositType
     */
    public Long getCpdDepositType() {
        return cpdDepositType;
    }

    /**
     * @param cpdDepositType the cpdDepositType to set
     */
    public void setCpdDepositType(final Long cpdDepositType) {
        this.cpdDepositType = cpdDepositType;
    }

    /**
     * @return the tempDate
     */
    public String getTempDate() {
        return tempDate;
    }

    /**
     * @param tempDate the tempDate to set
     */
    public void setTempDate(final String tempDate) {
        this.tempDate = tempDate;
    }

    public String getCpdDepositTypeDup() {
        return cpdDepositTypeDup;
    }

    public void setCpdDepositTypeDup(final String cpdDepositTypeDup) {
        this.cpdDepositTypeDup = cpdDepositTypeDup;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(final String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getLiveModeDate() {
        return liveModeDate;
    }

    public void setLiveModeDate(final String liveModeDate) {
        this.liveModeDate = liveModeDate;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(final String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getStatusCodeFlag() {
        return statusCodeFlag;
    }

    public void setStatusCodeFlag(final String statusCodeFlag) {
        this.statusCodeFlag = statusCodeFlag;
    }

    public String getStatusCodeValue() {
        return statusCodeValue;
    }

    public void setStatusCodeValue(String statusCodeValue) {
        this.statusCodeValue = statusCodeValue;
    }

    public String getDep_del_flag() {
        return dep_del_flag;
    }

    public void setDep_del_flag(String dep_del_flag) {
        this.dep_del_flag = dep_del_flag;
    }

    public String getAdv_del_flag() {
        return adv_del_flag;
    }

    public void setAdv_del_flag(String adv_del_flag) {
        this.adv_del_flag = adv_del_flag;
    }

    public TbServiceReceiptMasEntity getTbServiceReceiptMas() {
        return tbServiceReceiptMas;
    }

    public void setTbServiceReceiptMas(TbServiceReceiptMasEntity tbServiceReceiptMas) {
        this.tbServiceReceiptMas = tbServiceReceiptMas;
    }

	public String getDefectLiablityDate() {
		return defectLiablityDate;
	}

	public void setDefectLiablityDate(String defectLiablityDate) {
		this.defectLiablityDate = defectLiablityDate;
	}

    
}
