package com.abm.mainet.rts.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.rts.dto.FirstAppealDto;
import com.abm.mainet.rts.service.AppealService;
import com.abm.mainet.rts.ui.model.RtsFirstAppealModel;
@Controller
@RequestMapping("/FirstAppeal.html")
public class RtsFirstAppealController extends AbstractFormController<RtsFirstAppealModel> {
	
	
	@Autowired
	AppealService appeal;
	
	
	
	
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		RtsFirstAppealModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		FirstAppealDto reqDto = appeal.getFirstAppealData(appId,orgId);
		model.getFirstAppealDto().setStatus(appStatus);
		model.setFirstAppealDto(reqDto);
		mv = new ModelAndView("rtsFirstAppeal",
                MainetConstants.FORM_NAME, this.getModel());
        return mv;
		
	}
	
	@RequestMapping(params = "saveFirstAppeal", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveObjectiondetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        getModel().bind(httpServletRequest);
        ModelAndView mv = null;
        RtsFirstAppealModel model = this.getModel();
        if (model.saveForm()) {
            return jsonResult(JsonViewObject
                    .successResult(model.getSuccessMessage()));
        } else {
            mv = new ModelAndView("rtsFirstAppealValidn", MainetConstants.FORM_NAME,
                    getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
    }

}
