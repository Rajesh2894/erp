package com.abm.mainet.materialmgmt.ui.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.mapper.DesignationServiceMapper;
import com.abm.mainet.common.master.repository.DesignationJpaRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.dto.IndentIssueDto;
import com.abm.mainet.materialmgmt.mapper.ItemMasterServiceMapper;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.IndentProcessService;
import com.abm.mainet.materialmgmt.service.ItemMasterService;
import com.abm.mainet.materialmgmt.ui.model.IndentProcessModel;

@Controller
@RequestMapping(value = { "/IndentProcess.html", "/IndentProcessApproval.html" })
public class IndentProcessController extends AbstractFormController<IndentProcessModel> {

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IndentProcessService indentProcessService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Resource
	private ItemMasterServiceMapper itemMasterServiceMapper;
	
	@Resource
	private ItemMasterService itemMasterService;

	@Autowired
	DesignationService designationService;

	@Resource
	private DesignationJpaRepository designationJpaRepository;

	@Resource
	private DesignationServiceMapper designationServiceMapper;
	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private IAttachDocsService attachDocsService;

	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private IStoreMasterService iStoreMasterService;
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setListIndentProcessDTO(indentProcessService.searchIndentByStoreName(null, null, null, null, null, orgId));
		getSummaryFormLists(orgId);
		return defaultResult();
	}


	private void getSummaryFormLists(Long orgId) {
		this.getModel().setDepartmentIdNameList(departmentService.getAllDeptTypeNames());
		this.getModel().setStoreIdNameList(iStoreMasterService.getStoreIdAndNameList(orgId));
		this.getModel().setEmployees(employeeService.findAllEmpIntialInfoByOrg(orgId));
	}
	
	
	/**
	 * Add Indent form
	 * @param request
	 * @param storeid
	 * @param indentno
	 * @return
	 */
	@RequestMapping(params = "addIndent", method = RequestMethod.POST)
	public ModelAndView addStoreMaster(final HttpServletRequest request, final Model model) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployees(employeeService.findAllEmpIntialInfoByOrg(orgId));
		this.getModel().setStoreIdNameList(iStoreMasterService.getActiveStoreObjectListForAdd(orgId));
		this.getModel().setItemIdNameList(itemMasterService.getActiveItemIdNameListByOrgId(orgId));
		return new ModelAndView("indentProcessForm", MainetConstants.FORM_NAME, this.getModel());
	}
		

	/**
	 * search Indent summary
	 * @param request
	 * @param storeid
	 * @param indentno
	 */
	@ResponseBody
	@RequestMapping(params = "searchIndentStore", method = RequestMethod.POST)
	public ModelAndView searchVehicleMast(final HttpServletRequest request, @RequestParam Long storeid, @RequestParam String indentno,
			@RequestParam Long deptId, @RequestParam Long indenter, @RequestParam String status) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setListIndentProcessDTO(indentProcessService.searchIndentByStoreName(storeid, indentno, deptId,
				indenter, status, orgId));
		this.getModel().getIndentProcessDTO().setStoreid(storeid);
		this.getModel().getIndentProcessDTO().setIndentno(indentno);
		this.getModel().getIndentProcessDTO().setIndenter(indenter);
		this.getModel().getIndentProcessDTO().setStatus(status);
		getSummaryFormLists(orgId);
		return new ModelAndView("IndentProcessValidn", MainetConstants.FORM_NAME, this.getModel());
	}


	/** View Indent form
	 * @param indentid
	 */
	@RequestMapping(params = "viewDeptIndent", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewDeptIndent(Model model, @RequestParam("indentid") Long indentid, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployees(employeeService.findAllEmpIntialInfoByOrg(orgId));
		this.getModel().setIndentProcessDTO(indentProcessService.getIndentDataByIndentId(indentid, orgId));
				
		Map<Long, List<IndentIssueDto>> map = new LinkedHashMap<Long, List<IndentIssueDto>>();
		this.getModel().getIndentProcessDTO().getItem().forEach(issueItemList->{			
			List<IndentIssueDto> issueDtoList = new ArrayList<>();
			issueDtoList.addAll(issueItemList.getIndentIssueDtoList());
			map.put(issueItemList.getInditemid(), issueDtoList);	
		});
		this.getModel().getIndentIssueMap().putAll(map);
		
		return new ModelAndView("indentIssue", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	
	/**
	 * @return deptId, reportingmgr, desgId on selecting Indenter
	 */
	@ResponseBody
	@RequestMapping(params = "getEmpDetail", method = RequestMethod.POST)
	public Map<String, Object> getEmpDetail(@RequestParam("indenter") Long indenter, final HttpServletRequest request) {
		final Object[] empObject = employeeJpaRepository.getEmployeDetailsById(indenter, UserSession.getCurrent().getOrganisation().getOrgid());
		Object[] employee = (Object[]) empObject[0];
		Map<String, Object> object = new LinkedHashMap<String, Object>();			
		object.put("deptId", new Object[] {Long.valueOf(employee[0].toString()), employee[1].toString()});
		object.put("desgId", new Object[] {Long.valueOf(employee[2].toString()), employee[3].toString()});
		if(null !=  employee[5] && null != employee[6])
			object.put("reportingmgr", new Object[] {Long.valueOf(employee[4].toString()), employee[5].toString(), employee[6].toString()});
		return object;
	}

	/**
	 * @return location on selecting storeid
	 */
	@ResponseBody
	@RequestMapping(params = "getLocation", method = RequestMethod.POST)
	public Map<Long, String> getLocation(@RequestParam("storeid") Long storeid, final HttpServletRequest request) {
		final Object[] storeObject = iStoreMasterService.getStoreDetailsByStore(storeid, UserSession.getCurrent().getOrganisation().getOrgid());
		Object[] location = (Object[]) storeObject[0];
		Map<Long, String>  locationMap = new LinkedHashMap<>();
		locationMap.put(Long.valueOf(location[0].toString()), location[1].toString());
		return locationMap;
	}

	/**
	 * @return uom on selecting itemid
	 */
	@ResponseBody
	@RequestMapping(params = "getUom", method = RequestMethod.POST)
	public String getUom(@RequestParam("itemid") Long itemid, final HttpServletRequest request) {
		return CommonMasterUtility.getNonHierarchicalLookUpObject(itemMasterService.getUomByitemCode(UserSession.getCurrent().getOrganisation().getOrgid(),
				itemid), UserSession.getCurrent().getOrganisation()).getLookUpDesc();
	}
	
	
	@ResponseBody
	@RequestMapping(params = "submitIndentForm", method = RequestMethod.POST)
	public ModelAndView submitIndentForm(final HttpServletRequest request, final Model model) throws Exception {
		bindModel(request);
		if(this.getModel().saveForm())
			 return jsonResult(JsonViewObject.successResult(this.getModel().getSuccessMessage()));
		else {
			ModelAndView modelAndView = null;		
			modelAndView = new ModelAndView("indentIssue", MainetConstants.FORM_NAME, this.getModel());
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return modelAndView;
		}
	}
	
	
	@RequestMapping(params = "printDepartmentalIndent", method = RequestMethod.POST)
	public ModelAndView printDepartmentalIndent(final HttpServletRequest request, @RequestParam(required = false) String indentNo) {
		long levelCheck = this.getModel().getLevelcheck();
		if (0l == levelCheck) {
			Object[] nameObject = this.getModel().getStoreIdNameList().stream().filter(k -> Long
					.valueOf(((Object[]) k)[0].toString()).equals(this.getModel().getIndentProcessDTO().getStoreid()))
					.findFirst().orElse(null);
			this.getModel().getIndentProcessDTO().setStoreDesc(nameObject[1].toString());

			Map<Long, String> itemObjectMap = new HashMap<>();
			this.getModel().getItemIdNameList().forEach(
					itemObjec -> itemObjectMap.put(Long.parseLong(itemObjec[0].toString()), itemObjec[1].toString()));
			this.getModel().getIndentProcessDTO().getItem()
					.forEach(itemDetail -> itemDetail.setItemName(itemObjectMap.get(itemDetail.getItemid())));
		}

		Object[] employeeObject = this.getModel().getEmployees().stream().filter(k -> Long
				.valueOf(((Object[]) k)[3].toString()).equals(this.getModel().getIndentProcessDTO().getIndenter()))
				.findFirst().orElse(null);
		this.getModel().getIndentProcessDTO()
				.setIndenterName(employeeObject[0].toString() + " " + employeeObject[2].toString());

		if (0l == levelCheck)
			return new ModelAndView("DepartmentalIndentPrint", MainetConstants.FORM_NAME, this.getModel());
		else
			return new ModelAndView("DepartmentalIndentApprovalPrint", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView indentFormApproval(@RequestParam("appNo") final String appNo,
			@RequestParam("taskId") final String taskId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam(value = "taskName", required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.bindModel(httpServletRequest);
		IndentProcessModel indentModel = this.getModel();
		indentModel.setTaskId(actualTaskId);
		indentModel.getWorkflowActionDto().setReferenceId(appNo);
		indentModel.getWorkflowActionDto().setTaskId(actualTaskId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployees(employeeService.findAllEmpIntialInfoByOrg(orgId));
		indentModel.setIndentProcessDTO(indentProcessService.getIndentDataByIndentNo(appNo, orgId));
		getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId));
		getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(this.getModel().getIndentProcessDTO().getOrgid(),
				(MainetConstants.DEPT_SHORT_NAME.STORE + MainetConstants.WINDOWS_SLASH + this.getModel().getIndentProcessDTO().getIndentno()));
		this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("indentIssue", MainetConstants.FORM_NAME, indentModel);
	}

	
	@RequestMapping(params = "indentissueitem", method = RequestMethod.POST)
	public ModelAndView indentissueitem(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {	
		getModel().bind(httpServletRequest);
		int index = this.getModel().getIndexCount();
		this.getModel().getIndentProcessItemDTO().setInditemid(this.getModel().getIndentProcessDTO().getItem().get(index).getInditemid());
		this.getModel().getIndentProcessItemDTO().setIndentid(this.getModel().getIndentProcessDTO().getItem().get(index).getIndentid());
		this.getModel().setManagementCode(this.getModel().getIndentProcessDTO().getItem().get(index).getManagementCode());
		this.getModel().setBinLocList(indentProcessService.findAllBinLocationByItemID(UserSession.getCurrent().getOrganisation().getOrgid(), 
				 this.getModel().getIndentProcessDTO().getStoreid(), this.getModel().getIndentProcessDTO().getItem().get(index).getItemid()));
		
		this.getModel().getListIndentIssueDtos().clear();		
		List<IndentIssueDto> indentIssueDtoList = this.getModel().getIndentIssueMap().get(this.getModel().getIndentProcessItemDTO().getInditemid());
		if(indentIssueDtoList != null) {
			this.getModel().getListIndentIssueDtos().addAll(indentIssueDtoList);
			this.getModel().getListIndentIssueDtos().forEach(detailDto->{
				detailDto.setItemNumberList(this.getModel().getItemNumbersMap().get(this.getModel().getIndentProcessDTO().getItem().get(index).getItemid().toString().concat(",").concat(detailDto.getBinlocation().toString())));	
			});	
		}
		return new ModelAndView("itemissue", MainetConstants.FORM_NAME, this.getModel());
	}


	@ResponseBody
	@RequestMapping(params = "getItemNumbersByBin", method = RequestMethod.POST)
	public List<String> getItemNumbersByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemid") Long itemid, final HttpServletRequest request) {
		bindModel(request);
		this.getModel().setItemNumberList(indentProcessService.getItemNumberListByBinLoc( binLocId, itemid, this.getModel().getIndentProcessDTO().getStoreid(), UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getItemNumbersMap().put(itemid.toString().concat(",").concat(binLocId.toString()),  this.getModel().getItemNumberList());	
		return this.getModel().getItemNumberList();
	}

	
	@ResponseBody
	@RequestMapping(params = "getAvailableQntyByBin", method = RequestMethod.POST)
	public Double getAvailableQntyByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemid") Long itemid,  @RequestParam("itemNo") String itemNo, final HttpServletRequest request) {
		bindModel(request);	
		return indentProcessService.fetchBalanceQuantityForIndent(UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getIndentProcessDTO().getStoreid(), itemid, binLocId, itemNo);
	}
	
	
	@ResponseBody
	@RequestMapping(params = "getNotInBatchAvailableQntyByBin", method = RequestMethod.POST)
	public Double getNotInBatchAvailableQntyByBin(@RequestParam("binLocId") Long binLocId, @RequestParam("itemid") Long itemid, final HttpServletRequest request) {
		bindModel(request);
		return indentProcessService.fetchBalanceQuantityForIndent(UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getIndentProcessDTO().getStoreid(), itemid, binLocId, null);
	}
	
	@ResponseBody
	@RequestMapping(params = "saveIndentIssueForm", method = RequestMethod.POST)
	public ModelAndView saveIndentIssueForm(final HttpServletRequest request,final Model model) {
		bindModel(request);
		int index = this.getModel().getIndexCount();
		this.getModel().getListIndentIssueDtos().forEach(indentIssueDto->{
			indentIssueDto.setIndentid(this.getModel().getIndentProcessDTO().getItem().get(index).getIndentid());
			indentIssueDto.setInditemid(this.getModel().getIndentProcessDTO().getItem().get(index).getInditemid());
		});	
		List<IndentIssueDto> indentIssueHelperDTOs = new ArrayList<IndentIssueDto>();
		indentIssueHelperDTOs.addAll(this.getModel().getListIndentIssueDtos());
		this.getModel().getIndentIssueMap().put(this.getModel().getIndentProcessItemDTO().getInditemid(), indentIssueHelperDTOs);
		this.getModel().getListIndentIssueDtos().clear();
		return new ModelAndView("indentIssue", MainetConstants.FORM_NAME, this.getModel());
	}


	@RequestMapping(params = "backToMainForm", method = RequestMethod.POST)
	public ModelAndView backToMainForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Model model) {
		bindModel(httpServletRequest);
		return new ModelAndView("indentIssue", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = { "doItemDetailsDeletion" })
	public @ResponseBody boolean doItemDetailsDeletion(final Model model, final HttpServletRequest request,
			@RequestParam("rowIndex") int rowIndex) {
		if (this.getModel().getListIndentIssueDtos().size() > rowIndex)
			this.getModel().getListIndentIssueDtos().remove(rowIndex);
		return true;
	}

}
