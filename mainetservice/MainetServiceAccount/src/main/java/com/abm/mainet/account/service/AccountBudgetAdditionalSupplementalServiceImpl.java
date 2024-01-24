
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetAdditionalSupplementalDao;
import com.abm.mainet.account.domain.AccountBudgetAdditionalSupplementalEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetAdditionalSupplementalBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.mapper.AccountBudgetAdditionalSupplementalServiceMapper;
import com.abm.mainet.account.mapper.AccountBudgetProjectedExpenditureServiceMapper;
import com.abm.mainet.account.mapper.AccountBudgetProjectedRevenueEntryServiceMapper;
import com.abm.mainet.account.repository.BudgetAdditionalSupplementalRepository;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UtilityService;

/**
 * @author prasad.kancharla
 *
 */
@Component
public class AccountBudgetAdditionalSupplementalServiceImpl implements AccountBudgetAdditionalSupplementalService {

    @Resource
    private BudgetAdditionalSupplementalRepository accountBudgetAdditionalSupplementalJpaRepository;
    @Resource
    private AccountBudgetAdditionalSupplementalServiceMapper accountBudgetAdditionalSupplementalServiceMapper;
    @Resource
    private AccountBudgetAdditionalSupplementalDao accountBudgetAdditionalSupplementalDao;
    @Resource
    private BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;
    @Resource
    private BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;
    @Resource
    private AccountBudgetProjectedRevenueEntryServiceMapper accountBudgetProjectedRevenueEntryServiceMapper;
    @Resource
    private AccountBudgetProjectedExpenditureServiceMapper accountBudgetProjectedExpenditureServiceMapper;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;

    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalBean> findBudgetAdditionalSupplementalByFinancialId(final Long faYearid,
            final String budgIdentifyFlag,
            final Long orgId) {
        final List<AccountBudgetAdditionalSupplementalEntity> entities = accountBudgetAdditionalSupplementalJpaRepository
                .findByFinancialId(faYearid, budgIdentifyFlag, orgId);
        final List<AccountBudgetAdditionalSupplementalBean> bean = new ArrayList<>();
        for (final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity : entities) {
            bean.add(accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanEntityToAccountBudgetAdditionalSupplementalBean(
                            accountBudgetAdditionalSupplementalEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalBean> findByGridAllData(final Long faYearid, final Long cpdBugtypeId,
            final Long dpDeptid,
            final Long prBudgetCodeid, final String budgIdentifyFlag, final Long orgId) {
        final List<AccountBudgetAdditionalSupplementalEntity> entities = accountBudgetAdditionalSupplementalDao
                .findByGridAllData(faYearid, cpdBugtypeId, dpDeptid, prBudgetCodeid, budgIdentifyFlag, orgId);
        final List<AccountBudgetAdditionalSupplementalBean> bean = new ArrayList<>();
        for (final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity : entities) {
            bean.add(accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanEntityToAccountBudgetAdditionalSupplementalBean(
                            accountBudgetAdditionalSupplementalEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalBean> findByAuthorizationGridData(final Date frmDate, final Date todate,
            final Long cpdBugtypeId,
            final String status, final String budgIdentifyFlag, final Long orgId) {
        final List<AccountBudgetAdditionalSupplementalEntity> entities = accountBudgetAdditionalSupplementalDao
                .findByAuthorizationGridData(frmDate, todate, cpdBugtypeId, status, budgIdentifyFlag, orgId);
        final List<AccountBudgetAdditionalSupplementalBean> bean = new ArrayList<>();
        for (final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity : entities) {
            bean.add(accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanEntityToAccountBudgetAdditionalSupplementalBean(
                            accountBudgetAdditionalSupplementalEntity));
        }
        return bean;
    }

    @Override
    @Transactional
    public AccountBudgetAdditionalSupplementalBean saveBudgetAdditionalSupplementalFormData(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental, final int langId,
            final Organisation orgId) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, langId, orgId);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, langId, orgId);
        if (tbAcBudgetAdditionalSupplemental.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            additionalsupplimentalRevenueStoredData(tbAcBudgetAdditionalSupplemental, langId, orgId);
        } else if (tbAcBudgetAdditionalSupplemental.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            additionalsupplimentalExpenditureStoredData(tbAcBudgetAdditionalSupplemental, langId, orgId);
        }
        return tbAcBudgetAdditionalSupplemental;
    }

    private void additionalsupplimentalExpenditureStoredData(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental, final int langId,
            final Organisation orgId)
            throws ParseException {

        final List<AccountBudgetAdditionalSupplementalBean> tbAcBudgetAdditionalSupplementalList = new ArrayList<>();
        AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntitySaved = null;
        AccountBudgetAdditionalSupplementalBean acBudgetAdditionalSupplemental = null;
        for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : tbAcBudgetAdditionalSupplemental
                .getBugprojExpBeanList()) {
            acBudgetAdditionalSupplemental = setExpAdditionalSupplementalBudget(tbAcBudgetAdditionalSupplemental,
                    acBudgetAdditionalSupplemental, langId, orgId);
            budgetAdditionalSupplementalExpnditureDataStored(
                    acBudgetAdditionalSupplemental,
                    tbAcBudgetAdditionalSupplementalList,
                    accountBudgetProjectedExpenditureBean, orgId);
        }

        for (final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalSaved : tbAcBudgetAdditionalSupplementalList) {
            accountBudgetAdditionalSupplementalEntitySaved = new AccountBudgetAdditionalSupplementalEntity();
            accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanToAccountBudgetAdditionalSupplementalEntity(
                            accountBudgetAdditionalSupplementalSaved, accountBudgetAdditionalSupplementalEntitySaved);
            accountBudgetAdditionalSupplementalEntitySaved
                    .setCpdBugtypeId(accountBudgetAdditionalSupplementalSaved.getCpdBugtypeId());
            accountBudgetAdditionalSupplementalEntitySaved
                    .setCpdBugSubTypeId(accountBudgetAdditionalSupplementalSaved.getCpdBugSubTypeId());
            accountBudgetAdditionalSupplementalEntitySaved.setFaYearid(accountBudgetAdditionalSupplementalSaved.getFaYearid());
            accountBudgetAdditionalSupplementalEntitySaved.setDpDeptid(accountBudgetAdditionalSupplementalSaved.getDpDeptid());
            accountBudgetAdditionalSupplementalEntitySaved.setRemark(accountBudgetAdditionalSupplementalSaved.getRemark());
            accountBudgetAdditionalSupplementalEntitySaved
                    .setBudgetIdentifyFlag(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG);
            final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALE,
                    PrefixConstants.REV_PREFIX, langId, orgId);
            accountBudgetAdditionalSupplementalEntitySaved.setCpdProvtypeId(new BigDecimal(revenueTypeLookup.getLookUpId()));

            final Long prExpenditureid = accountBudgetAdditionalSupplementalSaved.getPrExpenditureid();
            AccountBudgetProjectedExpenditureEntity accountBudgetProjectedExpenditureEntity = null;
            AccountBudgetProjectedExpenditureBean budgetProjectedExpenditure = null;
            if (prExpenditureid == null) {
                budgetProjectedExpenditure = new AccountBudgetProjectedExpenditureBean();
                budgetProjectedExpenditure.setOrgid(tbAcBudgetAdditionalSupplemental.getOrgid());
                budgetProjectedExpenditure.setLangId(tbAcBudgetAdditionalSupplemental.getLangId());
                budgetProjectedExpenditure.setUserId(tbAcBudgetAdditionalSupplemental.getUserId());
                budgetProjectedExpenditure.setLmoddate(tbAcBudgetAdditionalSupplemental.getLmoddate());
                budgetProjectedExpenditure.setLgIpMac(tbAcBudgetAdditionalSupplemental.getLgIpMac());
                budgetProjectedExpenditure.setFaYearid(tbAcBudgetAdditionalSupplemental.getFaYearid());
                budgetProjectedExpenditure.setCpdBugsubtypeId(tbAcBudgetAdditionalSupplemental.getCpdBugSubTypeId());
                budgetProjectedExpenditure.setDpDeptid(tbAcBudgetAdditionalSupplemental.getDpDeptid());
                budgetProjectedExpenditure.setPrBudgetCodeid(accountBudgetAdditionalSupplementalSaved.getPrBudgetCodeid());
                budgetProjectedExpenditure
                        .setOrginalEstamt(accountBudgetAdditionalSupplementalSaved.getOrgRevBalamt().toString());
                BigDecimal revisedEstamt = accountBudgetAdditionalSupplementalSaved.getNewOrgRevAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                final BigDecimal balanceAmount = revisedEstamt;
                budgetProjectedExpenditure.setRevisedEstamt(balanceAmount.toString());
                budgetProjectedExpenditure.setPrBalanceAmt(balanceAmount.toString());
                accountBudgetProjectedExpenditureEntity = new AccountBudgetProjectedExpenditureEntity();
                accountBudgetProjectedExpenditureServiceMapper
                        .mapAccountBudgetProjectedExpenditureBeanToAccountBudgetProjectedExpenditureEntity(
                                budgetProjectedExpenditure, accountBudgetProjectedExpenditureEntity);
                accountBudgetProjectedExpenditureJpaRepository.save(accountBudgetProjectedExpenditureEntity);

                if (accountBudgetProjectedExpenditureEntity.getPrExpenditureid() != null) {
                    accountBudgetAdditionalSupplementalEntitySaved
                            .setPrExpenditureid(accountBudgetProjectedExpenditureEntity.getPrExpenditureid());
                }
            }

            accountBudgetAdditionalSupplementalEntitySaved = accountBudgetAdditionalSupplementalJpaRepository
                    .save(accountBudgetAdditionalSupplementalEntitySaved);
        }
    }

    private AccountBudgetAdditionalSupplementalBean setExpAdditionalSupplementalBudget(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            AccountBudgetAdditionalSupplementalBean acBudgetAdditionalSupplemental, final int langId, final Organisation orgId)
            throws ParseException {
        acBudgetAdditionalSupplemental = new AccountBudgetAdditionalSupplementalBean();
        if ((tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) && (tbAcBudgetAdditionalSupplemental.getPaAdjid() > 0l)) {
            acBudgetAdditionalSupplemental.setPaAdjid(tbAcBudgetAdditionalSupplemental.getPaAdjid());
        }
        acBudgetAdditionalSupplemental.setOrgid(tbAcBudgetAdditionalSupplemental.getOrgid());
        acBudgetAdditionalSupplemental.setLangId(tbAcBudgetAdditionalSupplemental.getLangId());
        acBudgetAdditionalSupplemental.setUserId(tbAcBudgetAdditionalSupplemental.getUserId());
        acBudgetAdditionalSupplemental.setLmoddate(tbAcBudgetAdditionalSupplemental.getLmoddate());
        acBudgetAdditionalSupplemental.setPaEntrydate(new Date());
        if (tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) {
            acBudgetAdditionalSupplemental.setApprovedBy(tbAcBudgetAdditionalSupplemental.getApprovedBy());
            acBudgetAdditionalSupplemental.setUpdatedBy(tbAcBudgetAdditionalSupplemental.getUpdatedBy());
            acBudgetAdditionalSupplemental.setUpdatedDate(tbAcBudgetAdditionalSupplemental.getUpdatedDate());
        }
        acBudgetAdditionalSupplemental.setFaYearid(tbAcBudgetAdditionalSupplemental.getFaYearid());
        acBudgetAdditionalSupplemental.setCpdBugtypeId(tbAcBudgetAdditionalSupplemental.getCpdBugtypeId());
        acBudgetAdditionalSupplemental.setCpdBugSubTypeId(tbAcBudgetAdditionalSupplemental.getCpdBugSubTypeId());
        acBudgetAdditionalSupplemental.setDpDeptid(tbAcBudgetAdditionalSupplemental.getDpDeptid());
        acBudgetAdditionalSupplemental.setRemark(tbAcBudgetAdditionalSupplemental.getRemark());
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALE,
                PrefixConstants.REV_PREFIX, langId, orgId);
        acBudgetAdditionalSupplemental.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        acBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.N);
        if ((tbAcBudgetAdditionalSupplemental.getApproved() != null)
                && !tbAcBudgetAdditionalSupplemental.getApproved().isEmpty()) {
            acBudgetAdditionalSupplemental.setAuthFlag(tbAcBudgetAdditionalSupplemental.getApproved());
        }
        return acBudgetAdditionalSupplemental;
    }

    private void budgetAdditionalSupplementalExpnditureDataStored(
            final AccountBudgetAdditionalSupplementalBean tbAccountBudgetAdditionalSupplemental,
            final List<AccountBudgetAdditionalSupplementalBean> tbAcBudgetAdditionalSupplementalList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean, final Organisation orgid)
            throws ParseException {
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic() != null) {
            tbAccountBudgetAdditionalSupplemental
                    .setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic());
        }
        tbAccountBudgetAdditionalSupplemental.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean
                .getPrExpBudgetCode().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        if (accountBudgetProjectedExpenditureBean.getOrginalEstamt() != null) {
            tbAccountBudgetAdditionalSupplemental
                    .setOrgRevBalamt(new BigDecimal(accountBudgetProjectedExpenditureBean.getOrginalEstamt()));
        } else {
            tbAccountBudgetAdditionalSupplemental.setOrgRevBalamt(new BigDecimal(0));
        }
        tbAccountBudgetAdditionalSupplemental.setTransferAmount(accountBudgetProjectedExpenditureBean.getTransferAmountO());
        tbAccountBudgetAdditionalSupplemental.setNewOrgRevAmount(accountBudgetProjectedExpenditureBean.getNewOrgExpAmountO());
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(AccountPrefix.SPL.name(), AccountPrefix.AUT.name(),
                orgid);
        final String isMakerChecker = lkp.getOtherField();
        if (isMakerChecker != null && !isMakerChecker.isEmpty()) {
            if (isMakerChecker.equals(MainetConstants.MENU.N)) {
                final Long prExpenditureid = tbAccountBudgetAdditionalSupplemental.getPrExpenditureid();
                if (prExpenditureid != null) {
                    tbAccountBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.Y);
                    final Long orgId = tbAccountBudgetAdditionalSupplemental.getOrgid();
                    final Long faYearid = tbAccountBudgetAdditionalSupplemental.getFaYearid();
                    BigDecimal revisedEstamt = tbAccountBudgetAdditionalSupplemental.getNewOrgRevAmount();
                    revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                    // final BigDecimal balanceAmount = revisedEstamt;
                    accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                            revisedEstamt.toString(), orgId);
                }
            }
            if (isMakerChecker.equals(MainetConstants.MENU.Y)) {
                if (tbAccountBudgetAdditionalSupplemental.getAuthFlag().equals(MainetConstants.MENU.Y)) {
                    final Long prExpenditureid = tbAccountBudgetAdditionalSupplemental.getPrExpenditureid();
                    if (prExpenditureid != null) {
                        tbAccountBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.Y);
                        final Long orgId = tbAccountBudgetAdditionalSupplemental.getOrgid();
                        final Long faYearid = tbAccountBudgetAdditionalSupplemental.getFaYearid();
                        BigDecimal revisedEstamt = tbAccountBudgetAdditionalSupplemental.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        // final BigDecimal balanceAmount = revisedEstamt;
                        accountBudgetProjectedExpenditureJpaRepository.updateRevisedEstmtDataExpTable(faYearid, prExpenditureid,
                                revisedEstamt.toString(), orgId);
                    }
                }
            }
        }
        tbAcBudgetAdditionalSupplementalList.add(tbAccountBudgetAdditionalSupplemental);
    }

    /**
     * @param tbAcBudgetAdditionalSupplemental
     * @throws ParseException
     */

    private void additionalsupplimentalRevenueStoredData(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental, final int langId,
            final Organisation orgId)
            throws ParseException {

        final List<AccountBudgetAdditionalSupplementalBean> tbAcBudgetAdditionalSupplementalList = new ArrayList<>();
        AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntitySaved = null;
        AccountBudgetAdditionalSupplementalBean acBudgetAdditionalSupplemental = null;
        for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : tbAcBudgetAdditionalSupplemental
                .getBugprojRevBeanList()) {
            acBudgetAdditionalSupplemental = setAdditionalSupplementalBudget(tbAcBudgetAdditionalSupplemental,
                    acBudgetAdditionalSupplemental, langId, orgId);
            budgetAdditionalSupplementalRevenueDataStored(
                    acBudgetAdditionalSupplemental,
                    tbAcBudgetAdditionalSupplementalList,
                    accountBudgetProjectedRevenueEntryBean, orgId);
        }

        for (final AccountBudgetAdditionalSupplementalBean accountBudgetAdditionalSupplementalSaved : tbAcBudgetAdditionalSupplementalList) {
            accountBudgetAdditionalSupplementalEntitySaved = new AccountBudgetAdditionalSupplementalEntity();
            accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanToAccountBudgetAdditionalSupplementalEntity(
                            accountBudgetAdditionalSupplementalSaved, accountBudgetAdditionalSupplementalEntitySaved);
            accountBudgetAdditionalSupplementalEntitySaved
                    .setCpdBugtypeId(accountBudgetAdditionalSupplementalSaved.getCpdBugtypeId());
            accountBudgetAdditionalSupplementalEntitySaved
                    .setCpdBugSubTypeId(accountBudgetAdditionalSupplementalSaved.getCpdBugSubTypeId());
            accountBudgetAdditionalSupplementalEntitySaved.setFaYearid(accountBudgetAdditionalSupplementalSaved.getFaYearid());
            accountBudgetAdditionalSupplementalEntitySaved.setDpDeptid(accountBudgetAdditionalSupplementalSaved.getDpDeptid());
            accountBudgetAdditionalSupplementalEntitySaved.setRemark(accountBudgetAdditionalSupplementalSaved.getRemark());
            accountBudgetAdditionalSupplementalEntitySaved
                    .setBudgetIdentifyFlag(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG);
            final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALE,
                    PrefixConstants.REV_PREFIX, langId, orgId);
            accountBudgetAdditionalSupplementalEntitySaved.setCpdProvtypeId(new BigDecimal(revenueTypeLookup.getLookUpId()));

            final Long prProjectionid = accountBudgetAdditionalSupplementalSaved.getPrProjectionid();
            AccountBudgetProjectedRevenueEntryEntity accountBudgetProjectedRevenueEntryEntity = null;
            AccountBudgetProjectedRevenueEntryBean budgetProjectedRevenueEntry = null;
            if (prProjectionid == null) {
                budgetProjectedRevenueEntry = new AccountBudgetProjectedRevenueEntryBean();
                budgetProjectedRevenueEntry.setOrgid(tbAcBudgetAdditionalSupplemental.getOrgid());
                budgetProjectedRevenueEntry.setLangId(tbAcBudgetAdditionalSupplemental.getLangId());
                budgetProjectedRevenueEntry.setUserId(tbAcBudgetAdditionalSupplemental.getUserId());
                budgetProjectedRevenueEntry.setLmoddate(tbAcBudgetAdditionalSupplemental.getLmoddate());
                budgetProjectedRevenueEntry.setLgIpMac(tbAcBudgetAdditionalSupplemental.getLgIpMac());
                budgetProjectedRevenueEntry.setFaYearid(tbAcBudgetAdditionalSupplemental.getFaYearid());
                budgetProjectedRevenueEntry.setCpdBugsubtypeId(tbAcBudgetAdditionalSupplemental.getCpdBugSubTypeId());
                budgetProjectedRevenueEntry.setDpDeptid(tbAcBudgetAdditionalSupplemental.getDpDeptid());
                budgetProjectedRevenueEntry.setPrBudgetCodeid(accountBudgetAdditionalSupplementalSaved.getPrBudgetCodeid());
                budgetProjectedRevenueEntry
                        .setOrginalEstamt(accountBudgetAdditionalSupplementalSaved.getOrgRevBalamt().toString());
                BigDecimal revisedEstamt = accountBudgetAdditionalSupplementalSaved.getNewOrgRevAmount();
                revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                final BigDecimal projectedAmount = revisedEstamt;
                budgetProjectedRevenueEntry.setRevisedEstamt(projectedAmount.toString());
                budgetProjectedRevenueEntry.setPrProjected(projectedAmount.toString());
                accountBudgetProjectedRevenueEntryEntity = new AccountBudgetProjectedRevenueEntryEntity();
                accountBudgetProjectedRevenueEntryServiceMapper
                        .mapAccountBudgetProjectedRevenueEntryBeanToAccountBudgetProjectedRevenueEntryBeanEntity(
                                budgetProjectedRevenueEntry, accountBudgetProjectedRevenueEntryEntity);
                accountBudgetProjectedRevenueEntryJpaRepository.save(accountBudgetProjectedRevenueEntryEntity);

                if (accountBudgetProjectedRevenueEntryEntity.getPrProjectionid() != null) {
                    accountBudgetAdditionalSupplementalEntitySaved
                            .setPrProjectionid(accountBudgetProjectedRevenueEntryEntity.getPrProjectionid());
                }
            }

            accountBudgetAdditionalSupplementalEntitySaved = accountBudgetAdditionalSupplementalJpaRepository
                    .save(accountBudgetAdditionalSupplementalEntitySaved);
        }
    }

    private AccountBudgetAdditionalSupplementalBean setAdditionalSupplementalBudget(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            AccountBudgetAdditionalSupplementalBean acBudgetAdditionalSupplemental, final int langId, final Organisation orgId)
            throws ParseException {
        acBudgetAdditionalSupplemental = new AccountBudgetAdditionalSupplementalBean();
        if ((tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) && (tbAcBudgetAdditionalSupplemental.getPaAdjid() > 0l)) {
            acBudgetAdditionalSupplemental.setPaAdjid(tbAcBudgetAdditionalSupplemental.getPaAdjid());
        }
        acBudgetAdditionalSupplemental.setOrgid(tbAcBudgetAdditionalSupplemental.getOrgid());
        acBudgetAdditionalSupplemental.setLangId(tbAcBudgetAdditionalSupplemental.getLangId());
        acBudgetAdditionalSupplemental.setUserId(tbAcBudgetAdditionalSupplemental.getUserId());
        acBudgetAdditionalSupplemental.setLmoddate(tbAcBudgetAdditionalSupplemental.getLmoddate());
        if (tbAcBudgetAdditionalSupplemental.getPaAdjid() != null) {
            acBudgetAdditionalSupplemental.setApprovedBy(tbAcBudgetAdditionalSupplemental.getApprovedBy());
            acBudgetAdditionalSupplemental.setUpdatedBy(tbAcBudgetAdditionalSupplemental.getUpdatedBy());
            acBudgetAdditionalSupplemental.setUpdatedDate(tbAcBudgetAdditionalSupplemental.getUpdatedDate());
        }
        acBudgetAdditionalSupplemental.setPaEntrydate(new Date());

        acBudgetAdditionalSupplemental.setFaYearid(tbAcBudgetAdditionalSupplemental.getFaYearid());
        acBudgetAdditionalSupplemental.setCpdBugtypeId(tbAcBudgetAdditionalSupplemental.getCpdBugtypeId());
        acBudgetAdditionalSupplemental.setCpdBugSubTypeId(tbAcBudgetAdditionalSupplemental.getCpdBugSubTypeId());
        acBudgetAdditionalSupplemental.setDpDeptid(tbAcBudgetAdditionalSupplemental.getDpDeptid());
        acBudgetAdditionalSupplemental.setRemark(tbAcBudgetAdditionalSupplemental.getRemark());
        acBudgetAdditionalSupplemental.setBudgetIdentifyFlag(MainetConstants.BUDGET_ADDITIONALSUPPLEMENTAL.BUDGET_IDENTIFY_FLAG);
        acBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.N);
        if ((tbAcBudgetAdditionalSupplemental.getApproved() != null)
                && !tbAcBudgetAdditionalSupplemental.getApproved().isEmpty()) {
            acBudgetAdditionalSupplemental.setAuthFlag(tbAcBudgetAdditionalSupplemental.getApproved());
        }
        final LookUp revenueTypeLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_TYPE_CPD_VALE,
                PrefixConstants.REV_PREFIX, langId, orgId);
        acBudgetAdditionalSupplemental.setCpdProvtypeId(revenueTypeLookup.getLookUpId());
        return acBudgetAdditionalSupplemental;
    }

    private void budgetAdditionalSupplementalRevenueDataStored(
            final AccountBudgetAdditionalSupplementalBean tbAccountBudgetAdditionalSupplemental,
            final List<AccountBudgetAdditionalSupplementalBean> tbAcBudgetAdditionalSupplementalList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean, final Organisation orgid)
            throws ParseException {
        if (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null) {
            tbAccountBudgetAdditionalSupplemental
                    .setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic());
        }
        tbAccountBudgetAdditionalSupplemental.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean
                .getPrRevBudgetCode().replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));

        if (accountBudgetProjectedRevenueEntryBean.getOrginalEstamt() != null) {
            tbAccountBudgetAdditionalSupplemental
                    .setOrgRevBalamt(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getOrginalEstamt()));
        } else {
            tbAccountBudgetAdditionalSupplemental.setOrgRevBalamt(new BigDecimal(0));
        }
        tbAccountBudgetAdditionalSupplemental.setTransferAmount(accountBudgetProjectedRevenueEntryBean.getTransferAmount());
        tbAccountBudgetAdditionalSupplemental.setNewOrgRevAmount(accountBudgetProjectedRevenueEntryBean.getNewOrgRevAmount());
        final LookUp lkp = CommonMasterUtility.getValueFromPrefixLookUp(AccountPrefix.SPL.name(), AccountPrefix.AUT.name(),
                orgid);
        final String isMakerChecker = lkp.getOtherField();
        if (isMakerChecker != null && !isMakerChecker.isEmpty()) {
            if (isMakerChecker.equals(MainetConstants.MENU.N)) {
                final Long prProjectionid = tbAccountBudgetAdditionalSupplemental.getPrProjectionid();
                if (prProjectionid != null) {
                    tbAccountBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.Y);
                    final Long orgId = tbAccountBudgetAdditionalSupplemental.getOrgid();
                    final Long faYearid = tbAccountBudgetAdditionalSupplemental.getFaYearid();
                    BigDecimal revisedEstamt = tbAccountBudgetAdditionalSupplemental.getNewOrgRevAmount();
                    revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                    // final BigDecimal projectedAmount = revisedEstamt;
                    accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                            revisedEstamt.toString(), orgId);
                }
            }
            if (isMakerChecker.equals(MainetConstants.MENU.Y)) {
                if (tbAccountBudgetAdditionalSupplemental.getAuthFlag().equals(MainetConstants.MENU.Y)) {
                    final Long prProjectionid = tbAccountBudgetAdditionalSupplemental.getPrProjectionid();
                    if (prProjectionid != null) {
                        tbAccountBudgetAdditionalSupplemental.setAuthFlag(MainetConstants.MENU.Y);
                        final Long orgId = tbAccountBudgetAdditionalSupplemental.getOrgid();
                        final Long faYearid = tbAccountBudgetAdditionalSupplemental.getFaYearid();
                        BigDecimal revisedEstamt = tbAccountBudgetAdditionalSupplemental.getNewOrgRevAmount();
                        revisedEstamt = revisedEstamt.setScale(2, RoundingMode.CEILING);
                        // final BigDecimal projectedAmount = revisedEstamt;
                        accountBudgetProjectedRevenueEntryJpaRepository.updateRevisedEstmtDataRevTable(faYearid, prProjectionid,
                                revisedEstamt.toString(), orgId);
                    }
                }
            }
        }
        tbAcBudgetAdditionalSupplementalList.add(tbAccountBudgetAdditionalSupplemental);
    }

    @Override
    @Transactional
    public AccountBudgetAdditionalSupplementalBean getDetailsUsingBudgetAdditionalSupplementalId(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental, final int langId,
            final Organisation orgId) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, langId, orgId);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, langId, orgId);
        final Long PaAdjid = tbAcBudgetAdditionalSupplemental.getPaAdjid();
        final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity = accountBudgetAdditionalSupplementalJpaRepository
                .findOne(PaAdjid);
        if (accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingAdditionalSupplementalRevenueData(
                    tbAcBudgetAdditionalSupplemental,
                    accountBudgetAdditionalSupplementalEntity);
        } else if (accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingAdditionalSupplementalExpenditureData(
                    tbAcBudgetAdditionalSupplemental,
                    accountBudgetAdditionalSupplementalEntity);
        }
        return tbAcBudgetAdditionalSupplemental;
    }

    private void gettingAdditionalSupplementalExpenditureData(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity) {
        tbAcBudgetAdditionalSupplemental
                .setTempDate(UtilityService.convertDateToDDMMYYYY(accountBudgetAdditionalSupplementalEntity.getLmoddate()));
        tbAcBudgetAdditionalSupplemental.setFaYearid(accountBudgetAdditionalSupplementalEntity.getFaYearid());
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeId(accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId());
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeDesc(
                CommonMasterUtility.findLookUpDesc(AccountPrefix.REX.name(), accountBudgetAdditionalSupplementalEntity.getOrgid(),
                        accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId()));
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeIdHidden(
                CommonMasterUtility.findLookUpCode(AccountPrefix.REX.name(), accountBudgetAdditionalSupplementalEntity.getOrgid(),
                        accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId()));
        if (accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetAdditionalSupplemental.setCpdBugSubTypeId(accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId());
            tbAcBudgetAdditionalSupplemental.setCpdBugsubtypeDesc(
                    CommonMasterUtility.findLookUpDesc(PrefixConstants.FTP, accountBudgetAdditionalSupplementalEntity.getOrgid(),
                            accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetAdditionalSupplemental.setDpDeptid(accountBudgetAdditionalSupplementalEntity.getDpDeptid());
        tbAcBudgetAdditionalSupplemental.setDepartmentDesc(
                departmentRepository.fetchDepartmentDescById(accountBudgetAdditionalSupplementalEntity.getDpDeptid()));
        tbAcBudgetAdditionalSupplemental.setRemark(accountBudgetAdditionalSupplementalEntity.getRemark());
        tbAcBudgetAdditionalSupplemental.setPaAdjid(accountBudgetAdditionalSupplementalEntity.getPaAdjid());
        if ((accountBudgetAdditionalSupplementalEntity.getAuthFlag() != null)
                && !accountBudgetAdditionalSupplementalEntity.getAuthFlag().isEmpty()) {
            tbAcBudgetAdditionalSupplemental.setApproved(accountBudgetAdditionalSupplementalEntity.getAuthFlag());
        }

        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();
        if ((accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrExpBudgetCode(
                    accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }
        if (accountBudgetAdditionalSupplementalEntity.getPrExpenditureid() != null) {
            bean.setPrExpenditureidExpDynamic(accountBudgetAdditionalSupplementalEntity.getPrExpenditureid());
        }
        if (accountBudgetAdditionalSupplementalEntity.getOrgRevBalamt() != null) {
            BigDecimal orgRevBalamt = accountBudgetAdditionalSupplementalEntity.getOrgRevBalamt();
            orgRevBalamt = orgRevBalamt.setScale(2, RoundingMode.CEILING);
            bean.setOrginalEstamt(orgRevBalamt.toString());
        }
        if (accountBudgetAdditionalSupplementalEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetAdditionalSupplementalEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            bean.setTransferAmountO(transferAmount);
        }
        if (accountBudgetAdditionalSupplementalEntity.getNewOrgRevAmount() != null) {
            BigDecimal newOrgRevAmount = accountBudgetAdditionalSupplementalEntity.getNewOrgRevAmount();
            newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
            bean.setNewOrgExpAmountO(newOrgRevAmount);
        }
        tbAcBudgetExpList.add(bean);
        tbAcBudgetAdditionalSupplemental.setBugprojExpBeanList(tbAcBudgetExpList);
    }

    /**
     * @param tbAcBudgetAdditionalSupplemental
     * @param accountBudgetAdditionalSupplementalEntity
     * @param accountBudgetReappropriationTrMasterEntity
     */
    private void gettingAdditionalSupplementalRevenueData(
            final AccountBudgetAdditionalSupplementalBean tbAcBudgetAdditionalSupplemental,
            final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity) {
        tbAcBudgetAdditionalSupplemental
                .setTempDate(UtilityService.convertDateToDDMMYYYY(accountBudgetAdditionalSupplementalEntity.getLmoddate()));
        tbAcBudgetAdditionalSupplemental.setFaYearid(accountBudgetAdditionalSupplementalEntity.getFaYearid());
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeId(accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId());
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeDesc(
                CommonMasterUtility.findLookUpDesc(AccountPrefix.REX.name(), accountBudgetAdditionalSupplementalEntity.getOrgid(),
                        accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId()));
        tbAcBudgetAdditionalSupplemental.setCpdBugtypeIdHidden(
                CommonMasterUtility.findLookUpCode(AccountPrefix.REX.name(), accountBudgetAdditionalSupplementalEntity.getOrgid(),
                        accountBudgetAdditionalSupplementalEntity.getCpdBugtypeId()));
        if (accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId() != null) {
            tbAcBudgetAdditionalSupplemental.setCpdBugSubTypeId(accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId());
            tbAcBudgetAdditionalSupplemental.setCpdBugsubtypeDesc(
                    CommonMasterUtility.findLookUpDesc(PrefixConstants.FTP, accountBudgetAdditionalSupplementalEntity.getOrgid(),
                            accountBudgetAdditionalSupplementalEntity.getCpdBugSubTypeId()));
        }
        tbAcBudgetAdditionalSupplemental.setDpDeptid(accountBudgetAdditionalSupplementalEntity.getDpDeptid());
        tbAcBudgetAdditionalSupplemental.setDepartmentDesc(
                departmentRepository.fetchDepartmentDescById(accountBudgetAdditionalSupplementalEntity.getDpDeptid()));
        tbAcBudgetAdditionalSupplemental.setRemark(accountBudgetAdditionalSupplementalEntity.getRemark());
        tbAcBudgetAdditionalSupplemental.setPaAdjid(accountBudgetAdditionalSupplementalEntity.getPaAdjid());
        if ((accountBudgetAdditionalSupplementalEntity.getAuthFlag() != null)
                && !accountBudgetAdditionalSupplementalEntity.getAuthFlag().isEmpty()) {
            tbAcBudgetAdditionalSupplemental.setApproved(accountBudgetAdditionalSupplementalEntity.getAuthFlag());
        }

        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();
        if ((accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster() != null)
                && (accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid() != null)) {
            bean.setPrRevBudgetCode(
                    accountBudgetAdditionalSupplementalEntity.getTbAcBudgetCodeMaster().getPrBudgetCodeid().toString());
        }

        if (accountBudgetAdditionalSupplementalEntity.getPrProjectionid() != null) {
            bean.setPrProjectionidRevDynamic(accountBudgetAdditionalSupplementalEntity.getPrProjectionid());
        }

        if (accountBudgetAdditionalSupplementalEntity.getOrgRevBalamt() != null) {
            BigDecimal orgRevBalamt = accountBudgetAdditionalSupplementalEntity.getOrgRevBalamt();
            orgRevBalamt = orgRevBalamt.setScale(2, RoundingMode.CEILING);
            bean.setOrginalEstamt(orgRevBalamt.toString());
        }
        if (accountBudgetAdditionalSupplementalEntity.getTransferAmount() != null) {
            BigDecimal transferAmount = accountBudgetAdditionalSupplementalEntity.getTransferAmount();
            transferAmount = transferAmount.setScale(2, RoundingMode.CEILING);
            bean.setTransferAmount(transferAmount);
        }
        if (accountBudgetAdditionalSupplementalEntity.getNewOrgRevAmount() != null) {
            BigDecimal newOrgRevAmount = accountBudgetAdditionalSupplementalEntity.getNewOrgRevAmount();
            newOrgRevAmount = newOrgRevAmount.setScale(2, RoundingMode.CEILING);
            bean.setNewOrgRevAmount(newOrgRevAmount);
        }
        tbAcBudgetRevenueList.add(bean);
        tbAcBudgetAdditionalSupplemental.setBugprojRevBeanList(tbAcBudgetRevenueList);
    }

    @Override
    @Transactional
    public List<AccountBudgetAdditionalSupplementalBean> findBudgetAdditionalSupplementalByOrg(final Long orgId,
            final String budgIdentifyFlag) {
        final List<AccountBudgetAdditionalSupplementalEntity> entities = accountBudgetAdditionalSupplementalJpaRepository
                .findByOrgId(orgId, budgIdentifyFlag);
        final List<AccountBudgetAdditionalSupplementalBean> bean = new ArrayList<>();
        for (final AccountBudgetAdditionalSupplementalEntity accountBudgetAdditionalSupplementalEntity : entities) {
            bean.add(accountBudgetAdditionalSupplementalServiceMapper
                    .mapAccountBudgetAdditionalSupplementalBeanEntityToAccountBudgetAdditionalSupplementalBean(
                            accountBudgetAdditionalSupplementalEntity));
        }
        return bean;
    }

}
