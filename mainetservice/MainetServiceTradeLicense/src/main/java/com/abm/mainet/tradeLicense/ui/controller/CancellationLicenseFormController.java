package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
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
import com.abm.mainet.tradeLicense.dto.TradeLicenseItemDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ICancellationLicenseService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseFormModel;
import com.abm.mainet.tradeLicense.ui.model.ChangeInBusinessNameFormModel;

@Controller
@RequestMapping("/CancellationLicenseForm.html")
public class CancellationLicenseFormController extends AbstractFormController<CancellationLicenseFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private ICancellationLicenseService cancellationLicenseService;
	
	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		// Defect #108699
		this.getModel().getTradeDetailDTO().setDeptId(serviceMaster.getTbDepartment().getDpDeptid());
		this.getModel().getTradeDetailDTO().setServiceId(serviceMaster.getSmServiceId());
		this.getModel().setServiceMaster(serviceMaster);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
		// code changed for showing only issued data and if any license in process of
		// CBN service it can't be show in drop down
		this.getModel().setTradeMasterDetailDTO(tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId));

		return new ModelAndView("CancellationLicenseForm", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = "saveCancellationLicenseForm", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		CancellationLicenseFormModel model = this.getModel();
		if (this.getModel().getScrutunyEditMode() != null && this.getModel().getScrutunyEditMode().equals("SM")) {
			model.saveForm();
		}
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		CancellationLicenseFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = organisationService.getOrganisationById(orgId);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		// for filtering only Approval Item Defect#107828
		if (tradeDTO != null) {
		/*	List<TradeLicenseItemDetailDTO> itemDto = tradeLicenseApplicationService
					.getTradeLicenseHistDetBuTrdId(tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriId());
			if (tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus() != null
					&& tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus().equals(MainetConstants.FlagM)) {
				itemDto.get(itemDto.size() - 2).setItemCategory1(MainetConstants.FlagA);

				tradeDTO.getTradeLicenseItemDetailDTO().add(0, itemDto.get(itemDto.size() - 2));
			} else {
				tradeDTO.getTradeLicenseItemDetailDTO().get(0).setItemCategory1(MainetConstants.FlagA);
			}
			List<TradeLicenseItemDetailDTO> trdItemList = tradeDTO.getTradeLicenseItemDetailDTO().parallelStream()
					.filter(trd -> trd != null
							&& (trd.getTriStatus() != null && trd.getTriStatus().equalsIgnoreCase(MainetConstants.FlagA)
									|| (trd.getItemCategory1() != null
											&& trd.getItemCategory1().equals(MainetConstants.FlagA))))
					.collect(Collectors.toList());
					tradeDTO.setTradeLicenseItemDetailDTO(trdItemList);*/
			
			//126060 - to show only approved item details		
			tradeDTO.setTradeLicenseItemDetailDTO(tradeDTO.getTradeLicenseItemDetailDTO()
					.stream()
					.filter(trdItem -> trdItem != null
							&& (trdItem.getTriStatus() != null && trdItem.getTriStatus().equals(MainetConstants.FlagA)))
					.collect(Collectors.toList()));
			
		}
		model.setTradeDetailDTO(tradeDTO);
		// Defect#106851

		if (tradeDTO != null && model.getTradeDetailDTO() != null
				&& !model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			// Defect #108769
			StringBuilder ownName = new StringBuilder();
			String ownerName = null;
			for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO()) {
				if (StringUtils.isNotBlank(ownDto.getTroName()))
					ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
			}
			ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
			if (StringUtils.isNotBlank(ownerName)) {
				model.setOwnerName(ownerName);
			}
		}
		if (trdLicno != null && tradeDTO != null && model.getTradeDetailDTO() != null) {
			Long lookUpId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("I", "LIS", orgId);
			if (!model.getTradeDetailDTO().getTrdStatus().equals(lookUpId)) {
				String troName = tradeLicenseApplicationService.getApprovedBuisnessName(trdLicno, orgId, lookUpId);
				if (troName != null) {
					model.getTradeDetailDTO().setTrdBusnm(troName);
				}
			}

		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setChecklistCheck(MainetConstants.FlagY);
			model.setViewMode(MainetConstants.FlagV);

		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}
	     //116162 -  cancellation is not allowed if license is not renewed for current year 
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && tradeDTO != null && tradeDTO.getTrdLictoDate() != null) {
			Date currentDate = new Date();
			Date d1 = tradeDTO.getTrdLictoDate();
			if (d1.before(currentDate)) {
				model.addValidationError(ApplicationSession.getInstance().getMessage("trade.validation.cancellation.license"));
				// for hide proceed and submit button
				model.setFilterType("V");
			}
		}
			
		return defaultMyResult();

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_CHECKLIST_AND_CHARGES)
	public ModelAndView getcharges(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
		CancellationLicenseFormModel model = getModel();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			model.getCheckListFromBrms();
		}
		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeDetailDTO().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().getTradeDetailDTO().setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			this.getModel().getTradeDetailDTO().setScrutinyAppFlag(MainetConstants.FlagN);
		}

		Date currentDate = new Date();
		Date d1 = masDto.getTrdLicfromDate();
		Date d2 = masDto.getTrdLictoDate();
		if (d1.before(currentDate) && d2.after(currentDate)) {
			model.setCancellationFlag(MainetConstants.FlagY);
		} else {
			model.setCancellationFlag(MainetConstants.FlagN);
		}
	

		if (masDto.getApplicationchargeApplFlag().equals(MainetConstants.FlagY)
		/* && model.getCancellationFlag().equals(MainetConstants.FlagN) */) {
			model.setTradeDetailDTO(cancellationLicenseService.getTradeLicenceAppChargesFromBrmsRule(masDto));

			model.getOfflineDTO().setAmountToShow(masDto.getTotalApplicationFee().doubleValue());
			model.getOfflineDTO().setAmountToPay(masDto.getTotalApplicationFee().toString());

		}
		model.setPaymentCheck(MainetConstants.FlagY);
		model.setChecklistCheck(MainetConstants.FlagN);
		return defaultMyResult();

	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND,
				getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		CancellationLicenseFormModel model = this.getModel();
		TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
		List<DocumentDetailsVO> docs = model.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		masDto.setDocumentList(docs);
		fileUpload.validateUpload(model.getBindingResult());
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}
		return defaultMyResult();
	}

	@RequestMapping(params = "editCancellationApplication", method = RequestMethod.POST)
	public @ResponseBody ModelAndView scrutinyInspectionLetter(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest) {

		this.getModel().setScrutunyEditMode("SM");
		return new ModelAndView("CancellationLicenseFormView", MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		CancellationLicenseFormModel model = this.getModel();
		String docStatus = new String();
		Organisation org = organisationService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.CANCELLATION_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
		Long appId = null;
		if (model.getTradeMasterDetailDTO() != null
				&& model.getTradeDetailDTO().getApmApplicationId() != null) {
			appId = model.getTradeDetailDTO().getApmApplicationId();
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
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService
					.getCFCApplicationByApplicationId(appId, UserSession.getCurrent().getOrganisation().getOrgid());
			model.setCfcEntity(cfcEntity);
		}
		// #129518 
		model.setApplicationId(appId);
		model.setApplicantName(model.getCfcEntity().getApmFname());
		model.setServiceName(model.getServiceMaster().getSmServiceName());
		model.setDepartmentName(serviceMas.getTbDepartment().getDpDeptdesc());
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
