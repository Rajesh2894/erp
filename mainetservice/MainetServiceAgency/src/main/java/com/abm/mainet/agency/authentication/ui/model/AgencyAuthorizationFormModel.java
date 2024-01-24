package com.abm.mainet.agency.authentication.ui.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.agency.authentication.service.AgencyAuthorizationService;
import com.abm.mainet.agency.authentication.ui.validation.AgencyAuthorizationFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.integration.dms.dto.CFCAttachmentsDTO;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
@Scope("session")
public class AgencyAuthorizationFormModel extends AbstractFormModel implements Serializable {

    private static final long serialVersionUID = 3641353589119673466L;
    private EmployeeDTO entity = new EmployeeDTO();

    /**
     * @return the entity
     */
    public EmployeeDTO getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(final EmployeeDTO entity) {
        this.entity = entity;
    }
    @Autowired
    private IEmployeeService iEmployeeService;

    @Autowired
    private AgencyAuthorizationService agencyAuthorizationService;


    private Date interviewDate;

    private Date appointmentDate;

    private long auth_By;

    private int fileSize;
    private String docApprStatus;
    private boolean isApprovedEnable = false;
    private String licenceNo;
    private String propertyNo;
    private String errorMassage;

    private List<LookUp> agencyLocationList = new ArrayList<>();

    private String titleName;

    public boolean getCheckAllDocApprStatus() {

        for (final CFCAttachmentsDTO temp : getEntity().getCfcAttachments()) {
            if ((temp.getClmAprStatus() == null) || temp.getClmAprStatus().equals("N")) {
                return false;
            }
        }
        return true;
    }

    public boolean saveOrUpdateForm() {


        validateBean(getEntity(), AgencyAuthorizationFormValidator.class);

        if (hasValidationErrors()) {
            return false;
        }
       /**
            * used this condition becoz callPRUpdateServiceProcedure internal update Agency Master
            */

        if (isTownPlanningPerson()) {
        }

        return agencyAuthorizationService.saveApprovalStatus(getEntity());

    }

    private boolean isTownPlanningPerson() {

        final String emplType = CommonMasterUtility.getNonHierarchicalLookUpObject(getEntity().getEmplType()).getLookUpCode();

        if (emplType.equals(PrefixConstants.NEC.ARCHITECT)
                || emplType.equals(PrefixConstants.NEC.ENGINEER)
                || emplType.equals(PrefixConstants.NEC.STRUCTURAL_ENGINEER)
                || emplType.equals(PrefixConstants.NEC.SUPERVISOR)
                || emplType.equals(PrefixConstants.NEC.TOWN_PLANNER)
                || emplType.equals(PrefixConstants.NEC.BUILDER)) {
            return true;
        }

        return false;
    }
    @Override
    public void editForm(final long rowId) {
        final EmployeeDTO agencyAsEmployee = iEmployeeService.getEmployeeById(rowId, UserSession.getCurrent().getOrganisation(),
                MainetConstants.IsDeleted.ZERO);

        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        setAuth_By(UserSession.getCurrent().getEmployee().getEmpId());
        setInterviewDate(null);
        setAppointmentDate(null);
        agencyAsEmployee.setDpDeptid(getEntity().getDpDeptid());
        agencyAsEmployee.setIsdeleted("0");
        setEntity(agencyAsEmployee);
        final String agencyName = getAgencyType();
        agencyAsEmployee.setCfcAttachments(agencyAuthorizationService.getAgencyAttachmentsByRowId(rowId, orgId));
        setEntity(agencyAsEmployee);

        if (isTownPlanningPerson()) {

            final Long cpdTitleId = getEntity().getTitle();
            final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(cpdTitleId);
            setTitleName(lookUp.getLookUpDesc());
            if (agencyName.equals("Builder")) {

            }

        }
    }

    public boolean getDocumentStatus() {
        for (final CFCAttachmentsDTO temp : getEntity().getCfcAttachments()) {
            if (temp.isMendatory()) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public List<LookUp> getAllAgencyDoc() {
        final List<LookUp> detailsList = new ArrayList<>(0);

        for (final CFCAttachmentsDTO temp : getEntity().getCfcAttachments()) {
            final LookUp lookUp = new LookUp(temp.getAttId(), temp.getAttPath());
            if (temp.isMendatory()) {
                lookUp.setLookUpId(1);
            } else {
                lookUp.setLookUpId(0);
            }
            lookUp.setLookUpDesc(temp.getAttPath());
            lookUp.setLookUpCode(temp.getAttFname());
            lookUp.setLookUpType(temp.getClmAprStatus());
            detailsList.add(lookUp);
        }
        fileSize = 0;
        if (!detailsList.isEmpty()) {
            fileSize = detailsList.size();
        }
        return detailsList;
    }

    public String getAgencyType() {

        String emplType = MainetConstants.BLANK;

        final List<LookUp> empllookup = this.getLevelData(PrefixConstants.NEC.PARENT);
        for (final LookUp looks : empllookup) {
            if (looks.getLookUpId() == getEntity().getEmplType()) {
                emplType = looks.getDescLangFirst();
            }
        }
        return emplType;
    }

    public String getAgencyCode() {

        String cpdOtherVal = "";

        final List<LookUp> empllookup = this.getLevelData(PrefixConstants.NEC.PARENT);
        for (final LookUp looks : empllookup) {
            if (looks.getLookUpId() == getEntity().getEmplType()) {
                cpdOtherVal = looks.getOtherField();
            }
        }
        return cpdOtherVal;
    }

    public String getCpdValue() {
        final List<LookUp> empllookup = this.getLevelData(PrefixConstants.NEC.PARENT);
        for (final LookUp looks : empllookup) {
            if (looks.getLookUpId() == getEntity().getEmplType()) {
                return looks.getLookUpCode();
            }
        }

        return "";
    }
    /**
     * @return the auth_By
     */
    public long getAuth_By() {
        return auth_By;
    }

    /**
     * @param auth_By the auth_By to set
     */
    public void setAuth_By(final long auth_By) {
        this.auth_By = auth_By;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(final Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(final Date interviewDate) {
        this.interviewDate = interviewDate;
    }

    public String getDocApprStatus() {
        return docApprStatus;
    }

    public void setDocApprStatus(final String docApprStatus) {
        this.docApprStatus = docApprStatus;
    }

    public boolean getIsApprovedEnable() {
        return isApprovedEnable;
    }

    public void setIsApprovedEnable(final boolean isApprovedEnable) {
        this.isApprovedEnable = isApprovedEnable;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(final int fileSize) {
        this.fileSize = fileSize;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(final String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getPropertyNo() {
        return propertyNo;
    }

    public void setPropertyNo(final String propertyNo) {
        this.propertyNo = propertyNo;
    }

    public String getErrorMassage() {
        return errorMassage;
    }

    public void setErrorMassage(final String errorMassage) {
        this.errorMassage = errorMassage;
    }

    /**
     * @return the agencyLocationList
     */
    public List<LookUp> getAgencyLocationList() {
        return agencyLocationList;
    }

    /**
     * @param agencyLocationList the agencyLocationList to set
     */
    public void setAgencyLocationList(final List<LookUp> agencyLocationList) {
        this.agencyLocationList = agencyLocationList;
    }

    /**
     * @return the titleName
     */
    public String getTitleName() {
        return titleName;
    }

    /**
     * @param titleName the titleName to set
     */
    public void setTitleName(final String titleName) {
        this.titleName = titleName;
    }
}
