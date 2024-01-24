/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

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
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.service.MilestoneEntryService;
import com.abm.mainet.sfac.ui.model.MilestoneEntryFormModel;


@Controller
@RequestMapping(MainetConstants.Sfac.MILESTONE_ENTRY_FORM_HTML)
public class MilestoneEntryFormController extends AbstractFormController<MilestoneEntryFormModel>{


	@Autowired MilestoneEntryService milestoneService;

	@Autowired IAMasterService iaMasterService;

	@Autowired CBBOMasterService cbboMasterService;
	
	@Autowired FPOMasterService fpoMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateModel();

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_ENTRY_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long msId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		this.getModel().setCbboMasterDtos(cbboMasterService.getCBBOList(UserSession.getCurrent().getEmployee().getMasId()));
		populateModel();
		MilestoneMasterDto dto = milestoneService.getDetailById(msId);

		this.getModel().setMilestoneMasterDto(dto);
		return new ModelAndView(MainetConstants.Sfac.MILESTONE_ENTRY_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		populateModel();
		this.getModel().setCbboMasterDtos(cbboMasterService.getCBBOList(UserSession.getCurrent().getEmployee().getMasId()));

		this.getModel().setViewMode(MainetConstants.FlagA);

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_ENTRY_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(Long iaId, String milestoneId ,final HttpServletRequest httpServletRequest) {

		
		List<MilestoneMasterDto> milestoneMasterDtos = milestoneService.getMilestoneDetails(iaId,milestoneId);
		this.getModel().setMilestoneMasterDtos(milestoneMasterDtos);

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_ENTRY_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}

	private void populateModel() {

		IAMasterDto iaMasterDto = iaMasterService.findByIaId(UserSession.getCurrent().getEmployee().getMasId());

		MilestoneMasterDto milestoneMasterDto = new MilestoneMasterDto();
		milestoneMasterDto.setIaId(iaMasterDto.getIAId());
		milestoneMasterDto.setIaName(iaMasterDto.getIAName());
		this.getModel().setMilestoneMasterDto(milestoneMasterDto);

	}
	
	@RequestMapping(params = "getFPOCount", method = RequestMethod.POST)
	public @ResponseBody Long getFPOCount(@RequestParam("cbboId") final Long cbboId) {
		Long fpoCount  = fpoMasterService.getFPOCount(cbboId);
         
		return fpoCount;
	}
}
