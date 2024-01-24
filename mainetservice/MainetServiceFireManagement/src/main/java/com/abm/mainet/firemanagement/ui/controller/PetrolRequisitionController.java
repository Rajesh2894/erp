/*package com.abm.mainet.firemanagement.ui.controller;

import java.util.ArrayList;
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
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.firemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.firemanagement.ui.model.PetrolRequisitionModel;

@Controller
@RequestMapping("/petrolRequestForm.html")
public class PetrolRequisitionController extends AbstractFormController<PetrolRequisitionModel> {

	*//**
	 * TbDepartment Service
	 *//*
	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private IPetrolRequisitionService petrolRequisitionService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model,HttpServletRequest request) throws Exception {

		this.sessionCleanup(request);
		this.getModel().setCommonHelpDocs("petrolRequestForm.html");
		request.setAttribute("departments", loadDepartmentList());
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PetrolRequisitionDTO> petrols = petrolRequisitionService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("PetrolRequisitionData", petrols);
		model.addAttribute("ListVehicles", list);
		return defaultResult();
	}

	@RequestMapping(params = "petrolRequest", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addForm(final HttpServletRequest request, Model reqmodel) {
		this.sessionCleanup(request);
		PetrolRequisitionModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		request.setAttribute("departments", loadDepartmentList());
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		reqmodel.addAttribute("employees", employeeList);
		List<PetrolRequisitionDTO> list = petrolRequisitionService
				.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListVehicles", list);

		return new ModelAndView("PetrolRequisitionAdd", MainetConstants.FORM_NAME, model);

	}
	

	
	@ResponseBody
	@RequestMapping(params = "getMeetingNo", method = RequestMethod.POST)
	public List<PetrolRequisitionDTO> getMeetingNo(@RequestParam("department") final Long department,
			@RequestParam("vehicleType") final Long vehicleType, final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PetrolRequisitionDTO> petrolRequisitionDTOList = new ArrayList<>();
		petrolRequisitionDTOList = petrolRequisitionService.fetchVeNoByDeptAndVeType(department, vehicleType, orgId);
		return petrolRequisitionDTOList;
	}

	
	

	@RequestMapping(params = "SearchPetrol", method = RequestMethod.POST)
	public @ResponseBody List<PetrolRequisitionDTO> findVehicleLogBook(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate ,@RequestParam Long department, @RequestParam("veNo") String veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PetrolRequisitionDTO> petrolRequisitionDTOs = petrolRequisitionService.searchPetrolRequest(fromDate, toDate,department, veNo,orgid);
		List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		// tbvehicleService.getList(veLogBoookDtos, list);
		request.setAttribute("PetrolRequisitionData", petrolRequisitionDTOs);
		// return getFires(occDtos);

		return petrolRequisitionDTOs;

	}

	

	*//**
	 * It will set mode render view vehicle log book Form
	 * 
	 * @param mode    String Mode(V:VIEW)
	 * @param request
	 * @return ModelAndView
	 *//*

	@RequestMapping(params = "viewPETROL", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPetrolRequest(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long requestId, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setPetrolRequisitionDTO(petrolRequisitionService.getDetailById(requestId));
		//this.getModel().setDriverEmpID(vehicleLo
		this.getModel().setSaveMode(mode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		httpServletRequest.setAttribute("departments", loadDepartmentList());
	//	List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		reqmodel.addAttribute("employees", employeeList);
		List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListVehicles", list);
		return new ModelAndView("PetrolRequisitionAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	*//**
	 * This will set mode as Edit and render view on Vehicle Log book Form as an
	 * Edit Mode
	 * 
	 * @param mode               String Mode(E:EDIT)
	 * @param id                 Long Vehicle Id
	 * @param httpServletRequest
	 * @return ModelAndView
	 *//*

	@RequestMapping(params = "editPETROL", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewHospitalMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long requestId, final HttpServletRequest httpServletRequest,Model reqmodel) {
		    this.getModel().setPetrolRequisitionDTO(petrolRequisitionService.getDetailById(requestId));
		    this.getModel().setSaveMode(mode);
	      	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	    	httpServletRequest.setAttribute("departments", loadDepartmentList());
		    List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		    reqmodel.addAttribute("employees", employeeList);
		    List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		    reqmodel.addAttribute("ListVehicles", list);
		    return new ModelAndView("PetrolRequisitionAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	
	
	*//**
	 * @return Department List
	 *//*
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

}




*/