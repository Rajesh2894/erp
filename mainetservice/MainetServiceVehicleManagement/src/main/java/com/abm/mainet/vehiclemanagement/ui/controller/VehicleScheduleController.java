package com.abm.mainet.vehiclemanagement.ui.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.CalanderDTO;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDetDTO;
import com.abm.mainet.vehiclemanagement.service.IBeatMasterService;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleScheduleService;
import com.abm.mainet.vehiclemanagement.ui.model.VehicleScheduleModel;
import com.abm.mainet.vehiclemanagement.ui.validator.VehicleScheduleValidator;

/**
 * @author Ajay.Kumar
 *
 */
@Controller
@RequestMapping("/vehicleScheduling.html")
public class VehicleScheduleController extends AbstractFormController<VehicleScheduleModel> {

	/**
	 * IVehicleSchedule Service
	 */
	@Autowired
	private IVehicleScheduleService vehicleScheduleservice;

	/**
	 * IBeatMaster Service
	 */
	@Autowired
	private IBeatMasterService routeMasterService;

	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	/**
	 * ISLRMEmployeeMasterService Service
	 */
	@Autowired
	private ISLRMEmployeeMasterService employeeService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private IVehicleScheduleService vehicleScheduleService;
	

	/**
	 * index It will return default Home Page of Vehicle Sheduling
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("vehicleScheduling.html");
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),""));
		this.getModel().setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
	    this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));	    
		setVeheicleDetails();
		return defaultResult();
	}

	private void setVeheicleDetails() {
		Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream()
				.collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
		this.getModel().getVehicleScheduleDtos().forEach(schedule -> {
			schedule.setVeDesc(CommonMasterUtility.getCPDDescription(schedule.getVeVetype(), MainetConstants.BLANK));
			schedule.setVeRegnNo(veheicleMap.get(schedule.getVeId()));
		});
	}


	/**
	 * add Vehicle Scheduling
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = Constants.ADD_VEHICLESCHEDULING)
	public ModelAndView addVehicleScheduling(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(Constants.SaveMode.CREATE);

		this.getModel().setRouteMasterList(routeMasterService.serchRouteByRouteTypeAndRouteNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));                                         //MainetConstants.FlagY
	      
                                            //changes for #78211---->starts
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(null, null,null,null,MainetConstants.FlagY,
				null,UserSession.getCurrent().getOrganisation().getOrgid()));                                        //changes for #78211---->ends
		// this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT",
		// UserSession.getCurrent().getOrganisation()));

		List<Employee> employeeList = iEmployeeService
				.findEmpList(UserSession.getCurrent().getOrganisation().getOrgid());
		request.setAttribute("employees", employeeList);
		request.setAttribute("departments", loadDepartmentList());
		this.getModel().setEmployeeList(iEmployeeService.getEmployeeData(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(),null));
		this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView(Constants.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	public String timeToStringConvert(Date date) {
		String dateString = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm");
		dateString = sdf.format(date);
		return dateString;
	}

	/**
	 * view Vehicle Scheduling
	 * 
	 * @param mode
	 * @param id
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.VIEW_VEHICLESCHEDULING)
	public ModelAndView viewVehicleScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(Constants.SaveMode.VIEW);
		
		
		this.getModel().setVehicleScheduleDto(vehicleScheduleservice.getVehicleScheduleByVehicleScheduleId(id));
		 List<VehicleScheduleDetDTO> veshDtos =	this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().stream().filter(isDel ->( isDel.getIsDeleted() !=null) && isDel.getIsDeleted().equals(MainetConstants.FlagN)).collect(Collectors.toList());
		 this.getModel().getVehicleScheduleDto().setTbSwVehicleScheddets(veshDtos);
		this.getModel()
				.setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(
						this.getModel().getVehicleScheduleDto().getVeVetype(), null,
						UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getSaveMode()));

		/*
		 * this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT",
		 * UserSession.getCurrent().getOrganisation()));
		 */
		StringBuilder vesDay = new StringBuilder();
		this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().forEach(schDet -> {
			List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
			List<Employee> empList=new ArrayList<>();
			schDet.setStartime(timeToStringConvert(schDet.getVesStartime()));
			schDet.setEndtime(timeToStringConvert(schDet.getVesEndtime()));
			httpServletRequest.setAttribute("departments", loadDepartmentList());
			schDet.setDeptDesc(departmentService.fetchDepartmentDescById(schDet.getDepartment()));
			schDet.setSheduleDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) schDet.getVeScheduledate()));
			schDet.setEmployeeList(employeeService.searchEmployeeList(null, null, null,
					UserSession.getCurrent().getOrganisation().getOrgid()));
			if (schDet.getEmpId() != null) {
				String[] str = schDet.getEmpId().split(",");
				for (int i = 0; i < str.length; i++) {
					final int j = i;
					schDet.getEmployeeList().forEach(emp->{
						if(emp.getEmpId() == Long.parseLong(str[j])) {
							emp.setEmpAddress1(MainetConstants.FlagY);
						}
					});
				}
			}
			
		
			empList.addAll( iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().
					  getOrganisation().getOrgid(),schDet.getDepartment()));
			if(schDet.getOccEmpName() != null) {
				String[] str1=schDet.getOccEmpName().split(",");
				empList.forEach(e -> { 
					for(int i=0; i < str1.length; i++) {
					if(e.getEmpId().toString().equals(str1[i])) {
					e.setStatusIS(MainetConstants.FlagY);
				}}});
				
			}

			 schDet.setEmplList(empList);
			
			 if(schDet.getVesWeekday() != null) {
				 if(vesDay.length() ==0 ) {
					 vesDay.append(schDet.getVesWeekday().toString());
					 }else {
						 vesDay.append(" "+schDet.getVesWeekday().toString());
					 }
			 }
		});
		this.getModel().getVehicleScheduleDto().setVesWeekday(vesDay.toString());
		
		return new ModelAndView(Constants.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * edit Vehicle Scheduling
	 * 
	 * @param mode
	 * @param id
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.EDIT_VEHICLESCHEDULING)
	public ModelAndView editVehicleScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setSaveMode(Constants.SaveMode.EDIT);
		
		this.getModel().setVehicleScheduleDto(vehicleScheduleservice.getVehicleScheduleByVehicleScheduleId(id));
		this.getModel()
				.setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(
						this.getModel().getVehicleScheduleDto().getVeVetype(), null,
						UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getSaveMode()));

		/*
		 * this.getModel().setVesCollTypeList(CommonMasterUtility.getLookUps("COT",
		 * UserSession.getCurrent().getOrganisation()));
		 */
		StringBuilder vesDay = new StringBuilder();
		this.getModel().setEmployeeBeanList(employeeService.searchEmployeeList(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		 List<VehicleScheduleDetDTO> veshDtos =	this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().stream().filter(isDel ->( isDel.getIsDeleted() !=null) && isDel.getIsDeleted().equals(MainetConstants.FlagN)).collect(Collectors.toList());
		 this.getModel().getVehicleScheduleDto().setTbSwVehicleScheddets(veshDtos);
		 this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().stream().filter(isDel -> isDel.getIsDeleted().equals(MainetConstants.FlagN)).forEach(schDet -> {
			List<SLRMEmployeeMasterDTO> employeeList = new ArrayList<>();
			List<Employee> empList=new ArrayList<>();
			schDet.setStartime(timeToStringConvert(schDet.getVesStartime()));
			schDet.setEndtime(timeToStringConvert(schDet.getVesEndtime()));
			httpServletRequest.setAttribute("departments", loadDepartmentList());
			schDet.setDeptDesc(departmentService.fetchDepartmentDescById(schDet.getDepartment()));
			schDet.setSheduleDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) schDet.getVeScheduledate()));
			schDet.setEmployeeList(employeeService.searchEmployeeList(null, null, null,
					UserSession.getCurrent().getOrganisation().getOrgid()));
			if (schDet.getEmpId() != null) {
				String[] str = schDet.getEmpId().split(",");
				for (int i = 0; i < str.length; i++) {
					final int j = i;
					schDet.getEmployeeList().forEach(emp->{
						if(emp.getEmpId() == Long.parseLong(str[j])) {
							emp.setEmpAddress1(MainetConstants.FlagY);
						}
					});
				}
			}
			
			empList.addAll( iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().
					  getOrganisation().getOrgid(),schDet.getDepartment()));
			if(schDet.getOccEmpName() != null) {
				String[] str1=schDet.getOccEmpName().split(",");
				empList.forEach(e -> { 
					for(int i=0; i < str1.length; i++) {
					if(e.getEmpId().toString().equals(str1[i])) {
					e.setStatusIS(MainetConstants.FlagY);
				}}});
				
			}
			 
			 
			 
			 schDet.setEmplList(empList);
			 if(schDet.getVesWeekday() != null) {
				 if(vesDay.length() ==0 ) {
					 vesDay.append(schDet.getVesWeekday().toString());
					 }else {
						 vesDay.append(" "+schDet.getVesWeekday().toString());
					 }
			 }
		});
		this.getModel().getVehicleScheduleDto().setVesWeekday(vesDay.toString());
		return new ModelAndView(Constants.VEHICLESCHEDULING_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * delete Vehicle Scheduling
	 * 
	 * @param vesId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.DELETE_VEHICLESCHEDULING)
	public ModelAndView deleteVehicleScheduling(@RequestParam("vesId") Long vesId,
			final HttpServletRequest httpServletRequest) {
		Employee emp = UserSession.getCurrent().getEmployee();
		vehicleScheduleservice.deleteVehicleSchedule(vesId, emp.getEmpId(), emp.getEmppiservername());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(),""));
		this.getModel().setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(
				null, null, UserSession.getCurrent().getOrganisation().getOrgid()));
		setVeheicleDetails();
		return new ModelAndView(Constants.VEHICLESCHEDULING_SEARCH, MainetConstants.FORM_NAME, this.getModel());
	}
	
	/**
	 * delete Vehicle SchedulingDet
	 * 
	 * @param vesId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.DELETE_VEHICLESCHEDULING_DET)
	public boolean deleteVehicleSchedulingDet(@RequestParam("vesId") Long vesId,@RequestParam("vesdId") Long vesdId,
			final HttpServletRequest httpServletRequest) {
		Employee emp = UserSession.getCurrent().getEmployee();
		vehicleScheduleservice.deleteVehicleScheduleDet(vesId,vesdId, emp.getEmpId(), emp.getEmppiservername());
		return true;
	}

	/**
	 * search Vehicle Scheduling
	 * 
	 * @param veId
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.SEARCH_VEHICLESCHEDULING)
	public ModelAndView searchVehicleScheduling(@RequestParam(value = "veId") final Long veId) {
		if (veId != null) {
			this.getModel().getVehicleScheduleDto().setVeRegnNo(veId.toString());
		}
		this.getModel().setSaveMode(Constants.SaveMode.SEARCH);
		this.getModel().setVehicleScheduleDtos(vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(
				null, veId, UserSession.getCurrent().getOrganisation().getOrgid()));
		setVeheicleDetails();
		return new ModelAndView(Constants.VEHICLESCHEDULING_GRID, MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * serchVehicleNo
	 * 
	 * @param vehicleTypeId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			final HttpServletRequest httpServletRequest) {                                                             
		/*	List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleTypeId, null,null,MainetConstants.FlagY,
		UserSession.getCurrent().getOrganisation().getOrgid());     */
  //changes for #78211---->starts
List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(null,vehicleTypeId,null,null,MainetConstants.FlagY,null,UserSession.getCurrent().getOrganisation().getOrgid());	      
// changes for #78211---->ends
//.stream().filter(i -> i.getVeFlag().equals("Y")   || (i.getVeFlag().equals("N") && i.getVeRentTodate().after(new Date()) )   ).collect(Collectors.toList());
Map<Long, String> data = new HashMap<>();
if (result != null && !result.isEmpty()) {
	result.forEach(vdata -> {
    	Date currentDate = new Date();
    	try {
    		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-dd-MM");
    		  currentDate = sdf.parse(sdf.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	
		if (vdata.getVeFlag().equals(MainetConstants.FlagN)){
			if( (currentDate.equals(vdata.getVeRentFromdate()) || currentDate.after(vdata.getVeRentFromdate()) ) && ( currentDate.equals(vdata.getVeRentTodate())  || currentDate.before(vdata.getVeRentTodate()) )){
				data.put(vdata.getVeId(), vdata.getVeNo());
			}
		} else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
			data.put(vdata.getVeId(), vdata.getVeNo());
		}
	});

}
return data;
}

	/*
	 * @RequestMapping(method = { RequestMethod.POST }, params =
	 * "getEmployeeByDept1") // @RequestMapping(params = "getEmployeeByDept", method
	 * = RequestMethod.POST) public @ResponseBody List<Employee>
	 * getEmployeeDetailsByDeptId(@RequestParam("department") final Long department,
	 * final HttpServletRequest request) { getModel().bind(request); List<Employee>
	 * employeeList =
	 * iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().
	 * getOrganisation().getOrgid(),department); List<Employee> employeeList1 =
	 * iEmployeeService.findEmpList(UserSession.getCurrent().getOrganisation().
	 * getOrgid()); request.setAttribute("employees", employeeList1);
	 * this.getModel().setEmployeList(employeeList);
	 * this.getModel().setCommonHelpDocs("vehicleScheduling.html"); return
	 * employeeList; }
	 */

	@RequestMapping(method = { RequestMethod.POST }, params ="getEmployeeByDept1") 
      public @ResponseBody Map<Long, String>  getEmployeeDetailsByDeptId(@RequestParam("department") final Long department,
	  final HttpServletRequest request) { getModel().bind(request); List<Employee>
	  employeeList =
	  iEmployeeService.findAllEmployeeByDept(UserSession.getCurrent().
	  getOrganisation().getOrgid(),department);
	  
	  List<Employee> employeeList1 =
	  iEmployeeService.findEmpList(UserSession.getCurrent().getOrganisation().
	  getOrgid());
	  request.setAttribute("employees", employeeList1); Map<Long,
	  String> data=new HashMap<>(); if (employeeList != null &&
	  !employeeList.isEmpty()) { employeeList.forEach(vdata -> {
	  data.put(vdata.getEmpId(), vdata.getFullName()); });
	  
	  } return data; 
	  }

	/**
	 * empScheduleLoad
	 * 
	 * @param httpServletRequest
	 * @param veVetype
	 * @param veid
	 * @return
	 */
	@RequestMapping(params = "getCalData", method = RequestMethod.POST)
	public @ResponseBody List<CalanderDTO> empScheduleLoad(final HttpServletRequest httpServletRequest,
			@RequestParam(required = false) Long veVetype, @RequestParam(required = false) Long veid,@RequestParam(required = false) Long veDriverName) {
		// sessionCleanup(httpServletRequest);

		/*this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,veDriverName,null,
				UserSession.getCurrent().getOrganisation().getOrgid()));*/
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(veid,veVetype,null, null,null,MainetConstants.FlagN,UserSession.getCurrent().getOrganisation().getOrgid()));	      
		
		List<VehicleScheduleDTO> vehicleList = vehicleScheduleservice.searchVehicleScheduleByVehicleTypeAndVehicleNo(
				veVetype, veid, UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> inactiveMap = this.getModel().getVehicleMasterList().stream().collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeActive));		
		Map<Long, String> veheicleMap = this.getModel().getVehicleMasterList().stream().collect(Collectors.toMap(GenVehicleMasterDTO::getVeId, GenVehicleMasterDTO::getVeNo));
		List<VehicleScheduleDTO> driverNameSortlist = vehicleList.stream().filter(ved -> veheicleMap.containsKey(ved.getVeId())).collect(Collectors.toList()); 
		driverNameSortlist.stream().
		forEach(schedule -> {
			if(inactiveMap.containsKey(schedule.getVeId()) && inactiveMap.get(schedule.getVeId()).equals(MainetConstants.FlagN) ) {
				List<VehicleScheduleDetDTO> vehSchDto =	schedule.getTbSwVehicleScheddets().stream().filter(schDet->schDet.getVeScheduledate().before(new Date()) ).collect(Collectors.toList());
				schedule.setTbSwVehicleScheddets(vehSchDto);
			}
			
		});
		this.getModel().getVehicleScheduleDtos().stream().filter(ved -> veheicleMap.containsKey(ved.getVeId()) ).
		forEach(schedule -> {
			schedule.setVeDesc(CommonMasterUtility.getCPDDescription(schedule.getVeVetype(), MainetConstants.BLANK));
			schedule.setVeRegnNo(veheicleMap.get(schedule.getVeId()));
		});
		List<CalanderDTO> calanderList = new ArrayList<>();
		VehicleScheduleDTO vehicleDet = new VehicleScheduleDTO();
		List<VehicleScheduleDTO> vehicleDetList = new ArrayList<>();
		for (VehicleScheduleDTO veclist : driverNameSortlist) {
			vehicleDet.setVeId(veclist.getVeId());
			vehicleDet.setVesFromdt(veclist.getVesFromdt());
			vehicleDet.setVesTodt(veclist.getVesTodt());
			veclist.getTbSwVehicleScheddets().forEach(d -> {
				if(d.getIsDeleted()!=null && !d.getIsDeleted().equals("Y"))
				{
				vehicleDet.setVeScheduledate(d.getVeScheduledate());
				for (Map.Entry<Long, String> entry : veheicleMap.entrySet()) {
					if (veclist.getVeId().equals(entry.getKey())) {
						vehicleDet.setVeNo(entry.getValue());
					}
				}
				vehicleDet.setVehStartTime(d.getVesStartime());
				String startTime = timeToDateConvert(d.getVesStartime());
				String endTime = timeToDateConvert(d.getVesEndtime());
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:a");
				Date sartDate = null;
				Date endDate = null;
				try {
					sartDate = dateFormat.parse(vehicleDet.getVeScheduledate() + "T" + startTime);
					endDate = dateFormat.parse(vehicleDet.getVeScheduledate() + "T" + endTime);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
				}

				CalanderDTO calanderDTO = new CalanderDTO(vehicleDet.getVeId(), sartDate, vehicleDet.getVeNo(),
						"bg-black", endDate, "");

				vehicleDetList.add(vehicleDet);
				if(null != veDriverName) {
					if(d.getEmpId().equalsIgnoreCase(veDriverName.toString())) {
						calanderList.add(calanderDTO);
					}
				} else {
					calanderList.add(calanderDTO);
				}

				/*
				 * CalanderDTO calanderDTO1 = new CalanderDTO(vehicleDet.getVeId(), sartDate,
				 * "bg-black", endTime, endDate, "");
				 * 
				 * //CalanderDTO calanderDTO = new CalanderDTO(vehicleDet.getVeNo(),
				 * sartDate,null, "bg-black", endDate, "");
				 * 
				 * CalanderDTO calanderDTO =new CalanderDTO(); calanderDTO.setStart(sartDate);
				 * calanderDTO.setEnd(endDate); calanderDTO.setClassName("bg-black");
				 * calanderDTO.setVeNo(vehicleDet.getVeNo()); vehicleDetList.add(vehicleDet);
				 * calanderList.add(calanderDTO);
				 */
				}
			});		
		}
		return calanderList;
	}

	public String timeToDateConvert(Date date) {
		String dateString = null;
		if(date!=null) {
		DateFormat sdf = new SimpleDateFormat("HH:mm:a");
		dateString = sdf.format(date);}
		return dateString;
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
	public @ResponseBody VehicleScheduleDTO searchVeNo(@RequestParam("veid") Long veid, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<Object[]> list = vehicleMasterService.getVehicleByNumber(veid, orgid);
		VehicleScheduleDTO dto = null;
		if(list != null) {
			for(Object[] obj : list) {
				dto = new VehicleScheduleDTO();
				dto.setVeId(Long.valueOf((Long)obj[0]));
				dto.setVeNo(obj[1].toString());
			}
		}
		return dto;
	}
	
	//Already Existed Validation
	@ResponseBody
    @RequestMapping(params = "recordAlreadyExists", method = RequestMethod.POST)
    public Boolean checkIfexit (@RequestParam String veId, @RequestParam String request, @RequestParam Date vesFromdt, @RequestParam Date vesTodt) throws ParseException {
    	
		boolean stat = false;
    	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    	VehicleScheduleDTO vehicleScheduleDto = new VehicleScheduleDTO();
    	List<VehicleScheduleDetDTO> tbSwVehicleScheddets = new ArrayList<VehicleScheduleDetDTO>();
    	VehicleScheduleDetDTO det=null;
    	
    	String[] parts = request.split("&");
    	
    	for(String s:parts) {                      
    		det=new VehicleScheduleDetDTO();
    		String[] s1=s.split(",");
    		   String[] v1=s1[0].split("=");
    		   String[] v2=s1[1].split("=");

   			det.setVesStartime(this.getModel().stringToTimeConvet(v1[1].toString()));
   			det.setVesEndtime(this.getModel().stringToTimeConvet(v2[1].toString()));
	    	tbSwVehicleScheddets.add(det);
    	}
    	
    	List<Date> date=new ArrayList<>();
    	date.add(vesFromdt);
    	date.add(vesTodt);
    	
    	vehicleScheduleDto.setVeId(Long.valueOf(veId));
    	vehicleScheduleDto.setOrgid(orgid);
    	vehicleScheduleDto.setSheduleDate(date);
    	vehicleScheduleDto.setTbSwVehicleScheddets(tbSwVehicleScheddets);
    	
    	List<GenVehicleMasterDTO> checkList = vehicleMasterService.searchVehicleByVehNoAndVehTypeAndVehRegNoAndrentToDate(vehicleScheduleDto.getVeId(),null,null,null,MainetConstants.FlagY,null,orgid);
    	if((checkList.size()) <= 0) {
    		stat = false;
    	} else if(vehicleScheduleService.vehicleScheduleValidate(vehicleScheduleDto).equals(MainetConstants.FlagN)){
			stat = true;
		} else {
			stat = false;
		};
    	
    	//stat = vehicleScheduleService.vehicleScheduleValidate(vehicleScheduleDto);
    	
    return stat;
    
    }
	
	@ResponseBody
    @RequestMapping(params = "recordAlreadyExistsCheck", method = RequestMethod.POST)
    public  VehicleScheduleDTO VehicleScheduleDTO (@RequestParam String veId, @RequestParam Date vesFromdt, @RequestParam Date vesTodt) {
    	
	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    VehicleScheduleDTO vehicleScheduleDto = new VehicleScheduleDTO();

    List<GenVehicleMasterDTO> vehicledto = vehicleMasterService.getVehDet(veId, vesFromdt, vesTodt);
	VehicleScheduleDTO dto = new VehicleScheduleDTO();
		
	if(vehicledto!=null) {
		for(GenVehicleMasterDTO vehicledtolist : vehicledto) {
			dto = new VehicleScheduleDTO();
			dto.setFromDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledtolist.getVeRentFromdate()));
			dto.setToDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledtolist.getVeRentTodate()));
			dto.setFlagMsg("recordPresent");
		}
		
    /*if(vehicledto.get(0).getVeRentFromdate()!=null) {
    	dto.setFromDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledto.get(0).getVeRentFromdate()));
    }
   
    if(vehicledto.get(0).getVeRentTodate()!=null) {
    	dto.setToDate(new SimpleDateFormat("dd/MM/yyyy").format((Date) vehicledto.get(0).getVeRentTodate()));
    }*/
	}
	
    
	return dto;
    
    }
	
	 @RequestMapping(method = { RequestMethod.POST }, params = "validSaveform")
		public @ResponseBody String save(final HttpServletRequest httpServletRequest) {
		 String errorMsg = "";
			getModel().bind(httpServletRequest);
			VehicleScheduleDTO veDto = this.getModel().getVehicleScheduleDto();
			Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
			this.getModel().getVehicleScheduleDto().setOrgid(orgid);
			if (this.getModel().getSaveMode() !="E") {
				this.getModel().validateBean(veDto, VehicleScheduleValidator.class);
				for(ObjectError oe : this.getModel().getBindingResult().getAllErrors()) {
					errorMsg+=oe.getDefaultMessage();
				}
			}
			if(errorMsg == "") {
			if(this.getModel().getSaveMode() !="E") {
				this.getModel().checkVehicleSheduleDay(veDto);
			}			
			if( this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().size()  == 0) {
				errorMsg+=ApplicationSession.getInstance().getMessage("vehicle.schedule.select.weekDay.proper");
			}else {
				if(this.getModel().maserAndScheduleToAndFromDateCheck() == false){
					errorMsg+=ApplicationSession.getInstance().getMessage("vehicleScheduleDto.rent.time.completed")+" ," + "<br>";
				}
				for(VehicleScheduleDetDTO det : this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets() ) {
					if(this.getModel().scheduleAndScheduleDetailsToAndFromDateCheck(det)  == false){
						errorMsg+=ApplicationSession.getInstance().getMessage("vehicleScheduleDto.schDate.bw.from.to.date")+" ," + "<br>";
					}
				}
			}
	      	String dupCheck =	vehicleScheduleService.vehicleScheduleValidate( this.getModel().getVehicleScheduleDto());
	    	
			if(dupCheck!=MainetConstants.FlagN) {
				errorMsg+=ApplicationSession.getInstance().getMessage("vehicle.schedule.already.exists ")+dupCheck+" ," + "<br>";
			}
			}
			if(this.getModel().getSaveMode() !="E") {
				this.getModel().setVehicleScheduleDto(null);
			}
			
		return errorMsg;

		}
	 
	 @RequestMapping(method = { RequestMethod.POST }, params = "deleteSave")
		public @ResponseBody String deleteSave(final HttpServletRequest httpServletRequest) {
		 getModel().bind(httpServletRequest);
		// this.getModel().getVehicleScheduleDto().setIsDeleted(delete);
		 this.getModel().saveForm();
			return "true";
		 
	 }
}
