package com.abm.mainet.securitymanagement.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.service.IEmployeeSchedulingService;
import com.abm.mainet.securitymanagement.ui.model.EmployeeOvertimeModel;

@Controller
@RequestMapping(value = "/EmployeeOvertimeEntry.html")
public class EmployeeOvertimeController extends AbstractFormController<EmployeeOvertimeModel>{
	
	@Autowired
	private TbAcVendormasterService tbVendormasterService;
	
	@Autowired
	private IEmployeeSchedulingService employeeSchedulingService;
	
	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,Model model) {
		sessionCleanup(httpServletRequest);
		List<EmployeeSchedulingDTO> emplDto = new ArrayList<EmployeeSchedulingDTO>();
		this.getModel().setCommonHelpDocs("EmployeeAttendanceEntry.html");
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("location", loadLocation());
		model.addAttribute("empName", findContractualEmpNameById());
		this.getModel().setVendorList(loadVendor());
		this.getModel().setLocation(loadLocation());
		this.getModel().setEmployeeSchedulingDTOList(emplDto);
		
	    return index();
		
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
	
	public List<EmployeeSchedulingDTO> findContractualEmpNameById() {
		List<EmployeeSchedulingDTO> empList = employeeSchedulingService.findContractualEmpNameById();
		return empList;
	}
	
	@ResponseBody
	@RequestMapping(params = "getEmpForOverTime", method = RequestMethod.POST)
	public ModelAndView  getEmpForOverTime(final HttpServletRequest httpServletRequest,Model model,
			@RequestParam("empTypeId") Long empTypeId, @RequestParam("vendorId") Long vendorId,
			@RequestParam("locId") Long locId, @RequestParam("cpdShiftId") Long cpdShiftId,
			@RequestParam("contStaffSchFrom") Date contStaffSchFrom,
			@RequestParam("contStaffSchTo") Date contStaffSchTo, @RequestParam("contStaffName") String contStaffName,
			@RequestParam("contStaffIdNo") String contStaffIdNo) {
		sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		String empCode=null;
		if(empTypeId!=null) {
			LookUp lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(empTypeId, UserSession.getCurrent().getOrganisation());
			empCode=lookUp.getLookUpCode();
		}
		List<EmployeeSchedulingDTO> employeeScheList = employeeSchedulingService.searchEmployees(empCode, vendorId,
				locId, cpdShiftId, contStaffSchFrom, contStaffSchTo, contStaffName, contStaffIdNo, null,UserSession.getCurrent().getOrganisation().getOrgid());
		
		if (!employeeScheList.isEmpty()) {
			if (!employeeScheList.get(0).getEmplDetDto().isEmpty()) {
				model.addAttribute("employeeSchedulingDTOList", employeeScheList.get(0).getEmplDetDto());
				List<EmployeeSchedulingDetDTO> empdetList = new ArrayList<>();
				this.getModel().setEmployeeSchedulingDTOList(employeeScheList);
				this.getModel().setEmployeeSchedulingDTO(employeeScheList.get(0));
				if (employeeScheList.get(0).getEmplDetDto() != null) {
					for (EmployeeSchedulingDTO employeeSchedulingDTO : employeeScheList) {
						employeeSchedulingDTO.getEmplDetDto().forEach(d -> {
							if ((Utility.compareDate(contStaffSchFrom, d.getShiftDate()) && Utility.compareDate(d.getShiftDate(),contStaffSchTo)) || 
									(Utility.comapreDates(contStaffSchFrom,d.getShiftDate())) ||(Utility.comapreDates(contStaffSchTo,d.getShiftDate()))) {
								empdetList.add(d);
							}
					   });
					}
					this.getModel().setDetList(empdetList);
				}
			}
		}
		
		if (this.getModel().getDetList().isEmpty()) {
			this.getModel().addValidationError(
					getApplicationSession().getMessage("ContractualStaffMasterDTO.Validation.noRecord"));
		}
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("location", loadLocation());
		model.addAttribute("empName", findContractualEmpNameById());
		this.getModel().setVendorList(loadVendor());
		this.getModel().setLocation(loadLocation());
		model.addAttribute("empName", findContractualEmpNameById());
		this.getModel().getEmployeeSchedulingDTO().setEmpTypeId(empTypeId);
        this.getModel().getEmployeeSchedulingDTO().setVendorId(vendorId);
        this.getModel().getEmployeeSchedulingDTO().setLocId(locId);
        this.getModel().getEmployeeSchedulingDTO().setCpdShiftId(cpdShiftId);
        this.getModel().getEmployeeSchedulingDTO().setContStaffSchFrom(contStaffSchFrom);
        this.getModel().getEmployeeSchedulingDTO().setContStaffSchTo(contStaffSchTo);
        this.getModel().getEmployeeSchedulingDTO().setContStaffIdNo(contStaffIdNo);
		
		ModelAndView mv = new ModelAndView("EmployeeOvertimeValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
}

	