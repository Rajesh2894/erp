package com.abm.mainet.materialmgmt.ui.controller;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

import com.abm.mainet.common.master.mapper.DesignationServiceMapper;
import com.abm.mainet.common.master.repository.DesignationJpaRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;

import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.materialmgmt.domain.DeptReturnEntity;
import com.abm.mainet.materialmgmt.dto.BinLocMasDto;
import com.abm.mainet.materialmgmt.dto.DeptItemDetailsDTO;
import com.abm.mainet.materialmgmt.dto.DeptReturnDTO;

import com.abm.mainet.materialmgmt.dto.IndentProcessDTO;

import com.abm.mainet.materialmgmt.mapper.ItemMasterServiceMapper;

import com.abm.mainet.materialmgmt.repository.DepartmentalReturnRepository;

import com.abm.mainet.materialmgmt.repository.ItemMasterRepository;
import com.abm.mainet.materialmgmt.repository.StoreMasterRepository;
import com.abm.mainet.materialmgmt.service.DepartmentalReturnService;
import com.abm.mainet.materialmgmt.service.IStoreMasterService;
import com.abm.mainet.materialmgmt.service.IndentProcessService;
import com.abm.mainet.materialmgmt.ui.model.DeptReturnModel;






@Controller
@RequestMapping(value = { "/DeptReturn.html", "/DeptReturnApproval.html" })
public class DeptReturnController extends AbstractFormController<DeptReturnModel> {

	

	@Autowired
	private IndentProcessService indentProcessService;

	@Autowired
	DepartmentService departmentService;

	@Resource
	private ItemMasterServiceMapper itemMasterServiceMapper;

	@Resource
	private ItemMasterRepository itemMasterRepository;

	@Autowired
	DesignationService designationService;

	@Resource
	private DesignationJpaRepository designationJpaRepository;

	@Resource
	private DesignationServiceMapper designationServiceMapper;
	
	
	@Autowired
	private DepartmentalReturnRepository departmentalReturnRepository;
	
	@Resource
	private EmployeeJpaRepository employeeJpaRepository;

	@Resource
	private StoreMasterRepository storeMasterRepository;
	
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;
	
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private DepartmentalReturnService departmentalReturnService;
	
	
	
	@Resource
	private IStoreMasterService iStoreMasterService;
   

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {      
		this.sessionCleanup(httpServletRequest);
		String formName = "DeptReturn";
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setListDeptReturnDTO(departmentalReturnService.fetchindentReturnSummaryData(orgid));
		getSummaryFormLists(orgid);
		return new ModelAndView(formName, MainetConstants.FORM_NAME, getModel());
	}


	
	
	/**
	 * Add Indent Return form
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addDeptReturn", method = RequestMethod.POST)
	public ModelAndView addDeptReturn(final HttpServletRequest request, final Model model) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.WorksManagement.ADD);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployees(departmentalReturnRepository.findAllEmpIntialInfoByOrg(orgId));
		//this.getModel().setStoreIdNameList(storeMasterRepository.getStoreIdAndNameList(orgId));
		this.getModel().setItemIdNameList(itemMasterRepository.getItemIdNameListByOrgId(orgId));
		return new ModelAndView("addDeptReturnform", MainetConstants.FORM_NAME, this.getModel());
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

	


	
	@ResponseBody
	@RequestMapping(params = "searchindentData", method = RequestMethod.POST)
	public ModelAndView searchIndentData( final HttpServletRequest request, final Model model) {
		this.getModel().bind(request);
		
			DeptReturnDTO dto1 = departmentalReturnService.findItemInfoByIndentIdORG(this.getModel().getDeptReturnDto(), UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setDeptReturnDto(dto1);
		
		return new ModelAndView("addDeptReturnform", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	 @ResponseBody
	    @RequestMapping(params = "IndetNoEmpDetails", method = RequestMethod.POST)
	    public Map<Long, String> getIndentList(@RequestParam("indenter") final long indenter,final HttpServletRequest request) {
	        final Map<Long, String> map = new HashMap<>();
	        final List<IndentProcessDTO> indentProcessDTO = departmentalReturnService.findIndentByEmpId(indenter,
	                UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.FlagI);
	        if (indentProcessDTO != null) {
	            for (final IndentProcessDTO dto : indentProcessDTO) {
	                map.put(dto.getIndentid(), dto.getIndentno());
	            }
	        }
	        this.getModel().setListIndentProcessDTO(indentProcessDTO);
	        return map;
	    }
	 
	 @ResponseBody
		@RequestMapping(params = "getIndentDetail", method = RequestMethod.POST)
		public Map<String, Object> getIndentDetail(@RequestParam("indentid") Long indentid, final HttpServletRequest request) {
			final Object[] data = departmentalReturnRepository.getStoreDetailsByIndentId(indentid, UserSession.getCurrent().getOrganisation().getOrgid());
			Object[] indentadata = (Object[]) data[0];
			Map<String, Object> object = new LinkedHashMap<String, Object>();			
			object.put("locId", new Object[] {Long.valueOf(indentadata[4].toString()),indentadata[0].toString()});
			object.put("storeId", new Object[] {Long.valueOf(indentadata[1].toString()),indentadata[2].toString()});
			object.put("beneficiary", new Object[] {indentadata[3].toString()});
			
			
			return object;
		}
	 
	 @RequestMapping(params = "showDetails", method = RequestMethod.POST)
		public ModelAndView indentFormApproval(@RequestParam("appNo") final String appNo,
				@RequestParam("taskId") final String taskId,
				@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
				@RequestParam(value = "taskName", required = false) final String taskName,
				final HttpServletRequest httpServletRequest, final Model model) {
				this.sessionCleanup(httpServletRequest);
			
			this.bindModel(httpServletRequest);
			DeptReturnModel deptModel = this.getModel();
			int index = this.getModel().getIndexCount();
			deptModel.setTaskId(actualTaskId);
			deptModel.getWorkflowActionDto().setReferenceId(appNo);
			deptModel.getWorkflowActionDto().setTaskId(actualTaskId);
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getModel().setItemIdNameList(itemMasterRepository.getItemIdNameListByOrgId(orgId));
			
			this.getModel().setEmployees(departmentalReturnRepository.findAllEmpIntialInfoByOrg(orgId));
			 DeptReturnDTO dto=departmentalReturnService.getIndentReturnDataById(appNo,orgId);
			 final Map<Long, String> map = new HashMap<>();
		        final List<IndentProcessDTO> indentProcessDTO = departmentalReturnService.findIndentByEmpId(dto.getIndenter(),
		                UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.FlagI);
		        if (indentProcessDTO != null) {
		            for (final IndentProcessDTO indentdto : indentProcessDTO) {
		                map.put(indentdto.getIndentid(), indentdto.getIndentno());
		            }
		        }
		    this.getModel().setListIndentProcessDTO(indentProcessDTO);
			deptModel.setDeptReturnDto(dto);
			getModel().setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId));
			getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
			if(getModel().isLastChecker()) {
				deptModel.setApprovalLastFlag(MainetConstants.FlagY);
			}
			else {
				deptModel.setApprovalLastFlag(MainetConstants.FlagN);
			}
			this.getModel().setBinLocList(indentProcessService.findAllBinLocationByItemID(UserSession.getCurrent().getOrganisation().getOrgid(), 
					 this.getModel().getDeptReturnDto().getStoreid(), this.getModel().getDeptReturnDto().getDeptItemDetailsDTOList().get(index).getItemid()));
			
			return new ModelAndView("addDeptReturnform", MainetConstants.FORM_NAME, deptModel);
		}
	
	
	 @RequestMapping(method = RequestMethod.POST, params = { "doDetailsDeletion" })
		@ResponseBody
		public boolean doItemDeletionDetails(@RequestParam(name = "id", required = true) Long id, final HttpServletRequest request) {
			bindModel(request);
			List<DeptItemDetailsDTO> item = this.getModel().getDeptReturnDto().getDeptItemDetailsDTOList();
			List<DeptItemDetailsDTO> beanList = item.stream().filter(c -> c.getPrimId().longValue() != id.longValue()).collect(Collectors.toList());
			if (!beanList.isEmpty())
				this.getModel().getDeptReturnDto().setDeptItemDetailsDTOList(beanList);
			return true;
		}
	 
	@Override
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest request) throws Exception {
		this.sessionCleanup(request);
		
		int index = this.getModel().getIndexCount();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		this.bindModel(request);
		DeptReturnModel deptReturnModel = this.getModel();
		this.getModel().setCompletedFlag(MainetConstants.FlagY);
		 this.getModel().setApprovalLastFlag(MainetConstants.FlagY);
		deptReturnModel.setTaskId(taskId);
		deptReturnModel.getWorkflowActionDto().setReferenceId(applicationId);
		deptReturnModel.getWorkflowActionDto().setTaskId(taskId);
		DeptReturnDTO dto = departmentalReturnService.getIndentReturnDataById(applicationId, orgId);
		deptReturnModel.setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(taskId));
		deptReturnModel.setLevelcheck(iWorkflowTaskService.findByTaskId(taskId).getCurentCheckerLevel());
		this.getModel().setEmployees(departmentalReturnRepository.findAllEmpIntialInfoByOrg(orgId));
		final Map<Long, String> map = new HashMap<>();
		final List<IndentProcessDTO> indentProcessDTO = departmentalReturnService.findIndentByEmpId(dto.getIndenter(),
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagI);
		if (indentProcessDTO != null) {
			for (final IndentProcessDTO indentdto : indentProcessDTO) {
				map.put(indentdto.getIndentid(), indentdto.getIndentno());
			}
		}
		this.getModel().setListIndentProcessDTO(indentProcessDTO);
		deptReturnModel.setDeptReturnDto(dto);

		
		this.getModel().setBinLocList(indentProcessService.findAllBinLocationByItemID(
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getDeptReturnDto().getStoreid(),
				this.getModel().getDeptReturnDto().getDeptItemDetailsDTOList().get(index).getItemid()));
		 

		return new ModelAndView("addDeptReturnform", MainetConstants.FORM_NAME, this.getModel());
	}
	
	private void getSummaryFormLists(Long orgId) {

		this.getModel().setStoreIdNameList(iStoreMasterService.getStoreIdAndNameList(orgId));

	}
	
	@RequestMapping(params = "searchIndentReturn", method = RequestMethod.POST)
	public ModelAndView searchStoresReturnData(final HttpServletRequest request, final Model model,
			@RequestParam(required = false) String dreturnno, @RequestParam(required = false) Long indentid,
			@RequestParam(required = false) Date fromDate, @RequestParam(required = false) Date toDate,
			@RequestParam(required = false) Long storeid, @RequestParam(required = false) Character status)
			throws ParseException {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().getDeptReturnDto().setStoreid(storeid);
		this.getModel().getDeptReturnDto().setDreturnno(dreturnno);
		this.getModel().getDeptReturnDto().setFromDate(fromDate);
		this.getModel().getDeptReturnDto().setToDate(toDate);
		this.getModel().getDeptReturnDto().setIndentid(indentid);
		this.getModel().getDeptReturnDto().setStatus(status);
		this.getModel().setListDeptReturnDTO(departmentalReturnService.searchStoresReturnData(dreturnno, indentid,
				fromDate, toDate, storeid, status, orgId));
		getSummaryFormLists(orgId);
		
		return new ModelAndView("DeptReturnList", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "viewDeptIndentReturn", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewDeptIndentReturn(Model model, @RequestParam("dreturnid") Long dreturnid,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		DeptReturnModel deptModel = this.getModel();
		this.getModel().setSaveMode(MainetConstants.WorksManagement.VIEW);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		this.getModel().setEmployees(departmentalReturnRepository.findAllEmpIntialInfoByOrg(orgId));
		DeptReturnEntity entity = departmentalReturnRepository.findByIndentReturnId(dreturnid, orgId);

		DeptReturnDTO dto = departmentalReturnService.getIndentReturnDataById(entity.getDreturnno(), orgId);
		final Map<Long, String> map = new HashMap<>();
		final List<IndentProcessDTO> indentProcessDTO = departmentalReturnService.findIndentByEmpId(dto.getIndenter(),
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagI);
		if (indentProcessDTO != null) {
			for (final IndentProcessDTO indentdto : indentProcessDTO) {
				map.put(indentdto.getIndentid(), indentdto.getIndentno());
			}
		}
		final List<BinLocMasDto>  BinLocMasDto=departmentalReturnService.getBinlocationList(orgId);
		this.getModel().setBinLocList(BinLocMasDto);
		this.getModel().setListIndentProcessDTO(indentProcessDTO);
		deptModel.setDeptReturnDto(dto);
		return new ModelAndView("addDeptReturnform", MainetConstants.FORM_NAME, this.getModel());
	}

}
