package com.abm.mainet.common.workflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.TaskNotificationRequest;
import com.abm.mainet.common.workflow.repository.WorkFlowTypeRepository;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * This service provides API for task related notification. This service will
 * make use of ISMSAndEmailService to send notification as per the templates
 * defined in master data.
 * 
 * This interface is CXF enabled service, which is exposed as REST and SOPA
 * API's
 * 
 * @author sanket.joshi
 *
 */
@Service
@WebService(endpointInterface = "com.abm.mainet.common.workflow.service.TaskNotificationService")
@Api("Task Notification Service")
@Path("/workflow/task")
public class TaskNotificationServiceImpl implements TaskNotificationService {

	@Autowired
	private WorkFlowTypeRepository workFlowTypeRepository;

	@Autowired
	private ISMSAndEmailService sMSAndEmailService;

	@Autowired
	private IEmployeeService iEmployeeService;

	@Async
	@POST
	@Override
	@Transactional
	@Path("/notify")
	@ApiOperation(value = "Notify task", notes = "Notify task")
	public void notifyUser(
			@ApiParam(value = "Task notification request", required = true) TaskNotificationRequest taskNotificationRequest) {

		if (taskNotificationRequest.isEmpty())
			return;

		String tokenNumber = (taskNotificationRequest.getApplicationId() != null)
				? taskNotificationRequest.getApplicationId()
				: taskNotificationRequest.getReferenceId();
		List<Long> empIdList = new ArrayList<>();
		String[] empIds = taskNotificationRequest.getActorId().split(MainetConstants.operator.COMMA);
		for (String s : empIds) {
			if (!StringUtils.isEmpty(s))
				empIdList.add(Long.valueOf(s));
		}

		WorkflowMas workflow = workFlowTypeRepository.findOne(taskNotificationRequest.getWorkflowId());
		List<Employee> empList = null;
		if (!CollectionUtils.isEmpty(empIdList))
			empList = iEmployeeService.getEmpDetailListByEmpIdList(empIdList, taskNotificationRequest.getOrgId());

		/*
		 * String emailIds = empList.stream().map(Employee::getEmpemail)
		 * .collect(Collectors.joining(MainetConstants.operator.COMMA)); String
		 * mobileNumbers = empList.stream().map(Employee::getEmpmobno)
		 * .collect(Collectors.joining(MainetConstants.operator.COMMA));
		 */

		SMSAndEmailDTO notification = new SMSAndEmailDTO();
		notification.setOrgId(workflow.getOrganisation().getOrgid());
		notification.setOrganizationName(workflow.getOrganisation().getONlsOrgname());
		notification.setOrgName(workflow.getOrganisation().getONlsOrgname());
		notification.setServName(workflow.getService().getSmServiceName());
		notification.setServNameMar(workflow.getService().getSmServiceNoteMar());
		notification.setServiceUrl(taskNotificationRequest.getServiceEventUrl());
		notification.setDeptShortCode(workflow.getDepartment().getDpDeptcode());
		notification.setServiceId(workflow.getService().getSmServiceId());
		/*Setting referenceId*/
		if(StringUtils.isNotBlank(taskNotificationRequest.getReferenceId())) {
			notification.setAppNo(taskNotificationRequest.getReferenceId());
		}
		
		notification.setTemplateType(PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION);
		// notification.setEmail(emailIds);
		// notification.setMobnumber(mobileNumbers);
		notification.setCurrentDate(Utility.dateToString(new Date(), MainetConstants.DATE_HOUR_FORMAT));
		notification.setTokenNumber(tokenNumber.trim());
		notification.setDecision(taskNotificationRequest.getLastDecision());
		notification.setStatus(MainetConstants.WorkFlow.Status.PENDING);
		notification.setMsg(taskNotificationRequest.getComments());

		if (taskNotificationRequest.getSlaCal() != null && taskNotificationRequest.getSlaCal() != 0) {
			long days = ((((taskNotificationRequest.getSlaCal() / 1000) / 60) / 60) / 24) % 24;
			notification.setSlaDays(String.valueOf(days));
		}
		Organisation organisation = new Organisation();

		organisation.setOrgid(notification.getOrgId());
		if (!CollectionUtils.isEmpty(empList)) {
			empList.forEach(emp -> {
				notification.setUserId(emp.getEmpId());
				if (!StringUtils.isEmpty(emp.getEmpmobno()))
					notification.setMobnumber(emp.getEmpmobno());
				if (!StringUtils.isEmpty(emp.getEmpemail()))
					notification.setEmail(emp.getEmpemail());
				sMSAndEmailService.sendEmailSMS(notification.getDeptShortCode(), notification.getServiceUrl(),
						notification.getTemplateType(), notification, organisation, notification.getLangId());
			});
		}

	}
}
