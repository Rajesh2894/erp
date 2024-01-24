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

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rajdeep.Sinha
 * @since 18 Dec 2015
 * @comment RTS Service Appeal Master
 */
@Entity
@Table(name = "TB_PORTAL_SERVICE_MASTER")
public class PortalService extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GenericGenerator(name = "MyCustomGenerator", strategy = "com.abm.mainet.common.dao.SequenceIdGenerator")
    @GeneratedValue(generator = "MyCustomGenerator")
    @Column(name = "psm_id", precision = 12, scale = 0, nullable = false)
    private long id;

    @Column(name = "psm_service_id", precision = 12, scale = 0, nullable = true)
    private Long serviceId;

    @Column(name = "psm_doc_verify_period", precision = 12, scale = 0, nullable = true)
    private Long docVerifyPeriod;

    @Column(name = "psm_sla_days", precision = 12, scale = 0, nullable = true)
    private Long slaDays;

    @Column(name = "psm_first_appeal_duration", precision = 12, scale = 0, nullable = true)
    private Long firstAppealDuration;

    @Column(name = "psm_second_appeal_duration", precision = 12, scale = 0, nullable = true)
    private Long secondAppealDuration;

    @Column(name = "ORGID")
    private Long serviceOrgId;

    @Column(name = "psm_service_name")
    private String serviceName;

    @Column(name = "psm_service_name_reg")
    private String serviceNameReg;

    @Column(name = "psm_short_name")
    private String shortName;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", updatable = false)
    private Employee userId;

    @Column(name = "LANG_ID", precision = 12, scale = 0, nullable = true)
    private int langId;

    @Column(name = "LMODDATE", nullable = true)
    private Date lmodDate;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", nullable = false, updatable = true)
    private Employee updatedBy;

    @Column(name = "UPDATED_DATE", nullable = true)
    private Date updatedDate;

    @Column(name = "IS_DELETED", length = 1, nullable = true)
    private String isDeleted;

    @Column(name = "PSM_SMFID")
    private Long psmSmfid;
    @Column(name = "PSM_DP_DEPTID")
    private Long psmDpDeptid;

    @Column(name = "PSM_FIRST_APL_AUTHO")
    private String firstAppealAuthority;

    @Column(name = "PSM_SEC_APL_AUTHO")
    private String secondAppealAuthority;

    @Column(name = "PSM_DP_DEPTCODE")
    private String psmDpDeptCode;

    @Column(name = "PSM_DP_DEPTDESC")
    private String psmDpDeptDesc;

    @Column(name = "PSM_DP_NAME_MAR")
    private String psmDpNameMar;

    public String getFirstAppealAuthority() {
        return firstAppealAuthority;
    }

    public void setFirstAppealAuthority(final String firstAppealAuthority) {
        this.firstAppealAuthority = firstAppealAuthority;
    }

    public String getSecondAppealAuthority() {
        return secondAppealAuthority;
    }

    public void setSecondAppealAuthority(final String secondAppealAuthority) {
        this.secondAppealAuthority = secondAppealAuthority;
    }

    public Long getPsmSmfid() {
        return psmSmfid;
    }

    public void setPsmSmfid(final Long psmSmfid) {
        this.psmSmfid = psmSmfid;
    }

    public Long getPsmDpDeptid() {
        return psmDpDeptid;
    }

    public void setPsmDpDeptid(final Long psmDpDeptid) {
        this.psmDpDeptid = psmDpDeptid;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getDocVerifyPeriod() {
        return docVerifyPeriod;
    }

    public void setDocVerifyPeriod(final Long docVerifyPeriod) {
        this.docVerifyPeriod = docVerifyPeriod;
    }

    public Long getSlaDays() {
        return slaDays;
    }

    public void setSlaDays(final Long slaDays) {
        this.slaDays = slaDays;
    }

    public Long getFirstAppealDuration() {
        return firstAppealDuration;
    }

    public void setFirstAppealDuration(final Long firstAppealDuration) {
        this.firstAppealDuration = firstAppealDuration;
    }

    public Long getSecondAppealDuration() {
        return secondAppealDuration;
    }

    public void setSecondAppealDuration(final Long secondAppealDuration) {
        this.secondAppealDuration = secondAppealDuration;
    }

    public Long getServiceOrgId() {
        return serviceOrgId;
    }

    public void setServiceOrgId(final Long serviceOrgId) {
        this.serviceOrgId = serviceOrgId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameReg() {
        return serviceNameReg;
    }

    public void setServiceNameReg(final String serviceNameReg) {
        this.serviceNameReg = serviceNameReg;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
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
    public String getIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(final String isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public void setOrgId(final Organisation orgId) {

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
    public Organisation getOrgId() {

        return null;
    }

    public String getPsmDpDeptCode() {
        return psmDpDeptCode;
    }

    public void setPsmDpDeptCode(String psmDpDeptCode) {
        this.psmDpDeptCode = psmDpDeptCode;
    }

    public String getPsmDpDeptDesc() {
        return psmDpDeptDesc;
    }

    public void setPsmDpDeptDesc(String psmDpDeptDesc) {
        this.psmDpDeptDesc = psmDpDeptDesc;
    }

    public String getPsmDpNameMar() {
        return psmDpNameMar;
    }

    public void setPsmDpNameMar(String psmDpNameMar) {
        this.psmDpNameMar = psmDpNameMar;
    }

    public String[] getPkValues() {

        return new String[] { "AUT", "TB_PORTAL_SERVICE_MASTER", "psm_id" };
    }

}