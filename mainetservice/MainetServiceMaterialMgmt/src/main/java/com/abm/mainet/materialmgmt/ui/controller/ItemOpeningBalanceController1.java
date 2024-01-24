package com.abm.mainet.materialmgmt.ui.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;
import com.abm.mainet.materialmgmt.dto.StoreMasterDTO;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;



@Controller
@RequestMapping("/ItemOpeningBalance1.html")
public class ItemOpeningBalanceController1 extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbItemOpeningBalance";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM = "tbItemOpeningBalance/form";
	private static final String JSP_VIEW = "tbItemOpeningBalance/view";
	private static final String JSP_LIST = "tbItemOpeningBalance/list";
	private static final String JSP_EXCELUPLOAD = "tbItemOpeningBalance/excelupload";
	private String modeView = MainetConstants.BLANK;
	@Autowired
	private IFileUploadService fileUpload;
	private List<ItemMasterDTO> chList = null;
	
	@Autowired
	private ILocationMasService iLocationMasService;

	@Resource
	private ItemMasterService itemMasterService;
	
	@Autowired
	private IStoreMasterService storeMasterservice;

	public ItemOpeningBalanceController1() {
		super(ItemOpeningBalanceController1.class, MAIN_ENTITY_NAME);
		log("ItemMasterController created.");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(params = "getGridData", method = RequestMethod.POST)
	public @ResponseBody JQGridResponse<? extends Serializable> geGridResults(final HttpServletRequest request,
			final Model model) {
		log("ItemMaster-'gridData' : 'Get grid Data'");
		final JQGridResponse response = new JQGridResponse<>();
		final int page = Integer.parseInt(request.getParameter(MainetConstants.CommonConstants.PAGE));
		response.setRows(chList);
		response.setTotal(chList.size());
		response.setRecords(chList.size());
		response.setPage(page);
		model.addAttribute(MAIN_LIST_NAME, chList);
		return response;
	}

	/*@RequestMapping(params = "getjqGridsearch")
	public @ResponseBody List getCheqData(final HttpServletRequest request, final Model model,
			@RequestParam("category") final Long category, @RequestParam("type") final Long type,
			@RequestParam("itemgroup") final Long itemgroup, @RequestParam("itemsubgroup") final Long itemsubgroup,
			@RequestParam("name") final String name) {
		log("ItemMaster-'getjqGridsearch' : 'get jqGrid search data'");

		Long orgId = null;
		orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        chList = new ArrayList<>();
        chList.clear();
        
        chList = itemMasterService.findByAllGridSearchData(category, type, itemgroup, itemsubgroup, name, orgId);
        if (chList != null && chList.size() > 0) {

			for (ItemMasterDTO bean : chList) {
				if (bean.getItemgroup() != null) {
					String itemMasterGroupDesc = MaterialManagementUtility.getLookUpDesc("ITG", 1,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemgroup());
					bean.setItemGroupDesc(itemMasterGroupDesc);
				}
				if (bean.getItemsubgroup() != null) {
					String itemMasterSubGroupDesc = MaterialManagementUtility.getLookUpDesc("ITG", 2,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemsubgroup());
					bean.setItemSubGroupDesc(itemMasterSubGroupDesc);
				}
				if (bean.getCategory() != null) {
					String itemCategoryDesc = MaterialManagementUtility.getLookUpDesc("ITC", 1,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getCategory());
					bean.setCategoryDesc(itemCategoryDesc);
				}
				if (bean.getType() != null) {
					String itemTypeDesc = CommonMasterUtility.findLookUpDesc("ITP",
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getType());
					bean.setTypeDesc(itemTypeDesc);
				}	
			}
		}
        return chList;
	}

	@RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
	public String loadBudgetReappropriationData(final ItemMasterDTO bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) throws Exception {
		log("AccountReappropriationMaster-'getjqGridload' : 'get jqGridload data'");
		String result = MainetConstants.CommonConstant.BLANK;
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		result = JSP_FORM;
		return result;
	} */

	@RequestMapping()
	public String getList(final Model model) throws Exception {
		log("ItemMaster-'list' :'list'");
		String result = MainetConstants.CommonConstant.BLANK;

		//chList = new ArrayList<>();
		//chList.clear();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		//chList = itemMasterService.findItemMasterDetailsByFinId(orgId);
        List<TbLocationMas> locList = iLocationMasService.fillAllActiveLocationByOrgId(orgId);
		List<StoreMasterDTO> storeMasterList= storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		
		/*if (chList != null && chList.size() > 0) {

			for (ItemMasterDTO bean : chList) {

				if (bean.getItemgroup() != null) {

					String itemMasterGroupDesc = MaterialManagementUtility.getLookUpDesc("ITG", 1,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemgroup());
					bean.setItemGroupDesc(itemMasterGroupDesc);

				}

				if (bean.getItemsubgroup() != null) {

					String itemMasterSubGroupDesc = MaterialManagementUtility.getLookUpDesc("ITG", 2,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemsubgroup());
					bean.setItemSubGroupDesc(itemMasterSubGroupDesc);
				}

				if (bean.getCategory() != null) {

					String itemCategoryDesc = MaterialManagementUtility.getLookUpDesc("ITC", 1,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getCategory());
					bean.setCategoryDesc(itemCategoryDesc);
				}

				if (bean.getType() != null) {

					String itemTypeDesc = CommonMasterUtility.findLookUpDesc("ITP",
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getType());
					bean.setTypeDesc(itemTypeDesc);
				}	

			}
		}*/

		final ItemOpeningBalanceDto bean = new ItemOpeningBalanceDto();
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		//model.addAttribute(MAIN_LIST_NAME, chList);
		model.addAttribute("locList", locList);
		model.addAttribute("storeList", storeMasterList);
		result = JSP_LIST;
		return result;
	}

	@RequestMapping(params = "form")
	public String formForCreate(final Model model) throws Exception {
		log("ItemMaster-'formForCreate' : 'formForCreate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final ItemOpeningBalanceDto bean = new ItemOpeningBalanceDto();
		List<StoreMasterDTO> storeMasterList= storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("storeList", storeMasterList);
		populateModel(model, bean, FormMode.CREATE);
		result = JSP_FORM;
		return result;
	}

	private void populateModel(final Model model, final ItemOpeningBalanceDto bean, final FormMode formMode) throws Exception {
		log("ItemMaster-'populateModel' : populate model");

		List<LookUp> itemMasterGroupList = CommonMasterUtility.getNextLevelData("ITG", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterGroupMap", itemMasterGroupList);

		List<LookUp> itemMasterSubGroupList = CommonMasterUtility.getNextLevelData("ITG", 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterSubGroupMap", itemMasterSubGroupList);

		/*final List<LookUp> itemTypeMap = CommonMasterUtility.getListLookup("ITP",
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemTypeMap", itemTypeMap);

		List<LookUp> itemMasterCategoryList = CommonMasterUtility.getNextLevelData("ITC", 1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterCategoryMap", itemMasterCategoryList);

		List<LookUp> itemMasterSubCategoryList = CommonMasterUtility.getNextLevelData("ITC", 2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterSubCategoryMap", itemMasterSubCategoryList);

		List<LookUp> itemMasterCategoryList3 = CommonMasterUtility.getNextLevelData("ITC", 3,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterGroupMap3", itemMasterCategoryList3);

		List<LookUp> itemMasterCategoryList4 = CommonMasterUtility.getNextLevelData("ITC", 4,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterGroupMap4", itemMasterCategoryList4);

		final List<LookUp> itemMasterUoMList = CommonMasterUtility.getListLookup("UOM",
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterUoMMap", itemMasterUoMList);

		final List<LookUp> itemMasterassetOfMasList = CommonMasterUtility.getListLookup("ASC",
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterAssetOfMasMap", itemMasterassetOfMasList);

		final List<LookUp> itemMasterExpiryMethodsList = CommonMasterUtility.getListLookup("EXP",
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterExpiryMethodsMap", itemMasterExpiryMethodsList);

		final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
				MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
		model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);

*/		model.addAttribute(MAIN_ENTITY_NAME, bean);
		if (formMode == FormMode.CREATE) {
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_CREATE);
			model.addAttribute(MainetConstants.CommonConstants.EDIT_MODE, false);
		} else if (formMode == FormMode.UPDATE) {
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE,
					MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE_UPDATE);
		}
	}

	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String create(final ItemMasterDTO tbMGItemMaster, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest httpServletRequest) throws Exception {
		log("ItemMasterBean-'create' : 'create'");
		String result = MainetConstants.CommonConstant.BLANK;
		if (!bindingResult.hasErrors()) {
			   final int langId = UserSession.getCurrent().getLanguageId();
				final Organisation org = UserSession.getCurrent().getOrganisation();
				final Employee employee = UserSession.getCurrent().getEmployee();
			tbMGItemMaster.setHasError(MainetMMConstants.MmItemMaster.FALSE);
			final UserSession userSession = UserSession.getCurrent();
			tbMGItemMaster.setOrgId(userSession.getOrganisation().getOrgid());
			tbMGItemMaster.setUserId(userSession.getEmployee().getEmpId());
			tbMGItemMaster.setLmodDate(new Date());
			tbMGItemMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
			tbMGItemMaster.setLangId(UserSession.getCurrent().getLanguageId());
			tbMGItemMaster.setLgIpMacUpd(UserSession.getCurrent().getOrganisation().getLgIpMacUpd());
			tbMGItemMaster.setUpdatedBy(UserSession.getCurrent().getOrganisation().getUpdatedBy());
			tbMGItemMaster.setUpdatedDate(UserSession.getCurrent().getOrganisation().getUpdatedDate());

			// need to remove after data comes from from start
			tbMGItemMaster.setEntryFlag("Y");
			tbMGItemMaster.setStatus("Y");
			// end

			//populateModel(model, tbMGItemMaster, FormMode.CREATE);
			ItemMasterDTO itemMasterCreated = itemMasterService.saveItemMasterFormData(tbMGItemMaster,org,langId,employee);
			if (itemMasterCreated == null) {
				itemMasterCreated = new ItemMasterDTO();
			}
			model.addAttribute(MAIN_ENTITY_NAME, itemMasterCreated);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			if (tbMGItemMaster.getItemId() == null) {
				model.addAttribute(MainetMMConstants.MmItemMaster.KEY_TEST,
						MainetMMConstants.MmItemMaster.RECORD_SAVE_SUCCESS);
			}
			if (tbMGItemMaster.getItemId() != null) {
				model.addAttribute(MainetMMConstants.MmItemMaster.KEY_TEST,
						MainetMMConstants.MmItemMaster.RECORD_UPDATE_SUCCESS);
			}
			result = JSP_FORM;
		} else {
			tbMGItemMaster.setHasError(MainetMMConstants.MmItemMaster.TRUE);
			model.addAttribute(MainetMMConstants.MmItemMaster.MODVIEW, getModeView());
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			//populateModel(model, tbMGItemMaster, FormMode.CREATE);
			result = JSP_FORM;
		}
		return result;
	}

	/*@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(ItemMasterDTO tbMGItemMaster, @RequestParam("itemid") final Long itemid,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws Exception {
		log("tbMGItemMaster-'gridData' : 'update'");
		String result = MainetConstants.CommonConstant.BLANK;

		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.EDIT)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.EDIT);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			UserSession.getCurrent().getOrganisation();
			UserSession.getCurrent().getLanguageId();
			tbMGItemMaster.setItemId(itemid);
			final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
					MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX,
					UserSession.getCurrent().getOrganisation());
			
			 * final String cpdIdStatusFlag = tbMGItemMaster.getCpdIdStatusFlag(); String
			 * lookUpCode = null; for (final LookUp lookUp : activeDeActiveMap) { lookUpCode
			 * = lookUp.getLookUpCode(); if (lookUpCode.equals(cpdIdStatusFlag)) {
			 * tbMGItemMaster.setCpdIdStatusFlagDup(lookUp.getLookUpCode()); } }
			 

			model.addAttribute(MAIN_ENTITY_NAME, tbMGItemMaster);
			populateModel(model, tbMGItemMaster, FormMode.UPDATE);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("BudgetCodeMaster 'update' : update done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("BudgetCodeMaster 'update' : update done - redirect");
			result = JSP_FORM;
		} else {
			log("BudgetCodeMaster 'update' : binding errors");
			populateModel(model, tbMGItemMaster, FormMode.UPDATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(ItemMasterDTO tbMGItemMaster, @RequestParam("itemid") final Long itemid,
			@RequestParam(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE) final String viewmode,
			final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
			final HttpServletRequest httpServletRequest) throws Exception {
		log("tbMGItemMaster-'gridData' : 'view'");
		String result = MainetConstants.CommonConstant.BLANK;

		if (!bindingResult.hasErrors()) {
			if (viewmode.equals(MainetConstants.VIEW)) {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.VIEW);
			} else {
				model.addAttribute(MainetConstants.VIEW_MODE, MainetConstants.FAILED);
			}
			setModeView(viewmode);
			tbMGItemMaster.setItemid(itemid);
			final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
					MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX,
					UserSession.getCurrent().getOrganisation());
			/*
			 * final String cpdIdStatusFlag = tbMGItemMaster.getCpdIdStatusFlag(); String
			 * lookUpCode = null; for (final LookUp lookUp : activeDeActiveMap) { lookUpCode
			 * = lookUp.getLookUpCode(); if (lookUpCode.equals(cpdIdStatusFlag)) {
			 * tbMGItemMaster.setCpdIdStatusFlagDup(lookUp.getLookUpCode()); } }
			 */
   /*
			 model.addAttribute(MAIN_ENTITY_NAME, tbMGItemMaster);
			populateModel(model, tbMGItemMaster, FormMode.VIEW);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("BudgetCodeMaster 'view' : view done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("BudgetCodeMaster 'view' : view done - redirect");
			result = JSP_VIEW;
		} else {
			log("BudgetCodeMaster 'view' : binding errors");
			populateModel(model, tbMGItemMaster, FormMode.VIEW);
			result = JSP_VIEW;
		}
		return result;
	}

	@RequestMapping(params = "importExportExcelTemplateData", method = RequestMethod.POST)
	public String exportImportExcelTemplate(final Model model) throws Exception {
		log("ItemMaster-'exportImportExcelTemplate' : 'exportImportExcelTemplate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final ItemMasterDTO bean = new ItemMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
		fileUpload.sessionCleanUpForFileUpload();
		result = JSP_EXCELUPLOAD;
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUpload")
	public @ResponseBody JsonViewObject uploadDocument(final HttpServletRequest httpServletRequest,
			final HttpServletResponse response, final String fileCode, @RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final MultipartHttpServletRequest request = (MultipartHttpServletRequest) httpServletRequest;
		final JsonViewObject jsonViewObject = FileUploadUtility.getCurrent().doFileUpload(request, fileCode,
				browserType);
		return jsonViewObject;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileUploadValidatn")
	public @ResponseBody List<JsonViewObject> doFileUploadValidatn(final HttpServletRequest httpServletRequest,
			@RequestParam final String browserType) {
		UserSession.getCurrent().setBrowserType(browserType);
		final List<JsonViewObject> result = FileUploadUtility.getCurrent().getFileUploadList();
		return result;
	}

	@RequestMapping(method = RequestMethod.POST, params = "doFileDeletion")
	public @ResponseBody JsonViewObject doFileDeletion(@RequestParam final String fileId,
			final HttpServletRequest httpServletRequest, @RequestParam final String browserType,
			@RequestParam(name = "uniqueId", required = false) final Long uniqueId) {
		UserSession.getCurrent().setBrowserType(browserType);
		JsonViewObject jsonViewObject = JsonViewObject.successResult();
		jsonViewObject = FileUploadUtility.getCurrent().deleteFile(fileId);
		return jsonViewObject;
	}
*/
	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}

}
