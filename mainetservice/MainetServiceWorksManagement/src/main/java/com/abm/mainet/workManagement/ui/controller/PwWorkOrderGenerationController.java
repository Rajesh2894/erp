package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkOrderContractDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.service.ScheduleOfRateService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.ui.model.WorkOrderGenerationModel;

/**
 * Object: this controller is used for work Order generation.
 * 
 * @author vishwajeet.kumar
 * @since 14 May 2018
 *
 */
@Controller
@RequestMapping(MainetConstants.WorksManagement.WORK_ORDER_GENERATION_HTML)
public class PwWorkOrderGenerationController extends AbstractFormController<WorkOrderGenerationModel> {
	
	private static final Logger LOGGER = Logger.getLogger(PwWorkOrderGenerationController.class);

	@Autowired
	private TenderInitiationService tenderInitiationService;

	private List<TbApprejMas> tbApprejList;

	@Resource
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private WorkOrderService workOrderService;

	@Autowired
	private WorkDefinitionService definitionService;

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private TbDepartmentService iTbDepartmentService;

	/**
	 * This Method is used for return work order summary page
	 * 
	 * @param model
	 * @param httpServletRequest
	 * @return defaultResult
	 */
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView workOrderSummaryPage(final Model model, HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("PwWorkOrderGeneration.html");
		List<WorkOrderDto> workOrderDtosList = workOrderService.getFilterWorkOrderGenerationList(null, null, null, null,
				null, UserSession.getCurrent().getOrganisation().getOrgid());
		httpServletRequest.setAttribute("departmentList", loadDepartmentList());

		for (WorkOrderDto workOrderDto : workOrderDtosList) {
			TenderWorkDto tenderWorkDto = tenderInitiationService
					.findWorkByWorkId(workOrderDto.getContractMastDTO().getContId());
			if (tenderWorkDto != null) {
				WorkDefinitionDto definitionDto = definitionService
						.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
				workOrderDto.setWorkName(definitionDto.getWorkName());

			}
		}
		model.addAttribute(MainetConstants.WorksManagement.WORK_ORDER_DTO_LIST, workOrderDtosList);
		return defaultResult();
	}

	/**
	 * This Method is used for filter Work Order Generation List of Data on search
	 * criteria
	 * 
	 * @param request
	 * @param workOrderNo
	 * @param workOrderDate
	 * @param vendorName
	 * @param agreementFormDate
	 * @param agreementToDate
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.FILTER_WORK_ORDERLIST_DATA)
	public @ResponseBody List<WorkOrderDto> getFilterWorkOrderGenerationList(final HttpServletRequest request,
			@RequestParam(MainetConstants.WorksManagement.WORK_ORDER_NO) String workOrderNo,
			@RequestParam(MainetConstants.WorksManagement.WORK_ORDER_DATE) final Date workStipulatedDate,
			@RequestParam(MainetConstants.WorksManagement.CONTRACT_FROMDATE) final Date contractFromDate,
			@RequestParam(MainetConstants.WorksManagement.CONTRACT_TODATE) final Date contractToDate) {

		List<WorkOrderDto> orderDtosList = workOrderService.getFilterWorkOrderGenerationList(workOrderNo,
				workStipulatedDate, contractFromDate, contractToDate, null,
				UserSession.getCurrent().getOrganisation().getOrgid());

		for (WorkOrderDto workOrderDto : orderDtosList) {
			TenderWorkDto tenderWorkDto = tenderInitiationService
					.findWorkByWorkId(workOrderDto.getContractMastDTO().getContId());
			WorkDefinitionDto definitionDto = definitionService.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
			workOrderDto.setWorkName(definitionDto.getWorkName());
		}

		return orderDtosList;
	}

	/**
	 * This Method is used for Show Work Order Generation form
	 * 
	 * @param servletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION)
	public ModelAndView showWorkOrderGenerationForm(final HttpServletRequest servletRequest) {
		sessionCleanup(servletRequest);
		fileUploadService.sessionCleanUpForFileUpload();
		LOGGER.info("Method Started-------------------------->");
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		LOGGER.info("Model Data Start-------------------->");
		populateModel(this.getModel());
		LOGGER.info("Model Data End AND Contract Detail Data Start-------------------->");
		
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL)) {
		  List<WorkOrderContractDetailsDto> contractDetails = workOrderService
		  .findAllContractDetailsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		  LOGGER.info("Contract Detail Data End-------------------->"+contractDetails.size()); 
		  this.getModel().setContractDetailsDtosList(contractDetails);
		}
		 
		

		LOGGER.info("Employee Data Start-------------------->"+this.getModel().getDeptId());
		this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId()));
		LOGGER.info("Employee Data End-------------------->");
		LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WOA", "REM",
				UserSession.getCurrent().getLanguageId());

		LOGGER.info("LoopUp Data End-------------------->"+lookUp.getLookUpId());
		tbApprejList = tbApprejMasService.findByRemarkTyped(lookUp.getLookUpId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		LOGGER.info("tbApprejList End--------------------->");
		List<String> termsList = new ArrayList<>();
		if (tbApprejList != null && !tbApprejList.isEmpty()) {
			this.getModel().setTermFlag("Y");
			for (TbApprejMas apprejMas : tbApprejList) {
				termsList.add(apprejMas.getArtRemarks());
			}
		}
		this.getModel().setTermsList(termsList);
		
		this.getModel().setDepartmentsList(
				iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
		
		LOGGER.info("Method End------------------------------>");
		return new ModelAndView(MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@RequestMapping(params = MainetConstants.WorksManagement.GET_WORKNAME, method = RequestMethod.POST)
	@ResponseBody
	public String getAllWorkByWorkId(@RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId) {
		String status = MainetConstants.BLANK;
		TenderWorkDto tenderWorkDto = tenderInitiationService.findWorkByWorkId(contId);
		if (tenderWorkDto != null) {
			WorkDefinitionDto definitionDto = definitionService.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
			this.getModel().setWorkName(definitionDto.getWorkName());
			status = definitionDto.getWorkName();
		}
		return status;
	}

	/**
	 * This Method is Used for edit And View Work Order Generation
	 * 
	 * @param workId
	 * @return ShowWorkOrderGeneration
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_VIEW_WORK_ORDER_GENERATION, method = RequestMethod.POST)
	public ModelAndView editWorkOrderGeneration(
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId,
			@RequestParam("mode") String mode) {
		fileUploadService.sessionCleanUpForFileUpload();
		populateModel(this.getModel());
		List<Employee> employees = employeeService.findAllEmployeeByDept(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId());

		WorkOrderDto workOrderDto = workOrderService.getWorkOredrByOrderId(workId);

		if (mode.equals("E")) {
			this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
			this.getModel().setEmployeeList(employees);
		} else {
			this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
			List<Employee> emp = new ArrayList<Employee>();
			workOrderDto.getMultiSelect().forEach(abc -> {
				for (Employee employee : employees) {
					if (employee.getEmpId().equals(Long.valueOf(abc))) {
						emp.add(employee);
					}
				}
			});
			this.getModel().setEmployeeList(emp);
		}
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), workOrderDto.getWorkOrderNo());

		this.getModel().setAttachDocsList(attachDocsList);
		List<WorkOrderContractDetailsDto> contractDetails = workOrderService
				.getAllContractDetailsInWorkOrderByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setContractDetailsDtosList(contractDetails);
		this.getModel().setWorkOrderDto(workOrderDto);

		LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue("WOA", "REM",
				UserSession.getCurrent().getLanguageId());

		tbApprejList = tbApprejMasService.findByRemarkTyped(lookUp.getLookUpId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<String> termsList = new ArrayList<>();
		if (tbApprejList != null && !tbApprejList.isEmpty()) {
			this.getModel().setTermFlag(MainetConstants.FlagY);
			for (TbApprejMas apprejMas : tbApprejList) {
				termsList.add(apprejMas.getArtRemarks());
			}
		}
		//#71812
		if (tbApprejList != null && !tbApprejList.isEmpty()) {
			if(mode!= null && !mode.equals(MainetConstants.FlagC)) {
				if(!this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().isEmpty() 
						&& this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).getTermsDesc().contains("EnCrYpTed")) {
					this.getModel().setTermFlag(MainetConstants.FlagY);
					String[] reg=this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).getTermsDesc().split("EnCrYpTed");
					this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).setTermsDesc((reg[0]));
				}else {
					this.getModel().setTermFlag(MainetConstants.FlagN);
				}
			}
		}else {
			if(!this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().isEmpty() 
					&& this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).getTermsDesc().contains("EnCrYpTed")) {
				String[] reg=this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).getTermsDesc().split("EnCrYpTed");
				this.getModel().getWorkOrderDto().getWorkOrderTermsDtoList().get(0).setTermsDesc((reg[0]));
			}
		}
			
		
		this.getModel().setTermsList(termsList);
		return new ModelAndView(MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/*  *//**
			 * This Method is Used for view Work OrderGeneration
			 * 
			 * @param workId
			 * @return ShowWorkOrderGeneration
			 *//*
				 * @ResponseBody
				 * 
				 * @RequestMapping(params =
				 * MainetConstants.WorksManagement.VIEW_WORK_OREDER_GENERATION, method =
				 * RequestMethod.POST) public ModelAndView viewWorkOrderGeneration(
				 * 
				 * @RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId) {
				 * fileUploadService.sessionCleanUpForFileUpload();
				 * this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
				 * WorkOrderDto workOrderDto = workOrderService.getWorkOredrByOrderId(workId);
				 * populateModel(this.getModel()); final List<AttachDocs> attachDocsList =
				 * attachDocsService
				 * .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
				 * workOrderDto.getWorkOrderNo());
				 * this.getModel().setAttachDocsList(attachDocsList);
				 * List<WorkOrderContractDetailsDto> contractDetails = workOrderService
				 * .getAllContractDetailsInWorkOrderByOrgId(UserSession.getCurrent().
				 * getOrganisation().getOrgid());
				 * this.getModel().setContractDetailsDtosList(contractDetails);
				 * this.getModel().setWorkOrderDto(workOrderDto);
				 * this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
				 * UserSession.getCurrent().getOrganisation().getOrgid(),
				 * this.getModel().getDeptId())); return new
				 * ModelAndView(MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION,
				 * MainetConstants.FORM_NAME, this.getModel()); }
				 */

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.PRINT_WORK_ORDER_GENERATION, method = RequestMethod.POST)
	public ModelAndView workOrderPrintionForm(
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId) {

		WorkOrderDto workOrderDto = workOrderService.getWorkOredrByOrderId(workId);
		workOrderDto.setOrderDateDesc(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(workOrderDto.getOrderDate()));
		this.getModel().setWorkOrderDto(workOrderDto);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setContractAgreementSummaryDTO(
				ApplicationContextProvider.getApplicationContext().getBean(IContractAgreementService.class)
						.findByContractNo(orgId, workOrderDto.getContractMastDTO().getContNo()));
		TenderWorkDto tenderWorkDto = tenderInitiationService
				.findWorkByWorkId(workOrderDto.getContractMastDTO().getContId());
		this.getModel().setTenderWorkDto(tenderWorkDto);
		Date tenderDate = tenderWorkDto.getTenderDate();
		tenderWorkDto.setTenderDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tenderDate));
		List<WorkEstimateMasterDto> workEstimateMasterDto = ApplicationContextProvider.getApplicationContext()
				.getBean(WorkEstimateService.class).getWorkEstimateByWorkId(tenderWorkDto.getWorkId(), orgId);
		Long sorId = workEstimateMasterDto.get(0).getSorId();
		if (sorId != null) {
			ScheduleOfRateMstDto scheduleOfRateMstDto = ApplicationContextProvider.getApplicationContext()
					.getBean(ScheduleOfRateService.class).findSORMasterWithDetailsBySorId(sorId);
			this.getModel().setScheduleOfRateMstDto(scheduleOfRateMstDto);
		}
		if (tenderWorkDto.getTendTypePercent() != null) {
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(tenderWorkDto.getTendTypePercent(),
					UserSession.getCurrent().getOrganisation());
			
			tenderWorkDto.setAction(lookUp.getLookUpDesc());
		}
		return new ModelAndView(MainetConstants.WorksManagement.WORK_ORDER_GENERATION_PRINTING,
				MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * used to common method to call
	 * 
	 * @param model
	 */
	public void populateModel(WorkOrderGenerationModel model) {
		String status = MainetConstants.FlagA;
		String WorKsShortCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
		final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(WorKsShortCode, status);
		model.setDeptId(deptId);
		this.getModel().setDepartmentsList(
				iTbDepartmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid()));
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.VIEW_CONTRACTDET, method = RequestMethod.POST)
	public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.CONTRACT_ID) Long contId,
			final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.WOG);
		String showForm = MainetConstants.WorksManagement.WOG;
		return new ModelAndView(
				MainetConstants.WorksManagement.REDIRECT_TOCONTRACT + contId + MainetConstants.WorksManagement.AND_TYPE
						+ MainetConstants.MODE_VIEW + MainetConstants.WorksManagement.AND_SHOWFORM + showForm);
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_WORKORDER, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		final WorkOrderGenerationModel generationModel = this.getModel();
		return new ModelAndView(MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION, MainetConstants.FORM_NAME,
				generationModel);
	}

	/**
	 * This method is used to add row in file upload table
	 * 
	 * @param request
	 * @return workOrderGenerationFileUpload
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getFileCountUpload().clear();

		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			this.getModel().getFileCountUpload().add(entry.getKey());
		}
		int fileCount = (int) FileUploadUtility.getCurrent().getFileMap().entrySet().size();
		this.getModel().getFileCountUpload().add(fileCount + 1L);
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getAttachments().size(); i++)
			attachments.add(new DocumentDetailsVO());
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {

			attachments.get(entry.getKey().intValue()).setDoc_DESC_ENGL(
					this.getModel().getAttachments().get(entry.getKey().intValue()).getDoc_DESC_ENGL());
		}
		if (attachments.get(this.getModel().getAttachments().size()).getDoc_DESC_ENGL() == null)
			attachments.get(this.getModel().getAttachments().size()).setDoc_DESC_ENGL(MainetConstants.BLANK);
		else {
			DocumentDetailsVO ob = new DocumentDetailsVO();
			ob.setDoc_DESC_ENGL(MainetConstants.BLANK);
			attachments.add(ob);
		}
		this.getModel().setAttachments(attachments);
		return new ModelAndView(MainetConstants.WorksManagement.WORK_ORDER_GENERATION_FILE_UPLOAD,
				MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "getAgrementByDeptId", method = RequestMethod.POST)
	public ModelAndView getAgrementByDeptId(@RequestParam("dpDeptId") Long dpDeptId,
			final HttpServletRequest request) {
		getModel().bind(request);
		List<WorkOrderContractDetailsDto> contractDetailsList = workOrderService
				.findAllContractDetailsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		LOGGER.info("Contract Detail Data End-------------------->"+contractDetailsList.size());
		List<WorkOrderContractDetailsDto> contractDetails =contractDetailsList.stream().filter(d -> (dpDeptId!=null && d.getContDeptId()!=null && d.getContDeptId().equals(dpDeptId))).collect(Collectors.toList());
		this.getModel().setContractDetailsDtosList(contractDetails);
		this.getModel().getWorkOrderDto().setDeptId(dpDeptId);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
		this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
				UserSession.getCurrent().getOrganisation().getOrgid(), dpDeptId));
		}
		return new ModelAndView(MainetConstants.WorksManagement.SHOW_WORK_ORDER_GENERATION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = "SearchData")
	public @ResponseBody List<WorkOrderDto> getFilterWorkOrderGenerationList(final HttpServletRequest request) {
		bindModel(request);
		String workOrderNo = this.getModel().getWorkOrderDto().getWorkOrderNo();
		String workStipulatedDate = this.getModel().getWorkOrderDto().getActualStartDateDesc();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date workdate = null;
		request.setAttribute("departmentList", loadDepartmentList());
		if (workOrderNo.equals("")) {
			workOrderNo = null;
		}
		if (workStipulatedDate == null) {
			workStipulatedDate = "";
		}
		if (workStipulatedDate != null) {
			workdate = (Utility.stringToDate(workStipulatedDate,MainetConstants.DATE_FORMAT));
		}
		if (this.getModel().getWorkOrderDto().getWardZoneDto().getCodId1() == null) {
			this.getModel().getWorkOrderDto().getWardZoneDto().setCodId1(0l);
		}
		if (this.getModel().getWorkOrderDto().getWardZoneDto().getCodId2() == null) {
			this.getModel().getWorkOrderDto().getWardZoneDto().setCodId2(0l);
		}
		if (this.getModel().getWorkOrderDto().getWardZoneDto().getCodId3() == null) {
			this.getModel().getWorkOrderDto().getWardZoneDto().setCodId3(0l);
		}
		if (this.getModel().getWorkOrderDto().getDpDeptId() == null) {
			this.getModel().getWorkOrderDto().setDpDeptId(0l);
		}

		List<WorkOrderDto> orderDtosList = workOrderService.getFilterWorkOrderGeneration(workOrderNo,
				workdate, this.getModel().getWorkOrderDto().getWardZoneDto().getCodId1(),
				this.getModel().getWorkOrderDto().getWardZoneDto().getCodId2(),
				this.getModel().getWorkOrderDto().getWardZoneDto().getCodId3(), orgId,
				this.getModel().getWorkOrderDto().getDpDeptId());

		if (!orderDtosList.isEmpty()) {

			for (WorkOrderDto workOrderDto : orderDtosList) {
				TenderWorkDto tenderWorkDto = tenderInitiationService
						.findWorkByWorkId(workOrderDto.getContractMastDTO().getContId());
				WorkDefinitionDto definitionDto = definitionService
						.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
				workOrderDto.setWorkName(definitionDto.getWorkName());
			}

		}
		return orderDtosList;

	}

	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}
}
