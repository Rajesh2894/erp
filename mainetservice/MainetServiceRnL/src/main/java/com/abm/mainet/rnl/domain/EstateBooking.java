package com.abm.mainet.rnl.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author ritesh.patil
 *
 * Estate Master entity Created for Rent and Lease.
 */
@Entity
@DynamicUpdate
@Table(name = "TB_RL_ESTATE_BOOKING")
public class EstateBooking implements Serializable {

    private static final long serialVersionUID = -4172501212383484097L;
    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "EBK_ID", nullable = false)
    private Long id;

    @Column(name = "APM_APPLICATION_ID")
    private Long applicationId;

    @Column(name = "EBK_BOOKING_NO")
    private String bookingNo;

    @Column(name = "EBK_BOOKING_DATE")
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @Column(name = "EBK_BOOK_TODT")
    @Temporal(TemporalType.DATE)
    private Date toDate;

    @Column(name = "EBK_BOOK_FROMDT")
    @Temporal(TemporalType.DATE)
    private Date fromDate;

    @Column(name = "EBK_BOOKING_AMT", precision = 15, scale = 2, nullable = true)
    private Double amount;

    @Column(name = "EBK_BOOKING_SECURITY_AMT", precision = 15, scale = 2, nullable = true)
    private Double securityAmount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PROP_ID")
    private EstatePropertyEntity estatePropertyEntity;

    @Column(name = "EBK_BOOK_SHIFT")
    private Long shiftId;

    @Column(name = "EBK_BOOK_PURPOSE")
    private Long purpose;

    @Column(name = "EBK_TERMS_ACCEPTED")
    private Boolean termAccepted;

    @Column(name = "EBK_PAY_FLAG")
    private Boolean payFlag;

    @Column(name = "EBK_BOOK_STATUS")
    private String bookingStatus;

    @Column(name = "EBK_YEAR")
    private String year;

    @Column(name = "EBK_MONTH")
    private String month;

    @Column(name = "EBK_DISCOUNT_AMT", precision = 15, scale = 2, nullable = true)
    private Double discountAmount;

    @Column(name = "EBK_NOINVITIES")
    private Long noOfInvitess;

    @Column(name = "ORGID")
    private Long orgId;

    @Column(name = "LANGID")
    private long langId;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100)
    private String lgIpMacUp;

    @Column(name = "EBK_RESAON_OFFREEZ")
    private String reasonOfFreezing;

    @Column(name = "EBK_CANCEL_REASON")
    private String cancelReason;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EBK_CANCEL_DATE")
    private Date cancelDate;
    
    @Column(name = "EBK_ULB_EMPLOYEE")
    private String IsEmployeeOrga;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * @return the applicationId
     */
    public Long getApplicationId() {
        return applicationId;
    }

    /**
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(final Long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * @return the bookingNo
     */
    public String getBookingNo() {
        return bookingNo;
    }

    /**
     * @param bookingNo the bookingNo to set
     */
    public void setBookingNo(final String bookingNo) {
        this.bookingNo = bookingNo;
    }

    /**
     * @return the bookingDate
     */
    public Date getBookingDate() {
        return bookingDate;
    }

    /**
     * @param bookingDate the bookingDate to set
     */
    public void setBookingDate(final Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    /**
     * @return the toDate
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * @param toDate the toDate to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the fromDate
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * @param fromDate the fromDate to set
     */
    public void setFromDate(final Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * @return the amount
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(final Double amount) {
        this.amount = amount;
    }

    /**
     * @return the securityAmount
     */
    public Double getSecurityAmount() {
        return securityAmount;
    }

    /**
     * @param securityAmount the securityAmount to set
     */
    public void setSecurityAmount(final Double securityAmount) {
        this.securityAmount = securityAmount;
    }

    /**
     * @return the estatePropertyEntity
     */
    public EstatePropertyEntity getEstatePropertyEntity() {
        return estatePropertyEntity;
    }

    /**
     * @param estatePropertyEntity the estatePropertyEntity to set
     */
    public void setEstatePropertyEntity(final EstatePropertyEntity estatePropertyEntity) {
        this.estatePropertyEntity = estatePropertyEntity;
    }

    /**
     * @return the shiftId
     */
    public Long getShiftId() {
        return shiftId;
    }

    /**
     * @param shiftId the shiftId to set
     */
    public void setShiftId(final Long shiftId) {
        this.shiftId = shiftId;
    }

    public Long getPurpose() {
        return purpose;
    }

    public void setPurpose(Long purpose) {
        this.purpose = purpose;
    }

    /**
     * @return the termAccepted
     */
    public Boolean getTermAccepted() {
        return termAccepted;
    }

    /**
     * @param termAccepted the termAccepted to set
     */
    public void setTermAccepted(final Boolean termAccepted) {
        this.termAccepted = termAccepted;
    }

    /**
     * @return the payFlag
     */
    public Boolean getPayFlag() {
        return payFlag;
    }

    /**
     * @param payFlag the payFlag to set
     */
    public void setPayFlag(final Boolean payFlag) {
        this.payFlag = payFlag;
    }

    /**
     * @return the bookingStatus Booking Status ('Y'->"Booked,'P'->Partial Booked)
     */
    public String getBookingStatus() {
        return bookingStatus;
    }

    /**
     * @param bookingStatus the bookingStatus to set
     */
    public void setBookingStatus(final String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(final String year) {
        this.year = year;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(final String month) {
        this.month = month;
    }

    /**
     * @return the discountAmount
     */
    public Double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * @param discountAmount the discountAmount to set
     */
    public void setDiscountAmount(final Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * @return the orgId
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Long orgId) {
        this.orgId = orgId;
    }

    public Long getNoOfInvitess() {
        return noOfInvitess;
    }

    public void setNoOfInvitess(Long noOfInvitess) {
        this.noOfInvitess = noOfInvitess;
    }

    /**
     * @return the langId
     */
    public long getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final long langId) {
        this.langId = langId;
    }

    /**
     * @return the createdBy
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedBy
     */
    public Long getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUp
     */
    public String getLgIpMacUp() {
        return lgIpMacUp;
    }

    /**
     * @param lgIpMacUp the lgIpMacUp to set
     */
    public void setLgIpMacUp(final String lgIpMacUp) {
        this.lgIpMacUp = lgIpMacUp;
    }

    public String getReasonOfFreezing() {
        return reasonOfFreezing;
    }

    public void setReasonOfFreezing(String reasonOfFreezing) {
        this.reasonOfFreezing = reasonOfFreezing;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public Date getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(Date cancelDate) {
        this.cancelDate = cancelDate;
    }
    
    

    public String getIsEmployeeOrga() {
        return IsEmployeeOrga;
    }

    public void setIsEmployeeOrga(String isEmployeeOrga) {
        IsEmployeeOrga = isEmployeeOrga;
    }

    public String[] getPkValues() {
        return new String[] { MainetConstants.RnLDetailEntity.RL, MainetConstants.RnLDetailEntity.TB_RL_ESTATE,
                MainetConstants.RnLDetailEntity.EBK_ID };
    }

}
