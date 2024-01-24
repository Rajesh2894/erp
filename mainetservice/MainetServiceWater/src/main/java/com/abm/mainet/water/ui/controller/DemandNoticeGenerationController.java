package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.water.ui.model.DemandNoticeGenerationModel;

/**
 * @author Lalit.Prusti
 *
 */
@Controller
@RequestMapping("/DemandNoticeGeneration.html")
public class DemandNoticeGenerationController extends AbstractFormController<DemandNoticeGenerationModel> {

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("DemandNoticeGeneration.html");
		return defaultResult();
	}

	@RequestMapping(params = "preView", method = RequestMethod.POST)
	public ModelAndView searchAllDefaulter(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		final DemandNoticeGenerationModel model = getModel();
		model.search();
		return defaultMyResult();

	}

}
