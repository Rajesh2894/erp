package com.abm.mainet.materialmgmt.ui.controller;

import java.text.ParseException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.service.IPurchaseReturnService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.model.PurchaseReturnModel;


@Controller
@RequestMapping(value = { "/PurchaseReturn.html" })
public class PurchaseReturnController extends AbstractFormController<PurchaseReturnModel> {

	@Autowired
	private IPurchaseReturnService iPurchaseReturnService;
	
	@Autowired
	private TbAcVendormasterService vendorService;

	@Autowired
	private IStoreMasterService storeMasterservice;
	
	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseReturnDtoList(iPurchaseReturnService.fetchRejectedItemSummaryData(null, null, null,
				null, null, null, orgId));
		getListsOnForm(model);
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(orgId, null,
				null, null, null, null, MainetConstants.FlagA));
		return new ModelAndView("PurchaseReturnSummary", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@SuppressWarnings("deprecation")
	private void getListsOnForm(Model model) {		
		this.getModel().setStoreNameList(storeMasterservice.serchStoreMasterDataByOrgid(UserSession.getCurrent().getOrganisation().getOrgid()));	
		final Long vendorStatus = CommonMasterUtility
				.getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS).getLookUpId();
		this.getModel().setVendors(vendorService
				.getAllActiveVendors(UserSession.getCurrent().getOrganisation().getOrgid(), vendorStatus));
	}


	@RequestMapping(params = "addPurchaseReturnForm", method = RequestMethod.POST)
	public ModelAndView addPurchaseReturn( final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		this.getModel().setGoodsReceivedNotesDtoList(iPurchaseReturnService.getGrnNumbersForReturn(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("PurchaseReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@ResponseBody
	@RequestMapping(params = "viewPurchaseReturnForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPurchaseReturn(final HttpServletRequest request, @RequestParam(required = false) Long returnId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setPurchaseReturnDto(iPurchaseReturnService.fetchRejectedItemDataByReturnId(returnId, UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("PurchaseReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
		
	@ResponseBody
	@RequestMapping(params = "getDataByGRNId", method = RequestMethod.POST)
	public ModelAndView searchGrnData(final HttpServletRequest request, final Model model) {
		bindModel(request);		
		this.getModel().setPurchaseReturnDto(iPurchaseReturnService.fetchRejectedItemDataByGRNId(
						this.getModel().getPurchaseReturnDto().getGrnId(), UserSession.getCurrent().getOrganisation().getOrgid()));		
		return new ModelAndView("PurchaseReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "printPurchaseReturn", method = RequestMethod.POST)
	public ModelAndView printPurchaseReturn(final HttpServletRequest request, @RequestParam(required = false) String returnNo) {
		return new ModelAndView("PurchaseReturnPrint", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "searchPurchaseReturn", method = RequestMethod.POST)
	public ModelAndView searchPurchaseReturnData(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long returnId, @RequestParam(required = false) Long grnId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) Long vendorId) throws ParseException {
		sessionCleanup(request);
		this.getModel().setPurchaseReturnDtoList(iPurchaseReturnService.fetchRejectedItemSummaryData(returnId, grnId, fromDate,
				toDate, storeId, vendorId, UserSession.getCurrent().getOrganisation().getOrgid()));		
		getListsOnForm(model);
		this.getModel().getPurchaseReturnDto().setReturnId(returnId);
		this.getModel().getPurchaseReturnDto().setGrnId(grnId);
		this.getModel().getPurchaseReturnDto().setFromDate(fromDate);
		this.getModel().getPurchaseReturnDto().setToDate(toDate);
		this.getModel().getPurchaseReturnDto().setStoreId(storeId);
		this.getModel().getPurchaseReturnDto().setVendorId(vendorId);		
		return new ModelAndView("PurchaseReturnSummarySearch", MainetConstants.FORM_NAME, this.getModel());
	}


}
