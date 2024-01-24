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
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.OEMWarrantyDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IOEMWarrantyService;
import com.abm.mainet.vehiclemanagement.ui.model.OEMWarrantyModel;

@Controller
@RequestMapping("/OEMWarranty.html")
public class OEMWarrantyController extends AbstractFormController<OEMWarrantyModel> {

	/*
	 * @Autowired private DepartmentService deptservice;
	 * 
	 */

	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	@Autowired
	private IOEMWarrantyService iOemWarrantyService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		List<OEMWarrantyDTO> list = iOemWarrantyService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		model.addAttribute("ListVehicles", list);
		this.getModel().setCommonHelpDocs("OEMWarranty.html");
		return defaultResult();
	}

	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

	/**
	 * add OEM Warranty
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = Constants.ADD_OEMWARRANTY)
	public ModelAndView addOEMWarranty(final HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setSaveMode(Constants.SaveMode.CREATE);
		if(this.getModel().getSaveMode().equals("C")){
			this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,	UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getSaveMode()));
		}else {
			this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,	UserSession.getCurrent().getOrganisation().getOrgid(), ""));
		}
		request.setAttribute("departments", loadDepartmentList());
		// Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		return new ModelAndView(Constants.OEMWarranty_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleType")
	public @ResponseBody Map<Long, String> searchVehicleType(@RequestParam("id") Long deptId,
			final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> list = vehicleMasterService.searchVehicleTypeByDeptId(deptId, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Map<Long, String> data = new HashMap<>();
		if (list != null && !list.isEmpty()) {
			list.forEach(veType -> {
				veType.setVeTypeDesc(
						CommonMasterUtility.getCPDDescription(veType.getVeVetype(), MainetConstants.BLANK));
				data.put(veType.getVeVetype(), veType.getVeTypeDesc());
			});
		}
		return data;
	}
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleNo")
	public @ResponseBody Map<Long, String> getVehicleNo(@RequestParam("id") Long vehicleType,
			@RequestParam("mode") String mode, final HttpServletRequest httpServletRequest) {
		// String mode = "C";
		List<GenVehicleMasterDTO> vehicleNoList = vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleType,
				null, UserSession.getCurrent().getOrganisation().getOrgid(), "");
		Map<Long, String> data = new HashMap<>();
		if (vehicleNoList != null && !vehicleNoList.isEmpty()) {
			if (mode != null && mode.equals("E")) {
				vehicleNoList.forEach(veNos -> {
					data.put(veNos.getVeId(), veNos.getVeNo());
				});
			} else {
				vehicleNoList.forEach(veNos -> {
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(new Date());
					Date date = cal1.getTime();

					if ((veNos.getVeFlag().equals(MainetConstants.FlagN)) && (veNos.getVeRentFromdate()!=null && veNos.getVeRentTodate()!=null)
							&& ((date.after(veNos.getVeRentFromdate()) && date.before(veNos.getVeRentTodate()))
									|| (date.equals(veNos.getVeRentFromdate()))
									|| (date.equals(veNos.getVeRentFromdate())))) {
						data.put(veNos.getVeId(), veNos.getVeNo());
					} else if (veNos.getVeFlag().equals(MainetConstants.FlagY)) {
						data.put(veNos.getVeId(), veNos.getVeNo());
					}

				});
			}
		}
		return data;
	}
	
	
	@RequestMapping(params = "SearchOemWarranty", method = RequestMethod.POST)
	public @ResponseBody List<OEMWarrantyDTO> finOemWarrantyDetails(@RequestParam("department") Long department,@RequestParam("vehicleType") Long vehicleType, @RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<OEMWarrantyDTO> oemWarrantyDetDTOs = iOemWarrantyService.searchOemWarrantyDetails(department, vehicleType, veNo,orgid);
		/*
		 * Map<Long, String> veheicleMap =
		 * this.getModel().getVehicleMasterList().stream()
		 * .collect(Collectors.toMap(GenVehicleMasterDTO::getVeId,
		 * GenVehicleMasterDTO::getVeNo)); List<GenVehicleMasterDTO> result =
		 * vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleTypeId, null,
		 * UserSession.getCurrent().getOrganisation().getOrgid());
		 */
		/*
		 * List<PetrolRequisitionDTO> list =
		 * petrolRequisitionService.getAllVehicles(UserSession.getCurrent().
		 * getOrganisation().getOrgid());
		 */		// tbvehicleService.getList(veLogBoookDtos, list);
		request.setAttribute("PetrolRequisitionData", oemWarrantyDetDTOs);
		
		return getPartType(oemWarrantyDetDTOs);

	}
	
	
	private List<OEMWarrantyDTO> getPartType(List<OEMWarrantyDTO> oemWarranty) {
		oemWarranty.forEach(oemwarranty -> {
			oemwarranty.setPartDesc(CommonMasterUtility.getCPDDescription(oemwarranty.getPartType(), MainetConstants.BLANK));
		});
		return oemWarranty;
	}
	
	
	

	/**
	 * view Vehicle Scheduling
	 * 
	 * @param mode
	 * @param id
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.VIEW_OEMWARRANTYFORM_FORM)
	public ModelAndView viewOemWarranty(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(Constants.SaveMode.VIEW);
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getSaveMode()));
		this.getModel().setOemWarrantyDto(iOemWarrantyService.getOemWarrantyDetails(id));
		//this.getModel().getOemWarrantyDto().getTbvmoemwarrantydetails().forEach(schDet -> {
		//});
		return new ModelAndView(Constants.OEMWarranty_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(method = { RequestMethod.POST }, params = Constants.EDIT_OEMWARRANTYFORM_FORM)
	public ModelAndView editOemWarranty(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(Constants.SaveMode.EDIT);
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getSaveMode()));
		this.getModel().setOemWarrantyDto(iOemWarrantyService.getOemWarrantyDetails(id));
		//this.getModel().getOemWarrantyDto().getTbvmoemwarrantydetails().forEach(schDet -> {
		//});
		return new ModelAndView(Constants.OEMWarranty_FORM, MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "searchVeNo", method = RequestMethod.POST)
	public @ResponseBody OEMWarrantyDTO searchVeNo(@RequestParam("veNo") Long veNo, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<OEMWarrantyDTO> list = iOemWarrantyService.getVehicleByNo(orgid,veNo);
		return list.get(0);
	}

	@RequestMapping(params = "checkValidInsuranceDate", method = RequestMethod.POST)
	public @ResponseBody Boolean checkInsuranceDate(HttpServletRequest request,Model model) {
		getModel().bind(request);
		return  iOemWarrantyService.searchVehicleByVehicleTypeAndVehicleRegNo( this.getModel().getOemWarrantyDto(),UserSession.getCurrent().getOrganisation().getOrgid());		
    }
/*	private List<OEMWarrantyDTO> getVehicleList(OEMWarrantyDTO listVeh) {
		listVeh.forEach(oemwarranty -> {
			oemwarranty.setVeNo(listVeh.get(0).getVeNo());
		});
		return listVeh;
	}*/
	
	
	/**
	 * edit Vehicle Scheduling
	 * 
	 * @param mode
	 * @param id
	 * @param httpServletRequest
	 * @return
	 */
	/*
	 * @RequestMapping(method = { RequestMethod.POST }, params =
	 * Constants.EDIT_VEHICLESCHEDULING) public ModelAndView
	 * editVehicleScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE)
	 * String mode,
	 * 
	 * @RequestParam(MainetConstants.Common_Constant.ID) Long id, final
	 * HttpServletRequest httpServletRequest) {
	 * this.getModel().setSaveMode(Constants.SaveMode.EDIT);
	 * 
	 * this.getModel().setVehicleScheduleDto(vehicleScheduleservice.
	 * getVehicleScheduleByVehicleScheduleId(id)); this.getModel()
	 * .setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(
	 * this.getModel().getVehicleScheduleDto().getVeVetype(), null,
	 * UserSession.getCurrent().getOrganisation().getOrgid()));
	 * 
	 * this.getModel().getVehicleScheduleDto().getTbSwVehicleScheddets().forEach(
	 * schDet -> {
	 * 
	 * 
	 * schDet.setStartime(timeToStringConvert(schDet.getVesStartime()));
	 * schDet.setEndtime(timeToStringConvert(schDet.getVesEndtime()));
	 * httpServletRequest.setAttribute("departments", loadDepartmentList());
	 * schDet.setDeptDesc(departmentService.fetchDepartmentDescById(schDet.
	 * getDepartment())); // schDet.setSheduleDate(new
	 * SimpleDateFormat("dd/MM/yyyy").format((Date) schDet.getVeScheduledate()));
	 * 
	 * 
	 * });
	 * 
	 * return new ModelAndView(Constants.VEHICLESCHEDULING_FORM,
	 * MainetConstants.FORM_NAME, this.getModel()); }
	 */
	
	
	
	

}


















