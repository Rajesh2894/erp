package com.abm.mainet.securitymanagement.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ShiftMasterDTO;
import com.abm.mainet.securitymanagement.service.IShiftMasterService;
import com.abm.mainet.securitymanagement.ui.model.ShiftMasterModel;

@Controller
@RequestMapping(value = "/ShiftMaster.html")
public class ShiftMasterController extends AbstractFormController<ShiftMasterModel> {

	@Autowired
	IShiftMasterService shiftService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		List<ShiftMasterDTO> list = shiftService.searchShift(null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("shiftList", list);
		return new ModelAndView("ShiftMaster", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.ADD)
	public ModelAndView addShiftMaster(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			final HttpServletRequest request, Model model) {
		this.getModel().setSaveMode(mode);
		return new ModelAndView("ShiftMasterValidn", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "searchShift", method = { RequestMethod.POST }, produces = "Application/JSON")
	public @ResponseBody List<ShiftMasterDTO> findShift(@RequestParam("shiftId") final Long shiftId,
			final HttpServletRequest request, final Model model) {
		List<ShiftMasterDTO> list = shiftService.searchShift(shiftId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		return list;
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView getShiftData(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest,
			Model model) {
		this.getModel().setSaveMode(mode);
		this.getModel().setShiftMasterDTO(shiftService.findById(id));
		return new ModelAndView("ShiftMasterEdit", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.VIEW)
	public ModelAndView viewShiftData(@RequestParam(MainetConstants.REQUIRED_PG_PARAM.MODE) String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long id, final HttpServletRequest httpServletRequest,
			Model model) {
		this.getModel().setSaveMode(mode);
		this.getModel().setShiftMasterDTO(shiftService.findById(id));
		return new ModelAndView("ShiftMasterEdit", MainetConstants.FORM_NAME, getModel());
	}
}
