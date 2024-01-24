/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.StateInformationDto;
import com.abm.mainet.sfac.service.StateInformationMasterService;
import com.abm.mainet.sfac.ui.model.StateInformationMasterModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.STATE_INFO_MAST_HTML)
public class StateInformationMasterController extends AbstractFormController<StateInformationMasterModel> {
	private static final Logger log = Logger.getLogger(StateInformationMasterController.class);

	@Autowired
	private StateInformationMasterService stateInfoMastService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.STATE_INFO_MAST_SUMMARY, MainetConstants.FORM_NAME, getModel());
	}

	/**
	 * 
	 */
	private void populateModel() {
		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		this.getModel().setStateList(stateList);
	}

	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		populateModel();
		this.getModel().setViewMode(MainetConstants.FlagA);
		return new ModelAndView(MainetConstants.Sfac.STATE_INFO_MAST_FORM, MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(params = "getDistrictList", method = { RequestMethod.POST })
	@ResponseBody
	public List<LookUp> getDistrictListByStateId(@RequestParam("state") Long state, HttpServletRequest request) {
		List<LookUp> lookUpList1 = new java.util.ArrayList<LookUp>();
		try {
			List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
					UserSession.getCurrent().getOrganisation());
			lookUpList1 = lookUpList.stream().filter(lookUp -> lookUp.getLookUpParentId() == state)
					.collect(Collectors.toList());
			return lookUpList1;
		} catch (Exception e) {
			log.error("SDB Prefix not found");
			return lookUpList1;

		}
	}

	@RequestMapping(params = "getStateInfoDetails", method = { RequestMethod.POST })
	@ResponseBody
	public List<StateInformationDto> getStateInfoDetails(@RequestParam("state") Long state,
			@RequestParam("district") Long district, HttpServletRequest request) {
		List<StateInformationDto> infoDtoList = new ArrayList<>();
		infoDtoList = stateInfoMastService.getStateInfoDetailsByIds(state, district,
				null);
		return infoDtoList;
	}

	@RequestMapping(params = MainetConstants.Sfac.EDIT_AND_VIEW_FORM, method = RequestMethod.POST)
	public ModelAndView editAndViewForm(@RequestParam("stId") final Long stId,
			@RequestParam(name = MainetConstants.FORM_MODE, required = false) String formMode,
			final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		log.info("editAndViewForm started");
		StateInformationMasterModel model = this.getModel();
		if (formMode.equals(MainetConstants.FlagE))
			model.setViewMode(MainetConstants.FlagE);
		else
			model.setViewMode(MainetConstants.FlagV);
		model.setStateInfoDto(stateInfoMastService.findById(stId));
		List<LookUp> lookUpList = CommonMasterUtility.getLevelData("SDB", 2,
				UserSession.getCurrent().getOrganisation());
		if (model.getStateInfoDto() != null && model.getStateInfoDto().getState() != null) {
			List<LookUp> lookUpList1 = lookUpList.stream()
					.filter(lookUp -> lookUp.getLookUpParentId() == model.getStateInfoDto().getState())
					.collect(Collectors.toList());
			model.setDistrictList(lookUpList1);
		}
		log.info("editAndViewForm started");
		return new ModelAndView(MainetConstants.Sfac.STATE_INFO_MAST_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	
	@ResponseBody
	@RequestMapping(params = "checkDataAlreadyExistByDist", method = RequestMethod.POST)
	public Boolean checkSpecialCateExist(HttpServletRequest request,@RequestParam("district") Long district) {
		boolean result = stateInfoMastService.checkSpecialCateExist(district);
		return result;
	}
}
