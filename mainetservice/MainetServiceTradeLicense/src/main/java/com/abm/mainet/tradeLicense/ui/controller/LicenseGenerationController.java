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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.domain.TbTaxMasEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.DepartmentDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbTaxMasService;
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
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.TradeLicenseApplicationServiceImpl;
import com.abm.mainet.tradeLicense.ui.model.LicenseGenerationModel;

@Controller
@RequestMapping(MainetConstants.TradeLicense.LICENSE_GENERATION_HTML)
public class LicenseGenerationController extends AbstractFormController<LicenseGenerationModel> {

	private static final Logger LOGGER = Logger.getLogger(LicenseGenerationController.class);

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	TbLoiDetService tbLoiDetService;

	/**
	 * Trade License Approval Form VIew
	 * 
	 * @param httpServletRequest
	 * @param applicationId
	 * @param taskId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
	public ModelAndView viewApproval(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
			@RequestParam("workflowId") Long workflowId) throws ParseException {
		getData(applicationId, taskId, workflowId, httpServletRequest);
		// Defect#130694 for validate if cheque is dishonoured
		ModelAndView mv = new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_APPROVAL,
				MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("actualTaskId") final long taskId, @RequestParam("workflowId") final long workflowId,
			final HttpServletRequest httpServletRequest) {
		getData(Long.valueOf(applicationId), taskId, workflowId, httpServletRequest);
		return new ModelAndView("TradeLicenseApprovalView", MainetConstants.FORM_NAME, getModel());
	}

	public void getData(Long applicationId, Long taskId, Long workflowId, HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final LicenseGenerationModel model = this.getModel();
		WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setTaskId(taskId);
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		ServiceMaster sm = applicationContext.getBean(ServiceMasterService.class).getServiceMasterByShortCode(
				MainetConstants.TradeLicense.SERVICE_SHORT_CODE, wfmass.getOrganisation().getOrgid());
		getModel().setServiceMaster(sm);
		List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(applicationId, sm.getSmServiceId(),
				wfmass.getOrganisation().getOrgid());
		List<TbLoiDetEntity> loiDetails = new ArrayList<TbLoiDetEntity>();
		model.setTbLoiMas(tbLoiMas);
		// For Resovling AIOB Exception
		if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
			loiDetails = tbLoiDetService.findLoiDetailsByLoiIdAndOrgId(tbLoiMas.get(0).getLoiId(), orgId);
		}

		List<TbTaxMasEntity> taxesMaster1 = new ArrayList<TbTaxMasEntity>();
		Long taxId = null;
		final LookUp chargeApplicableAt = CommonMasterUtility.getValueFromPrefixLookUp(
				PrefixConstants.NewWaterServiceConstants.SCRUTINY, PrefixConstants.NewWaterServiceConstants.CAA,
				UserSession.getCurrent().getOrganisation());

		final List<TbTaxMasEntity> taxesMaster = ApplicationContextProvider.getApplicationContext()
				.getBean(TbTaxMasService.class)
				.fetchAllApplicableServiceCharge(sm.getSmServiceId(), orgId, chargeApplicableAt.getLookUpId()).stream()
				.sorted(Comparator.comparingLong(TbTaxMasEntity::getTaxDisplaySeq)).collect(Collectors.toList());
		// For fetching dishonour charge details D#130924
		try {
			LookUp lookUp1 = CommonMasterUtility.getHieLookupByLookupCode("CDC", "TAC", 2, orgId);
			taxesMaster1 = ApplicationContextProvider.getApplicationContext().getBean(TbTaxMasService.class)
					.findAllTaxesByChargeAppAtAndTaxSubCat(orgId, sm.getTbDepartment().getDpDeptid(),
							chargeApplicableAt.getLookUpId(), lookUp1.getLookUpId());
			if (!CollectionUtils.isEmpty(taxesMaster1)) {
				taxId = taxesMaster1.get(0).getTaxId();
			}

		} catch (Exception e) {
			LOGGER.error("Tax not found for TAC prefix CDC value ");
		}

		Map<String, Double> chargeDescAndAmount = new LinkedHashMap<>();

		for (TbTaxMasEntity taxesMasterdto : taxesMaster) {

			double loiAmount = 0d;

			for (TbLoiDetEntity loiDetailDto : loiDetails) {

				if (loiDetailDto.getLoiChrgid().longValue() == taxesMasterdto.getTaxId().longValue()) {

					loiAmount = loiAmount + loiDetailDto.getLoiAmount().doubleValue();
					chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(), loiAmount);

				}

			}
		}
		// For setting dishonour charge details
		if (!CollectionUtils.isEmpty(taxesMaster1)) {
			for (TbTaxMasEntity taxesMasterdto : taxesMaster1) {

				double loiAmount = 0d;

				for (TbLoiDetEntity loiDetailDto : loiDetails) {

					if (loiDetailDto.getLoiChrgid().longValue() == taxesMasterdto.getTaxId().longValue()) {

						loiAmount = loiAmount + loiDetailDto.getLoiAmount().doubleValue();
						chargeDescAndAmount.put(taxesMasterdto.getTaxDesc(), loiAmount);

					}

				}
			}
		}
		model.setChargeDescAndAmount(chargeDescAndAmount);
		model.setViewMode(MainetConstants.FlagV);

		if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
			model.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
					tbLoiMas.get(0).getLoiNo());
			// Defect#130694 for validate if cheque is dishonoured or cleared
			Boolean isAccLive = tradeLicenseApplicationService.checkAccountActiveOrNot();
		if (dto != null && !dto.isEmpty()) {
			if ((dto.get(0).getCpdValue() != null && dto.get(0).getCpdValue().equals(MainetConstants.FlagQ))
					&& isAccLive) {
				if (dto.get(0).getCheckStatus() == null) {
					model.addValidationError(ApplicationSession.getInstance()
							.getMessage(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear")));
					model.setViewMode(MainetConstants.FlagH);
				} else {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.get(0).getCheckStatus());
					if (!lookUp.getLookUpCode().equals(MainetConstants.Property.CLEARED)) {
						model.addValidationError(ApplicationSession.getInstance()
								.getMessage(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear")));
						model.setViewMode(MainetConstants.FlagH);
					}
				}

			}
		}
			if (dto != null && !dto.isEmpty()) {
				model.setRmRcptno(dto.get(0).getRmRcptno());
				model.setRmAmount(dto.get(0).getRmAmount());
				model.setPayMode(dto.get(0).getCpdDesc());
			}
		}
		//
		model.setTradeDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
		// added for Defect#106834
		if (model.getTradeDetailDTO() != null && model.getTradeDetailDTO().getTradeLicenseItemDetailDTO() != null
				&& !model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().isEmpty()) {
			model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().get(0).setTriRate(model.getRmAmount());
			if (model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().get(0).getDepositAmt() != null )
			model.setDepositeAmount(model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().get(0).getDepositAmt());
			if (model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().get(0).getLicenseFee() !=null)
			model.setLicenseFee(model.getTradeDetailDTO().getTradeLicenseItemDetailDTO().get(0).getLicenseFee());
		}

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
		final LicenseGenerationModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
				.getTradeLicenseWithAllDetailsByApplicationId(masDto.getApmApplicationId());

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
		
		if(lookup.getLookUpCode() != null)
			model.setCategoryCode(lookup.getLookUpCode());
		
		if(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod2() != null) {
			LookUp sublookup = CommonMasterUtility
					.getHierarchicalLookUp(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriCod2(), orgId);
			model.setSubCategoryDesc(sublookup.getDescLangFirst());
		}
		
		
		if (tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriRate() != null)
			tradeMasterDetailDTO.setCpdValue(tradeMasterDetailDTO.getTradeLicenseItemDetailDTO().get(0).getTriRate().toString());
		tradeMasterDetailDTO.setMobileNo(tradeMasterDetailDTO.getTradeLicenseOwnerdetailDTO().get(0).getTroMobileno());
		
		TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
				.getCFCApplicationByApplicationId(masDto.getApmApplicationId(), orgId);
		model.setTradeDetailDTO(tradeMasterDetailDTO);
		Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
		if (licenseEndDate != null) {
			tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		model.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
		model.setDateDesc(Utility.dateToString(cfcApplicationDetails.getApmApplicationDate()));
		List<CFCAttachment> imgList = checklistVerificationService.getDocumentUploadedByRefNo(
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
		//US#151005-category wise terms and conditions for skdcl project 
		List<TbApprejMas> apprejMasListCate = new ArrayList<>();
		if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)) {
			boolean flag = false;
			for (TbApprejMas tbApprej : apprejMasList) {
				if (tbApprej.getCategoryId() != null && tbApprej.getCategoryId().equals(lookup.getLookUpId())) {
					apprejMasListCate.add(tbApprej);
				    model.setApprejMasList(apprejMasListCate);
				    flag=true;
				}else if(!flag){
					model.setApprejMasList(apprejMasList);
				}
			}
			}else {
		    model.setApprejMasList(apprejMasList);
			}
		// set value for SKDCL ENV
		Department dept = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartment(MainetConstants.TradeLicense.MARKET_LICENSE, MainetConstants.FlagA);
		this.getModel().setDepartment(dept);

		// US#104597 // pushing document to DMS
		
		String URL = ServiceEndpoints.BIRT_REPORT_DMS_URL + "="
				+ ApplicationSession.getInstance().getMessage("birtName.tradeLicense") + "&__format=pdf&RP_ORGID="
				+ org.getOrgid() + "&RP_AppliationID=" + masDto.getApmApplicationId() + "&RP_LicenseNo="
				+ this.getModel().getTradeDetailDTO().getTrdLicno();
		Utility.pushDocumentToDms(URL, masDto.getApmApplicationId().toString(), MainetConstants.Dms.TRADE_DEPT,
				fileUpload);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.APP_NAME.TSCL)) {
			return new ModelAndView("ThaneTradeLicenseReportApproval", MainetConstants.FORM_NAME,
					this.getModel());
		} else {
		return new ModelAndView(MainetConstants.TradeLicense.TRADE_LICENSE_REPORT_APPROVAL, MainetConstants.FORM_NAME,
				this.getModel());
		}
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
		LicenseGenerationModel model = this.getModel();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
				UserSession.getCurrent().getOrganisation());

		model.getTradeDetailDTO().setTrdStatus(lookUp.getLookUpId());
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

	}

}