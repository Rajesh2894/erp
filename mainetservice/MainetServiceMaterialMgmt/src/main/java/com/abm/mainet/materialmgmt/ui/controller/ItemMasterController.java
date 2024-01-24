package com.abm.mainet.materialmgmt.ui.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.FormMode;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.ReadExcelData;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.ui.controller.telosys.AbstractController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.upload.excel.WriteExcelData;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Message;
import com.abm.mainet.common.utility.MessageType;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.materialmgmt.dto.ItemMasterUploadDTO;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.ui.validator.ItemMasterValidator;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Controller
@RequestMapping("/ItemMaster.html")
public class ItemMasterController extends AbstractController {

	private static final String MAIN_ENTITY_NAME = "tbMGItemMaster";
	private static final String MAIN_LIST_NAME = "list";
	private static final String JSP_FORM = "tbMGItemMaster/form";
	private static final String JSP_VIEW = "tbMGItemMaster/view";
	private static final String JSP_LIST = "tbMGItemMaster/list";
	private static final String JSP_EXCELUPLOAD = "tbMGItemMaster/excelupload";
	private String modeView = MainetConstants.BLANK;
	@Autowired
	private IFileUploadService fileUpload;
	private List<ItemMasterDTO> chList = null;

	@Resource
	private ItemMasterService itemMasterService;
	
	@Resource
    private TbOrganisationService tbOrganisationService;

	public ItemMasterController() {
		super(ItemMasterController.class, MAIN_ENTITY_NAME);
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

	@RequestMapping(params = "getjqGridsearch")
	public @ResponseBody List<ItemMasterDTO> getItemMasterDetails(final HttpServletRequest request, final Model model,
			@RequestParam("category") final Long category, @RequestParam("type") final Long type,
			@RequestParam("itemgroup") final Long itemgroup, @RequestParam("itemsubgroup") final Long itemsubgroup,
			@RequestParam("name") final String name) {
		log("ItemMaster-'getjqGridsearch' : 'get jqGrid search data'");

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		chList = new ArrayList<>();
		chList.clear();
		chList = itemMasterService.findByAllGridSearchData(category, type, itemgroup, itemsubgroup, name, orgId);
		chList =processItemDetails(chList);
		return chList;
	}

	@RequestMapping(params = "getjqGridload", method = RequestMethod.POST)
	public String loadItemMasterApproriateData(final ItemMasterDTO bean, final Model model,
			final RedirectAttributes redirectAttributes, final HttpServletRequest request,
			final BindingResult bindingResult) throws Exception {
		log("AccountReappropriationMaster-'getjqGridload' : 'get jqGridload data'");
		String result = MainetConstants.CommonConstant.BLANK;
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		result = JSP_FORM;
		return result;
	}

	@RequestMapping()
	public String getList(final Model model) throws Exception {
		log("ItemMaster-'list' :'list'");
		String result = MainetConstants.CommonConstant.BLANK;
		chList = new ArrayList<>();
		chList.clear();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		chList = itemMasterService.findItemMasterDetailsByOrgId(orgId);
		chList = processItemDetails(chList);
		final ItemMasterDTO bean = new ItemMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
		model.addAttribute(MAIN_ENTITY_NAME, bean);
		model.addAttribute(MAIN_LIST_NAME, chList);
		result = JSP_LIST;
		return result;
	}

	@RequestMapping(params = "form")
	public String formForCreate(final Model model) throws Exception {
		log("ItemMaster-'formForCreate' : 'formForCreate'");
		String result = MainetConstants.CommonConstant.BLANK;
		final ItemMasterDTO bean = new ItemMasterDTO();
		populateModel(model, bean, FormMode.CREATE);
		result = JSP_FORM;
		return result;
	}

	private void populateModel(final Model model, final ItemMasterDTO bean, final FormMode formMode) throws Exception {
		log("ItemMaster-'populateModel' : populate model");

		List<LookUp> itemMasterGroupList = CommonMasterUtility.getNextLevelData(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL1,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterGroupMap", itemMasterGroupList);

		List<LookUp> itemMasterSubGroupList = CommonMasterUtility.getNextLevelData(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL2,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("itemMasterSubGroupMap", itemMasterSubGroupList);

		final List<LookUp> itemTypeMap = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_TYPE_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemTypeMap", itemTypeMap);

		List<LookUp> itemMasterCategoryList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_CATEGORY_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterCategoryMap", itemMasterCategoryList);

		final List<LookUp> itemMasterUoMList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.UOM_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterUoMMap", itemMasterUoMList);

		final List<LookUp> itemMasterassetOfMasList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_CLASSIFICATION_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterAssetOfMasMap", itemMasterassetOfMasList);

		final List<LookUp> itemMasterExpiryMethodsList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_EXPIRY_TYPE_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterExpiryMethodsMap", itemMasterExpiryMethodsList);
		
		final List<LookUp> itemMasterValuationMethodsList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_VALUATION_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterValuationMethodsMap", itemMasterValuationMethodsList);
		
		final List<LookUp> itemMasterMgmtMethodsList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX,
				UserSession.getCurrent().getOrganisation());
		model.addAttribute("itemMasterMgmtMethodsMap", itemMasterMgmtMethodsList);

		final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
				MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
		model.addAttribute(MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_MAP, activeDeActiveMap);

		model.addAttribute(MAIN_ENTITY_NAME, bean);
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
		ItemMasterValidator validator =new ItemMasterValidator();
		validator.inputValidation(tbMGItemMaster, bindingResult);
		if (!bindingResult.hasErrors()) {
			tbMGItemMaster.setHasError(MainetMMConstants.MmItemMaster.FALSE);
			final UserSession userSession = UserSession.getCurrent();
            final int langId = UserSession.getCurrent().getLanguageId();
			final Organisation org = UserSession.getCurrent().getOrganisation();
			final Employee employee = UserSession.getCurrent().getEmployee();
			tbMGItemMaster.setOrgId(userSession.getOrganisation().getOrgid());
			tbMGItemMaster.setUserId(userSession.getEmployee().getEmpId());
			tbMGItemMaster.setLmodDate(new Date());
			tbMGItemMaster.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
			tbMGItemMaster.setLangId(langId);
			if (tbMGItemMaster.getItemId() != null) {
				tbMGItemMaster.setLgIpMacUpd(UserSession.getCurrent().getOrganisation().getLgIpMacUpd());
				tbMGItemMaster.setUpdatedBy(UserSession.getCurrent().getOrganisation().getUpdatedBy());
				tbMGItemMaster.setUpdatedDate(UserSession.getCurrent().getOrganisation().getUpdatedDate());
			}
			tbMGItemMaster.setEntryFlag(MainetMMConstants.MmItemMaster.ENTRY_FLAG_CREATE);

			if(tbMGItemMaster.getStatus() == null || tbMGItemMaster.getStatus().isEmpty()) {
				tbMGItemMaster.setStatus(MainetMMConstants.MmItemMaster.ACTIVE_STATUS);
			}

			populateModel(model, tbMGItemMaster, FormMode.CREATE);
			ItemMasterDTO itemMasterCreated = itemMasterService.saveItemMasterFormData(tbMGItemMaster,org,langId,employee);
			if (itemMasterCreated == null) {
				itemMasterCreated = new ItemMasterDTO();
			}
			model.addAttribute(MAIN_ENTITY_NAME, itemMasterCreated);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			if (tbMGItemMaster.getItemId() == null) {
				model.addAttribute(MainetMMConstants.MmItemMaster.KEY_TEST, ApplicationSession.getInstance().getMessage("material.item.master.item.number")
						+ MainetConstants.WHITE_SPACE + itemMasterCreated.getItemCode() + MainetConstants.WHITE_SPACE + 
						ApplicationSession.getInstance().getMessage("material.item.master.save.success"));
			}
			if (tbMGItemMaster.getItemId() != null) {
				model.addAttribute(MainetMMConstants.MmItemMaster.KEY_TEST, ApplicationSession.getInstance().getMessage("material.item.master.item.number")
						+ MainetConstants.WHITE_SPACE + itemMasterCreated.getItemCode() + MainetConstants.WHITE_SPACE + 
						ApplicationSession.getInstance().getMessage("material.item.master.update.success"));
			}
			result = JSP_FORM;
		} else {
			tbMGItemMaster.setHasError(MainetMMConstants.MmItemMaster.TRUE);
			model.addAttribute(MainetMMConstants.MmItemMaster.MODVIEW, getModeView());
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, bindingResult);
			populateModel(model, tbMGItemMaster, FormMode.CREATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(ItemMasterDTO tbMGItemMaster, @RequestParam("itemid") final Long itemid,
			@RequestParam(MainetMMConstants.MmItemMaster.MODE) final String viewmode,
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
			tbMGItemMaster.setItemId(itemid);
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			model.addAttribute(MAIN_ENTITY_NAME, tbMGItemMaster);
			populateModel(model, tbMGItemMaster, FormMode.UPDATE);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("ItemMaster 'update' : update done - redirect");
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODE, viewmode);
			model.addAttribute(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.MODVIEW, getModeView());
			log("ItemMaster 'update' : update done - redirect");
			result = JSP_FORM;
		} else {
			log("ItemMaster 'update' : binding errors");
			populateModel(model, tbMGItemMaster, FormMode.UPDATE);
			result = JSP_FORM;
		}
		return result;
	}

	@RequestMapping(params = "formForView", method = RequestMethod.POST)
	public String formForView(ItemMasterDTO tbMGItemMaster, @RequestParam("itemid") final Long itemid,
			@RequestParam(MainetMMConstants.MmItemMaster.MODE) final String viewmode,
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
			tbMGItemMaster.setItemId(itemid);
			tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
			if (null != tbMGItemMaster) {

				if (tbMGItemMaster.getItemGroup() != null) {

					String itemMasterGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL1,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getItemGroup());
					tbMGItemMaster.setItemGroupDesc(itemMasterGroupDesc);

				}

				if (tbMGItemMaster.getItemSubGroup() != null) {

					String itemMasterSubGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL2,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getItemSubGroup());
					tbMGItemMaster.setItemSubGroupDesc(itemMasterSubGroupDesc);
				}

				if (tbMGItemMaster.getCategory() != null) {

					String itemCategoryDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_CATEGORY_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getCategory());
					tbMGItemMaster.setCategoryDesc(itemCategoryDesc);
				}

				if (tbMGItemMaster.getType() != null) {

					String itemTypeDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_TYPE_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getType());
					tbMGItemMaster.setTypeDesc(itemTypeDesc);
				}
				
				if (tbMGItemMaster.getUom() != null) {

					String uomDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.UOM_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getUom());
					tbMGItemMaster.setUomDesc(uomDesc);
				}
				
				if (tbMGItemMaster.getValueMethod() != null) {

					String valuationmethodDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_VALUATION_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getValueMethod());
					tbMGItemMaster.setValueMethodDesc(valuationmethodDesc);
				}
				
				if (tbMGItemMaster.getManagement() != null) {

					String managementDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getManagement());
					tbMGItemMaster.setManagementDesc(managementDesc);
				}
				
				if (tbMGItemMaster.getClassification() != null) {

					String classificationDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_CLASSIFICATION_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getClassification());
					tbMGItemMaster.setClassificationDesc(classificationDesc);
				}
				
				if (tbMGItemMaster.getExpiryType() != null) {

					String expirytypeDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_EXPIRY_TYPE_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), tbMGItemMaster.getExpiryType());
					tbMGItemMaster.setExpiryTypeDesc(expirytypeDesc);
				}
				
				if (tbMGItemMaster.getStatus() != null) {
					
					final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
							MainetMMConstants.MmItemMaster.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
					for (LookUp lookUp : activeDeActiveMap) {
						if (lookUp.getLookUpCode().equals(tbMGItemMaster.getStatus())){
							tbMGItemMaster.setStatusDesc(lookUp.getLookUpDesc());
						}
					}
				}
			}

			model.addAttribute(MAIN_ENTITY_NAME, tbMGItemMaster);
			populateModel(model, tbMGItemMaster, FormMode.VIEW);
			messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
			log("ItemMaster 'view' : view done - redirect");
			model.addAttribute(MainetMMConstants.MmItemMaster.MODE, viewmode);
			model.addAttribute(MainetMMConstants.MmItemMaster.MODVIEW, getModeView());
			log("ItemMaster 'view' : view done - redirect");
			result = JSP_VIEW;
		} else {
			log("ItemMaster 'view' : binding errors");
			populateModel(model, tbMGItemMaster, FormMode.VIEW);
			result = JSP_VIEW;
		}
		return result;
	}
	
	 private String getItemMasterLookUpDescription(String itemMasterPrefix, int level, long orgid, Long lookUpId) {
		 
		 List<LookUp> itemMasterList = CommonMasterUtility.getNextLevelData(itemMasterPrefix, level, orgid);
			String desc = null;
			for (LookUp lookup : itemMasterList) {
				if (lookup.getLookUpId() == lookUpId) {
					desc = lookup.getDescLangFirst();
					break;
				}
			}
			return desc;
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

	 @RequestMapping(params = "exportExcelTemplateData")
	    public void exportItemMasterExcelData(final HttpServletResponse response,
			final HttpServletRequest request) {

		try {
			WriteExcelData<ItemMasterUploadDTO> data = new WriteExcelData<>(
					MainetMMConstants.MmItemMaster.ITEM_MASTER_EXCEL_NAME + MainetConstants.XLSX_EXT, request,
					response);

			data.getExpotedExcelSheet(new ArrayList<ItemMasterUploadDTO>(), ItemMasterUploadDTO.class);
		} catch (Exception ex) {
			throw new FrameworkException(ex.getMessage());
		}
	}

	@RequestMapping(params = "loadExcelData", method = RequestMethod.POST)
    public String loadValidateAndLoadExcelData(ItemMasterDTO tbMGItemMaster,
            final BindingResult bindingResult, final Model model, final RedirectAttributes redirectAttributes,
            final HttpServletRequest httpServletRequest) throws Exception {
        log("Action 'loadExcelData'");
        final ApplicationSession session = ApplicationSession.getInstance();
       
        final int langId = UserSession.getCurrent().getLanguageId();
        final Long userId = UserSession.getCurrent().getEmployee().getEmpId();
        final Organisation org = UserSession.getCurrent().getOrganisation();
        final String filePath = getUploadedFinePath();
        ReadExcelData<ItemMasterUploadDTO> data = new ReadExcelData<>(filePath,
        		ItemMasterUploadDTO.class);
        data.parseExcelList();
        List<String> errlist = data.getErrorList();
        if (!errlist.isEmpty()) {
            bindingResult.addError(
                    new org.springframework.validation.FieldError(MainetMMConstants.MmItemMaster.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("itemmaster.empty.excel")));
        } else {
            final List<ItemMasterUploadDTO> itemMasterUploadDtos = data.getParseData();
         
        	final List<LookUp> itemMasterUoMLookUp = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.UOM_PREFIX,
    				UserSession.getCurrent().getOrganisation());
        	
        	List<LookUp> itemMasterGroupLookup = CommonMasterUtility.getNextLevelData(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL1,
    				UserSession.getCurrent().getOrganisation().getOrgid());

    		List<LookUp> itemMasterSubGroupLookup = CommonMasterUtility.getNextLevelData(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL2,
    				UserSession.getCurrent().getOrganisation().getOrgid());

    		final List<LookUp> itemTypeMap = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_TYPE_PREFIX,
    				UserSession.getCurrent().getOrganisation());

    		List<LookUp> itemMasterCategoryList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_CATEGORY_PREFIX,
    				UserSession.getCurrent().getOrganisation());
        
    		final List<LookUp> itemMasterValuationMethodsLookUp = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_VALUATION_PREFIX,
    				UserSession.getCurrent().getOrganisation());
    		
    		final List<LookUp> itemMasterMgmtMethodsLookUp = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_MANAGEMENT_PREFIX,
    				UserSession.getCurrent().getOrganisation());
       
    		final List<LookUp> itemMasterExpiryMethodsLookUp = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_EXPIRY_TYPE_PREFIX,
    				UserSession.getCurrent().getOrganisation());
    		

    		final List<LookUp> itemMasterassetOfMasList = CommonMasterUtility.getListLookup(MainetMMConstants.MmItemMaster.ITEM_CLASSIFICATION_PREFIX,
    				UserSession.getCurrent().getOrganisation());
    		ItemMasterValidator validator = new ItemMasterValidator();
            List<ItemMasterUploadDTO> itemMasterUploadDtoList = null;
    		
    			   itemMasterUploadDtoList = validator
                           .excelValidation(itemMasterUploadDtos, bindingResult, itemMasterUoMLookUp, itemMasterGroupLookup, itemMasterSubGroupLookup,
                        		   itemTypeMap,itemMasterCategoryList,itemMasterValuationMethodsLookUp,itemMasterMgmtMethodsLookUp,itemMasterExpiryMethodsLookUp,itemMasterassetOfMasList);
    		   
			if (!bindingResult.hasErrors()) {
				for (ItemMasterUploadDTO itemMasterUploadDto : itemMasterUploadDtoList) {

					itemMasterUploadDto.setOrgId(org.getOrgid());
					itemMasterUploadDto.setLangId(langId);
					itemMasterUploadDto.setUserId(userId);
					itemMasterUploadDto.setLmodDate(new Date());
					itemMasterUploadDto.setLgIpMac(Utility.getClientIpAddress(httpServletRequest));
					itemMasterUploadDto.setEntryFlag(MainetMMConstants.MmItemMaster.ENTRY_FLAG_EXCEL_UPLOAD);
					itemMasterUploadDto.setStatus(MainetMMConstants.MmItemMaster.ACTIVE_STATUS);
					itemMasterUploadDto.setLgIpMacUpd(UserSession.getCurrent().getOrganisation().getLgIpMacUpd());
					itemMasterUploadDto.setUpdatedBy(UserSession.getCurrent().getOrganisation().getUpdatedBy());
					itemMasterUploadDto.setUpdatedDate(UserSession.getCurrent().getOrganisation().getUpdatedDate());
					itemMasterService.saveItemMasterExcelData(itemMasterUploadDto);
				}

				model.addAttribute(MainetMMConstants.MmItemMaster.KEY_TEST,  session.getMessage("itemmaster.success.excel"));
			}
         
        }
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MAIN_ENTITY_NAME, bindingResult);
        populateModel(model, tbMGItemMaster, FormMode.CREATE);
        model.addAttribute(MAIN_ENTITY_NAME, tbMGItemMaster);
        messageHelper.addMessage(redirectAttributes, new Message(MessageType.SUCCESS, MainetConstants.SAVE));
        return JSP_EXCELUPLOAD;
    
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

	public String getModeView() {
		return modeView;
	}

	public void setModeView(final String modeView) {
		this.modeView = modeView;
	}
	
	private List<ItemMasterDTO> processItemDetails(List<ItemMasterDTO> listOfItemMasterdtos) {
		
		if (listOfItemMasterdtos != null && listOfItemMasterdtos.size() > 0) {

			for (ItemMasterDTO bean : listOfItemMasterdtos) {
				if (bean.getItemGroup() != null) {
					String itemMasterGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL1,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemGroup());
					bean.setItemGroupDesc(itemMasterGroupDesc);
				}
				if (bean.getItemSubGroup() != null) {
					String itemMasterSubGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX, MainetMMConstants.MmItemMaster.LEVEL2,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getItemSubGroup());
					bean.setItemSubGroupDesc(itemMasterSubGroupDesc);
				}
				if (bean.getCategory() != null) {
					String itemCategoryDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_CATEGORY_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getCategory());
					bean.setCategoryDesc(itemCategoryDesc);
				}
				if (bean.getType() != null) {
					String itemTypeDesc = CommonMasterUtility.findLookUpDesc(MainetMMConstants.MmItemMaster.ITEM_TYPE_PREFIX,
							UserSession.getCurrent().getOrganisation().getOrgid(), bean.getType());
					bean.setTypeDesc(itemTypeDesc);
				}
	           if (bean.getStatus() != null) {
					
					final List<LookUp> activeDeActiveMap = CommonMasterUtility.getListLookup(
							MainetConstants.BUDGET_CODE.ACTIVE_INACTIVE_STATUS_PREFIX, UserSession.getCurrent().getOrganisation());
					for (LookUp lookUp : activeDeActiveMap) {
						if (lookUp.getLookUpCode().equals(bean.getStatus())){
							bean.setStatusDesc(lookUp.getLookUpDesc());
						}
					}
				}
			}
		}
		
		return listOfItemMasterdtos;
		
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
	
	 
	 @RequestMapping(params = "CheckName", method = RequestMethod.POST)
		public @ResponseBody Boolean CheckName(@RequestParam("name") final String name, 
				 final HttpServletRequest httpServletRequest)  {
		 
		Long id=itemMasterService.ItemNameCount(name,UserSession.getCurrent().getOrganisation().getOrgid());
			
			if (id == 0) {
				return true;
			}
			else {
				return false; 
			}
		}

}
