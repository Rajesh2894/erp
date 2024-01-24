package com.abm.mainet.legal.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.LocationMasEntity;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.LocationDTO;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.dto.LegalOpinionDetailDTO;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.service.ICourtMasterService;
import com.abm.mainet.legal.service.ILegalOpinionService;
import com.abm.mainet.legal.ui.model.LegalOpinionModel;

@Controller
@RequestMapping("/LegalOpinion.html")
public class LegalOpinionController extends AbstractFormController<LegalOpinionModel> {
	
	private static final Logger LOGGER = Logger.getLogger(LegalOpinionController.class);
	
	@Autowired
	private ICaseEntryService caseEntryService;
	@Autowired
	private ICourtMasterService courtMasterService;

	@Autowired
	private ILocationMasService locationMasService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IAttachDocsService attachDocsService;
	@Autowired
	private ILegalOpinionService iLegalOpinionService;
	@Autowired
	private ICFCApplicationMasterService cfcService;
	@Autowired
	private IWorkflowExecutionService workflowExecutionService;
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	@Autowired
	private IWorkflowActionService workflowActionService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);

		Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagP,
				PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();

		this.getModel().setCaseEntryDTOList(caseEntryService.getCaseEntryByStatus(closedCaseStatus,
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setLegalOpinionDetailDTOList(
				iLegalOpinionService.findAllByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));

		ModelAndView mv = defaultResult();
		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());

		return mv;
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.ADD)
	public ModelAndView addLegalOpinionEntry(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode("A");
		this.getModel().setSaveMode(MainetConstants.Legal.SaveMode.CREATE);
		Long closedCaseStatus = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.FlagP,
				PrefixConstants.LegalPrefix.CASE_STATUS, UserSession.getCurrent().getOrganisation()).getLookUpId();
		this.getModel().setCaseEntryDTOList(caseEntryService.getCaseEntryByStatus(closedCaseStatus,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = new ModelAndView("LegalOpinionForm", MainetConstants.FORM_NAME, this.getModel());

		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());

		return mv;

	}

	@ResponseBody
	@RequestMapping(params = { "searchOpinionEntry" }, method = RequestMethod.POST)
	public ModelAndView searchLegalOpinionEntry(final HttpServletRequest request,
			@RequestParam(required = false) Long Deptid) {

		getModel().bind(request);

		if (Deptid != null) {
			this.getModel().setLegalOpinionDetailDTOList(iLegalOpinionService
					.findAllByOrgIdAndDeptId(UserSession.getCurrent().getOrganisation().getOrgid(), Deptid));

		}
		getModel().getLegalOpinionDetailDTO().setOpniondeptId(Deptid);
		ModelAndView mv = new ModelAndView("LegalOpinionSearch", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());

		return mv;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "viewCaseDetails")
	public ModelAndView viewCaseDetails(final HttpServletRequest httpServletRequest,
			@RequestParam(required = false) Long cseID)

	{
		bindModel(httpServletRequest);
		CaseEntryDTO dto = caseEntryService.getCaseEntryById(cseID);
		this.getModel().setCaseEntryDTO(dto);
		this.getModel().setCaseAttachDocsList(
				attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
						MainetConstants.Legal.CASE_ENTRY + MainetConstants.DOUBLE_BACK_SLACE + cseID));
		ModelAndView mv = new ModelAndView("CaseEntryViewForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());
		mv.addObject("locations", getLocationList());
		mv.addObject("courtMasterDTOList", getCourtMasterList());
		mv.addObject("caseEntryDTO", dto);
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewLegalOpinionApplication(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId)
			throws ParseException {
		LOGGER.info("Method Started ------------------------------------->");
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		LegalOpinionModel model = this.getModel();
		LOGGER.info("Call  CFC Service  ------------------------------------->");
		TbCfcApplicationMstEntity tbCfcApplicationMstEntity = cfcService.getCFCApplicationByApplicationId(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LOGGER.info("Call  CFC Service End and Start Legal ------------------------------------->");
		LegalOpinionDetailDTO legalOpinionDetailDTO = iLegalOpinionService.findLegalOpinionApplicationByApmId(
				UserSession.getCurrent().getOrganisation().getOrgid(), applicationId);
		LOGGER.info("Legal Ended ------------------------------------->");
		model.setLegalOpinionDetailDTO(legalOpinionDetailDTO);
		model.getLegalOpinionDetailDTO().setApmApplicationId(applicationId);
		model.getLegalOpinionDetailDTO().setTaskId(taskId);
		model.setCfcAttachment((iChecklistVerificationService.getDocumentUploadedByRefNo(applicationId.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid())));
		LOGGER.info("Checklist ended  ------------------------------------->");
		ModelAndView mv = new ModelAndView("LegalOpinionDecisionForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());
		LOGGER.info("Method Ended ------------------------------------->");
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveDecision")
	public ModelAndView saveLegalOpinionDecisionApplication(final HttpServletRequest httpServletRequest)
			throws ParseException {
		bindModel(httpServletRequest);

		LegalOpinionModel model = this.getModel();
 		LegalOpinionDetailDTO legalOpinionDetailDTO = model.getLegalOpinionDetailDTO();	
 	

		WorkflowTaskAction workflowActionDto = model.getWorkflowActionDto();
		// #119895
		if (StringUtils.isEmpty(workflowActionDto.getDecision()) || StringUtils.isEmpty(workflowActionDto.getComments())) {
		if (StringUtils.isEmpty(workflowActionDto.getDecision())) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validate.decision"));
		}
		if (StringUtils.isEmpty(workflowActionDto.getComments())) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("lgl.validate.remark"));
		}
		ModelAndView mv = new ModelAndView("LegalOpinionDecisionForm", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
				getModel().getBindingResult());
		return mv;
		}

		try {
			Employee emp = UserSession.getCurrent().getEmployee();

			model.getLegalOpinionDetailDTO().setUpdatedBy(emp.getEmpId());
			model.getLegalOpinionDetailDTO().setUpdateddate(new Date());

			model.getLegalOpinionDetailDTO().setUpdatedIpMac(emp.getEmppiservername());
			model.setDocumentDetailsList(ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
			List<DocumentDetailsVO> docList = model.getDocumentDetailsList();
					if (docList != null) {
						docList = fileUpload.prepareFileUpload(docList);
					}
			legalOpinionDetailDTO.setDocumentDetailsList(docList);
			iLegalOpinionService.saveLegalOpinionApplication(model.getLegalOpinionDetailDTO());

			/* Update workflow */
			WorkflowProcessParameter workflowdto = new WorkflowProcessParameter();
			workflowdto.setProcessName("maker-checker");
			WorkflowTaskAction workflowAction = new WorkflowTaskAction();
			workflowAction.setApplicationId(legalOpinionDetailDTO.getApmApplicationId());
			workflowAction.setDateOfAction(new Date());
			workflowAction.setOrgId(legalOpinionDetailDTO.getOrgId());
			workflowAction.setEmpId(emp.getEmpId());
			workflowAction.setEmpType(emp.getEmplType());
			workflowAction.setEmpName(emp.getEmpname());
			workflowAction.setCreatedBy(emp.getEmpId());
			workflowAction.setCreatedDate(new Date());
			workflowAction.setTaskId(legalOpinionDetailDTO.getTaskId());
			workflowAction.setModifiedBy(emp.getEmpId());
			workflowAction.setDecision(workflowActionDto.getDecision());
			workflowAction.setIsFinalApproval(false);
			workflowdto.setWorkflowTaskAction(workflowAction);
			if(workflowActionDto.getComments().length()<=1020)
			{
			workflowAction.setComments(workflowActionDto.getComments());
			}
			else
			{
				
				model.addValidationError("Remark field size limit exceeded.");
				ModelAndView modelAndView = new ModelAndView("LegalOpinionDecisionForm", MainetConstants.FORM_NAME, model);
				modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						getModel().getBindingResult());

				return modelAndView;
			}
		
			workflowExecutionService.updateWorkflow(workflowdto);

		} catch (Exception e) {

			model.addValidationError("Error while Updating Workflow:  " + " " + e.getMessage());
			ModelAndView modelAndView = new ModelAndView("LegalOpinionDecisionForm", MainetConstants.FORM_NAME, model);
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					getModel().getBindingResult());

			return modelAndView;
		}

		return jsonResult(JsonViewObject.successResult(ApplicationSession.getInstance().getMessage("lgl.legalOpinion.app.approved")));
	}

	private List<Department> loadDepartmentList() {
		LOGGER.info("Department Method Started ------------------------------------->");
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); // Active
		LOGGER.info("Department Method Ended ------------------------------------->");																					// = "A"
		return departments;
	}

	private List<CourtMasterDTO> getCourtMasterList() {

		List<CourtMasterDTO> courtMasterDTOList = courtMasterService
				.getAllActiveCourtMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		return courtMasterDTOList;
	}

	private List<LocationDTO> getLocationList() {
		List<LocationMasEntity> locationMasEntityList = locationMasService
				.getlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		List<LocationDTO> locationDTOList = new ArrayList<>();
		for (LocationMasEntity locationMasEntity : locationMasEntityList) {
			LocationDTO locationDTO = new LocationDTO();
			locationDTO.setLocId(locationMasEntity.getLocId());
			locationDTO.setLocName(locationMasEntity.getLocNameEng());
			locationDTO.setLocNameEng(locationMasEntity.getLocNameEng());
			locationDTO.setLocNameReg(locationMasEntity.getLocNameReg());
			locationDTO.setLandmark(locationMasEntity.getLandmark());
			locationDTO.setPincode(locationMasEntity.getPincode());
			locationDTOList.add(locationDTO);
		}
		return locationDTOList;
	}
	
	//#141902
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.VIEW)
	public ModelAndView viewOpinionDetails(@RequestParam(MainetConstants.Common_Constant.ID) Long id,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		LegalOpinionModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		LegalOpinionDetailDTO legalOpinionDetailDTO = iLegalOpinionService.findByIds(id, orgId);
		Long deptId = departmentService.getDepartmentIdByDeptCode("LEGL");
		if (deptId != null)
		 legalOpinionDetailDTO.setDeptId(deptId);
		model.setLegalOpinionDetailDTO(legalOpinionDetailDTO);
		if (legalOpinionDetailDTO.getApmApplicationId() != null)
		model.setCfcAttachment((iChecklistVerificationService.getDocumentUploadedByRefNo(legalOpinionDetailDTO.getApmApplicationId().toString(),
				UserSession.getCurrent().getOrganisation().getOrgid())));
		model.setCfcAttachmentList((iChecklistVerificationService.getDocumentUploadedForAppId(legalOpinionDetailDTO.getId(),
				UserSession.getCurrent().getOrganisation().getOrgid())));
		WorkflowAction dto = workflowActionService.getCommentAndDecisionByAppId(legalOpinionDetailDTO.getApmApplicationId(), orgId);
		if (dto != null) {
			legalOpinionDetailDTO.setDecision(dto.getDecision());
			legalOpinionDetailDTO.setComments(dto.getComments());
			if(StringUtils.isNotEmpty(dto.getDecision()) && StringUtils.isNotEmpty(dto.getComments()))
			model.setViewCommentAndDecision(MainetConstants.FlagY);
			else
				model.setViewCommentAndDecision(MainetConstants.FlagN);
		}
		
			
		ModelAndView mv = new ModelAndView("LegalOpinionDecisionViewForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject("departments", loadDepartmentList());
		return mv;
 
	}

}
