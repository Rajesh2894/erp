package com.abm.mainet.property.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import com.abm.mainet.common.domain.FinancialYear;

/**
 * 
 * @author hiren.poriya
 * @since 21-Nov-2017
 */

@Entity
@Table(name = "TB_AS_BILL_SCHEDULE_MAST")
public class BillingScheduleMstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "AS_BILL_SCHEID", nullable = false)
    private Long asBillScheid;

    @ManyToOne
    @JoinColumn(name = "FA_YEARID", referencedColumnName = "FA_YEARID", nullable = false)
    private FinancialYear tbFinancialyear;

    @Column(name = "AS_BILL_FREQUENCY", nullable = false)
    private Long asBillFrequency;

    @Column(name = "AS_AUT_STATUS", length = 1)
    private String asAutStatus;

    @Column(name = "AS_AUT_BY")
    private Long asAutBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "AS_AUT_DATE")
    private Date asAutDate;

    @Column(name = "ORGID", nullable = false)
    private Long orgId;

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
    private String lgIpMacUpd;

    @Column(name = "as_bill_status", length = 1)
    private String asBillStatus;

    @OneToMany(mappedBy = "billScheduleMas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Where(clause = "status = 'Y'")
    private List<BillingScheduleDetailEntity> billScheduleDetail = new ArrayList<>(0);

    public BillingScheduleMstEntity() {
        super();
    }

    public void setAsBillScheid(Long asBillScheid) {
        this.asBillScheid = asBillScheid;
    }

    public Long getAsBillScheid() {
        return this.asBillScheid;
    }

    public void setAsBillFrequency(Long asBillFrequency) {
        this.asBillFrequency = asBillFrequency;
    }

    public Long getAsBillFrequency() {
        return this.asBillFrequency;
    }

    public void setAsAutStatus(String asAutStatus) {
        this.asAutStatus = asAutStatus;
    }

    public String getAsAutStatus() {
        return this.asAutStatus;
    }

    public void setAsAutBy(Long asAutBy) {
        this.asAutBy = asAutBy;
    }

    public Long getAsAutBy() {
        return this.asAutBy;
    }

    public void setAsAutDate(Date asAutDate) {
        this.asAutDate = asAutDate;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Date getAsAutDate() {
        return this.asAutDate;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getUpdatedDate() {
        return this.updatedDate;
    }

    public void setLgIpMac(String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    public String getLgIpMac() {
        return this.lgIpMac;
    }

    public void setLgIpMacUpd(String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getLgIpMacUpd() {
        return this.lgIpMacUpd;
    }

    public FinancialYear getTbFinancialyear() {
        return tbFinancialyear;
    }

    public void setTbFinancialyear(FinancialYear tbFinancialyear) {
        this.tbFinancialyear = tbFinancialyear;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BillScheduleMstEntity [asBillScheid=");
        builder.append(asBillScheid);
        builder.append(", tbFinancialyear=");
        builder.append(tbFinancialyear);
        builder.append(", asBillFrequency=");
        builder.append(asBillFrequency);
        builder.append(", asFrequencyFrom=");
        builder.append(asAutStatus);
        builder.append(", asAutBy=");
        builder.append(asAutBy);
        builder.append(", asAutDate=");
        builder.append(asAutDate);
        builder.append(", orgId=");
        builder.append(orgId);
        builder.append(", createdBy=");
        builder.append(createdBy);
        builder.append(", createdDate=");
        builder.append(createdDate);
        builder.append(", updatedBy=");
        builder.append(updatedBy);
        builder.append(", updatedDate=");
        builder.append(updatedDate);
        builder.append(", lgIpMac=");
        builder.append(lgIpMac);
        builder.append(", lgIpMacUpd=");
        builder.append(lgIpMacUpd);
        builder.append("]");
        return builder.toString();
    }

    public String[] getPkValues() {
        return new String[] { "AS", "TB_AS_BILL_SCHEDULE_MAST", "AS_BILL_SCHEID" };

    }

    public List<BillingScheduleDetailEntity> getBillScheduleDetail() {
        return billScheduleDetail;
    }

    public void setBillScheduleDetail(List<BillingScheduleDetailEntity> billScheduleDetail) {
        this.billScheduleDetail = billScheduleDetail;
    }

    public String getAsBillStatus() {
        return asBillStatus;
    }

    public void setAsBillStatus(String asBillStatus) {
        this.asBillStatus = asBillStatus;
    }

}
