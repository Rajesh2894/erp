/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_AST_RETIRE")
public class AssetRetire implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4249661153634953160L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RETIRE_ID", nullable = false)
    private Long retireId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSET_ID", referencedColumnName = "ASSET_ID", nullable = false, updatable = false)
    private AssetInformation assetId;

    @Column(name = "DISPOSITION_METHOD", nullable = true)
    private Long dispositionMethod;

    @Column(name = "DISPOSITION_DATE", nullable = true)
    private Date dispositionDate;

    @Column(name = "DOC_DATE", nullable = true)
    private Date docDate;

    @Column(name = "POST_DATE", nullable = true)
    private Date postDate;

    @Column(name = "CAPITAL_GAIN", nullable = true)
    private Long capitalGain;

    @Column(name = "CAPITAL_TAX", nullable = true)
    private BigDecimal capitalTax;

    @Column(name = "CAPITAL_CHART_OF_ACCOUNT", nullable = true)
    private Long capitalChartOfAccount;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

    @Column(name = "RETIRE_FLAG", nullable = false)
    private String retireFlag;

    @Column(name = "CREATION_DATE", nullable = true)
    private Date creationDate;

    @Column(name = "CREATED_BY", nullable = true)
    private Long createdBy;

    @Column(name = "UPDATED_BY", nullable = true)
    private Long updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", nullable = true)
    private String lgIpMacUpd;

    @Column(name = "SOLD_PERSON_ADDR", nullable = true)
    private String addressToWhomSold;

    @Column(name = "SOLD_PERSON_NAME", nullable = true)
    private String personToWhomSold;

    @Column(name = "PAY_MODE", nullable = true)
    private Long payMode;

    @Column(name = "AMOUNT", nullable = true)
    private BigDecimal amount;

    @Column(name = "DISPOSAL_ORDER_NO", nullable = true)
    private String disOrderNo;

    @Column(name = "AUTH_STATUS", nullable = true)
    private String authStatus;

    @Column(name = "AUTH_BY", nullable = true)
    private Long authBy;

    @Column(name = "AUTH_DATE", nullable = true)
    private Date authDate;

    @Column(name = "NON_FUNCTIONAL_DATE", nullable = false)
    private Date nonfucDate;

    @Column(name = "REMARKS", nullable = false)
    private String remarks;

    @Column(name = "DISPOSAL_ORDER_DATE", nullable = false)
    private Date DisOrderDate;

    @Column(name = "BANKID", nullable = false)
    private Long bankId;

    @Column(name = "RD_CHEQUEDDDATE", nullable = false)
    private Date chequeDate;

    @Column(name = "RD_CHEQUEDDNO", nullable = false)
    private Long chequeNo;

    @Column(name = "DEPT_CODE", length = 5, nullable = false)
    private String deptCode;

    public Long getRetireId() {
        return retireId;
    }

    public void setRetireId(Long retireId) {
        this.retireId = retireId;
    }

    public AssetInformation getAssetId() {
        return assetId;
    }

    public void setAssetId(AssetInformation assetId) {
        this.assetId = assetId;
    }

    public Long getDispositionMethod() {
        return dispositionMethod;
    }

    public void setDispositionMethod(Long dispositionMethod) {
        this.dispositionMethod = dispositionMethod;
    }

    public Date getDispositionDate() {
        return dispositionDate;
    }

    public void setDispositionDate(Date dispositionDate) {
        this.dispositionDate = dispositionDate;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Long getCapitalGain() {
        return capitalGain;
    }

    public void setCapitalGain(Long capitalGain) {
        this.capitalGain = capitalGain;
    }

    public BigDecimal getCapitalTax() {
        return capitalTax;
    }

    public void setCapitalTax(BigDecimal capitalTax) {
        this.capitalTax = capitalTax;
    }

    public Long getCapitalChartOfAccount() {
        return capitalChartOfAccount;
    }

    public void setCapitalChartOfAccount(Long capitalChartOfAccount) {
        this.capitalChartOfAccount = capitalChartOfAccount;
    }

    public Long getOrgId() {
        return orgId;
    }

    public String getRetireFlag() {
        return retireFlag;
    }

    public void setRetireFlag(String retireFlag) {
        this.retireFlag = retireFlag;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
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

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_RETIRE", "RETIRE_ID" };
    }

    public String getAddressToWhomSold() {
        return addressToWhomSold;
    }

    public void setAddressToWhomSold(String addressToWhomSold) {
        this.addressToWhomSold = addressToWhomSold;
    }

    public String getPersonToWhomSold() {
        return personToWhomSold;
    }

    public void setPersonToWhomSold(String personToWhomSold) {
        this.personToWhomSold = personToWhomSold;
    }

    public Long getPayMode() {
        return payMode;
    }

    public void setPayMode(Long payMode) {
        this.payMode = payMode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(String authStatus) {
        this.authStatus = authStatus;
    }

    public Long getAuthBy() {
        return authBy;
    }

    public void setAuthBy(Long authBy) {
        this.authBy = authBy;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public Date getNonfucDate() {
        return nonfucDate;
    }

    public void setNonfucDate(Date nonfucDate) {
        this.nonfucDate = nonfucDate;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDisOrderNo() {
        return disOrderNo;
    }

    public void setDisOrderNo(String disOrderNo) {
        this.disOrderNo = disOrderNo;
    }

    public Date getDisOrderDate() {
        return DisOrderDate;
    }

    public void setDisOrderDate(Date disOrderDate) {
        DisOrderDate = disOrderDate;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Long getChequeNo() {
        return chequeNo;
    }

    public void setChequeNo(Long chequeNo) {
        this.chequeNo = chequeNo;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

}
