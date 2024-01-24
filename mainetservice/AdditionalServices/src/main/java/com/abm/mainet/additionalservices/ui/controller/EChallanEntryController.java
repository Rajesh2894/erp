/**
 * 
 */
package com.abm.mainet.additionalservices.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.additionalservices.datamodel.AdditionalServicesModel;
import com.abm.mainet.additionalservices.dto.EChallanItemDetailsDto;
import com.abm.mainet.additionalservices.dto.EChallanMasterDto;
import com.abm.mainet.additionalservices.service.EChallanEntryService;
import com.abm.mainet.additionalservices.ui.model.EChallanEntryModel;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.ui.validator.FileUploadServiceValidator;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/EChallanEntry.html")
public class EChallanEntryController extends AbstractFormController<EChallanEntryModel>{
	
	@Autowired
	private EChallanEntryService challanService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
    private BRMSCommonService brmsCommonService;
	
	@Autowired
	private com.abm.mainet.additionalservices.service.BRMSAdditionalService BRMSAdditionalService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index( HttpServletRequest request) {
		sessionCleanup(request);
		return index();
	}
	
	@RequestMapping(params = "AddForm", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addNewChallan(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		EChallanEntryModel model = this.getModel();
		model.getChallanMasterDto().setChallanType("OS");
		
		EChallanItemDetailsDto itemdto = new EChallanItemDetailsDto();
		
		return new ModelAndView("ChallanDetailsForm", MainetConstants.FORM_NAME, model);
	}
	
	@ResponseBody
    @RequestMapping(params = "searchChallanDetails", method = RequestMethod.POST)
    public ModelAndView searchChallanDetails(final HttpServletRequest request,
            @RequestParam(required = false) String challanNo,
            @RequestParam(required = false) String raidNo,
            @RequestParam(required = false) String offenderName,
            @RequestParam(required = false) Date challanFromDate,
            @RequestParam(required = false) Date challanToDate,
            @RequestParam(required = false) String offenderMobNo) {     
		sessionCleanup(request);
		getModel().bind(request);
		ModelAndView mv = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		String challanType = MainetConstants.EncroachmentChallan.ON_SPOT_CHALLAN;
		List<EChallanMasterDto> searchList=challanService.searchChallanDetailsList(challanNo, raidNo, 
				offenderName, challanFromDate, challanToDate, offenderMobNo, challanType, orgid);
		this.getModel().getChallanMasterDto().setChallanNo(challanNo);
		this.getModel().getChallanMasterDto().setChallanFromDate(challanFromDate);
		this.getModel().getChallanMasterDto().setChallanToDate(challanToDate);
		this.getModel().getChallanMasterDto().setOffenderMobNo(offenderMobNo);
		this.getModel().getChallanMasterDto().setOffenderName(offenderName);
		if(searchList.isEmpty()) {
			mv = new ModelAndView("EChallanEntrySearch", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("EChallan.noRecordFound"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}else {			
			this.getModel().setChallanMasterDtoList(searchList);
			mv = new ModelAndView("EChallanEntrySearch", MainetConstants.FORM_NAME, this.getModel());
		}
		return mv;
	}
	
	@RequestMapping(params = "printAcknowledgement", method = {RequestMethod.POST })
    public ModelAndView printAcknowledgement(HttpServletRequest request,
    		 @RequestParam(required = false) String offenderName) {
        bindModel(request);
        final EChallanEntryModel model = this.getModel();
        ModelAndView mv = null;
        
        // runtime print acknowledge or certificate
        String viewName = "printChallanAcknowledgement";
           
	       mv = new ModelAndView(viewName, MainetConstants.FORM_NAME, getModel());
        return mv;
	}
	
	@RequestMapping(params = "ViewForm", method = {RequestMethod.POST })
	public ModelAndView ViewForm(@RequestParam("challanId") String challanId, 
								 @RequestParam("saveMode") String saveMode, HttpServletRequest request){
		this.getModel().setSaveMode(saveMode);
		EChallanMasterDto masterDto = challanService
				.getEChallanMasterByOrgidAndChallanId(UserSession.getCurrent().getOrganisation().getOrgid(), Long.parseLong(challanId));
		if (masterDto != null) {
		    this.getModel().setChallanMasterDto(masterDto);
		}
		
		// fetch uploaded document
		List<CFCAttachment> checklist = new ArrayList<>();
		checklist = iChecklistVerificationService.getDocumentUploadedByRefNo(
		this.getModel().getChallanMasterDto().getChallanNo(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setDocumentList(checklist);
		
		return new ModelAndView(MainetConstants.EncroachmentChallan.CHALLAN_DETAILS_FORM, MainetConstants.FORM_NAME,
				this.getModel());
    }
	
	@RequestMapping(params = "receiptDownload", method = { RequestMethod.POST })
	public ModelAndView receiptDownload(@RequestParam("challanId") Long challanId,
			final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ChallanReceiptPrintDTO receiptDto = challanService.getDuplicateReceiptDetail(challanId, orgId);
		getModel().setReceiptDTO(receiptDto);
		if (receiptDto != null) {
			return new ModelAndView("ChallanAtULBReceiptPrint", MainetConstants.FORM_NAME, getModel());
		}
		return null;
	}
	
	@RequestMapping(params = "backToMainSearch", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		sessionCleanup(httpServletRequest);
        return new ModelAndView("EChallanEntrySearch", MainetConstants.FORM_NAME, this.getModel());
    }
	
	@ResponseBody
	@RequestMapping(params = "getChargesForItem", method = RequestMethod.POST)
	public Double getChargesForItem(@RequestParam("itemName") String itemName, @RequestParam("itemQuantity") Long itemQuantity, HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		EChallanEntryModel model = this.getModel();
		final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
		checkListRateRequestModel.setModelName("AdditionalServicesModel");
		WSResponseDTO checkListRateResponseModel = brmsCommonService.initializeModel(checkListRateRequestModel);
		 final List<Object> ADHRateMasterList = RestClient.castResponse(checkListRateResponseModel,
				 AdditionalServicesModel.class, 0);
		final AdditionalServicesModel ADHRateMaster = (AdditionalServicesModel) ADHRateMasterList.get(0);
		ADHRateMaster.setItemName(itemName);
		ADHRateMaster.setNoOfCopies(itemQuantity);
		Double charges = callBrmsForApplicationCharges(model, ADHRateMaster);
		return charges;
	}
	
	private Double callBrmsForApplicationCharges(EChallanEntryModel model, AdditionalServicesModel ADHRateMaster) {
		Double amoutToPay = 0.0;
		final WSRequestDTO adhRateRequestDto = new WSRequestDTO();
		ADHRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		ADHRateMaster.setServiceCode(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE);
		ADHRateMaster
			.setChargeApplicableAt(
				Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
						PrefixConstants.LookUpPrefix.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
		adhRateRequestDto.setDataModel(ADHRateMaster);
		WSResponseDTO adhRateResponseDto = BRMSAdditionalService.getApplicableTaxes(adhRateRequestDto);
		if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(adhRateResponseDto.getWsStatus())) {
		    if (!adhRateResponseDto.isFree()) {
			final List<Object> rates = (List<Object>) adhRateResponseDto.getResponseObj();
			final List<AdditionalServicesModel> requiredCHarges = new ArrayList<>();
			for (final Object rate : rates) {
				AdditionalServicesModel master1 = (AdditionalServicesModel) rate;
			    master1 = populateChargeModel(model, master1);
			    requiredCHarges.add(master1);
			}
			WSRequestDTO chargeReqDto = new WSRequestDTO();
			chargeReqDto.setModelName("AdditionalServicesModel");
			chargeReqDto.setDataModel(requiredCHarges);
			WSResponseDTO applicableCharges = BRMSAdditionalService.getApplicableCharges(chargeReqDto);
			if (StringUtils.equalsIgnoreCase(applicableCharges.getWsStatus(),
				MainetConstants.WebServiceStatus.SUCCESS)) {
			    List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
			    model.setChargesInfo(detailDTOs);
			    model.setAmountToPay((chargesToPay(detailDTOs)));
			    model.getOfflineDTO().setAmountToShow(model.getAmountToPay());
			    //model.setApplicationchargeApplFlag(MainetConstants.FlagN);
			    amoutToPay = model.getAmountToPay();
			}

		    } else {
			//model.setPayableFlag(MainetConstants.FlagN);
			//model.getAgencyRequestDto().setFree(true);
			model.setAmountToPay(0.0d);
		    }
		}

		return amoutToPay;
	    }

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}
	
	private AdditionalServicesModel populateChargeModel(final EChallanEntryModel model, final AdditionalServicesModel chargeModel) {
		chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		chargeModel.setServiceCode(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE);
		//chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode("ENC");
		return chargeModel;
	    }
	
	@RequestMapping(params = "resetFormWithChallanType", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView resetFormWithChallanType(@Param("challanType") String challanType,final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		EChallanEntryModel model = this.getModel();
		model.getChallanMasterDto().setChallanType(challanType);
		
		EChallanItemDetailsDto itemdto = new EChallanItemDetailsDto();
		
		return new ModelAndView("ChallanDetailsForm", MainetConstants.FORM_NAME, model);
	}
	
}
