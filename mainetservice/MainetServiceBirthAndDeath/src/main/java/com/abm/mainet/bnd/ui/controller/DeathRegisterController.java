package com.abm.mainet.bnd.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.ui.model.DeathRegisterModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;


@Controller
@RequestMapping(value="/DeathRegister.html")
public class DeathRegisterController extends AbstractFormController<DeathRegisterModel>{
	
	@Autowired
	private IEmployeeService employeeService;
	
	@Autowired
	private DepartmentService departmentService;
	
	
	
  @RequestMapping(method = RequestMethod.POST, params = "getDeathFemale")
   public ModelAndView getDeathFemale(final HttpServletRequest httpServletRequest) {
   sessionCleanup(httpServletRequest);
   Long bndDeptId = departmentService.getDepartmentIdByDeptCode("BND");
   httpServletRequest.setAttribute("bndDeptEmployee", employeeService.getEmployeeData(bndDeptId , null, null , UserSession.getCurrent().getOrganisation().getOrgid(), null));
    return customResult("DeathFemale");
   }
	
	
  @RequestMapping(method = RequestMethod.POST, params = "getDeathReg")
   public ModelAndView getDeathReg(final HttpServletRequest httpServletRequest) {
   sessionCleanup(httpServletRequest);
   
    return customResult("DeathReg");
   }
	
	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST})
   public  ModelAndView index(Model model,final HttpServletRequest httpServletRequest)
   {
		this.sessionCleanup(httpServletRequest);
	   
	    		return index();
	
   }
	@RequestMapping(params = "GetDeathRegister", method=RequestMethod.POST) 
	  public @ResponseBody String viewDeathRegister(@RequestParam ("fromDate")Date fromDate,@RequestParam
	  ("toDate")Date toDate,@RequestParam ("registrationUnit")String registrationUnit , @RequestParam ("periodOfReportBy")String periodOfReportBy,@RequestParam ("sortOrder")String sortOrder)
	  {
		if (StringUtils.isNotBlank(registrationUnit) && StringUtils.isNotBlank(periodOfReportBy) && StringUtils.isNotBlank(sortOrder) 
				 && fromDate != null && toDate != null) {
			
	        return ServiceEndpoints.BIRT_REPORT_URL +"Death_Register_Report.rptdesign&registrationUnit="+ registrationUnit 
	         + "&periodOfReportBy="+ periodOfReportBy  + "&sortOrder="+ sortOrder+"&fromDate="+
	        Utility.dateToString(fromDate, "yyyy-MM-dd")+"&toDate="+ Utility.dateToString(toDate, "yyyy-MM-dd");
	    }
	  else 
	     { 
		  return "f"; 
		 }
   }
	
	
	@RequestMapping(params = "GetDeathRegisterFemale", method=RequestMethod.POST) 
	  public @ResponseBody String viewBirthRegister(@RequestParam ("periodFrom")Date periodFrom,@RequestParam
	  ("periodTo")Date periodTo,@RequestParam ("registrationUnit")String registrationUnit , @RequestParam ("periodOfReportBy")String periodOfReportBy,@RequestParam ("user")String user)
	  {
		if (StringUtils.isNotBlank(registrationUnit) && StringUtils.isNotBlank(periodOfReportBy) && StringUtils.isNotBlank(user) 
	 && periodFrom != null && periodTo != null) {
			
	        return ServiceEndpoints.BIRT_REPORT_URL +"Death_Register_Female_Report.rptdesign&registrationUnit="+ registrationUnit 
	         + "&periodOfReportBy="+ periodOfReportBy + "&user="+ user +"&periodFrom="+
	        Utility.dateToString(periodFrom, "yyyy-MM-dd")+"&periodTo="+ Utility.dateToString(periodTo, "yyyy-MM-dd");
	    }
	  else 
	     { 
		  return "f"; 
		 }
 }
	

}
