package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicenseFormModel;
import com.abm.mainet.tradeLicense.ui.model.TradeApplicationFormModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_HTML)
public class RenewalLicenseFormController extends AbstractFormController<RenewalLicenseFormModel> {

	@Autowired
	private IRenewalLicenseApplicationService renewalLicenseApplicationService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private IOrganisationService organisatonService;
	private static final Logger LOGGER = Logger.getLogger(RenewalLicenseFormController.class);
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		sessionCleanup(httpServletRequest);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		// For setting help document
		getModel().setCommonHelpDocs("RenewalLicenseForm.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
       //#140263
		UserSession userSession = UserSession.getCurrent();
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {
		TradeMasterDetailDTO dto = new TradeMasterDetailDTO();
		dto.setOrgId(orgId);
		dto.setEmpId(userSession.getEmployee().getEmpId());
		dto.setMobileNo(userSession.getEmployee().getEmpmobno());
		dto.setSource(MainetConstants.MENU.P);
		List<TradeMasterDetailDTO> dtoList = tradeLicenseApplicationService.getLicenseDetails(dto);
		if (CollectionUtils.isNotEmpty(dtoList))
			this.getModel().setTradeMasterDtoList(dtoList);
		this.getModel().setSudaEnv(MainetConstants.FlagY);
		}else {
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		}
		final Long serviceId = iPortalServiceMasterService
				.getServiceId(MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE, orgId);
		final PortalService service = iPortalServiceMasterService.getService(serviceId, orgId);
		
		ModelAndView mv =new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM, MainetConstants.FORM_NAME,
				getModel());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
		String authentication="";
		
		if(null!=str && null!=ns && null!=ULBID && null!=ULBDistrict){
			EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
			LOGGER.info("Encrypted Key: " + str);
			authentication=encryptDecrypt.authentication(str,ns);	
			
			if(!authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)){
				this.getModel().setTenant(authentication);
			}
		}
		
		
		Employee emp = UserSession.getCurrent().getEmployee();
		if(emp.getEmploginname().equalsIgnoreCase("NOUSER") && !authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)&& StringUtils.isNotEmpty(authentication)) {
			mv= new ModelAndView("RenewalLicenseForm", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("RenewalLicenseForm", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}}
		return mv;

	}

	/**
	 * Get License details From License Number
	 * 
	 * @param httpServletRequest
	 * @param trdLicno
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		final RenewalLicenseFormModel model = getModel();
		ModelAndView mv = null;
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		tradeDTO.setLangId((long) UserSession.getCurrent().getLanguageId());
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, tradeDTO.getOrgid());
		// code added for check is this license rejected in any of the service flow
		tradeDTO.setTenant(model.getTenant());
		Boolean flagCheck = false;
		boolean flag = false;
		Boolean isExist = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(tradeDTO.getApmApplicationId(), orgId);
			//#129518 to check license is present under objection process
            isExist  = renewalLicenseApplicationService.checkLicenseNoExist(trdLicno, orgId);
		}
		
		if (isExist) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("valid.obj.licNo"));
			flag = true;
		}
	    if (tradeDTO != null && tradeDTO.getTrdLictype() != null ) {
		String lookUp = CommonMasterUtility.findLookUpCode("LIT", UserSession.getCurrent().getOrganisation().getOrgid(),
				tradeDTO.getTrdLictype());
		}
		LookUp lookUpIsue = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS");
		LookUp lookUpCancel = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS");
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setTradeMasterDetailDTO(tradeDTO);
			// US#140275-outstanding amount check against property for suda
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA)) {
				TradeMasterDetailDTO dto = tradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(tradeDTO);
				if (dto != null && dto.getTotalOutsatandingAmt() > 0)
					model.getTradeMasterDetailDTO().setTotalOutsatandingAmt(dto.getTotalOutsatandingAmt());
				if (dto != null && StringUtils.isNotEmpty(dto.getPrimaryOwnerName()))
					model.getTradeMasterDetailDTO().setPrimaryOwnerName(dto.getPrimaryOwnerName());
			}
			model.setViewDetFlag(MainetConstants.FlagY);
			model.setViewPaymentFlag(MainetConstants.FlagN);
			// For Setting Owner name in case of partnership business
			if (!tradeDTO.getTradeLicenseOwnerdetailDTO().isEmpty()) {
				StringBuilder ownName = new StringBuilder();
				String ownerName = null;
				for (TradeLicenseOwnerDetailDTO ownDto : tradeDTO.getTradeLicenseOwnerdetailDTO()) {
					if (ownDto.getTroName() != null && !ownDto.getTroName().isEmpty())
						ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
				}
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				if (ownerName != null && !ownerName.isEmpty()) {
					model.setOwnerName(ownerName);
				}
			}
			// 125867
			LookUp licType = null;
			if (tradeDTO.getTrdLictype() != null) {
				licType = CommonMasterUtility.getNonHierarchicalLookUpObject(tradeDTO.getTrdLictype());
			}
			// add code for 111813
			if (tradeDTO.getTrdLicfromDate() != null) {
				tradeDTO.getRenewalMasterDetailDTO().setRenewalFromDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tradeDTO.getTrdLicfromDate()));
			}
			if (tradeDTO.getTrdLictoDate() != null) {
				tradeDTO.getRenewalMasterDetailDTO().setRenewalTodDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(tradeDTO.getTrdLictoDate()));
			}

			int noofdays = 5; // replace with prefix
			// model.getRenewalDate(tradeDTO.getTrdLictoDate(), noofdays);
			if ((lookUpCancel != null && (tradeDTO.getTrdStatus().equals(lookUpCancel.getLookUpId())) && !flagCheck)) {
				flag = true;
				model.addValidationError(getApplicationSession().getMessage("trade.licNo.cancel"));

			} else if (!tradeDTO.getTrdStatus().equals(lookUpIsue.getLookUpId()) && !flagCheck) {
				flag = true;
				model.addValidationError(getApplicationSession().getMessage("renewal.valid.licenseStatus")+" "+tradeDTO.getApmApplicationId());
			}

			else if (licType != null && licType.getLookUpCode() != null
					&& licType.getLookUpCode().equals(MainetConstants.FlagT)) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.temp.ren"));
			}
			// 126139 to give validation of renewal date period on search for skdcl env
			else if (renewalLicenseApplicationService.isKDMCEnvPresent() || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)) {
				 //User Story #133712
				double renPendDays = Utility.getDaysBetweenDates(tradeDTO.getTrdLictoDate(), new Date());
				if (renPendDays != 0) {
					tradeDTO.setRenewalPendingDays((long) Math.ceil((renPendDays / 365)));
					tradeDTO.setRenewCycle((long) Math.ceil((renPendDays / 365)));
				}
				long DifferenceInDays = Utility.getDaysBetweenDates(new Date(), tradeDTO.getTrdLictoDate());

				if (DifferenceInDays <= 0) {
					DifferenceInDays = 0;
				}

				if (DifferenceInDays > 30) {
					model.addValidationError(
							ApplicationSession.getInstance().getMessage("renewal.license.day.validity1") + " " + 30
									+ " "
									+ ApplicationSession.getInstance().getMessage("renewal.license.day.validity2"));
					flag = true;
				}

			}
			if (flag) {
				// for hide proceed button
				model.setFilterType("V");
				mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
						MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}

			/*
			 * tradeDTO.getRenewalMasterDetailDTO()
			 * .setTreLicfromDate(tradeDTO.getTrdLicfromDate());
			 * tradeDTO.getRenewalMasterDetailDTO()
			 * .setTreLictoDate(tradeDTO.getTrdLictoDate());
			 */

			/*
			 * model.setTradeMasterDetailDTO(tradeLicenseApplicationService
			 * .getTradeLicenseWithAllDetailsByApplicationId(tradeDTO.getApmApplicationId())
			 * );
			 */
			// model.setTradeMasterDetailDTO(tradeLicenseApplicationService.getTradeLicenceChargesFromBrmsRule(tradeDTO));
			// Defect #111806

		} else
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
				model);

		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

		return mv;
	}

	/**
	 * Get charges from BRMS rule
	 * 
	 * @param request
	 * @return
	 */
  //#140263
	@RequestMapping(params = "getchargesAndPay", method = RequestMethod.POST)
	public ModelAndView getchargesAndPay(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		final RenewalLicenseFormModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv = null;
		boolean flag = false;
		Boolean flagCheck = false;
		Boolean isExist = false;
		TradeMasterDetailDTO masDto = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		if (masDto != null) {
			if (masDto != null && masDto.getApmApplicationId() != null) {
				flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(masDto.getApmApplicationId(), orgId);
				//to check license is present under objection process
	            isExist  = renewalLicenseApplicationService.checkLicenseNoExist(trdLicno, orgId);
			}
			if (isExist) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("valid.obj.licNo"));
				flag = true;
			}
			LookUp lookUpIsue = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS");
			LookUp lookUpCancel = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS");
			LookUp licType = null;
			if (masDto.getTrdLictype() != null) {
				licType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype());
			}
			if ((lookUpCancel != null && (masDto.getTrdStatus().equals(lookUpCancel.getLookUpId())) && !flagCheck)) {
				flag = true;
				model.addValidationError(getApplicationSession().getMessage("trade.licNo.cancel"));

			} else if (!masDto.getTrdStatus().equals(lookUpIsue.getLookUpId()) && !flagCheck) {
				flag = true;
				model.addValidationError(getApplicationSession().getMessage("renewal.valid.licenseStatus")+" "+masDto.getApmApplicationId());
			}

			else if (licType != null && licType.getLookUpCode() != null
					&& licType.getLookUpCode().equals(MainetConstants.FlagT)) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.temp.ren"));
			}
			if (!masDto.getTradeLicenseOwnerdetailDTO().isEmpty()) {
				StringBuilder ownName = new StringBuilder();
				String ownerName = null;
				for (TradeLicenseOwnerDetailDTO ownDto : masDto.getTradeLicenseOwnerdetailDTO()) {
					if (ownDto.getTroName() != null && !ownDto.getTroName().isEmpty())
						ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
				}
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				if (ownerName != null && !ownerName.isEmpty()) {
					model.setOwnerName(ownerName);
				}
			}
			if (masDto.getTrdLicfromDate() != null) {
				masDto.getRenewalMasterDetailDTO().setRenewalFromDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(masDto.getTrdLicfromDate()));
			}
			if (masDto.getTrdLictoDate() != null) {
				masDto.getRenewalMasterDetailDTO().setRenewalTodDateDesc(
						new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(masDto.getTrdLictoDate()));
			}
			this.getModel().setTradeMasterDetailDTO(masDto);
			List<LookUp> lookUps = CommonMasterUtility.getLookUps("LRP", UserSession.getCurrent().getOrganisation());
			if (CollectionUtils.isNotEmpty(lookUps))
				for (LookUp lookUp : lookUps) {
					if (MainetConstants.FlagY.equals(lookUp.getDefaultVal()))
						masDto.getRenewalMasterDetailDTO().setRenewalPeriod(lookUp.getLookUpId());
					break;
				}
			if (masDto.getRenewalMasterDetailDTO().getRenewalPeriod() > 0) {
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(
						masDto.getRenewalMasterDetailDTO().getRenewalPeriod(),
						model.getUserSession().getOrganisation());
				Long renewalBeforeToDatedays = Long.valueOf(lookup.getOtherField());
				long DifferenceInDays = Utility.getDaysBetweenDates(new Date(),
						model.getTradeMasterDetailDTO().getTrdLictoDate());
				if (DifferenceInDays <= 0) {
					DifferenceInDays = 0;
				}
				if (DifferenceInDays > 0) {
					if (DifferenceInDays > renewalBeforeToDatedays) {
						model.addValidationError(
								ApplicationSession.getInstance().getMessage("renewal.license.day.validity1") + " "
										+ renewalBeforeToDatedays + " "
										+ ApplicationSession.getInstance().getMessage("renewal.license.day.validity2"));
						flag = true;
					}
				}
				this.getModel().getTradeMasterDetailDTO().setOrgid(orgId);
				LinkedHashMap<String, Object> map = tradeLicenseApplicationService.getCheckListChargeFlagAndLicMaxDay(
						UserSession.getCurrent().getOrganisation().getOrgid(), "RTL");
				if (map.get("checkListApplFlag") != null && map.get("checkListApplFlag").equals("Y")) {
					model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
					model.getCheckListFromBrms();
				} else {
					model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
				}
				if (map.get("applicationchargeApplFlag") != null && map.get("applicationchargeApplFlag").equals("Y")) {
					model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
					try {
						// to get application time charges from BRMS Rule
						model.setTradeMasterDetailDTO(
								renewalLicenseApplicationService.getTradeLicenceApplicationChargesFromBrmsRule(masDto));
						model.getOfflineDTO().setAmountToShow(
								(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
						model.getOfflineDTO().setAmountToPay(
								this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee().toString());
					} catch (Exception e) {
						model.setTradeMasterDetailDTO(masDto);
					}
				} else {
					model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
				}
				TradeMasterDetailDTO dto = tradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(masDto);
				if (dto != null && dto.getTotalOutsatandingAmt() > 0)
					model.getTradeMasterDetailDTO().setTotalOutsatandingAmt(dto.getTotalOutsatandingAmt());
				if (dto != null && dto.getTotalWaterOutsatandingAmt() > 0)
					model.getTradeMasterDetailDTO().setTotalWaterOutsatandingAmt(dto.getTotalWaterOutsatandingAmt());
				if (dto != null && StringUtils.isNotEmpty(dto.getPrimaryOwnerName()))
					model.getTradeMasterDetailDTO().setPrimaryOwnerName(dto.getPrimaryOwnerName());
				model.setPaymentCheck(MainetConstants.TradeLicense.FlagY);
				model.setChecklistCheck("N");
			}
			model.setViewPaymentFlag(MainetConstants.FLAGY);
			if (flag) {
				model.setFilterType("V");
				mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
						MainetConstants.FORM_NAME, this.getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}else 
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
				model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS)
	public ModelAndView getCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		final RenewalLicenseFormModel model = getModel();
		TradeMasterDetailDTO masDto = model.getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);

		// 126139 to give renewal date validation on proceed
		if (!renewalLicenseApplicationService.isKDMCEnvPresent()) {
			Long renewalPeriod = model.getTradeMasterDetailDTO().getRenewalMasterDetailDTO().getRenewalPeriod();

			if (renewalPeriod != null) {
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(renewalPeriod,
						model.getUserSession().getOrganisation());

				Long renewalBeforeToDatedays = Long.valueOf(lookup.getOtherField());

				long DifferenceInDays = Utility.getDaysBetweenDates(new Date(),
						model.getTradeMasterDetailDTO().getTrdLictoDate());

				if (DifferenceInDays <= 0) {
					DifferenceInDays = 0;
				}

				if (DifferenceInDays > 0) {

					if (DifferenceInDays > renewalBeforeToDatedays) {

						model.addValidationError(
								ApplicationSession.getInstance().getMessage("renewal.license.day.validity1") + " "
										+ renewalBeforeToDatedays + " "
										+ ApplicationSession.getInstance().getMessage("renewal.license.day.validity2"));
						ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
								MainetConstants.FORM_NAME, this.getModel());
						mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
								getModel().getBindingResult());
						return mv;
					}

				}

			}
		}
		this.getModel().getTradeMasterDetailDTO().setOrgid(orgid);
		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "RTL");

		if (map.get("checkListApplFlag") != null && map.get("checkListApplFlag").equals("Y")) {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
			model.getCheckListFromBrms();
		} else {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		if (map.get("applicationchargeApplFlag") != null && map.get("applicationchargeApplFlag").equals("Y")) {
			model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
			try {
				// 127361 to get application time charges from BRMS Rule
				model.setTradeMasterDetailDTO(
						renewalLicenseApplicationService.getTradeLicenceApplicationChargesFromBrmsRule(masDto));
				model.getOfflineDTO().setAmountToShow(
						(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
				model.getOfflineDTO()
						.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee().toString());
			} catch (Exception e) {
				model.setTradeMasterDetailDTO(masDto);
				// TODO: handle exception
			}
		} else {
			model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		}

		model.setPaymentCheck(MainetConstants.TradeLicense.FlagY);
		model.setChecklistCheck("N");
		model.setViewPaymentFlag(MainetConstants.FLAGY);
		return new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
				model);
	}

	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_RENEWAL_LICENSE, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveRenewalLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		RenewalLicenseFormModel model = this.getModel();
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("RenewalLicenseChargesDetails", MainetConstants.FORM_NAME, getModel());
	}

	/**
	 * generate Payment mode and save renewal license
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public  ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		RenewalLicenseFormModel model = this.getModel();

		ModelAndView mv = null;
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else {
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}
		}
		mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
				getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}
	// Defect #129362
		@RequestMapping(params = "printAgencyRegAckw", method = { RequestMethod.POST })
		public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
			bindModel(request);
			Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
			RenewalLicenseFormModel model = this.getModel();
			TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
			Long serviceId = iPortalServiceMasterService.getServiceId("RTL", orgId);
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
				if (model.getServiceMast()  !=null && model.getServiceMaster() .getServiceName() != null) 
				model.setServiceName(model.getServiceMaster() .getServiceName());
				model.setDepartmentName(model.getServiceMaster().getPsmDpDeptDesc());
				}
			else {
				if(model.getServiceMaster() != null && model.getServiceMaster() .getServiceNameReg() !=null)
				model.setServiceName(model.getServiceMaster() .getServiceNameReg());
				model.setDepartmentName(model.getServiceMaster().getPsmDpNameMar());
			}
				
				model.setAppDate(new Date());
				model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
				//#157760-display due date on the acknowledgement copy
				LinkedHashMap<String, Object> map = tradeLicenseApplicationService
						.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "RTL");
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