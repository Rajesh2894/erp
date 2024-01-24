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
@Table(name = "tb_as_bill_mas_hist")
public class MainBillMasHistEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6694463752364511064L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "MN_BM_IDNO_HIST_ID")
    private long bmIdnoHistId;

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

    @Column(name = "MN_ARREARS_BILL")
    private String arrearsBill;

    @Column(name = "MN_ASS_ID")
    private Long assId;

    @Column(name = "MN_auth_bm_idno")
    private Long authBmIdNo;

    @Column(name = "MN_BILLDT")
    private Date bmBilldt;

    @Column(name = "MN_BM_IDNO")
    private long bmIdno;

    @Column(name = "MN_BM_YEAR")
    private Long bmYear;

    @Column(name = "MN_DIST_DATE")
    private Date bmDistDate;

    @Column(name = "MN_DUEDATE")
    private Date bmDuedate;

    @Column(name = "MN_ENTRY_FLAG")
    private String bmEntryFlag;

    @Column(name = "MN_FLAG_JV_POST")
    private String flagJvPost;

    @Column(name = "MN_FROMDT")
    private Date bmFromdt;

    @Column(name = "mn_gen_flag")
    private String genFlag;

    @Column(name = "MN_GEN_DES")
    private String bmGenDes;

    @Column(name = "MN_INT_FROM")
    private Date intFrom;

    @Column(name = "MN_INT_TO")
    private Date intTo;

    @Column(name = "MN_INT_VALUE")
    private double bmIntValue;

    @Column(name = "MN_INTAMT_AFTDUE")
    private double bmIntamtAftdue;

    @Column(name = "MN_LAST_RCPTAMT")
    private double bmLastRcptamt;

    @Column(name = "MN_LAST_RCPTDT")
    private Date bmLastRcptdt;

    @Column(name = "MN_NO")
    private String bmNo;

    @Column(name = "MN_PAID_FLAG")
    private String bmPaidFlag;

    @Column(name = "MN_PRINTDATE")
    private Date bmPrintdate;

    @Column(name = "MN_PROP_NO")
    private String propNo;

    @Column(name = "MN_REMARKS")
    private String bmRemarks;

    @Column(name = "MN_TOATL_INT")
    private double bmToatlInt;

    @Column(name = "MN_TOATL_REBATE")
    private double bmToatlRebate;

    @Column(name = "MN_TODT")
    private Date bmTodt;

    @Column(name = "MN_TOTAL_AMOUNT")
    private double bmTotalAmount;

    @Column(name = "MN_TOTAL_ARREARS")
    private double bmTotalArrears;

    @Column(name = "MN_TOTAL_ARREARS_WITHOUT_INT")
    private double bmTotalArrearsWithoutInt;

    @Column(name = "MN_TOTAL_BAL_AMOUNT")
    private double bmTotalBalAmount;

    @Column(name = "MN_TOTAL_CUM_INT_ARREARS")
    private double bmTotalCumIntArrears;

    @Column(name = "MN_TOTAL_OUTSTANDING")
    private double bmTotalOutstanding;

    @Column(name = "mn_actual_arr_amount")
    private double bmActualArrearsAmt;

    @Column(name = "MN_TOTAL_PENALTY")
    private double totalPenalty;

    @Column(name = "MN_TOTPAYAMT_AFTDUE")
    private double bmTotpayamtAftdue;

    @Column(name = "orgid")
    private Long orgid;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;
    
    @Column(name = "demand_rebate")
    private Double demandRebate;
    
	@Column(name = "PARENT_MN_NO")
	private String parentMnNo;
	
	@Column(name = "Assd_std_rate")
	private Double assdStdRate;

	@Column(name = "Assd_alv")
	private Double assdAlv;

	@Column(name = "Assd_rv")
	private Double assdRv;

	@Column(name = "Assd_cv")
	private Double assdCv;
	
	@Column(name = "parent_prop_no")
	private String parentPropNo;
	
	@Column(name = "Revised_bill_date")
	private Date revisedBillDate;
	
	@Column(name = "Revised_bill_type")
	private String RevisedBillType;
	
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;

    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_bill_mas_hist", "MN_BM_IDNO_HIST_ID" };
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

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setArrearsBill(String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
    }

    public Long getAuthBmIdNo() {
        return authBmIdNo;
    }

    public void setAuthBmIdNo(Long authBmIdNo) {
        this.authBmIdNo = authBmIdNo;
    }

    public Date getBmBilldt() {
        return bmBilldt;
    }

    public void setBmBilldt(Date bmBilldt) {
        this.bmBilldt = bmBilldt;
    }

    public Long getBmYear() {
        return bmYear;
    }

    public void setBmYear(Long bmYear) {
        this.bmYear = bmYear;
    }

    public Date getBmDistDate() {
        return bmDistDate;
    }

    public void setBmDistDate(Date bmDistDate) {
        this.bmDistDate = bmDistDate;
    }

    public Date getBmDuedate() {
        return bmDuedate;
    }

    public void setBmDuedate(Date bmDuedate) {
        this.bmDuedate = bmDuedate;
    }

    public String getBmEntryFlag() {
        return bmEntryFlag;
    }

    public void setBmEntryFlag(String bmEntryFlag) {
        this.bmEntryFlag = bmEntryFlag;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setFlagJvPost(String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public Date getBmFromdt() {
        return bmFromdt;
    }

    public void setBmFromdt(Date bmFromdt) {
        this.bmFromdt = bmFromdt;
    }

    public String getGenFlag() {
        return genFlag;
    }

    public void setGenFlag(String genFlag) {
        this.genFlag = genFlag;
    }

    public Date getIntFrom() {
        return intFrom;
    }

    public void setIntFrom(Date intFrom) {
        this.intFrom = intFrom;
    }

    public Date getIntTo() {
        return intTo;
    }

    public void setIntTo(Date intTo) {
        this.intTo = intTo;
    }

    public double getBmIntValue() {
        return bmIntValue;
    }

    public void setBmIntValue(double bmIntValue) {
        this.bmIntValue = bmIntValue;
    }

    public double getBmIntamtAftdue() {
        return bmIntamtAftdue;
    }

    public void setBmIntamtAftdue(double bmIntamtAftdue) {
        this.bmIntamtAftdue = bmIntamtAftdue;
    }

    public double getBmLastRcptamt() {
        return bmLastRcptamt;
    }

    public void setBmLastRcptamt(double bmLastRcptamt) {
        this.bmLastRcptamt = bmLastRcptamt;
    }

    public Date getBmLastRcptdt() {
        return bmLastRcptdt;
    }

    public void setBmLastRcptdt(Date bmLastRcptdt) {
        this.bmLastRcptdt = bmLastRcptdt;
    }

    public String getBmNo() {
        return bmNo;
    }

    public void setBmNo(String bmNo) {
        this.bmNo = bmNo;
    }

    public String getBmPaidFlag() {
        return bmPaidFlag;
    }

    public void setBmPaidFlag(String bmPaidFlag) {
        this.bmPaidFlag = bmPaidFlag;
    }

    public Date getBmPrintdate() {
        return bmPrintdate;
    }

    public void setBmPrintdate(Date bmPrintdate) {
        this.bmPrintdate = bmPrintdate;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public String getBmRemarks() {
        return bmRemarks;
    }

    public void setBmRemarks(String bmRemarks) {
        this.bmRemarks = bmRemarks;
    }

    public double getBmToatlInt() {
        return bmToatlInt;
    }

    public void setBmToatlInt(double bmToatlInt) {
        this.bmToatlInt = bmToatlInt;
    }

    public double getBmToatlRebate() {
        return bmToatlRebate;
    }

    public void setBmToatlRebate(double bmToatlRebate) {
        this.bmToatlRebate = bmToatlRebate;
    }

    public Date getBmTodt() {
        return bmTodt;
    }

    public void setBmTodt(Date bmTodt) {
        this.bmTodt = bmTodt;
    }

    public double getBmTotalAmount() {
        return bmTotalAmount;
    }

    public void setBmTotalAmount(double bmTotalAmount) {
        this.bmTotalAmount = bmTotalAmount;
    }

    public double getBmTotalArrears() {
        return bmTotalArrears;
    }

    public void setBmTotalArrears(double bmTotalArrears) {
        this.bmTotalArrears = bmTotalArrears;
    }

    public double getBmTotalArrearsWithoutInt() {
        return bmTotalArrearsWithoutInt;
    }

    public void setBmTotalArrearsWithoutInt(double bmTotalArrearsWithoutInt) {
        this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
    }

    public double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public double getBmTotalCumIntArrears() {
        return bmTotalCumIntArrears;
    }

    public void setBmTotalCumIntArrears(double bmTotalCumIntArrears) {
        this.bmTotalCumIntArrears = bmTotalCumIntArrears;
    }

    public double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public double getBmActualArrearsAmt() {
        return bmActualArrearsAmt;
    }

    public void setBmActualArrearsAmt(double bmActualArrearsAmt) {
        this.bmActualArrearsAmt = bmActualArrearsAmt;
    }

    public double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
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

    public long getBmIdnoHistId() {
        return bmIdnoHistId;
    }

    public void setBmIdnoHistId(long bmIdnoHistId) {
        this.bmIdnoHistId = bmIdnoHistId;
    }

    public long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public String getBmGenDes() {
        return bmGenDes;
    }

    public void setBmGenDes(String bmGenDes) {
        this.bmGenDes = bmGenDes;
    }

	public Double getDemandRebate() {
		return demandRebate;
	}

	public void setDemandRebate(Double demandRebate) {
		this.demandRebate = demandRebate;
	}

	public String getParentMnNo() {
		return parentMnNo;
	}

	public void setParentMnNo(String parentMnNo) {
		this.parentMnNo = parentMnNo;
	}

	public Double getAssdStdRate() {
		return assdStdRate;
	}

	public void setAssdStdRate(Double assdStdRate) {
		this.assdStdRate = assdStdRate;
	}

	public Double getAssdAlv() {
		return assdAlv;
	}

	public void setAssdAlv(Double assdAlv) {
		this.assdAlv = assdAlv;
	}

	public Double getAssdRv() {
		return assdRv;
	}

	public void setAssdRv(Double assdRv) {
		this.assdRv = assdRv;
	}

	public Double getAssdCv() {
		return assdCv;
	}

	public void setAssdCv(Double assdCv) {
		this.assdCv = assdCv;
	}

	public String getParentPropNo() {
		return parentPropNo;
	}

	public void setParentPropNo(String parentPropNo) {
		this.parentPropNo = parentPropNo;
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
	
}
