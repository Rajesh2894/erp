package com.abm.mainet.tradeLicense.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITransferLicenseService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicenseFormModel;
import com.abm.mainet.tradeLicense.ui.model.TransperLicenseModel;

@Controller
@RequestMapping("/TransperLicense.html")
public class TransperLicenseController extends AbstractFormController<TransperLicenseModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	ServiceMasterService serviceMasterService;
	@Autowired
	ITransferLicenseService iTransferLicenseService;
	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	
	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		this.getModel()
				.setTradeMasterDetailDTOList(tradeLicenseApplicationService.getActiveApplicationIdByOrgId(orgId));

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName("TLA",
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setServiceMaster(serviceMaster);
		LookUp checkListApplLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(
				serviceMaster.getSmChklstVerify(), UserSession.getCurrent().getOrganisation());
		TradeMasterDetailDTO tradeDetailDTO = this.getModel().getTradeDetailDTO();
		if (checkListApplLookUp.getLookUpCode().equals(MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			tradeDetailDTO.setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			tradeDetailDTO.setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			tradeDetailDTO.setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			tradeDetailDTO.setScrutinyAppFlag(MainetConstants.FlagN);
		}

		this.getModel().setTradeDetailDTO(tradeDetailDTO);

		this.getModel().setScrutunyEditMode(MainetConstants.FlagN);

		return new ModelAndView("TransperLicense", MainetConstants.FORM_NAME, getModel());

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = organisationService.getOrganisationById(orgId);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		if(tradeDTO.getTradeLicenseItemDetailDTO() != null) {
			model.setLicCateg(tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod1());
		}
		ServiceMaster serviceMaster = model.getServiceMaster();

		if (this.getModel().getCheckListApplFlag().equals(MainetConstants.FlagY)) {
			try {
				this.getModel().getCheckListFromBrms();
			} catch (FrameworkException e) {
				ModelAndView mv = defaultMyResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(e.getMessage());
				return mv;

			}
		}

		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setViewMode(MainetConstants.FlagE);

		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
		}
		// for defect Defect#115307 comment this code
		/*
		 * @SuppressWarnings("unlikely-arg-type") List<TradeLicenseOwnerDetailDTO> list
		 * = Optional.ofNullable(tradeDTO.getTradeLicenseOwnerdetailDTO())
		 * .map(Collection::stream).orElseGet(Stream::empty).filter(Objects::nonNull)
		 * .filter(k ->
		 * Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagA)))
		 * .collect(Collectors.toList());
		 * 
		 * tradeDTO.setTradeLicenseOwnerdetailDTO(list);
		 */
		// added code for filtering only approve trade item catagory
		/*if (tradeDTO != null) {
			List<TradeLicenseItemDetailDTO> itemDto = tradeLicenseApplicationService
					.getTradeLicenseHistDetBuTrdId(tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriId());
			if (tradeDTO.getTradeLicenseItemDetailDTO().get(0).getTriStatus().equals(MainetConstants.FlagM)) {
				
				itemDto.get(itemDto.size() - 2).setItemCategory1(MainetConstants.FlagA);

				tradeDTO.getTradeLicenseItemDetailDTO().add(0, itemDto.get(itemDto.size() - 2));
			} else {
				tradeDTO.getTradeLicenseItemDetailDTO().get(0).setItemCategory1(MainetConstants.FlagA);
			}
			List<TradeLicenseItemDetailDTO> trdItemList = tradeDTO.getTradeLicenseItemDetailDTO().parallelStream()
					.filter(trd -> trd != null && (trd.getTriStatus().equalsIgnoreCase(MainetConstants.FlagA)
							|| (trd.getItemCategory1() != null
									&& trd.getItemCategory1().equals(MainetConstants.FlagA))))
					.collect(Collectors.toList());
			tradeDTO.setTradeLicenseItemDetailDTO(trdItemList);
		}*/

		model.setTradeDetailDTO(tradeDTO);
		// for defect Defect#115307
		if (!model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
			// Defect #108769
			StringBuilder ownName = new StringBuilder();
			String ownerName = "";
			List<TradeLicenseOwnerDetailDTO> ownerDetailDTOList = model.getTradeDetailDTO()
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
		// code added for adding rejected owner details
		//D#125383
		if (trdLicno != null && orgId != null) {
			model.getTradeDetailDTO().setTradeLicenseOwnerdetailDTO(
					tradeLicenseApplicationService.getOwnerListByLicNoAndOrgId(trdLicno, orgId));
		}

		// end Defect#115307

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY))

		{
			try {
				this.getModel()
						.setTradeDetailDTO(iTransferLicenseService.getTradeLicenceAppChargesFromBrmsRule(tradeDTO));
			} catch (FrameworkException e) {
				ModelAndView mv = defaultMyResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));
				return mv;

			}

			model.getOfflineDTO().setAmountToShow(Double.valueOf(model.getTradeDetailDTO().getApplicationCharge()));
			model.getOfflineDTO().setAmountToPay(model.getTradeDetailDTO().getApplicationCharge().toString());
			model.setPaymentCheck(MainetConstants.FlagY);

		}
		// 116162 to give validation if license is not renewed for current year
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL) && tradeDTO != null
				&& tradeDTO.getTrdLictoDate() != null) {
			Date currentDate = new Date();
			Date d1 = tradeDTO.getTrdLictoDate();
			if (d1.before(currentDate)) {
				ModelAndView mv = defaultMyResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(
						ApplicationSession.getInstance().getMessage("trade.validation.transfer.license"));
				return mv;
			}
		}

		return new ModelAndView("TransperLicenseForm", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveTransferLicenseForm")
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO masDto = model.getTradeDetailDTO();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);

		List<DocumentDetailsVO> ownerDocs = model.getTradeMasterDetailDTO().getAttachments();

		if (ownerDocs != null) {
			try {
				ownerDocs = getModel().prepareFileUploadForImg(ownerDocs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		model.getTradeMasterDetailDTO().setAttachments(ownerDocs);

		model.getTradeDetailDTO().setAttachments(ownerDocs);
		
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			
			tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(model.getTradeDetailDTO().getTrdLicno(), orgid);
			model.getTradeDetailDTO().setTrdLicfromDate(tradeDTO.getTrdLicfromDate());
			model.getTradeDetailDTO().setTrdLictoDate(tradeDTO.getTrdLictoDate());
		}

		List<DocumentDetailsVO> docs = model.getCheckList();

		if (docs != null) {

			docs = fileUpload.prepareFileUpload(docs);
		}

		model.getTradeDetailDTO().setDocumentList(docs);

		// model.getTradeDetailDTO().setTradeLicenseOwnerdetailDTO(model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO());

		model.getTradeDetailDTO().setTrdFtype(model.getTradeMasterDetailDTO().getTrdFtype());
		model.getTradeDetailDTO().setTransferMode(model.getTradeMasterDetailDTO().getTransferMode());
		if (model.validateInputs()) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}
		ModelAndView mv = new ModelAndView("TransperLicenseForm", MainetConstants.FORM_NAME, this.getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
	public ModelAndView backEditPageForm(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);

		this.getModel().setAppid(applId);
		this.getModel().setLabelid(labelId);
		this.getModel().setServiceid(Long.valueOf(serviceId));
		this.getModel().setPaymentCheck(MainetConstants.FlagN);

		// view mode
		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setOpenMode(MainetConstants.FlagD);
		this.getModel().setDownloadMode(MainetConstants.FlagN);
		this.getModel().setHideshowAddBtn(MainetConstants.FlagY);
		this.getModel().setHideshowDeleteBtn(MainetConstants.FlagY);
		// this.getModel().setTemporaryDateHide(MainetConstants.FlagD);
		// this.getModel().getdataOfUploadedImage();

		return new ModelAndView("TransferLicenseEdit", MainetConstants.FORM_NAME, this.getModel());

	}

	@RequestMapping(method = RequestMethod.POST, params = { "updateTransferLicenseForm" })
	public ModelAndView updateTransferLicenseDetails(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		TransperLicenseModel model = this.getModel();

		try {
			if (model.updateForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		} catch (FrameworkException e) {
			ModelAndView mv = new ModelAndView("TransferLicenseEdit", MainetConstants.FORM_NAME, this.getModel());
			model.addValidationError(e.getMessage());
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
					this.getModel().getBindingResult());

			return mv;

		}

	}

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.VIEW_TERMS_CONDITION)
	public ModelAndView tradeTermsCondition(final HttpServletRequest request) {
		bindModel(request);

		return new ModelAndView(MainetConstants.TradeLicense.TRADE_TERMS_CONDITION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("TransferLicenseChargesDetailMarketLicense", MainetConstants.CommonConstants.COMMAND,
				getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		TransperLicenseModel model = this.getModel();
		//125867 commented to set open mode as 'O' to disable add button on scrutiny form
		 //model.setOpenMode("M");
		
	 /*	if (model.getTradeMasterDetailDTO().getApmApplicationId() == null) { 
		 model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().clear();
		 model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO().add(new
		 TradeLicenseOwnerDetailDTO());
		 }*/
		model.setOwnershipPrefix(ownershipType);
        //Defect#111116
		if (model.getTradeMasterDetailDTO().getApmApplicationId() != null) {
			model.setFileMode("G");
		}
		return new ModelAndView("transferOwnershipTable", MainetConstants.FORM_NAME, model);
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
		return new ModelAndView("transferOwnershipTable", MainetConstants.FORM_NAME, this.getModel());
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

	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		TransperLicenseModel model = this.getModel();
		String docStatus = new String();
		Long appId = null;
		Organisation org = organisationService.getOrganisationById(UserSession.getCurrent().getOrganisation().getOrgid());
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.TRANSFER_SERVICE_SHORT_CODE, UserSession.getCurrent().getOrganisation().getOrgid());
		if (model.getTradeDetailDTO() != null && model.getTradeDetailDTO().getApmApplicationId() != null) {
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
		// #129518
		if (appId != null) {
			TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService
					.getCFCApplicationByApplicationId(appId, UserSession.getCurrent().getOrganisation().getOrgid());
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
