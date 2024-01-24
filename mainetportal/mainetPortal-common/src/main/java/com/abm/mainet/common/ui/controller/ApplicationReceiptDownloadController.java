package com.abm.mainet.common.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.model.ApplicationReceiptFormModel;
import com.abm.mainet.common.util.UserSession;
@Controller
@RequestMapping("/ApplicationReceiptDownload.html")
public class ApplicationReceiptDownloadController extends AbstractController<ApplicationReceiptFormModel>{
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        return new ModelAndView("ReceiptApplicationFormHome", MainetConstants.FORM_NAME, this.getModel());

    }
	
	@RequestMapping(params = "generateApplicationReceipt", method = RequestMethod.POST)
	public @ResponseBody String generateApplicationReceipt(@RequestParam("applicationNo") String applicationNo,@RequestParam("appReceiptDate") String appReceiptDate, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
    		getModel().bind(httpServletRequest);
				return ServiceEndpoints.Application_NO_BIRT_REPORT_URL + "=ReceiptReprintingReport_ApplicationNoWise.rptdesign&ULBName="
				+ UserSession.getCurrent().getOrganisation().getOrgid()+"&ApplicationNo="+ applicationNo + "&ReceiptDate="+ appReceiptDate;
	}
	
}
