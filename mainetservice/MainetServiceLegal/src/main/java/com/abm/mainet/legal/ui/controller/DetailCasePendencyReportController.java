package com.abm.mainet.legal.ui.controller;

import java.util.List;

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
import com.abm.mainet.legal.dto.AdvocateMasterDTO;

import com.abm.mainet.legal.service.IAdvocateMasterService;
import com.abm.mainet.legal.service.ICaseEntryService;

import com.abm.mainet.legal.ui.model.DetailCasePendencyReportModel;

@Controller
@RequestMapping("/DetailCaseReport.html")
public class DetailCasePendencyReportController extends AbstractFormController<DetailCasePendencyReportModel> {

	
	  @Autowired
	  private IAdvocateMasterService iadvocateMasterservice;
	  
	/*
	 * @Autowired private ICourtMasterService courtMasterService;
	 */
	  
	   @Autowired
	    private ICaseEntryService caseEntryService;
	  
	 
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpservletrequest) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		DetailCasePendencyReportModel model = this.getModel();
		
		model.setCaseEntryListDto(caseEntryService.getAllCaseEntry(currentOrgId));

		
		/*
		 * model.setCourtMasterDTOList(courtMasterService.getAllActiveCourtMaster(
		 * currentOrgId));
		 */
	  List<AdvocateMasterDTO> advName =  iadvocateMasterservice.getAllAdvocateMaster(currentOrgId); 
		  for (AdvocateMasterDTO advMasterDto : advName) {
		  advMasterDto.setFullName(advMasterDto.getAdvFirstNm()+" "+advMasterDto.getAdvLastNm());
		  } 
		  model.setAdvocateName(advName);
		 return index();
	}
	
	  @RequestMapping(params="GetCaseReport",method= {RequestMethod.POST,RequestMethod.GET}) 
	  public @ResponseBody String  viewDetailCasePendencySheet(@RequestParam("CasedSuitNo") String CasedSuitNo,
	@RequestParam("cseDivision") Long cseDivision, @RequestParam("cseAdvocateId") Long cseAdvocateId, 
			@RequestParam("cseCourtId") Long cseCourtId, final HttpServletRequest request) {

      Long  currentOrgId=UserSession.getCurrent().getOrganisation().getOrgid();
	 // String caseSuit = CasedSuitNo.trim();
	  return ServiceEndpoints.LEGAL_CASE_BIRT_REPORT_URL+"=DetailCasePendencyReport.rptdesign&OrgId="+currentOrgId+"&CaseSuitNo="+
	  CasedSuitNo+"&Division="+cseDivision+"&AdvocateId="+cseAdvocateId+"&CourtId="+cseCourtId;
	  
	  
	  }
	 
}
