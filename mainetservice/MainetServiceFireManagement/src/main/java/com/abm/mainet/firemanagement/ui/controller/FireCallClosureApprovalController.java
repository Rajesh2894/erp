package com.abm.mainet.firemanagement.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IFireCallRegisterService;
import com.abm.mainet.firemanagement.ui.model.FireCallClosureApprovalModel;

@Controller
@RequestMapping(value = "/FireCallClosureApproval.html")
public class FireCallClosureApprovalController extends AbstractFormController<FireCallClosureApprovalModel> {

	/**
	 * @param complainNo
	 * @param actualTaskId
	 * @param serviceId
	 * @param workflowId
	 * @param taskName
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;

	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IFireCallRegisterService  iFireCallRegisterService;
	
	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	
	@Autowired
    private IAttachDocsService iAttachDocsService;
	
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView workorder(
			@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();

		FireCallClosureApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("FireCallClosureApproval.html");
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

		
	    String result = complainNo;
	    int len = result.length();
	    int len1=result.indexOf(Constants.str);
	    String result1=result.substring(len1+1, len);
	    
	    String varTaskName = taskName;
	    int lenth = varTaskName.trim().length();
	    String varTaskNm = varTaskName.substring(lenth-2, lenth);
	    
	    if(varTaskNm.equalsIgnoreCase("L1")) {
	    	model.addAttribute("taskNameToCheck" , false);
	    }
	    else {
	    	model.addAttribute("taskNameToCheck" , true);
	    }
	    
	    Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
	    httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		List<FireCallRegisterDTO> list1 = iFireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),secuDeptId);
		httpServletRequest.setAttribute("listVeh", list1);
		//model.addAttribute("listVeh" , list1);
	    FireCallRegisterDTO closerDto = iFireCallRegisterService.findOne1(Long.valueOf(result1));
	
		if(closerDto!=null) {
			approvalModel.setEntity(closerDto); 
			model.addAttribute("departments", loadDepartmentList());
			model.addAttribute("locations", loadLocation());
			approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
			approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
			approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
			Long fireDeptId = departmentService.getDepartmentIdByDeptCode("FM");
			model.addAttribute("fireDeptEmployee", employeeService.getEmployeeData(fireDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
				
		}
		
		//fetch uploaded document start
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setIdfId(Constants.FIRE_CALL_REG_TABLE + MainetConstants.WINDOWS_SLASH + closerDto.getCmplntNo().toString());
		List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), requestDTO.getIdfId());
		this.getModel().setAttachDocsList(attachDocs);
		List<AttachDocs> attachDoc1 = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),Constants.FIRE_CALL_CLOSURE + MainetConstants.WINDOWS_SLASH +closerDto.getClosureId()+MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setAttachDocs(attachDoc1);
		//fetch uploaded document end

		return new ModelAndView("FireCallClosureApproval", MainetConstants.FORM_NAME, this.getModel());

	}

	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
	
	@ResponseBody
	@RequestMapping(params = "saveFireCallClosureApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveFireCallClosureApprovalDetails(String.valueOf(this.getModel().getEntity().getCmplntId()), UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("FireWfStatus",	this.getModel().getEntity().getCallRegClosureStatus());	    
		return object;
   }
	
	
}
