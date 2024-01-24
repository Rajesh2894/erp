package com.abm.mainet.tradeLicense.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.repository.ReceiptRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.IDuplicateLicenseApplicationService;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.DuplicateLicensePrintingModel;

@Controller
@RequestMapping("/DuplicateLicensePrinting.html")

public class DuplicateLicensePrintingController extends AbstractFormController<DuplicateLicensePrintingModel> {

	@Resource
	private IFileUploadService fileUpload;

	@Autowired
	private ITradeLicenseApplicationService tradeLicenseApplicationService;

	@Autowired
	private ICFCApplicationMasterDAO cfcApplicationMasterDAO;

	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private IWorkflowExecutionService iWorkflowExecutionService;

	@Autowired
	private IDuplicateLicenseApplicationService duplicateService;

	@Autowired
	ReceiptRepository recieptRepository;

	@RequestMapping(method = { RequestMethod.POST }, params = { MainetConstants.TradeLicense.SHOWDETAILS })
	public ModelAndView index(final HttpServletRequest httpServletRequest, @RequestParam("appNo") Long applicationId,
			@RequestParam("actualTaskId") Long taskId, @RequestParam("workflowId") Long workflowId) {
		/*
		 * sessionCleanup(httpServletRequest); fileUpload.sessionCleanUpForFileUpload();
		 */

		getData(applicationId, taskId, workflowId, httpServletRequest);
		ModelAndView mv = new ModelAndView("DuplicateTradeLicenseCertificate", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
	}

	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("actualTaskId") final long taskId, @RequestParam("workflowId") final long workflowId,
			final HttpServletRequest httpServletRequest) {
		getData(Long.valueOf(applicationId), taskId, workflowId, httpServletRequest);
		return new ModelAndView("DuplicateTradeLicenseCertificateView", MainetConstants.FORM_NAME, getModel());
	}

	public void getData(Long applicationId, Long taskId, Long workflowId, HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);

		final DuplicateLicensePrintingModel model = getModel();
		final Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode(
				MainetConstants.TradeLicense.DUPLICATE_SERVICE_SHORT_CODE,
				UserSession.getCurrent().getOrganisation().getOrgid());

		model.setServiceMaster(sm);
		// Defect #109584
		// for saving history data D#125619
		String trdLicno = duplicateService.getLicenseNoByAppId(applicationId, orgId);

		TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
				.getLicenseDetailsByLicenseNo(trdLicno, orgId);
		// Defect #133557
		TbServiceReceiptMasEntity rcptBean = recieptRepository.getReceiptDetByAppIdAndServiceId(applicationId,
				sm.getSmServiceId(), orgId);
         //Defect #138334
		if (rcptBean != null && taskId!=null) {
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentModeByRcptId(orgId,
					rcptBean.getRmRcptid());
			// Defect#130694 for validate if cheque is dishonoured or cleared
			Boolean isAccLive = tradeLicenseApplicationService.checkAccountActiveOrNot();
			if ((dto.get(0).getCpdValue() != null && dto.get(0).getCpdValue().equals(MainetConstants.FlagQ))
					&& isAccLive) {
				if (dto.get(0).getCheckStatus() == null) {
					model.addValidationError(ApplicationSession.getInstance()
							.getMessage(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear")));
					model.setViewMode(MainetConstants.FlagH);
					return;
				} else {
					LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.get(0).getCheckStatus());
					if (!lookUp.getLookUpCode().equals(MainetConstants.Property.CLEARED)) {
						model.addValidationError(ApplicationSession.getInstance()
								.getMessage(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear")));
						model.setViewMode(MainetConstants.FlagH);
						return;
					}
				}

			}
		}

		model.setTradeDetailDTO(tradeMasterDetailDTO);
		TradeMasterDetailDTO masDto = this.getModel().getTradeDetailDTO();
		// for saving in TB_ML_TRADE_MASTER_HIST table
        //Defect #138334
		if(taskId!=null) {
		duplicateService.saveHistoryData(masDto);
		}

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

		/*
		 * TbCfcApplicationMstEntity cfcApplicationDetails = cfcApplicationMasterDAO
		 * .getCFCApplicationByApplicationId(applicationId, orgId);
		 */

		Date licenseEndDate = tradeMasterDetailDTO.getTrdLictoDate();
		if (licenseEndDate != null) {
			tradeMasterDetailDTO.setLicenseDateDesc(Utility.dateToString(licenseEndDate));
		}
		model.setLicenseFromDateDesc(Utility.dateToString(tradeMasterDetailDTO.getTrdLicfromDate()));
		model.setDateDesc(Utility.dateToString(new Date()));
		if (CollectionUtils.isNotEmpty(model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO())) {
			List<CFCAttachment> imgList = checklistVerificationService.getDocumentUploadedByRefNo(
					model.getTradeDetailDTO().getTradeLicenseOwnerdetailDTO().get(0).getTroId().toString(), orgId);
			model.setDocumentList(imgList);
			if (!imgList.isEmpty() && imgList != null) {
				model.setImagePath(getPropImages(imgList.get(0)));
			}
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
	
		// Defect#115171
		// Defect #132404 for setting License fee details
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_SKDCL)
				|| Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_TSCL)) {
			model.setPaymentDetails(trdLicno);
		} else {
			List<TbLoiMas> loiMas = duplicateService.getTotalAmount(trdLicno);
			if (CollectionUtils.isNotEmpty(loiMas)) {
				model.setRmAmount(loiMas.get(0).getLoiAmount());
			}
		}

		// Defect #112517
		if (taskId != null) {
			WorkflowTaskAction taskAction = new WorkflowTaskAction();
			taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
			taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
			taskAction.setDateOfAction(new Date());
			taskAction.setCreatedDate(new Date());
			taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
			taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
			taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
			taskAction.setApplicationId(applicationId);
			taskAction.setDecision(MainetConstants.WorkFlow.Decision.APPROVED);
			taskAction.setTaskId(taskId);

			WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
			// #129656 BPM Process as maker-checker for all
			workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
			taskAction.setPaymentMode(MainetConstants.FlagY);
			taskAction.setIsFinalApproval(true);
			workflowProcessParameter.setWorkflowTaskAction(taskAction);
			try {
				iWorkflowExecutionService.updateWorkflow(workflowProcessParameter);
			} catch (Exception exception) {
				throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
			}

			this.getModel().sendSmsEmail(tradeMasterDetailDTO);
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
  //Defect #138334
	@RequestMapping(method = { RequestMethod.POST }, params = { "printApplicantData" })
	public ModelAndView printApplicantData(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId) {
		TradeMasterDetailDTO tradeDto = tradeLicenseApplicationService.getTradeDetailsByTrdId(applicationId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (tradeDto.getApmApplicationId() != null) {
			getData(tradeDto.getApmApplicationId(), null, null, httpServletRequest);
		} else {
			TradeMasterDetailDTO tradeMasterDetailDTO = tradeLicenseApplicationService
					.getLicenseDetailsByLicenseNo(tradeDto.getTrdLicno(), UserSession.getCurrent().getOrganisation().getOrgid());
			getModel().setTradeDetailDTO(tradeMasterDetailDTO);
		}
		ModelAndView mv = new ModelAndView("DuplicateTradeLicenseCertificate", MainetConstants.FORM_NAME, getModel());
		return mv;
	}

}
