package com.abm.mainet.tradeLicense.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.common.util.UtilityService;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeInBusinessNameService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.ChangeInBusinessNameFormModel;

@Controller
@RequestMapping("/ChangeInBusinessNameForm.html")
public class ChangeInBusinessNameFormController extends AbstractFormController<ChangeInBusinessNameFormModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IChangeInBusinessNameService changeInBusinessNameService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		//For setting help document
		getModel().setCommonHelpDocs("ChangeInBusinessNameForm.html");
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		/*
		 * Long orgId = UserSession.getCurrent().getOrganisation().getOrgid(); final
		 * Long serviceId =
		 * iPortalServiceMasterService.getServiceId(MainetConstants.TradeLicense.
		 * CHANGE_IN_BUSINESS_NAME_SHORT_CODE, orgId); final PortalService service =
		 * iPortalServiceMasterService.getService(serviceId, orgId); final LookUp lookup
		 * = CommonMasterUtility.getNonHierarchicalLookUpObject(service.getServiceId(),
		 * UserSession.getCurrent().getOrganisation()); if
		 * (!lookup.getLookUpCode().equals("NA")) {
		 * this.getModel().setImmediateServiceMode("Y"); } else {
		 * this.getModel().setImmediateServiceMode("N"); }
		 */
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
		ModelAndView mv =new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_BUSINESS_NAME_FORM, MainetConstants.FORM_NAME,
				getModel());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL)) {
		String authentication="";
		
		if(null!=str && null!=ns && null!=ULBID && null!=ULBDistrict){
			EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
			authentication=encryptDecrypt.authentication(str,ns);	
			
			if(!authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)){
				this.getModel().setTenant(authentication);
			}
		}
		
		
		Employee emp = UserSession.getCurrent().getEmployee();
		if(emp.getEmploginname().equalsIgnoreCase("NOUSER") && !authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)&& StringUtils.isNotEmpty(authentication)) {
			mv= new ModelAndView("ChangeInBusinessNameForm", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("ChangeInBusinessNameForm", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}}
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_OTP_BTN_SHOW)
	public ModelAndView getOtpBtn(final HttpServletRequest request, @RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(request);
		ChangeInBusinessNameFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, tradeDTO.getOrgid());
		tradeDTO.setTenant(model.getTenant());
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

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		ChangeInBusinessNameFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		tradeDTO.setTenant(model.getTenant());
		// 124169
		// code added for check is this license rejected in any of the service flow
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(tradeDTO.getApmApplicationId(), orgId);
		}
		model.setTradeDetailDTO(tradeDTO);
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

		if (model.getOtp()!= null && model.getOtp().equals(model.getUserOtp()) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(tradeDTO.getTrdLicno(),
					tradeDTO.getOrgid());
			model.setTradeMasterDetailDTO(tradeDTO);
			if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {

				model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
				model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
				model.setLicenseDetails(MainetConstants.TradeLicense.FlagY);
				model.setChecklistCheck(MainetConstants.TradeLicense.FlagY);
				model.setLicenseBtnFlag("N");

			}
			if (!model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
				StringBuilder ownName = new StringBuilder();
				String ownerName = null;
				for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeMasterDetailDTO()
						.getTradeLicenseOwnerdetailDTO()) {
					if (ownDto.getTroName() != null && !ownDto.getTroName().isEmpty())
						ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
				}
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				if (ownerName != null && !ownerName.isEmpty()) {
					model.setOwnerName(ownerName);
				}
			}
		} else {
			model.addValidationError(getApplicationSession().getMessage("trade.validation.otp"));
		}

		// #122900 to check value editable flag in ENV prefix
		LookUp valueEditableCheck = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TOVE,
				MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
		if (valueEditableCheck != null && StringUtils.isNotBlank(valueEditableCheck.getOtherField())
				&& StringUtils.equals(valueEditableCheck.getOtherField(), MainetConstants.FlagY)) {
			this.getModel().setValueEditableCheckFlag(MainetConstants.FlagY);
		}
		return defaultMyResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = "getCheckListAndCharges")
	public ModelAndView getChecklistCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		ChangeInBusinessNameFormModel model = getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		// to set old business to new business name if new business is not entered
		if (model.getTradeMasterDetailDTO().getTrdNewBusnm() == null
				|| model.getTradeMasterDetailDTO().getTrdNewBusnm().isEmpty()
				|| model.getTradeMasterDetailDTO().getTrdNewBusnm() == "")
			model.getTradeMasterDetailDTO().setTrdNewBusnm(model.getTradeMasterDetailDTO().getTrdBusnm());
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		model.getCheckListFromBrms();
		model.setTradeMasterDetailDTO(changeInBusinessNameService.getBusinessNameChargesFromBrmsRule(masDto));
		model.getOfflineDTO().setAmountToShow((model.getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
		model.getOfflineDTO().setAmountToPay(model.getTradeMasterDetailDTO().getTotalApplicationFee().toString());
		model.setPaymentCheck(MainetConstants.TradeLicense.FlagY);
		model.setChecklistCheck("N");
		model.setHideshowAddBtn("N");
		model.setHideshowDeleteBtn("N");
		return defaultMyResult();

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGES_DETAIL, MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ChangeInBusinessNameFormModel model = this.getModel();
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else {
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}

		}
		return defaultMyResult();
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_OTP, method = RequestMethod.POST)
	@ResponseBody
	public TradeMasterDetailDTO generateOtp(final HttpServletRequest request) {

		getModel().bind(request);
		final ChangeInBusinessNameFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = getModel().getTradeMasterDetailDTO();
		final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
		model.setOtp(otp);

		SMSAndEmailDTO emailDto = new SMSAndEmailDTO();
		if (tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno() != null
				&& !tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno().isEmpty()) {
			emailDto.setMobnumber(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		}
		if (tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid() != null
				&& !tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid().isEmpty()) {
			emailDto.setEmail(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroEmailid());
		}
		emailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		emailDto.setCurrentDate(Utility.dateToString(new Date()));
		emailDto.setAppNo(otp);
		emailDto.setOneTimePassword(otp);
		emailDto.setAppName(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName());

		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE,
				"ChangeInBusinessNameForm.html", MainetConstants.SMS_EMAIL.OTP_MSG, emailDto,
				UserSession.getCurrent().getOrganisation(), UserSession.getCurrent().getLanguageId());
		model.setTradeMasterDetailDTO(tradeDTO);
		return model.getTradeMasterDetailDTO();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHANGE_IN_BUSINESS_LICENSE_PRINT)
	public ModelAndView duplicateLicensePrint(final HttpServletRequest request) {
		this.getModel().bind(request);
		final ChangeInBusinessNameFormModel model = getModel();
		model.setIssuanceDateDesc(Utility.dateToString(new Date()));
		return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_BUS_NAME_LICENSE_PRINT,
				MainetConstants.FORM_NAME, this.getModel());
	}

}
