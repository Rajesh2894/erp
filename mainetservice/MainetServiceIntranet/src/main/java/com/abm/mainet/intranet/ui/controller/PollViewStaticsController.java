package com.abm.mainet.intranet.ui.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.service.PollViewService;
import com.abm.mainet.intranet.ui.model.PollModel;

@Controller
@RequestMapping(value = "/PollViewStatics.html")
public class PollViewStaticsController extends AbstractFormController<PollModel>{
	
	@Autowired
	private PollViewService pollViewService;
	
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		
    	sessionCleanup(httpServletRequest);
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid(); 
		
		//Fetch Count
		List<Object> pollViewCount = pollViewService.getPollViewCount(orgId);
		if (pollViewCount !=null && (!pollViewCount.isEmpty())) {
			appModel.setIntranetPollViewDTOListObj(pollViewCount);
		}
						
		//Fetch All Data
		//List<PollView> pollViewData = pollViewService.getPollViewData(orgId);
		//appModel.setIntranetPollViewDTOList(pollViewData);

		return new ModelAndView("PollViewStatics", MainetConstants.FORM_NAME, appModel);
		
	}
    
    
	
	
}
