package com.abm.mainet.cfc.checklist.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BaseEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.UserSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Lalit.Prusti
 * @since 22 Jun 2015
 */
@Entity
@Table(name = "VW_CFC_CHECKLIST")
public class ChecklistStatusView extends BaseEntity {

    private static final long serialVersionUID = -293820475171815452L;

    @Id
    @Column(name = "APM_APPLICATION_ID", precision = 16, scale = 0, nullable = false)
    private Long apmApplicationId;

    @Column(name = "ORGID", nullable = false)
    private Long organisationId;

    @Column(name = "APM_APPLICATION_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date apmApplicationDate;

    @Column(name = "ENGLIS_TITLE", length = 4000, nullable = true)
    private String englisTitle;

    @Column(name = "REGIONAL_TITLE", length = 4000, nullable = true)
    private String regionalTitle;

    @Column(name = "APPLICANTS_NAME", length = 302, nullable = true)
    private String applicantsName;

    @Column(name = "SM_SERVICE_ID", precision = 12, scale = 0, nullable = false)
    private Long smServiceId;

    @Column(name = "SERVICE_NAME", length = 100, nullable = true)
    private String serviceName;

    @Column(name = "SERVICE_NAME_MAR", length = 200, nullable = true)
    private String serviceNameMar;

    @Column(name = "APM_CHKLST_VRFY_FLAG", length = 1, nullable = true)
    private String apmChklstVrfyFlag;

    @Column(name = "CDM_DEPT_ID", precision = 12, scale = 0, nullable = true)
    private Long cdmDeptId;
    
    @Column(name = "REF_NO", length = 100, nullable = true)
    private String ref_no;

    public Long getCdmDeptId() {
        return cdmDeptId;
    }

    public void setCdmDeptId(final Long cdmDeptId) {
        this.cdmDeptId = cdmDeptId;
    }

    public Long getApmApplicationId() {
        return apmApplicationId;
    }

    public void setApmApplicationId(final Long apmApplicationId) {
        this.apmApplicationId = apmApplicationId;
    }

    public Date getApmApplicationDate() {
        return apmApplicationDate;
    }

    public void setApmApplicationDate(final Date apmApplicationDate) {
        this.apmApplicationDate = apmApplicationDate;
    }

    public String getEnglisTitle() {
        return englisTitle;
    }

    public void setEnglisTitle(final String englisTitle) {
        this.englisTitle = englisTitle;
    }

    public String getRegionalTitle() {
        return regionalTitle;
    }

    public void setRegionalTitle(final String regionalTitle) {
        this.regionalTitle = regionalTitle;
    }

    public String getApplicantsName() {
        return applicantsName;
    }

    public void setApplicantsName(final String applicantsName) {
        this.applicantsName = applicantsName;
    }

    public Long getSmServiceId() {
        return smServiceId;
    }

    public void setSmServiceId(final Long smServiceId) {
        this.smServiceId = smServiceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceNameMar() {
        return serviceNameMar;
    }

    public void setServiceNameMar(final String serviceNameMar) {
        this.serviceNameMar = serviceNameMar;
    }

    public String getApmChklstVrfyFlag() {
        return apmChklstVrfyFlag;
    }

    public void setApmChklstVrfyFlag(final String apmChklstVrfyFlag) {
        this.apmChklstVrfyFlag = apmChklstVrfyFlag;
    }

    @Override
    public long getId() {
        return apmApplicationId;
    }

    @Override
    public Organisation getOrgId() {
        return null;
    }

    @Override
    public void setOrgId(final Organisation orgId) {
    }

    @Override
    public Employee getUserId() {
        return null;
    }

    @Override
    public void setUserId(final Employee userId) {
    }

    @Override
    public Employee getUpdatedBy() {
        return null;
    }

    @Override
    public void setUpdatedBy(final Employee updatedBy) {
    }

    @Override
    public int getLangId() {
        return 0;
    }

    @Override
    public void setLangId(final int langId) {

    }

    @Override
    public Date getLmodDate() {
        return null;
    }

    @Override
    public void setLmodDate(final Date lmodDate) {
    }

    @Override
    public String getLgIpMac() {
        return null;
    }

    @Override
    public void setLgIpMac(final String lgIpMac) {

    }

    @Override
    public Date getUpdatedDate() {
        return null;
    }

    @Override
    public void setUpdatedDate(final Date updatedDate) {
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

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(final Long organisationId) {
        this.organisationId = organisationId;
    }

    @JsonIgnore
    public String getApprovalStatus() {
        String status = null;
        switch (getApmChklstVrfyFlag()) {
        case "Y":
            status = getAppSession().getMessage("cfc.status.approve");
            break;
        case "N":
            status = getAppSession().getMessage("cfc.status.reject");
            break;
        case "H":
            status = getAppSession().getMessage("cfc.status.hold");
            break;
        case "R":
            status = getAppSession().getMessage("cfc.status.resubmit");
            break;
        default:
            status = getAppSession().getMessage("cfc.status.pending");
            break;
        }
        return status;
    }

    public String getApplicationService() {
        final int langId = UserSession.getCurrent().getLanguageId();
        String service = "";
        service = serviceName;
        if (langId == MainetConstants.MARATHI) {
            if (null != serviceNameMar) {
                service = serviceNameMar;
            }
        }

        return service;

    }

	public String getRef_no() {
		return ref_no;
	}

	public void setRef_no(String ref_no) {
		this.ref_no = ref_no;
	}
}