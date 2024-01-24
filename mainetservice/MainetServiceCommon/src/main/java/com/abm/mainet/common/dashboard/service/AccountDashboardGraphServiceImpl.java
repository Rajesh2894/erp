package com.abm.mainet.common.dashboard.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dashboard.dao.AccountDashboardGraphDAO;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountAmountTotalEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountClassifDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountCollectionEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountFundStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountRatioDashboardCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountReceiptsAndPaymentsEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.AccountTransactionCntEntity;

@Service
@Transactional(readOnly = true)
public class AccountDashboardGraphServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(AccountDashboardGraphServiceImpl.class);

	@Autowired
	AccountDashboardGraphDAO accountDashboardGraphDAO;

	@Autowired
	CitizenDashboardGraphServiceImpl citizenDashboardGraphServiceImpl;

	private static final String INCOME = "I";
	private static final String EXPENDITURE = "E";

	public List<AccountTransactionCntEntity> getYearWiseOnlineTransactionCount() {
		return accountDashboardGraphDAO.getYearWiseOnlineTransactionCount();
	}

	public List<AccountTransactionCntEntity> getYearWiseCashTransactionCount() {
		return accountDashboardGraphDAO.getYearWiseCashTransactionCount();
	}

	public List<AccountAmountTotalEntity> getYearWiseEstimatedBudget() {
		return accountDashboardGraphDAO.getYearWiseEstimatedBudget();
	}

	public List<AccountAmountTotalEntity> getYearWiseExpenditureAmt() {
		return accountDashboardGraphDAO.getYearWiseExpenditureAmt();
	}

	public List<AccountCollectionEntity> getDeptWiseCollectionForModes() {
		return accountDashboardGraphDAO.getDeptWiseCollectionForModes();
	}

	public List<AccountReceiptsAndPaymentsEntity> getfunctionWiseReceiptsAndPaymentCount() {
		return accountDashboardGraphDAO.getfunctionWiseReceiptsAndPaymentCount();
	}

	public List<AccountReceiptsAndPaymentsEntity> getZoneWiseReceiptsAndPaymentCount() {
		return accountDashboardGraphDAO.getZoneWiseReceiptsAndPaymentCount();
	}

	public List<AccountClassifDashboardCntEntity> getDashboardCountByClassificationType(String type) {
		if (type != null && type.equalsIgnoreCase(INCOME)) {
			return accountDashboardGraphDAO.getDashboardCountByIncomeClassificationType();
		}
		if (type != null && type.equalsIgnoreCase(EXPENDITURE)) {
			return accountDashboardGraphDAO.getDashboardCountByExpenseClassificationType();
		}
		return null;
	}

	public List<AccountRatioDashboardCntEntity> getDashboardCountByRatioType(String type) {
		if (type != null && type.equalsIgnoreCase(INCOME)) {
			return accountDashboardGraphDAO.getDashboardCountByIncomeRatioType();
		}
		if (type != null && type.equalsIgnoreCase(EXPENDITURE)) {
			return accountDashboardGraphDAO.getDashboardCountByExpenseRatioType();
		}
		return null;
	}

	public List<AccountFundStatusEntity> getDashboardCountByFundStatus() {
		return accountDashboardGraphDAO.getDashboardCountByFundStatus();
	}

	public List<AccountTransCntDayWiseEntity> getTransactionCountByDays(int noOfDays) {
		noOfDays = citizenDashboardGraphServiceImpl.getNoOfDays(noOfDays);
		return accountDashboardGraphDAO.getTransactionCountByDays(noOfDays);
	}
}
