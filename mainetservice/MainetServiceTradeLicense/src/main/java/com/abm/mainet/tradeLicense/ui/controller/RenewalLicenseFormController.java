package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.service.DepartmentService;
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
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicenseFormModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_HTML)
public class RenewalLicenseFormController extends AbstractFormController<RenewalLicenseFormModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private ServiceMasterService serviceMasterService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	IRenewalLicenseApplicationService iRenewalLicenseApplicationService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);

		RenewalLicenseFormModel model = this.getModel();

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());

		model.setServiceMaster(sm);

		if (sm.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (sm.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setScrutinyAppFlag(MainetConstants.FlagN);
		}
		final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
		if (!lookup.getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
			this.getModel().setImmediateServiceMode(MainetConstants.FlagY);
		} else {
			this.getModel().setImmediateServiceMode(MainetConstants.FlagN);
		}

		model.getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagN);
		model.getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		//US#140275
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			this.getModel().setSudaEnv(MainetConstants.FlagY);
		} else {
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		}

		return new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM, MainetConstants.FORM_NAME,
				getModel());

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
		RenewalLicenseFormModel model = getModel();
		ModelAndView mv = null;
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setFilterType(null);
		boolean flag = false;
		Boolean isExist = false;
		if (trdLicno != null && !trdLicno.isEmpty()) {
			tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
			// #129518 to check license is present under objection process
			isExist = iRenewalLicenseApplicationService.checkLicenseNoExist(trdLicno, orgId);
		}
		if (isExist) {
			model.addValidationError(ApplicationSession.getInstance().getMessage("valid.obj.licNo"));
			flag = true;
		}
		// for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkRejectedInTaskflow(tradeDTO.getApmApplicationId(), orgId);
		}

		// Defect #110124 added code for checking licnce canceled or not and throwing
		// validation
		if (tradeDTO != null && tradeDTO.getTrdStatus() != null) {
			model.setTradeMasterDetailDTO(tradeDTO);
			//US#140275-outstanding amount check against property for suda
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),MainetConstants.ENV_SUDA)) {
				TradeMasterDetailDTO dto = tradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(tradeDTO);
				if (dto != null && dto.getTotalOutsatandingAmt() > 0)
					model.getTradeMasterDetailDTO().setTotalOutsatandingAmt(dto.getTotalOutsatandingAmt());
				if (dto != null && StringUtils.isNotEmpty(dto.getPrimaryOwnerName()))
					model.getTradeMasterDetailDTO().setPrimaryOwnerName(dto.getPrimaryOwnerName());
			}
			// Defect #110792
			if (model.getTradeMasterDetailDTO() != null
					&& !CollectionUtils.isEmpty(model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO())) {
				StringBuilder ownName = new StringBuilder();
				String ownerName = "";
				for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeMasterDetailDTO()
						.getTradeLicenseOwnerdetailDTO()) {
					if (StringUtils.isNotBlank(ownDto.getTroName()))
						ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
				}
				if (StringUtils.isNotBlank(ownName.toString())) {
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				}
				if (StringUtils.isNotBlank(ownerName)) {
					model.setOwnerName(ownerName);
				}
			}
			LookUp licenseStatus = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
					UserSession.getCurrent().getOrganisation());
			LookUp issuedlicenseStatus = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
					UserSession.getCurrent().getOrganisation());
			// add for Defect #111784 for throwing validation in case of license cancel or
			// not approved
			LookUp licType = null;
			if (tradeDTO.getTrdLictype() != null) {
				licType = CommonMasterUtility.getNonHierarchicalLookUpObject(tradeDTO.getTrdLictype());
			}
			if (licenseStatus != null && tradeDTO.getTrdStatus().equals(licenseStatus.getLookUpId()) && !flagCheck) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.cancel"));

			} else if (issuedlicenseStatus != null && !tradeDTO.getTrdStatus().equals(issuedlicenseStatus.getLookUpId())
					&& !flagCheck) {
				flag = true;

				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.licenseStatus"));

			} else if (licType != null && licType.getLookUpCode() != null
					&& licType.getLookUpCode().equals(MainetConstants.FlagT)) {
				flag = true;
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.licNo.temp.ren"));

			}
			// Defect #121660
			else if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
					MainetConstants.ENV_SKDCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
							MainetConstants.ENV_TSCL)) {
                //User Story #133712
				double renPendDays = Utility.getDaysBetDates(tradeDTO.getTrdLictoDate(), new Date());
				if (renPendDays != 0) {
					tradeDTO.setRenewalPendingDays((long) Math.ceil((renPendDays / MainetConstants.DAS_IN_YEAR)));
					tradeDTO.setRenewCycle((long) Math.ceil((renPendDays / MainetConstants.DAS_IN_YEAR)));
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

		}
		/*
		 * // added code for filtering only approve trade item catagory if (tradeDTO !=
		 * null) { List<TradeLicenseItemDetailDTO> itemDto =
		 * tradeLicenseApplicationService
		 * .getTradeLicenseHistDetBuTrdId(tradeDTO.getTradeLicenseItemDetailDTO().get(0)
		 * .getTriId()); if
		 * (tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus().equals(
		 * MainetConstants.FlagM)) { itemDto.get(itemDto.size() -
		 * 2).setItemCategory1(MainetConstants.FlagA);
		 * 
		 * tradeDTO.getTradeLicenseItemDetailDTO().add(0, itemDto.get(itemDto.size() -
		 * 2)); } else {
		 * tradeDTO.getTradeLicenseItemDetailDTO().get(0).setItemCategory1(
		 * MainetConstants.FlagA); } List<TradeLicenseItemDetailDTO> trdItemList =
		 * tradeDTO.getTradeLicenseItemDetailDTO().parallelStream() .filter(trd -> trd
		 * != null && (trd.getTriStatus().equalsIgnoreCase(MainetConstants.FlagA) ||
		 * (trd.getItemCategory1() != null &&
		 * trd.getItemCategory1().equals(MainetConstants.FlagA))))
		 * .collect(Collectors.toList());
		 * tradeDTO.setTradeLicenseItemDetailDTO(trdItemList); }
		 */

		if (flag) {
			// for hide proceed and submit button
			model.setFilterType("V");
			// model.setTradeMasterDetailDTO(new TradeMasterDetailDTO());
			model.getTradeMasterDetailDTO().setTrdLicno(tradeDTO.getTrdLicno());
			mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}

		if (tradeDTO == null)

		{
			mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
					this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
			model.setFilterType("V");
			return mv;

		} else {
			model.getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagN);
			model.getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);

			mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
					this.getModel());

			return mv;
		}

	}

	/**
	 * Get charges from BRMS rule
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHARGES_FROM_BRMS)
	public ModelAndView getCharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		final RenewalLicenseFormModel model = getModel();
		TradeMasterDetailDTO masDto = model.getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("RTL",
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (model.getTradeMasterDetailDTO().getTrdStatus() == null) {
			ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
					MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.search.msg"));
			return mv;

		}
		if (!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			Long renewalPeriod = model.getTradeMasterDetailDTO().getRenewalMasterDetailDTO().getRenewalPeriod();

			if (renewalPeriod != null) {
				LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(renewalPeriod,
						model.getUserSession().getOrganisation().getOrgid(), "LRP");

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

		LookUp licenseType = CommonMasterUtility.getNonHierarchicalLookUpObject(masDto.getTrdLictype(),
				UserSession.getCurrent().getOrganisation());

		LookUp licenseStatus = CommonMasterUtility.getValueFromPrefixLookUp("T", "LIS",
				UserSession.getCurrent().getOrganisation());

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS",
				UserSession.getCurrent().getOrganisation());

		if (model.getTradeMasterDetailDTO().getTrdStatus() != lookUp.getLookUpId()) {

			LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
					serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
			model.setPaymentCheck(MainetConstants.FlagN);
			if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {

				this.getModel().getCheckListFromBrms();
				this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
			} else {
				this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
			}
			if (model.hasValidationErrors()) {
				model.setPaymentCheck(null);
			}

			if (serviceMaster.getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.FlagY)) {

				List<TradeLicenseItemDetailDTO> tradeLicenseItemDetailDTO = masDto.getTradeLicenseItemDetailDTO();

				masDto.setTradeLicenseItemDetailDTO(tradeLicenseItemDetailDTO);

				try {
					model.setTradeMasterDetailDTO(
							iRenewalLicenseApplicationService.getTradeLicenceChargesAtApplicationFromBrmsRule(masDto));
					model.setPaymentCheck(MainetConstants.FlagY);
					model.getOfflineDTO()
							.setAmountToShow(Double.valueOf(model.getTradeMasterDetailDTO().getApplicationCharge()));
					model.getOfflineDTO()
							.setAmountToPay(model.getTradeMasterDetailDTO().getApplicationCharge().toString());

				} catch (FrameworkException e) {

					ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
							MainetConstants.FORM_NAME, this.getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));

					if (model.hasValidationErrors()) {
						model.setPaymentCheck(null);
					}

					return mv;

				}

			}
			model.setTradeMasterDetailDTO(masDto);
			if (serviceMaster.getSmScrutinyApplicableFlag().equalsIgnoreCase(MainetConstants.FlagY)) {

				model.getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagY);

			}
			if (serviceMaster.getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.FlagY)) {

				model.getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);

			}
		}

		else {
			ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN,
					MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.cancle"));

			return mv;
		}

		return defaultMyResult();

	}

	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_RENEWAL_LICENSE, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveRenewalLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		RenewalLicenseFormModel model = this.getModel();
		ModelAndView mv = null;
		TradeMasterDetailDTO masDto = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(masDto.getTrdLicno(), orgId);
		List<DocumentDetailsVO> docs = model.getCheckList();

		if (docs != null) {

			docs = fileUpload.prepareFileUpload(docs);
		}

		model.getTradeMasterDetailDTO().setDocumentList(docs);

		mv = new ModelAndView(MainetConstants.TradeLicense.RENEWAL_LICENSE_FORM_VALIDN, MainetConstants.FORM_NAME,
				this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("RenewalLicenseChargesDetails", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	/**
	 * generate Payment mode and save renewal license
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public @ResponseBody ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		RenewalLicenseFormModel model = this.getModel();

		List<DocumentDetailsVO> docs = model.getCheckList();

		if (docs != null) {

			docs = fileUpload.prepareFileUpload(docs);
		}

		model.getTradeMasterDetailDTO().setDocumentList(docs);

		fileUpload.validateUpload(model.getBindingResult());

		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return defaultMyResult();
		}
		return defaultMyResult();
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
	public ModelAndView backEditPageForm(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setPaymentCheck(MainetConstants.FlagN);

		return new ModelAndView("RenewalLicenseEditForm", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(method = RequestMethod.POST, params = { "updateTransferLicenseForm" })
	public ModelAndView updateTransferLicenseDetails(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		RenewalLicenseFormModel model = this.getModel();

		try {

			if (model.updateForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		} catch (FrameworkException e) {

			ModelAndView mv = new ModelAndView("RenewalLicenseEditForm", MainetConstants.FORM_NAME, this.getModel());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(e.getMessage());

			return mv;

		}

	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		RenewalLicenseFormModel model = this.getModel();
		String docStatus = new String();
		Organisation org = organisationService
				.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.RENEWAL_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long appId = null;
		if (model.getTradeMasterDetailDTO() != null
				&& model.getTradeMasterDetailDTO().getRenewalMasterDetailDTO() != null) {
			appId = model.getTradeMasterDetailDTO().getRenewalMasterDetailDTO().getApmApplicationId();
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
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(appId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			model.setCfcEntity(cfcEntity);
		}
		model.setApplicationId(appId);
		model.setApplicantName(model.getCfcEntity().getApmFname());
		model.setServiceName(model.getServiceMaster().getSmServiceName());
		model.setDepartmentName(model.getServiceMaster().getTbDepartment().getDpDeptdesc());
		model.setAppDate(new Date());
		model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
			model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(), serviceMas.getSmServiceDuration().intValue()));

		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL))
			return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		else
			return new ModelAndView("TradeLicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

}