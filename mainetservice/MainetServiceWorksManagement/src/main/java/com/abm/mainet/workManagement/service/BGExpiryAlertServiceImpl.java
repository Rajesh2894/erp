package com.abm.mainet.workManagement.service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.ContractMastEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.TbAcVendormasterEntity;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.repository.ContractAgreementRepository;
import com.abm.mainet.common.master.repository.EmployeeJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author sadik.shaikh
 *
 */
@Service
public class BGExpiryAlertServiceImpl implements BGExpiryAlertService {

	@Autowired
	ContractAgreementRepository contractRepo;

	@Autowired
	EmployeeJpaRepository employeeRepo;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private IOrganisationService organisationService;

	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;

	@Transactional
	public void alertForBGExpiry(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {

		Long orgId = runtimeBean.getOrgId().getOrgid();
		Long empId = 1l;
		TbDepartment department = departmentService.findDeptByCode(orgId, "A", "WMS");
		Long deptId = department.getDpDeptid();
		List<ContractMastEntity> contractMastEntities = contractRepo.findAllContractUnderDept(deptId, orgId);

		List<ContractMastEntity> contractMastEntitiesMapped = new ArrayList<ContractMastEntity>();

		contractMastEntities.forEach(masEntity -> {

			String str = contractRepo.findContractMapedOrNotUnderDept(deptId, masEntity.getContId());
			if (str != null) {
				if (str.equals("Y")) {
					contractMastEntitiesMapped.add(masEntity);
				}
			}
		});

		contractMastEntitiesMapped.forEach(masEntity -> {
			if (!masEntity.getLoaNo().isEmpty()) {
				masEntity.setContractDetailList(contractRepo.getContractDetail(masEntity, orgId));
				masEntity.getContractDetailList().forEach(detailEntity -> {
					if (detailEntity.getContDepDueDt() != null) {

						String currentDate = getCurrnetDate();
						String dueDate = getDueDate(detailEntity.getContDepDueDt().toString());
						long dayDiff = getDaysDiff(currentDate, dueDate);

						if (dayDiff == 10) {
							masEntity.setContractPart1List(contractRepo.getContractParty1Detail(masEntity, orgId));
							masEntity.getContractPart1List().forEach(party1Entity -> {
								SMSAndEmailDTO smsAndEmailDto = new SMSAndEmailDTO();
								String[] fName = party1Entity.getContp1Name().split(" ");
								Employee employee = employeeRepo.findEmployeeByName(fName[0], party1Entity.getOrgId());
								if (employee != null) {
									smsAndEmailDto.setMobnumber(employee.getEmpmobno());
									smsAndEmailDto.setUserId(empId);
									smsAndEmailDto.setAppName("Alert message");
									smsAndEmailDto.setEmail(employee.getEmpemail());
									smsAndEmailDto.setOrgId(employee.getOrganisation().getOrgid());
									smsAndEmailDto.setToDt(dueDate);
									sendMessageAndEmail(smsAndEmailDto, orgId);
								}

							});

							masEntity.setContractPart2List(contractRepo.getContractParty2Detail(masEntity, orgId));
							masEntity.getContractPart2List().forEach(party2Entity -> {
								SMSAndEmailDTO smsAndEmailDto = new SMSAndEmailDTO();
								TbAcVendormasterEntity vendorEntity = contractRepo
										.getVenderbyId(party2Entity.getOrgId(), party2Entity.getVmVendorid());
								if (vendorEntity != null) {
									smsAndEmailDto.setMobnumber(vendorEntity.getMobileNo());
									smsAndEmailDto.setUserId(empId);
									smsAndEmailDto.setAppName("Alert message");
									smsAndEmailDto.setEmail(vendorEntity.getEmailId());
									smsAndEmailDto.setOrgId(vendorEntity.getOrgid());
									smsAndEmailDto.setToDt(dueDate);
									sendMessageAndEmail(smsAndEmailDto, orgId);
								}
							});
						}

					}

				});

			}

		});

	}

	private String getCurrnetDate() {
		LocalDateTime now = null;
		String localdate = new String();
		try {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			now = LocalDateTime.now();
			localdate = dtf.format(now);

		} catch (Exception e) {
			// TODO: handle exception
		}

		return localdate;
	}

	private long getDaysDiff(String currentDate, String dueDate) {

		LocalDate dateBefore = LocalDate.parse(currentDate);
		LocalDate dateAfter = LocalDate.parse(dueDate);

		return ChronoUnit.DAYS.between(dateBefore, dateAfter);

	}

	private String getDueDate(String date) {

		String[] str = date.split(" ");
		return str[0];
	}

	private void sendMessageAndEmail(SMSAndEmailDTO smsAndEmailDTO, Long orgId) {
		Organisation org = organisationService.getOrganisationById(orgId);
		int langId = Utility.getDefaultLanguageId(org);
		String menuUrl = "ContractAgreement.html";

		boolean status = iSMSAndEmailService.sendEmailSMS("WMS", menuUrl,
				PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsAndEmailDTO, org, langId);
		System.out.println(status);
	}
}
