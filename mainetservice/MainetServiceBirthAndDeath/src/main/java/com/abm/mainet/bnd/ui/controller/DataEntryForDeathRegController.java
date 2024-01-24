package com.abm.mainet.bnd.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.ICemeteryMasterService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.ui.model.DataEntryForDeathRegModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
/**
 * @author Bhagyashri.Dongardive
 * @since 31 Aug 2021
 */
@Controller
@RequestMapping("/dataEntryForDeathReg.html")
public class DataEntryForDeathRegController extends AbstractFormController<DataEntryForDeathRegModel>{

	private static final Logger LOG = Logger.getLogger(DataEntryForDeathRegController.class);
	
	@Autowired
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ICemeteryMasterService iCemeteryMasterService;

	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		return new ModelAndView("DataEntryDeathRegSummary", MainetConstants.FORM_NAME, this.getModel());
	}
	
	@RequestMapping(params = "searchDeathRegData", method = RequestMethod.POST,  produces = "Application/JSON")
	public @ResponseBody List<TbDeathregDTO> searchDeathDataForCorr(HttpServletRequest request,Model model) {
		
		DataEntryForDeathRegModel deathRegModel=this.getModel();
		getModel().bind(request);
		TbDeathregDTO tbDeathregDTO = deathRegModel.getTbDeathregDTO();
		tbDeathregDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid()); 
		List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathDataForCorr(deathRegModel.getTbDeathregDTO());
		
		deathRegModel.setTbDeathregDTOList(tbDeathRegDtoList);
		return getDeath(tbDeathRegDtoList);

	}
	
	private List<TbDeathregDTO> getDeath(List<TbDeathregDTO> deaths) {
		deaths.forEach(tbDeathRegDTO -> {
			if(tbDeathRegDTO.getCpdRegUnit()!=null) {
			tbDeathRegDTO.setCpdDesc(CommonMasterUtility.getCPDDescription(tbDeathRegDTO.getCpdRegUnit(), MainetConstants.BLANK));
			}
		});
		return deaths;
	}
	
	@ResponseBody
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathreg(Model model,@RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long drID, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		this.getModel().setTbDeathregDTO(ideathregCorrectionService.getDeathById(drID));
		
		try {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
				this.getModel().setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(UserSession.getCurrent().getOrganisation().getOrgid()));
				this.getModel().setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemetery(UserSession.getCurrent().getOrganisation().getOrgid()));
			} else {
				this.getModel().setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(UserSession.getCurrent().getOrganisation().getOrgid()));
				this.getModel().setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemeteryList(UserSession.getCurrent().getOrganisation().getOrgid()));
			}
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital or cemetery List");
		 }
		this.getModel().setSaveMode(mode);
		getModel().bind(httpServletRequest);
		ModelAndView mv = new ModelAndView("DataEntryDeathRegValidn", MainetConstants.FORM_NAME, this.getModel());
		if (this.getModel().getSaveMode().equals("E")) {
			if (this.getModel().getTbDeathregDTO().getDeathWFStatus().equals("OPEN")) {
				this.getModel().setSaveMode("V");
				this.getModel()
						.addValidationError(getApplicationSession().getMessage("BirthRegistrationDTO.call.norecord"));
				return mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
						this.getModel().getBindingResult());
			}
			return mv;
		}
		return mv;
		
	}
}
