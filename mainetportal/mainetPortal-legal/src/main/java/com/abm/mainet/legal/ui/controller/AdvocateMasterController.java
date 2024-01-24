package com.abm.mainet.legal.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.service.AdvocateMasterService;
import com.abm.mainet.legal.ui.model.AdvocateMasterModel;

@Controller
@RequestMapping("/AdvocateMaster.html")
public class AdvocateMasterController extends AbstractFormController<AdvocateMasterModel> {
	
	private static final Logger LOG = Logger.getLogger(AdvocateMasterController.class);
	
	@Autowired
	private AdvocateMasterService advocateMasterService;
	
	@Autowired
	private IOrganisationService iOrganisationService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);		
		this.getModel().setCommonHelpDocs("AdvocateMaster.html");
		this.getModel().setSaveMode(MainetConstants.FlagC);
		this.getModel().setOrg(iOrganisationService.getAllOrganisationActiveWorkflow(MainetConstants.Legal.DEPT_CODE));
		return new ModelAndView(MainetConstants.Legal.ADVOCATE_MASTER_FORM, MainetConstants.FORM_NAME, this.getModel());
	}

	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long applicationId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		AdvocateMasterModel model = this.getModel();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		AdvocateMasterDTO reqDto = advocateMasterService.getAdvocateDetailsById(applicationId,orgid);
		if (reqDto != null)
		model.setAdvocateMasterDTO(reqDto);
		model.setSaveMode(MainetConstants.FlagV);
		mv = new ModelAndView(MainetConstants.Legal.ADVOCATE_MASTER_VALIDN, MainetConstants.FORM_NAME, this.getModel());
        return mv;
	}


}
