package com.abm.mainet.rts.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.rts.ui.model.RtsExternalAppealModel;

@Controller
@RequestMapping(value = "/RTSExternalAppeal.html")
public class RtsExternalAppealController extends AbstractFormController<RtsExternalAppealModel> {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		sessionCleanup(httpServletRequest);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		return new ModelAndView("rtsExternalAppeal", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "openExternalAppealForm", method = RequestMethod.POST)
	public ModelAndView openExternalAppealForm(final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		return new ModelAndView("rtsExternalAppeal", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "saveExternalAppeal", method = RequestMethod.POST)
    public @ResponseBody ModelAndView saveObjectiondetails(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws Exception {
        getModel().bind(httpServletRequest);
        ModelAndView mv = null;
        RtsExternalAppealModel model = this.getModel();
        if (model.saveForm()) {
            return jsonResult(JsonViewObject
                    .successResult(model.getSuccessMessage()));
        } else {
            mv = new ModelAndView("rtsExternalAppealValidn", MainetConstants.FORM_NAME,
                    getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
    }

}
