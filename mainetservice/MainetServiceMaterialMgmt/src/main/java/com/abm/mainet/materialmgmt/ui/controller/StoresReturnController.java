package com.abm.mainet.materialmgmt.ui.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.service.IBinMasService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.IStoressReturnService;
import com.abm.mainet.materialmgmt.ui.model.StoresReturnModel;

@Controller
@RequestMapping(value = { "/StoresReturn.html" })
public class StoresReturnController extends AbstractFormController<StoresReturnModel> {

	@Resource
	private IStoressReturnService storessReturnService;
	
	@Resource
	private IStoreMasterService storeMasterService;
	
	@Resource
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Resource
	private IBinMasService binMasService; 

	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoresReturnDTOList(
				storessReturnService.fetchStoreReturnSummaryData(null, null, null, null, null, null, orgId));
		getSummaryFormLists(orgId);
		return index();
	}

	private void getSummaryFormLists(Long orgId) {
		this.getModel().setCommonHelpDocs("MaterialDispatchNote.html");
		this.getModel().setStoreIdNameList(storeMasterService.getStoreIdAndNameList(orgId));
		Map<Long, String> storeMap = new HashMap<>();
		this.getModel().getStoreIdNameList().forEach(storeIdNameObject -> {
			storeMap.put(Long.valueOf(storeIdNameObject[0].toString()), storeIdNameObject[1].toString());
		});
		this.getModel().setMdnIdNameList(storessReturnService.getMDNNumbersReturned(orgId));
		Map<Long, String> mdnMap = new HashMap<>();
		this.getModel().getMdnIdNameList().forEach(MdnIdNameObject -> {
			mdnMap.put(Long.valueOf(MdnIdNameObject[0].toString()), MdnIdNameObject[1].toString());
		});

		this.getModel().getStoresReturnDTOList().forEach(storesReturnDTO -> {
			storesReturnDTO.setMdnNumber(mdnMap.get(storesReturnDTO.getMdnId()));
			storesReturnDTO.setIssueStoreName(storeMap.get(storesReturnDTO.getIssueStoreId()));
			storesReturnDTO.setRequestStoreName(storeMap.get(storesReturnDTO.getRequestStoreId()));
		});
	}

	@RequestMapping(params = "addStoresReturnForm", method = RequestMethod.POST)
	public ModelAndView addStoresReturn(final Model model, final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setMdnIdNameList(storessReturnService.getMDNNumbersForReturn(orgId));
		return new ModelAndView("StoresReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "viewStoresReturnForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewStoresReturn(final HttpServletRequest request, @RequestParam("storeReturnId") Long storeReturnId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoresReturnDTO(storessReturnService.fetchStoreRetunDataByReturnId(storeReturnId, orgId));
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByOrgId(orgId));				
		return new ModelAndView("StoresReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}
	

	@RequestMapping(params = "getDataByNDNId", method = RequestMethod.POST)
	public ModelAndView searchMDNData(final HttpServletRequest request, final Model model) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Date returnDate = this.getModel().getStoresReturnDTO().getStoreReturnDate();
		this.getModel().setStoresReturnDTO(storessReturnService
				.fetchRejectedItemDataByMDNId(this.getModel().getStoresReturnDTO().getMdnId(), orgId));
		this.getModel().getStoresReturnDTO().setStoreReturnDate(returnDate);
		return new ModelAndView("StoresReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "searchStoresReturn", method = RequestMethod.POST)
	public ModelAndView searchStoresReturnData(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) Long storeReturnId, @RequestParam(required = false) Long mdnId,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,
			@RequestParam(required = false) Long issueStoreId, @RequestParam(required = false) Long requestStoreId)
			throws ParseException {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoresReturnDTOList(storessReturnService.fetchStoreReturnSummaryData(storeReturnId, mdnId,
				fromDate, toDate, issueStoreId, requestStoreId, orgId));
		getSummaryFormLists(orgId);
		this.getModel().getStoresReturnDTO().setStoreReturnId(storeReturnId);
		this.getModel().getStoresReturnDTO().setMdnId(mdnId);
		this.getModel().getStoresReturnDTO().setFromDate(fromDate);
		this.getModel().getStoresReturnDTO().setToDate(toDate);
		this.getModel().getStoresReturnDTO().setIssueStoreId(issueStoreId);
		this.getModel().getStoresReturnDTO().setRequestStoreId(requestStoreId);
		return new ModelAndView("StoresReturnSearchForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	/** 
	 * Store Return Item Entry
	 * @param appNo
	 */
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView indentFormApproval(@RequestParam("appNo") final String appNo, @RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		this.bindModel(httpServletRequest);
		this.getModel().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setReferenceId(appNo);
		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setStoresReturnDTO(storessReturnService.fetchStoreRetunDataByReturnNo(appNo, orgId));
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByOrgId(orgId));				
		return new ModelAndView("StoresReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	@Override
	public ModelAndView viewDetails(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long actualTaskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		this.sessionCleanup(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		this.getModel().setStoresReturnDTO(storessReturnService.fetchStoreRetunDataByReturnNo(appNo, orgId));
		getModel().setLevelCheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		this.getModel().setBinLocObjectList(binMasService.getbinLocIdAndNameListByOrgId(orgId));				
		return new ModelAndView("StoresReturnForm", MainetConstants.FORM_NAME, this.getModel());
	}

}
