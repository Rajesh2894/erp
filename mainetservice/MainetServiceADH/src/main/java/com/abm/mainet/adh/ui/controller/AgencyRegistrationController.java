package com.abm.mainet.adh.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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
import com.abm.mainet.adh.service.ADHCommonService;
import com.abm.mainet.adh.service.IBRMSADHService;
import com.abm.mainet.adh.ui.model.AgencyRegistrationModel;
import com.abm.mainet.adh.ui.validator.AdverstiserMasterValidator;
import com.abm.mainet.adh.ui.validator.AgencyValidatorOwner;
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



/**
 * @author cherupelli.srikanth
 * @since 08 august 2019
 */
@Controller
@RequestMapping("/AgencyRegistration.html")
public class AgencyRegistrationController extends AbstractFormController<AgencyRegistrationModel> {

    @Resource
    IFileUploadService fileUpload;

    @Autowired
    private BRMSCommonService brmsCommonService;

    @Autowired
    private ServiceMasterService serviceMasterService;

    @Autowired
    IBRMSADHService BRMSADHService;

    /**
     * This method is used to load index page of Agency Registration.
     * 
     * @param request
     * @return Agency Registration Form
     * @throws Exception
     */
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) throws Exception {
	sessionCleanup(request);
	fileUpload.sessionCleanUpForFileUpload();
	ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
		MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE,
		UserSession.getCurrent().getOrganisation().getOrgid());

	this.getModel()
		.setLicMaxTenureDays(ApplicationContextProvider.getApplicationContext().getBean(ADHCommonService.class)
			.calculateLicMaxTenureDays(serviceMaster.getTbDepartment().getDpDeptid(),
				serviceMaster.getSmServiceId(), null,
				UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.ZERO_LONG));

	this.getModel().setAgenctCategoryLookUp(CommonMasterUtility.getValueFromPrefixLookUp(
		PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.AGN,
		PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.ADC, UserSession.getCurrent().getOrganisation()));

	this.getModel().setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	this.getModel().getAgencyRequestDto().getMasterDto().setApplicationTypeFlag(MainetConstants.FlagN);
	this.getModel().getAgencyRequestDto().getMasterDto()
		.setAgencyCategory(this.getModel().getAgenctCategoryLookUp().getLookUpId());
	this.getModel().setServiceMaster(serviceMaster);
	// Checking condition whether checkList applicable for particular Service.
	if (StringUtils
		.equalsIgnoreCase(CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMaster.getSmChklstVerify(),
			UserSession.getCurrent().getOrganisation()).getLookUpCode(), MainetConstants.FlagA)) {
	    this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
	} else {
	    this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
	}
	if (StringUtils.equalsIgnoreCase(serviceMaster.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
	    this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagY);
	    this.getModel().getAgencyRequestDto().setFree(false);
	} else {
	    this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagN);
	    this.getModel().getAgencyRequestDto().setFree(true);
	}
	
	return index();
    }

    /**
     * This method is used to get documents and charges which are applicable for
     * Agency registration
     * 
     * @param request
     * @return Agency Registration Form with applicable checkList and charges
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = MainetConstants.AdvertisingAndHoarding.GET_CHECKLIST_CHARGES, method = {
	    RequestMethod.POST })
    public ModelAndView getChecklistAndCharges(final HttpServletRequest request) {

	this.getModel().bind(request);
	AgencyRegistrationModel model = this.getModel();

	model.validateBean(model.getAgencyRequestDto().getMasterDto(), AgencyValidatorOwner.class);

	ModelAndView mv = new ModelAndView(MainetConstants.AdvertisingAndHoarding.AGENCY_REGISTRATION_VALIDN,
		MainetConstants.FORM_NAME, getModel());

	if (!model.hasValidationErrors()) {
	    if (StringUtils.equalsIgnoreCase(this.getModel().getCheckListApplFlag(), MainetConstants.FlagY)) {

		final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
		checkListRateRequestModel.setModelName(MainetConstants.AdvertisingAndHoarding.CHECKLIST_ADHRATEMASTER);
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
	this.getModel().setViewMode(MainetConstants.FlagV);
	this.getModel().setOpenMode(MainetConstants.FlagD);
	mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
	return mv;
    }

    /**
     * This method is used to set required fields in checkListModel to call BRMS
     * Sheet
     * 
     * @param model
     * @param checklistModel
     */
    private void populateChecklistModel(AgencyRegistrationModel model, CheckListModel checklistModel) {

	checklistModel.setOrgId(this.getModel().getOrgId());
	checklistModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
	checklistModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
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

	if (CollectionUtils.isNotEmpty(this.getModel().getCheckList())) {
	    List<CFCAttachment> downloadDocs = new ArrayList<>();
	    List<CFCAttachment> preparePreviewOfFileUpload = model.preparePreviewOfFileUpload(downloadDocs,
		    this.getModel().getCheckList());

	    if (CollectionUtils.isNotEmpty(preparePreviewOfFileUpload)) {
		this.getModel().setDocumentList(preparePreviewOfFileUpload);
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

    private double chargesToPay(final List<ChargeDetailDTO> charges) {
	double amountSum = 0.0;
	for (final ChargeDetailDTO charge : charges) {
	    amountSum = amountSum + charge.getChargeAmount();
	}
	return amountSum;
    }

    private Double callBrmsForApplicationCharges(AgencyRegistrationModel model, ADHRateMaster ADHRateMaster) {
	Double amoutToPay = 0.0;
	final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
	ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
	ADHRateMaster.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
	 LookUp lookUp = CommonMasterUtility.lookUpByLookUpIdAndPrefix( model.getAgencyRequestDto().getMasterDto().getAgencyCategory(), "AGC", UserSession.getCurrent().getOrganisation().getOrgid());
   
	ADHRateMaster.setAdvertiserCategory(lookUp.getLookUpDesc());
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
		if (StringUtils.equalsIgnoreCase(applicableCharges.getWsStatus(),
			MainetConstants.WebServiceStatus.SUCCESS)) {
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
	chargeModel.setOrgId(model.getOrgId());
	chargeModel.setServiceCode(MainetConstants.AdvertisingAndHoarding.AGENCY_REG_SHORT_CODE);
	chargeModel.setRateStartDate(new Date().getTime());
	chargeModel.setDeptCode(MainetConstants.AdvertisingAndHoarding.ADH_SHORT_CODE);
	return chargeModel;
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

		AgencyRegistrationModel model = this.getModel();
		model.setOwnershipPrefix(ownershipType);
		//Defect #125016 
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
	    /*LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp(ownershipType, MainetConstants.AdvertisingAndHoarding.AOT,
				                                 UserSession.getCurrent().getOrganisation());
	    model.getAgencyRequestDto().getMasterDtolist().get(0).setTrdFtype(lookup.getLookUpId());*/
	    /*if (!model.getAgencyRequestDto().getMasterDtolist().isEmpty()
                && !ownershipType.equals("A")) {
            AdvertiserMasterDto ownerDto =  model.getAgencyRequestDto().getMasterDtolist().get(0);
            model.getAgencyRequestDto().getMasterDtolist().clear();
            model.getAgencyRequestDto().getMasterDtolist().add(ownerDto);
        }*/
	
		
		
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
		/*for (int i = 0; i <= this.getModel().getLength(); i++) {
			attachments.add(new DocumentDetailsVO());
			owner.add(new TradeLicenseOwnerDetailDTO());
		}*/
		/*int count = 0;
		for (AdvertiserMasterDto ownerDto : this.getModel().getAdvertiserMasterDto()
				.getAdvertiserMasterDtoList()) {
			BeanUtils.copyProperties(ownerDto, owner.get(count));
			count++;
		}*/
		//this.getModel().getTradeMasterDetailDTO().setAttachments(attachments);
		this.getModel().getAdvertiserMasterDto().setAdvertiserMasterDtoList(owner);
		return new ModelAndView(MainetConstants.AdvertisingAndHoarding.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
				this.getModel());
	}
	

}
