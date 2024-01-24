package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeCategorySubCategoryService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.ChangeInCategorySubcategoryFormModel;

@Controller
@RequestMapping("/ChangeInCategorySubcategoryForm.html")
public class ChangeInCategorySubcategoryFormController
		extends AbstractFormController<ChangeInCategorySubcategoryFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IChangeCategorySubCategoryService changeCategorySubCategoryService;
	
	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			getModel().setOtpBtnShowFlag(MainetConstants.FLAGN);
			getModel().setLicenseBtnFlag(MainetConstants.TradeLicense.FlagY);
			getModel().setEnvFlag(MainetConstants.TradeLicense.FlagY);
			}
			else {
			getModel().setOtpBtnShowFlag(MainetConstants.TradeLicense.FlagY);
			getModel().setLicenseBtnFlag(MainetConstants.FLAGN);
			getModel().setEnvFlag(MainetConstants.FLAGN);
			}
		return new ModelAndView("ChangeInCategorySubcategoryForm", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_OTP_BTN_SHOW)
	public ModelAndView getOtpBtn(final HttpServletRequest request, @RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(request);
		ChangeInCategorySubcategoryFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, tradeDTO.getOrgid());
		model.setTradeMasterDetailDTO(tradeDTO);
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));

		} else {
			model.addValidationError(getApplicationSession().getMessage("trade.ValidateLicenseNo"));
		}
		model.setOtpBtnShowFlag("N");
		model.setLicenseBtnFlag(MainetConstants.TradeLicense.FlagY);
		return defaultMyResult();
	}
	
	/**
	 * Generate OTP Method
	 * @param request
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_OTP, method = RequestMethod.POST)
	@ResponseBody
	public TradeMasterDetailDTO generateOtp(final HttpServletRequest request) {

		getModel().bind(request);
		final ChangeInCategorySubcategoryFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = getModel().getTradeMasterDetailDTO();
		final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		model.setOtp(otp);

		SMSAndEmailDTO emailDto = new SMSAndEmailDTO();
		if (tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno() != null && !tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().isEmpty()) {
			emailDto.setMobnumber(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		}
		if (tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid() != null && !tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid().isEmpty()) {
			emailDto.setEmail(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		}
		emailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		emailDto.setCurrentDate(Utility.dateToString(new Date()));
		emailDto.setOneTimePassword(otp);
		emailDto.setAppName(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName());

		 iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "ChangeInCategorySubcategoryForm.html",
				MainetConstants.SMS_EMAIL.OTP_MSG, emailDto, UserSession.getCurrent().getOrganisation(),
				UserSession.getCurrent().getLanguageId());
		
		model.setTradeMasterDetailDTO(tradeDTO);
		return model.getTradeMasterDetailDTO();
	}


	/**
	 * Get License Details By License Number
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		model.setNewEntry(MainetConstants.FlagN);
		model.setShowHideFlag(MainetConstants.FlagN);
		model.setEditAppFlag(MainetConstants.FlagY);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(tradeDTO.getTrdLicno(),
				tradeDTO.getOrgid());
		// code added for check is this license rejected in any of the service flow
				Boolean flagCheck = false;
				if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
					// D#125111
					flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(tradeDTO.getApmApplicationId(), orgId);
				}
				model.setTradeDTO(tradeDTO);
				// to check license cancelled or in transit
				LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS");
				LookUp lookUpCancel = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS");
				boolean flag = false;
				if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
					if ((lookUp != null && lookUp.getLookUpId() != 0) && !flagCheck) {
						if (lookUpCancel != null && (tradeDTO.getTrdStatus().equals(lookUpCancel.getLookUpId()))) {
							model.addValidationError(getApplicationSession().getMessage("trade.licNo.cancel"));
							flag = true;
						} else if (!tradeDTO.getTrdStatus().equals(lookUp.getLookUpId())) {
							model.addValidationError(getApplicationSession().getMessage("renewal.valid.licenseStatus")+" "+tradeDTO.getApmApplicationId());
							flag = true;
						}
						if (flag) {
							this.getModel().setViewMode(MainetConstants.MENU.B);
							return defaultMyResult();
						}

					}
				}
		if (model.getOtp() != null && model.getOtp().equals(model.getUserOtp()) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			
		//model.setTradeMasterDetailDTO(tradeDTO);
		List<TradeLicenseItemDetailDTO> detList = tradeDTO.getTradeLicenseItemDetailDTO().stream()
		                .filter(s -> s.getTriStatus().equals("A") || s.getTriStatus().equals("Y")).collect(Collectors.toList());
		   tradeDTO.setTradeLicenseItemDetailDTO(detList);
		    model.setTradeDTO(tradeDTO);
			if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
				model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
				model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
				model.setLicenseDetails(MainetConstants.TradeLicense.FlagY);
				model.setChecklistCheck(MainetConstants.TradeLicense.FlagY);
				model.setLicenseBtnFlag("N");
				if (model.getTradeDTO() != null && !model.getTradeDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
					StringBuilder ownName = new StringBuilder();
					String ownerName = null;
					for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeDTO().getTradeLicenseOwnerdetailDTO()) {
						if (StringUtils.isNotBlank(ownDto.getTroName()))
							ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
					if (StringUtils.isNotBlank(ownerName)) {
						model.setOwnerName(ownerName);
					}
				}

			}
		} else {
			model.addValidationError(getApplicationSession().getMessage("trade.ValidateLicenseNo"));
		}
		return defaultMyResult();

	}
	
	/**
	 * Edit Category on Application
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
    public ModelAndView editChangeCategoryAppln(final HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        ChangeInCategorySubcategoryFormModel model = getModel();
        TradeMasterDetailDTO tradeDetailDto = model.getTradeDTO();
        TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();
        List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
        BeanUtils.copyProperties(tradeDetailDto, tradeDTO);
        tradeDetailDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
            TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
            BeanUtils.copyProperties(itemdDetails, itemDto);
            itemDto.setTriCategory1(itemdDetails.getTriCod1());
			itemDto.setTriCategory2(itemdDetails.getTriCod2());
			itemDto.setTriCategory3(itemdDetails.getTriCod3());
			itemDto.setTriCategory4(itemdDetails.getTriCod4());
			itemDto.setTriCategory5(itemdDetails.getTriCod5());
            itemdDetailsList.add(itemDto);
        });

        tradeDTO.setTradeLicenseItemDetailDTO(itemdDetailsList);
      //  model.setTradeDTO(tradeDTO);
    	model.setTradeMasterDetailDTO(tradeDTO);
    	model.setShowHideFlag(MainetConstants.FlagY);
        return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_VATEGORY_SUBCATEGORY_EDIT,
                MainetConstants.FORM_NAME, this.getModel());

    }
	
	@RequestMapping(params = "saveCategorySubCategoryForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
	}
	

	/**
	 * Get Checklist and application Chaarges from BRMS Rule
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView getChecklistCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		ChangeInCategorySubcategoryFormModel model = getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		model.setNewEntry(MainetConstants.FlagY);
		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "CCS");
		if (map.get("checkListApplFlag") != null && map.get("checkListApplFlag").equals("Y")) {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
			model.getCheckListFromBrms();
		} else {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		if (map.get("applicationchargeApplFlag") != null && map.get("applicationchargeApplFlag").equals("Y")) {
			model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		model.setTradeMasterDetailDTO(changeCategorySubCategoryService.getChangeCategoryChargesFromBrmsRule(masDto));
		}else {
			model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		}
		if (model.getTradeMasterDetailDTO().getTotalApplicationFee() !=null ) {
		model.getOfflineDTO().setAmountToShow((model.getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
		model.getOfflineDTO().setAmountToPay(model.getTradeMasterDetailDTO().getTotalApplicationFee().toString());
         }
		model.setPaymentCheck(MainetConstants.TradeLicense.FlagY);
		model.setChecklistCheck("N");
		model.setEditAppFlag(MainetConstants.FlagN);
		return defaultResult();

	}
	
	
	/**
	 * Generate challan and payment and save application
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = "generateChallanAndPayement", method = RequestMethod.POST)
    public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        getModel().bind(httpServletRequest);
        ChangeInCategorySubcategoryFormModel model = this.getModel();
        ModelAndView mv = null;
        if (model.validateInputs())
		{
        if (model.saveForm()) {
            return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

        } else {
            return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        }
    }
        mv = new ModelAndView("ChangeInCategorySubcategoryFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
 }
	
	
	@RequestMapping(params = "printAgencyRegAckw", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long serviceId = iPortalServiceMasterService.getServiceId("CCS", orgId);
		if (serviceId != null) {

			PortalService serviseMast = iPortalServiceMasterService.getService(serviceId,orgId);
			model.setServiceMaster(serviseMast);
		}
		if (!tradeDTO.getTradeLicenseOwnerdetailDTO().isEmpty()) {
			StringBuilder ownName = new StringBuilder();
			String ownerName = null;
			for (TradeLicenseOwnerDetailDTO ownDto : tradeDTO.getTradeLicenseOwnerdetailDTO()) {
				if (ownDto.getTroName() != null && !ownDto.getTroName().isEmpty())
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (!StringUtils.isEmpty(ownName.toString())) {
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
			if (ownerName != null && !ownerName.isEmpty()) {
				model.setOwnerName(ownerName);
			}
		}
        //#144362
		if (model != null && model.getOwnerName() != null)
			model.setApplicantName(model.getOwnerName());
		if (MainetConstants.DEFAULT_LANGUAGE_ID == UserSession.getCurrent().getLanguageId()) {
			if (model.getServiceMaster()  !=null && model.getServiceMaster() .getServiceName() != null) 
			model.setServiceName(model.getServiceMaster() .getServiceName());
			model.setDepartmentName(model.getServiceMaster().getPsmDpDeptDesc());
		    }
		else {
			if(model.getServiceMaster()!= null && model.getServiceMaster().getServiceNameReg() !=null)
			model.setServiceName(model.getServiceMaster() .getServiceNameReg());
			model.setDepartmentName(model.getServiceMaster().getPsmDpNameMar());
		}
			
			model.setAppDate(new Date());
			model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
			LinkedHashMap<String, Object> map = tradeLicenseApplicationService
					.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "DTL");
			if (map.get("smServiceDuration") != null)
				model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(),Long.valueOf((String) map.get("smServiceDuration")).intValue()));
			if (CollectionUtils.isNotEmpty(model.getCheckList()))
			model.setCheckList(model.getCheckList());

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL))
			return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		else
		return new ModelAndView("TradeLicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}
}
