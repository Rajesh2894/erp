package com.abm.mainet.firemanagement.ui.controller;

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
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.VehicleLogBookDTO;
import com.abm.mainet.firemanagement.service.ILogBookService;
import com.abm.mainet.firemanagement.ui.model.VehicleLogBookReportModel;

@Controller
@RequestMapping(value = "/VehLogBookReport.html")
public class FireManagementReportController extends AbstractFormController<VehicleLogBookReportModel>{
	
	@Autowired
	ILogBookService tbvehicleService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(Model model,final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		//Long department = departmentService.getDepartmentIdByDeptCode(Constants.FIRE_DRPT_CODE);
		//List<VehicleLogBookDTO> list = tbvehicleService.getAllVehiclesByDept(UserSession.getCurrent().getOrganisation().getOrgid(),department);
		//model.addAttribute("ListDriver", list);
		return index();
	}
	
	@RequestMapping(params="GetVehLogBook",method= RequestMethod.POST)
	public @ResponseBody String checkCallDetails(@RequestParam("fromDate")Date fromDate,@RequestParam("toDate")Date toDate,@RequestParam("veNo")String veNo,final HttpServletRequest request)
	  {
		  if (fromDate != null && toDate !=null) 
		  { 
			  return ServiceEndpoints.BIRT_REPORT_URL +"=REP_VEH_LOG_BOOK.rptdesign&fromDate=" + Utility.dateToString(fromDate,"yyyy-MM-dd") 
			  + "&toDate=" + Utility.dateToString(toDate, "yyyy-MM-dd")+"&veNo="+veNo; 
		  } 
		  else 
		  { 
		  return "false"; 
		  }
	  }
	
	@RequestMapping(params="getVeNo",method= RequestMethod.POST)
	public ModelAndView getVeNo(Model model,@RequestParam("fromDate")Date fromDate,@RequestParam("toDate")Date toDate,final HttpServletRequest request){
		getModel().bind(request);
		//List<VehicleLogBookDTO> list = tbvehicleService.searchFireCallRegisterwithDate(fromDate,toDate,null,UserSession.getCurrent().getOrganisation().getOrgid());
		List<String> list = tbvehicleService.getVehicleNoListByFromTodate(fromDate, toDate, UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("ListDriver", list);
		 this.getModel().getVehicleLogBookReportDTO().setFromDate(fromDate);
		 this.getModel().getVehicleLogBookReportDTO().setToDate(toDate);
		return new ModelAndView("FireManagementReportVal", MainetConstants.FORM_NAME, model); 
		 
	  }
}
