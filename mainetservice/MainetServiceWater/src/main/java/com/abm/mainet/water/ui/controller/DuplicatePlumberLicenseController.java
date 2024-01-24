package com.abm.mainet.water.ui.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.water.dto.PlumberLicenseRequestDTO;
import com.abm.mainet.water.dto.PlumberLicenseResponseDTO;
import com.abm.mainet.water.service.PlumberLicenseService;
import com.abm.mainet.water.ui.model.DuplicatePlumberLicenseModel;

@Controller
@RequestMapping(value = { "/DuplicatePlumberLicense.html", "/DuplicatePlumberLicenseAuth.html" })
public class DuplicatePlumberLicenseController extends AbstractFormController<DuplicatePlumberLicenseModel> {

	private static Logger logger = Logger.getLogger(DuplicatePlumberLicenseController.class);
	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	PlumberLicenseService plumberLicenseService;

	@Autowired
	private DepartmentService departmentService;

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	IWorkFlowTypeService iWorkFlowTypeService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		final DuplicatePlumberLicenseModel model = getModel();
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ServiceMaster service = serviceMaster.getServiceByShortName("DPL", orgId);
		model.setServiceId(service.getSmServiceId());
		model.setDeptId(service.getTbDepartment().getDpDeptid());
		model.getPlumDto().setServiceId(service.getSmServiceId());
		model.getPlumDto().setFlag(MainetConstants.FlagY);
		model.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
		ModelAndView mv = null;
		mv = new ModelAndView("DuplicatePlumberLicense", MainetConstants.FORM_NAME, getModel());
		mv.addObject(MainetConstants.CommonConstants.COMMAND, getModel());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "getPlumberDetails")
	public ModelAndView getPlumberLicDetails(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final DuplicatePlumberLicenseModel model = getModel();
		if (StringUtils.isBlank(model.getPlumDto().getPlumberLicenceNo())) {
			model.getPlumDto().setFlag(MainetConstants.FlagY);
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("enter.license"));
		}
		PlumberLicenseRequestDTO plumberLicenseRequestDTO = plumberLicenseService
				.getPlumberDetailsByLicenseNumber(orgId, model.getPlumDto().getPlumberLicenceNo());
		if (plumberLicenseRequestDTO != null) {
			model.setPlumDto(plumberLicenseRequestDTO);
			model.setApplicantDetailDto(plumberLicenseRequestDTO.getApplicant());
			model.getPlumDto().setFlag(MainetConstants.FlagN);
			long dateCompare = 0;
			if (plumberLicenseRequestDTO.getPlumLicToDate() != null) {
				dateCompare = Utility.getCompareDate(plumberLicenseRequestDTO.getPlumLicToDate());
			}
			if (plumberLicenseRequestDTO.getPlumberLicenceNo() == null
					|| plumberLicenseRequestDTO.getPlumberId() == 0l) {
				model.getPlumDto().setFlag(MainetConstants.FlagY);
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
			} else if (dateCompare < 0l) {
				model.getPlumDto().setFlag(MainetConstants.FlagY);
				getModel().addValidationError(
						ApplicationSession.getInstance().getMessage("water.duplicatePlumber.RenewalMsg"));
			}
		} else {
			model.getPlumDto().setFlag(MainetConstants.FlagY);
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("wwater.Norecord"));
		}

		ModelAndView mv = new ModelAndView("DuplicatePlumberLicenseValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@Override
	@RequestMapping(params = "saveform", method = RequestMethod.POST)
	public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
		this.bindModel(httpServletRequest);
		DuplicatePlumberLicenseModel model = this.getModel();
		fileUpload.validateUpload(model.getBindingResult());
		if (model.hasValidationErrors()) {
			return defaultMyResult();
		}
		JsonViewObject respObj = null;

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Integer langId = UserSession.getCurrent().getLanguageId();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		final Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.WATER_DEPARTMENT_CODE,
				MainetConstants.STATUS.ACTIVE);
		model.getPlumDto().setDeptId(deptId);
		model.getPlumDto().setOrgId(orgId);
		model.getPlumDto().setLangId(langId.longValue());
		model.getPlumDto().setUserId(userId);
		model.getPlumDto().setServiceId(model.getServiceId());

		try {
			if (model.saveForm()) {
				PlumberLicenseResponseDTO responseDTO = model.getResponseDTO();
				if (responseDTO != null && (responseDTO.getStatus() != null)
						&& responseDTO.getStatus().equals(PrefixConstants.NewWaterServiceConstants.SUCCESS)) {

					if ((model.getIsFree() != null)
							&& model.getIsFree().equals(PrefixConstants.NewWaterServiceConstants.NO)) {
						model.save();
						return jsonResult(JsonViewObject.successResult(getApplicationSession()
								.getMessage("Your Application No. " + responseDTO.getApplicationId()
										+ " for Duplicate Plumber License has been saved successfully.")));

					}

				} else {
					respObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));
				}
			}
		} catch (final Exception e1) {
			throw new FrameworkException(
					"Sorry,Your application for Plumber has not been saved due to some technical problem.", e1);
		}
		return jsonResult(respObj);
	}

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewDuplicatePlumberApplication(@RequestParam("appNo") final Long applicationId,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest request) {
		try {
			getData(applicationId, actualTaskId, request);
		} catch (final Exception ex) {
			logger.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
		return new ModelAndView("DuplicatePlumberLicenseView", MainetConstants.CommonConstants.COMMAND, getModel());
	}
	
	@Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {	
    	
		try {
			getData(Long.valueOf(applicationId), taskId, httpServletRequest);
		} catch (final Exception ex) {
			logger.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
        return new ModelAndView("DuplicatePlumberLicenseViewDet", MainetConstants.FORM_NAME, getModel());
	}

	public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		
		fileUpload.sessionCleanUpForFileUpload();
		DuplicatePlumberLicenseModel model = this.getModel();
		getModel().bind(httpServletRequest);
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		model.setApplicationId(applicationId);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("DPL", orgId);
		model.setServiceId(service.getSmServiceId());
		model.setLookUpList(CommonMasterUtility.getListLookup("ECN", UserSession.getCurrent().getOrganisation()));
			model.populateApplicationData(applicationId);
			model.getPlumDto().setFlag(MainetConstants.FlagN);
		
	}
	
	@RequestMapping(params = "saveDecision", method = RequestMethod.POST)
	public ModelAndView approveDuplicatePlumberApplicationDecision(final HttpServletRequest httpServletRequest) {

		JsonViewObject respObj = null;
		this.bindModel(httpServletRequest);
		DuplicatePlumberLicenseModel model = this.getModel();
		String decision = model.getWorkflowActionDto().getDecision();
		Long taskId = model.getWorkflowActionDto().getTaskId();
		Long applicationId = model.getWorkflowActionDto().getApplicationId();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		model.getPlumDto().setOrgId(orgId);
		model.getPlumDto().setApplicationId(applicationId);
		model.getPlumDto().setLangId(Long.valueOf(langId));
		if (model.getWorkflowActionDto().getDecision() == null
				|| model.getWorkflowActionDto().getDecision().isEmpty()) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.decision"));
			return defaultMyResult();
		}
		if (model.getWorkflowActionDto().getComments() == null
				|| model.getWorkflowActionDto().getComments().isEmpty()) {
			getModel().addValidationError(ApplicationSession.getInstance().getMessage("water.comment"));
			return defaultMyResult();
		}
		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode("DPL", orgId);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(applicationId.toString());
		requestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDTO.setDepartmentName(MainetConstants.WATER_DEPT);
		requestDTO.setServiceId(service.getSmServiceId());
		requestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDTO.setDeptId(service.getTbDepartment().getDpDeptid());
		List<DocumentDetailsVO> docs = new ArrayList<>();
		DocumentDetailsVO document = new DocumentDetailsVO();
		document.setDocumentSerialNo(1L);
		docs.add(document);
		model.setAttachments(fileUpload.prepareFileUpload(docs));
		fileUpload.doFileUpload(model.getAttachments(), requestDTO);
		List<Long> attacheMentIds = ApplicationContextProvider.getApplicationContext()
				.getBean(IChecklistVerificationService.class).fetchAllAttachIdByReferenceId(applicationId.toString(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		model.getWorkflowActionDto().setAttachementId(attacheMentIds);

		boolean lastProcess = iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId);
		model.setFinalProcess(lastProcess);
		if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED) && lastProcess) {
			if (model.saveLoiData()) {
				boolean apprvlFlag = model.plumberUpdateAction();
				if (apprvlFlag)
					respObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("water.application.Loi") + "-"
									+ model.getLoiId());
				else
					respObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));

			} else {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));
			}

		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
			boolean apprvlFlag = model.plumberUpdateAction();
			if (apprvlFlag)
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.approved"));
			else
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("water.application.failure"));
		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)) {
			model.plumberUpdateAction();
			/*
			 * String fileName =
			 * MainetConstants.PlumberJasperFiles.REJECTION_JRXML.getColDescription(); Map
			 * oParms = new HashMap(); oParms.put("APM_APPLICATION_ID",
			 * applicationId.toString()); ByteArrayOutputStream outputStream = new
			 * ByteArrayOutputStream(); String menuURL =
			 * MainetConstants.URLBasedOnShortCode.DPL.getUrl(); String type =
			 * PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
			 * plumberLicenseService.generateJasperReportPDF(this.getModel().getPlumDto(),
			 * outputStream, oParms, fileName, menuURL, type);
			 */

			// plumberLicenseService.sendSMSandEMail(applicantDto, applicationNo,
			// payAmount, // serviceShortCode, organisation);
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("water.application.reject"));
		} else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("water.application.sendback"));
		}
		return jsonResult(respObj);

	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView doGetApplicableCheckListAndCharges(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView modelAndView = null;
		final DuplicatePlumberLicenseModel model = getModel();
		try {

			final ServiceMaster service = serviceMaster.getServiceByShortName("DPL", orgId);
			String serviceCode = "DPL";
			model.getPlumDto().setServiceId(service.getSmServiceId());
			model.getPlumDto().setOrgId(orgId);
			model.findApplicableCheckListAndCharges(serviceCode, orgId);

			modelAndView = new ModelAndView("DuplicatePlumberLicenseValidn", MainetConstants.CommonConstants.COMMAND,
					model);
			if (model.getBindingResult() != null) {
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());
			}
		} catch (final Exception ex) {
			logger.error("Exception has been occurred in getChecklist and charges", ex);
			modelAndView = defaultExceptionFormView();
		}
		return modelAndView;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(params = "printRejectionReport", method = RequestMethod.POST)
	public ModelAndView printRejAppReport(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		DuplicatePlumberLicenseModel model = this.getModel();
		String fileName = MainetConstants.PlumberJasperFiles.REJECTION_JRXML.getColDescription();
		Map oParms = new HashMap();
		oParms.put("APM_APPLICATION_ID", model.getPlumDto().getApplicationId().toString());
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		String menuURL = MainetConstants.URLBasedOnShortCode.PLCA.getUrl();
		String type = PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED;
		String path = plumberLicenseService.generateJasperReportPDF(model.getPlumDto(), outputStream, oParms, fileName,
				menuURL, type);
		if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
			getModel().setFilePath(path);
		}
		getModel().setRedirectURL("AdminHome.html");
		return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, "command", model);
	}

}
