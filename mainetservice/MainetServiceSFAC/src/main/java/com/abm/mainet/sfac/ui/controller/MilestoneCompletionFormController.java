package com.abm.mainet.sfac.ui.controller;

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
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.MilestoneCompletionMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.MilestoneCompletionService;
import com.abm.mainet.sfac.ui.model.MilestoneCompletionFormModel;

@Controller
@RequestMapping(MainetConstants.Sfac.MILESTONE_COMP_FORM_HTML)
public class MilestoneCompletionFormController extends AbstractFormController<MilestoneCompletionFormModel>{

	@Autowired CBBOMasterService cbboMasterService;

	@Autowired MilestoneCompletionService milestoneCompletionService;

	@Autowired
	private IFileUploadService fileUpload;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		populateModel();

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}

	private void populateModel() {

		CBBOMasterDto cbboMasterDto =	cbboMasterService.findById(UserSession.getCurrent().getEmployee().getMasId());
		MilestoneCompletionMasterDto milestoneCompletionMasterDto = new MilestoneCompletionMasterDto();
		List<CBBOMasterDto> masterDtoList = cbboMasterService.findIAList(cbboMasterDto.getCbboUniqueId());
		milestoneCompletionMasterDto.setCbboId(cbboMasterDto.getCbboId());
		this.getModel().setDto(milestoneCompletionMasterDto);
		this.getModel().setCbboMasterDtos(masterDtoList);
		this.getModel().setCbboMasterDto(cbboMasterDto);

	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().setViewMode(MainetConstants.FlagA);

		List<MilestoneMasterDto> milestoneMasterDtos =	milestoneCompletionService.getMilestoneDetails(UserSession.getCurrent().getEmployee().getMasId());
		this.getModel().setMilestoneMasterDtos(milestoneMasterDtos);
		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(Long iaId, Long cbboId, String status ,final HttpServletRequest httpServletRequest) {


		List<MilestoneCompletionMasterDto> milestoneCompletionMasterDtos = milestoneCompletionService.getMilestoneDetails(iaId,cbboId, status);
		this.getModel().setMilestoneCompletionMasterDtos(milestoneCompletionMasterDtos);

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long mscId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);
		
		MilestoneCompletionMasterDto dto = milestoneCompletionService.getDetailById(mscId);
		List<MilestoneMasterDto> milestoneMasterDtos =	milestoneCompletionService.getMilestoneDetailsByID(dto.getMsId());
		this.getModel().setMilestoneMasterDtos(milestoneMasterDtos);
		dto.setMscId(mscId);
		this.getModel().setDto(dto);
		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_FORM, MainetConstants.FORM_NAME, getModel());
	}



	@RequestMapping(method = { RequestMethod.POST }, params = "getMilestoneDetails")
	public ModelAndView getMilestoneDetails(Long msId,final HttpServletRequest httpServletRequest) {


		MilestoneCompletionMasterDto milestoneCompletionMasterDto = milestoneCompletionService.findMilestoneDetails(msId, UserSession.getCurrent().getEmployee().getMasId());
		this.getModel().setDto(milestoneCompletionMasterDto);
		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_FORM, MainetConstants.FORM_NAME, getModel());
	}

}
