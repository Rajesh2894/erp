package com.abm.mainet.dms.domain;

import java.util.Date;
import java.util.ListIterator;

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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author rajendra.bhujbal
 * @since 24 Jun 2014
 */
@Entity
@Table(name = "TB_ATTACH_CFC")
public class CFCAttachment extends BaseEntity {
    public String getDocApprStatus() {
        return docApprStatus;
    }

    public void setDocApprStatus(final String docApprStatus) {
        this.docApprStatus = docApprStatus;
    }

    private static final long serialVersionUID = -7939956311162600064L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "ATT_ID", precision = 12, scale = 0, nullable = false)
    private long attId;

    @Column(name = "ATT_DATE", nullable = false)
    private Date attDate;

    @Column(name = "ATT_PATH", length = 255, nullable = true)
    private String attPath;

    @Column(name = "ATT_FNAME", length = 500, nullable = true)
    private String attFname;

    @Column(name = "ATT_BY", length = 255, nullable = true)
    private String attBy;

    @Column(name = "ATT_FROM_PATH", length = 255, nullable = true)
    private String attFromPath;

    @Column(name = "DEPT", precision = 12, scale = 0, nullable = true)
    private Long dept;

    public String getRejectedMsg() {
        return rejectedMsg;
    }

    public void setRejectedMsg(final String rejectedMsg) {
        this.rejectedMsg = rejectedMsg;
    }

    @Column(name = "CLM_REMARK", precision = 30, scale = 0, nullable = true)
    private String rejectedMsg;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 12, scale = 0, nullable = true)
    private int langId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "LG_IP_MAC", length = 100, nullable = true)
    private String lgIpMac;

    @Column(name = "LG_IP_MAC_UPD", length = 100, nullable = true)
    private String lgIpMacUpd;

    @Column(name = "LMODATE", nullable = true)
    private Date lmodDate;

    @Column(name = "APPLICATION_ID", precision = 30, scale = 0, nullable = true)
    private Long applicationId;

    @Column(name = "SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long serviceId;

    @Column(name = "CLM_ID", precision = 12, scale = 0, nullable = true)
    private Long clmId;

    @Column(name = "CLM_DESC", length = 2000, nullable = true)
    private String clmDesc;

    @Column(name = "CLM_STATUS", length = 1, nullable = true)
    private String clmStatus;

    @Column(name = "CLM_SR_NO", precision = 3, scale = 0, nullable = true)
    private Long clmSrNo;

    @Column(name = "CHK_STATUS", precision = 12, scale = 0, nullable = true)
    private Long chkStatus;

    @Column(name = "CLM_APR_STATUS", scale = 0, nullable = true)
    private String docApprStatus;

    @Column(name = "MANDATORY", length = 1, nullable = true)
    private String mandatory;

    @Transient
    private long draftId;

    @Transient
    private String chkDocDesc;

    /**
     * @return the attId
     */
    public long getAttId() {
        return attId;
    }

    /**
     * @param attId the attId to set
     */
    public void setAttId(final long attId) {
        this.attId = attId;
    }

    /**
     * @return the attDate
     */
    public Date getAttDate() {
        return attDate;
    }

    /**
     * @param attDate the attDate to set
     */
    public void setAttDate(final Date attDate) {
        this.attDate = attDate;
    }

    /**
     * @return the attPath
     */
    public String getAttPath() {
        return attPath;
    }

    /**
     * @param attPath the attPath to set
     */
    public void setAttPath(final String attPath) {
        this.attPath = attPath;
    }

    /**
     * @return the attFname
     */
    public String getAttFname() {
        return attFname;
    }

    /**
     * @param attFname the attFname to set
     */
    public void setAttFname(final String attFname) {
        this.attFname = attFname;
    }

    /**
     * @return the attBy
     */
    public String getAttBy() {
        return attBy;
    }

    /**
     * @param attBy the attBy to set
     */
    public void setAttBy(final String attBy) {
        this.attBy = attBy;
    }

    /**
     * @return the attFromPath
     */
    public String getAttFromPath() {
        return attFromPath;
    }

    /**
     * @param attFromPath the attFromPath to set
     */
    public void setAttFromPath(final String attFromPath) {
        this.attFromPath = attFromPath;
    }

    /**
     * @return the dept
     */
    public Long getDept() {
        return dept;
    }

    /**
     * @param dept the dept to set
     */
    public void setDept(final Long dept) {
        this.dept = dept;
    }

    /**
     * @return the orgId
     */
    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the userId
     */
    @Override
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    @Override
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    @Override
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the updatedBy
     */
    @Override
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    @Override
    public void setUpdatedBy(final Employee updatedBy) {
        this.updatedBy = updatedBy;
    }

    /**
     * @return the updatedDate
     */
    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    /**
     * @param updatedDate the updatedDate to set
     */
    @Override
    public void setUpdatedDate(final Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the lgIpMac
     */
    @Override
    public String getLgIpMac() {
        return lgIpMac;
    }

    /**
     * @param lgIpMac the lgIpMac to set
     */
    @Override
    public void setLgIpMac(final String lgIpMac) {
        this.lgIpMac = lgIpMac;
    }

    /**
     * @return the lgIpMacUpd
     */
    @Override
    public String getLgIpMacUpd() {
        return lgIpMacUpd;
    }

    /**
     * @param lgIpMacUpd the lgIpMacUpd to set
     */
    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {
        this.lgIpMacUpd = lgIpMacUpd;
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
     * @return the serviceId
     */
    public Long getServiceId() {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * @return the clmId
     */
    public Long getClmId() {
        return clmId;
    }

    /**
     * @param clmId the clmId to set
     */
    public void setClmId(final Long clmId) {
        this.clmId = clmId;
    }

    /**
     * @return the clmDesc
     */
    public String getClmDesc() {
        return clmDesc;
    }

    /**
     * @param clmDesc the clmDesc to set
     */
    public void setClmDesc(final String clmDesc) {
        this.clmDesc = clmDesc;
    }

    /**
     * @return the clmStatus
     */
    public String getClmStatus() {
        return clmStatus;
    }

    /**
     * @param clmStatus the clmStatus to set
     */
    public void setClmStatus(final String clmStatus) {
        this.clmStatus = clmStatus;
    }

    /**
     * @return the clmSrNo
     */
    public Long getClmSrNo() {
        return clmSrNo;
    }

    /**
     * @param clmSrNo the clmSrNo to set
     */
    public void setClmSrNo(final Long clmSrNo) {
        this.clmSrNo = clmSrNo;
    }

    /**
     * @return the chkStatus
     */
    public Long getChkStatus() {
        return chkStatus;
    }

    /**
     * @param chkStatus the chkStatus to set
     */
    public void setChkStatus(final Long chkStatus) {
        this.chkStatus = chkStatus;
    }

    /**
     * @return the lmodDate
     */
    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    @Override
    public long getId() {
        return attId;
    }

    @Override
    public String getIsDeleted() {
        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    public boolean isMendatory() {
        final ListIterator<LookUp> iterator = getLookUps(MainetConstants.PrefixInfo.SET,
                UserSession.getCurrent().getOrganisation()).listIterator();

        if ((chkStatus != null) && (chkStatus > 0L)) {
            while (iterator.hasNext()) {
                final LookUp lookUp = iterator.next();
                if (chkStatus == lookUp.getLookUpId()) {
                    if (lookUp.getLookUpCode().equals(MainetConstants.PrefixInfo.SET_CODE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public String getCcmValueStr() {
        final ListIterator<LookUp> iterator = getLookUps(MainetConstants.PrefixInfo.SET,
                UserSession.getCurrent().getOrganisation()).listIterator();

        while (iterator.hasNext()) {
            final LookUp lookUp = iterator.next();
            if (chkStatus == lookUp.getLookUpId()) {
                return lookUp.getLookUpDesc();
            }
        }
        return MainetConstants.BLANK;
    }

    public long getDraftId() {
        return draftId;
    }

    public void setDraftId(final long draftId) {
        this.draftId = draftId;
    }

    public String getApprovalStatus() {
        String status = null;
        switch (getDocApprStatus()) {
        case MainetConstants.AUTH:
            status = "APPROVRD";
            break;
        case MainetConstants.UNAUTH:
            status = MainetConstants.AuthStatus.REJECT;
            break;
        case MainetConstants.AuthStatus.ONHOLD:
            status = MainetConstants.AuthStatus.HOLD;
            break;
        default:
            status = MainetConstants.BLANK;
            break;
        }
        return status;
    }

    public String getChkDocDesc() {
        return chkDocDesc;
    }

    public void setChkDocDesc(final String chkDocDesc) {
        this.chkDocDesc = chkDocDesc;
    }

    @Override
    public String[] getPkValues() {
        return new String[] { "AUT", "TB_ATTACH_CFC", "ATT_ID" };
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(final String mandatory) {
        this.mandatory = mandatory;
    }

}