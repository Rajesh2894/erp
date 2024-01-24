package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.AccountBillEntryMasterBean;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Designation;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.DesignationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * 
 * @author vishwanath.s
 *
 */
@Service
public class AccountBillPaymentReminderAlertServiceImpl implements AccountBillPaymentReminderAlertService {

	@Resource
	private AccountBillEntryService billEntryService;
	@Autowired
	private ISMSAndEmailService iSMSAndEmailService;
	@Autowired
	private IEmployeeService iEmployeeService;
	@Autowired
	private DesignationService designationService;
	
	 private static final Logger LOGGER = Logger.getLogger(AccountLoanInstallmentPaymentAlertServiceImpl.class);
	
	@Override
	public void sendBillPaymentReminderAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		// TODO Auto-generated method stub
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());
		List<AccountBillEntryMasterEnitity> allBillData = billEntryService
				.getAllPendingPaymentBillEntryData(runtimeBean.getOrgId().getOrgid());
		String emplDesignation = ApplicationSession.getInstance().getMessage("employee.desi");
		LOGGER.info("Employee Designation "+emplDesignation);
		Designation designation = designationService.findByShortname(emplDesignation);
		if (designation != null) {
			List<Employee> employee = iEmployeeService.findAllEmployeeByDesgId(runtimeBean.getOrgId().getOrgid(),
					designation.getDsgid());
			if (CollectionUtils.isNotEmpty(allBillData)) {
				for (AccountBillEntryMasterEnitity entity : allBillData) {
					for (Employee emp : employee) {
						if (entity.getDueDate() != null) {
							AccountBillEntryMasterBean billDto = new AccountBillEntryMasterBean();
							billDto.setBillNo(entity.getBillNo());
							billDto.setVendorDesc(entity.getVendorName());
							billDto.setBillAmountStr(entity.getBillTotalAmount().toString());
							billDto.setDueDate(Utility.dateToString(entity.getDueDate()));
							executeSmsAndEmail(billDto, org, emp);
						}
					}
				}
			}
		}
	}

	private void executeSmsAndEmail(AccountBillEntryMasterBean billDto, Organisation org, Employee emp) {
		LOGGER.info("Enter into the executeSmsAndEmail method");
		Integer langId = 1;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.AccountBillEntry.BPR, org);
		List<Integer> days = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lookUps)) {
			lookUps.forEach(lookUp -> {
				days.add(Integer.parseInt(lookUp.getLookUpCode()));
			});
		}
		if (billDto != null) {
			int daysBetweenDates = Utility.getDaysBetweenDates(new Date(), Utility.stringToDate(billDto.getDueDate()));
			if (days.contains(daysBetweenDates)) {
				// send MSG and mail
				LOGGER.info("Satisfying the condition");
				final SMSAndEmailDTO smsdto = new SMSAndEmailDTO();
				smsdto.setMobnumber(emp.getEmpmobno());
				smsdto.setEmail(emp.getEmpemail());
				smsdto.setAppName(emp.getEmpname() + " " + emp.getEmplname());
				smsdto.setUserId(emp.getEmpId());
				// Data added for message template
				smsdto.setAppNo(billDto.getBillNo());
				smsdto.setDueDt(billDto.getDueDate());
				smsdto.setAppAmount(billDto.getBillAmountStr());
				smsdto.setOwnerName(billDto.getVendorDesc());
				// smsdto.setUserId();
				smsdto.setOrgId(org.getOrgid());
				iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ACCOUNT,
						MainetConstants.AccountBillEntry.BILL_ENTRY_URL,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsdto, org, langId);
			}

		}
	}
}
