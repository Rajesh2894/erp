package com.abm.mainet.securitymanagement.ui.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.domain.ShiftMaster;
import com.abm.mainet.securitymanagement.dto.CalendarDataDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDTO;
import com.abm.mainet.securitymanagement.dto.EmployeeSchedulingDetDTO;
import com.abm.mainet.securitymanagement.repository.ShiftMasterRepository;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.service.IEmployeeSchedulingService;
import com.abm.mainet.securitymanagement.service.IShiftMasterService;
import com.abm.mainet.securitymanagement.ui.model.EmployeeSchedulingModel;

@Controller
@RequestMapping(value = "/employeeCalendar.html")
public class EmployeeSchedulingController extends AbstractFormController<EmployeeSchedulingModel> {

	@Autowired
	private TbAcVendormasterService tbVendormasterService;

	@Autowired
	private IEmployeeSchedulingService employeeSchedulingService;
	
	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;
	
	
	
	@Autowired
	private IShiftMasterService iShiftMasterService;
	
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {
		this.sessionCleanup(httpServletRequest);
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("location", loadLocation());
		model.addAttribute("empName", findContractualEmpNameById());
		return defaultResult();
	}

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }, params = MainetConstants.CommonConstants.ADD)
	public ModelAndView addEmployeeScheduling(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			final HttpServletRequest request, final Model model) {
		
		this.getModel().setSaveMode(mode);
		this.getModel().setEmployeeSchedulingDTO(new EmployeeSchedulingDTO());
		this.getModel().setVendorList(loadVendor()); 
		this.getModel().setLocation(loadLocation());
		this.getModel().setLookup(iShiftMasterService.getAvtiveShift(UserSession.getCurrent().getOrganisation().getOrgid()));
		
		return new ModelAndView("EmployeeSchedulingValidn", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "getCalData", method = RequestMethod.POST)
	public @ResponseBody List<CalendarDataDTO> empSchedulingDataOnSearch(final HttpServletRequest httpServletRequest,
			@RequestParam("empTypeId") Long empTypeId, @RequestParam("vendorId") Long vendorId,
			@RequestParam("locId") Long locId, @RequestParam("cpdShiftId") Long cpdShiftId,
			@RequestParam("contStaffSchFrom") Date contStaffSchFrom,
			@RequestParam("contStaffSchTo") Date contStaffSchTo, @RequestParam("contStaffName") String contStaffName,
			@RequestParam("contStaffIdNo") String contStaffIdNo) {

		String empCode=null;
		if(empTypeId!=null) {
			LookUp lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(empTypeId, UserSession.getCurrent().getOrganisation());
			empCode=lookUp.getLookUpCode();
		}
		List<EmployeeSchedulingDTO> employeeScheList = employeeSchedulingService.searchEmployees(empCode, vendorId,
				locId, cpdShiftId, contStaffSchFrom, contStaffSchTo, contStaffName, contStaffIdNo, null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		List<CalendarDataDTO> calanderList = new ArrayList<>();
		List<EmployeeSchedulingDTO> empdetList = new ArrayList<>();

		for (EmployeeSchedulingDTO emplist : employeeScheList) {
			List<EmployeeSchedulingDetDTO> detDto = employeeSchedulingService.findStaffDetails(emplist.getEmplScdlId(),
					emplist.getOrgid());
			EmployeeSchedulingDTO empdet = new EmployeeSchedulingDTO();
			if (detDto != null) {
				detDto.forEach(d -> {
					empdet.setEmplScdlId(emplist.getEmplScdlId());
					empdet.setContStaffName(emplist.getContStaffName());
					empdet.setShiftDesc(emplist.getShiftDesc());
					empdet.setLocId(emplist.getLocId());
					empdet.setFromTime(d.getStartimeShift().toString());
					empdet.setToTime(d.getEndtimeShift().toString());
					String startTime = timeToDateConvert(d.getStartimeShift());
					String endTime = timeToDateConvert(d.getEndtimeShift());
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:a");
					Date startDate = null;
					Date endDate = null;
					try {
						String dateConvert = dateConvert(d.getShiftDate(), startTime);
						String dateConvert1 = dateConvert(d.getShiftDate(), endTime);
						startDate = dateFormat.parse(dateConvert);
						endDate = dateFormat.parse(dateConvert1);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					CalendarDataDTO calanderDTO = new CalendarDataDTO(empdet.getEmplScdlId(), startDate,
							empdet.getContStaffName() + "", "bg-black", endDate, "");
					empdetList.add(empdet);
					calanderList.add(calanderDTO);
				});
			}
		}
		return calanderList;
	}

	public String timeToDateConvert(Date date) {
		String dateString = null;
		DateFormat sdf = new SimpleDateFormat("HH:mm:a");
		dateString = sdf.format(date);
		return dateString;
	}

	public String dateConvert(Date date, String startTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String str = formatter.format(date);
		String d = str + "T" + startTime;
		return d;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchEmployeeScheduling")
	public ModelAndView searchEmployeeScheduling(@RequestParam(value = "emplScdlId") final Long emplScdlId,
			final Model model) {

		List<EmployeeSchedulingDTO> emplList = employeeSchedulingService.searchEmployees(null, null, null, null, null,
				null, null, null, emplScdlId, UserSession.getCurrent().getOrganisation().getOrgid());
		EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
		dto.setEmpTypeId(emplList.get(0).getEmpTypeId());
		dto.setVendorId(emplList.get(0).getVendorId());
		dto.setContStaffSchFrom(emplList.get(0).getContStaffSchFrom());
		dto.setContStaffSchTo(emplList.get(0).getContStaffSchTo());
		dto.setContStaffIdNo(emplList.get(0).getContStaffIdNo());
		dto.setContStaffName(emplList.get(0).getContStaffName());
		this.getModel().setEmployeeSchedulingDTOList(emplList);
		this.getModel().setEmployeeSchedulingDTO(dto);
		this.getModel().setSaveMode("V");
		model.addAttribute("location", loadLocation());
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("empName", findContractualEmpNameById());
		return new ModelAndView("EmployeeSchedulingView", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "getStaffNameByVendorId", method = { RequestMethod.POST })
	public ModelAndView getStaffNameByVendorId(@RequestParam("vendorId") Long vendorId,
			@RequestParam("empTypeId") Long empTypeId, @RequestParam("contStaffSchFrom") Date contStaffSchFrom,
			@RequestParam("contStaffSchTo") Date contStaffSchTo, final HttpServletRequest request, final Model model) {
		String empCode=null;
		if(empTypeId!=null) {
			LookUp lookUp=CommonMasterUtility.getNonHierarchicalLookUpObject(empTypeId, UserSession.getCurrent().getOrganisation());
			empCode=lookUp.getLookUpCode();
		}
		List<EmployeeSchedulingDTO> staffListByVendor = employeeSchedulingService.getStaffNameByVendorId(vendorId,empCode,UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("staffListbyVendor", staffListByVendor);
		EmployeeSchedulingDTO dto = new EmployeeSchedulingDTO();
		dto.setVendorId(vendorId);
		dto.setEmpTypeId(empTypeId);
		dto.setContStaffSchFrom(contStaffSchFrom);
		dto.setContStaffSchTo(contStaffSchTo);
		model.addAttribute("location", loadLocation());
		model.addAttribute("VendorList", loadVendor());
		this.getModel().setEmployeeSchedulingDTO(dto);
		this.getModel().setEmpNameList(staffListByVendor);
		return new ModelAndView("EmployeeSchedulingValidn", MainetConstants.FORM_NAME, this.getModel());
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

	

}
