package com.abm.mainet.adh.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.adh.datamodel.ADHRateMaster;
import com.abm.mainet.adh.dto.ADHRequestDto;
import com.abm.mainet.adh.dto.ADHResponseDTO;
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.service.IAgencyRegistrationService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationFormModel;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationFormValidator;
import com.abm.mainet.adh.ui.validator.AgencyRegistrationValidatorOwner;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.ChargeDetailDTO;
import com.abm.mainet.common.dto.CheckListModel;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.ICommonBRMSService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.domain.CFCAttachment;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.integration.ws.JersyCall;
import com.abm.mainet.integration.ws.dto.WSRequestDTO;
import com.abm.mainet.integration.ws.dto.WSResponseDTO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * @author cherupelli.srikanth
 * @since 17 October 2019
 */
@Controller
@RequestMapping("/AgencyRegistrationForm.html")
public class AgencyRegistrationFormController extends AbstractFormController<AgencyRegistrationFormModel> {

	@Autowired
	private IPortalServiceMasterService PortalServiceMaster;

	@Autowired
	private ICommonBRMSService iCommonBRMSService;

	@Autowired
	IAgencyRegistrationService agencyRegistrationService;
	
	@Autowired
    private INewAdvertisementApplicationService advertisementApplicationService;

	@Autowired
	IBRMSADHService BRMSADHService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		LookUp agencyTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.AGN,
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.ADC, UserSession.getCurrent().getOrganisation());

		if (agencyTypeLookUp != null) {
			this.getModel().setAgenctCategoryLookUp(agencyTypeLookUp);
		}

		PortalService service = PortalServiceMaster.getService(
				PortalServiceMaster.getServiceId(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE, orgId),
				orgId);
		this.getModel().setService(service);
		this.getModel().getAgencyRequestDto().getMasterDto().setApplicationTypeFlag(MainetConstants.FlagN);
		LinkedHashMap<String, Object> checkListChargeFlagAndLicMaxDay = agencyRegistrationService
				.getCheckListChargeFlagAndLicMaxDay(orgId,
						MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
		if (MapUtils.isNotEmpty(checkListChargeFlagAndLicMaxDay)) {
			for (Map.Entry<String, Object> map : checkListChargeFlagAndLicMaxDay.entrySet()) {
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.CHECKLIST_APPL_FLAG)) {
					this.getModel().setCheckListApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.APPL_CHARGE_FLAG)) {
					this.getModel().setApplicationchargeApplFlag(map.getValue().toString());
				}
				if (StringUtils.equals(map.getKey(), MainetConstants.AdvertisingAndHoarding.LIC_MAX_TENURE_DAYS)) {
					Long valueOf = Long.valueOf(map.getValue().toString());
					this.getModel().setLicMaxTenureDays(valueOf);
				}

			}
		}
		if(StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagY))
	    this.getModel().getAgencyRequestDto().setFree(false);
		if(StringUtils.equals(this.getModel().getApplicationchargeApplFlag(), MainetConstants.FlagN))
			this.getModel().getAgencyRequestDto().setFree(true);
		return index();
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CHECKLIST_CHARGES, method = RequestMethod.POST)
	public ModelAndView getChecklistAndCharges(HttpServletRequest request) {
		this.getModel().bind(request);
		AgencyRegistrationFormModel model = this.getModel();
		model.validateBean(model.getAgencyRequestDto().getMasterDto(), AgencyRegistrationValidatorOwner.class);
		ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_VALIDN,
				MainetConstants.FORM_NAME, this.getModel());

		if (!model.hasValidationErrors()) {
			final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
			checkListRateRequestModel.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
			WSResponseDTO checkListRateResponseModel = iCommonBRMSService.initializeModel(checkListRateRequestModel);

			// checking the checkList initializing response is success or not(if not
			// displaying the message on Screen)
			if (StringUtils.equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS,
					checkListRateResponseModel.getWsStatus())) {
				List<DocumentDetailsVO> checkListList = new ArrayList<>();
				final List<Object> castCheckListModel = JersyCall.castResponse(checkListRateResponseModel,
						CheckListModel.class, 0);

				final List<Object> ADHRateMasterList = JersyCall.castResponse(checkListRateResponseModel,
						ADHRateMaster.class, 1);

				final CheckListModel checkListModel = (CheckListModel) castCheckListModel.get(0);
				final ADHRateMaster ADHRateMaster = (ADHRateMaster) ADHRateMasterList.get(0);

				populateChecklistModel(checkListModel);
				WSRequestDTO checklistReqDto = new WSRequestDTO();
				checklistReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECK_LIST);
				checklistReqDto.setDataModel(checkListModel);

				checkListList = iCommonBRMSService.getChecklist(checkListModel);

				// checking the response after getting applicable checklist from BRMS is
				// success.(If not then, displaying the validation message on Screen)
				if (checkListList != null && !checkListList.isEmpty()) {
					this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
					if (CollectionUtils.isNotEmpty(checkListList)) {
						long count = 1;
						for (DocumentDetailsVO doc : checkListList) {
							doc.setDocumentSerialNo(count);
							count++;
						}
						model.setCheckList(checkListList);
					}
				}

				if (StringUtils.equals(model.getApplicationchargeApplFlag(), MainetConstants.FlagY)) {
					Double applicationCharge = callBrmsForApplicationCharges(model, ADHRateMaster);
					if (applicationCharge <= 0) {
						model.addValidationError(getApplicationSession().getMessage("adh.validate.application.charges.notfound"));
					}
				}
			} else {
				// This message if error occured in initializing the checkList
				model.addValidationError(
						getApplicationSession().getMessage("adh.problem.occured.initializing.checklist"));
			}

		}
		
		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	private void populateChecklistModel(CheckListModel checklistModel) {

		checklistModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		checklistModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
		checklistModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	}

	
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE_AGENCY_REGISTRATION, method = RequestMethod.POST)
	public Map<String, Object> saveAgencyRegistration(HttpServletRequest httpServletRequest)
			throws JsonParseException, JsonMappingException, IOException {
		getModel().bind(httpServletRequest);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		if (this.getModel().saveForm()) {
			object.put(MainetConstants.AdvertisingAndHoarding.APPLICATION_ID,
					this.getModel().getAgencyRequestDto().getApplicationId());
			this.getModel().getAgencyRequestDto().getMasterDto()
					.setApmApplicationId(this.getModel().getAgencyRequestDto().getApplicationId());

		} else {
			object.put(MainetConstants.AdvertisingAndHoarding.ERROR, this.getModel().getBindingResult().getAllErrors());
		}

		return object;

	}

	/**
	 * This method is used to print Agency Registraton Acknowledgement
	 * 
	 * @param request
	 * @return Agency Registraton Acknowledgement
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.PRINT_AGENCY_REG_ACKW, method = {
			RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		AgencyRegistrationFormModel model = this.getModel();
		List<CFCAttachment> downloadDocs = new ArrayList<>();
		List<CFCAttachment> preparePreviewOfFileUpload = model.preparePreviewOfFileUpload(downloadDocs,
				this.getModel().getCheckList());

		if (CollectionUtils.isNotEmpty(preparePreviewOfFileUpload)) {
			this.getModel().setDocumentList(preparePreviewOfFileUpload);
		}

		String applicantName = model.getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE;
		applicantName += model.getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
				: model.getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
		applicantName += model.getApplicantDetailDto().getApplicantLastName();
		this.getModel().setApplicationId(model.getAgencyRequestDto().getMasterDto().getApmApplicationId());
		this.getModel().setApplicantName(applicantName);
		this.getModel().setServiceName(this.getModel().getService().getServiceName());
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_ACKNOWLEDGEMENT,
				MainetConstants.FORM_NAME, this.getModel());

	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	/**
	 * This method is used to call BRMS Sheet for application charges.
	 * @param model
	 * @param ADHRateMaster
	 * @return Application Charges
	 */
	private Double callBrmsForApplicationCharges(AgencyRegistrationFormModel model, ADHRateMaster ADHRateMaster) {
		Double amoutToPay = 0.0;
		final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
		ADHRateMaster.setChargeApplicableAt(
				Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.Prefix.APL,
						PrefixConstants.Common.CAA, UserSession.getCurrent().getOrganisation()).getLookUpId()));
		adhRateRequestDto.setDataModel(ADHRateMaster);

		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);

		WSResponseDTO adhRateResponseDto = BRMSADHService.getApplicableTaxes(ADHRateMaster);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateResponseDto.getWsStatus())) {
			if (!adhRateResponseDto.isFree()) {

				final List<?> rates = JersyCall.castResponse(adhRateResponseDto, ADHRateMaster.class);
				final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
				for (final Object rate : rates) {
					ADHRateMaster master1 = (com.abm.mainet.adh.datamodel.ADHRateMaster) rate;
					master1 = populateChargeModel(model, master1);
					requiredCHarges.add(master1);
				}
				WSRequestDTO chargeReqDto = new WSRequestDTO();
				chargeReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.ADH_Rate_Master);
				chargeReqDto.setDataModel(requiredCHarges);
				List<ChargeDetailDTO> detailDTOs = BRMSADHService.getApplicableCharges(requiredCHarges);

				model.getAgencyRequestDto().setFree(false);
				model.setPayableFlag(MainetConstants.FlagY);
				model.setChargesInfo(detailDTOs);
				model.setAmountToPay((chargesToPay(detailDTOs)));
				model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
				model.setApplicationchargeApplFlag(MainetConstants.FlagN);
			} else {
				model.setPayableFlag(MainetConstants.FlagN);
				model.getAgencyRequestDto().setFree(true);
				model.setAmountToPay(0.0d);
			}
		}
		amoutToPay = model.getAmountToPay();
		return amoutToPay;
	}

	
	/**
	 * This method is used to set the data to call BRMS charges
	 * @param model
	 * @param chargeModel
	 * @return ADHRateMaster with the data
	 */
	private ADHRateMaster populateChargeModel(final AgencyRegistrationFormModel model,
			final ADHRateMaster chargeModel) {
		chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		chargeModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
		return chargeModel;
	}
	
	//D#79968
	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam("appId") final long appId,
			final HttpServletRequest httpServletRequest) throws Exception {
		getModel().bind(httpServletRequest);
		try {
			//call to GET_ADH_DATA_BY_APPLICATION_ID
			ADHRequestDto adhRequestDto = new ADHRequestDto();
			adhRequestDto.setApplicationId(appId);
			adhRequestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			adhRequestDto.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
			ADHResponseDTO  adhResponseDTO=advertisementApplicationService.getADHDataByApplicationId(adhRequestDto);
			getModel().setMasterDtoList(adhResponseDTO.getAgencyRegistrationRequestDto().getMasterDtolist());
			//getModel().setAgencyRequestDto(adhResponseDTO.getAgencyRegistrationRequestDto());
			getModel().setApplicantDetailDto(adhResponseDTO.getAgencyRegistrationRequestDto().getApplicantDetailDto());
			
		} catch (Exception exception) {
			throw new FrameworkException(exception);
		}
		LookUp agencyTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.AGN,
				PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.ADC, UserSession.getCurrent().getOrganisation());

		if (agencyTypeLookUp != null) {
			getModel().setAgenctCategoryLookUp(agencyTypeLookUp);
		}
		getModel().setSaveMode(MainetConstants.AdvertisingAndHoarding.VIEW);
		getModel().setCheckListApplFlag(MainetConstants.AdvertisingAndHoarding.VIEW);
		getModel().setApplicationchargeApplFlag(MainetConstants.AdvertisingAndHoarding.VIEW);
		return new ModelAndView("AgencyRegistrationFormValidn", MainetConstants.FORM_NAME, getModel());
	}
	
	
	
	/**
	 * To get Ownership table
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param ownershipType
	 * @return
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		AgencyRegistrationFormModel model = this.getModel();
		model.setOwnershipPrefix(ownershipType);
		if( model.getAgencyRequestDto().getMasterDtolist().isEmpty() || (model.getAgencyRequestDto().getMasterDtolist() !=null 
				&& model.getAgencyRequestDto().getMasterDtolist().size() <= 2 && model.getAgencyRequestDto().getMasterDtolist().get(0).getAgencyOwner() == null  )) {
			model.getAgencyRequestDto().getMasterDtolist().clear();
		if(model.getOwnershipPrefix().equals("A")) {
			AdvertiserMasterDto dto=new AdvertiserMasterDto();
			AdvertiserMasterDto dto1=new AdvertiserMasterDto();
			model.getAgencyRequestDto().getMasterDtolist().add(dto);
			model.getAgencyRequestDto().getMasterDtolist().add(dto1);
		}
		}
			
		
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME, model);
	}
	
	
	 /*@RequestMapping(params = "deleteOwnerTable", method = RequestMethod.POST)
	    public @ResponseBody void deleteOwnerTable(HttpServletRequest httpServletRequest,
	            HttpServletResponse httpServletResponse, @RequestParam(value = "deletedOwnerRowId") Integer deletedOwnerRowId) {
	        this.getModel().bind(httpServletRequest);
	        AgencyRegistrationModel model = this.getModel();
	        if (!model.getAgencyRequestDto().getMasterDto().isEmpty()
	                && model.getAgencyRequestDto().getMasterDto().size() > deletedOwnerRowId) {
	        	AdvertiserMasterDto detDto = model.getAgencyRequestDto().getMasterDto().get(deletedOwnerRowId);
	            model.getAgencyRequestDto().getMasterDto().remove(detDto);
	        }
	    }*/
	
	
	
	/*@RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.ENTRY_DELETE)
	public void doEntryDeletion(@RequestParam(name = MainetConstants.AdvertisingAndHoarding.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					FileUploadUtility.getCurrent().getFileMap().remove((long) id);
				}

			}

		}

	}*/
	
	
	
	/*@RequestMapping(method = RequestMethod.POST, params = MainetConstants.AdvertisingAndHoarding.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		//List<DocumentDetailsVO> attachments = new ArrayList<>();
		List<AdvertiserMasterDto> owner = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getLength(); i++) {
			attachments.add(new DocumentDetailsVO());
			owner.add(new TradeLicenseOwnerDetailDTO());
		}
		int count = 0;
		for (AdvertiserMasterDto ownerDto : this.getModel().getAdvertiserMasterDto()
				.getAdvertiserMasterDtoList()) {
			BeanUtils.copyProperties(ownerDto, owner.get(count));
			count++;
		}
		//this.getModel().getTradeMasterDetailDTO().setAttachments(attachments);
		this.getModel().getAdvertiserMasterDto().setAdvertiserMasterDtoList(owner);
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
				this.getModel());
	}*/
	
}
