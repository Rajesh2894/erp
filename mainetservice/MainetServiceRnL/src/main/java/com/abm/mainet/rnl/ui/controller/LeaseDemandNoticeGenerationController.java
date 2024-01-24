/**
 * 
 */
package com.abm.mainet.rnl.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rnl.dto.LeaseDemandNoticeGenerationDTO;
import com.abm.mainet.rnl.service.LeaseDemandNoticeGenerationService;
import com.abm.mainet.rnl.ui.model.LeaseDemandNoticeGenerationModel;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/LeaseDemandNoticeGeneration.html")
public class LeaseDemandNoticeGenerationController extends AbstractFormController<LeaseDemandNoticeGenerationModel>{
	
	@Autowired
    private ILocationMasService iLocationMasService;
	@Resource
    private LeaseDemandNoticeGenerationService leaseDemandNoticeGenerationService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index(final Model uiModel, HttpServletRequest request) {
		sessionCleanup(request);
		
		/*
		 * uiModel.addAttribute("locationList",
		 * iLocationMasService.getLocationNameByOrgId(UserSession.getCurrent().
		 * getOrganisation().getOrgid()));
		 */
		
		List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
		this.getModel().setLocationList(locationList);
			 
		return index();
	}
	
	@ResponseBody
    @RequestMapping(params = "searchDetails", method = RequestMethod.POST)
    public ModelAndView searchDetails(final HttpServletRequest request,
            @RequestParam(required = false) Long locationId, @RequestParam(required = false) Long notTyp,
            @RequestParam(required = false) String noticeTypeDesc) { 
		getModel().bind(request);
		ModelAndView mv = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		LeaseDemandNoticeGenerationModel model = this.getModel();
		model.getDto().setNotTyp(notTyp);
		model.getDto().setLocationId(locationId);
		model.getDto().setNoticeTypeDesc(noticeTypeDesc);
		LookUp noticeTypeByLookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(notTyp, orgid, "RNG");

		if (noticeTypeByLookup.getLookUpCode().equals("RRN2")) {
			// RRN2 will only be generated after RRN1
			// to check RRN1 is generated or not
			Long rrn1ByLookup = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RRN1", "RNG", orgid);
			
			List<LeaseDemandNoticeGenerationDTO> demandDtoList = leaseDemandNoticeGenerationService
					.findSecondReminderNotice(rrn1ByLookup, orgid);
			
			if(demandDtoList.isEmpty()) {
				mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
				model.addValidationError(ApplicationSession.getInstance().getMessage("rnl.renewal.billPayment.NoRecord"));
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
			}else {
				Long rrn2ByLookup = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RRN2", "RNG", orgid);
				List<LeaseDemandNoticeGenerationDTO> demandDtoListForRrn2 = leaseDemandNoticeGenerationService
						.findSecondReminderNotice(rrn2ByLookup, orgid);
				if(demandDtoListForRrn2.isEmpty()) {
					model.setDemandDtoList(demandDtoList);
					model.getDto().setLocationId(locationId);
					model.getDto().setNoticeTypeDesc("M");
					mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
				}else {
					mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
					model.addValidationError(ApplicationSession.getInstance().getMessage("rnl.renewal.billPayment.NoRecord"));
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
				}
				
			}			
		}else {
			List<LeaseDemandNoticeGenerationDTO> dtoListByLocation = leaseDemandNoticeGenerationService
					.findByLocationId(orgid, locationId);
			if(dtoListByLocation.isEmpty()) {
				mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
				model.addValidationError(ApplicationSession.getInstance().getMessage("rnl.renewal.billPayment.NoRecord"));
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
			}else {
				Long rrn1ByLookup = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("RRN1", "RNG", orgid);
				
				List<LeaseDemandNoticeGenerationDTO> demandDtoList = leaseDemandNoticeGenerationService
						.findSecondReminderNotice(rrn1ByLookup, orgid);
				if(demandDtoList.isEmpty()) {
					model.setDemandDtoList(dtoListByLocation);
					model.getDto().setLocationId(locationId);
					model.getDto().setNoticeTypeDesc("M");
					mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
				}else {
					mv = new ModelAndView("LeaseDemandNoticeGenerationSearch", MainetConstants.FORM_NAME, model);
					model.addValidationError(ApplicationSession.getInstance().getMessage("rnl.renewal.billPayment.NoRecord"));
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
				}
				
			}		
		}
		return mv;
	}  
	
	@RequestMapping(params = "back", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    
        getModel().bind(httpServletRequest);
        LeaseDemandNoticeGenerationModel model = this.getModel();
        return new ModelAndView("LeaseDemandNoticeGenerationValidn", MainetConstants.FORM_NAME, model);
    }
}
