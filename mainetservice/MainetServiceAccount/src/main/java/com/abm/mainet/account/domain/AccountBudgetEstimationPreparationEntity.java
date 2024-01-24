
package com.abm.mainet.account.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "TB_AC_BUDGETORY_ESTIMATE")

public class AccountBudgetEstimationPreparationEntity implements Serializable {

    private static final long serialVersionUID = 8683880608834172574L;

    // ----------------------------------------------------------------------
    // ENTITY PRIMARY KEY ( BASED ON A SINGLE FIELD )
    // ----------------------------------------------------------------------
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BUGEST_ID", nullable = false)
    private Long bugestId;

    // ----------------------------------------------------------------------
    // ENTITY DATA FIELDS
    // ----------------------------------------------------------------------
    @Column(name = "NFA_YEARID")
    private Long nextFaYearid;

    @Column(name = "ESTIMATE_FOR_NEXTYEAR")
    private BigDecimal estimateForNextyear;

    @Column(name = "APPR_BUG_STAND_COM")
    private BigDecimal apprBugStandCom;

    @Column(name = "FINALIZED_BUG_GEN_BODY")
    private BigDecimal finalizedBugGenBody;

    @Column(name = "ORGID", nullable = false)
    private Long orgid;

    @Column(name = "LANG_ID", nullable = false)
    private int langId;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUpd;

    @Column(name = "FIELD_ID")
    private Long fieldId;

    @Column(name = "FI04_V1")
    private String fi04V1;

    @Temporal(TemporalType.DATE)
    @Column(name = "FI04_D1")
    private Date fi04D1;

   /* @Column(name = "FI04_LO1", length = 1)
    private String fi04Lo1; // this filed is rename as below(Find_ID) filed*/
    
    @Column(name = "FUND_ID",nullable=true)
    private Long fundId;
    
    @Column(name = "REMARK",nullable=true)
    private String remark;

    
	@Column(name = "FA_YEARID")
    private Long faYearid;

    @Column(name = "CPD_BUGTYPE_ID", nullable = false)
    private Long cpdBugtypeId;

    @Column(name = "DP_DEPTID", nullable = false)
    private Long dpDeptid;

    @Column(name = "CPD_BUGSUBTYPE_ID", nullable = false)
    private Long cpdBugsubtypeId;

    @ManyToOne
    @JoinColumn(name = "BUDGETCODE_ID", referencedColumnName = "BUDGETCODE_ID")
    private AccountBudgetCodeEntity tbAcBudgetCodeMaster;

    // ----------------------------------------------------------------------
    // CONSTRUCTOR(S)
    // ----------------------------------------------------------------------
    public AccountBudgetEstimationPreparationEntity() {
        super();
    }

    // ----------------------------------------------------------------------
    // GETTER & SETTER FOR THE KEY FIELD
    // ----------------------------------------------------------------------
    public void setBugestId(final Long bugestId) {
        this.bugestId = bugestId;
    }

    public Long getBugestId() {
        return bugestId;
    }

    public Long getNextFaYearid() {
        return nextFaYearid;
    }

    public void setNextFaYearid(Long nextFaYearid) {
        this.nextFaYearid = nextFaYearid;
    }

    // --- DATABASE MAPPING : ESTIMATE_FOR_NEXTYEAR ( NUMBER )
    public void setEstimateForNextyear(final BigDecimal estimateForNextyear) {
        this.estimateForNextyear = estimateForNextyear;
    }

    public BigDecimal getEstimateForNextyear() {
        return estimateForNextyear;
    }

    // --- DATABASE MAPPING : APPR_BUG_STAND_COM ( NUMBER )
    public void setApprBugStandCom(final BigDecimal apprBugStandCom) {
        this.apprBugStandCom = apprBugStandCom;
    }

    public BigDecimal getApprBugStandCom() {
        return apprBugStandCom;
    }

    // --- DATABASE MAPPING : FINALIZED_BUG_GEN_BODY ( NUMBER )
    public void setFinalizedBugGenBody(final BigDecimal finalizedBugGenBody) {
        this.finalizedBugGenBody = finalizedBugGenBody;
    }

    public BigDecimal getFinalizedBugGenBody() {
        return finalizedBugGenBody;
    }

    // --- DATABASE MAPPING : ORGID ( NUMBER )
    public void setOrgid(final Long orgid) {
        this.orgid = orgid;
    }

    public Long getOrgid() {
        return orgid;
    }

    // --- DATABASE MAPPING : LANG_ID ( NUMBER )
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    public int getLangId() {
        return langId;
    }

    // --- DATABASE MAPPING : CREATED_BY ( NUMBER )
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    // --- DATABASE MAPPING : CREATED_DATE ( DATE )
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    // --- DATABASE MAPPING : UPDATED_BY ( NUMBER )
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    // --- DATABASE MAPPING : UPDATED_DATE ( DATE )
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    // --- DATABASE MAPPING : LG_IP_MAC ( VARCHAR2 )
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return lgIpMac;
    }

    // --- DATABASE MAPPING : LG_IP_MAC_UPD ( VARCHAR2 )
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	// --- DATABASE MAPPING : FI04_V1 ( NVARCHAR2 )
    public void setFi04V1(final String fi04V1) {
        this.fi04V1 = fi04V1;
    }

    public String getFi04V1() {
        return fi04V1;
    }

    // --- DATABASE MAPPING : FI04_D1 ( DATE )
    public void setFi04D1(final Date fi04D1) {
        this.fi04D1 = fi04D1;
    }

    public Date getFi04D1() {
        return fi04D1;
    }

    // --- DATABASE MAPPING : FI04_LO1 ( CHAR )
    /*public void setFi04Lo1(final String fi04Lo1) {
        this.fi04Lo1 = fi04Lo1;
    }

    public String getFi04Lo1() {
        return fi04Lo1;
    }*/
    

    /**
     * @return the faYearid
     */
    public Long getFaYearid() {
        return faYearid;
    }

    public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
     * @param faYearid the faYearid to set
     */
    public void setFaYearid(final Long faYearid) {
        this.faYearid = faYearid;
    }

    /**
     * @return the cpdBugtypeId
     */
    public Long getCpdBugtypeId() {
        return cpdBugtypeId;
    }

    /**
     * @param cpdBugtypeId the cpdBugtypeId to set
     */
    public void setCpdBugtypeId(final Long cpdBugtypeId) {
        this.cpdBugtypeId = cpdBugtypeId;
    }

    /**
     * @return the dpDeptid
     */
    public Long getDpDeptid() {
        return dpDeptid;
    }

    /**
     * @param dpDeptid the dpDeptid to set
     */
    public void setDpDeptid(final Long dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public static String[] getPkValues() {
        return new String[] { "AC", "TB_AC_BUDGETORY_ESTIMATE", "BUGEST_ID" };
    }

    /**
     * @return the cpdBugsubtypeId
     */
    public Long getCpdBugsubtypeId() {
        return cpdBugsubtypeId;
    }

    /**
     * @param cpdBugsubtypeId the cpdBugsubtypeId to set
     */
    public void setCpdBugsubtypeId(final Long cpdBugsubtypeId) {
        this.cpdBugsubtypeId = cpdBugsubtypeId;
    }

    /**
     * @return the tbAcBudgetCodeMaster
     */
    public AccountBudgetCodeEntity getTbAcBudgetCodeMaster() {
        return tbAcBudgetCodeMaster;
    }

    /**
     * @param tbAcBudgetCodeMaster the tbAcBudgetCodeMaster to set
     */
    public void setTbAcBudgetCodeMaster(
            final AccountBudgetCodeEntity tbAcBudgetCodeMaster) {
        this.tbAcBudgetCodeMaster = tbAcBudgetCodeMaster;
    }

    // ----------------------------------------------------------------------
    // toString METHOD
    // ----------------------------------------------------------------------
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(bugestId);
        sb.append("]:");
        sb.append(nextFaYearid);
        sb.append("|");
        sb.append(cpdBugtypeId);
        sb.append("|");
        sb.append(dpDeptid);
        sb.append("|");
        sb.append(cpdBugsubtypeId);
        sb.append("|");
        sb.append(faYearid);
        sb.append("|");
        sb.append(estimateForNextyear);
        sb.append("|");
        sb.append(apprBugStandCom);
        sb.append("|");
        sb.append(finalizedBugGenBody);
        sb.append("|");
        sb.append(orgid);
        sb.append("|");
        sb.append(langId);
        sb.append("|");
        sb.append(createdBy);
        sb.append("|");
        sb.append(createdDate);
        sb.append("|");
        sb.append(updatedBy);
        sb.append("|");
        sb.append(updatedDate);
        sb.append("|");
        sb.append(lgIpMac);
        sb.append("|");
        sb.append(lgIpMacUpd);
        sb.append("|");
        sb.append(fieldId);
        sb.append("|");
        sb.append(fi04V1);
        sb.append("|");
        sb.append(fi04D1);
        sb.append("|");
        sb.append("fundId");
        return sb.toString();
    }

}
