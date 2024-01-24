package com.abm.mainet.materialmgmt.ui.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.ExpiryItemsDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.ExpirtyItemService;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.ExpiryItemsEntryModel;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;

@Controller
@RequestMapping(value = { "/DisposalOfStock.html", "/DisposalApproval.html" })
public class ExpiryItemsEntryController extends AbstractFormController<ExpiryItemsEntryModel> {

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private ExpirtyItemService expirtyItemService;

	@Autowired
	private TbAcVendormasterService vendorService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private ItemMasterService itemMasterService;

	@Autowired
	private IBinMasService binMasService;
	
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Resource
	private BankMasterService bankMasterService;
	
	@Resource
	private TbBankmasterService banksMasterService;
	
	private static final String TB_SERVICE_RECEIPTMAS_BEAN = "tbServiceReceiptMasBean";



	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setExpiryItemDtoList(expirtyItemService.expiryItemSearchForSummaryData(null, null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		getListsOnForm(model);
		this.getModel().setCommonHelpDocs("ExpiryItem.html");
		return index();
	}


	@RequestMapping(params = "addDisposalOfStock", method = RequestMethod.POST)
	public ModelAndView addRouteMaster( final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		getListsOnForm(model);
		return new ModelAndView("addDisposalOfStock/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@ResponseBody
	@RequestMapping(params = "viewDisposalOfStock", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView viewExpiryItem(final HttpServletRequest request, @RequestParam(required = false) Long expiryId,
			@RequestParam(name="redirectTender",required=false) String redirectTender) {
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);		
		this.getModel().setExpiryItemsDto(expirtyItemService.getExpiryItemDataById(expiryId, UserSession.getCurrent().getOrganisation().getOrgid() ));
		loadVendor();
		if(!StringUtils.isBlank(redirectTender))
	        this.getModel().setIsTenderViewEstimate(MainetConstants.Y_FLAG);
		return new ModelAndView("disposalProcessApproval", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "disposalReceiptEntryForm", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView disposalReceiptEntry(final HttpServletRequest request, @RequestParam("expiryId") Long expiryId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		this.getModel().setExpiryItemsDto(expirtyItemService.getExpiryItemDataById(expiryId, UserSession.getCurrent().getOrganisation().getOrgid() ));
		loadVendor();
		return new ModelAndView("disposalProcessApproval", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("expiryId") Long expiryId,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		getModel().setReceiptDTO(expirtyItemService.getDuplicateReceiptDetail(expiryId, UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("ChallanAtULBReceiptPrint", MainetConstants.FORM_NAME, getModel());
	}
	
	
	/** to get data of expired items in Expire add form */
	@ResponseBody
	@RequestMapping(params = "getExpiredItemsFromStore", method = RequestMethod.POST)
	public ModelAndView searchPurchaseData(final HttpServletRequest request, @RequestParam(required = false) Date expiryDate,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) Date movementDate )throws ParseException {
		bindModel(request);	
		this.getModel().setExpiryItemsDto(expirtyItemService.getExpiredItemsDataforExpiry(storeId, expiryDate, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getExpiryItemsDto().setStoreId(storeId);
		this.getModel().getExpiryItemsDto().setExpiryCheck(expiryDate);
		this.getModel().getExpiryItemsDto().setMovementDate(movementDate);
		return new ModelAndView("getExpiryItemForm", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@ResponseBody
	@RequestMapping(params = "searchDisposalItem", method = RequestMethod.POST)
	public ModelAndView searchExpiryData(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) String movementNo, 
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate) throws ParseException {
		sessionCleanup(request);
		this.getModel().setStoreNameList(storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setExpiryItemDtoList(expirtyItemService.expiryItemSearchForSummaryData(storeId, movementNo,
				fromDate, toDate, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getExpiryItemsDto().setStoreId(storeId);
		this.getModel().getExpiryItemsDto().setMovementNo(movementNo);
		this.getModel().getExpiryItemsDto().setFromDate(fromDate);
		this.getModel().getExpiryItemsDto().setToDate(toDate);
		getListsOnForm(model);
		return new ModelAndView("expiryItemsEntrySerch", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	private void getListsOnForm(Model model) {
		this.getModel().setStoreNameList(storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setEmpList(employeeService.getActiveEmployeeList(UserSession.getCurrent().getOrganisation().getOrgid()));	
	}
	
	
	private String replaceNull(Object name) {
		if (name == null) {
			name = MainetConstants.BLANK;
		}
		return name.toString();
	}
	
	
	private void loadVendor() {
		final Long vendorStatus = CommonMasterUtility
				.getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS).getLookUpId();
		this.getModel().setVendors(vendorService
				.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
	}


	@ResponseBody
	@RequestMapping(params = "printDisposalOfStock", method = RequestMethod.POST)
	public ModelAndView printDisposalOfStock(final HttpServletRequest request,
			@RequestParam(required = false) Long expiryId) {
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setStoreNameList(storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setItemNameList(itemMasterService.findItemMasterDetailsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().setExpiryItemsDto(expirtyItemService.getExpiryItemDataById(expiryId, UserSession.getCurrent().getOrganisation().getOrgid() ));

		this.getModel().setBinLocList(binMasService.findAllBinLocation(UserSession.getCurrent().getOrganisation().getOrgid()));

		Map<Long, String> binLocMap = this.getModel().getBinLocList().stream()
				.collect(Collectors.toMap(BinLocMasDto::getBinLocId, BinLocMasDto::getLocationName));
		Map<Long, String> storeMap = this.getModel().getStoreNameList().stream()
				.collect(Collectors.toMap(StoreMasterDTO::getStoreId, StoreMasterDTO::getStoreName));
		String storeName = null;
		for (Map.Entry<Long, String> storeMaps : storeMap.entrySet()) {
			if (this.getModel().getExpiryItemsDto().getStoreId().equals(storeMaps.getKey())) {
				storeName = storeMaps.getValue();
			}
		}
		List<Employee> empList = employeeService.findEmpList(UserSession.getCurrent().getOrganisation().getOrgid());
		for (Employee emp : empList) {
			if (this.getModel().getExpiryItemsDto().getMovementBy().equals(emp.getEmpId())) {
				this.getModel().getExpiryItemsDto().setMovementByName(emp.getEmpname() + " " + emp.getEmplname());
			}
		}
		this.getModel().getExpiryItemsDto().setStoreName(storeName);
		Map<Long, String> itemMap = this.getModel().getItemNameList().stream()
				.collect(Collectors.toMap(ItemMasterDTO::getItemId, ItemMasterDTO::getName));
		Map<Long, Long> uomMap = this.getModel().getItemNameList().stream()
				.collect(Collectors.toMap(ItemMasterDTO::getItemId, ItemMasterDTO::getUom));
		final List<LookUp> itemMasterUoMList = CommonMasterUtility.getListLookup("UOM",
				UserSession.getCurrent().getOrganisation());
		this.getModel().getExpiryItemsDto().getExpiryItemDetailsDtoList().forEach(master -> {
			master.setItemName(itemMap.get(master.getItemId()));
			Long uomId = uomMap.get(master.getItemId());
			for (LookUp itemMasterUoM : itemMasterUoMList) {
				if (uomId.equals(itemMasterUoM.getLookUpId())) {
					master.setUomName(itemMasterUoM.getDescLangFirst());
				}
			}
			master.setBinLocName(binLocMap.get(master.getBinLocation()));
		});
		return new ModelAndView("printDisposalOfStock/Form", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView disposalFormApproval(@RequestParam("appNo") final Long appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		ExpiryItemsEntryModel expiryItemsEntryModel = this.getModel();
		expiryItemsEntryModel.setTaskId(actualTaskId);
		expiryItemsEntryModel.getWorkflowActionDto().setApplicationId(appNo);
		expiryItemsEntryModel.getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId));
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().setExpiryItemsDto(expirtyItemService.getExpiryItemDataByApplicationNo(appNo, orgId));
		getListsOnForm(model);
		if(this.getModel().isLastChecker()) {
			loadVendor();
		}
		return new ModelAndView("disposalProcessApproval", MainetConstants.FORM_NAME, this.getModel());
	}



}
