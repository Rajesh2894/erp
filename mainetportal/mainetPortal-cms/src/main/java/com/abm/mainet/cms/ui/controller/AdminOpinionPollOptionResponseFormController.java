package com.abm.mainet.cms.ui.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.OpinionPollOptionResponse;
import com.abm.mainet.cms.dto.OpinionPollDTO;
import com.abm.mainet.cms.service.IAdminOpinionPollOptionResponseService;
import com.abm.mainet.cms.ui.model.AdminOpinionPollOptionResponseFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @author swapnil.shirke
 *
 */
@Controller
@RequestMapping("/AdminOpinionPollOptionResponseForm.html")
public class AdminOpinionPollOptionResponseFormController extends AbstractEntryFormController<AdminOpinionPollOptionResponseFormModel> {

	@Autowired
    private IAdminOpinionPollOptionResponseService iAdminOpinionPollOptionResponseService;
	
	 @RequestMapping(method = RequestMethod.GET)
	    public ModelAndView index(final HttpServletRequest httpServletRequest) {
	        sessionCleanup(httpServletRequest);
	        OpinionPollOptionResponse opinionPollOptionResponse = new OpinionPollOptionResponse();
	        opinionPollOptionResponse.setOpinion(getModel().getCitizenOpinion());
	        opinionPollOptionResponse.setOpinionPollList(getModel().getOpinionPolls());
	        getModel().setEntity(opinionPollOptionResponse);
	        
	        ModelAndView modelAndView = new ModelAndView("AdminOpinionPollOptionResponseForm", MainetConstants.FORM_NAME, getModel());
	        modelAndView.addObject("optionpoll", getModel().getCitizenOpinion());
	        return modelAndView;
	    }
	 
	 @RequestMapping(params = "opinionpoll", method = RequestMethod.POST)
	    public ModelAndView opinionpoll(final HttpServletRequest httpServletRequest) {
	        final BindingResult bindingResult = bindModel(httpServletRequest);

	        final AdminOpinionPollOptionResponseFormModel model = getModel();
	        model.setEntity(model.getEntity());
	        if (!bindingResult.hasErrors()) {
	        	iAdminOpinionPollOptionResponseService.saveOrUpdate(getModel().getEntity());
	            sessionCleanup(httpServletRequest);
	            
	            OpinionPollOptionResponse opinionPollOptionResponse = new OpinionPollOptionResponse();
		        opinionPollOptionResponse.setOpinionPollList(getModel().getOpinionPolls());
		        getModel().setEntity(opinionPollOptionResponse);
		        
		        ModelAndView modelAndView = new ModelAndView("AdminOpinionPollOptionResponseForm", MainetConstants.FORM_NAME, getModel());
		        modelAndView.addObject("optionpoll", getModel().getCitizenOpinion());
		        return modelAndView;
	        }
	        return defaultResult();
	    }
	 
	 	@RequestMapping(value = "/opinionpollMobil", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
		@ResponseStatus(HttpStatus.OK)
		@ResponseBody
	    public List<OpinionPollDTO> opinionpollMobile() {
	        

	        final AdminOpinionPollOptionResponseFormModel model = getModel();
	        
		        
	        return model.getOpinionPolls();
	    }
	 	
	 	  @RequestMapping(params = "ActivePollRsult", method = { RequestMethod.GET })
		    public @ResponseBody List<OpinionPollDTO> getActivePollRsult(final HttpServletRequest request,
		            @RequestParam("id") final long id) {
				try {
					
					final List<OpinionPollDTO> organisationsList = iAdminOpinionPollOptionResponseService.getOpinionPolls(id);
					return organisationsList;
				} catch (final Exception exception) {
					
					return null;
				}
				
			}
}


