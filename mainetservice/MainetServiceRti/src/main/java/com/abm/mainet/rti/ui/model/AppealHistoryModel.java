package com.abm.mainet.rti.ui.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Component
@Scope("session")
public class AppealHistoryModel extends AbstractFormModel {

    private static final long serialVersionUID = 1L;
    private List<RtiApplicationFormDetailsReqDTO> rtiList = new ArrayList<>();
    private RtiApplicationFormDetailsReqDTO reqDTO = new RtiApplicationFormDetailsReqDTO();
    private Set<LookUp> locations = new HashSet<>();
    private String isValidationError;
    private Set<LookUp> departments = new HashSet<>();
    private ServiceMaster serviceMaster = new ServiceMaster();
    private String viewEditFlag;

    @Autowired
    IRtiApplicationDetailService rtiApplicationDetailService;

    @Autowired
    private ISMSAndEmailService ismsAndEmailService;

    @Override
    protected void initializeModel() {

        departments.addAll(
                rtiApplicationDetailService.getActiveDepartment(UserSession.getCurrent().getOrganisation().getOrgid()));

    }

    @Override
    public boolean saveForm() {

        RtiApplicationFormDetailsReqDTO requestDTO = this.getReqDTO();
        requestDTO = rtiApplicationDetailService.saveAppealHistory(requestDTO);
        setSuccessMessage(getAppSession().getMessage("rti.success"));

        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setAppName(requestDTO.getApplicantName());
        dto.setMobnumber(requestDTO.getMobileNo());
        dto.setAppNo(requestDTO.getApmApplicationId().toString());
        dto.setAppDate(requestDTO.getRtiDeciRecDateDesc());
        ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
                .getServiceMasterByShortCode("RAF", UserSession.getCurrent().getOrganisation().getOrgid());
        setServiceMaster(sm);
        dto.setServName(sm.getSmServiceName());
        dto.setEmail(requestDTO.getEmail());
        String paymentUrl = "AppealHistory.html";
        Organisation org = new Organisation();
        org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
        int langId = Utility.getDefaultLanguageId(org);
        dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
        ismsAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.RTI, paymentUrl,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);

        return true;

    }

    /* for validating Inputs and other functional validation */
    public boolean validateInputs() {
        if (hasValidationErrors()) {
            this.isValidationError = MainetConstants.Y_FLAG;
            return false;
        }
        return true;
    }
    /* end of validation method */

    public List<RtiApplicationFormDetailsReqDTO> getRtiList() {
        return rtiList;
    }

    public void setRtiList(List<RtiApplicationFormDetailsReqDTO> rtiList) {
        this.rtiList = rtiList;
    }

    public RtiApplicationFormDetailsReqDTO getReqDTO() {
        return reqDTO;
    }

    public void setReqDTO(RtiApplicationFormDetailsReqDTO reqDTO) {
        this.reqDTO = reqDTO;
    }

    public Set<LookUp> getLocations() {
        return locations;
    }

    public void setLocations(Set<LookUp> locations) {
        this.locations = locations;
    }

    public String getIsValidationError() {
        return isValidationError;
    }

    public void setIsValidationError(String isValidationError) {
        this.isValidationError = isValidationError;
    }

    public Set<LookUp> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<LookUp> departments) {
        this.departments = departments;
    }

    public ServiceMaster getServiceMaster() {
        return serviceMaster;
    }

    public void setServiceMaster(ServiceMaster serviceMaster) {
        this.serviceMaster = serviceMaster;
    }

    public String getViewEditFlag() {
        return viewEditFlag;
    }

    public void setViewEditFlag(String viewEditFlag) {
        this.viewEditFlag = viewEditFlag;
    }

}
