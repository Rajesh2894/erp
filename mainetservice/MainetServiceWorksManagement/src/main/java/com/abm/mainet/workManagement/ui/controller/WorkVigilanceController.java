package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.VigilanceDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.VigilanceService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.ui.model.WorkVigilanceModel;

/**
 * Object: this controller is used for work Vigilance create memo.
 * 
 * @author Suhel Chaudhry
 * @since 24 July 2019
 */
@Controller
@RequestMapping(MainetConstants.WorksManagement.WORK_VIGILANCE_HTML)
public class WorkVigilanceController extends AbstractFormController<WorkVigilanceModel> {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IFileUploadService fileUploadService;

	@Autowired
	private VigilanceService vigilanceService;

	@Autowired
	private WmsProjectMasterService iProjectMasterService;

	@Autowired
	private MeasurementBookService mbService;

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private WorksRABillService raBillService;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private WorkOrderService workOrderService;

	@Autowired
	private TenderInitiationService tenderInitiationService;

	private static final Logger LOGGER = Logger.getLogger(WorkVigilanceController.class);

	/**
	 * This Method to show work vigilance Summary page
	 * 
	 * @param model
	 * @param request
	 * @return index
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView index(final Model model, final HttpServletRequest request) {

		sessionCleanup(request);
		this.getModel().setCommonHelpDocs("WorkVigilance.html");
		fileUploadService.sessionCleanUpForFileUpload();
		List<VigilanceDto> vigilanceDtosList = vigilanceService.getFilterVigilanceList(null, null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (!vigilanceDtosList.isEmpty()) {
			model.addAttribute(MainetConstants.WorksManagement.VIGILANCEDTO_LIST, vigilanceDtosList);

		}
		this.getModel().setWmsprojectDto(iProjectMasterService
				.getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		return index();

	}

	/**
	 * This Method is Used for show create memo page
	 * 
	 * @param request
	 * @return OpenWorkVigilanceForm
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.OPEN_WORK_VIGILANCE_FORM, method = RequestMethod.POST)
	public ModelAndView openCreateMemoForm(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		fileUploadService.sessionCleanUpForFileUpload();
		this.getModel().setCommonHelpDocs("WorkVigilance.html");
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);

		this.getModel().setWmsprojectDto(iProjectMasterService
				.getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));

		return new ModelAndView(MainetConstants.WorksManagement.OPEN_WORK_VIGILANCE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * Used to get All Active Works Name By ProjectId
	 * 
	 * @param orgId
	 * @param projId
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
	public @ResponseBody List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {

		List<WorkDefinitionDto> workdefinitiondto = workDefinitionService
				.findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId);

		if (workdefinitiondto.isEmpty()) {
			throw new FrameworkException("Work does not exist Project Corresponding");
		}

		return workdefinitiondto;

	}

	/**
	 * Used to get All Active Works Name By ProjectId
	 * 
	 * @param workId
	 * @param projId based on racode and find out contract fromDate and todate
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.RACODE_MB, method = RequestMethod.POST)
	public ModelAndView getRacodeBasedMb(@RequestParam(MainetConstants.WorksManagement.WORKID_FOR_RA) Long workId,
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId, HttpServletRequest request) {

		this.bindModel(request);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();

		TenderWorkDto tenderworkdto = tenderInitiationService.findContractByWorkId(workId);
		if (tenderworkdto != null) {
			if (tenderworkdto.getContractId() != null) {
				WorkOrderDto workorderdto = workOrderService.fetchWorkOrderByContId(tenderworkdto.getContractId(),
						currentOrgId);
				this.getModel().setContractFromDate(workorderdto.getContractFromDate());
				this.getModel().setContractToDate(workorderdto.getContractToDate());

				List<WorksRABillDto> worksrabilist = raBillService.getRaBillListByProjAndWorkNumber(projId, workId,
						currentOrgId);
				this.getModel().setWorksrabilldto(worksrabilist);
				this.getModel().getVigilanceDto().setProjId(projId);
				this.getModel().setSorCommonId(workId);
			}
		} else {
			//#39015
			this.getModel().getVigilanceDto().setProjId(projId);
			this.getModel().setSorCommonId(workId);
			this.getModel().setContractFromDate(null);
			this.getModel().setContractToDate(null);
			this.getModel().setWorksrabilldto(null);
		}

		return new ModelAndView(MainetConstants.WorksManagement.OPEN_WORK_VIGILANCE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * 
	 * 
	 * @param racode
	 * @description by racode fetch the mb Details
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.RACODE_UI, method = RequestMethod.POST)
	public ModelAndView getRacodeBased(@RequestParam(MainetConstants.WorksManagement.RACODE) String racode) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkVigilanceModel raModel = this.getModel();
		if (!racode.isEmpty()) {
			this.getModel().getVigilanceDto().setReferenceNumber(racode);
			WorksRABillDto billDto = raBillService.getRABillDetailsByRaCode(racode, currentOrgId);
			raModel.setMeasureMentBookMastDtosList(
					mbService.getAllMbDeatilsByListOfMBId(billDto.getMbId(), currentOrgId));
		} 

		return new ModelAndView(MainetConstants.WorksManagement.MBSET_IN_GRID, MainetConstants.FORM_NAME, raModel);
	}

	/**
	 * 
	 * 
	 * @param workMbId
	 * @description by workMbId fetch the Mb details get_workmb_abstract_sheet
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_WORKMB_ABSTRACT_SHEET, method = RequestMethod.POST)
	public ModelAndView viewWorkMBAbstractSheetForm(
			@RequestParam(MainetConstants.WorksManagement.WORK_MB_ID) final Long workMbId,
			final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				this.getModel().getUserSession().getOrganisation().getOrgid());
		this.getModel().setWorksrabilldto(
				raBillService.getRaBillByRaCode(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setWorkMbId(workMbId);
		this.getModel().setMeasureMentBookMastDtosList(mbService.findAbstractSheetReport(workMbId));
		return new ModelAndView(MainetConstants.WorksManagement.WORK_MB_ABSTRACT_SHEETFORM, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/**
	 * This Method is used for edit Work Vigilance Form
	 * 
	 * @param vigilanceId
	 * @return editWorkVigilance
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_WORK_VIGILANCE, method = RequestMethod.POST)
	public ModelAndView editWorkVigilanceForm(
			@RequestParam(MainetConstants.WorksManagement.PROJECT_V_ID) final Long vigilanceId) {
		fileUploadService.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);

		VigilanceDto vigilanceDto = vigilanceService.getVigilanceById(vigilanceId);

		//Defect #92874
		/*this.getModel().setWorksrabilldto(
				raBillService.getRaBillByRaCode(UserSession.getCurrent().getOrganisation().getOrgid()));*/
		
		this.getModel().setWorksrabilldto(raBillService.getRaBillListByProjAndWorkNumber(vigilanceDto.getProjId(), vigilanceDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setWmsprojectDto(iProjectMasterService
				.getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setEmployeeList(
				employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), vigilanceDto.getReferenceNumber());

		TenderWorkDto tenderworkdto = tenderInitiationService.findContractByWorkId(vigilanceDto.getWorkId());
		if (tenderworkdto != null) {
			Long contId = tenderworkdto.getContractId();
			WorkOrderDto workorderdto = workOrderService.fetchWorkOrderByContId(contId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setContractFromDate(workorderdto.getContractFromDate());
			this.getModel().setContractToDate(workorderdto.getContractToDate());
		}
		this.getModel().setSorCommonId(vigilanceDto.getWorkId());
		this.getModel().setAttachDocsList(attachDocsList);
		this.getModel().setVigilanceDto(vigilanceDto);
		return new ModelAndView(MainetConstants.WorksManagement.OPEN_WORK_VIGILANCE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This Method is used for edit Work Vigilance Form
	 * 
	 * @param WORK_MB_ID
	 * @return getWorkEstimate
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.EditMb, method = RequestMethod.POST)
	public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.WORK_MB_ID) Long workMbId,
			@RequestParam(MainetConstants.WorksManagement.MODE) String mode, final HttpServletRequest request) {
		getModel().bind(request);
		fileUploadService.sessionCleanUpForFileUpload();
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.APPROVAL);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWorksrabilldto(
				raBillService.getRaBillByRaCode(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setEstimateMode(MainetConstants.FlagV);
		MeasurementBookMasterDto bookMaster = mbService.getMBById(workMbId);

		return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_MB + workMbId
				+ MainetConstants.WorksManagement.AND_MODE + mode);
	}

	/**
	 * This Method is used for view Work Vigilance Form
	 * 
	 * @param vigilanceId
	 * @return viewWorkVigilance
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.VIEW_WORK_VIGILAANCE, method = RequestMethod.POST)
	public ModelAndView viewWorkVigilanceForm(
			@RequestParam(MainetConstants.WorksManagement.PROJECT_V_ID) final Long vigilanceId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode, HttpServletRequest request) {

		fileUploadService.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);

		VigilanceDto vigilanceDto = vigilanceService.getVigilanceById(vigilanceId);

		//Defect #92874
		/*this.getModel().setWorksrabilldto(
				raBillService.getRaBillByRaCode(UserSession.getCurrent().getOrganisation().getOrgid()));*/
		
		this.getModel().setWorksrabilldto(raBillService.getRaBillListByProjAndWorkNumber(vigilanceDto.getProjId(), vigilanceDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));

		this.getModel().setWmsprojectDto(iProjectMasterService
				.getActiveProjectMasterListByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setEmployeeList(
				employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), vigilanceDto.getReferenceNumber());

		TenderWorkDto tenderworkdto = tenderInitiationService.findContractByWorkId(vigilanceDto.getWorkId());
		if (tenderworkdto != null) {
			Long contId = tenderworkdto.getContractId();
			WorkOrderDto workorderdto = workOrderService.fetchWorkOrderByContId(contId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setContractFromDate(workorderdto.getContractFromDate());
			this.getModel().setContractToDate(workorderdto.getContractToDate());
		}
		this.getModel().setSorCommonId(vigilanceDto.getWorkId());
		this.getModel().setAttachDocsList(attachDocsList);
		this.getModel().setVigilanceDto(vigilanceDto);
		return new ModelAndView(MainetConstants.WorksManagement.OPEN_WORK_VIGILANCE_FORM, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/**
	 * This Method is used for view Response And ActionForm
	 * 
	 * @param vigilanceIdhttp://192.168.100.54/MainetService/WorkVigilance.html
	 * @return viewWorkVigilance
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.VIEW_RESPONSE_AND_ACTION, method = RequestMethod.POST)
	public ModelAndView viewResponseAndActionForm(
			@RequestParam(MainetConstants.WorksManagement.PROJECT_V_ID) final Long vigilanceId) {
		fileUploadService.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.RM);

		VigilanceDto vigilanceDto = vigilanceService.getVigilanceById(vigilanceId);

		this.getModel().setEmployeeList(
				employeeService.findAllEmpByOrg(UserSession.getCurrent().getOrganisation().getOrgid()));
		final List<AttachDocs> attachDocsList = attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), vigilanceDto.getReferenceType());

		this.getModel().setAttachDocsList(attachDocsList);
		this.getModel().setVigilanceDto(vigilanceDto);
		return new ModelAndView(MainetConstants.WorksManagement.WORK_VIGILANCE_RESPONSE_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This Method is used for back for Mb View
	 * 
	 *
	 */

	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_FORM, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentForm(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		return new ModelAndView(MainetConstants.WorksManagement.WORKMB_VIEW_BACK, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This Method is used for back from abstractSheet View
	 * 
	 *
	 */

	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_APPROVAL_CURRENT_FORM, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		return new ModelAndView(MainetConstants.WorksManagement.WORK_VIGILANCE_ABSTRACT, MainetConstants.FORM_NAME,
				this.getModel());
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
		return new ModelAndView(MainetConstants.WorksManagement.WORK_VIGILANCE_FILEUPLOAD_TAG,
				MainetConstants.FORM_NAME, this.getModel());
	}

    @RequestMapping(params = MainetConstants.WorksManagement.SEARCH_DATA, method = RequestMethod.POST)
    public ModelAndView  searchVigilanceDetails(final Model model,final HttpServletRequest request,@RequestParam(MainetConstants.WorksManagement.PROJECT_ID) final Long projId,
            @RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId){

    	bindModel(request);
    	List<VigilanceDto> vigilanceDtosList  = new ArrayList<>();
    	List<VigilanceDto> vigilanceDtoList = vigilanceService.getVigilanceDetByProjIdAndWorkId(projId, workId,
				UserSession.getCurrent().getOrganisation().getOrgid());
    	for (VigilanceDto vigilanceDto : vigilanceDtoList) {
    		VigilanceDto dto = new VigilanceDto();
    		dto.setVigilanceId(vigilanceDto.getVigilanceId());
    		dto.setProjName(vigilanceDto.getProjName());
    		dto.setWorkName(vigilanceDto.getWorkName());
    		dto.setInspectDateDesc(vigilanceDto.getInspectDateDesc());
    		vigilanceDtosList.add(dto);
		}
        model.addAttribute(MainetConstants.WorksManagement.VIGILANCEDTO_LIST, vigilanceDtosList);
        this.getModel().getVigilanceDto().setProjId(projId);
        this.getModel().setSorCommonId(workId);
    	if (vigilanceDtosList.isEmpty()) {
    		ModelAndView mv = new ModelAndView("SearchWorkVigilance", MainetConstants.FORM_NAME, this.getModel());
    		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
    		this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("work.management.vldn.grid.nodatafound"));
    		return mv;
		}       
    	return new ModelAndView(MainetConstants.WorksManagement.VIGILANCE_SUMMARY, MainetConstants.FORM_NAME,this.getModel());
  			
    }

}
