package com.abm.mainet.property.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ProvisionalBillMasDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6523080304284576870L;

    private long proBmIdno;

    private Long proAssId;

    private String proPropNo;

    private Long proBmYear;

    private Date proBmBilldt;

    private Date proBmFromdt;

    private Date proBmTodt;

    private Date proBmDuedate;

    private BigDecimal proBmTotalAmount;

    private BigDecimal proBmTotalArrWoutInt;

    private BigDecimal proBmTotalArrears;

    private BigDecimal proBmTotalBalAmount;

    private BigDecimal proBmTotalCumIntArr;

    private BigDecimal proBmTotalOutstanding;

    private Date proBmDistDate;

    private String proBmEntryFlag;

    private String proArrearsBill;

    private Date proBmIntFrom;

    private Date proBmIntTo;

    private BigDecimal proBmIntValue;

    private BigDecimal proBmIntamtAftdue;

    private BigDecimal proBmLastRcptamt;

    private Date proBmLastRcptdt;

    private String proBmNo;

    private String proBmPaidFlag;

    private Date proBmPrintdate;

    private String proBmRemarks;

    private BigDecimal proBmToatlInt;

    private BigDecimal proBmToatlRebate;

    private BigDecimal proBmTotpayamtAftdue;

    private String proFlagJvPost;

    private Long orgid;

    private Long createdBy;

    private Date createdDate;

    private Long updatedBy;

    private Date updatedDate;

    private String lgIpMac;

    private String lgIpMacUpd;

    private Long proAuthBmIdNo;

    private List<ProvisionalBillDetDto> provisionalBillDetDtoList;

    public long getProBmIdno() {
        return proBmIdno;
    }

    public void setProBmIdno(long proBmIdno) {
        this.proBmIdno = proBmIdno;
    }

    public Long getProAssId() {
        return proAssId;
    }

    public void setProAssId(Long proAssId) {
        this.proAssId = proAssId;
    }

    public String getProPropNo() {
        return proPropNo;
    }

    public void setProPropNo(String proPropNo) {
        this.proPropNo = proPropNo;
    }

    public Long getProBmYear() {
        return proBmYear;
    }

    public void setProBmYear(Long proBmYear) {
        this.proBmYear = proBmYear;
    }

    public Date getProBmBilldt() {
        return proBmBilldt;
    }

    public void setProBmBilldt(Date proBmBilldt) {
        this.proBmBilldt = proBmBilldt;
    }

    public Date getProBmFromdt() {
        return proBmFromdt;
    }

    public void setProBmFromdt(Date proBmFromdt) {
        this.proBmFromdt = proBmFromdt;
    }

    public Date getProBmTodt() {
        return proBmTodt;
    }

    public void setProBmTodt(Date proBmTodt) {
        this.proBmTodt = proBmTodt;
    }

    public Date getProBmDuedate() {
        return proBmDuedate;
    }

    public void setProBmDuedate(Date proBmDuedate) {
        this.proBmDuedate = proBmDuedate;
    }

    public BigDecimal getProBmTotalAmount() {
        return proBmTotalAmount;
    }

    public void setProBmTotalAmount(BigDecimal proBmTotalAmount) {
        this.proBmTotalAmount = proBmTotalAmount;
    }

    public BigDecimal getProBmTotalArrWoutInt() {
        return proBmTotalArrWoutInt;
    }

    public void setProBmTotalArrWoutInt(BigDecimal proBmTotalArrWoutInt) {
        this.proBmTotalArrWoutInt = proBmTotalArrWoutInt;
    }

    public BigDecimal getProBmTotalArrears() {
        return proBmTotalArrears;
    }

    public void setProBmTotalArrears(BigDecimal proBmTotalArrears) {
        this.proBmTotalArrears = proBmTotalArrears;
    }

    public BigDecimal getProBmTotalBalAmount() {
        return proBmTotalBalAmount;
    }

    public void setProBmTotalBalAmount(BigDecimal proBmTotalBalAmount) {
        this.proBmTotalBalAmount = proBmTotalBalAmount;
    }

    public BigDecimal getProBmTotalCumIntArr() {
        return proBmTotalCumIntArr;
    }

    public void setProBmTotalCumIntArr(BigDecimal proBmTotalCumIntArr) {
        this.proBmTotalCumIntArr = proBmTotalCumIntArr;
    }

    public BigDecimal getProBmTotalOutstanding() {
        return proBmTotalOutstanding;
    }

    public void setProBmTotalOutstanding(BigDecimal proBmTotalOutstanding) {
        this.proBmTotalOutstanding = proBmTotalOutstanding;
    }

    public Date getProBmDistDate() {
        return proBmDistDate;
    }

    public void setProBmDistDate(Date proBmDistDate) {
        this.proBmDistDate = proBmDistDate;
    }

    public String getProBmEntryFlag() {
        return proBmEntryFlag;
    }

    public void setProBmEntryFlag(String proBmEntryFlag) {
        this.proBmEntryFlag = proBmEntryFlag;
    }

    public String getProArrearsBill() {
        return proArrearsBill;
    }

    public void setProArrearsBill(String proArrearsBill) {
        this.proArrearsBill = proArrearsBill;
    }

    public Date getProBmIntFrom() {
        return proBmIntFrom;
    }

    public void setProBmIntFrom(Date proBmIntFrom) {
        this.proBmIntFrom = proBmIntFrom;
    }

    public Date getProBmIntTo() {
        return proBmIntTo;
    }

    public void setProBmIntTo(Date proBmIntTo) {
        this.proBmIntTo = proBmIntTo;
    }

    public BigDecimal getProBmIntValue() {
        return proBmIntValue;
    }

    public void setProBmIntValue(BigDecimal proBmIntValue) {
        this.proBmIntValue = proBmIntValue;
    }

    public BigDecimal getProBmIntamtAftdue() {
        return proBmIntamtAftdue;
    }

    public void setProBmIntamtAftdue(BigDecimal proBmIntamtAftdue) {
        this.proBmIntamtAftdue = proBmIntamtAftdue;
    }

    public BigDecimal getProBmLastRcptamt() {
        return proBmLastRcptamt;
    }

    public void setProBmLastRcptamt(BigDecimal proBmLastRcptamt) {
        this.proBmLastRcptamt = proBmLastRcptamt;
    }

    public Date getProBmLastRcptdt() {
        return proBmLastRcptdt;
    }

    public void setProBmLastRcptdt(Date proBmLastRcptdt) {
        this.proBmLastRcptdt = proBmLastRcptdt;
    }

    public String getProBmNo() {
        return proBmNo;
    }

    public void setProBmNo(String proBmNo) {
        this.proBmNo = proBmNo;
    }

    public String getProBmPaidFlag() {
        return proBmPaidFlag;
    }

    public void setProBmPaidFlag(String proBmPaidFlag) {
        this.proBmPaidFlag = proBmPaidFlag;
    }

    public Date getProBmPrintdate() {
        return proBmPrintdate;
    }

    public void setProBmPrintdate(Date proBmPrintdate) {
        this.proBmPrintdate = proBmPrintdate;
    }

    public String getProBmRemarks() {
        return proBmRemarks;
    }

    public void setProBmRemarks(String proBmRemarks) {
        this.proBmRemarks = proBmRemarks;
    }

    public BigDecimal getProBmToatlInt() {
        return proBmToatlInt;
    }

    public void setProBmToatlInt(BigDecimal proBmToatlInt) {
        this.proBmToatlInt = proBmToatlInt;
    }

    public BigDecimal getProBmToatlRebate() {
        return proBmToatlRebate;
    }

    public void setProBmToatlRebate(BigDecimal proBmToatlRebate) {
        this.proBmToatlRebate = proBmToatlRebate;
    }

    public BigDecimal getProBmTotpayamtAftdue() {
        return proBmTotpayamtAftdue;
    }

    public void setProBmTotpayamtAftdue(BigDecimal proBmTotpayamtAftdue) {
        this.proBmTotpayamtAftdue = proBmTotpayamtAftdue;
    }

    public String getProFlagJvPost() {
        return proFlagJvPost;
    }

    public void setProFlagJvPost(String proFlagJvPost) {
        this.proFlagJvPost = proFlagJvPost;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public List<ProvisionalBillDetDto> getProvisionalBillDetDtoList() {
        return provisionalBillDetDtoList;
    }

    public void setProvisionalBillDetDtoList(List<ProvisionalBillDetDto> provisionalBillDetDtoList) {
        this.provisionalBillDetDtoList = provisionalBillDetDtoList;
    }

    public Long getProAuthBmIdNo() {
        return proAuthBmIdNo;
    }

    public void setProAuthBmIdNo(Long proAuthBmIdNo) {
        this.proAuthBmIdNo = proAuthBmIdNo;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

}
