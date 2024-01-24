package com.abm.mainet.bnd.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.ui.model.HospitalWiseBirthDetailsModel;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractController;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping(("/HospitalWiseBirthDetails.html"))
public class HospitalWiseBirthDetailsController extends AbstractController<HospitalWiseBirthDetailsModel> {
	
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		return index();
	}
		

	@RequestMapping(params="GetAllHospitalWiseBirthDetails",method= RequestMethod.POST)
	  public @ResponseBody String viewAllHospitalWiseBirthDetailsReport(@RequestParam("fromDate")Date
	  fromDate,@RequestParam("toDate")Date toDate,final HttpServletRequest request)
	  {
	  
	  if (fromDate != null && toDate != null) { 
		  	return ServiceEndpoints.BIRT_REPORT_URL +"Hospital_Wise_Birth_Details_Report.rptdesign&fromDate=" + Utility.dateToString(fromDate,
		  			"yyyy-MM-dd") + "&toDate=" + Utility.dateToString(toDate, "yyyy-MM-dd");
		  	} 
	  	else 
	  	{ 
	  		return "false"; 
	  	}
	  
	  }
	
	

	
	
}
