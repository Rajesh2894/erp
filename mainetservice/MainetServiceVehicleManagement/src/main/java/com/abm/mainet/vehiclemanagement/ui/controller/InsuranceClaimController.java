package com.abm.mainet.vehiclemanagement.ui.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceClaimDTO;
import com.abm.mainet.vehiclemanagement.dto.InsuranceDetailsDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IInsuranceClaimService;
import com.abm.mainet.vehiclemanagement.service.IInsuranceDetailService;
import com.abm.mainet.vehiclemanagement.service.ILogBookService;
import com.abm.mainet.vehiclemanagement.ui.model.InsuranceClaimModel;

@Controller
@RequestMapping("/insuranceClaim.html")
public class InsuranceClaimController extends AbstractFormController<InsuranceClaimModel> {

	@Autowired
	ILogBookService tbvehicleService;

	@Autowired
	private IGenVehicleMasterService vehicleMasterService;

	@Autowired
	private TbAcVendormasterService vendorMasterService;

	@Autowired
	private IInsuranceDetailService iinsuranceDetailService;

	@Autowired
	private IInsuranceClaimService iinsuranceClaimService;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
    private IAttachDocsService attachDocsService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		 fileUpload.sessionCleanUpForFileUpload();
		populateVendorList();
		/*List<VehicleLogBookDTO> list = tbvehicleService
				.getAllVehiclesWithoutEmp(UserSession.getCurrent().getOrganisation().getOrgid());*/
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		//model.addAttribute("ListVehicles", list);
		this.getModel().setCommonHelpDocs("insuranceClaim.html");
		return defaultResult();
	}
	
	// ADD 
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params =Constants.ADD_InsuranceDetails)
	public ModelAndView addInsurancedetails(final HttpServletRequest request) {
		sessionCleanup(request);
		   populateVendorList();
		request.setAttribute("departments", loadDepartmentList());
		return new ModelAndView("InsuranceClaimForm", MainetConstants.FORM_NAME, this.getModel());
	}
	
	//VIEW
	@RequestMapping(params = "viewInsuranceForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewPetrolRequests(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long insuranceDetId, final HttpServletRequest httpServletRequest,
			Model reqmodel) {
		this.getModel().setInsuranceClaimDto(iinsuranceClaimService.getDetailById(insuranceDetId));
		this.getModel().setSaveMode(mode);
		httpServletRequest.setAttribute("departments", loadDepartmentList());
		List<InsuranceClaimDTO> list = iinsuranceClaimService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		reqmodel.addAttribute("ListVehicles", list);
	    this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),mode));
	    populateVendorList();
	    final List<AttachDocs> attachDocs = attachDocsService.findByCode(
                UserSession.getCurrent().getOrganisation().getOrgid(),
                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH +insuranceDetId +MainetConstants.WINDOWS_SLASH +UserSession.getCurrent().getOrganisation().getOrgid()));
        this.getModel().setAttachDocsList(attachDocs);
		return new ModelAndView("InsuranceClaimForm", MainetConstants.FORM_NAME, this.getModel());
	}

	// EDIT
	@RequestMapping(params = "editInsuranceForm", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewPetrolRequest(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long insuranceDetId, final HttpServletRequest httpServletRequest,Model reqmodel) {
		    this.getModel().setInsuranceClaimDto(iinsuranceClaimService.getDetailById(insuranceDetId));
		    this.getModel().setSaveMode(mode);
	    	httpServletRequest.setAttribute("departments", loadDepartmentList());
		    List<InsuranceClaimDTO> list = iinsuranceClaimService.getAllVehicles(UserSession.getCurrent().getOrganisation().getOrgid());
		    reqmodel.addAttribute("ListVehicles", list);
		    this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(null, null,UserSession.getCurrent().getOrganisation().getOrgid(),mode));
		    populateVendorList();
		    final List<AttachDocs> attachDocs = attachDocsService.findByCode(
	                UserSession.getCurrent().getOrganisation().getOrgid(),
	                (Constants.SHORT_CODE + MainetConstants.WINDOWS_SLASH + insuranceDetId +MainetConstants.WINDOWS_SLASH + UserSession.getCurrent().getOrganisation().getOrgid()));
	        this.getModel().setAttachDocsList(attachDocs);
		    return new ModelAndView("InsuranceClaimForm", MainetConstants.FORM_NAME, this.getModel());
	}

	private List<Department> loadDepartmentList() {
		DepartmentService departmentService = ApplicationContextProvider.getApplicationContext()
				.getBean(DepartmentService.class);
		List<Department> departments = departmentService
				.getDepartments(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA);
		return departments;
	}

	@SuppressWarnings("deprecation")
	private void populateVendorList() {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		final LookUp lookUpVendorStatus = CommonMasterUtility.getValueFromPrefixLookUp(AccountConstants.AC.getValue(),
				PrefixConstants.VSS);
		final Long vendorStatus = lookUpVendorStatus.getLookUpId();
		final List<TbAcVendormaster> vendorList = vendorMasterService.getAllActiveVendors(org.getOrgid(), vendorStatus);
		this.getModel().setVendorList(vendorList);

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
					if (vdata.getVeFlag().equals(MainetConstants.FlagN)
							&& (vdata.getVeRentFromdate() != null && vdata.getVeRentTodate() != null)
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
	
	@RequestMapping(params = "SearchInsuranceClaim", method = RequestMethod.POST)
	public @ResponseBody List<InsuranceClaimDTO> findInsuranceClaim(@RequestParam("department") Long department,@RequestParam("vehicleType") Long vehicleType, @RequestParam("veid") Long veid, HttpServletRequest request) {
		getModel().bind(request);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<InsuranceClaimDTO> insuranceDetailsDTOs = iinsuranceClaimService.searchInsuranceClaim(department, vehicleType, veid,orgid);
		request.setAttribute("InsuranceDetailsData", insuranceDetailsDTOs);
		return getvehicleType(insuranceDetailsDTOs);
        
	}

	@RequestMapping(params = "SearchInsuranceDetails", method = RequestMethod.POST)
	public @ResponseBody ModelAndView findInsuranceDetails(@RequestParam("department") Long department,
			@RequestParam("vehicleType") Long vehicleType, @RequestParam("veid") Long veid,
			HttpServletRequest request) {
		getModel().bind(request);
		ModelAndView mv = null;
		InsuranceClaimDTO dto = new InsuranceClaimDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		request.setAttribute("departments", loadDepartmentList());
		this.getModel().setVehicleMasterList(vehicleMasterService.searchVehicleByVehTypeAndVehRegNo(vehicleType, null,
				UserSession.getCurrent().getOrganisation().getOrgid(), "E"));
		List<InsuranceDetailsDTO> insuranceDetailsDTOs = iinsuranceDetailService.searchInsuranceDetails(department,
				vehicleType, veid, orgid);
		dto.setVehicleType(vehicleType);
		dto.setDepartment(department);
		//dto.setVeNo(insuranceDetailsDTOs.get(0).getVeNo());
		dto.setVeId(veid);
		if (insuranceDetailsDTOs != null && !insuranceDetailsDTOs.isEmpty()) {
			
			for (int i = 0; i < insuranceDetailsDTOs.size(); i++) {
				Date date = new Date();
				String curentDate = new SimpleDateFormat("dd/MM/yyyy").format((Date) date);
				String issueDate = new SimpleDateFormat("dd/MM/yyyy")
						.format((Date) insuranceDetailsDTOs.get(i).getIssueDate());
				String endDate = new SimpleDateFormat("dd/MM/yyyy")
						.format((Date) insuranceDetailsDTOs.get(i).getEndDate());

				if ((date.after(insuranceDetailsDTOs.get(i).getIssueDate())
						&& date.before(insuranceDetailsDTOs.get(i).getEndDate())) || curentDate.equals(issueDate)
						|| curentDate.equals(endDate)) {
					dto.setInsuredBy(insuranceDetailsDTOs.get(i).getInsuredBy());
					dto.setPolicyNo(insuranceDetailsDTOs.get(i).getPolicyNo());
					dto.setInsuredAmount(insuranceDetailsDTOs.get(i).getInsuredAmount());
					dto.setIssueDate(insuranceDetailsDTOs.get(i).getIssueDate());
					dto.setEndDate(insuranceDetailsDTOs.get(i).getEndDate());
				}
			}
		}
		this.getModel().setInsuranceClaimDto(dto);
		if (dto.getIssueDate() == null || insuranceDetailsDTOs == null || insuranceDetailsDTOs.isEmpty()) {
			mv = new ModelAndView("InsuranceClaimForm", MainetConstants.FORM_NAME, getModel());
			this.getModel().addValidationError(getApplicationSession().getMessage("vehicle.validate.noInsurancedetail"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		} else {
			return new ModelAndView("InsuranceClaimForm", MainetConstants.FORM_NAME, getModel());
		}

	}

	// Already Existed Validation
	@ResponseBody
	@RequestMapping(params = "recordAlreadyExists", method = RequestMethod.POST)
	public Boolean checkIfexit(@RequestParam String veNo, @RequestParam Date issueDate, @RequestParam Date endDate)
			throws ParseException {

		boolean stat = false;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		InsuranceClaimDTO insuranceDto = new InsuranceClaimDTO();
		Long insurDetId = this.getModel().getInsuranceClaimDto().getInsuranceClaimId();
		insuranceDto.setVeId(Long.valueOf(veNo));
		insuranceDto.setIssueDate(issueDate);
		insuranceDto.setEndDate(endDate);
		List<InsuranceClaimDTO> checkforExistance = iinsuranceClaimService.insuranceClaim(issueDate, endDate,
				insurDetId, insuranceDto.getVeId(), orgid);
		if ((checkforExistance.size()) > 0) {
			stat = false;
		} else {
			stat = true;
		}
		return stat;
	}
	
	private List<InsuranceClaimDTO> getvehicleType(List<InsuranceClaimDTO> insuranceDetailsDTOs) {
		insuranceDetailsDTOs.forEach(oemwarranty -> {
			oemwarranty.setVeDesc(CommonMasterUtility.getCPDDescription(oemwarranty.getVehicleType(), MainetConstants.BLANK));
		});
		return insuranceDetailsDTOs;
	}

}
