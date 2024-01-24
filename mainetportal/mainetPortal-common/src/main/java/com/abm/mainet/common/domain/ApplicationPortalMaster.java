package com.abm.mainet.common.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.abm.mainet.common.constant.MainetConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rajdeep.Sinha
 * @since 18 Dec 2015
 */
@Entity
@Table(name = "TB_PORTAL_APPLICATION_MST")
public class ApplicationPortalMaster extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "PAM_ID", precision = 12, scale = 0, nullable = false)
    private Long pamId;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "PAM_APPLICATION_ID", precision = 16, scale = 0, nullable = true)
    private Long pamApplicationId;

    @Column(name = "PAM_APPLICATION_DATE", nullable = true)
    private Date pamApplicationDate;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = true)
    private Long smServiceId;

    @Column(name = "PAM_CFC_CITIZENID", length = 16, nullable = true)
    private String pamCfcCitizenid;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "CREATED_DATE", nullable = true)
    private Date lmodDate;

    @Column(name = "CREATED_BY", length = 100, nullable = true)
    private String createdBy;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "PAM_SLA_DATE", nullable = true)
    private Date pamSlaDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "PAM_DOC_VERIFICATION_DATE", nullable = true)
    private Date pamDocVerificationDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "PAM_FIRST_APPEAL_DATE", nullable = true)
    private Date pamFirstAppealDate;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "PAM_SECOND_APPEAL_DATE", nullable = true)
    private Date pamSecondAppealDate;

    @Column(name = "PAM_APPLICATION_STATUS", length = 100, nullable = true)
    private String pamApplicationStatus;

    @Temporal(value = TemporalType.DATE)
    @Column(name = "PAM_APPLICATION_REJECTION_DATE", nullable = true)
    private Date pamApplicationRejectionDate;

    @Column(name = "PAM_PAYMENT_STATUS", length = 20, nullable = true)
    private String pamPaymentStatus;

    @Column(name = "PAM_APP_ACCEPTED_DATE", nullable = true)
    private Date pamAppAcceptedDate;

    @Column(name = "PAM_CHECKLIST_APP", length = 1, nullable = true)
    private String pamChecklistApp;

    @Column(name = "PAM_AAPLE_TRACK_ID", nullable = true)
    private String pam_aaple_track_id;

    @Column(name = "PAM_AAPLE_STATUS", nullable = true)
    private String pam_aaple_status;

    @Column(name = "PAM_DIGITAL_SIGN", nullable = true)
    private String pamDigitalSign;

    public Long getPamId() {

        return pamId;
    }

    public void setPamId(final Long pamId) {
        this.pamId = pamId;
    }

    @Override
    public Organisation getOrgId() {
        return orgId;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    public Long getPamApplicationId() {
        return pamApplicationId;
    }

    public void setPamApplicationId(final Long pamApplicationId) {
        this.pamApplicationId = pamApplicationId;
    }

    public Date getPamApplicationDate() {
        return pamApplicationDate;
    }

    public void setPamApplicationDate(final Date pamApplicationDate) {
        this.pamApplicationDate = pamApplicationDate;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public String getPamCfcCitizenid() {
        return pamCfcCitizenid;
    }

    public void setPamCfcCitizenid(final String pamCfcCitizenid) {
        this.pamCfcCitizenid = pamCfcCitizenid;
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
    public int getLangId() {
        return langId;
    }

    @Override
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    @Override
    public Date getLmodDate() {
        return lmodDate;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
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

    public Date getPamSlaDate() {
        return pamSlaDate;
    }

    public void setPamSlaDate(final Date pamSlaDate) {
        this.pamSlaDate = pamSlaDate;
    }

    public Date getPamDocVerificationDate() {
        return pamDocVerificationDate;
    }

    public void setPamDocVerificationDate(final Date pamDocVerificationDate) {
        this.pamDocVerificationDate = pamDocVerificationDate;
    }

    public Date getPamFirstAppealDate() {
        return pamFirstAppealDate;
    }

    public String getPam_aaple_track_id() {
        return pam_aaple_track_id;
    }

    public void setPam_aaple_track_id(final String pam_aaple_track_id) {
        this.pam_aaple_track_id = pam_aaple_track_id;
    }

    public String getPam_aaple_status() {
        return pam_aaple_status;
    }

    public void setPam_aaple_status(final String pam_aaple_status) {
        this.pam_aaple_status = pam_aaple_status;
    }

    public void setPamFirstAppealDate(final Date pamFirstAppealDate) {
        this.pamFirstAppealDate = pamFirstAppealDate;
    }

    public Date getPamSecondAppealDate() {
        return pamSecondAppealDate;
    }

    public void setPamSecondAppealDate(final Date pamSecondAppealDate) {
        this.pamSecondAppealDate = pamSecondAppealDate;
    }

    public String getPamApplicationStatus() {
        return pamApplicationStatus;
    }

    public void setPamApplicationStatus(final String pamApplicationStatus) {
        this.pamApplicationStatus = pamApplicationStatus;
    }

    public Date getPamApplicationRejectionDate() {
        return pamApplicationRejectionDate;
    }

    public void setPamApplicationRejectionDate(final Date pamApplicationRejectionDate) {
        this.pamApplicationRejectionDate = pamApplicationRejectionDate;
    }

    public String getPamPaymentStatus() {
        return pamPaymentStatus;
    }

    public void setPamPaymentStatus(final String pamPaymentStatus) {
        this.pamPaymentStatus = pamPaymentStatus;
    }

    public Date getPamAppAcceptedDate() {
        return pamAppAcceptedDate;
    }

    public void setPamAppAcceptedDate(final Date pamAppAcceptedDate) {
        this.pamAppAcceptedDate = pamAppAcceptedDate;
    }

    public String getPamChecklistApp() {
        return pamChecklistApp;
    }

    public void setPamChecklistApp(final String pamChecklistApp) {
        this.pamChecklistApp = pamChecklistApp;
    }

    @Override
    public long getId() {

        return 0;
    }

    @Override
    public String getLgIpMac() {

        return null;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {

    }

    @Override
    public String getLgIpMacUpd() {

        return null;
    }

    @Override
    public void setLgIpMacUpd(final String lgIpMacUpd) {

    }

    @Override
    public String getIsDeleted() {

        return null;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {

    }

    @Override
    public String[] getPkValues() {

        return new String[] { "CFC", "TB_PORTAL_APPLICATION_MST", "PAM_ID" };
    }

    public String getPamDigitalSign() {
        return pamDigitalSign;
    }

    public void setPamDigitalSign(final String pamDigitalSign) {
        this.pamDigitalSign = pamDigitalSign;
    }

    public String getApplicantsName() {
        return userId.getFullName();
    }

    public String getActionTempletForDigitalSignature() {
        final String assidval = "DigiGrid.html?PrintReport";
        final String SignedPdfActionUrl = "DigiGrid.html?displaySignedPdf";
        final StringBuilder datastring = new StringBuilder();

        if (pamDigitalSign.equalsIgnoreCase(MainetConstants.AUTH) & pamApplicationStatus.equalsIgnoreCase("Completed")) {
            datastring.append("&nbsp;&nbsp;<form action='" + SignedPdfActionUrl + "'  " + "method=\"post\" >");
            datastring.append("<input type='hidden' name='applId' value='" + pamApplicationId + "'/>");
            datastring.append("<input type='hidden' name='smServiceId' value='" + smServiceId + "'/>");
            datastring.append("<a href='" + SignedPdfActionUrl + "' target='_blank'>");
            datastring.append("<input type='image' src='css/images/grid/signature_icon.png' "
                    + "alt='Digital Signing' title='Digital Signing'/>");
            datastring.append("</a>");
            datastring.append("</form>");
        } else if (pamDigitalSign.equalsIgnoreCase(MainetConstants.UNAUTH) & pamApplicationStatus.equalsIgnoreCase("Completed")) {
            datastring.append("&nbsp;&nbsp;<form action='" + assidval + "'  " + "method=\"post\" >");
            datastring.append("<input type='hidden' name='applId' value='" + pamApplicationId + "'/>");
            datastring.append("<input type='hidden' name='smServiceId' value='" + smServiceId + "'/>");
            datastring.append("<a href='" + assidval + "' target='_blank'>");
            datastring.append("<input type='image' src='css/images/grid/print-orange-icon.png' "
                    + "alt='Print Report' title='Digital Signing'/>");
            datastring.append("</a>");
            datastring.append("</form>");
        }

        return datastring.toString();
    }
}