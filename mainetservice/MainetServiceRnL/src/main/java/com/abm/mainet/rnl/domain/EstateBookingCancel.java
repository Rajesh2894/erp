package com.abm.mainet.rnl.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

@Entity
@Table(name = "TB_RL_ESTATE_BOOKING_CANCEL")
public class EstateBookingCancel implements Serializable {

    private static final long serialVersionUID = 7012237067780903143L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "B_CANCELID", nullable = false)
    private Long bcancelId;

    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @Column(name = "RF_FEEID")
    private Long rfFeeid;

    /*
     * Ask to SAMADHAN Sir it need or not
     * @Column(name = "VENDOR_ID") private Long vendorId;// used when account integrate
     */
    @Column(name = "REFUND_AMT", precision = 15, scale = 2, nullable = true)
    private BigDecimal refundAmt;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    public Long getBcancelId() {
        return bcancelId;
    }

    public void setBcancelId(Long bcancelId) {
        this.bcancelId = bcancelId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getRfFeeid() {
        return rfFeeid;
    }

    public void setRfFeeid(Long rfFeeid) {
        this.rfFeeid = rfFeeid;
    }

    public BigDecimal getRefundAmt() {
        return refundAmt;
    }

    public void setRefundAmt(BigDecimal refundAmt) {
        this.refundAmt = refundAmt;
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

    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    public void setLgIpMacUp(String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, "TB_RL_ESTATE_BOOKING_CANCEL", "B_CANCELID" };
    }

}
