package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TB_AS_TEMP_PRO_BILL_MAS")
public class TemporaryProvisionalBillMasEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    /*
     * @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
     * @GeneratedValue(generator = "MyCustomGenerator")
     */
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "Temp_pro_bm_idno", nullable = false)
    private long bmIdno;

    @Column(name = "Temp_pro_ass_id", nullable = false)
    private Long assId;

    @Column(name = "Temp_pro_prop_no", nullable = false)
    private String propNo;

    @Column(name = "Temp_pro_bm_year", nullable = false)
    private Long bmYear;

    @Column(name = "Temp_pro_bm_billdt")
    private Date bmBilldt;

    @Column(name = "Temp_pro_bm_fromdt", nullable = false)
    private Date bmFromdt;

    @Column(name = "Temp_pro_bm_todt", nullable = false)
    private Date bmTodt;

    @Column(name = "Temp_pro_bm_duedate")
    private Date bmDuedate;

    @Column(name = "Temp_pro_bm_total_amount")
    private Double bmTotalAmount;

    @Column(name = "Temp_pro_bm_total_arr_wout_int")
    private Double bmTotalArrearsWithoutInt;

    @Column(name = "Temp_pro_bm_total_arrears")
    private Double bmTotalArrears;

    @Column(name = "Temp_pro_bm_actual_arr_amount")
    private Double bmActualArrearsAmt;

    @Column(name = "Temp_pro_bm_total_bal_amount")
    private Double bmTotalBalAmount;

    @Column(name = "Temp_pro_bm_total_cum_int_arr")
    private Double bmTotalCumIntArrears;

    @Column(name = "Temp_pro_bm_total_outstanding")
    private Double bmTotalOutstanding;

    @Column(name = "Temp_pro_gen_flag")
    private String genFlag;

    @Column(name = "Temp_pro_bm_dist_date")
    private Date bmDistDate;

    @Column(name = "Temp_pro_bm_entry_flag")
    private String bmEntryFlag;

    @Column(name = "Temp_pro_GEN_DES")
    private String bmGenDes;

    @Column(name = "Temp_pro_arrears_bill")
    private String arrearsBill;

    @Column(name = "Temp_pro_bm_int_from")
    private Date intFrom;

    @Column(name = "Temp_pro_bm_int_to")
    private Date intTo;

    @Column(name = "Temp_pro_bm_int_value")
    private Double bmIntValue;

    @Column(name = "Temp_pro_bm_intamt_aftdue")
    private Double bmIntamtAftdue;

    @Column(name = "Temp_pro_bm_last_rcptamt")
    private Double bmLastRcptamt;

    @Column(name = "Temp_pro_bm_last_rcptdt")
    private Date bmLastRcptdt;

    @Column(name = "Temp_pro_bm_no")
    private String bmNo;

    @Column(name = "Temp_pro_bm_paid_flag")
    private String bmPaidFlag;

    @Column(name = "Temp_pro_bm_printdate")
    private Date bmPrintdate;

    @Column(name = "Temp_pro_bm_remarks")
    private String bmRemarks;

    @Column(name = "Temp_pro_bm_toatl_int")
    private Double bmToatlInt;

    @Column(name = "Temp_pro_bm_toatl_rebate")
    private Double bmToatlRebate;

    @Column(name = "Temp_pro_bm_totpayamt_aftdue")
    private Double bmTotpayamtAftdue;

    @Column(name = "Temp_pro_flag_jv_post")
    private String flagJvPost;

    @Column(name = "orgid", nullable = false)
    private Long orgid;

    @Column(name = "created_by", nullable = false)
    private Long userId;

    @Column(name = "created_date", nullable = false)
    private Date lmoddate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedDate;

    @Column(name = "lg_ip_mac")
    private String lgIpMac;

    @Column(name = "lg_ip_mac_upd")
    private String lgIpMacUpd;

    @Column(name = "Temp_pro_auth_bm_idno")
    private Long authBmIdNo;

    @Column(name = "Temp_pro_BM_TOTAL_PENALTY")
    private Double totalPenalty;

    @Transient
    private long dummyMasId;

    @Transient
    private String newBillFlag;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bmIdNo", cascade = CascadeType.ALL)
    private List<TemporaryProvisionalBillDetEntity> temporaryProvisionalBillDetEntity;

    public TemporaryProvisionalBillMasEntity() {
        super();
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

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_TEMP_PRO_BILL_MAS", "Temp_pro_bm_idno" };
    }

    public long getBmIdno() {
        return bmIdno;
    }

    public void setBmIdno(long bmIdno) {
        this.bmIdno = bmIdno;
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

    public Double getBmTotalArrears() {
        return bmTotalArrears;
    }

    public void setBmTotalArrears(Double bmTotalArrears) {
        this.bmTotalArrears = bmTotalArrears;
    }

    public Double getBmTotalBalAmount() {
        return bmTotalBalAmount;
    }

    public void setBmTotalBalAmount(Double bmTotalBalAmount) {
        this.bmTotalBalAmount = bmTotalBalAmount;
    }

    public Double getBmTotalOutstanding() {
        return bmTotalOutstanding;
    }

    public void setBmTotalOutstanding(Double bmTotalOutstanding) {
        this.bmTotalOutstanding = bmTotalOutstanding;
    }

    public Date getBmDistDate() {
        return bmDistDate;
    }

    public void setBmDistDate(Date bmDistDate) {
        this.bmDistDate = bmDistDate;
    }

    public String getBmEntryFlag() {
        return bmEntryFlag;
    }

    public void setBmEntryFlag(String bmEntryFlag) {
        this.bmEntryFlag = bmEntryFlag;
    }

    public String getArrearsBill() {
        return arrearsBill;
    }

    public void setArrearsBill(String arrearsBill) {
        this.arrearsBill = arrearsBill;
    }

    public Double getBmIntValue() {
        return bmIntValue;
    }

    public void setBmIntValue(Double bmIntValue) {
        this.bmIntValue = bmIntValue;
    }

    public Double getBmIntamtAftdue() {
        return bmIntamtAftdue;
    }

    public void setBmIntamtAftdue(Double bmIntamtAftdue) {
        this.bmIntamtAftdue = bmIntamtAftdue;
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

    public String getBmRemarks() {
        return bmRemarks;
    }

    public void setBmRemarks(String bmRemarks) {
        this.bmRemarks = bmRemarks;
    }

    public Double getBmToatlInt() {
        return bmToatlInt;
    }

    public void setBmToatlInt(Double bmToatlInt) {
        this.bmToatlInt = bmToatlInt;
    }

    public Double getBmToatlRebate() {
        return bmToatlRebate;
    }

    public void setBmToatlRebate(Double bmToatlRebate) {
        this.bmToatlRebate = bmToatlRebate;
    }

    public Double getBmTotpayamtAftdue() {
        return bmTotpayamtAftdue;
    }

    public void setBmTotpayamtAftdue(Double bmTotpayamtAftdue) {
        this.bmTotpayamtAftdue = bmTotpayamtAftdue;
    }

    public String getFlagJvPost() {
        return flagJvPost;
    }

    public void setFlagJvPost(String flagJvPost) {
        this.flagJvPost = flagJvPost;
    }

    public Long getAuthBmIdNo() {
        return authBmIdNo;
    }

    public void setAuthBmIdNo(Long authBmIdNo) {
        this.authBmIdNo = authBmIdNo;
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

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public long getDummyMasId() {
        return dummyMasId;
    }

    public void setDummyMasId(long dummyMasId) {
        this.dummyMasId = dummyMasId;
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

    public List<TemporaryProvisionalBillDetEntity> getTemporaryProvisionalBillDetEntity() {
        return temporaryProvisionalBillDetEntity;
    }

    public void setTemporaryProvisionalBillDetEntity(List<TemporaryProvisionalBillDetEntity> temporaryProvisionalBillDetEntity) {
        this.temporaryProvisionalBillDetEntity = temporaryProvisionalBillDetEntity;
    }

    public String getNewBillFlag() {
        return newBillFlag;
    }

    public void setNewBillFlag(String newBillFlag) {
        this.newBillFlag = newBillFlag;
    }

    /*
     * @Override public Long getId() { return bmIdno; }
     * @Override public boolean isNew() { if (bmIdno == 0) { return true; } return false; }
     */

}
