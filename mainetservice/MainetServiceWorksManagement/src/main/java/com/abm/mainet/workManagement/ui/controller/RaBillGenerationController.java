package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.TaxDefinationDto;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.ContractMastDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.IContractAgreementService;
import com.abm.mainet.common.master.service.ITaxDefinationService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.domain.WorkOrder;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WmsProjectMasterDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkOrderDto;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.service.WorksRABillService;
import com.abm.mainet.workManagement.ui.model.RaBillGenerationModel;
import com.aspose.slides.p2cbca448.org;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Controller
@RequestMapping("/raBillGeneration.html")
public class RaBillGenerationController extends AbstractFormController<RaBillGenerationModel> {

	@Autowired
	private WmsProjectMasterService projectMasterService;

	@Autowired
	private WorkDefinitionService workDefinationService;

	@Autowired
	private MeasurementBookService mbService;

	@Autowired
	private TenderInitiationService initiationService;

	@Autowired
	private IContractAgreementService contractAgreementService;

	@Autowired
	private WorkOrderService orderService;

	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private WorksRABillService raBillService;

	@Autowired
	private ITaxDefinationService taxDefinationService;

	/**
	 * Used to default Ra-Bill Generation Summary page
	 * 
	 * @param httpServletRequest
	 * @return defaultResult
	 * @throws Exception
	 */

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) throws Exception {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setCommonHelpDocs("raBillGeneration.html");
		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
		this.getModel().setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                .getSecondaryHeadcodesForWorks(currentOrgId));
		/*
		 * this.getModel().setMeasureMentBookMastDtosList(mbService.getAllMBListByOrgId(
		 * currentOrgId));
		 */
		return defaultResult();
	}

	/**
	 * Used to get All Active WorksName By ProjectId
	 * 
	 * @param orgId
	 * @param projId
	 * @return workDefinationDto
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
	public List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {
		List<WorkDefinitionDto> obj = workDefinationService
				.findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId);
		obj.forEach(dto -> {
			dto.setWorkName("" + dto.getWorkcode() + " - " + dto.getWorkName());
		});
		this.getModel().setWorkDefList(obj);
		this.getModel().setCommonHelpDocs("raBillGeneration.html");
		return obj;

	}

	// User Story #38044
	@RequestMapping(params = MainetConstants.WorksManagement.ADD_RA, method = RequestMethod.POST)
	public ModelAndView addRaBill(final HttpServletRequest request) {
		RaBillGenerationModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setSaveMode(MainetConstants.FlagC);
		model.setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
		model.setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                .getSecondaryHeadcodesForWorks(orgId));
		return new ModelAndView(MainetConstants.WorksManagement.RABILL_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * this method is used to Create new RA Bill Entry
	 * 
	 * @param request
	 * @return create RA Bill Entry form.
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.CREATE_RA, method = RequestMethod.POST)
	public ModelAndView createRABill(final HttpServletRequest request,
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
		RaBillGenerationModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkOrderDto orderDto = null;
		List<TaxDefinationDto> definationDtos = new ArrayList<>();
		List<WorksRABillDto> raList = new ArrayList<>();
		List<MeasurementBookMasterDto> mbList = new ArrayList<>();
		List<Long> mbIds = new ArrayList<>();
		model.setSaveMode(MainetConstants.FlagC);
		populateModel(projId, workId);
        WorkDefinitionDto workDto = workDefinationService.findAllWorkDefinitionById(workId);
        this.getModel().setWmsDto(workDto);
		List<WorksRABillDto> billList = raBillService.getRaBillListByProjAndWorkNumber(projId, workId, orgId);
		TaxDefinationDto definationDto = null;
		List<TaxDefinationDto> taxDefinationDtos = taxDefinationService.getTaxDefinationList(orgId);
		getWithHeldorOtherTaxData(taxDefinationDtos);

		model.setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		TenderWorkDto tenderWorkDto = initiationService.findContractByWorkId(workId);
		if (tenderWorkDto.getTendTypePercent() != null) {
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(tenderWorkDto.getTendTypePercent(),
					UserSession.getCurrent().getOrganisation());

			tenderWorkDto.setAction(lookUp.getLookUpCode());
		}
		if (tenderWorkDto != null) {
			tenderWorkDto.setTenderTypeCode(CommonMasterUtility.findLookUpCode(MainetConstants.WorksManagement.VTY,
					orgId, tenderWorkDto.getTenderType()));
			model.setTndWorkDto(tenderWorkDto);
			orderDto = orderService.fetchWorkOrderByContId(tenderWorkDto.getContractId(), orgId);
			for (TaxDefinationDto dto : taxDefinationDtos) {
				if (!dto.getLookUpOtherField().isEmpty() && dto.getLookUpOtherField() != null) {
					//if (!tenderWorkDto.getVendorPanNo().isEmpty()) {
						if (dto.getCpdVendorSubType() != null && dto.getTaxPanApp().equals(MainetConstants.FlagY)
								&& tenderWorkDto.getVendorSubType() != null
								&& tenderWorkDto.getVendorSubType().longValue() == dto.getCpdVendorSubType()) {
							definationDtos.add(dto);
					} else {
						if (dto.getCpdVendorSubType() != null && dto.getTaxPanApp().equals(MainetConstants.FlagN)
								&& tenderWorkDto.getVendorSubType() != null
								&& tenderWorkDto.getVendorSubType().longValue() == dto.getCpdVendorSubType())
							definationDtos.add(dto);
					}
				} else {
					definationDtos.add(dto);
				}
			}
		}
		if (orderDto != null) {
			List<MeasurementBookMasterDto> mebList = mbService.filterMeasurementBookData(orderDto.getWorkId(),
					MainetConstants.WorksManagement.RATE_TYPE, orgId);

			if (!billList.isEmpty()) {
				for (WorksRABillDto dto : billList) {
					if (dto.getRaStatus().equals(MainetConstants.FlagA))
						raList.add(dto);
					String ids = dto.getRaMbIds();

					if (ids != null && !ids.isEmpty()) {
						String array[] = ids.split(MainetConstants.operator.COMMA);
						for (String id : array) {
							mbIds.add(Long.parseLong(id));
						}

					}
					// Defect #86738
					if (dto.getRaTaxAmt() != null) {
						BigDecimal taxAmt = dto.getRaTaxAmt();
						dto.setRaTaxAmt(taxAmt.abs());
					}
				}
				for (MeasurementBookMasterDto mbDto : mebList) {
					if (!mbIds.contains(mbDto.getWorkMbId())) {
						mbList.add(mbDto);
					}
				}
				model.setMbList(mbList);
			} else {
				model.setMbList(mebList);

			}
		}

		model.setListTaxDefinationDto(definationDtos);
		model.setOrderDto(orderDto);
		model.setRaBillList(raList);
		// D90045
		ContractMastDTO mastEntity = null;
		if (tenderWorkDto.getContractId() != null) {
			mastEntity = contractAgreementService.findById(tenderWorkDto.getContractId(), orgId);
			if (!mastEntity.getContractDetailList().isEmpty()
					&& mastEntity.getContractDetailList().get(0).getContDepDueDt() != null)
				this.getModel()
						.setContractbgDate(mastEntity.getContractDetailList().get(0).getContDepDueDt().getTime());
		}
		return new ModelAndView(MainetConstants.WorksManagement.RABILL_FORM, MainetConstants.FORM_NAME,
				this.getModel());

	}

	private void getWithHeldorOtherTaxData(List<TaxDefinationDto> taxDefinationDtos) {
		TaxDefinationDto definationDto;
		List<TbTaxMas> tdsList = tbTaxMasService
				.findAllByOrgIdAdnTaxDesc(UserSession.getCurrent().getOrganisation().getOrgid());
		tdsList.forEach(dto -> {
			TaxDefinationDto definationDt = new TaxDefinationDto();
			definationDt.setTaxId(dto.getTaxId());
			definationDt.setTaxDesc(dto.getTaxDesc());
			definationDt.setLookUpOtherField(MainetConstants.BLANK);
			taxDefinationDtos.add(definationDt);
		});
	}

	/**
	 * this method is used to Create new RA Bill Entry
	 * 
	 * @param request
	 * @return create RA Bill Entry form.
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.SEARCH_RA, method = RequestMethod.POST)
	public List<WorksRABillDto> searchRaBill(final HttpServletRequest request,
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
		RaBillGenerationModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		populateModel(projId, workId);
		List<WorksRABillDto> billList = raBillService.getRaBillListByProjAndWorkNumber(projId, workId, orgId);
		if (!billList.isEmpty()) {
			String projectName = MainetConstants.BLANK;
			if(langId == 1)
				projectName = model.getProjMasDto().getProjNameEng();
			else
				projectName = model.getProjMasDto().getProjNameReg();
			
			String workName = model.getWorkDefDto().getWorkName();
			for (WorksRABillDto dto : billList) {
				dto.setProjName(projectName);
				dto.setWorkName(workName);
				if (dto.getRaBillno() == null) {
					dto.setRaBillno(MainetConstants.BLANK);
				}
				if (dto.getRaBillDate() != null) {
					dto.setRaBillStringDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(dto.getRaBillDate()));
				} else {
					dto.setRaBillStringDate(MainetConstants.BLANK);
				}
				
				if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
					if (dto.getRaStatus().equals(MainetConstants.FlagD)) {				
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.TASK_STATUS_DRAFT);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("status.draft"));
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagB)) {				
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.WorksManagement.BILL_GEN);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("wms.ra.bill.generated"));
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagS)) {				
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.WorksManagement.SEND_FOR_APPROVAL);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("work.management.sendTool"));
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagR)) {				
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.TASK_STATUS_REJECTED);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("wms.Rejected.status"));
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagA)) {
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.TASK_STATUS_APPROVED);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("status.approved"));
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagP)) {
						if(UserSession.getCurrent().getLanguageId() == 1)
							dto.setRaStatus(MainetConstants.TASK_STATUS_PENDING);
						else
							dto.setRaStatus(ApplicationSession.getInstance().getMessage("status.pending"));
					}
				}
				else{
					if (dto.getRaStatus().equals(MainetConstants.FlagD)) {
						dto.setRaStatus(MainetConstants.TASK_STATUS_DRAFT);
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagB)) {
						dto.setRaStatus(MainetConstants.WorksManagement.BILL_GEN);
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagS)) {
						dto.setRaStatus(MainetConstants.WorksManagement.SEND_FOR_APPROVAL);
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagR)) {
						dto.setRaStatus(MainetConstants.TASK_STATUS_REJECTED);
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagA)) {
						dto.setRaStatus(MainetConstants.TASK_STATUS_APPROVED);
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagP)) {
						dto.setRaStatus(MainetConstants.TASK_STATUS_PENDING);
					}
				}
				
			}

		}
		return billList;
	}

	/**
	 * this method is used to Update RA Bill Entry
	 * 
	 * @param request
	 * @return create RA Bill Entry form.
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_RA, method = RequestMethod.POST)
	public ModelAndView updateRABill(final HttpServletRequest request,
			@RequestParam(MainetConstants.WorksManagement.RA_ID) Long raId,
			@RequestParam(MainetConstants.WorksManagement.MODE) String mode) {
		RaBillGenerationModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkOrderDto orderDto = null;
		List<TaxDefinationDto> definationDtos = new ArrayList<>();
		List<WorksRABillDto> raList = new ArrayList<>();
		List<MeasurementBookMasterDto> mbList = new ArrayList<>();
		model.setBillMasDto(raBillService.getRaBillByRaId(raId));
		List<Long> mbIds = new ArrayList<>();
		List<WorksRABillDto> billList = raBillService.getRaBillListByProjAndWorkNumber(
				model.getBillMasDto().getProjId(), model.getBillMasDto().getWorkId(), orgId);

		if (mode.equals(MainetConstants.WorksManagement.APPROVAL)) {
			model.setSaveMode(MainetConstants.FlagV);
			model.setApprovalMode(MainetConstants.WorksManagement.APPROVAL);
			model.setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
			List<WorkDefinitionDto> obj = workDefinationService.findAllWorkDefinationByProjId(orgId,
					model.getBillMasDto().getProjId());
			this.getModel().setWorkDefList(obj);
			populateModel(model.getBillMasDto().getProjId(), model.getBillMasDto().getWorkId());
		} else {
			model.setSaveMode(mode);
		}

		TaxDefinationDto definationDto = null;
		List<TaxDefinationDto> taxDefinationDtos = taxDefinationService.getTaxDefinationList(orgId);
		getWithHeldorOtherTaxData(taxDefinationDtos);

		model.setValueTypeList(CommonMasterUtility.getLookUps(MainetConstants.WorksManagement.VTY,
				UserSession.getCurrent().getOrganisation()));
		TenderWorkDto tenderWorkDto = initiationService.findContractByWorkId(model.getWorkDefDto().getWorkId());
		// Defect #86789
		if (tenderWorkDto.getTendTypePercent() != null) {
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(tenderWorkDto.getTendTypePercent(),
					UserSession.getCurrent().getOrganisation());

			tenderWorkDto.setAction(lookUp.getLookUpCode());
		}
		if (tenderWorkDto != null) {
			tenderWorkDto.setTenderTypeCode(CommonMasterUtility.findLookUpCode(MainetConstants.WorksManagement.VTY,
					orgId, tenderWorkDto.getTenderType()));
			model.setTndWorkDto(tenderWorkDto);
			orderDto = orderService.fetchWorkOrderByContId(tenderWorkDto.getContractId(), orgId);
			for (TaxDefinationDto dto : taxDefinationDtos) {
				if (!dto.getLookUpOtherField().isEmpty()) {
					if (!tenderWorkDto.getVendorPanNo().isEmpty()) {
						if (dto.getTaxPanApp().equals(MainetConstants.FlagY)
								&& tenderWorkDto.getVendorSubType().longValue() == dto.getCpdVendorSubType())
							definationDtos.add(dto);
					} else {
						if (dto.getTaxPanApp().equals(MainetConstants.FlagN)
								&& tenderWorkDto.getVendorSubType() != null && dto.getCpdVendorSubType()  != null && tenderWorkDto.getVendorSubType().longValue() == dto.getCpdVendorSubType())
							definationDtos.add(dto);
					}
				} else {
					definationDtos.add(dto);
				}
			}
		}
		if (orderDto != null) {
			List<MeasurementBookMasterDto> mebList = mbService.filterMeasurementBookData(orderDto.getWorkId(),
					MainetConstants.WorksManagement.RATE_TYPE, orgId);
			if (!billList.isEmpty() && mode.equals(MainetConstants.MODE_EDIT)) {
				for (WorksRABillDto dto : billList) {
					if (!dto.getRaStatus().equals(MainetConstants.FlagD)) {

						String ids = dto.getRaMbIds();
						if (ids != null && !ids.isEmpty()) {
							String array[] = ids.split(MainetConstants.operator.COMMA);
							for (String id : array) {
								mbIds.add(Long.parseLong(id));
							}
						}
					}
					if (dto.getRaStatus().equals(MainetConstants.FlagA))
						raList.add(dto);
				}
				for (MeasurementBookMasterDto mbDto : mebList) {
					if (!mbIds.contains(mbDto.getWorkMbId())) {
						mbList.add(mbDto);
					}
				}
			} else {
				for (Long id : model.getBillMasDto().getMbId()) {
					MeasurementBookMasterDto bookMasterDto = mbService.getMBById(id);
					bookMasterDto.setMbTakenDate(Utility.dateToString(bookMasterDto.getWorkMbTakenDate(), MainetConstants.DATE_FORMAT).toString());
					mbList.add(bookMasterDto);
				}
				if (!billList.isEmpty()) {
					for (WorksRABillDto dto : billList) {
						if (dto.getRaStatus().equals(MainetConstants.FlagA))
							raList.add(dto);
					}
				}
			}
			model.setMbList(mbList);
			for (MeasurementBookMasterDto mbDto : model.getMbList()) {
				for (Long id : model.getBillMasDto().getMbId()) {
					if (mbDto.getWorkMbId().longValue() == id) {
						mbDto.setCheckBox(true);
					}
				}
			}
			model.setOrderDto(orderDto);
		}
		// Defect #86738
		if (!billList.isEmpty()) {
			for (WorksRABillDto dto : billList) {
				// D87734
				if (dto.getRaTaxAmt() != null) {
					BigDecimal taxAmt = dto.getRaTaxAmt();
					dto.setRaTaxAmt(taxAmt.abs());
				}
			}
		}
		model.setListTaxDefinationDto(definationDtos);
		model.setRaBillList(raList);
		// D90045
		ContractMastDTO mastEntity = null;
		if (tenderWorkDto.getContractId() != null) {
			mastEntity = contractAgreementService.findById(tenderWorkDto.getContractId(), orgId);
			if (!mastEntity.getContractDetailList().isEmpty()
					&& mastEntity.getContractDetailList().get(0).getContDepDueDt() != null)
				this.getModel()
						.setContractbgDate(mastEntity.getContractDetailList().get(0).getContDepDueDt().getTime());
		}
		
		WorkDefinitionDto workDto = workDefinationService.findAllWorkDefinitionById(model.getBillMasDto().getWorkId());
        this.getModel().setWmsDto(workDto);

		return new ModelAndView(MainetConstants.WorksManagement.RABILL_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}

	public void populateModel(Long projId, Long workId) {
		
		Organisation org = UserSession.getCurrent().getOrganisation();
	    Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RaBillGenerationModel model = this.getModel();
		if (projId != null) {
			for (WmsProjectMasterDto dto : model.getProjectMasterList()) {
				if (dto.getProjId().longValue() == projId) {
					model.setProjMasDto(dto);
					break;
				}
			}
		}
		if (workId != null) {
			for (WorkDefinitionDto dto : model.getWorkDefList()) {
				if (dto.getWorkId().longValue() == workId) {
					model.setWorkDefDto(dto);
					break;
				}
			}
		}
		String status = MainetConstants.FlagA;
		String WorKsShortCode = MainetConstants.WorksManagement.WORKS_MANAGEMENT;
		final Long deptId = departmentService.getDepartmentIdByDeptCode(WorKsShortCode, status);
		model.setDeptId(deptId);
		model.setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class)
                 .getSecondaryHeadcodesForWorks(org.getOrgid()));
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.PAYMENT_ORDER, method = RequestMethod.POST)
	public ModelAndView paymentOrder(final HttpServletRequest request) {
		getModel().bind(request);
		RaBillGenerationModel model = this.getModel();

		if (this.getModel().isRaBill()) {

			for (MeasurementBookMasterDto mbDto : model.getMbList()) {
				if (mbDto.isCheckBox()) {
					model.getBillMasDto().getMbId().add(mbDto.getWorkMbId());
					List<MeasurementBookMasterDto> bookMasterDtos = mbService
							.findAbstractSheetReport(mbDto.getWorkMbId());
					this.getModel().getMeasureMentBookMastDtosList().addAll(bookMasterDtos);
				}
			}

			return new ModelAndView(MainetConstants.WorksManagement.PAYMENT_ORDER, MainetConstants.FORM_NAME,
					this.getModel());
		} else if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)){
			return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_ADMIN);
		}else {
			return new ModelAndView(MainetConstants.WorksManagement.REDIRECT_RABILL);

		}

	}

	@RequestMapping(params = MainetConstants.WorksManagement.VALIDATE_MB, method = RequestMethod.POST)
	public @ResponseBody String validateMbForRa(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String isValid = null;
		WorkOrderDto orderDto = null;
		List<MeasurementBookMasterDto> mbList = new ArrayList<>();
		List<Long> mbIds = new ArrayList<>();
		List<WorksRABillDto> billList = raBillService.getRaBillListByProjAndWorkNumber(projId, workId, orgId);
		TenderWorkDto tenderWorkDto = initiationService.findContractByWorkId(workId);
		if (tenderWorkDto != null) {
			if (tenderWorkDto.getContractId() != null)
				orderDto = orderService.fetchWorkOrderByContId(tenderWorkDto.getContractId(), orgId);

			if (orderDto != null) {
				List<MeasurementBookMasterDto> mebList = mbService.filterMeasurementBookData(orderDto.getWorkId(),
						MainetConstants.WorksManagement.RATE_TYPE, orgId);
				if (!billList.isEmpty()) {
					for (WorksRABillDto dto : billList) {
						String ids = dto.getRaMbIds();

						if (ids != null && !ids.isEmpty()) {
							String array[] = ids.split(MainetConstants.operator.COMMA);
							for (String id : array) {
								if (!mbIds.contains(Long.parseLong(id)))
									mbIds.add(Long.parseLong(id));
							}

						}

					}
					for (MeasurementBookMasterDto mbDto : mebList) {
						if (!mbIds.contains(mbDto.getWorkMbId())) {
							mbList.add(mbDto);
						}
					}
				} else {
					for (MeasurementBookMasterDto mbDto : mebList) {
						mbList.add(mbDto);
					}
				}
			}
		}
		if (!mbList.isEmpty())
			isValid = MainetConstants.FlagY;
		return isValid;
	}

	/**
	 * get Work Flow History
	 * 
	 * @param raId
	 * @param mode
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(params = MainetConstants.WorksManagement.GET_WORKFLOW_HISTORY, method = RequestMethod.POST)
	public @ResponseBody ModelAndView getWorkFlowHistory(
			@RequestParam(name = MainetConstants.WorksManagement.RA_ID) final Long raId,
			@RequestParam(name = MainetConstants.WorksManagement.MODE) final String mode, ModelMap modelMap) {

		String raCode = raBillService.getRaBillByRaId(raId).getRaCode();
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, raCode, UserSession.getCurrent().getOrganisation().getOrgid());
		List<WorkflowTaskActionWithDocs> actionHistory = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowActionService.class).getActionLogByUuidAndWorkflowId(raCode, workflowRequest.getId(),
						(short) UserSession.getCurrent().getLanguageId());
		modelMap.put(MainetConstants.WorksManagement.ACTIONS, actionHistory);
		return new ModelAndView(MainetConstants.WorksManagement.WORK_WORKFLOW_HISTORY, MainetConstants.FORM_NAME,
				modelMap);
	}

	/**
	 * check Budget Code Available or Not For Ra Bill Generation
	 * 
	 * @param request
	 * @return status true or false
	 */
	@ResponseBody
	@RequestMapping(params = "checkBudgetCode", method = RequestMethod.POST)
	public boolean checkBudgetCode(final HttpServletRequest request) {
		boolean status = false;
		WorkDefinitionDto definitionDto = workDefinationService
				.findAllWorkDefinitionById(this.getModel().getWorkDefDto().getWorkId());
		if (!definitionDto.getYearDtos().isEmpty()) {
			status = true;
		}
		return status;
	}

	// D90045 To get crrent date
	@RequestMapping(params = "getCurrentDate", method = RequestMethod.POST)
	public @ResponseBody Long getCurrentDate() {

		return new Date().getTime();
	}
	
	//TSCL - Defect #183479
	@ResponseBody
	@RequestMapping(params = "raBillCheck", method = RequestMethod.POST)
	public String raBillCheck(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId ){
		String finalBillFlag = MainetConstants.FlagN;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<WorksRABillDto> billList = raBillService.getRaBillListByProjAndWorkNumber(projId, workId, orgId);
		
		if(null!=billList && !billList.isEmpty()){
			for(WorksRABillDto dto : billList){
				if(null!=dto.getRaBillType() && dto.getRaBillType().equals(MainetConstants.FlagF)){
					if(null!=dto.getRaStatus() && dto.getRaStatus().equals(MainetConstants.FlagA)){
						finalBillFlag = MainetConstants.FlagA;
						return finalBillFlag;
					}
					else{
						finalBillFlag = MainetConstants.FlagY;
						return finalBillFlag;
					}
					
				}
			}
		}
		return finalBillFlag;
	}

}
