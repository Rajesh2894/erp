
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetoryRevisionDao;
import com.abm.mainet.account.domain.AccountBudgetoryRevisionEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.dto.AccountBudgetoryRevisionBean;
import com.abm.mainet.account.mapper.AccountBudgetoryRevisionServiceMapper;
import com.abm.mainet.account.repository.AccountReceiptEntryJpaRepository;
import com.abm.mainet.account.repository.BillEntryRepository;
import com.abm.mainet.account.repository.BudgetHeadRepository;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.account.repository.BudgetoryRevisionRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetoryRevisionServiceImpl implements AccountBudgetoryRevisionService {

    @Resource
    private AccountBudgetoryRevisionServiceMapper accountBudgetoryRevisionServiceMapper;
    @Resource
    private BudgetoryRevisionRepository accountBudgetoryRevisionJpaRepository;
    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
    @Resource
    private BudgetHeadRepository budgetCodeRepository;
    @Resource
    private AccountBudgetoryRevisionDao accountBudgetoryRevisionDao;
    @Resource
    private BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;
    @Resource
    private BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;
    @Resource
    private AccountReceiptEntryJpaRepository accountReceiptEntryJpaRepository;
    @Resource
    private BillEntryRepository billEntryRepository;

    @Override
    @Transactional
    public Boolean isBudgetoryRevisionEntryExists(final Long faYearid, final Long prRevBudgetCode, final Long orgId) {

        return accountBudgetoryRevisionDao.isBudgetoryRevisionEntryExists(faYearid, prRevBudgetCode, orgId);
    }

    @Override
    @Transactional
    public List<AccountBudgetoryRevisionBean> findByFinancialId(final Long orgId) {
        final List<AccountBudgetoryRevisionEntity> entities = accountBudgetoryRevisionJpaRepository.findByFinancialId(
                orgId);
        final List<AccountBudgetoryRevisionBean> bean = new ArrayList<>();
        for (final AccountBudgetoryRevisionEntity accountBudgetoryRevisionEntity : entities) {
            bean.add(accountBudgetoryRevisionServiceMapper
                    .mapAccountBudgetoryRevisionEntityToAccountBudgetoryRevisionBean(accountBudgetoryRevisionEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public List<AccountBudgetoryRevisionBean> findByGridAllData(final Long faYearid, final Long cpdBugtypeId, final Long dpDeptid,
            final Long prBudgetCodeid, final Long orgId) {
        final List<AccountBudgetoryRevisionEntity> entities = accountBudgetoryRevisionDao.findByGridAllData(faYearid,
                cpdBugtypeId,
                dpDeptid, prBudgetCodeid, orgId);
        final List<AccountBudgetoryRevisionBean> bean = new ArrayList<>();
        for (final AccountBudgetoryRevisionEntity accountBudgetoryRevisionEntity : entities) {
            bean.add(accountBudgetoryRevisionServiceMapper
                    .mapAccountBudgetoryRevisionEntityToAccountBudgetoryRevisionBean(accountBudgetoryRevisionEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public Map<String, String> findBudgetCodeIdCodeFromBudgetoryRevision(final Long faYearid, final Long fundId,
            final Long functionId,
            final Long cpdBugtypeId,
            final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {

        final List<Object[]> findById = accountBudgetoryRevisionDao.findByAllBudgetCodeId(faYearid, fundId, functionId,
                cpdBugtypeId,
                prBudgetCodeid, dpDeptid, orgId);
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
    public AccountBudgetoryRevisionBean saveBudgetoryRevisionFormData(final AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            final int languageId, final Organisation org) throws ParseException {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, languageId, org);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, languageId, org);
        if (tbAcBudgetoryRevision.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            budgetoryRevisionRevenueStoredData(tbAcBudgetoryRevision, languageId);
        } else if (tbAcBudgetoryRevision.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            budgetoryRevisionExpenditureStoredData(tbAcBudgetoryRevision, languageId);
        }
        return tbAcBudgetoryRevision;
    }

    private void budgetoryRevisionRevenueStoredData(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision, final int languageId) throws ParseException {
        final AccountBudgetoryRevisionBean budgetoryRevision = tbAcBudgetoryRevision;
        AccountBudgetoryRevisionEntity budgetoryRevisionEntity = null;
        final List<AccountBudgetProjectedRevenueEntryBean> bugProjRevList = tbAcBudgetoryRevision.getBugprojRevBeanList();
        if ((bugProjRevList != null) && !bugProjRevList.isEmpty()) {
            for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : bugProjRevList) {
                budgetoryRevision.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
                        .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
                budgetoryRevision.setRevisedAmount(accountBudgetProjectedRevenueEntryBean.getRevisedAmount());
                budgetoryRevisionEntity = new AccountBudgetoryRevisionEntity();
                final Long orgId = tbAcBudgetoryRevision.getOrgid();
                final Long faYearid = tbAcBudgetoryRevision.getFaYearid();
                Long prProjectionid = null;
                if (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null) {
                    prProjectionid = accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic();
                }
                BigDecimal revisedEstamt = accountBudgetProjectedRevenueEntryBean.getRevisedAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                // final BigDecimal projectedAmount = revisedEstamt;
                accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                        revisedEstamt.toString(), orgId);

                accountBudgetoryRevisionServiceMapper.mapAccountBudgetoryRevisionBeanToAccountBudgetoryRevisionEntity(
                        budgetoryRevision, budgetoryRevisionEntity);
                accountBudgetoryRevisionJpaRepository.save(budgetoryRevisionEntity);
            }
        }
    }

    private void budgetoryRevisionExpenditureStoredData(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision, final int languageId) throws ParseException {
        final AccountBudgetoryRevisionBean budgetoryRevision = tbAcBudgetoryRevision;
        AccountBudgetoryRevisionEntity budgRevisionEntity = null;
        final List<AccountBudgetProjectedExpenditureBean> budgetProjExpBean = tbAcBudgetoryRevision.getBugprojExpBeanList();
        if ((budgetProjExpBean != null) && !budgetProjExpBean.isEmpty()) {
            for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : budgetProjExpBean) {
                budgetoryRevision.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
                        .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
                budgetoryRevision.setRevisedAmount(accountBudgetProjectedExpenditureBean.getRevisedAmount());
                budgRevisionEntity = new AccountBudgetoryRevisionEntity();
                final Long orgId = tbAcBudgetoryRevision.getOrgid();
                final Long faYearid = tbAcBudgetoryRevision.getFaYearid();
                Long prExpenditureid = null;
                if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic() != null) {
                    prExpenditureid = accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic();
                }
                BigDecimal revisedEstamt = accountBudgetProjectedExpenditureBean.getRevisedAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                // final BigDecimal balanceAmount = revisedEstamt;
                accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                        revisedEstamt.toString(), orgId);

                accountBudgetoryRevisionServiceMapper
                        .mapAccountBudgetoryRevisionBeanToAccountBudgetoryRevisionEntity(budgetoryRevision, budgRevisionEntity);
                accountBudgetoryRevisionJpaRepository.save(budgRevisionEntity);

            }
        }
    }

    @Override
    @Transactional
    public AccountBudgetoryRevisionBean getDetailsUsingBudgetoryRevisionId(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            final int languageId, final Organisation org) throws ParseException {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, languageId, org);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, languageId, org);
        final Long BugrevId = tbAcBudgetoryRevision.getBugrevId();
        final AccountBudgetoryRevisionEntity accountBudgetoryRevisionEntity = accountBudgetoryRevisionJpaRepository
                .findOne(BugrevId);
        if (accountBudgetoryRevisionEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingBudgetoryRevisionRevenueData(
                    tbAcBudgetoryRevision,
                    accountBudgetoryRevisionEntity);
        } else if (accountBudgetoryRevisionEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingBudgetoryRevisionExpenditureData(
                    tbAcBudgetoryRevision,
                    accountBudgetoryRevisionEntity);
        }
        return tbAcBudgetoryRevision;
    }

    /**
     * @param tbAcBudgetoryRevision
     * @param accountBudgetoryRevisionEntity
     * @param accountBudgetReappropriationTrMasterEntity
     * @throws ParseException
     */
    private void gettingBudgetoryRevisionExpenditureData(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            final AccountBudgetoryRevisionEntity accountBudgetoryRevisionEntity) throws ParseException {
        tbAcBudgetoryRevision.setFaYearid(accountBudgetoryRevisionEntity.getFaYearid());
        if (accountBudgetoryRevisionEntity.getCpdBugtypeId() != null) {
            tbAcBudgetoryRevision.setCpdBugtypeId(accountBudgetoryRevisionEntity.getCpdBugtypeId());
            tbAcBudgetoryRevision.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugtypeId()));
            tbAcBudgetoryRevision.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugtypeId()));
        }
        if (accountBudgetoryRevisionEntity.getCpdBugsubtypeId() != null) {
            tbAcBudgetoryRevision.setCpdBugsubtypeId(accountBudgetoryRevisionEntity.getCpdBugsubtypeId());
            tbAcBudgetoryRevision.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugsubtypeId()));
        }
        if (accountBudgetoryRevisionEntity.getDpDeptid() != null) {
            tbAcBudgetoryRevision.setDpDeptid(accountBudgetoryRevisionEntity.getDpDeptid());
            tbAcBudgetoryRevision
                    .setDepartmentDesc(
                            departmentRepository.fetchDepartmentDescById(accountBudgetoryRevisionEntity.getDpDeptid()));
        }
        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();
        if ((accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrExpBudgetCode(accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        final Long orgId = accountBudgetoryRevisionEntity.getOrgid();
        final Long faYearid = accountBudgetoryRevisionEntity.getFaYearid();
        final Long prBudgetCodeid = accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid();
        final String budgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
        bean.setPrExpBudgetCodeDup(budgetCode);
        final Long budgCodeid = accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
        final List<Object[]> OrgEstimateAmtDetails = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                budgCodeid, orgId);
        BigDecimal orgAmount = null;
        for (final Object[] objects : OrgEstimateAmtDetails) {
            if (objects[0] == null) {
                orgAmount = new BigDecimal(objects[1].toString());
                orgAmount = orgAmount.setScale(2, RoundingMode.CEILING);
                bean.setOrginalEstamt(orgAmount.toString());
            }
            if (objects[0] != null) {
                orgAmount = new BigDecimal(objects[0].toString());
                orgAmount = orgAmount.setScale(2, RoundingMode.CEILING);
                bean.setOrginalEstamt(orgAmount.toString());
                bean.setRevisedAmount(orgAmount);
            }
            if (objects[3] != null) {
                final Long primaryKeyId = Long.valueOf(objects[3].toString());
                bean.setPrExpenditureidExpDynamic(primaryKeyId);
            }
        }

        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        final List<Object[]> currentfinanceYears = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        String frmdateYear;
        String todateYear;
        if ((currentfinanceYears != null) && !currentfinanceYears.isEmpty()) {
            Date dateFrom = null;
            Date dateTo = null;
            BigDecimal tillNovAmmount = null;
            for (final Object[] obj : currentfinanceYears) {
                frmdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                final String frmdteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH + MainetConstants.HYPHEN
                        + frmdateYear;
                final String todteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_MONTH + MainetConstants.HYPHEN
                        + todateYear;
                dateFrom = dateFormater.parse(frmdteActualTillDate);
                dateTo = dateFormater.parse(todteActualTillDate);
                tillNovAmmount = billEntryRepository.getAllExpenditureAmount(faYearid, budgCodeid, orgId,
                        dateFrom, dateTo);
                // tillNovAmmount = accountBudgetoryRevisionJpaRepository.getSumFeeExpAmount(prBudgetCodeid, dateFrom,
                // dateTo,orgId);
                if (tillNovAmmount != null) {
                    tillNovAmmount = tillNovAmmount.setScale(2, RoundingMode.CEILING);
                }
                if (tillNovAmmount != null) {
                    tillNovAmmount = tillNovAmmount.setScale(2, RoundingMode.CEILING);
                    bean.setActualTillNovAmount(tillNovAmmount);
                } else {
                    BigDecimal defaultValue = new BigDecimal(0);
                    defaultValue = defaultValue.setScale(2, RoundingMode.CEILING);
                    bean.setActualTillNovAmount(defaultValue);
                }
            }
        }
        if ((bean.getOrginalEstamt() != null) && !bean.getOrginalEstamt().isEmpty()) {
            BigDecimal orgEsmtAmount = new BigDecimal(bean.getOrginalEstamt());
            orgEsmtAmount = orgEsmtAmount.setScale(2, RoundingMode.CEILING);
            BigDecimal actualTillNovAmount = bean.getActualTillNovAmount();
            actualTillNovAmount = actualTillNovAmount.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmount = orgEsmtAmount.subtract(actualTillNovAmount);
            budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
            bean.setBudgetedFromDecAmount(budgFormDecTOMarAmount);
        } else {
            BigDecimal orgEstmtAmount = new BigDecimal(0);
            orgEstmtAmount = orgEstmtAmount.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmount = orgEstmtAmount.subtract(bean.getActualTillNovAmount());
            budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
            bean.setBudgetedFromDecAmount(budgFormDecTOMarAmount);
        }
        if (accountBudgetoryRevisionEntity.getRevisedAmount() != null) {
            BigDecimal revisedAmount = accountBudgetoryRevisionEntity.getRevisedAmount();
            revisedAmount = revisedAmount.setScale(2, RoundingMode.CEILING);
            // bean.setRevisedAmount(revisedAmount);
        }
        tbAcBudgetExpList.add(bean);
        tbAcBudgetoryRevision.setBugprojExpBeanList(tbAcBudgetExpList);
    }

    /**
     * @param tbAcBudgetoryRevision
     * @param accountBudgetoryRevisionEntity
     * @param accountBudgetReappropriationTrMasterEntity
     * @throws ParseException
     */
    private void gettingBudgetoryRevisionRevenueData(
            final AccountBudgetoryRevisionBean tbAcBudgetoryRevision,
            final AccountBudgetoryRevisionEntity accountBudgetoryRevisionEntity) throws ParseException {
        tbAcBudgetoryRevision.setFaYearid(accountBudgetoryRevisionEntity.getFaYearid());
        if (accountBudgetoryRevisionEntity.getCpdBugtypeId() != null) {
            tbAcBudgetoryRevision.setCpdBugtypeId(accountBudgetoryRevisionEntity.getCpdBugtypeId());
            tbAcBudgetoryRevision.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugtypeId()));
            tbAcBudgetoryRevision.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugtypeId()));
        }
        if (accountBudgetoryRevisionEntity.getCpdBugsubtypeId() != null) {
            tbAcBudgetoryRevision.setCpdBugsubtypeId(accountBudgetoryRevisionEntity.getCpdBugsubtypeId());
            tbAcBudgetoryRevision.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                    accountBudgetoryRevisionEntity.getOrgid(), accountBudgetoryRevisionEntity.getCpdBugsubtypeId()));
        }
        if (accountBudgetoryRevisionEntity.getDpDeptid() != null) {
            tbAcBudgetoryRevision.setDpDeptid(accountBudgetoryRevisionEntity.getDpDeptid());
            tbAcBudgetoryRevision
                    .setDepartmentDesc(
                            departmentRepository.fetchDepartmentDescById(accountBudgetoryRevisionEntity.getDpDeptid()));
        }
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();
        if ((accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrRevBudgetCode(accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        final Long orgId = accountBudgetoryRevisionEntity.getOrgid();
        final Long prBudgetCodeid = accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid();
        final String budgetCode = budgetCodeRepository.findByBudgetCode(prBudgetCodeid, orgId);
        bean.setPrRevBudgetCodeDup(budgetCode);
        final Long faYearid = accountBudgetoryRevisionEntity.getFaYearid();
        final Long budgCodeid = accountBudgetoryRevisionEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
        final List<Object[]> orgEstimateAmtDetails = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(
                faYearid,
                budgCodeid, orgId);
        if (!orgEstimateAmtDetails.isEmpty()) {
            for (final Object[] objects : orgEstimateAmtDetails) {
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(revisedEsmtAmount.toString());
                    bean.setRevisedAmount(revisedEsmtAmount);
                }
                if (objects[3] != null) {
                    final Long primaryKeyId = Long.valueOf(objects[3].toString());
                    bean.setPrProjectionidRevDynamic(primaryKeyId);
                }

            }
        }

        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        final List<Object[]> currentfinanceYears = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        String frmdateYear;
        String todateYear;
        if ((currentfinanceYears != null) && !currentfinanceYears.isEmpty()) {
            Date dateFrom = null;
            Date dateTo = null;
            BigDecimal tillNovAmmount = null;
            for (final Object[] obj : currentfinanceYears) {
                frmdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                final String frmdteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY +
                        MainetConstants.HYPHEN + MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH
                        + MainetConstants.HYPHEN + frmdateYear;
                final String todteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_DAY
                        +
                        MainetConstants.HYPHEN + MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_MONTH
                        +
                        MainetConstants.HYPHEN + todateYear;
                dateFrom = dateFormater.parse(frmdteActualTillDate);
                dateTo = dateFormater.parse(todteActualTillDate);
                tillNovAmmount = accountReceiptEntryJpaRepository.getAllCollectedAmount(faYearid, budgCodeid,
                        orgId, dateFrom, dateTo);
                // tillNovAmmount = tbFinancialyearJpaRepository.getSumFeeAmount(prBudgetCodeid, dateFrom, dateTo, orgId);
                if (tillNovAmmount != null) {
                    tillNovAmmount = tillNovAmmount.setScale(2, RoundingMode.CEILING);
                    bean.setActualTillNovAmount(tillNovAmmount);
                } else {
                    BigDecimal defaultValue = new BigDecimal(0);
                    defaultValue = defaultValue.setScale(2, RoundingMode.CEILING);
                    bean.setActualTillNovAmount(defaultValue);
                }
            }
        }
        if ((bean.getOrginalEstamt() != null) && !bean.getOrginalEstamt().isEmpty()) {
            BigDecimal orgEsmtAmount = new BigDecimal(bean.getOrginalEstamt());
            orgEsmtAmount = orgEsmtAmount.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmount = orgEsmtAmount.subtract(bean.getActualTillNovAmount());
            budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
            bean.setBudgetedFromDecAmount(budgFormDecTOMarAmount);
        } else {
            BigDecimal orgEstmtAmount = new BigDecimal(0);
            orgEstmtAmount = orgEstmtAmount.setScale(2, RoundingMode.CEILING);
            BigDecimal budgFormDecTOMarAmount = orgEstmtAmount.subtract(bean.getActualTillNovAmount());
            budgFormDecTOMarAmount = budgFormDecTOMarAmount.setScale(2, RoundingMode.CEILING);
            bean.setBudgetedFromDecAmount(budgFormDecTOMarAmount);
        }
        if (accountBudgetoryRevisionEntity.getRevisedAmount() != null) {
            BigDecimal revisedAmount = accountBudgetoryRevisionEntity.getRevisedAmount();
            revisedAmount = revisedAmount.setScale(2, RoundingMode.CEILING);
            // bean.setRevisedAmount(revisedAmount);
        }
        tbAcBudgetRevenueList.add(bean);
        tbAcBudgetoryRevision.setBugprojRevBeanList(tbAcBudgetRevenueList);
    }

    @Override
    @Transactional
    public BigDecimal findBudgetgetRevisionActualTillNovFromDecAmountData(final Long faYearid, final Long budgCodeid,
            final Long orgId)
            throws Exception {

        final List<Object[]> currentfinanceYears = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        String frmdateYear;
        String todateYear;
        BigDecimal tillNovAmmount = null;
        if ((currentfinanceYears != null) && !currentfinanceYears.isEmpty()) {
            Date dateFrom = null;
            Date dateTo = null;
            for (final Object[] obj : currentfinanceYears) {
                frmdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                final String frmdteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH + MainetConstants.HYPHEN
                        + frmdateYear;
                final String todteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_MONTH + MainetConstants.HYPHEN
                        + todateYear;
                dateFrom = dateFormater.parse(frmdteActualTillDate);
                dateTo = dateFormater.parse(todteActualTillDate);

                tillNovAmmount = accountReceiptEntryJpaRepository.getAllCollectedAmount(faYearid, budgCodeid,
                        orgId, dateFrom, dateTo);

                // tillNovAmmount = tbFinancialyearJpaRepository.getSumFeeAmount(budgCodeid, dateFrom, dateTo, orgId);
            }
        }
        if (tillNovAmmount == null) {
            tillNovAmmount = new BigDecimal(0);
        }
        return tillNovAmmount;
    }

    @Override
    @Transactional
    public BigDecimal findBudgetgetRevisionActualTillNovFromDecAmountExpData(final Long faYearid, final Long budgCodeid,
            final Long orgId)
            throws Exception {

        final List<Object[]> currentfinanceYears = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearid);
        final DateFormat yearFormater = new SimpleDateFormat(MainetConstants.YEAR_FORMAT);
        final DateFormat dateFormater = new SimpleDateFormat(MainetConstants.DATE_FRMAT);
        String frmdateYear;
        String todateYear;
        BigDecimal tillNovAmmount = null;
        if ((currentfinanceYears != null) && !currentfinanceYears.isEmpty()) {
            Date dateFrom = null;
            Date dateTo = null;
            for (final Object[] obj : currentfinanceYears) {
                frmdateYear = yearFormater.format(obj[0]);
                todateYear = yearFormater.format(obj[1]);
                final String frmdteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_START_MONTH + MainetConstants.HYPHEN
                        + frmdateYear;
                final String todteActualTillDate = MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_DAY
                        + MainetConstants.HYPHEN +
                        MainetConstants.BUDGET_ESTIMATION_PREPARATION.FINA_YEAR_ACTUAL_TILL_NOV_AMT_MONTH + MainetConstants.HYPHEN
                        + todateYear;
                dateFrom = dateFormater.parse(frmdteActualTillDate);
                dateTo = dateFormater.parse(todteActualTillDate);

                tillNovAmmount = billEntryRepository.getAllExpenditureAmount(faYearid, budgCodeid, orgId,
                        dateFrom, dateTo);

                // tillNovAmmount = accountBudgetoryRevisionJpaRepository.getSumFeeExpAmount(budgCodeid, dateFrom, dateTo, orgId);
            }
        }
        if (tillNovAmmount == null) {
            tillNovAmmount = new BigDecimal(0);
        }
        return tillNovAmmount;
    }
}
