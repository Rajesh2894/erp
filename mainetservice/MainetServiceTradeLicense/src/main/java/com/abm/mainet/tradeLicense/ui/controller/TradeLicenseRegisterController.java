package com.abm.mainet.tradeLicense.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.ui.model.TradeLicenseRegisterModel;

@Controller
@RequestMapping("/TradeLicenseRegister.html")
public class TradeLicenseRegisterController  extends AbstractFormController<TradeLicenseRegisterModel>{
 @RequestMapping(method = { RequestMethod.POST,RequestMethod.GET })
	    public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
	        sessionCleanup(httpServletRequest);
              TradeLicenseRegisterModel   model = this.getModel();
	        return index();
	    }

	    @RequestMapping(params ="GetTradeLicense",method={RequestMethod.POST, RequestMethod.GET })
	    public @ResponseBody String viewTradeLicenseReportSheet(@RequestParam("ward1") Long ward1, @RequestParam("ward2") Long ward2,
	    	final HttpServletRequest request) {
	           sessionCleanup(request);

	        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
	         
	        return ServiceEndpoints.TRADE_LICENSE_BIRT_REPORT_URL+"=TradeLicenseRegister.rptdesign&OrgId="
	                + currentOrgId +  "&WardLevel1=" + ward1 + "&WardLevel2=" + ward2;
	     
	    }

	}	

