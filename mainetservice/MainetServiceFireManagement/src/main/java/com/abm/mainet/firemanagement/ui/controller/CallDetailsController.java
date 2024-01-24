package com.abm.mainet.firemanagement.ui.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonUtility;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.firemanagement.ui.model.CallDetailsModel;


@Controller
@RequestMapping("/CallDetails.html")
public class CallDetailsController extends AbstractFormController<CallDetailsModel>
{
	@Resource
	private ILocationMasService iLocationMasService;
	
	
	@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView index(Model model,final HttpServletRequest httpServletRequest)
	{
		this.sessionCleanup(httpServletRequest);
		return index();
		
	}
	
	
	  @RequestMapping(params="GetCallDetails",method= RequestMethod.POST)
	  public @ResponseBody String checkCallDetails(@RequestParam("fireStation")String fireStation,@RequestParam("fromDate")Date fromDate,@RequestParam("toDate")Date toDate,final HttpServletRequest request)
	  {
		  if (StringUtils.isNotBlank(fireStation) && fromDate != null && toDate !=null) 
		  { 
			  return ServiceEndpoints.BIRT_REPORT_URL +"=REP_FIRE_CALLDETAILS.rptdesign&fromDate=" + Utility.dateToString(fromDate,"yyyy-MM-dd") 
			  + "&toDate=" + Utility.dateToString(toDate, "yyyy-MM-dd")+"&fireStation="+fireStation; 
		  } 
		  else 
		  { 
		  return "false"; 
		  }
	  
	  }
	 
	
	  
	
}



