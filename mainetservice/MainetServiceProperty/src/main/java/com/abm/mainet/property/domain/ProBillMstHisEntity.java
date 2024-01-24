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
@Table(name = "tb_as_pro_bill_mas_hist")
public class ProBillMstHisEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2522590294370784784L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "pro_bm_idno_HIST_ID", nullable = false)
    private long proBmIdnoHistId;

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

    @Column(name = "pro_arrears_bill")
    private String arrearsBill;

    @Column(name = "pro_ass_id")
    private Long assId;

    @Column(name = "pro_auth_bm_idno")
    private Long authBmIdno;

    @Column(name = "pro_bm_billdt")
    private Date bmBilldt;

    @Column(name = "pro_bm_dist_date")
    private Date bmDistDate;

    @Column(name = "pro_bm_duedate")
    private Date bmDuedate;

    @Column(name = "pro_bm_entry_flag")
    private String bmEntryFlag;

    @Column(name = "pro_bm_fromdt")
    private Date bmFromdt;

    @Column(name = "pro_bm_idno")
    private long bmIdno;

    @Column(name = "pro_bm_int_from")
    private Date bmIntFrom;

    @Column(name = "pro_bm_int_to")
    private Date bmIntTo;

    @Column(name = "pro_bm_int_value")
    private double bmIntValue;

    @Column(name = "pro_bm_intamt_aftdue")
    private double bmIntamtAftdue;

    @Column(name = "pro_bm_last_rcptamt")
    private double bmLastRcptamt;

    @Column(name = "pro_bm_last_rcptdt")
    private Date bmLastRcptdt;

    @Column(name = "pro_bm_no")
    private String proBmNo;

    @Column(name = "pro_bm_paid_flag")
    private String bmPaidFlag;

    @Column(name = "pro_bm_printdate")
    private Date bmPrintdate;

    @Column(name = "pro_bm_remarks")
    private String bmRemarks;

    @Column(name = "pro_bm_toatl_int")
    private double bmToatlInt;

    @Column(name = "pro_bm_toatl_rebate")
    private double bmToatlRebate;

    @Column(name = "pro_bm_todt")
    private Date bmTodt;

    @Column(name = "pro_bm_total_amount")
    private double bmTotalAmount;

    @Column(name = "pro_bm_total_arr_wout_int")
    private double bmTotalArrearsWithoutInt;

    @Column(name = "pro_bm_total_arrears")
    private double bmTotalArrears;

    @Column(name = "pro_bm_total_bal_amount")
    private double bmTotalBalAmount;

    @Column(name = "pro_bm_total_cum_int_arr")
    private double bmTotalCumIntArrears;

    @Column(name = "pro_bm_actual_arr_amount")
    private double bmActualArrearsAmt;

    @Column(name = "pro_bm_total_outstanding")
    private double bmTotalOutstanding;

    @Column(name = "pro_bm_totpayamt_aftdue")
    private double bmTotpayamtAftdue;

    @Column(name = "pro_bm_year")
    private Long bmYear;

    @Column(name = "pro_flag_jv_post")
    private String flagJvPost;

    @Column(name = "pro_gen_flag")
    private String genFlag;

    @Column(name = "PRO_GEN_DES")
    private String bmGenDes;

    @Column(name = "pro_prop_no")
    private String propNo;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "PRO_BM_TOTAL_PENALTY")
    private Double totalPenalty;

    @Column(name = "demand_rebate")
    private Double demandRebate;
    
    @Column(name = "H_STATUS")
    private String hStatus;
    
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
    
    public String[] getPkValues() {
        return new String[] { "AS", "tb_as_pro_bill_mas_hist", "pro_bm_idno_HIST_ID" };
    }

    public long getProBmIdnoHistId() {
        return proBmIdnoHistId;
    }

    public void setProBmIdnoHistId(long proBmIdnoHistId) {
        this.proBmIdnoHistId = proBmIdnoHistId;
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

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setArrearsBill(String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public Long getAuthBmIdno() {
        return authBmIdno;
    }

    public void setAuthBmIdno(Long authBmIdno) {
        this.authBmIdno = authBmIdno;
    }

    public Date getBmBilldt() {
        return bmBilldt;
    }

    public void setBmBilldt(Date bmBilldt) {
        this.bmBilldt = bmBilldt;
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

    public Date getBmFromdt() {
        return bmFromdt;
    }

    public void setBmFromdt(Date bmFromdt) {
        this.bmFromdt = bmFromdt;
    }

    public Date getBmIntFrom() {
        return bmIntFrom;
    }

    public void setBmIntFrom(Date bmIntFrom) {
        this.bmIntFrom = bmIntFrom;
    }

    public Date getBmIntTo() {
        return bmIntTo;
    }

    public void setBmIntTo(Date bmIntTo) {
        this.bmIntTo = bmIntTo;
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

    public String getProBmNo() {
        return proBmNo;
    }

    public void setProBmNo(String proBmNo) {
        this.proBmNo = proBmNo;
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

    public double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public Long getBmYear() {
        return bmYear;
    }

    public void setBmYear(Long bmYear) {
        this.bmYear = bmYear;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setFlagJvPost(String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
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

    public long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(long bmIdno) {
        this.bmIdno = bmIdno;
    }

    public Long getAssId() {
        return assId;
    }

    public void setAssId(Long assId) {
        this.assId = assId;
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

    public double getBmTotalArrearsWithoutInt() {
        return bmTotalArrearsWithoutInt;
    }

    public void setBmTotalArrearsWithoutInt(double bmTotalArrearsWithoutInt) {
        this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
    }

    public double getBmTotalCumIntArrears() {
        return bmTotalCumIntArrears;
    }

    public void setBmTotalCumIntArrears(double bmTotalCumIntArrears) {
        this.bmTotalCumIntArrears = bmTotalCumIntArrears;
    }

    public Double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(Double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public double getBmActualArrearsAmt() {
        return bmActualArrearsAmt;
    }

    public void setBmActualArrearsAmt(double bmActualArrearsAmt) {
        this.bmActualArrearsAmt = bmActualArrearsAmt;
    }

    public String getGenFlag() {
        return genFlag;
    }

    public void setGenFlag(String genFlag) {
        this.genFlag = genFlag;
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

	public String gethStatus() {
		return hStatus;
	}

	public void sethStatus(String hStatus) {
		this.hStatus = hStatus;
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
	
}
