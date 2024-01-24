package com.abm.mainet.tradeLicense.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.PortalService;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.service.IPortalServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
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
import com.abm.mainet.tradeLicense.service.iTransferLicenseService;
import com.abm.mainet.tradeLicense.ui.model.CancellationLicenseFormModel;
import com.abm.mainet.tradeLicense.ui.model.TransperLicenseModel;

@Controller
@RequestMapping("/TransperLicense.html")
public class TransperLicenseController extends AbstractFormController<TransperLicenseModel> {

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	private iTransferLicenseService iTransferLicenseService;
	@Autowired
	private IPortalServiceMasterService iPortalServiceMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest,@RequestParam(value="str",required=false) String str,
			@RequestParam(value="ns",required=false) String ns,@RequestParam(value="ULBID",required=false) String ULBID,@RequestParam(value="ULBDistrict",required=false) String ULBDistrict) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		// For setting help document
		getModel().setCommonHelpDocs("TransperLicense.html");
		FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
		TransperLicenseModel model = getModel();
		Organisation organisation = UserSession.getCurrent().getOrganisation();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		ModelAndView mv =new ModelAndView("TransperLicense", MainetConstants.FORM_NAME, model);
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
			mv= new ModelAndView("TransperLicense", MainetConstants.FORM_NAME, getModel());
			 
		}
		else if(!emp.getEmploginname().equalsIgnoreCase("NOUSER")){
			mv = new ModelAndView("TransperLicense", MainetConstants.FORM_NAME, getModel());
		}
		else{
			mv= new ModelAndView("AutherizationFail", MainetConstants.FORM_NAME, getModel());
		}}
		return mv;

		

	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_LICENSE_DETAILS, method = RequestMethod.POST)
	public ModelAndView getLicenseDetails(HttpServletRequest httpServletRequest,
			@RequestParam("trdLicno") String trdLicno) {
		this.getModel().bind(httpServletRequest);
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeDetailDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		tradeDTO.setTenant(model.getTenant());
		// code added for check is this license rejected in any of the service flow
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
			// D#125111
			flagCheck = tradeLicenseApplicationService.checkTaskRejectedOrNot(tradeDTO.getApmApplicationId(), orgId);
		}
		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS");
		LookUp lookUpCancel = CommonMasterUtility.getValueFromPrefixLookUp("C", "LIS");
		// model.setTradeMasterDetailDTO(tradeDTO);
		boolean flag = false;
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			if ((lookUp != null && lookUp.getLookUpId() != 0) && !flagCheck) {
				if (lookUpCancel != null && (tradeDTO.getTrdStatus().equals(lookUpCancel.getLookUpId()))) {

					model.addValidationError(getApplicationSession().getMessage("trade.licNo.cancel"));
					model.setFilterType("C");
					flag = true;
				} else if (!tradeDTO.getTrdStatus().equals(lookUp.getLookUpId())) {
					model.addValidationError(getApplicationSession().getMessage("renewal.valid.licenseStatus")+" "+tradeDTO.getApmApplicationId());
					model.setFilterType("T");
					flag = true;
				}

			}

			// 116162 - cancellation is not allowed if license is not renewed for current
			// year
			if (tradeLicenseApplicationService.isKDMCEnvPresent() && tradeDTO.getTrdLictoDate() != null) {
				Date currentDate = new Date();
				Date d1 = tradeDTO.getTrdLictoDate();
				if (d1.before(currentDate)) {
					model.addValidationError(getApplicationSession().getMessage("trade.validation.transfer.license"));
					flag = true;
				}
			}
		}
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setViewMode(MainetConstants.FlagE);

		} else {
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));
			flag = true;
		}
		if (flag) {
			ModelAndView mv = defaultMyResult();
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}

		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "TLA");
		model.setAppChargeFlag(map.get("applicationchargeApplFlag").toString());
		if (map.get("checkListApplFlag") != null && map.get("checkListApplFlag").equals("Y")) {
			model.setCheckListApplFlag(map.get("checkListApplFlag").toString());
			model.getCheckListFromBrms();
		}

		@SuppressWarnings("unlikely-arg-type")
		List<TradeLicenseOwnerDetailDTO> list = Optional.ofNullable(tradeDTO.getTradeLicenseOwnerdetailDTO())
				.map(Collection::stream).orElseGet(Stream::empty).filter(Objects::nonNull)
				.filter(k -> Optional.ofNullable(k.getTroPr()).equals(Optional.of(MainetConstants.FlagA))
						|| (k.getTroPr()).equals(Optional.of(MainetConstants.FlagD)))
				.collect(Collectors.toList());

		// tradeDTO.setTradeLicenseOwnerdetailDTO(list);
		model.setTradeDetailDTO(tradeDTO);

		// 124309 - to show multiple owner if there
		if (!model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
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
		// D#125383
		if (trdLicno != null && orgId != null) {
			model.getTradeDetailDTO()
					.setTradeLicenseOwnerdetailDTO(tradeLicenseApplicationService.getOwnerList(trdLicno, orgId));
		}
		if (map.get("applicationchargeApplFlag") != null && map.get("applicationchargeApplFlag").equals("Y"))

		{
			try {
				model.setTradeMasterDetailDto(iTransferLicenseService.getTransferChargesFromBrmsRule(tradeDTO));

			} catch (FrameworkException e) {
				ModelAndView mv = defaultMyResult();
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError(ApplicationSession.getInstance().getMessage("renewal.brms.msg"));
				return mv;

			}

			model.getOfflineDTO()
					.setAmountToShow(Double.parseDouble(model.getTradeMasterDetailDto().getApplicationCharge()));
			model.getOfflineDTO().setAmountToPay(model.getTradeMasterDetailDto().getApplicationCharge().toString());
			model.setPaymentCheck(MainetConstants.FlagY);

		}

		return new ModelAndView("TransperLicenseForm", MainetConstants.FORM_NAME, model);
	}

	@RequestMapping(method = RequestMethod.POST, params = "saveTransferLicenseForm")
	public ModelAndView get(final HttpServletRequest request) {
		this.getModel().bind(request);
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO masDto = model.getTradeDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);

		List<DocumentDetailsVO> ownerDocs = model.getTradeMasterDetailDTO().getAttachments();

		if (ownerDocs != null) {
			try {
				ownerDocs = model.prepareFileUploadForImg(ownerDocs);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		model.getTradeMasterDetailDTO().setAttachments(ownerDocs);

		model.getTradeDetailDTO().setAttachments(ownerDocs);

		List<DocumentDetailsVO> docs = model.getCheckList();
		model.getTradeDetailDTO().setDocumentList(docs);

		// model.getTradeDetailDTO().setTradeLicenseOwnerdetailDTO(model.getTradeMasterDetailDTO().getTradeLicenseOwnerdetailDTO());

		model.getTradeDetailDTO().setTrdFtype(model.getTradeMasterDetailDTO().getTrdFtype());
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

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.VIEW_TERMS_CONDITION)
	public ModelAndView tradeTermsCondition(final HttpServletRequest request) {
		bindModel(request);

		return new ModelAndView(MainetConstants.TradeLicense.TRADE_TERMS_CONDITION, MainetConstants.FORM_NAME,
				this.getModel());
	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("TransferLicenseChargesDetailMarketLicense", MainetConstants.COMMAND, getModel());
	}

	@RequestMapping(params = MainetConstants.TradeLicense.GET_OWNERSHIP_TYPE_DIV, method = RequestMethod.POST)
	public ModelAndView getOwnershipTypeDiv(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			@RequestParam(value = MainetConstants.TradeLicense.OWNERSHIP_TYPE) String ownershipType) {
		this.getModel().bind(httpServletRequest);

		TransperLicenseModel model = this.getModel();

		model.setOwnershipPrefix(ownershipType);

		return new ModelAndView("transferOwnershipTable", MainetConstants.FORM_NAME, model);
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
		return new ModelAndView("transferOwnershipTable", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "EntryDelete")
	public void doEntryDeletion(@RequestParam(name = "ID", required = false) int id, final HttpServletRequest request) {
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

	@RequestMapping(params = "printAgencyRegAckw", method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		bindModel(request);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TransperLicenseModel model = this.getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		Long serviceId = iPortalServiceMasterService.getServiceId("TLA", orgId);
		if (serviceId != null) {

			PortalService serviseMast = iPortalServiceMasterService.getService(serviceId, orgId);
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
		// #144362
		if (model != null && model.getOwnerName() != null)
			model.setApplicantName(model.getOwnerName());
		if (MainetConstants.DEFAULT_LANGUAGE_ID == UserSession.getCurrent().getLanguageId()) {
			if (model.getServiceMaster() != null && model.getServiceMaster().getServiceName() != null) 
				model.setServiceName(model.getServiceMaster().getServiceName());
				model.setDepartmentName(model.getServiceMaster().getPsmDpDeptDesc());
			} else {
				if (model.getServiceMaster() != null && model.getServiceMaster().getServiceNameReg() != null)
					model.setServiceName(model.getServiceMaster().getServiceNameReg());
				model.setDepartmentName(model.getServiceMaster().getPsmDpNameMar());
			}

		model.setAppDate(new Date());
		model.setAppTime(new SimpleDateFormat("HH:mm:ss").format(new Date()));
		model.setApplicationId(model.getTradeMasterDetailDTO().getApmApplicationId());
		//#157760-display due date on the acknowledgement copy
		LinkedHashMap<String, Object> map = tradeLicenseApplicationService
				.getCheckListChargeFlagAndLicMaxDay(UserSession.getCurrent().getOrganisation().getOrgid(), "TLA");
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
