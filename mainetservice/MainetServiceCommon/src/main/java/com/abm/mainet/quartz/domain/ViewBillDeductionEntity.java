package com.abm.mainet.quartz.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "BILL_DEDUCTION")
public class ViewBillDeductionEntity {
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "BDH_ID", nullable = false)
    private Long bdhId;
    @Column(name = "BCH_ID", precision = 12, scale = 0, nullable = true)
    private Long bchId;
    @Column(name = "DEDUCTION_AMT", precision = 15, scale = 2, nullable = true)
    private BigDecimal deductionAmount;
    @Column(name = "SAC_HEAD_ID", precision = 19, scale = 0, nullable = true)
    private Long sacHeadId;
    @ManyToOne
    @JoinColumn(name = "BM_ID", referencedColumnName = "BM_ID")
    private ViewSalaryBillMasterEntity viewBillMasterId;

    public Long getBdhId() {
        return bdhId;
    }

    public void setBdhId(Long bdhId) {
        this.bdhId = bdhId;
    }

    public Long getBchId() {
        return bchId;
    }

    public void setBchId(Long bchId) {
        this.bchId = bchId;
    }

    public BigDecimal getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(BigDecimal deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public Long getSacHeadId() {
        return sacHeadId;
    }

    public void setSacHeadId(Long sacHeadId) {
        this.sacHeadId = sacHeadId;
    }

    public ViewSalaryBillMasterEntity getViewBillMasterId() {
        return viewBillMasterId;
    }

    public void setViewBillMasterId(ViewSalaryBillMasterEntity viewBillMasterId) {
        this.viewBillMasterId = viewBillMasterId;
    }
}
