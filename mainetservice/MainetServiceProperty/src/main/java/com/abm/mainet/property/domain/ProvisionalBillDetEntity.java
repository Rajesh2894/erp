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
import javax.persistence.Transient;

@Entity
@Table(name = "TB_AS_PRO_BILL_DET")
public class ProvisionalBillDetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*
     * @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
     * @GeneratedValue(generator = "MyCustomGenerator")
     */
    @Column(name = "pro_bd_billdetid", nullable = false)
    private long bdBilldetid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bm_idno", nullable = false)
    private ProvisionalBillMasEntity bmIdNo;

    @Column(name = "tax_id", nullable = false)
    private Long taxId;

    @Column(name = "rebate_id")
    private Long rebateId;

    @Column(name = "adjustment_id")
    private Long adjustmentId;

    @Column(name = "pro_bd_cur_bal_taxamt")
    private Double bdCurBalTaxamt;

    @Column(name = "pro_bd_cur_taxamt")
    private Double bdCurTaxamt;

    @Column(name = "pro_bd_cur_taxamt_print")
    private Double bdCurTaxamtPrint;

    @Column(name = "pro_bd_prv_arramt")
    private Double bdPrvArramt;

    @Column(name = "pro_bd_prv_bal_arramt")
    private Double bdPrvBalArramt;

    @Column(name = "pro_bd_billflag")
    private String bdBillflag;

    @Column(name = "orgid", nullable = false)
    private Long orgid;

    @Column(name = "created_by", nullable = false)
    private Long userId;

    @Column(name = "created_date", nullable = false)
    private Date lmoddate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "coll_seq")
    private Long collSeq;

    @Column(name = "tax_category")
    private Long taxCategory;

    @Column(name = "pro_bd_cur_bal_taxamt_auth")
    private Double bdCurBalTaxamtAuth;

    @Column(name = "pro_bd_cur_taxamt_auth")
    private Double bdCurTaxamtAuth;

    @Column(name = "pro_bd_prv_arramt_auth")
    private Double bdPrvArramtAuth;

    @Column(name = "pro_bd_prv_bal_arramt_auth")
    private Double bdPrvBalArramtAuth;

    @Column(name = "PRO_BM_FORMULA")
    private String formula;

    @Column(name = "PRO_BM_RULE")
    private String ruleId;

    @Column(name = "PRO_BM_BRMSTAX")
    private Double baseRate;
   
    @Column(name = "MN_PER_RATE")
    private Double percentageRate;
    
    @Column(name = "Revised_bill_date")
	private Date revisedBillDate;
	
	@Column(name = "Revised_bill_type")
	private String RevisedBillType;
	
	@Column(name = "bill_gen_penalty")
	private Double billGenPenalty;
    
    @Transient
    private long dummyDetId;

    public ProvisionalBillDetEntity() {
        super();
    }

    public Long getAdjustmentId() {
        return this.adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Long getCollSeq() {
        return this.collSeq;
    }

    public void setCollSeq(Long collSeq) {
        this.collSeq = collSeq;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public Long getRebateId() {
        return this.rebateId;
    }

    public void setRebateId(Long rebateId) {
        this.rebateId = rebateId;
    }

    public Long getTaxCategory() {
        return this.taxCategory;
    }

    public void setTaxCategory(Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Long getTaxId() {
        return this.taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public ProvisionalBillMasEntity getBmIdNo() {
        return bmIdNo;
    }

    public void setBmIdNo(ProvisionalBillMasEntity bmIdNo) {
        this.bmIdNo = bmIdNo;
    }

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_PRO_BILL_DET", "proBdBilldetid" };

    }

    public long getBdBilldetid() {
        return bdBilldetid;
    }

    public void setBdBilldetid(long bdBilldetid) {
        this.bdBilldetid = bdBilldetid;
    }

    public Double getBdCurBalTaxamt() {
        return bdCurBalTaxamt;
    }

    public void setBdCurBalTaxamt(Double bdCurBalTaxamt) {
        this.bdCurBalTaxamt = bdCurBalTaxamt;
    }

    public Double getBdCurTaxamt() {
        return bdCurTaxamt;
    }

    public void setBdCurTaxamt(Double bdCurTaxamt) {
        this.bdCurTaxamt = bdCurTaxamt;
    }

    public Double getBdCurTaxamtPrint() {
        return bdCurTaxamtPrint;
    }

    public void setBdCurTaxamtPrint(Double bdCurTaxamtPrint) {
        this.bdCurTaxamtPrint = bdCurTaxamtPrint;
    }

    public Double getBdPrvArramt() {
        return bdPrvArramt;
    }

    public void setBdPrvArramt(Double bdPrvArramt) {
        this.bdPrvArramt = bdPrvArramt;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public long getDummyDetId() {
        return dummyDetId;
    }

    public void setDummyDetId(long dummyDetId) {
        this.dummyDetId = dummyDetId;
    }

    public Double getBdCurBalTaxamtAuth() {
        return bdCurBalTaxamtAuth;
    }

    public void setBdCurBalTaxamtAuth(Double bdCurBalTaxamtAuth) {
        this.bdCurBalTaxamtAuth = bdCurBalTaxamtAuth;
    }

    public Double getBdCurTaxamtAuth() {
        return bdCurTaxamtAuth;
    }

    public void setBdCurTaxamtAuth(Double bdCurTaxamtAuth) {
        this.bdCurTaxamtAuth = bdCurTaxamtAuth;
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

	public Double getBillGenPenalty() {
		return billGenPenalty;
	}

	public void setBillGenPenalty(Double billGenPenalty) {
		this.billGenPenalty = billGenPenalty;
	}
	
}
