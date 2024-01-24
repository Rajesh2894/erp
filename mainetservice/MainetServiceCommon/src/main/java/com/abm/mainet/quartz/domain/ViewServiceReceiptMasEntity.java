package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VW_RECEIPTMASTER")
public class ViewServiceReceiptMasEntity {
    private static final long serialVersionUID = -9038205557221109375L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RM_RECEIPTID", precision = 12, scale = 0, nullable = false)
    private long rmRcptid;
    @Column(name = "RM_RECEIPTNO", precision = 19, scale = 0, nullable = true)
    private Long rmRcptno;
    @Temporal(TemporalType.DATE)
    @Column(name = "RM_RECEIPTDATE", nullable = true)
    private Date rmDate;
    @Column(name = "RM_RECEIPTAMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rmAmount;
    @Column(name = "RM_RECEIVEDFROM", length = 100, nullable = true)
    private String rmReceivedfrom;
    @Column(name = "RM_NARRATION", length = 200, nullable = true)
    private String rmNarration;
    @Column(name = "ORGID", nullable = false, updatable = false)
    private Long orgId;
    @Column(name = "CREATED_BY", nullable = false, updatable = false)
    private Long createdBy;
    @Column(name = "CREATED_DATE", nullable = true)
    private Date createdDate;
    @Column(name = "DP_DEPTID", precision = 12, scale = 0, nullable = true)
    private Long dpDeptId;
    /*
     * @Column(name = "RECEIPT_TYPE_FLAG", length = 1) private String receiptTypeFlag;
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "rmRcptid", cascade = CascadeType.ALL)
    private ViewSrcptModesDetEntity receiptModeDetail = null;
    @OneToMany(mappedBy = "rmRcptid", targetEntity = ViewSrcptFeesDetEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ViewSrcptFeesDetEntity> receiptFeeDetail;
    /*
     * @Column(name = "FIELD_ID") private Long fieldId;
     */
    @Column(name = "RECEIPT_DEL_ORDER_NO")
    private BigDecimal receiptDelOrderNo;

    public long getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(long rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getRmRcptno() {
        return rmRcptno;
    }

    public void setRmRcptno(Long rmRcptno) {
        this.rmRcptno = rmRcptno;
    }

    public Date getRmDate() {
        return rmDate;
    }

    public void setRmDate(Date rmDate) {
        this.rmDate = rmDate;
    }

    public BigDecimal getRmAmount() {
        return rmAmount;
    }

    public void setRmAmount(BigDecimal rmAmount) {
        this.rmAmount = rmAmount;
    }

    public String getRmReceivedfrom() {
        return rmReceivedfrom;
    }

    public void setRmReceivedfrom(String rmReceivedfrom) {
        this.rmReceivedfrom = rmReceivedfrom;
    }

    public String getRmNarration() {
        return rmNarration;
    }

    public void setRmNarration(String rmNarration) {
        this.rmNarration = rmNarration;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
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

    public Long getDpDeptId() {
        return dpDeptId;
    }

    public void setDpDeptId(Long dpDeptId) {
        this.dpDeptId = dpDeptId;
    }

    public ViewSrcptModesDetEntity getReceiptModeDetail() {
        return receiptModeDetail;
    }

    public void setReceiptModeDetail(ViewSrcptModesDetEntity receiptModeDetail) {
        this.receiptModeDetail = receiptModeDetail;
    }

    public List<ViewSrcptFeesDetEntity> getReceiptFeeDetail() {
        return receiptFeeDetail;
    }

    public void setReceiptFeeDetail(List<ViewSrcptFeesDetEntity> receiptFeeDetail) {
        this.receiptFeeDetail = receiptFeeDetail;
    }

    public BigDecimal getReceiptDelOrderNo() {
        return receiptDelOrderNo;
    }

    public void setReceiptDelOrderNo(BigDecimal receiptDelOrderNo) {
        this.receiptDelOrderNo = receiptDelOrderNo;
    }

}
