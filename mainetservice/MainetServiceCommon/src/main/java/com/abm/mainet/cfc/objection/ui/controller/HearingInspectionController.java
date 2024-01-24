package com.abm.mainet.cfc.objection.ui.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.cfc.objection.dto.HearingInspectionDto;
import com.abm.mainet.cfc.objection.dto.NoticeMasterDto;
import com.abm.mainet.cfc.objection.dto.ObjectionDetailsDto;
import com.abm.mainet.cfc.objection.service.HearingAndInspectionService;
import com.abm.mainet.cfc.objection.service.IObjectionDetailsService;
import com.abm.mainet.cfc.objection.service.NoticeMasterService;
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
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.UserTaskDTO;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping("/HearingInspection.html")
public class HearingInspectionController extends AbstractFormController<ObjectionDetailsModel> {

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
    private IWorkflowActionService iWorkflowActionService;

    @Autowired
    private IWorkflowTaskService iWorkflowTaskService;

    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    @Autowired
    private IEmployeeService iEmployeeService;
    
    @Autowired
    private NoticeMasterService noticeMasterSevice;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("HearingInspection.html");
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
        //get User task based on service id
        UserTaskDTO userDto = new UserTaskDTO();
        String serviceCode=ApplicationContextProvider.getApplicationContext().getBean(IServiceMasterDAO.class).getServiceShortDescByServiceId(serviceId);
        if(MainetConstants.RightToService.SERVICE_CODE.FIRST_APPEAL.equals(serviceCode) || MainetConstants.RightToService.SERVICE_CODE.SECOND_APPEAL.equals(serviceCode)) {
        	userDto = iWorkflowTaskService.findByTaskIdAndReferenceId(taskId, applicationId);
        	if(userDto== null)
        	    userDto = iWorkflowTaskService.findByTaskId(taskId);
        }else {
        	userDto = iWorkflowTaskService.findByTaskId(taskId);
        }
        
        ObjectionDetailsDto objDto = iObjectionDetailsService.getObjectionDetailByObjNo(orgId,
                userDto.getReferenceId());

        HearingInspectionDto inspDto = hearingAndInspectionService.getInspectionByObjNo(orgId,
                userDto.getReferenceId());
        if (inspDto != null) {
            getModel().setOnlyInspecMode(MainetConstants.FlagY);
            objDto.setSchedulingSelection(MainetConstants.Objection.HEARING);
            objDto.setLocName(iLocationMasService.getLocationNameById(objDto.getCodIdOperLevel1(), orgId));
        }

        ServiceMaster service = serviceMaster.getServiceMaster(serviceId, orgId);
        objDto.setDeptName(service.getTbDepartment().getDpDeptdesc());
        objDto.setServiceName(service.getSmServiceName());
        objDto.setApmApplicationId(objDto.getApmApplicationId());
        objDto.setTaskId(taskId);
        this.getModel().setDocumentList(
                iChecklistVerificationService.getDocumentUploadedByRefNo(userDto.getReferenceId(), orgId));
        this.getModel().setObjectionDetailsDto(objDto);
        getModel().setInspectionDto(inspDto);
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_SKDCL)
				&& StringUtils.isNotBlank(objDto.getNoticeNo())) {
			NoticeMasterDto noticeMas = noticeMasterSevice.getNoticeByNoticeNo(orgId, objDto.getNoticeNo());
			if (noticeMas.getNotDuedt() != null) {
				LocalDate date = noticeMas.getNotDuedt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate nextOrSameThursday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
				Date hearingDate = Date.from(nextOrSameThursday.atStartOfDay(ZoneId.systemDefault()).toInstant());
				Date hearingDateWithTime = DateUtils.addHours(hearingDate,
						Integer.valueOf(MainetConstants.Common_Constant.NUMBER.ELEVEN));
				getModel().getHearingInspectionDto().setInsHearDate(hearingDateWithTime);
			}
		}
        this.getModel().setEmpList(getEmpListByDeptId(objDto.getObjectionDeptId(), orgId));

        return defaultResult();

    }

    @RequestMapping(params = "saveShedule", method = RequestMethod.POST)
    public ModelAndView saveHearingOrInspectionShedule(final HttpServletRequest httpServletRequest) throws Exception {
        this.getModel().bind(httpServletRequest);
        final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Employee emp = UserSession.getCurrent().getEmployee();
        ObjectionDetailsDto objDto = this.getModel().getObjectionDetailsDto();

        /* Defect #34050 */
        ServiceMaster service = serviceMaster.getServiceMaster(objDto.getServiceId(), orgId);
        objDto.setDeptCode(service.getTbDepartment().getDpDeptcode());

        HearingInspectionDto hearInsDto = this.getModel().getHearingInspectionDto();
        hearInsDto.setObjId(objDto.getObjectionId());
        objDto.setLangId((long) UserSession.getCurrent().getLanguageId());
        WorkflowTaskAction workflowAction = new WorkflowTaskAction();

        if (objDto.getSchedulingSelection().equals(MainetConstants.Objection.HEARING)) {
            Long satausId = CommonMasterUtility
                    .getValueFromPrefixLookUp(MainetConstants.Objection.InspHearStatus.SCHEDULE,
                            PrefixConstants.Objection.HRD, UserSession.getCurrent().getOrganisation())
                    .getLookUpId();
            hearInsDto.setHearingStatus(satausId);
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.HEARING);
            hearingAndInspectionService.saveHearingMaster(hearInsDto, orgId, emp.getEmpId(), emp.getEmppiservername(), objDto);
        } else if (objDto.getSchedulingSelection().equals(MainetConstants.Objection.INSPECTION)) {
            hearInsDto.setInspStatus(MainetConstants.Objection.InspHearStatus.SCHEDULE);
            workflowAction.setDecision(MainetConstants.WorkFlow.Decision.INSPECTION);
            hearingAndInspectionService.saveInspectionMaster(hearInsDto, orgId, emp.getEmpId(),
                    emp.getEmppiservername());
        }
        workflowAction.setApplicationId(objDto.getApmApplicationId());
        workflowAction.setTaskId(objDto.getTaskId());
        workflowAction.setReferenceId(objDto.getObjectionNumber());
        iWorkflowActionService.updateWorkFlow(workflowAction, emp,
                UserSession.getCurrent().getOrganisation().getOrgid(), objDto.getServiceId());
        this.getModel().setSuccessMessage(
                getApplicationSession().getMessage("obj.her.scheduling.save") + hearInsDto.getInsHearNo());
        sendSmsAndEmail(objDto,hearInsDto, emp.getEmpId());
        return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
    }

    private void sendSmsAndEmail(ObjectionDetailsDto objDto,HearingInspectionDto hearInsDto, Long empId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setEmail(objDto.geteMail());
        dto.setHearDate(Utility.dateToString(hearInsDto.getInsHearDate(),"dd-MM-yyyy hh:mm a"));
        dto.setMobnumber(objDto.getMobileNo());
        if (objDto.getApmApplicationId() != null) {
            dto.setAppNo(objDto.getApmApplicationId().toString());
        }
        dto.setReferenceNo(objDto.getObjectionReferenceNumber());
        dto.setObjectionNo(objDto.getObjectionNumber());
        dto.setFrmDt(Utility.dateToString(objDto.getObjectionDate()));
        String paymentUrl = MainetConstants.OBJECTION_COMMON.SCHEDULING_SMS_EMAIL;
        Organisation org = new Organisation();
        org.setOrgid(objDto.getOrgId());

        if (objDto.getSchedulingSelection().equals(MainetConstants.Objection.HEARING)) {
            dto.setMsg("Hearing");
            dto.setType("Hearing");
        } else if (objDto.getSchedulingSelection().equals(MainetConstants.Objection.INSPECTION)) {
            dto.setMsg("Inspection");
            dto.setType("Inspection");
        }
        // Added Changes As per told by Rajesh Sir For Sms and Email
        dto.setUserId(empId);
        // need to check on department name
        iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.CFC_CENTER, paymentUrl,
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.APPROVAL, dto, org, Utility.getDefaultLanguageId(org));

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

}