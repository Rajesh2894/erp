package com.abm.mainet.tradeLicense.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IHawkerLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.HawkerLicenseApplicationFormModel;

@Controller
@RequestMapping("/HawkerLicenseApplicationForm.html")
public class HawkerLicenseApplicationFormController extends AbstractFormController<HawkerLicenseApplicationFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	IHawkerLicenseApplicationService iHawkerLicenseApplicationService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	@Autowired
	private ILocationMasService locationMasService;
	@Resource
	private TbBankmasterService banksMasterService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("NHL",
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().getTradeMasterDetailDTO().setOrgId((UserSession.getCurrent().getOrganisation().getOrgid()));
		this.getModel().getTradeMasterDetailDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
		this.getModel().getTradeMasterDetailDTO().setServiceId(serviceMaster.getSmServiceId());
		this.getModel().setServiceMaster(serviceMaster);

		LookUp lookup = CommonMasterUtility.getDefaultValue("FPT");

		this.getModel().getTradeMasterDetailDTO().setTrdFtype(lookup.getLookUpId());

		LookUp licenseType = CommonMasterUtility.getDefaultValue("LIT");

		this.getModel().getTradeMasterDetailDTO().setTrdLictype(licenseType.getLookUpId());
		this.getModel().getTradeMasterDetailDTO().setTrdLicfromDate(new Date());

		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");

		this.getModel().getTradeMasterDetailDTO().setTrdLictype(licenseType.getLookUpId());

		Long calculateLicMaxTenureDays = this.getModel().calculateLicMaxTenureDays(department.getDpDeptid(),
				this.getModel().getServiceMaster().getSmServiceId(), null,
				UserSession.getCurrent().getOrganisation().getOrgid());
		final Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + calculateLicMaxTenureDays.intValue());

		Date todate = calendar.getTime();
		this.getModel().getTradeMasterDetailDTO().setTrdLictoDate(todate);

		List<TbLocationMas> locationList = locationMasService.getlocationByDeptId(
				serviceMaster.getTbDepartment().getDpDeptid(), UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setLocationList(locationList);

		final Map<Long, List<String>> bankMap = new HashMap<>();
		final List<Object[]> blist = banksMasterService.findActiveBankList();
		for (final Object[] obj : blist) {
			List<String> bankList = new ArrayList<>();
			bankList.add(obj[1] + MainetConstants.SEPARATOR + obj[2]);
			bankList.add(obj[3].toString());
			bankMap.put((Long) obj[0], bankList);
		}

		this.getModel().setCustBankList(bankMap);

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

		return new ModelAndView("HawkerLicenseApplicationForm", MainetConstants.FORM_NAME, getModel());
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
	@RequestMapping(params = "savehawkerLicenseApplicationForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		HawkerLicenseApplicationFormModel model = this.getModel();
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

		HawkerLicenseApplicationFormModel model = this.getModel();
		/* if (model.getTradeMasterDetailDTO().getApmApplicationId() == null) { */
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		// model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		// TradeLicenseOwnerDetailDTO());
		// }
		model.setOwnershipPrefix(ownershipType);
		return new ModelAndView("HawkerOwnershipTable", MainetConstants.FORM_NAME, model);
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
		return new ModelAndView(MainetConstants.TradeLicense.OWNERSHIP_DETAIL_TABLE, MainetConstants.FORM_NAME,
				this.getModel());
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

	@RequestMapping(method = RequestMethod.POST, params = "doItemDeletion")
	public void doItemDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		HawkerLicenseApplicationFormModel model = this.getModel();

		List<TradeLicenseItemDetailDTO> item = this.getModel().getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO();
		item.remove(id);
		this.getModel().getTradeMasterDetailDTO().setTradeLicenseItemDetailDTO(item);

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
		HawkerLicenseApplicationFormModel model = this.getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		model.setPaymentCheck(MainetConstants.FlagN);
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("NHL",
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().getCheckListFromBrms();
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {

			try {

				this.getModel().setTradeMasterDetailDTO(
						iHawkerLicenseApplicationService.getTradeLicenceAppChargesFromBrmsRule(masDto));

				this.getModel().getOfflineDTO().setAmountToShow(
						Double.valueOf(this.getModel().getTradeMasterDetailDTO().getApplicationCharge()));
				this.getModel().getOfflineDTO()
						.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getApplicationCharge());
				this.getModel().setPaymentCheck(MainetConstants.FlagY);

			} catch (FrameworkException e) {
				ModelAndView mv = defaultResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));
				if (model.hasValidationErrors()) {
					model.setPaymentCheck(null);
				}
				return mv;
			}

		}
		if (model.hasValidationErrors()) {
			model.setPaymentCheck(null);
		}
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
		HawkerLicenseApplicationFormModel model = this.getModel();
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
		return new ModelAndView("HawkerLicenseApplicationFormEdit", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		HawkerLicenseApplicationFormModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
					.getBean(IChecklistVerificationService.class)
					.getAttachDocumentByDocumentStatus(model.getTradeMasterDetailDTO().getApmApplicationId(), docStatus,
							UserSession.getCurrent().getOrganisation().getOrgid());
			if (CollectionUtils.isNotEmpty(documentUploaded)) {
				model.setDocumentList(documentUploaded);
			}
		}
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
				model.getTradeMasterDetailDTO().getApmApplicationId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setCfcEntity(cfcEntity);
		model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
		model.setApplicantName(model.getCfcEntity().getApmFname());
		model.setServiceName(model.getServiceMaster().getSmServiceName());
		return new ModelAndView("HawkerApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(method = RequestMethod.POST, params = "LicenseType")
	public @ResponseBody Long getLicenseValidity(@RequestParam("licenseType") final long licenseType,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA, "ML");

		this.getModel().getTradeMasterDetailDTO().setTrdLictype(licenseType);

		return this.getModel().calculateLicMaxTenureDays(department.getDpDeptid(),
				this.getModel().getServiceMaster().getSmServiceId(), null,
				UserSession.getCurrent().getOrganisation().getOrgid());

	}

	@RequestMapping(method = RequestMethod.POST, params = "AdharNumber")
	public @ResponseBody Long validateAdharNumber(@RequestParam("adharNumber") final Long adharNumber,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		return iHawkerLicenseApplicationService.validateAdharNumber(adharNumber,
				UserSession.getCurrent().getOrganisation().getOrgid());

	}

}
