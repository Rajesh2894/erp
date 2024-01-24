
package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
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

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.ICemeteryMasterService;
import com.abm.mainet.bnd.ui.model.CemeteryMasterModel;
import com.abm.mainet.bnd.ui.model.HospitalMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping("/CemeteryMaster.html")
public class CemeteryMasterController extends AbstractFormController<CemeteryMasterModel> {

	@Autowired
	private ICemeteryMasterService cemeteryService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		this.getModel().setCommonHelpDocs("CemeteryMaster.html");

		List<CemeteryMasterDTO> cemeterys = cemeteryService
				.getAllCemetery(UserSession.getCurrent().getOrganisation().getOrgid());
		List<CemeteryMasterDTO> cemetery = getCemeterys(cemeterys);
		model.addAttribute("cemeterys", cemetery);
		return new ModelAndView("CemeteryMaster", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "cemeteryMaster", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addpopulationForm1(final HttpServletRequest request) {
		this.sessionCleanup(request);
		CemeteryMasterModel model = this.getModel();
		model.setSaveMode(MainetConstants.CommonConstants.ADD);
		return new ModelAndView("CemeteryMasterAdd", MainetConstants.FORM_NAME, model);

	}

	@RequestMapping(params = "getAll", method = RequestMethod.GET)
	public ModelAndView listCemetery(ModelAndView model) throws IOException {
		List<CemeteryMasterDTO> cemeteryMasterDTOs = cemeteryService.getAllCemetery(null);
		model.addObject("cemeteryMasterDTOs", cemeteryMasterDTOs);
		model.setViewName("cemeteryMasterDTOs");

		return model;

	}

	@RequestMapping(params = "viewBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView viewcemeteryMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long ceId, final HttpServletRequest httpServletRequest) {
		this.getModel().setCemeteryMasterDTO(cemeteryService.getCemeteryById(ceId));
		this.getModel().setSaveMode(mode);
		return new ModelAndView("CemeteryMasterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editviewHospitalMaster(@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long ceId, final HttpServletRequest httpServletRequest) {
		this.getModel().setCemeteryMasterDTO(cemeteryService.getCemeteryById(ceId));
		this.getModel().setSaveMode(mode);
		return new ModelAndView("CemeteryMasterAdd", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "searchCemetery", method = RequestMethod.POST)
	public List<CemeteryMasterDTO> findCemetery(@RequestParam("ceName") final String ceName,
			@RequestParam("cpdTypeId") final long cpdTypeId, final HttpServletRequest request, final Model model) {
		
		CemeteryMasterModel modelCmetery = this.getModel();
		
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<CemeteryMasterDTO> cemeteryMasterDtoList = cemeteryService.findByCemeteryNameAndCemeteryType(ceName,
				cpdTypeId, orgId);
		model.addAttribute("cemeteryMasterDtoList", cemeteryMasterDtoList);
		List<CemeteryMasterDTO> cemeteryList = modelCmetery.setCemeteryMasterDTOList(cemeteryService.getAllCemetery(orgId));
		model.addAttribute("cemeteryList", cemeteryList);
		return getCemeterys(cemeteryMasterDtoList);
		
	
		
	}

	private List<CemeteryMasterDTO> getCemeterys(List<CemeteryMasterDTO> cemeterys) {
		cemeterys.forEach(cemetery -> {
			cemetery.setCpdDesc(CommonMasterUtility.getCPDDescription(cemetery.getCpdTypeId(), MainetConstants.BLANK));
		});
		return cemeterys;

	}

}
