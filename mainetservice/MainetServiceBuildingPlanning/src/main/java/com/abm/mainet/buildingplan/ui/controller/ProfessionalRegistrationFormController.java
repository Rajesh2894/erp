package com.abm.mainet.buildingplan.ui.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.buildingplan.dto.ProfessionalRegistrationDTO;
import com.abm.mainet.buildingplan.service.ProfessionalRegistrationService;
import com.abm.mainet.buildingplan.ui.model.ProfessionalRegistrationFormModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.StringUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.common.workflow.ui.validator.CheckerActionValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = { MainetConstants.BuildingPlanning.PROF_REG_FORM_HTML,
		MainetConstants.BuildingPlanning.PROF_REG_APPROVAL_HTML })
public class ProfessionalRegistrationFormController extends AbstractFormController<ProfessionalRegistrationFormModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionalRegistrationFormController.class);

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private BRMSCommonService brmsCommonService;

	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private ProfessionalRegistrationService professionalRegService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	
	@Resource
	private IEmployeeService employeeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		final ProfessionalRegistrationFormModel model = getModel();		
		EmployeeBean employee = employeeService.findById(UserSession.getCurrent().getEmployee().getEmpId());		
		if (null == employee.getReportingManager()) { 
			ProfessionalRegistrationDTO dto = model.getProfessionalRegDTO();
			dto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			model.setOfficeCircle(CommonMasterUtility.getLevelData(MainetConstants.BuildingPlanning.PREFIX_AWZ, 1,
					UserSession.getCurrent().getOrganisation()));
			model.setDistrict(CommonMasterUtility.getLevelData(MainetConstants.BuildingPlanning.PREFIX_DDZ, 1,
					UserSession.getCurrent().getOrganisation()));
			model.setModeType(MainetConstants.FlagC);
			return new ModelAndView(MainetConstants.BuildingPlanning.PROF_REG_FORM, MainetConstants.FORM_NAME, model);
		}else{		
			return new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, this.getModel()); 
		}
		
	}

	@RequestMapping(params = MainetConstants.BuildingPlanning.GET_CHECKLIST, method = RequestMethod.POST)
	public ModelAndView getCheckList(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		final ProfessionalRegistrationFormModel model = getModel();
		try {
			getcheckListDocument(model);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView(MainetConstants.BuildingPlanning.PROF_REG_FORM_VALIDN, MainetConstants.FORM_NAME,
				model);
	}

	private void getcheckListDocument(ProfessionalRegistrationFormModel model) {
		ProfessionalRegistrationDTO dto = model.getProfessionalRegDTO();
		LookUp checkListApplLookUp = null;
		Organisation org = organisationService.getOrganisationById(dto.getOrgId());
		ServiceMaster serviceMas = serviceMaster
				.getServiceByShortName(MainetConstants.BuildingPlanning.SERVICE_CODE_TPPR, dto.getOrgId());
		if (serviceMas != null) {
			dto.setServiceId(serviceMas.getSmServiceId());
			dto.setDeptId(serviceMas.getTbDepartment().getDpDeptid());
			checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(),
					org);
			if (StringUtils.equalsIgnoreCase(checkListApplLookUp.getLookUpCode(), MainetConstants.FlagA)) {
				final WSRequestDTO requestDTO = new WSRequestDTO();
				requestDTO.setModelName(MainetConstants.BuildingPlanning.TCP_CHECKLIST);
				WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);
				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
					final List<Object> checklistModel = this.castResponse(response, CheckListModel.class, 0);
					final CheckListModel checkListModel2 = (CheckListModel) checklistModel.get(0);
					checkListModel2.setOrgId(dto.getOrgId());
					checkListModel2.setServiceCode(MainetConstants.BuildingPlanning.SERVICE_CODE_TPPR);
					checkListModel2.setUsageSubtype1(
							CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getUserType(), org).getLookUpDesc());
					WSRequestDTO checklistReqDto = new WSRequestDTO();
					checklistReqDto.setModelName(MainetConstants.BuildingPlanning.TCP_CHECKLIST);
					checklistReqDto.setDataModel(checkListModel2);
					try {
						WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);
						if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
								|| MainetConstants.CommonConstants.NA
										.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
							if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
								List<DocumentDetailsVO> checkListList = Collections.emptyList();
								checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();

								long cnt = 1;
								for (final DocumentDetailsVO doc : checkListList) {
									doc.setDocumentSerialNo(cnt);
									cnt++;
								}
								if ((checkListList != null) && !checkListList.isEmpty()) {
									model.setCheckList(checkListList);
								}
							}
						}
					} catch (Exception e) {
						LOGGER.info("Checklist not found..!");
						model.addValidationError(getApplicationSession().getMessage("Checklist not found..!"));
					}

				}
			}
		}

	}

	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@RequestMapping(params = MainetConstants.BuildingPlanning.SHOWDETAILS, method = { RequestMethod.POST })
	public ModelAndView showFormDetails(@RequestParam(MainetConstants.BuildingPlanning.APP_NO) final Long applicationId,
			@RequestParam(MainetConstants.BuildingPlanning.TASK_ID) String serviceId,
			@RequestParam(MainetConstants.BuildingPlanning.ACTUAL_TASKID) Long taskId, final HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		try {
			getModel().setModeType(MainetConstants.FlagV);
			getData(applicationId, serviceId, taskId, request);
			} catch (Exception exception) {
			logger.error("Error While Rendoring the form", exception);
			return defaultExceptionFormView();
		}
		return new ModelAndView(MainetConstants.BuildingPlanning.PROF_REG_FORM_VALIDN, MainetConstants.FORM_NAME,
				getModel());

	}
	
	@Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
    public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
            @RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
            final HttpServletRequest httpServletRequest) throws Exception {
        try {
            getData(Long.valueOf(applicationId), String.valueOf(serviceId), taskId, httpServletRequest);
            getModel().setModeType(MainetConstants.FlagA);
        } catch (Exception exception) {
            logger.error("Error While Rendoring the form", exception);
            return defaultExceptionFormView();
        }
        return new ModelAndView(MainetConstants.BuildingPlanning.PROF_REG_FORM_VALIDN, MainetConstants.FORM_NAME, getModel());
    }

	private void getData(Long applicationId, String serviceId, long taskId, HttpServletRequest httpServletRequest) {
		// TODO Auto-generated method stub
		final ProfessionalRegistrationFormModel model = getModel();
		Organisation org = UserSession.getCurrent().getOrganisation();
		UserTaskDTO userTaskdto = iWorkflowTaskService.findByTaskId(taskId);
		ProfessionalRegistrationDTO dto = professionalRegService.getDetailByAppIdAndOrgId(applicationId,
				org.getOrgid());
		dto.setOrgId(org.getOrgid());
		model.setProfessionalRegDTO(dto);
		model.setOfficeCircle(CommonMasterUtility.getLevelData(MainetConstants.BuildingPlanning.PREFIX_AWZ, 1,
				UserSession.getCurrent().getOrganisation()));
		model.setDistrict(CommonMasterUtility.getLevelData(MainetConstants.BuildingPlanning.PREFIX_DDZ, 1,
				UserSession.getCurrent().getOrganisation()));
		getModel().setDocumentList(
				iChecklistVerificationService.getDocumentUploadedForAppId(applicationId, org.getOrgid()));

		if (StringUtils.equalsIgnoreCase(userTaskdto.getTaskName(), MainetConstants.WorkFlow.Decision.INITIATOR)) {
			model.setModeType(MainetConstants.FlagE);
			try {
				getcheckListDocument(model);
			} catch (Exception e) {
				e.printStackTrace();
			}
			FileUploadUtility.getCurrent().setFileMap(
					getUploadedFileList(getModel().getDocumentList(), FileNetApplicationClient.getInstance()));

		}
		ServiceMaster serviceMas = serviceMaster
				.getServiceByShortName(MainetConstants.BuildingPlanning.SERVICE_CODE_TPPR, org.getOrgid());
		model.setServiceMst(serviceMas);
		model.getWorkflowActionDto().setTaskId(taskId);
		getModel().setCurrentLevel(userTaskdto.getCurentCheckerLevel());

	}

	@RequestMapping(params = MainetConstants.BuildingPlanning.SAVE_AUTHORIZATION, method = RequestMethod.POST)
	public ModelAndView saveAuthorization(HttpServletRequest request) {
		JsonViewObject responseObj = null;
		this.getModel().bind(request);
		ProfessionalRegistrationFormModel model = this.getModel();
		if (!StringUtils.equalsIgnoreCase(model.getModeType(), MainetConstants.FlagE)) {
			if (StringUtils.isBlank(model.getWorkflowActionDto().getDecision())) {
				getModel().addValidationError(getApplicationSession().getMessage("professional.val.decision"));
				return defaultMyResult();
			}

			if (StringUtils.isBlank(model.getWorkflowActionDto().getComments())) {
				getModel().addValidationError(getApplicationSession().getMessage("professional.val.comment"));

				return defaultMyResult();
			}
		} else {
			model.getWorkflowActionDto().setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
		}

		boolean lastApproval = workFlowTypeService
				.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
		if (lastApproval && StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.APPROVED)) {
			if (model.saveForm()) {
				if (model.closeWorkFlowTask()) {
					 CompletableFuture.runAsync(() -> {
				            professionalRegService.saveOBPASData(model.getProfessionalRegDTO());			           
				        });
					 CompletableFuture.runAsync(() -> {
						 sendSmsAndEmail(model.getProfessionalRegDTO(),UserSession.getCurrent().getOrganisation());		           
				        });
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.approve.msg"));
				} else {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.failed.msg"));
				}
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.APPROVED)) {
			if (model.saveForm()) {
				if (model.ProfessioanlRegApprovalAction()) {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.approve.msg"));
					if (model.getCurrentLevel() == 3) {
						CompletableFuture.runAsync(() -> {
				            sendSmsAndEmail(model.getProfessionalRegDTO(),UserSession.getCurrent().getOrganisation());
				        });
					}
				} else {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.failed.msg"));
				}
			} else {
				return defaultMyResult();
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			if (model.saveForm()) {
				if (model.ProfessioanlRegApprovalAction()) {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.sendback.msg"));
				} else {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.failed.msg"));
				}
			} else {
				return defaultMyResult();
			}

		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.SEND_BACK_TO_APPLICANT)) {
			if (model.saveForm()) {
				if (model.ProfessioanlRegApprovalAction()) {
					responseObj = JsonViewObject.successResult(
							ApplicationSession.getInstance().getMessage("professional.sendback.applicant.msg"));
				} else {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.failed.msg"));
				}
			} else {
				return defaultMyResult();
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.REJECTED)) {
			if (model.saveForm()) {
				if (model.ProfessioanlRegApprovalAction()) {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.rejected.msg"));
					CompletableFuture.runAsync(() -> {
			            sendSmsAndEmail(model.getProfessionalRegDTO(),UserSession.getCurrent().getOrganisation());
			        });
				} else {
					responseObj = JsonViewObject
							.successResult(ApplicationSession.getInstance().getMessage("professional.failed.msg"));
				}
			} else {
				return defaultMyResult();
			}

		}
		return jsonResult(responseObj);
	}

	private void sendSmsAndEmail(ProfessionalRegistrationDTO professionalRegDTO,Organisation organisation) {
		final SMSAndEmailDTO smsEmailDto = new SMSAndEmailDTO();
		if (null != professionalRegDTO.getMobileNo())
			smsEmailDto.setMobnumber(professionalRegDTO.getMobileNo());
		if (null != professionalRegDTO.getEmailId())
			smsEmailDto.setEmail(professionalRegDTO.getEmailId());
		smsEmailDto.setRegNo(String.valueOf(professionalRegDTO.getApplicationId()));
		smsEmailDto.setDueDt(Utility.dateToString(new Date()));
		smsEmailDto.setServName(MainetConstants.BuildingPlanning.SERVICE_CODE_TPPR);
		try {
			iSMSAndEmailService.sendEmailSMS(MainetConstants.APP_NAME.TCP, "ProfessionalRegistrationForm.html",
					PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsEmailDto,
					organisation, (int)professionalRegDTO.getLangId());
		} catch (Exception e) {
			logger.error("Exception occur while sending SMS and Email for Applicayion Number:" + " "
					+ professionalRegDTO.getApplicationId(), e);
		}

	}

	public Map<Long, Set<File>> getUploadedFileList(List<CFCAttachment> newMap,

			FileNetApplicationClient fileNetApplicationClient) {

		Set<File> fileList = null;

		Map<Long, Set<File>> fileMap = new HashMap<>();

		Long count = 0l;
		for (CFCAttachment doc : newMap) {
			fileList = new HashSet<>();
			final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER

					+ MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.CommonMasterUi.SHOW_DOCS;

			String existingPath = doc.getAttPath() + MainetConstants.FILE_PATH_SEPARATOR + doc.getAttFname();

			final String fileName = StringUtility.staticStringAfterChar(MainetConstants.FILE_PATH_SEPARATOR,

					existingPath);

			String directoryPath = StringUtility.staticStringBeforeChar(MainetConstants.FILE_PATH_SEPARATOR,

					existingPath);

			directoryPath = directoryPath.replace(MainetConstants.FILE_PATH_SEPARATOR, MainetConstants.operator.COMMA);

			FileOutputStream fos = null;

			File file = null;

			try {

				final byte[] image = fileNetApplicationClient.getFileByte(fileName, directoryPath);

				Utility.createDirectory(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR);

				file = new File(Filepaths.getfilepath() + outputPath + MainetConstants.FILE_PATH_SEPARATOR + fileName);

				fos = new FileOutputStream(file);

				fos.write(image);

				fos.close();

			} catch (final Exception e) {

				throw new FrameworkException("Exception in getting getUploadedFileList", e);

			} finally {

				try {

					if (fos != null) {

						fos.close();

					}

				} catch (final IOException e) {

					throw new FrameworkException("Exception in getting getUploadedFileList", e);

				}

			}

			fileList.add(file);
			fileMap.put(doc.getClmId(), fileList);
			count++;
		}

		return fileMap;

	}

}
