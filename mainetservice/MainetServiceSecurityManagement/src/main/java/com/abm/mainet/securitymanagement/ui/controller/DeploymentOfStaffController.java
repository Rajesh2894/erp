package com.abm.mainet.securitymanagement.ui.controller;

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
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.dto.DeploymentOfStaffDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.service.IDeploymentOfStaffService;
import com.abm.mainet.securitymanagement.service.IEmployeeSchedulingService;
import com.abm.mainet.securitymanagement.service.IShiftMasterService;
import com.abm.mainet.securitymanagement.ui.model.DeploymentOfStaffModel;

@Controller
@RequestMapping(value = "/DeploymentOfStaff.html")
public class DeploymentOfStaffController extends AbstractFormController<DeploymentOfStaffModel> {

	@Autowired
	private TbAcVendormasterService tbVendormasterService;

	@Autowired
	private IDeploymentOfStaffService deploymentStaffService;
	
	@Autowired
	private IEmployeeSchedulingService employeeSchedulingService;
	
	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;
	
	@Autowired
	private IShiftMasterService iShiftMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {
		this.sessionCleanup(httpServletRequest);
		List<DeploymentOfStaffDTO> list=deploymentStaffService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		deploymentStaffService.getList(list, loadLocation(), loadVendor());
		
		model.addAttribute("deploymentOfStaffs",list);
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("location", loadLocation());
		model.addAttribute("shift", getDescription(list));
		this.getModel().setLookup(iShiftMasterService.getAvtiveShift(UserSession.getCurrent().getOrganisation().getOrgid()));
		return defaultResult();
	}

	@RequestMapping(params ="searchStaffDetails" ,method=RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<DeploymentOfStaffDTO> searchStaffDetails(@RequestParam("empTypeId")Long empTypeId,@RequestParam("vendorId")Long vendorId,
	@RequestParam("cpdShiftId")Long cpdShiftId,@RequestParam("locId") Long locId,final Model model){
		List<DeploymentOfStaffDTO> searchDetails=deploymentStaffService.findStaffDetails(empTypeId, vendorId, cpdShiftId, 
				locId,UserSession.getCurrent().getOrganisation().getOrgid());
		deploymentStaffService.getList(searchDetails, loadLocation(), loadVendor());
		return getDescription(searchDetails);
		
	}
	
	private List<DeploymentOfStaffDTO> getDescription(List<DeploymentOfStaffDTO> staffs) {
		staffs.forEach(staffDetails -> {
			if(staffDetails.getCpdShiftId() != null) {
				staffDetails.setShiftDesc(CommonMasterUtility.getCPDDescription(staffDetails.getCpdShiftId(),  MainetConstants.BLANK));
			}
		});
		return staffs;
	}
	
	@RequestMapping(params="getVendorDataOnClick",method= {RequestMethod.POST})
	public @ResponseBody Map<String, String>  getStaffNameByVendorId(@RequestParam("vendorId")Long vendorId,@RequestParam("empTypeId")String empTypeId){
		List<DeploymentOfStaffDTO> staffNameList=deploymentStaffService.getStaffNameByVendorId(vendorId,empTypeId, UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setEmployeeList(staffNameList);
		Map<String, String> data = new HashMap<>();
		if (staffNameList != null && !staffNameList.isEmpty()) {
			staffNameList.forEach(vdata -> {
				data.put(vdata.getContStaffIdNo(), vdata.getContStaffName());
			});
		}
		return data;
	}
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = MainetConstants.CommonConstants.ADD)
	public ModelAndView addDeploymentStaff(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			final HttpServletRequest request, final Model model) {
		this.getModel().setSaveMode(mode);
		this.getModel().setDeploymentOfStaffDTO(new DeploymentOfStaffDTO());
		this.getModel().setVendorList(loadVendor()); 
		//this.getModel().setEmpNameList(findEmployeeNameList());
		this.getModel().setLocation(loadLocation());
		this.getModel().setLookup(iShiftMasterService.getAvtiveShift(UserSession.getCurrent().getOrganisation().getOrgid()));
		return new ModelAndView("DeploymentOfStaffValidn", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.VIEW)
	public ModelAndView viewStaff(Model model,@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(mode);
		this.getModel().setDeploymentOfStaffDTO(deploymentStaffService.findById(id));
		this.getModel().setLocation(loadLocation());
		this.getModel().setVendorList(loadVendor());
		this.getModel().setEmpNameList(findEmployeeNameList());
		return new ModelAndView("DeploymentOfStaffValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "getStaffData",method = { RequestMethod.POST })
	public  ModelAndView getEmployeeDetails(@RequestParam(value = "contStaffIdNo") final String contStaffIdNo,@RequestParam(value = "empTypeId") final Long empTypeId,
			@RequestParam("vendorId")final Long vendorId,final Model model,final HttpServletRequest request) {
		this.getModel().bind(request);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ContractualStaffMasterDTO employeeData = deploymentStaffService.findEmpByEmpId(contStaffIdNo,vendorId,orgId);
		Long locId = employeeSchedulingService.getLatestEmployeeScheduledLocId(contStaffIdNo, orgId);
		this.getModel().getDeploymentOfStaffDTO().setContStaffIdNo(employeeData.getContStaffIdNo());
		if(locId != null) {
			this.getModel().getDeploymentOfStaffDTO().setFromLocId(locId);
		}else {
			this.getModel().getDeploymentOfStaffDTO().setFromLocId(employeeData.getLocId());
		}
		this.getModel().getDeploymentOfStaffDTO().setFromCpdShiftId(employeeData.getCpdShiftId());
		this.getModel().getDeploymentOfStaffDTO().setEmpTypeId(empTypeId);
		this.getModel().getDeploymentOfStaffDTO().setVendorId(vendorId);
		this.getModel().getDeploymentOfStaffDTO().setContStaffSchFrom(employeeData.getContStaffSchFrom());
		this.getModel().getDeploymentOfStaffDTO().setContStaffSchTo(employeeData.getContStaffSchTo());
		model.addAttribute("employeeList", employeeData);
		model.addAttribute("location", loadLocation());
		model.addAttribute("empNameList", findEmployeeNameList());
		model.addAttribute("VendorList", loadVendor());
		return new ModelAndView("DeploymentOfStaffValidation", MainetConstants.FORM_NAME, this.getModel());
	}
	
	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}

	private List<TbAcVendormaster> loadVendor() {
		final List<TbAcVendormaster> list1 = contractualStaffMasterService
				.findAgencyBasedOnStaffMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		return list1;
	}

	private List<ContractualStaffMasterDTO> findEmployeeNameList() {
		List<ContractualStaffMasterDTO> empNameList = deploymentStaffService
				.findEmployeeNameList(UserSession.getCurrent().getOrganisation().getOrgid());
		return empNameList;

	}

}
