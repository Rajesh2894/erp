package com.abm.mainet.smsemail.domain;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.SystemModuleFunction;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author kavali.kiran
 * @since 29 Nov 2014
 */
@Entity
@Table(name = "TB_PORTAL_SMS_INTEGRATION")
public class SMSAndEmailInterface extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.utility.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SE_ID", precision = 12, scale = 0, nullable = false)
    // comments : sms templete Master Primary Key Id
    private long seId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DP_DEPTID", nullable = false, updatable = false)
    // comments : dept id
    private Department dpDeptid;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SM_SERVICEID", nullable = true, updatable = false)
    // comments : foreign key for the ServiceMaster
    private ServiceMaster serviceId;

    @Column(name = "ALERT_TYPE", length = 3, nullable = false)
    // comments : alert type--E-email,S-SMS,B-both,N-for not applicable
    private String alertType;

    @Column(name = "ISDELETED", length = 1, nullable = false)
    // comments : Record Deletion flag - value N non-deleted record and Y- deleted record
    private String isDeleted;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    // comments : Organization Id.
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false)
    // comments : User Id
    private Employee userId;

    @Column(name = "CREATED_DATE", nullable = false)
    // comments : Created Date
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = true, updatable = false)
    // comments : Modified By
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    // comments : Modification Date
    private Date updatedDate;

    @JsonIgnore
    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    // comments : Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMac;

    @JsonIgnore
    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    // comments : Updated Client Machine''s Login Name | IP Address | Physical Address
    private String lgIpMacUpd;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SMFID", nullable = false, updatable = false)
    private SystemModuleFunction smfid;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "seId", cascade = CascadeType.ALL)
    private SmsAndMailTemplate smsAndmailTemplate = new SmsAndMailTemplate();

    @Transient
    private String selectedMode;

    public SmsAndMailTemplate getSmsAndmailTemplate() {
        return smsAndmailTemplate;
    }

    public void setSmsAndmailTemplate(final SmsAndMailTemplate smsAndmailTemplate) {
        this.smsAndmailTemplate = smsAndmailTemplate;
    }

    public long getSeId() {
        return seId;
    }

    public void setSeId(final long seId) {
        this.seId = seId;
    }

    public Department getDpDeptid() {
        return dpDeptid;
    }

    public void setDpDeptid(final Department dpDeptid) {
        this.dpDeptid = dpDeptid;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(final String alertType) {
        this.alertType = alertType;
    }

    @Override
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    @Override
    public Employee getUserId() {
        return userId;
    }

    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(final String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public ServiceMaster getServiceId() {
        return serviceId;
    }

    public void setServiceId(final ServiceMaster serviceId) {
        this.serviceId = serviceId;
    }

    public SystemModuleFunction getSmfid() {
        return smfid;
    }

    public void setSmfid(final SystemModuleFunction smfid) {
        this.smfid = smfid;
    }

    @Override
    public long getId() {
        return getSeId();
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "COM", "TB_PORTAL_SMS_INTEGRATION", "SE_ID" };
    }

    @Override
    public int getLangId() {
        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

}