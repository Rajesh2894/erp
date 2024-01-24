package com.abm.mainet.materialmgmt.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDetDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionDto;
import com.abm.mainet.materialmgmt.dto.PurchaseRequistionYearDetDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.IPurchaseRequistionService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.ui.model.PurchaseRequistionModel;

@Controller
@RequestMapping(value = { "/PurchaseRequisition.html", "/PurchaseRequisitionApproval.html" })
public class PurchaseRequisitionController extends AbstractFormController<PurchaseRequistionModel> {

	private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private IPurchaseRequistionService purchaseRequistionService;

	@Autowired
	private ILocationMasService locationMasService;
	
	@Resource
	private DepartmentService departmentService;

	@Resource
	private SecondaryheadMasterService secondaryheadMasterService;
	
	@Resource
	private TbFinancialyearService financialyearService;
	
	@Resource
	private IWorkflowTaskService workflowTaskService;

	
	@RequestMapping(method = { RequestMethod.POST , RequestMethod.GET})
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setPurchaseRequistionList(purchaseRequistionService.purchaseReqSearchForSummaryData(null,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		loadSummaryData(this.getModel().getPurchaseRequistionList());
		this.getModel().setCommonHelpDocs("PurchaseRequisition.html");
		return index();
	}

	private void loadSummaryData(List<PurchaseRequistionDto> requistionDtoList) {
		this.getModel().setStoreIdNameList(storeMasterservice.getStoreIdAndNameList(UserSession.getCurrent().getOrganisation().getOrgid()));
		Map<Long, String> storeMap = new HashMap<>();
		this.getModel().getStoreIdNameList().forEach(storeObject -> storeMap.put(Long.parseLong(storeObject[0].toString()), storeObject[1].toString()));
		requistionDtoList.forEach(master -> master.setDepartmentName(storeMap.get(master.getStoreId())));
		this.getModel().setPurchaseRequistionList(requistionDtoList);
	}

	@RequestMapping(params = "addPurRequisition", method = RequestMethod.POST)
	public ModelAndView addRouteMaster(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setFormMode(MainetConstants.MODE_CREATE);
		getListsForPurchaseRequisition();
		return new ModelAndView("addPurRequisition/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	
	private void getListsForPurchaseRequisition() {
		long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if(MainetConstants.MODE_CREATE.equals(this.getModel().getFormMode())) {
			this.getModel().setStoreIdNameList(storeMasterservice.getActiveStoreObjectListForAdd(orgId));
			this.getModel().setItemIdNameList(itemMasterService.getActiveItemIdNameListByOrgId(orgId));
		} else {
			this.getModel().setStoreIdNameList(storeMasterservice.getStoreIdAndNameList(orgId));
			this.getModel().setItemIdNameList(itemMasterService.getItemIdNameListByOrgId(orgId));
		}
		final List<TbFinancialyear> finYearList =financialyearService.findAllFinancialYearByOrgId(UserSession.getCurrent().getOrganisation());
		this.getModel().getFaYears().clear();
		if (finYearList != null && !finYearList.isEmpty()) {
			finYearList.forEach(finYearTemp -> {
				try {
					finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
					this.getModel().getFaYears().add(finYearTemp);
				} catch (Exception ex) {
					throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
				}
			});
			Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo, Comparator.reverseOrder());
			Collections.sort(this.getModel().getFaYears(), comparing);
		}
		
		LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
		if (defaultVal != null) {
			this.getModel().setCpdMode(CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE).getLookUpCode());
			if (this.getModel().getCpdMode().equals(MainetConstants.FlagL))
				this.getModel().setBudgetList(secondaryheadMasterService.getSecondaryHeadcodesForWorks(orgId));
		} else
			this.getModel().setCpdMode(null);
	}
	
	
	@RequestMapping(params = "getStoredata", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public StoreMasterDTO getStoreData(@RequestParam("storeId") Long storeId, final HttpServletRequest httpServletRequest) {
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(storeId);
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), storeMasterDTO.getOrgId()));
		this.getModel().setLocationId(storeMasterDTO.getLocation());
		return storeMasterDTO;
	}

	@RequestMapping(params = "getUomdata", method = { RequestMethod.POST }, produces = "application/json")
	@ResponseBody
	public String getUomData(@RequestParam("itemId") Long itemId, final HttpServletRequest httpServletRequest) {
		return CommonMasterUtility.getNonHierarchicalLookUpObject(
				itemMasterService.getUomByitemCode(UserSession.getCurrent().getOrganisation().getOrgid(), itemId),
				UserSession.getCurrent().getOrganisation()).getLookUpDesc();
	}
	

	@RequestMapping(params = "purchaseReqSaveForm", method = RequestMethod.POST)
	public @ResponseBody String saveApprovalPurchaseOrderForm(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getPurchaseRequistionDto().setWfFlag(MainetConstants.MENU.TRUE);
		this.getModel().getPurchaseRequistionDto().setStatus(MainetConstants.FlagP);
		this.getModel().saveForm();
		return this.getModel().getPurchaseRequistionDto().getPrNo();
	}


	@ResponseBody
	@RequestMapping(params = "searchPurchaseRequisition", method = RequestMethod.POST)
	public ModelAndView searchPurchaseData(final HttpServletRequest request,
			@RequestParam(required = false) Long storeNameId, @RequestParam(required = false) String prnoId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate) {
		sessionCleanup(request);
		this.getModel().setPurchaseRequistionList(purchaseRequistionService.purchaseReqSearchForSummaryData(storeNameId,
				prnoId, fromDate, toDate, UserSession.getCurrent().getOrganisation().getOrgid()));
		loadSummaryData(this.getModel().getPurchaseRequistionList());
		this.getModel().getPurchaseRequistionDto().setStoreId(storeNameId);
		this.getModel().getPurchaseRequistionDto().setPrNo(prnoId);
		this.getModel().getPurchaseRequistionDto().setFromDate(fromDate);
		this.getModel().getPurchaseRequistionDto().setToDate(toDate);
		return new ModelAndView("searcPurchaseRequisition", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@ResponseBody
	@RequestMapping(params = "viewPurchaseRequisition", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPurchaseData(final HttpServletRequest request, @RequestParam("prId") Long prId,
			@RequestParam(name = "redirectTender", required = false) String redirectTender) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PurchaseRequistionDto purchaseRequistionDto = purchaseRequistionService.purchaseReqSearchForEditAndView(prId, orgId);
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(purchaseRequistionDto.getStoreId());
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), orgId));
		purchaseRequistionDto.setDepartmentName(storeMasterDTO.getStoreInchargeName());
		purchaseRequistionDto.setRequestedName(storeMasterDTO.getLocationName());
		if (!StringUtils.isBlank(redirectTender)) {
			this.getModel().setIsTenderViewEstimate(MainetConstants.Y_FLAG);
		}
		this.getModel().setPurchaseRequistionDto(purchaseRequistionDto);
		List<PurchaseRequistionYearDetDto> purchaseRequistionYearDetDtos = purchaseRequistionService.getAllYearDetEntity(orgId, purchaseRequistionDto.getPrId());
		this.getModel().getPurchaseRequistionDto().setYearDto(purchaseRequistionYearDetDtos);	
		getListsForPurchaseRequisition();
		return new ModelAndView("viewPurchaseRequisition", MainetConstants.FORM_NAME, this.getModel());
	}


	@ResponseBody
	@RequestMapping(params = "editPurchaseRequisition", method = RequestMethod.POST)
	public ModelAndView editPurchaseData(final HttpServletRequest request, @RequestParam("prId") Long prId) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		PurchaseRequistionDto purchaseRequistionDto = purchaseRequistionService.purchaseReqSearchForEditAndView(prId, orgId);
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(purchaseRequistionDto.getStoreId());
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), orgId));
		purchaseRequistionDto.setDepartmentName(storeMasterDTO.getStoreInchargeName());
		purchaseRequistionDto.setRequestedName(storeMasterDTO.getLocationName());
		this.getModel().setPurchaseRequistionDto(purchaseRequistionDto);
		List<PurchaseRequistionYearDetDto> purchaseRequistionYearDetDtos = purchaseRequistionService.getAllYearDetEntity(orgId, purchaseRequistionDto.getPrId());
		this.getModel().getPurchaseRequistionDto().setYearDto(purchaseRequistionYearDetDtos);
		getListsForPurchaseRequisition();
		return new ModelAndView("addPurRequisition/Form", MainetConstants.FORM_NAME, this.getModel());
	}
	

	@ResponseBody
	@RequestMapping(params = "deletePurReqisition", method = RequestMethod.POST)
	public ModelAndView deleteStoreForm(final HttpServletRequest request, @RequestParam("prId") Long prId) {
		sessionCleanup(request);
		purchaseRequistionService.purchaseReqFordelete(prId, UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setPurchaseRequistionList(purchaseRequistionService.purchaseReqSearchForSummaryData(null,
				null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		loadSummaryData(this.getModel().getPurchaseRequistionList());
		return new ModelAndView("searcPurchaseRequisition", MainetConstants.FORM_NAME, this.getModel());
	}


	/**
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView disposalFormApproval(@RequestParam("appNo") final String appNo, @RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		PurchaseRequistionModel purchaseRequistionModel = this.getModel();
		purchaseRequistionModel.setTaskId(actualTaskId);
		purchaseRequistionModel.getWorkflowActionDto().setReferenceId(appNo);
		purchaseRequistionModel.getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseRequistionDto(purchaseRequistionService.getPurchaseReuisitonByPONumber(appNo, orgId));
		httpServletRequest.setAttribute("list",	employeeService.findEmpList(orgId));
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(this.getModel().getPurchaseRequistionDto().getStoreId());
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), orgId));
		this.getModel().getPurchaseRequistionDto().setDepartmentName(storeMasterDTO.getStoreInchargeName());
		this.getModel().getPurchaseRequistionDto().setRequestedName(storeMasterDTO.getLocationName());
		getModel().setLevelCheck(workflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		List<PurchaseRequistionYearDetDto> purchaseRequistionYearDetDtos = purchaseRequistionService
				.getAllYearDetEntity(orgId, this.getModel().getPurchaseRequistionDto().getPrId());
		this.getModel().getPurchaseRequistionDto().setYearDto(purchaseRequistionYearDetDtos);
		getListsForPurchaseRequisition();
		return new ModelAndView("addPurRequisition/Form", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(params = "printPurchaseRequisition", method = RequestMethod.POST)
	public ModelAndView printSanitationStaffTarget(final HttpServletRequest request, @RequestParam("prNo") String prNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long prId = purchaseRequistionService.getPrIdById(prNo, orgId);
		PurchaseRequistionDto purchaseRequistionDto = purchaseRequistionService.purchaseReqSearchForEditAndView(prId, orgId);
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(purchaseRequistionDto.getStoreId());
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), orgId));
		purchaseRequistionDto.setDepartmentName(storeMasterDTO.getStoreInchargeName());
		purchaseRequistionDto.setRequestedName(storeMasterDTO.getLocationName());
		this.getModel().setItemNameList(itemMasterService.findItemMasterDetailsByOrgId(orgId));
		Map<Long, String> itemMap = this.getModel().getItemNameList().stream()
				.collect(Collectors.toMap(ItemMasterDTO::getItemId, ItemMasterDTO::getName));

		purchaseRequistionDto.getPurchaseRequistionDetDtoList().forEach(master -> {
			master.setItemName(itemMap.get(master.getItemId()));
			Long UomId = purchaseRequistionService.getUomByUsingItemCode(orgId, master.getItemId());			
			final List<LookUp> itemMasterUoMList = CommonMasterUtility.getListLookup("UOM", UserSession.getCurrent().getOrganisation());
			for (LookUp itemMasterUoM : itemMasterUoMList) {
				if (UomId.equals(itemMasterUoM.getLookUpId()))
					master.setUonName(itemMasterUoM.getDescLangFirst());
			}
		});
		this.getModel().setStoreNameList(
				storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
		Map<Long, String> storeMap = this.getModel().getStoreNameList().stream()
				.collect(Collectors.toMap(StoreMasterDTO::getStoreId, StoreMasterDTO::getStoreName));
		String storeName = null;
		for (Map.Entry<Long, String> storeMaps : storeMap.entrySet()) {
			if (purchaseRequistionDto.getStoreId().equals(storeMaps.getKey())) {
				storeName = storeMaps.getValue();
			}
		}
		purchaseRequistionDto.setStoreName(storeName);
		this.getModel().setPurchaseRequistionDto(purchaseRequistionDto);
		return new ModelAndView("PrintPurchaseRequisitionForm", MainetConstants.FORM_NAME, this.getModel());
	}


	/**
	 * SLI Prefix Check Live Mode or Setting Mode
	 * 
	 * @param httpServletRequest
	 * @return default Value
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.WorkDefination.DEFAUL_SLI_CHECK, method = RequestMethod.POST)
	public String checkForDefaultSLI(final HttpServletRequest httpServletRequest) {
		String defaultVal = MainetConstants.Common_Constant.NO;
		if (null != CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE))
			defaultVal = MainetConstants.Common_Constant.YES;
		return defaultVal;
	}

	@RequestMapping(method = RequestMethod.POST, params = { "doYearDeletion" })
	@ResponseBody
	public boolean doItemDeletionYears(@RequestParam(name = "id", required = true) Long id, final HttpServletRequest request) {
		bindModel(request);
		List<PurchaseRequistionYearDetDto> items = this.getModel().getPurchaseRequistionDto().getYearDto();
		List<PurchaseRequistionYearDetDto> beanList = items.stream().filter(c -> c.getYearId() != id).collect(Collectors.toList());
		if (!beanList.isEmpty())
			this.getModel().getPurchaseRequistionDto().setYearDto(beanList);
		return true;
	}

	@RequestMapping(method = RequestMethod.POST, params = { "doDetailsDeletion" })
	@ResponseBody
	public boolean doItemDeletionDetails(@RequestParam(name = "id", required = true) Long id, final HttpServletRequest request) {
		bindModel(request);
		List<PurchaseRequistionDetDto> item = this.getModel().getPurchaseRequistionDto().getPurchaseRequistionDetDtoList();
		List<PurchaseRequistionDetDto> beanList = item.stream().filter(c -> c.getPrdetId().longValue() != id.longValue()).collect(Collectors.toList());
		if (!beanList.isEmpty())
			this.getModel().getPurchaseRequistionDto().setPurchaseRequistionDetDtoList(beanList);
		return true;
	}

	@ResponseBody
	@RequestMapping(params = "getBudgetHeadDetails", method = RequestMethod.POST)
	public VendorBillApprovalDTO checkBudgetHeadDetails(@RequestParam("sacHeadId") final Long sacHeadId,
			@RequestParam("faYearId") final Long faYearId, @RequestParam("yeBugAmount") final BigDecimal yeBugAmount,
			@RequestParam(value = "fieldId", required = false) Long fieldId,
			@RequestParam(value = "dpDeptId", required = false) final Long dpDeptId) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
		budgetHeadDTO.setBillAmount(yeBugAmount);
		budgetHeadDTO.setDepartmentId(dpDeptId);
		budgetHeadDTO.setFaYearid(faYearId);
		budgetHeadDTO.setBudgetCodeId(sacHeadId);
		budgetHeadDTO.setOrgId(orgId);
		budgetHeadDTO.setDepartmentId(departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.STORE));
		budgetHeadDTO.setFieldId(locationMasService.findFieldIdByLocationId(this.getModel().getLocationId(), orgId));

		VendorBillApprovalDTO dto = purchaseRequistionService.getBudgetExpenditureDetails(budgetHeadDTO);
		if (dto != null) {
			dto.setBillAmount(yeBugAmount.setScale(2, RoundingMode.UP));
			dto.setAuthorizationStatus(MainetConstants.FlagY);
			if (dto.getInvoiceAmount().subtract(dto.getSanctionedAmount()).compareTo(yeBugAmount) < 0)
				dto.setDisallowedRemark(MainetConstants.FlagY);
		} else {
			dto = new VendorBillApprovalDTO();
			dto.setAuthorizationStatus(MainetConstants.FlagN);
		}
		return dto;
	}
	
	
	@Override
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long actualTaskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		this.sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseRequistionDto(purchaseRequistionService.getPurchaseReuisitonByPONumber(applicationId, orgId));		
		httpServletRequest.setAttribute("list",	employeeService.findEmpList(orgId));
		StoreMasterDTO storeMasterDTO = storeMasterservice.getStoreMasterByStoreId(this.getModel().getPurchaseRequistionDto().getStoreId());
		storeMasterDTO.setStoreInchargeName(employeeService.getEmpNameByEmpId(storeMasterDTO.getStoreIncharge()));
		storeMasterDTO.setLocationName(iLocationMasService.getLocationNameById(storeMasterDTO.getLocation(), orgId));
		this.getModel().getPurchaseRequistionDto().setDepartmentName(storeMasterDTO.getStoreInchargeName());
		this.getModel().getPurchaseRequistionDto().setRequestedName(storeMasterDTO.getLocationName());
		this.getModel().getPurchaseRequistionDto().setYearDto(purchaseRequistionService.getAllYearDetEntity(orgId,
				this.getModel().getPurchaseRequistionDto().getPrId()));
		getListsForPurchaseRequisition();
		getModel().setLevelCheck(workflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().setSaveMode(MainetConstants.VIEW);		
		return new ModelAndView("viewPurchaseRequisition", MainetConstants.FORM_NAME, this.getModel());
	}
	

}
