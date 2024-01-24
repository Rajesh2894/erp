package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.PetrolRequestModel;

@Controller
@RequestMapping("/petrolRequisitionForm.html")
public class PetrolRequestController extends AbstractFormController<PetrolRequestModel> {

	/**
	 * TbDepartment Service
	 */
	
	@Autowired
	private IPetrolRequisitionService petrolRequisitionService;

	@Autowired
	private IEmployeeService iEmployeeService;
	
    @Autowired
    ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	
	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	@Autowired
    private IPumpMasterService pumpMasterService;
	
	@Autowired
	private IFileUploadService fileUpload;
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model,HttpServletRequest request) throws Exception {

		this.sessionCleanup(request);
		this.getModel().setCommonHelpDocs("petrolRequestForm.html");
		request.setAttribute("departments", loadDepartmentList());
		//List<PetrolRequisitionDTO> petrols = petrolRequisitionService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		List<PetrolRequisitionDTO> petrolRequisitionDTOs = petrolRequisitionService.searchPetrolRequest(null, null,new Long(MainetConstants.NUMBERS.ZERO),new Long(MainetConstants.NUMBERS.ZERO),UserSession.getCurrent().getOrganisation().getOrgid());
		
		model.addAttribute("PetrolRequisitionData",petrolRequisitionDTOs);
		model.addAttribute("ListVehicles", list);
		return defaultResult();
	}

	@RequestMapping(params = "petrolRequest", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView petrolForm(final HttpServletRequest request, Model reqmodel) {
		this.sessionCleanup(request);
		PetrolRequestModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		request.setAttribute("departments", loadDepartmentList());
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		Long dsgId =  UserSession.getCurrent().getEmployee().getDesignation().getDsgid();
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(),model.getSaveMode()));
		 List<SLRMEmployeeMasterDTO> sLRMEmpList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		 List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		//List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		//employeeList =	employeeList.stream().filter(d -> d.getDesignation().getDsgid() != null && d.getDesignation().getDsgid().equals(dsgId)).collect(Collectors.toList());
		 List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		 reqmodel.addAttribute("ListOfChassisNo", list);
		reqmodel.addAttribute("employees", sLRMEmpList);
		/*
		 * List<PetrolRequisitionDTO> list =
		 * petrolRequisitionService.getAllVehicles(UserSession.getCurrent().
		 * getOrganisation().getOrgid()); reqmodel.addAttribute("ListVehicles", list);
		 */

		return new ModelAndView("FuelRequisitionAdd", MainetConstants.FORM_NAME, model);

	}
	

	
	@ResponseBody
	@RequestMapping(params = "getMeetingNo", method = RequestMethod.POST)
	public List<PetrolRequisitionDTO> getMeetingNos(@RequestParam("department") final Long department,
			@RequestParam("vehicleType") final Long vehicleType, final HttpServletRequest request) {
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<PetrolRequisitionDTO> petrolRequisitionDTOList = new ArrayList<>();
		petrolRequisitionDTOList = petrolRequisitionService.fetchVeNoByDeptAndVeType(department, vehicleType, orgId);
		return petrolRequisitionDTOList;
	}
        
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(params = "getVehicleNo", method = RequestMethod.POST) public
	 * List<PetrolRequisitionDTO> getVehicleNo(@RequestParam("department") final
	 * Long department,
	 * 
	 * @RequestParam("vehicleType") final Long vehicleType, final HttpServletRequest
	 * request) { getModel().bind(request); Long orgId =
	 * UserSession.getCurrent().getOrganisation().getOrgid();
	 * List<PetrolRequisitionDTO> petrolRequisitionDTOList = new ArrayList<>();
	 * petrolRequisitionDTOList =
	 * petrolRequisitionService.fetchVeNoByDeptAndVeType(department, vehicleType,
	 * orgId); return petrolRequisitionDTOList; }
	 */
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId, @RequestParam("deptId") Long deptId,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.fetchVeNoByVehicleTypeIdAndDeptId(vehicleTypeId, deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				Calendar cal1 = Calendar.getInstance();
		    	cal1.setTime(new Date());
		    	Date date = cal1.getTime();
				if (vdata.getVeFlag().equals(MainetConstants.FlagN)
						&& ((date.after(vdata.getVeRentFromdate()) && date.before(vdata.getVeRentTodate()))
								|| (date.equals(vdata.getVeRentFromdate()))
								|| (date.equals(vdata.getVeRentFromdate())))) {
				data.put(vdata.getVeId(), vdata.getVeNo());
				}
				else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
					data.put(vdata.getVeId(), vdata.getVeNo());
				}
			});

		}
		return data;
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getChasisNo")
	public @ResponseBody Map<Long, String> serchChasisNo(@RequestParam("id") Long vehicleTypeId, @RequestParam("deptId") Long deptId,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.fetchVeNoByVehicleTypeIdAndDeptId(vehicleTypeId, deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				Calendar cal1 = Calendar.getInstance();
		    	cal1.setTime(new Date());
		    	Date date = cal1.getTime();
				if (vdata.getVeFlag().equals(MainetConstants.FlagN)
						&& ((date.after(vdata.getVeRentFromdate()) && date.before(vdata.getVeRentTodate()))
								|| (date.equals(vdata.getVeRentFromdate()))
								|| (date.equals(vdata.getVeRentFromdate())))) {
					data.put(vdata.getVeId(), vdata.getVeChasisSrno());
				}
				else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
					data.put(vdata.getVeId(), vdata.getVeChasisSrno());
				}
			});

		}
		return data;
	}
	
	

	@RequestMapping(method = { RequestMethod.POST }, params = "getFuelType")
	public @ResponseBody Map<Long, String> serchFuelType(@RequestParam("id") Long vehicleFuleTypeId,@RequestParam(value ="chasisno",required = false ) String chasisno,
			final HttpServletRequest httpServletRequest) {
		/*List<GenVehicleMasterDTO> result = vehicleMasterService.searchFuelByVehRegNo(vehicleFuleTypeId,chasisno,
				UserSession.getCurrent().getOrganisation().getOrgid());*/
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchFuelByVehRegNo(vehicleFuleTypeId,null,UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				vdata.setFuelTypeDesc(CommonMasterUtility.getCPDDescription(vdata.getFuelType(), MainetConstants.BLANK));
				data.put(vdata.getFuelType(), vdata.getFuelTypeDesc());
			});
		}
		
		return data;
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getDepartmentType")
	public @ResponseBody Map<Long, String> serchVehicleType(@RequestParam("id") Long deptId,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleTypeByDeptId(deptId, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				vdata.setVeTypeDesc(CommonMasterUtility.getCPDDescription(vdata.getVeVetype(), MainetConstants.BLANK));
			//	vdata.setFuelTypeDesc(CommonMasterUtility.getCPDDescription(vdata.getFuelType(), MainetConstants.BLANK));
			//	vdata.setVeRegnNo(veheicleMap.get(vdata.getVeId()));
				data.put(vdata.getVeVetype(), vdata.getVeTypeDesc());
			});

		}
		return data;
	}
	
	
	@RequestMapping(params = "SearchPetrol", method = RequestMethod.POST)
	public @ResponseBody List<PetrolRequisitionDTO> findVehicleLogBooks(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate ,@RequestParam Long department, @RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		/*
		 * Map<Long, String> veheicleMap =
		 * this.getModel().getVehicleMasterList().stream()
		 * .collect(Collectors.toMap(GenVehicleMasterDTO::getVeId,
		 * GenVehicleMasterDTO::getVeNo)); List<GenVehicleMasterDTO> result =
		 * vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleTypeId, null,
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		List<PetrolRequisitionDTO> petrolRequisitionDTOs = petrolRequisitionService.searchPetrolRequest(fromDate, toDate,department, veNo,orgid);
		/*
		 * List<PetrolRequisitionDTO> list =
		 * petrolRequisitionService.getAllVehicles(UserSession.getCurrent().
		 * getOrganisation().getOrgid());
		 */		// tbvehicleService.getList(veLogBoookDtos, list);
		request.setAttribute("PetrolRequisitionData", petrolRequisitionDTOs);
		
		return petrolRequisitionDTOs;

	}

	

	/**
	 * It will set mode render view vehicle log book Form
	 * 
	 * @param mode    String Mode(V:VIEW)
	 * @param request
	 * @return ModelAndView
	 */

	@RequestMapping(params = "viewPETROL", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPetrolRequests(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long requestId, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setPetrolRequisitionDTO(petrolRequisitionService.getDetailById(requestId));
		//this.getModel().setDriverEmpID(vehicleLo
		this.getModel().setSaveMode(mode);
		String remaks=petrolRequisitionService.getRemark(this.getModel().getPetrolRequisitionDTO().getFuelReqNo(),this.getModel().getPetrolRequisitionDTO().getOrgid());
		this.getModel().getPetrolRequisitionDTO().setPetrolRegRemark(remaks);
		if (this.getModel().getPetrolRequisitionDTO().getRequestStatus().equals("A")) {
			this.getModel().getPetrolRequisitionDTO().setPetrolRegstatus("APPROVED");
		} else if (this.getModel().getPetrolRequisitionDTO().getRequestStatus().equals("R")) {
			this.getModel().getPetrolRequisitionDTO().setPetrolRegstatus("REJECTED");
		}
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		httpServletRequest.setAttribute("departments", loadDepartmentList());
	//	List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		 List<SLRMEmployeeMasterDTO> sLRMEmpList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());	
		//List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		reqmodel.addAttribute("employees", sLRMEmpList);
		List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListVehicles", list);
		List<PumpMasterDTO> pumpList = pumpMasterService.serchPumpMasterByPumpType(null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("pumps", pumpList);
		reqmodel.addAttribute("ListOfChassisNo", list);

		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			final List<AttachDocs> attachDocs = attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
					(Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + this.getModel().getPetrolRequisitionDTO().getRequestId().toString()));
			this.getModel().setAttachDocsList(attachDocs);
		}
		return new ModelAndView("FuelRequisitionAdd", MainetConstants.FORM_NAME, this.getModel());
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

	@RequestMapping(params = "editPETROL", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewPetrolRequest(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long requestId, final HttpServletRequest httpServletRequest,Model reqmodel) {
		    this.getModel().setPetrolRequisitionDTO(petrolRequisitionService.getDetailById(requestId));
		    this.getModel().setSaveMode(mode);
	      	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	    	httpServletRequest.setAttribute("departments", loadDepartmentList());
		    List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
		    reqmodel.addAttribute("employees", employeeList);
		    List<PetrolRequisitionDTO> list = petrolRequisitionService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		    reqmodel.addAttribute("ListVehicles", list);
		    return new ModelAndView("FuelRequisitionAdd", MainetConstants.FORM_NAME, this.getModel());
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
	
	
	@RequestMapping(params = "searchVeNo", method = RequestMethod.POST)
	public @ResponseBody PetrolRequisitionDTO searchVeNo(@RequestParam("veid") Long veid, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> list = vehicleMasterService.getVehicleByNumber(veid, orgid);
		PetrolRequisitionDTO dto = null;
		if(list != null) {
			for(Object[] obj : list) {
				dto = new PetrolRequisitionDTO();
				dto.setVeId(Long.valueOf((Long)obj[0]));
				dto.setVeNo(obj[1].toString());
			}
		}
		return dto;
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleNoByDept")
	public @ResponseBody Map<Long, String> getVehicleNoByDept(@RequestParam("department") Long department,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleNoByDeptId(department, UserSession.getCurrent().getOrganisation().getOrgid());
		
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			result.forEach(vdata -> {
				data.put(vdata.getVeId(), vdata.getVeNo());
			});

		}
		return data;
	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public @ResponseBody Boolean save(@RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		String reqStatus = petrolRequisitionService.fetchVehicleNoByVeId(veNo, "O");
		if (reqStatus != null && !(reqStatus.isEmpty()) && reqStatus.equals("O")) {
			return false;
		}
		return true;
	}

}



