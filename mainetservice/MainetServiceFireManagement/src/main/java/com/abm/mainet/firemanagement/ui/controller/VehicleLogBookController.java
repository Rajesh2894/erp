package com.abm.mainet.firemanagement.ui.controller;

import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.firemanagement.service.ILogBookService;
import com.abm.mainet.firemanagement.ui.model.VehicleLogBookModel;

@Controller
@RequestMapping("/VehicleLogBook.html")
public class VehicleLogBookController extends AbstractFormController<VehicleLogBookModel> {



	@Autowired
	ILogBookService tbvehicleService;

	@Autowired
	private IEmployeeService iEmployeeService;  
	
	@Autowired
	private DepartmentService departmentService;

	/**
	 * It will return default Home Page of Vehicle Log Book
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("VehicleLogBook.html");
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		Long department = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		List<VehicleLogBookDTO> list = tbvehicleService.getAllVehiclesByDept(UserSession.getCurrent().getOrganisation().getOrgid(),department);
	//	List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
	//	model.addAttribute("employees", employeeList);
		model.addAttribute("VehicleLogBookData", books);
		model.addAttribute("ListDriver", list);
		return new ModelAndView("VehicleLogBook", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "logBook", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(final HttpServletRequest request, Model reqmodel) {
		this.sessionCleanup(request);
		VehicleLogBookModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		// Long secuDeptId = departmentService.getDepartmentIdByDeptCode("SM");
		// request.setAttribute("secuDeptEmployee",
		// employeeService.getEmployeeData(secuDeptId , null, null ,
		// UserSession.getCurrent().getOrganisation().getOrgid()));

		Long department = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		reqmodel.addAttribute("employees", iEmployeeService.getEmployeesForVehicleDriverMas(orgid, MainetConstants.DESIGNATION_DRIVER));
		List<VehicleLogBookDTO> list = tbvehicleService
				.getAllVehiclesByDept(UserSession.getCurrent().getOrganisation().getOrgid(),department);
		reqmodel.addAttribute("ListDriver", list);
		return new ModelAndView("VehicleLogBookAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "SearchLogBook", method = RequestMethod.POST)
	public @ResponseBody List<VehicleLogBookDTO> findVehicleLogBook(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, @RequestParam("veNo") String veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<VehicleLogBookDTO> veLogBoookDtos = tbvehicleService.searchFireCallRegisterwithDate(fromDate, toDate, veNo,
				orgid);
		/*
		 * List<VehicleLogBookDTO> list = tbvehicleService
		 * .getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		// tbvehicleService.getList(veLogBoookDtos, list);
		request.setAttribute("VehicleLogBookData", veLogBoookDtos);
		// return getFires(occDtos);

		return veLogBoookDtos;

	}

	/**
	 * It will set mode render view vehicle log book Form
	 * 
	 * @param mode    String Mode(V:VIEW)
	 * @param request
	 * @return ModelAndView
	 */

	@RequestMapping(params = "viewVLB", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewLogBook(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long veID, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setVehicleLogBookDTO(tbvehicleService.getVehicleById(veID));
		//this.getModel().setDriverEmpID(vehicleLo
		this.getModel().setSaveMode(mode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		Long department = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
	//	List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("employees", iEmployeeService.getEmployeesForVehicleDriverMas(orgid, MainetConstants.DESIGNATION_DRIVER));
		List<VehicleLogBookDTO> list = tbvehicleService.getAllVehiclesByDept(UserSession.getCurrent().getOrganisation().getOrgid(),department);
		reqmodel.addAttribute("ListDriver", list);
		return new ModelAndView("VehicleLogBookAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * This will set mode as Edit and render view on Vehicle Log book Form as an
	 * Edit Mode
	 * 
	 * @param mode               String Mode(E:EDIT)
	 * @param id                 Long Vehicle Id
	 * @param httpServletRequest
	 * @return ModelAndView
	 */

	@RequestMapping(params = "editVLB", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewHospitalMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long veID, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setVehicleLogBookDTO(tbvehicleService.getVehicleById(veID));
		this.getModel().setSaveMode(mode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		Long department = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		reqmodel.addAttribute("employees", iEmployeeService.getEmployeesForVehicleDriverMas(orgid, MainetConstants.DESIGNATION_DRIVER));
		List<VehicleLogBookDTO> list = tbvehicleService
				.getAllVehiclesByDept(UserSession.getCurrent().getOrganisation().getOrgid(),department);
		reqmodel.addAttribute("ListDriver", list);
		return new ModelAndView("VehicleLogBookAdd", MainetConstants.FORM_NAME, this.getModel());
	}



}
