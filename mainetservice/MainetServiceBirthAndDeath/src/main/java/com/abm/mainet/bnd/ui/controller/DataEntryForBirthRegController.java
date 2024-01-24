package com.abm.mainet.bnd.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.BndAcknowledgementDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.ui.model.DataEntryForBirthRegModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping("/dataEntryForBirthReg.html")
public class DataEntryForBirthRegController extends AbstractFormController<DataEntryForBirthRegModel> {
	private static Logger LOGGER = Logger.getLogger(DataEntryForBirthRegController.class);
	

	@Autowired
	private IBirthRegService iBirthRegSevice;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;


	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		DataEntryForBirthRegModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		try {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
				List<HospitalMasterDTO> hospitalList = appModel
						.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
				model.addAttribute("hospitalList", hospitalList);
			} else {
				appModel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		 }
		
		return new ModelAndView("DataEntryBirthRegSummary", MainetConstants.FORM_NAME, getModel());
	}

	/*@RequestMapping(params = "searchBirthDetail", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCertificate(@RequestParam("brCertNo") String brCertNo,
			@RequestParam("applnId") String applnId, @RequestParam("year") String year,
			@RequestParam("brRegNo") String brRegNo,@RequestParam("brDob") Date brDob,@RequestParam("brChildName") String brChildName ,final Model model) {
      
		
		DataEntryForBirthRegModel appModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegisteredAppliDetail(brCertNo, brRegNo, year,brDob,brChildName,
				applnId, orgId);
               appModel.setBirthRegistrationDTOList(registrationDetail);
		model.addAttribute("birthList", registrationDetail);
		return getBirth(registrationDetail);
	}
	*/
	
	private List<BirthRegistrationDTO> getBirth(List<BirthRegistrationDTO> births) {
		births.forEach(registrationDetail -> {
			if(registrationDetail.getParentDetailDTO()!=null && registrationDetail.getParentDetailDTO().getPdRegUnitId()!=null) {
			registrationDetail.setCpdRegUnit(CommonMasterUtility.getCPDDescription(registrationDetail.getParentDetailDTO().getPdRegUnitId(),  MainetConstants.BLANK));
			}
			registrationDetail.setBrSex(CommonMasterUtility.getCPDDescription(Long.parseLong(registrationDetail.getBrSex()),  MainetConstants.BLANK));
		});
		return births;
	}

		@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView editDeathreg(Model model,@RequestParam("mode") String mode,
				@RequestParam(MainetConstants.Common_Constant.ID) Long brID, final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		 this.getModel().setSaveMode(mode);
		this.getModel().setBirthRegDto(iBirthRegSevice.getBirthByID(brID));
		 
		 Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		 getModel().bind(httpServletRequest);
		 try {
				if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
					List<HospitalMasterDTO> hospitalList = this.getModel().
							setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
					model.addAttribute("hospitalList", hospitalList);
				} else {
					this.getModel().setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
				}
			} catch (Exception e) {
				throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
			 }
		  ModelAndView mv = new ModelAndView("DataEntryBirthRegValidn", MainetConstants.FORM_NAME, this.getModel());
			if(this.getModel().getSaveMode().equals("E"))
			{
				if(this.getModel().getBirthRegDto().getBirthWfStatus().equals("OPEN")){
					this.getModel().setSaveMode("V");
					this.getModel().addValidationError(getApplicationSession().getMessage(
							  "BirthRegistrationDTO.call.norecord"));
					final BindingResult bindingResult = this.getModel().getBindingResult();
						return	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								this.getModel().getBindingResult());
				}
				return mv;
			}
			return mv;
		 
	 }
		
		@RequestMapping(params = "searchBirthRegDetail", method = RequestMethod.POST, produces = "Application/JSON")
		public @ResponseBody List<BirthRegistrationDTO> searchBirthDataForCorr(HttpServletRequest request,Model model) {
	      
			getModel().bind(request);
			DataEntryForBirthRegModel appModel = this.getModel();
			BirthRegistrationDTO birthRegDto = appModel.getBirthRegDto();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			birthRegDto.setOrgId(orgId);
			List<BirthRegistrationDTO> registrationDetail = iBirthRegSevice.getBirthRegiDetailForCorr(birthRegDto);
			   appModel.setBirthRegistrationDTOList(registrationDetail);
			model.addAttribute("birthList", registrationDetail);
			return getBirth(registrationDetail);
		}
		
		
		@RequestMapping(params = "printBndAcknowledgement", method = {RequestMethod.POST })
	    public ModelAndView printBndRegAcknowledgement(HttpServletRequest request) {
	        bindModel(request);
	        final DataEntryForBirthRegModel birthModel = this.getModel();
	        ModelAndView mv = null;
	        if(birthModel.getBirthRegDto().getApmApplicationId()!=null) {
	        ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BRC, UserSession.getCurrent().getOrganisation().getOrgid());
	        BndAcknowledgementDto ackDto = new BndAcknowledgementDto();
	        Long title = birthModel.getBirthRegDto().getRequestDTO().getTitleId();
	        LookUp lokkup = null;
			if (birthModel.getBirthRegDto().getRequestDTO().getTitleId() != null) {
				lokkup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(birthModel.getBirthRegDto().getRequestDTO().getTitleId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), "TTL");
			}
			if(lokkup!=null) {
				ackDto.setApplicantTitle(lokkup.getLookUpDesc());
			}
			/*
			 * if(title == 1) { ackDto.setApplicantTitle(MainetConstants.MR); } else
			 * if(title == 2) { ackDto.setApplicantTitle(MainetConstants.MRS); } else
			 * if(title == 3) { ackDto.setApplicantTitle(MainetConstants.MS); }
			 */
	        ackDto.setApplicationId(birthModel.getBirthRegDto().getApmApplicationId());
	        ackDto.setApplicantName(String.join(" ", Arrays.asList(birthModel.getBirthRegDto().getRequestDTO().getfName(),
	        		birthModel.getBirthRegDto().getRequestDTO().getmName(), birthModel.getBirthRegDto().getRequestDTO().getlName())));
	        if(UserSession.getCurrent().getLanguageId()==MainetConstants.DEFAULT_LANGUAGE_ID) {
	        	ackDto.setServiceShortCode(serviceMas.getSmServiceName());
	        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpDeptdesc());
	        }else {
	        	ackDto.setServiceShortCode(serviceMas.getSmServiceNameMar());
	        	ackDto.setDepartmentName(serviceMas.getTbDepartment().getDpNameMar());
	        }
	        ackDto.setAppDate(new Date());
	        ackDto.setAppTime(new SimpleDateFormat("HH:mm").format(new Date()));
	        ackDto.setDueDate(Utility.getAddedDateBy2(ackDto.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
	        ackDto.setHelpLine(getApplicationSession().getMessage("bnd.acknowledgement.helplineNo"));
	        birthModel.setAckDto(ackDto);
	        // runtime print acknowledge or certificate
	        String viewName = "bndRegAcknow";
	        mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
	        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	        }
	        return mv;

	    }
		
		

}
