package com.abm.mainet.authentication.agency.ui.model;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.Common;
//import com.abm.mainet.common.domain.AgencyAuthorizationRejectDocView;
//import com.abm.mainet.common.domain.CFCCheckListMaster;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.model.AbstractEntryFormModel;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 *
 * @author vivek.kumar
 *
 */

@Component
@Scope(value = "session")
public class AgencyDOCVerificationModel extends AbstractEntryFormModel<Employee> implements Serializable {

    private static final long serialVersionUID = -7814883402282385873L;

    private long advId;

    private String agencyLoc;

    private Integer agencyExperience;

    private String agencyQualification;

    private Integer agencyRegNo;

    private String authorizedRName;

    private String agencyName;

    private String autMob;

    private String agencyLicenseNo;

    private String advFirstNm;

    private String advAdd1;
    private String advMobile;
    private Integer advExperience;

    private Employee agencyEmployee;

    /**
     *
     * @return ServiceURL to get document required
     */
    public String getServiceURL() {
        final String agency = getAgencyType();
        if (agency.equals(MainetConstants.AGENCY.NAME.BUILDER) || agency.equals(MainetConstants.AGENCY.NAME.ARCHITECT)
                || agency.equals(MainetConstants.AGENCY.NAME.ENGINEER)) {
            return MainetConstants.AGENCY.URL.BUILDER;
        } else if (agency.equals(MainetConstants.AGENCY.NAME.CYBER) || agency.equals(MainetConstants.AGENCY.NAME.CFC)) {
            return MainetConstants.AGENCY.URL.CYBER_CAFE;
        } else if (agency.equals(MainetConstants.AGENCY.NAME.HOSPITAL)) {
            return MainetConstants.AGENCY.URL.HOSPITAL;
        } else if (agency.equals(MainetConstants.AGENCY.NAME.CREMATORIA)) {
            return MainetConstants.AGENCY.URL.CREMATORIA;
        } else if (agency.equals(MainetConstants.AGENCY.NAME.ADVOCATE)) {
            return MainetConstants.AGENCY.URL.ADVOCATE;
        } else if (agency.equals(MainetConstants.AGENCY.NAME.ADVERTISE)) {
            return MainetConstants.PG_URL.ADVERTISE_AGENCY_REGISTRATION;
        }

        return MainetConstants.BLANK;
    }

    /**
     *
     * @return type of Agency
     */
    public String getAgencyType() {
        final List<LookUp> emplLookUps = this.getLevelData(MainetConstants.NEC.PARENT);

        for (final LookUp lookUp : emplLookUps) {
            if (lookUp.getLookUpId() == getUserSession().getEmployee().getEmplType()) {
                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.ARCHITECT)) {
                    return MainetConstants.AGENCY.NAME.ARCHITECT;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.BUILDER)) {
                    return MainetConstants.AGENCY.NAME.BUILDER;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.ENGINEER)) {
                    return MainetConstants.AGENCY.NAME.ENGINEER;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.HOSPITAL)) {
                    return MainetConstants.AGENCY.NAME.HOSPITAL;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CYBER)) {
                    return MainetConstants.AGENCY.NAME.CYBER;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CENTER)) {
                    return MainetConstants.AGENCY.NAME.CFC;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CREMATORIA)) {
                    return MainetConstants.AGENCY.NAME.CREMATORIA;
                }

                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.EMPLOYEE)) {
                    return MainetConstants.AGENCY.NAME.EMPLOYEE;
                }
                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.CITIZEN)) {
                    return MainetConstants.AGENCY.NAME.CITIZEN;
                }
                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.ADVOCATE)) {
                    return MainetConstants.AGENCY.NAME.ADVOCATE;
                }
                if (lookUp.getLookUpCode().equals(MainetConstants.NEC.ADVERTISE)) {
                    return MainetConstants.AGENCY.NAME.ADVERTISE;
                }

            }
        }

        return MainetConstants.BLANK;
    }

    public String getDirectory() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + MainetConstants.CFC_ATTACHMENT
                + File.separator + MainetConstants.AGENCY_UPLOADED_DOC + File.separator
                + getUserSession().getEmployee().getEmpId();
    }

    public String getDirectoryAfterReject() {
        return UserSession.getCurrent().getOrganisation().getOrgid() + File.separator + MainetConstants.CFC_ATTACHMENT
                + File.separator + MainetConstants.AGENCY_UPLOADED_DOC + File.separator
                + getUserSession().getEmployee().getEmpId() + File.separator + MainetConstants.AFTER_REJECT;
    }

    @Override
    protected void initializeModel() {
        super.initializeModel();
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        initializeLookupFields(Common.HOSPITAL_TYPE);

    }

    @Override
    protected final String findPropertyPathPrefix(final String parentCode) {
        switch (parentCode) {
        case Common.HOSPITAL_TYPE:
            return "entity.hospitalType";
        }
        return null;
    }

    @Override
    public void cleareUploadFile() {

        getEntity().getCfcAttachments().clear();
    }

    public String getAgencyLoc() {
        return agencyLoc;
    }

    public void setAgencyLoc(final String agencyLoc) {
        this.agencyLoc = agencyLoc;
    }

    public Integer getAgencyExperience() {
        return agencyExperience;
    }

    public void setAgencyExperience(final Integer agencyExperience) {
        this.agencyExperience = agencyExperience;
    }

    public String getAgencyQualification() {
        return agencyQualification;
    }

    public void setAgencyQualification(final String agencyQualification) {
        this.agencyQualification = agencyQualification;
    }

    public Integer getAgencyRegNo() {
        return agencyRegNo;
    }

    public void setAgencyRegNo(final Integer agencyRegNo) {
        this.agencyRegNo = agencyRegNo;
    }

    public String getAuthorizedRName() {
        return authorizedRName;
    }

    public void setAuthorizedRName(final String authorizedRName) {
        this.authorizedRName = authorizedRName;
    }

    public long getAdvId() {
        return advId;
    }

    public void setAdvId(final long advId) {
        this.advId = advId;
    }

    public String getAutMob() {
        return autMob;
    }

    public void setAutMob(final String autMob) {
        this.autMob = autMob;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(final String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAdvExperience() {
        return advExperience;
    }

    public void setAdvExperience(final Integer advExperience) {
        this.advExperience = advExperience;
    }

    public String getAgencyLicenseNo() {
        return agencyLicenseNo;
    }

    public void setAgencyLicenseNo(final String agencyLicenseNo) {
        this.agencyLicenseNo = agencyLicenseNo;
    }

    public String getAdvFirstNm() {
        return advFirstNm;
    }

    public void setAdvFirstNm(final String advFirstNm) {
        this.advFirstNm = advFirstNm;
    }

    public String getAdvAdd1() {
        return advAdd1;
    }

    public void setAdvAdd1(final String advAdd1) {
        this.advAdd1 = advAdd1;
    }

    public String getAdvMobile() {
        return advMobile;
    }

    public void setAdvMobile(final String advMobile) {
        this.advMobile = advMobile;
    }

    public Employee getAgencyEmployee() {
        return agencyEmployee;
    }

    public void setAgencyEmployee(final Employee agencyEmployee) {
        this.agencyEmployee = agencyEmployee;
    }

}
