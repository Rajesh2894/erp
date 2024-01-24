package com.abm.mainet.firemanagement.ui.controller;

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
import com.abm.mainet.firemanagement.ui.model.FireCallRegisterModel;

@Controller
@RequestMapping(value = "/FireCallRegister.html")
public class FireCallRegisterController extends AbstractFormController<FireCallRegisterModel> {

	
	@Autowired
	private IFileUploadService fileUpload;
	
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private IFireCallRegisterService fireCallRegisterService;
	
	@Autowired
    private IAttachDocsService iAttachDocsService;
	

	/**
	 * It will return page of Complain Register
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		 this.sessionCleanup(httpServletRequest);
		 fileUpload.sessionCleanUpForFileUpload();
		 this.getModel().setCommonHelpDocs("FireCallRegister.html");
		 this.getModel().setSaveMode(MainetConstants.MODE_CREATE);
		// httpServletRequest.setAttribute("departments", loadDepartmentList());
		 //httpServletRequest.setAttribute("locations", loadLocation());
		// Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		 List<FireCallRegisterDTO> listfirecall = fireCallRegisterService.findAllFire(UserSession.getCurrent().getOrganisation().getOrgid());
		 model.addAttribute("listfirecalls",listfirecall);
		// httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		// List<FireCallRegisterDTO> list = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid());
		// model.addAttribute("listDto", list);
		return defaultResult();	
	}
	
	@RequestMapping(params = "fireCallRegister", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addpopulationForm1(final HttpServletRequest request) {
		this.sessionCleanup(request);
		FireCallRegisterModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);	
		//Long deptId=departmentService.getDepartment(Constants.FM, MainetConstants.FlagA).getDpDeptid();
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		request.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		List<FireCallRegisterDTO> list = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),secuDeptId);
		//List<FireCallRegisterDTO> newList =list.stream().filter(d -> (deptId!=null && d.getDeptId()!=null && d.getDeptId().equals(deptId))).collect(Collectors.toList());
		request.setAttribute("ListDto", list);
		//List<FireCallRegisterDTO> list1 = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid());
		request.setAttribute("listVeh", list); 
		return new ModelAndView("FireCallRegisterAdd", MainetConstants.FORM_NAME, model);	
	}
	
	@RequestMapping(params = "fireCallRegisterAdd", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addpopulationForm1Add(final HttpServletRequest request) {
		this.sessionCleanup(request);
		FireCallRegisterModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);	
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		request.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		List<FireCallRegisterDTO> list = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),secuDeptId);
		request.setAttribute("ListDto", list);
		request.setAttribute("listVeh", list); 
		return new ModelAndView("FireCallRegisterAddBas", MainetConstants.FORM_NAME, model);	
	}
	
	@RequestMapping(params = "searchVehicle", method = RequestMethod.POST)
	public @ResponseBody List<FireCallRegisterDTO> searchVehicle(@RequestParam("date") Date date,
			HttpServletRequest request) {
		Long deptId = departmentService.getDepartment(Constants.FM, MainetConstants.FlagA).getDpDeptid();
		List<FireCallRegisterDTO> list = fireCallRegisterService
				.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),deptId);
		List<FireCallRegisterDTO> newList = new ArrayList<FireCallRegisterDTO>();
		list.forEach(data -> {
			if (deptId != null && data.getDeptId()!= null && data.getDeptId().equals(deptId)) {
				if (data.getVeFlag() != null && data.getVeFlag().equals(MainetConstants.FlagN)) {
					if ((data.getVeRentFromdate() != null && data.getVeRentTodate() != null)
							&& ((date.after(data.getVeRentFromdate()) && (date.before(data.getVeRentTodate())))
									|| (date.equals(data.getVeRentFromdate()))
									|| (date.equals(data.getVeRentTodate())))) {
						newList.add(data);
					}
				} else {
					newList.add(data);
				}
			}
		});
		return newList;
	}

	/**
	 * @return Department List
	 */
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA); 
		return departments;
	}

	/**
	 * @return Location List
	 */
	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}
	
	
	@RequestMapping(params = "SearchData", method = RequestMethod.POST)
	public @ResponseBody List<FireCallRegisterDTO> findOccBook(@RequestParam("fromDate") Date fromDate,@RequestParam("toDate") Date toDate,@RequestParam("cmplntNo") String cmplntNo,
			@RequestParam("fireStation") String fireStation,@RequestParam("status") String status,
			HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<FireCallRegisterDTO> firecallreg = fireCallRegisterService.searchFireCallRegisterReg(fromDate, toDate, fireStation, cmplntNo, orgid, status);
		return firecallreg;
	}
	
	
	
	@RequestMapping(params = "edit", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editFire(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long  cmplntId, final HttpServletRequest httpServletRequest) {	
		Long deptId = departmentService.getDepartment(Constants.FM, MainetConstants.FlagA).getDpDeptid();
		Long secuDeptId = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		httpServletRequest.setAttribute("secuDeptEmployee", employeeService.getEmployeeData(secuDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(),null));
		List<FireCallRegisterDTO> list = fireCallRegisterService.getAllVehiclesAssign(UserSession.getCurrent().getOrganisation().getOrgid(),deptId);
		//List<FireCallRegisterDTO> newList = list.stream().filter(d -> (deptId!=null && d.getDeptId()!=null && d.getDeptId().equals(deptId))).collect(Collectors.toList());	
		httpServletRequest.setAttribute("listVeh", list);
		this.getModel().setEntity(fireCallRegisterService.getFireById(cmplntId));
		this.getModel().setSaveMode(mode);
		
		//fetch uploaded document start
		FireCallRegisterDTO fireEntity = fireCallRegisterService.findOne(cmplntId);
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setIdfId(Constants.FIRE_CALL_REG_TABLE + MainetConstants.WINDOWS_SLASH + fireEntity.getCmplntNo().toString());
		List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(), requestDTO.getIdfId());
		if(!attachDocs.isEmpty()) {
			if(!attachDocs.get(0).getAttFname().isEmpty()) {
				this.getModel().getEntity().setAtdFname(attachDocs.get(0).getAttFname());
			}
			if(!attachDocs.get(0).getAttPath().isEmpty()) {
				this.getModel().getEntity().setAtdPath(attachDocs.get(0).getAttPath());
			}
		}
		//fetch uploaded document end
		return new ModelAndView("FireCallRegisterAdd", MainetConstants.FORM_NAME, this.getModel());
	}
	
	
	
	
}
	
	
