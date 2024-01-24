package com.abm.mainet.materialmgmt.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDetDto;
import com.abm.mainet.materialmgmt.dto.ItemOpeningBalanceDto;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.service.ItemOpeningBalanceService;
import com.abm.mainet.materialmgmt.ui.model.ItemMasterDTO;
import com.abm.mainet.materialmgmt.ui.model.ItemOpeningBalanceModel;
import com.abm.mainet.materialmgmt.utility.MainetMMConstants;

@Controller
@RequestMapping("/ItemOpeningBalance.html")
public class ItemOpeningBalanceController extends AbstractFormController<ItemOpeningBalanceModel> {

	@Resource
	private ItemMasterService itemMasterService;

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private IBinMasService binMasService;

	@Autowired
	private ItemOpeningBalanceService itemOpeningBalanceService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateDtails();
		this.getModel().setOpeningBalList(itemOpeningBalanceService.findByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("itemOpeningBalance/list", MainetConstants.FORM_NAME, this.getModel());
	}

	private void populateDtails() {
		this.getModel().setCommonHelpDocs("ItemOpeningBalance.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreObjectList(storeMasterservice.getStoreIdAndNameList(orgId));
		this.getModel().setItemList(itemMasterService.findItemMasterDetailsByOrgId(orgId));
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "form")
	public ModelAndView addItemOpeningBalanceRecord(final HttpServletRequest httpServletRequest,
			@RequestParam(required = false, value = "openBalId") final Long openBalId,
			@RequestParam(value = MainetConstants.Common_Constant.TYPE) String type) {
		sessionCleanup(httpServletRequest);
		ItemOpeningBalanceModel model = this.getModel();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		this.getModel().setItemList(itemMasterService.findItemMasterDetailsByOrgId(organisation.getOrgid()));
		if (null == openBalId) {
			model.setModeType(MainetConstants.MODE_CREATE);
			model.setStoreObjectList(storeMasterservice.getActiveStoreObjectListForAdd(organisation.getOrgid()));
		} else {
			model.setStoreObjectList(storeMasterservice.getStoreIdAndNameList(organisation.getOrgid()));
			if (MainetMMConstants.BIN_MASTER.V.equals(type)) {
				model.setModeType(MainetConstants.MODE_VIEW);
			} else {
				model.setModeType(MainetConstants.MODE_EDIT);
			}
			ItemOpeningBalanceDto dto = itemOpeningBalanceService.findById(openBalId);
			LookUp managementMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getMethodId(), organisation);
			LookUp uomPrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.getUomId(), organisation);
			dto.setUom(uomPrefix.getLookUpDesc());
			dto.setValueMethod(managementMethod.getLookUpDesc());
			dto.setValueMethodCode(managementMethod.getLookUpCode());
			dto.setGroup(getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX,
					MainetMMConstants.MmItemMaster.LEVEL1, organisation.getOrgid(), dto.getItemGroup1()));
			dto.setSubGroup(getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX,
					MainetMMConstants.MmItemMaster.LEVEL2, organisation.getOrgid(), dto.getItemGroup2()));
			model.setItemOpeningBalanceDto(dto);
			this.getModel().setBinLocList(binMasService.findAllBinLocation(organisation.getOrgid()));
		}

		return new ModelAndView("itemOpeningBalance/form", MainetConstants.FORM_NAME, model);
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "ItemDetails")
	public ItemMasterDTO findItemDetails(@RequestParam("itemId") final Long itemId,
			final HttpServletRequest httpServletRequest) {
		ItemMasterDTO tbMGItemMaster = new ItemMasterDTO();
		tbMGItemMaster.setItemId(itemId);
		tbMGItemMaster = itemMasterService.getDetailsByUsingItemId(tbMGItemMaster);
		String itemMasterGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX,
				MainetMMConstants.MmItemMaster.LEVEL1, UserSession.getCurrent().getOrganisation().getOrgid(),
				tbMGItemMaster.getItemGroup());
		tbMGItemMaster.setItemGroupDesc(itemMasterGroupDesc);
		String itemMasterSubGroupDesc = getItemMasterLookUpDescription(MainetMMConstants.MmItemMaster.ITEM_GROUP_PREFIX,
				MainetMMConstants.MmItemMaster.LEVEL2, UserSession.getCurrent().getOrganisation().getOrgid(),
				tbMGItemMaster.getItemSubGroup());
		tbMGItemMaster.setItemSubGroupDesc(itemMasterSubGroupDesc);
		LookUp managementMethod = CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getManagement(),
				UserSession.getCurrent().getOrganisation());
		tbMGItemMaster.setManagementDesc(managementMethod.getLookUpDesc());
		tbMGItemMaster.setItemCode(managementMethod.getLookUpCode());
		if (null != tbMGItemMaster.getExpiryType()) {
			LookUp exipryType = CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getExpiryType(),
					UserSession.getCurrent().getOrganisation());
			tbMGItemMaster.setExpiryTypeDesc(exipryType.getLookUpCode());
		}
		LookUp uomPrefix = CommonMasterUtility.getNonHierarchicalLookUpObject(tbMGItemMaster.getUom(),
				UserSession.getCurrent().getOrganisation());
		tbMGItemMaster.setUomDesc(uomPrefix.getLookUpDesc());
		return tbMGItemMaster;
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "FindMethod")
	public ModelAndView findItemManagedMethod(@RequestParam("methodType") final String methodType,
			final HttpServletRequest httpServletRequest) {

		this.getModel()
				.setBinLocList(binMasService.findAllBinLocation(UserSession.getCurrent().getOrganisation().getOrgid()));
		// String viewName;
		/*
		 * if("NIB".equals(methodType)) viewName = "itemOpeningBalance/NIBMethodform" ;
		 * else if("IB".equals(methodType)) viewName =
		 * "itemOpeningBalance/IBMethodform"; else viewName =
		 * "itemOpeningBalance/serialMethodform";
		 */
		/* viewName = "itemOpeningBalance/serialMethodform"; */
		// viewName = "itemOpeningBalance/IBMethodform";

		// if("NIB".equals(methodType)) viewName = "itemOpeningBalance/NIBMethodform" ;
		// else
		// viewName = "itemOpeningBalance/serialMethodform";
		this.getModel().getItemOpeningBalanceDto().setValueMethodCode(methodType);
		return new ModelAndView("itemOpeningBalance/methodform", MainetConstants.FORM_NAME, this.getModel());
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

	@ResponseBody
    @RequestMapping(params = "search", method = RequestMethod.POST)
    public ModelAndView searchStoreMaster(final HttpServletRequest request,
            @RequestParam(required = false) Long storeId, @RequestParam(required = false) Long itemId) {
        sessionCleanup(request);
        populateDtails();
        this.getModel().setOpeningBalList(itemOpeningBalanceService.serchByItemIdAndStoreId(storeId, itemId, UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getItemOpeningBalanceDto().setStoreId(storeId);
		this.getModel().getItemOpeningBalanceDto().setItemId(itemId);
        return new ModelAndView("itemOpeningBalance/showPage", MainetConstants.FORM_NAME, this.getModel());
	}

	/*
	 * @RequestMapping(params = "exportTemplateData", method = RequestMethod.POST)
	 * public String exportImportExcelTemplate(final Model model) throws Exception {
	 * log("BankMasterDTO-'exportImportExcelTemplate' : 'exportImportExcelTemplate'"
	 * ); String result = MainetConstants.CommonConstant.BLANK; final BankMasterDTO
	 * bean = new BankMasterDTO(); populateModel(model, bean, FormMode.CREATE);
	 * result = JSP_EXCELUPLOAD; return result; }
	 * 
	 * @RequestMapping(params = "ExcelTemplateData") public void
	 * exportAccountDepositExcelData(final HttpServletResponse response, final
	 * HttpServletRequest request) {
	 * 
	 * try { WriteExcelData<BankMasterUploadDTO> data = new WriteExcelData<>(
	 * MainetConstants.BANKMASTERUPLOADDTO + MainetConstants.XLSX_EXT, request,
	 * response);
	 * 
	 * data.getExpotedExcelSheet(new ArrayList<BankMasterUploadDTO>(),
	 * BankMasterUploadDTO.class); } catch (Exception ex) { throw new
	 * FrameworkException(ex.getMessage()); } }
	 */

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "deActivate")
	public ModelAndView deActivateRecord(@RequestParam("openBalId") final Long openBalId,
			final HttpServletRequest httpServletRequest) {
		itemOpeningBalanceService.updateStatus(openBalId, UserSession.getCurrent().getEmployee().getEmpId());
		populateDtails();
		this.getModel().setOpeningBalList(itemOpeningBalanceService.findByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("itemOpeningBalance/showPage", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = { "doDeletion" })
	@ResponseBody
	public boolean doItemDeletion(@RequestParam(name = "id", required = true) Long id,
			final HttpServletRequest request) {
		bindModel(request);
		List<ItemOpeningBalanceDetDto> item = this.getModel().getItemOpeningBalanceDto().getItemOpeningBalanceDetDto();
		List<ItemOpeningBalanceDetDto> beanList = item.stream().filter(c -> c.getOpenBalDetId() != id)
				.collect(Collectors.toList());
		if (!beanList.isEmpty()) {
			this.getModel().getItemOpeningBalanceDto().setItemOpeningBalanceDetDto(beanList);
		}
		return true;
	}


	/**To Check Duplicate Item No i.e  Serial No. Or Batch No.*/ 
	@ResponseBody
	@RequestMapping(params = "duplicateChecker", method = RequestMethod.POST)
	public List<String>  duplicateChecker(final HttpServletRequest httpServletRequest) {		
		bindModel(httpServletRequest);
		Long itemId = this.getModel().getItemOpeningBalanceDto().getItemId();
		Long OrgId =  UserSession.getCurrent().getOrganisation().getOrgid();
		List<String> itemNumberList = new ArrayList<>();
		this.getModel().getItemOpeningBalanceDto().getItemOpeningBalanceDetDto().forEach(detailDto->{
			if(null == detailDto.getOpenBalDetId())
				itemNumberList.add(detailDto.getItemNo());
		});
		if(itemNumberList.size() > 0)
			return itemOpeningBalanceService.checkDuplicateItemNos(itemId, itemNumberList, OrgId);
		else
			return itemNumberList;
	}

}
