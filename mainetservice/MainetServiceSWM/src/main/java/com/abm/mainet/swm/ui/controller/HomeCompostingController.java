/**
 * 
 */
package com.abm.mainet.swm.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.swm.dto.HomeComposeDetailsDto;
import com.abm.mainet.swm.service.IHomeCompostingService;
import com.abm.mainet.swm.service.IWasteRateMasterService;
import com.abm.mainet.swm.ui.model.HomeCompostingFormModel;

/**
 * @author cherupelli.srikanth
 *
 */
@Controller
@RequestMapping("/HomeCompostingForm.html")
public class HomeCompostingController extends AbstractFormController<HomeCompostingFormModel> {

    @Autowired
    IHomeCompostingService homeCompostingService;

    @Autowired
    IWasteRateMasterService wasteRateMasterService;

    @Autowired
    IOrganisationService organisationService;

    private static Long prefixLevel;
    private static int orgId;
    private static List<LookUp> childsByOrgPrefixTopParentLevel;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest httpServletRequest) {
	loadDefaultData(httpServletRequest);
	getModel().bind(httpServletRequest);
	
	
	HomeCompostingFormModel model = getModel();
	Long userOrgId = UserSession.getCurrent().getOrganisation().getOrgid(); 
	
	model.setOrgId(userOrgId);
	model.setPrefixLevel(wasteRateMasterService.getPrefixLevel(MainetConstants.SolidWasteManagement.WASTETYPE,model.getOrgId()));
	
	if (model.getPrefixLevel() != null) {
	    orgId = (int) UserSession.getCurrent().getOrganisation().getOrgid();
	    
	} else {
	    
	    model.setPrefixLevel(wasteRateMasterService.getPrefixLevel(MainetConstants.SolidWasteManagement.WASTETYPE,
		    organisationService.getSuperUserOrganisation().getOrgid()));
	    orgId = (int) organisationService.getSuperUserOrganisation().getOrgid();
	}
	
	List<LookUp> lookUpList1 = CommonMasterUtility.getLevelData(MainetConstants.SolidWasteManagement.WASTETYPE, 1,
		UserSession.getCurrent().getOrganisation());
	for (LookUp lookUp : lookUpList1) {
	    if (lookUp.getLookUpCode().equals(MainetConstants.SolidWasteManagement.WETWASTE)) {
		childsByOrgPrefixTopParentLevel = ApplicationSession.getInstance().getChildsByOrgPrefixTopParentLevel(orgId,
			MainetConstants.SolidWasteManagement.WASTETYPE, lookUp.getLookUpId(), model.getPrefixLevel());
	    }
	}
	
	return index();

    }

    @RequestMapping(params = "addHomeComposting", method = RequestMethod.POST)
    public ModelAndView addHomeComposting(HttpServletRequest httpServletRequest) {
       this.getModel().setLookupList(childsByOrgPrefixTopParentLevel);
	return new ModelAndView("HomeCompostingForm", MainetConstants.FORM_NAME, getModel());

    }

    public void loadDefaultData(final HttpServletRequest httpServletRequest) {
	sessionCleanup(httpServletRequest);
	this.getModel().setLookupList(childsByOrgPrefixTopParentLevel);
	this.getModel().setMasterDtoList(homeCompostingService.searchHomeCompost(null, null, null, null, null, null,
		null, UserSession.getCurrent().getOrganisation().getOrgid()));
    }

    @ResponseBody
    @RequestMapping(params = "editCitizenMaster", method = RequestMethod.POST)
    public ModelAndView editCitizenForm(final HttpServletRequest httpServletRequest,
	    @RequestParam Long registrationid) {
	HomeComposeDetailsDto composeDetailDto = null;
	List<HomeComposeDetailsDto> composeDetailList = new ArrayList<>();
	this.getModel().setMasterDto(homeCompostingService.getCitizenByRegistrationId(registrationid));
	for (HomeComposeDetailsDto det : this.getModel().getMasterDto().getHomeComposeDetailList()) {
	    composeDetailDto = new HomeComposeDetailsDto();
	    composeDetailDto.setSwHomeComItem(det.getSwHomeComItem());
	    composeDetailDto.setSwHomeCompCollection(det.getSwHomeCompCollection());
	    composeDetailDto.setSwHomeCompPepared(det.getSwHomeCompPepared());
	    composeDetailDto.setSwHomeCompDate(det.getSwHomeCompDate());
	    composeDetailList.add(composeDetailDto);
	}
	this.getModel().setComposeDate(composeDetailList.get(0).getSwHomeCompDate());
	this.getModel().setLookupList(childsByOrgPrefixTopParentLevel);
	this.getModel().setHomeComposeDetailList(composeDetailList);
	return new ModelAndView("editHomeCompstingMaster", MainetConstants.FORM_NAME, getModel());

    }

    @ResponseBody
    @RequestMapping(params = "viewCitizenMaster", method = RequestMethod.POST)
    public ModelAndView viewCitizenForm(final HttpServletRequest httpServletRequest,
	    @RequestParam Long registrationid) {
	HomeComposeDetailsDto composeDetailDto = null;
	List<HomeComposeDetailsDto> composeDetailList = new ArrayList<>();
	this.getModel().setMasterDto(homeCompostingService.getCitizenByRegistrationId(registrationid));

	for (HomeComposeDetailsDto det : this.getModel().getMasterDto().getHomeComposeDetailList()) {
	    composeDetailDto = new HomeComposeDetailsDto();
	    composeDetailDto.setSwHomeComItem(det.getSwHomeComItem());
	    composeDetailDto.setSwHomeCompCollection(det.getSwHomeCompCollection());
	    composeDetailDto.setSwHomeCompPepared(det.getSwHomeCompPepared());
	    composeDetailDto.setSwHomeCompDate(det.getSwHomeCompDate());
	    composeDetailList.add(composeDetailDto);
	}

	this.getModel().setComposeDate(composeDetailList.get(0).getSwHomeCompDate());
	this.getModel().setLookupList(childsByOrgPrefixTopParentLevel);
	this.getModel().setHomeComposeDetailList(composeDetailList);
	return new ModelAndView("viewHomeCompstingMaster", MainetConstants.FORM_NAME, getModel());

    }

    @ResponseBody
    @RequestMapping(params = "searchCitizen", method = RequestMethod.POST)
    public ModelAndView searchcitizen(final HttpServletRequest httpServletRequest,
	    @RequestParam(required = false) Long zone, @RequestParam(required = false) Long ward,
	    @RequestParam(required = false) Long block, @RequestParam(required = false) Long route,
	    @RequestParam(required = false) Long house, @RequestParam(required = false) Long mobileNo,
	    @RequestParam(required = false) String name) {
	sessionCleanup(httpServletRequest);
	getModel().bind(httpServletRequest);
	this.getModel().getMasterDto().setSwCod1(zone);
	this.getModel().getMasterDto().setSwCod2(ward);
	this.getModel().getMasterDto().setSwCod3(block);
	this.getModel().getMasterDto().setSwCod4(route);
	this.getModel().getMasterDto().setSwCod5(house);
	this.getModel().getMasterDto().setSwMobile(mobileNo);
	this.getModel().getMasterDto().setSwName(name);

	this.getModel().setMasterDtoList(homeCompostingService.searchHomeCompost(zone, ward, block, route, house,
		mobileNo, name, UserSession.getCurrent().getOrganisation().getOrgid()));
	return new ModelAndView("HomeCompostingSearch", MainetConstants.FORM_NAME, getModel());

    }
}
