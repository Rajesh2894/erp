package com.abm.mainet.workManagement.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDelayReasonDto;
import com.abm.mainet.workManagement.service.IWorkDelayReasonService;
import com.abm.mainet.workManagement.service.WmsProjectMasterService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.ui.model.WorkDelayReasonModel;

@Controller
@RequestMapping("DelayReason.html")
public class WorkDelayReasonController extends AbstractFormController<WorkDelayReasonModel> {

	@Autowired
	private WmsProjectMasterService projectMasterService;

	@Autowired
	private WorkDefinitionService workDefinationService;

	@Autowired
	private IWorkDelayReasonService workDelayReasonService;

	@Resource
	private IEmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));

		this.getModel().setDelayReasonDtos(workDelayReasonService
				.getAllData(UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null));

		return index();
	}

	@ResponseBody
	@RequestMapping(params = "searchForm", method = RequestMethod.POST)
	public ModelAndView searchDelayReason(final HttpServletRequest request, @RequestParam Long projId,
			@RequestParam Long workId, @RequestParam Date occuranceDate, @RequestParam String status) {
		this.bindModel(request);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
       
       
       this.getModel().setWorkDefinitionDtoList(workDefinationService
			.findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId));
        this.getModel().getDelayReasonDto().setProjId(projId);
       if(occuranceDate !=null)
       this.getModel().getDelayReasonDto().setDateOccuranceDesc(
               new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(occuranceDate));
        this.getModel().getDelayReasonDto().setStatus(status);
		this.getModel().setDelayReasonDtos(workDelayReasonService.getAllData(
				UserSession.getCurrent().getOrganisation().getOrgid(), projId, workId, occuranceDate, status));
		 this.getModel().getDelayReasonDto().setWorkId(workId);
		if(this.getModel().getDelayReasonDtos().isEmpty()) {
		 this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("work.management.vldn.grid.nodatafound"));
		}

		//return new ModelAndView("DelayReason/search", MainetConstants.FORM_NAME, this.getModel());
		ModelAndView mv = new ModelAndView("DelayReason/search", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@ResponseBody
	@RequestMapping(params = "viewDelayReason", method = RequestMethod.POST)
	public ModelAndView viewDelayReason(final HttpServletRequest request, @RequestParam Long delResId) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
		Organisation org = UserSession.getCurrent().getOrganisation();
		Department department = departmentService.getDepartment("WMS", MainetConstants.STATUS.ACTIVE);
		List<Employee> dto = employeeService.getAllListEmployeeByDeptId(org, department.getDpDeptid());

		this.getModel().setEmployees(dto);

		WorkDelayReasonDto delayReasonDto = workDelayReasonService.getDelayReasonById(orgId, delResId);

		this.getModel().setDelayReasonDto(delayReasonDto);

		return new ModelAndView("DelayReason/form", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "editDelayReason", method = RequestMethod.POST)
	public ModelAndView editSequenceMaster(final HttpServletRequest request, @RequestParam Long delResId) {		
		this.getModel().setSaveMode(MainetConstants.MODE_EDIT);

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(orgId));
		Organisation org = UserSession.getCurrent().getOrganisation();
		Department department = departmentService.getDepartment("WMS", MainetConstants.STATUS.ACTIVE);
		List<Employee> dto = employeeService.getAllListEmployeeByDeptId(org, department.getDpDeptid());

		this.getModel().setEmployees(dto);

		WorkDelayReasonDto delayReasonDto = workDelayReasonService.getDelayReasonById(orgId, delResId);
		delayReasonDto.setDateOccuranceDesc(
                new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(delayReasonDto.getDateOccurance()));
		delayReasonDto.setDateClearanceDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(delayReasonDto.getDateClearance()));

		this.getModel().setDelayReasonDto(delayReasonDto);

		return new ModelAndView("DelayReason/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "addDetailsofDelayReason", method = RequestMethod.POST)
	public ModelAndView addDelayReason(HttpServletRequest request) {
        this.bindModel(request);
		this.getModel().setSaveMode(MainetConstants.MODE_CREATE);

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setProjectMasterList(projectMasterService.getActiveProjectMasterListByOrgId(currentOrgId));
		Organisation org = UserSession.getCurrent().getOrganisation();
		Department department = departmentService.getDepartment("WMS", MainetConstants.STATUS.ACTIVE);
		List<Employee> dto = employeeService.getAllListEmployeeByDeptId(org, department.getDpDeptid());
		this.getModel().setEmployees(dto);

		return new ModelAndView("DelayReason/form", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.WORKS_NAME, method = RequestMethod.POST)
	public List<WorkDefinitionDto> getAllActiveWorksNameByProjectId(
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId) {

		List<WorkDefinitionDto> obj = workDefinationService
				.findAllWorkDefinationByProjId(UserSession.getCurrent().getOrganisation().getOrgid(), projId);

		return obj;

	}

}
