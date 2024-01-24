package com.abm.mainet.cfc.objection.ui.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.HearingAndInspectionService;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.ui.model.ObjectionDetailsModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/InspectionDetailEntry.html")
public class InspectionDetailEntryController extends AbstractFormController<ObjectionDetailsModel> {
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
	private IEmployeeService iEmployeeService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;

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
		// get user task based on serviceId
		UserTaskDTO userDto = new UserTaskDTO();
		String serviceCode = ApplicationContextProvider.getApplicationContext().getBean(IServiceMasterDAO.class)
				.getServiceShortDescByServiceId(serviceId);
		if (MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL.equals(serviceCode)
				|| MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL.equals(serviceCode)) {
			userDto = iWorkflowTaskService.findByTaskIdAndReferenceId(taskId, applicationId);
			if(userDto== null)
	                    userDto = iWorkflowTaskService.findByTaskId(taskId);
		} else {
			userDto = iWorkflowTaskService.findByTaskId(taskId);
		}
		ObjectionDetailsDto objDto = iObjectionDetailsService.getObjectionDetailByObjNo(orgId,
				userDto.getReferenceId());
		HearingInspectionDto hearInsDto = hearingAndInspectionService.getInspectionByObjId(orgId,
				objDto.getObjectionId());
		/* Defect #34050 */
		if (hearInsDto.getInspStatus().contentEquals(MainetConstants.FlagC)) {
			objDto.setInspectionStatus(MainetConstants.FlagY);
		} else {
			objDto.setInspectionStatus(MainetConstants.FlagN);
		}
		ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
		objDto.setDeptName(service.getTbDepartment().getDpDeptdesc());
		objDto.setServiceName(service.getSmServiceName());
		objDto.setApmApplicationId(objDto.getApmApplicationId());
		objDto.setTaskId(taskId);
		objDto.setDeptCode(service.getTbDepartment().getDpDeptcode());
		this.getModel().setDocumentList(
				iChecklistVerificationService.getDocumentUploaded(objDto.getApmApplicationId(), orgId));
		this.getModel().setObjectionDetailsDto(objDto);
		this.getModel().setHearingInspectionDto(hearInsDto);
		this.getModel().setEmpList(getEmpListByDeptId(objDto.getObjectionDeptId(), orgId));
		objDto.setLocName(iLocationMasService.getLocationNameById(objDto.getCodIdOperLevel1(), orgId));

		return defaultResult();

	}

	@RequestMapping(params = "saveInspection", method = RequestMethod.POST)
	public ModelAndView saveHearingOrInspectionShedule(final HttpServletRequest httpServletRequest) throws Exception {
		this.getModel().bind(httpServletRequest);
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Employee emp = UserSession.getCurrent().getEmployee();
		ObjectionDetailsDto objDto = this.getModel().getObjectionDetailsDto();
		HearingInspectionDto hearInsDto = this.getModel().getHearingInspectionDto();
		hearInsDto.setInspStatus(MainetConstants.Objection.InspHearStatus.COMPLETE); // while saving Inspection status
																						// should be
																						// Complete

		hearingAndInspectionService.saveInspectionMaster(hearInsDto, orgId, emp.getEmpId(), emp.getEmppiservername());
		ServiceMaster serviceMasterService = serviceMaster.getServiceMaster(objDto.getServiceId(), orgId);
		/* Defect #34050 */
		if (!(serviceMasterService.getSmShortdesc().contentEquals(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL)
				|| serviceMasterService.getSmShortdesc().contentEquals("RAF")

						&& objDto.getDeptCode().contentEquals("RTI"))) {
			hearingAndInspectionService.callWorkflow(emp, objDto, MainetConstants.WorkFlow.Decision.APPROVED);
		}
		// Defect #81687 For checking service short code ,desc and hearIns No null in
		// case Of RTI
		if ((serviceMasterService.getSmShortdesc().contentEquals(MainetConstants.RTISERVICE.RTIFIRSTAPPEAL)
				|| serviceMasterService.getSmShortdesc().contentEquals("RAF")
						&& objDto.getDeptCode().contentEquals("RTI")
						&& (hearInsDto.getInsHearNo() != null && !hearInsDto.getInsHearNo().isEmpty()))) {
			hearingAndInspectionService.callWorkflow(emp, objDto, MainetConstants.WorkFlow.Decision.APPROVED);
		}


		sendSmsAndEmail(objDto, emp.getEmpId());
		this.getModel().setSuccessMessage("Inspection Detail Entry save sucessfully");
		return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
	}

	private List<LookUp> getEmpListByDeptId(final Long deptId, final Long orgId) {
		List<LookUp> empListLookup = new ArrayList<>();
		List<Employee> empList = iEmployeeService.findAllEmployeeByDept(orgId, deptId);
		for (Employee emp : empList) {
			LookUp lookUp = new LookUp();
			lookUp.setLookUpId(emp.getEmpId());
			lookUp.setDescLangFirst(emp.getEmpname() + " " + emp.getEmplname());
			lookUp.setDescLangSecond(emp.getEmpname() + " " + emp.getEmplname());
			empListLookup.add(lookUp);
		}
		return empListLookup;
	}

	private void sendSmsAndEmail(ObjectionDetailsDto objectionDetailsDto, Long empId) {
		final SMSAndEmailDTO dto = new SMSAndEmailDTO();
		dto.setEmail(objectionDetailsDto.geteMail());
		dto.setAppName(String.join(" ", Arrays.asList(objectionDetailsDto.getfName(), objectionDetailsDto.getmName(),
				objectionDetailsDto.getlName())));
		dto.setMobnumber(objectionDetailsDto.getMobileNo());
		if (objectionDetailsDto.getApmApplicationId() != null) {
			dto.setAppNo(objectionDetailsDto.getApmApplicationId().toString());
		}
		dto.setReferenceNo(objectionDetailsDto.getObjectionReferenceNumber());
		dto.setObjectionNo(objectionDetailsDto.getObjectionNumber());
		dto.setFrmDt(Utility.dateToString(objectionDetailsDto.getObjectionDate()));
		String paymentUrl = MainetConstants.OBJECTION_COMMON.INSPECTION_SMS_EMAIL;
		Organisation org = new Organisation();
		org.setOrgid(objectionDetailsDto.getOrgId());
		// Added Changes As per told by Rajesh Sir For Sms and Email
		dto.setUserId(empId);
		iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, Utility.getDefaultLanguageId(org));

	}
}
