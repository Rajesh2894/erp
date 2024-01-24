package com.abm.mainet.materialmgmt.ui.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.master.service.ITaxDefinationService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;
import com.abm.mainet.materialmgmt.service.IInvoiceEntryService;
import com.abm.mainet.materialmgmt.service.Igoodsrecievednoteservice;
import com.abm.mainet.materialmgmt.ui.model.InvoiceEntryModel;

@Controller
@RequestMapping(value = { "/StoreInvoiceEntry.html" })
public class InvoiceEntryController extends AbstractFormController<InvoiceEntryModel> {

	@Autowired
	private IInvoiceEntryService invoiceEntryService;

	@Autowired
	private TbAcVendormasterService vendorService;

	@Autowired
	private Igoodsrecievednoteservice goodsrecievednoteservice;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private ITaxDefinationService taxDefinationService;
	
	@Resource
	private StoreMasterRepository storeMasterRepository;


	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setInvoiceEntryDTOList(invoiceEntryService.fetchInvoiceEntrySummaryData(null, null, null, null,	null, null, orgId));
		getListsOnSummaryForm(model, orgId);
		return new ModelAndView("InvoiceEntrySummaryForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@SuppressWarnings("deprecation")
	private void getListsOnSummaryForm(Model model, Long orgId) {
		this.getModel().setStoreIdNameList(storeMasterRepository.getStoreIdAndNameList(orgId));
		this.getModel().setPurchaseOrderDtoList(invoiceEntryService.getPONumbersForInvoiceSummary(orgId));
		this.getModel().setGoodsReceivedNotesDtoList(goodsrecievednoteservice.searchGoodsReceivedNotes(orgId, null,
				null, null, null, null, MainetConstants.FlagA));
		this.getModel().setVendors(vendorService.getAllActiveVendors(orgId, CommonMasterUtility
				.getValueFromPrefixLookUp(AccountConstants.AC.getValue(), PrefixConstants.VSS).getLookUpId()));
	}

	@RequestMapping(params = "addInvoiceEntryForm", method = RequestMethod.POST)
	public ModelAndView addPurchaseReturn(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreIdNameList(storeMasterRepository.getStoreIdAndNameList(orgId));
		getDeductionList(orgId);
		return new ModelAndView("InvoiceEntry/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	private void getDeductionList(Long orgId) {
		this.getModel().getListTaxDefinationDto().clear();
		this.getModel().setListTaxDefinationDto(taxDefinationService.getTaxDefinationList(orgId));
	}

	@ResponseBody
	@RequestMapping(params = "editStoreInvoiceEntryForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editStoreInvoiceEntry(final Model model, final HttpServletRequest request,
			@RequestParam(required = false) Long invoiceId) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.EDIT);
		this.getModel().setInvoiceEntryDTO(invoiceEntryService.invoiceEntryEditAndView(invoiceId, orgId));
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(orgId, (MainetConstants.DEPT_SHORT_NAME.STORE
						+ MainetConstants.WINDOWS_SLASH + this.getModel().getInvoiceEntryDTO().getInvoiceNo()));
		this.getModel().setAttachDocsList(attachDocs);
		getDeductionList(orgId);
		return new ModelAndView("InvoiceEntry/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "viewStoreInvoiceEntryForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewStoreInvoiceEntry(final Model model, final HttpServletRequest request,
			@RequestParam(required = false) Long invoiceId) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setInvoiceEntryDTO(invoiceEntryService.invoiceEntryEditAndView(invoiceId, orgId));
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(orgId, (MainetConstants.DEPT_SHORT_NAME.STORE
						+ MainetConstants.WINDOWS_SLASH + this.getModel().getInvoiceEntryDTO().getInvoiceNo()));
		this.getModel().setAttachDocsList(attachDocs);
		getDeductionList(orgId);
		return new ModelAndView("InvoiceEntry/Form", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "printInvoiceEntry", method = RequestMethod.POST)
	public ModelAndView printInvoiceEntry(final HttpServletRequest request, @RequestParam(required = false) String invoiceNo) {
		if(0l == this.getModel().getLevelCheck()) {
			Object[] nameObject = this.getModel().getStoreIdNameList().stream().filter(k -> Long
					.valueOf(((Object[]) k)[0].toString()).equals(this.getModel().getInvoiceEntryDTO().getStoreId()))
					.findFirst().orElse(null);
			this.getModel().getInvoiceEntryDTO().setStoreName(nameObject[1].toString());
			
			PurchaseOrderDto purchaseOrderDto = this.getModel().getPurchaseOrderDtoList().stream().filter(dto -> dto.getPoId().equals(this.getModel().getInvoiceEntryDTO().getPoId())).findFirst().orElse(null);
			this.getModel().getInvoiceEntryDTO().setPoNumber(purchaseOrderDto.getPoNo());			
		}		
		
		Map<Long, String> taxDefinationMap = new HashMap<>();
		this.getModel().getListTaxDefinationDto().forEach(taxDefination -> taxDefinationMap.put(taxDefination.getTaxId(), taxDefination.getTaxDesc()));
		this.getModel().getInvoiceEntryDTO().getInvoiceOverheadsDTOList().forEach(overheadsDTO -> overheadsDTO
				.setDescription(taxDefinationMap.get(Long.valueOf(overheadsDTO.getDescription()))));
		
		return new ModelAndView("InvoiceEntryPrint", MainetConstants.FORM_NAME, this.getModel());
	}
	

	@ResponseBody
	@RequestMapping(params = "getGrnAndItemDetails", method = RequestMethod.POST)
	public ModelAndView searchGrnData(final HttpServletRequest request, final Model model) {
		bindModel(request);
		this.getModel().getInvoiceEntryDTO().getInvoiceEntryGRNDTOList().clear();
		this.getModel().getInvoiceEntryDTO().getInvoiceEntryDetailDTOList().clear();
		this.getModel().setInvoiceEntryDTO(invoiceEntryService.fetchInvoiceGRNAndItemDataByGRNId(
				this.getModel().getInvoiceEntryDTO(), UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("InvoiceEntry/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "getPONumbersByStore")
	public Map<Long, String> getPONumberListByStore(@RequestParam("storeId") Long storeId, final HttpServletRequest request) {
		Map<Long, String> object = new LinkedHashMap<Long, String>();
		this.getModel().getPurchaseOrderDtoList().clear();
		List<PurchaseOrderDto> purchaseOrderDtoList = invoiceEntryService.getPONumbersByStore(storeId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setPurchaseOrderDtoList(purchaseOrderDtoList);
		purchaseOrderDtoList.forEach(purchaseOrderDto -> {
			object.put(purchaseOrderDto.getPoId(), purchaseOrderDto.getPoNo());
		});
		return object;
	}

	@ResponseBody
	@RequestMapping(method = { RequestMethod.POST }, params = "getGRNListByPoId")
	public Map<String, Object[]> getGRNListByPoId(@RequestParam("storeId") Long storeId, @RequestParam("poId") Long poId,
				final HttpServletRequest request) {
		Map<String, Object[]> objectMap = new LinkedHashMap<String, Object[]>();
		this.getModel().getGoodsReceivedNotesMultiselectList().clear();
		List<GoodsReceivedNotesDto> goodsReceivedNotesDtoList = invoiceEntryService.getGRNNumberListByPoId(storeId,
				poId, UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setGoodsReceivedNotesMultiselectList(goodsReceivedNotesDtoList);
		Long grnIdListid = 0L; // there is list of GRN Nos, Sting Key overrides data
		for (GoodsReceivedNotesDto gooReceiveDto : goodsReceivedNotesDtoList) {
			objectMap.put(grnIdListid.toString(), new Object[] { gooReceiveDto.getGrnid(), gooReceiveDto.getGrnno() });
			grnIdListid++;
		}
		objectMap.put("vendorId", new Object[] { goodsReceivedNotesDtoList.get(0).getVendorId(), goodsReceivedNotesDtoList.get(0).getVendorName() });
		return objectMap;
	}


	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView invoiceEntryApproval(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.bindModel(httpServletRequest);
		InvoiceEntryModel invoiceEntryModel = this.getModel();
		invoiceEntryModel.setTaskId(actualTaskId);
		invoiceEntryModel.getWorkflowActionDto().setReferenceId(appNo);
		invoiceEntryModel.getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().setInvoiceEntryDTO(invoiceEntryService.getInvoiceApprovalData(appNo, orgId));
		getDeductionList(orgId);
		return new ModelAndView("InvoiceEntry/Form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchInvoiceEntryForm", method = RequestMethod.POST)
	public ModelAndView searchInvoiceEntry(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long invoiceId, @RequestParam(required = false) Long poId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,
			@RequestParam(required = false) Long storeId, @RequestParam(required = false) Long vendorId) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setInvoiceEntryDTOList(invoiceEntryService.fetchInvoiceEntrySummaryData(invoiceId, poId,
				fromDate, toDate, storeId, vendorId, orgId));
		getListsOnSummaryForm(model, orgId);
		this.getModel().getInvoiceEntryDTO().setInvoiceId(invoiceId);
		this.getModel().getInvoiceEntryDTO().setPoId(poId);
		this.getModel().getInvoiceEntryDTO().setFromDate(fromDate);
		this.getModel().getInvoiceEntryDTO().setToDate(toDate);
		this.getModel().getInvoiceEntryDTO().setStoreId(storeId);
		this.getModel().getInvoiceEntryDTO().setVendorId(vendorId);
		return new ModelAndView("InvoiceEntrySearchForm", MainetConstants.FORM_NAME, this.getModel());
	}

}
