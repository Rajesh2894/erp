package com.abm.mainet.care.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.care.domain.CareFeedback;
import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.domain.ComplaintAcknowledgementModel;
import com.abm.mainet.care.dto.CareFeedbackDTO;
import com.abm.mainet.care.dto.CareRequestDTO;
import com.abm.mainet.care.dto.ComplaintSearchDTO;
import com.abm.mainet.care.service.ICareFeedbackService;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.ComplaintRegistrationModel;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.ITaskService;

@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH + MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTREOPEN)
public class GrievanceDepartmentReopenController extends AbstractFormController<ComplaintRegistrationModel> {

    @Autowired
    private ICareRequestService careRequestService;

    @Resource
    private IFileUploadService fileUploadService;

    @Resource
    private ICareFeedbackService careFeedbackService;

    @Autowired
    private IChecklistVerificationService iChecklistVerificationService;

    @Autowired
    private ITaskService taskService;
    
	private static final Logger LOGGER = Logger.getLogger(GrievanceDepartmentReopenController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView viewCompalintSearchForm(HttpServletRequest request) {
        sessionCleanup(request);
        fileUploadService.sessionCleanUpForFileUpload();
        bindModel(request);
        getModel().setCommonHelpDocs(MainetConstants.ServiceCareCommon.GRIEVANCEDEPARTMENTREOPEN);
        return new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM,
                MainetConstants.CommonConstants.COMMAND,
                getModel());
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.SEARCH_GRIEVANCE, method = RequestMethod.POST)
    public ModelAndView viewCompalint(@RequestParam(MainetConstants.ServiceCareCommon.SEARCH_STRING) String searchString,
            HttpServletRequest request, @RequestParam(value = "hitFrom", required = false) String hitFrom)
            throws IllegalAccessException, InvocationTargetException {
        ModelAndView modelAndView = null;
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();
        List<CareRequestDTO> careRequests = null;
        String kdmcEnv = MainetConstants.N_FLAG;
        String dsclEnv = MainetConstants.N_FLAG;
        String asclEnv = MainetConstants.N_FLAG;

        ComplaintSearchDTO filter = new ComplaintSearchDTO();
        // filter.setApplicationId(Long.parseLong(searchString));
        filter.setMobileNumber(searchString);
        filter.setComplaintId(searchString);

        if (UserSession.getCurrent().getOrganisation().getDefaultStatus()
                .equalsIgnoreCase(MainetConstants.Organisation.SUPER_ORG_STATUS)) {
            careRequests = careRequestService.findComplaint(filter);
        } else {
            filter.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            careRequests = careRequestService.findComplaint(filter);
        }

        // D#112758 here filter in case of SKDCL
        if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid()) ||
        		CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL, UserSession.getCurrent().getOrganisation().getOrgid())||
        		CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, UserSession.getCurrent().getOrganisation().getOrgid())) {
            /// validation for rejected and pending complaint if user input
            if (careRequests.size() == 1 && careRequests.get(0).getStatus().equals(MainetConstants.WorkFlow.Status.PENDING)) {
                getModel().addValidationError(
                        getApplicationSession().getMessage("care.cant.reopen.pending.invalid"));
                return modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
            } else if (careRequests.size() == 1
                    && careRequests.get(0).getLastDecision().equals(MainetConstants.WorkFlow.Decision.FORCE_CLOSURE)) {
                getModel().addValidationError(
                        getApplicationSession().getMessage("care.cant.reopen.forceclosure.invalid"));
                return modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
            } else if (careRequests.size() == 1
                    && careRequests.get(0).getStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED)) {
                getModel().addValidationError(
                        getApplicationSession().getMessage("care.cant.reopen.rejected.invalid"));
                return modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
            } else if (careRequests.size() > 1) {
                careRequests.removeIf(care -> care.getStatus().equals(MainetConstants.WorkFlow.Status.PENDING)
                        || care.getStatus().equals(MainetConstants.WorkFlow.Decision.REJECTED));
            }
            
        }
        if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())){
        	kdmcEnv = MainetConstants.Y_FLAG;
        }else if(CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL, UserSession.getCurrent().getOrganisation().getOrgid())){
        	dsclEnv = MainetConstants.Y_FLAG;
        }else if(CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, UserSession.getCurrent().getOrganisation().getOrgid())){
        	asclEnv = MainetConstants.Y_FLAG;
        }
       

        // If more than one records found then return list view otherwise return status view
        if (careRequests.size() > 1) {
            model.setCareRequests(careRequests);
            modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_LIST,
                    MainetConstants.CommonConstants.COMMAND,
                    getModel());
        } else if (careRequests.size() == 1) {
            CareRequestDTO careRequestDTO = careRequests.get(0);
            CareRequest careRequest = careRequestService.findByApplicationId(careRequestDTO.getApplicationId());
            model.getCareRequest().setApplicationId(careRequest.getApplicationId());
            ComplaintAcknowledgementModel complaintAcknowledgementModel = careRequestService
                    .getComplaintAcknowledgementModel(careRequest, UserSession.getCurrent().getLanguageId());
            model.setComplaintAcknowledgementModel(complaintAcknowledgementModel);
            CareFeedbackDTO feedbackDto = new CareFeedbackDTO();
            CareFeedback feedback = careFeedbackService.getFeedbackByApplicationId(careRequest.getApplicationId().toString());
            if (feedback != null) {
                BeanUtils.copyProperties(feedbackDto, feedback);
            } else {
                feedbackDto.setTokenNumber(careRequest.getApplicationId().toString());
            }
            model.setCareFeedback(feedbackDto);
            modelAndView = new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_STATUS,
                    MainetConstants.CommonConstants.COMMAND,
                    getModel());
            modelAndView.addObject("kdmcEnv", kdmcEnv);
            modelAndView.addObject("dsclEnv", dsclEnv);
            modelAndView.addObject("asclEnv", asclEnv);
            modelAndView.addObject("backBTURL", hitFrom);
        } else {
            getModel().addValidationError(
                    getApplicationSession().getMessage(MainetConstants.ServiceCareCommon.Report.NORECORDFOUND));
            modelAndView = customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
        }
        return modelAndView;
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.REOPEN_GRIEVANCE, method = RequestMethod.POST)
    public ModelAndView reopCompalint(HttpServletRequest request, ModelMap modelMap) throws Exception {
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();
        CareRequestDTO crr = model.getCareRequest();
        WorkflowTaskAction reopenAction = model.getCareDepartmentAction();
        CareRequest careRequest = careRequestService.findByApplicationId(crr.getApplicationId());
        RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);

        model.setAttachments(fileUploadService.setFileUploadMethod(model.getAttachments()));
        applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        fileUploadService.doFileUpload(model.getAttachments(), applicantDetailDto);
        List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(crr.getApplicationId(),
                applicantDetailDto.getOrgId());
        reopenAction.setApplicationId(crr.getApplicationId());
        reopenAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        reopenAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
        reopenAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
        reopenAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
        reopenAction.setAttachementId(attachmentId);
        reopenAction.setCreatedDate(new Date());
        reopenAction.setDateOfAction(new Date());
        reopenAction.setDecision(MainetConstants.WorkFlow.Decision.REOPENED);
        ActionResponse actResponse = careRequestService.restartCareProces(careRequest, reopenAction);
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
        } else {
            /* D123919 start */
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.REGIONAL_LANGUAGE_ID)
                getModel().addValidationError(actResponse.getResponseData().get(MainetConstants.ERROR_MESSAGE_REG));
            else
                getModel().addValidationError(actResponse.getResponseData().get(MainetConstants.ERROR_MESSAGE));
            return customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
            /* D123919 end */
        }
    }

    @RequestMapping(params = MainetConstants.ServiceCareCommon.GRIEVANCE_FEEDBACK, method = RequestMethod.POST)
    public ModelAndView submitComplaintFeedback(HttpServletRequest request, ModelMap modelMap) throws Exception {
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();
        CareFeedbackDTO cfd = model.getCareFeedback();
        String starRatings = MainetConstants.ServiceCareCommon.StarRatings.getStarRatings(cfd.getRatingsStarCount());
        cfd.setRatings(starRatings);
        CareFeedback cf = CareUtility.toCareFeedback(cfd);
        if (cf != null) {
            careFeedbackService.saveCareFeedbak(cf);
        }
        return jsonResult(
                JsonViewObject.successResult(getApplicationSession().getMessage("care.feedback.save")));
        /*
         * return new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_STATUS,
         * MainetConstants.CommonConstants.COMMAND, getModel());
         */
    }

    // D#131629
    @RequestMapping(params = "validComplForReopen", method = RequestMethod.POST)
    public ModelAndView validComplForReopen(HttpServletRequest request, ModelMap modelMap) throws Exception {
        bindModel(request);
        ComplaintRegistrationModel model = this.getModel();
        Long applicationId = model.getCareRequest().getApplicationId();
        List<UserTaskDTO> list = taskService.getTaskList(applicationId.toString());

        ActionResponse response = null;
        if (list == null || list.isEmpty()) {
            String errorMsg = "Unable to reopen CARE Request due to task not found For  RequestNo: " + applicationId;
            response = new ActionResponse(MainetConstants.COMMON_STATUS.FAILURE.toUpperCase());
            response.addResponseData(MainetConstants.ERROR_MESSAGE, errorMsg);
            getModel().addValidationError(errorMsg);
            return customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);
        }

        Date reopenEndDate = null;
        Date currentDate = new Date();
        String reopenDay = null;
        /* D123919 start If reopen count days completed then complaint should not be able to reopen */
        UserTaskDTO task = list.get(0);

        Organisation organisation = new Organisation();
        LOGGER.info("Org Id From Model: " + model.getCareRequest().getOrgId());
        
        CareRequest tbCareRequest = careRequestService.findByApplicationId(applicationId);
        
        if(null!=model.getCareRequest().getOrgId()){
        organisation.setOrgid(model.getCareRequest().getOrgId());
        }
        else
        {
        	organisation.setOrgid(tbCareRequest.getOrgId());
        	LOGGER.info("Inside else of validComplForReopen: " + tbCareRequest.getOrgId());
        }
        
        Optional<LookUp> lookup = CommonMasterUtility
                .getLookUps(PrefixConstants.ComplaintPrefix.COMPLAINT_EXPIRY_DURATION_DAYS_PREFIX,
                        organisation)
                .stream().findFirst();
        if (lookup.isPresent()) {
            reopenDay = lookup.get().getLookUpCode();
            reopenEndDate = Utility.getAddedDateBy2(task.getCreatedDate(), Integer.parseInt(reopenDay));
        }
        /* Checked if reopened days are completed or not */
        if (currentDate.after(reopenEndDate)) {
            /* setting error code & error msg in eng & reg in response if reopend days are completed */
            if (UserSession.getCurrent().getLanguageId() == MainetConstants.REGIONAL_LANGUAGE_ID)
                getModel().addValidationError(
                        ApplicationSession.getInstance().getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,
                                MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, new Locale(MainetConstants.REG_ENG.REGIONAL),
                                new Object[] { reopenDay }));
            else
                getModel().addValidationError(
                        ApplicationSession.getInstance().getMessage(MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR,
                                MainetConstants.ServiceCareCommon.CARE_REOPEN_ERROR, new Locale(MainetConstants.REG_ENG.ENGLISH),
                                new Object[] { reopenDay }));
        } else {
            // normal flow
            return new ModelAndView(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_STATUS,
                    MainetConstants.CommonConstants.COMMAND,
                    getModel());
        }

        return customDefaultMyResult(MainetConstants.ServiceCareCommon.VIEW_GRIEVANCE_SEARCH_FORM);

    }
}
