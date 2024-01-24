package com.abm.mainet.intranet.ui.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.intranet.domain.Choice;
import com.abm.mainet.intranet.domain.Poll;
import com.abm.mainet.intranet.domain.PollView;
import com.abm.mainet.intranet.service.ChoiceService;
import com.abm.mainet.intranet.service.PollService;
import com.abm.mainet.intranet.service.PollViewService;
import com.abm.mainet.intranet.ui.model.PollModel;

@Controller
@RequestMapping(value = "/PollView.html")
public class PollViewController extends AbstractFormController<PollModel>{
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private PollViewService pollViewService;
	
	@Autowired
	private ChoiceService choiceService;
		
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long userId = UserSession.getCurrent().getEmployee().getEmpId();
		
		List<Poll> pollData = pollService.getPollData(orgId);
		if((pollData!=null) && !(pollData.isEmpty())){
			appModel.setIntranetPollObj(pollData.get(0).getQuestion());
			appModel.setIntranetChoiceList(pollData.get(0).getChoices());
			appModel.getPollRequest().setPollid(pollData.get(0).getPollid());
			appModel.getPollRequest().setPollQue(pollData.get(0).getQuestion());
		}
		
		if((pollData!=null) && !(pollData.isEmpty())){
			if(pollData.get(0).getPollid() != null) {
				List<PollView> pollViewData = pollViewService.getPollViewDet(pollData.get(0).getPollid(),userId);
				if(pollViewData != null && !(pollViewData.isEmpty())) {
					appModel.getPollRequest().setCheckLoggedEmp(pollViewData.get(0).getLoggedinEmpId());
					appModel.getPollRequest().setChoiceDescVal(pollViewData.get(0).getChoiceDescVal());
					
				}
			}
		}
		
		return new ModelAndView("PollView", MainetConstants.FORM_NAME, appModel);
		
	}
    

    @RequestMapping(params = "savePollVeiwData", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView pollCreationQuestion(@RequestParam("pollQue") final String pollQue, @RequestParam("choiceRadio") final String choiceRadio, @RequestParam("pollid") final Long pollid, 
			@RequestParam("pollAnsId") final Long pollAnsId, final HttpServletRequest request, final Model model) {
		PollModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		if(pollAnsId != null) {
			Choice choiceDesc = choiceService.getChoiceDesc(orgId, pollAnsId);
			appModel.setChoiceText(choiceDesc.getText());
		}
		
		appModel.savePollViewData(pollid, pollQue, choiceRadio, pollAnsId, appModel.getChoiceText());
		return new ModelAndView("PollViewValidn", MainetConstants.FORM_NAME, appModel);
	}
    
	
	
}
