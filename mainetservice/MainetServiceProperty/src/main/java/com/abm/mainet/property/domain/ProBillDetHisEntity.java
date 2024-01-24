package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tb_as_pro_bill_det_hist")
public class ProBillDetHisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7886891702675133983L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "pro_bd_billdetid_HIST_ID", nullable = false)
    private long proBdBilldetidHistId;

    @Column(name = "adjustment_id")
    private Long adjustmentId;

    @Column(name = "bm_idno")
    private Long bmIdno;

    @Column(name = "coll_seq")
    private Long collSeq;

    @Column(name = "created_by")
    private Long userId;

    @Column(name = "created_date")
    private Date lmoddate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "ORGID")
    private Long orgid;

    @Column(name = "pro_bd_billdetid")
    private Long bdBilldetid;

    @Column(name = "pro_bd_billflag")
    private String bdBillflag;

    @Column(name = "pro_bd_cur_bal_taxamt")
    private double bdCurBalTaxamt;

    @Column(name = "pro_bd_cur_taxamt")
    private double bdCurTaxamt;

    @Column(name = "pro_bd_cur_taxamt_print")
    private double bdCurTaxamtPrint;

    @Column(name = "pro_bd_prv_arramt")
    private double bdPrvArramt;

    @Column(name = "pro_bd_prv_bal_arramt")
    private double bdPrvBalArramt;

    @Column(name = "rebate_id")
    private Long rebateId;

    @Column(name = "tax_category")
    private Long taxCategory;

    @Column(name = "tax_id")
    private Long taxId;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

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

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_bill_det_hist", "pro_bd_billdetid_HIST_ID" };
    }

    public long getProBdBilldetidHistId() {
        return proBdBilldetidHistId;
    }

    public void setProBdBilldetidHistId(long proBdBilldetidHistId) {
        this.proBdBilldetidHistId = proBdBilldetidHistId;
    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
    }

    public Long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(Long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public Long getCollSeq() {
        return collSeq;
    }

    public void setCollSeq(Long collSeq) {
        this.collSeq = collSeq;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public Long getBdBilldetid() {
        return bdBilldetid;
    }

    public void setBdBilldetid(Long bdBilldetid) {
        this.bdBilldetid = bdBilldetid;
    }

    public String getBdBillflag() {
        return bdBillflag;
    }

    public void setBdBillflag(String bdBillflag) {
        this.bdBillflag = bdBillflag;
    }

    public double getBdCurBalTaxamt() {
        return bdCurBalTaxamt;
    }

    public void setBdCurBalTaxamt(double bdCurBalTaxamt) {
        this.bdCurBalTaxamt = bdCurBalTaxamt;
    }

    public double getBdCurTaxamt() {
        return bdCurTaxamt;
    }

    public void setBdCurTaxamt(double bdCurTaxamt) {
        this.bdCurTaxamt = bdCurTaxamt;
    }

    public double getBdCurTaxamtPrint() {
        return bdCurTaxamtPrint;
    }

    public void setBdCurTaxamtPrint(double bdCurTaxamtPrint) {
        this.bdCurTaxamtPrint = bdCurTaxamtPrint;
    }

    public double getBdPrvArramt() {
        return bdPrvArramt;
    }

    public void setBdPrvArramt(double bdPrvArramt) {
        this.bdPrvArramt = bdPrvArramt;
    }

    public double getBdPrvBalArramt() {
        return bdPrvBalArramt;
    }

    public void setBdPrvBalArramt(double bdPrvBalArramt) {
        this.bdPrvBalArramt = bdPrvBalArramt;
    }

    public Long getRebateId() {
        return rebateId;
    }

    public void setRebateId(Long rebateId) {
        this.rebateId = rebateId;
    }

    public Long getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(Long taxCategory) {
        this.taxCategory = taxCategory;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
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
    
}
