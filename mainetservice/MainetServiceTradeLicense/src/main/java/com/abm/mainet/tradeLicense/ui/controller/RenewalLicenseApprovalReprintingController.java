package com.abm.mainet.tradeLicense.ui.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.domain.DishonourChargeEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.repository.DishonurChargeEntryRepository;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.domain.TbSrcptFeesDetEntity;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IReceiptEntryService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.tradeLicense.dto.RenewalHistroyDetails;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IRenewalLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.RenewalLicenseApprovalReprintingModel;


@Controller
@RequestMapping("/RenewalLicenseApprovalReprinting.html")
public class RenewalLicenseApprovalReprintingController
		extends AbstractFormController<RenewalLicenseApprovalReprintingModel> {
	private static final Logger LOGGER = Logger.getLogger(RenewalLicenseApprovalReprintingController.class);

	@Autowired
	IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;
	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private TbLoiMasService tbLoiMasService;
	@Autowired
	TbLoiDetService tbLoiDetService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private TbApprejMasService tbApprejMasService;
	@Autowired
	ICFCApplicationMasterService cfcApplicationService;

	@Autowired
	private IRenewalLicenseApplicationService iRenewalService;

	@Autowired
	private IReceiptEntryService receiptEntryService;
	

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
	public ModelAndView viewApproval(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
			@RequestParam("workflowId") Long workflowId) throws ParseException {
		getData(applicationId, taskId, workflowId, httpServletRequest);
		// Defect#130694 for validate if cheque is dishonoured
		ModelAndView mv = new ModelAndView("RenewalLicenseApprovalReprinting", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("actualTaskId") final long taskId, @RequestParam("workflowId") final long workflowId,
			final HttpServletRequest httpServletRequest) {
		getData(Long.valueOf(applicationId), taskId, workflowId, httpServletRequest);
		return new ModelAndView("RenewalLicenseApprovalReprintingView", MainetConstants.FORM_NAME, getModel());
	}

	public void getData(Long applicationId, Long taskId, Long workflowId, HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		RenewalLicenseApprovalReprintingModel model = this.getModel();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = UserSession.getCurrent().getOrganisation();
		TbServiceReceiptMasEntity entity = null;
		WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
		this.getModel().setTaskId(taskId);
		long serviceIdByApplicationId = cfcApplicationService.getServiceIdByApplicationId(applicationId, orgId);

		model.setServiceMaster(serviceMasterService.getServiceMaster(serviceIdByApplicationId, orgId));
		model.setTradeDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(applicationId, serviceIdByApplicationId,
				wfmass.getOrganisation().getOrgid());
		// 127361 to fetch details of application time charges for skdcl env
		Boolean skDclFlag = Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
				MainetConstants.ENV_SKDCL);
		LookUp chargeApplicableAt = null;
		List<TbTaxMasEntity> taxesMaster1 = new ArrayList<TbTaxMasEntity>();
		Long deptId = null;
		Long taxId = null;
		if (skDclFlag) {
			chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.TradeLicense.APL,
					PrefixConstants.NewWaterServiceConstants.CAA, org);
			deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
			model.setSkdclEnvFlag(MainetConstants.FlagY);
		} else {
			chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
					UserSession.getCurrent().getOrganisation());
			model.setSkdclEnvFlag(MainetConstants.FlagN);
		}
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode("RTL", orgId);
		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class)
				.fetchAllApplicableServiceCharge(sm.getSmServiceId(), orgId, chargeApplicableAt.getLookUpId()).stream()
				.sorted(Comparator.comparingLong(TbTaxMasEntity::getTaxDisplaySeq)).collect(Collectors.toList());
		// For fetching dishonour charge details D#130924
		try {
			LookUp lookUp1 = CommonMasterUtility.getHieLookupByLookupCode("CDC", "TAC", 2, orgId);
			if (!skDclFlag) {
				taxesMaster1 = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
						.findAllTaxesByChargeAppAtAndTaxSubCat(orgId, sm.getTbDepartment().getDpDeptid(),
								chargeApplicableAt.getLookUpId(), lookUp1.getLookUpId());
			} else {
				taxesMaster1 = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
						.findAllTaxesByChargeAppAtAndTaxSubCat(orgId, deptId, chargeApplicableAt.getLookUpId(),
								lookUp1.getLookUpId());
			}

		} catch (Exception e) {
			LOGGER.error("Tax not found for TAC prefix CDC value");
		}

		Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();
		model.setViewMode(MainetConstants.FlagV);
		if (CollectionUtils.isNotEmpty(tbLoiMas) && !skDclFlag) {
			List<TbLoiDetEntity> loiDetails = tbLoiDetService.findLoiDetailsByLoiIdAndOrgId(tbLoiMas.get(0).getLoiId(),
					orgId);
			// Defect #133557
			try {
				entity = receiptEntryService.getReceiptDetailsByAppId(applicationId, orgId);
			} catch (Exception e) {
				LOGGER.error("Reciept Detail not found  " + applicationId);
			}

			for (TbTaxMasEntity taxesMasterdto : taxesMaster) {

				double loiAmount = 0d;

				for (TbLoiDetEntity loiDetailDto : loiDetails) {

					if (loiDetailDto.getLoiChrgid().longValue() == taxesMasterdto.getTaxId().longValue()) {

						/*
						 * String taxCategoryDesc = CommonMasterUtility
						 * .getHierarchicalLookUp(taxesMasterdto.getTaxCategory2(),
						 * wfmass.getOrganisation()) .getDescLangFirst();
						 */
						if (loiDetailDto.getLoiAmount() != null) {
							loiAmount = loiAmount + loiDetailDto.getLoiAmount().doubleValue();
						}
						chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(), Double.valueOf(Math.round(loiAmount)));

					}

				}
			}
			// For set dishonour charge details
			if (!CollectionUtils.isEmpty(taxesMaster1)) {
				for (TbTaxMasEntity taxesMasterdto : taxesMaster1) {

					double loiAmount = 0d;

					for (TbLoiDetEntity loiDetailDto : loiDetails) {

						if (loiDetailDto.getLoiChrgid().longValue() == taxesMasterdto.getTaxId().longValue()) {

							if (loiDetailDto.getLoiAmount() != null) {
								loiAmount = loiAmount + loiDetailDto.getLoiAmount().doubleValue();
							}
							chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(), Double.valueOf(Math.round(loiAmount)));

						}

					}
				}
			}
			model.setTbLoiMas(tbLoiMas);
			model.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
					tbLoiMas.get(0).getLoiNo());
			model.setRmRcptno(dto.get(0).getRmRcptno());
			model.setRmAmount(BigDecimal.valueOf((dto.get(0).getRmAmount().floatValue())));
			model.setPayMode(dto.get(0).getCpdDesc());

		} else {
			entity = receiptEntryService.getReceiptDetailsByAppId(applicationId, orgId);
			if (entity != null) {
				for (TbTaxMasEntity taxesMasterdto : taxesMaster) {
					for (TbSrcptFeesDetEntity det : entity.getReceiptFeeDetail()) {
						if (taxesMasterdto.getTaxId().equals(det.getTaxId()))
							chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(),
									Double.valueOf(Math.round(det.getRfFeeamount().doubleValue())));
					}
				}
				if (!CollectionUtils.isEmpty(taxesMaster1)) {
					for (TbTaxMasEntity taxesMasterdto : taxesMaster1) {

						for (TbSrcptFeesDetEntity det : entity.getReceiptFeeDetail()) {
							if (taxesMasterdto.getTaxId().equals(det.getTaxId()))
								chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(),
										Double.valueOf(Math.round(det.getRfFeeamount().doubleValue())));
						}
					}
				}
				// Defect#130694 for validate i in case of loi not applicabe application time
				// cheque is dishonoured
				// Defect #133557

				model.setRmRcptno(entity.getRmRcptno());
				model.setRmAmount(entity.getRmAmount());
				LookUp look = CommonMasterUtility
						.getNonHierarchicalLookUpObject(entity.getReceiptModeDetail().get(0).getCpdFeemode(), org);
				model.setPayMode(look.getLookUpDesc());
				model.setLoiDateDesc(Utility.dateToString(entity.getRmDate()));
			}
		}
		// Defect #133557
		if (entity != null && entity.getRmRcptid() != 0L) {
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentModeByRcptId(orgId,
					entity.getRmRcptid());
			// if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
			// MainetConstants.ENV_SKDCL)) {
			Boolean isAccLive = tradeLicenseApplicationService.checkAccountActiveOrNot();
			if ((dto.get(0).getCpdValue() != null && dto.get(0).getCpdValue().equals(MainetConstants.FlagQ))
					&& isAccLive) {
				if (dto.get(0).getCheckStatus() == null) {
					model.addValidationError(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear"));
					model.setViewMode(MainetConstants.FlagH);
				} else {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.get(0).getCheckStatus());
					if (!lookUp.getLookUpCode().equals(MainetConstants.Property.CLEARED)) {
						model.addValidationError(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear"));
						model.setViewMode(MainetConstants.FlagH);
					}
				}
			}
		}

		model.setChargeDescAndAmount(chargeDescAndAmount);

		model.setTaxesMaster(taxesMaster);

	}

	/**
	 * Trade License Certificate Form View
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.GET_LICENSE_PRINT)
	public ModelAndView getTradeCertificate(final HttpServletRequest request) {
		this.getModel().bind(request);
		TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
		final RenewalLicenseApprovalReprintingModel model = this.getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());

		List<CFCAttachment> imgList = new ArrayList<>();

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
				.getHierarchicalLookUp(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod1(), orgId);
		model.setCategoryDesc(lookup.getDescLangFirst());

		TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(masDto.getApmApplicationId(), orgId);
		model.setTradeDetailDTO(tradeMasterDetailDTO);
		Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
		if (licenseEndDate != null) {
			tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		model.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
		model.setDateDesc(Utility.dateToString(cfcApplicationDetails.getApmApplicationDate()));
		imgList = checklistVerificationService.getDocumentUploadedByRefNo(
				model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);

		model.setDocumentList(imgList);
		if (!imgList.isEmpty() && imgList != null) {
			model.setImagePath(getPropImages(imgList.get(0)));
		}
		
		//D#142923
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			if (tradeMasterDetailDTO.getTrdLicisdate() != null)
			model.setTrdBusDate(Utility.dateToString(tradeMasterDetailDTO.getTrdLicisdate()));
			model.setTsclEnvFlag(MainetConstants.FlagY);
		}else {
			model.setTsclEnvFlag(MainetConstants.FlagN);
		}
		// For US#112674
		try {
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
				List<RenewalHistroyDetails> renHistDetails = iRenewalService
						.getRenewalHistoryDetails(tradeMasterDetailDTO.getTrdLicno(), orgId);

				model.setRenHistList(renHistDetails);
				Department dept = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
						.getDepartment(MainetConstants.TradeLicense.MARKET_LICENSE, MainetConstants.FlagA);
				this.getModel().setDepartment(dept);

			}
		} catch (Exception e) {
			// TODO: handle exception
			LOGGER.error("TEV Prefix not found");
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
		// US#151005-category wise terms and conditions for skdcl project
		List<TbApprejMas> apprejMasListCate = new ArrayList<>();
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			boolean flag = false;
			for (TbApprejMas tbApprej : apprejMasList) {
				if (tbApprej.getCategoryId() != null && tbApprej.getCategoryId().equals(lookup.getLookUpId())) {
					apprejMasListCate.add(tbApprej);
					model.setApprejMasList(apprejMasListCate);
					flag=true;
				} else if(!flag){
					model.setApprejMasList(apprejMasList);
				}
			}
		} else {
			model.setApprejMasList(apprejMasList);
		}

		return new ModelAndView("RenewalLicenseApprovalReport", MainetConstants.FORM_NAME, this.getModel());

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

	/**
	 * Used to get Generate License Number
	 * 
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	@RequestMapping(params = MainetConstants.TradeLicense.GENERATE_LICENSE_NUMBER, method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveTradeLicense(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		this.getModel().bind(httpServletRequest);
		RenewalLicenseApprovalReprintingModel model = this.getModel();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
				UserSession.getCurrent().getOrganisation());

		model.getTradeDetailDTO().setTrdStatus(lookUp.getLookUpId());
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

	}
}
