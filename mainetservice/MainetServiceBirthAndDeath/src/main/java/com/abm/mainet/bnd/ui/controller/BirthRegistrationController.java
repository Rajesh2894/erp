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
import com.abm.mainet.bnd.dto.BirthRegDraftDto;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IssuenceOfBirthCertificateService;
import com.abm.mainet.bnd.ui.model.BirthRegistrationModel;
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
@RequestMapping(value = "/BirthRegistrationForm.html")
public class BirthRegistrationController extends AbstractFormController<BirthRegistrationModel> {

	private static final Logger LOGGER = Logger.getLogger(BirthRegistrationController.class);

	@Autowired
	private IHospitalMasterService iHospitalMasterService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private BRMSCommonService brmsCommonService;
	
	@Autowired
	private TbTaxMasService tbTaxMasService;

	@Autowired
	private IBirthRegService iBirthRegService;
	
	@Autowired
	private IssuenceOfBirthCertificateService issuenceOfBirthCertificateService;
	
	@Resource
	private ServiceMasterService serviceMasterService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest) {

		sessionCleanup(httpServletRequest);
		BirthRegistrationModel appModel = this.getModel();
		appModel.setCommonHelpDocs("BirthRegistrationForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		try {
			List<HospitalMasterDTO> hospitalList = appModel
					.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalByStatus("A", orgId));
			model.addAttribute("hospitalMasterDTOList", hospitalList);    //hospitalList
			//appModel.setHospitalMasterDTOList(hospitalList);
			
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		}
		
		List<BirthRegDraftDto> birthRegDraftList = iBirthRegService.getAllBirthRegdraft(UserSession.getCurrent().getOrganisation().getOrgid());
		List<BirthRegDraftDto> tbBirthRegDtoLists = getBirth(birthRegDraftList);
		appModel.setBirthRegDraftDtoList(tbBirthRegDtoLists);
		model.addAttribute("data", tbBirthRegDtoLists);
		return new ModelAndView("BirthRegistration", MainetConstants.FORM_NAME, getModel());

		//return new ModelAndView("BirthRegistrationForm", MainetConstants.FORM_NAME, getModel());

	}

	public List<BirthRegDraftDto> getBirth(List<BirthRegDraftDto> births) {
		List<BirthRegDraftDto> listdraftDto = new ArrayList<BirthRegDraftDto>();
		births.forEach(entity -> {
			BirthRegDraftDto dto= new BirthRegDraftDto();
			BeanUtils.copyProperties(entity, dto);
			if(dto.getPdRegUnitId()!=null && dto.getPdRegUnitId()!=0)
			{	
				dto.setCpdDesc(
					CommonMasterUtility.getCPDDescription(dto.getPdRegUnitId(), MainetConstants.BLANK));
			}
			listdraftDto.add(dto);
		});
		return listdraftDto;
	}

@RequestMapping(params = "searchBirthDraft", method = RequestMethod.POST, produces = "Application/JSON")
	public @ResponseBody List<BirthRegDraftDto> searchBirthDraft(@RequestParam("applnId") final Long applnId,
			@RequestParam("brDob") final Date brDob, final HttpServletRequest request, final Model model) {

		BirthRegistrationModel birthRegistrationModel = this.getModel();
		birthRegistrationModel.setCommonHelpDocs("BirthRegistrationForm.html");
		getModel().bind(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<BirthRegDraftDto> birthRegDraftDtoList = iBirthRegService.getBirthRegDraftAppliDetail(applnId,
				brDob, orgId);

		birthRegistrationModel.setBirthRegDraftDtoList(birthRegDraftDtoList);
		return getBirth(birthRegDraftDtoList);

	}

       @ResponseBody
	@RequestMapping(params = "editBND", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView editBirthDraft(Model model, @RequestParam String mode,
			@RequestParam(MainetConstants.Common_Constant.ID) Long brDraftId,
			@RequestParam("applicationId") Long applicationId, final HttpServletRequest httpServletRequest) {

		this.getModel().setBirthRegDto(iBirthRegService
				.getBirthRegDTOFromDraftDTO(iBirthRegService.getBirthById(brDraftId)));
		this.getModel().getBirthRegDto().setApmApplicationId(applicationId != null ? applicationId : null);
		this.getModel().setSaveMode(mode);
		List<CFCAttachment> checklist = new ArrayList<>();
		checklist = (iChecklistVerificationService.getDocumentUploadedByRefNo(String.valueOf(applicationId), this.getModel().getBirthRegDto().getOrgId()));
		this.getModel().setFetchDocumentList(checklist);
		BirthRegistrationModel birthRegistrationModel = this.getModel();
		birthRegistrationModel.setCommonHelpDocs("BirthRegistrationForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BR, orgId);
		LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
		if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()==1 )
		{
		this.getModel().getBirthRegDto().setStatusCheck("A");
		}
		else {
			this.getModel().getBirthRegDto().setStatusCheck("NA");
		}

		try {
			model.addAttribute("hospitalList",
					birthRegistrationModel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalByStatus("A", orgId)));
		} catch (Exception e) {
			LOGGER.error("", e);
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		}

		return new ModelAndView("BirthRegistrationEdit", MainetConstants.FORM_NAME, this.getModel());
	}


	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(params = "getCheckListAndCharges", method = RequestMethod.POST)
	public ModelAndView getCheckListAndCharges(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, final Model model) {
		//JsonViewObject responseObj = null;
		getModel().bind(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		ModelAndView mv = null;
		final BirthRegistrationModel birthRegModel = this.getModel();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		birthRegModel.setCommonHelpDocs("BirthRegistrationForm.html");

		final WSRequestDTO requestDTO = new WSRequestDTO();
		requestDTO.setModelName("ChecklistModel");
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BR,orgId);
		LookUp lookup= CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify());
		
		if(serviceMas.getSmFeesSchedule()!=0)
		{
			getCharges();
		}
		
		if(this.getModel().getOfflineDTO().getAmountToShow()!=null) {
			if(this.getModel().getOfflineDTO().getAmountToShow()!=0 || !(this.getModel().getOfflineDTO().getAmountToShow()==0.0)) {
				this.getModel().getBirthRegDto().setAmount(this.getModel().getOfflineDTO().getAmountToShow());		
			} else {
				this.getModel().getBirthRegDto().setAmount(0.0);
			}
		} else {
			this.getModel().getBirthRegDto().setAmount(0.0);
		}
		
		if (lookup.getLookUpCode().equals("A")) {

		WSResponseDTO response = brmsCommonService.initializeModel(requestDTO);

		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			final List<Object> models = this.castResponse(response, CheckListModel.class, 0);
			final CheckListModel checkListModel = (CheckListModel) models.get(0);
			long noOfDays = iBirthRegService.CalculateNoOfDays(birthRegModel.getBirthRegDto());
			checkListModel.setOrgId(orgId);
			checkListModel.setServiceCode(BndConstants.BR);
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
						birthRegModel.setCheckList(checkListList);
					} else {
						// Message For Checklist
						this.getModel().addValidationError("No CheckList Found");
					}				
					mv = new ModelAndView("BirthRegistrationEditValidn", MainetConstants.FORM_NAME, getModel());
				} else {
					mv = new ModelAndView("BirthRegistrationEdit", MainetConstants.FORM_NAME, getModel());
				}
			}
			
		}
		}else {
			mv = new ModelAndView("BirthRegistrationEdit", MainetConstants.FORM_NAME, getModel());
		}
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
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

	 private BndRateMaster populateChargeModel(BirthRegistrationModel model,
	    		BndRateMaster bndRateMaster) {
	    	bndRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	    	bndRateMaster.setServiceCode(BndConstants.BR);
	    	bndRateMaster.setDeptCode(BndConstants.BIRTH_DEATH);
	    	//bndRateMaster.setStartDate(new Date().getTime());
	        return bndRateMaster;
	    }
	
	 @RequestMapping(params = "birthRegDraft", method = { RequestMethod.POST, RequestMethod.GET })
		public ModelAndView addpopulationForm1(Model model, final HttpServletRequest request) {
			this.sessionCleanup(request);
			BirthRegistrationModel birthRegistrationModel = this.getModel();
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(BndConstants.BR, orgId);
			LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMas.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
			if(lookUp.getLookUpCode().equals("A") || serviceMas.getSmFeesSchedule()==1 )
			{
			this.getModel().getBirthRegDto().setStatusCheck("A");
			}
			else {
				this.getModel().getBirthRegDto().setStatusCheck("NA");
			}
			try {
				model.addAttribute("hospitalList",
						birthRegistrationModel.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalByStatus("A", orgId)));
			} catch (Exception e) {
				logger.error("", e);
				throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
			}
			birthRegistrationModel.setSaveMode(MainetConstants.CommonConstants.ADD);
			return new ModelAndView("BirthRegistrationEdit", MainetConstants.FORM_NAME, birthRegistrationModel);

		}
	 
	@SuppressWarnings("unchecked")
	private void getCharges() {
		BirthRegistrationModel appModel = this.getModel();
		String chargesAmount = null;
		WSResponseDTO certificateCharges = null;
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final WSRequestDTO requestDTO1 = new WSRequestDTO();
		requestDTO1.setModelName("BNDRateMaster");
		WSResponseDTO response1 = brmsCommonService.initializeModel(requestDTO1);
		// Charges started
		ChargeDetailDTO chargeDetailDTO = new ChargeDetailDTO();
		List<ChargeDetailDTO> chargesInfo = new ArrayList<>();
		if (response1.getWsStatus() != null
                && MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response1.getWsStatus())) {
		List<Object> rateMaster = RestClient.castResponse(response1, BndRateMaster.class, 0);
		BndRateMaster rateMasterModel = (BndRateMaster) rateMaster.get(0);
		rateMasterModel.setOrgId(orgId);
		rateMasterModel.setServiceCode(BndConstants.BR);
		rateMasterModel.setChargeApplicableAt(
				Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
						BndConstants.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
		rateMasterModel.setOrganisationType(CommonMasterUtility
				.getNonHierarchicalLookUpObjectByPrefix(UserSession.getCurrent().getOrganisation().getOrgCpdId(),
						UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.CommonMasterUi.OTY)
				.getDescLangFirst());
		final WSRequestDTO taxRequestDto = new WSRequestDTO();
		taxRequestDto.setDataModel(rateMasterModel);
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(rateMasterModel.getServiceCode(), orgId);
		WSResponseDTO responsefortax = null;
		try {
				responsefortax =issuenceOfBirthCertificateService.getApplicableTaxes(taxRequestDto);
		}catch(Exception ex){
			chargesAmount = BndConstants.CHARGES_AMOUNT;
			appModel.setChargesAmount(chargesAmount);
			appModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
			this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
			if(serviceMas.getSmFeesSchedule()!=0) {
				this.getModel().getBirthRegDto().setChargesStatus(BndConstants.CHARGES_APPLY_STATUS);
			}
			return;
		}

		if(responsefortax != null) {
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responsefortax.getWsStatus())) {
			List<Object> detailDTOs = null;
			LinkedHashMap<String, String> charges = null;
			if (!responsefortax.isFree()) {
				final List<Object> rates = (List<Object>) responsefortax.getResponseObj();
				final List<BndRateMaster> requiredCharges = new ArrayList<>();
				for (final Object rate : rates) {
					BndRateMaster masterrate = (BndRateMaster) rate;
					masterrate = populateChargeModel(appModel, masterrate);
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
				chargesAmount = issuenceOfBirthCertificateService
						.getAmount(Long.valueOf(Utility.getDaysBetweenDates(appModel.getBirthRegDto().getBrDob(),
								appModel.getBirthRegDto().getBrRegDate())), charges);
				// chargesAmount=String.valueOf(charges.get(BndConstants.BNDCHARGES));
				appModel.setChargesAmount(chargesAmount);
				}else {
					chargesAmount = BndConstants.CHARGES_AMOUNT;
					appModel.setChargesAmount(chargesAmount);
					appModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
					this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
				}
			} else {
				chargesAmount = BndConstants.CHARGES_AMOUNT;
				appModel.setChargesAmount(chargesAmount);
			}

			if(chargesAmount != null) {
			chargeDetailDTO.setChargeAmount(Double.parseDouble(chargesAmount));
		    }
			TbTaxMas taxMas=null;
			if(certificateCharges != null) {
				 taxMas=tbTaxMasService
						.getTaxMasterByTaxCode(orgId, serviceMas.getTbDepartment().getDpDeptid(), charges.get("taxCode"));
			chargeDetailDTO.setChargeCode(taxMas.getTaxId());
			}
			chargeDetailDTO.setChargeDescReg(getApplicationSession().getMessage("BirthRegDto.brBirthSerRegName"));
			chargeDetailDTO.setChargeDescEng(getApplicationSession().getMessage("BirthRegDto.brBirthSerEngName"));
			chargesInfo.add(chargeDetailDTO);
			appModel.setChargesInfo(chargesInfo);
			if(chargesAmount != null) {
			appModel.setChargesAmount(chargesAmount);
			this.getModel().getOfflineDTO().setAmountToShow(Double.parseDouble(chargesAmount));
		    }
		}
			if(serviceMas.getSmFeesSchedule()!=0) {
				this.getModel().getBirthRegDto().setChargesStatus(BndConstants.CHARGES_APPLY_STATUS);
			}
			}
		}else {
			//when BRMS server off
			chargesAmount = BndConstants.CHARGES_AMOUNT;
			appModel.setChargesAmount(chargesAmount);
			appModel.setChargesFetched(BndConstants.CHARGES_AMOUNT_FLG);
			this.getModel().addValidationError(getApplicationSession().getMessage("bnd.validation.brmscharges"));
		}
		// Charges End

	}
	  @RequestMapping(params = "checkRegnoDupl", method = RequestMethod.POST, produces = "Application/JSON")
		public @ResponseBody boolean checkRegnoDupl(@RequestParam("brRegNo") final String brRegNo,@RequestParam("brDraftId") final Long brDraftId,final HttpServletRequest request)
		{
			getModel().bind(request);
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			boolean result = iBirthRegService.checkregnoByregno(brRegNo,orgId,brDraftId);
			return result;
		}
	
}
