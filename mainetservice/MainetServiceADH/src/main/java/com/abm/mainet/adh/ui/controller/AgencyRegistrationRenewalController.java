package com.abm.mainet.adh.ui.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.adh.dto.AdvertiserMasterDto;
import com.abm.mainet.adh.dto.AgencyRegistrationResponseDto;
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IAdvertiserMasterService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.service.INewAdvertisementApplicationService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.adh.ui.validator.AgencyValidatorOwner;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.validitymaster.dto.LicenseValidityMasterDto;
import com.abm.mainet.validitymaster.service.ILicenseValidityMasterService;
import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author cherupelli.srikanth
 * @since 09 september 2019
 */
@Controller
@RequestMapping("/AgencyRegistrationRenewal.html")
public class AgencyRegistrationRenewalController extends AbstractFormController<AgencyRegistrationModel> {

    @Autowired
    private IAdvertiserMasterService advertiserMasterService;

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    IBRMSADHService BRMSADHService;

    @Autowired
    INewAdvertisementApplicationService advertisementApplicationService;
    
    @Autowired
	ILicenseValidityMasterService licenseValidityMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) {

        sessionCleanup(request);
        fileUploadService.sessionCleanUpForFileUpload();

        List<AdvertiserMasterDto> masterDtoList = advertiserMasterService
                .getLicNoAndAgenNameAndStatusByorgId(UserSession.getCurrent().getOrganisation().getOrgid());

        this.getModel()
                .setMasterDtoList(masterDtoList.stream()
                        .filter(master -> !StringUtils.equals(master.getAgencyStatus(), MainetConstants.FlagC)
                                && !StringUtils.equals(master.getAgencyStatus(), MainetConstants.FlagI)
                                && StringUtils.isNotBlank(master.getAgencyLicNo()))
                        .collect(Collectors.toList()));

        LookUp agencyTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
                PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.AGN,
                PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.ADC, UserSession.getCurrent().getOrganisation());

        if (agencyTypeLookUp != null) {
            this.getModel().setAgenctCategoryLookUp(agencyTypeLookUp);
        }

        ServiceMaster renewalServiceMaster = serviceMaster.getServiceByShortName(
                MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,
                UserSession.getCurrent().getOrganisation().getOrgid());

        this.getModel().setServiceMaster(renewalServiceMaster);

        if (StringUtils.equalsIgnoreCase(
                CommonMasterUtility.getNonHierarchicalLookUpObject(renewalServiceMaster.getSmChklstVerify(),
                        UserSession.getCurrent().getOrganisation()).getLookUpCode(),
                MainetConstants.FlagA)) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
        } else {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
        }
        if (StringUtils.equalsIgnoreCase(renewalServiceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
            this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagY);
            this.getModel().getAgencyRequestDto().setFree(false);
        } else {
            this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagN);
            this.getModel().getAgencyRequestDto().setFree(true);
        }

        this.getModel().setFormDisplayFlag(MainetConstants.FlagN);
        this.getModel().setServiceMaster(renewalServiceMaster);

        return index();
    }

    @SuppressWarnings("unused")
    @RequestMapping(params = "searchAgencyByLicNo", method = { RequestMethod.POST })
    public ModelAndView searchAgencyByLicNo(
            @RequestParam(MainetConstants.AdvertisingAndHoarding.AGENCY_LIC_NO) String agencyLicNo,
            HttpServletRequest request) throws Exception {

        this.getModel().bind(request);
        AgencyRegistrationModel model = this.getModel();

        ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_RENEW_VALIDN,
                MainetConstants.FORM_NAME, this.getModel());
        fileUploadService.sessionCleanUpForFileUpload();
        if(StringUtils.isNotBlank(agencyLicNo)) {

        AgencyRegistrationResponseDto agencyRegDto = advertiserMasterService.getAgencyDetailByLicnoAndOrgId(agencyLicNo,
                UserSession.getCurrent().getOrganisation().getOrgid());

        if (agencyRegDto.getMasterDto() != null) {
            LocalDate date = agencyRegDto.getMasterDto().getAgencyLicToDate().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
            LocalDate licEligibleLocalDate = date.minusMonths(3);
            Date licEligibleDate = Date.from(licEligibleLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (StringUtils.equalsIgnoreCase(agencyRegDto.getMasterDto().getAgencyStatus(),
                    MainetConstants.AdvertisingAndHoarding.TRANSIENT_STATUS)) {
                model.addValidationError(
                        getApplicationSession().getMessage("agency.registration.validate.already.applied.service"));
            } else if (Utility.compareDate(licEligibleDate, new Date())
                    || (Utility.comapreDates(licEligibleDate, new Date())
                    && !Utility.comapreDates(agencyRegDto.getMasterDto().getAgencyLicFromDate(), new Date()))) {

                if (Utility.compareDate(agencyRegDto.getMasterDto().getAgencyLicToDate(), new Date())) {
                    this.getModel()
                            .setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext()
                                    .getBean(ADHCommonService.class)
                                    .calculateLicMaxTenureDays(model.getServiceMaster().getTbDepartment().getDpDeptid(),
                                            model.getServiceMaster().getSmServiceId(), null,
                                            UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG)
                                    + 1);
                    this.getModel().setLicMinTenureDays(0l);
                } else {
                    this.getModel().setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext()
                            .getBean(ADHCommonService.class)
                            .calculateLicMaxTenureDays(model.getServiceMaster().getTbDepartment().getDpDeptid(),
                                    model.getServiceMaster().getSmServiceId(), agencyRegDto.getMasterDto().getAgencyLicToDate(),
                                    UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG)
                            - 1);
                    this.getModel()
                            .setLicMinTenureDays(Long.valueOf(Math
                                    .abs(Utility.getDaysBetweenDates(new Date(),
                                            agencyRegDto.getMasterDto().getAgencyLicToDate()))
                                    + 1));
                }
                ZoneId defaultZoneId = ZoneId.systemDefault();
                LocalDate licenseFromDate = agencyRegDto.getMasterDto().getAgencyLicToDate().toInstant().atZone(ZoneId.systemDefault())
                        .toLocalDate().plusDays(1);
                Date FromDate = Date.from(licenseFromDate.atStartOfDay(defaultZoneId).toInstant());
                agencyRegDto.getMasterDto().setAgencyLicFromDate(FromDate);
                this.getModel().getAgencyRequestDto().setMasterDto(agencyRegDto.getMasterDto());
                this.getModel().getAgencyRequestDto().setMasterDtolist(agencyRegDto.getMasterDtolist());

                this.getModel().setFormDisplayFlag(MainetConstants.FlagY);

                this.getModel().setApplicantDetailDto(agencyRegDto.getApplicantDetailDTO());

            } else {
                String licToDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(agencyRegDto.getMasterDto().getAgencyLicToDate());
                String licFromDate = new SimpleDateFormat(MainetConstants.DATE_FORMAT)
                        .format(agencyRegDto.getMasterDto().getAgencyLicFromDate());

                model.addValidationError(getApplicationSession().getMessage("Your license period is " + " "
                        + licFromDate + " " + "to " + licToDate + " "
                        + "You can apply to this service before three months of expiry date. / After completion of one day from license from date"));
            }
        
        } else {
            model.addValidationError(getApplicationSession().getMessage("adh.validate.search"));

        }
        }else {
        	model.addValidationError(getApplicationSession().getMessage("adh.validate.license.number"));
        }
        this.getModel().setViewMode(MainetConstants.FlagV);
    	this.getModel().setOpenMode(MainetConstants.FlagD);
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    @RequestMapping(params = "getChecklistAndCharges", method = { RequestMethod.POST })
    public ModelAndView getCheckListAndCharges(HttpServletRequest request) {

        this.getModel().bind(request);
        AgencyRegistrationModel model = this.getModel();
        model.getAgencyRequestDto().getMasterDto().setApplicationTypeFlag(MainetConstants.FlagR);
        ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_RENEW_VALIDN,
                MainetConstants.FORM_NAME, getModel());
        model.validateBean(model.getAgencyRequestDto().getMasterDto(), AgencyValidatorOwner.class);

        if (!model.hasValidationErrors()) {
            if (StringUtils.equalsIgnoreCase(this.getModel().getCheckListApplFlag(), MainetConstants.FlagY)) {

                final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
                checkListRateRequestModel
                        .setModelName(MainetConstants.AdvertisingAndHoarding.AGENCY_CHECKLIST_RATE_MODEL);
                WSResponseDTO checkListRateResponseModel = brmsCommonService.initializeModel(checkListRateRequestModel);

                if (StringUtils.equalsIgnoreCase(MainetConstants.WebServiceStatus.SUCCESS,
                        checkListRateResponseModel.getWsStatus())) {

                    final List<Object> castCheckListModel = RestClient.castResponse(checkListRateResponseModel,
                            CheckListModel.class, 0);
                    final List<Object> ADHRateMasterList = RestClient.castResponse(checkListRateResponseModel,
                            ADHRateMaster.class, 1);

                    final CheckListModel checkListModel = (CheckListModel) castCheckListModel.get(0);
                    final ADHRateMaster ADHRateMaster = (ADHRateMaster) ADHRateMasterList.get(0);

                    populateChecklistModel(model, checkListModel);
                    List<DocumentDetailsVO> checkListFromBrms = callBrmsForCheckList(model, checkListModel);
                    if (CollectionUtils.isNotEmpty(checkListFromBrms)) {
                        model.setCheckList(checkListFromBrms);

                    } else {
                        model.addValidationError(getApplicationSession().getMessage("adh.checklist.not.found"));
                    }

                    if (StringUtils.equals(model.getApplicationchargeApplFlag(), MainetConstants.FlagY)) {
                        Double applicationCharge = callBrmsForApplicationCharges(model, ADHRateMaster);
                        if (applicationCharge <= 0) {
                            model.addValidationError(getApplicationSession().getMessage("adh.charges.not.found"));
                        }
                    }
                } else {
                    model.addValidationError(
                            getApplicationSession().getMessage("adh.problem.occured.initializing.checklist"));
                }
            }
        }
        mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
        return mv;
    }

    private void populateChecklistModel(AgencyRegistrationModel model, CheckListModel checklistModel) {

        checklistModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        checklistModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
        checklistModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
    }

    private Double callBrmsForApplicationCharges(AgencyRegistrationModel model, ADHRateMaster ADHRateMaster) {
        Double amoutToPay = 0.0;
        final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
        ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
        ADHRateMaster
                .setChargeApplicableAt(
                        Long.toString(CommonMasterUtility
                                .getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
                                        PrefixConstants.LookUpPrefix.CAA, UserSession.getCurrent().getOrganisation())
                                .getLookUpId()));
        adhRateRequestDto.setDataModel(ADHRateMaster);
        WSResponseDTO adhRateResponseDto = BRMSADHService.getApplicableTaxes(adhRateRequestDto);
        if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateResponseDto.getWsStatus())) {
            if (!adhRateResponseDto.isFree()) {
                final List<Object> rates = (List<Object>) adhRateResponseDto.getResponseObj();
                final List<ADHRateMaster> requiredCHarges = new ArrayList<>();
                for (final Object rate : rates) {
                    ADHRateMaster master1 = (ADHRateMaster) rate;
                    master1 = populateChargeModel(model, master1);
                    requiredCHarges.add(master1);
                }
                WSRequestDTO chargeReqDto = new WSRequestDTO();
                chargeReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.ADH_Rate_Master);
                chargeReqDto.setDataModel(requiredCHarges);
                WSResponseDTO applicableCharges = BRMSADHService.getApplicableCharges(chargeReqDto);
                if (StringUtils.equals(applicableCharges.getWsStatus(), MainetConstants.WebServiceStatus.SUCCESS)) {
                    List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
                    model.getAgencyRequestDto().setFree(false);
                    model.setPayableFlag(MainetConstants.FlagY);
                    model.setChargesInfo(detailDTOs);
                    model.setAmountToPay((chargesToPay(detailDTOs)));
                    model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
                    model.setApplicationchargeApplFlag(MainetConstants.FlagN);
                    amoutToPay = model.getAmountToPay();
                }

            } else {
                model.setPayableFlag(MainetConstants.FlagN);
                model.getAgencyRequestDto().setFree(true);
                model.setAmountToPay(0.0d);
            }
        }

        return amoutToPay;
    }

    @SuppressWarnings("unchecked")
    private List<DocumentDetailsVO> callBrmsForCheckList(AgencyRegistrationModel model, CheckListModel checkListModel) {
        List<DocumentDetailsVO> checkList = new ArrayList<>();
        WSRequestDTO checklistReqDto = new WSRequestDTO();
        checklistReqDto.setModelName(MainetConstants.AdvertisingAndHoarding.CHECK_LIST);
        checklistReqDto.setDataModel(checkListModel);
        WSResponseDTO checklistResponse = brmsCommonService.getChecklist(checklistReqDto);

        // checking the response after getting applicable checklist from BRMS is
        // success.(If not then, displaying the validation message on Screen)
        if (StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.WebServiceStatus.SUCCESS)
                || StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.CommonConstants.NA)) {
            this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
            if (!StringUtils.equalsIgnoreCase(checklistResponse.getWsStatus(), MainetConstants.CommonConstants.NA)) {

                List<DocumentDetailsVO> checklistList = Collections.emptyList();
                checklistList = (List<DocumentDetailsVO>) checklistResponse.getResponseObj();
                if (CollectionUtils.isNotEmpty(checklistList)) {
                    long count = 1;
                    for (DocumentDetailsVO doc : checklistList) {
                        doc.setDocumentSerialNo(count);
                        count++;
                    }
                    checkList = checklistList;
                }
            }
        }

        return checkList;
    }

    private ADHRateMaster populateChargeModel(final AgencyRegistrationModel model, final ADHRateMaster chargeModel) {
        chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
        chargeModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE);
        chargeModel.setRateStartDate(new Date().getTime());
        chargeModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
        return chargeModel;
    }

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
        double amountSum = 0.0;
        for (final ChargeDetailDTO charge : charges) {
            amountSum = amountSum + charge.getChargeAmount();
        }
        return amountSum;
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE_AGENCY_REGISTRATION, method = RequestMethod.POST)
    public Map<String, Object> saveAgencyRegistration(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        if (this.getModel().saveForm()) {
            object.put("applicationId", this.getModel().getApmApplicationId());
        } else {
            object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
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
        AgencyRegistrationModel model = this.getModel();
        String docStatus = new String();

        if (CollectionUtils.isNotEmpty(this.getModel().getCheckList())) {
            List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
                    .getBean(IChecklistVerificationService.class)
                    .getAttachDocumentByDocumentStatus(model.getApmApplicationId(), docStatus,
                            UserSession.getCurrent().getOrganisation().getOrgid());
            if (CollectionUtils.isNotEmpty(documentUploaded)) {
                this.getModel().setDocumentList(documentUploaded);
            }
        }

        String applicantName = model.getApplicantDetailDto().getApplicantFirstName() + MainetConstants.WHITE_SPACE;
        applicantName += model.getApplicantDetailDto().getApplicantMiddleName() == null ? MainetConstants.BLANK
                : model.getApplicantDetailDto().getApplicantMiddleName() + MainetConstants.WHITE_SPACE;
        applicantName += model.getApplicantDetailDto().getApplicantLastName();
        this.getModel().setApplicationId(model.getAgencyRequestDto().getMasterDto().getApmApplicationId());
        this.getModel().setApplicantName(applicantName);
        this.getModel().setServiceName(model.getServiceMaster().getSmServiceName());
        return new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_ACKNOWLEDGEMENT,
                MainetConstants.FORM_NAME, this.getModel());

    }
    
    //Defect  126626
    
    /**
	 * To get Ownership table
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param ownershipType
	 * @return
	 */
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_RENEWAL_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getRenewalOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.AdvertisingAndHoarding.RENEWAL_OWNERSHIP_TYPE) String RenewalownershipType) {
		this.getModel().bind(httpServletRequest);

		AgencyRegistrationModel model = this.getModel();
		model.setOwnershipPrefix(RenewalownershipType);
		
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
	   
		
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME, model);
	}

	
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
	public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
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

	}
	
	
	
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		//List<DocumentDetailsVO> attachments = new ArrayList<>();
		List<AdvertiserMasterDto> owner = new ArrayList<>();
		this.getModel().getAdvertiserMasterDto().setAdvertiserMasterDtoList(owner);
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.RENEWAL_OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
				this.getModel());
	}
	
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_LICENCE_TYPE, method = RequestMethod.POST)
	public @ResponseBody Long getLicMaxTenureDays(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());

		Long calculateLicMaxTenureDays = ApplicationContextProvider.getApplicationContext()
				.getBean(ADHCommonService.class).calculateLicMaxTenureDays(
						serviceMaster.getTbDepartment().getDpDeptid(), serviceMaster.getSmServiceId(), null,
						UserSession.getCurrent().getOrganisation().getOrgid(), licType);
		this.getModel().setLicMaxTenureDays(calculateLicMaxTenureDays);
		return calculateLicMaxTenureDays;
	}

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CALCULATE_YEAR_TYPE, method = RequestMethod.POST)
	public @ResponseBody String gtCalculateYearTpe(@RequestParam("licType") Long licType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_REN_SHORT_CODE,
						UserSession.getCurrent().getOrganisation().getOrgid());
		List<LicenseValidityMasterDto> licValMasterDtoList = licenseValidityMasterService.searchLicenseValidityData(
				UserSession.getCurrent().getOrganisation().getOrgid(), serviceMaster.getTbDepartment().getDpDeptid(),
				serviceMaster.getSmServiceId(), MainetConstants.ZERO_LONG, licType);
		LookUp dependsOnLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				licValMasterDtoList.get(0).getLicDependsOn(), UserSession.getCurrent().getOrganisation());
		String YearType = dependsOnLookUp.getLookUpCode();
		return YearType;
	}

}
