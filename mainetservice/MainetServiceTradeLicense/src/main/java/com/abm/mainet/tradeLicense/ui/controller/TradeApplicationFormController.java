package com.abm.mainet.tradeLicense.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
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
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TradeApplicationFormModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.TRADE_APPLICATION_FORM_HTML)
public class TradeApplicationFormController extends AbstractFormController<TradeApplicationFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("NTL",
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());

		this.getModel().getTradeMasterDetailDTO().setOrgId((UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getTradeMasterDetailDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
		this.getModel().getTradeMasterDetailDTO().setServiceId(serviceMaster.getSmServiceId());
		this.getModel().setServiceMaster(serviceMaster);
		//D#142725
		this.getModel().setFileMode(MainetConstants.FlagH);
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.AS,
				MainetConstants.TradeLicense.MLI, UserSession.getCurrent().getOrganisation());
		if (lookUp != null) {
			if (lookUp.getOtherField().equals(MainetConstants.Y_FLAG))
				this.getModel().setPropertyActiveStatus(MainetConstants.FlagY);
		}
        //D#145143
		this.getModel().setItcLevel(Utility.getLastLevel(MainetConstants.TradeLicense.ITC,  UserSession.getCurrent().getOrganisation()));
		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");

		// this.getModel().setLicMaxTenureDays(this.getModel().calculateLicMaxTenureDays(department.getDpDeptid(),
		// serviceMaster.getSmServiceId(), null,
		// UserSession.getCurrent().getOrganisation().getOrgid()));
		// Checking condition whether checkList applicable for particular Service.
   
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeMasterDetailDTO().setScrutinyAppFlag(MainetConstants.FlagN);
		}
		// #129248
		Organisation org = organisationService
				.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			this.getModel().setKdmcEnv(MainetConstants.FlagY);
		} else {
			this.getModel().setKdmcEnv(MainetConstants.FlagN);
		}
		// #140197
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			this.getModel().setSudaEnv(MainetConstants.FlagY);
		} else {
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		}
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_APPLICATION_FORM, MainetConstants.FORM_NAME,
				getModel());
	}

	/**
	 * TO view terms and condition page for trade license.
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.VIEW_TERMS_CONDITION)
	public ModelAndView tradeTermsCondition(final HttpServletRequest request) {
		bindModel(request);

		return new ModelAndView(MainetConstants.TradeLicense.TRADE_TERMS_CONDITION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	/**
	 * Save Trade License Form
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_TRADE_LICENSE_FORM, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();
		if (this.getModel().getScrutunyEditMode() != null && this.getModel().getScrutunyEditMode().equals("SM")) {
			model.saveForm();
		}
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}

	/**
	 * To get Ownership table
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @param ownershipType
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		TradeApplicationFormModel model = this.getModel();
		/* if (model.getTradeMasterDetailDTO().getApmApplicationId() == null) { */
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		// TradeLicenseOwnerDetailDTO());
		// }
		// set flag for Defect #111216
		// D#129514 for showing two grid at the time of Joint owner for SKDCL
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
			if (model.getTradeMasterDetailDTO().getApmApplicationId() == null
					&& (StringUtils.isEmpty(model.getViewMode()) || (!model.getViewMode().equals(MainetConstants.FlagV)
							&& !model.getViewMode().equals(MainetConstants.FlagB)))) {

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
		if (model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
			model.setDownloadMode(MainetConstants.FlagV);
		}
		model.setOwnershipPrefix(ownershipType);
		//D#142725
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)
				&& (model.getFileMode() != null && model.getFileMode().equals(MainetConstants.FlagH))) {

			return new ModelAndView("ThaneTradeDataEntryOwnership", MainetConstants.FORM_NAME, this.getModel());
		}
		return new ModelAndView(MainetConstants.TradeLicense.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME, model);
	}

	/**
	 * To get Property details from Property Number
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@ResponseBody
	@RequestMapping(params = MainetConstants.TradeLicense.GET_PROPERTY_DETAILS, method = RequestMethod.POST)
	public TradeMasterDetailDTO getPropertyDetails(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO tradeMasterDetailDTO = new TradeMasterDetailDTO();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		final LookUp lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue(MainetConstants.TradeLicense.AS,
				MainetConstants.TradeLicense.MLI, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		if (lookUp != null && lookUp.getOtherField().equals(MainetConstants.Y_FLAG)) {
			tradeDTO.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
			tradeDTO.setLangId(Long.valueOf(UserSession.getCurrent().getLanguageId()));
			tradeMasterDetailDTO = tradeLicenseApplicationService.getPropertyDetailsByPropertyNumber(tradeDTO);
			if (tradeMasterDetailDTO != null) {
				this.getModel().setTradeMasterDetailDTO(tradeMasterDetailDTO);
			}
		}
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

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.FILE_COUNT_UPLOAD)
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
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)) {

			return new ModelAndView("ThaneTradeDataEntryOwnership", MainetConstants.FORM_NAME, this.getModel());
		}else {
			return new ModelAndView(MainetConstants.TradeLicense.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
					this.getModel());
		}
	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.WorksManagement.ENTRY_DELETE)
	public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		// TradeApplicationFormModel model = this.getModel();
		/*
		 * List<TradeLicenseOwnerDetailDTO> own =
		 * this.getModel().getTradeMasterDetailDTO() .getTradeLicenseOwnerdetailDTO();
		 * own.remove(id);
		 * this.getModel().getTradeMasterDetailDTO().setTradeLicenseOwnerdetailDTO(own);
		 */
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					FileUploadUtility.getCurrent().getFileMap().remove((long) id);
				}

			}

		}

	}

	/**
	 * To get Cheklist and Charges from BRMS
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGES)
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getCheckListFromBrms();
		}
		if (this.getModel().getTradeMasterDetailDTO() != null && !StringUtils.isEmpty(this.getModel().getTradeMasterDetailDTO().getTrdFlatNo())) {
            //#144177
			String[] arr = this.getModel().getTradeMasterDetailDTO().getTrdFlatNo().split(",");
			if (arr != null && arr.length > 0 && arr[0] != null) {
				masDto.setTrdFlatNo(arr[0]);
			}
		}
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("NTL",
				UserSession.getCurrent().getOrganisation().getOrgid());

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {

			try {
				this.getModel().setTradeMasterDetailDTO(
						tradeLicenseApplicationService.getTradeLicenceAppChargesFromBrmsRule(masDto));

				this.getModel().getTradeMasterDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
			} catch (FrameworkException e) {
				ModelAndView mv = defaultResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));

				return mv;
			}

			this.getModel().getOfflineDTO()
					.setAmountToShow(Double.valueOf(this.getModel().getTradeMasterDetailDTO().getApplicationCharge()));
			this.getModel().getOfflineDTO()
					.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getApplicationCharge());
		}
		this.getModel().setPaymentCheck(MainetConstants.FlagY);

		// view mode
		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		this.getModel().setDownloadMode(MainetConstants.FlagM);
		this.getModel().setHideshowAddBtn(MainetConstants.FlagY);
		this.getModel().setHideshowDeleteBtn(MainetConstants.FlagY);
		this.getModel().setTemporaryDateHide(MainetConstants.FlagD);
		this.getModel().getdataOfUploadedImage();
		return defaultResult();

	}

	/**
	 * Generate challan and receipt method
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		getModel().bind(httpServletRequest);
		TradeApplicationFormModel model = this.getModel();
		List<DocumentDetailsVO> docs = model.getCheckList();
		List<DocumentDetailsVO> ownerDocs = model.getTradeMasterDetailDTO().getAttachments();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (ownerDocs != null) {
			ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
		}

		model.getTradeMasterDetailDTO().setDocumentList(docs);
		model.getTradeMasterDetailDTO().setAttachments(ownerDocs);
		fileUpload.validateUpload(model.getBindingResult());
		if (model.validateInputs()) {
			try {
				if (model.saveForm()) {
					return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

				} else
					return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
			} catch (FrameworkException e) {

			}
		}
		return defaultMyResult();
	}

	/**
	 * Show charge details Page
	 */
	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND,
				getModel());
	}

	@RequestMapping(params = "editNewTradeApplication", method = RequestMethod.POST)
	public @ResponseBody ModelAndView scrutinyInspectionLetter(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest) {
		this.getModel().setAppid(applId);
		this.getModel().setLabelid(labelId);
		this.getModel().setServiceid(Long.valueOf(serviceId));
		this.getModel().setScrutunyEditMode("SM");

//D#130231 for editing value at the time of scruting view edi
		this.getModel().setValueEdit(true);
		// Defect #133748
		this.getModel().setViewMode("E");
		this.getModel().setValueEdit(true);
		this.getModel().getTradeMasterDetailDTO().setEditValue(true);
		this.getModel().getTradeMasterDetailDTO()
				.setTotalApplicationFee(BigDecimal.valueOf(this.getModel().getTradeMasterDetailDTO()
						.getTradeLicenseItemDetailDTO().stream().filter(c -> c.getTriRate() != null)
						.mapToDouble(c -> c.getTriRate().doubleValue()).sum()));
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_APPLICATION_FORM_VIEW, MainetConstants.FORM_NAME,
				this.getModel());

	}

	/**
	 * Edit trade license application form
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
	public ModelAndView backEditPageForm(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().setPaymentCheck(MainetConstants.FlagA);
		this.getModel().setViewMode(MainetConstants.FlagB);
		this.getModel().setOpenMode(MainetConstants.FlagY);
		this.getModel().setDownloadMode(MainetConstants.FlagN);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)) {
			this.getModel().setSudaEnv(MainetConstants.FlagY);
		} else {
			this.getModel().setSudaEnv(MainetConstants.FlagN);
		}
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_EDIT, MainetConstants.FORM_NAME,
				this.getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		TradeApplicationFormModel model = this.getModel();
		String docStatus = new String();
		Organisation org = organisationService
				.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.SERVICE_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			if (model.getTradeMasterDetailDTO() != null
					&& model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
				List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
						.getBean(IChecklistVerificationService.class)
						.getAttachDocumentByDocumentStatus(model.getTradeMasterDetailDTO().getApmApplicationId(),
								docStatus, UserSession.getCurrent().getOrganisation().getOrgid());
				if (CollectionUtils.isNotEmpty(documentUploaded)) {
					model.setDocumentList(documentUploaded);
				}

			}
		}
		// #129518
		if (model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
					model.getTradeMasterDetailDTO().getApmApplicationId(),
					UserSession.getCurrent().getOrganisation().getOrgid());
			model.setCfcEntity(cfcEntity);
			model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
		}
		if (model.getCfcEntity() != null && model.getCfcEntity().getApmFname() !=null)
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

	@RequestMapping(method = RequestMethod.POST, params = "LicenseType")
	public @ResponseBody Long getLicenseValidity(@RequestParam("licenseType") final long licenseType,
			@RequestParam("triCod1") final long triCod1, final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");

		this.getModel().getTradeMasterDetailDTO().setTrdLictype(licenseType);

		return this.getModel().calculateLicMaxTenureDays(department.getDpDeptid(),
				this.getModel().getServiceMaster().getSmServiceId(), null,
				UserSession.getCurrent().getOrganisation().getOrgid(), triCod1);

	}

	@RequestMapping(method = RequestMethod.POST, params = "getOtherValue")
	public @ResponseBody Long getOtherValue(@RequestParam("itemCode1") final long itemCode1,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		List<LookUp> lookupListLevel1 = new ArrayList<LookUp>();
		lookupListLevel1 = CommonMasterUtility.getNextLevelData(MainetConstants.TradeLicense.ITC, 1,
				UserSession.getCurrent().getOrganisation().getOrgid());

		List<LookUp> level1 = lookupListLevel1.parallelStream()
				.filter(clList -> clList != null && clList.getLookUpId() == itemCode1).collect(Collectors.toList());

		String otherField = level1.get(0).getOtherField();
		if (otherField != null && !otherField.isEmpty()) {

			if (MainetConstants.FlagY.equalsIgnoreCase(otherField)) {

				return 1l;
			}
		}

		return null;

	}

	// Defect #112673
	@ResponseBody
	@RequestMapping(params = "checkRefNoValidOrNot", method = RequestMethod.POST)
	public Boolean checkRefNoValidOrNot(@RequestParam("referenceNo") String referenceNo) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		boolean result = tradeLicenseApplicationService.checkLicensePresent(referenceNo, orgId);
		if (result == true) {
			return true;
		}
		return false;
	}

	// #124288

	@ResponseBody
	@RequestMapping(params = "fetchPropertyFlatNo", method = RequestMethod.POST)
	public List<String> fetchPropertyFlatNo(@RequestParam("propNo") final String propNo,
			final HttpServletRequest request) {
		getModel().bind(request);
		List<String> flatNoList = tradeLicenseApplicationService.getPropertyFlatNo(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setFlatNoList(flatNoList);
		return flatNoList;
	}

}
