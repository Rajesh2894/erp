package com.abm.mainet.validitymaster.ui.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.validitymaster.dto.EmployeeWardZoneMappingDto;
import com.abm.mainet.validitymaster.service.IEmployeeWardZoneMappingService;
import com.abm.mainet.validitymaster.ui.model.EmployeeWardZoneMappingModel;

/**
 * @author cherupelli.srikanth
 * @since 29 nov 2021
 */

@Controller
@RequestMapping("/EmployeeWardZoneMapping.html")
public class EmployeeWardZoneMappingController extends AbstractFormController<EmployeeWardZoneMappingModel>{

	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private IEmployeeWardZoneMappingService employeeWardZoneMappingService;
	
	@RequestMapping(method= {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(MainetConstants.FlagA);
		this.getModel().setFormFlag(MainetConstants.FlagN);
		List<EmployeeBean> employeeList = employeeService.getAllActiveEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
		if(CollectionUtils.isNotEmpty(employeeList)) {
			employeeList.forEach(emp ->{
				StringBuilder name = new StringBuilder();
				name.append(emp.getEmpname());
				if(StringUtils.isNotBlank(emp.getEmpmname())) {
					name.append(MainetConstants.WHITE_SPACE);
					name.append(emp.getEmpmname());
				}
				if(StringUtils.isNotBlank(emp.getEmplname())) {
					name.append(MainetConstants.WHITE_SPACE);
					name.append(emp.getEmplname());
				}
				emp.setEmpname(name.toString());
			});
		}
		this.getModel().setEmployeeList(employeeList);
		List<EmployeeWardZoneMappingDto> wardZoneMappingByOrgId = employeeWardZoneMappingService.getWardZoneMappingByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		if(CollectionUtils.isNotEmpty(wardZoneMappingByOrgId)) {
			wardZoneMappingByOrgId.forEach(wardEmployee ->{
				List<EmployeeBean> employeeWardList = employeeList.stream().filter(emp -> emp.getEmpId().equals(wardEmployee.getEmpId())).collect(Collectors.toList());
				if(CollectionUtils.isNotEmpty(employeeWardList)) {
					wardEmployee.setEmpName(employeeWardList.get(0).getEmpname());
				}
				
			});
		}
		this.getModel().setEmpWardZoneMstList(wardZoneMappingByOrgId);
		return index();
	}
	
	
	  @RequestMapping(params = MainetConstants.EDIT, method = RequestMethod.POST)
	    public ModelAndView editOrViewWardZone(
		    @RequestParam("empId") Long empId,
		    @RequestParam(value = MainetConstants.AdvertisingAndHoarding.SAVE_MODE, required = true) String saveMode,
		    HttpServletRequest request) {
		this.getModel().setSaveMode(saveMode);
		this.getModel().setFormFlag(MainetConstants.FlagN);
		EmployeeWardZoneMappingDto wardZoneMappingByOrgIdAndEmpId = employeeWardZoneMappingService.getWardZoneMappingByOrgIdAndEmpId(UserSession.getCurrent().getOrganisation().getOrgid(), empId);
		if (wardZoneMappingByOrgIdAndEmpId != null) {
		    this.getModel().setEmployeeWardZoneMapDto(wardZoneMappingByOrgIdAndEmpId);
		}
		return new ModelAndView("EmployeeWardZoneMappingValidn", MainetConstants.FORM_NAME,
			this.getModel());

	    }
	  
	  @RequestMapping(params = "addWardZone", method = RequestMethod.POST)
	    public ModelAndView addAdvertiserMaster(HttpServletRequest request) {
		this.getModel().setSaveMode(MainetConstants.FlagA);
		List<EmployeeBean> employeeList = employeeService.getAllActiveEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
		if(CollectionUtils.isNotEmpty(employeeList)) {
			employeeList.forEach(emp ->{
				StringBuilder name = new StringBuilder();
				name.append(emp.getEmpname());
				if(StringUtils.isNotBlank(emp.getEmpmname())) {
					name.append(MainetConstants.WHITE_SPACE);
					name.append(emp.getEmpmname());
				}
				if(StringUtils.isNotBlank(emp.getEmplname())) {
					name.append(MainetConstants.WHITE_SPACE);
					name.append(emp.getEmplname());
				}
				emp.setEmpname(name.toString());
			});
		}
		this.getModel().setEmployeeList(employeeList);
		this.getModel().setFormFlag(MainetConstants.FlagN);
		return new ModelAndView("ViewWardZoneMapping", MainetConstants.FORM_NAME,
			this.getModel());

	    }
}
