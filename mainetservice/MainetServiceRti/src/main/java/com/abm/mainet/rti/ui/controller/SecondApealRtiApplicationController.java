package com.abm.mainet.rti.ui.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.brms.datamodel.CheckListModel;
import com.abm.mainet.common.integration.brms.service.BRMSCommonService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.SecondRtiApplicationFormModel;

@Controller
@RequestMapping("/SecondApealRtiApplication.html")
public class SecondApealRtiApplicationController extends AbstractFormController<SecondRtiApplicationFormModel> {
	@Autowired
	IRtiApplicationDetailService iRtiApplicationDetailService;
	@Resource
	IFileUploadService fileUpload;
	@Resource
	private BRMSCommonService brmsCommonService;
	@Autowired
	ServiceMasterService serviceMasterService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private IWorkflowRequestService requestService;

	@Resource
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private IChecklistVerificationService ichckService;
	@Autowired
	private IAttachDocsService attachDocsService;

	@Autowired
	private DepartmentService departmentService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		this.sessionCleanup(request);
		bindModel(request);
		fileUpload.sessionCleanUpForFileUpload();
		SecondRtiApplicationFormModel model = this.getModel();

		Organisation org = UserSession.getCurrent().getOrganisation();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		ModelAndView modelAndView = null;

		ServiceMaster service = serviceMasterService
				.getServiceByShortName(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE, orgId);

		/* To check whether service is configured or not and active or inactive */

		if (service.getSmServiceId() == null) {
			{
				modelAndView = new ModelAndView("SecondApealRtiApplication", MainetConstants.FORM_NAME,
						this.getModel());
				if (service.getSmServActive() == null) {
					modelAndView = new ModelAndView("SecondApealRtiApplication", MainetConstants.FORM_NAME,
							this.getModel());
					this.getModel().getBindingResult().addError(new ObjectError("",
							ApplicationSession.getInstance().getMessage("rti.validation.inactive.service")));
				}
				this.getModel().getBindingResult().addError(new ObjectError("",
						ApplicationSession.getInstance().getMessage("rti.validation.service.ntconfig")));
			}
			modelAndView.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.CommonConstants.COMMAND,
					getModel().getBindingResult());
		} else {

			List<RtiApplicationFormDetailsReqDTO> rtiApplicationDetailList = iRtiApplicationDetailService
					.getRTIApplicationDetailBy(org.getOrgid());
//filtering data to show in forwarded organisation
			model.setRtiApplicationList(rtiApplicationDetailList.stream()
					.filter(r -> r.getFrdOrgId() == null || r.getFrdOrgId() == 0
							|| (r.getFrdOrgId() != null && r.getFrdOrgId().equals(orgId)))
					.collect(Collectors.toList()));
			modelAndView = new ModelAndView("SecondApealRtiApplication", MainetConstants.FORM_NAME, this.getModel());
		}

		setCommonFields(model);

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = { "view" }, method = { RequestMethod.POST })
	public ModelAndView viewRtiForm(HttpServletRequest request,
			@RequestParam("applicationId") final Long applicationId) {

		fileUpload.sessionCleanUpForFileUpload();

		this.getModel().bind(request);
		final SecondRtiApplicationFormModel model = this.getModel();

		// For filtering deprtment RTI from lookup
		List<LookUp> lookUp = CommonMasterUtility.getListLookup(MainetConstants.LookUpPrefix.PFR,
				UserSession.getCurrent().getOrganisation());
		if (lookUp.get(0).getLookUpDesc().trim().equalsIgnoreCase(MainetConstants.APP_NAME.DSCL)) {
			Long deptId = departmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
			model.setDepartments(
					model.getDepartments().stream().filter(l -> l.getLookUpId() == deptId).collect(Collectors.toSet()));
		}

		ModelAndView mv = null;
		Organisation org = UserSession.getCurrent().getOrganisation();
		RtiApplicationFormDetailsReqDTO rtiDTO = model.getRtiApplicationList().stream()
				.filter(dto -> applicationId.equals(dto.getApmApplicationId())).findAny().orElse(null);
		// for geting latest second appeal record Defect #81535
		rtiApplicationDetailService.setSecondAppealDataByRtiNo(rtiDTO);
		if (rtiDTO.getAppealType() != null && rtiDTO.getAppealType().equalsIgnoreCase(MainetConstants.FlagF)) {
			rtiDTO.setRtiSubject("");
		}

		rtiDTO.setOrgId(org.getOrgid());

		model.setReqDTO(rtiDTO);

		RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		model.setLocations(getLocationByDepartment((long) reqDTO.getRtiDeptId()));

		List<DocumentDetailsVO> docs = null;
		final WSRequestDTO initRequestDto = new WSRequestDTO();
		WSResponseDTO checklistResp = new WSResponseDTO();
		initRequestDto.setModelName(MainetConstants.RTISERVICE.CHECKLIST_RTIRATE_MASTER);
		WSResponseDTO response = brmsCommonService.initializeModel(initRequestDto);

		if (model.getIsFree().equals(MainetConstants.FlagN)) {
			if (response.getWsStatus() != null
					&& MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(response.getWsStatus())) {
				List<Object> checklist = RestClient.castResponse(response, CheckListModel.class, 0);
				CheckListModel checkListModel = (CheckListModel) checklist.get(0);
				checkListModel.setOrgId(org.getOrgid());
				checkListModel.setServiceCode(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE);
				checkListModel.setApplicantType(reqDTO.getApplicant());

				final WSRequestDTO checkRequestDto = new WSRequestDTO();
				checkRequestDto.setDataModel(checkListModel);
				checklistResp = brmsCommonService.getChecklist(checkRequestDto);
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

				model.setSecondcheckList(docs);
			}
		} else {
			mv = new ModelAndView("SecondApealRtiApplicationValdin", MainetConstants.FORM_NAME, model);
			model.addValidationError("Please configure as free service");
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
			return mv;
		}
		// for setting checklist data which was already uploaded Defect #81535
		if ((rtiDTO.getAppealType() != null && rtiDTO.getAppealType().equalsIgnoreCase(MainetConstants.FlagS))
				&& !CollectionUtils.isEmpty(docs)) {
			List<AttachDocs> attachsList = new ArrayList<>();
			final List<CFCAttachment> attCfc = ichckService.findAttachmentsForAppId(rtiDTO.getApmApplicationId(), null,
					rtiDTO.getOrgId());
			model.setDocumentList(attCfc);
			model.setServiceFlag(MainetConstants.FlagY);
		}
		// adding code for checking checklist available or not Defect #71847
		if (CollectionUtils.isEmpty(docs) && (checklistResp.getWsStatus() != null
				&& !checklistResp.getWsStatus().equals(MainetConstants.REQUIRED_PG_PARAM.NA))) {

			mv = new ModelAndView("SecondApealRtiApplicationValdin", MainetConstants.FORM_NAME, model);
			model.addValidationError("Please configure checklist");
			mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

			return mv;

		}

		mv = new ModelAndView("secondRtiApplicationDetailForm", MainetConstants.FORM_NAME, model);
		return mv;

	}

	@RequestMapping(params = "save", method = RequestMethod.POST)
	public ModelAndView saveRtiApplication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException {
		this.getModel().bind(httpServletRequest);

		SecondRtiApplicationFormModel model = this.getModel();
		model.bind(httpServletRequest);

		List<DocumentDetailsVO> docs = model.getSecondcheckList();
		List<DocumentDetailsVO> docList = model.getUploadFileList();

		if (docs != null) {
			docs = fileUpload.prepareFileUpload(docs);
		}
		if (docList != null && docList.get(0).getDocumentByteCode() != null) {
			docList = fileUpload.prepareFileUpload(docList);
		}
		model.getReqDTO().setDocumentList(docs);
		model.getReqDTO().setFetchDocs(docList);
		prepareFileUploadForImg(docList);

		ModelAndView mv = null;
		fileUpload.validateUpload(model.getBindingResult());
		if (model.validateInputDocs(docs)) {
			if (model.saveForm()) {
				return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

			} else
				return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
		}

		mv = new ModelAndView("secondRtiApplicationDetailFormValdin", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	@RequestMapping(params = "getLocationByDepartment", method = RequestMethod.POST)
	@ResponseBody
	public Set<LookUp> getLocationByDepartment(@RequestParam(value = "deptId", required = true) Long deptId) {
		final RtiApplicationFormDetailsReqDTO dto = this.getModel().getReqDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		dto.setDeptId(deptId);
		Set<LookUp> locList = rtiApplicationDetailService.getDeptLocation(orgId, deptId);
		this.getModel().setLocations(locList);
		return locList;
	}

	@RequestMapping(params = "proceed", method = RequestMethod.POST)
	public ModelAndView confirmToProceed(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		getModel().bind(httpServletRequest);
		final SecondRtiApplicationFormModel model = this.getModel();
		Long applicationId = model.getReqDTO().getApmApplicationId();
		WorkflowRequest workflowRequest = requestService.findByApplicationId(applicationId);
		model.setReqDTO(rtiApplicationDetailService.fetchRtiApplicationInformationById(applicationId, orgId));
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(applicationId));
		model.setCfcEntity(cfcService.getCFCApplicationByApplicationId(applicationId, orgId));
		model.getReqDTO().setApplicantName(model.getCfcEntity().getApmFname().concat(MainetConstants.WHITE_SPACE)
				.concat(model.getCfcEntity().getApmLname()));
		model.getReqDTO().setDateDesc(
				new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(model.getReqDTO().getApmApplicationDate()));
		model.getReqDTO().setDepartmentName(
				rtiApplicationDetailService.getdepartmentNameById(Long.valueOf(model.getReqDTO().getRtiDeptId())));
		/*
		 * if(workflowRequest.getStatus()!=null) {
		 * model.getReqDTO().setStatus(workflowRequest.getStatus()); }
		 */
		this.getModel().setReqDTO(model.getReqDTO());
		return new ModelAndView(MainetConstants.RTISERVICE.RTI_SUCCESS_PAGE, MainetConstants.FORM_NAME, model);

	}

	private void setCommonFields(SecondRtiApplicationFormModel model) {
		final RtiApplicationFormDetailsReqDTO dto = model.getReqDTO();
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		dto.setOrgId(orgId);
		model.setOrgId(orgId);
		final ServiceMaster service = serviceMasterService.getServiceByShortName("RSA", orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getSmServiceId());
		model.getReqDTO().setServiceId(service.getSmServiceId());
		Long smFeesSchedule = service.getSmFeesSchedule();

		if (smFeesSchedule == 1)
			model.setIsFree(MainetConstants.FlagY);
		else
			model.setIsFree(MainetConstants.FlagN);

	}

	@RequestMapping(params = { "back" }, method = { RequestMethod.POST })
	public ModelAndView backToSearch(HttpServletRequest request) {
		SecondRtiApplicationFormModel model = this.getModel();
		model.bind(request);
		ModelAndView mv = new ModelAndView("SecondApealRtiApplicationValdin", MainetConstants.FORM_NAME, getModel());
		mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
		return mv;

	}

	public List<DocumentDetailsVO> prepareFileUploadForImg(List<DocumentDetailsVO> document) throws IOException {

		final Map<Long, String> listOfString = new HashMap<>();
		final Map<Long, String> fileName = new HashMap<>();
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						fileName.put(entry.getKey(), file.getName());
						listOfString.put(entry.getKey(), bytestring);
					} catch (final IOException e) {
						// LOGGER.error("Exception has been occurred in file byte to string
						// conversions", e);
					}
				}
			}
		}
		if (document != null && !document.isEmpty() && !listOfString.isEmpty()) {
			long count = 200;
			for (final DocumentDetailsVO d : document) {
				if (d.getDocumentSerialNo() != null) {
					count = d.getDocumentSerialNo() - 1;

				}
				if (listOfString.containsKey(count) && fileName.containsKey(count)) {
					d.setDocumentByteCode(listOfString.get(count));
					d.setDocumentName(fileName.get(count));
				}
				count++;
			}
		}

		return document;
	}

}
