package com.abm.mainet.legal.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.legal.service.ICaseEntryService;
import com.abm.mainet.legal.ui.model.CaseImplementaionDetailsReportModel;

@Controller
@RequestMapping("/CaseImplementationDetailsReport.html")
public class CaseImplementationDetailsReportController extends AbstractFormController<CaseImplementaionDetailsReportModel> {

	@Autowired
	private ICaseEntryService caseEntryService;
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpservletrequest) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		CaseImplementaionDetailsReportModel model = this.getModel();	
		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));
		 return index();
	}
	
	
	
	  @RequestMapping(params="GetCaseReport",method= {RequestMethod.POST,RequestMethod.GET}) 
	  public @ResponseBody String  viewCaseImplDetailReport(@RequestParam("CasedSuitNo") String CasedSuitNo,
			  @RequestParam("csefrmDate") Date csefrmDate,@RequestParam("csetoDate") Date csetoDate, final HttpServletRequest request) {
		  
      Long  currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
  	  String fromdate = Utility.dateToString(csefrmDate, "yyyy-MM-dd");
	  String todate = Utility.dateToString(csetoDate, "yyyy-MM-dd");

      
	  return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL+"=CaseImplementationDetailsReport.rptdesign&OrgId="+currentOrgId+"&CaseSuitNo="+
	  CasedSuitNo + "&ActiveFromDate=" + fromdate + "&ActiveToDate=" + todate ;  
	  
	  }
	
}
