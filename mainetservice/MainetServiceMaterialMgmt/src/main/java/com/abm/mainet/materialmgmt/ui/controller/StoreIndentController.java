package com.abm.mainet.materialmgmt.ui.controller;

import java.util.LinkedHashMap;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.service.IStoreIndentService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.StoreIndentModel;

@Controller
@RequestMapping(value = { "/StoreIndent.html" })
public class StoreIndentController extends AbstractFormController<StoreIndentModel> {

	@Resource
	private ItemMasterService itemMasterService;

	@Resource
	private IStoreMasterService iStoreMasterService;

	@Resource
	private IStoreIndentService storeIndentService;
	
	@Resource
	private IWorkflowTaskService iWorkflowTaskService;


	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreIndentDtoList(storeIndentService.getStoreIndentSummaryList(null, null, null, null, orgId));
		getSummaryFormLists(orgId);
		return new ModelAndView("StoreIndentSummaryForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	private void getSummaryFormLists(Long orgId) {
		this.getModel().setCommonHelpDocs("StoreIndent.html");
		this.getModel().setStoreIdNameList(iStoreMasterService.getStoreIdAndNameList(orgId));
		Map<Long, String> storeObjectMap = new LinkedHashMap<>();
		this.getModel().getStoreIdNameList().forEach(storeIdName -> storeObjectMap
				.put(Long.parseLong(storeIdName[0].toString()), storeIdName[1].toString()));

		this.getModel().getStoreIndentDtoList().forEach(storeIndentDto -> {
			storeIndentDto.setRequestStoreName(storeObjectMap.get(storeIndentDto.getRequestStore()));
			storeIndentDto.setIssueStoreName(storeObjectMap.get(storeIndentDto.getIssueStore()));
		});
	}
	
	/** 
	 * Add Store Indent form
	 */
	@RequestMapping(params = "addStoreIndentForm", method = RequestMethod.POST)
	public ModelAndView addPurchaseReturn(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreIdNameList(iStoreMasterService.getActiveStoreObjectListForAdd(orgId));
		this.getModel().setItemIdNameList(itemMasterService.getActiveItemIdNameListByOrgId(orgId));
		return new ModelAndView("StoreIndentForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/** 
	 * View Store Indent form
	 * @param storeIndentId
	 */
	@RequestMapping(params = "viewStoreIndent", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewStoreIndent(Model model, @RequestParam("storeIndentId") Long storeIndentId, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setStoreIndentDto(storeIndentService.getStoreIndentDataByIndentId(storeIndentId,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("StoreIndentForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	/**
	 * @param storeId
	 */
	@RequestMapping(params = "getStoreDetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getStoreDetails(@RequestParam("storeId") Long storeId, final HttpServletRequest request) {
		final Object[] storeObjectData = iStoreMasterService.getStoreDetailsByStore(storeId, UserSession.getCurrent().getOrganisation().getOrgid());
		Object[] storeObject = (Object[]) storeObjectData[0];
		Map<String, Object> object = new LinkedHashMap<String, Object>();			
		object.put("location", new Object[] {Long.valueOf(storeObject[0].toString()), storeObject[1].toString()});
		object.put("incharge", new Object[] {Long.valueOf(storeObject[3].toString()), (storeObject[4].toString() + 
				MainetConstants.WHITE_SPACE + storeObject[5].toString())});
		return object;
	}

	/**
	 * @param itemId
	 */
	@RequestMapping(params = "getUom", method = RequestMethod.POST)
	public @ResponseBody String getUom(@RequestParam("itemId") Long itemId, final HttpServletRequest request) {
		return CommonMasterUtility.getNonHierarchicalLookUpObject(
				itemMasterService.getUomByitemCode(UserSession.getCurrent().getOrganisation().getOrgid(), itemId),
				UserSession.getCurrent().getOrganisation()).getLookUpDesc();
	}
	

	/**
	 * @param requestStore
	 * @param storeIndentId
	 * @param issueStore
	 * @param status
	 */
	@RequestMapping(params = "StoreIndentSearchForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView searchStoreIndent(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long requestStore, @RequestParam(required = false) Long storeIndentId,
			@RequestParam(required = false) Long issueStore, @RequestParam(required = false) String status) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoreIndentDtoList(
				storeIndentService.getStoreIndentSummaryList(requestStore, storeIndentId, issueStore, status, orgId));
		getSummaryFormLists(orgId);
		this.getModel().getStoreIndentDto().setRequestStore(requestStore);
		this.getModel().getStoreIndentDto().setStoreIndentId(storeIndentId);
		this.getModel().getStoreIndentDto().setIssueStore(issueStore);
		this.getModel().getStoreIndentDto().setStatus(status);
		return new ModelAndView("StoreIndentSearchForm", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView indentFormApproval(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		this.getModel().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setReferenceId(appNo);
		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		this.getModel().setStoreIndentDto(storeIndentService.getStoreIndentDataByIndentNo(appNo,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		return new ModelAndView("StoreIndentForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
}
