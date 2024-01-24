package com.abm.mainet.bnd.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.ui.model.BNDBirtReportModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value="/BirthAndDeathReport.html")
public class BNDBirtReportController extends AbstractFormController<BNDBirtReportModel>{
	
	@RequestMapping(method= {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(final HttpServletRequest httpServletRequest)
	{
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("BNDBirtReport", MainetConstants.FORM_NAME, getModel());
	}
	
	@RequestMapping(params = "GetBirtReport",  method=RequestMethod.POST)
	public @ResponseBody String viewBirtReport(@RequestParam("birtReport") String birtReport,
			final HttpServletRequest request) {

		if (birtReport!=null) {
			return ServiceEndpoints.BIRT_REPORT_URL + '=' +birtReport;
				
		} else {
			return "f";
		}

	}
	
	
	
}
