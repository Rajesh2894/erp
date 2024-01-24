
package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetAllocationDao;
import com.abm.mainet.account.domain.AccountBudgetAllocationEntity;
import com.abm.mainet.account.domain.AccountBudgetAllocationHistoryEntity;
import com.abm.mainet.account.dto.AccountBudgetAllocationBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureBean;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryBean;
import com.abm.mainet.account.mapper.AccountBudgetAllocationServiceMapper;
import com.abm.mainet.account.repository.BudgetAllocationRepository;
import com.abm.mainet.account.repository.BudgetProjectedExpenditureRepository;
import com.abm.mainet.account.repository.BudgetProjectedRevenueRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.repository.TbDepartmentJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UtilityService;

@Component
public class AccountBudgetAllocationServiceImpl implements AccountBudgetAllocationService {

    @Resource
    private AccountBudgetAllocationServiceMapper accountBudgetAllocationServiceMapper;

    @Resource
    private BudgetAllocationRepository accountBudgetAllocationJpaRepository;

    @Resource
    BudgetProjectedRevenueRepository accountBudgetProjectedRevenueEntryJpaRepository;

    @Resource
    BudgetProjectedExpenditureRepository accountBudgetProjectedExpenditureJpaRepository;

    @Resource
    private AccountBudgetAllocationDao accountBudgetAllocationDao;

    @Resource
    private AuditService auditService;
    @Resource
    private TbDepartmentJpaRepository departmentRepository;

    private static Logger LOGGER = Logger.getLogger(AccountBudgetAllocationServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetAllocationBean> findBudgetAllocationByFinYearId(final Long faYearid, final Long orgId) {
        final List<AccountBudgetAllocationEntity> entities = accountBudgetAllocationJpaRepository.findByFinancialIds(faYearid,
                orgId);
        final List<AccountBudgetAllocationBean> accountBudgetAllocationList = new ArrayList<>();
        for (final AccountBudgetAllocationEntity accountBudgetAllocationEntity : entities) {
            accountBudgetAllocationList.add(accountBudgetAllocationServiceMapper
                    .mapAccountBudgetAllocationEntityToTbAcBudgetallocation(accountBudgetAllocationEntity));
        }
        return accountBudgetAllocationList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetAllocationBean> findByGridAllData(final Long faYearid, final Long cpdBugtypeId, final Long dpDeptid,
            final Long prBudgetCodeid, final Long orgId) {
        final List<AccountBudgetAllocationEntity> entities = accountBudgetAllocationDao.findByGridAllData(faYearid, cpdBugtypeId,
                dpDeptid, prBudgetCodeid, orgId);
        final List<AccountBudgetAllocationBean> accountBudgetAllocationList = new ArrayList<>();
        for (final AccountBudgetAllocationEntity accountBudgetAllocationEntity : entities) {
            accountBudgetAllocationList.add(accountBudgetAllocationServiceMapper
                    .mapAccountBudgetAllocationEntityToTbAcBudgetallocation(accountBudgetAllocationEntity));
        }
        return accountBudgetAllocationList;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> findBudgetIdCodeFromBudgetAllocation(final Long faYearid, final Long fundId, final Long functionId,
            final Long cpdBugtypeId,
            final Long prBudgetCodeid, final Long dpDeptid, final Long orgId) {

        final List<Object[]> findById = accountBudgetAllocationDao.findByAllBudgetCodeId(faYearid, fundId, functionId,
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
    @Transactional(readOnly = true)
    public Boolean isBudgetAllocationEntryExists(final Long faYearid, final Long prRevBudgetCode, final Long orgId) {

        return accountBudgetAllocationDao.isCombinationExists(faYearid, prRevBudgetCode, orgId);
    }

    @Override
    @Transactional
    public AccountBudgetAllocationBean saveBudgetAllocationFormData(final AccountBudgetAllocationBean tbAcBudgetAllocation,
            final int LanguageId, final Organisation Organisation) throws ParseException {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);

        if (tbAcBudgetAllocation.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            budgetAllocationRevenueAllDataStored(tbAcBudgetAllocation);
        } else if (tbAcBudgetAllocation.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            budgetAllocationExpenditureAllDataStored(tbAcBudgetAllocation);
        }
        return tbAcBudgetAllocation;
    }

    /**
     * @param tbAcBudgetAllocation
     * @throws ParseException
     */
    private void budgetAllocationExpenditureAllDataStored(
            final AccountBudgetAllocationBean tbAcBudgetAllocation)
            throws ParseException {
        final List<AccountBudgetAllocationBean> acBudgetAllocationList = new ArrayList<>();
        AccountBudgetAllocationEntity acBudgetAllocationEntitySaved = null;
        AccountBudgetAllocationBean acBudgetAllocation = null;
        for (final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean : tbAcBudgetAllocation
                .getBugprojExpBeanList()) {
            acBudgetAllocation = setAllocationBudget(tbAcBudgetAllocation, acBudgetAllocation);
            budgetAllocationExpenditureDataStored(
                    acBudgetAllocation,
                    acBudgetAllocationList,
                    accountBudgetProjectedExpenditureBean);
        }
        for (final AccountBudgetAllocationBean tbaccountBudgetAllocationSaved : acBudgetAllocationList) {
            acBudgetAllocationEntitySaved = new AccountBudgetAllocationEntity();
            AccountBudgetAllocationHistoryEntity acBudgetAllocationHistoryEntity = null;
            accountBudgetAllocationServiceMapper.mapTbAcBudgetallocationToAccountBudgetAllocationEntity(
                    tbaccountBudgetAllocationSaved, acBudgetAllocationEntitySaved);
            acBudgetAllocationEntitySaved.setCpdBugtypeId(tbaccountBudgetAllocationSaved.getCpdBugtypeId());
            acBudgetAllocationEntitySaved.setCpdBugsubtypeId(tbaccountBudgetAllocationSaved.getCpdBugsubtypeId());
            acBudgetAllocationEntitySaved.setFinancialYear(tbaccountBudgetAllocationSaved.getFaYearid());
            acBudgetAllocationEntitySaved.setDepartment(tbaccountBudgetAllocationSaved.getDpDeptid());
            acBudgetAllocationEntitySaved.setLgIpMac(tbAcBudgetAllocation.getLgIpMac());

            if ((acBudgetAllocationEntitySaved.getBaId() != null) && (acBudgetAllocationEntitySaved.getBaId() > 0)) {
                acBudgetAllocationHistoryEntity = new AccountBudgetAllocationHistoryEntity();
                acBudgetAllocationHistoryEntity.setPrExpenditureid(tbaccountBudgetAllocationSaved.getPrExpenditureid());
                acBudgetAllocationHistoryEntity.setPrBudgetCodeid(tbaccountBudgetAllocationSaved.getPrBudgetCodeid());
                try {
                    auditService.createHistory(acBudgetAllocationEntitySaved, acBudgetAllocationHistoryEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + acBudgetAllocationEntitySaved, ex);
                }
            }
            acBudgetAllocationEntitySaved = accountBudgetAllocationJpaRepository.save(acBudgetAllocationEntitySaved);
        }
    }

    /**
     * @param tbAcBudgetAllocation2
     * @param tbAcBudgetAllocationList1
     * @param accountBudgetProjectedExpenditureBean
     * @throws ParseException
     */
    private void budgetAllocationExpenditureDataStored(
            final AccountBudgetAllocationBean tbAccountBudgetAllocation,
            final List<AccountBudgetAllocationBean> accountBudgetAllocationList,
            final AccountBudgetProjectedExpenditureBean accountBudgetProjectedExpenditureBean) throws ParseException {
        if (accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic() != null) {
            tbAccountBudgetAllocation.setPrExpenditureid(accountBudgetProjectedExpenditureBean.getPrExpenditureidExpDynamic());
        }
        final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
        tbAccountBudgetAllocation.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedExpenditureBean.getPrExpBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        tbAccountBudgetAllocation.setValidtillDate(sdf.parse(accountBudgetProjectedExpenditureBean.getExpBudgetControlDate()));
        tbAccountBudgetAllocation.setReleasePer(new BigDecimal(accountBudgetProjectedExpenditureBean.getExpAllocation()));
        accountBudgetAllocationList.add(tbAccountBudgetAllocation);
    }

    /**
     * @param tbAcBudgetAllocation
     * @throws ParseException
     */
    private void budgetAllocationRevenueAllDataStored(
            final AccountBudgetAllocationBean tbAcBudgetAllocation)
            throws ParseException {

        final List<AccountBudgetAllocationBean> tbAcBudgetAllocationList = new ArrayList<>();
        AccountBudgetAllocationEntity accountBudgetAllocationEntitySaved = null;
        AccountBudgetAllocationHistoryEntity accountBudgetAllocationHistoryEntity = null;
        AccountBudgetAllocationBean acBudgetAllocation = null;
        for (final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean : tbAcBudgetAllocation
                .getBugprojRevBeanList()) {
            acBudgetAllocation = setAllocationBudget(tbAcBudgetAllocation, acBudgetAllocation);
            budgetAllocationRevenueDataStored(
                    acBudgetAllocation,
                    tbAcBudgetAllocationList,
                    accountBudgetProjectedRevenueEntryBean);
        }
        for (final AccountBudgetAllocationBean accountBudgetAllocationSaved : tbAcBudgetAllocationList) {
            accountBudgetAllocationEntitySaved = new AccountBudgetAllocationEntity();
            accountBudgetAllocationServiceMapper.mapTbAcBudgetallocationToAccountBudgetAllocationEntity(
                    accountBudgetAllocationSaved, accountBudgetAllocationEntitySaved);
            accountBudgetAllocationEntitySaved.setCpdBugtypeId(accountBudgetAllocationSaved.getCpdBugtypeId());
            accountBudgetAllocationEntitySaved.setCpdBugsubtypeId(accountBudgetAllocationSaved.getCpdBugsubtypeId());
            accountBudgetAllocationEntitySaved.setFinancialYear(accountBudgetAllocationSaved.getFaYearid());
            accountBudgetAllocationEntitySaved.setDepartment(accountBudgetAllocationSaved.getDpDeptid());
            accountBudgetAllocationEntitySaved.setLgIpMac(tbAcBudgetAllocation.getLgIpMac());

            if ((accountBudgetAllocationEntitySaved.getBaId() != null) && (accountBudgetAllocationEntitySaved.getBaId() > 0)) {
                accountBudgetAllocationHistoryEntity = new AccountBudgetAllocationHistoryEntity();
                accountBudgetAllocationHistoryEntity.setPrProjectionid(accountBudgetAllocationSaved.getPrProjectionid());
                accountBudgetAllocationHistoryEntity.setPrBudgetCodeid(accountBudgetAllocationSaved.getPrBudgetCodeid());
                try {
                    auditService.createHistory(accountBudgetAllocationEntitySaved, accountBudgetAllocationHistoryEntity);
                } catch (Exception ex) {
                    LOGGER.error("Could not make audit entry for " + accountBudgetAllocationEntitySaved, ex);
                }
            }
            accountBudgetAllocationEntitySaved = accountBudgetAllocationJpaRepository.save(accountBudgetAllocationEntitySaved);
        }
    }

    /**
     * @param tbAcBudgetAllocation
     * @param acBudgetAllocation
     * @return
     * @throws ParseException
     */
    private AccountBudgetAllocationBean setAllocationBudget(
            final AccountBudgetAllocationBean tbAcBudgetAllocation, AccountBudgetAllocationBean acBudgetAllocation)
            throws ParseException {
        acBudgetAllocation = new AccountBudgetAllocationBean();
        if ((tbAcBudgetAllocation.getBaId() != null) && (tbAcBudgetAllocation.getBaId() > 0l)) {
            acBudgetAllocation.setBaId(tbAcBudgetAllocation.getBaId());
        }
        acBudgetAllocation.setOrgid(tbAcBudgetAllocation.getOrgid());
        acBudgetAllocation.setLangId(tbAcBudgetAllocation.getLangId());
        acBudgetAllocation.setCreatedBy(tbAcBudgetAllocation.getCreatedBy());
        acBudgetAllocation.setCreatedDate(tbAcBudgetAllocation.getCreatedDate());
        acBudgetAllocation.setBaEntrydate(new Date());
        acBudgetAllocation.setFaYearid(tbAcBudgetAllocation.getFaYearid());
        acBudgetAllocation.setCpdBugtypeId(tbAcBudgetAllocation.getCpdBugtypeId());
        acBudgetAllocation.setCpdBugsubtypeId(tbAcBudgetAllocation.getCpdBugsubtypeId());
        acBudgetAllocation.setDpDeptid(tbAcBudgetAllocation.getDpDeptid());
        return acBudgetAllocation;
    }

    /**
     * @param tbAcBudgetAllocation1
     * @param tbAcBudgetAllocationList
     * @param accountBudgetProjectedRevenueEntryBean
     * @throws ParseException
     */
    private void budgetAllocationRevenueDataStored(
            final AccountBudgetAllocationBean tbAccountBudgetAllocation,
            final List<AccountBudgetAllocationBean> tbAcBudgetAllocationList,
            final AccountBudgetProjectedRevenueEntryBean accountBudgetProjectedRevenueEntryBean) throws ParseException {
        if (accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic() != null) {
            tbAccountBudgetAllocation.setPrProjectionid(accountBudgetProjectedRevenueEntryBean.getPrProjectionidRevDynamic());
        }
        tbAccountBudgetAllocation.setPrBudgetCodeid(Long.valueOf(accountBudgetProjectedRevenueEntryBean.getPrRevBudgetCode()
                .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK)));
        final SimpleDateFormat sdf = new SimpleDateFormat(MainetConstants.CommonConstants.D_FORMATE);
        tbAccountBudgetAllocation.setValidtillDate(sdf.parse(accountBudgetProjectedRevenueEntryBean.getBudgetControlDate()));
        tbAccountBudgetAllocation.setReleasePer(new BigDecimal(accountBudgetProjectedRevenueEntryBean.getAllocation()));
        tbAcBudgetAllocationList.add(tbAccountBudgetAllocation);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetAllocationBean getDetailsUsingBudgetAllocationId(final AccountBudgetAllocationBean tbAcBudgetAllocation,
            final int LanguageId, final Organisation Organisation) {
        final LookUp revenueLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.REV_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final LookUp expLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.EXP_CPD_VALUE,
                PrefixConstants.PREFIX, LanguageId, Organisation);
        final Long BaId = tbAcBudgetAllocation.getBaId();
        final AccountBudgetAllocationEntity accountBudgetAllocationEntity = accountBudgetAllocationJpaRepository.findOne(BaId);
        if (accountBudgetAllocationEntity.getCpdBugtypeId().equals(revenueLookup.getLookUpId())) {
            gettingBudgetAllocationRevenueData(
                    tbAcBudgetAllocation,
                    accountBudgetAllocationEntity);
        }

        else if (accountBudgetAllocationEntity.getCpdBugtypeId().equals(expLookup.getLookUpId())) {
            gettingBudgetAllocationExpenditureData(
                    tbAcBudgetAllocation,
                    accountBudgetAllocationEntity);

        }
        return tbAcBudgetAllocation;
    }

    /**
     * @param tbAcBudgetAllocation
     * @param accountBudgetAllocationEntity
     */
    private void gettingBudgetAllocationExpenditureData(
            final AccountBudgetAllocationBean tbAcBudgetAllocation,
            final AccountBudgetAllocationEntity accountBudgetAllocationEntity) {
        tbAcBudgetAllocation.setFaYearid(accountBudgetAllocationEntity.getFinancialYear());
        tbAcBudgetAllocation.setCpdBugtypeId(accountBudgetAllocationEntity.getCpdBugtypeId());
        tbAcBudgetAllocation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugtypeId()));
        tbAcBudgetAllocation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugtypeId()));
        tbAcBudgetAllocation.setCpdBugsubtypeId(accountBudgetAllocationEntity.getCpdBugsubtypeId());
        tbAcBudgetAllocation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugsubtypeId()));
        tbAcBudgetAllocation.setDpDeptid(accountBudgetAllocationEntity.getDepartment());
        tbAcBudgetAllocation
                .setDepartmentDesc(departmentRepository.fetchDepartmentDescById(accountBudgetAllocationEntity.getDepartment()));
        final Long orgId = accountBudgetAllocationEntity.getOrgid();
        final Long faYearid = accountBudgetAllocationEntity.getFinancialYear();
        final Long budgCodeid = accountBudgetAllocationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
        final AccountBudgetProjectedExpenditureBean bean = new AccountBudgetProjectedExpenditureBean();
        final List<AccountBudgetProjectedExpenditureBean> tbAcBudgetExpList = new ArrayList<>();
        final List<Object[]> orgEsmtAmount = accountBudgetProjectedExpenditureJpaRepository.findByExpOrgAmount(faYearid,
                budgCodeid,
                orgId);
        if (!orgEsmtAmount.isEmpty()) {
            for (final Object[] objects : orgEsmtAmount) {
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(revisedEsmtAmount.toString());
                }
            }
        }
        bean.setPrExpBudgetCode(accountBudgetAllocationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid().toString());
        bean.setExpAllocation(accountBudgetAllocationEntity.getReleasePer().longValue());
        bean.setExpBudgetControlDate(UtilityService.convertDateToDDMMYYYY(accountBudgetAllocationEntity.getValidtillDate()));
        bean.setPrExpenditureidExpDynamic(accountBudgetAllocationEntity.getTbAcProjectedExpenditure().getPrExpenditureid());
        BigDecimal orgEsmtAmt = new BigDecimal(bean.getOrginalEstamt());
        orgEsmtAmt = orgEsmtAmt.setScale(2, RoundingMode.CEILING);
        final BigDecimal allocaionPer = new BigDecimal(bean.getExpAllocation());
        final BigDecimal divDefValue = new BigDecimal(100);
        bean.setExpAmount((orgEsmtAmt.multiply(allocaionPer)).divide(divDefValue));
        tbAcBudgetExpList.add(bean);
        tbAcBudgetAllocation.setBugprojExpBeanList(tbAcBudgetExpList);
    }

    /**
     * @param tbAcBudgetAllocation
     * @param accountBudgetAllocationEntity
     */
    private void gettingBudgetAllocationRevenueData(
            final AccountBudgetAllocationBean tbAcBudgetAllocation,
            final AccountBudgetAllocationEntity accountBudgetAllocationEntity) {
        tbAcBudgetAllocation.setFaYearid(accountBudgetAllocationEntity.getFinancialYear());
        tbAcBudgetAllocation.setCpdBugtypeId(accountBudgetAllocationEntity.getCpdBugtypeId());
        tbAcBudgetAllocation.setCpdBugtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugtypeId()));
        tbAcBudgetAllocation.setCpdBugtypeIdHidden(CommonMasterUtility.findLookUpCode(PrefixConstants.PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugtypeId()));
        tbAcBudgetAllocation.setCpdBugsubtypeId(accountBudgetAllocationEntity.getCpdBugsubtypeId());
        tbAcBudgetAllocation.setCpdBugsubtypeDesc(CommonMasterUtility.findLookUpDesc(PrefixConstants.BUG_SUB_PREFIX,
                accountBudgetAllocationEntity.getOrgid(), accountBudgetAllocationEntity.getCpdBugsubtypeId()));
        tbAcBudgetAllocation.setDpDeptid(accountBudgetAllocationEntity.getDepartment());
        tbAcBudgetAllocation
                .setDepartmentDesc(departmentRepository.fetchDepartmentDescById(accountBudgetAllocationEntity.getDepartment()));
        final Long orgId = accountBudgetAllocationEntity.getOrgid();
        final Long faYearid = accountBudgetAllocationEntity.getFinancialYear();
        final Long budgCodeid = accountBudgetAllocationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid();
        final AccountBudgetProjectedRevenueEntryBean bean = new AccountBudgetProjectedRevenueEntryBean();
        final List<AccountBudgetProjectedRevenueEntryBean> tbAcBudgetRevenueList = new ArrayList<>();
        final List<Object[]> orgEsmtAmount = accountBudgetProjectedRevenueEntryJpaRepository.findByRenueOrgAmount(faYearid,
                budgCodeid,
                orgId);
        if (!orgEsmtAmount.isEmpty()) {
            for (final Object[] objects : orgEsmtAmount) {
                if (objects[0] == null) {
                    BigDecimal originalEstAmount = new BigDecimal(objects[1].toString());
                    originalEstAmount = originalEstAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(originalEstAmount.toString());
                }
                if (objects[0] != null) {
                    BigDecimal revisedEsmtAmount = new BigDecimal(objects[0].toString());
                    revisedEsmtAmount = revisedEsmtAmount.setScale(2, RoundingMode.CEILING);
                    bean.setOrginalEstamt(revisedEsmtAmount.toString());
                }
            }
        }
        bean.setPrRevBudgetCode(accountBudgetAllocationEntity.getTbAcBudgetCodeMaster().getprBudgetCodeid().toString());
        bean.setAllocation(accountBudgetAllocationEntity.getReleasePer().longValue());
        bean.setBudgetControlDate(UtilityService.convertDateToDDMMYYYY(accountBudgetAllocationEntity.getValidtillDate()));
        bean.setPrProjectionidRevDynamic(accountBudgetAllocationEntity.getTbAcProjectedrevenue().getPrProjectionid());
        BigDecimal orgEsmtAmt = new BigDecimal(bean.getOrginalEstamt());
        orgEsmtAmt = orgEsmtAmt.setScale(2, RoundingMode.CEILING);
        final BigDecimal allocaionPer = new BigDecimal(bean.getAllocation());
        final BigDecimal divDefValue = new BigDecimal(100);
        bean.setAmount((orgEsmtAmt.multiply(allocaionPer)).divide(divDefValue));
        tbAcBudgetRevenueList.add(bean);
        tbAcBudgetAllocation.setBugprojRevBeanList(tbAcBudgetRevenueList);
    }
}
