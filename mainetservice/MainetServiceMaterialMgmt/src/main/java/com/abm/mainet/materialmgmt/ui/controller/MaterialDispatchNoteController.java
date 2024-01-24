package com.abm.mainet.materialmgmt.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteDTO;
import com.abm.mainet.materialmgmt.dto.MaterialDispatchNoteItemsEntryDTO;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IMaterialDispatchNoteService;
import com.abm.mainet.materialmgmt.service.IStoreIndentService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.IndentProcessService;
import com.abm.mainet.materialmgmt.ui.model.MaterialDispatchNoteModel;

@Controller
@RequestMapping(value = { "/MaterialDispatchNote.html" })
public class MaterialDispatchNoteController extends AbstractFormController<MaterialDispatchNoteModel> {

	@Resource
	private IStoreMasterService iStoreMasterService;

	@Resource
	private IStoreIndentService storeIndentService;
	
	@Resource
	private IMaterialDispatchNoteService materialDispatchNoteService;
	
	@Resource
	private IndentProcessService indentProcessService;
	
	@Resource
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Resource
	private IBinMasService binMasService; 
	
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setDispatchNoteDTOList(materialDispatchNoteService.getmdnSummaryDataObjectList(orgId, null, null, 
				null, null,  null));
		getSummaryFormLists(orgId);
		return index();
	}
	
	private void getSummaryFormLists(Long orgId) {
		this.getModel().setCommonHelpDocs("MaterialDispatchNote.html");
		this.getModel().setStoreIdNameList(iStoreMasterService.getStoreIdAndNameList(orgId));
		this.getModel().setStoreIndentList(storeIndentService.getStoreIndentIdNumberList(orgId));
	}
	

	/** 
	 * MDN Add form
	 */
	@RequestMapping(params = "addMaterialDispatchNote", method = RequestMethod.POST)
	public ModelAndView addMaterialDispatchNote(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		this.getModel().setStoreIndentList(materialDispatchNoteService
				.getgetStoreIndentListForMDN(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/** 
	 * View MDN form
	 * @param mdnId
	 */
	@RequestMapping(params = "viewMaterialDispatch", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewMaterialDispatch(Model model, @RequestParam("mdnId") Long mdnId, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setDispatchNoteDTO(materialDispatchNoteService.getMDNDataByMDNId(mdnId, orgId));
		getMDNItemEntryDetailsList(this.getModel().getDispatchNoteDTO());
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/** 
	 * MDN Approval form
	 * @param appNo
	 */
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView indentFormApproval(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.bindModel(httpServletRequest);
		this.getModel().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setReferenceId(appNo);
		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		this.getModel().setDispatchNoteDTO(materialDispatchNoteService.getMDNApprovalDataByMDNNumber(appNo, orgId));
		getMDNItemEntryDetailsList(this.getModel().getDispatchNoteDTO());				
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	/** 
	 * MDN Approved form
	 * @param appNo
	 */
	@Override
	public ModelAndView viewDetails(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long actualTaskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		this.sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setDispatchNoteDTO(materialDispatchNoteService.getMDNApprovalDataByMDNNumber(appNo, orgId));
		getMDNItemEntryDetailsList(this.getModel().getDispatchNoteDTO());				
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/**
	 * common method to put Child List in map
	 **/
	private void getMDNItemEntryDetailsList(MaterialDispatchNoteDTO dispatchNoteDTO) {
		Map<Long, List<MaterialDispatchNoteItemsEntryDTO>> map = new LinkedHashMap<Long, List<MaterialDispatchNoteItemsEntryDTO>>();
		dispatchNoteDTO.getMatDispatchItemList().forEach(dispatchItemDto->{			
			List<MaterialDispatchNoteItemsEntryDTO> dispatchNoteEntryDTOList = new ArrayList<>();
			dispatchNoteEntryDTOList.addAll(dispatchItemDto.getMatDispatchItemsEntryList());
			map.put(dispatchItemDto.getMdnItemId(), dispatchNoteEntryDTOList);	
		});
		this.getModel().getNoteItemsEntryMap().putAll(map);
	}
	
	
	/**
	 * to get Store Indent Details by Store Indent Id
	 */
	@RequestMapping(params = "getStoreIndentDetails", method = RequestMethod.POST)
	public ModelAndView getStoreIndentDetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		this.getModel().setDispatchNoteDTO(materialDispatchNoteService.getStoreIndentDetailsById(
				this.getModel().getDispatchNoteDTO(), UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	

	@RequestMapping(params = "viewMDNIssueDetails", method = RequestMethod.POST)
	public ModelAndView viewMDNIssueDetails(final Model model, final HttpServletRequest httpServletRequest) {	
		getModel().bind(httpServletRequest);
		String viewName = null;
		int index = this.getModel().getIndexCount();
		String saveMode = this.getModel().getSaveMode();
		List<MaterialDispatchNoteItemsEntryDTO> noteItemsEntryDTOList = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long mdnItemId = this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getMdnItemId();
		this.getModel().setManagement(this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getManagement());	
		
		this.getModel().getNoteItemsEntryDTOList().clear();
		if (MainetConstants.FlagA.equalsIgnoreCase(saveMode) || (MainetConstants.FlagV.equalsIgnoreCase(saveMode)
				&& MainetConstants.FlagI.equalsIgnoreCase(this.getModel().getDispatchNoteDTO().getStatus()))) {
			if (null == mdnItemId)
				mdnItemId = this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getSiItemId();
			getMDNIssueDetailsData(noteItemsEntryDTOList, orgId, index, mdnItemId);
			viewName = "MaterialDispatchNoteIssueDetails";
		} else {
			getMDNInspectionDetailsData(noteItemsEntryDTOList, orgId, index, mdnItemId);
			viewName = "MaterialDispatchNoteInspection";
		}
		return new ModelAndView(viewName, MainetConstants.FORM_NAME, this.getModel());
	}
	
	private void getMDNIssueDetailsData(List<MaterialDispatchNoteItemsEntryDTO> noteItemsEntryDTOList, Long orgId, int index, Long mdnItemId) {
		Long itemId = this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getItemId();
		this.getModel().setBinLocList(indentProcessService.findAllBinLocationByItemID(orgId,
				this.getModel().getDispatchNoteDTO().getIssueStoreId(), itemId));
		
		BigDecimal requestedQnty = this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getRequestedQty();
		BigDecimal prevReceivedQnty = this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getPrevRecQty();
		this.getModel().setRemainQtyToBeIssued(requestedQnty.subtract(prevReceivedQnty));
		
		noteItemsEntryDTOList = this.getModel().getNoteItemsEntryMap().get(mdnItemId);		
		if(null != noteItemsEntryDTOList) {
			this.getModel().getNoteItemsEntryDTOList().addAll(noteItemsEntryDTOList);
			this.getModel().getNoteItemsEntryDTOList().forEach(noteItemsEntryDTO -> {
				noteItemsEntryDTO.setItemNumberList(this.getModel().getItemNumbersMap().get(itemId.toString()
						.concat(MainetConstants.operator.COMMA).concat(noteItemsEntryDTO.getIssueBinLocation().toString())));
			});
		}		
	}
	
	private void getMDNInspectionDetailsData(List<MaterialDispatchNoteItemsEntryDTO> noteItemsEntryDTOList, Long orgId, int index, Long mdnItemId) {
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByOrgId(orgId));				
		noteItemsEntryDTOList = this.getModel().getNoteItemsEntryMap().get(mdnItemId);
		if(null != noteItemsEntryDTOList) {
			this.getModel().getNoteItemsEntryDTOList().addAll(noteItemsEntryDTOList);
			this.getModel().getItemNumberList().clear();
			this.getModel().getNoteItemsEntryDTOList().forEach(
					noteItemsEntryDTO -> this.getModel().getItemNumberList().add(noteItemsEntryDTO.getItemNo()));
		}
	}
	
	
	@RequestMapping(params = "saveMDNItemEntryDetails", method = RequestMethod.POST)
	public ModelAndView saveMDNItemEntryDetails(final Model model, final HttpServletRequest request) {
		bindModel(request);
		int index = this.getModel().getIndexCount();
		List<MaterialDispatchNoteItemsEntryDTO> helperItemsEntryDTOList = new ArrayList<>();
		if (MainetConstants.FlagA.equalsIgnoreCase(this.getModel().getSaveMode())) {
			this.getModel().getNoteItemsEntryDTOList().forEach(noteItemsEntryDTO -> noteItemsEntryDTO.setSiItemId(
					this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getSiItemId()));
			helperItemsEntryDTOList.addAll(this.getModel().getNoteItemsEntryDTOList());
			this.getModel().getNoteItemsEntryMap().put(helperItemsEntryDTOList.get(0).getSiItemId(), helperItemsEntryDTOList);
		} else {
			this.getModel().getNoteItemsEntryDTOList().forEach(noteItemsEntryDTO -> noteItemsEntryDTO.setMdnItemId(
					this.getModel().getDispatchNoteDTO().getMatDispatchItemList().get(index).getMdnItemId()));
			helperItemsEntryDTOList.addAll(this.getModel().getNoteItemsEntryDTOList());
			this.getModel().getNoteItemsEntryMap().put(helperItemsEntryDTOList.get(0).getMdnItemId(), helperItemsEntryDTOList);
		}		
		this.getModel().getNoteItemsEntryDTOList().clear();
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@RequestMapping(params = "backToMDNForm", method = RequestMethod.POST)
	public ModelAndView backToMDNForm(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		return new ModelAndView("MaterialDispatchNoteForm", MainetConstants.FORM_NAME, this.getModel());
	}	
	
	
	@RequestMapping(params = "getItemNumbersByBin", method = RequestMethod.POST)
	public @ResponseBody List<String> getItemNumbersByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemId") Long itemId, final HttpServletRequest request) {
		bindModel(request);
		this.getModel().setItemNumberList(indentProcessService.getItemNumberListByBinLoc( binLocId, itemId, this.getModel().getDispatchNoteDTO().getIssueStoreId(), UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getItemNumbersMap().put(itemId.toString().concat(",").concat(binLocId.toString()),  this.getModel().getItemNumberList());	
		return this.getModel().getItemNumberList();
	}
	
	
	@RequestMapping(params = "getAvailableQntyByBin", method = RequestMethod.POST)
	public @ResponseBody Double getAvailableQntyByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemId") Long itemId,  @RequestParam("itemNo") String itemNo, final HttpServletRequest request) {
		bindModel(request);	
		return indentProcessService.fetchBalanceQuantityForIndent(UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getDispatchNoteDTO().getIssueStoreId(), itemId, binLocId, itemNo);
	}

	
	@RequestMapping(params = "getNotInBatchAvailableQntyByBin", method = RequestMethod.POST)
	public @ResponseBody Double getNotInBatchAvailableQntyByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemId") Long itemId, final HttpServletRequest request) {
		bindModel(request);	
		return indentProcessService.fetchBalanceQuantityForIndent(UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getDispatchNoteDTO().getIssueStoreId(), itemId, binLocId, null);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, params = { "doItemDeletionDetails" })
	public @ResponseBody boolean doItemDeletionDetails(final Model model, final HttpServletRequest request,
			@RequestParam("rowIndex") int rowIndex) {
		if(this.getModel().getNoteItemsEntryDTOList().size()>1)
			this.getModel().getNoteItemsEntryDTOList().remove(rowIndex);
		return true;
	}


	@RequestMapping(params = "searchMaterialDispatchData", method = RequestMethod.POST)
	public ModelAndView searchMaterialDispatchData(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long requestStoreId, @RequestParam(required = false) Long mdnId,
			@RequestParam(required = false) Long issueStoreId, @RequestParam(required = false) String status,
			@RequestParam(required = false) Long siId) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getSummaryFormLists(orgId);
		status = status.isEmpty() ? null : status;
		this.getModel().setDispatchNoteDTOList(materialDispatchNoteService.getmdnSummaryDataObjectList(orgId, mdnId, requestStoreId, 
						issueStoreId, status,  siId));
		this.getModel().getDispatchNoteDTO().setRequestStoreId(requestStoreId);
		this.getModel().getDispatchNoteDTO().setMdnId(mdnId);
		this.getModel().getDispatchNoteDTO().setIssueStoreId(issueStoreId);
		this.getModel().getDispatchNoteDTO().setStatus(status);
		this.getModel().getDispatchNoteDTO().setSiId(siId);
		return new ModelAndView("MaterialDispatchSearchForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
}
