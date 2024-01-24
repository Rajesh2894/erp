package com.abm.mainet.account.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dto.AccountLoanDetDto;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.repository.AccountLoanMasterRepository;
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
 * 
 * @author vishwanath.s
 *
 */
@Service
public class AccountLoanInstallmentPaymentAlertServiceImpl implements AccountLoanInstallmentPaymentAlertService {

	@Autowired
	AccountLoanMasterService accountLoanMasterService;
	
	@Autowired
	AccountLoanMasterRepository accountLoanMasterRepository;
	
	@Autowired
	ISMSAndEmailService iSMSAndEmailService;


	@Override
	@Transactional
	public void sendLoanInstallmentPaymentAlertMsg(QuartzSchedulerMaster runtimeBean, List<Object> parameterList) {
		Organisation org = new Organisation();
		org.setOrgid(runtimeBean.getOrgId().getOrgid());
		List<AccountLoanMasterDto> LoanAllData = accountLoanMasterService.findLoanMasterData(null,null, null, org.getOrgid(), null);
		List<Employee>  loanEmpList = accountLoanMasterRepository
				.getAllEmployeeAssociatedWithLoanMaster(org.getOrgid());
		
		if (CollectionUtils.isNotEmpty(loanEmpList)) {
			for (Employee e : loanEmpList) {
				if (CollectionUtils.isNotEmpty(LoanAllData)) {
					for (AccountLoanMasterDto loan : LoanAllData) {
						if (loan.getCreatedBy().equals(e.getEmpId())) {
							executeSmsAndEmail(loan, org, e);
						}
					}
				}
			}
		}
	}

	private void executeSmsAndEmail(AccountLoanMasterDto investdto, Organisation org, Employee emp) {
		Integer langId = 1;
		List<LookUp> lookUps = CommonMasterUtility.getLookUps(MainetConstants.LOAN_MASTER.LOAN_REPAYMENT_ALERT,
				org);
		List<Integer> days = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(lookUps)) {
			lookUps.forEach(lookUp -> {
				days.add(Integer.parseInt(lookUp.getLookUpCode()));
			});
		}
		if (CollectionUtils.isNotEmpty(investdto.getAccountLoanDetList())) {
			for(AccountLoanDetDto investdtoDet : investdto.getAccountLoanDetList()) {
			int daysBetweenDates =Utility.getDaysBetweenDates(new Date(), investdtoDet.getInstDueDate());
			
			if (days.contains(daysBetweenDates)) {
				// send MSG and mail
				final SMSAndEmailDTO smsdto = new SMSAndEmailDTO();
				smsdto.setMobnumber(emp.getEmpmobno());
				smsdto.setEmail(emp.getEmpemail());
				smsdto.setAppName(emp.getEmpname()+" "+emp.getEmplname());
				// Data added for message template
				smsdto.setAppNo(investdto.getLnNo());
				smsdto.setDueDt(Utility.dateToString(investdtoDet.getInstDueDate()));
				smsdto.setFrmDt(Utility.dateToString(investdto.getSanctionDate()));
				smsdto.setReferenceNo(String.valueOf(investdto.getLnNo()));
				smsdto.setUserId(investdto.getCreatedBy());
				smsdto.setOrgId(org.getOrgid());
				iSMSAndEmailService.sendEmailSMS(MainetConstants.DEPT_SHORT_NAME.ACCOUNT,
						MainetConstants.LOAN_MASTER.LOAN_URL,
						PrefixConstants.SMS_EMAIL_ALERT_TYPE.TASK_NOTIFICATION, smsdto, org, langId);
			}
		}
	  }
	}
}
