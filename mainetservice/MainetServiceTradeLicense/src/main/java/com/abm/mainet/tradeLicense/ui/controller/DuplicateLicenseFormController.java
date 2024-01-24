package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.DuplicateLicenseFormModel;

@Controller
@RequestMapping("/DuplicateLicenseForm.html")
public class DuplicateLicenseFormController extends AbstractFormController<DuplicateLicenseFormModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IDuplicateLicenseApplicationService duplicateLicenseApplicationService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());

		getModel().setServiceMaster(sm);
		final LookUp lookup = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmProcessId(),
				UserSession.getCurrent().getOrganisation());
		if (!lookup.getLookUpCode().equals(MainetConstants.CommonConstants.NA)) {
			this.getModel().setImmediateServiceMode(MainetConstants.FlagY);
		} else {
			this.getModel().setImmediateServiceMode(MainetConstants.FlagN);
		}
		return new ModelAndView(MainetConstants.TradeLicense.DUPLICATE_LICENSE_FORM, MainetConstants.FORM_NAME,
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
		DuplicateLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);

		// 124306 for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkRejectedInTaskflow(tradeDTO.getApmApplicationId(), orgId);
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
		// for setting only approved Buisness name
		if (trdLicno != null) {
			Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
			if ((tradeDTO != null && tradeDTO.getTrdStatus() != null) && !tradeDTO.getTrdStatus().equals(lookUpId)) {
				String troName = tradeLicenseApplicationService.getApprovedBuisnessName(trdLicno, orgId, lookUpId);
				if (troName != null) {
					tradeDTO.setTrdBusnm(troName);
				}
			}
			//Defect #156183
			if ((tradeDTO != null && tradeDTO.getTrdStatus() != null) && !tradeDTO.getTrdStatus().equals(lookUpId)) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.licenseStatus"));
				return defaultMyResult();
			}

		}
		// for filtering only Approval Item Defect#107828
		if (tradeDTO != null) {
			model.setTradeMasterDetailDTO(tradeDTO);
			model.getTradeMasterDetailDTO().setTradeLicenseItemDetailDTO(tradeDTO.getTradeLicenseItemDetailDTO()
					.stream()
					.filter(trdItem -> trdItem != null
							&& (trdItem.getTriStatus() != null && trdItem.getTriStatus().equals(MainetConstants.FlagA)))
					.collect(Collectors.toList()));
			// Defect#106851
			if (!model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
				// Defect #108769
				StringBuilder ownName = new StringBuilder();
				String ownerName = "";
				List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = model.getTradeMasterDetailDTO()
						.getTradeLicenseOwnerdetailDTO();

				if (CollectionUtils.isNotEmpty(ownerDetailDTOList)) {

					for (TradeLicenseOwnerDetailDTO ownDto : ownerDetailDTOList) {
						if (StringUtils.isNotBlank(ownDto.getTroName()))
							ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
				}

				if (ownName.length() > 0) {
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				}
				if (StringUtils.isNotBlank(ownerName)) {
					model.setOwnerName(ownerName);
				}
			}

		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			// model.setChecklistCheck(MainetConstants.FlagY);
		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}
		return defaultMyResult();

	}

	/**
	 * Get License details From License Number and Mobile Number
	 * 
	 * @param httpServletRequest
	 * @param trdLicno
	 * @return
	 */
	@RequestMapping(params = "getLicenseDetailbyLicAndMobile", method = RequestMethod.POST)
	public ModelAndView getLicenseDetailbyLicAndMobile(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno,@RequestParam("mobileNo") String mobileNo) {
		this.getModel().bind(httpServletRequest);
		DuplicateLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		List<TradeMasterDetailDTO> searchList = tradeLicenseApplicationService.getLicenseDetailbyLicAndMobile(trdLicno, mobileNo, orgId);
		this.getModel().setListTradeMasterDetailDTO(searchList);
		// 124306 for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkRejectedInTaskflow(tradeDTO.getApmApplicationId(), orgId);
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
		// for setting only approved Buisness name
		if (trdLicno != null) {
			Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
			if ((tradeDTO != null && tradeDTO.getTrdStatus() != null) && !tradeDTO.getTrdStatus().equals(lookUpId)) {
				String troName = tradeLicenseApplicationService.getApprovedBuisnessName(trdLicno, orgId, lookUpId);
				if (troName != null) {
					tradeDTO.setTrdBusnm(troName);
				}
			}
			//Defect #156183
			if ((tradeDTO != null && tradeDTO.getTrdStatus() != null) && !tradeDTO.getTrdStatus().equals(lookUpId)) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.valid.licenseStatus"));
				return defaultMyResult();
			}

		}
		// for filtering only Approval Item Defect#107828
		if (tradeDTO != null) {
			model.setTradeMasterDetailDTO(tradeDTO);
			model.getTradeMasterDetailDTO().setTradeLicenseItemDetailDTO(tradeDTO.getTradeLicenseItemDetailDTO()
					.stream()
					.filter(trdItem -> trdItem != null
							&& (trdItem.getTriStatus() != null && trdItem.getTriStatus().equals(MainetConstants.FlagA)))
					.collect(Collectors.toList()));
			// Defect#106851
			if (!model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
				// Defect #108769
				StringBuilder ownName = new StringBuilder();
				String ownerName = "";
				List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = model.getTradeMasterDetailDTO()
						.getTradeLicenseOwnerdetailDTO();

				if (CollectionUtils.isNotEmpty(ownerDetailDTOList)) {

					for (TradeLicenseOwnerDetailDTO ownDto : ownerDetailDTOList) {
						if (StringUtils.isNotBlank(ownDto.getTroName()))
							ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
					}
				}

				if (ownName.length() > 0) {
					ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				}
				if (StringUtils.isNotBlank(ownerName)) {
					model.setOwnerName(ownerName);
				}
			}

		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			// model.setChecklistCheck(MainetConstants.FlagY);
		}
		return defaultMyResult();

	}
	/**
	 * Get charges from BRMS rule
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGES)
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		DuplicateLicenseFormModel model = getModel();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());

		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(sm.getSmChklstVerify(),
				UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			model.getCheckListFromBrms();
			model.setChecklistCheck(MainetConstants.FlagY);
		} else {
			model.setChecklistCheck(MainetConstants.FlagN);
		}
		if (sm.getSmFeesSchedule().longValue() != 0) {
			if (sm.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
				model.setTradeMasterDetailDTO(
						duplicateLicenseApplicationService.getAppDuplicateChargesFromBrmsRule(masDto));
				model.getOfflineDTO().setAmountToShow(
						(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
				model.getOfflineDTO()
						.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee().toString());
				model.setPaymentCheck(MainetConstants.FlagY);
			}else {
				model.setPaymentCheck(MainetConstants.FlagN);
			}
		}
		if (sm.getSmFeesSchedule().longValue() == 0) {

			model.setPaymentCheck(MainetConstants.FlagN);

		}

		return defaultMyResult();

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND,
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
		DuplicateLicenseFormModel model = this.getModel();
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

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_DUPLICATE_LICENSE_PRINT)
	public ModelAndView duplicateLicensePrint(final HttpServletRequest request) {

		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		final DuplicateLicenseFormModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		final List<LookUp> lookupList = CommonMasterUtility.getListLookup("MWZ", org);

		if (masDto.getTrdWard1() != null) {
			model.setTrdWard1Desc(lookupList.get(0).getLookUpDesc());
			model.setWard1Level(
					CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard1(), org).getDescLangFirst());
		}
		if (masDto.getTrdWard2() != null) {
			model.setTrdWard2Desc(lookupList.get(0).getLookUpDesc());
			model.setWard2Level(
					CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard2(), org).getDescLangFirst());
		}
		if (masDto.getTrdWard3() != null) {
			model.setTrdWard3Desc(lookupList.get(0).getLookUpDesc());
			model.setWard3Level(
					CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard3(), org).getDescLangFirst());

		}
		if (masDto.getTrdWard4() != null) {
			model.setTrdWard4Desc(lookupList.get(0).getLookUpDesc());
			model.setWard4Level(
					CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard4(), org).getDescLangFirst());
		}
		if (masDto.getTrdWard5() != null) {
			model.setTrdWard5Desc(lookupList.get(0).getLookUpDesc());
			model.setWard5Level(
					CommonMasterUtility.getHierarchicalLookUp(masDto.getTrdWard5(), org).getDescLangFirst());
		}

		LookUp lookup = CommonMasterUtility
				.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);
		model.setCategoryDesc(lookup.getDescLangFirst());
		
		LookUp subCategory = CommonMasterUtility
				.getHierarchicalLookUp(masDto.getTradeLicenseItemDetailDTO().get(0).getTriCod2(), orgId);
		model.setSubCategoryDesc(subCategory.getDescLangFirst());

		/*
		 * TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
		 * .getCFCApplicationByApplicationId(applicationId, orgId);
		 */
		model.setTradeDetailDTO(masDto);
		Date licenseEndDate = masDto.getTrdLictoDate();
		if (licenseEndDate != null) {
			masDto.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		model.setLicenseFromDateDesc(Utility.dateToString(masDto.getTrdLicfromDate()));
		model.setDateDesc(Utility.dateToString(new Date()));
		List<CFCAttachment> imgList = new ArrayList<>();
		if (model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId() != null)
			imgList = checklistVerificationService.getDocumentUploadedByRefNo(
					model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);
		model.setDocumentList(imgList);
		if (!imgList.isEmpty() && imgList != null) {
			model.setImagePath(getPropImages(imgList.get(0)));
		}
		Long artId = 0l;
		final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp Lookup1 : lookUpList) {

			if (Lookup1.getLookUpCode().equals(PrefixConstants.WATERMODULEPREFIX.APP)) {
				artId = Lookup1.getLookUpId();
			}
		}
		List<TbApprejMas> apprejMasList = tbApprejMasService.findByRemarkType(model.getServiceMaster().getSmServiceId(),
				artId);
		model.setApprejMasList(apprejMasList);

		List<TbLoiMas> loiMas = duplicateLicenseApplicationService.getTotalAmount(masDto.getTrdLicno());
		if (CollectionUtils.isNotEmpty(loiMas)) {
			model.setRmAmount(loiMas.get(0).getLoiAmount());
		}
		// Defect#106851
		if (masDto != null && !masDto.getTradeLicenseOwnerdetailDTO().isEmpty()) {
			String[] s = masDto.getTradeLicenseOwnerdetailDTO().get(0).getTroName().split(",");
			masDto.getTradeLicenseOwnerdetailDTO().get(0).setTroName(s[0]);
		}
		model.setTradeDetailDTO(masDto);

		return new ModelAndView("DuplicateTradeLicenseCertificatePrint", MainetConstants.FORM_NAME, this.getModel());

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

	// Defect #109584
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, params = "checkWorkflow")
	public Boolean checkWorkflow(final HttpServletRequest request) {
		getModel().bind(request);
		final DuplicateLicenseFormModel model = getModel();
		String processName = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getProcessName(model.getServiceMaster().getSmServiceId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
		if (processName == null || (processName != null && processName.equals(MainetConstants.NOT_APPLICABLE))) {
			return true;
		} else {
			return false;
		}
	}

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		DuplicateLicenseFormModel model = this.getModel();
		String docStatus = new String();
		Organisation org = organisationService
				.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		Long appId = null;
		if (model.getTradeMasterDetailDTO() != null && model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
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
		if (appId != null) {
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(appId,
					UserSession.getCurrent().getOrganisation().getOrgid());
			model.setCfcEntity(cfcEntity);

			model.setApplicationId(appId);
		}
		model.setApplicantName(model.getCfcEntity().getApmFname());
		model.setServiceName(model.getServiceMaster().getSmServiceName());
		// #129518
		model.setDepartmentName(serviceMas.getTbDepartment().getDpDeptdesc());
		model.setAppDate(new Date());
		model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		if (serviceMas.getSmServiceDuration() != null)
			model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(), serviceMas.getSmServiceDuration().intValue()));

		// #129518
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL))
			return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		else
			return new ModelAndView("TradeLicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());

	}

}