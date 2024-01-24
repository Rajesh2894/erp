
package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
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

import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.ui.model.HospitalMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/HospitalMaster.html")
public class HospitalMasterController extends AbstractFormController<HospitalMasterModel> {

	@Autowired
	private IHospitalMasterService tbHospitalService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("HospitalMaster.html");
		List<HospitalMasterDTO> hospitals = new ArrayList<HospitalMasterDTO>();
		if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
		hospitals = tbHospitalService
				.getAllHospitlsWithAllStatus(UserSession.getCurrent().getOrganisation().getOrgid());
       }else {
    	   hospitals=tbHospitalService.getAllHospitlsWithAllStatus(UserSession.getCurrent().getOrganisation().getOrgid());
		}
		List<HospitalMasterDTO> hospital = getHospitals(hospitals);
		model.addAttribute("hospitals", hospital);	
		
		return new ModelAndView("HospitalMaster", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "hospitalMaster", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addpopulationForm1(final HttpServletRequest request) {
		this.sessionCleanup(request);
		HospitalMasterModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		return new ModelAndView("HospitalMasterAdd", MainetConstants.FORM_NAME, model);
		

	}

	@RequestMapping(params = "getAll", method = RequestMethod.GET)
	public ModelAndView listHospitals(ModelAndView model) throws IOException {
		model.setViewName("hospitalMasterDTOs");
		return model;
	}

	@RequestMapping(params = "viewBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewHospitalMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long hiId, final HttpServletRequest httpServletRequest) {
		this.getModel().setHospitalMasterDTO(tbHospitalService.getHospitalById(hiId));
		this.getModel().setSaveMode(mode);
		return new ModelAndView("HospitalMasterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewHospitalMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long hiId, final HttpServletRequest httpServletRequest) {
		this.getModel().setHospitalMasterDTO(tbHospitalService.getHospitalById(hiId));
		this.getModel().setSaveMode(mode);
		return new ModelAndView("HospitalMasterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchHospital", method = RequestMethod.POST)
	public List<HospitalMasterDTO> findHospital(@RequestParam("hiName") final String hiName,
			@RequestParam("cpdTypeId") final long cpdTypeId, final HttpServletRequest request, final Model model) {
		
		HospitalMasterModel modelHosp = this.getModel();
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<HospitalMasterDTO> hospitalMasterDtoList = tbHospitalService.findByHospitalNameAndHospitalType(hiName,cpdTypeId, orgId);
		model.addAttribute("hospitalMasterDtoList", hospitalMasterDtoList);
		List<HospitalMasterDTO> hospitalList = modelHosp.setHospitalMasterDTOList(tbHospitalService.getAllHospitalList(orgId));
		model.addAttribute("hospitalList", hospitalList);
		
		return getHospitals(hospitalMasterDtoList); 	
		/*
		 * if (hospitalMasterDtoList != null) {
		 * modelHosp.setHospitalMaster(hospitalMasterDtoList); }
		 */
		//return new ModelAndView("HospitalMasterValidn", MainetConstants.FORM_NAME, modelHosp);
	}

	private List<HospitalMasterDTO> getHospitals(List<HospitalMasterDTO> hospitals) {
		hospitals.forEach(hospital -> {
			hospital.setCpdDesc(CommonMasterUtility.getCPDDescription(hospital.getCpdTypeId(), MainetConstants.BLANK));
		});
		return hospitals;
	}
	
	
	
}
