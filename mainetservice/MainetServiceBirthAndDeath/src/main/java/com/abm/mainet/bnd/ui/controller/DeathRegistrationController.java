package com.abm.mainet.bnd.ui.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
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
import com.abm.mainet.bnd.datamodel.BndRateMaster;
import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathRegdraftDto;
import com.abm.mainet.bnd.service.ICemeteryMasterService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.DeathRegistrationModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

@Controller
@RequestMapping(value = "/DeathRegistration.html")
public class DeathRegistrationController extends AbstractFormController<DeathRegistrationModel> {

	private static final Logger LOGGER = Logger.getLogger(DeathRegistrationController.class);

	@Autowired
	private IHospitalMasterService iHospitalMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ICemeteryMasterService iCemeteryMasterService;

	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private IDeathRegistrationService iDeathRegistrationService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(Model model, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);

		DeathRegistrationModel deathRegistrationmodel = this.getModel();
		deathRegistrationmodel.setCommonHelpDocs("DeathRegistration.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		try {
			//model.addAttribute("hospitalList",iHospitalMasterService.getAllHospitls(orgId));
			deathRegistrationmodel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
			
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		}

		try {
			//model.addAttribute("cemeteryList",iCemeteryMasterService.getAllCemetery(orgId));
			deathRegistrationmodel.setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemetery(orgId));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
			throw new FrameworkException("Some Problem Occured While Fetching Cemetery List");
		}
        List<TbDeathRegdraftDto> tbDeathRegdraftList = iDeathRegistrationService
				.getAllDeathRegdraft(UserSession.getCurrent().getOrganisation().getOrgid());
		List<TbDeathRegdraftDto> tbDeathRegDtoLists = getDeath(tbDeathRegdraftList);
		deathRegistrationmodel.setTbDeathRegdraftDtoList(tbDeathRegDtoLists);
		model.addAttribute("data", tbDeathRegDtoLists);

		return new ModelAndView("DeathRegistration", MainetConstants.FORM_NAME, getModel());

	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		
		 String ceName=httpServletRequest.getParameter("tbDeathregDTO.ceName");
		 String ceNameMar=httpServletRequest.getParameter("tbDeathregDTO.ceNameMar");
		 String ceAddr=httpServletRequest.getParameter("tbDeathregDTO.ceAddr");
		 String ceAddrMar=httpServletRequest.getParameter("tbDeathregDTO.ceAddrMar");
		 String ceId=httpServletRequest.getParameter("cemetryId");
		 
		ModelAndView mv = null;
		//String chargesAmount = null;
		final DeathRegistrationModel deathModel = this.getModel();
		//WSResponseDTO certificateCharges = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		deathModel.setCommonHelpDocs("DeathRegistration.html");
		/*
		 * if(ceId!=null && !ceId.isEmpty()) { Long ceId1 = Long.valueOf(ceId);
		 * for(CemeteryMasterDTO ce:deathModel.getCemeteryMasterDTOList()) {
		 * if(ce.getCeId().equals(ceId1)) {
		 * deathModel.getTbDeathregDTO().setCeId(ce.getCeId());
		 * deathModel.getTbDeathregDTO().setCeAddr(ce.getCeAddr());
		 * deathModel.getTbDeathregDTO().setCeAddrMar(ce.getCeAddrMar());
		 * deathModel.getTbDeathregDTO().setCeName(ce.getCeName());
		 * deathModel.getTbDeathregDTO().setCeNameMar(ce.getCeNameMar()); break; } } }
		 */		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DR,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		
		if(serviceMas.getSmFeesSchedule()!=0)
		{
			getCharges();
		}
		
		if(this.getModel().getOfflineDTO().getAmountToShow()!=null) {
			if(this.getModel().getOfflineDTO().getAmountToShow()!=0 || !(this.getModel().getOfflineDTO().getAmountToShow()==0.0)) {
				this.getModel().getTbDeathregDTO().setAmount(this.getModel().getOfflineDTO().getAmountToShow());		
			} else {
				this.getModel().getTbDeathregDTO().setAmount(0.0);
			}
		} else {
			this.getModel().getTbDeathregDTO().setAmount(0.0);
		}
		
		if (lookup.getLookUpCode().equals("A")) {

			WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

			if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
				final CheckListModel checkListModel = (CheckListModel) models.get(0);

				long noOfDays = iDeathRegistrationService.CalculateNoOfDays(deathModel.getTbDeathregDTO());

				checkListModel.setOrgId(orgId);
				checkListModel.setServiceCode("DR");
				checkListModel.setNoOfDays(noOfDays);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName("ChecklistModel");
				checklistReqDto.setDataModel(checkListModel);
				WSResponseDTO checklistRespDto = brmsCommonService.getChecklist(checklistReqDto);

				if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(checklistRespDto.getWsStatus())
						|| MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {

					if (!MainetConstants.CommonConstants.NA.equalsIgnoreCase(checklistRespDto.getWsStatus())) {
						List<DocumentDetailsVO> checkListList = Collections.emptyList();
						checkListList = (List<DocumentDetailsVO>) checklistRespDto.getResponseObj();// docs;

						if ((checkListList != null) && !checkListList.isEmpty()) {
							deathModel.setCheckList(checkListList);
						} else {
							// Message For Checklist
							this.getModel().addValidationError("No CheckList Found");
						}
						mv = new ModelAndView("DeathRegistrationEditValidn", MainetConstants.FORM_NAME, getModel());
					} else {
						mv = new ModelAndView("DeathRegistrationEdit", MainetConstants.FORM_NAME, getModel());
					}
				}
			}

		} else {
			mv = new ModelAndView("DeathRegistrationEdit", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	private BndRateMaster populateChargeModel(DeathRegistrationModel deathModel, BndRateMaster bndRateMaster) {
		bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		bndRateMaster.setServiceCode(BndConstants.DR);
		bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
		return bndRateMaster;
	}

	@SuppressWarnings("unchecked")
	public List<Object> castResponse(final WSResponseDTO response, final Class<?> clazz, final int position) {

		Object dataModel = null;
		LinkedHashMap<Long, Object> responseMap = null;
		final List<Object> dataModelList = new ArrayList<>();
		try {
			if (MainetConstants.SUCCESS_MSG.equalsIgnoreCase(response.getWsStatus())) {
				final List<?> list = (List<?>) response.getResponseObj();
				final Object object = list.get(position);
				responseMap = (LinkedHashMap<Long, Object>) object;
				final String jsonString = new JSONObject(responseMap).toString();
				dataModel = new ObjectMapper().readValue(jsonString, clazz);
				dataModelList.add(dataModel);
			}

		} catch (final IOException e) {
			logger.error("Error Occurred during cast response object while BRMS call is success!", e);
		}

		return dataModelList;

	}

	@RequestMapping(params = "searchDeathDraft", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<TbDeathRegdraftDto> SearchDeathDraft(@RequestParam("applnId") final Long applnId,
			@RequestParam("drDod") final Date drDod, final HttpServletRequest request, final Model model) {

		DeathRegistrationModel deathRegistrationModel = this.getModel();
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TbDeathRegdraftDto> tbDeathRegdraftList = iDeathRegistrationService.getDeathRegisteredAppliDetail(applnId,
				drDod, orgId);

		deathRegistrationModel.setTbDeathRegdraftDtoList(tbDeathRegdraftList);
		return getDeath(tbDeathRegdraftList);

	}

	public List<TbDeathRegdraftDto> getDeath(List<TbDeathRegdraftDto> deaths) {
		List<TbDeathRegdraftDto> listdraftDto = new ArrayList<TbDeathRegdraftDto>();
		deaths.forEach(entity -> {
			TbDeathRegdraftDto dto= new TbDeathRegdraftDto();
			BeanUtils.copyProperties(entity, dto);
			if(dto.getCpdRegUnit()!=null && dto.getCpdRegUnit()!=0)
			{	
				dto.setCpdDesc(
					CommonMasterUtility.getCPDDescription(dto.getCpdRegUnit(), MainetConstants.BLANK));
			}
			listdraftDto.add(dto);
		});
		return listdraftDto;
	}
	
	
	@RequestMapping(params = "getCemeteryDetails", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody CemeteryMasterDTO getCemeteryDetails(@RequestParam("ceId") final Long ceId,
		             final HttpServletRequest request, final Model model) {
		getModel().bind(request);
		CemeteryMasterDTO cemeteryMasterDTO = iCemeteryMasterService.getCemeteryById(ceId);
		
		return cemeteryMasterDTO;

	}
	
	
	@SuppressWarnings("deprecation")
	@ResponseBody
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editDeathDraft(Model model, @RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long drDraftId,
			@RequestParam("applicationId") Long applicationId, final HttpServletRequest httpServletRequest) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setTbDeathregDTO(iDeathRegistrationService
				.getTbDeathregDTOFromDraftDTO(iDeathRegistrationService.getDeathById(drDraftId)));
		this.getModel().getTbDeathregDTO().setApmApplicationId(applicationId != null ? applicationId : null);
		this.getModel().setSaveMode(mode);
		this.getModel().setCommonHelpDocs("DeathRegistration.html");
		List<CFCAttachment> checklist = new ArrayList<>();
		checklist = (iChecklistVerificationService.getDocumentUploadedByRefNo(String.valueOf(applicationId), this.getModel().getTbDeathregDTO().getOrgId()));
		this.getModel().setFetchDocumentList(checklist);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DR,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		if(lookup.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
		{
			this.getModel().getTbDeathregDTO().setCheckStatus("A");
		}else {
			this.getModel().getTbDeathregDTO().setCheckStatus("NA");
		}
		/*
		 * if(!lookup.getLookUpCode().equals("A") && serviceMas.getSmFeesSchedule()!=0)
		 * { this.getModel().getTbDeathregDTO().setChargeStatus("CA"); }
		 */
		return new ModelAndView("DeathRegistrationEdit", MainetConstants.FORM_NAME, this.getModel());
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(params = "deathRegDraft", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addpopulationForm1(Model model, final HttpServletRequest request) {
		this.sessionCleanup(request);
		DeathRegistrationModel deathRegistrationModel = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		try {
			model.addAttribute("hospitalList",
					deathRegistrationModel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId)));
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		}

		try {
			model.addAttribute("cemeteryList",
					deathRegistrationModel.setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemetery(orgId)));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
			throw new FrameworkException("Some Problem Occured While Fetching Cemetery List");
		}
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.DR,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		if(lookup.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()!=0)
		{
			deathRegistrationModel.getTbDeathregDTO().setCheckStatus("A");
		}else {
			deathRegistrationModel.getTbDeathregDTO().setCheckStatus("NA");
		}
		deathRegistrationModel.setSaveMode(MainetConstants.CommonConstants.ADD);
		
		return new ModelAndView("DeathRegistrationEdit", MainetConstants.FORM_NAME, deathRegistrationModel);

	}
	
	@RequestMapping(params = "checkRegnoDupl", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody boolean checkRegnoDupl(@RequestParam("drRegno") final String drRegno,@RequestParam("drDraftId") final Long drDraftId,
                                        final HttpServletRequest request)
	{
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		boolean result = iDeathRegistrationService.checkregnoByregno(drRegno,orgId,drDraftId);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private void getCharges()
	{
		final DeathRegistrationModel deathModel = this.getModel();
		WSResponseDTO certificateCharges = null;
		String chargesAmount = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		final WSRequestDTO requestDTO1 = new WSRequestDTO();
		requestDTO1.setModelName("BNDRateMaster");
		WSResponseDTO response1 = brmsCommonService.initializeModel(requestDTO1);
		// Charges started
		 ChargeDetailDTO chargeDetailDTO=new ChargeDetailDTO();
		 List<ChargeDetailDTO> chargesInfo=new ArrayList<>();
		 if (response1.getWsStatus() != null
	                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response1.getWsStatus())) {
		List<Object> rateMaster = RestClient.castResponse(response1, BndRateMaster.class, 0);
		BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
		rateMasterModel.setOrgId(orgId);
		rateMasterModel.setServiceCode(BndConstants.DR);
		rateMasterModel.setChargeApplicableAt(
				Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
						BndConstants.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
		rateMasterModel.setOrganisationType(CommonMasterUtility
				.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
				.getDescLangFirst());
		final WSRequestDTO taxRequestDto = new WSRequestDTO();
		taxRequestDto.setDataModel(rateMasterModel);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(),
				orgId);
		WSResponseDTO responsefortax =null;
				try {
				responsefortax =issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
				}catch(Exception ex) {
					chargesAmount = BndConstants.CHARGES_AMOUNT;
					deathModel.setChargesAmount(chargesAmount);
					deathModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
					this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
					if(serviceMas.getSmFeesSchedule()!=0) {
						this.getModel().getTbDeathregDTO().setChargeStatus(BndConstants.CHARGES_APPLY_STATUS);
					}
					return;
				}
				

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
			 List<Object> detailDTOs=null;
        	  LinkedHashMap<String,String> charges=null;
			if (!responsefortax.isFree()) {
				final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
				final List<BndRateMaster> requiredCharges = new ArrayList<>();
				for (final Object rate : rates) {
					BndRateMaster masterrate = (BndRateMaster) rate;
					masterrate = populateChargeModel(deathModel, masterrate);
					requiredCharges.add(masterrate);
				}
				final WSRequestDTO bndChagesRequestDto = new WSRequestDTO();
				bndChagesRequestDto.setDataModel(requiredCharges);
				bndChagesRequestDto.setModelName(BndConstants.BND_RATE_MASTER);
				certificateCharges = issuenceOfBirthCertificateService.getBndCharge(bndChagesRequestDto);
				if(certificateCharges != null) {
				 detailDTOs = (List<Object>) certificateCharges.getResponseObj();
				for (final Object rate : detailDTOs) {
					charges = (LinkedHashMap<String, String>) rate;
					break;
				}
				chargesAmount = issuenceOfBirthCertificateService.getAmount(Long.valueOf(Utility.getDaysBetweenDates(deathModel.getTbDeathregDTO().getDrDod(), deathModel.getTbDeathregDTO().getDrRegdate())), charges);					
				deathModel.setChargesAmount(chargesAmount);
				}else {
					chargesAmount = BndConstants.CHARGES_AMOUNT;
					deathModel.setChargesAmount(chargesAmount);
					deathModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
					this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
				}
			} else {
				chargesAmount = BndConstants.CHARGES_AMOUNT;
				deathModel.setChargesAmount(chargesAmount);
			}
			
			 if(chargesAmount != null) {
			 chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
		     }
				TbTaxMas taxMaster =null;
			 if(certificateCharges != null) {
			 taxMaster=tbTaxMasService.getTaxMasterByTaxCode(orgId, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
			 chargeDetailDTO.setChargeCode(taxMaster.getTaxId());
			 }
			 chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("TbDeathregDTO.drDeathSerRegName"));
			 chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("TbDeathregDTO.drDeathSerEngName"));
			 chargesInfo.add(chargeDetailDTO);
			 deathModel.setChargesInfo(chargesInfo);
			 if(chargesAmount != null) {
			 deathModel.setChargesAmount(chargesAmount);
			 this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
			 }
			 if(serviceMas.getSmFeesSchedule()!=0) {
					this.getModel().getTbDeathregDTO().setChargeStatus(BndConstants.CHARGES_APPLY_STATUS);
				}
		}
		}else {
			//when BRMS server off
			chargesAmount = BndConstants.CHARGES_AMOUNT;
			deathModel.setChargesAmount(chargesAmount);
			deathModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
			this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
		}
		// Charges End
		
	}
	
	

}
