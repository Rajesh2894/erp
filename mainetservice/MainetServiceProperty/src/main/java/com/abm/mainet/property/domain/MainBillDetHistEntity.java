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
@Table(name = "tb_as_bill_det_hist")
public class MainBillDetHistEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4107195706973292868L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BD_BILLDETID_HIST_ID")
    private long bdBillDetIdHistId;

    @Column(name = "ADJUSTMENT_ID")
    private Long adjustmentId;

    @Column(name = "BD_BILLDETID")
    private long bdBilldetid;

    @Column(name = "BD_BILLFLAG")
    private String bdBillflag;

    @Column(name = "BD_CUR_BAL_TAXAMT")
    private double bdCurBalTaxamt;

    @Column(name = "BD_CUR_BAL_TAXAMT_AUTH")
    private double bdCurBalTaxamtAuth;

    @Column(name = "BD_CUR_TAXAMT")
    private double bdCurTaxamt;

    @Column(name = "BD_CUR_TAXAMT_AUTH")
    private double bdCurTaxamtAuth;

    @Column(name = "BD_CUR_TAXAMT_PRINT")
    private double bdCurTaxamtPrint;

    @Column(name = "BD_PRV_ARRAMT")
    private double bdPrvArramt;

    @Column(name = "bd_prv_arramt_auth")
    private double bdPrvArramtAuth;

    @Column(name = "BD_PRV_BAL_ARRAMT")
    private double bdPrvBalArramt;

    @Column(name = "BD_PRV_BAL_ARRAMT_AUTH")
    private double bdPrvBalArramtAuth;

    @Column(name = "BM_IDNO")
    private Long bmIdno;

    @Column(name = "COLL_SEQ")
    private Long collSeq;

    @Column(name = "CREATED_BY")
    private Long userId;

    @Column(name = "CREATED_DATE")
    private Date lmoddate;

    @Column(name = "H_STATUS")
    private String hStatus;

    @Column(name = "LG_IP_MAC")
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD")
    private String lgIpMacUpd;

    @Column(name = "MN_BM_BRMSTAX")
    private double mnBmBrmstax;

    @Column(name = "MN_BM_FORMULA")
    private String mnBmFormula;

    @Column(name = "MN_BM_RULE")
    private String mnBmRule;

    @Column(name = "orgid")
    private Long orgid;

    @Column(name = "REBATE_ID")
    private Long rebateId;

    @Column(name = "TAX_CATEGORY")
    private Long taxCategory;

    @Column(name = "TAX_ID")
    private Long taxId;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "MN_PER_RATE")
    private Double percentageRate;
    
    @Column(name = "Revised_bill_date")
	private Date revisedBillDate;
	
	@Column(name = "Revised_bill_type")
	private String RevisedBillType;
	
	@Column(name = "BM_IDNO_HIST_ID")
    private long bmIdnoHistId;
	
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_bill_det_hist", "BD_BILLDETID_HIST_ID" };

    }

    public Long getAdjustmentId() {
        return adjustmentId;
    }

    public void setAdjustmentId(Long adjustmentId) {
        this.adjustmentId = adjustmentId;
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

    public double getBdCurBalTaxamtAuth() {
        return bdCurBalTaxamtAuth;
    }

    public void setBdCurBalTaxamtAuth(double bdCurBalTaxamtAuth) {
        this.bdCurBalTaxamtAuth = bdCurBalTaxamtAuth;
    }

    public double getBdCurTaxamt() {
        return bdCurTaxamt;
    }

    public void setBdCurTaxamt(double bdCurTaxamt) {
        this.bdCurTaxamt = bdCurTaxamt;
    }

    public double getBdCurTaxamtAuth() {
        return bdCurTaxamtAuth;
    }

    public void setBdCurTaxamtAuth(double bdCurTaxamtAuth) {
        this.bdCurTaxamtAuth = bdCurTaxamtAuth;
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

    public double getBdPrvArramtAuth() {
        return bdPrvArramtAuth;
    }

    public void setBdPrvArramtAuth(double bdPrvArramtAuth) {
        this.bdPrvArramtAuth = bdPrvArramtAuth;
    }

    public double getBdPrvBalArramt() {
        return bdPrvBalArramt;
    }

    public void setBdPrvBalArramt(double bdPrvBalArramt) {
        this.bdPrvBalArramt = bdPrvBalArramt;
    }

    public double getBdPrvBalArramtAuth() {
        return bdPrvBalArramtAuth;
    }

    public void setBdPrvBalArramtAuth(double bdPrvBalArramtAuth) {
        this.bdPrvBalArramtAuth = bdPrvBalArramtAuth;
    }

    public Long getCollSeq() {
        return collSeq;
    }

    public void setCollSeq(Long collSeq) {
        this.collSeq = collSeq;
    }

    public String gethStatus() {
        return hStatus;
    }

    public void sethStatus(String hStatus) {
        this.hStatus = hStatus;
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

    public double getMnBmBrmstax() {
        return mnBmBrmstax;
    }

    public void setMnBmBrmstax(double mnBmBrmstax) {
        this.mnBmBrmstax = mnBmBrmstax;
    }

    public String getMnBmFormula() {
        return mnBmFormula;
    }

    public void setMnBmFormula(String mnBmFormula) {
        this.mnBmFormula = mnBmFormula;
    }

    public String getMnBmRule() {
        return mnBmRule;
    }

    public void setMnBmRule(String mnBmRule) {
        this.mnBmRule = mnBmRule;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public long getBdBillDetIdHistId() {
        return bdBillDetIdHistId;
    }

    public void setBdBillDetIdHistId(long bdBillDetIdHistId) {
        this.bdBillDetIdHistId = bdBillDetIdHistId;
    }

    public long getBdBilldetid() {
        return bdBilldetid;
    }

    public void setBdBilldetid(long bdBilldetid) {
        this.bdBilldetid = bdBilldetid;
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

	public Double getPercentageRate() {
		return percentageRate;
	}

	public void setPercentageRate(Double percentageRate) {
		this.percentageRate = percentageRate;
	}

	public Long getBmIdno() {
		return bmIdno;
	}

	public void setBmIdno(Long bmIdno) {
		this.bmIdno = bmIdno;
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

	public long getBmIdnoHistId() {
		return bmIdnoHistId;
	}

	public void setBmIdnoHistId(long bmIdnoHistId) {
		this.bmIdnoHistId = bmIdnoHistId;
	}

	public Long getApmApplicationId() {
		return apmApplicationId;
	}

	public void setApmApplicationId(Long apmApplicationId) {
		this.apmApplicationId = apmApplicationId;
	}
	   
	
}
