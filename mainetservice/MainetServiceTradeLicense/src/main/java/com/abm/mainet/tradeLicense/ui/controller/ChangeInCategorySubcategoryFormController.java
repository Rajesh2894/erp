package com.abm.mainet.tradeLicense.ui.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
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
import com.abm.mainet.tradeLicense.service.IChangeCategorySubCategoryService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.ChangeInCategorySubcategoryFormModel;

@Controller
@RequestMapping("/ChangeInCategorySubcategoryForm.html")
public class ChangeInCategorySubcategoryFormController
		extends AbstractFormController<ChangeInCategorySubcategoryFormModel> {

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private IChangeCategorySubCategoryService changeCategorySubCategoryService;

	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);

		ServiceMaster serviceMaster = serviceMasterService.getServiceByShortName(
				MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
				UserSession.getCurrent().getOrganisation().getOrgid());

		this.getModel().setServiceMaster(serviceMaster);

		if (serviceMaster.getSmAppliChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagN);
		}
		if (serviceMaster.getSmScrutinyChargeFlag().equals(MainetConstants.FlagY)) {
			this.getModel().setScrutinyAppFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setScrutinyAppFlag(MainetConstants.FlagN);
		}

		return new ModelAndView(MainetConstants.TradeLicense.CHNAGE_IN_CATEGORY_SUBCATEGORY_FORM,
				MainetConstants.FORM_NAME, getModel());

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
		ChangeInCategorySubcategoryFormModel model = getModel();
		TradeMasterDetailDTO tradeDTO = model.getTradeMasterDetailDTO();
		List<TradeLicenseItemDetailDTO> detList = new ArrayList<TradeLicenseItemDetailDTO>();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		model.setNewEntry(MainetConstants.FlagN);
		model.setShowHideFlag(MainetConstants.FlagN);
		model.setEditAppFlag(MainetConstants.FlagY);
		tradeDTO = tradeLicenseApplicationService.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		// for checking License Rejected on Checklist Verification phase or not
		Boolean flagCheck = false;
		if (tradeDTO != null && tradeDTO.getApmApplicationId() != null) {
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
		if (tradeDTO != null) {
			detList = tradeDTO.getTradeLicenseItemDetailDTO().stream()
					.filter(k -> k != null && k.getTriStatus() != null)
					.filter(s -> s.getTriStatus().equals("A") || s.getTriStatus().equals("Y"))
					.collect(Collectors.toList());
			tradeDTO.setTradeLicenseItemDetailDTO(detList);
		}
		// model.setTradeMasterDetailDTO(tradeDTO);
		model.setTradeDTO(tradeDTO);
		if (tradeDTO != null && tradeDTO.getTrdLicno() != null && tradeDTO.getTrdStatus() != lookUp.getLookUpId()) {
			model.setLicFromDateDesc(Utility.dateToString(tradeDTO.getTrdLicfromDate()));
			model.setLicToDateDesc(Utility.dateToString(tradeDTO.getTrdLictoDate()));
			model.setLicenseDetails(MainetConstants.FlagY);
			model.setChecklistCheck(MainetConstants.FlagY);
			if (model.getTradeDTO() != null && !model.getTradeDTO().getTradeLicenseOwnerdetailDTO().isEmpty()) {
				// Defect #108769
				StringBuilder ownName = new StringBuilder();
				String ownerName = null;
				for (TradeLicenseOwnerDetailDTO ownDto : model.getTradeDTO().getTradeLicenseOwnerdetailDTO()) {
					if (StringUtils.isNotBlank(ownDto.getTroName()))
						ownName.append(ownDto.getTroName() + " " + MainetConstants.operator.COMMA);
				}
				ownerName = ownName.deleteCharAt(ownName.length() - 1).toString();
				if (StringUtils.isNotBlank(ownerName)) {
					model.setOwnerName(ownerName);
				}
			}

		} else {

			ModelAndView mv = defaultMyResult();
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			model.addValidationError(ApplicationSession.getInstance().getMessage("trade.ValidateLicenseNo"));

			return mv;
		}

		return defaultMyResult();

	}

	/**
	 * Edit Change Category sub Category Application
	 * 
	 * @param httpServletRequest
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.EDIT_APPLICATION)
	public ModelAndView editChangeCategoryAppln(final HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = getModel();
		TradeMasterDetailDTO tradeDetailDto = model.getTradeDTO();
		TradeMasterDetailDTO tradeDTO = new TradeMasterDetailDTO();
		List<TradeLicenseItemDetailDTO> itemdDetailsList = new ArrayList<>();
		BeanUtils.copyProperties(tradeDetailDto, tradeDTO);
		tradeDetailDto.getTradeLicenseItemDetailDTO().forEach(itemdDetails -> {
			TradeLicenseItemDetailDTO itemDto = new TradeLicenseItemDetailDTO();
			BeanUtils.copyProperties(itemdDetails, itemDto);
			itemDto.setTriCategory1(itemdDetails.getTriCod1());
			itemDto.setTriCategory2(itemdDetails.getTriCod2());
			itemDto.setTriCategory3(itemdDetails.getTriCod3());
			itemDto.setTriCategory4(itemdDetails.getTriCod4());
			itemDto.setTriCategory5(itemdDetails.getTriCod5());
			itemdDetailsList.add(itemDto);
		});

		tradeDTO.setTradeLicenseItemDetailDTO(itemdDetailsList);
		// model.setTradeDTO(tradeDTO);
		model.setTradeMasterDetailDTO(tradeDTO);
		model.setShowHideFlag(MainetConstants.FlagY);
		return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_VATEGORY_SUBCATEGORY_EDIT,
				MainetConstants.FORM_NAME, this.getModel());

	}

	/**
	 * Save Category Sub Category Form
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	@RequestMapping(params = MainetConstants.TradeLicense.SAVE_CATEGORY_SUBCATEGORY_FORM, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = this.getModel();

		if (this.getModel().getScrutunyEditMode() != null && this.getModel().getScrutunyEditMode().equals("SM")) {
			model.saveForm();
		}
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
	}

	@RequestMapping(method = { RequestMethod.GET }, params = MainetConstants.TradeLicense.SHOW_CHARGE_DETAILS_MARKET)
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView(MainetConstants.TradeLicense.CHARGES_DETAIL, MainetConstants.CommonConstants.COMMAND,
				getModel());
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
		TradeMasterDetailDTO masDto = this.getModel().getTradeMasterDetailDTO();
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		masDto.setOrgid(orgid);
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE,
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setServiceMaster(sm);
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
		
		if (model.getServiceMaster().getSmAppliChargeFlag().equalsIgnoreCase(MainetConstants.FlagY)) {

			try {
				model.setTradeMasterDetailDTO(changeCategorySubCategoryService.getCategoryChargesFromBrmsRule(masDto));
			} catch (FrameworkException e) {
				ModelAndView mv = defaultResult();/*
													 * new ModelAndView(MainetConstants.TradeLicense.
													 * CHANGE_IN_VATEGORY_SUBCATEGORY_EDIT, MainetConstants.FORM_NAME,
													 * this.getModel());
													 */
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				model.addValidationError("No appropriate Rule found");

				return mv;
			}

			model.getOfflineDTO().setAmountToShow(
					(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee()).doubleValue());
			model.getOfflineDTO()
					.setAmountToPay(this.getModel().getTradeMasterDetailDTO().getTotalApplicationFee().toString());
			model.setPaymentCheck(MainetConstants.FlagY);
			model.setPaymentCheck(MainetConstants.FlagY);
		}
	    model.setChecklistCheck(MainetConstants.FlagY);
		model.setNewEntry(MainetConstants.FlagY);
		model.setEditAppFlag(MainetConstants.FlagN);
		return defaultResult();

	}

	/**
	 * generate Payment mode and save change category and sub category license
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_CHALLAN_AND_PAYMENT, method = RequestMethod.POST)
	public ModelAndView generateChallanAndPayement(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = this.getModel();
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

	@RequestMapping(params = MainetConstants.TradeLicense.DELETE_CATEGORY_TABLE_ROW, method = RequestMethod.POST)
	public @ResponseBody void deleteUnitTableRow(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, @RequestParam(value = "deletedRowCount") int deletedRowCount) {
		this.getModel().bind(httpServletRequest);
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		if (!model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().isEmpty()
				&& model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().size() > 1) {
			TradeLicenseItemDetailDTO detDto = model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO()
					.get(deletedRowCount);
			model.getTradeMasterDetailDTO().getTradeLicenseItemDetailDTO().remove(detDto);
		}
	}

	@RequestMapping(params = MainetConstants.TradeLicense.EDIT_CHANGE_CATEGORY_APPLICATION, method = RequestMethod.POST)
	public @ResponseBody ModelAndView scrutinyInspectionLetter(@RequestParam("applId") final long applId,
			@RequestParam("formUrl") final String formUrl, @RequestParam("mode") final String mode,
			@RequestParam("labelId") final long labelId, @RequestParam("serviceId") final String serviceId,
			@RequestParam("level") final String level, @RequestParam("labelVal") final String labelVal,
			final HttpServletRequest httpServletRequest) {

		this.getModel().setScrutunyEditMode("SM");
		this.getModel().setLicenseDetails("N");
		this.getModel().setEditMode("E");
		return new ModelAndView(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_FORM_VIEW,
				MainetConstants.FORM_NAME, this.getModel());

	}
	
	@RequestMapping(params = MainetConstants.TradeLicense.PRINT_AGENCY_REG_ACK, method = { RequestMethod.POST })
	public ModelAndView printAgencyRegAcknowledgement(HttpServletRequest request) {
		ChangeInCategorySubcategoryFormModel model = this.getModel();
		String docStatus = new String();
		ServiceMaster serviceMas = serviceMasterService.getServiceByShortName(MainetConstants.TradeLicense.CHANGE_IN_CATEGORY_SUBCATEGORY_SERVICE_SHORTCODE, UserSession.getCurrent().getOrganisation().getOrgid());
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
		model.setDueDate(Utility.getAddedDateBy2(model.getAppDate(),serviceMas.getSmServiceDuration().intValue()));
		return new ModelAndView("LicenseApplicantAcknowledgement", MainetConstants.FORM_NAME, this.getModel());
		
	}
}
