/**
 * 
 */
package com.abm.mainet.asset.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author sarojkumar.yadav
 *
 */
@Entity
@Table(name = "TB_AST_VALUATION_DET")
public class AssetValuationDetails implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3124280217948919980L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "VALUATION_DET_ID", nullable = false)
    private Long valuationDetId;

    @Column(name = "VALUATION_DET_REF_ID", nullable = true)
    private Long valuationDetRefId;

    @Column(name = "ASSET_ID", nullable = true)
    private Long assetId;

    @Column(name = "BOOK_VALUE", nullable = true)
    private BigDecimal bookValue;

    @Column(name = "BOOK_DATE", nullable = true)
    private Date bookDate;

    @Column(name = "BOOK_FINYEAR", nullable = true)
    private Long bookFinYear;

    @Column(name = "BOOK_END_DATE", nullable = true)
    private Date bookEndDate;

    @Column(name = "BOOK_END_VALUE", nullable = true)
    private BigDecimal bookEndValue;

    @Column(name = "CHANGE_TYPE", nullable = true)
    private String changetype;

    @Column(name = "ADDITIONAL_COST", nullable = true)
    private BigDecimal additionalCost;

    @Column(name = "EXPENDITURE_COST", nullable = true)
    private BigDecimal expenditureCost;

    @Column(name = "ACCUM_DEPR_VALUE", nullable = true)
    private BigDecimal accumDeprValue;

    @Column(name = "DEPR_VALUE", nullable = true)
    private BigDecimal deprValue;

    @Column(name = "GROUP_ID", nullable = true)
    private Long groupId;

    @Column(name = "STATE", nullable = true)
    private String state;

    @Column(name = "ORGID", nullable = true)
    private Long orgId;

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

    @Column(name = "BATCH_NO", nullable = true)
    private String batchNo;

    public Long getValuationDetId() {
        return valuationDetId;
    }

    public void setValuationDetId(Long valuationDetId) {
        this.valuationDetId = valuationDetId;
    }

    public Long getValuationDetRefId() {
        return valuationDetRefId;
    }

    public void setValuationDetRefId(Long valuationDetRefId) {
        this.valuationDetRefId = valuationDetRefId;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public BigDecimal getBookValue() {
        return bookValue;
    }

    public void setBookValue(BigDecimal bookValue) {
        this.bookValue = bookValue;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Long getBookFinYear() {
        return bookFinYear;
    }

    public void setBookFinYear(Long bookFinYear) {
        this.bookFinYear = bookFinYear;
    }

    public Date getBookEndDate() {
        return bookEndDate;
    }

    public void setBookEndDate(Date bookEndDate) {
        this.bookEndDate = bookEndDate;
    }

    public BigDecimal getBookEndValue() {
        return bookEndValue;
    }

    public void setBookEndValue(BigDecimal bookEndValue) {
        this.bookEndValue = bookEndValue;
    }

    public String getChangetype() {
        return changetype;
    }

    public void setChangetype(String changetype) {
        this.changetype = changetype;
    }

    public BigDecimal getAdditionalCost() {
        return additionalCost;
    }

    public void setAdditionalCost(BigDecimal additionalCost) {
        this.additionalCost = additionalCost;
    }

    public BigDecimal getExpenditureCost() {
        return expenditureCost;
    }

    public void setExpenditureCost(BigDecimal expenditureCost) {
        this.expenditureCost = expenditureCost;
    }

    public BigDecimal getAccumDeprValue() {
        return accumDeprValue;
    }

    public void setAccumDeprValue(BigDecimal accumDeprValue) {
        this.accumDeprValue = accumDeprValue;
    }

    public BigDecimal getDeprValue() {
        return deprValue;
    }

    public void setDeprValue(BigDecimal deprValue) {
        this.deprValue = deprValue;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Long getOrgId() {
        return orgId;
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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public static String[] getPkValues() {
        return new String[] { MainetConstants.AssetManagement.ASSET_MANAGEMENT, "TB_AST_VALUATION_DET",
                "VALUATION_DET_REF_ID" };
    }
}
