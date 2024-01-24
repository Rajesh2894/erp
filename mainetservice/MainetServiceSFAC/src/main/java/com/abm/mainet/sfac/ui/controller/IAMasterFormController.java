/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.IAMasterDto;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.IAMasterFormModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.IA_MASTER_FORM_HTML)
public class IAMasterFormController extends AbstractFormController<IAMasterFormModel>{
	
	private static final Logger logger = Logger.getLogger(IAMasterFormController.class);
	
	
	@Autowired
	private IAMasterService iaMasterService;
	
	@Autowired
    private DesignationService designationService;
	
	@Autowired
	private IOrganisationService orgService;

	
	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		populateModel();
		return new ModelAndView(MainetConstants.Sfac.IA_MASTER_SUMMARY_FORM,MainetConstants.FORM_NAME,getModel());
	}

	
	private void populateModel() {
		try {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
			List<IAMasterDto> masterDtoList = iaMasterService.findAllIA();
			Map<Object, List<IAMasterDto>> grouped = masterDtoList.stream()
					.collect(Collectors.groupingBy(o -> o.getIAName()));

			List<IAMasterDto> beanList = new LinkedList<>();
			for (Entry<Object, List<IAMasterDto>> entry : grouped.entrySet()) {
				if (CollectionUtils.isNotEmpty(entry.getValue())) {
					List<IAMasterDto> listBean = entry.getValue();
					beanList.add(listBean.get(0));
				}
			}
			this.getModel().setIaMasterDtoList(masterDtoList);
			this.getModel()
					.setFaYears(iaMasterService.getfinancialYearList(org));
			final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1, UserSession.getCurrent().getOrganisation());
			this.getModel().setStateList(stateList);
		} catch (Exception e) {
			logger.error("Error Ocurred while fething details of financial year or Ia master details by orgid" + e);
		}

	}
	
	@ResponseBody
	@RequestMapping(params = "formForCreate", method = RequestMethod.POST)
	public ModelAndView formForCreate(final HttpServletRequest httpServletRequest, Model model) {
		sessionCleanup(httpServletRequest);
		populateModel();
	    this.getModel().setViewMode(MainetConstants.FlagA);
	   // this.getModel().setDesignlist(designationService.findAll());
		return new ModelAndView(MainetConstants.Sfac.IA_MASTER_FORM, MainetConstants.FORM_NAME,
				this.getModel());

	}
	
	@ResponseBody
	@RequestMapping(params=MainetConstants.Sfac.GET_IA_DETAILS_BY_IDS, method=RequestMethod.POST)
	public  List<IAMasterDto> getIaDetailsByIds(HttpServletRequest request, Long IAName,Long allocationYear){
		List<IAMasterDto> iaMasterDtoList = new ArrayList<IAMasterDto>();
		try {
			Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.IA);
		iaMasterDtoList = iaMasterService.getIaDetailsByIds(IAName,allocationYear,org.getOrgid());
		this.getModel().setIaDetailDtoList(iaMasterDtoList);
		}catch (Exception e) {
			logger.error("Error occured while fetching Ia details by IaName and allocation year" + e);
		}
		return iaMasterDtoList;
		
	}
	
	@RequestMapping(params = MainetConstants.Sfac.EDIT_AND_VIEW_FORM, method = RequestMethod.POST)
	public ModelAndView editAndViewForm(@RequestParam("IAId") final Long IAId,
			@RequestParam(name = MainetConstants.FORM_MODE, required = false) String formMode,
			final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		logger.info("editAndViewForm started");
		IAMasterFormModel model= this.getModel();
		if (formMode.equals(MainetConstants.FlagE))
			model.setViewMode(MainetConstants.FlagE);
		else
			model.setViewMode(MainetConstants.FlagV);
		model.setIaMasterDto(iaMasterService.findByIaId(IAId));
	//	this.getModel().setDesignlist(designationService.findAll());
		logger.info("editAndViewForm started");
		return new ModelAndView(MainetConstants.Sfac.IA_MASTER_FORM, MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@ResponseBody
	@RequestMapping(params = "checkIaNameExist", method = RequestMethod.POST)
	public Boolean checkIANameExist(HttpServletRequest request, @RequestParam("IAName") String IAName) {
		boolean result = iaMasterService.checkIANameExist(IAName);
		return result;
	}

	@ResponseBody
	@RequestMapping(params = "checkIaShortNmExist", method = RequestMethod.POST)
	public Boolean checkIaShortNmExist(HttpServletRequest request, @RequestParam("iaShortName") String iaShortName) {
		boolean result = iaMasterService.checkIaShortNmExist(iaShortName);
		return result;
	}
}
