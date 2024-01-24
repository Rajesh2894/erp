package com.abm.mainet.care.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.abm.mainet.care.domain.CareRequest;
import com.abm.mainet.care.dto.DepartmentComplaintDTO;
import com.abm.mainet.care.service.ICareRequestService;
import com.abm.mainet.care.ui.model.ComplaintRegistrationModel;
import com.abm.mainet.care.utility.CareUtility;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ComplaintType;
import com.abm.mainet.common.domain.DepartmentComplaint;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.IComplaintTypeService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.ActionResponse;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkFlowTypeGrid;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.care.domain.CareDepartmentAction;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Jasvinder.Bhomra
 *
 */
@Controller
@RequestMapping("/GrievanceResolution.html")
public class GrievanceResolutionController extends AbstractFormController<ComplaintRegistrationModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(GrievanceResolutionController.class);

	@Autowired
	private ICareRequestService careRequestService;

	@Resource
	private TbDepartmentService tbDepartmentService;

	@Resource
	private ILocationMasService iLocationMasService;

	@Resource
	IFileUploadService fileUpload;

	@Autowired
	private IComplaintTypeService iComplaintService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Resource
	private IFileUploadService fileUploadService;

	@Autowired
	TbServicesMstService tbServicesMstService;
	
	@Autowired
	IWorkflowTyepResolverService workflowTypeservice;
	
	@Autowired
    private ApplicationSession applicationSession;
	
	  @Autowired
	    private ISMSAndEmailService iSMSAndEmailService;

	@RequestMapping(method = RequestMethod.POST, params = "showDetails")
	public ModelAndView viewGrievanceResolution(HttpServletRequest httpServletRequest,
			@RequestParam("appNo") Long applicationId, @RequestParam("actualTaskId") Long taskId, ModelMap model)
			throws EntityNotFoundException, Exception {
		LOGGER.info("From Grievance Service Rest Controller viewGrievanceResolution");

		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		getModel().bind(httpServletRequest);
		StringBuffer applicantType = new StringBuffer();
		StringBuffer complaintLabel = new StringBuffer();
		CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
		LOGGER.info("From GrievanceResolutionController tracing applicationId: " + applicationId );
		getModel().setCareRequest(CareUtility.toCareRequestDTO(careRequest));
		getModel().setAgeOfRequest(careRequest.getDateOfRequest());
		getModel().getCareRequest().setLatitude(careRequest.getLatitude());
		getModel().getCareRequest().setLongitude(careRequest.getLongitude());

		Set<DepartmentComplaintDTO> deptList = careRequestService
				.getDepartmentComplaintsByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.put(MainetConstants.ServiceCareCommon.DEPT_LIST, deptList);
		
		List<CareDepartmentAction> cd = careRequestService.findByCareId(careRequest.getId());
		
		List<CareDepartmentAction> cda = new ArrayList<>();
		for(CareDepartmentAction cd1 : cd){
			if(cd1.getDepartment()!=null){
				cda.add(cd1);
			}
		}
		
		setDepartmentAndComplaintTypeDesc(getModel(), careRequest);
		
		Organisation org = new Organisation();
		org.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && cda.isEmpty()) {
			model.put(MainetConstants.RecieptRegister.EMP_LIST, employeeService.findAllEmployeeByDept(
					UserSession.getCurrent().getOrganisation().getOrgid(), careRequest.getDepartmentComplaint()));
		}else if(Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL) && !cda.isEmpty()){
			if(cda.size() > 1){
				CareDepartmentAction cdf = cda.get(cda.size() - 1);
				model.put(MainetConstants.RecieptRegister.EMP_LIST, employeeService.findAllEmployeeByDept(
						UserSession.getCurrent().getOrganisation().getOrgid(), cdf.getDepartment()));
			}else{
				model.put(MainetConstants.RecieptRegister.EMP_LIST, employeeService.findAllEmployeeByDept(
						UserSession.getCurrent().getOrganisation().getOrgid(), cda.get(0).getDepartment()));
			}
		}
		else {
			model.put(MainetConstants.RecieptRegister.EMP_LIST,
					employeeService.findAllEmployeeByDept(UserSession.getCurrent().getOrganisation().getOrgid(),
							UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptid()));
		}
		RequestDTO applicantDetail = careRequestService.getApplicationDetails(careRequest);
		getModel().setApplicantDetailDto(applicantDetail);
		model.put(MainetConstants.TASK_ID, taskId);
		model.put(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO, applicationId.toString());
		model.put(MainetConstants.REQUIRED_PG_PARAM.COMPLAINT_NO, careRequest.getComplaintId());
		//Defect #105783 start
		List<LookUp> lookUpList = CommonMasterUtility.lookUpListByPrefix(MainetConstants.SEQ_PREFIXES.AC,
				UserSession.getCurrent().getOrganisation().getOrgid());

		lookUpList.forEach(lookup -> {
			if (applicantType != null && !applicantType.toString().isEmpty()) {
				applicantType.append(",");
			}
			if (lookup.getOtherField().equalsIgnoreCase(MainetConstants.FlagY)) {
				applicantType.append(lookup.getLookUpCode());
				complaintLabel.append(lookup.getLookUpDesc());
				complaintLabel.append(",");
			}
		});
		getModel().setApplicationType(applicantType.toString());
		getModel().setLabelType(complaintLabel.toString());
		//Defect #105783 end
		model.put(MainetConstants.ServiceCareCommon.GENDER, getPrefixDesc(PrefixConstants.MobilePreFix.GENDER,
				Long.parseLong(getModel().getApplicantDetailDto().getGender())));
		if(getModel().getApplicantDetailDto().getTitleId() != null) {
			model.put(MainetConstants.TenantMaster.TITLE,
				getPrefixDesc(PrefixConstants.LookUp.TITLE, getModel().getApplicantDetailDto().getTitleId()));
		}
		
		/*D#115566*/
		String prefixName = tbDepartmentService.findDepartmentPrefixName(careRequest.getDepartmentComplaint(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setPrefixName(prefixName);
		if (StringUtils.isEmpty(prefixName)) {
	            this.getModel().setPrefixName("CWZ");
	        }
		if (CareUtility.isENVCodePresent(MainetConstants.ENV_ASCL, UserSession.getCurrent().getOrganisation().getOrgid())){
			 this.getModel().setPrefixName("CWZ");
		}
		//#135509,#142867
		if (careRequest.getWard1() != null) {
			getModel().getCareRequest()
					.setWard1Desc(CommonMasterUtility
							.getHierarchicalLookUp(careRequest.getWard1(), UserSession.getCurrent().getOrganisation())
							.getDescLangFirst());
			
			getModel().getCareRequest().setWard1DescReg(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard1(),
					UserSession.getCurrent().getOrganisation()).getDescLangSecond());
		}
		if (careRequest.getWard2() != null) {
			getModel().getCareRequest()
					.setWard2Desc(CommonMasterUtility
							.getHierarchicalLookUp(careRequest.getWard2(), UserSession.getCurrent().getOrganisation())
							.getDescLangFirst());
			
			getModel().getCareRequest().setWard2DescReg(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard2(),
					UserSession.getCurrent().getOrganisation()).getDescLangSecond());
		
		}
		if (careRequest.getWard3() != null) {
			getModel().getCareRequest()
					.setWard3Desc(CommonMasterUtility
							.getHierarchicalLookUp(careRequest.getWard3(), UserSession.getCurrent().getOrganisation())
							.getDescLangFirst());
		
			getModel().getCareRequest().setWard3DescReg(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard3(),
					UserSession.getCurrent().getOrganisation()).getDescLangSecond());
		}
		if (careRequest.getWard4() != null) {
			getModel().getCareRequest()
					.setWard4Desc(CommonMasterUtility
							.getHierarchicalLookUp(careRequest.getWard4(), UserSession.getCurrent().getOrganisation())
							.getDescLangFirst());
		
			getModel().getCareRequest().setWard4DescReg(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard4(),
					UserSession.getCurrent().getOrganisation()).getDescLangSecond());	
		}
		if (careRequest.getWard5() != null) {
			getModel().getCareRequest()
					.setWard5Desc(CommonMasterUtility
							.getHierarchicalLookUp(careRequest.getWard5(), UserSession.getCurrent().getOrganisation())
							.getDescLangFirst());
			
			getModel().getCareRequest().setWard5DescReg(CommonMasterUtility.getHierarchicalLookUp(careRequest.getWard5(),
					UserSession.getCurrent().getOrganisation()).getDescLangSecond());
		
		}
		model.put(MainetConstants.WorkFlow.ACTIONS,
				careRequestService.getCareWorkflowActionLogByApplicationId(applicationId,
						UserSession.getCurrent().getOrganisation().getOrgid(),
						UserSession.getCurrent().getLanguageId()));
		getModel().setRequestType(careRequest.getApplnType());
		// U#96713 check here code running on KDMC or not
	        getModel().setKdmcEnv(MainetConstants.FlagN);
	        if (CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
	            getModel().setKdmcEnv(MainetConstants.FlagY);
	        }
	        if (!CareUtility.isCallCenterApplicable(careRequest.getOrgId())) {
	            model.put("callCenterAppli", MainetConstants.FlagN);   
	        }else {
	            model.put("callCenterAppli", MainetConstants.FlagY);    
	        }
	        
		return new ModelAndView(MainetConstants.ServiceCareCommon.GRIEVENCE_SOLUTION,
				MainetConstants.CommonConstants.COMMAND, getModel());
	}

	private void setDepartmentAndComplaintTypeDesc(ComplaintRegistrationModel model, CareRequest careRequest) {

		TbDepartment dept = tbDepartmentService.findById(careRequest.getDepartmentComplaint());
		getModel().getCareRequest().setDepartmentComplaintDesc(dept.getDpDeptdesc());
		getModel().getCareRequest().setDepartmentComplaintDescReg(dept.getDpNameMar());
		if (careRequest.getApplnType().equalsIgnoreCase(MainetConstants.FlagC)) {
			ComplaintType complaint = iComplaintService.findComplaintTypeById(careRequest.getComplaintType());
			getModel().getCareRequest().setComplaintTypeDesc(complaint.getComplaintDesc());
			getModel().getCareRequest().setComplaintTypeDescReg(complaint.getComplaintDescreg());
		} else {
			TbServicesMst service = tbServicesMstService.findById(careRequest.getSmServiceId());
			getModel().getCareRequest().setComplaintTypeDesc(service.getSmServiceName());
			getModel().getCareRequest().setComplaintTypeDescReg(service.getSmServiceNameMar());
		}

	}

	@RequestMapping(method = RequestMethod.POST, params = "saveDetails")
	public @ResponseBody String submit(HttpServletRequest request, ModelMap modelMap)
			throws EntityNotFoundException, Exception {
		LOGGER.info("From Grievance Service Rest Controller submit Approving the Requests");

		getModel().bind(request);
		ComplaintRegistrationModel model = getModel();
		String decisionResponse = null;
		
		// D#125503 Check workflow is defined for ward zone wise.
        WorkflowMas workflowType = null;
            if (model.getCareDepartmentAction().getDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT)) {
            	try {
                workflowType = workflowTypeservice.resolveComplaintWorkflowType(UserSession.getCurrent().getOrganisation().getOrgid(),
                		Long.parseLong(model.getCareDepartmentAction().getForwardDepartment()), Long.parseLong(model.getCareDepartmentAction().getForwardComplaintType()), 
                		model.getCareDepartmentAction().getCodIdOperLevel1(),model.getCareDepartmentAction().getCodIdOperLevel2(),
                		model.getCareDepartmentAction().getCodIdOperLevel3(),model.getCareDepartmentAction().getCodIdOperLevel4(),
                		model.getCareDepartmentAction().getCodIdOperLevel5());
            	} catch (FrameworkException e) {
                	LOGGER.error("Workflow Not Found for Department Id: "+model.getCareDepartmentAction().getForwardDepartment()
                			+" and Complaint Sub Type Id : "+model.getCareDepartmentAction().getForwardComplaintType());
                }
            	if (workflowType == null) {
                    // D#125503
                	decisionResponse = MainetConstants.InputError.WARDZONE_WORKFLOW_NOT_FOUND;
                    return decisionResponse;
                }
            }
		
		Long applicationId = Long.parseLong(request.getParameter(MainetConstants.REQUIRED_PG_PARAM.APPLICATION_NO));

		WorkflowTaskAction departmentAction = model.getCareDepartmentAction();
		departmentAction.setTaskId(model.getTaskId());
		departmentAction.setApplicationId(applicationId);
		departmentAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		departmentAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		departmentAction.setCreatedDate(new Date());
		departmentAction.setDateOfAction(new Date());
		departmentAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		departmentAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());

		CareRequest careRequest = careRequestService.findByApplicationId(applicationId);
		RequestDTO applicantDetailDto = careRequestService.getApplicationDetails(careRequest);
		applicantDetailDto.setApplicationId(applicationId);
		departmentAction.setReferenceId(careRequest.getComplaintId());

		model.setAttachments(fileUploadService.setFileUploadMethod(model.getAttachments()));
		applicantDetailDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		fileUploadService.doFileUpload(model.getAttachments(), applicantDetailDto);
		List<Long> attachmentId = iChecklistVerificationService.fetchAttachmentIdByAppid(applicationId,
				applicantDetailDto.getOrgId());
		departmentAction.setAttachementId(attachmentId);

		ActionResponse actResponse = careRequestService.updateCareProces(careRequest, departmentAction);
		decisionResponse = actResponse.getResponseData(MainetConstants.DECISION);
		//Defect #110438
		LOGGER.info("Decision Response: " + decisionResponse);
		if (decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED) || decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED)
				|| decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT)
				|| decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
			sendSMSAndEmail(applicantDetailDto,careRequest,actResponse);
		}
		
		
		boolean statusUpdate=false;
		TbDepartment dept = tbDepartmentService.findById(careRequest.getDepartmentComplaint());
		ComplaintType complaintType = iComplaintService.findComplaintTypeById(careRequest.getComplaintType());
		
		if((decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED) || decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT) ||
				decisionResponse.equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) && (CareUtility.isENVCodePresent(MainetConstants.ENV_DSCL, careRequest.getOrgId()))
				&& complaintType.getExternalWorkFlowFlag().equalsIgnoreCase(MainetConstants.Y_FLAG) && (dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.WORKS_MANAGEMENT)
				|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.VPCA)
				|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.LAND)
				|| dept.getDpDeptcode().equalsIgnoreCase(MainetConstants.WorksManagement.Water_Sewarage))){
			
			if(StringUtils.isNotEmpty(careRequest.getExtReferNumber())){
				statusUpdate = callSwacchBharatAPIToUpdateStatus(careRequest,model.getCareDepartmentAction().getComments(),decisionResponse);
				if(statusUpdate){
				LOGGER.info("Status updated sucessfully");
				}
			}
		} 
		
		
		return decisionResponse;
			
	}


	/**
	 * Operational Ward Zone is different from department to
	 * department.Administrative,Electoral and Revenue Ward Zone are common across
	 * all department.
	 */
	@RequestMapping(params = "areaMapping", method = RequestMethod.POST)
	public ModelAndView getOperationalWardZonePrefixName(@RequestParam("deptId") Long deptId) {
		String prefixName = null;
		/*In SKDCL wardzone wise workflow is defined on basis of selected department Id's prefix
   	 	And other environment wardzone wise workflow is defined on basis of CFC department Id(CWZ prefix)*/
	   	if(CareUtility.isENVCodePresent(MainetConstants.ENV_PRODUCT, UserSession.getCurrent().getOrganisation().getOrgid())) {
	   		prefixName = tbDepartmentService.findDepartmentPrefixName(deptId,
	   				UserSession.getCurrent().getOrganisation().getOrgid());
	   		if (prefixName != null && !prefixName.isEmpty()) {
	                    // D#130416 check at least one level define or not in prefix
	                    // if not than it throws exception which is handle using try catch and inside catch pass prefix CWZ
	                           try {
	                               CommonMasterUtility.getLevelData(prefixName, 1, UserSession.getCurrent().getOrganisation());
	                           } catch (FrameworkException e) {
	                               prefixName=MainetConstants.COMMON_PREFIX.CWZ;
	                           }
	                   } else {
	                       prefixName=MainetConstants.COMMON_PREFIX.CWZ;
	                   }
	   	}else {
	   		prefixName=MainetConstants.COMMON_PREFIX.CWZ;
	   	}
		this.getModel().setPrefixName(prefixName);
		return new ModelAndView(MainetConstants.ServiceCareCommon.AREA_MAPPING_WARD_ZONE,
				MainetConstants.CommonConstants.COMMAND, this.getModel());
	}

	@RequestMapping(params = "isWardZoneRequired", method = RequestMethod.POST)
	@ResponseBody
	public Boolean isWardZoneRequired(@RequestParam("deptId") Long deptId,
			@RequestParam("compTypeId") Long compTypeId) {
		if (deptId == null || compTypeId == null)
			return false;
		String workflowDefinitionType = careRequestService
				.resolveWorkflowTypeDefinition(UserSession.getCurrent().getOrganisation().getOrgid(), compTypeId);
		return workflowDefinitionType.equals(MainetConstants.MENU.N);
	}

	@SuppressWarnings("unused")
	private List<DepartmentComplaint> getCareWorkflowMasterDefinedDepartmentsListByOrgId(Long id) {
		List<WorkFlowTypeGrid> workFlowTypeGrid = iWorkFlowTypeService.findAllRecords(MainetConstants.MENU.Y,
				MainetConstants.MENU.Y);
		List<DepartmentComplaint> deptList = iComplaintService.getcomplainedDepartment(id);
		List<DepartmentComplaint> careWorkflowDefinedDeptList = new ArrayList<>();
		Set<Long> deptId = new HashSet<>();
		workFlowTypeGrid.forEach(workFlowType -> {
			deptId.add(workFlowType.getDeptId());
		});
		deptList.forEach(dept -> {
			if (deptId.contains(dept.getDepartment().getDpDeptid()))
				careWorkflowDefinedDeptList.add(dept);
		});
		return careWorkflowDefinedDeptList;
	}

	protected ModelAndView redirectToHome(ActionResponse actResponse, HttpServletRequest request, String moduleUrl,
			final RedirectAttributes redirectAttributes, String decision) {
		String message = MainetConstants.BLANK;
		if (actResponse != null) {
			if (null != actResponse.getError() && !MainetConstants.BLANK.equals(actResponse.getError())) {
				request.getSession().setAttribute(MainetConstants.DELETE_ERROR, actResponse.getError());
			} else {
				String actionKey = actResponse.getResponseData(MainetConstants.RESPONSE);
				String decision_ = actResponse.getResponseData(MainetConstants.DECISION);
				if (null != actionKey) {
					String requestNo = actResponse.getResponseData(MainetConstants.REQUEST_NO);
					if (null != requestNo) {
						message = MainetConstants.REQUEST_NUMBER + requestNo + MainetConstants.WHITE_SPACE + decision_
								+ MainetConstants.SUCCESSFULLY;
						redirectAttributes.addFlashAttribute(MainetConstants.SUCCESS_MESSAGE, message);

					}
				}
			}
		}
		List<String> errorCodes = new ArrayList<String>();
		for (ObjectError errors : actResponse.getErrorList()) {
			errorCodes.add(errors.getCode());
		}
		redirectAttributes.addFlashAttribute(MainetConstants.ERROR_MESSAGE, errorCodes);
		return new ModelAndView(MainetConstants.REDIRECT + moduleUrl);
	}

	private String getPrefixDesc(String code, long id) {
		String prefixDesc = MainetConstants.BLANK;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(code, UserSession.getCurrent().getOrganisation());
		for (LookUp lookUp : lookUps) {
			if (lookUp.getLookUpId() == id) {
				prefixDesc = lookUp.getLookUpDesc();
				break;
			}
		}
		return prefixDesc;
	}

	
	//Defect #110438
		private void sendSMSAndEmail(RequestDTO applicantDetailDto, CareRequest careRequest,ActionResponse actResponse) {
			final SMSAndEmailDTO dto = new SMSAndEmailDTO();
			String userName = applicantDetailDto.getfName() == null ? MainetConstants.BLANK
					: applicantDetailDto.getfName() + MainetConstants.WHITE_SPACE;
			userName += applicantDetailDto.getmName() == null ? MainetConstants.BLANK
					: applicantDetailDto.getmName() + MainetConstants.WHITE_SPACE;
			userName += applicantDetailDto.getlName() == null ? MainetConstants.BLANK : applicantDetailDto.getlName();
			Organisation org = new Organisation();
			org.setOrgid(careRequest.getOrgId());
			int langId = Utility.getDefaultLanguageId(org);
			dto.setAppName(userName);
			dto.setEmail(applicantDetailDto.getEmail());
			dto.setMobnumber(applicantDetailDto.getMobileNo());
			dto.setTokenNumber(careRequest.getComplaintId());
			dto.setAppNo(careRequest.getComplaintId());
			dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
			dto.setOrgId(org.getOrgid());
			dto.setOrganizationName(org.getONlsOrgname());
			dto.setOrgName(org.getONlsOrgname());
			dto.setServiceUrl(MainetConstants.ServiceCareCommon.SMS_EMAIL_URL.GRIEVANCE_RESOLUTION);
			dto.setServName(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER);
			if ((actResponse.getResponseData(MainetConstants.DECISION)).equalsIgnoreCase(MainetConstants.WorkFlow.Decision.APPROVED)) {
				LOGGER.info("Inside APPROVED Message with Decision: " +actResponse.getResponseData(MainetConstants.DECISION));
				iSMSAndEmailService.sendEmailSMS(dto.getServName(), dto.getServiceUrl(),
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.COMPLETED_MESSAGE, dto, org, langId);

			} 
			if ((actResponse.getResponseData(MainetConstants.DECISION).equalsIgnoreCase(MainetConstants.WorkFlow.Decision.REJECTED))) {
				LOGGER.info("Inside REJECTED Message with Decision: " +actResponse.getResponseData(MainetConstants.DECISION));
				iSMSAndEmailService.sendEmailSMS(dto.getServName(), dto.getServiceUrl(),
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED_MSG, dto, org, langId);
			}
			if ((actResponse.getResponseData(MainetConstants.DECISION)).equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT)
					|| (actResponse.getResponseData(MainetConstants.DECISION)).equalsIgnoreCase(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
				LOGGER.info("Inside FORWARD_TO_DEPARTMENT/FORWARD_TO_EMPLOYEE Message with Decision: " +actResponse.getResponseData(MainetConstants.DECISION));
				iSMSAndEmailService.sendEmailSMS(dto.getServName(), dto.getServiceUrl(),
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.FORWARD_TO_DEPARTMENT, dto, org, langId);
			}
			
		}
		
		//D#127175
		@RequestMapping(method = RequestMethod.POST, params = "cleareFile")
		    public @ResponseBody String cleareFile(
		            final HttpServletRequest httpServletRequest) {
		        int length = 0;
		        final String folderPath = FileUploadUtility.getCurrent().getExistingFolderPath();
		        FileUploadUtility.getCurrent().getFileMap().clear();
		        if (folderPath != null) {
		            final File file = new File(folderPath);
		            if (file != null) {
		                length = file.list() != null ? file.list().length
		                        : 0;
		            }
		            FileUploadUtility.getCurrent()
		                    .setFolderCreated(false);
		        }
		        return length + MainetConstants.BLANK;
		    }

		public boolean callSwacchBharatAPIToUpdateStatus(CareRequest careRequest, String comments,String decisionResponse) {
			
			int statusId=4;
			
			LOGGER.info(" Complaint With callSwacchBharatAPI to update status is Started ---------------->" );
			
			String complaintStatusUrl = ApplicationSession.getInstance().getMessage("swacchaBharatApp.UpdateStatus.Url");
			String api_key = ApplicationSession.getInstance().getMessage("swacchaBharatApp.apikey");
			String vendor_name = ApplicationSession.getInstance().getMessage("swacchaBharatApp.vendorName");
			String access_key = ApplicationSession.getInstance().getMessage("swacchaBharatApp.accessKey");
			String deviceOs = ApplicationSession.getInstance().getMessage("swacchaBharatApp.deviceOS");
			if(StringUtils.equalsIgnoreCase(decisionResponse, MainetConstants.WorkFlow.Decision.APPROVED)){
				statusId=4;
			}
			else if(StringUtils.equalsIgnoreCase(decisionResponse, MainetConstants.WorkFlow.Decision.FORWARD_TO_DEPARTMENT)
					|| StringUtils.equalsIgnoreCase(decisionResponse, MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)){
				statusId=3;
			}
			
			String extReferNumber="";
			
			if(StringUtils.isNotEmpty(careRequest.getExtReferNumber())){
			extReferNumber = careRequest.getExtReferNumber();
			}
			
			String[] split = extReferNumber.split("C");
			
			int complaintId= Integer.parseInt(split[1]);
			
			String complaintDescription="";
			
			if(StringUtils.isNotEmpty(comments)){
				complaintDescription=comments;
			}
			else{
				complaintDescription="Resolved";
			}
			
			String file = MainetConstants.Dms.Doon_file_path;
			
			RestTemplate webLineReq = new RestTemplate();
			HttpHeaders webLineHeaders = new HttpHeaders();
			
			webLineHeaders.add("Content-Type", "application/json");
			HttpEntity<String> webLinbeHttpentity = new HttpEntity<>(webLineHeaders);
			
			String webMailUri = complaintStatusUrl + "?statusId=" + statusId + "&complaintId=" + complaintId + "&commentDescription="
					+ complaintDescription + "&deviceOs=" + deviceOs + "&apiKey=" + api_key + "&vendor_name="
					+ vendor_name + "&access_key=" + access_key + "&check=rrrr&file=" + file;
			
			
			LOGGER.info("webMailUri :- " + webMailUri);
			
			try {
				ResponseEntity<String> resp = webLineReq.exchange(webMailUri, HttpMethod.PUT, webLinbeHttpentity,
						String.class);
				
				HttpStatus statusCode  =resp.getStatusCode();
				if(statusCode== HttpStatus.OK)
				{
					
					LOGGER.info("Complaint resolved successfully");
					LOGGER.info("response for swaccha API :- " + resp.toString());
					return true;
					
				}
				
				
			} catch (Exception e) {
				LOGGER.error("Exception occured while calling SwacchaBharatAPI " + e);
			}
			
			
			return false;
		}
}
