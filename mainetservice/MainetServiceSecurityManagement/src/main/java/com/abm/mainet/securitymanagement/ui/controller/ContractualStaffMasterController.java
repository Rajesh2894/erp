package com.abm.mainet.securitymanagement.ui.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;


import javax.annotation.Resource;
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
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.ui.model.ContractualStaffMasterModel;
/**
 * 
 * @author arunshinde
 *
 */
@Controller
@RequestMapping("/ContractualStaffMaster.html")
public class ContractualStaffMasterController extends AbstractFormController<ContractualStaffMasterModel> {

	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;

	@Autowired
	private DesignationService designationService;
	 
	@Resource
	private TbAcVendormasterService tbVendormasterService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private EmployeeJpaRepository employeeJpaRepository;
	
	/**
	 * It will return summary page of Contractual Staff Master
	 * 
	 * @param httpServletRequest
	 * @return ModelAndView
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("ContractualStaffMaster.html");
		httpServletRequest.setAttribute("location", loadLocation());
		httpServletRequest.setAttribute("VendorList", loadVendor());
	    List<Designation> desigList = contractualStaffMasterService.findDesignNameById(UserSession.getCurrent().getOrganisation().getOrgid());
	    List<ContractualStaffMasterDTO>  list = contractualStaffMasterService.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		List<ContractualStaffMasterDTO> list1=getDescription(list);   
		contractualStaffMasterService.getList(list1, desigList, loadLocation(), loadVendor());
	    httpServletRequest.setAttribute("contractualStaffMasters",list1);
	    return new ModelAndView("ContractualStaffMaster", MainetConstants.FORM_NAME, getModel());
		
	}
	
	@RequestMapping(params = "searchStaffDetails", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<ContractualStaffMasterDTO> findStaff(
			@RequestParam("contStaffName") final String contStaffName,
			@RequestParam("vendorId") final Long vendorId, @RequestParam("locId") final Long locId,
			@RequestParam("cpdShiftId") final Long cpdShiftId,@RequestParam("dayPrefixId") final Long dayPrefixId, final HttpServletRequest request, final Model model) {

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<ContractualStaffMasterDTO> staffDetails = contractualStaffMasterService.findStaffDetails(contStaffName,
				vendorId, locId, cpdShiftId,dayPrefixId ,orgId);
		List<Designation> desigList = contractualStaffMasterService.findDesignNameById(UserSession.getCurrent().getOrganisation().getOrgid());
		contractualStaffMasterService.getList(staffDetails, desigList, loadLocation(), loadVendor());
		return getDescription(staffDetails);  
	}
	
	/* To get description */
	private List<ContractualStaffMasterDTO> getDescription(List<ContractualStaffMasterDTO> staffs) {
		staffs.forEach(staffDetails -> {
			if(staffDetails.getCpdShiftId() != null) {
				staffDetails.setShiftDesc(CommonMasterUtility.getCPDDescription(staffDetails.getCpdShiftId(),  MainetConstants.BLANK));
			}
		});
		return staffs;
	}
	
	/**
	 * It will set mode as ADD and render ContractualStaffMasterr Form
	 * 
	 * @param mode    String Mode(ADD:A, EDIT:E, VIEW:V)
	 * @param request
	 * @return ModelAndViewtbVendormasterService
	 */
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = MainetConstants.CommonConstants.ADD)
	public ModelAndView addContractualStaffMasterr(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			final HttpServletRequest request, final Model model) {

		this.getModel().setSaveMode(mode);
		request.setAttribute("location", loadLocation());
		request.setAttribute("VendorList", loadVendor());
		model.addAttribute("designation", getDesignationData(request, model));
		this.getModel().setDto(new ContractualStaffMasterDTO());
		return new ModelAndView("ContractualStaffMasterValidn", MainetConstants.FORM_NAME, getModel());
		
	}

	private List<TbAcVendormaster> loadVendor() {
		final List<TbAcVendormaster> list1 = tbVendormasterService
				.findAll(UserSession.getCurrent().getOrganisation().getOrgid());
		return list1;
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;

	}


	 public  List<DesignationBean> getDesignationData(final HttpServletRequest request, final Model model) {
	        List<DesignationBean> designlist = null;
	        try {
	            // --- Populates the model with a new instance
	            final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	            designlist = designationService.getDesignByDeptId(null, orgId);
	            Collections.sort(designlist);
	        } catch (final Exception e) {
	            e.printStackTrace();
	        }
	        return designlist;
	    }
	
	 @RequestMapping(params = "checkEmployeeId", method = RequestMethod.POST)
	 public @ResponseBody Boolean checkDuplicateEmpId(@RequestParam("vmVendorid")Long vmVendorid,@RequestParam("contStaffIdNo")String contStaffIdNo) {
		
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		return contractualStaffMasterService.checkDuplicateEmployeeId(vmVendorid, contStaffIdNo, orgId); 
	 }
	 
	 @RequestMapping(params ="checkMobileNumber", method = RequestMethod.POST)
	 public @ResponseBody Boolean checkDuplicateMobileNumber(@RequestParam("contStaffMob")String contStaffMob, @RequestParam("contStsffId") Long contStsffId) {
		 
		 final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 return contractualStaffMasterService.checkDuplicateMobileNo(contStsffId, contStaffMob, orgId);
	 }
	 
	/**
	 * It will set mode as EDIT/VIEW and render ContractualStaffMasterr Form
	 * 
	 * @param mode               String Mode(ADD:A, EDIT:E, VIEW:V)
	 * @param id
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView getContractualStaffMasterr(Model model,@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest) {
		this.getModel().setSaveMode(mode);
		this.getModel().setDto(contractualStaffMasterService.findById(id));
		if(this.getModel().getDto().getContStaffIdNo()!=null && this.getModel().getDto().getEmpType()!=null && this.getModel().getDto().getEmpType().equals(MainetConstants.FlagR)) {
			this.getModel().getDto().setEmpId(Long.valueOf(this.getModel().getDto().getContStaffIdNo()));
			List<EmployeeBean> empList = new ArrayList<EmployeeBean>();
			empList=employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());				
			model.addAttribute("empList", empList);
		}
		httpServletRequest.setAttribute("location", loadLocation());
		httpServletRequest.setAttribute("VendorList", loadVendor());
		model.addAttribute("designation", getDesignationData(httpServletRequest, model));
		return new ModelAndView("ContractualStaffMasterValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.VIEW)
	public ModelAndView viewContractualStaffMasterr(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest,Model model) {
		this.getModel().setSaveMode(mode);
		this.getModel().setDto(contractualStaffMasterService.findById(id));
		if(this.getModel().getDto().getContStaffIdNo()!=null && this.getModel().getDto().getEmpType()!=null && this.getModel().getDto().getEmpType().equals(MainetConstants.FlagR)) {
			this.getModel().getDto().setEmpId(Long.valueOf(this.getModel().getDto().getContStaffIdNo()));
			List<EmployeeBean> empList = new ArrayList<EmployeeBean>();
			empList=employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());				
			model.addAttribute("empList", empList);
		}
		httpServletRequest.setAttribute("location", loadLocation());
		httpServletRequest.setAttribute("VendorList", loadVendor());
		model.addAttribute("designation", getDesignationData(httpServletRequest, model));
		return new ModelAndView("ContractualStaffMasterValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params="getEmployeeList",method = { RequestMethod.POST })	// get list of permanent employees
	public @ResponseBody List<Object[]> getEmployeelist(@RequestParam("empType")String empType, HttpServletRequest req,Model model){
		//List<EmployeeBean> empList = new ArrayList<EmployeeBean>();
		
		if(empType!=null && empType.equals(MainetConstants.FlagR)) {
			//empList=employeeService.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setEmployees(employeeJpaRepository.findAllEmpInfoByOrg(UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.WorksManagement.SM));
			
			/*
			 * Collections.sort(empList, new Comparator<EmployeeBean>() { public int
			 * compare(EmployeeBean A, EmployeeBean B) {
			 * 
			 * return A.getFullName().compareTo(B.getFullName()); } });
			 */
		}		
		
		return this.getModel().getEmployees();
	}

	@RequestMapping(params="getEmpData",method = { RequestMethod.POST })    // Set data for permanent employees
	public ModelAndView  getEmpData(@RequestParam("empId")Long empId, HttpServletRequest req,Model model){
		Employee employee=employeeService.findEmployeeByIdAndOrgId(empId, UserSession.getCurrent().getOrganisation().getOrgid());
		ContractualStaffMasterDTO dto = new ContractualStaffMasterDTO();
		String fullName = "";

		if (employee.getEmpname() != null) {
		    fullName = employee.getEmpname();
		}

		if (employee.getEmpmname() != null) {
		    fullName += MainetConstants.WHITE_SPACE + employee.getEmpmname();
		}

		if (employee.getEmplname() != null) {
		    fullName += MainetConstants.WHITE_SPACE + employee.getEmplname();
		}

		dto.setContStaffName(fullName);

		
		if(employee.getEmpGender()!=null) {
			LookUp lookUp= CommonMasterUtility.getValueFromPrefixLookUp(employee.getEmpGender(), MainetConstants.GENDER, UserSession.getCurrent().getOrganisation());
			dto.setSex(String.valueOf(lookUp.getLookUpId()));
		}
		dto.setContStaffMob(employee.getEmpmobno());
		dto.setContStaffAddress(employee.getEmpAddress());
		dto.setDob(employee.getEmpdob());
		dto.setDsgId(employee.getDesignation().getDsgid());
		dto.setEmpId(employee.getEmpId());
		dto.setContStaffIdNo(employee.getEmpId().toString());
		dto.setLocId(employee.getTbLocationMas().getLocId());
		this.getModel().setDto(dto);
		this.getModel().setSaveMode(MainetConstants.FlagV);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			this.getModel().setEmployees(employeeJpaRepository.findAllEmpInfoByOrg(
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.WorksManagement.SM));
		} else {
			List<EmployeeBean> empList = employeeService
					.getAllEmployee(UserSession.getCurrent().getOrganisation().getOrgid());

			Collections.sort(empList, new Comparator<EmployeeBean>() {
				public int compare(EmployeeBean A, EmployeeBean B) {

					return A.getFullName().compareTo(B.getFullName());
				}
			});
		
		 
		model.addAttribute("empList", empList);
		}
		model.addAttribute("location", loadLocation());
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("designation", getDesignationData(req, model));
		return new ModelAndView("ContarctualEmpData", MainetConstants.FORM_NAME, this.getModel());
	}
	

	
}
