package com.abm.mainet.adh.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.ui.model.AdvertiserCancellationFormModel;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;




/**
 * @author Sharvan kumar Mandal
 * @since 18-06-2021
 */


@Controller
@RequestMapping("/AdvertisercancellationForm.html")
public class AdvertiserCancellationFormController extends AbstractFormController<AdvertiserCancellationFormModel> {

	private static final Logger logger = LoggerFactory.getLogger(AdvertiserCancellationFormController.class);
    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ServiceMasterService serviceMaster;
    
    @Autowired
    private IWorkFlowTypeService workFlowTypeService;
    
    @Autowired
    private IAdvertiserMasterService advertiserMasterservice;
    
    

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
	public ModelAndView executeAgencyRenewalRegistration(
			@RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
			@RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
			@RequestParam(MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
			HttpServletRequest request) {

		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(request);
		// Added new controller class for User Story 112154 
		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceDto = serviceMaster
				.getServiceByShortName(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE, orgId);
		if (serviceDto != null) {
			this.getModel().setServiceMaster(serviceDto);
		}
		this.getModel().setServiceId(serviceDto.getSmServiceId());
		this.getModel().setServiceName(serviceDto.getSmServiceName());
		this.getModel().setApmApplicationId(applicationId);
		try {
			this.getModel().populateApplicationData(applicationId);
		} catch (Exception exception) {
			logger.error("Error While Rendoring the form", exception);
			return defaultExceptionFormView();
		}
		this.getModel().setPayableFlag(MainetConstants.FlagN);
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_Cancellation,
				MainetConstants.CommonConstants.COMMAND, getModel());

	}

    
    @Override
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = { RequestMethod.POST })
    public ModelAndView saveform(HttpServletRequest request) {
	JsonViewObject responseObj = null;
	this.getModel().bind(request);

	AdvertiserCancellationFormModel model = this.getModel();
	if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
	    getModel().addValidationError(getApplicationSession().getMessage("adh.approval.decision"));
	    return defaultMyResult();
	}

	if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
	    getModel().addValidationError(getApplicationSession().getMessage("adh.approval.remarks"));
	    return defaultMyResult();
	}

	RequestDTO requestDto = new RequestDTO();
	requestDto.setReferenceId(String.valueOf(model.getApmApplicationId()));
	requestDto.setDepartmentName(MainetConstants.AdvertisingAndHoarding.AGENCY_CAN_SHORT_CODE);
	requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	requestDto.setServiceId(model.getServiceId());
	requestDto.setDeptId(model.getServiceMaster().getTbDepartment().getDpDeptid());
	requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
	requestDto.setApplicationId(model.getApmApplicationId());
	List<DocumentDetailsVO> docs = new ArrayList<>();
	DocumentDetailsVO document = new DocumentDetailsVO();
	document.setDocumentSerialNo(1L);
	docs.add(document);
	model.setApprovalDocumentAttachment(fileUpload.prepareFileUpload(docs));
	fileUpload.doFileUpload(model.getApprovalDocumentAttachment(), requestDto);
	List<Long> documentIds = ApplicationContextProvider.getApplicationContext()
		.getBean(IChecklistVerificationService.class)
		.fetchAllAttachIdByReferenceId(String.valueOf(model.getApmApplicationId()),
			UserSession.getCurrent().getOrganisation().getOrgid());

	model.getWorkflowActionDto().setAttachementId(documentIds);
	boolean lastApproval = workFlowTypeService
		.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());

	if (lastApproval
		&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
			MainetConstants.WorkFlow.Decision.APPROVED)
		&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
			MainetConstants.FlagY)) {

	    boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
	    if (approvalFlag) {
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("Loi charges generated succeesfully. Your LOI No : "
				+ model.getWorkflowActionDto().getLoiDetails().get(0).getLoiNumber()));

	    } else {
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("Loi charges not found. Please define rule"));
	    }

	} else if (lastApproval
		&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
			MainetConstants.WorkFlow.Decision.APPROVED)
		&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
			MainetConstants.FlagN)) {
	    if (model.closeWorkFlowTask()) {
		this.getModel().getAgencyRequestDto().getMasterDto().setAgencyStatus(MainetConstants.FlagC);
		advertiserMasterservice
			.updateAdvertiserMasterData(this.getModel().getAgencyRequestDto().getMasterDto());
		model.sendSmsEmail(model, "AdvertisercancellationForm.html",PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL);

		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
	    } else {
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
	    }

	} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
		MainetConstants.WorkFlow.Decision.APPROVED)) {

	    boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
	    if (approvalFlag) {
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("adh.application.approval.success.message"));
	    } else {
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("adh.application.approval.fail.message"));
	    }

	} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
		MainetConstants.WorkFlow.Decision.REJECTED)) {
	    boolean approvalFlag = this.getModel().AgencyRegistrationApprovalAction();
	    if (approvalFlag) {
		model.sendSmsEmail(model, "AdvertisercancellationForm.html",
			MainetConstants.AdvertisingAndHoarding.REJECTED_MESSAGE);
		responseObj = JsonViewObject.successResult(
			ApplicationSession.getInstance().getMessage("Application rejected successfully"));
	    } else {
		responseObj = JsonViewObject
			.successResult(ApplicationSession.getInstance().getMessage("Application rejection failed"));
	    }

	}

	return jsonResult(responseObj);

    }

    }
	