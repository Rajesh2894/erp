package com.abm.mainet.materialmgmt.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
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
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.dto.PurchaseorderAttachmentDto;
import com.abm.mainet.materialmgmt.service.IPurchaseOrderService;
import com.abm.mainet.materialmgmt.service.IPurchaseRequistionService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.ui.model.PurchaseOrderModel;

@Controller
@RequestMapping(value = {"/PurchaseOrder.html"})
public class PurchaseOrderController extends AbstractFormController<PurchaseOrderModel> {

	@Autowired
	private IStoreMasterService storeMasterservice;

	@Autowired
	private TbAcVendormasterService tbAcVendormasterService;

	@Autowired
	private IPurchaseRequistionService purchaseRequistionService;

	@Autowired
	private IPurchaseOrderService purchaseOrderService;

	@Autowired
	private IFileUploadService fileUpload;


	@RequestMapping(method = { RequestMethod.POST , RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseOrderDtoList(purchaseOrderService.searchPurchaseOrderData(null, null, null, null, orgId));
		loadDefaultData(orgId);
		return new ModelAndView("PurchaseOrderList", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * @param purchaseOrderDtoList 
	 * @param httpServletRequest
	 * it has common lists being used
	 */
	@SuppressWarnings("deprecation")
	private List<PurchaseOrderDto> loadDefaultData(Long orgId) {
		this.getModel().setStoreIdNameList(storeMasterservice.getStoreIdAndNameList(orgId));
		Map<Long, String> storeIdToNameMap = new HashMap<>();
		this.getModel().getStoreIdNameList().forEach(StoreIdName -> {
			storeIdToNameMap.put(Long.valueOf(StoreIdName[0].toString()), StoreIdName[1].toString());
		});

		this.getModel().setVendors(tbAcVendormasterService.getAllActiveVendors(orgId, CommonMasterUtility
				.getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS).getLookUpId()));
		Map<Long, String> vendorCodeMap = new HashMap<>();
		this.getModel().getVendors().forEach(vendor -> {
			vendorCodeMap.put(vendor.getVmVendorid(), vendor.getVmVendorname());
		});

		this.getModel().getPurchaseOrderDtoList().forEach(purchaseOrderDto -> {
			purchaseOrderDto.setStoreName(storeIdToNameMap.get(purchaseOrderDto.getStoreId()));
			purchaseOrderDto.setVendorName(vendorCodeMap.get(purchaseOrderDto.getVendorId()));
		});
		return this.getModel().getPurchaseOrderDtoList();
	}
	
	@RequestMapping(params = "addPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView addPurchaseOrder(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setRequisitionObject(purchaseRequistionService.purchaseReqForTender(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}
		
	
	@RequestMapping(params = "saveApprovalPurchaseOrder", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveApprovalPurchaseOrderForm(final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getPurchaseOrderDto().setStatus(MainetConstants.CommonConstants.CHAR_Y);
		this.getModel().saveForm();
		return jsonResult(JsonViewObject.successResult(this.getModel().getSuccessMessage()));
	}

	
	@ResponseBody
	@RequestMapping(params = "getpurreqdata", method = { RequestMethod.POST })
	public ModelAndView getPurReqDataTest(final Model model, final HttpServletRequest request) {
		bindModel(request);
		this.getModel().setPurchaseOrderDto(purchaseOrderService.getPurchaseRequisitionByPrID(
				this.getModel().getPurchaseOrderDto(), UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "editPurchaseOrder", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView editPurchaseOrder(final HttpServletRequest request, @RequestParam("poId") Long poId,
			@RequestParam(name="redirectTender",required=false) String redirectTender) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseOrderDto(purchaseOrderService.purchaseOrderEditAndView(poId, orgId));
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "viewPurchaseOrder", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView viewPurchaseData(final HttpServletRequest request, @RequestParam("poId") Long poId,
			@RequestParam(name="redirectTender",required=false) String redirectTender) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseOrderDto(purchaseOrderService.purchaseOrderEditAndView(poId, orgId));
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "deletePurchaseOrder", method = RequestMethod.POST)
	public ModelAndView deletePurchaseOrderForm(final HttpServletRequest request, @RequestParam("poId") Long poId) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		purchaseOrderService.purchaseOrderFordelete(poId, orgId);
		this.getModel().setPurchaseOrderDtoList(purchaseOrderService.searchPurchaseOrderData(null, null, null, null, orgId));
		loadDefaultData(orgId);
		return new ModelAndView("searcPurchaseOrder", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "searchPurchaseOrder", method = RequestMethod.POST)
	public ModelAndView searchPurchaseData(final HttpServletRequest request,
			@RequestParam(required = false) Long storeNameId, @RequestParam(required = false) Long vendorId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPurchaseOrderDtoList(purchaseOrderService.searchPurchaseOrderData(storeNameId, vendorId, fromDate, toDate, orgId));
		loadDefaultData(orgId);
		this.getModel().getPurchaseOrderDto().setStoreId(storeNameId);
		this.getModel().getPurchaseOrderDto().setVendorId(vendorId);
		this.getModel().getPurchaseOrderDto().setFromDate(fromDate);
		this.getModel().getPurchaseOrderDto().setToDate(toDate);
		return new ModelAndView("searcPurchaseOrder", MainetConstants.FORM_NAME, this.getModel());
	}
	
		
	@RequestMapping(method = RequestMethod.POST, params = "fileCountUpload")
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<PurchaseorderAttachmentDto> list = new ArrayList<>();
		for (PurchaseorderAttachmentDto purchaseorderAttachmentDto : this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto()) {
			list.add(purchaseorderAttachmentDto);
		}
		int count = 0;
		for (PurchaseorderAttachmentDto purchaseorderAttachmentDto : this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto()) {
			if (purchaseorderAttachmentDto.getDescription() != null) {
				BeanUtils.copyProperties(purchaseorderAttachmentDto, list.get(count));
				count++;
			}
		}
		this.getModel().getPurchaseOrderDto().setPurchaseorderAttachmentDto(list);
		// copy old case entry data to next row in dataTable
		long lengthOfList = list.size() - 1;
		List<PurchaseorderAttachmentDto> listData = new ArrayList<>();
		for (int i = 0; i <= lengthOfList; i++) {
			PurchaseorderAttachmentDto tesDto = list.get(i);
			PurchaseorderAttachmentDto newData = new PurchaseorderAttachmentDto();
			newData = tesDto;
			newData.setDescription(this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto().get(i).getDescription());
			listData.add(newData);
		}
		Long count1 = 0l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list1 = new ArrayList<>(entry.getValue());
			if (!list1.isEmpty()) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);
		listData.add(new PurchaseorderAttachmentDto());
		this.getModel().getPurchaseOrderDto().setPurchaseorderAttachmentDto(listData);
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}
	

	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletion")
	public ModelAndView doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		List<Long> enclosureRemoveById = new ArrayList<>();
		if(this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto().get(id).getPodocId()!=null) {
			enclosureRemoveById.add(this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto().get(id).getPodocId());
			this.getModel().setRemovedIds(enclosureRemoveById);
		}
		this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
					this.getModel().getPurchaseOrderDto().getPurchaseorderAttachmentDto();
				}
			}
			Long count1 = 0l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list1 = new ArrayList<>(entry.getValue());
				if (!list1.isEmpty()) {
					fileMap1.put(count1, entry.getValue());
					count1++;
				}
			}
		}
		return new ModelAndView("PurchaseOrderForm", MainetConstants.FORM_NAME, this.getModel());
	}

}
