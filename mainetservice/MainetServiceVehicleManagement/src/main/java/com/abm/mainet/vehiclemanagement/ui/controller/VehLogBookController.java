package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.ILogBookService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.VeLogBookModel;

@Controller
@RequestMapping("/vehLogBook.html")
public class VehLogBookController extends AbstractFormController<VeLogBookModel> {



	@Autowired
	ILogBookService tbvehicleService;

	@Autowired
	private IEmployeeService iEmployeeService;  
	
	
	 @Autowired
	    private IGenVehicleMasterService vehicleMasterService;

	 @Autowired
	 private  ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	 
	 @Autowired
	    private IAttachDocsService attachDocsService;
	 
	 @Autowired
	    private IFileUploadService fileUpload;

	/**
	 * It will return default Home Page of Vehicle Log Book
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("vehicleLogBookCon.htmll");
		List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(),""));
		List<VehicleLogBookDTO> list = tbvehicleService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
	//	List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
	//	model.addAttribute("employees", employeeList);
	    model.addAttribute("VehicleLogBookData", books);
	   model.addAttribute("ListDriver", list);
		return new ModelAndView("VehLogBook", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "logBook", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addVehicle(final HttpServletRequest request, Model reqmodel) {
		this.sessionCleanup(request);
		VeLogBookModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(),model.getSaveMode()));
		List<SLRMEmployeeMasterDTO> driverDetais = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()) ;
		reqmodel.addAttribute("employees", driverDetais);
		//this.getModel().setEmployees(employeeList);
		//List<VehicleLogBookDTO> list = tbvehicleService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		//reqmodel.addAttribute("ListDriver", list);
		return new ModelAndView("VehLogBookAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "searchLogBook", method = RequestMethod.POST)
	public @ResponseBody List<VehicleLogBookDTO> findVehicleBook(@RequestParam("fromDate") Date fromDate,
			@RequestParam("toDate") Date toDate, @RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<VehicleLogBookDTO> veLogBoookDtos = tbvehicleService.searchVehicleDetail(fromDate, toDate, veNo,
				orgid);
		//List<VehicleLogBookDTO> list = tbvehicleService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		// tbvehicleService.getList(veLogBoookDtos, list);
		request.setAttribute("VehicleLogBookData", veLogBoookDtos);
		
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
	public ModelAndView viewVehicleLogBook(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long veID, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setVehicleLogBookDTO(tbvehicleService.getVehicleById(veID));
		//this.getModel().setDriverEmpID(vehicleLo
		this.getModel().setSaveMode(mode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	//	List<VehicleLogBookDTO> books = tbvehicleService.getAllRecord(UserSession.getCurrent().getOrganisation().getOrgid());
		List<SLRMEmployeeMasterDTO> driverDetais = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()) ;
		reqmodel.addAttribute("employees", driverDetais);
		List<VehicleLogBookDTO> list = tbvehicleService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListDriver", list);
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.LOG_BOOK+ MainetConstants.WINDOWS_SLASH+ this.getModel().getVehicleLogBookDTO().getVeID()+MainetConstants.WINDOWS_SLASH + UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("VehLogBookAdd", MainetConstants.FORM_NAME, this.getModel());
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
	public ModelAndView editVehicleLogBook(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long veID, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setVehicleLogBookDTO(tbvehicleService.getVehicleById(veID));
		this.getModel().setSaveMode(mode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<SLRMEmployeeMasterDTO> driverDetais = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid()) ;
		reqmodel.addAttribute("employees", driverDetais);
		List<VehicleLogBookDTO> list = tbvehicleService
				.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListDriver", list);
		final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +Constants.LOG_BOOK+ MainetConstants.WINDOWS_SLASH+ this.getModel().getVehicleLogBookDTO().getVeID()+MainetConstants.WINDOWS_SLASH + UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("VehLogBookAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	  @RequestMapping(method = { RequestMethod.POST }, params = "vehicleNo")
	    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
	            final HttpServletRequest httpServletRequest) {
	        List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehicleTypeAndVehicleRegNo(vehicleTypeId, null,null,null,
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
					} else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
						data.put(vdata.getVeId(), vdata.getVeNo());
					}
	            });
	        }
	        return data;
	    }
	  
	@RequestMapping(method = { RequestMethod.POST }, params = "save")
	public @ResponseBody boolean save(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		VehicleLogBookDTO list = this.getModel().getVehicleLogBookDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		list.setOrgId(orgId);
		if (tbvehicleService.veLogBookDupCheck(list) == false) {
			return false;
		}
		return true;

	}
		
}

