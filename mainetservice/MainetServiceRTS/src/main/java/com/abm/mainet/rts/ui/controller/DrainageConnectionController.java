package com.abm.mainet.rts.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.ChargeDetailDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.WardZoneDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.rts.domain.DrainageConnectionRoadDetEntity;
import com.abm.mainet.rts.dto.DrainageConnectionDto;
import com.abm.mainet.rts.dto.DrainageConnectionRoadDetDTO;
import com.abm.mainet.rts.dto.MediaChargeAmountDTO;
import com.abm.mainet.rts.service.DrainageConnectionService;
import com.abm.mainet.rts.service.IRtsService;
import com.abm.mainet.rts.service.RtsBRMSService;
import com.abm.mainet.rts.ui.model.DrainageConnectionModel;
import com.abm.mainet.rts.ui.model.WaterRateMaster;

@Controller
@RequestMapping("/drainageConnection.html")
public class DrainageConnectionController extends AbstractFormController<DrainageConnectionModel> {

	@Autowired
	private DrainageConnectionService drainageConnectionService;

	@Autowired
	private ILocationMasService locationMasterService;

	@Autowired
	BRMSCommonService brmsCommonService;

	@Autowired
	private RtsBRMSService rtsBrmsService;

	@Autowired
	private IRtsService rtsService;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private ICFCApplicationMasterService cfcApplicationMasterService;
	
	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;
	
	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	

	private static final Logger LOGGER = Logger.getLogger(DrainageConnectionController.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(final Model model, final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse, @ModelAttribute("requestDTO") RequestDTO requestDTO) {
		LOGGER.info("Index Started----------------------------->");
		ModelAndView mv;
		fileUpload.sessionCleanUpForFileUpload();
		this.sessionCleanup(httpServletRequest);
		this.getModel().setRequestDTO(requestDTO);
		loadWardZone(httpServletRequest);
		LOGGER.info("ServiceMaster Started----------------------------->");
		ServiceMaster serviceMasterData = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceByShortName(MainetConstants.RightToService.DCS,
						UserSession.getCurrent().getOrganisation().getOrgid());

		if (StringUtils.equalsIgnoreCase(
				CommonMasterUtility.getNonHierarchicalLookUpObject(serviceMasterData.getSmChklstVerify(),
						UserSession.getCurrent().getOrganisation()).getLookUpCode(),
				MainetConstants.FlagA)) {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagY);
		} else {
			this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
		}
		if (StringUtils.equalsIgnoreCase(serviceMasterData.getSmAppliChargeFlag(), MainetConstants.FlagY)) {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagY);
			this.getModel().setFree(false);
			this.getModel().getRequestDTO().setPayStatus(MainetConstants.FlagF);
		} else {
			this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagN);
			this.getModel().getRequestDTO().setPayStatus(MainetConstants.FlagY);
			this.getModel().setFree(true);
		}
		LOGGER.info("ApplicationId----------------------------->"+requestDTO.getApplicationId());
		if (requestDTO.getApplicationId() != null) {
			this.getModel().setSaveMode("V");
			DrainageConnectionDto dto = ApplicationContextProvider.getApplicationContext()
					.getBean(DrainageConnectionService.class).getDrainageConnectionData(requestDTO.getApplicationId(),
							UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setDrainageConnectionDto(dto);

			mv = new ModelAndView("rtsDrainageConnectionForm", MainetConstants.FORM_NAME, getModel());
		} else {

			this.getModel().setSaveMode("C");
			this.getModel().setDrainageConnectionDto(null);
			mv = new ModelAndView("rtsDrainageConnectionForm", MainetConstants.FORM_NAME, getModel());
		}
		LOGGER.info("Index End----------------------------->");
		return mv;
	}

	private void loadWardZone(HttpServletRequest httpServletRequest) {
		List<WardZoneDTO> wardList = new ArrayList<WardZoneDTO>();
		wardList = locationMasterService.findlocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());

		Map<Long, String> wardMapList = new LinkedHashMap<Long, String>();
		for (WardZoneDTO ward : wardList) {
			wardMapList.put(ward.getLocationId(), ward.getLocationName());
		}
		this.getModel().setWardList(wardMapList);

	}

	@RequestMapping(params = "resetdraiangeForm", method = RequestMethod.POST)
	public ModelAndView resetdraiangeForm(final Model model, final HttpServletRequest httpServletRequest) {
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		// this.getModel().setRequestDTO(requestDTO);
		return new ModelAndView("rtsDrainageConnectionForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "previousPage", method = RequestMethod.POST)
	public ModelAndView previousPage(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		// this.getModel().setRequestDTO(requestDTO);
		return new ModelAndView("rtsDrainageConnectionForm", MainetConstants.FORM_NAME, getModel());
	}

	@RequestMapping(params = "showCheckList", method = RequestMethod.POST)
	public ModelAndView showCheckList(final Model model, final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		DrainageConnectionModel dcsModel = this.getModel();
		RequestDTO reqDTO = dcsModel.getRequestDTO();
		reqDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		String isBPLFlag = MainetConstants.FlagN;
		Long applicationType = dcsModel.getDrainageConnectionDto().getApplicantType();
		String applicationCode=null;
		try {
		 applicationCode = CommonMasterUtility.findLookUpCode("AT",
				UserSession.getCurrent().getOrganisation().getOrgid(),
				dcsModel.getDrainageConnectionDto().getApplType());
		}catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		reqDTO.setApplicationType(applicationType);
		ModelAndView mv = null;

		/* Method for getting Checklist Document for BPL from rules */

		try {

			List<DocumentDetailsVO> docs = null;
			final WSRequestDTO initRequestDto = new WSRequestDTO();
			initRequestDto.setModelName(MainetConstants.RightToService.CHECKLIST_RTSRATE_MASTER);
			WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);

			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {

				List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
				List<Object> waterRateMasterlist = RestClient.castResponse(response, WaterRateMaster.class, 1);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterlist.get(0);
				// RTSRateMaster rtsRateMaster = new RTSRateMaster();
				// BeanUtils.copyProperties(waterRateMaster, rtsRateMaster);

				checkListModel.setOrgId(reqDTO.getOrgId());
				checkListModel.setServiceCode(MainetConstants.RightToService.DCS);
				checkListModel.setIsBPL(isBPLFlag);
				if(applicationCode!=null)
				checkListModel.setUsageSubtype1(applicationCode);
				// checkListModel.setApplicantType(reqDTO.getApplicationType());

				final WSRequestDTO checkRequestDto = new WSRequestDTO(); //
				checkRequestDto.setDataModel(checkListModel);
				WSResponseDTO checklistResp = brmsCommonService.getChecklist(checkRequestDto);
				if (response.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
					docs = (List<DocumentDetailsVO>) checklistResp.getResponseObj();

					if (docs != null && !docs.isEmpty()) {
						long cnt = 1;
						for (final DocumentDetailsVO doc : docs) {
							doc.setDocumentSerialNo(cnt);
							cnt++;
						}
					}

				} else {
					mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
				}

				dcsModel.setCheckList(docs);
				/* end of checklist document fetch code */
				waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				waterRateMaster.setServiceCode(MainetConstants.RightToService.DCS);
				waterRateMaster.setChargeApplicableAt(
						Long.toString(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.LookUpPrefix.APL,
								MainetConstants.NewWaterServiceConstants.CAA,
								UserSession.getCurrent().getOrganisation()).getLookUpId()));
				final WSRequestDTO taxRequestDto = new WSRequestDTO();
				taxRequestDto.setDataModel(waterRateMaster);

				/* Method of getting Service Charge from BRMS rule */
				WSResponseDTO responseDTO = null;
				try {
					responseDTO = rtsBrmsService.getApplicableTaxes(taxRequestDto);
				} catch (Exception e) {
					dcsModel.getOfflineDTO().setAmountToShow(0d);
					this.getModel()
							.addValidationError(ApplicationSession.getInstance().getMessage("rts.validation.payment"));
					mv = new ModelAndView("rtsCheckListFormValdin", MainetConstants.FORM_NAME, getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}

				// added by rajesh.das Defect #78975
				if (dcsModel.getDrainageConnectionDto().getApmApplicationId() != null) {

					this.getModel().setApplicationchargeApplFlag(MainetConstants.FlagP);
					this.getModel().setCheckListApplFlag(MainetConstants.FlagN);
					List<DocumentDetailsVO> dvo = rtsService.getRtsUploadedCheckListDocuments(
							dcsModel.getDrainageConnectionDto().getApmApplicationId(),
							dcsModel.getDrainageConnectionDto().getOrgId());

					this.getModel().setCheckList(new ArrayList<DocumentDetailsVO>());
					this.getModel().setCheckList(dvo);
				}

				if (responseDTO.getWsStatus() != null
						&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
					if (!responseDTO.isFree()) {
						final List<Object> rates = (List<Object>) responseDTO.getResponseObj();
						final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
						for (final Object rate : rates) {
							WaterRateMaster master1 = (WaterRateMaster) rate;
							master1 = populateChargeModel(dcsModel, master1);
							if(this.getModel().getDrainageConnectionDto().getNoOfFlat()!=null) {
							master1.setNoOfCopies(this.getModel().getDrainageConnectionDto().getNoOfFlat().intValue());}
							requiredCHarges.add(master1);
						}
						WSRequestDTO chargeReqDto = new WSRequestDTO();
						chargeReqDto.setModelName("WaterRateMaster");
						chargeReqDto.setDataModel(requiredCHarges);
						WSResponseDTO chargesResDTO = rtsBrmsService.getApplicableCharges(chargeReqDto);
						WSResponseDTO applicableCharges = rtsBrmsService.getApplicableCharges(chargeReqDto);
						List<ChargeDetailDTO> detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
						// code added for throwing validation msg when charge not found from BRMS sheet
						// Defect #92930
						if (detailDTOs == null) {
							throw new FrameworkException(
									ApplicationSession.getInstance().getMessage("rts.validation.payment"));
						}
						final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
								.getResponseObj();
						dcsModel.setFree(false);
						dcsModel.getRequestDTO().setFree(false);
						dcsModel.setCharges(chargesToPay(detailDTOs));
						setChargeMap(dcsModel, chargeDetailDTO);
						if (dcsModel.getCharges() != null)
							dcsModel.getOfflineDTO().setAmountToShow(Double.valueOf(dcsModel.getCharges()));
						if (dcsModel.getChargesAmount() != null)
							dcsModel.getOfflineDTO().setAmountToPay(dcsModel.getChargesAmount().toString());
					}
					mv = new ModelAndView(MainetConstants.RightToService.CHECKLISTFORM, MainetConstants.FORM_NAME,
							this.getModel());

				} else {

					mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
				}

			} else {
				mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
			}

		 } catch (final Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			mv = new ModelAndView("rtsCheckListFormValdin", MainetConstants.CommonConstants.COMMAND, getModel());
			getModel().getBindingResult().addError(new ObjectError("", ex.getMessage()));
			mv.addObject(
					BindingResult.MODEL_KEY_PREFIX
							+ ApplicationSession.getInstance().getMessage(MainetConstants.FORM_NAME),
					getModel().getBindingResult());
		}

		return mv;
	}

	@RequestMapping(params = "saveRts", method = RequestMethod.POST)
	public ModelAndView saveCheckListAppdetails(final Model model, final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		JsonViewObject respObj;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long empId = UserSession.getCurrent().getEmployee().getEmpId();
		Long langId = (long) UserSession.getCurrent().getLanguageId();
		String ulbName = UserSession.getCurrent().getOrganisation().getOrgShortNm();
		String ipMacAddress = Utility.getClientIpAddress(httpServletRequest);
		DrainageConnectionModel appModel = this.getModel();
       // #148917
		ServiceMaster sm = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.getServiceMasterByShortCode(MainetConstants.RightToService.DCS, UserSession.getCurrent().getOrganisation().getOrgid());
	      if (sm != null)
		appModel.setServiceMasterData(sm);
		DrainageConnectionDto dto = appModel.getDrainageConnectionDto();
		// appModel.validateBean(appModel, DrainageConnectionFormValidator.class);
		List<DocumentDetailsVO> docs = appModel.getCheckList();
		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		dto.setDocumentList(docs);
		fileUpload.validateUpload(appModel.getBindingResult());

		if (appModel.hasValidationErrors()) {

			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, appModel.getBindingResult());
			return new ModelAndView(MainetConstants.RightToService.CHECKLISTFORM, MainetConstants.FORM_NAME, appModel);
		}

		dto.setOrgId(orgId);
		dto.setCreatedBy(empId);
		dto.setCreatedDate(new Date());
		dto.setLgIpMac(ipMacAddress);
		dto.setLangId(langId);
		dto.setUlbName(ulbName);
		if (appModel.validateInputs()) {
		if (appModel.saveForm()) {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage(appModel.getSuccessMessage()));

		} else {
			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("Could not save the Application."));

		}
		}else {
			model.addAttribute(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, appModel.getBindingResult());
			return new ModelAndView(MainetConstants.RightToService.CHECKLISTFORM, MainetConstants.FORM_NAME, appModel);
		}
		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}

	private WaterRateMaster populateChargeModel(DrainageConnectionModel model, final WaterRateMaster chargeModel) {
		final Organisation org = UserSession.getCurrent().getOrganisation();
		chargeModel.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		chargeModel.setServiceCode(MainetConstants.RightToService.DCS);

		chargeModel.setRateStartDate(new Date().getTime());
		chargeModel.setDeptCode("RTS");
		return chargeModel;
	}

	private double chargesToPay(final List<ChargeDetailDTO> charges) {
		double amountSum = 0.0;
		for (final ChargeDetailDTO charge : charges) {
			amountSum = amountSum + charge.getChargeAmount();
		}
		return amountSum;
	}

	private void setChargeMap(DrainageConnectionModel model, final List<MediaChargeAmountDTO> charges) {
		final Map<Long, Double> chargesMap = new HashMap<>();
		for (final MediaChargeAmountDTO dto : charges) {
			chargesMap.put(dto.getTaxId(), dto.getChargeAmount());
		}
		model.setChargesList(charges);
		model.setChargesMap(chargesMap);
	}

	@RequestMapping(method = { RequestMethod.GET }, params = "showChargeDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest) {
		return new ModelAndView("ChargesDetailRts", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	// workflow save decision call
	@RequestMapping(params = "saveDecision", method = RequestMethod.POST)
	public ModelAndView approvalDecision(final HttpServletRequest httpServletRequest) {

		JsonViewObject respObj = null;
		boolean updFlag = false;

		this.bindModel(httpServletRequest);

		DrainageConnectionModel model = this.getModel();
		
		if(model.getLevelcheck()==1) {
			model.getDrainageConnectionDto().getRoadDetDto().forEach(roadDetDTO -> {
				roadDetDTO.setConnectionId(model.getDrainageConnectionDto().getConnectionId());
				roadDetDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
				roadDetDTO.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
				roadDetDTO.setCreatedDate(new Date());
				roadDetDTO.setLgIpMac(UserSession.getCurrent().getEmployee().getLgIpMac());
			});			
			drainageConnectionService.saveRoadDetailList(model.getDrainageConnectionDto().getRoadDetDto());
		}
		
		
		String decision = model.getWorkflowActionDto().getDecision();

		// server side validation(Defect #83792 added by Sharvan Mandal)
		Boolean checkFinalAproval = drainageConnectionService.getEmployeeRole(UserSession.getCurrent());
		if (checkFinalAproval) {

			if ((FileUploadUtility.getCurrent().getFileMap() != null)
					&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			} else {

				this.getModel()
						.addValidationError(ApplicationSession.getInstance().getMessage("rts.validation.document"));
				ModelAndView mv = new ModelAndView("rtsApplicantFormView", MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				return mv;
			}
		}
		updFlag = model.callWorkFlow(this.getModel());

		if (updFlag) {
			if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
				if(model.isLastChecker() && model.getLoiNo()!=null) {
					respObj = JsonViewObject
							.successResult((ApplicationSession.getInstance().getMessage("Your LOI Has Been Generated Successfully. Your LOI Number Is :"))+model.getLoiNo());
					}
				else {
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("BirthCertificateDTO.submit.approve"));}}
			else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("BirthCertificateDTO.submit.reject"));
			else if (decision.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.SEND_BACK))
				respObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("social.info.application.sendBack"));
		} else {

			respObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("social.info.application.failure"));
		}

		return new ModelAndView(new MappingJackson2JsonView(), MainetConstants.FORM_NAME, respObj);

	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView executeChangeOfOwner(@RequestParam("appNo") final Long applicationId,
			@RequestParam("taskId") final String serviceId,
			@RequestParam(value = "actualTaskId", required = false) final Long actualTaskId,
			@RequestParam("workflowId") final Long workflowId, final HttpServletRequest request) {
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(request);
		getModel().bind(request);
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.findById(workflowId).getCurrentOrgId();
		ModelAndView mv = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		int langId = UserSession.getCurrent().getLanguageId();
		DrainageConnectionModel model = this.getModel();
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		model.getWorkflowActionDto().setId(workflowId);
		model.getWorkflowActionDto().setComments("");
		model.setParentOrgId(parentOrgId);
		model.setViewFlag("N");
		// added by rajesh.das for Defect #80721
		Boolean checkFinalAproval = drainageConnectionService.getEmployeeRole(UserSession.getCurrent());
		if (checkFinalAproval) {
			model.getWorkflowActionDto().setEmpGroupDescEng("RTS_APPROVER");
		} else {
			model.getWorkflowActionDto().setEmpGroupDescEng(null);
		}

		loadService(request);
		loadWardZone(request);
		String serviceShortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(Long.valueOf(serviceId), UserSession.getCurrent().getOrganisation().getOrgid());
		/*
		 * new Code for Task #79006 start
		 */
		model.getDrainageConnectionDto().setApmApplicationId(applicationId);
		model.getDrainageConnectionDto().setOrgId(orgId);
		model.setLastChecker(iWorkFlowTypeService.isLastTaskInCheckerTaskList(actualTaskId));
		model.setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
		model.setLoiChargeApplFlag("N");

		List<DocumentDetailsVO> attachsList = drainageConnectionService
				.getDrnPortalDocs(model.getDrainageConnectionDto());
		if (!attachsList.isEmpty()) {
			this.getModel().setCheckList(attachsList);
		}

		// end

		try {
			RequestDTO data = ApplicationContextProvider.getApplicationContext()
					.getBean(DrainageConnectionService.class)
					.getApplicationFormData(applicationId, UserSession.getCurrent().getOrganisation().getOrgid());
			data.setServiceId(Long.valueOf(serviceId));
			this.getModel().setRequestDTO(data);
			if (serviceShortCode.equalsIgnoreCase(MainetConstants.RightToService.DCS)) {
				this.getModel()
						.setDrainageConnectionDto(ApplicationContextProvider.getApplicationContext()
								.getBean(DrainageConnectionService.class)
								.getDrainageConnectionData(data.getApplicationId(), orgId));
				this.getModel().getDrainageConnectionDto().setServiceId(Long.valueOf(serviceId));
				this.getModel().getDrainageConnectionDto().setApplTypeDesc(CommonMasterUtility.getNonHierarchicalLookUpObject(this.getModel().getDrainageConnectionDto().getApplType(),
						UserSession.getCurrent().getOrganisation()).getLookUpCode());
				mv = new ModelAndView("rtsApplicantFormView", MainetConstants.FORM_NAME, model);
			}

		} catch (final Exception ex) {
			LOGGER.warn("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
		return mv;
	}
	
	@Override
	@RequestMapping(params ="viewRefNoDetails", method = RequestMethod.POST)
 	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest request )throws Exception{    
		fileUpload.sessionCleanUpForFileUpload();
		sessionCleanup(request);
		getModel().bind(request);
		ModelAndView mv = null;
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		DrainageConnectionModel model = this.getModel();
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setApplicationId(Long.valueOf(applicationId));
		model.getWorkflowActionDto().setComments("");
		model.setViewFlag("Y");

		loadService(request);
		loadWardZone(request);
		String serviceShortCode = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.fetchServiceShortCode(Long.valueOf(serviceId), UserSession.getCurrent().getOrganisation().getOrgid());
		model.getDrainageConnectionDto().setApmApplicationId(Long.valueOf(applicationId));
		model.getDrainageConnectionDto().setOrgId(orgId);

		List<DocumentDetailsVO> attachsList = drainageConnectionService.getDrnPortalDocs(model.getDrainageConnectionDto());
		if (!attachsList.isEmpty()) {
			this.getModel().setCheckList(attachsList);
		}
		try {
			RequestDTO data = ApplicationContextProvider.getApplicationContext()
					.getBean(DrainageConnectionService.class)
					.getApplicationFormData(Long.valueOf(applicationId), UserSession.getCurrent().getOrganisation().getOrgid());
			data.setServiceId(Long.valueOf(serviceId));
			this.getModel().setRequestDTO(data);
			if (serviceShortCode.equalsIgnoreCase(MainetConstants.RightToService.DCS)) {
				this.getModel()
						.setDrainageConnectionDto(ApplicationContextProvider.getApplicationContext()
								.getBean(DrainageConnectionService.class)
								.getDrainageConnectionData(data.getApplicationId(), orgId));

				mv = new ModelAndView("rtsApplicantFormView", MainetConstants.FORM_NAME, model);
			}

		} catch (final Exception ex) {
			LOGGER.warn("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
		return mv;
	}

	
	
	private void loadService(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		List<Object[]> service = null;
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode("RTS");
		final Map<Long, String> serviceMap = new LinkedHashMap<Long, String>();

		service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.findAllActiveServicesForDepartment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId);

		for (final Object[] obj : service) {
			if (obj[0] != null) {
				serviceMap.put((Long) (obj[0]), (String) (obj[1]));
			}
		}
		this.getModel().setServiceList(serviceMap);

	}

	/* For showing acknowledgment page in case of free service */

	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		DrainageConnectionModel model = this.getModel();
		String docStatus = new String();

		if (CollectionUtils.isNotEmpty(model.getCheckList())) {
			List<CFCAttachment> documentUploaded = ApplicationContextProvider.getApplicationContext()
					.getBean(IChecklistVerificationService.class).getAttachDocumentByDocumentStatus(
							Long.valueOf(model.getDrainageConnectionDto().getApmApplicationId()), docStatus,
							UserSession.getCurrent().getOrganisation().getOrgid());
			if (CollectionUtils.isNotEmpty(documentUploaded)) {
				model.setDocumentList(documentUploaded);
			}
		}
		TbCfcApplicationMstEntity cfcEntity = cfcApplicationMasterService.getCFCApplicationByApplicationId(
				Long.valueOf(model.getDrainageConnectionDto().getApmApplicationId()),
				UserSession.getCurrent().getOrganisation().getOrgid());
		model.setCfcEntity(cfcEntity);
		model.setApplicationId(Long.valueOf(model.getDrainageConnectionDto().getApmApplicationId()));
		String name = " ";
		if (model.getCfcEntity().getApmFname() != null) {
			name = model.getCfcEntity().getApmFname() + " ";
		}
		if (model.getCfcEntity().getApmMname()!= null) {
			name += model.getCfcEntity().getApmMname() + " ";
		}
		if (model.getCfcEntity().getApmLname() != null) {
			name += model.getCfcEntity().getApmLname();
		}
		int langId = UserSession.getCurrent().getLanguageId();
		if (langId == 1) {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getONlsOrgname());
		} else {
			model.setOrgName(UserSession.getCurrent().getOrganisation().getoNlsOrgnameMar());
		}
		model.setApplicantName(name);
		model.setServiceName(getApplicationSession().getMessage("DrainageConnectionDTO.serName"));
		model.setFormName(getApplicationSession().getMessage("DrainageConnectionDTO.serviceName"));
		//model.setServiceName(RtsConstants.APPLY_DRAINAGE_CONNECTION);
		//model.setFormName(RtsConstants.DRAINAGE_CONNECTION_FORMNAME);
		return new ModelAndView(MainetConstants.RightToService.DRN_ACK_PAGE, MainetConstants.FORM_NAME, model);

	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "generateLoiCharges", method = RequestMethod.POST)
	public ModelAndView generateLoiCharges(HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		DrainageConnectionModel model = this.getModel();
		model.setLoiChargeApplFlag(MainetConstants.FlagY);
		model.setShowFlag(MainetConstants.FlagY);
		LookUp lookup = CommonMasterUtility.getValueFromPrefixLookUp("RDG", MainetConstants.PG_REQUEST_PROPERTY.TXN,
				UserSession.getCurrent().getOrganisation());
		model.setTaxDesc(lookup.getLookUpDesc());
		ModelAndView mv = null;
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		initRequestDto.setModelName(MainetConstants.RightToService.CHECKLIST_RTSRATE_MASTER);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);
		if (response.getWsStatus() != null
				&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
			List<Object> waterRateMasterlist = RestClient.castResponse(response, WaterRateMaster.class, 1);
			final WaterRateMaster waterRateMaster = (WaterRateMaster) waterRateMasterlist.get(0);
			waterRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
			waterRateMaster.setServiceCode(MainetConstants.RightToService.DCS);
			waterRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
					.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
							MainetConstants.NewWaterServiceConstants.CAA, UserSession.getCurrent().getOrganisation())
					.getLookUpId()));
			final WSRequestDTO taxRequestDto = new WSRequestDTO();
			taxRequestDto.setDataModel(waterRateMaster);

			WSResponseDTO responseDTO = null;
			try {
				responseDTO = rtsBrmsService.getApplicableTaxes(taxRequestDto);
			} catch (Exception e) {
				model.setTotalLoiAmount(0d);

			}
			if (responseDTO.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(responseDTO.getWsStatus())) {
				final List<Object> rates = (List<Object>) responseDTO.getResponseObj();
				final List<WaterRateMaster> requiredCHarges = new ArrayList<>();
				
				final List<WaterRateMaster> chargeModelList = new ArrayList<>();
				
				WaterRateMaster tempRate = null;
				WaterRateMaster rateMaster = null;
				WaterRateMaster dependsOnRateMaster = null;
				for (final Object rate : rates) {
					WaterRateMaster master1 = (WaterRateMaster) rate;
					master1 = populateChargeModel(model, master1);
					if (this.getModel().getDrainageConnectionDto().getNoOfFlat() != null) {
						master1.setNoOfCopies(this.getModel().getDrainageConnectionDto().getNoOfFlat().intValue());
					}
					if(this.getModel().getDrainageConnectionDto().getRoadType()!=null)
					master1.setRoadType(CommonMasterUtility
							.getNonHierarchicalLookUpObject(this.getModel().getDrainageConnectionDto().getRoadType(),
									UserSession.getCurrent().getOrganisation())
							.getLookUpDesc());
					if(this.getModel().getDrainageConnectionDto().getLenRoad()!=null)
					master1.setRoadLength(this.getModel().getDrainageConnectionDto().getLenRoad());
					
					master1.setConnectionType(CommonMasterUtility.getNonHierarchicalLookUpObject(this.getModel().getDrainageConnectionDto().getApplType(),
							UserSession.getCurrent().getOrganisation()).getLookUpDesc());
					requiredCHarges.add(master1);
				}
				List<ChargeDetailDTO> detailDTOs =null;
				WSResponseDTO chargesResDTO=null;
				try {
				LookUp lookupBooking = CommonMasterUtility.getValueFromPrefixLookUp("RDG", "TXN", UserSession.getCurrent().getOrganisation());
				
				for (final WaterRateMaster actualRate : requiredCHarges) {
					if (actualRate.getTaxSubCategory().equals(lookupBooking.getLookUpDesc())) {
							for (final DrainageConnectionRoadDetDTO road : this.getModel().getDrainageConnectionDto().getRoadDetDto()) {
								
									rateMaster = null;
									tempRate = (WaterRateMaster) actualRate.clone();
									dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
									tempRate.setRoadType(CommonMasterUtility.getNonHierarchicalLookUpObject(road.getRoadType(),
									UserSession.getCurrent().getOrganisation()).getLookUpDesc());
									rateMaster = tempRate;
									chargeModelList.add(rateMaster);
							}
						
					} else {
						tempRate = (WaterRateMaster) actualRate.clone();
						dependsOnRateMaster = (WaterRateMaster) actualRate.clone();
						rateMaster = tempRate;
						chargeModelList.add(rateMaster);
					}
				}
				WSRequestDTO chargeReqDto = new WSRequestDTO();
				chargeReqDto.setModelName("WaterRateMaster");
				chargeReqDto.setDataModel(chargeModelList);
				 chargesResDTO = rtsBrmsService.getApplicableCharges(chargeReqDto);
				WSResponseDTO applicableCharges = rtsBrmsService.getApplicableCharges(chargeReqDto);
				detailDTOs = (List<ChargeDetailDTO>) applicableCharges.getResponseObj();
				if (detailDTOs == null) {
					this.getModel()
							.addValidationError(ApplicationSession.getInstance().getMessage("rts.validation.payment"));
					mv = new ModelAndView("rtsApplicantFormView", MainetConstants.FORM_NAME, getModel());
					mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME,
							getModel().getBindingResult());
					return mv;
				}
				}catch(Exception e) {
					LOGGER.info("Exception Occur in LOI"+e);
				}	
				final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
						.getResponseObj();
				model.setFree(false);
				model.getRequestDTO().setFree(false);
				model.setCharges(chargesToPay(detailDTOs));
				setChargeMap(model, chargeDetailDTO);
				model.setTotalLoiAmount(model.getCharges());
				model.setChargesList(chargeDetailDTO);
				if(model.getAppCheck()!=null && model.getAppCheck().equals("V")) {
					mv = new ModelAndView("viewApplicationCharge", MainetConstants.FORM_NAME, this.getModel());
				}else {
					mv = new ModelAndView("rtsApplicantFormView", MainetConstants.FORM_NAME, this.getModel());
				}
			} else {

				mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
			}

		} else {

			mv = new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_VIEW);
		}
		return mv;

	}
	
	@RequestMapping(params = "getBirtReport", method = RequestMethod.POST)
	public @ResponseBody String viewBirthRegister(@RequestParam("applicationNo") String applicationNo,
			@RequestParam("applTypeDesc") String applTypeDesc) {
		if (StringUtils.isNotBlank(applicationNo) && StringUtils.isNotBlank(applTypeDesc)) {
			Long orgId=UserSession.getCurrent().getOrganisation().getOrgid();
			if (applTypeDesc.equals("EC")) {
				return ServiceEndpoints.BIRT_REPORT_URL
						+ "=DrainageApplicationDetailsReport_ExistingStructure.rptdesign&ULBName=" + orgId
						+ "&ApplicationNo=" + applicationNo;
			} else {
				return ServiceEndpoints.BIRT_REPORT_URL
						+ "=DrainageApplicationDetailsReport_NewStructure.rptdesign&ULBName=" + orgId
						+ "&ApplicationNo=" + applicationNo;
			}
		} else {
			return "f";
		}
	}

}
