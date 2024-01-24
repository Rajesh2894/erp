package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_as_bill_det")
public class MainBillDetEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3634944082389848669L;

    @Id
    @Column(name = "BD_BILLDETID")
    private long bdBilldetid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BM_IDNO")
    private MainBillMasEntity bmIdNo;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "REBATE_ID")
    private Long rebateId;

    @Column(name = "ADJUSTMENT_ID")
    private Long adjustmentId;

    @Column(name = "BD_CUR_TAXAMT")
    private Double bdCurTaxamt;

    @Column(name = "BD_CUR_BAL_TAXAMT")
    private Double bdCurBalTaxamt;

    @Column(name = "BD_PRV_ARRAMT")
    private Double bdPrvArramt;

    @Column(name = "BD_CUR_TAXAMT_PRINT")
    private Double bdCurTaxamtPrint;

    @Column(name = "BD_PRV_BAL_ARRAMT")
    private Double bdPrvBalArramt;

    @Column(name = "BD_BILLFLAG")
    private String bdBillflag;

    @Column(name = "orgid")
    private Long orgid;

    @Column(name = "CREATED_BY")
    private Long userId;

    @Column(name = "CREATED_DATE")
    private Date lmoddate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "TAX_CATEGORY")
    private Long taxCategory;

    @Column(name = "COLL_SEQ")
    private Long collSeq;

    @Column(name = "BD_CUR_TAXAMT_AUTH")
    private Double bdCurTaxamtAuth;

    @Column(name = "BD_CUR_BAL_TAXAMT_AUTH")
    private Double bdCurBalTaxamtAuth;

    @Column(name = "BD_PRV_ARRAMT_AUTH")
    private Double bdPrvArramtAuth;

    @Column(name = "BD_PRV_BAL_ARRAMT_AUTH")
    private Double bdPrvBalArramtAuth;

    @Column(name = "MN_BM_FORMULA")
    private String formula;

    @Column(name = "MN_BM_RULE")
    private String ruleId;

    @Column(name = "MN_BM_BRMSTAX")
    private Double baseRate;
    
    @Column(name = "MN_PER_RATE")
    private Double percentageRate;
    
    @Column(name = "Revised_bill_date")
	private Date revisedBillDate;
	
	@Column(name = "Revised_bill_type")
	private String RevisedBillType;
	
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;
	
	@Column(name = "bill_gen_penalty")
	private Double billGenPenalty;

    public long getBdBilldetid() {
        return bdBilldetid;
    }

    public void setBdBilldetid(long bdBilldetid) {
        this.bdBilldetid = bdBilldetid;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getRebateId() {
        return rebateId;
    }

    public void setRebateId(Long rebateId) {
        this.rebateId = rebateId;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Double getBdCurTaxamt() {
        return bdCurTaxamt;
    }

    public void setBdCurTaxamt(Double bdCurTaxamt) {
        this.bdCurTaxamt = bdCurTaxamt;
    }

    public Double getBdCurBalTaxamt() {
        return bdCurBalTaxamt;
    }

    public void setBdCurBalTaxamt(Double bdCurBalTaxamt) {
        this.bdCurBalTaxamt = bdCurBalTaxamt;
    }

    public Double getBdPrvArramt() {
        return bdPrvArramt;
    }

    public void setBdPrvArramt(Double bdPrvArramt) {
        this.bdPrvArramt = bdPrvArramt;
    }

    public Double getBdCurTaxamtPrint() {
        return bdCurTaxamtPrint;
    }

    public void setBdCurTaxamtPrint(Double bdCurTaxamtPrint) {
        this.bdCurTaxamtPrint = bdCurTaxamtPrint;
    }

    public Double getBdPrvBalArramt() {
        return bdPrvBalArramt;
    }

    public void setBdPrvBalArramt(Double bdPrvBalArramt) {
        this.bdPrvBalArramt = bdPrvBalArramt;
    }

    public String getBdBillflag() {
        return bdBillflag;
    }

    public void setBdBillflag(String bdBillflag) {
        this.bdBillflag = bdBillflag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getLmoddate() {
        return lmoddate;
    }

    public void setLmoddate(Date lmoddate) {
        this.lmoddate = lmoddate;
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

    public Long getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Long getCollSeq() {
        return collSeq;
    }

    public void setCollSeq(Long collSeq) {
        this.collSeq = collSeq;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public MainBillMasEntity getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(MainBillMasEntity bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public Double getBdCurTaxamtAuth() {
        return bdCurTaxamtAuth;
    }

    public void setBdCurTaxamtAuth(Double bdCurTaxamtAuth) {
        this.bdCurTaxamtAuth = bdCurTaxamtAuth;
    }

    public Double getBdCurBalTaxamtAuth() {
        return bdCurBalTaxamtAuth;
    }

    public void setBdCurBalTaxamtAuth(Double bdCurBalTaxamtAuth) {
        this.bdCurBalTaxamtAuth = bdCurBalTaxamtAuth;
    }

    public Double getBdPrvArramtAuth() {
        return bdPrvArramtAuth;
    }

    public void setBdPrvArramtAuth(Double bdPrvArramtAuth) {
        this.bdPrvArramtAuth = bdPrvArramtAuth;
    }

    public Double getBdPrvBalArramtAuth() {
        return bdPrvBalArramtAuth;
    }

    public void setBdPrvBalArramtAuth(Double bdPrvBalArramtAuth) {
        this.bdPrvBalArramtAuth = bdPrvBalArramtAuth;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Double getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }

	public Double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(Double percentageRate) {
		this.percentageRate = percentageRate;
	}

	public Date getRevisedBillDate() {
		return revisedBillDate;
	}

	public void setRevisedBillDate(Date revisedBillDate) {
		this.revisedBillDate = revisedBillDate;
	}

	public String getRevisedBillType() {
		return RevisedBillType;
	}

	public void setRevisedBillType(String revisedBillType) {
		RevisedBillType = revisedBillType;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}

	public Double getBillGenPenalty() {
		return billGenPenalty;
	}

	public void setBillGenPenalty(Double billGenPenalty) {
		this.billGenPenalty = billGenPenalty;
	}
    
	
}
