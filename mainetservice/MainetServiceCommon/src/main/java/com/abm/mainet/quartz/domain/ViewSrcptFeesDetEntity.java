package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VW_RECEIPT_DETAILS")
public class ViewSrcptFeesDetEntity {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RF_FEEID", precision = 12, scale = 0, nullable = false)
    private long rfFeeid;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RM_RCPTID", nullable = false, referencedColumnName = "RM_RECEIPTID")
    private ViewServiceReceiptMasEntity rmRcptid;
    @Column(name = "TAX_ID", precision = 12, scale = 0, nullable = true)
    private Long taxId;
    @Column(name = "RF_FEEAMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rfFeeamount;
    @Column(name = "BILLID", precision = 12, scale = 0, nullable = true)
    private Long billdetId;
    @Column(name = "BILL_TYPE", length = 1)
    private String billType;

    public long getRfFeeid() {
        return rfFeeid;
    }

    public void setRfFeeid(long rfFeeid) {
        this.rfFeeid = rfFeeid;
    }

    public ViewServiceReceiptMasEntity getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(ViewServiceReceiptMasEntity rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getTaxId() {
        return taxId;
    }

    public void setTaxId(Long taxId) {
        this.taxId = taxId;
    }

    public BigDecimal getRfFeeamount() {
        return rfFeeamount;
    }

    public void setRfFeeamount(BigDecimal rfFeeamount) {
        this.rfFeeamount = rfFeeamount;
    }

    public Long getBilldetId() {
        return billdetId;
    }

    public void setBilldetId(Long billdetId) {
        this.billdetId = billdetId;
    }

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

}
