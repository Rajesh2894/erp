package com.abm.mainet.care.ui.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.ComplaintRegistrationModel;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTRESUBMISSION)
public class GrievanceDepartmentResubmissionController extends AbstractFormController<ComplaintRegistrationModel> {

    @Autowired
    private ICareRequestService careRequestService;

    @Resource
    private IFileUploadService fileUploadService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView resubmitApplication(@ModelAttribute("appNo") final Long applicationId,
            final HttpServletRequest httpServletRequest) throws Exception {

        sessionCleanup(httpServletRequest);
        fileUploadService.sessionCleanUpForFileUpload();
        bindModel(httpServletRequest);
        ComplaintRegistrationModel model = getModel();
        if (applicationId != null) {
            CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
            CareRequestDTO crr = CareUtility.toCareRequestDTO(careRequest);
            ComplaintAcknowledgementModel ackm = careRequestService
                    .getComplaintAcknowledgementModel(careRequest, UserSession.getCurrent().getLanguageId());
            if (ackm != null) {
                model.setCareRequest(crr);
                model.setComplaintAcknowledgementModel(ackm);
                return new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVANCERESUBMISSION,
                        MainetConstants.FORM_NAME,
                        model);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.RESUBMIT_GRIEVANCE, method = RequestMethod.POST)
    public ModelAndView reopCompalint(HttpServletRequest request, ModelMap modelMap) throws Exception {
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();
        CareRequestDTO crr = model.getCareRequest();
        WorkflowTaskAction resubmitAction = model.getCareDepartmentAction();
        CareRequest careRequest = careRequestService.findByApplicationId(crr.getApplicationId());
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);

        model.setAttachments(fileUploadService.setFileUploadMethod(model.getAttachments()));
        applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        fileUploadService.doFileUpload(model.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(crr.getApplicationId(),
                applicantDetailDto.getOrgId());
        resubmitAction.setApplicationId(crr.getApplicationId());
        resubmitAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        resubmitAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        resubmitAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        resubmitAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        resubmitAction.setAttachementId(attachmentId);
        resubmitAction.setCreatedDate(new Date());
        resubmitAction.setDateOfAction(new Date());
        resubmitAction.setDecision(MainetConstants.WorkFlow.Decision.SUBMITTED);
        ActionResponse actResponse = careRequestService.resubmitCareProces(careRequest, resubmitAction);
        if (MainetConstants.COMMON_STATUS.SUCCESS.equalsIgnoreCase(actResponse.getResponse())) {
            if (actResponse != null) {
                if (null != actResponse.getError()
                        && !MainetConstants.BLANK.equals(actResponse.getError())) {
                    request.getSession().setAttribute(
                            MainetConstants.DELETE_ERROR, actResponse.getError());
                } else {
                    String actionKey = actResponse
                            .getResponseData(MainetConstants.RESPONSE);
                    if (null != actionKey) {
                        String requestNo = actResponse
                                .getResponseData(MainetConstants.REQUEST_NO);
                        if (null != requestNo) {
                            String message = MainetConstants.REQUEST_NUMBER + requestNo + MainetConstants.SUBMITTED_SUCCESSFULLY;
                            modelMap.addAttribute(MainetConstants.SUCCESS_MESSAGE, message);
                        }
                    }
                }
            }
            ComplaintAcknowledgementModel complaintAcknowledgementModel = careRequestService
                    .getComplaintAcknowledgementModel(careRequest, UserSession.getCurrent().getLanguageId());
            modelMap.addAttribute(MainetConstants.ServiceCareCommon.COMPLAINT_ACKNOWLEDGEMENT_MODEL,
                    complaintAcknowledgementModel);
            return new ModelAndView(MainetConstants.ServiceCareCommon.REGISTRATION_ACKNOWLEDGEMENT_RECEIPT,
                    MainetConstants.ServiceCareCommon.CARE_REQUEST,
                    careRequest);
        }
        return null;
    }

}
