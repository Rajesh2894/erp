package com.abm.mainet.securitymanagement.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.ui.model.LocationDetailOfStaffModel;

@Controller
@RequestMapping(value = "SecurityReport.html")
public class BirtSecurityManagementController extends AbstractFormController<LocationDetailOfStaffModel> {

	@Resource
	private TbAcVendormasterService tbVendormasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		httpServletRequest.setAttribute("location", loadLocation());
		httpServletRequest.setAttribute("VendorList", loadVendor());
		return index();

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
	
	 @RequestMapping(params="GetLocationDetails",method= RequestMethod.POST)
	  public @ResponseBody String checkCallDetails(@RequestParam("contStaffName")String contStaffName,@RequestParam("empTypeId")Long empTypeId
			  ,@RequestParam("locId")Long locId,@RequestParam("vendorId")Long vendorId,@RequestParam("cpdShiftId")Long cpdShiftId,final HttpServletRequest request)
	  {
			  return ServiceEndpoints.BIRT_REPORT_URL+"=REP_LOCATION_DETAIL_OF_STAFF.rptdesign&contStaffName=" + contStaffName
			  +"&locId="+locId+"&vendorId="+vendorId+"&cpdShiftId="+cpdShiftId; 
		
	  }

}

	