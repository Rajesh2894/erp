package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.AccountInvestmentMasterDto;
import com.abm.mainet.account.repository.AccoutInvestMentMasterJpaRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.quartz.domain.QuartzSchedulerMaster;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author Bhagyashri.dongardive
 * @since 02 December 2020
 */
@Service
public class MaturityOrRenewalAlertServiceImpl implements IMaturityOrRenewalAlertService {

	@Autowired
	AccountInvestmentService accountInvestmentService;

	@Autowired
	ISMSAndEmailService iSMSAndEmailService;

	@Autowired
	AccoutInvestMentMasterJpaRepository accoutInvestMentMasterJpaRepository;

	@Override
	@Transactional
	public void sendMaturityOrRenewalAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());

		List<AccountInvestmentMasterDto> allData = accountInvestmentService
				.findAllInvestmentDataByOrgId(org.getOrgid());
		List<Employee> empList = accoutInvestMentMasterJpaRepository
				.getAllEmployeeAssociatedWithInvestmestMaster(org.getOrgid());
		if (CollectionUtils.isNotEmpty(empList)) {
			for (Employee e : empList) {
				if (CollectionUtils.isNotEmpty(allData)) {
					for (AccountInvestmentMasterDto inv : allData) {

						if (inv.getCreatedBy().equals(e.getEmpId())) {

							executeSmsAndEmail(inv, org, e);
						}

					}
				}

			}
		}

	}

	private void executeSmsAndEmail(AccountInvestmentMasterDto investdto, Organisation org, Employee emp) {
		Integer langId = 1;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.FUND_MASTER.INVESTMENT_MATURITY_ALERT,
				org);
		List<Integer> days = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lookUps)) {
			lookUps.forEach(lookUp -> {
				days.add(Integer.parseInt(lookUp.getLookUpCode()));
			});
		}

		if (investdto.getInvstDueDate() != null) {

			int daysBetweenDates = Utility.getDaysBetweenDates(new Date(), investdto.getInvstDueDate());
			//commented by bhagyashri
			/*if (DateUtils.isSameDay(new Date(), investdto.getInvstDueDate())) {
				daysBetweenDates = 0;
			}*/

			if (days.contains(daysBetweenDates)) {
				// send MSG and mail
				final SMSAndEmailDTO smsdto = new SMSAndEmailDTO();
				smsdto.setMobnumber(emp.getEmpmobno());
				smsdto.setEmail(emp.getEmpemail());
				smsdto.setAppName(emp.getEmpname()+" "+emp.getEmplname());
				// Data added for message template
				smsdto.setAppNo(investdto.getInvstNo());
				smsdto.setDueDt(Utility.dateToString(investdto.getInvstDueDate()));
				smsdto.setFrmDt(Utility.dateToString(investdto.getInvstDate()));
				smsdto.setReferenceNo(String.valueOf(investdto.getInFdrNo()));
				smsdto.setUserId(investdto.getCreatedBy());
				smsdto.setOrgId(org.getOrgid());
				iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ACCOUNT,
						MainetConstants.FUND_MASTER.INVESTMENT_URL,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsdto, org, langId);
			}
		}

	}

}
