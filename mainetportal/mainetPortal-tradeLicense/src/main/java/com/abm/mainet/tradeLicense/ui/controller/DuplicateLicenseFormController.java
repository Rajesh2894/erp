package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
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
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
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
import com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseFormModel;
import com.abm.mainet.tradeLicense.ui.model.DuplicateLicenseFormModel;

@Controller
@RequestMapping("/DuplicateLicenseForm.html")
public class DuplicateLicenseFormController extends AbstractFormController<DuplicateLicenseFormModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IDuplicateLicenseApplicationService duplicateLicenseApplicationService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		//For setting help document
	   getModel().setCommonHelpDocs("DuplicateLicenseForm.html");
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
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
		 //#155458  to hide document related div when checklist not applicable
		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "NTL");
		if (map.get("applicationchargeApplFlag") != null) {
			getModel().setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		}
		if (map.get("checkListApplFlag") != null) {
			getModel().setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		ModelAndView mv=new ModelAndView(MainetConstants.TradeLicense.DUPLICATE_LICENSE_FORM, MainetConstants.FORM_NAME,
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
				mv= new ModelAndView("DuplicateLicenseForm", MainetConstants.FORM_NAME, getModel());
				 
			}
			else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
				mv = new ModelAndView("DuplicateLicenseForm", MainetConstants.FORM_NAME, getModel());
			}
			else{
				mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
			}}
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_OTP_BTN_SHOW)
	public ModelAndView getOtpBtn(final HttpServletRequest request, @RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(request);
		DuplicateLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno,tradeDTO.getOrgid());
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
		DuplicateLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO.setOrgid(orgId);
		// 124306 for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			//D#125111
			flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(tradeDTO.getApmApplicationId(),orgId);
		}
		// Defect #110124 added code for checking license canceled or not and throwing
		// validation
		if (tradeDTO != null && tradeDTO.getTrdStatus() != null && !flagCheck) {
			LookUp licenseStatus = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
					UserSession.getCurrent().getOrganisation());
			if (licenseStatus != null && tradeDTO.getTrdStatus().equals(licenseStatus.getLookUpId())) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.cancel"));
				return defaultMyResult();
			}

		}
		if  ((model.getOtp()!= null && model.getOtp().equals(model.getUserOtp())) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENVIRNMENT_VARIABLE.ENV_SKDCL)) {
			tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(tradeDTO.getTrdLicno(),tradeDTO.getOrgid());
			model.setTradeMasterDetailDTO(tradeDTO);
			if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
				model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
				model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
				model.setLicenseDetails(MainetConstants.TradeLicense.FlagY);
				model.setChecklistCheck(MainetConstants.TradeLicense.FlagY);
				model.setLicenseBtnFlag("N");
				
				//126060 - to show only approved item details		
				tradeDTO.setTradeLicenseItemDetailDTO(tradeDTO.getTradeLicenseItemDetailDTO()
						.stream()
						.filter(trdItem -> trdItem != null
								&& (trdItem.getTriStatus() != null && trdItem.getTriStatus().equals(MainetConstants.FlagA)))
						.collect(Collectors.toList()));
				//Defect #110970
				StringBuilder ownName = new StringBuilder();
				String fName = null;
				tradeDTO.getTradeLicenseOwnerdetailDTO().forEach(dto -> {
					if (dto.getTroName() != null ) {
						ownName.append(dto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
				});
				if (ownName.length() > 0)
				fName = ownName.deleteCharAt(ownName.length() - 1).toString();
				if (fName != null) {
					model.setOwnerName(fName);
				}			
			} 
		} else {
			model.addValidationError(getApplicationSession().getMessage("trade.validation.otp"));
		}
		return defaultMyResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGE)
	public ModelAndView getChecklistCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		DuplicateLicenseFormModel model = getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		model.getCheckListFromBrms();
		model.setTradeMasterDetailDTO(duplicateLicenseApplicationService.getDuplicateChargesFromBrmsRule(masDto));
		model.getOfflineDTO().setAmountToShow((model.getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
		model.getOfflineDTO().setAmountToPay(model.getTradeMasterDetailDTO().getTotalApplicationFee().toString());
		model.setPaymentCheck(MainetConstants.TradeLicense.FlagY);
		model.setChecklistCheck("N");
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
		DuplicateLicenseFormModel model = this.getModel();
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
		final DuplicateLicenseFormModel model = getModel();
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
		emailDto.setAppNo(otp);
		emailDto.setOneTimePassword(otp);
		emailDto.setAppName(tradeDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroName());

		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ONLINE_SERVICE, "DuplicateLicenseForm.html",
				MainetConstants.SMS_EMAIL.OTP_MSG, emailDto, UserSession.getCurrent().getOrganisation(),
				UserSession.getCurrent().getLanguageId());
		model.setTradeMasterDetailDTO(tradeDTO);
		return model.getTradeMasterDetailDTO();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_DUPLICATE_LICENSE_PRINT)
	public ModelAndView duplicateLicensePrint(final HttpServletRequest request) {
		this.getModel().bind(request);
		final DuplicateLicenseFormModel model = getModel();
		model.setIssuanceDateDesc(Utility.dateToString(new Date()));
		return new ModelAndView(MainetConstants.TradeLicense.DUPLICATE_LICENSE_PRINT, MainetConstants.FORM_NAME,
				this.getModel());
	}
	@RequestMapping(params = "printAgencyRegAckw", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		DuplicateLicenseFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long serviceId = iPortalServiceMasterService.getServiceId("DTL", orgId);
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
			//#157760-display due date on the acknowledgement copy
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
