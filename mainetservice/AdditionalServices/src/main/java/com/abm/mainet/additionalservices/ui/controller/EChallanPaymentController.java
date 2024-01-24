/**
 * 
 */
package com.abm.mainet.additionalservices.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.abm.mainet.additionalservices.service.BRMSAdditionalService;
import com.abm.mainet.additionalservices.service.EChallanEntryService;
import com.abm.mainet.additionalservices.ui.model.EChallanEntryModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.google.common.util.concurrent.AtomicDouble;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/EChallanPayment.html")
public class EChallanPaymentController extends AbstractFormController<EChallanEntryModel>{
	
	@Autowired
	private EChallanEntryService challanService;
	
	@Autowired
	private ServiceMasterService serviceMaster;
	@Autowired
    private BRMSCommonService brmsCommonService;
	
	@Autowired
	private com.abm.mainet.additionalservices.service.BRMSAdditionalService BRMSAdditionalService;
	
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView index( HttpServletRequest request) {
		sessionCleanup(request);
		this.getModel().setFormType("PAY");
		
		ServiceMaster serviceDto = serviceMaster
				.getServiceByShortName(MainetConstants.EncroachmentChallan.CHALLAN_SHORT_CODE, 
						UserSession.getCurrent().getOrganisation().getOrgid());
		if (serviceDto != null) {
			this.getModel().setServiceMaster(serviceDto);
		}
		this.getModel().setServiceId(serviceDto.getSmServiceId());
		this.getModel().setServiceName(serviceDto.getSmServiceName());
		return index();
	}

	@ResponseBody
    @RequestMapping(params = "searchRaidDetails", method = RequestMethod.POST)
	public ModelAndView searchRaidDetails(final HttpServletRequest request,
            @RequestParam(required = false) String raidNo,
            @RequestParam(required = false) String offenderMobNo) {
		getModel().bind(request);
		ModelAndView mv = null;
		Date challanFromDate = null;
		Date challanToDate = null;
		String offenderName = null;
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		
		String challanType = MainetConstants.EncroachmentChallan.AGAINST_SEIZED_ITEMS;
		List<EChallanMasterDto> searchList=challanService.searchRaidDetailsList(raidNo, offenderName, 
				challanFromDate, challanToDate, offenderMobNo, challanType, orgid);
		
		if(searchList.isEmpty()) {
			mv =  new ModelAndView("EChallanPaymentSearch", MainetConstants.FORM_NAME, this.getModel());
			this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("EChallan.noRecordFound"));
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, this.getModel().getBindingResult());
		}else {			
			this.getModel().setChallanMasterDtoList(searchList);
			mv =  new ModelAndView("EChallanPaymentSearch", MainetConstants.FORM_NAME, this.getModel());
		}
		return mv;
	}
	
	@RequestMapping(params = "ViewForm", method = {RequestMethod.POST })
	public ModelAndView ViewForm(@RequestParam("challanId") String challanId, 
								 @RequestParam("saveMode") String saveMode, HttpServletRequest request){
		this.getModel().setSaveMode(saveMode);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		EChallanMasterDto masterDto = challanService
				.getEChallanMasterByOrgidAndChallanId(orgid, Long.parseLong(challanId));
		if (masterDto != null) {
			AtomicDouble totalAmnt = new AtomicDouble(0);
			masterDto.getEchallanItemDetDto().forEach(itemDto ->{
				Double itemAmnt = getChargesForItem(itemDto);
				itemDto.setItemAmount(itemAmnt);
				totalAmnt.addAndGet(itemAmnt);
			});
			masterDto.setChallanAmt(totalAmnt.doubleValue());
		    this.getModel().setChallanMasterDto(masterDto);
		}
		return new ModelAndView("EChallanPaymentValidn", MainetConstants.FORM_NAME, this.getModel());
	}
	
	public Double getChargesForItem(EChallanItemDetailsDto itemDto) {
		EChallanEntryModel model = this.getModel();
		final WSRequestDTO checkListRateRequestModel = new WSRequestDTO();
		checkListRateRequestModel.setModelName("AdditionalServicesModel");
		WSResponseDTO checkListRateResponseModel = brmsCommonService.initializeModel(checkListRateRequestModel);
		 final List<Object> ADHRateMasterList = RestClient.castResponse(checkListRateResponseModel,
				 AdditionalServicesModel.class, 0);
		final AdditionalServicesModel ADHRateMaster = (AdditionalServicesModel) ADHRateMasterList.get(0);
		ADHRateMaster.setItemName(itemDto.getItemName());
		ADHRateMaster.setNoOfCopies(itemDto.getItemQuantity());
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
}
