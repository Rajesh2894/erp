package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_as_bill_mas")
public class MainBillMasEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3200765632456496802L;

    @Id
    @Column(name = "MN_BM_IDNO")
    private long bmIdno;

    @Column(name = "MN_ASS_ID")
    private Long assId;

    @Column(name = "MN_PROP_NO")
    private String propNo;

    @Column(name = "MN_BM_YEAR")
    private Long bmYear;

    @Column(name = "MN_BILLDT")
    private Date bmBilldt;

    @Column(name = "MN_FROMDT")
    private Date bmFromdt;

    @Column(name = "MN_TODT")
    private Date bmTodt;

    @Column(name = "MN_DUEDATE")
    private Date bmDuedate;

    @Column(name = "MN_TOTAL_AMOUNT")
    private Double bmTotalAmount;

    @Column(name = "MN_TOTAL_BAL_AMOUNT")
    private Double bmTotalBalAmount;

    @Column(name = "MN_TOTAL_ARREARS")
    private Double bmTotalArrears;

    @Column(name = "MN_TOTAL_OUTSTANDING")
    private Double bmTotalOutstanding;

    @Column(name = "MN_TOTAL_ARREARS_WITHOUT_INT")
    private Double bmTotalArrearsWithoutInt;

    @Column(name = "mn_actual_arr_amount")
    private Double bmActualArrearsAmt;

    @Column(name = "MN_TOTAL_CUM_INT_ARREARS")
    private Double bmTotalCumIntArrears;

    @Column(name = "MN_TOATL_INT")
    private Double bmToatlInt;

    @Column(name = "MN_LAST_RCPTAMT")
    private Double bmLastRcptamt;

    @Column(name = "MN_LAST_RCPTDT")
    private Date bmLastRcptdt;

    @Column(name = "MN_TOATL_REBATE")
    private Double bmToatlRebate;

    @Column(name = "MN_PAID_FLAG")
    private String bmPaidFlag;

    @Column(name = "MN_FLAG_JV_POST")
    private String flagJvPost;

    @Column(name = "MN_DIST_DATE")
    private Date bmDistDate;

    @Column(name = "MN_REMARKS")
    private String bmRemarks;

    @Column(name = "MN_PRINTDATE")
    private Date bmPrintdate;

    @Column(name = "MN_ARREARS_BILL")
    private String arrearsBill;

    @Column(name = "MN_TOTPAYAMT_AFTDUE")
    private Double bmTotpayamtAftdue;

    @Column(name = "MN_INTAMT_AFTDUE")
    private Double bmIntamtAftdue;

    @Column(name = "MN_ENTRY_FLAG")
    private String bmEntryFlag;

    @Column(name = "MN_GEN_DES")
    private String bmGenDes;

    @Column(name = "mn_gen_flag")
    private String genFlag;

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

    @Column(name = "MN_INT_FROM")
    private Date intFrom;

    @Column(name = "MN_INT_TO")
    private Date intTo;

    @Column(name = "MN_NO")
    private String bmNo;

    @Column(name = "MN_INT_VALUE")
    private Double bmIntValue;

    @Column(name = "MN_auth_bm_idno")
    private Long authBmIdNo;

    @Column(name = "MN_TOTAL_PENALTY")
    private Double totalPenalty;

    @Column(name = "demand_rebate")
    private Double demandRebate;
    
    @Column(name = "BM_SRV_DT")
    private Date billDistrDate;
    
    @Column(name = "PD_FLATNO")
    private String flatNo;
    
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
	
	@Column(name = "LOGICAL_PROP_NO")
	private String logicalPropNo;
	 
	@Column(name = "Revised_bill_date")
	private Date revisedBillDate;
	
	@Column(name = "Revised_bill_type")
	private String RevisedBillType;
	
	@Column(name = "APM_APPLICATION_ID")
	private Long apmApplicationId;
    
	/*
	 * @Column(name = "GROUP_MN_NO") private String groupBmNo;
	 * 
	 * @Column(name = "group_prop_no") private String groupPropNo;
	 * 
	 * @Column(name = "parent_prop_no") private String parentPropNo;
	 */
	 
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bmIdNo", cascade = CascadeType.ALL)
    private List<MainBillDetEntity> billDetEntityList;

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

    public String getPropNo() {
        return propNo;
    }

    public void setPropNo(String propNo) {
        this.propNo = propNo;
    }

    public Long getBmYear() {
        return bmYear;
    }

    public void setBmYear(Long bmYear) {
        this.bmYear = bmYear;
    }

    public Date getBmBilldt() {
        return bmBilldt;
    }

    public void setBmBilldt(Date bmBilldt) {
        this.bmBilldt = bmBilldt;
    }

    public Date getBmFromdt() {
        return bmFromdt;
    }

    public void setBmFromdt(Date bmFromdt) {
        this.bmFromdt = bmFromdt;
    }

    public Date getBmTodt() {
        return bmTodt;
    }

    public void setBmTodt(Date bmTodt) {
        this.bmTodt = bmTodt;
    }

    public Date getBmDuedate() {
        return bmDuedate;
    }

    public void setBmDuedate(Date bmDuedate) {
        this.bmDuedate = bmDuedate;
    }

    public Double getBmTotalAmount() {
        return bmTotalAmount;
    }

    public void setBmTotalAmount(Double bmTotalAmount) {
        this.bmTotalAmount = bmTotalAmount;
    }

    public Double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(Double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public Double getBmTotalArrears() {
        return bmTotalArrears;
    }

    public void setBmTotalArrears(Double bmTotalArrears) {
        this.bmTotalArrears = bmTotalArrears;
    }

    public Double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(Double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public Double getBmTotalArrearsWithoutInt() {
        return bmTotalArrearsWithoutInt;
    }

    public void setBmTotalArrearsWithoutInt(Double bmTotalArrearsWithoutInt) {
        this.bmTotalArrearsWithoutInt = bmTotalArrearsWithoutInt;
    }

    public Double getBmTotalCumIntArrears() {
        return bmTotalCumIntArrears;
    }

    public void setBmTotalCumIntArrears(Double bmTotalCumIntArrears) {
        this.bmTotalCumIntArrears = bmTotalCumIntArrears;
    }

    public Double getBmToatlInt() {
        return bmToatlInt;
    }

    public void setBmToatlInt(Double bmToatlInt) {
        this.bmToatlInt = bmToatlInt;
    }

    public Double getBmLastRcptamt() {
        return bmLastRcptamt;
    }

    public void setBmLastRcptamt(Double bmLastRcptamt) {
        this.bmLastRcptamt = bmLastRcptamt;
    }

    public Date getBmLastRcptdt() {
        return bmLastRcptdt;
    }

    public void setBmLastRcptdt(Date bmLastRcptdt) {
        this.bmLastRcptdt = bmLastRcptdt;
    }

    public Double getBmToatlRebate() {
        return bmToatlRebate;
    }

    public void setBmToatlRebate(Double bmToatlRebate) {
        this.bmToatlRebate = bmToatlRebate;
    }

    public String getBmPaidFlag() {
        return bmPaidFlag;
    }

    public void setBmPaidFlag(String bmPaidFlag) {
        this.bmPaidFlag = bmPaidFlag;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setFlagJvPost(String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public Date getBmDistDate() {
        return bmDistDate;
    }

    public void setBmDistDate(Date bmDistDate) {
        this.bmDistDate = bmDistDate;
    }

    public String getBmRemarks() {
        return bmRemarks;
    }

    public void setBmRemarks(String bmRemarks) {
        this.bmRemarks = bmRemarks;
    }

    public Date getBmPrintdate() {
        return bmPrintdate;
    }

    public void setBmPrintdate(Date bmPrintdate) {
        this.bmPrintdate = bmPrintdate;
    }

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setArrearsBill(String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public Double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(Double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public Double getBmIntamtAftdue() {
        return bmIntamtAftdue;
    }

    public void setBmIntamtAftdue(Double bmIntamtAftdue) {
        this.bmIntamtAftdue = bmIntamtAftdue;
    }

    public String getBmEntryFlag() {
        return bmEntryFlag;
    }

    public void setBmEntryFlag(String bmEntryFlag) {
        this.bmEntryFlag = bmEntryFlag;
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

    public String getBmNo() {
        return bmNo;
    }

    public void setBmNo(String bmNo) {
        this.bmNo = bmNo;
    }

    public Double getBmIntValue() {
        return bmIntValue;
    }

    public void setBmIntValue(Double bmIntValue) {
        this.bmIntValue = bmIntValue;
    }

    public Long getAuthBmIdNo() {
        return authBmIdNo;
    }

    public void setAuthBmIdNo(Long authBmIdNo) {
        this.authBmIdNo = authBmIdNo;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public List<MainBillDetEntity> getBillDetEntityList() {
        return billDetEntityList;
    }

    public void setBillDetEntityList(List<MainBillDetEntity> billDetEntityList) {
        this.billDetEntityList = billDetEntityList;
    }

    public Double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(Double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    public Double getBmActualArrearsAmt() {
        return bmActualArrearsAmt;
    }

    public void setBmActualArrearsAmt(Double bmActualArrearsAmt) {
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

	public Date getBillDistrDate() {
		return billDistrDate;
	}

	public void setBillDistrDate(Date billDistrDate) {
		this.billDistrDate = billDistrDate;
	}

	public String getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
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

	public String getLogicalPropNo() {
		return logicalPropNo;
	}

	public void setLogicalPropNo(String logicalPropNo) {
		this.logicalPropNo = logicalPropNo;
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
	
	
	/*
	 * public String getGroupBmNo() { return groupBmNo; }
	 * 
	 * public void setGroupBmNo(String groupBmNo) { this.groupBmNo = groupBmNo; }
	 * 
	 * public String getGroupPropNo() { return groupPropNo; }
	 * 
	 * public void setGroupPropNo(String groupPropNo) { this.groupPropNo =
	 * groupPropNo; }
	 * 
	 * public String getParentPropNo() { return parentPropNo; }
	 * 
	 * public void setParentPropNo(String parentPropNo) { this.parentPropNo =
	 * parentPropNo; }
	 */
	 
	 
    
}
