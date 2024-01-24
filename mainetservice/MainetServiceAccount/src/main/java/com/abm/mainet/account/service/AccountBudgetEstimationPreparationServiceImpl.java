
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetEstimationPreparationDao;
import com.abm.mainet.account.dao.AccountBudgetProjectedExpenditureDao;
import com.abm.mainet.account.dao.AccountBudgetProjectedRevenueEntryDao;
import com.abm.mainet.account.domain.AccountBudgetEstimationPreparationEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetEstimationPreparationBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.mapper.AccountBudgetEstimationPreparationServiceMapper;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.BudgetEstimationPreparationRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetEstimationPreparationServiceImpl implements AccountBudgetEstimationPreparationService {

	@Resource
	private AccountBudgetEstimationPreparationServiceMapper accountBudgetEstimationPreparationServiceMapper;
	@Resource
	private BudgetEstimationPreparationRepository accountBudgetEstimationPreparationJpaRepository;
	@Resource
	BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;
	@Resource
	BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;
	@Resource
	private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
	@Resource
	private AccountBudgetProjectedRevenueEntryDao accountBudgetProjectedRevenueEntryDao;
	@Resource
	private AccountBudgetProjectedExpenditureDao accountBudgetProjectedExpenditureDao;
	@Resource
	private AccountBudgetEstimationPreparationDao accountBudgetEstimationPreparationDao;
	@Resource
	private BudgetHeadRepository budgetCodeRepository;
	@Resource
	private TbDepartmentJpaRepository departmentRepository;

	@Resource
	private BillEntryRepository billEntryRepository;

	@Resource
	private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;
	
	private static Logger LOGGER = Logger.getLogger(AccountBudgetEstimationPreparationServiceImpl.class);

	@Override
	@Transactional(readOnly = true)
	public Boolean isBudgetEstimationPreparationEntryExists(final Long faYearid, final Long prRevBudgetCode,
			final Long orgId, Long deptId) {

		return accountBudgetEstimationPreparationDao.isBudgetEstimationPreparationEntryExists(faYearid, prRevBudgetCode,
				orgId, deptId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountBudgetEstimationPreparationBean> findBudgetEstimationPreparationByFinId(final Long orgId) {
		final List<AccountBudgetEstimationPreparationEntity> entities = accountBudgetEstimationPreparationJpaRepository
				.findBudgetEstimationPreparationByFinId(orgId);
		final List<AccountBudgetEstimationPreparationBean> bean = new ArrayList<>();
		for (final AccountBudgetEstimationPreparationEntity accountBudgetOpenBalanceEntity : entities) {
			bean.add(accountBudgetEstimationPreparationServiceMapper
					.mapAccountBudgetEstimationPreparationEntityToAccountBudgetEstimationPreparationBean(
							accountBudgetOpenBalanceEntity));
		}
		return bean;
	}

	@Override
	@Transactional(readOnly = true)
	public List<AccountBudgetEstimationPreparationBean> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
			final Long dpDeptid, final Long prBudgetCodeid, final Long orgId) {
		final List<AccountBudgetEstimationPreparationEntity> entities = accountBudgetEstimationPreparationDao
				.findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid, orgId);
		final List<AccountBudgetEstimationPreparationBean> bean = new ArrayList<>();
		for (final AccountBudgetEstimationPreparationEntity accountBudgetOpenBalanceEntity : entities) {
			bean.add(accountBudgetEstimationPreparationServiceMapper
					.mapAccountBudgetEstimationPreparationEntityToAccountBudgetEstimationPreparationBean(
							accountBudgetOpenBalanceEntity));
		}
		return bean;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> findByBudgetIds(final Long faYearid, final Long fundId, final Long functionId,
			final Long cpdBugtypeId, final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {

		final List<Object[]> findById = accountBudgetEstimationPreparationDao.findByAllBudgetCodeId(faYearid, fundId,
				functionId, cpdBugtypeId, prBudgetCodeid, dpDeptid, orgId);
		final Map<String, String> map = new LinkedHashMap<>();
		if ((findById != null) && !findById.isEmpty()) {
			for (final Object[] objects : findById) {
				map.put(objects[0].toString(), objects[1].toString());
			}
		}
		return map;
	}

	@Override
	@Transactional
	public AccountBudgetEstimationPreparationBean saveBudgetEstimationPreparationFormData(
			final AccountBudgetEstimationPreparationBean budgetEstimationPreparation, final int langId,
			final Organisation org) throws Exception {
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, langId, org);
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, langId, org);
		if (budgetEstimationPreparation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
			estimationPreparationRevenueStoredData(budgetEstimationPreparation, langId);
		} else if (budgetEstimationPreparation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
			estimationPreparationExpenditureStoredData(budgetEstimationPreparation, langId);
		}
		String name = "temp";
		return budgetEstimationPreparation;
	}
	
	
	@Override
	@Transactional
	public AccountBudgetEstimationPreparationBean saveBudgetEstimationPreparationFormDataBulkEdit(
			final AccountBudgetEstimationPreparationBean budgetEstimationPreparation, final int langId,
			final Organisation org) throws Exception {
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, langId, org);
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, langId, org);
		if (budgetEstimationPreparation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
			estimationPreparationRevenueStoredDataBulkEdit(budgetEstimationPreparation, langId);
		} else if (budgetEstimationPreparation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
			estimationPreparationExpenditureStoredDataBulkEdit(budgetEstimationPreparation, langId);
		}
		String name = "temp";
		return budgetEstimationPreparation;
	}

	private void estimationPreparationRevenueStoredData(
			final AccountBudgetEstimationPreparationBean budgetEstimationPreparation, final int langId)
			throws ParseException {
		final AccountBudgetEstimationPreparationBean budEstPreparation = budgetEstimationPreparation;

		Date fromDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
				.getFinanceYearFrmDate(budgetEstimationPreparation.getFaYearid());
		for (Object[] objects : faYearFromDate) {
			fromDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(fromDate);
		int year1 = getYearFromDate(fromDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		Long currFinYrId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(new Date());
		if (newlyFaYearId == null) {
			throw new NullPointerException("Next Finanacial Year is Not Exist.");
		}
		budEstPreparation.setNextFaYearid(newlyFaYearId);
		boolean currFinYr=false;
		if (newlyFaYearId.equals(currFinYrId)) {
			currFinYr=true;
		}
		AccountBudgetEstimationPreparationEntity budgetEstimationPreparationEntity = null;
		final List<AccountBudgetProjectedRevenueEntryBean> bugProjRevList = budgetEstimationPreparation
				.getBugprojRevBeanList();
		if ((bugProjRevList != null) && !bugProjRevList.isEmpty()) {
			for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : bugProjRevList) {
				accountBudgetProjectedRevenueEntryBean
						.setPrRevBudgetCode(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
								.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
				budEstPreparation
						.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
								.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
				budEstPreparation
						.setEstimateForNextyear(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear());
				budEstPreparation.setApprBugStandCom(accountBudgetProjectedRevenueEntryBean.getApprBugStandCom());
				budEstPreparation
						.setFinalizedBugGenBody(accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody());
				// budEstPreparation.setFi04N1(accountBudgetProjectedRevenueEntryBean.getFi04N1());
				/*
				 * Long prProjectionid = accountBudgetProjectedRevenueEntryJpaRepository.
				 * getBudgetProjectedRevenuePrimaryKeyId(
				 * budgetEstimationPreparation.getFaYearid(),
				 * budgetEstimationPreparation.getDpDeptid(),
				 * budgetEstimationPreparation.getCpdBugsubtypeId(),
				 * budgetEstimationPreparation.getOrgid(),
				 * Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				 */
				Long prProjectionid = accountBudgetProjectedRevenueEntryJpaRepository
						.getBudgetProjectedRevenuePrimaryKeyId(budEstPreparation.getNextFaYearid(),
								budgetEstimationPreparation.getDpDeptid(), budgetEstimationPreparation.getOrgid(),
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				Long updateCurrentReviseProjectedId = accountBudgetProjectedRevenueEntryJpaRepository
						.getBudgetProjectedRevenuePrimaryKeyId(budEstPreparation.getFaYearid(),
								budgetEstimationPreparation.getDpDeptid(), budgetEstimationPreparation.getOrgid(),
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				// updateCurrentReviseProjectedId update current year
				if (updateCurrentReviseProjectedId != null && updateCurrentReviseProjectedId != 0L
						&& accountBudgetProjectedRevenueEntryBean.getRevisedEstamt() != null
						&& !accountBudgetProjectedRevenueEntryBean.getRevisedEstamt().isEmpty()) {
					// accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(budEstPreparation.getFaYearid(),
					// updateCurrentReviseProjectedId,
					// accountBudgetProjectedRevenueEntryBean.getRevisedEstamt(),
					// budgetEstimationPreparation.getOrgid());
					accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(
							budEstPreparation.getFaYearid(), updateCurrentReviseProjectedId,
							accountBudgetProjectedRevenueEntryBean.getRevisedEstamt(),
							accountBudgetProjectedRevenueEntryBean.getExpectedCurrentYear(),
							budgetEstimationPreparation.getOrgid());
				} else {

					// accountBudgetProjectedRevenueEntryEntity.setFaYearid(budEstPreparation.getFaYearid());

				}
				// work for edit mode
				if (prProjectionid != null && prProjectionid != 0L
						&& accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody() != null) {
					/*
					 * accountBudgetProjectedRevenueEntryJpaRepository.
					 * updateNextYearOriginalEstimateAmount(prProjectionid,
					 * String.valueOf(accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody(
					 * )), budgetEstimationPreparation.getOrgid());
					 */
					/*
					 * accountBudgetProjectedRevenueEntryJpaRepository.
					 * updateNextYearOriginalEstimateAmount(prProjectionid,
					 * accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear(),
					 * budgetEstimationPreparation.getOrgid());
					 */
					accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(
							newlyFaYearId, prProjectionid,
							accountBudgetProjectedRevenueEntryBean.getRevisedEstamt(),
							accountBudgetProjectedRevenueEntryBean.getExpectedCurrentYear(),
							budgetEstimationPreparation.getOrgid());
				} else {
					if(currFinYr) {
						accountBudgetProjectedRevenueEntryBean.setRevisedEstamt(null);
					}
					AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity = new AccountBudgetProjectedRevenueEntryEntity();
					if (accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode() != null
							&& !accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode().isEmpty()) {
						final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
						tbAcBudgetCodeMaster.setPrBudgetCodeid(
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode().toString()));
						accountBudgetProjectedRevenueEntryEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
					}
					accountBudgetProjectedRevenueEntryEntity.setFaYearid(budEstPreparation.getNextFaYearid());
					Department department = new Department();
					department.setDpDeptid(budgetEstimationPreparation.getDpDeptid());
					accountBudgetProjectedRevenueEntryEntity
							.setCpdBugsubtypeId(budgetEstimationPreparation.getCpdBugsubtypeId());
					accountBudgetProjectedRevenueEntryEntity.setTbDepartment(department);
					accountBudgetProjectedRevenueEntryEntity.setOrginalEstamt(new BigDecimal(0.00));
					accountBudgetProjectedRevenueEntryEntity.setPrProjected(new BigDecimal(0.00));
					accountBudgetProjectedRevenueEntryEntity.setRevisedEstamt(null);
					accountBudgetProjectedRevenueEntryEntity
							.setExpectedCurrentYear(accountBudgetProjectedRevenueEntryBean.getExpectedCurrentYear());
					accountBudgetProjectedRevenueEntryEntity
							.setRemark(accountBudgetProjectedRevenueEntryBean.getRemark());
					accountBudgetProjectedRevenueEntryEntity.setFieldId(budgetEstimationPreparation.getFieldId());
					accountBudgetProjectedRevenueEntryEntity.setFundId(budgetEstimationPreparation.getFundId());
					// accountBudgetProjectedRevenueEntryEntity.setNxtYrOe(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear().toString());
					accountBudgetProjectedRevenueEntryEntity
							.setOrginalEstamt(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear());
					accountBudgetProjectedRevenueEntryEntity.setOrgid(budgetEstimationPreparation.getOrgid());
					accountBudgetProjectedRevenueEntryEntity.setLangId(budgetEstimationPreparation.getLangId());
					accountBudgetProjectedRevenueEntryEntity.setLgIpMac(budgetEstimationPreparation.getLgIpMac());
					accountBudgetProjectedRevenueEntryEntity.setLmoddate(budgetEstimationPreparation.getCreatedDate());
					accountBudgetProjectedRevenueEntryEntity.setUserId(budgetEstimationPreparation.getCreatedBy());
					accountBudgetProjectedRevenueEntryJpaRepository.save(accountBudgetProjectedRevenueEntryEntity);
					// throw new FrameworkException("Updating next year original estimate amount in
					// budget projected revenue table
					// primary key is null or empty." + " financial year id : " +
					// budgetEstimationPreparation.getFaYearid() + "
					// department id : " + budgetEstimationPreparation.getDpDeptid() + " budget
					// subtype id : " +
					// budgetEstimationPreparation.getCpdBugsubtypeId() + " orgid : " +
					// budgetEstimationPreparation.getOrgid() + "
					// budget code id : " +
					// accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode());
				}
				
				if(accountBudgetProjectedRevenueEntryBean.getBudgetId()!=null){
					budEstPreparation.setBugestId(accountBudgetProjectedRevenueEntryBean.getBudgetId());
				}

				budgetEstimationPreparationEntity = new AccountBudgetEstimationPreparationEntity();
				accountBudgetEstimationPreparationServiceMapper
						.mapAccountBudgetEstimationPreparationBeanToAccountBudgetEstimationPreparationEntity(
								budEstPreparation, budgetEstimationPreparationEntity);
				accountBudgetEstimationPreparationJpaRepository.save(budgetEstimationPreparationEntity);
			}
		}
	}
	
	private void estimationPreparationRevenueStoredDataBulkEdit(
			final AccountBudgetEstimationPreparationBean budgetEstimationPreparation, final int langId)
			throws ParseException {
		final AccountBudgetEstimationPreparationBean budEstPreparation = budgetEstimationPreparation;

		Date fromDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
				.getFinanceYearFrmDate(budgetEstimationPreparation.getFaYearid());
		for (Object[] objects : faYearFromDate) {
			fromDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(fromDate);
		int year1 = getYearFromDate(fromDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		if (newlyFaYearId == null) {
			throw new NullPointerException("Next Finanacial Year is Not Exist.");
		}
		budEstPreparation.setNextFaYearid(newlyFaYearId);

		AccountBudgetEstimationPreparationEntity budgetEstimationPreparationEntity = null;
		final List<AccountBudgetProjectedRevenueEntryBean> bugProjRevList = budgetEstimationPreparation
				.getBugprojRevBeanList();
		if ((bugProjRevList != null) && !bugProjRevList.isEmpty()) {
			for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : bugProjRevList) {
				accountBudgetProjectedRevenueEntryBean
						.setPrRevBudgetCode(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
								.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
				budEstPreparation
						.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
								.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
				budEstPreparation
						.setEstimateForNextyear(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear());
				budEstPreparation.setApprBugStandCom(accountBudgetProjectedRevenueEntryBean.getApprBugStandCom());
				budEstPreparation
						.setFinalizedBugGenBody(accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody());
				// budEstPreparation.setFi04N1(accountBudgetProjectedRevenueEntryBean.getFi04N1());
				/*
				 * Long prProjectionid = accountBudgetProjectedRevenueEntryJpaRepository.
				 * getBudgetProjectedRevenuePrimaryKeyId(
				 * budgetEstimationPreparation.getFaYearid(),
				 * budgetEstimationPreparation.getDpDeptid(),
				 * budgetEstimationPreparation.getCpdBugsubtypeId(),
				 * budgetEstimationPreparation.getOrgid(),
				 * Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				 */
				Long prProjectionid = accountBudgetProjectedRevenueEntryJpaRepository
						.getBudgetProjectedRevenuePrimaryKeyId(budEstPreparation.getNextFaYearid(),
								budgetEstimationPreparation.getDpDeptid(), budgetEstimationPreparation.getOrgid(),
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				Long updateCurrentReviseProjectedId = accountBudgetProjectedRevenueEntryJpaRepository
						.getBudgetProjectedRevenuePrimaryKeyId(budEstPreparation.getFaYearid(),
								budgetEstimationPreparation.getDpDeptid(), budgetEstimationPreparation.getOrgid(),
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
				// updateCurrentReviseProjectedId update current year
				if (updateCurrentReviseProjectedId != null && updateCurrentReviseProjectedId != 0L
						&& accountBudgetProjectedRevenueEntryBean.getRevisedEstamt() != null
						&& !accountBudgetProjectedRevenueEntryBean.getRevisedEstamt().isEmpty()) {
					// accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(budEstPreparation.getFaYearid(),
					// updateCurrentReviseProjectedId,
					// accountBudgetProjectedRevenueEntryBean.getRevisedEstamt(),
					// budgetEstimationPreparation.getOrgid());
					accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(
							budEstPreparation.getFaYearid(), updateCurrentReviseProjectedId,
							accountBudgetProjectedRevenueEntryBean.getRevisedEstamt(),
							accountBudgetProjectedRevenueEntryBean.getExpectedCurrentYear(),
							budgetEstimationPreparation.getOrgid());
				} else {

					// accountBudgetProjectedRevenueEntryEntity.setFaYearid(budEstPreparation.getFaYearid());

				}
				// work for edit mode
				if (prProjectionid != null && prProjectionid != 0L
						&& accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody() != null) {
					/*
					 * accountBudgetProjectedRevenueEntryJpaRepository.
					 * updateNextYearOriginalEstimateAmount(prProjectionid,
					 * String.valueOf(accountBudgetProjectedRevenueEntryBean.getFinalizedBugGenBody(
					 * )), budgetEstimationPreparation.getOrgid());
					 */
					accountBudgetProjectedRevenueEntryJpaRepository.updateNextYearOriginalEstimateAmount(prProjectionid,
							accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear(),
							budgetEstimationPreparation.getOrgid());
				} else {
					AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity = new AccountBudgetProjectedRevenueEntryEntity();
					if (accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode() != null
							&& !accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode().isEmpty()) {
						final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
						tbAcBudgetCodeMaster.setPrBudgetCodeid(
								Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode().toString()));
						accountBudgetProjectedRevenueEntryEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
					}
					accountBudgetProjectedRevenueEntryEntity.setFaYearid(budEstPreparation.getNextFaYearid());
					Department department = new Department();
					department.setDpDeptid(budgetEstimationPreparation.getDpDeptid());
					accountBudgetProjectedRevenueEntryEntity
							.setCpdBugsubtypeId(budgetEstimationPreparation.getCpdBugtypeId());
					accountBudgetProjectedRevenueEntryEntity.setTbDepartment(department);
					accountBudgetProjectedRevenueEntryEntity.setOrginalEstamt(new BigDecimal(0.00));
					accountBudgetProjectedRevenueEntryEntity.setPrProjected(new BigDecimal(0.00));
					accountBudgetProjectedRevenueEntryEntity.setRevisedEstamt(null);
					accountBudgetProjectedRevenueEntryEntity
							.setExpectedCurrentYear(accountBudgetProjectedRevenueEntryBean.getExpectedCurrentYear());
					accountBudgetProjectedRevenueEntryEntity
							.setRemark(accountBudgetProjectedRevenueEntryBean.getRemark());
					accountBudgetProjectedRevenueEntryEntity.setFieldId(budgetEstimationPreparation.getFieldId());
					accountBudgetProjectedRevenueEntryEntity.setFundId(budgetEstimationPreparation.getFundId());
					// accountBudgetProjectedRevenueEntryEntity.setNxtYrOe(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear().toString());
					accountBudgetProjectedRevenueEntryEntity
							.setOrginalEstamt(accountBudgetProjectedRevenueEntryBean.getEstimateForNextyear());
					accountBudgetProjectedRevenueEntryEntity.setOrgid(budgetEstimationPreparation.getOrgid());
					accountBudgetProjectedRevenueEntryEntity.setLangId(budgetEstimationPreparation.getLangId());
					accountBudgetProjectedRevenueEntryEntity.setLgIpMac(budgetEstimationPreparation.getLgIpMac());
					accountBudgetProjectedRevenueEntryEntity.setLmoddate(budgetEstimationPreparation.getCreatedDate());
					accountBudgetProjectedRevenueEntryEntity.setUserId(budgetEstimationPreparation.getCreatedBy());
					accountBudgetProjectedRevenueEntryJpaRepository.save(accountBudgetProjectedRevenueEntryEntity);
					// throw new FrameworkException("Updating next year original estimate amount in
					// budget projected revenue table
					// primary key is null or empty." + " financial year id : " +
					// budgetEstimationPreparation.getFaYearid() + "
					// department id : " + budgetEstimationPreparation.getDpDeptid() + " budget
					// subtype id : " +
					// budgetEstimationPreparation.getCpdBugsubtypeId() + " orgid : " +
					// budgetEstimationPreparation.getOrgid() + "
					// budget code id : " +
					// accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode());
				}
				
				if(budEstPreparation.getBugestId()==null) {
					
					Long bugestId = accountBudgetEstimationPreparationJpaRepository.getBudgetEstimationPrimaryKeyId(budEstPreparation.getFaYearid(), 
							budEstPreparation.getDpDeptid(), budEstPreparation.getOrgid(), Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()));
					
					//Long getBudgetEstimationPrimaryKeyId(@Param("faYearid") Long faYearid, @Param("dpDeptid") Long dpDeptid,
				      //       @Param("orgid") Long orgid, @Param("budgetCode") Long prRevBudgetCode);
					budEstPreparation.setBugestId(bugestId);
				}

				budgetEstimationPreparationEntity = new AccountBudgetEstimationPreparationEntity();
				accountBudgetEstimationPreparationServiceMapper
						.mapAccountBudgetEstimationPreparationBeanToAccountBudgetEstimationPreparationEntity(
								budEstPreparation, budgetEstimationPreparationEntity);
				accountBudgetEstimationPreparationJpaRepository.save(budgetEstimationPreparationEntity);
			}
		}
	}
	
	

	public static int getYearFromDate(Date date) {
		int result = -1;
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			result = cal.get(Calendar.YEAR);
		}
		return result;
	}

	private void estimationPreparationExpenditureStoredData(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, final int LanguageId)
			throws ParseException {
		final AccountBudgetEstimationPreparationBean budgestEsmtPreparation = tbAcBudgetEstimationPreparation;

		Date fromDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
				.getFinanceYearFrmDate(tbAcBudgetEstimationPreparation.getFaYearid());
		for (Object[] objects : faYearFromDate) {
			fromDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(fromDate);
		int year1 = getYearFromDate(fromDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		Long currFinYrId=tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(new Date());
		if (newlyFaYearId == null) {
			throw new NullPointerException("Next Finanacial Year is Not Exist.");
		}
		budgestEsmtPreparation.setNextFaYearid(newlyFaYearId);
		boolean currFinYr=false;
		if (newlyFaYearId.equals(currFinYrId)) {
			currFinYr=true;
		}
		AccountBudgetEstimationPreparationEntity tbAcBudgetEstimationPreparationEntity = null;
		final List<AccountBudgetProjectedExpenditureBean> bugProjExpList = tbAcBudgetEstimationPreparation
				.getBugprojExpBeanList();
		for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : bugProjExpList) {
			if(StringUtils.isNotBlank(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode())) {
				
			accountBudgetProjectedExpenditureBean
					.setPrExpBudgetCode(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
							.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
			budgestEsmtPreparation
					.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
							.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
			
			budgestEsmtPreparation
					.setEstimateForNextyear(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
			budgestEsmtPreparation.setApprBugStandCom(accountBudgetProjectedExpenditureBean.getApprBugStandCom());
			budgestEsmtPreparation
					.setFinalizedBugGenBody(accountBudgetProjectedExpenditureBean.getFinalizedBugGenBody());

			/*
			 * Long prExpenditureid = accountBudgetProjectedExpenditureJpaRepository.
			 * getBudgetProjectedExpenditurePrimaryKeyId(
			 * tbAcBudgetEstimationPreparation.getFaYearid(),
			 * tbAcBudgetEstimationPreparation.getDpDeptid(),
			 * tbAcBudgetEstimationPreparation.getCpdBugsubtypeId(),
			 * tbAcBudgetEstimationPreparation.getOrgid(),
			 * Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));
			 */
			Long prExpenditureid = accountBudgetProjectedExpenditureJpaRepository
					.getBudgetProjectedExpenditurePrimaryKeyId(budgestEsmtPreparation.getNextFaYearid(),
							tbAcBudgetEstimationPreparation.getDpDeptid(), tbAcBudgetEstimationPreparation.getOrgid(),
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));

			Long updateReviseExpeditureId = accountBudgetProjectedExpenditureJpaRepository
					.getBudgetProjectedExpenditurePrimaryKeyId(budgestEsmtPreparation.getFaYearid(),
							tbAcBudgetEstimationPreparation.getDpDeptid(), tbAcBudgetEstimationPreparation.getOrgid(),
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));

			if (updateReviseExpeditureId != null && updateReviseExpeditureId != 0L
					&& accountBudgetProjectedExpenditureBean.getRevisedEstamt() != null
					&& !(accountBudgetProjectedExpenditureBean.getRevisedEstamt().isEmpty())) {

				accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(
						budgestEsmtPreparation.getFaYearid(), updateReviseExpeditureId,
						accountBudgetProjectedExpenditureBean.getRevisedEstamt(),
						accountBudgetProjectedExpenditureBean.getExpectedCurrentYearO(),
						tbAcBudgetEstimationPreparation.getOrgid());

			}

			// if is used to update else for save
			if (prExpenditureid != null && prExpenditureid != 0L
					&& accountBudgetProjectedExpenditureBean.getEstimateForNextyear() != null) {
				accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(
						newlyFaYearId, prExpenditureid,
						accountBudgetProjectedExpenditureBean.getRevisedEstamt(),
						accountBudgetProjectedExpenditureBean.getExpectedCurrentYearO(),
						tbAcBudgetEstimationPreparation.getOrgid());
			} else {
				if(currFinYr) {
					accountBudgetProjectedExpenditureBean.setRevisedEstamt(null);
					
				}
				AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = new AccountBudgetProjectedExpenditureEntity();
				if (accountBudgetProjectedExpenditureBean.getPrExpBudgetCode() != null
						&& !accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().isEmpty()) {
					final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
					tbAcBudgetCodeMaster.setPrBudgetCodeid(
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().toString()));
					accountBudgetProjectedExpenditureEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
				}
				accountBudgetProjectedExpenditureEntity.setFaYearid(budgestEsmtPreparation.getNextFaYearid());
				Department department = new Department();
				department.setDpDeptid(tbAcBudgetEstimationPreparation.getDpDeptid());
				accountBudgetProjectedExpenditureEntity
						.setCpdBugsubtypeId(tbAcBudgetEstimationPreparation.getCpdBugsubtypeId());
				accountBudgetProjectedExpenditureEntity.setTbDepartment(department);
				accountBudgetProjectedExpenditureEntity.setOrginalEstamt(new BigDecimal(0.00));
				accountBudgetProjectedExpenditureEntity.setPrBalanceAmt(new BigDecimal(0.00));
				accountBudgetProjectedExpenditureEntity.setRevisedEstamt(accountBudgetProjectedExpenditureBean.getRevisedEstamt());
				accountBudgetProjectedExpenditureEntity
						.setExpectedCurrentYearO(accountBudgetProjectedExpenditureBean.getExpectedCurrentYearO());
				accountBudgetProjectedExpenditureEntity.setRemark(accountBudgetProjectedExpenditureBean.getRemark());
				// accountBudgetProjectedExpenditureEntity.setNxtYrOe(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
				accountBudgetProjectedExpenditureEntity
						.setOrginalEstamt(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
				accountBudgetProjectedExpenditureEntity.setFieldId(tbAcBudgetEstimationPreparation.getFieldId());
				accountBudgetProjectedExpenditureEntity.setFundId(tbAcBudgetEstimationPreparation.getFundId());
				accountBudgetProjectedExpenditureEntity.setOrgid(tbAcBudgetEstimationPreparation.getOrgid());
				accountBudgetProjectedExpenditureEntity.setLangId(tbAcBudgetEstimationPreparation.getLangId());
				accountBudgetProjectedExpenditureEntity.setLgIpMac(tbAcBudgetEstimationPreparation.getLgIpMac());
				accountBudgetProjectedExpenditureEntity.setLmoddate(tbAcBudgetEstimationPreparation.getCreatedDate());
				accountBudgetProjectedExpenditureEntity.setUserId(tbAcBudgetEstimationPreparation.getCreatedBy());
				accountBudgetProjectedExpenditureJpaRepository.save(accountBudgetProjectedExpenditureEntity);
				// throw new FrameworkException("Updating next year original estimate amount in
				// budget projected expenditure table
				// primary key is null or empty." + " financial year id : " +
				// tbAcBudgetEstimationPreparation.getFaYearid() + "
				// department id : " + tbAcBudgetEstimationPreparation.getDpDeptid() + " budget
				// subtype id : " +
				// tbAcBudgetEstimationPreparation.getCpdBugsubtypeId() + " orgid : " +
				// tbAcBudgetEstimationPreparation.getOrgid()
				// + " budget code id : " +
				// accountBudgetProjectedExpenditureBean.getPrExpBudgetCode());
			}
			
			if(accountBudgetProjectedExpenditureBean.getBudgetId()!=null){
				budgestEsmtPreparation.setBugestId(accountBudgetProjectedExpenditureBean.getBudgetId());
			}

			tbAcBudgetEstimationPreparationEntity = new AccountBudgetEstimationPreparationEntity();
			accountBudgetEstimationPreparationServiceMapper
					.mapAccountBudgetEstimationPreparationBeanToAccountBudgetEstimationPreparationEntity(
							budgestEsmtPreparation, tbAcBudgetEstimationPreparationEntity);
			accountBudgetEstimationPreparationJpaRepository.save(tbAcBudgetEstimationPreparationEntity);
		}
		}
	}
	
	private void estimationPreparationExpenditureStoredDataBulkEdit(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, final int LanguageId)
			throws ParseException {
		final AccountBudgetEstimationPreparationBean budgestEsmtPreparation = tbAcBudgetEstimationPreparation;

		Date fromDate = null;
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
				.getFinanceYearFrmDate(tbAcBudgetEstimationPreparation.getFaYearid());
		for (Object[] objects : faYearFromDate) {
			fromDate = (Date) objects[0];
		}
		String myDate = Utility.dateToString(fromDate);
		int year1 = getYearFromDate(fromDate);
		String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
		Long newlyFaYearId = tbFinancialyearJpaRepository.getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
		if (newlyFaYearId == null) {
			throw new NullPointerException("Next Finanacial Year is Not Exist.");
		}
		budgestEsmtPreparation.setNextFaYearid(newlyFaYearId);

		AccountBudgetEstimationPreparationEntity tbAcBudgetEstimationPreparationEntity = null;
		final List<AccountBudgetProjectedExpenditureBean> bugProjExpList = tbAcBudgetEstimationPreparation
				.getBugprojExpBeanList();
		for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : bugProjExpList) {
			accountBudgetProjectedExpenditureBean
					.setPrExpBudgetCode(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
							.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK));
			budgestEsmtPreparation
					.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
							.replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
			budgestEsmtPreparation
					.setEstimateForNextyear(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
			budgestEsmtPreparation.setApprBugStandCom(accountBudgetProjectedExpenditureBean.getApprBugStandCom());
			budgestEsmtPreparation
					.setFinalizedBugGenBody(accountBudgetProjectedExpenditureBean.getFinalizedBugGenBody());

			/*
			 * Long prExpenditureid = accountBudgetProjectedExpenditureJpaRepository.
			 * getBudgetProjectedExpenditurePrimaryKeyId(
			 * tbAcBudgetEstimationPreparation.getFaYearid(),
			 * tbAcBudgetEstimationPreparation.getDpDeptid(),
			 * tbAcBudgetEstimationPreparation.getCpdBugsubtypeId(),
			 * tbAcBudgetEstimationPreparation.getOrgid(),
			 * Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));
			 */
			Long prExpenditureid = accountBudgetProjectedExpenditureJpaRepository
					.getBudgetProjectedExpenditurePrimaryKeyId(budgestEsmtPreparation.getNextFaYearid(),
							tbAcBudgetEstimationPreparation.getDpDeptid(), tbAcBudgetEstimationPreparation.getOrgid(),
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));

			Long updateReviseExpeditureId = accountBudgetProjectedExpenditureJpaRepository
					.getBudgetProjectedExpenditurePrimaryKeyId(budgestEsmtPreparation.getFaYearid(),
							tbAcBudgetEstimationPreparation.getDpDeptid(), tbAcBudgetEstimationPreparation.getOrgid(),
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));

			if (updateReviseExpeditureId != null && updateReviseExpeditureId != 0L
					&& accountBudgetProjectedExpenditureBean.getRevisedEstamt() != null
					&& !(accountBudgetProjectedExpenditureBean.getRevisedEstamt().isEmpty())) {

				accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(
						budgestEsmtPreparation.getFaYearid(), updateReviseExpeditureId,
						accountBudgetProjectedExpenditureBean.getRevisedEstamt(),
						accountBudgetProjectedExpenditureBean.getExpectedCurrentYearO(),
						tbAcBudgetEstimationPreparation.getOrgid());

			}

			// if is used to update else for save
			if (prExpenditureid != null && prExpenditureid != 0L
					&& accountBudgetProjectedExpenditureBean.getEstimateForNextyear() != null) {
				accountBudgetProjectedExpenditureJpaRepository.updateNextYearOriginalEstimateAmount(prExpenditureid,
						accountBudgetProjectedExpenditureBean.getEstimateForNextyear(),
						tbAcBudgetEstimationPreparation.getOrgid());
			} else {
				AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = new AccountBudgetProjectedExpenditureEntity();
				if (accountBudgetProjectedExpenditureBean.getPrExpBudgetCode() != null
						&& !accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().isEmpty()) {
					final AccountBudgetCodeEntity tbAcBudgetCodeMaster = new AccountBudgetCodeEntity();
					tbAcBudgetCodeMaster.setPrBudgetCodeid(
							Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode().toString()));
					accountBudgetProjectedExpenditureEntity.setTbAcBudgetCodeMaster(tbAcBudgetCodeMaster);
				}
				accountBudgetProjectedExpenditureEntity.setFaYearid(budgestEsmtPreparation.getNextFaYearid());
				Department department = new Department();
				department.setDpDeptid(tbAcBudgetEstimationPreparation.getDpDeptid());
				accountBudgetProjectedExpenditureEntity
						.setCpdBugsubtypeId(tbAcBudgetEstimationPreparation.getCpdBugtypeId());
				accountBudgetProjectedExpenditureEntity.setTbDepartment(department);
				accountBudgetProjectedExpenditureEntity.setOrginalEstamt(new BigDecimal(0.00));
				accountBudgetProjectedExpenditureEntity.setPrBalanceAmt(new BigDecimal(0.00));
				accountBudgetProjectedExpenditureEntity.setRevisedEstamt(null);
				accountBudgetProjectedExpenditureEntity
						.setExpectedCurrentYearO(accountBudgetProjectedExpenditureBean.getExpectedCurrentYearO());
				accountBudgetProjectedExpenditureEntity.setRemark(accountBudgetProjectedExpenditureBean.getRemark());
				// accountBudgetProjectedExpenditureEntity.setNxtYrOe(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
				accountBudgetProjectedExpenditureEntity
						.setOrginalEstamt(accountBudgetProjectedExpenditureBean.getEstimateForNextyear());
				accountBudgetProjectedExpenditureEntity.setFieldId(tbAcBudgetEstimationPreparation.getFieldId());
				accountBudgetProjectedExpenditureEntity.setFundId(tbAcBudgetEstimationPreparation.getFundId());
				accountBudgetProjectedExpenditureEntity.setOrgid(tbAcBudgetEstimationPreparation.getOrgid());
				accountBudgetProjectedExpenditureEntity.setLangId(tbAcBudgetEstimationPreparation.getLangId());
				accountBudgetProjectedExpenditureEntity.setLgIpMac(tbAcBudgetEstimationPreparation.getLgIpMac());
				accountBudgetProjectedExpenditureEntity.setLmoddate(tbAcBudgetEstimationPreparation.getCreatedDate());
				accountBudgetProjectedExpenditureEntity.setUserId(tbAcBudgetEstimationPreparation.getCreatedBy());
				accountBudgetProjectedExpenditureJpaRepository.save(accountBudgetProjectedExpenditureEntity);
				// throw new FrameworkException("Updating next year original estimate amount in
				// budget projected expenditure table
				// primary key is null or empty." + " financial year id : " +
				// tbAcBudgetEstimationPreparation.getFaYearid() + "
				// department id : " + tbAcBudgetEstimationPreparation.getDpDeptid() + " budget
				// subtype id : " +
				// tbAcBudgetEstimationPreparation.getCpdBugsubtypeId() + " orgid : " +
				// tbAcBudgetEstimationPreparation.getOrgid()
				// + " budget code id : " +
				// accountBudgetProjectedExpenditureBean.getPrExpBudgetCode());
			}
			
			if(budgestEsmtPreparation.getBugestId()==null) {
				
				Long bugestId = accountBudgetEstimationPreparationJpaRepository.getBudgetEstimationPrimaryKeyId(budgestEsmtPreparation.getFaYearid(), 
						budgestEsmtPreparation.getDpDeptid(), budgestEsmtPreparation.getOrgid(), Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()));
				
				//Long getBudgetEstimationPrimaryKeyId(@Param("faYearid") Long faYearid, @Param("dpDeptid") Long dpDeptid,
			      //       @Param("orgid") Long orgid, @Param("budgetCode") Long prRevBudgetCode);
				budgestEsmtPreparation.setBugestId(bugestId);
			}

			tbAcBudgetEstimationPreparationEntity = new AccountBudgetEstimationPreparationEntity();
			accountBudgetEstimationPreparationServiceMapper
					.mapAccountBudgetEstimationPreparationBeanToAccountBudgetEstimationPreparationEntity(
							budgestEsmtPreparation, tbAcBudgetEstimationPreparationEntity);
			accountBudgetEstimationPreparationJpaRepository.save(tbAcBudgetEstimationPreparationEntity);
		}
	}

	@Override
	@Transactional
	public AccountBudgetEstimationPreparationBean getDetailsUsingEstimationPreparationId(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, final int languageId,
			final Organisation org, final Long orgId) {
		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, languageId, org);
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, languageId, org);
		final Long BugestId = tbAcBudgetEstimationPreparation.getBugestId();
		final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity = accountBudgetEstimationPreparationJpaRepository
				.findOne(BugestId);

		if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
			gettingEstimationPreparationRevenueData(tbAcBudgetEstimationPreparation,
					accountBudgetEstimationPreparationEntity, orgId);
		} else if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
			gettingEstimationPreparationExpenditureData(tbAcBudgetEstimationPreparation,
					accountBudgetEstimationPreparationEntity, orgId);
		}
		return tbAcBudgetEstimationPreparation;
	}

	@Override
	@Transactional
	public AccountBudgetEstimationPreparationBean getDetailsUsingEstimationPreparationIdBulkEdit(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation, final int languageId,
			final Organisation org, final Long orgId, String budgetCodeIds) {

		String[] budgetCodeIdsArr = budgetCodeIds.split(",");

		final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
				PrefixConstants.PREFIX, languageId, org);
		final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
				PrefixConstants.PREFIX, languageId, org);
		// final Long BugestId = tbAcBudgetEstimationPreparation.getBugestId();

		final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();
		final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();

		for (String budgetCodeId : budgetCodeIdsArr) {

			Long BugestId = Long.valueOf(budgetCodeId);

			final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity = accountBudgetEstimationPreparationJpaRepository
					.findOne(BugestId);

			if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
				gettingEstimationPreparationRevenueDataBulkEdit(tbAcBudgetRevenueList, tbAcBudgetEstimationPreparation,
						accountBudgetEstimationPreparationEntity, orgId, BugestId);
			} else if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
				gettingEstimationPreparationExpenditureDataBulkEdit(tbAcBudgetExpList, tbAcBudgetEstimationPreparation,
						accountBudgetEstimationPreparationEntity, orgId, BugestId);
			}
		}

		tbAcBudgetEstimationPreparation.setBugprojRevBeanList(tbAcBudgetRevenueList);
		tbAcBudgetEstimationPreparation.setBugprojExpBeanList(tbAcBudgetExpList);

		return tbAcBudgetEstimationPreparation;
	}

	/**
	 * @param tbAcBudgetEstimationPreparation
	 * @param accountBudgetEstimationPreparationEntity
	 * @param accountBudgetReappropriationTrMasterEntity
	 */
	private void gettingEstimationPreparationExpenditureData(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
			final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity, final Long orgId) {
		tbAcBudgetEstimationPreparation.setFaYearid(accountBudgetEstimationPreparationEntity.getFaYearid());
		if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId() != null) {
			tbAcBudgetEstimationPreparation.setCpdBugtypeId(accountBudgetEstimationPreparationEntity.getCpdBugtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
					accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
			tbAcBudgetEstimationPreparation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(
					PrefixConstants.PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId() != null) {
			tbAcBudgetEstimationPreparation
					.setCpdBugsubtypeId(accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(
					PrefixConstants.BUG_SUB_PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getDpDeptid() != null) {
			tbAcBudgetEstimationPreparation.setDpDeptid(accountBudgetEstimationPreparationEntity.getDpDeptid());
			tbAcBudgetEstimationPreparation.setDepartmentDesc(departmentRepository
					.fetchDepartmentDescById(accountBudgetEstimationPreparationEntity.getDpDeptid()));
		}
		if (accountBudgetEstimationPreparationEntity.getNextFaYearid() != null) {
			tbAcBudgetEstimationPreparation.setNextFaYearid(accountBudgetEstimationPreparationEntity.getNextFaYearid());
		}
		if (accountBudgetEstimationPreparationEntity.getFieldId() != null) {
			tbAcBudgetEstimationPreparation.setFieldId(accountBudgetEstimationPreparationEntity.getFieldId());
		}

		if (accountBudgetEstimationPreparationEntity.getFundId() != null) {
			tbAcBudgetEstimationPreparation.setFundId(accountBudgetEstimationPreparationEntity.getFundId());
		}

		if (accountBudgetEstimationPreparationEntity.getRemark() != null) {
			tbAcBudgetEstimationPreparation.setRemark(accountBudgetEstimationPreparationEntity.getRemark());
		}

		final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
		final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();

		if ((accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster() != null)
				&& (accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
			bean.setPrExpBudgetCode(
					accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
		}
		final Long prBudgetCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster()
				.getPrBudgetCodeid();
		final String acBudgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
		bean.setPrExpBudgetCodeDup(acBudgetCode);
		final Long faYearid = accountBudgetEstimationPreparationEntity.getFaYearid();
		final Long budgCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
		final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureJpaRepository
				.findByExpOrgAmount(faYearid, budgCodeid, orgId);

		final List<Object[]> orgEstimateAmtDetailsNextYear = accountBudgetProjectedExpenditureJpaRepository
				.findByExpOrgAmount(tbAcBudgetEstimationPreparation.getNextFaYearid(), budgCodeid, orgId);

		List<BigDecimal> LastYearData = findBudgetEstimationPreparationLastYearCountAmountDataExp(faYearid, budgCodeid,
				orgId);

		for (final Object[] objects : orgEstimateAmtDetailsNextYear) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {
				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				} else {
					// bean.setRemark("");
				}
			}
		}

		for (final Object[] objects : orgEstimateAmtDetails) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {

				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setRevisedEstamt(revisedEsmtAmount.toString());
				}

				if (objects[1] != null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					bean.setOrginalEstamt(originalEstAmount.toString());
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYearO = new BigDecimal(objects[5].toString());
					expectedCurrentYearO = expectedCurrentYearO.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYearO(expectedCurrentYearO);
				}

				if (objects[5] != null && objects[0] != null) {
					BigDecimal actualsCurrentYearO;

					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);

					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);

					actualsCurrentYearO = revisedEsmtAmount.subtract(expectedCurrentYear);

					if (revisedEsmtAmount.compareTo(expectedCurrentYear) != 0) {
						bean.setActualsCurrentYearO(actualsCurrentYearO);
					} else {
						// do nothing
					}

				}

			}
		}

		if (CollectionUtils.isNotEmpty(LastYearData)) {
			int count = 0;
			for (BigDecimal amt : LastYearData) {
				if (count == 0)
					bean.setLastYearCountDupThree(amt);
				if (count == 1)
					bean.setLastYearCountDupTwo(amt);
				if (count == 2)
					bean.setLastYearCountDupOne(amt);
				count++;
			}
		}

		if (accountBudgetEstimationPreparationEntity.getEstimateForNextyear() != null) {
			BigDecimal estimateForNextyear = accountBudgetEstimationPreparationEntity.getEstimateForNextyear();
			estimateForNextyear = estimateForNextyear.setScale(2, RoundingMode.CEILING);
			bean.setEstimateForNextyear(estimateForNextyear);
		}
		if (accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody() != null) {
			BigDecimal finalizedBugGenBody = accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody();
			finalizedBugGenBody = finalizedBugGenBody.setScale(2, RoundingMode.CEILING);
			bean.setFinalizedBugGenBody(finalizedBugGenBody);
		}
		if (accountBudgetEstimationPreparationEntity.getApprBugStandCom() != null) {
			BigDecimal apprBugStandCom = accountBudgetEstimationPreparationEntity.getApprBugStandCom();
			apprBugStandCom = apprBugStandCom.setScale(2, RoundingMode.CEILING);
			bean.setApprBugStandCom(apprBugStandCom);
		}
		tbAcBudgetExpList.add(bean);
		tbAcBudgetEstimationPreparation.setBugprojExpBeanList(tbAcBudgetExpList);
	}

	/**
	 * @param tbAcBudgetEstimationPreparation
	 * @param accountBudgetEstimationPreparationEntity
	 * @param accountBudgetReappropriationTrMasterEntity
	 * @throws Exception
	 */
	private void gettingEstimationPreparationRevenueData(
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
			final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity, final Long orgId) {

		tbAcBudgetEstimationPreparation.setFaYearid(accountBudgetEstimationPreparationEntity.getFaYearid());
		if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId() != null) {
			tbAcBudgetEstimationPreparation.setCpdBugtypeId(accountBudgetEstimationPreparationEntity.getCpdBugtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
					accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
			tbAcBudgetEstimationPreparation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(
					PrefixConstants.PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId() != null) {
			tbAcBudgetEstimationPreparation
					.setCpdBugsubtypeId(accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(
					PrefixConstants.BUG_SUB_PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getDpDeptid() != null) {
			tbAcBudgetEstimationPreparation.setDpDeptid(accountBudgetEstimationPreparationEntity.getDpDeptid());
			tbAcBudgetEstimationPreparation.setDepartmentDesc(departmentRepository
					.fetchDepartmentDescById(accountBudgetEstimationPreparationEntity.getDpDeptid()));
		}
		if (accountBudgetEstimationPreparationEntity.getNextFaYearid() != null) {
			tbAcBudgetEstimationPreparation.setNextFaYearid(accountBudgetEstimationPreparationEntity.getNextFaYearid());
		}
		if (accountBudgetEstimationPreparationEntity.getFieldId() != null) {
			tbAcBudgetEstimationPreparation.setFieldId(accountBudgetEstimationPreparationEntity.getFieldId());
		}
		if (accountBudgetEstimationPreparationEntity.getFundId() != null) {
			tbAcBudgetEstimationPreparation.setFundId(accountBudgetEstimationPreparationEntity.getFundId());
		}
		if (accountBudgetEstimationPreparationEntity.getRemark() != null) {
			tbAcBudgetEstimationPreparation.setRemark(accountBudgetEstimationPreparationEntity.getRemark());
		}
		final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
		final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();
		if ((accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster() != null)
				&& (accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
			bean.setPrRevBudgetCode(
					accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
		}
		final Long prBudgetCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster()
				.getPrBudgetCodeid();
		final String acBudgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
		bean.setPrRevBudgetCodeDup(acBudgetCode);
		final Long faYearid = accountBudgetEstimationPreparationEntity.getFaYearid();
		final Long budgCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
		final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryJpaRepository
				.findByRenueOrgAmount(faYearid, budgCodeid, orgId);

		final List<Object[]> orgEstimateAmtDetailsNextYear = accountBudgetProjectedRevenueEntryJpaRepository
				.findByRenueOrgAmount(tbAcBudgetEstimationPreparation.getNextFaYearid(), budgCodeid, orgId);

		List<BigDecimal> LastYearData = findBudgetEstimationPreparationLastYearCountAmountData(faYearid, budgCodeid,
				orgId);

		for (final Object[] objects : orgEstimateAmtDetailsNextYear) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {
				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				} else {
					// bean.setRemark("");
				}
			}
		}

		for (final Object[] objects : orgEstimateAmtDetails) {
			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {

				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setRevisedEstamt(revisedEsmtAmount.toString());
				}

				if (objects[1] != null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					bean.setOrginalEstamt(originalEstAmount.toString());
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYear(expectedCurrentYear);
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYear(expectedCurrentYear);
				}

				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				}

				if (objects[5] != null && objects[0] != null) {
					BigDecimal actualsCurrentYear;

					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);

					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);

					actualsCurrentYear = revisedEsmtAmount.subtract(expectedCurrentYear);

					if (revisedEsmtAmount.compareTo(expectedCurrentYear) != 0) {
						bean.setActualsCurrentYear(actualsCurrentYear);
					} else {
						// do nothing
					}
				}

			}
		}

		if (CollectionUtils.isNotEmpty(LastYearData)) {
			int count = 0;
			for (BigDecimal amt : LastYearData) {
				if (count == 0)
					bean.setLastYearCountDupThree(amt);
				if (count == 1)
					bean.setLastYearCountDupTwo(amt);
				if (count == 2)
					bean.setLastYearCountDupOne(amt);
				count++;
			}
		}
		if (accountBudgetEstimationPreparationEntity.getEstimateForNextyear() != null) {
			BigDecimal estimateForNextyear = accountBudgetEstimationPreparationEntity.getEstimateForNextyear();
			estimateForNextyear = estimateForNextyear.setScale(2, RoundingMode.CEILING);
			bean.setEstimateForNextyear(estimateForNextyear);
		}
		if (accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody() != null) {
			BigDecimal finalizedBugGenBody = accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody();
			finalizedBugGenBody = finalizedBugGenBody.setScale(2, RoundingMode.CEILING);
			bean.setFinalizedBugGenBody(finalizedBugGenBody);
		}
		if (accountBudgetEstimationPreparationEntity.getApprBugStandCom() != null) {
			BigDecimal apprBugStandCom = accountBudgetEstimationPreparationEntity.getApprBugStandCom();
			apprBugStandCom = apprBugStandCom.setScale(2, RoundingMode.CEILING);
			bean.setApprBugStandCom(apprBugStandCom);
		}
		tbAcBudgetRevenueList.add(bean);
		tbAcBudgetEstimationPreparation.setBugprojRevBeanList(tbAcBudgetRevenueList);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> findBudgetEstimationPreparationLastYearCountAmountData(final Long faYearid,
			final Long budgCodeid, final Long orgId) {

		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
		final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
		final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String fromdateYear;
		final List<BigDecimal> objectList = new ArrayList<>();
		if ((faYearFromDate != null) && !faYearFromDate.isEmpty()) {
			Long lastFromDate = null;
			Date dateFromParsing = null;
			Long faYearIds = null;
			final String April = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH;
			String fromDate = MainetConstants.CommonConstants.BLANK;
			for (final Object[] obj : faYearFromDate) {
				fromdateYear = yearFormater.format(obj[0]);
				for (int count = 1; 3 >= count; count++) {
					lastFromDate = Long.parseLong(fromdateYear) - count;
					fromDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY
							+ MainetConstants.HYPHEN + April + MainetConstants.HYPHEN + lastFromDate;
					try {
						dateFromParsing = dateFormater.parse(fromDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
					faYearIds = tbFinancialyearJpaRepository.getFinanceYearIds(dateFromParsing);
					if (faYearIds != null) {
						List<Object[]> orgEstmtList = accountBudgetProjectedRevenueEntryDao
								.getOrgEsmtAmtsLFYear(faYearIds, budgCodeid, orgId);
						BigDecimal orgEstimationAmmounts = BigDecimal.ZERO;
						if (orgEstmtList != null) {
							for (Object[] objects : orgEstmtList) {
								if (objects[1] != null) {
									orgEstimationAmmounts = new BigDecimal(objects[1].toString());
									orgEstimationAmmounts = orgEstimationAmmounts.setScale(2, RoundingMode.CEILING);
									objectList.add(orgEstimationAmmounts);
								} else if (objects[0] != null) {
									orgEstimationAmmounts = new BigDecimal(objects[0].toString());
									orgEstimationAmmounts = orgEstimationAmmounts.setScale(2, RoundingMode.CEILING);
									objectList.add(orgEstimationAmmounts);
								} else {
									objectList.add(new BigDecimal(0));
								}
							}
						}
					}
				}
			}
		}
		return objectList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> findBudgetEstimationPreparationLastYearCountAmountDataExp(final Long faYearid,
			final Long budgCodeid, final Long orgId) {

		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
		final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
		final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String fromdateYear;
		final List<BigDecimal> objectList = new ArrayList<>();
		if ((faYearFromDate != null) && !faYearFromDate.isEmpty()) {
			Long lastFromDate = null;
			Date dateFromParsing = null;
			Long faYearIds = null;
			final String April = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH;
			String fromDate = MainetConstants.CommonConstants.BLANK;
			for (final Object[] obj : faYearFromDate) {
				fromdateYear = yearFormater.format(obj[0]);
				for (int count = 1; 3 >= count; count++) {
					lastFromDate = Long.parseLong(fromdateYear) - count;
					fromDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY
							+ MainetConstants.HYPHEN + April + MainetConstants.HYPHEN + lastFromDate;
					try {
						dateFromParsing = dateFormater.parse(fromDate);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
					faYearIds = tbFinancialyearJpaRepository.getFinanceYearIds(dateFromParsing);
					if (faYearIds != null) {
						List<Object[]> orgEstmtList = accountBudgetProjectedExpenditureDao
								.getOrgEsmtAmtsLFYear(faYearIds, Long.valueOf(budgCodeid), orgId);
						BigDecimal orgEstimationAmmounts = BigDecimal.ZERO;
						if (orgEstmtList != null) {
							for (Object[] objects : orgEstmtList) {
								if (objects[1] != null) {
									orgEstimationAmmounts = new BigDecimal(objects[1].toString());
									orgEstimationAmmounts = orgEstimationAmmounts.setScale(2, RoundingMode.CEILING);
									objectList.add(orgEstimationAmmounts);
								} else if (objects[0] != null) {
									orgEstimationAmmounts = new BigDecimal(objects[0].toString());
									orgEstimationAmmounts = orgEstimationAmmounts.setScale(2, RoundingMode.CEILING);
									objectList.add(orgEstimationAmmounts);
								} else {
									objectList.add(new BigDecimal(0));
								}
							}
						}
					}
				}
			}
		}
		return objectList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigDecimal> findBudgetEstimationPreparationLastYearExpActualCountAmountDataExp(Long faYearid,
			Long budgCodeid, Long orgId, Long deptId) {

		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
		final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
		final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String fromdateYear;
		String todateYear;
		final List<BigDecimal> objectList = new ArrayList<>();

		Date fromDate = null;
		Date toDate = null;
		for (Object[] objects : faYearFromDate) {
			if (objects[0] != null && objects[1] != null) {
				fromDate = (Date) objects[0];
				toDate = (Date) objects[1]; // unused
			}
		}
		// we need to fetch BudgetEstimation of 01.04 to 31.12 so we used below logic
		toDate.setDate(31);
		toDate.setMonth(11);
		toDate.setYear(fromDate.getYear());
			
		final BigDecimal collectedAmountValue = billEntryRepository.getAllExpenditureAmountBasedOnDeptId(faYearid,
				budgCodeid, orgId, fromDate, toDate, deptId);
		objectList.add(collectedAmountValue);

		return objectList;

	}

	@Override
	public List<BigDecimal> findBudgetEstimationPreparationLastYearActualCountAmountDataRev(Long faYearid,
			Long budgCodeid, Long orgId, Long deptId) {
		// TODO Auto-generated method stub
		final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
		final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
		final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
		String fromdateYear;
		String todateYear;
		final List<BigDecimal> objectList = new ArrayList<>();

		Date fromDate = null;
		Date toDate = null;
		for (Object[] objects : faYearFromDate) {
			if (objects[0] != null && objects[1] != null) {
				fromDate = (Date) objects[0];
				toDate = (Date) objects[1]; // unused
			}
		}
		// we need to fetch BudgetEstimation of 01.04 to 31.12 so we used below logic
		toDate.setDate(31);
		toDate.setMonth(11);
		toDate.setYear(fromDate.getYear());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
			deptId=departmentRepository.getDepartmentIdByDeptCode(AccountConstants.AC.getValue());
		}

		final BigDecimal collectedAmountValue = accountReceiptEntryJpaRepository
				.getAllCollectedAmountByBasedOnDeptId(faYearid, budgCodeid, orgId, fromDate, toDate, deptId);
		objectList.add(collectedAmountValue);

		return objectList;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<String, String> findByBudgetIdsBulkEdit(Long faYearid, Long cpdBugtypeId, Long dpDeptid,
			Long cpdBugsubtypeId, Long fieldId, Long orgId) {
		// TODO Auto-generated method stub

		final List<Object[]> findById = accountBudgetEstimationPreparationDao.findByAllBudgetCodeIdBulkEdit(faYearid,
				cpdBugtypeId, dpDeptid, cpdBugsubtypeId, fieldId, orgId);
		final Map<String, String> map = new LinkedHashMap<>();
		if ((findById != null) && !findById.isEmpty()) {
			for (final Object[] objects : findById) {
				map.put(objects[0].toString(), objects[1].toString());
			}
		}
		return map;

	}

	private List<AccountBudgetProjectedRevenueEntryBean> gettingEstimationPreparationRevenueDataBulkEdit(
			final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList,
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
			final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity, final Long orgId,final Long bugetId) {

		tbAcBudgetEstimationPreparation.setFaYearid(accountBudgetEstimationPreparationEntity.getFaYearid());
		if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId() != null) {
			tbAcBudgetEstimationPreparation.setCpdBugtypeId(accountBudgetEstimationPreparationEntity.getCpdBugtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
					accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
			tbAcBudgetEstimationPreparation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(
					PrefixConstants.PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId() != null) {
			tbAcBudgetEstimationPreparation
					.setCpdBugsubtypeId(accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(
					PrefixConstants.BUG_SUB_PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getDpDeptid() != null) {
			tbAcBudgetEstimationPreparation.setDpDeptid(accountBudgetEstimationPreparationEntity.getDpDeptid());
			tbAcBudgetEstimationPreparation.setDepartmentDesc(departmentRepository
					.fetchDepartmentDescById(accountBudgetEstimationPreparationEntity.getDpDeptid()));
		}
		if (accountBudgetEstimationPreparationEntity.getNextFaYearid() != null) {
			tbAcBudgetEstimationPreparation.setNextFaYearid(accountBudgetEstimationPreparationEntity.getNextFaYearid());
		}
		if (accountBudgetEstimationPreparationEntity.getFieldId() != null) {
			tbAcBudgetEstimationPreparation.setFieldId(accountBudgetEstimationPreparationEntity.getFieldId());
		}
		if (accountBudgetEstimationPreparationEntity.getFundId() != null) {
			tbAcBudgetEstimationPreparation.setFundId(accountBudgetEstimationPreparationEntity.getFundId());
		}
		if (accountBudgetEstimationPreparationEntity.getRemark() != null) {
			tbAcBudgetEstimationPreparation.setRemark(accountBudgetEstimationPreparationEntity.getRemark());
		}
		final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
		bean.setBudgetId(bugetId);
		// final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList =
		// new ArrayList<>();
		if ((accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster() != null)
				&& (accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
			bean.setPrRevBudgetCode(
					accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
		}
		final Long prBudgetCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster()
				.getPrBudgetCodeid();
		final String acBudgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
		bean.setPrRevBudgetCodeDup(acBudgetCode);
		final Long faYearid = accountBudgetEstimationPreparationEntity.getFaYearid();
		final Long budgCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
		final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryJpaRepository
				.findByRenueOrgAmount(faYearid, budgCodeid, orgId);

		final List<Object[]> orgEstimateAmtDetailsNextYear = accountBudgetProjectedRevenueEntryJpaRepository
				.findByRenueOrgAmount(tbAcBudgetEstimationPreparation.getNextFaYearid(), budgCodeid, orgId);

		List<BigDecimal> LastYearData = findBudgetEstimationPreparationLastYearCountAmountData(faYearid, budgCodeid,
				orgId);

		for (final Object[] objects : orgEstimateAmtDetailsNextYear) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {
				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				} else {
					// bean.setRemark("");
				}
			}
		}

		for (final Object[] objects : orgEstimateAmtDetails) {
			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {

				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setRevisedEstamt(revisedEsmtAmount.toString());
				}

				if (objects[1] != null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					bean.setOrginalEstamt(originalEstAmount.toString());
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYear(expectedCurrentYear);
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYear(expectedCurrentYear);
				}

				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				}

				if (objects[5] != null && objects[0] != null) {
					BigDecimal actualsCurrentYear;

					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);

					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);

					actualsCurrentYear = revisedEsmtAmount.subtract(expectedCurrentYear);

					if (revisedEsmtAmount.compareTo(expectedCurrentYear) != 0) {
						bean.setActualsCurrentYear(actualsCurrentYear);
					} else {
						// do nothing
					}
				}

			}
		}

		if (CollectionUtils.isNotEmpty(LastYearData)) {
			int count = 0;
			for (BigDecimal amt : LastYearData) {
				if (count == 0)
					bean.setLastYearCountDupThree(amt);
				if (count == 1)
					bean.setLastYearCountDupTwo(amt);
				if (count == 2)
					bean.setLastYearCountDupOne(amt);
				count++;
			}
		}
		if (accountBudgetEstimationPreparationEntity.getEstimateForNextyear() != null) {
			BigDecimal estimateForNextyear = accountBudgetEstimationPreparationEntity.getEstimateForNextyear();
			estimateForNextyear = estimateForNextyear.setScale(2, RoundingMode.CEILING);
			bean.setEstimateForNextyear(estimateForNextyear);
		}
		if (accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody() != null) {
			BigDecimal finalizedBugGenBody = accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody();
			finalizedBugGenBody = finalizedBugGenBody.setScale(2, RoundingMode.CEILING);
			bean.setFinalizedBugGenBody(finalizedBugGenBody);
		}
		if (accountBudgetEstimationPreparationEntity.getApprBugStandCom() != null) {
			BigDecimal apprBugStandCom = accountBudgetEstimationPreparationEntity.getApprBugStandCom();
			apprBugStandCom = apprBugStandCom.setScale(2, RoundingMode.CEILING);
			bean.setApprBugStandCom(apprBugStandCom);
		}
		tbAcBudgetRevenueList.add(bean);
		// tbAcBudgetEstimationPreparation.setBugprojRevBeanList(tbAcBudgetRevenueList);

		return tbAcBudgetRevenueList;

	}

	private List<AccountBudgetProjectedExpenditureBean> gettingEstimationPreparationExpenditureDataBulkEdit(
			final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList,
			final AccountBudgetEstimationPreparationBean tbAcBudgetEstimationPreparation,
			final AccountBudgetEstimationPreparationEntity accountBudgetEstimationPreparationEntity, final Long orgId, final Long budgetId) {
		tbAcBudgetEstimationPreparation.setFaYearid(accountBudgetEstimationPreparationEntity.getFaYearid());
		if (accountBudgetEstimationPreparationEntity.getCpdBugtypeId() != null) {
			tbAcBudgetEstimationPreparation.setCpdBugtypeId(accountBudgetEstimationPreparationEntity.getCpdBugtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
					accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
			tbAcBudgetEstimationPreparation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(
					PrefixConstants.PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId() != null) {
			tbAcBudgetEstimationPreparation
					.setCpdBugsubtypeId(accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId());
			tbAcBudgetEstimationPreparation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(
					PrefixConstants.BUG_SUB_PREFIX, accountBudgetEstimationPreparationEntity.getOrgid(),
					accountBudgetEstimationPreparationEntity.getCpdBugsubtypeId()));
		}
		if (accountBudgetEstimationPreparationEntity.getDpDeptid() != null) {
			tbAcBudgetEstimationPreparation.setDpDeptid(accountBudgetEstimationPreparationEntity.getDpDeptid());
			tbAcBudgetEstimationPreparation.setDepartmentDesc(departmentRepository
					.fetchDepartmentDescById(accountBudgetEstimationPreparationEntity.getDpDeptid()));
		}
		if (accountBudgetEstimationPreparationEntity.getNextFaYearid() != null) {
			tbAcBudgetEstimationPreparation.setNextFaYearid(accountBudgetEstimationPreparationEntity.getNextFaYearid());
		}
		if (accountBudgetEstimationPreparationEntity.getFieldId() != null) {
			tbAcBudgetEstimationPreparation.setFieldId(accountBudgetEstimationPreparationEntity.getFieldId());
		}

		if (accountBudgetEstimationPreparationEntity.getFundId() != null) {
			tbAcBudgetEstimationPreparation.setFundId(accountBudgetEstimationPreparationEntity.getFundId());
		}

		if (accountBudgetEstimationPreparationEntity.getRemark() != null) {
			tbAcBudgetEstimationPreparation.setRemark(accountBudgetEstimationPreparationEntity.getRemark());
		}

		final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
		bean.setBudgetId(budgetId);
		// final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new
		// ArrayList<>();

		if ((accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster() != null)
				&& (accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
			bean.setPrExpBudgetCode(
					accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
		}
		final Long prBudgetCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster()
				.getPrBudgetCodeid();
		final String acBudgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
		bean.setPrExpBudgetCodeDup(acBudgetCode);
		final Long faYearid = accountBudgetEstimationPreparationEntity.getFaYearid();
		final Long budgCodeid = accountBudgetEstimationPreparationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
		final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedExpenditureJpaRepository
				.findByExpOrgAmount(faYearid, budgCodeid, orgId);

		final List<Object[]> orgEstimateAmtDetailsNextYear = accountBudgetProjectedExpenditureJpaRepository
				.findByExpOrgAmount(tbAcBudgetEstimationPreparation.getNextFaYearid(), budgCodeid, orgId);

		List<BigDecimal> LastYearData = findBudgetEstimationPreparationLastYearCountAmountDataExp(faYearid, budgCodeid,
				orgId);

		for (final Object[] objects : orgEstimateAmtDetailsNextYear) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {
				if (objects[6] != null) {
					String remark = new String(objects[6].toString());
					bean.setRemark(remark);
				} else {
					// bean.setRemark("");
				}
			}
		}

		for (final Object[] objects : orgEstimateAmtDetails) {

			if (accountBudgetEstimationPreparationEntity.getDpDeptid() == objects[4]) {

				if (objects[0] != null) {
					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setRevisedEstamt(revisedEsmtAmount.toString());
				}

				if (objects[1] != null) {
					BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
					originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
					bean.setOrginalEstamt(originalEstAmount.toString());
				}

				if (objects[5] != null) {
					BigDecimal expectedCurrentYearO = new BigDecimal(objects[5].toString());
					expectedCurrentYearO = expectedCurrentYearO.setScale(2, RoundingMode.CEILING);
					// bean.setOrginalEstamt(revisedEsmtAmount.toString());
					bean.setExpectedCurrentYearO(expectedCurrentYearO);
				}

				if (objects[5] != null && objects[0] != null) {
					BigDecimal actualsCurrentYearO;

					BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
					revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);

					BigDecimal expectedCurrentYear = new BigDecimal(objects[5].toString());
					expectedCurrentYear = expectedCurrentYear.setScale(2, RoundingMode.CEILING);

					actualsCurrentYearO = revisedEsmtAmount.subtract(expectedCurrentYear);

					if (revisedEsmtAmount.compareTo(expectedCurrentYear) != 0) {
						bean.setActualsCurrentYearO(actualsCurrentYearO);
					} else {
						// do nothing
					}

				}

			}
		}

		if (CollectionUtils.isNotEmpty(LastYearData)) {
			int count = 0;
			for (BigDecimal amt : LastYearData) {
				if (count == 0)
					bean.setLastYearCountDupThree(amt);
				if (count == 1)
					bean.setLastYearCountDupTwo(amt);
				if (count == 2)
					bean.setLastYearCountDupOne(amt);
				count++;
			}
		}

		if (accountBudgetEstimationPreparationEntity.getEstimateForNextyear() != null) {
			BigDecimal estimateForNextyear = accountBudgetEstimationPreparationEntity.getEstimateForNextyear();
			estimateForNextyear = estimateForNextyear.setScale(2, RoundingMode.CEILING);
			bean.setEstimateForNextyear(estimateForNextyear);
		}
		if (accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody() != null) {
			BigDecimal finalizedBugGenBody = accountBudgetEstimationPreparationEntity.getFinalizedBugGenBody();
			finalizedBugGenBody = finalizedBugGenBody.setScale(2, RoundingMode.CEILING);
			bean.setFinalizedBugGenBody(finalizedBugGenBody);
		}
		if (accountBudgetEstimationPreparationEntity.getApprBugStandCom() != null) {
			BigDecimal apprBugStandCom = accountBudgetEstimationPreparationEntity.getApprBugStandCom();
			apprBugStandCom = apprBugStandCom.setScale(2, RoundingMode.CEILING);
			bean.setApprBugStandCom(apprBugStandCom);
		}
		tbAcBudgetExpList.add(bean);
		// tbAcBudgetEstimationPreparation.setBugprojExpBeanList(tbAcBudgetExpList);
		return tbAcBudgetExpList;
	}

	@Override
	public Map<String, String> findAccountBudgetSummaryBasedOnNFY(Long nextFaYearid) {
		LOGGER.info("Next Fa Year ID----------------------------------->"+nextFaYearid);
		final Map<String, String> map = new LinkedHashMap<>();
		final List<LookUp> bugSubTypelevelMap = CommonMasterUtility.getListLookup(PrefixConstants.PREFIX,
                UserSession.getCurrent().getOrganisation());
        if (bugSubTypelevelMap != null) {
            for (final LookUp lookUp : bugSubTypelevelMap) {
               
				if (lookUp.getLookUpCode().equals(PrefixConstants.REV_CPD_VALUE)) {
					List<Object[]> findAccountBudgetRevSmryBasedOnNFY = accountBudgetEstimationPreparationJpaRepository.findAccountBudgetReceiptsSummaryBasedOnNFY(nextFaYearid, lookUp.getLookUpId());
					if ((findAccountBudgetRevSmryBasedOnNFY != null) && !findAccountBudgetRevSmryBasedOnNFY.isEmpty()) {
						LOGGER.info("NOT NUll  findAccountBudgetRevSmryBasedOnNFY ----------------------------->"
								+ findAccountBudgetRevSmryBasedOnNFY);
						for (final Object[] objects : findAccountBudgetRevSmryBasedOnNFY) {
							LOGGER.info("estimateForNextyearRevAvg----------->" + objects[0] + objects[1] + objects[2]);
							if (objects[0] != null) {
								map.put("estimateForNextyearRevAvg", objects[0].toString());
							}
							if (objects[1] != null) {
								map.put("apprBugStandComRevAvg", objects[1].toString());
							}
							if (objects[2] != null) {
								map.put("finalizedBugGenBodRevAvg", objects[2].toString());
							}
						}
					}

				}
				if (lookUp.getLookUpCode().equals(PrefixConstants.EXP_CPD_VALUE)) {
					List<Object[]> findAccountBudgetExpSmryBasedOnNFY = accountBudgetEstimationPreparationJpaRepository.findAccountBudgetExpenditureSummaryBasedOnNFY(nextFaYearid, lookUp.getLookUpId());
					if ((findAccountBudgetExpSmryBasedOnNFY != null) && !findAccountBudgetExpSmryBasedOnNFY.isEmpty()) {
						LOGGER.info("NOT NUll  findAccountBudgetExpSmryBasedOnNFY ----------------------------->"
								+ findAccountBudgetExpSmryBasedOnNFY);
						for (final Object[] objects : findAccountBudgetExpSmryBasedOnNFY) {
							LOGGER.info("estimateForNextyearRevAvg----------->" + objects[0] + objects[1] + objects[2]);
							if (objects[0] != null) {
								map.put("estimateForNextyearExpAvg", objects[0].toString());
							}
							if (objects[1] != null) {
								map.put("apprBugStandComExpAvg", objects[1].toString());
							}
							if (objects[2] != null) {
								map.put("finalizedBugGenBodExpAvg", objects[2].toString());
							}
						}
					}
				}
          }
        } 
		return map;
	}

}
