package com.abm.mainet.disastermanagement.ui.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.disastermanagement.ui.model.DisasterCallDetailsModel;

@Controller
@RequestMapping(value="/DisCallDetails.html")
public class DisasterCallDetailsController extends AbstractFormController<DisasterCallDetailsModel>
{
	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(final HttpServletRequest httpServletRequest)
	{
		this.sessionCleanup(httpServletRequest);
		httpServletRequest.setAttribute("location", loadLocation()); 
		return index();
		
	}
	
	  private List<TbLocationMas> loadLocation() { 
		  ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class); 
		  List<TbLocationMas> locations =locationMasService.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid()); 
		  return locations;
	  
	  }
	  
	
	
	
	  @RequestMapping(params="GetDisCallDetails" ,method=RequestMethod.POST)
	  public @ResponseBody String verifyDetails(@RequestParam("callType")String callType,@RequestParam("location")String location,@RequestParam("fromDate")Date fromDate,@RequestParam("toDate")Date toDate,
			  @RequestParam("subType")String subType ,final HttpServletRequest request) 
	  {
		  if (StringUtils.isNotBlank(callType) && StringUtils.isNotBlank(location) && StringUtils.isNotBlank(subType) && fromDate != null && toDate != null) 
		  {
	  
			  return ServiceEndpoints.BIRT_REPORT_URL +"=REP_DIS_CALLDETAILS.rptdesign&fromDate=" + Utility.dateToString(fromDate,"yyyy-MM-dd") +
					  "&toDate=" + Utility.dateToString(toDate, "yyyy-MM-dd")+"&callType="+callType+"&location="+location +"&subType="+subType; 
		  } 
		  else { 
			  return "false"; 
		  }
	  
	  }
	 
	 
	 
	 
}
