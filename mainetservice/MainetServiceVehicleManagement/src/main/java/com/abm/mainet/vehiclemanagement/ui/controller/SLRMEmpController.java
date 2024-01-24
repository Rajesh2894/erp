package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.SLRMEmpModel;

@Controller
@RequestMapping("/vehicleEmpDetails.html")
public class SLRMEmpController extends AbstractFormController<SLRMEmpModel> {

	@Autowired
	DesignationService designationService;

	@Autowired
	DepartmentService departmentService;

	@Resource
	private ISLRMEmployeeMasterService sLRMEmployeeMasterService;

	@Resource
	private IEmployeeService employeeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        this.getModel().setsLRMEmployeeMasterDtoList(sLRMEmployeeMasterService.searchEmployeeList(null, null, null, orgId)); 
        httpServletRequest.setAttribute("sLRMEmployeeMasterlist", this.getModel().getsLRMEmployeeMasterDtoList());
        getSummarySearchData(orgId);
        return defaultResult();
	}
		
	private void getSummarySearchData(Long orgId) {
		long languageId = UserSession.getCurrent().getLanguageId();
		this.getModel().setDesignationList(designationService.findDesgByOrgId(orgId));
		Map<Long, String> desigMap;
		if(1L == languageId)
			desigMap = this.getModel().getDesignationList().stream()
				.collect(Collectors.toMap(DesignationBean::getDsgid, DesignationBean::getDsgname));
		else
			desigMap = this.getModel().getDesignationList().stream()
			.collect(Collectors.toMap(DesignationBean::getDsgid, DesignationBean::getDsgnameReg));	
		
		this.getModel().setDeptObjectList(departmentService.getAllDeptTypeNames());		
		Map<Long, String> departmentMap;
		if(1L == languageId)
			departmentMap = this.getModel().getDeptObjectList().stream().collect(
					Collectors.toMap(departmentArr -> (Long) departmentArr[0], departmentArr -> (String) departmentArr[1]));
		else
			departmentMap = this.getModel().getDeptObjectList().stream().collect(
				Collectors.toMap(departmentArr -> (Long) departmentArr[0], departmentArr -> (String) departmentArr[2]));
	
		this.getModel().getsLRMEmployeeMasterDtoList().forEach(emp -> {
			emp.setDesigName(desigMap.get(emp.getDesgId()));
			emp.setDeptName(departmentMap.get(emp.getMrfId()));
		});
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "addEmployeeMaster")
	public ModelAndView addEmployeeMaster(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(Constants.SaveMode.CREATE);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setEmployeeObjectList(
				sLRMEmployeeMasterService.getEmployeesForVehicleDriverMas(orgId, MainetConstants.DESIGNATION_DRIVER));
		return new ModelAndView("SLRMEmpAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = "viewEmployeeMaster")
	public ModelAndView viewEmployeeMaster(@RequestParam Long empId, final HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setsLRMEmployeeMasterDto(sLRMEmployeeMasterService.searchEmployeeDetails(empId, orgId,  UserSession.getCurrent().getLanguageId()));
		this.getModel().setSaveMode(Constants.SaveMode.VIEW);
		return new ModelAndView("SLRMEmpEdit", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = "editEmployeeMaster")
	public ModelAndView editEmployeeMaster(@RequestParam Long empId, final HttpServletRequest request) {
		sessionCleanup(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setsLRMEmployeeMasterDto(sLRMEmployeeMasterService.searchEmployeeDetails(empId, orgId,  UserSession.getCurrent().getLanguageId()));
		this.getModel().setSaveMode(Constants.SaveMode.EDIT);
		return new ModelAndView("SLRMEmpEdit", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = "searchEmployeeMaster")
	public ModelAndView searchEmployeeMaster(@RequestParam Long mrfId, @RequestParam Long empId, final HttpServletRequest request) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setsLRMEmployeeMasterDtoList(sLRMEmployeeMasterService.searchEmployeeList(empId, null, mrfId, orgId));
		request.setAttribute("sLRMEmployeeMasterlist", sLRMEmployeeMasterService.searchEmployeeList(null, null, null, orgId));
		getSummarySearchData(orgId);
		return new ModelAndView("SLRMEmpSearch", MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "checkEmpCode", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody boolean checkEmpCodeDup(@RequestParam("empUId") final String empUId, final HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		boolean result = sLRMEmployeeMasterService.checkEmpCodeByEmpCode(empUId, orgid);
		return result;
	}

	
	@RequestMapping(params = "checkDuplicateNumber", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody boolean checkDuplicateNumber(@RequestParam("empMobNo") final String empMobNo, final HttpServletRequest request) {
		getModel().bind(request);
		boolean result = sLRMEmployeeMasterService.checkDuplicateMob(UserSession.getCurrent().getOrganisation().getOrgid(), empMobNo,
				this.getModel().getsLRMEmployeeMasterDto().getEmpId());
		return result;
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = "getEmpDetail")
	public ModelAndView getEmpDetail(@RequestParam("empUId") final String empUId, final HttpServletRequest request) {
		this.getModel().setsLRMEmployeeMasterDto(sLRMEmployeeMasterService.getEmpDetails(Long.valueOf(empUId), UserSession.getCurrent().getLanguageId()));
		return new ModelAndView("SLRMEmpAdd", MainetConstants.FORM_NAME, this.getModel());
	}

}
