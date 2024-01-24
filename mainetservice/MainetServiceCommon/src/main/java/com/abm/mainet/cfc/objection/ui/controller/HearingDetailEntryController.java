package com.abm.mainet.cfc.objection.ui.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.HearingAndInspectionService;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.DesignationBean;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.RestClient;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.TaskAssignment;
import com.abm.mainet.common.workflow.dto.TaskAssignmentRequest;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.ITaskAssignmentService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/HearingDetailEntry.html")
public class HearingDetailEntryController extends AbstractFormController<ObjectionDetailsModel> {

	private static final Logger LOGGER = LoggerFactory.getLogger(HearingDetailEntryController.class);

	@Autowired
	private ServiceMasterService serviceMaster;

	@Autowired
	private IFileUploadService fileUpload;

	@Autowired
	private IObjectionDetailsService iObjectionDetailsService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;

	@Autowired
	private ILocationMasService iLocationMasService;

	@Autowired
	private HearingAndInspectionService hearingAndInspectionService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

	@Autowired
	private ITaskAssignmentService taskAssignmentService;

	@Autowired
	private IEmployeeService employeeService;
	
    @Autowired
    private DesignationService designationService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		return defaultResult();

	}

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView defaultLoad(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		// UserTaskDTO userDto = iWorkflowTaskService.findByTaskId(taskId);
		// #76315
		// get User task based on service id
		UserTaskDTO userDto = new UserTaskDTO();
		String serviceCode = ApplicationContextProvider.getApplicationContext().getBean(IServiceMasterDAO.class)
				.getServiceShortDescByServiceId(serviceId);
		if (MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL.equals(serviceCode)
				|| MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL.equals(serviceCode)) {
			userDto = iWorkflowTaskService.findByTaskIdAndReferenceId(taskId, applicationId);
			if (userDto == null)
				userDto = iWorkflowTaskService.findByTaskId(taskId);
		} else {
			userDto = iWorkflowTaskService.findByTaskId(taskId);
		}
		ObjectionDetailsDto objDto = iObjectionDetailsService.getObjectionDetailByObjNo(orgId,
				userDto.getReferenceId());

		HearingInspectionDto hearInsDto = hearingAndInspectionService.getHearingDetailByObjId(orgId,
				objDto.getObjectionId());
		ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
		objDto.setDeptName(service.getTbDepartment().getDpDeptdesc());
		objDto.setServiceName(service.getSmServiceName());
		objDto.setApmApplicationId(objDto.getApmApplicationId());
		objDto.setTaskId(taskId);

		objDto.setDeptCode(service.getTbDepartment().getDpDeptcode());
		getModel().setDocumentList(
				iChecklistVerificationService.getDocumentUploadedByRefNo(userDto.getReferenceId(), orgId));
		getModel().setObjectionDetailsDto(objDto);
		getModel().setHearingInspectionDto(hearInsDto);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
		this.getModel().getHearingInspectionDto().setName(objDto.getfName() + MainetConstants.WHITE_SPACE + objDto.getlName());
		List<DesignationBean> designlist  = designationService.getDesignByOrgId(orgId);
		if(!CollectionUtils.isEmpty(designlist))
		this.getModel().setDesignlist(designlist);
		}
		objDto.setLocName(iLocationMasService.getLocationNameById(objDto.getCodIdOperLevel1(), orgId));

		if (service.getSmShortdesc().equals(MainetConstants.RTISERVICE.RTIAPPLICATIONSERVICECODE)) {
			Long group = 1l;
			TaskAssignmentRequest taskDto = new TaskAssignmentRequest();
			taskDto.setWorkflowTypeId(userDto.getWorkflowId());
			taskDto.setServiceEventName(MainetConstants.RTISERVICE.SERVICE_EVENT_NAME);
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
					StringBuilder emp = new StringBuilder();

					List<Employee> empList = employeeService.getEmpDetailListByEmpIdList(actorIdList, orgId);

					/*
					 * for (Employee employee : empList) { emp += employee.getEmpname() +
					 * MainetConstants.WHITE_SPACE + employee.getEmpmname() +
					 * MainetConstants.WHITE_SPACE + employee.getEmplname(); }
					 */

					if (!StringUtils.isEmpty(empList)) {
						/*
						 * emp += empList.get(0).getEmpname() + MainetConstants.WHITE_SPACE +
						 * empList.get(0).getEmpmname() + MainetConstants.WHITE_SPACE +
						 * empList.get(0).getEmplname();
						 */
						// adding code for showing all employee name whom rights for RTI workflow as per
						// Anuj's demo points.
						for (Employee employee : empList) {

							emp.append(employee.getEmpname() + MainetConstants.WHITE_SPACE);

							if (null != employee.getEmpmname() && !employee.getEmpmname().isEmpty()) {
								emp.append(employee.getEmpmname() + MainetConstants.WHITE_SPACE);

							}
							if (null != employee.getEmplname() && !employee.getEmplname().isEmpty()) {
								emp.append(employee.getEmplname() + MainetConstants.WHITE_SPACE);

							}
							emp.append(",");
						}
						if (!StringUtils.isEmpty(emp)) {

							this.getModel().setPioName(emp.deleteCharAt(emp.length() - 1).toString());
						}
					}

				}
			}
		}
		httpServletRequest.setAttribute("serviceCode", serviceCode);
		return defaultResult();

	}

	@RequestMapping(params = "saveHearing", method = RequestMethod.POST)
	public ModelAndView saveHearing(final HttpServletRequest httpServletRequest) throws Exception {
		this.getModel().bind(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee emp = UserSession.getCurrent().getEmployee();
		ObjectionDetailsDto objDto = this.getModel().getObjectionDetailsDto();
		HearingInspectionDto hearInsDto = this.getModel().getHearingInspectionDto();
		objDto.setLangId((long) UserSession.getCurrent().getLanguageId());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)) {
		String hearingStat = CommonMasterUtility.getNonHierarchicalLookUpObject(hearInsDto.getHearingStatus(),
				UserSession.getCurrent().getOrganisation()).getLookUpCode();
		if(MainetConstants.Objection.InspHearStatus.REJECT.equals(hearingStat)){
			Long  decisionFav = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix("D", "DIF", orgId);
		this.getModel().getHearingInspectionDto().setDecisionInFavorOf(decisionFav);
		}
		}
		List<DocumentDetailsVO> hearingDocs = prepareFileUploadForHearing();
		hearInsDto.setHearingDocs(hearingDocs);
		
		ServiceMaster service = serviceMaster.getServiceMaster(objDto.getServiceId(), orgId);
		hearingAndInspectionService.saveHearingMaster(hearInsDto, orgId, emp.getEmpId(), emp.getEmppiservername(),
				objDto);
		String hearingStatus = CommonMasterUtility.getNonHierarchicalLookUpObject(hearInsDto.getHearingStatus(),
				UserSession.getCurrent().getOrganisation()).getLookUpCode();
		String decisionInFavorOf = CommonMasterUtility.getNonHierarchicalLookUpObject(hearInsDto.getDecisionInFavorOf(),
				UserSession.getCurrent().getOrganisation()).getLookUpCode();
		List<String> serviceCodeList = Arrays.asList("SAS", "CIA", "NCA");
		if(Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SUDA)
				&& MainetConstants.FlagC.equals(decisionInFavorOf)
				&& MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(service.getTbDepartment().getDpDeptcode())
				&& serviceCodeList.contains(service.getSmShortdesc())
				&& MainetConstants.Objection.InspHearStatus.COMPLETE.equals(hearingStatus)) {
			objDto.setUserId(emp.getEmpId());
			Boolean saveStatus = saveAssessmentAfterObjection(objDto);
			if (!saveStatus) {
				this.getModel().setSuccessMessage(getApplicationSession().getMessage("obj.hering.fail"));
	    		return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
	        }
		}
		if (MainetConstants.Objection.InspHearStatus.COMPLETE.equals(hearingStatus)) {
			if (!StringUtils.isEmpty(decisionInFavorOf) && decisionInFavorOf.equals(MainetConstants.FlagC)) {
				hearingAndInspectionService.callWorkflow(emp, objDto, MainetConstants.WorkFlow.Decision.APPROVED,decisionInFavorOf);
			} else {
				hearingAndInspectionService.callWorkflow(emp, objDto, MainetConstants.WorkFlow.Decision.APPROVED);
			}
		} else if (MainetConstants.Objection.InspHearStatus.SCHEDULE.equals(hearingStatus)) {
			hearingAndInspectionService.callWorkflow(emp, objDto, "RESCHEDULE_HEARING");
		}
		else {
			hearingAndInspectionService.callWorkflow(emp, objDto,MainetConstants.WorkFlow.Decision.REJECTED);
		}
		
		// update the objection status in case of RTS service
		if (service.getTbDepartment().getDpDeptcode().equals(MainetConstants.RightToService.RTS_DEPT_CODE)
				&& (service.getSmShortdesc().equals(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL) || service
						.getSmShortdesc().equals(MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL))) {
			if (MainetConstants.Objection.InspHearStatus.REJECT.equals(hearingStatus)) {
				hearingAndInspectionService.callWorkflow(emp, objDto, MainetConstants.WorkFlow.Decision.REJECTED);
			}
			// first check workflow request update the final status or not than only update
			// objection
			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(null, objDto.getObjectionNumber(), objDto.getOrgId());
			if (workflowRequest != null && workflowRequest.getStatus().equals(MainetConstants.WorkFlow.Status.CLOSED)) {
				iObjectionDetailsService.updateStatusOfOjection(objDto.getObjectionId(),
						UserSession.getCurrent().getEmployee().getEmpId(),
						UserSession.getCurrent().getEmployee().getEmppiservername(), workflowRequest.getLastDecision());
			}
		}
		//D#106556
		sendSmsAndEmail(objDto, emp.getEmpId(),hearingStatus);
		this.getModel().setSuccessMessage(getApplicationSession().getMessage("obj.hering.save"));
		return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
	}

	private Boolean saveAssessmentAfterObjection(ObjectionDetailsDto objDto) {
        Boolean status = false;
        final ResponseEntity<?> responseEntity = RestClient.callRestTemplateClient(objDto,
                ServiceEndpoints.SAVE_ASSESSMENT_AFTER_OBJECTION);
        if ((responseEntity != null) && (responseEntity.getStatusCode() == HttpStatus.OK)) {
            status = (Boolean) responseEntity.getBody();
        }
        return status;
    }
	private void sendSmsAndEmail(ObjectionDetailsDto objectionDetailsDto, Long empId,String hearingStatus) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(objectionDetailsDto.geteMail());
		dto.setAppName(String.join(" ", Arrays.asList(objectionDetailsDto.getfName(), objectionDetailsDto.getmName(),
				objectionDetailsDto.getlName())));
		dto.setMobnumber(objectionDetailsDto.getMobileNo());
		if (objectionDetailsDto.getApmApplicationId() != null) {
			dto.setAppNo(objectionDetailsDto.getApmApplicationId().toString());
		}
		String SMS_EMAIL_ALERT_TYPE=null;
		if (!StringUtils.isEmpty(hearingStatus)) {
			if(MainetConstants.Objection.InspHearStatus.COMPLETE.equals(hearingStatus)) {
				SMS_EMAIL_ALERT_TYPE=PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL;
			}
			else if (MainetConstants.Objection.InspHearStatus.SCHEDULE.equals(hearingStatus)) {
				SMS_EMAIL_ALERT_TYPE=PrefixConstants.SMS_EMAIL_ALERT_TYPE.SCHEDULED_MESSAGE;
			}
			else   {
				SMS_EMAIL_ALERT_TYPE=PrefixConstants.SMS_EMAIL_ALERT_TYPE.REJECTED_MSG;
			}
			
		}
		dto.setReferenceNo(objectionDetailsDto.getObjectionReferenceNumber());
		dto.setObjectionNo(objectionDetailsDto.getObjectionNumber());
		dto.setFrmDt(Utility.dateToString(objectionDetailsDto.getObjectionDate()));
		String paymentUrl = MainetConstants.OBJECTION_COMMON.HEARING_SMS_EMAIL;
		Organisation org = new Organisation();
		org.setOrgid(objectionDetailsDto.getOrgId());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(empId);
		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
				SMS_EMAIL_ALERT_TYPE, dto, org, Utility.getDefaultLanguageId(org));

	}

	public List<DocumentDetailsVO> prepareFileUploadForHearing() {
		long count = 0;
		List<DocumentDetailsVO> docs = null;
		if ((FileUploadUtility.getCurrent().getFileMap() != null)
				&& !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
			docs = new ArrayList<>();
			for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
				final List<File> list = new ArrayList<>(entry.getValue());
				for (final File file : list) {
					try {
						DocumentDetailsVO d = new DocumentDetailsVO();
						final Base64 base64 = new Base64();
						final String bytestring = base64.encodeToString(FileUtils.readFileToByteArray(file));
						d.setDocumentByteCode(bytestring);
						d.setDocumentName(file.getName());
						d.setDocumentSerialNo(count);
						count++;
						docs.add(d);
					} catch (final IOException e) {
						LOGGER.error("Exception has been occurred in file byte to string conversions", e);
					}
				}
			}
		}
		return docs;
	}
	
	@RequestMapping(params = "getSelectedOwnerInfo", method = RequestMethod.POST)
	public @ResponseBody List<String> getSelectedOwnerInfo(final HttpServletRequest httpServletRequest) {
		this.getModel().bind(httpServletRequest);
		return hearingAndInspectionService.getSelectedOwnerInfoByApplId(
				this.getModel().getObjectionDetailsDto().getApmApplicationId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
	}

}
