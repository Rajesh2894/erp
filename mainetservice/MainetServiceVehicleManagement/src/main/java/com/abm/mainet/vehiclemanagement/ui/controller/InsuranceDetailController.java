package com.abm.mainet.vehiclemanagement.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleScheduleDetDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IInsuranceDetailService;
import com.abm.mainet.vehiclemanagement.service.ILogBookService;
import com.abm.mainet.vehiclemanagement.ui.model.InsuranceDetailModel;


@Controller
@RequestMapping("/insuranceDetails.html")
public class InsuranceDetailController extends AbstractFormController<InsuranceDetailModel>{
	
	
	/**
	 * IVehicleMaster Service
	 */
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	
	@Autowired
	private IInsuranceDetailService iinsuranceDetailService;
	
	 /**
     * TbAc Vendormaster Service
     */
    @Autowired
    private TbAcVendormasterService vendorMasterService;
    
    

	@Autowired
	ILogBookService tbvehicleService;

	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		/*List<VehicleLogBookDTO> list = tbvehicleService.getAllVehiclesWithoutEmp(UserSession.getCurrent().getOrganisation().getOrgid());*/
		httpServletRequest.setAttribute("departments", loadDepartmentList());
        //model.addAttribute("ListVehicles", list);
		this.getModel().setCommonHelpDocs("insuranceDetails.html");
		return defaultResult();
	}


	/**
	 * add insurance Details
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params =Constants.ADD_InsuranceDetails)
	public ModelAndView addInsurancedetails(final HttpServletRequest request) {
		sessionCleanup(request);
		   populateVendorList();
	  //  this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid()));
		request.setAttribute("departments", loadDepartmentList());
		return new ModelAndView(Constants.InsuranceDetails_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	
	   /**
     * Populates the list of vendors
     */
    private void populateVendorList() {
        final Organisation org = UserSession.getCurrent().getOrganisation();
     //   final Integer languageId = UserSession.getCurrent().getLanguageId();
        final LookUp lookUpVendorStatus = CommonMasterUtility.getValueFromPrefixLookUp(AccountConstants.AC.getValue(),PrefixConstants.VSS);
        final Long vendorStatus = lookUpVendorStatus.getLookUpId();
        final List<TbAcVendormaster> vendorList = vendorMasterService.getAllActiveVendors(org.getOrgid(), vendorStatus);
        this.getModel().setVendorList(vendorList);

    }
    
	@RequestMapping(params = "SearchInsuranceDetails", method = RequestMethod.POST)
	public @ResponseBody List<InsuranceDetailsDTO> findInsuranceDetails(@RequestParam("department") Long department,@RequestParam("vehicleType") Long vehicleType, @RequestParam("veid") Long veid, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		List<InsuranceDetailsDTO> insuranceDetailsDTOs = iinsuranceDetailService.searchInsuranceDetails(department, vehicleType, veid,orgid);
	
		request.setAttribute("InsuranceDetailsData", insuranceDetailsDTOs);
		return getvehicleType(insuranceDetailsDTOs);
        
	}
	
	@RequestMapping(params = "viewInsuranceForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPetrolRequests(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long insuranceDetId, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setInsuranceDetailsDto(iinsuranceDetailService.getDetailById(insuranceDetId));
		this.getModel().setSaveMode(mode);
//		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		List<InsuranceDetailsDTO> list = iinsuranceDetailService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListVehicles", list);
	    this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),mode));
	    populateVendorList();
		return new ModelAndView(Constants.InsuranceDetails_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	/**
	 * This will set mode as Edit and render view on Insurance Detail Form as an
	 * Edit Mode
	 * 
	 * @param mode               String Mode(E:EDIT)
	 * @param id                 Long insuranceDetId
	 * @param httpServletRequest
	 * @return ModelAndView
	 */

	@RequestMapping(params = "editInsuranceForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewPetrolRequest(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long insuranceDetId, final HttpServletRequest httpServletRequest,Model reqmodel) {
		    this.getModel().setInsuranceDetailsDto(iinsuranceDetailService.getDetailById(insuranceDetId));
		    this.getModel().setSaveMode(mode);
	  //    	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
	    	httpServletRequest.setAttribute("departments", loadDepartmentList());
		    List<InsuranceDetailsDTO> list = iinsuranceDetailService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		    reqmodel.addAttribute("ListVehicles", list);
		    this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),mode));
		    populateVendorList();
		    return new ModelAndView(Constants.InsuranceDetails_FORM, MainetConstants.FORM_NAME, this.getModel());
	}
	
	private List<InsuranceDetailsDTO> getvehicleType(List<InsuranceDetailsDTO> insuranceDetailsDTOs) {
		insuranceDetailsDTOs.forEach(oemwarranty -> {
			oemwarranty.setVeDesc(CommonMasterUtility.getCPDDescription(oemwarranty.getVehicleType(), MainetConstants.BLANK));
		});
		return insuranceDetailsDTOs;
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
	
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getVehicleNo")
	public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("id") Long vehicleTypeId,
			@RequestParam("mode") String mode, final HttpServletRequest httpServletRequest) {
		List<GenVehicleMasterDTO> result = vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleTypeId, null,
				UserSession.getCurrent().getOrganisation().getOrgid(), "");
		Map<Long, String> data = new HashMap<>();
		if (result != null && !result.isEmpty()) {
			if (mode != null && mode.equals("E")) {
				result.forEach(vdata -> {
					data.put(vdata.getVeId(), vdata.getVeNo());
				});
			} else {
				result.forEach(vdata -> {
					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(new Date());
					Date date = cal1.getTime();
					if (vdata.getVeFlag().equals(MainetConstants.FlagN) && (vdata.getVeRentFromdate()!=null && vdata.getVeRentTodate()!=null)
							&& ((date.after(vdata.getVeRentFromdate()) && date.before(vdata.getVeRentTodate()))
									|| (date.equals(vdata.getVeRentFromdate()))
									|| (date.equals(vdata.getVeRentFromdate())))) {
						data.put(vdata.getVeId(), vdata.getVeNo());
					} else if (vdata.getVeFlag().equals(MainetConstants.FlagY)) {
						data.put(vdata.getVeId(), vdata.getVeNo());
					}
				});
			}
		}
		return data;
	}
	
	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class);
		List<Department> departments = departmentService.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}
	
	//Already Existed Validation
	@ResponseBody
    @RequestMapping(params = "recordAlreadyExists", method = RequestMethod.POST)
    public Boolean checkIfexit (@RequestParam String veNo, @RequestParam Date issueDate, @RequestParam Date endDate) throws ParseException {
    	
		boolean stat = false;
    	Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    	InsuranceDetailsDTO insuranceDto = new InsuranceDetailsDTO();
    	Long insurDetId=this.getModel().getInsuranceDetailsDto().getInsuranceDetId();
    	insuranceDto.setVeId(Long.valueOf(veNo));
    	insuranceDto.setIssueDate(issueDate);
    	insuranceDto.setEndDate(endDate);
    	
    	List<InsuranceDetailsDTO> checkforExistance = iinsuranceDetailService.insuranceDetails(issueDate, endDate, insurDetId,insuranceDto.getVeId(), orgid);
    	if((checkforExistance.size()) > 0) {
    		stat = false;
		} else {
			stat = true;
		}
    	
    return stat;
    
    }
	
	@RequestMapping(params = "checkInsuranceDate", method = RequestMethod.POST)
	public @ResponseBody Boolean checkInsuranceDate(@RequestParam Long veNo,@RequestParam Date issueDate) {

		return  iinsuranceDetailService.searchVehicleByVehicleTypeAndVehicleRegNo( veNo,issueDate,UserSession.getCurrent().getOrganisation().getOrgid());		
   
    }
	

}

