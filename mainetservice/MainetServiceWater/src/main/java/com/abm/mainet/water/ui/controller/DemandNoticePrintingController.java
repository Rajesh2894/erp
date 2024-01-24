package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.water.ui.model.DemandNoticePrintingModel;

/**
 * @author Lalit.Prusti
 *
 */
@Controller
@RequestMapping("/DemandNoticePrinting.html")
public class DemandNoticePrintingController extends AbstractFormController<DemandNoticePrintingModel> {

	private static final String FINAL_DEMAND_NOTICE = "FinalDemandNotice";

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("DemandNoticePrinting.html");
		return defaultResult();
	}

	@RequestMapping(params = "preView", method = RequestMethod.POST)
	public ModelAndView searchAllDefaulter(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final DemandNoticePrintingModel model = getModel();
		model.search();
		return defaultMyResult();
	}

	@RequestMapping(params = "printDemandNotice", method = RequestMethod.POST)
	public ModelAndView printDemandNotice(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final DemandNoticePrintingModel model = getModel();
		if (model.saveForm()) {
			model.setCommonData();
			return new ModelAndView(FINAL_DEMAND_NOTICE, MainetConstants.FORM_NAME, model);
		} else {
			return defaultMyResult();
		}
	}

}
