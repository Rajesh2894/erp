/**
 * 
 */
package com.abm.mainet.workManagement.ui.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbScrutinyLabels;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.master.service.TbScrutinyLabelsService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;

import com.abm.mainet.workManagement.dto.MbOverHeadDetDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDetailDto;
import com.abm.mainet.workManagement.dto.MeasurementBookCheckListDto;
import com.abm.mainet.workManagement.dto.MeasurementBookDetailsDto;
import com.abm.mainet.workManagement.dto.MeasurementBookLbhDto;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.ReadExcelData;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkEstimOverHeadDetDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMasterDto;
import com.abm.mainet.workManagement.dto.WorkEstimateMeasureDetailsDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WriteExcelData;
import com.abm.mainet.workManagement.service.MeasurementBookLbhService;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.MeasurementBookTaxDetailsService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsMaterialMasterService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksMeasurementSheetService;
import com.abm.mainet.workManagement.service.WorksWorkFlowService;
import com.abm.mainet.workManagement.ui.model.MeasurementBookModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/MeasurementBook.html")
public class MeasurementBookController extends AbstractFormController<MeasurementBookModel> {
	@Autowired
	WorkEstimateService workEstimateService;

	@Autowired
	MeasurementBookService mbService;

	@Autowired
	MeasurementBookLbhService mbLbhService;

	@Autowired
	WorkOrderService workOrderService;

	@Resource
	private IAttachDocsService attachDocsService;

	@Autowired
	private IFileUploadService fileUpload;

	@Resource
	private TenderInitiationService initiationService;

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private WmsProjectMasterService projectMasterService;
	
	@Resource
    private TbOrganisationService tbOrganisationService;
	
	

	/**
	 * Used to default Measurement Book Summary page
	 * 
	 * @param httpServletRequest
	 * @return defaultResult
	 * @throws Exception
	 */

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		MeasurementBookModel model = this.getModel();
		this.getModel().setCommonHelpDocs("MeasurementBook.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagN, orgId));
		if ((Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)|| Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))) {
			model.setMbList(mbService.getAllMBListSummaryByOrgId(orgId));
		} else {
			model.setMbList(mbService.getAllMBListByOrgId(orgId));
		}
		
		model.setWorkList(workDefinitionService.findAllWorkOrderGeneratedWorks(orgId));
		model.setVendorDetail(workOrderService.findAllWorkOrderGeneratedVendorDetail(orgId));
		populateModel(model);
		return defaultResult();
	}

	/**
	 * Used to get WorkOrder Detail By workOrderId
	 * 
	 * @param orgId
	 * @param projId
	 * @return
	 * @return workDefinationDto
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GetWorkOrderDetail, method = RequestMethod.POST)
	public String WorkOrderDetailByWorkOrderId(
			@RequestParam(MainetConstants.WorksManagement.WORK_ORDER_ID) Long workOrderId) {
		for (WorkOrderDto workOrderDto : this.getModel().getWorkOrderDtoList()) {
			if (workOrderDto.getWorkId().longValue() == workOrderId.longValue()) {
				workOrderDto.setContractNo(workOrderDto.getContractMastDTO().getContNo());
				List<Long> ids = new ArrayList<>();
				for (String id : workOrderDto.getMultiSelect()) {
					ids.add(Long.parseLong(id));
				}
				if (!ids.isEmpty() && ids != null) {
					String employee = MainetConstants.BLANK;
					List<EmployeeBean> empList = mbService.getAssignedEmpDetailByEmpIdList(ids,
							UserSession.getCurrent().getOrganisation().getOrgid());
					for (EmployeeBean employeeBean : empList) {
						employee += employeeBean.getEmpname() + MainetConstants.WHITE_SPACE + employeeBean.getEmpmname()
								+ MainetConstants.WHITE_SPACE + employeeBean.getEmplname()
								+ MainetConstants.WorksManagement.FW_ARROW + MainetConstants.WHITE_SPACE
								+ employeeBean.getDesignName() + MainetConstants.WHITE_SPACE
								+ MainetConstants.operator.COMMA + MainetConstants.WHITE_SPACE;
					}

					workOrderDto.setWorkAssigneeName(employee);
				}
				this.getModel().setWorkOrderDto(workOrderDto);
				break;
			}
		}

		List<Long> ids = new ArrayList<>();
		for (String id : this.getModel().getWorkOrderDto().getMultiSelect()) {
			ids.add(Long.parseLong(id));
		}

		return this.getModel().getWorkOrderDto().getContractNo() + MainetConstants.operator.COMMA
				+ this.getModel().getWorkOrderDto().getContractFromDate();
	}

	/**
	 * This Method is used for filter Measurement Book Data on search criteria
	 * 
	 * @param workId
	 * @return workOrder Details
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.FilterMeasurementBookData, method = RequestMethod.POST)
	public @ResponseBody List<WorkOrderDto> filterMeasurementBookData(final HttpServletRequest request,
			@RequestParam(MainetConstants.WorkDefination.WORK_ID) final Long workId,
			@RequestParam(MainetConstants.WorksManagement.STATUS) final String status,
			@RequestParam("mbNo") final String mbNo, @RequestParam("workName") final Long workName,
			@RequestParam("vendorId") final Long vendorId) {
		List<WorkOrderDto> workOrderDtoList = new ArrayList<>();
		String workState = null;
		int langId = UserSession.getCurrent().getLanguageId();
		List<MeasurementBookMasterDto> bookMasDtos = mbService.getAllMbDeatilsBySearch(workId, status, mbNo, workName,
				vendorId, UserSession.getCurrent().getOrganisation().getOrgid());
		if (workId != null) {
			Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();

			TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);

			WorkDefinitionDto definitionDto = workDefinitionService
					.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
			workState = definitionDto.getWorkStatus();
		}

		if (!bookMasDtos.isEmpty()) {
			for (MeasurementBookMasterDto bookMasterDto : bookMasDtos) {
				WorkOrderDto orderDto = workOrderService.getWorkOredrByOrderId(bookMasterDto.getWorkOrId());
				
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
					if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagP)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.TASK_STATUS_PENDING);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("mb.pending"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagD)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.TASK_STATUS_DRAFT);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("mb.draft"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagA)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.TASK_STATUS_APPROVED);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("status.approved"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagR)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.TASK_STATUS_REJECTED);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("wms.Rejected.status"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.RATE_TYPE)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.WorksManagement.RABILL);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("wms.RA.bill"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.Send_for_RA_Bill)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.WorksManagement.SendRAbill);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("works.measurement.ra.bill"));
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.send_for_approval)) {
						if(langId == 1)
							orderDto.setMbStatus(MainetConstants.WorksManagement.SendForApproval);
						else
							orderDto.setMbStatus(ApplicationSession.getInstance().getMessage("work.management.sendTool"));
					}
				}
				else{
					if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagP)) {
						orderDto.setMbStatus(MainetConstants.TASK_STATUS_PENDING);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagD)) {
						orderDto.setMbStatus(MainetConstants.TASK_STATUS_DRAFT);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagA)) {
						orderDto.setMbStatus(MainetConstants.TASK_STATUS_APPROVED);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.FlagR)) {
						orderDto.setMbStatus(MainetConstants.TASK_STATUS_REJECTED);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.RATE_TYPE)) {
						orderDto.setMbStatus(MainetConstants.WorksManagement.RABILL);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.Send_for_RA_Bill)) {
						orderDto.setMbStatus(MainetConstants.WorksManagement.SendRAbill);
					} else if (bookMasterDto.getMbStatus().equals(MainetConstants.WorksManagement.send_for_approval)) {
						orderDto.setMbStatus(MainetConstants.WorksManagement.SendForApproval);
					}
				}
				
				if (bookMasterDto.getMbTotalAmt() == null) {
					orderDto.setMbTotalAmt(new BigDecimal(MainetConstants.ZERO));
				} else {
					orderDto.setMbTotalAmt(bookMasterDto.getMbTotalAmt().setScale(2, BigDecimal.ROUND_UP));
				}
				orderDto.setMbId(bookMasterDto.getWorkMbId());
				orderDto.setMbNo(bookMasterDto.getWorkMbNo());
				//Defect #148411
				if(bookMasterDto.getWorkMbTakenDate() != null)
				orderDto.setWorkMbTakenDate(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(bookMasterDto.getWorkMbTakenDate()));
				if (workId != null) {
					orderDto.setWorkStatus(workState);
				}
				workOrderDtoList.add(orderDto);
			}
		}
		return workOrderDtoList;
	}

	/**
	 * this method is used to Create new Measurement Book details
	 * 
	 * @param request
	 * @return create Measurement Book details form.
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.CreateMb, method = RequestMethod.POST)
	public ModelAndView createMb(final HttpServletRequest request,
			@RequestParam(value = MainetConstants.WorksManagement.contractNo, required = false) String contractNo) {

		MeasurementBookModel model = this.getModel();
		model.setSaveMode(MainetConstants.FlagC);
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		String ids = this.getModel().getWorkOrderDto().getWorkAssignee();
		if (ids != null && !ids.isEmpty()) {
			String array[] = ids.split(MainetConstants.operator.COMMA);
			for (String id : array) {
				this.getModel().getMbMasDto().getMbMultiSelect().add(id);
			}
		}
		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		if (tenderWorkDto != null) {
			this.getModel().setEstimateMasDtoList(workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
					UserSession.getCurrent().getOrganisation().getOrgid()));
			model.getWorkOrderDto().setWorkName(tenderWorkDto.getWorkName());
			model.getWorkOrderDto().setProjName(tenderWorkDto.getProjectName());
		}
		this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId()));
		return new ModelAndView(MainetConstants.WorksManagement.MeasurementBookForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SaveMbEstimationData, method = RequestMethod.POST)
	public void saveEstimateData(final HttpServletRequest request) {
		bindModel(request);

		MeasurementBookModel model = this.getModel();
		List<WorkEstimateMasterDto> masdtoList = new ArrayList<>();
		model.saveMbEstimationData();
		MeasurementBookMasterDto mbMasterDto = mbService.getMBByWorkOrderId(model.getWorkOrderDto().getWorkId(), null);
		model.setMbMasDto(mbMasterDto);
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		model.setEstimateMasDtoList(workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
		for (MeasurementBookDetailsDto mbDetDto : mbMasterDto.getMbDetails()) {
				if (workEstimateDto.getWorkEstemateId().longValue() == mbDetDto.getWorkEstimateMaster()
						.getWorkEstemateId().longValue()) {
					
					WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
					BeanUtils.copyProperties(workEstimateDto, dto);
					workEstimateDto.setCheckBox(true);
					dto.setCheckBox(true);
					dto.setMbDetId(mbDetDto.getMbdId());
					dto.setMbId(mbMasterDto.getWorkMbId());
					workEstimateDto.setMbDetId(mbDetDto.getMbdId());
					workEstimateDto.setMbId(mbMasterDto.getWorkMbId());
					dto.setSorIteamUnitDesc(CommonMasterUtility
							.getCPDDescription(workEstimateDto.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
					MeasurementBookDetailsDto detailsDto = mbService
							.getMBDetailsByDetailsId(workEstimateDto.getMbDetId());
					BigDecimal test = new BigDecimal(0);
					dto.setTotalMbAmount(new BigDecimal(0));
					if (detailsDto.getWorkActualQty() != null) {
						dto.setWorkEstimQuantityUtl(detailsDto.getWorkActualQty());
						test = test.add(new BigDecimal(detailsDto.getWorkActualQty().toString()));
					}

					if (detailsDto.getWorkActualAmt() != null) {
						test = test.add(new BigDecimal(detailsDto.getWorkActualAmt().toString()));
					}
					dto.setTotalMbAmount(test);

					mbDetDto.getWorkEstimateMaster().setMbDetId(mbDetDto.getMbdId());
					mbDetDto.getWorkEstimateMaster().setMbId(mbMasterDto.getWorkMbId());
					masdtoList.add(dto);
				}
			}
		}
		model.setSavedEstimateDtoList(masdtoList);
	}

	/**
	 * This Method is used for display measurement sheet
	 * 
	 * @param request
	 * @return AddMeasurementSheet
	 */

	@RequestMapping(params = MainetConstants.WorksManagement.ADD_MEASUREMENT_SHEET, method = RequestMethod.POST)
	public ModelAndView addMeasurementSheet(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		for (WorkEstimateMasterDto workEstimateDto : this.getModel().getSavedEstimateDtoList()) {
			workEstimateDto.setSorIteamUnitDesc(CommonMasterUtility
					.getCPDDescription(workEstimateDto.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
			MeasurementBookDetailsDto detailsDto = mbService.getMBDetailsByDetailsId(workEstimateDto.getMbDetId());

			//workEstimateDto.setTotalMbAmount(new BigDecimal(0));
			workEstimateDto.setWorkEstimQuantityUtl(detailsDto.getWorkActualQty());
			workEstimateDto.setCummulativeAmount(detailsDto.getWorkUtlQty());
			if (detailsDto.getWorkActualAmt() != null) {
				workEstimateDto.setTotalMbAmount(detailsDto.getWorkActualAmt());
			} else {
				if (detailsDto.getWorkActualQty() != null) {
					BigDecimal amount = detailsDto.getWorkActualQty().multiply(workEstimateDto.getSorBasicRate());
					workEstimateDto.setTotalMbAmount(amount);
				}
			}
		}
		return new ModelAndView(MainetConstants.WorksManagement.AddMeasurementDetail, MainetConstants.FORM_NAME, model);
	}

	/**
	 * This method is used for select Measurement data on the basis of this
	 * attribute
	 * 
	 * @param workEId
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.MeasurementListByworkEstimateId)
	public ModelAndView measurementListByworkEstimateId(
			@RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
			@RequestParam(name = MainetConstants.WorksManagement.MbDetId) final Long mbDetId,
			@RequestParam(name = MainetConstants.WorksManagement.directFlag) final String directFlag,
			final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setParentLbhEstimatedId(workEId);
		if (directFlag.equals(MainetConstants.FlagU)) {
			model.setLbhDtosList(mbLbhService.getLbhDetailsByMbDetailId(orgId, mbDetId));
			Map<Long, Long> directQuantity = mbService.getPreviousDirectUtilzedQuantity(
					model.getMbMasDto().getWorkMbId(), model.getWorkOrderDto().getWorkId());
			for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
				if (workEstimateDto.getWorkEstemateId().longValue() == workEId.longValue()) {
					List<WorkEstimateMasterDto> measureDetList = new ArrayList<>();

					WorkEstimateMasterDto dto = workEstimateService.findById(orgId, workEId);
					if (dto != null) {
						dto.setMbDetId(mbDetId);
						BigDecimal amount = new BigDecimal(1);
						if (dto.getMeMentLength() != null) {
							amount = amount.multiply(dto.getMeMentLength());
						}
						if (dto.getMeMentHeight() != null) {
							amount = amount.multiply(dto.getMeMentHeight());
						}
						if (dto.getMeMentBreadth() != null) {
							amount = amount.multiply(dto.getMeMentBreadth());
						}
						//#154284-code added for direct abstract with no and without lbh
						/*dto.setMeActValue(
								amount.multiply(new BigDecimal(dto.getMeNos())).setScale(2, BigDecimal.ROUND_UP));*/
						dto.setMeActValue(dto.getWorkEstimQuantity());
						if (directQuantity != null && directQuantity.containsKey(dto.getWorkEstemateId())) {
							dto.setMeActualNos(dto.getMeNos() - directQuantity.get(dto.getWorkEstemateId()));
							measureDetList.add(dto);
						} else {
							dto.setMeActualNos(dto.getMeNos());
							measureDetList.add(dto);
						}

						this.getModel().setDirectEstimateList(measureDetList);
					}

					break;
				}
			}

			for (WorkEstimateMasterDto workEstimateMasterDto : this.getModel().getEstimateMasDtoList()) {
				if (workEstimateMasterDto.getWorkEstemateId().longValue() == workEId.longValue()) {
					this.getModel().setEstimateMasterDto(workEstimateMasterDto);
					break;
				}
			}
				return new ModelAndView(MainetConstants.WorksManagement.mbDirectMeasureDetailsForm,
						MainetConstants.FORM_NAME, this.getModel());
			
		} else {
			List<Long> EstimateMasterDtoId = new ArrayList<>();
			List<WorkEstimateMeasureDetailsDto> measureList = new ArrayList<>();
			List<WorkEstimateMeasureDetailsDto> measureDetList = ApplicationContextProvider.getApplicationContext()
					.getBean(WorksMeasurementSheetService.class).getWorkEstimateDetailsByWorkEId(workEId);
			for (WorkEstimateMeasureDetailsDto estimateMeasureDetDto : measureDetList) {
				EstimateMasterDtoId.add(estimateMeasureDetDto.getMeMentId());
			}
			model.setLbhDtosList(
					mbLbhService.getLbhDetailsByMeasurementId(EstimateMasterDtoId, mbDetId, model.getSaveMode()));
			Map<Long, Long> quantity = mbService.getPreviousMbUtilzedQuantity(model.getMbMasDto().getWorkMbId(),
					model.getWorkOrderDto().getWorkId());
			if (model.getSaveMode().equals(MainetConstants.WorksManagement.APPROVAL)) {
				List<WorkEstimateMeasureDetailsDto> measureDetailsList = new ArrayList<>();
				for (MeasurementBookLbhDto measurementBookLbhDto : model.getLbhDtosList()) {
					for (WorkEstimateMeasureDetailsDto estimateMeasureDetailsDto : measureDetList) {
						if (estimateMeasureDetailsDto.getMeMentId().longValue() == measurementBookLbhDto
								.getEstimateMeasureDetId().longValue()) {
							measureDetailsList.add(estimateMeasureDetailsDto);
						}
					}
				}
				model.setMeasureDetailsList(measureDetailsList);

			} else {
				for (WorkEstimateMeasureDetailsDto estimateMeasureDetailsDto : measureDetList) {
					if (quantity != null && quantity.containsKey(estimateMeasureDetailsDto.getMeMentId())) {

						for (final Map.Entry<Long, Long> entry : quantity.entrySet()) {
							if (estimateMeasureDetailsDto.getMeMentId().longValue() == entry.getKey()) {
								estimateMeasureDetailsDto.setMeMentNumber(
										estimateMeasureDetailsDto.getMeMentNumber() - entry.getValue());
							}
						}
						measureList.add(estimateMeasureDetailsDto);
					} else {
						measureList.add(estimateMeasureDetailsDto);
					}
				}
			}
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				List<MeasurementBookLbhDto> lbhdtolist = new ArrayList<>();
				
				
				
				List<WorkEstimateMeasureDetailsDto> measureDetLi = ApplicationContextProvider.getApplicationContext()
						.getBean(WorksMeasurementSheetService.class).getWorkEstimateDetailsByWorkEId(workEId);

				List<Long> meMentIdList = new ArrayList<>();
				model.getLbhDtosList().forEach(lbhDto -> meMentIdList.add(lbhDto.getMBFlag()));
				
				for (WorkEstimateMeasureDetailsDto dto : measureDetLi) {
					if (!meMentIdList.contains(dto.getWorkEstemateId())) {
						MeasurementBookLbhDto detailsdto = new MeasurementBookLbhDto();
						detailsdto.setMbArea(dto.getMbArea());
						detailsdto.setMbVolume(dto.getMbVolume());
						detailsdto.setMbWeight(dto.getMbWeight());
						detailsdto.setMbParticulare(dto.getMeMentParticulare());
						detailsdto.setMbFormula(dto.getMeMentFormula());
						detailsdto.setMbHeight(dto.getMeMentHeight());
						detailsdto.setMbLength(dto.getMeMentLength());
						detailsdto.setMbNosAct(dto.getMeMentNumber());
						detailsdto.setMbValueType(dto.getMeMentValType());
						detailsdto.setMbValue(dto.getMeValue());
						detailsdto.setMbType(dto.getMeMentType());
						detailsdto.setMbTotal(dto.getMeMentToltal());
						detailsdto.setMbBreadth(dto.getMeMentBreadth());
						detailsdto.setEstimateMeasureDetId(dto.getMeMentId());
						detailsdto.setMbdId(mbDetId);
						detailsdto.setMBFlag(dto.getWorkEstemateId());
						Long longValue = detailsdto.getMbNosAct(); 
						BigDecimal mbNosAct = new BigDecimal(longValue.toString()); 
						BigDecimal mbTotal = detailsdto.getMbTotal();
						BigDecimal result = mbNosAct.multiply(mbTotal);
						detailsdto.setTotalAmt(result);

						
						lbhdtolist.add(detailsdto);
					}

				}
				
				lbhdtolist.addAll(model.getLbhDtosList());
				model.setLbhDtosList(lbhdtolist);
				  }
			else {
				model.setMeasureDetailsList(measureList);
			}
			
			
			for (WorkEstimateMasterDto workEstimateMasterDto : this.getModel().getEstimateMasDtoList()) {

				if (workEstimateMasterDto.getWorkEstemateId().longValue() == workEId.longValue()) {
					this.getModel().setEstimateMasterDto(workEstimateMasterDto);
					break;
				}
			}
			return new ModelAndView(MainetConstants.WorksManagement.MeasurementListDetails, MainetConstants.FORM_NAME,
					this.getModel());
		}
	}

	/**
	 * This Method is used show rate analysis pages
	 * 
	 * @param workEId
	 * @param request
	 * @return openRateAnalysis
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.OPEN_RATE_ANALYSIS)
	public ModelAndView openRateAnalysis(
			@RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
			@RequestParam(name = MainetConstants.WorksManagement.MbId) final Long mbId,
			@RequestParam(name = MainetConstants.WorksManagement.MbDetId) final Long mbDetId,
			@RequestParam(name = MainetConstants.ScheduleOfRate.SOR_ID) final Long sorId,
			final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		List<MeasurementBookDetailsDto> detDtoList = new ArrayList<>();
		this.getModel().prepareAllMasterTypeList();
		List<WorkEstimateMasterDto> rateTypeEntity = workEstimateService.getAllRateTypeMBByEstimateNo(workEId, mbId,
				mbDetId);
		if (model.getSaveMode().equals(MainetConstants.WorksManagement.APPROVAL)) {
			List<WorkEstimateMasterDto> rateDtoList = new ArrayList<>();
			model.setMbDetRateDtoList(mbService.getMBByMbdetParentId(mbDetId));
			for (MeasurementBookDetailsDto measurementBookDetailsDto : model.getMbDetRateDtoList()) {
				for (WorkEstimateMasterDto estimateMasDto : rateTypeEntity) {
					if (estimateMasDto.getWorkEstemateId().longValue() == measurementBookDetailsDto.getWorkEstemateId()
							.longValue()) {
						rateDtoList.add(estimateMasDto);
					}
				}
			}
			model.setAddAllRatetypeEntity(rateDtoList);
		} else {
			this.getModel()
					.setAddAllRatetypeEntity(workEstimateService.getAllRateTypeMBByEstimateNo(workEId, mbId, mbDetId));
			for (WorkEstimateMasterDto estimateDto : this.getModel().getAddAllRatetypeEntity()) {
				detDtoList.add(mbService.getMBDetailsByEstimateId(estimateDto.getWorkEstemateId(), mbDetId, null));
			}
			this.getModel().setMbDetRateDtoList(detDtoList);
		}
		for (WorkEstimateMasterDto workEstimateMasterDto : this.getModel().getEstimateMasDtoList()) {
			if (workEstimateMasterDto.getWorkEstemateId().longValue() == workEId.longValue()) {
				this.getModel().setEstimateMasterDto(workEstimateMasterDto);
				break;
			}
		}

		model.setRateList(ApplicationContextProvider.getApplicationContext().getBean(WmsMaterialMasterService.class)
				.getMaterialListBySorId(sorId));
		return new ModelAndView(MainetConstants.WorksManagement.RateAnalysis, MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SAVE_RATE_ANALYSIS, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveRateAnalysis(final HttpServletRequest request) {
		this.bindModel(request);
		this.getModel().saveMbRateEntity(this.getModel().getMbDetRateDtoList());
		return index();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_LBH_FORM)
	public ModelAndView saveLbhForm(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().saveLbhForm();
		return new ModelAndView(MainetConstants.WorksManagement.AddMeasurementDetail, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.OpenMbMasForm, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView openMbMasForm(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();

		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		model.setEstimateMasDtoList(workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));

		for (MeasurementBookDetailsDto mbDetDto : model.getMbMasDto().getMbDetails()) {
			for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
				if (workEstimateDto.getWorkEstemateId().longValue() == mbDetDto.getWorkEstimateMaster()
						.getWorkEstemateId().longValue()) {
					workEstimateDto.setCheckBox(true);
					workEstimateDto.setMbId(model.getMbMasDto().getWorkMbId());
					workEstimateDto.setMbDetId(mbDetDto.getMbdId());
				}
			}
		}
		return new ModelAndView(MainetConstants.WorksManagement.MeasurementBookForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This method is used for select Measurement Tax Details Form data on the basis
	 * of this attribute
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.MbTaxDetailsForm)
	public ModelAndView createMbTaxDetailsForm(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.setMbTaxDetailsDto(ApplicationContextProvider.getApplicationContext()
				.getBean(MeasurementBookTaxDetailsService.class).getMbTaxDetails(model.getMbMasDto().getWorkMbId(),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		model.setTaxList(ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
				.getAllTaxesBasedOnDept(UserSession.getCurrent().getOrganisation().getOrgid(),
						ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
								.getDepartmentIdByDeptCode(MainetConstants.WorksManagement.WORKS_MANAGEMENT)));
		model.setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		return new ModelAndView(MainetConstants.WorksManagement.MbTaxDetailsForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This method is used for save Measurement Tax Details Form data on the basis
	 * of this attribute
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SaveMbTaxDetails)
	public ModelAndView saveMbTaxDetailsForm(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.saveMbTaxDetailsForm();
		return new ModelAndView(MainetConstants.WorksManagement.mbTaxDetailsForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This method is used for select Measurement EnclosuersForm data on the basis
	 * of this attribute
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.AddMbEnclosuersForm)
	public ModelAndView addMbEnclosures(final HttpServletRequest request) {
		bindModel(request);
		fileUpload.sessionCleanUpForFileUpload();
		MeasurementBookMasterDto mbMasDto = mbService.getMBById(this.getModel().getMbMasDto().getWorkMbId());
		if (mbMasDto.getMbTotalAmt() != null)
			this.getModel().setTotalMbAmount(mbMasDto.getMbTotalAmt().toString());
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getMbMasDto().getWorkMbNo());
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView(MainetConstants.WorksManagement.AddMbEnclosuersForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getFileCountUpload().clear();

		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			this.getModel().getFileCountUpload().add(entry.getKey());
		}
		int fileCount = FileUploadUtility.getCurrent().getFileMap().entrySet().size();
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
		return new ModelAndView(MainetConstants.WorksManagement.MbEnclosuresFileUpload, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SaveMbEnclosuers)
	public ModelAndView saveWorkEnclosuresData(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.prepareMbEnclosuersData(model.getMbMasDto().getWorkMbNo());
		return defaultResult();
	}

	@RequestMapping(params = MainetConstants.WorksManagement.VeiwMB, method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView measurementBookForm(final HttpServletRequest request,
			@RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId) {
		bindModel(request);
		populateModel(this.getModel());
		List<WorkEstimateMasterDto> masdtoList = new ArrayList<>();
		MeasurementBookModel model = this.getModel();
		MeasurementBookMasterDto mbMasDto = null;
		if (request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE) == null
				|| (!request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString()
						.equals(MainetConstants.WorksManagement.APPROVAL))) {
			model.setSaveMode(mode);
		} else {
			this.getModel().setRequestFormFlag(
					request.getSession().getAttribute(MainetConstants.WorksManagement.SAVEMODE).toString());
			model.setSaveMode(mode);
			model.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagN,
					UserSession.getCurrent().getOrganisation().getOrgid()));
			request.getSession().removeAttribute(MainetConstants.WorksManagement.SAVEMODE);
		}
		if (mode.equals(MainetConstants.FlagV)) {
			mbMasDto = mbService.getMBById(workId);
		} else {
			mbMasDto = mbService.getMBByWorkOrderId(workId, null);
			this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
					UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId()));
		}
		model.setMbMasDto(mbMasDto);
		model.setWorkOrderDto(workOrderService.getWorkOredrByOrderId(mbMasDto.getWorkOrId()));

		List<Long> ids = new ArrayList<>();
		for (String id : model.getWorkOrderDto().getMultiSelect()) {
			ids.add(Long.parseLong(id));
		}
		if (!ids.isEmpty() && ids != null) {
			String employee = MainetConstants.BLANK;
			List<EmployeeBean> empList = mbService.getAssignedEmpDetailByEmpIdList(ids,
					UserSession.getCurrent().getOrganisation().getOrgid());
			for (EmployeeBean employeeBean : empList) {
				employee += employeeBean.getEmpname() + MainetConstants.WHITE_SPACE + employeeBean.getEmpmname()
						+ MainetConstants.WHITE_SPACE + employeeBean.getEmplname()
						+ MainetConstants.WorksManagement.FW_ARROW + MainetConstants.WHITE_SPACE
						+ employeeBean.getDesignName() + MainetConstants.WHITE_SPACE + MainetConstants.operator.COMMA
						+ MainetConstants.WHITE_SPACE;
			}

			model.getWorkOrderDto().setWorkAssigneeName(employee);
		}
		if (mbMasDto.getMbStatus().equals(MainetConstants.WorksManagement.APPROVAL)) {
			model.setSaveMode(MainetConstants.WorksManagement.APPROVAL);
		}
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();

		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		model.setEstimateMasDtoList(workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		model.getWorkOrderDto().setWorkName(tenderWorkDto.getWorkName());
		model.getWorkOrderDto().setProjName(tenderWorkDto.getProjectName());
		for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
		for (MeasurementBookDetailsDto mbDetDto : mbMasDto.getMbDetails()) {
			
				if (workEstimateDto.getWorkEstemateId().longValue() == mbDetDto.getWorkEstimateMaster()
						.getWorkEstemateId().longValue()) {
					WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
					BeanUtils.copyProperties(workEstimateDto, dto);
					workEstimateDto.setCheckBox(true);
					dto.setCheckBox(true);
					dto.setMbDetId(mbDetDto.getMbdId());
					dto.setMbId(mbMasDto.getWorkMbId());
					workEstimateDto.setMbId(mbMasDto.getWorkMbId());
					workEstimateDto.setMbDetId(mbDetDto.getMbdId());
					dto.setCummulativeAmount(mbDetDto.getWorkUtlQty());
					masdtoList.add(dto);
				}
			}
		}
		model.setSavedEstimateDtoList(masdtoList);
		this.getModel().setAttachDocsList(attachDocsService
				.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), mbMasDto.getWorkMbNo()));
		return new ModelAndView(MainetConstants.WorksManagement.MeasurementBookForm, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@RequestMapping(params = MainetConstants.WorksManagement.openMbNonSorForm, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView openMbNonSorForm(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		List<MeasurementBookDetailsDto> detDtoList = new ArrayList<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		MeasurementBookDetailsDto detDto = null;
		List<WorkEstimateMasterDto> nonSorDtoList = new ArrayList<>();
		Map<Long, BigDecimal> quantity = mbService.getPreviousNonSorUtilzedQuantity(model.getMbMasDto().getWorkMbId(),
				model.getWorkOrderDto().getWorkId());
		for (WorkEstimateMasterDto estimateDto : model.getSavedEstimateDtoList()) {
			if (estimateDto.getWorkEstimFlag().equals(MainetConstants.FlagN)
					|| estimateDto.getWorkEstimFlag().equals(MainetConstants.WorksManagement.MN)) {
				if (quantity != null && quantity.containsKey(estimateDto.getWorkEstemateId())) {
					WorkEstimateMasterDto nonSorDto = workEstimateService.findById(orgId,
							estimateDto.getWorkEstemateId());
					detDto = mbService.getMBDetailsByEstimateId(estimateDto.getWorkEstemateId(),
							estimateDto.getMbDetId(), estimateDto.getWorkEstimFlag());
					nonSorDto.setCummulativeAmount(quantity.get(estimateDto.getWorkEstemateId()));
					nonSorDto.setWorkEstimQuantity(nonSorDto.getWorkEstimQuantity());
					detDto.setWorkEstemateId(estimateDto.getWorkEstemateId());
					// nonSorDto.getWorkEstimQuantity().subtract(quantity.get(estimateDto.getWorkEstemateId())));
					detDtoList.add(detDto);
					nonSorDtoList.add(nonSorDto);
				} else {
					detDtoList.add(mbService.getMBDetailsByEstimateId(estimateDto.getWorkEstemateId(),
							estimateDto.getMbDetId(), estimateDto.getWorkEstimFlag()));
					nonSorDtoList.add(estimateDto);
				}

			}
		}
		this.getModel().setMbDetNonSorDtoList(detDtoList);
		this.getModel().setNonSorEstimateDtoList(nonSorDtoList);
		return new ModelAndView(MainetConstants.WorksManagement.MbNonSorForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.WorksManagement.saveMbNonSorItems, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveMbNonSorItems(final HttpServletRequest request) {
		this.bindModel(request);
		this.getModel().saveMbNonSorEntity(this.getModel().getMbDetNonSorDtoList());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
		List<WorkEstimateMasterDto> masdtoList = new ArrayList<>();
		MeasurementBookMasterDto mbMasterDto = mbService.getMBByWorkOrderId(this.getModel().getWorkOrderDto().getWorkId(), null);
		this.getModel().setMbMasDto(mbMasterDto);
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		this.getModel().setEstimateMasDtoList(workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
				UserSession.getCurrent().getOrganisation().getOrgid()));
		for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
		for (MeasurementBookDetailsDto mbDetDto : mbMasterDto.getMbDetails()) {
				if (workEstimateDto.getWorkEstemateId().longValue() == mbDetDto.getWorkEstimateMaster()
						.getWorkEstemateId().longValue()) {
					
					WorkEstimateMasterDto dto = new WorkEstimateMasterDto();
					BeanUtils.copyProperties(workEstimateDto, dto);
					workEstimateDto.setCheckBox(true);
					dto.setCheckBox(true);
					dto.setMbDetId(mbDetDto.getMbdId());
					dto.setMbId(mbMasterDto.getWorkMbId());
					workEstimateDto.setMbDetId(mbDetDto.getMbdId());
					workEstimateDto.setMbId(mbMasterDto.getWorkMbId());
					dto.setSorIteamUnitDesc(CommonMasterUtility
							.getCPDDescription(workEstimateDto.getSorIteamUnit().longValue(), MainetConstants.MENU.E));
					MeasurementBookDetailsDto detailsDto = mbService
							.getMBDetailsByDetailsId(workEstimateDto.getMbDetId());
					BigDecimal test = new BigDecimal(0);
					dto.setTotalMbAmount(new BigDecimal(0));
					if (detailsDto.getWorkActualQty() != null) {
						dto.setWorkEstimQuantityUtl(detailsDto.getWorkActualQty());
						test = test.add(new BigDecimal(detailsDto.getWorkActualQty().toString()));
					}

					if (detailsDto.getWorkActualAmt() != null) {
						test = test.add(new BigDecimal(detailsDto.getWorkActualAmt().toString()));
					}
					dto.setTotalMbAmount(test);

					mbDetDto.getWorkEstimateMaster().setMbDetId(mbDetDto.getMbdId());
					mbDetDto.getWorkEstimateMaster().setMbId(mbMasterDto.getWorkMbId());
					masdtoList.add(dto);
				}
			}
		}
		this.getModel().setSavedEstimateDtoList(masdtoList);
		}
		return index();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.openMBEstimateForm)
	public ModelAndView openSchemeMasterForm(final HttpServletRequest request) {
		bindModel(request);
		return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_WORKESTIMATE
				+ this.getModel().getEstimateMasDtoList().get(0).getWorkId() + MainetConstants.WorksManagement.AND_MODE
				+ MainetConstants.FlagM);
	}

	@RequestMapping(params = MainetConstants.WorksManagement.getMbAmountSheet, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getMbAmountSheet(final HttpServletRequest request,
			@RequestParam(name = MainetConstants.WorkDefination.WORK_ID) final Long workId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode) {
		bindModel(request);
		Map<String, Object> dataMap = new HashMap<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Boolean flag = true;
		List<MeasurementBookMasterDto> bookMasDtos = mbService.filterMeasurementBookData(workId, null, orgId);
		StringBuilder itemcode = new StringBuilder(150);

		for (MeasurementBookMasterDto mbDto : bookMasDtos) {
			for (MeasurementBookDetailsDto detDto : mbDto.getMbDetails()) {
				if (detDto.getWorkActualAmt() == null && detDto.getWorkActualQty() == null) {
					flag = false;
					itemcode.append(workEstimateService.findById(orgId, detDto.getWorkEstemateId()).getSorDIteamNo());
					itemcode.append(MainetConstants.WHITE_SPACE + MainetConstants.operator.COMMA);
				}

			}
			/*
			 * Defect #82006-->not allowed to add MB entry if previous MB is not sent for
			 * approval
			 */
			dataMap.put(MainetConstants.WorksManagement.MBSTATUS, mbDto.getMbStatus());
		}

		BigDecimal mbAmount = new BigDecimal(MainetConstants.Common_Constant.ZERO_SEC);

		for (MeasurementBookMasterDto measurementBookMasterDto : bookMasDtos) {
			if (measurementBookMasterDto.getMbTotalAmt() != null) {
				mbAmount = mbAmount.add(measurementBookMasterDto.getMbTotalAmt());
			}
		}

		this.getModel().setWorkOrderDto(workOrderService.getWorkOredrByOrderId(workId));

		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		WorkDefinitionDto definitionDto = workDefinitionService.findAllWorkDefinitionById(tenderWorkDto.getWorkId());
		BigDecimal estimatedAmtWithCpdMode = null;
		if (this.getModel().getCpdModeCatSor() != null
				&& this.getModel().getCpdModeCatSor().equals(MainetConstants.FlagY)) {
			estimatedAmtWithCpdMode = definitionDto.getEstimateWithoutOverheadAmt();
		} else {
			estimatedAmtWithCpdMode = definitionDto.getWorkEstAmt();
		}
		BigDecimal estimateAcessAmount = null;
		if (definitionDto.getDeviationPercent() != null) {
			estimateAcessAmount = (estimatedAmtWithCpdMode
					.multiply(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per)
							.add(definitionDto.getDeviationPercent())))
									.divide(new BigDecimal(MainetConstants.WorksManagement.Hundred_Per));
			dataMap.put(MainetConstants.WorksManagement.deviation, definitionDto.getDeviationPercent());
		} else {
			dataMap.put(MainetConstants.WorksManagement.deviation, MainetConstants.ZERO);
			estimateAcessAmount = estimatedAmtWithCpdMode;
		}
		if (definitionDto.getDeviationPercent() == null) {
			dataMap.put(MainetConstants.WorksManagement.estimateAcessAmount, MainetConstants.ZERO);
			dataMap.put(MainetConstants.WorksManagement.mbAmount, mbAmount.toString());
			dataMap.put(MainetConstants.WorksManagement.estimateAmount, estimatedAmtWithCpdMode);
			dataMap.put(MainetConstants.WorksManagement.deviation, MainetConstants.ZERO);
		}

		else {
			dataMap.put(MainetConstants.WorksManagement.estimateAcessAmount,
					estimateAcessAmount.setScale(2, BigDecimal.ROUND_FLOOR));
			dataMap.put(MainetConstants.WorksManagement.mbAmount, mbAmount.toString());
			dataMap.put(MainetConstants.WorksManagement.estimateAmount, estimatedAmtWithCpdMode);
		}
		if (estimateAcessAmount.compareTo(mbAmount) >= 0 && flag) {
			dataMap.put(MainetConstants.WorksManagement.STATUS, MainetConstants.FlagA);
			if (estimateAcessAmount.compareTo(mbAmount) == 0)
				dataMap.put(MainetConstants.WorksManagement.mbConsumed, MainetConstants.FlagY);
		} else {
			dataMap.put(MainetConstants.WorksManagement.STATUS, MainetConstants.FlagR);
			if (itemcode.length() != 0)
				dataMap.put(MainetConstants.WorksManagement.itemCode, itemcode);
		}
		return dataMap;
	}

	/**
	 * Used to hide SOR details
	 * 
	 * @param MeasurementBookModel
	 */
	private void populateModel(MeasurementBookModel model) {
		// check for sub category flag is active or not
		List<LookUp> lookUpList = CommonMasterUtility.getLookUps(MainetConstants.ScheduleOfRate.HSF,
				UserSession.getCurrent().getOrganisation());
		if (lookUpList != null && !lookUpList.isEmpty()) {
			model.setCpdModeCatSor(lookUpList.get(0).getLookUpCode());
		} else {
			model.setCpdModeCatSor(null);
		}
		String status = MainetConstants.FlagA;
		String WorKsShortCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
		final Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(WorKsShortCode, status);
		model.setDeptId(deptId);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.updateRaBillStatus, method = RequestMethod.POST)
	public ModelAndView updateRABillStatus(@RequestParam(MainetConstants.WorksManagement.WORK_ID) final Long workId,
			@RequestParam(MainetConstants.WorksManagement.MODE) final String mode) {
		MeasurementBookMasterDto mbMasDto = mbService.getMBByWorkOrderId(workId, null);
		mbService.updateMeasureMentMode(mbMasDto.getWorkMbId(), MainetConstants.WorksManagement.RATE_TYPE);
		return defaultResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.getUploadedImage)
	public ModelAndView getUploadedImage(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getdataOfUploadedImage();
		return new ModelAndView(MainetConstants.WorksManagement.UpdateMbImages, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.UpdateMbImg, method = RequestMethod.POST)
	public ModelAndView updateMbImages(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		long count = 0;
		model.setDocDescription(null);
		List<AttachDocs> attachDocs = new ArrayList<>();
		fileUpload.sessionCleanUpForFileUpload();

		Map<Long, Set<File>> fileMap = new HashMap<>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<AttachDocs> docs = new ArrayList<>();
		if (model.getUploadMode().equals(MainetConstants.FlagU)) {
			attachDocs = attachDocsService.findByCode(orgId,
					model.getMbMasDto().getWorkMbNo() + MainetConstants.WINDOWS_SLASH + orgId
							+ MainetConstants.WINDOWS_SLASH + MainetConstants.FlagU + model.getMeasurementId());
		} else {
			attachDocs = attachDocsService.findByCode(orgId,
					model.getMbMasDto().getWorkMbNo() + MainetConstants.WINDOWS_SLASH + orgId
							+ MainetConstants.WINDOWS_SLASH + MainetConstants.FlagS + model.getMeasurementId());
		}

		if (!attachDocs.isEmpty()) {
			for (AttachDocs attachDoc : attachDocs) {
				docs.add(attachDoc);
			}
			model.setDocDescription(attachDocs.get(0).getDmsDocName());
		}
		if (!docs.isEmpty())
			fileMap.put(count, mbService.getUploadedFileList(docs, FileNetApplicationClient.getInstance()));

		if (!docs.isEmpty())
			FileUploadUtility.getCurrent().setFileMap(fileMap);
		model.setAttachList(docs);
		model.getdataOfUploadedImage();
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			for (MeasurementBookLbhDto dto : this.getModel().getLbhDtosList()) {

				if (null!=dto.getEstimateMeasureDetId() && dto.getEstimateMeasureDetId() == model.getMeasurementId().longValue()) {
					
					this.getModel().setMeasurementBookLbhDto(dto);
					break;
				}
			}
			
		}
		else {
			for (WorkEstimateMeasureDetailsDto dto : this.getModel().getMeasureDetailsList()) {

				if (null!=dto.getMeMentId() && dto.getMeMentId().longValue() == model.getMeasurementId().longValue()) {
					this.getModel().setMeasureDto(dto);
					break;
				}
			}
		}
		

		
		return new ModelAndView(MainetConstants.WorksManagement.UpdateMbImages, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * This method is used for save Uploaded Images of this attribute
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SaveUploadedMbImg)
	public ModelAndView saveMbUploadedImage(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.prepareMeasureDtailsDocData();
		if (model.getEstimateMasterDto().getEstimateType().equals(MainetConstants.FlagU)) {
			return new ModelAndView(MainetConstants.WorksManagement.mbDirectMeasureDetailsForm,
					MainetConstants.FORM_NAME, this.getModel());
		} else {
			return new ModelAndView(MainetConstants.WorksManagement.MeasurementListDetails, MainetConstants.FORM_NAME,
					this.getModel());
		}

	}

	/**
	 * this method is used to open CheckList Form
	 * 
	 * @param request
	 * @return create Measurement Book CheckList Form.
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.OpenCheckList, method = RequestMethod.POST)
	public ModelAndView openCheckList(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setLocList(ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
				.fillAllActiveLocationByOrgId(orgId));
		MeasurementBookCheckListDto dto = mbService.getMBCheckListByMBAndOrgId(model.getMbMasDto().getWorkMbId(),
				orgId);
		if (dto == null)
			dto = new MeasurementBookCheckListDto();
		ServiceMaster service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceByShortName(MainetConstants.WorksManagement.MCS, orgId);
		if (service != null) {
			List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.MBC,
					UserSession.getCurrent().getOrganisation());
			if (!lookUps.isEmpty()) {
				for (LookUp lookUp : lookUps) {
					if (lookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
						List<TbScrutinyLabels> classACheckList = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsService.class).findAllScrutinyLabelsByServiceAndLevelId(
										service.getSmServiceId(), Long.parseLong(lookUp.getOtherField()), orgId);
						for (TbScrutinyLabels tbScrutinyLabels : classACheckList) {
							for (MeasurementBookCheckListDetailDto chkDto : dto.getMbChkListDetails()) {
								if (tbScrutinyLabels.getLevels().longValue() == chkDto.getLevels().longValue()
										&& tbScrutinyLabels.getSlLabelId().longValue() == chkDto.getSlLabelId()
												.longValue()) {
									tbScrutinyLabels.setSlValidationText(chkDto.getMbcdValue());
									tbScrutinyLabels.setComN5(chkDto.getMbcdId());
								}
							}
						}
						dto.setClassACheckList(classACheckList);
					}
					if (lookUp.getLookUpCode().equals(MainetConstants.FlagC)) {
						List<TbScrutinyLabels> checkListColumnBeams = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsService.class).findAllScrutinyLabelsByServiceAndLevelId(
										service.getSmServiceId(), Long.parseLong(lookUp.getOtherField()), orgId);
						for (TbScrutinyLabels tbScrutinyLabels : checkListColumnBeams) {
							for (MeasurementBookCheckListDetailDto chkDto : dto.getMbChkListDetails()) {
								if (tbScrutinyLabels.getLevels().longValue() == chkDto.getLevels().longValue()
										&& tbScrutinyLabels.getSlLabelId().longValue() == chkDto.getSlLabelId()
												.longValue()) {
									tbScrutinyLabels.setSlValidationText(chkDto.getMbcdValue());
									tbScrutinyLabels.setComN5(chkDto.getMbcdId());
								}
							}
						}
						dto.setCheckListColumnBeams(checkListColumnBeams);
					}
					if (lookUp.getLookUpCode().equals(MainetConstants.FlagB)) {
						List<TbScrutinyLabels> checkListBrickWork = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsService.class).findAllScrutinyLabelsByServiceAndLevelId(
										service.getSmServiceId(), Long.parseLong(lookUp.getOtherField()), orgId);
						for (TbScrutinyLabels tbScrutinyLabels : checkListBrickWork) {
							for (MeasurementBookCheckListDetailDto chkDto : dto.getMbChkListDetails()) {
								if (tbScrutinyLabels.getLevels().longValue() == chkDto.getLevels().longValue()
										&& tbScrutinyLabels.getSlLabelId().longValue() == chkDto.getSlLabelId()
												.longValue()) {
									tbScrutinyLabels.setSlValidationText(chkDto.getMbcdValue());
									tbScrutinyLabels.setComN5(chkDto.getMbcdId());
								}
							}
						}
						dto.setCheckListBrickWork(checkListBrickWork);
					}
					if (lookUp.getLookUpCode().equals(MainetConstants.FlagP)) {
						List<TbScrutinyLabels> checkListPlastering = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsService.class).findAllScrutinyLabelsByServiceAndLevelId(
										service.getSmServiceId(), Long.parseLong(lookUp.getOtherField()), orgId);
						for (TbScrutinyLabels tbScrutinyLabels : checkListPlastering) {
							for (MeasurementBookCheckListDetailDto chkDto : dto.getMbChkListDetails()) {
								if (tbScrutinyLabels.getLevels().longValue() == chkDto.getLevels().longValue()
										&& tbScrutinyLabels.getSlLabelId().longValue() == chkDto.getSlLabelId()
												.longValue()) {
									tbScrutinyLabels.setSlValidationText(chkDto.getMbcdValue());
									tbScrutinyLabels.setComN5(chkDto.getMbcdId());
								}
							}
						}
						dto.setCheckListPlastering(checkListPlastering);
					}
					if (lookUp.getLookUpCode().equals(MainetConstants.FlagW)) {
						List<TbScrutinyLabels> checkListWaterSupply = ApplicationContextProvider.getApplicationContext()
								.getBean(TbScrutinyLabelsService.class).findAllScrutinyLabelsByServiceAndLevelId(
										service.getSmServiceId(), Long.parseLong(lookUp.getOtherField()), orgId);
						for (TbScrutinyLabels tbScrutinyLabels : checkListWaterSupply) {
							for (MeasurementBookCheckListDetailDto chkDto : dto.getMbChkListDetails()) {
								if (tbScrutinyLabels.getLevels().longValue() == chkDto.getLevels().longValue()
										&& tbScrutinyLabels.getSlLabelId().longValue() == chkDto.getSlLabelId()
												.longValue()) {
									tbScrutinyLabels.setSlValidationText(chkDto.getMbcdValue());
									tbScrutinyLabels.setComN5(chkDto.getMbcdId());
								}
							}
						}
						dto.setCheckListWaterSupply(checkListWaterSupply);
					}
				}
			}

		}
		model.setCheckListDto(dto);

		return new ModelAndView(MainetConstants.WorksManagement.MB_CHKLIST_FORM, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/**
	 * This method is used for save Measurement Book CheckList Form data on the
	 * basis
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_CHECKLIST)
	public ModelAndView saveCheckListData(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.saveMbCheckListData();
		return new ModelAndView(MainetConstants.WorksManagement.MB_CHKLIST_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	// User Story #38057
	@RequestMapping(params = MainetConstants.WorksManagement.ADD_MB, method = RequestMethod.POST)
	public ModelAndView addMbDetails(final HttpServletRequest request) {
		MeasurementBookModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setSaveMode(MainetConstants.FlagC);
		model.setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
		model.setWorkList(workDefinitionService.findAllWorkOrderGeneratedWorks(orgId));
		model.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagN, orgId));
		this.getModel().setEmployeeList(employeeService.findAllEmployeeByDept(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptId()));
		return new ModelAndView(MainetConstants.WorksManagement.MeasurementBookForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.AddMbOverheadsForm)
	public ModelAndView addOverHeadsDetails(final HttpServletRequest request) {
		bindModel(request);
		Organisation org = UserSession.getCurrent().getOrganisation();

		this.getModel()
				.setOverHeadPercentLookUp(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY, org));
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();

		/*
		 * MeasurementBookMasterDto mbMasDto =
		 * mbService.getMBByWorkOrderId(this.getModel().getMbMasDto().getWorkOrId(),
		 * null);
		 */
		List<MbOverHeadDetDto> mboverheadmastDtos = mbService
				.getmbOverDetByMbMasId(this.getModel().getMbMasDto().getWorkMbId(), org.getOrgid());

		/* this.getModel().setMbMasDto(mbMasDto); */

		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		List<WorkEstimOverHeadDetDto> workOverDtoList = workEstimateService
				.getEstimateOverHeadDetByWorkId(tenderWorkDto.getWorkId());
		List<MbOverHeadDetDto> mbOverHeadDtoList = new ArrayList<>();
		// List<MbOverHeadDetDto> mboverheadmastDtos =
		// this.getModel().getMbMasDto().getMbOverHeadDetDtos();
		for (WorkEstimOverHeadDetDto workOverHeadDetDto : workOverDtoList) {
			MbOverHeadDetDto mbOverHeadDto = new MbOverHeadDetDto();
			mbOverHeadDto.setWorkEstimOverHeadDetDto(workOverHeadDetDto);
			mbOverHeadDto.setOverHeadId(workOverHeadDetDto.getOverHeadId());
			mbOverHeadDto.setWorkId(workOverHeadDetDto.getWorkId());
			mbOverHeadDto.setOverHeadCode(workOverHeadDetDto.getOverHeadCode());
			mbOverHeadDto.setOverheadDesc(workOverHeadDetDto.getOverheadDesc());
			mbOverHeadDto.setOverHeadRate(workOverHeadDetDto.getOverHeadRate());
			mbOverHeadDto.setOverHeadValue(workOverHeadDetDto.getOverHeadValue());
			mbOverHeadDto.setOverHeadvalType(workOverHeadDetDto.getOverHeadvalType());
			mbOverHeadDto.setMbMasterId(this.getModel().getMbMasDto().getWorkMbId());
			if (!mboverheadmastDtos.isEmpty()) {
				mboverheadmastDtos.forEach(dto -> {
					if (dto.getOverHeadId().equals(workOverHeadDetDto.getOverHeadId())) {
						mbOverHeadDto.setActualAmount(dto.getActualAmount());
						mbOverHeadDto.setMbOvhId(dto.getMbOvhId());
						mbOverHeadDto.setCreatedBy(dto.getCreatedBy());
						mbOverHeadDto.setCreatedDate(dto.getCreatedDate());
						mbOverHeadDto.setLgIpMac(dto.getLgIpMac());
						mbOverHeadDto.setOrgId(dto.getOrgId());
					}
				});
			}
			mbOverHeadDtoList.add(mbOverHeadDto);
		}

		this.getModel().setMbeOverHeadDetDtoList(mbOverHeadDtoList);

		this.getModel().setTotalMbAmount(
				mbService.getTotalMbAmtByMbId(this.getModel().getMbMasDto().getWorkMbId(), org.getOrgid()).toString());
		BigDecimal sum = new BigDecimal(0);
		if (mbOverHeadDtoList != null && !mbOverHeadDtoList.isEmpty()) {
			if (mbOverHeadDtoList.size() == 1) {
				sum = mbOverHeadDtoList.get(0).getActualAmount();
			} else {
				if (mbOverHeadDtoList.get(0).getActualAmount() != null) {
					sum = mbOverHeadDtoList.stream().map(MbOverHeadDetDto::getActualAmount).reduce(BigDecimal::add)
							.get();
				}
			}
		}
		if (sum == null) {
			sum = new BigDecimal(0);
		}
		this.getModel().setPreviousMbOverHeadAmt(sum);
		this.getModel().setOverHeadLookUp(CommonMasterUtility.getLookUps("OHT", org));
		this.getModel().setNewWorkId(tenderWorkDto.getWorkId());

		return new ModelAndView(MainetConstants.WorksManagement.AddMbOverheadsForm, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.SAVE_OVERHEAD_DATA)
	public ModelAndView saveOverHeadData(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().prepareOverHeadData(this.getModel().getMbeOverHeadDetDtoList(), this.getModel());
		return defaultResult();
	}
	//D90007
	@RequestMapping(params = "validateOverheadAmount", method = RequestMethod.POST)
	public @ResponseBody Boolean validateOverheadAmount(final HttpServletRequest request) {
		bindModel(request);
		Boolean flag = true;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Map<Long, BigDecimal>> overheadMap = new ArrayList<Map<Long, BigDecimal>>();
		WorkDefinitionDto definitionDto = new WorkDefinitionDto();
		if (!this.getModel().getEstimateMasDtoList().isEmpty()) {
			definitionDto = workDefinitionService
					.findWorkDefinitionByWorkId(this.getModel().getEstimateMasDtoList().get(0).getWorkId(), orgId);

		}
		this.getModel().getMbeOverHeadDetDtoList().forEach(dto -> {
			Map<Long, BigDecimal> map = new HashMap<Long, BigDecimal>();
			map.put(dto.getOverHeadId(), dto.getActualAmount());
			overheadMap.add(map);
		});

		if (!this.getModel().getMbeOverHeadDetDtoList().isEmpty()) {
			// this.getModel().getMbeOverHeadDetDtoList().forEach(dto -> {
			for (MbOverHeadDetDto mbOverHeadDetDto : this.getModel().getMbeOverHeadDetDtoList()) {
				BigDecimal sum = new BigDecimal(0);
				BigDecimal totalOverheadValue = new BigDecimal(0);
				if (definitionDto != null && definitionDto.getDeviationPercent() != null
						&& definitionDto.getDeviationPercent().compareTo(new BigDecimal(1)) > 0) {
					totalOverheadValue = (mbOverHeadDetDto.getOverHeadValue()
							.multiply(definitionDto.getDeviationPercent()).divide(new BigDecimal(100)));
					totalOverheadValue = mbOverHeadDetDto.getOverHeadValue().add(totalOverheadValue);

				}
				List<MbOverHeadDetDto> headDetDtos = mbService
						.getmbOverDetByEstimationId(mbOverHeadDetDto.getOverHeadId(), orgId);

				if (!headDetDtos.isEmpty()) {
					sum = headDetDtos.stream().map(MbOverHeadDetDto::getActualAmount).reduce(BigDecimal::add).get();
					BigDecimal overheadAmount = getoverheadValue(overheadMap, mbOverHeadDetDto.getOverHeadId());
					sum = sum.add(overheadAmount);
					if (totalOverheadValue.compareTo(new BigDecimal(0)) > 0) {
						if (sum.compareTo(totalOverheadValue) > 0) {
							return false;
						}
					} else {
						if (sum.compareTo(mbOverHeadDetDto.getOverHeadValue()) > 0) {
							return false;
						}
					}

				} else {
					BigDecimal overheadAmount = getoverheadValue(overheadMap, mbOverHeadDetDto.getOverHeadId());
					if (totalOverheadValue.compareTo(new BigDecimal(0)) > 0) {
						if (overheadAmount.compareTo(totalOverheadValue) > 0) {
							return false;
						}
					} else {
						if (overheadAmount.compareTo(mbOverHeadDetDto.getOverHeadValue()) > 0) {
							return false;
						}
					}
				}
			}
		}
		return flag;

	}

	private BigDecimal getoverheadValue(List<Map<Long, BigDecimal>> overheadMap, Long overHeadId) {
		BigDecimal value = new BigDecimal(0);

		for (Map<Long, BigDecimal> mpp : overheadMap) {
			for (Map.Entry<Long, BigDecimal> entry : mpp.entrySet()) {
				if (entry.getKey().equals(overHeadId)) {
					value = entry.getValue();
				}
			}
		}

		return value;
	}
	
	@RequestMapping(params = "updateAmount", method = RequestMethod.POST)
	public @ResponseBody Boolean updateAmount(final HttpServletRequest request,@RequestParam("amt") final BigDecimal amt) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		mbService.updateTotalEstimateAmounts(amt,model.getMbMasDto().getWorkMbId());
		return true;
		
	}

	@RequestMapping(params = "updateListAmount", method = RequestMethod.POST)
	public @ResponseBody Boolean updateListAmount(final HttpServletRequest request,@RequestParam("amt") final BigDecimal amt,@RequestParam("count") final int count,@RequestParam("mbDetId") final Long mbDetId) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		model.getSavedEstimateDtoList().get(count).setTotalMbAmount(amt);
		mbService.updateTotalAmountByMbId(amt, mbDetId);
		return true;
	}		
		
	@RequestMapping(params = "sendForApproval", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> sendForApproval(final HttpServletRequest request,
			@RequestParam(value = "mbId", required = false) final Long mbId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode) {
		bindModel(request);
		String flag = null;
		Map<String, Object> object = new LinkedHashMap<String, Object>();

		MeasurementBookMasterDto measurementBookMaster = mbService.getMBById(mbId);

		WorkflowMas workFlowMas;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.WorksManagement.MCS, orgId);
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		taskAction.setReferenceId(measurementBookMaster.getWorkMbNo());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
         try {
		workFlowMas = ApplicationContextProvider.getApplicationContext().getBean(IWorkflowTyepResolverService.class)
				.resolveServiceWorkflowType(orgId, sm.getTbDepartment().getDpDeptid(), sm.getSmServiceId(), null, null,
						null, null, null);
        
		if (workFlowMas != null) {
			flag = ApplicationContextProvider.getApplicationContext().getBean(WorksWorkFlowService.class)
					.initiateWorkFlowWorksService(taskAction, workFlowMas, "MeasurementBookApproval.html",
							MainetConstants.FlagA);
			if (flag.equals(MainetConstants.SUCCESS_MSG)) {

				flag = MainetConstants.FlagY;
			} else if (flag.equals(MainetConstants.FAILURE_MSG)) {
				flag = MainetConstants.FlagN;
				
			}
		} else {
			flag = MainetConstants.FlagN;
			
		}
         }catch (Exception e) {
       	
       	getModel().addValidationError(getApplicationSession()
                .getMessage("Please Configure Work Flow"));
        
        }
		mbService.updateMeasureMentMode(measurementBookMaster.getWorkMbId(),
				"P");
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(MainetConstants.WorksManagement.CHECK_FLAG, flag);
		return object;
	}
	
	@RequestMapping(params = "exportExcelTemplateData")
	public void exportAccountBudgetCodeMasterExcelData(final HttpServletResponse response,
			final HttpServletRequest request) {
		
		this.getModel().getLbhDtosList().clear();
		try {
			WriteExcelData<MeasurementBookLbhDto> data = new WriteExcelData<>(
					"MeasurementBookLbhDto" + MainetConstants.XLSX_EXT, request, response);
			data.getExpotedExcelSheet(this.getModel().getLbhDtosList(), MeasurementBookLbhDto.class);

		} catch (Exception ex) {
			throw new FrameworkException(ex.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = "importExportExcelTemplateData")
	public ModelAndView exportImportExcelTemplate(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		fileUpload.sessionCleanUpForFileUpload();

		return new ModelAndView("mbMeasurement/excelupload", MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
	public ModelAndView loadValidateAndLoadExcelData(MeasurementBookLbhDto measurementBookLbhDto, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest)
					throws Exception {
		//bindModel(httpServletRequest);
		final ApplicationSession session = ApplicationSession.getInstance();
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		Organisation defaultOrg = null;
		if (isDafaultOrgExist) {
			defaultOrg = session.getSuperUserOrganization();
		} else {
			defaultOrg = UserSession.getCurrent().getOrganisation();
		}
		
		final Organisation orgid = UserSession.getCurrent().getOrganisation();
		final String filePath = getUploadedFinePath();
		ReadExcelData<MeasurementBookLbhDto> data = new ReadExcelData<>(filePath,
				MeasurementBookLbhDto.class);
		data.parseExcelList();

		List<String> errlist = data.getErrorList();
		if (!bindingResult.hasErrors()) {
			final List<MeasurementBookLbhDto> dto = data.getParseData();
			
			for(MeasurementBookLbhDto mbcode:dto) {
				mbcode.setOrgId(orgid.getOrgid());	
				mbcode.setSorRate(this.getModel().getEstimateMasterDto().getSorBasicRate());
				List<WorkEstimateMeasureDetailsDto> estimatdto=mbLbhService.getWorkEstimateByWorkEId(this.getModel().getEstimateMasterDto().getWorkEstemateId());
				for(WorkEstimateMeasureDetailsDto dto1:estimatdto) {
				mbcode.setEstimateMeasureDetId(dto1.getMeMentId());
				}
			}
			this.getModel().setLbhDtosList(dto);
			this.getModel().saveLbhForm();
		}

		return new ModelAndView(MainetConstants.WorksManagement.AddMeasurementDetail, MainetConstants.FORM_NAME,
				this.getModel());
	}


	private String getUploadedFinePath() {
		String filePath = null;
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			Set<File> list = entry.getValue();
			for (final File file : list) {
				filePath = file.toString();
				break;
			}
		}
		return filePath;
	}
	
	
	@RequestMapping(method = RequestMethod.POST, params = "measurementListByworkEstimateIdUpload")
	public ModelAndView measurementListByworkEstimateIdUpload(
			@RequestParam(name = MainetConstants.WorksManagement.WORK_EID) final Long workEId,
			@RequestParam(name = MainetConstants.WorksManagement.MbDetId) final Long mbDetId,
			@RequestParam(name = MainetConstants.WorksManagement.directFlag) final String directFlag,
			final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		fileUpload.sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setParentLbhEstimatedId(workEId);
			model.setLbhDtosList(mbLbhService.getLbhDetailsByMbDetailId(orgId, mbDetId));
			Map<Long, Long> directQuantity = mbService.getPreviousDirectUtilzedQuantity(
					model.getMbMasDto().getWorkMbId(), model.getWorkOrderDto().getWorkId());
			for (WorkEstimateMasterDto workEstimateDto : this.getModel().getEstimateMasDtoList()) {
				if (workEstimateDto.getWorkEstemateId().longValue() == workEId.longValue()) {
					List<WorkEstimateMasterDto> measureDetList = new ArrayList<>();

					WorkEstimateMasterDto dto = workEstimateService.findById(orgId, workEId);
					if (dto != null) {
						dto.setMbDetId(mbDetId);
						BigDecimal amount = new BigDecimal(1);
						if (dto.getMeMentLength() != null) {
							amount = amount.multiply(dto.getMeMentLength());
						}
						if (dto.getMeMentHeight() != null) {
							amount = amount.multiply(dto.getMeMentHeight());
						}
						if (dto.getMeMentBreadth() != null) {
							amount = amount.multiply(dto.getMeMentBreadth());
						}
						dto.setMeActValue(dto.getWorkEstimQuantity());
						if (directQuantity != null && directQuantity.containsKey(dto.getWorkEstemateId())) {
							dto.setMeActualNos(dto.getMeNos() - directQuantity.get(dto.getWorkEstemateId()));
							measureDetList.add(dto);
						} else {
							dto.setMeActualNos(dto.getMeNos());
							measureDetList.add(dto);
						}

						this.getModel().setDirectEstimateList(measureDetList);
					}

					break;
				}
			}

			for (WorkEstimateMasterDto workEstimateMasterDto : this.getModel().getEstimateMasDtoList()) {
				if (workEstimateMasterDto.getWorkEstemateId().longValue() == workEId.longValue()) {
					this.getModel().setEstimateMasterDto(workEstimateMasterDto);
					break;
				}
			}
				return new ModelAndView("mbMeasurement/excelupload", MainetConstants.FORM_NAME,
						this.getModel());
			
		
	}	
	
	@RequestMapping(method = RequestMethod.POST, params ="backMbUploadedImage")
	public ModelAndView backMbUploadedImage(final HttpServletRequest request) {
		bindModel(request);
		MeasurementBookModel model = this.getModel();
		if(model.getEstimateMasterDto().getEstimateType()!=null && model.getEstimateMasterDto().getEstimateType().equals(MainetConstants.FlagS))
			return new ModelAndView(MainetConstants.WorksManagement.MeasurementListDetails, MainetConstants.FORM_NAME,
					this.getModel());
		else
			return new ModelAndView(MainetConstants.WorksManagement.mbDirectMeasureDetailsForm, MainetConstants.FORM_NAME,
					this.getModel());
		}
	
	 @RequestMapping(method = RequestMethod.POST, params = { "doDetailsDeletion" })
		@ResponseBody
		public boolean doItemDeletionDetails(@RequestParam(name = "id", required = true) Long id, final HttpServletRequest request) {
			bindModel(request);
			List<MeasurementBookLbhDto> item = this.getModel().getLbhDtosList();
			List<MeasurementBookLbhDto> beanList = item.stream().filter(c -> c.getMbLbhId().longValue() != id.longValue()).collect(Collectors.toList());
			if (!beanList.isEmpty())
				this.getModel().setLbhDtosList(beanList);
			return true;
		}

}
