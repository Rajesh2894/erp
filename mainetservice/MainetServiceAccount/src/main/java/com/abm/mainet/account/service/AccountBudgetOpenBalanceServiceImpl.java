package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.dao.AccountBudgetOpenBalanceDao;
import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;
import com.abm.mainet.account.domain.AccountBudgetOpenBalanceHistEntity;
import com.abm.mainet.account.dto.AccountBudgetOpenBalanceBean;
import com.abm.mainet.account.dto.AccountBudgetOpenBalanceUploadDto;
import com.abm.mainet.account.mapper.AccountBudgetOpenBalanceServiceMapper;
import com.abm.mainet.account.repository.BudgetOpenBalanceRepository;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountBudgetOpenBalanceMasterDto;
import com.abm.mainet.common.integration.acccount.repository.SecondaryheadMasterJpaRepository;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Component
public class AccountBudgetOpenBalanceServiceImpl implements AccountBudgetOpenBalanceService {

    @Resource
    private BudgetOpenBalanceRepository tbAcBugopenBalanceJpaRepository;

    @Resource
    private AccountBudgetOpenBalanceServiceMapper tbAcBugopenBalanceServiceMapper;

    @Resource
    private SecondaryheadMasterJpaRepository tbAcSecondaryheadMasterJpaRepository;

    @Resource
    private AccountBudgetOpenBalanceDao accountBudgetOpenBalanceDao;

    @Resource
    private AuditService auditService;

    private static Logger LOGGER = Logger.getLogger(AccountBudgetOpenBalanceServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public Boolean isCombinationExists(final Long faYearid, final Long fundId, final Long fieldId,
            final String cpdIdDrcr, final Long sacHeadId, final Long orgId) {

        return accountBudgetOpenBalanceDao.isCombinationExists(faYearid, fundId, fieldId, cpdIdDrcr, sacHeadId, orgId);
    }

    @Override
    @Transactional
    public AccountBudgetOpenBalanceBean saveBudgetOpeningBalanceExcelSheetData(
            final AccountBudgetOpenBalanceBean tbAcBudgetMaster) throws ParseException {

        tbAcBudgetMaster.setOpnEntrydate(new Date());
        AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = null;
        budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
        tbAcBugopenBalanceServiceMapper.mapTbAcBugopenBalanceToTbAcBugopenBalanceEntity(tbAcBudgetMaster,
                budgetOpenBalanceEntity);
        tbAcBugopenBalanceJpaRepository.save(budgetOpenBalanceEntity);
        return tbAcBudgetMaster;
    }

    @Override
    @Transactional
    public AccountBudgetOpenBalanceBean saveBudgetOpeningBalanceFormData(
            final AccountBudgetOpenBalanceBean tbAcBudgetMaster) throws ParseException {
        final AccountBudgetOpenBalanceBean budgetOpenBalance = tbAcBudgetMaster;
        final LookUp drLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_LIABILITY,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        final LookUp crLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_BAL_TYPE_ASSETS,
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, UserSession.getCurrent().getLanguageId(),
                UserSession.getCurrent().getOrganisation());
        budgetOpenBalance.setOpnEntrydate(new Date());
        AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = null;
        AccountBudgetOpenBalanceHistEntity accountBudgetOpenBalanceHistEntity = null;
        final List<AccountBudgetOpenBalanceMasterDto> budgetOpenBalanceDto = tbAcBudgetMaster
                .getBugReappMasterDtoList();
        if ((budgetOpenBalanceDto != null) && !budgetOpenBalanceDto.isEmpty()) {
            for (final AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto : budgetOpenBalanceDto) {
                budgetOpenBalance.setSacHeadId(accountBudgetOpenBalanceMasterDto.getSacHeadId());
                if ((accountBudgetOpenBalanceMasterDto.getOpenbalAmt() != null)
                        && !accountBudgetOpenBalanceMasterDto.getOpenbalAmt().isEmpty()) {
                    final String bal = accountBudgetOpenBalanceMasterDto.getOpenbalAmt()
                            .replace(MainetConstants.operator.COMMA, MainetConstants.CommonConstants.BLANK);
                    budgetOpenBalance.setOpenbalAmt(bal);
                } else {
                    budgetOpenBalance.setOpenbalAmt(accountBudgetOpenBalanceMasterDto.getOpenbalAmt());
                }
                if ((accountBudgetOpenBalanceMasterDto.getFlagFlzd() != null)
                        && !accountBudgetOpenBalanceMasterDto.getFlagFlzd().isEmpty()) {
                    budgetOpenBalance.setFlagFlzd(accountBudgetOpenBalanceMasterDto.getFlagFlzd());
                } else {
                    budgetOpenBalance.setFlagFlzd(MainetConstants.MASTER.N);
                }
                if ((accountBudgetOpenBalanceMasterDto.getCpdIdDrcr() != null)
                        && !accountBudgetOpenBalanceMasterDto.getCpdIdDrcr().isEmpty()) {
                    budgetOpenBalance.setCpdIdDrcr(accountBudgetOpenBalanceMasterDto.getCpdIdDrcr());
                } else {
                    if (tbAcBudgetMaster.getOpnBalType()
                            .equals(MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_LIVE_VALUE)) {
                        budgetOpenBalance.setCpdIdDrcr(String.valueOf(drLookup.getLookUpId()));
                    } else if (tbAcBudgetMaster.getOpnBalType().equals(MainetConstants.MENU.A)) {
                        budgetOpenBalance.setCpdIdDrcr(String.valueOf(crLookup.getLookUpId()));
                    }
                }
                budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                tbAcBugopenBalanceServiceMapper.mapTbAcBugopenBalanceToTbAcBugopenBalanceEntity(budgetOpenBalance,
                        budgetOpenBalanceEntity);
                AccountBudgetOpenBalanceEntity finalEntity = tbAcBugopenBalanceJpaRepository
                        .save(budgetOpenBalanceEntity);

                if ((budgetOpenBalance.getOpnId() != null) && (budgetOpenBalance.getOpnId() > 0L)) {
                    accountBudgetOpenBalanceHistEntity = new AccountBudgetOpenBalanceHistEntity();
                    accountBudgetOpenBalanceHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_U);
                    accountBudgetOpenBalanceHistEntity
                            .setTbAcSecondaryheadMaster(finalEntity.getTbAcSecondaryheadMaster().getSacHeadId());
                    if ((finalEntity.getTbComparamDet() != null)
                            && (finalEntity.getTbComparamDet().getCpdId() != null)) {
                        accountBudgetOpenBalanceHistEntity.setTbComparamDet(finalEntity.getTbComparamDet().getCpdId());
                    }
                    if ((finalEntity.getTbAcFundMaster() != null)
                            && (finalEntity.getTbAcFundMaster().getFundId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFundMaster(finalEntity.getTbAcFundMaster().getFundId());
                    }
                    if ((finalEntity.getTbAcFieldMaster() != null)
                            && (finalEntity.getTbAcFieldMaster().getFieldId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFieldMaster(finalEntity.getTbAcFieldMaster().getFieldId());
                    }
                    if ((finalEntity.getTbAcFunctionMaster() != null)
                            && (finalEntity.getTbAcFunctionMaster().getFunctionId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFunctionMaster(finalEntity.getTbAcFunctionMaster().getFunctionId());
                    }
                    if ((finalEntity.getTbAcPrimaryheadMaster() != null)
                            && (finalEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcPrimaryheadMaster(finalEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId());
                    }
                    try {
                        auditService.createHistory(finalEntity, accountBudgetOpenBalanceHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                } else {
                    accountBudgetOpenBalanceHistEntity = new AccountBudgetOpenBalanceHistEntity();
                    accountBudgetOpenBalanceHistEntity.sethStatus(MainetConstants.CommonConstants.CHAR_C);
                    accountBudgetOpenBalanceHistEntity
                            .setTbAcSecondaryheadMaster(finalEntity.getTbAcSecondaryheadMaster().getSacHeadId());
                    if ((finalEntity.getTbComparamDet() != null)
                            && (finalEntity.getTbComparamDet().getCpdId() != null)) {
                        accountBudgetOpenBalanceHistEntity.setTbComparamDet(finalEntity.getTbComparamDet().getCpdId());
                    }
                    if ((finalEntity.getTbAcFundMaster() != null)
                            && (finalEntity.getTbAcFundMaster().getFundId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFundMaster(finalEntity.getTbAcFundMaster().getFundId());
                    }
                    if ((finalEntity.getTbAcFieldMaster() != null)
                            && (finalEntity.getTbAcFieldMaster().getFieldId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFieldMaster(finalEntity.getTbAcFieldMaster().getFieldId());
                    }
                    if ((finalEntity.getTbAcFunctionMaster() != null)
                            && (finalEntity.getTbAcFunctionMaster().getFunctionId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcFunctionMaster(finalEntity.getTbAcFunctionMaster().getFunctionId());
                    }
                    if ((finalEntity.getTbAcPrimaryheadMaster() != null)
                            && (finalEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId() != null)) {
                        accountBudgetOpenBalanceHistEntity
                                .setTbAcPrimaryheadMaster(finalEntity.getTbAcPrimaryheadMaster().getPrimaryAcHeadId());
                    }
                    try {
                        auditService.createHistory(finalEntity, accountBudgetOpenBalanceHistEntity);
                    } catch (Exception ex) {
                        LOGGER.error("Could not make audit entry for " + finalEntity, ex);
                    }
                }
            }
        }
        return tbAcBudgetMaster;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountBudgetOpenBalanceBean getDetailsUsingOpnId(final AccountBudgetOpenBalanceBean tbAcBugopenBalance,
            final Long orgId) {
        final Long opnId = tbAcBugopenBalance.getOpnId();
        final AccountBudgetOpenBalanceEntity tbAcBugopenBalanceEntity = tbAcBugopenBalanceJpaRepository.findOne(opnId);
        // tbAcBugopenBalance.setOpnEntrydate(tbAcBugopenBalanceEntity.getOpnEntrydate());
        tbAcBugopenBalance.setFaYearid(tbAcBugopenBalanceEntity.getFaYearid());
        tbAcBugopenBalance.setOpnBalType(tbAcBugopenBalanceEntity.getOpnBalType());
        tbAcBugopenBalance.setOpnBalTypeDesc(CommonMasterUtility.findLookUpCodeDesc(
                MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_OPN_BAL_TYPE_VALUE,
                tbAcBugopenBalanceEntity.getOrgid(), tbAcBugopenBalanceEntity.getOpnBalType()));

        if ((tbAcBugopenBalanceEntity.getTbAcFundMaster() != null)
                && (tbAcBugopenBalanceEntity.getTbAcFundMaster().getFundId() != null)) {
            tbAcBugopenBalance.setFundId(tbAcBugopenBalanceEntity.getTbAcFundMaster().getFundId());
        }
        if ((tbAcBugopenBalanceEntity.getTbAcFieldMaster() != null)
                && (tbAcBugopenBalanceEntity.getTbAcFieldMaster().getFieldId() != null)) {
            tbAcBugopenBalance.setFieldId(tbAcBugopenBalanceEntity.getTbAcFieldMaster().getFieldId());
        }
        AccountBudgetOpenBalanceMasterDto accountBudgetOpenBalanceMasterDto = null;
        final List<AccountBudgetOpenBalanceMasterDto> budgetOpenBalanceMasterDtoList = new ArrayList<>();
        accountBudgetOpenBalanceMasterDto = new AccountBudgetOpenBalanceMasterDto();
        if ((tbAcBugopenBalanceEntity.getOpenbalAmt() != null) && !tbAcBugopenBalanceEntity.getOpenbalAmt().isEmpty()) {
            final BigDecimal bd = new BigDecimal(tbAcBugopenBalanceEntity.getOpenbalAmt());
            final String amount = CommonMasterUtility.getAmountInIndianCurrency(bd);
            accountBudgetOpenBalanceMasterDto.setOpenbalAmt(amount);
        }
        if ((tbAcBugopenBalanceEntity.getTbComparamDet() != null)
                && (tbAcBugopenBalanceEntity.getTbComparamDet().getCpdId() != null)) {
            accountBudgetOpenBalanceMasterDto
                    .setCpdIdDrcr(tbAcBugopenBalanceEntity.getTbComparamDet().getCpdId().toString());
            accountBudgetOpenBalanceMasterDto.setCpdIdDrCrDesc(CommonMasterUtility.findLookUpDesc(
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.BUG_CPD_VALUE, tbAcBugopenBalanceEntity.getOrgid(),
                    tbAcBugopenBalanceEntity.getTbComparamDet().getCpdId()));
        }
        accountBudgetOpenBalanceMasterDto
                .setSacHeadId(tbAcBugopenBalanceEntity.getTbAcSecondaryheadMaster().getSacHeadId());
        accountBudgetOpenBalanceMasterDto
                .setSacHeadCodeOpenBalanceMaster(tbAcBugopenBalanceEntity.getTbAcSecondaryheadMaster().getAcHeadCode());
        if ((tbAcBugopenBalanceEntity.getFlagFlzd() != null) && !tbAcBugopenBalanceEntity.getFlagFlzd().isEmpty()) {
            if (tbAcBugopenBalanceEntity.getFlagFlzd().equals(MainetConstants.MASTER.Y)) {
                accountBudgetOpenBalanceMasterDto.setFlagFlzd(tbAcBugopenBalanceEntity.getFlagFlzd());
                tbAcBugopenBalance.setFlagFlzdDup(tbAcBugopenBalanceEntity.getFlagFlzd());
            }
        }
        budgetOpenBalanceMasterDtoList.add(accountBudgetOpenBalanceMasterDto);
        tbAcBugopenBalance.setBugReappMasterDtoList(budgetOpenBalanceMasterDtoList);
        return tbAcBugopenBalance;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetOpenBalanceBean> findByFinancialId(final Long faYearid, final Long orgId) {
        final List<AccountBudgetOpenBalanceEntity> entities = tbAcBugopenBalanceJpaRepository
                .findByFinancialId(faYearid, orgId);
        final List<AccountBudgetOpenBalanceBean> bean = new ArrayList<>();
        for (final AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : entities) {
            bean.add(tbAcBugopenBalanceServiceMapper
                    .mapTbAcBugopenBalanceEntityToTbAcBugopenBalance(accountBudgetOpenBalanceEntity));
        }
        return bean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetOpenBalanceBean> findByGridDataFinancialId(final Long faYearid, final String cpdIdDrcr,
            final Long sacHeadId, final String status, final Long orgId) {
        final List<AccountBudgetOpenBalanceEntity> entities = accountBudgetOpenBalanceDao
                .findByGridDataFinancialId(faYearid, cpdIdDrcr, sacHeadId, status, orgId);
        final List<AccountBudgetOpenBalanceBean> bean = new ArrayList<>();
        if ((entities != null) && !entities.isEmpty()) {
            for (final AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : entities) {
                bean.add(tbAcBugopenBalanceServiceMapper
                        .mapTbAcBugopenBalanceEntityToTbAcBugopenBalance(accountBudgetOpenBalanceEntity));
            }
        }
        return bean;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountBudgetOpenBalanceBean> findAllAccountHeadsData(final Long orgId) {
        final List<AccountBudgetOpenBalanceEntity> entities = tbAcBugopenBalanceJpaRepository
                .findAllAccountHeadsData(orgId);
        final List<AccountBudgetOpenBalanceBean> bean = new ArrayList<>();
        if ((entities != null) && !entities.isEmpty()) {
            for (final AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : entities) {
                bean.add(tbAcBugopenBalanceServiceMapper
                        .mapTbAcBugopenBalanceEntityToTbAcBugopenBalance(accountBudgetOpenBalanceEntity));
            }
        }
        return bean;
    }

    @Override
    public void saveAccountBudgetopeningBalance(AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto,
            Long orgId, int langId) throws ParseException {

        AccountBudgetOpenBalanceBean bean = new AccountBudgetOpenBalanceBean();
        bean.setFaYearid(Long.valueOf(accountBudgetOpenBalanceUploadDto.getFinancialYear()));
        bean.setOpnBalType(accountBudgetOpenBalanceUploadDto.getHeadCategory());
        bean.setSacHeadId(Long.valueOf(accountBudgetOpenBalanceUploadDto.getAccountHeads()));
        bean.setOpenbalAmt(accountBudgetOpenBalanceUploadDto.getOpeningBal().replace(",", ""));
        bean.setCpdIdDrcr(accountBudgetOpenBalanceUploadDto.getDrOrCr());
        bean.setFlagFlzd(MainetConstants.N_FLAG);
        bean.setOrgid(accountBudgetOpenBalanceUploadDto.getOrgid());
        bean.setLangId(accountBudgetOpenBalanceUploadDto.getLangId().intValue());
        bean.setUserId(accountBudgetOpenBalanceUploadDto.getUserId());
        bean.setLmoddate(accountBudgetOpenBalanceUploadDto.getLmoddate());
        bean.setLgIpMac(accountBudgetOpenBalanceUploadDto.getLgIpMac());
        saveBudgetOpeningBalanceExcelSheetData(bean);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean isAcHeadWiseHeadCategoryExists(Long accountHeadId, Long headCategoryId, Long orgId) {

        boolean headCatagoryExist = false;
        Long acHeadTypesId = tbAcBugopenBalanceJpaRepository.checkHeadCatedoryTypeId(orgId, accountHeadId);
        if (acHeadTypesId == null) {
            headCatagoryExist = true;
        } else {
            if (!acHeadTypesId.equals(headCategoryId)) {
                headCatagoryExist = true;
            }
        }
        return headCatagoryExist;
    }

}
