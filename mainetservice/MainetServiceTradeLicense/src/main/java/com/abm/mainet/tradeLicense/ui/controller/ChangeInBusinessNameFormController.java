package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IChangeBusinessNameService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.ChangeInBusinessNameFormModel;

@Controller
@RequestMapping("/ChangeInBusinessNameForm.html")
public class ChangeInBusinessNameFormController extends AbstractFormController<ChangeInBusinessNameFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private IChangeBusinessNameService changeBusinessNameService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
		/*
		 * if (!lookup.getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
		 * this.getModel().setImmediateServiceMode(MainetConstants.FlagY); } else {
		 * this.getModel().setImmediateServiceMode(MainetConstants.FlagN); }
		 */

		if (sm.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (sm.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagN);
		}
		return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_BUSINESS_NAME_FORM, MainetConstants.FORM_NAME,
				getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = organisationService.getOrganisationById(orgId);
		ChangeInBusinessNameFormModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();
		// 123752 -> to check license no or not
		boolean isLicensePresent = tradeLicenseApplicationService.checkLicensePresent(trdLicno, orgId);
		if (!isLicensePresent) {
			ModelAndView mv = defaultMyResult();
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
			return mv;
		}

		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);

		// for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkRejectedInTaskflow(tradeDTO.getApmApplicationId(), orgId);
		}
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
				UserSession.getCurrent().getOrganisation());
		if (tradeDTO != null && tradeDTO.getTrdStatus() != null && !flagCheck) {
			LookUp issuedlicenseStatus = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
					UserSession.getCurrent().getOrganisation());
			// add for Defect #111784 for throwing validation in case of license cancel or
			// not approved
			boolean flag = false;
			if (lookUp != null && tradeDTO.getTrdStatus().equals(lookUp.getLookUpId())) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.cancel"));
			} else if (issuedlicenseStatus != null
					&& !tradeDTO.getTrdStatus().equals(issuedlicenseStatus.getLookUpId())) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.licenseStatus"));
			}
			if (flag) {
				this.getModel().setViewMode(MainetConstants.FlagB);

				return defaultMyResult();
			}
		}
		// #122900 to check value editable flag in ENV prefix
		LookUp valueEditableCheck = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TOVE,
				MainetConstants.ENV, org);
		if (valueEditableCheck != null && StringUtils.isNotBlank(valueEditableCheck.getOtherField())
				&& StringUtils.equals(valueEditableCheck.getOtherField(), MainetConstants.FlagY)) {
			this.getModel().setValueEditableCheckFlag(MainetConstants.FlagY);
		}

		model.setTradeDetailDTO(tradeDTO);
		model.setTradeMasterDetailDTO(tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId));
		/*
		 * // For Fetching only approve item Defect#107828 if
		 * (model.getTradeMasterDetailDTO()!=null && model.getTradeMasterDetailDTO() !=
		 * null &&
		 * !model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().isEmpty()) {
		 * List<TradeLicenseItemDetailDTO> itemDto =
		 * tradeLicenseApplicationService.getTradeLicenseHistDetBuTrdId(
		 * model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().get(0).
		 * getTriId()); if
		 * (model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().get(0).
		 * getTriStatus() .equals(MainetConstants.FlagM)) { itemDto.get(itemDto.size() -
		 * 2).setItemCategory1(MainetConstants.FlagA);
		 * 
		 * model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().add(0,
		 * itemDto.get(itemDto.size() - 2)); } else {
		 * model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().get(0)
		 * .setItemCategory1(MainetConstants.FlagA); } List<TradeLicenseItemDetailDTO>
		 * trdItemList = model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO()
		 * .parallelStream() .filter(trd -> trd != null &&
		 * (trd.getTriStatus().equalsIgnoreCase(MainetConstants.FlagA) ||
		 * (trd.getItemCategory1() != null &&
		 * trd.getItemCategory1().equals(MainetConstants.FlagA))))
		 * .collect(Collectors.toList());
		 * model.getTradeMasterDetailDTO().setTradeLicenseItemDetailDTO(trdItemList); }
		 */
		if (model.getTradeMasterDetailDTO() != null
				&& !model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			// Defect #108769
			StringBuilder ownName = new StringBuilder();
			String ownerName = null;
			for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO()) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			if (ownName.length() > 0) {
			 ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			}
			if (StringUtils.isNotBlank(ownerName)) {
				model.setOwnerName(ownerName);
			}
		}
		if (trdLicno != null) {
			Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
			// Changes#126501
			if (lookUpId != null) {
				String troName = tradeLicenseApplicationService.getApprovedBuisnessName(trdLicno, orgId, lookUpId);
				if (troName != null) {
					model.getTradeMasterDetailDTO().setTrdBusnm(troName);

				}
			}

		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null && tradeDTO.getTrdStatus() != lookUp.getLookUpId()) {
			// Changes#126501 After discussion with Hammad no impact on charges on change
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)||true) {
				model.getTradeMasterDetailDTO().setTrdNewBusnm(model.getTradeMasterDetailDTO().getTrdBusnm());
			}
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setChecklistCheck(MainetConstants.FlagY);
			model.setViewMode("N");
			// model.setHideshowAddBtn("Y");
			// model.setHideshowDeleteBtn("Y");
		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}

		this.getModel().setViewMode(MainetConstants.FlagB);

		return defaultMyResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGES)
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		ChangeInBusinessNameFormModel model = this.getModel();
		// Defect #120380
		if (model.getTradeMasterDetailDTO().getTrdNewBusnm() == null
				|| model.getTradeMasterDetailDTO().getTrdNewBusnm().isEmpty()
				|| model.getTradeMasterDetailDTO().getTrdNewBusnm() == "")
			model.getTradeMasterDetailDTO().setTrdNewBusnm(model.getTradeMasterDetailDTO().getTrdBusnm());
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setServiceMaster(sm);
		if (sm.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}

		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
				UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setChecklistFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setChecklistFlag(MainetConstants.FlagN);
		}

		if (model.getChecklistFlag().equals("Y")) {

			model.getCheckListFromBrms();
		}

		if (sm.getSmAppliChargeFlag().equals("Y")) {

			try {
				model.setTradeMasterDetailDTO(changeBusinessNameService.getBusinessNameChargesFromBrmsRule(masDto));

				model.getOfflineDTO().setAmountToShow(
						Double.valueOf(this.getModel().getTradeMasterDetailDTO().getApplicationCharge()));
				model.getOfflineDTO().setAmountToPay(this.getModel().getTradeMasterDetailDTO().getApplicationCharge());
				model.setPaymentCheck(MainetConstants.FlagY);
			} catch (FrameworkException e) {

				e.printStackTrace();

				ModelAndView mv = defaultMyResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError("No appropriate BRMS Rule found");

				return mv;

			}

		}
		model.setChecklistCheck(MainetConstants.FlagN);
		model.setViewMode(MainetConstants.FlagV);
		model.setHideshowAddBtn("N");
		model.setHideshowDeleteBtn("N");
		model.setPaymentCheck(MainetConstants.FlagN);
		return defaultMyResult();

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("TransferLicenseChargesDetailMarketLicense", MainetConstants.CommonConstants.COMMAND,
				getModel());
	}

	/**
	 * generate Payment mode and save duplicate license
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ChangeInBusinessNameFormModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		model.getTradeMasterDetailDTO().setDocumentList(docs);
		fileUpload.validateUpload(model.getBindingResult());
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());

		model.getTradeMasterDetailDTO().setTrdStatus(lookUp.getLookUpId());
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}
		return defaultMyResult();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHANGE_IN_BUSINESS_LICENSE_PRINT)
	public ModelAndView BusinessNameLicensePrint(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		final ChangeInBusinessNameFormModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService
				.getCFCApplicationByApplicationId(masDto.getApmApplicationId(), orgId);
		model.setCfcEntity(cfcEntity);
		model.setIssuanceDateDesc(Utility.dateToString(new Date()));
		TradeMasterDetailDTO tradeDetailDto = tradeLicenseApplicationService
				.getLicenseDetailsByLicenseNo(cfcEntity.getRefNo(), orgId);
		model.setTradeMasterDetailDTO(tradeDetailDto);
		model.setLicFromDateDesc(Utility.dateToString(tradeDetailDto.getTrdLicfromDate()));
		model.setLicToDateDesc(Utility.dateToString(tradeDetailDto.getTrdLictoDate()));

		List<CFCAttachment> imgList = checklistVerificationService.getDocumentUploadedByRefNo(
				model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);
		model.setDocumentList(imgList);
		if (!imgList.isEmpty() && imgList != null) {
			model.setImagePath(getPropImages(imgList.get(0)));
		}
		return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_BUS_NAME_LICENSE_PRINT,
				MainetConstants.FORM_NAME, this.getModel());
	}

	private String getPropImages(final CFCAttachment attachDocs) {

		new ArrayList<String>();
		final UUID uuid = UUID.randomUUID();
		final String randomUUIDString = uuid.toString();
		final String outputPath = MainetConstants.DirectoryTree.DEFAULT_CACHE_FOLDER
				+ MainetConstants.FILE_PATH_SEPARATOR + UserSession.getCurrent().getOrganisation().getOrgid()
				+ MainetConstants.FILE_PATH_SEPARATOR + randomUUIDString + MainetConstants.FILE_PATH_SEPARATOR
				+ "PROPERTY";
		final String path1 = attachDocs.getAttPath();
		final String name = attachDocs.getAttFname();
		final String data = Utility.downloadedFileUrl(path1 + MainetConstants.FILE_PATH_SEPARATOR + name, outputPath,
				FileNetApplicationClient.getInstance());
		return data;
	}

	@RequestMapping(params = "saveChangeInBusinessForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		ChangeInBusinessNameFormModel model = this.getModel();
		if (this.getModel().getScrutunyEditMode() != null && this.getModel().getScrutunyEditMode().equals("SM")) {
			model.saveForm();
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
		}
		return jsonResult(JsonViewObject.successResult());

	}

	@RequestMapping(params = "editChangeInBusinessApplication", method = RequestMethod.POST)
	public @ResponseBody ModelAndView scrutinyInspectionLetter(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest) {
           //Defect #133748
		this.getModel().setAppid(applId);
		this.getModel().setLabelid(labelId);
		this.getModel().setServiceid(Long.valueOf(serviceId));
		this.getModel().setViewMode("N");
		this.getModel().setScrutunyEditMode("SM");
		this.getModel().getTradeMasterDetailDTO().setEditValue(true);
		this.getModel().getTradeMasterDetailDTO().setScrutinyAppFlag("SM");
		this.getModel().setValueEdit(true);
		
		return new ModelAndView("ChangeInBusinessNameFormView", MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		ChangeInBusinessNameFormModel model = this.getModel();
		String docStatus = new String();
		Organisation org = organisationService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.CHANGE_BUSINESS_NAME_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
		Long appId = null;
		if (model.getTradeMasterDetailDTO() != null
				&& model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
			appId = model.getTradeMasterDetailDTO().getApmApplicationId();
		}
		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			if (model.getTradeMasterDetailDTO() != null && appId != null) {
				List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
						.getBean(IChecklistVerificationService.class).getAttachDocumentByDocumentStatus(appId,
								docStatus, UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(documentUploaded)) {
					model.setDocumentList(documentUploaded);
				}
			}
			
		}
		// #129518 
		if (appId != null) {
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService
					.getCFCApplicationByApplicationId(appId, UserSession.getCurrent().getOrganisation().getOrgid());
			model.setCfcEntity(cfcEntity);
		}
		model.setApplicationId(appId);
		model.setApplicantName(model.getCfcEntity().getApmFname());
		model.setServiceName(model.getServiceMaster().getSmServiceName());
        // #129518
		model.setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
		model.setAppDate(new Date());
		model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
		model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
		
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL))
			return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		else
		return new ModelAndView("TradeLicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

}
