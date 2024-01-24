package com.abm.mainet.tradeLicense.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.client.FileNetApplicationClient;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
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
import com.abm.mainet.tradeLicense.dto.TradeLicenseOwnerDetailDTO;
import com.abm.mainet.tradeLicense.dto.TradeMasterDetailDTO;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.ChangeInBusinessPrintingModel;

@Controller
@RequestMapping("/ChangeInBusinessPrinting.html")
public class ChangeInBusinessPrintingController extends AbstractFormController<ChangeInBusinessPrintingModel> {

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
	private IWorkFlowTypeService workFlowTypeService;

	@Autowired
	private TbApprejMasService tbApprejMasService;
	@Autowired
	ICFCApplicationMasterService cfcApplicationService;

	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.TradeLicense.SHOWDETAILS)
	public ModelAndView viewApproval(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId,
			@RequestParam("workflowId") Long workflowId) throws ParseException {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final ChangeInBusinessPrintingModel model = this.getModel();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		WorkflowMas wfmass = workFlowTypeService.getWorkFlowById(workflowId);
		this.getModel().setTaskId(taskId);
		long serviceIdByApplicationId = cfcApplicationService.getServiceIdByApplicationId(applicationId, orgId);
		List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(applicationId, serviceIdByApplicationId,
				wfmass.getOrganisation().getOrgid());
		model.setTbLoiMas(tbLoiMas);
		model.setViewMode(MainetConstants.FlagV);
		// adding code for handling AIOB Exception Defect#1075825
		if (tbLoiMas != null && !tbLoiMas.isEmpty()) {
			model.setLoiDateDesc(Utility.dateToString(tbLoiMas.get(0).getLoiDate()));
			List<TradeMasterDetailDTO> dto = tradeLicenseApplicationService.getpaymentMode(orgId,
					tbLoiMas.get(0).getLoiNo());
			// Defect#130694 for validate if cheque is dishonoured and not clear
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
				Boolean isAccLive = tradeLicenseApplicationService.checkAccountActiveOrNot();
				if ((dto.get(0).getCpdValue() != null && dto.get(0).getCpdValue().equals(MainetConstants.FlagQ))
						&& isAccLive) {
					if (dto.get(0).getCheckStatus() == null) {
						model.addValidationError(ApplicationSession.getInstance().getMessage("trade.cheque.not.clear"));
						model.setViewMode(MainetConstants.FlagH);
					} else {
						LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(dto.get(0).getCheckStatus());
						if (!lookUp.getLookUpCode().equals(MainetConstants.Property.CLEARED)) {
							model.addValidationError(
									ApplicationSession.getInstance().getMessage("trade.cheque.not.clear"));
							model.setViewMode(MainetConstants.FlagH);
						}
					}

				}
			}
			model.setRmRcptno(dto.get(0).getRmRcptno());
			model.setRmAmount(dto.get(0).getRmAmount());
		}
		model.setTradeDetailDTO(
				tradeLicenseApplicationService.getTradeLicenseWithAllDetailsByApplicationId(applicationId));
//Defect#130694 for validate if cheque is dishonoured
		ModelAndView mv = new ModelAndView("changeInBusDetailsApproval", MainetConstants.FORM_NAME, model);
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;
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
		final ChangeInBusinessPrintingModel model = this.getModel();
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

		// set value for SKDCL ENV Defect #131834
		Department dept = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartment(MainetConstants.TradeLicense.MARKET_LICENSE, MainetConstants.FlagA);
		this.getModel().setDepartment(dept);
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
				} else if(!flag) {
					model.setApprejMasList(apprejMasList);
				}
			}
		} else {
			model.setApprejMasList(apprejMasList);
		}

		return new ModelAndView("changeInBusDetailsLicensePrint", MainetConstants.FORM_NAME, this.getModel());
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
		ChangeInBusinessPrintingModel model = this.getModel();

		LookUp lookUp = CommonMasterUtility.getValueFromPrefixLookUp("I", "LIS",
				UserSession.getCurrent().getOrganisation());

		model.getTradeDetailDTO().setTrdStatus(lookUp.getLookUpId());
		if (model.saveForm()) {
			return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

		} else
			return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);

	}
}
