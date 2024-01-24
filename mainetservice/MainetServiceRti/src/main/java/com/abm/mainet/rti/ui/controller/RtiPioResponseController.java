package com.abm.mainet.rti.ui.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.loi.domain.TbLoiDetEntity;
import com.abm.mainet.cfc.loi.domain.TbLoiMasEntity;
import com.abm.mainet.cfc.loi.dto.TbLoiDet;
import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiDetService;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.dto.TbApprejMas;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.integration.dto.WSResponseDTO;
import com.abm.mainet.common.integration.report.utility.ReportUtility;
import com.abm.mainet.common.master.dto.EmployeeBean;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.dto.TbComparentDet;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.service.TbComparentDetService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.rti.datamodel.RtiRateMaster;
import com.abm.mainet.rti.dto.FormEReportDTO;
import com.abm.mainet.rti.dto.MediaChargeAmountDTO;
import com.abm.mainet.rti.dto.RtiApplicationFormDetailsReqDTO;
import com.abm.mainet.rti.dto.RtiFrdEmployeeDetails;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.service.IRtiApplicationDetailService;
import com.abm.mainet.rti.ui.model.RtiPioResponseModel;
import com.abm.mainet.rti.utility.RtiUtility;

@Controller
@RequestMapping("/PioResponse.html")
public class RtiPioResponseController extends AbstractFormController<RtiPioResponseModel> {

	private static Logger log = Logger.getLogger(RtiPioResponseController.class);

	@Resource
	IFileUploadService fileUpload;
	@Autowired
	private TbComparamDetService tbComparamService;
	@Autowired
	IRtiApplicationDetailService rtiApplicationDetailService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ICFCApplicationMasterService cfcService;

	@Autowired
	private RtiUtility rtiUtility;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	ServiceMasterService serviceMasterService;

	@Resource
	private TbApprejMasService tbApprejMasService;

	@Autowired
	private IWorkflowTyepResolverService workflowTyepResolverService;

	@Autowired
	private TbDepartmentService tbDepartmentService;

	@Autowired
	private ITaskAssignmentService taskAssignmentService;

	@Autowired
	private TbLoiMasService tbLoiMasService;

	@Autowired
	private TbLoiDetService tbLoiDetService;

	@Autowired
	private TbComparentDetService comparentDetService;

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewRtiApplication(final HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId)
			throws ParseException {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		final RtiPioResponseModel model = getModel();
		// US#111591 change for orgid dependancy
		model.setReqDTO(rtiApplicationDetailService.fetchRtiApplicationInformationById(applicationId, 0L));
		RtiApplicationFormDetailsReqDTO rtidto = model.getReqDTO();
		model.setFetchDocumentList(
				(iChecklistVerificationService.getDocumentUploadedForAppId(applicationId, rtidto.getOrgId())));

		model.setFetchApplnUpload(iChecklistVerificationService.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo(),
				rtidto.getOrgId()));
		// US#111591
		// Change as per RTI Demo point of anuj
		List<CFCAttachment> att = iChecklistVerificationService.getDocumentUploadedByRefNo(
				model.getReqDTO().getRtiNo() + MainetConstants.DEPT_SHORT_NAME.RTI, rtidto.getOrgId());
		if (!CollectionUtils.isEmpty(att)) {
			if (att.size() > 1) {
				model.setFetchPioUploadDoc(Arrays.asList(att.get(att.size() - 1)));
			} else {
				model.setFetchPioUploadDoc(att);
			}
		}
		// D#122582
		model.setFetchStampDoc(iChecklistVerificationService
				.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo() + MainetConstants.FlagS, rtidto.getOrgId()));
//added regarding US#111612
		model.setFetchPostalDoc(iChecklistVerificationService
				.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo() + MainetConstants.FlagP, rtidto.getOrgId()));
		setDMSPath();

		model.setCfcAddressEntity(cfcService.getApplicantsDetails(applicationId));
		model.setCfcEntity(cfcService.getCFCApplicationByApplicationId(applicationId, rtidto.getOrgId()));

		model.getReqDTO().setApplicant(rtiUtility.getPrefixDesc(PrefixConstants.APPLICATION_TYPE_PREFIX,
				model.getCfcEntity().getCcdApmType()));
		model.getReqDTO()
				.setTitle(rtiUtility.getPrefixDesc(PrefixConstants.LookUp.TITLE, model.getCfcEntity().getApmTitle()));
		if(model.getCfcEntity().getApmSex()!=null) {
		model.getReqDTO().setGen(rtiUtility.getPrefixDesc(PrefixConstants.MobilePreFix.GENDER,
				Long.parseLong(model.getCfcEntity().getApmSex())));}
		/* getting location name from location ID */
		model.getReqDTO()
				.setLocationName(rtiApplicationDetailService.getlocationNameById(
						Long.valueOf(model.getReqDTO().getRtiLocationId()),
						UserSession.getCurrent().getOrganisation().getOrgid()));
		/* Defect #74661 by rajesh.das */

		if (model.getReqDTO().getCorrAddrsAreaName() == null || model.getReqDTO().getCorrAddrsAreaName().isEmpty()) {
			model.getReqDTO().setCorrAddrsAreaName(model.getReqDTO().getAddress());
		}
		if (model.getReqDTO().getCorrAddrsPincodeNo() == null) {
			model.getReqDTO().setCorrAddrsPincodeNo(model.getReqDTO().getPincodeNo());
		}
		/* end */
		if (model.getCfcEntity().getApmBplNo() != null && !model.getCfcEntity().getApmBplNo().isEmpty()) {
			model.getReqDTO().setIsBPL(MainetConstants.YES);
		} else {
			model.getReqDTO().setIsBPL(MainetConstants.NO);
		}

		model.getReqDTO().setInwardTypeName(
				rtiUtility.getPrefixDesc(PrefixConstants.INWARD_TYPE, Long.valueOf(model.getReqDTO().getInwardType())));
		/* fetching Department Name based on Department Id */
		model.getReqDTO().setDepartmentName(
				rtiApplicationDetailService.getdepartmentNameById(Long.valueOf(model.getReqDTO().getRtiDeptId())));
		model.setCommonHelpDocs("RtiApplicationDetailForm.html");
		/* end */
		/* Date Formatting */
		model.getReqDTO().setApplicationDate(model.getReqDTO().getApmApplicationDate());
		model.getReqDTO().setReferenceDate(Utility.dateToString(model.getReqDTO().getInwReferenceDate()));
		/* end */
		model.getReqDTO().setTaskId(taskId);
		List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation().getOrgid());
		boolean dsclEnv = envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals(MainetConstants.ENV_DSCL)
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));
		if (dsclEnv) {
			this.getModel().setEnvFlag(MainetConstants.FlagY);
		}
		// US#34043
		model.setListOrg(ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.findAllActiveOrganization(MainetConstants.FlagA));
		// US#109003
		Long deptId=tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)) {
			model.getReqDTO().setFrdDeptId(deptId);
			setRtiActionAndIfo(model);
		}
		model.setFdlDepartments(
				model.getDepartments().stream()
						.filter(dept -> dept != null && dept.getLookUpId() == deptId)
						.collect(Collectors.toSet()));
		model.setRtiEmployee(serviceMasterService.findAllActiveServicesForDepartment(UserSession.getCurrent().getOrganisation().getOrgid(), deptId).stream().filter(obj->obj[3]!=null && (!obj[3].toString().equals(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE)&&!obj[3].toString().equals(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL)&&!obj[3].toString().equals(MainetConstants.RTISERVICE.RTISECONDAPPEALSERVICE))).collect(Collectors.toList()));
		//model.setRtiEmployee(employeeService.findActiveEmployeeAndDsgByDeptId(deptId, UserSession.getCurrent().getOrganisation().getOrgid()));
		try {
			LookUp lookup  = CommonMasterUtility.getValueFromPrefixLookUp(MainetConstants.RTI_SLI_DAYS_RANGE, MainetConstants.ENV, UserSession.getCurrent().getOrganisation());
			model.setSliDaysList(LongStream.range(1, Integer.valueOf(lookup.getOtherField())).boxed().collect(Collectors.toList()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			model.setRtiFrdEmpDet(rtiApplicationDetailService.setRtiFwdEmployeeDetails(applicationId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setLoidet(rtiApplicationDetailService.setLoiDetails(Long.valueOf(model.getReqDTO().getRtiId()),UserSession.getCurrent().getOrganisation().getOrgid()));
		ModelAndView mv = null;
		mv = new ModelAndView(MainetConstants.RTISERVICE.PIO_RESPONSE, MainetConstants.FORM_NAME, model);
		return mv;

	}



	private void setRtiActionAndIfo(RtiPioResponseModel model) {
		
		List<LookUp> rtiAction=CommonMasterUtility.getLookUps(PrefixConstants.ACTION, UserSession.getCurrent().getOrganisation());
		List<LookUp> rtiInfo=CommonMasterUtility.getLookUps("RIN", UserSession.getCurrent().getOrganisation());
		if(StringUtils.equalsIgnoreCase(UserSession.getCurrent().getRoleCode(), "Deemed PIO")) {
			rtiAction=rtiAction.stream().filter(act->act!=null && act.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagA)).collect(Collectors.toList());
			rtiInfo=rtiInfo.stream().filter(act->act!=null && act.getLookUpCode().equalsIgnoreCase(MainetConstants.FlagF)).collect(Collectors.toList());
		}
		model.setRtiAction(rtiAction);	
		model.setPartialInfoFlag(rtiInfo);
	}



	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.RTISERVICE.REMARK_DETAILS_BY_ACTION)
	public @ResponseBody List<TbApprejMas> getRemarkDetailsByAction(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "rtiAction") Long rtiAction) {
		getModel().bind(httpServletRequest);
		final RtiPioResponseModel model = getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ServiceMaster service = serviceMasterService
				.getServiceByShortName(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		model.setServiceMaster(service);
		model.setServiceId(service.getSmServiceId());
		String remark = "";
		LookUp decision1 = CommonMasterUtility.getNonHierarchicalLookUpObject(rtiAction,
				UserSession.getCurrent().getOrganisation());

		if (decision1.getLookUpCode().equals(MainetConstants.FlagA)) {
			model.setApplicationStatus(MainetConstants.FlagA);
		}

		if (decision1.getLookUpCode().equals(MainetConstants.FlagA)) {
			remark = PrefixConstants.REJ;
		} else if (decision1.getLookUpCode().equals(MainetConstants.FlagR)) {
			remark = PrefixConstants.REJ;
		}
		/*
		 * if(((decision1.getLookUpCode().equals("A")) &&
		 * (decision1.getLookUpCode().equals("P"))) ||
		 * decision1.getLookUpCode().equals("R")) { remark=PrefixConstants.REJ; }
		 */
		/*
		 * if(decision1.getLookUpCode().equals("A")) { remark=WATERMODULEPREFIX.APP;
		 * }else if(decision1.getLookUpCode().equals("R")){ remark=PrefixConstants.REJ;
		 * }
		 */ Long remarkId = null;
		final List<LookUp> lookUpList = CommonMasterUtility.getListLookup(PrefixConstants.WATERMODULEPREFIX.REM,
				UserSession.getCurrent().getOrganisation());
		for (final LookUp Lookup1 : lookUpList) {

			if (Lookup1.getLookUpCode().equalsIgnoreCase(remark)) {
				remarkId = Lookup1.getLookUpId();
				break;
			}
		}
		this.getModel().setApprejMasList(tbApprejMasService.findByRemarkType(service.getSmServiceId(), remarkId));
		return this.getModel().getApprejMasList();

	}

	/* /For Getting Employee name by Department */
	@RequestMapping(params = MainetConstants.RTISERVICE.GET_EMP_NAME, method = RequestMethod.POST)
	public @ResponseBody List<Object[]> getActiveEmpNameByDeptId(@RequestParam("deptId") final Long deptId) {

		List<Object[]> obj = employeeService.findActiveEmployeeByDeptId(deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		return obj;
	}
	/* End */
	@RequestMapping(params = "initiateWorkFlow", method = RequestMethod.POST)
	public @ResponseBody boolean initiateWorkFlow(@RequestParam("empName") final Long empname,@RequestParam("remList") final String remList,final HttpServletRequest request) {
		bindModel(request);
		RtiApplicationFormDetailsReqDTO reqDTO = getModel().getReqDTO();
		rtiApplicationDetailService.initiateWorkFlowForFrdEmploye(reqDTO,empname,remList);
		return true;
	}
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.RTISERVICE.FILE_COUNT_UPLOAD)
	public ModelAndView fileCountUpload(final HttpServletRequest request) {

		bindModel(request);
		FileUploadUtility.getCurrent().getFileMap().entrySet();
		List<DocumentDetailsVO> attachments = new ArrayList<>();
		List<RtiMediaListDTO> progress = new ArrayList<>();
		for (int i = 0; i <= this.getModel().getPioDoc().size(); i++) {
			attachments.add(new DocumentDetailsVO());
			progress.add(new RtiMediaListDTO());
		}
		int count = 0;
		for (RtiMediaListDTO dto : this.getModel().getRtiMediaListDTO()) {
			BeanUtils.copyProperties(dto, progress.get(count));
			count++;

		}
		this.getModel().setPioDoc(attachments);
		this.getModel().setRtiMediaListDTO(progress);
		Long count1 = 0l;
		Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
		for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
			final List<File> list = new ArrayList<>(entry.getValue());
			if (!list.isEmpty()) {
				fileMap1.put(count1, entry.getValue());
				count1++;
			}
		}
		FileUploadUtility.getCurrent().setFileMap(fileMap1);
		return new ModelAndView(MainetConstants.RTISERVICE.RTI_FILE_UPLOAD, MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "doEntryDeletion")
	public void doEntryDeletion(@RequestParam(name = MainetConstants.WorksManagement.ID, required = false) int id,
			final HttpServletRequest request) {
		bindModel(request);
		this.getModel().getRtiMediaListDTO().remove(id);
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {

			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				if (id == entry.getKey().intValue()) {
					entry.getValue().clear();
				}

			}
			Long count1 = 0l;
			Map<Long, Set<File>> fileMap1 = new LinkedHashMap<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				if (!list.isEmpty()) {
					fileMap1.put(count1, entry.getValue());
					count1++;
				}
			}
			FileUploadUtility.getCurrent().setFileMap(fileMap1);

		}

	}

	/* Method for PIO response save and rule call for getting media charge */
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.RTISERVICE.SAVE_AND_MEDIA_CHARGE)
	public ModelAndView saveAndMediaCharge(final HttpServletRequest httpServletRequest) {
		ModelAndView mv = null;
		this.getModel().bind(httpServletRequest);
		RtiPioResponseModel model = this.getModel();
		String flag = MainetConstants.FlagN;
		RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		if (reqDTO.getOtherRemarks() != null
				&& (reqDTO.getOtherRemarks().startsWith(",") || reqDTO.getOtherRemarks().startsWith("0"))
				|| reqDTO.getOtherRemarks().endsWith(",") || reqDTO.getOtherRemarks().endsWith("0")) {
//D#125469
			String s = "";
			StringBuilder sb = new StringBuilder(reqDTO.getOtherRemarks());
			if (!StringUtils.isEmpty(sb.toString()) && reqDTO.getOtherRemarks().endsWith(",")) {
				s = sb.deleteCharAt(sb.length() - 1).toString();
			} else if (!StringUtils.isEmpty(sb.toString()) && reqDTO.getOtherRemarks().startsWith(",")) {
				s = sb.deleteCharAt(0).toString();
			}
			reqDTO.setRtiRemarks(s);
			reqDTO.setOtherRemarks(s);
		} else {

			reqDTO.setRtiRemarks(reqDTO.getOtherRemarks());
		}
		// Defect #87885
		if (reqDTO.getPartialInfoFlag() != Integer.valueOf(MainetConstants.ZERO)) {
			TbComparamDet tbCompDet = tbComparamService.findById((long) reqDTO.getPartialInfoFlag());
			if ((tbCompDet.getCpdValue().equals(MainetConstants.FlagF))) {
				reqDTO.setRtiRemarks(MainetConstants.ZERO);
			}
		}
		// US#111591
		List<DocumentDetailsVO> docList = model.getAttachments();
		if (docList != null) {
			docList = fileUpload.prepareFileUpload(docList);
		}

		model.setAttachments(docList);
		try {
			prepareFileUploadForImg(docList);
		} catch (Exception e) {
			logger.error("Exception has been occurred in file byte to string conversions", e);
		}
		// US#111591 end
		/*
		 * Validating Duplicate Media Type ,If not duplicate than BRMS call for getting
		 * total amount to pay when LOI applicable
		 */
		Organisation organisation = new Organisation();

		organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());

		Long loiNotApplicable = Long.valueOf(model.getReqDTO().getLoiApplicable());

		LookUp loiNotApplicabledesc = CommonMasterUtility.getNonHierarchicalLookUpObject(loiNotApplicable,
				organisation);

		List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV,
				UserSession.getCurrent().getOrganisation().getOrgid());
		boolean dsclEnv = envLookUpList.stream().anyMatch(env -> env.getLookUpCode().equals(MainetConstants.ENV_DSCL)
				&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));

		if (model.getReqDTO().getLoiApplicable() != null
				&& MainetConstants.CommonMasterUi.LOI_APPLICABLE.equalsIgnoreCase(rtiUtility.getPrefixCode(
						PrefixConstants.LOI_APPLICABLE, Long.valueOf(model.getReqDTO().getLoiApplicable())))
				&& MainetConstants.AuthStatus.APPROVED.equalsIgnoreCase(rtiUtility.getPrefixCode(PrefixConstants.ACTION,
						Long.valueOf(model.getReqDTO().getRtiAction())))) {
			if (model.validateDuplicateMediaType()) {
				// Us#116366
				if (MainetConstants.NO.equalsIgnoreCase(model.getReqDTO().getIsBPL()) && (dsclEnv)) {
					for (int j = 0; j < model.getRtiMediaListDTO().size(); j++) {
						model.getRtiMediaListDTO().get(j)
								.setMediaTypeDesc(rtiUtility.getPrefixCode(PrefixConstants.MEDIA_TYPE,
										Long.valueOf(model.getRtiMediaListDTO().get(j).getMediaType())));
						if ((model.getRtiMediaListDTO().get(j).getMediaTypeDesc()
								.equals(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A4)
								|| (model.getRtiMediaListDTO().get(j).getMediaTypeDesc()
										.equals(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A3_A4)))
								&& model.getRtiMediaListDTO().get(j).getQuantity() != null
								&& model.getRtiMediaListDTO().get(j).getQuantity() <= MainetConstants.NUMBERS.FIVE) {
							reqDTO.setLoiApplicable(null);
							model.saveForm();
							flag = MainetConstants.FlagY;
						} else {
							final WSRequestDTO wsRequestDto = new WSRequestDTO();
							WSResponseDTO wsResponseDto = null;
							wsRequestDto.setModelName(MainetConstants.RTISERVICE.RTI_RATE_MASTER);
							wsResponseDto = RestClient.callBRMS(wsRequestDto,
									ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
							if (MainetConstants.WebServiceStatus.SUCCESS
									.equalsIgnoreCase(wsResponseDto.getWsStatus())) {
								final List<Object> rtiRateMasterList = RestClient.castResponse(wsResponseDto,
										RtiRateMaster.class);
								final RtiRateMaster rtiRateMaster = (RtiRateMaster) rtiRateMasterList.get(0);
								rtiRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
								rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
								rtiRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
										.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
												MainetConstants.NewWaterServiceConstants.CAA,
												UserSession.getCurrent().getOrganisation())
										.getLookUpId()));

								final WSRequestDTO taxRequestDto = new WSRequestDTO();
								taxRequestDto.setDataModel(rtiRateMaster);
								final WSResponseDTO taxResponseDTO = rtiApplicationDetailService
										.getApplicableTaxes(taxRequestDto);
								if (taxResponseDTO.getWsStatus() != null && MainetConstants.WebServiceStatus.SUCCESS
										.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
									if (!taxResponseDTO.isFree()) {
										final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
										final List<RtiRateMaster> requiredCharges = new ArrayList<>();
										for (final Object rate : rates) {
											for (int i = 0; i < model.getRtiMediaListDTO().size(); i++) {
												RtiRateMaster rtiChargeMaster = (RtiRateMaster) rate;
												rtiChargeMaster = populateChargeModel(model, rtiChargeMaster, i);
												requiredCharges.add(rtiChargeMaster);
											}
										}
										WSRequestDTO chargeReqDto = new WSRequestDTO();
										chargeReqDto.setDataModel(requiredCharges);
										WSResponseDTO chargesResDTO = rtiApplicationDetailService
												.getApplicableCharges(chargeReqDto);
										final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
												.getResponseObj();
										List<MediaChargeAmountDTO> finalChargeDetailDTO = setChargeAmount(
												chargeDetailDTO);
										model.setChargeAmountList(finalChargeDetailDTO);
									}

								}

							}

						}

					}
				}

				else {
					final WSRequestDTO wsRequestDto = new WSRequestDTO();
					WSResponseDTO wsResponseDto = null;
					wsRequestDto.setModelName(MainetConstants.RTISERVICE.RTI_RATE_MASTER);
					wsResponseDto = RestClient.callBRMS(wsRequestDto,
							ServiceEndpoints.BRMSMappingURL.INITIALIZE_MODEL_URL);
					if (MainetConstants.WebServiceStatus.SUCCESS.equalsIgnoreCase(wsResponseDto.getWsStatus())) {
						final List<Object> rtiRateMasterList = RestClient.castResponse(wsResponseDto,
								RtiRateMaster.class);
						final RtiRateMaster rtiRateMaster = (RtiRateMaster) rtiRateMasterList.get(0);
						rtiRateMaster.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
						rtiRateMaster.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
						rtiRateMaster.setChargeApplicableAt(Long.toString(CommonMasterUtility
								.getValueFromPrefixLookUp(PrefixConstants.NewWaterServiceConstants.SCRUTINY,
										MainetConstants.NewWaterServiceConstants.CAA,
										UserSession.getCurrent().getOrganisation())
								.getLookUpId()));

						final WSRequestDTO taxRequestDto = new WSRequestDTO();
						taxRequestDto.setDataModel(rtiRateMaster);
						final WSResponseDTO taxResponseDTO = rtiApplicationDetailService
								.getApplicableTaxes(taxRequestDto);
						if (taxResponseDTO.getWsStatus() != null && MainetConstants.WebServiceStatus.SUCCESS
								.equalsIgnoreCase(taxResponseDTO.getWsStatus())) {
							if (!taxResponseDTO.isFree()) {
								final List<Object> rates = (List<Object>) taxResponseDTO.getResponseObj();
								final List<RtiRateMaster> requiredCharges = new ArrayList<>();
								for (final Object rate : rates) {
									for (int i = 0; i < model.getRtiMediaListDTO().size(); i++) {
										RtiRateMaster rtiChargeMaster = (RtiRateMaster) rate;
										rtiChargeMaster = populateChargeModel(model, rtiChargeMaster, i);
										requiredCharges.add(rtiChargeMaster);
									}
								}
								WSRequestDTO chargeReqDto = new WSRequestDTO();
								chargeReqDto.setDataModel(requiredCharges);
								WSResponseDTO chargesResDTO = rtiApplicationDetailService
										.getApplicableCharges(chargeReqDto);
								final List<MediaChargeAmountDTO> chargeDetailDTO = (List<MediaChargeAmountDTO>) chargesResDTO
										.getResponseObj();
								List<MediaChargeAmountDTO> finalChargeDetailDTO = setChargeAmount(chargeDetailDTO);
								model.setChargeAmountList(finalChargeDetailDTO);
							}

						}

					}
				}

			} else {
				mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_PIO_VALDIN, MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());

				return mv;
			}
		}
		if (flag.equalsIgnoreCase(MainetConstants.FlagN)) {
			if (model.saveForm()) {
				if (model.getApplicationStatus() != null && model.getApplicationStatus() == "A"
						&& StringUtils.isNotEmpty(loiNotApplicabledesc.getLookUpCode())
						&& loiNotApplicabledesc.getLookUpCode().equals("LA")) {
					return new ModelAndView("EdiatbleLOIviewForm", MainetConstants.FORM_NAME, model);
				} else {
					return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));
				}
			} else {
				mv = new ModelAndView(MainetConstants.RTISERVICE.RTI_PIO_VALDIN, MainetConstants.FORM_NAME, getModel());
				mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("rti.valid.workflow"));
				return mv;

			}

		}

		/* end */
		return jsonResult(JsonViewObject.successResult(model.getSuccessMessage()));

	}

	private RtiRateMaster populateChargeModel(RtiPioResponseModel model, RtiRateMaster rtiRateMaster, int i) {

		final RtiRateMaster rtiRateMasterRule = new RtiRateMaster();

		rtiRateMasterRule.setTaxType(rtiRateMaster.getTaxType());
		rtiRateMasterRule.setTaxCode(rtiRateMaster.getTaxCode());
		rtiRateMasterRule.setTaxCategory(rtiRateMaster.getTaxCategory());
		rtiRateMasterRule.setTaxSubCategory(rtiRateMaster.getTaxSubCategory());
		rtiRateMasterRule.setSlab1(rtiRateMaster.getSlab1());
		rtiRateMasterRule.setSlab2(rtiRateMaster.getSlab2());
		rtiRateMasterRule.setSlab3(rtiRateMaster.getSlab3());
		rtiRateMasterRule.setSlab4(rtiRateMaster.getSlab4());
		rtiRateMasterRule.setSlabRate1(rtiRateMaster.getSlabRate1());
		rtiRateMasterRule.setSlabRate2(rtiRateMaster.getSlabRate2());
		rtiRateMasterRule.setSlabRate3(rtiRateMaster.getSlabRate3());
		rtiRateMasterRule.setSlabRate4(rtiRateMaster.getSlabRate4());
		rtiRateMasterRule.setFlatRate(rtiRateMaster.getFlatRate());
		rtiRateMasterRule.setChargeApplicableAt(rtiRateMaster.getChargeApplicableAt());
		rtiRateMasterRule.setPercentageRate(rtiRateMaster.getPercentageRate());
		rtiRateMasterRule.setChargeAmount(rtiRateMaster.getChargeAmount());
		rtiRateMasterRule.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		rtiRateMasterRule.setServiceCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE);
		rtiRateMasterRule.setDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		rtiRateMasterRule.setRateStartDate(new Date().getTime());
		if (MainetConstants.NO.equalsIgnoreCase(model.getReqDTO().getIsBPL())) {
			rtiRateMasterRule.setIsBPL(MainetConstants.N_FLAG);
		} else if (MainetConstants.YES.equalsIgnoreCase(model.getReqDTO().getIsBPL())) {
			rtiRateMasterRule.setIsBPL(MainetConstants.Y_FLAG);
		}
		rtiRateMasterRule.setMediaType(rtiUtility.getPrefixCode(PrefixConstants.MEDIA_TYPE,
				Long.valueOf(model.getRtiMediaListDTO().get(i).getMediaType())));
		rtiRateMasterRule.setQuantity(model.getRtiMediaListDTO().get(i).getQuantity());
		rtiRateMasterRule.setFreeCopy(rtiRateMaster.getFreeCopy());
		rtiRateMasterRule.setTaxId(rtiRateMaster.getTaxId());

		return rtiRateMasterRule;

	}

	private List<MediaChargeAmountDTO> setChargeAmount(List<MediaChargeAmountDTO> chargeDetailDTO) {

		List<MediaChargeAmountDTO> chargeAmountList = new ArrayList<>();
		RtiPioResponseModel model = this.getModel();
		Double totalbeforesave = 0.0;
		Double totalaftersave = 0.0;

		for (final MediaChargeAmountDTO dto : chargeDetailDTO) {

			/*
			 * Calculate to actual amount to be paid by User after considering the free copy
			 * <if Applicable>
			 */
			Double total = null;
			Double newTotal = null;

			// Us#116366
			List<LookUp> envLookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.ENV,
					UserSession.getCurrent().getOrganisation().getOrgid());
			boolean dsclEnv = envLookUpList.stream()
					.anyMatch(env -> env.getLookUpCode().equals(MainetConstants.ENV_DSCL)
							&& StringUtils.equals(env.getOtherField(), MainetConstants.FlagY));

			if (dsclEnv && (dto.getMediaType().equalsIgnoreCase(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A4)
					|| dto.getMediaType().equalsIgnoreCase(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A3_A4))) {
				dto.setFreeCopy(MainetConstants.Common_Constant.NUMBER.FIVE_DOUBLE);
				if (dto.getChargeAmount() > 0) {
					total = (dto.getQuantity() - dto.getFreeCopy()) * dto.getChargeAmount();
				}
				if (dto.getEditedAmount() > 0) {
					newTotal = (dto.getQuantity() - dto.getFreeCopy()) * dto.getEditedAmount();
				}
			} else {
				if (dto.getQuantity() > dto.getFreeCopy()) {
					if (dto.getChargeAmount() > 0) {
						total = (dto.getQuantity() - dto.getFreeCopy()) * dto.getChargeAmount();
					}
					if (dto.getEditedAmount() > 0) {
						newTotal = (dto.getQuantity() - dto.getFreeCopy()) * dto.getEditedAmount();
					}

				} else if (dto.getFreeCopy() >= dto.getQuantity()) {
					total = 0.0;
					newTotal = 0.0;
				}
			}

			if (total != null && total > 0) {
				dto.setTotal(total);
			}
			if (newTotal != null && newTotal > 0) {
				dto.setNewTotal(newTotal);
			}

			if (total != null && total > 0) {

				totalbeforesave = totalbeforesave + dto.getTotal();
				model.setTotalBeforeSave(totalbeforesave);
			}
			if (newTotal != null && newTotal > 0) {
				totalaftersave = totalaftersave + dto.getNewTotal();
				model.setTotalAfterSave(totalaftersave);
			}

			/* In Editable LOI enable only Model/Sample to edit start */

			LookUp code = CommonMasterUtility.getValueFromPrefixLookUp(dto.getMediaType(), PrefixConstants.MEDIA_TYPE);
			// For setting Reg Value D#129967
			dto.setMediatypeMar(code.getDescLangSecond());
			if (StringUtils.equals(code.getLookUpCode(), MainetConstants.RTISERVICE.RTI_MODEL_N_SAMPLE_SHORTCODE)
					|| StringUtils.equals(code.getLookUpCode(), "MODEL/SAMPLE")) {
				dto.setEditableLoiFlag(MainetConstants.FlagN);
			}
            //Defect No-139588
			else if(code.getLookUpCode().equals("LS")
					|| code.getLookUpCode().equals("LARGE-SIZE")
					|| code.getLookUpCode().equals("LARGESIZE")) {
				dto.setEditableLoiFlag(MainetConstants.FlagN);
			}
			//Defect #156823
			else if(code.getLookUpCode().equals(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A4)
					|| code.getLookUpCode().equals(PrefixConstants.RTI_PREFIX.MEDIA_TYPE_A3_A4)) {
				dto.setEditableLoiFlag(MainetConstants.FlagN);
			}

			/* In Editable LOI enable only Model/Sample ,LARGE SIZE to edit end */

			chargeAmountList.add(dto);

		}

		model.setChargeAmountList(chargeAmountList);

		if (model.getTotalBeforeSave() == null) {
			model.setTotalBeforeSave(0.0);
		}

		if (model.getTotalAfterSave() == null) {
			model.setTotalAfterSave(0.0);
		}

		model.getChargeAmountList();

		return chargeAmountList;

	}

	/* Method for to Print FormE Report */
	@RequestMapping(params = MainetConstants.RTISERVICE.PRINT_FORME_REPORT, method = RequestMethod.POST)
	public ModelAndView printRejAppReport(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse) {
		bindModel(httpServletRequest);
		final RtiPioResponseModel model = getModel();
		final List<FormEReportDTO> dtolist = rtiApplicationDetailService.getRTIApplicationDetail(
				model.getReqDTO().getApmApplicationId(), UserSession.getCurrent().getOrganisation());
		final Map<String, Object> map = new HashMap<>();
		final String subReportSource = Filepaths.getfilepath() + MainetConstants.JASPER_REPORT_NAME
				+ MainetConstants.FILE_PATH_SEPARATOR;
		map.put("SUBREPORT_DIR", subReportSource);
		String jrxmlName = "";
		jrxmlName = "FormELetter_English.jrxml";
		final String jrxmlFileLocation = Filepaths.getfilepath() + "jasperReport" + MainetConstants.FILE_PATH_SEPARATOR
				+ jrxmlName;
		String fileName = ReportUtility.generateReportFromCollectionUtility(httpServletRequest, httpServletResponse,
				jrxmlFileLocation, map, dtolist, UserSession.getCurrent().getEmployee().getEmpId());
		// Defect_34043
		/*
		 * String s[] = fileName.split(MainetConstants.FILE_PATH_SEPARATOR +
		 * MainetConstants.FILE_PATH_SEPARATOR); String fleName = s[0] +
		 * MainetConstants.FILE_PATH_SEPARATOR + MainetConstants.FILE_PATH_SEPARATOR +
		 * s[1]; if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
		 * getModel().setFilePath(fleName); }
		 */
		if (!fileName.equals(MainetConstants.SERVER_ERROR)) {
			if (fileName.contains(MainetConstants.DOUBLE_BACK_SLACE)) {
				fileName = fileName.replace(MainetConstants.DOUBLE_BACK_SLACE, MainetConstants.QUAD_BACK_SLACE);
			} else if (fileName.contains(MainetConstants.DOUBLE_FORWARD_SLACE)) {
				fileName = fileName.replace(MainetConstants.DOUBLE_FORWARD_SLACE, MainetConstants.QUAD_FORWARD_SLACE);
			}
			getModel().setFilePath(fileName);
		}
		getModel().setRedirectURL("AdminHome.html");
		return new ModelAndView(MainetConstants.URL_EVENT.OPEN_NEXT_TAB, MainetConstants.FORM_NAME, model);
	}
	/* End */

	@RequestMapping(method = RequestMethod.POST, params = "printRejectReport")
	public ModelAndView applicantInfoReport(final HttpServletRequest request) {
		this.getModel().bind(request);
		RtiPioResponseModel model = this.getModel();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RtiApplicationFormDetailsReqDTO rtiDto = model.getReqDTO();

		ServiceMaster sm = serviceMasterService.getServiceMasterByShortCode("RAF", orgId);
		model.setServiceMaster(sm);
		model.getReqDTO().setDateDesc(Utility.dateToString(rtiDto.getApmApplicationDate()));
		TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService
				.getCFCApplicationByApplicationId(rtiDto.getApmApplicationId(), orgId);
		model.setCfcEntity(cfcApplicationMstEntity);
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(rtiDto.getApmApplicationId()));
		String fullName = String.join(" ", Arrays.asList(cfcApplicationMstEntity.getApmFname(),
				cfcApplicationMstEntity.getApmMname(), cfcApplicationMstEntity.getApmLname()));
		rtiDto.setApplicantName(fullName);

		List<RtiApplicationFormDetailsReqDTO> dto = rtiApplicationDetailService
				.getRtiDetails(rtiDto.getApmApplicationId(), orgId);
		List<TbApprejMas> tbApprejMas = new ArrayList<>();
		final List<Long> artId = new ArrayList<>(0);
		Long woId = null;
		for (RtiApplicationFormDetailsReqDTO appRej : dto) {
			woId = Long.valueOf(appRej.getRtiId());
			if (appRej.getRtiRemarks() != null && !appRej.getRtiRemarks().isEmpty()) {
				artId.add(Long.valueOf(appRej.getRtiRemarks()));
			}
		}
		if (artId != null && !artId.isEmpty()) {
			tbApprejMas = tbApprejMasService.findByArtId(artId, UserSession.getCurrent().getOrganisation().getOrgid());
			model.setApprejMasList(tbApprejMas);
		}
		Department dept = tbDepartmentService.findDepartmentByCode("RTI");
		// US#109003
		WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(orgId, dept.getDpDeptid(),
				sm.getSmServiceId(), rtiDto.getTrdWard1(), rtiDto.getTrdWard2(), rtiDto.getTrdWard3(),
				rtiDto.getTrdWard4(), rtiDto.getTrdWard5());

		Long group = 1l;

		TaskAssignmentRequest taskDto = new TaskAssignmentRequest();
		taskDto.setWorkflowTypeId(workflowMas.getWfId());
		taskDto.setServiceEventName("PIO Response");
		LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> hashmap = taskAssignmentService
				.getEventLevelGroupsByWorkflowTypeAndEventName(taskDto);

		LinkedHashMap<String, TaskAssignment> grp1 = hashmap
				.get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + group);

		for (int j = 1; j <= grp1.size(); j++) {
			TaskAssignment ta = grp1.get(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + j);

			List<Long> actorIdList = new ArrayList<>();
			String[] empIds = ta.getActorId().split(MainetConstants.operator.COMMA);
			for (String s : empIds) {
				actorIdList.add(Long.valueOf(s));
			}
			if (!actorIdList.isEmpty()) {
				String emp = MainetConstants.BLANK;
				List<Employee> empList = employeeService.getEmpDetailListByEmpIdList(actorIdList, orgId);

				for (Employee employee : empList) {
					emp += employee.getEmpname() + MainetConstants.WHITE_SPACE + employee.getEmpmname()
							+ MainetConstants.WHITE_SPACE + employee.getEmplname();
				}
				model.setPioName(emp);
			}
		}

		return new ModelAndView("RtiRejectionReport", MainetConstants.FORM_NAME, this.getModel());
	}

	@RequestMapping(method = RequestMethod.POST, params = "printLOILetter")
	public ModelAndView LoiLetter(final HttpServletRequest request) {
		// sessionCleanup(request);
		this.getModel().bind(request);
		RtiPioResponseModel model = this.getModel();
		// 78415by rajesh Das
		String deptName = model.getReqDTO().getDepartmentName();
		Employee employee1 = UserSession.getCurrent().getEmployee();
		TbLoiDet loiDet = null;
		final List<TbLoiDet> loiDetails = new ArrayList<>();
		setChargeAmount(model.getChargeAmountList());

		model.setEditableLOIflag("Y");

		/* Task #31165 */

		for (int k = 0; k < model.getChargeAmountList().size(); k++) {
			loiDet = new TbLoiDet();

			loiDet.setLoiAmount(BigDecimal.valueOf(model.getChargeAmountList().get(k).getNewTotal()));
			loiDet.setLoiPrevAmt(BigDecimal.valueOf(model.getChargeAmountList().get(k).getTotal()));
			loiDet.setLoiDetN3(Long.valueOf(k));
			loiDet.setLoiRemarks(model.getChargeAmountList().get(k).getRemarks());
			loiDetails.add(loiDet);

		}
		/* update loi master amount */

		if (model.getTotalBeforeSave() != null) {

			rtiApplicationDetailService.updateLoiMasterAmount(BigDecimal.valueOf(model.getTotalAfterSave()),
					model.getReqDTO().getApmApplicationId());
		}

		for (int j = 0; j < loiDetails.size(); j++) {

			TbLoiMasEntity loiId = rtiApplicationDetailService.getLoiIdbyAppno(model.getReqDTO().getApmApplicationId());
			if (loiId != null) {
				List<TbLoiDetEntity> entity = tbLoiDetService.findLoiDetailsByLoiIdAndOrgId(loiId.getLoiId(),
						UserSession.getCurrent().getOrganisation().getOrgid());
				List<TbLoiDet> detsList = new ArrayList<TbLoiDet>();
				int m = 0;
				for (TbLoiDetEntity detEntity : entity) {
					TbLoiDet det = new TbLoiDet();
					BeanUtils.copyProperties(detEntity, det);
					det.setLoiDetN3(Long.valueOf(m++));
					for (TbLoiDet tbLoiDet : loiDetails) {
						if (tbLoiDet.getLoiDetN3().equals(det.getLoiDetN3())) {
							det.setLoiAmount(tbLoiDet.getLoiAmount());
							det.setLoiPrevAmt(tbLoiDet.getLoiPrevAmt());
							det.setLoiRemarks(tbLoiDet.getLoiRemarks());
							/* update loi details amount */
							rtiApplicationDetailService.updateLoiDetailsAmount(det.getLoiAmount(), det.getLoiPrevAmt(),
									det.getLoiRemarks(), det.getLoiDetId());
						}

					}

				}
			}

		}

		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		RtiApplicationFormDetailsReqDTO rtiDto = model.getReqDTO();

		TbCfcApplicationMstEntity cfcApplicationMstEntity = cfcService
				.getCFCApplicationByApplicationId(rtiDto.getApmApplicationId(), rtiDto.getOrgId());
		model.setCfcEntity(cfcApplicationMstEntity);
		model.setCfcAddressEntity(cfcService.getApplicantsDetails(rtiDto.getApmApplicationId()));
		RtiApplicationFormDetailsReqDTO rtiApplicationDto = rtiApplicationDetailService
				.fetchRtiApplicationInformationById(rtiDto.getApmApplicationId(), orgId);
		model.setReqDTO(rtiApplicationDto);
		List<RtiMediaListDTO> mediDto = rtiApplicationDetailService.getMediaList(Long.valueOf(rtiDto.getRtiId()),
				orgId);
		model.setRtiMediaListDTO(mediDto);

		if (cfcApplicationMstEntity.getApmApplicationDate() != null) {
			model.setDateDesc(Utility.dateToString(cfcApplicationMstEntity.getApmApplicationDate()));
		}

		ServiceMaster sm = serviceMasterService
				.getServiceMasterByShortCode(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE, orgId);
		getModel().setServiceMaster(sm);
		List<TbLoiMas> tbLoiMas = tbLoiMasService.getloiByApplicationId(rtiDto.getApmApplicationId(),
				sm.getSmServiceId(), orgId);
		model.setLoidata(tbLoiMas);

		for (TbLoiMas loiData : model.getLoidata()) {

			if (loiData.getLoiNo().equals(tbLoiMas.get(0).getLoiNo())) {
				RtiApplicationFormDetailsReqDTO rtiLoidto = model.getReqDTO();
				rtiLoidto.setAmountToPay(String.valueOf(loiData.getLoiAmount()));
				String.valueOf(loiData.getLoiAmount());
				final String amountInWords = Utility.convertBiggerNumberToWord(loiData.getLoiAmount());
				model.setAmountInWords(amountInWords);

				model.setLoiId(loiData.getLoiId());
				break;
			}
		}

		List<TbLoiDetEntity> loidetlist = tbLoiDetService.findLoiDetailsByLoiIdAndOrgId(model.getLoiId(), orgId);

		model.setA3a4Quantity(0);
		model.setFlopCopy(0);
		model.setLargeCopy(0l);
		model.setPageQuantity1(0d);
		model.setPageQuantity2(0d);
		model.setPageQuantity3(0d);
		model.setPageQuantity4(0d);
		model.setPageQuantityForCharges(0d);
		model.setPageQuantityForPhoto(0d);

		for (int i = 0; i < model.getRtiMediaListDTO().size(); i++) {
			for (int j = 0; i < loidetlist.size(); i++) {
				mediDto.get(i).setMediaTypeDesc(rtiUtility.getPrefixCode(PrefixConstants.MEDIA_TYPE,
						Long.valueOf(mediDto.get(i).getMediaType())));

				if (mediDto.get(i).getMediaTypeDesc().equals("A4") || mediDto.get(i).getMediaTypeDesc().equals("A4-A3")
						|| mediDto.get(i).getMediaTypeDesc().equals("A-4")) {
					if (mediDto.get(i).getQuantity() != null) {
						model.setPage(loidetlist.get(i).getLoiDetN2());
						model.getRtiLoiReportDTO().setFreeCopyPages(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountPages(model.getChargeAmountList().get(i).getChargeAmount());
						 */
						model.getRtiLoiReportDTO()
								.setEditedAmountPages(model.getChargeAmountList().get(i).getEditedAmount());
					}

					/*
					 * model.setPageQuantity1(new
					 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
					 */

					model.setPageQuantity1(model.getChargeAmountList().get(i).getNewTotal());

				}
				if (mediDto.get(i).getMediaTypeDesc().equals("A-3")) {
					if (mediDto.get(i).getQuantity() != null) {
						model.setPageA3(loidetlist.get(i).getLoiDetN2());
						model.getRtiLoiReportDTO().setFreeCopyPagesA3(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountPages(model.getChargeAmountList().get(i).getChargeAmount());
						 */
						model.getRtiLoiReportDTO()
								.setEditedAmountA3Pages(model.getChargeAmountList().get(i).getEditedAmount());
					}

					/*
					 * model.setPageQuantity1(new
					 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
					 */

					model.setPageQuantityA3(model.getChargeAmountList().get(i).getNewTotal());

				}

				if (mediDto.get(i).getMediaTypeDesc().equals("LS")
						|| mediDto.get(i).getMediaTypeDesc().equals("LARGE-SIZE")
						|| mediDto.get(i).getMediaTypeDesc().equals("LARGESIZE")) {
					if (mediDto.get(i).getQuantity() != null) {

						model.setLargeCopy(loidetlist.get(i).getLoiDetN2());

						/*
						 * model.setPageQuantity2(new
						 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
						 */

						model.setPageQuantity2(model.getChargeAmountList().get(i).getNewTotal());

						model.getRtiLoiReportDTO()
								.setFreeCopyLargePage(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountLargePage(model.getChargeAmountList().get(i).getChargeAmount(
						 * ));
						 */

						model.getRtiLoiReportDTO()
								.setEditedAmountLargePage(model.getChargeAmountList().get(i).getEditedAmount());
					}

				}

				if (mediDto.get(i).getMediaTypeDesc().equals("CD")
						|| mediDto.get(i).getMediaTypeDesc().equals("CD-FLOPPY")
						|| mediDto.get(i).getMediaTypeDesc().equals("F")) {
					if (mediDto.get(i).getQuantity() != null) {

						model.setFlopCopy(loidetlist.get(i).getLoiDetN2());
						// model.setPageQuantity3(new
						// Double(loidetlist.get(i).getLoiAmount().doubleValue()));

						model.setPageQuantity3(model.getChargeAmountList().get(i).getNewTotal());
						model.getRtiLoiReportDTO().setFreeCopyFloppy(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountFloppy(model.getChargeAmountList().get(i).getChargeAmount());
						 */

						model.getRtiLoiReportDTO()
								.setEditedAmountFloppy(model.getChargeAmountList().get(i).getEditedAmount());

					}
				}

				if (mediDto.get(i).getMediaTypeDesc().equals("MS")
						|| mediDto.get(i).getMediaTypeDesc().equals("MODEL-SAMPLE")
						|| mediDto.get(i).getMediaTypeDesc().equals("MODEL/SAMPLE")) {
					if (mediDto.get(i).getQuantity() != null) {

						model.setInspection(loidetlist.get(i).getLoiDetN2());
						/*
						 * model.setPageQuantity4(new
						 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
						 */

						model.setPageQuantity4(model.getChargeAmountList().get(i).getNewTotal());

						model.getRtiLoiReportDTO().setFreecopyModel(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountModel(model.getChargeAmountList().get(i).getChargeAmount());
						 */

						model.getRtiLoiReportDTO()
								.setEditedAmountModel(model.getChargeAmountList().get(i).getEditedAmount());

					}
				}

				if (mediDto.get(i).getMediaTypeDesc().equals("IMG")
						|| mediDto.get(i).getMediaTypeDesc().equals("PHOTO")) {
					if (mediDto.get(i).getQuantity() != null) {
						model.setPhoto(loidetlist.get(i).getLoiDetN2());

						/*
						 * model.setPageQuantityForPhoto(new
						 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
						 */

						model.setPageQuantityForPhoto(model.getChargeAmountList().get(i).getNewTotal());

						model.getRtiLoiReportDTO().setFreeCopyPhoto(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountPhoto(model.getChargeAmountList().get(i).getChargeAmount());
						 */

						model.getRtiLoiReportDTO()
								.setEditedAmountPhoto(model.getChargeAmountList().get(i).getEditedAmount());

					}
				}

				if (mediDto.get(i).getMediaTypeDesc().equals("INS")
						|| mediDto.get(i).getMediaTypeDesc().equals("INSPECTION-CHARGES")) {
					if (mediDto.get(i).getQuantity() != null) {

						model.setInspectioncharges(loidetlist.get(i).getLoiDetN2());

						/*
						 * model.setPageQuantityForCharges(new
						 * Double(loidetlist.get(i).getLoiAmount().doubleValue()));
						 */

						model.setPageQuantityForCharges(model.getChargeAmountList().get(i).getNewTotal());

						model.getRtiLoiReportDTO()
								.setFreeCopyInspection(model.getChargeAmountList().get(i).getFreeCopy());
						/*
						 * model.getRtiLoiReportDTO()
						 * .setChargeAmountInspection(model.getChargeAmountList().get(i).getChargeAmount
						 * ());
						 */
						model.getRtiLoiReportDTO()
								.setEditedAmountInspection(model.getChargeAmountList().get(i).getEditedAmount());

					}
				}

				Double total = 0.0d;

				if (model.getPageQuantity1() != null) {
					total = model.getPageQuantity1();
				}
				if (model.getPageQuantity2() != null) {
					total = total + model.getPageQuantity2();
				}
				if (model.getPageQuantity3() != null) {
					total = total + model.getPageQuantity3();
				}
				if (model.getPageQuantity4() != null) {
					total = total + model.getPageQuantity4();
				}
				if (model.getPageQuantityForPhoto() != null) {
					total = total + model.getPageQuantityForPhoto();
				}
				if (model.getPageQuantityForCharges() != null) {
					total = total + model.getPageQuantityForCharges();
				}
				model.setGrandTotal(model.getLoidata().get(0).getLoiAmount());
			}

			Department dept = tbDepartmentService.findDepartmentByCode("RTI");
			// add by rajesh,das
			dept.setDpDeptdesc(deptName);
			//
			model.setDepartment(dept);
// US#109003
			WorkflowMas workflowMas = workflowTyepResolverService.resolveServiceWorkflowType(orgId, dept.getDpDeptid(),
					model.getServiceMaster().getSmServiceId(), rtiApplicationDto.getTrdWard1(),
					rtiApplicationDto.getTrdWard2(), rtiApplicationDto.getTrdWard3(), rtiApplicationDto.getTrdWard4(),
					rtiApplicationDto.getTrdWard5());

			Long group = 1l;

			TaskAssignmentRequest taskDto = new TaskAssignmentRequest();
			taskDto.setWorkflowTypeId(workflowMas.getWfId());
			taskDto.setServiceEventName("PIO Response");
			LinkedHashMap<String, LinkedHashMap<String, TaskAssignment>> hashmap = taskAssignmentService
					.getEventLevelGroupsByWorkflowTypeAndEventName(taskDto);

			LinkedHashMap<String, TaskAssignment> grp1 = hashmap
					.get(MainetConstants.GROUP + MainetConstants.operator.UNDER_SCORE + group);

			for (int j = 1; j <= grp1.size(); j++) {
				TaskAssignment ta = grp1.get(MainetConstants.LEVEL + MainetConstants.operator.UNDER_SCORE + j);

				List<Long> actorIdList = new ArrayList<>();
				String[] empIds = ta.getActorId().split(MainetConstants.operator.COMMA);
				for (String s : empIds) {
					actorIdList.add(Long.valueOf(s));
				}
				// defect #78415 by rajesh.das
				if (actorIdList != null) {
					actorIdList = new ArrayList<Long>();
					actorIdList.add(employee1.getEmpId());
				}
				if (!actorIdList.isEmpty()) {
					String emp = MainetConstants.BLANK;

					List<Employee> empList = employeeService.getEmpDetailListByEmpIdList(actorIdList, orgId);
					model.setPioNumber(empList.get(0).getEmpmobno());
					model.setPioEmail(empList.get(0).getEmpemail());

					for (Employee employee : empList) {

						emp += employee.getEmpname() + MainetConstants.WHITE_SPACE;

						if (null != employee.getEmpmname() && !employee.getEmpmname().isEmpty()) {
							emp += employee.getEmpmname() + MainetConstants.WHITE_SPACE;

						}
						if (null != employee.getEmplname() && !employee.getEmplname().isEmpty()) {
							emp += employee.getEmplname() + MainetConstants.WHITE_SPACE;

						}

					}
					model.setPioName(emp);

				}
			}
		}

		return new ModelAndView("RtiLoiLetter", MainetConstants.FORM_NAME, getModel());
	}

//added for checking duplicate employee User Story #85006
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST, params = MainetConstants.RTISERVICE.CHECK_DUPLICATE_EMPLOYEE)
	public @ResponseBody Boolean checkDuplicateEmpName(final HttpServletRequest httpServletRequest) {
		ModelAndView mv = null;
		this.getModel().bind(httpServletRequest);
		RtiPioResponseModel model = this.getModel();
		RtiApplicationFormDetailsReqDTO reqDTO = model.getReqDTO();
		if (reqDTO.getEmpList() != null && !reqDTO.getEmpList().isEmpty()) {
			if (reqDTO.getEmpList().size() != reqDTO.getEmpList().stream().distinct().count())
				return true;
		}
		return false;
	}

	// US#111591
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

	// US#109003
	@RequestMapping(params = "isWardZoneRequired", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isWardZoneRequired(@RequestParam("deptId") Long deptId) {
		if (deptId == null)
			return false;
		String workflowDefinitionType = rtiApplicationDetailService
				.resolveWorkflowTypeDefinition(UserSession.getCurrent().getOrganisation().getOrgid(), deptId);
		return workflowDefinitionType.equals(MainetConstants.MENU.N);
	}

	// US#109003
	@RequestMapping(params = "areaMapping", method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixName(@RequestParam("deptId") Long deptId) {
		String prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setPrefixName(prefixName);
		return new ModelAndView(MainetConstants.ServiceCareCommon.AREA_MAPPING_WARD_ZONE,
				MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	// US#109003
	@RequestMapping(params = "getWard", method = RequestMethod.POST)
	@ResponseBody
	public List<TbComparentDet> getWard(@RequestParam("frdOrgId") Long frdOrgId,
			HttpServletRequest httpServletRequest) {
		List<TbComparentDet> wardList = new ArrayList<TbComparentDet>();
		if (frdOrgId == null || frdOrgId == 0L)
			return wardList;
		Organisation org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.getOrganisationById(frdOrgId);
		List<LookUp> lookupList = CommonMasterUtility.getListLookup("RWZ", org);
		if (lookupList != null && !lookupList.isEmpty()) {
			wardList = comparentDetService.findComparentDetDataById(lookupList.get(0).getLookUpId(), frdOrgId);

		}

		return wardList;
	}

	@RequestMapping(params = "isWardZoneRequiredByOrgId", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isWardZoneRequiredByOrgId(@RequestParam("frdOrgId") Long frdOrgId) {
		if (frdOrgId == null)
			return false;
		Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		String workflowDefinitionType = rtiApplicationDetailService.resolveWorkflowTypeDefinition(frdOrgId, deptId);
		return workflowDefinitionType.equals(MainetConstants.MENU.N);
	}

	@RequestMapping(params = "getZone", method = RequestMethod.POST)
	@ResponseBody
	public List<TbComparentDet> getZone(@RequestParam("pioWard") Long pioWard, @RequestParam("frdOrgId") Long frdOrgId,
			HttpServletRequest httpServletRequest) {
		List<TbComparentDet> wardList = new ArrayList<TbComparentDet>();
		if (pioWard == null || pioWard == 0L)
			return wardList;
		Organisation org = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class)
				.getOrganisationById(frdOrgId);

		wardList = comparentDetService.findComparentDetDataByParentId(pioWard, frdOrgId);

		return wardList;
	}

	@RequestMapping(params = "checkWorkflow", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkWorkflow(@RequestParam("frdOrgId") Long frdOrgId, HttpServletRequest httpServletRequest) {
		if (frdOrgId == null)
			return false;
		Long deptId = tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI);
		String workflowDefinitionType = rtiApplicationDetailService.resolveWorkflowTypeDefinition(frdOrgId, deptId);
		if (workflowDefinitionType.equals("")) {
			return true;
		}
		return false;
	}

//code added for showing action history details on Pio Response form  as per Anujs demo points	
	@RequestMapping(method = { RequestMethod.GET }, params = "showHistoryDetails")
	public ModelAndView showChargesDetails(final HttpServletRequest httpServletRequest, ModelMap modelMap) {
		this.getModel().bind(httpServletRequest);
		RtiPioResponseModel model = this.getModel();

		modelMap.addAttribute(MainetConstants.WorkFlow.ACTIONS,
				rtiApplicationDetailService.getRtiActionLogByApplicationId(model.getReqDTO().getApmApplicationId(),
						model.getReqDTO().getOrgId(), UserSession.getCurrent().getLanguageId()));
		return new ModelAndView("RtiActionHistory", MainetConstants.CommonConstants.COMMAND, getModel());
	}

//code added for validating work flow exist or not
	@RequestMapping(params = "checkWorkflowByWardZone", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkWorkflowByWardZone(@RequestParam("frdOrgId") Long frdOrgId,
			@RequestParam("trdWard1") Long trdWard1, @RequestParam("trdWard2") Long trdWard2,
			HttpServletRequest httpServletRequest) {
		RtiApplicationFormDetailsReqDTO dto = new RtiApplicationFormDetailsReqDTO();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		if (frdOrgId != null && frdOrgId != 0) {
			orgId = frdOrgId;
		}
		dto.setOrgId(orgId);
		dto.setTrdWard1(trdWard1);
		dto.setTrdWard2(trdWard2);
		Boolean flag = rtiApplicationDetailService.checkWoflowDefinedOrNot(dto);
		if (!flag) {
			return true;
		}
		return false;
	}

	@RequestMapping(method = RequestMethod.POST, params = "employeeInfoReport")
	public ModelAndView getforwardEmplApplicanReport(final HttpServletRequest request) {
		this.getModel().bind(request);
		RtiPioResponseModel model = this.getModel();
		RtiApplicationFormDetailsReqDTO reqDto = model.getReqDTO();
		List<RtiFrdEmployeeDetails> frdDet = new ArrayList<RtiFrdEmployeeDetails>();
		int i = 0;
		if(!Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_DSCL)) {
		if (!CollectionUtils.isEmpty(model.getReqDTO().getDeptIdList())) {
			for (Long deptId : model.getReqDTO().getDeptIdList()) {
				RtiFrdEmployeeDetails rtfrdDept = new RtiFrdEmployeeDetails();
				if (deptId != null) {
					TbDepartment dept = tbDepartmentService.findById(deptId);
					if (dept != null) {
						if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
							rtfrdDept.setDeptName(dept.getDpDeptdesc());
						} else {
							rtfrdDept.setDeptName(dept.getDpNameMar());
						}
					}
					setEmplyeeData(reqDto.getEmpList().get(i), reqDto.getRemList().get(i), rtfrdDept);
					frdDet.add(rtfrdDept);
				}
				i = i + 1;

			}
		} else {
			if (reqDto.getFdlDeptId() != null) {
				RtiFrdEmployeeDetails rtfrdDept = new RtiFrdEmployeeDetails();
				TbDepartment dept = tbDepartmentService.findById(reqDto.getFrdDeptId());
				if (dept != null) {
					if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
						rtfrdDept.setDeptName(dept.getDpDeptdesc());
					} else {
						rtfrdDept.setDeptName(dept.getDpNameMar());
					}
				}
				setEmplyeeData(Long.valueOf(reqDto.getEmpname()), reqDto.getEmpRmk(), rtfrdDept);
			}
		
		}}else {
			if(!CollectionUtils.isEmpty(reqDto.getEmpList())) {
			for(Long emp:reqDto.getEmpList()) {
				if(emp!=null) {
				RtiFrdEmployeeDetails rtfrdDept = new RtiFrdEmployeeDetails();
				TbDepartment dept = tbDepartmentService.findById(tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI));
				if (dept != null) {
					if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
						rtfrdDept.setDeptName(dept.getDpDeptdesc());
					} else {
						rtfrdDept.setDeptName(dept.getDpNameMar());
					}
				}
				rtfrdDept.setEmpRematk(reqDto.getRemList().get(i));
				rtfrdDept.setEmpName(serviceMasterService.getServiceNameByServiceId(emp));
				//setEmplyeeData(reqDto.getEmpList().get(i), reqDto.getRemList().get(i), rtfrdDept);
				frdDet.add(rtfrdDept);
			}i=i+1;}
			
		}else{
			 {
					if (reqDto.getEmpname() != null) {
						RtiFrdEmployeeDetails rtfrdDept = new RtiFrdEmployeeDetails();
						TbDepartment dept = tbDepartmentService.findById(tbDepartmentService.getDepartmentIdByDeptCode(MainetConstants.DEPT_SHORT_NAME.RTI));
						if (dept != null) {
							if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
								rtfrdDept.setDeptName(dept.getDpDeptdesc());
							} else {
								rtfrdDept.setDeptName(dept.getDpNameMar());
							}
						}
						rtfrdDept.setEmpName(serviceMasterService.getServiceNameByServiceId(Long.valueOf(reqDto.getEmpname())));
						rtfrdDept.setEmpRematk(reqDto.getEmpRmk());
						//setEmplyeeData(Long.valueOf(reqDto.getEmpname()), reqDto.getEmpRmk(), rtfrdDept);
						frdDet.add(rtfrdDept);
					}
				
				}}
		}
		Employee empl = UserSession.getCurrent().getEmployee();
		model.setPioName(empl.getEmpname());
		SimpleDateFormat formatter = new SimpleDateFormat(MainetConstants.DATE_FORMAT);
		String strDate=null;
		if(model.getReqDTO().getRtiDeptidFdate()!=null) {
		 strDate = formatter.format(model.getReqDTO().getRtiDeptidFdate());
		}
		else {
			strDate=formatter.format(new Date());
		}
		model.getReqDTO().setRtiDeciDet(strDate);
		model.setRtiFrdEmpDet(frdDet);
		return new ModelAndView("forwardEmplApplicanReport", MainetConstants.FORM_NAME, model);
	}
	
	private void setEmplyeeData(Long empId,String remark,RtiFrdEmployeeDetails rtfrdDept) {
		
		String emp = "";
		EmployeeBean employee = employeeService.findById(Long.valueOf(empId));
		if(employee!=null) {
		rtfrdDept.setEmpDesg(employee.getDesignName());
		rtfrdDept.setEmpRematk(remark);
		emp += employee.getEmpname() + MainetConstants.WHITE_SPACE;

		if (null != employee.getEmpmname() && !employee.getEmpmname().isEmpty()) {
			emp += employee.getEmpmname() + MainetConstants.WHITE_SPACE;

		}
		if (null != employee.getEmplname() && !employee.getEmplname().isEmpty()) {
			emp += employee.getEmplname() + MainetConstants.WHITE_SPACE;

		}
		rtfrdDept.setEmpName(emp);
		}
	}
}
