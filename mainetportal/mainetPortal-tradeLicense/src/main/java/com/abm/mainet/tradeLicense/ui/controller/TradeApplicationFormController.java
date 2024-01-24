/**
 * 
 */
package com.abm.mainet.tradeLicense.ui.controller;

import java.io.IOException;
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
import org.apache.log4j.Logger;
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
import com.abm.mainet.common.constant.MainetConstants.TradeLicense;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.EncryptionAndDecryptionAapleSarkar;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TradeApplicationFormModel;

/**
 * @author Gayatri.Kokane
 *
 */

@Controller
@RequestMapping(MainetConstants.TradeLicense.TRADE_APPLICATION_FORM_HTML)
public class TradeApplicationFormController extends AbstractFormController<TradeApplicationFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	IOrganisationService organisationService;

	@Autowired
	IPortalServiceMasterService iPortalServiceMasterService;

	private static final Logger LOGGER = Logger.getLogger(TradeApplicationFormController.class);

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest request,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		this.sessionCleanup(request);
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("AS", "MLI");
		TradeApplicationFormModel model = this.getModel();

		// Defect #128321
		model.setCommonHelpDocs("TradeApplicationForm.html");

		if (lookUp != null) {
			if (lookUp.getOtherField().equals("Y"))
				this.getModel().setPropertyActiveStatus("Y");
		}

		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "NTL");
		if (map.get("applicationchargeApplFlag") != null) {
			model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		}
		if (map.get("checkListApplFlag") != null) {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
		}
		// #129248
		if (isKDMCEnvPresent()) {
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		} else {
			this.getModel().setKdmcEnv(MainetConstants.FlagN);
		}
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA))
			this.getModel().setSudaEnv(MainetConstants.FlagY);
		else
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		ModelAndView mv =defaultResult();
		if (isKDMCEnvPresent()) {
		String authentication="";
		
		if(null!=str && null!=ns && null!=ULBID && null!=ULBDistrict){
			EncryptionAndDecryptionAapleSarkar encryptDecrypt = new EncryptionAndDecryptionAapleSarkar();
			LOGGER.info("Encrypted Key: " + str);
			authentication=encryptDecrypt.authentication(str,ns);	
			
			if(!authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)){
				this.getModel().getTradeMasterDetailDTO().setTenant(authentication);
			}
		}
		
		
		Employee emp = UserSession.getCurrent().getEmployee();
		if(emp.getEmploginname().equalsIgnoreCase("NOUSER") && !authentication.equalsIgnoreCase(MainetConstants.MENU.FALSE)&& StringUtils.isNotEmpty(authentication)) {
			mv= new ModelAndView("TradeApplicationForm", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("TradeApplicationForm", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}
		}
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.VIEW_TERMS_CONDITION)
	public ModelAndView tradeTermsCondition(final HttpServletRequest request) {
		bindModel(request);
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_TERMS_CONDITION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		TradeApplicationFormModel model = this.getModel();
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		//D#129514 for showing two grid at the time of Joint owner for SKDCL 
		if (isKDMCEnvPresent()) {
			if (model.getTradeMasterDetailDTO().getApmApplicationId() == null
					&& (StringUtils.isEmpty(model.getViewMode())
							|| !model.getViewMode().equals(MainetConstants.FlagV) && !model.getViewMode().equals(MainetConstants.FlagB))) {
				model.getTradeMasterDetailDTO()
						.setTradeLicenseOwnerdetailDTO(new ArrayList<TradeLicenseOwnerDetailDTO>());
				if (ownershipType.equals("P")) {
					TradeLicenseOwnerDetailDTO trdto = new TradeLicenseOwnerDetailDTO();
					model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(trdto);
					TradeLicenseOwnerDetailDTO trdto1 = new TradeLicenseOwnerDetailDTO();
					model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(trdto1);

				}
			}
		}

		model.setOwnershipPrefix(ownershipType);
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		// TradeLicenseOwnerDetailDTO());
		return new ModelAndView(MainetConstants.TradeLicense.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {
		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		List<TradeLicenseOwnerDetailDTO> owner = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getLength(); i++) {
			attachments.add(new DocumentDetailsVO());
			owner.add(new TradeLicenseOwnerDetailDTO());
		}
		int count = 0;
		for (TradeLicenseOwnerDetailDTO ownerDto : this.getModel().getTradeMasterDetailDTO()
				.getTradeLicenseOwnerdetailDTO()) {
			BeanUtils.copyProperties(ownerDto, owner.get(count));
			count++;
		}
		this.getModel().getTradeMasterDetailDTO().setAttachments(attachments);
		this.getModel().getTradeMasterDetailDTO().setTradeLicenseOwnerdetailDTO(owner);
		return new ModelAndView(MainetConstants.TradeLicense.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_TRADE_LICENSE_FORM, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGE_DETAILS_MARKET_LICENSE, MainetConstants.FORM_NAME,
				getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGE, method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
			throws IOException {
		this.getModel().bind(httpServletRequest);
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		if (masDto.getTrdWard1() == null || masDto.getTrdWard1() == 0) {
			this.getModel().addValidationError(getApplicationSession().getMessage("trade.license.zone"));
			if (masDto.getTrdWard2() == null || masDto.getTrdWard2() == 0) {
				this.getModel().addValidationError(getApplicationSession().getMessage("trade.license.ward"));
			}
			return defaultResult();
		}
		masDto.setOrgid(orgid);
		if (this.getModel().getTradeMasterDetailDTO() != null && StringUtils.isNotEmpty(this.getModel().getTradeMasterDetailDTO().getTrdFlatNo())) { //#143640
			String[] arr = this.getModel().getTradeMasterDetailDTO().getTrdFlatNo().split(",");
			if(arr != null && arr.length > 0 && arr[0] != null) {
				masDto.setTrdFlatNo(arr[0]);
			}
		}
		TradeApplicationFormModel model = this.getModel();
		this.getModel().setPaymentCheck(MainetConstants.FlagN);
		// 123803
		masDto.setAttachments(fileUpload.setFileUploadMethod(new ArrayList<DocumentDetailsVO>()));
		if (MainetConstants.FlagY.equalsIgnoreCase(model.getCheckListApplFlag())) {
			this.getModel().getCheckListFromBrms();
		}
		if (MainetConstants.FlagY.equalsIgnoreCase(model.getAppChargeFlag())) {
			this.getModel().setTradeMasterDetailDTO(
					tradeLicenseApplicationService.getTradeLicenceApplicationChargesFromBrmsRule(masDto));

			this.getModel().getOfflineDTO().setAmountToShow(
					(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
			this.getModel().getOfflineDTO()
					.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee().toString());
			this.getModel().setPaymentCheck(TradeLicense.FlagY);
		}

		this.getModel().setViewMode("V");
		this.getModel().setOpenMode("D");
		this.getModel().setDownloadMode("M");
		this.getModel().setHideshowAddBtn(TradeLicense.FlagY);
		this.getModel().setHideshowDeleteBtn(TradeLicense.FlagY);
		this.getModel().getdataOfUploadedImage();
		/*
		 * List<DocumentDetailsVO> ownerDocs = masDto.getAttachments(); if (ownerDocs !=
		 * null) { ownerDocs = getModel().prepareFileUploadForImg(ownerDocs); }
		 */
		this.getModel().getTradeMasterDetailDTO().setTrdFlatNo(masDto.getTrdFlatNo());
		return defaultResult();

	}

	@RequestMapping(params = "generateChallanAndPayement", method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();

		ModelAndView mv = null;
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else {
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			}

		}
		mv = new ModelAndView("TradeApplicationFormValidn", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.GET_PROPERTY_DETAILS, method = RequestMethod.POST)
	public TradeMasterDetailDTO getPropertyDetails(HttpServletRequest request) {

		this.getModel().bind(request);
		TradeApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		final LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("AS", "MLI");
		if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.Common_Constant.YES)) {

			tradeDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			tradeDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			tradeMasterDetailDTO = tradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(tradeDTO);
			if (tradeMasterDetailDTO != null) {
				this.getModel().setTradeMasterDetailDTO(tradeMasterDetailDTO);
			}
		}
		if (tradeMasterDetailDTO != null && tradeMasterDetailDTO.getAssessmentCheckFlag() != null)
		model.setAssessmentCheckFlag(tradeMasterDetailDTO.getAssessmentCheckFlag());
		return tradeMasterDetailDTO;
	}

	// 124307
	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.CHECK_ASS_STATUS, method = RequestMethod.POST)
	public String checkStatus(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		String assementFlag = this.getModel().getAssessmentCheckFlag();
		if (assementFlag != null && assementFlag.equals("Y")) {
			return MainetConstants.FlagY;
		} else {
			return MainetConstants.FlagN;
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
	public ModelAndView backEditPageForm(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setPaymentCheck("A");
		this.getModel().setViewMode("B");
		this.getModel().setOpenMode(TradeLicense.FlagY);
		this.getModel().setDownloadMode("N");
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SUDA))
			this.getModel().setSudaEnv(MainetConstants.FlagY);
		else
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_EDIT, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/*
	 * @RequestMapping(method = RequestMethod.POST, params =
	 * MainetConstants.TradeLicense.BACKPAGE) public ModelAndView backPageForm(final
	 * HttpServletRequest httpServletRequest) { getModel().bind(httpServletRequest);
	 * return new
	 * ModelAndView(MainetConstants.TradeLicense.TRADE_APPLICATION_BACK_FORM,
	 * MainetConstants.FORM_NAME, this.getModel()); }
	 */

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView dashboardView(@RequestParam("appId") final long appId,
			@RequestParam("appStatus") String appStatus, final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDto = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(appId);
		model.setTradeMasterDetailDTO(tradeDto);
		model.setPaymentCheck("V");
		model.setDownloadMode("M");
		model.setViewMode("V");
		model.setOpenMode("D");
		model.setHideshowAddBtn(TradeLicense.FlagY);
		model.setHideshowDeleteBtn(TradeLicense.FlagY);
		// Defect #129239
		model.setBackBtn("N");
		return defaultMyResult();
	}

	@RequestMapping(method = RequestMethod.POST, params = "getOtherValue")
	public @ResponseBody Long getOtherValue(@RequestParam("itemCode1") final long itemCode1,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		lookupListLevel1 = CommonMasterUtility.getLevelData(MainetConstants.TradeLicense.ITC, 1,
				UserSession.getCurrent().getOrganisation());

		List<LookUp> level1 = lookupListLevel1.parallelStream()
				.filter(clList -> clList != null && clList.getLookUpId() == itemCode1).collect(Collectors.toList());

		String otherField = level1.get(0).getOtherField();

		LOGGER.info("Other Field Value of " + " " + MainetConstants.TradeLicense.ITC + " " + "Prefix is =" + " "
				+ otherField);
		if (otherField != null && !otherField.isEmpty()) {

			if (MainetConstants.FlagY.equalsIgnoreCase(otherField)) {

				return 1l;
			}
		}

		return null;

	}

	// #129248
	public boolean isKDMCEnvPresent() {
		Organisation org = organisationService.getOrganisationById(Utility.getOrgId());
		List<LookUp> envLookUpList = CommonMasterUtility.getLookUps("ENV", org);
		return envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals("SKDCL")
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		// return true;
	}

	// Defect #129362
	@RequestMapping(params = "printAgencyRegAckw", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TradeApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long serviceId = iPortalServiceMasterService.getServiceId("NTL", orgId);
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
			if (model.getServiceMaster() !=null && model.getServiceMaster().getServiceName() != null) 
			model.setServiceName(model.getServiceMaster().getServiceName());
			}
		else {
			if(model.getServiceMaster() != null && model.getServiceMaster().getServiceNameReg() !=null)
			model.setServiceName(model.getServiceMaster().getServiceNameReg());
		}
			if (StringUtils.isNotEmpty(tradeDTO.getDepartmentName()))
		    model.setDepartmentName(tradeDTO.getDepartmentName());
			model.setAppDate(new Date());
			model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
			model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
			//#157760-display due date on the acknowledgement copy
			LinkedHashMap<String, Object> map = tradeLicenseApplicationService
					.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "NTL");
			if (map.get("smServiceDuration") != null)
				model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(),Long.valueOf((String) map.get("smServiceDuration")).intValue()));
			if (CollectionUtils.isNotEmpty(model.getCheckList()))
			model.setCheckList(model.getCheckList());

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.SKDCL))
			return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		else
		return new ModelAndView("TradeLicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}
	
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(params = "fetchPropertyFlatNo", method = RequestMethod.POST)
    public List<String> fetchPropertyFlatNo(@RequestParam("propNo") final String propNo,
            final HttpServletRequest request) {
    	Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	    List<String> flatNoList = tradeLicenseApplicationService.getPropertyFlatNo(propNo,orgId);
	    this.getModel().setFlatNoList(flatNoList);
	    return flatNoList;
 }

}
