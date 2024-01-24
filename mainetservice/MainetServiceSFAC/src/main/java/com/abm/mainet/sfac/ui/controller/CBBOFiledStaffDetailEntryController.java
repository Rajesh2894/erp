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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.BlockAllocationDetailDto;
import com.abm.mainet.sfac.dto.CBBOFiledStaffDetailsDto;
import com.abm.mainet.sfac.service.CBBOFiledStaffDetailEntryService;
import com.abm.mainet.sfac.ui.model.CBBOFieldStaffDetailModel;

@Controller
@RequestMapping(MainetConstants.Sfac.CBBO_FIELD_STAFF_FORM_HTML)
public class CBBOFiledStaffDetailEntryController extends AbstractFormController<CBBOFieldStaffDetailModel>{

	@Autowired CBBOFiledStaffDetailEntryService cbboFiledStaffDetailEntryService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);


		List<LookUp> blockList = cbboFiledStaffDetailEntryService.getBlockDetails(UserSession.getCurrent().getEmployee().getMasId());

		this.getModel().setBlockList(blockList);

		return new ModelAndView(MainetConstants.Sfac.CBBO_FIELD_STAFF_SUMMARY,MainetConstants.FORM_NAME,getModel());
	}

	@RequestMapping(params = "formForCreate", method = { RequestMethod.POST })
	public ModelAndView formForCreate(HttpServletRequest request, Model model) {
		sessionCleanup(request);
		List<LookUp> blockList = cbboFiledStaffDetailEntryService.getBlockDetails(UserSession.getCurrent().getEmployee().getMasId());
		populateModel();
		this.getModel().setBlockList(blockList);
		this.getModel().setViewMode(MainetConstants.FlagA);
		this.getModel().getDto().setCbboId(UserSession.getCurrent().getEmployee().getMasId());
		return new ModelAndView(MainetConstants.Sfac.CBBO_FIELD_STAFF_FORM, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(method = { RequestMethod.POST }, params = "searchForm")
	public ModelAndView searchForm(String name, Long block ,final HttpServletRequest httpServletRequest) {
		
		populateModel();
		
		List<CBBOFiledStaffDetailsDto> cbboFiledStaffDetailsDtos = cbboFiledStaffDetailEntryService.getCbboFieldStaffDetails(name, block);
		this.getModel().setCbboFiledStaffDetailsDtos(cbboFiledStaffDetailsDtos);
		 
		return new ModelAndView(MainetConstants.Sfac.CBBO_FIELD_STAFF_SUMMARY_VALIDN, MainetConstants.FORM_NAME, getModel());
	}


	@RequestMapping(method = { RequestMethod.POST }, params = MainetConstants.CommonConstants.EDIT)
	public ModelAndView editOrView(Long fsdId,
			@RequestParam(value = MainetConstants.REQUIRED_PG_PARAM.MODE, required = true) String mode,
			final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(mode);

		CBBOFiledStaffDetailsDto dto = cbboFiledStaffDetailEntryService.getDetailById(fsdId);
		dto.setFsdId(fsdId);
		this.getModel().setDto(dto);
		List<LookUp> blockList = cbboFiledStaffDetailEntryService.getBlockDetails(UserSession.getCurrent().getEmployee().getMasId());

		this.getModel().setBlockList(blockList);
		populateModel();
		
		return new ModelAndView(MainetConstants.Sfac.CBBO_FIELD_STAFF_FORM, MainetConstants.FORM_NAME, getModel());
	}
	
	private void populateModel() {
		List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		List<LookUp> districtList = CommonMasterUtility.getLevelData("SDB", 2,
				UserSession.getCurrent().getOrganisation());
		
		this.getModel().setStateList(stateList);
		this.getModel().setDistrictList(districtList);
	}
	
	@RequestMapping(method = { RequestMethod.POST }, params = "getSD")
	@ResponseBody
	public BlockAllocationDetailDto getSD( Long block ,final HttpServletRequest httpServletRequest) {
		
		populateModel();
		
		BlockAllocationDetailDto blockAllocationDetailDto  = cbboFiledStaffDetailEntryService.getSD(block);
		this.getModel().getDto().setSdb1(blockAllocationDetailDto.getSdb1());
		this.getModel().getDto().setSdb2(blockAllocationDetailDto.getSdb2());
		 
		return blockAllocationDetailDto;
	}

}
