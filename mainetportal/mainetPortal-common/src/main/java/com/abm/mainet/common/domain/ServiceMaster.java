package com.abm.mainet.common.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.util.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

@NamedQueries({
        @NamedQuery(name = "selectServiceShortName",

                query = "SELECT smShortdesc FROM ServiceMaster WHERE smServiceId = :smServiceId AND orgId.orgid = :orgId"),
        @NamedQuery(name = "selectSmDigitalSignApplicable",

                query = "SELECT smDigiSignAppl FROM ServiceMaster WHERE smServiceId = :smServiceId AND orgId.orgid = :orgId"),
        @NamedQuery(name = "selectApplChargeFlag",

                query = "SELECT smAppliChargeFlag FROM ServiceMaster WHERE smServiceId = :smServiceId AND orgId.orgid = :orgId"),
})

@Entity
@Table(name = "TB_SERVICES_MST")
public class ServiceMaster implements Serializable {
    private static final long serialVersionUID = -1315822758003760399L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    private long smServiceId;

    @Column(name = "SM_SERVICE_NAME", length = 100, nullable = true)
    private String smServiceName;

    @Column(name = "SM_SERV_OWN_DESIG", precision = 12, scale = 0, nullable = true)
    private Long smServOwnDesig;

    @Column(name = "SM_SERV_ACTIVE", precision = 12, scale = 0, nullable = true)
    private Long smServActive;

    @Column(name = "SM_SERV_TYPE", precision = 12, scale = 0, nullable = true)
    private Long smServType;

    @Column(name = "SM_SERV_COUNTER", precision = 12, scale = 0, nullable = true)
    private Long smServCounter;

    @Column(name = "SM_SERV_DURATION", precision = 3, scale = 0, nullable = true)
    private Long smServDuration;

    @Column(name = "SM_APPL_FORM", precision = 12, scale = 0, nullable = true)
    private Long smApplForm;

    @Column(name = "SM_CHKLST_VERIFY", precision = 12, scale = 0, nullable = true)
    private Long smChklstVerify;

    @Column(name = "SM_SECURITY_DEPOSIT", precision = 12, scale = 0, nullable = true)
    private Long smSecurityDeposit;

    @Column(name = "SM_FEES_SCHEDULE", precision = 3, scale = 0, nullable = true)
    private Long smFeesSchedule;

    @Column(name = "SM_ACKNOWLEDGE", precision = 12, scale = 0, nullable = true)
    private Long smAcknowledge;

    @Column(name = "SM_SCRUTINY_LEVEL", precision = 3, scale = 0, nullable = true)
    private Long smScrutinyLevel;

    @Column(name = "SM_AUTHO_LEVEL", precision = 12, scale = 0, nullable = true)
    private Long smAuthoLevel;

    @Column(name = "SM_PRINT_RESPONS", precision = 12, scale = 0, nullable = true)
    private Long smPrintRespons;

    @Column(name = "SM_SPECIFIC_INFO_FORM", length = 100, nullable = true)
    private String smSpecificInfoForm;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 7, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @Column(name = "SM_SERVICE_NAME_MAR", length = 200, nullable = true)
    private String smServiceNameMar;

    @Column(name = "CDM_DEPT_ID", precision = 12, scale = 0, nullable = true)
    private Long cdmDeptId;

    @Column(name = "SM_APPROVAL_FORM", length = 100, nullable = true)
    private String smApprovalForm;

    @Column(name = "SM_REJECTION_FORM", length = 100, nullable = true)
    private String smRejectionForm;

    @Column(name = "SM_WEB_ENABLED", length = 1, nullable = true)
    private String smWebEnabled;

    @Column(name = "SM_URL", length = 100, nullable = true)
    private String smUrl;

    @Column(name = "SM_SRNO", length = 12, nullable = true)
    private String smSrno;

    @Column(name = "SM_SWITCH", length = 1, nullable = true)
    private String smSwitch;

    @Column(name = "SM_ADDR", length = 1, nullable = true)
    private String smAddr;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "SM_RCPT", length = 1, nullable = true)
    private String smRcpt;

    @Column(name = "SM_INITIATING_EMPID", length = 12, nullable = true)
    private String smInitiatingEmpid;

    @Column(name = "SM_CPD_ID", precision = 12, scale = 0, nullable = true)
    private Long smCpdId;

    @Column(name = "SM_LOI_DURATION", precision = 3, scale = 0, nullable = true)
    private Long smLoiDuration;

    @Column(name = "SM_SHORTDESC", length = 5, nullable = true)
    private String smShortdesc;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGID", nullable = false, updatable = false)
    private Organisation orgId;

    @Column(name = "COM_V1", length = 100, nullable = true)
    private String comV1;

    @Column(name = "COM_V2", length = 100, nullable = true)
    private String comV2;

    @Column(name = "COM_V3", length = 100, nullable = true)
    private String comV3;

    @Column(name = "COM_V4", length = 100, nullable = true)
    private String comV4;

    @Column(name = "COM_V5", length = 100, nullable = true)
    private String comV5;

    @Column(name = "SM_CHALLAN_DURATION", precision = 3, scale = 0, nullable = true)
    private Long comN1;

    @Column(name = "COM_N2", precision = 15, scale = 0, nullable = true)
    private Long comN2;

    @Column(name = "SMFID", precision = 15, scale = 0, nullable = true)
    private Long smfid;

    @Column(name = "COM_N4", precision = 15, scale = 0, nullable = true)
    private Long comN4;

    @Column(name = "COM_N5", precision = 15, scale = 0, nullable = true)
    private Long comN5;

    @Column(name = "COM_D1", nullable = true)
    private Date comD1;

    @Column(name = "COM_D2", nullable = true)
    private Date comD2;

    @Column(name = "COM_D3", nullable = true)
    private Date comD3;

    @Column(name = "SM_CSC_FLAG", length = 1, nullable = true)
    private String smCscFlag;

    @Column(name = "COM_LO2", length = 1, nullable = true)
    private String comLo2;

    @Column(name = "COM_LO3", length = 1, nullable = true)
    private String comLo3;

    @Column(name = "SRID_HOURS", precision = 2, scale = 0, nullable = true)
    private Long sridHours;

    @Column(name = "SRID_MIN", precision = 2, scale = 0, nullable = true)
    private Long sridMin;

    @Column(name = "SM_CHECKLIST_HOURS", precision = 2, scale = 0, nullable = true)
    private Long smChecklistHours;

    @Column(name = "SM_CHECKLIST_MIN", precision = 2, scale = 0, nullable = true)
    private Long smChecklistMin;

    @Column(name = "SM_CHECKLIST_DAYS", precision = 3, scale = 0, nullable = true)
    private Long smChecklistDays;

    @Column(name = "SM_SERV_DURATION_TYPE", precision = 12, scale = 0, nullable = true)
    private Long smServDurationType;

    @Column(name = "IP_MAC", length = 100, nullable = true)
    private String ipMac;

    @Column(name = "IP_MAC_UPD", length = 100, nullable = true)
    private String ipMacUpd;

    @Column(name = "SM_SERVICE_NOTE", length = 400, nullable = true)
    private String smServiceNote;

    @Column(name = "SM_SERVICE_NOTE_MAR", length = 600, nullable = true)
    private String smServiceNoteMar;

    @Column(name = "SM_TYPE_OF_SIGN", precision = 12, scale = 0, nullable = true)
    private Long smTypeOfSign;

    @Column(name = "SM_APPLI_CHARGE_FLAG", length = 1, nullable = true)
    private String smAppliChargeFlag;

    @Column(name = "SM_SCRUTINY_CHARGE_FLAG", length = 1, nullable = true)
    private String smScrutinyChargeFlag;

    @Column(name = "SM_ESTIMATE_FROM", precision = 15, scale = 0, nullable = true)
    private Long smEstimateFrom;

    @Column(name = "SM_ESTIMATE_TO", precision = 15, scale = 0, nullable = true)
    private Long smEstimateTo;

    @Column(name = "SM_NOTLOGIN_FLG", length = 1, nullable = true)
    private String smNotloginFlg;

    @Column(name = "CPD_ID_APL_CHKLIST_VERIFY", precision = 12, nullable = true)
    private String aplChklistVerify;

    @Column(name = "SM_DIGI_SIGN_APPL", length = 1, nullable = false)
    private String smDigiSignAppl;

    public String getAplChklistVerify() {
        return aplChklistVerify;
    }

    public String getSmDigiSignAppl() {
        return smDigiSignAppl;
    }

    public void setSmDigiSignAppl(final String smDigiSignAppl) {
        this.smDigiSignAppl = smDigiSignAppl;
    }

    public void setAplChklistVerify(final String aplChklistVerify) {
        this.aplChklistVerify = aplChklistVerify;
    }

    /**
     * @return the smServiceId
     */
    public long getSmServiceId() {
        return smServiceId;
    }

    /**
     * @param smServiceId the smServiceId to set
     */
    public void setSmServiceId(final long smServiceId) {
        this.smServiceId = smServiceId;
    }

    /**
     * @return the smServiceName
     */
    public String getSmServiceName() {
        return smServiceName;
    }

    /**
     * @param smServiceName the smServiceName to set
     */
    public void setSmServiceName(final String smServiceName) {
        this.smServiceName = smServiceName;
    }

    /**
     * @return the smServOwnDesig
     */
    public Long getSmServOwnDesig() {
        return smServOwnDesig;
    }

    /**
     * @param smServOwnDesig the smServOwnDesig to set
     */
    public void setSmServOwnDesig(final Long smServOwnDesig) {
        this.smServOwnDesig = smServOwnDesig;
    }

    /**
     * @return the smServActive
     */
    public Long getSmServActive() {
        return smServActive;
    }

    /**
     * @param smServActive the smServActive to set
     */
    public void setSmServActive(final Long smServActive) {
        this.smServActive = smServActive;
    }

    /**
     * @return the smServType
     */
    public Long getSmServType() {
        return smServType;
    }

    /**
     * @param smServType the smServType to set
     */
    public void setSmServType(final Long smServType) {
        this.smServType = smServType;
    }

    /**
     * @return the smServCounter
     */
    public Long getSmServCounter() {
        return smServCounter;
    }

    /**
     * @param smServCounter the smServCounter to set
     */
    public void setSmServCounter(final Long smServCounter) {
        this.smServCounter = smServCounter;
    }

    /**
     * @return the smServDuration
     */
    public Long getSmServDuration() {
        return smServDuration;
    }

    /**
     * @param smServDuration the smServDuration to set
     */
    public void setSmServDuration(final Long smServDuration) {
        this.smServDuration = smServDuration;
    }

    /**
     * @return the smApplForm
     */
    public Long getSmApplForm() {
        return smApplForm;
    }

    /**
     * @param smApplForm the smApplForm to set
     */
    public void setSmApplForm(final Long smApplForm) {
        this.smApplForm = smApplForm;
    }

    /**
     * @return the smChklstVerify
     */
    public Long getSmChklstVerify() {
        return smChklstVerify;
    }

    /**
     * @param smChklstVerify the smChklstVerify to set
     */
    public void setSmChklstVerify(final Long smChklstVerify) {
        this.smChklstVerify = smChklstVerify;
    }

    /**
     * @return the smSecurityDeposit
     */
    public Long getSmSecurityDeposit() {
        return smSecurityDeposit;
    }

    /**
     * @param smSecurityDeposit the smSecurityDeposit to set
     */
    public void setSmSecurityDeposit(final Long smSecurityDeposit) {
        this.smSecurityDeposit = smSecurityDeposit;
    }

    /**
     * @return the smFeesSchedule
     */
    public Long getSmFeesSchedule() {
        return smFeesSchedule;
    }

    /**
     * @param smFeesSchedule the smFeesSchedule to set
     */
    public void setSmFeesSchedule(final Long smFeesSchedule) {
        this.smFeesSchedule = smFeesSchedule;
    }

    /**
     * @return the smAcknowledge
     */
    public Long getSmAcknowledge() {
        return smAcknowledge;
    }

    /**
     * @param smAcknowledge the smAcknowledge to set
     */
    public void setSmAcknowledge(final Long smAcknowledge) {
        this.smAcknowledge = smAcknowledge;
    }

    /**
     * @return the smScrutinyLevel
     */
    public Long getSmScrutinyLevel() {
        return smScrutinyLevel;
    }

    /**
     * @param smScrutinyLevel the smScrutinyLevel to set
     */
    public void setSmScrutinyLevel(final Long smScrutinyLevel) {
        this.smScrutinyLevel = smScrutinyLevel;
    }

    /**
     * @return the smAuthoLevel
     */
    public Long getSmAuthoLevel() {
        return smAuthoLevel;
    }

    /**
     * @param smAuthoLevel the smAuthoLevel to set
     */
    public void setSmAuthoLevel(final Long smAuthoLevel) {
        this.smAuthoLevel = smAuthoLevel;
    }

    /**
     * @return the smPrintRespons
     */
    public Long getSmPrintRespons() {
        return smPrintRespons;
    }

    /**
     * @param smPrintRespons the smPrintRespons to set
     */
    public void setSmPrintRespons(final Long smPrintRespons) {
        this.smPrintRespons = smPrintRespons;
    }

    /**
     * @return the smSpecificInfoForm
     */
    public String getSmSpecificInfoForm() {
        return smSpecificInfoForm;
    }

    /**
     * @param smSpecificInfoForm the smSpecificInfoForm to set
     */
    public void setSmSpecificInfoForm(final String smSpecificInfoForm) {
        this.smSpecificInfoForm = smSpecificInfoForm;
    }

    /**
     * @return the userId
     */
    public Employee getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(final Employee userId) {
        this.userId = userId;
    }

    /**
     * @return the langId
     */
    public int getLangId() {
        return langId;
    }

    /**
     * @param langId the langId to set
     */
    public void setLangId(final int langId) {
        this.langId = langId;
    }

    /**
     * @return the lmodDate
     */
    public Date getLmodDate() {
        return lmodDate;
    }

    /**
     * @param lmodDate the lmodDate to set
     */
    public void setLmodDate(final Date lmodDate) {
        this.lmodDate = lmodDate;
    }

    /**
     * @return the smServiceNameMar
     */
    public String getSmServiceNameMar() {
        return smServiceNameMar;
    }

    /**
     * @param smServiceNameMar the smServiceNameMar to set
     */
    public void setSmServiceNameMar(final String smServiceNameMar) {
        this.smServiceNameMar = smServiceNameMar;
    }

    /**
     * @return the cdmDeptId
     */
    public Long getCdmDeptId() {
        return cdmDeptId;
    }

    /**
     * @param cdmDeptId the cdmDeptId to set
     */
    public void setCdmDeptId(final Long cdmDeptId) {
        this.cdmDeptId = cdmDeptId;
    }

    /**
     * @return the smApprovalForm
     */
    public String getSmApprovalForm() {
        return smApprovalForm;
    }

    /**
     * @param smApprovalForm the smApprovalForm to set
     */
    public void setSmApprovalForm(final String smApprovalForm) {
        this.smApprovalForm = smApprovalForm;
    }

    /**
     * @return the smRejectionForm
     */
    public String getSmRejectionForm() {
        return smRejectionForm;
    }

    /**
     * @param smRejectionForm the smRejectionForm to set
     */
    public void setSmRejectionForm(final String smRejectionForm) {
        this.smRejectionForm = smRejectionForm;
    }

    /**
     * @return the smWebEnabled
     */
    public String getSmWebEnabled() {
        return smWebEnabled;
    }

    /**
     * @param smWebEnabled the smWebEnabled to set
     */
    public void setSmWebEnabled(final String smWebEnabled) {
        this.smWebEnabled = smWebEnabled;
    }

    /**
     * @return the smUrl
     */
    public String getSmUrl() {
        return smUrl;
    }

    /**
     * @param smUrl the smUrl to set
     */
    public void setSmUrl(final String smUrl) {
        this.smUrl = smUrl;
    }

    /**
     * @return the smSrno
     */
    public String getSmSrno() {
        return smSrno;
    }

    /**
     * @param smSrno the smSrno to set
     */
    public void setSmSrno(final String smSrno) {
        this.smSrno = smSrno;
    }

    /**
     * @return the smSwitch
     */
    public String getSmSwitch() {
        return smSwitch;
    }

    /**
     * @param smSwitch the smSwitch to set
     */
    public void setSmSwitch(final String smSwitch) {
        this.smSwitch = smSwitch;
    }

    /**
     * @return the smAddr
     */
    public String getSmAddr() {
        return smAddr;
    }

    /**
     * @param smAddr the smAddr to set
     */
    public void setSmAddr(final String smAddr) {
        this.smAddr = smAddr;
    }

    /**
     * @return the updatedBy
     */
    public Employee getUpdatedBy() {
        return updatedBy;
    }

    /**
     * @param updatedBy the updatedBy to set
     */
    public void setUpdatedBy(final Employee updatedBy) {
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
     * @return the smRcpt
     */
    public String getSmRcpt() {
        return smRcpt;
    }

    /**
     * @param smRcpt the smRcpt to set
     */
    public void setSmRcpt(final String smRcpt) {
        this.smRcpt = smRcpt;
    }

    /**
     * @return the smInitiatingEmpid
     */
    public String getSmInitiatingEmpid() {
        return smInitiatingEmpid;
    }

    /**
     * @param smInitiatingEmpid the smInitiatingEmpid to set
     */
    public void setSmInitiatingEmpid(final String smInitiatingEmpid) {
        this.smInitiatingEmpid = smInitiatingEmpid;
    }

    /**
     * @return the smCpdId
     */
    public Long getSmCpdId() {
        return smCpdId;
    }

    /**
     * @param smCpdId the smCpdId to set
     */
    public void setSmCpdId(final Long smCpdId) {
        this.smCpdId = smCpdId;
    }

    /**
     * @return the smLoiDuration
     */
    public Long getSmLoiDuration() {
        return smLoiDuration;
    }

    /**
     * @param smLoiDuration the smLoiDuration to set
     */
    public void setSmLoiDuration(final Long smLoiDuration) {
        this.smLoiDuration = smLoiDuration;
    }

    /**
     * @return the smShortdesc
     */
    public String getSmShortdesc() {
        return smShortdesc;
    }

    /**
     * @param smShortdesc the smShortdesc to set
     */
    public void setSmShortdesc(final String smShortdesc) {
        this.smShortdesc = smShortdesc;
    }

    /**
     * @return the orgId
     */
    public Organisation getOrgId() {
        return orgId;
    }

    /**
     * @param orgId the orgId to set
     */
    public void setOrgId(final Organisation orgId) {
        this.orgId = orgId;
    }

    /**
     * @return the comV1
     */
    public String getComV1() {
        return comV1;
    }

    /**
     * @param comV1 the comV1 to set
     */
    public void setComV1(final String comV1) {
        this.comV1 = comV1;
    }

    /**
     * @return the comV2
     */
    public String getComV2() {
        return comV2;
    }

    /**
     * @param comV2 the comV2 to set
     */
    public void setComV2(final String comV2) {
        this.comV2 = comV2;
    }

    /**
     * @return the comV3
     */
    public String getComV3() {
        return comV3;
    }

    /**
     * @param comV3 the comV3 to set
     */
    public void setComV3(final String comV3) {
        this.comV3 = comV3;
    }

    /**
     * @return the comV4
     */
    public String getComV4() {
        return comV4;
    }

    /**
     * @param comV4 the comV4 to set
     */
    public void setComV4(final String comV4) {
        this.comV4 = comV4;
    }

    /**
     * @return the comV5
     */
    public String getComV5() {
        return comV5;
    }

    /**
     * @param comV5 the comV5 to set
     */
    public void setComV5(final String comV5) {
        this.comV5 = comV5;
    }

    /**
     * @return the comN1
     */
    public Long getComN1() {
        return comN1;
    }

    /**
     * @param comN1 the comN1 to set
     */
    public void setComN1(final Long comN1) {
        this.comN1 = comN1;
    }

    /**
     * @return the comN2
     */
    public Long getComN2() {
        return comN2;
    }

    /**
     * @param comN2 the comN2 to set
     */
    public void setComN2(final Long comN2) {
        this.comN2 = comN2;
    }

    /**
     * @return the smfid
     */
    public Long getSmfid() {
        return smfid;
    }

    /**
     * @param smfid the smfid to set
     */
    public void setSmfid(final Long smfid) {
        this.smfid = smfid;
    }

    /**
     * @return the comN4
     */
    public Long getComN4() {
        return comN4;
    }

    /**
     * @param comN4 the comN4 to set
     */
    public void setComN4(final Long comN4) {
        this.comN4 = comN4;
    }

    /**
     * @return the comN5
     */
    public Long getComN5() {
        return comN5;
    }

    /**
     * @param comN5 the comN5 to set
     */
    public void setComN5(final Long comN5) {
        this.comN5 = comN5;
    }

    /**
     * @return the comD1
     */
    public Date getComD1() {
        return comD1;
    }

    /**
     * @param comD1 the comD1 to set
     */
    public void setComD1(final Date comD1) {
        this.comD1 = comD1;
    }

    /**
     * @return the comD2
     */
    public Date getComD2() {
        return comD2;
    }

    /**
     * @param comD2 the comD2 to set
     */
    public void setComD2(final Date comD2) {
        this.comD2 = comD2;
    }

    /**
     * @return the comD3
     */
    public Date getComD3() {
        return comD3;
    }

    /**
     * @param comD3 the comD3 to set
     */
    public void setComD3(final Date comD3) {
        this.comD3 = comD3;
    }

    /**
     * @return the smCscFlag
     */
    public String getSmCscFlag() {
        return smCscFlag;
    }

    /**
     * @param smCscFlag the smCscFlag to set
     */
    public void setSmCscFlag(final String smCscFlag) {
        this.smCscFlag = smCscFlag;
    }

    /**
     * @return the comLo2
     */
    public String getComLo2() {
        return comLo2;
    }

    /**
     * @param comLo2 the comLo2 to set
     */
    public void setComLo2(final String comLo2) {
        this.comLo2 = comLo2;
    }

    /**
     * @return the comLo3
     */
    public String getComLo3() {
        return comLo3;
    }

    /**
     * @param comLo3 the comLo3 to set
     */
    public void setComLo3(final String comLo3) {
        this.comLo3 = comLo3;
    }

    /**
     * @return the sridHours
     */
    public Long getSridHours() {
        return sridHours;
    }

    /**
     * @param sridHours the sridHours to set
     */
    public void setSridHours(final Long sridHours) {
        this.sridHours = sridHours;
    }

    /**
     * @return the sridMin
     */
    public Long getSridMin() {
        return sridMin;
    }

    /**
     * @param sridMin the sridMin to set
     */
    public void setSridMin(final Long sridMin) {
        this.sridMin = sridMin;
    }

    /**
     * @return the smChecklistHours
     */
    public Long getSmChecklistHours() {
        return smChecklistHours;
    }

    /**
     * @param smChecklistHours the smChecklistHours to set
     */
    public void setSmChecklistHours(final Long smChecklistHours) {
        this.smChecklistHours = smChecklistHours;
    }

    /**
     * @return the smChecklistMin
     */
    public Long getSmChecklistMin() {
        return smChecklistMin;
    }

    /**
     * @param smChecklistMin the smChecklistMin to set
     */
    public void setSmChecklistMin(final Long smChecklistMin) {
        this.smChecklistMin = smChecklistMin;
    }

    /**
     * @return the smChecklistDays
     */
    public Long getSmChecklistDays() {
        return smChecklistDays;
    }

    /**
     * @param smChecklistDays the smChecklistDays to set
     */
    public void setSmChecklistDays(final Long smChecklistDays) {
        this.smChecklistDays = smChecklistDays;
    }

    /**
     * @return the smServDurationType
     */
    public Long getSmServDurationType() {
        return smServDurationType;
    }

    /**
     * @param smServDurationType the smServDurationType to set
     */
    public void setSmServDurationType(final Long smServDurationType) {
        this.smServDurationType = smServDurationType;
    }

    /**
     * @return the ipMac
     */
    public String getIpMac() {
        return ipMac;
    }

    /**
     * @param ipMac the ipMac to set
     */
    public void setIpMac(final String ipMac) {
        this.ipMac = ipMac;
    }

    /**
     * @return the ipMacUpd
     */
    public String getIpMacUpd() {
        return ipMacUpd;
    }

    /**
     * @param ipMacUpd the ipMacUpd to set
     */
    public void setIpMacUpd(final String ipMacUpd) {
        this.ipMacUpd = ipMacUpd;
    }

    /**
     * @return the smServiceNote
     */
    public String getSmServiceNote() {
        return smServiceNote;
    }

    /**
     * @param smServiceNote the smServiceNote to set
     */
    public void setSmServiceNote(final String smServiceNote) {
        this.smServiceNote = smServiceNote;
    }

    /**
     * @return the smServiceNoteMar
     */
    public String getSmServiceNoteMar() {
        return smServiceNoteMar;
    }

    /**
     * @param smServiceNoteMar the smServiceNoteMar to set
     */
    public void setSmServiceNoteMar(final String smServiceNoteMar) {
        this.smServiceNoteMar = smServiceNoteMar;
    }

    /**
     * @return the smTypeOfSign
     */
    public Long getSmTypeOfSign() {
        return smTypeOfSign;
    }

    /**
     * @param smTypeOfSign the smTypeOfSign to set
     */
    public void setSmTypeOfSign(final Long smTypeOfSign) {
        this.smTypeOfSign = smTypeOfSign;
    }

    /**
     * @return the smAppliChargeFlag
     */
    public String getSmAppliChargeFlag() {
        return smAppliChargeFlag;
    }

    /**
     * @param smAppliChargeFlag the smAppliChargeFlag to set
     */
    public void setSmAppliChargeFlag(final String smAppliChargeFlag) {
        this.smAppliChargeFlag = smAppliChargeFlag;
    }

    /**
     * @return the smScrutinyChargeFlag
     */
    public String getSmScrutinyChargeFlag() {
        return smScrutinyChargeFlag;
    }

    /**
     * @param smScrutinyChargeFlag the smScrutinyChargeFlag to set
     */
    public void setSmScrutinyChargeFlag(final String smScrutinyChargeFlag) {
        this.smScrutinyChargeFlag = smScrutinyChargeFlag;
    }

    /**
     * @return the smEstimateFrom
     */
    public Long getSmEstimateFrom() {
        return smEstimateFrom;
    }

    /**
     * @param smEstimateFrom the smEstimateFrom to set
     */
    public void setSmEstimateFrom(final Long smEstimateFrom) {
        this.smEstimateFrom = smEstimateFrom;
    }

    /**
     * @return the smEstimateTo
     */
    public Long getSmEstimateTo() {
        return smEstimateTo;
    }

    /**
     * @param smEstimateTo the smEstimateTo to set
     */
    public void setSmEstimateTo(final Long smEstimateTo) {
        this.smEstimateTo = smEstimateTo;
    }

    /**
     * @return the smNotloginFlg
     */
    public String getSmNotloginFlg() {
        return smNotloginFlg;
    }

    /**
     * @param smNotloginFlg the smNotloginFlg to set
     */
    public void setSmNotloginFlg(final String smNotloginFlg) {
        this.smNotloginFlg = smNotloginFlg;
    }

    public String getServiceNameByLanguage() {
        if (UserSession.getCurrent().getLanguageId() == MainetConstants.ENGLISH) {
            return getSmServiceName();
        } else if (UserSession.getCurrent().getLanguageId() == MainetConstants.MARATHI) {
            if ((getSmServiceNameMar() != null) || !getSmServiceNameMar().equals(MainetConstants.BLANK)) {
                return getSmServiceNameMar();
            } else {
                return getSmServiceName();
            }
        }
        return MainetConstants.BLANK;
    }

    public String[] getPkValues() {

        return new String[] { "DROP", "TB_SERVICES_MST", "SM_SERVICE_ID" };
    }
}