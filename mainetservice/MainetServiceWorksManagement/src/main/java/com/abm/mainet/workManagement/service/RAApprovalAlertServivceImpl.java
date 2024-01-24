package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowDetDTO;
import com.abm.mainet.common.workflow.dto.WorkflowMasDTO;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTask;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.workManagement.dto.WorksRABillDto;
import com.ibm.icu.text.SimpleDateFormat;

public class RAApprovalAlertServivceImpl implements RAApprovalAlertService {

	private static final Logger LOGGER = Logger.getLogger(RAApprovalAlertServivceImpl.class);

	@Autowired
	private WorksRABillService raBillService;

	@Autowired
	EmployeeJpaRepository employeeRepo;

	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private IWorkFlowTypeService iWorkFlowTypeService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Transactional
	public void alertForRaApproval(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		Long orgId = runtimeBean.getOrgId().getOrgid();
		// Organisation organisation = organisationService.getOrganisationById(orgId);
		List<WorksRABillDto> worksRABillDtos = raBillService.getAllRaDetailforSchedular(orgId);

		worksRABillDtos.forEach(dto -> {
			WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(null, dto.getRaCode(), dto.getOrgId());

			if (workflowRequest != null) {
				int count = 0;
				for (WorkflowTask taskDto : workflowRequest.getWorkFlowTaskList()) {
					// To check whether the status is pending or not
					if (taskDto.getTaskStatus().equals("PENDING")) {
						String currentDate = getCurrnetDate();
						// To get the last decision date on work estimate approval
						String lastDecisionDate = getLastDecisionDate(workflowRequest.getLastDateOfAction());

						// Taking the WorkflowMasDTO base on workflow id
						WorkflowMasDTO workflowMasDTO = iWorkFlowTypeService
								.findById(workflowRequest.getWorkflowTypeId());
						// Taking the final authority WorkflowDetDTO
						WorkflowDetDTO workflowDetDto = workflowMasDTO.getWorkflowDet()
								.get(workflowMasDTO.getWorkflowDet().size() - 1);
						// Taking the WorkflowDetDTO detail for which work is in pending state
						WorkflowDetDTO workflowDets = new WorkflowDetDTO();
						if (count <= workflowMasDTO.getWorkflowDet().size()) {
							workflowDets = workflowMasDTO.getWorkflowDet().get(count - 1);
						} else {
							workflowDets = workflowMasDTO.getWorkflowDet()
									.get(workflowMasDTO.getWorkflowDet().size() - 1);
						}
						if (workflowDets.getSla() != null && !workflowDets.getSla().isEmpty()) {

							LookUp lookUp = CommonMasterUtility
									.getNonHierarchicalLookUpObjectByPrefix(workflowDets.getUnit(), orgId, "UTS");

							Long dayDiff = 0l;
							if (lookUp != null) {
								if (lookUp.getLookUpCode().equals(MainetConstants.FlagD)) {
									// Day difference between last decision date and current date
									dayDiff = getDaysDiff(currentDate, lastDecisionDate);
								} else {
									try {
										Long dateDifference = getDaysDiff(currentDate, lastDecisionDate);
										if (dateDifference == 0) {
											dayDiff = getHourDiff(workflowRequest.getLastDateOfAction());
										} else {
											dayDiff = dateDifference * 24l;
										}
									} catch (ParseException e) {
										e.printStackTrace();
									}
								}
							}

							BigDecimal sla = new BigDecimal(workflowDets.getSla());
							if (dayDiff >= sla.longValue()) {
								String empIds = workflowDetDto.getRoleOrEmpIds();
								String[] arr = empIds.split(",");
								for (String id : arr) {
									if (!id.isEmpty()) {
										Employee employee = employeeRepo.findEmployeeByIdAndOrgId(Long.parseLong(id),
												orgId);
										SMSAndEmailDTO smsAndEmailDto = new SMSAndEmailDTO();
										if (employee != null) {
											smsAndEmailDto.setMobnumber(employee.getEmpmobno());
											smsAndEmailDto.setUserId(employee.getEmpId());
											smsAndEmailDto.setAppName("Alert Message");
											smsAndEmailDto.setEmail(employee.getEmpemail());
											smsAndEmailDto.setOrgId(employee.getOrganisation().getOrgid());
											sendMessageAndEmail(smsAndEmailDto, orgId);
										}
									}
								}
							}
						}

					}
					count++;
				}

			}
		});
	}

	private Long getDaysDiff(String currentDate, String dueDate) {

		LocalDate dateBefore = LocalDate.parse(currentDate);
		LocalDate dateAfter = LocalDate.parse(dueDate);

		return ChronoUnit.DAYS.between(dateAfter, dateBefore);

	}

	private String getCurrnetDate() {
		LocalDateTime now = null;
		String localdate = new String();
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			now = LocalDateTime.now();
			localdate = dtf.format(now);

		} catch (Exception e) {
			LOGGER.info("Exception while getting the current date" + e);
		}
		return localdate;
	}

	private String getLastDecisionDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(date);
	}

	private void sendMessageAndEmail(SMSAndEmailDTO smsAndEmailDTO, Long orgId) {
		Organisation org = organisationService.getOrganisationById(orgId);
		int langId = Utility.getDefaultLanguageId(org);
		String menuUrl = "MeasurementBook.html";

		iSMSAndEmailService.sendEmailSMS("WMS", menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION,
				smsAndEmailDTO, org, langId);
	}

	private Long getHourDiff(Date date) throws ParseException {
		Long timeInMill = date.getTime();
		Date date1 = Calendar.getInstance().getTime();
		Long diff = date1.getTime() - timeInMill;
		Long hours = (Long) ((diff / (1000 * 60 * 60)) % 24);
		return hours;
	}

}