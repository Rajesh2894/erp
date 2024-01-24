package com.abm.mainet.rti.ui.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.domain.TbObjectionEntity;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.HolidayMasterDto;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.HolidayMasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiDispatchModel;
import com.abm.mainet.rti.ui.model.RtiPioResponseModel;
import com.abm.mainet.rti.utility.RtiUtility;

@Controller
@RequestMapping("/RtiDispatch.html")
public class RtiDispatchController extends AbstractFormController<RtiDispatchModel> {

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Resource
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private RtiUtility rtiUtility;

	@Autowired
	private HolidayMasterService holidayMasterService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private ITaskAssignmentService taskAssignmentService;

	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private TbDepartmentService departmentService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewDispatchRtiApplication(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId)
			throws ParseException {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final RtiDispatchModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		model.setReqDTO(rtiApplicationDetailService.fetchRtiApplicationInformationById(applicationId, orgId));
		orgId = model.getReqDTO().getOrgId();

		// Change as per RTI Demo point of anuj
		List<CFCAttachment> att = iChecklistVerificationService
				.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo() + MainetConstants.DEPT_SHORT_NAME.RTI, orgId);
		if (!CollectionUtils.isEmpty(att)) {
			if (att.size() > 1) {
				model.setFetchPioUploadDoc(Arrays.asList(att.get(att.size() - 1)));
			} else {
				model.setFetchPioUploadDoc(att);
			}
		}
		model.setFetchDocumentList((iChecklistVerificationService.getDocumentUploaded(applicationId, orgId)));
		model.setFetchApplnUpload(
				iChecklistVerificationService.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo(), orgId));
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(applicationId));
		model.setCfcEntity(cfcService.getCFCApplicationByApplicationId(applicationId, orgId));

		model.getReqDTO().setApplicant(rtiUtility.getPrefixDesc(PrefixConstants.APPLICATION_TYPE_PREFIX,
				model.getCfcEntity().getCcdApmType()));
		model.getReqDTO()
				.setTitle(rtiUtility.getPrefixDesc(PrefixConstants.LookUp.TITLE, model.getCfcEntity().getApmTitle()));
		if(model.getCfcEntity().getApmSex()!=null) {
		model.getReqDTO().setGen(rtiUtility.getPrefixDesc(PrefixConstants.MobilePreFix.GENDER,
				Long.parseLong(model.getCfcEntity().getApmSex())));}
		setDMSPath();

		/* getting location name from location ID */
		model.getReqDTO()
				.setLocationName(rtiApplicationDetailService.getlocationNameById(
						Long.valueOf(model.getReqDTO().getRtiLocationId()),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		/* end */
		if (model.getCfcEntity().getApmBplNo() != null && !model.getCfcEntity().getApmBplNo().isEmpty()) {
			model.getReqDTO().setIsBPL(MainetConstants.YES);
		} else {
			model.getReqDTO().setIsBPL(MainetConstants.NO);
		}
		model.getReqDTO().setInwardTypeName(
				rtiUtility.getPrefixDesc(PrefixConstants.INWARD_TYPE, Long.valueOf(model.getReqDTO().getInwardType())));
		/* fetching Department Name based on Department Id */
		model.getReqDTO().setDepartmentName(
				rtiApplicationDetailService.getdepartmentNameById(Long.valueOf(model.getReqDTO().getRtiDeptId())));

		/* fetching media list */

		List<RtiMediaListDTO> tbRtiMediaDetails = rtiApplicationDetailService
				.getMediaList(Long.valueOf(model.getReqDTO().getRtiId()), orgId);

		List<String> mediaTypeList = new ArrayList<>();
		for (int i = 0; i < tbRtiMediaDetails.size(); i++) {
			model.setMediaType((rtiUtility.getPrefixDesc(PrefixConstants.MEDIA_TYPE,
					Long.valueOf(tbRtiMediaDetails.get(i).getMediaType()))));
			mediaTypeList.add(model.getMediaType());
		}
		model.setMediaTypeList(mediaTypeList);

		model.setCommonHelpDocs("RtiApplicationDetailForm.html");
		/* end */

		/* Date Formatting */
		model.getReqDTO().setApplicationDate(model.getReqDTO().getApmApplicationDate());
		model.getReqDTO().setReferenceDate(Utility.dateToString(model.getReqDTO().getInwReferenceDate()));
		/* end */
		// for getting dispatch No
		// Fetching Organisation from user session due to addition of Forward to
		// organisation functionality
		String dispatchNo = rtiApplicationDetailService.generateDispatchNo(new Date(),
				UserSession.getCurrent().getOrganisation().getOrgid(), Long.valueOf(model.getReqDTO().getRtiDeptId()));
		model.getReqDTO().setDispatchNo(dispatchNo);

		model.getReqDTO().setTaskId(taskId);
		ModelAndView mv = null;
		mv = new ModelAndView(MainetConstants.RTISERVICE.DISPATCH, MainetConstants.FORM_NAME, model);
		return mv;

	}

	@RequestMapping(params = MainetConstants.RTISERVICE.SAVE_DISPATCH, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveDispatch(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RtiDispatchModel model = this.getModel();

		/*
		 * change for updating status in TBobjectionMas table after dispatch by
		 * rajesh.das Defect 27792
		 */

		if (model.saveForm()) {
			rtiApplicationDetailService.getObjectionDetailByApplicationId(orgId,
					model.getReqDTO().getApmApplicationId(), MainetConstants.RTISERVICE.RTIFIRSTAPPEAL);
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else {
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}

	}

	@ResponseBody
	@RequestMapping(params = "getHolidayDates", method = RequestMethod.POST)
	public String getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("holidayDate") Date holidayDate) {
		this.getModel().bind(httpServletRequest);
		String validMsg = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<HolidayMasterDto> holidayDto = holidayMasterService.getHolidayDates(holidayDate, orgId);
		if (holidayDto != null && !holidayDto.isEmpty()) {

			validMsg = ApplicationSession.getInstance().getMessage("rti.validate.holiday.date");
		}
		return validMsg;

	}

	@RequestMapping(method = RequestMethod.POST, params = "applicantInfoReportPrint")
	public ModelAndView applicantInfoReport(final HttpServletRequest request) {
		this.getModel().bind(request);
		RtiDispatchModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee employee1 = UserSession.getCurrent().getEmployee();
		RtiApplicationFormDetailsReqDTO rtiDto = model.getReqDTO();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("RAF", orgId);
		model.setServiceMaster(sm);
		model.getReqDTO().setDateDesc(Utility.dateToString(rtiDto.getApmApplicationDate()));
		TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService
				.getCFCApplicationByApplicationId(rtiDto.getApmApplicationId(), orgId);
		model.setCfcEntity(cfcApplicationMstEntity);
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(rtiDto.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(cfcApplicationMstEntity.getApmFname(),
				cfcApplicationMstEntity.getApmMname(), cfcApplicationMstEntity.getApmLname()));
		rtiDto.setApplicantName(fullName);
		LookUp lookup = null;
		List<RtiMediaListDTO> tbRtiMediaDetails = null;
		//Defect #125827
		if (rtiDto.getLoiApplicable() != null) {
			lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(rtiDto.getLoiApplicable()));
		}
		if (lookup != null && (lookup.getLookUpCode() != null
				&& lookup.getLookUpCode().equals(MainetConstants.CommonMasterUi.LOI_APPLICABLE))) {

			tbRtiMediaDetails = rtiApplicationDetailService.getMediaList(Long.valueOf(model.getReqDTO().getRtiId()),
					orgId);
			model.setRtiMediaListDTO(tbRtiMediaDetails);
		}
		// code for converting date to String and set in RtiDecDet for showing in Rti
		// Information Print page
		SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
		String strDate = formatter.format(model.getReqDTO().getDispatchDt());
		model.getReqDTO().setRtiDeciDet(strDate);
		//Defect #125827
		if (!CollectionUtils.isEmpty(tbRtiMediaDetails)) {
			for (RtiMediaListDTO dto : tbRtiMediaDetails) {
				dto.setMediaTypeDesc(
						rtiUtility.getPrefixDesc(PrefixConstants.MEDIA_TYPE, Long.valueOf(dto.getMediaType())));
			}
		}
		List<RtiApplicationFormDetailsReqDTO> dto = rtiApplicationDetailService
				.getRtiDetails(rtiDto.getApmApplicationId(), orgId);
		List<TbApprejMas> tbApprejMas = new ArrayList<>();
		final List<Long> artId = new ArrayList<>(0);
		Long woId = null;
		for (RtiApplicationFormDetailsReqDTO appRej : dto) {
			woId = Long.valueOf(appRej.getRtiId());
			if (appRej.getRtiRemarks() != null && !appRej.getRtiRemarks().isEmpty()) {
				artId.add(Long.valueOf(appRej.getRtiRemarks()));
			}
		}
		if (artId != null && !artId.isEmpty()) {
			tbApprejMas = tbApprejMasService.findByArtId(artId, UserSession.getCurrent().getOrganisation().getOrgid());
			model.setApprejMasList(tbApprejMas);
		}
		rtiDto.getApplicantDTO().setDepartmentId(Long.valueOf(rtiDto.getRtiDeptId()));
		Department dept = departmentService.findDepartmentByCode("RTI");
		WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(orgId, dept.getDpDeptid(),
				sm.getSmServiceId(), rtiDto.getTrdWard1(), rtiDto.getTrdWard2(), rtiDto.getTrdWard4(),
				rtiDto.getTrdWard4(), rtiDto.getTrdWard5());

		Long group = 1l;
		// D#124802
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)
				&& rtiDto.getRtiRelatedDeptId() != null) {
			model.getReqDTO()
					.setDepartmentName(departmentService.findDepartmentDescByDeptId(rtiDto.getRtiRelatedDeptId()));
		}

		TaskAssignmentRequest taskDto = new TaskAssignmentRequest();
		taskDto.setWorkflowTypeId(workflowMas.getWfId());
		taskDto.setServiceEventName("PIO Response");
		LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> hashmap = taskAssignmentService
				.getEventLevelGroupsByWorkflowTypeAndEventName(taskDto);

		LinkedHashMap<String, TaskAssignment> grp1 = hashmap
				.get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + group);

		for (int j = 1; j <= grp1.size(); j++) {
			TaskAssignment ta = grp1.get(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + j);

			List<Long> actorIdList = new ArrayList<>();
			String[] empIds = ta.getActorId().split(MainetConstants.operator.COMMA);
			for (String s : empIds) {
				actorIdList.add(Long.valueOf(s));
			}
			// change for showing only current loging employee name in report
			if (actorIdList != null) {
				actorIdList = new ArrayList<Long>();
				actorIdList.add(employee1.getEmpId());
			}
			if (!actorIdList.isEmpty()) {
				String emp = MainetConstants.BLANK;
				List<Employee> empList = employeeService.getEmpDetailListByEmpIdList(actorIdList, orgId);

				if (null != empList.get(0).getEmpmobno() && !empList.get(0).getEmpmobno().isEmpty()) {
					model.setPioNumber(empList.get(0).getEmpmobno());
				}

				for (Employee employee : empList) {
					emp += employee.getEmpname() + MainetConstants.WHITE_SPACE;

					if (null != employee.getEmpmname() && !employee.getEmpmname().isEmpty()) {
						emp += employee.getEmpmname() + MainetConstants.WHITE_SPACE;

					}
					if (null != employee.getEmplname() && !employee.getEmplname().isEmpty()) {
						emp += employee.getEmplname() + MainetConstants.WHITE_SPACE;

					}
				}
				model.setPioName(emp);
			}
		}

		return new ModelAndView("ApplicantDispatchReport", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getDeliveryMode", method = RequestMethod.POST)
	public ModelAndView getDeliveryModeTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = "deliveryModeType") String deliveryModeType) {
		this.getModel().bind(httpServletRequest);
		RtiDispatchModel model = this.getModel();
		model.setDeliveryMode(deliveryModeType);
		return new ModelAndView("RtiDispatchDeliveryMode", MainetConstants.FORM_NAME, model);
	}

//code added for showing action history details on Dispatch form  as per Anujs demo points
	@RequestMapping(method = { RequestMethod.GET }, params = "showHistoryDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest, ModelMap modelMap) {
		this.getModel().bind(httpServletRequest);
		RtiDispatchModel model = this.getModel();

		modelMap.addAttribute(MainetConstants.WorkFlow.ACTIONS,
				rtiApplicationDetailService.getRtiActionLogByApplicationId(model.getReqDTO().getApmApplicationId(),
						model.getReqDTO().getOrgId(), UserSession.getCurrent().getLanguageId()));
		return new ModelAndView("RtiActionHistory", MainetConstants.CommonConstants.COMMAND, getModel());
	}

}
