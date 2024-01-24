package com.abm.mainet.bnd.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.ui.model.BirthRegisterModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping(value="/BirthRegister.html")
public class BirthRegisterController extends AbstractFormController<BirthRegisterModel>{
	

	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(final HttpServletRequest httpServletRequest)
	{
		this.sessionCleanup(httpServletRequest);
		return index();
	}
	
	 @RequestMapping(method = RequestMethod.POST, params = "getApplStatus")
	   public ModelAndView getDeathReg(final HttpServletRequest httpServletRequest) {
	   sessionCleanup(httpServletRequest);
	   
	    return customResult("ApplStatus");
	   }
	
	
	@RequestMapping(params = "GetBirthRegister", method=RequestMethod.POST) 
	  public @ResponseBody String viewBirthRegister(@RequestParam ("fromDate")Date fromDate,@RequestParam
	  ("toDate")Date toDate,@RequestParam ("registrationUnit")String registrationUnit , @RequestParam ("periodOfReportBy")String periodOfReportBy,@RequestParam ("sortOrder")String sortOrder,@RequestParam ("reportType")String reportType)
	  {
		if (StringUtils.isNotBlank(registrationUnit) && StringUtils.isNotBlank(periodOfReportBy) && StringUtils.isNotBlank(sortOrder) 
				&& StringUtils.isNotBlank(reportType) && fromDate != null && toDate != null) {
			
	        return ServiceEndpoints.BIRT_REPORT_URL +"Birth_Register_Report.rptdesign&registrationUnit="+ registrationUnit 
	         + "&periodOfReportBy="+ periodOfReportBy + "&reportType="+ reportType + "&sortOrder="+ sortOrder+"&fromDate="+
	        Utility.dateToString(fromDate, "yyyy-MM-dd")+"&toDate="+ Utility.dateToString(toDate, "yyyy-MM-dd");
	    }
	  else 
	     { 
		  return "f"; 
		 }
     }

}
