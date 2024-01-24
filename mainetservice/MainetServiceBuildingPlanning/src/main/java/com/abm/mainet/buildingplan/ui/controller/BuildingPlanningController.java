package com.abm.mainet.buildingplan.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;

@Controller
@RequestMapping("/BuildingPlanning.html")
public class BuildingPlanningController {
	
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,final Model model) {
		return new ModelAndView("itemOpeningBalance/list", MainetConstants.FORM_NAME, model);
	}

}
