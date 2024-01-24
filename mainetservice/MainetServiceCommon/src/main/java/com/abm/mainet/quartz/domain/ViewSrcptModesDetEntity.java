package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "VW_RECEIPTMODES")
public class ViewSrcptModesDetEntity {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "RD_RECEIPTMODESID", precision = 12, scale = 0, nullable = false)
    private long rdModesid;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "RM_RECEIPTID", nullable = false, referencedColumnName = "RM_RECEIPTID")
    private ViewServiceReceiptMasEntity rmRcptid;
    @Column(name = "CPD_RECEIPTMODE", precision = 12, scale = 0, nullable = true)
    private Long cpdFeemode;
    @Column(name = "RD_CHEQUEDDNO", precision = 19, scale = 0, nullable = true)
    private String rdChequeddno;
    @Column(name = "RD_CHEQUEDDDATE", nullable = true)
    private Date rdChequedddate;
    @Column(name = "BANKID", precision = 12, scale = 0, nullable = true)
    private Long cbBankid;
    @Column(name = "RD_AMOUNT", precision = 12, scale = 2, nullable = true)
    private BigDecimal rdAmount;
    @Column(name = "RD_DRAWNON", precision = 12, scale = 2, nullable = true)
    private String rdDrawnon;

    public long getRdModesid() {
        return rdModesid;
    }

    public void setRdModesid(long rdModesid) {
        this.rdModesid = rdModesid;
    }

    public ViewServiceReceiptMasEntity getRmRcptid() {
        return rmRcptid;
    }

    public void setRmRcptid(ViewServiceReceiptMasEntity rmRcptid) {
        this.rmRcptid = rmRcptid;
    }

    public Long getCpdFeemode() {
        return cpdFeemode;
    }

    public void setCpdFeemode(Long cpdFeemode) {
        this.cpdFeemode = cpdFeemode;
    }

    public String getRdChequeddno() {
        return rdChequeddno;
    }

    public void setRdChequeddno(String rdChequeddno) {
        this.rdChequeddno = rdChequeddno;
    }

    public Date getRdChequedddate() {
        return rdChequedddate;
    }

    public void setRdChequedddate(Date rdChequedddate) {
        this.rdChequedddate = rdChequedddate;
    }

    public Long getCbBankid() {
        return cbBankid;
    }

    public void setCbBankid(Long cbBankid) {
        this.cbBankid = cbBankid;
    }

    public BigDecimal getRdAmount() {
        return rdAmount;
    }

    public void setRdAmount(BigDecimal rdAmount) {
        this.rdAmount = rdAmount;
    }

    public String getRdDrawnon() {
        return rdDrawnon;
    }

    public void setRdDrawnon(String rdDrawnon) {
        this.rdDrawnon = rdDrawnon;
    }

}
