package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.account.domain.AccountBudgetOpenBalanceEntity;
import com.abm.mainet.account.dto.AccountYearEndProcessDTO;
import com.abm.mainet.account.repository.AccountFinancialReportRepository;
import com.abm.mainet.account.repository.BudgetOpenBalanceRepository;
import com.abm.mainet.account.repository.VoucherTemplateRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.TbComparamDetEntity;
import com.abm.mainet.common.integration.acccount.domain.AccountHeadSecondaryAccountCodeMasterEntity;
import com.abm.mainet.common.master.repository.TbFinancialyearJpaRepository;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Utility;

@Service
public class AccountYearEndProcessServiceImpl implements AccountYearEndProcessService {

    private static final Logger LOGGER = Logger.getLogger(AccountYearEndProcessServiceImpl.class);

    @Resource
    private TbFinancialyearJpaRepository tbFinancialyearJpaRepository;
    @Resource
    private AccountFinancialReportRepository accountFinancialReportRepository;
    @Resource
    private BudgetCodeService budgetCodeService;
    @Resource
    private BudgetOpenBalanceRepository tbAcBugopenBalanceJpaRepository;
    @Resource
    private TbDepartmentService deparmentService;
    @Resource
    private VoucherTemplateRepository voucherTemplateRepository;

    @Override
    @Transactional
    public AccountYearEndProcessDTO processFaYearEndProcessReport(Long faYearId, Long orgId) {
        // TODO Auto-generated method stub
        Date fromDate = null;
        Date toDate = null;
        List<Object[]> faYearFromToDates = tbFinancialyearJpaRepository.getFinanceYearFrmDate(faYearId);
        for (Object[] objects : faYearFromToDates) {
            fromDate = (Date) objects[0];
            toDate = (Date) objects[1];
        }
        AccountYearEndProcessDTO dto = new AccountYearEndProcessDTO();
        final List<Object[]> records = findReportDataForYearEndProcess(faYearId, fromDate, toDate, orgId);
        if ((records == null) || records.isEmpty()) {
            LOGGER.error("No Records found for General Ledger Register from VW_VOUCHER_DETAIL ");
        } else {
            dto = mapYearEndProcessDataToDTO(records, orgId, fromDate, toDate, faYearId);
        }
        return dto;
    }

    public List<Object[]> findReportDataForYearEndProcess(final Long faYearId, final Date fromDate, final Date toDate,
            final Long orgId) {

        return accountFinancialReportRepository.queryForForYearEndProcessData(faYearId, fromDate, toDate, orgId);
    }

    private AccountYearEndProcessDTO mapYearEndProcessDataToDTO(final List<Object[]> resultList, final Long orgId,
            Date fromDate, Date toDate, Long faYearId) {

        BigDecimal sumTransactionDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        BigDecimal sumTransactionCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        BigDecimal sumOpeningCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        BigDecimal sumOpeningDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        BigDecimal sumClosingCR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        BigDecimal sumClosingDR = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
        final AccountYearEndProcessDTO bean = new AccountYearEndProcessDTO();
        bean.setFaYearid(faYearId);
        final List<AccountYearEndProcessDTO> list = new ArrayList<>();
        for (final Object[] objects : resultList) {
            final AccountYearEndProcessDTO dto = new AccountYearEndProcessDTO();
            if (objects[1] != null) {
                dto.setSacHeadId(Long.valueOf(objects[1].toString()));
                dto.setAccountCode(
                        budgetCodeService.findAccountHeadCodeBySacHeadId(Long.valueOf(objects[1].toString()), orgId));
            }
            Long cpdIdCrDr = null;
            BigDecimal crAmount = null;
            BigDecimal drAmount = null;
            final Long drId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.AccountJournalVoucherEntry.DR, PrefixConstants.DCR, orgId);
            final Long crId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.AccountJournalVoucherEntry.CR, PrefixConstants.DCR, orgId);
            if (objects[2] != null) {
                cpdIdCrDr = (Long.valueOf(objects[2].toString()));
                if (cpdIdCrDr.equals(drId)) {
                    if (objects[0] != null) {
                        drAmount = (BigDecimal) objects[0];
                    } else {
                        drAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                    }
                }
                if (!cpdIdCrDr.equals(drId)) {
                    crAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                }
                if (cpdIdCrDr.equals(crId)) {
                    if (objects[0] != null) {
                        crAmount = (BigDecimal) objects[0];
                    } else {
                        crAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                    }
                }
                if (!cpdIdCrDr.equals(crId)) {
                    drAmount = new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT);
                }
            }

            if (cpdIdCrDr != null) {
                if (cpdIdCrDr.equals(drId)) {

                    dto.setOpeningDrAmount(((BigDecimal) objects[0]).setScale(2, RoundingMode.HALF_EVEN));
                    sumOpeningDR = sumOpeningDR.add(dto.getOpeningDrAmount());

                } else if (cpdIdCrDr.equals(crId)) {

                    dto.setOpeningCrAmount(((BigDecimal) objects[0]).setScale(2, RoundingMode.HALF_EVEN));
                    sumOpeningCR = sumOpeningCR.add(dto.getOpeningCrAmount());

                }
            }

            if (objects[3] != null
                    && !(objects[3].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
                    && !(objects[3].equals(BigDecimal.ZERO))) {
                dto.setTransactionCrAmount(((BigDecimal) objects[3]).setScale(2, RoundingMode.HALF_EVEN));
                sumTransactionCR = sumTransactionCR.add((BigDecimal) objects[3]);

            }
            if (objects[4] != null
                    && !(objects[4].equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT)))
                    && !(objects[4].equals(BigDecimal.ZERO))) {
                dto.setTransactionDrAmount(((BigDecimal) objects[4]).setScale(2, RoundingMode.HALF_EVEN));
                sumTransactionDR = sumTransactionDR.add((BigDecimal) objects[4]);

            }
            if (dto.getOpeningCrAmount() != null
                    && !dto.getOpeningCrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
                    && !dto.getOpeningCrAmount().equals(BigDecimal.ZERO)) {
                BigDecimal finalCrAmount = calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
                        dto.getTransactionCrAmount(), dto.getTransactionDrAmount());
                if (finalCrAmount.signum() == -1 || finalCrAmount
                        .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                    dto.setClosingDrAmount(finalCrAmount.abs());
                    if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
                    }
                } else {
                    dto.setClosingCrAmount(finalCrAmount);
                    if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
                    }
                }
            } else if (dto.getOpeningDrAmount() != null
                    && !dto.getOpeningDrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
                    && !dto.getOpeningDrAmount().equals(BigDecimal.ZERO)) {
                BigDecimal finalDrAmount = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
                        dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
                if (finalDrAmount.signum() == -1 || finalDrAmount
                        .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                    dto.setClosingCrAmount(finalDrAmount.abs());
                    if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
                    }
                } else {
                    dto.setClosingDrAmount(finalDrAmount);
                    if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
                    }
                }
            } else {
                if (dto.getTransactionCrAmount() != null
                        && !dto.getTransactionCrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
                        && !dto.getTransactionCrAmount().equals(BigDecimal.ZERO)) {
                    BigDecimal finalTranCrAmt = calculateClosingBalAsOnDate(dto.getOpeningCrAmount(),
                            dto.getTransactionCrAmount(), dto.getTransactionDrAmount());
                    if (finalTranCrAmt.signum() == -1 || finalTranCrAmt
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        dto.setClosingDrAmount(finalTranCrAmt.abs());
                        if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
                        }
                    } else {
                        dto.setClosingCrAmount(finalTranCrAmt);
                        if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
                        }
                    }
                } else if (dto.getTransactionDrAmount() != null
                        && !dto.getTransactionDrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))
                        && !dto.getTransactionDrAmount().equals(BigDecimal.ZERO)) {
                    BigDecimal finalTranDrAmt = calculateClosingBalAsOnDate(dto.getOpeningDrAmount(),
                            dto.getTransactionDrAmount(), dto.getTransactionCrAmount());
                    if (finalTranDrAmt.signum() == -1 || finalTranDrAmt
                            .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                        dto.setClosingCrAmount(finalTranDrAmt.abs());
                        if (dto.getClosingCrAmount() != null && !dto.getClosingCrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            sumClosingCR = sumClosingCR.add(dto.getClosingCrAmount());
                        }
                    } else {
                        dto.setClosingDrAmount(finalTranDrAmt);
                        if (dto.getClosingDrAmount() != null && !dto.getClosingDrAmount()
                                .equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
                            sumClosingDR = sumClosingDR.add(dto.getClosingDrAmount());
                        }
                    }
                }
            }

            list.add(dto);
        }
        bean.setFromDate(Utility.dateToString(fromDate));
        bean.setToDate(Utility.dateToString(toDate));
        bean.setListOfSum(list);
        bean.setSumClosingCR(sumClosingCR);
        bean.setSumClosingDR(sumClosingDR);
        if (sumOpeningCR.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
            bean.setSumOpeningCR(null);
        } else
            bean.setSumOpeningCR(sumOpeningCR);

        if (sumOpeningDR.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
            bean.setSumOpeningDR(null);
        } else
            bean.setSumOpeningDR(sumOpeningDR);

        bean.setSumTransactionCR(sumTransactionCR);
        bean.setSumTransactionDR(sumTransactionDR);
        return bean;
    }

    private BigDecimal calculateClosingBalAsOnDate(final BigDecimal openBalance, final BigDecimal drAmount,
            final BigDecimal crAmount) {
        BigDecimal closingBalance = BigDecimal.ZERO.setScale(2);
        if (openBalance != null) {
            closingBalance = closingBalance.add(openBalance);
        }
        if (drAmount != null
                && !drAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
            closingBalance = closingBalance.add(drAmount);
        }
        if (crAmount != null
                && !crAmount.equals(new BigDecimal(MainetConstants.AccountJournalVoucherEntry.SET_AMOUNT))) {
            closingBalance = closingBalance.subtract(crAmount);
        }

        return closingBalance;
    }

    @Override
    @Transactional
    public AccountYearEndProcessDTO updateYearEndProcessFormData(AccountYearEndProcessDTO dto){
        // TODO Auto-generated method stub
        List<AccountYearEndProcessDTO> listOfYearEndprocess = dto.getListOfSum();
        if (listOfYearEndprocess != null && !listOfYearEndprocess.isEmpty()) {
            for (AccountYearEndProcessDTO detList : listOfYearEndprocess) {
                Long sacHeadId = detList.getSacHeadId();
                String acHeadTypeCode = "";
                Long acHeadTypesId = tbAcBugopenBalanceJpaRepository.checkHeadCatedoryTypeId(dto.getOrgid(), sacHeadId);
                if (acHeadTypesId != null) {
                    acHeadTypeCode = CommonMasterUtility.findLookUpCode(PrefixConstants.COA, dto.getOrgid(),
                            acHeadTypesId);
                }
                if (acHeadTypeCode.equals("A") || acHeadTypeCode.equals("L")) {
                    BigDecimal openingDrAmount = detList.getOpeningDrAmount();
                    BigDecimal openingCrAmount = detList.getOpeningCrAmount();
                    BigDecimal closingDrAmt = detList.getClosingDrAmount();
                    BigDecimal closingCrAmt = detList.getClosingCrAmount();
                    Long drCrId = null;
                    if (closingDrAmt != null && closingDrAmt != BigDecimal.ZERO) {
                        drCrId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                                PrefixConstants.DCR, dto.getOrgid());
                        boolean bugOpnBalExist = false;
                        List<AccountBudgetOpenBalanceEntity> bugOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                .checkBugOpenBalRecordExists(dto.getOrgid(), dto.getFaYearid(), sacHeadId);
                        if (bugOpnBalExistList == null || bugOpnBalExistList.isEmpty()) {
                            bugOpnBalExist = false;
                        } else {
                            bugOpnBalExist = true;
                            for (AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : bugOpnBalExistList) {
                                Date fromDate = null;
                                final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
                                        .getFinanceYearFrmDate(dto.getFaYearid());
                                for (Object[] objects : faYearFromDate) {
                                    fromDate = (Date) objects[0];
                                }
                                String myDate = Utility.dateToString(fromDate);
                                int year1 = getYearFromDate(fromDate);
                                String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
                                Long newlyFaYearId = tbFinancialyearJpaRepository
                                        .getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));
                                if (newlyFaYearId == null) {
                        			throw new NullPointerException("Next Finanacial Year is Not Exist.");
                        		}
                                List<AccountBudgetOpenBalanceEntity> bugNewOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                        .checkBugOpenBalRecordExists(dto.getOrgid(), newlyFaYearId, sacHeadId);
                                if (bugNewOpnBalExistList == null || bugNewOpnBalExistList.isEmpty()) {
                                    AccountBudgetOpenBalanceEntity budgetNewOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                    budgetNewOpenBalanceEntity.setOpenbalAmt(closingDrAmt.toString());
                                    budgetNewOpenBalanceEntity.setOrgid(dto.getOrgid());
                                    budgetNewOpenBalanceEntity.setUserId(dto.getUserId());
                                    budgetNewOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                    budgetNewOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());
                                    budgetNewOpenBalanceEntity.setFaYearid(newlyFaYearId);
                                    budgetNewOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                    budgetNewOpenBalanceEntity.setCloBalAmt(BigDecimal.ZERO);
                                    budgetNewOpenBalanceEntity.setCloBalType(drCrId);
                                    budgetNewOpenBalanceEntity
                                            .setOpnBalType(accountBudgetOpenBalanceEntity.getOpnBalType());
                                    budgetNewOpenBalanceEntity.setTbAcSecondaryheadMaster(
                                            accountBudgetOpenBalanceEntity.getTbAcSecondaryheadMaster());
                                    budgetNewOpenBalanceEntity
                                            .setTbComparamDet(accountBudgetOpenBalanceEntity.getTbComparamDet());

                                    if (accountBudgetOpenBalanceEntity.getTbAcFundMaster() != null) {
                                        budgetNewOpenBalanceEntity
                                                .setTbAcFundMaster(accountBudgetOpenBalanceEntity.getTbAcFundMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcFieldMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcFieldMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcFieldMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcFunctionMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcFunctionMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcFunctionMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcPrimaryheadMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster());
                                    }
                                    tbAcBugopenBalanceJpaRepository.save(budgetNewOpenBalanceEntity);
                                } else {
                                    tbAcBugopenBalanceJpaRepository.updateYearEndProcessNextYearOpeningAmtData(
                                            dto.getUpdatedBy(), dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId,
                                            closingDrAmt.toString(), drCrId, dto.getOrgid(), newlyFaYearId);
                                }
                            }
                        }
                        if (bugOpnBalExist) {
                            tbAcBugopenBalanceJpaRepository.updateYearEndProcessClosingDrAmtData(dto.getUpdatedBy(),
                                    dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId, closingDrAmt, drCrId,
                                    dto.getOrgid(), dto.getFaYearid());
                        } else {
                            if (acHeadTypeCode.equals("A") || acHeadTypeCode.equals("L")) {
                                AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                budgetOpenBalanceEntity.setOpenbalAmt("0.00");
                                budgetOpenBalanceEntity.setOrgid(dto.getOrgid());
                                budgetOpenBalanceEntity.setUserId(dto.getUserId());
                                budgetOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                budgetOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());
                                budgetOpenBalanceEntity.setFaYearid(dto.getFaYearid());
                                budgetOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                budgetOpenBalanceEntity.setCloBalAmt(closingDrAmt);
                                budgetOpenBalanceEntity.setCloBalType(drCrId);
                                budgetOpenBalanceEntity.setOpnBalType(acHeadTypeCode);
                                AccountHeadSecondaryAccountCodeMasterEntity sacHead = new AccountHeadSecondaryAccountCodeMasterEntity();
                                sacHead.setSacHeadId(sacHeadId);
                                budgetOpenBalanceEntity.setTbAcSecondaryheadMaster(sacHead);
                                TbComparamDetEntity drCrType = new TbComparamDetEntity();
                                drCrType.setCpdId(drCrId);
                                budgetOpenBalanceEntity.setTbComparamDet(drCrType);
                                tbAcBugopenBalanceJpaRepository.save(budgetOpenBalanceEntity);
                            }
                        }
                    }

                    if (closingCrAmt != null && closingCrAmt != BigDecimal.ZERO) {

                        drCrId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                                PrefixConstants.DCR, dto.getOrgid());
                        boolean bugOpnBalExist = false;
                        List<AccountBudgetOpenBalanceEntity> bugOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                .checkBugOpenBalRecordExists(dto.getOrgid(), dto.getFaYearid(), sacHeadId);
                        if (bugOpnBalExistList == null || bugOpnBalExistList.isEmpty()) {
                            bugOpnBalExist = false;
                        } else {
                            bugOpnBalExist = true;

                            for (AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : bugOpnBalExistList) {

                                Date fromDate = null;
                                final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
                                        .getFinanceYearFrmDate(dto.getFaYearid());
                                for (Object[] objects : faYearFromDate) {
                                    fromDate = (Date) objects[0];
                                }
                                String myDate = Utility.dateToString(fromDate);
                                int year1 = getYearFromDate(fromDate);
                                String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
                                Long newlyFaYearId = tbFinancialyearJpaRepository
                                        .getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));

                                List<AccountBudgetOpenBalanceEntity> bugNewOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                        .checkBugOpenBalRecordExists(dto.getOrgid(), newlyFaYearId, sacHeadId);
                                if (bugNewOpnBalExistList == null || bugNewOpnBalExistList.isEmpty()) {

                                    AccountBudgetOpenBalanceEntity budgetNewOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                    budgetNewOpenBalanceEntity.setOpenbalAmt(closingCrAmt.toString());
                                    budgetNewOpenBalanceEntity.setOrgid(dto.getOrgid());
                                    budgetNewOpenBalanceEntity.setUserId(dto.getUserId());
                                    budgetNewOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                    budgetNewOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());

                                    budgetNewOpenBalanceEntity.setFaYearid(newlyFaYearId);
                                    budgetNewOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                    budgetNewOpenBalanceEntity.setCloBalAmt(BigDecimal.ZERO);
                                    budgetNewOpenBalanceEntity.setCloBalType(drCrId);
                                    budgetNewOpenBalanceEntity
                                            .setOpnBalType(accountBudgetOpenBalanceEntity.getOpnBalType());
                                    budgetNewOpenBalanceEntity.setTbAcSecondaryheadMaster(
                                            accountBudgetOpenBalanceEntity.getTbAcSecondaryheadMaster());
                                    budgetNewOpenBalanceEntity
                                            .setTbComparamDet(accountBudgetOpenBalanceEntity.getTbComparamDet());

                                    if (accountBudgetOpenBalanceEntity.getTbAcFundMaster() != null) {
                                        budgetNewOpenBalanceEntity
                                                .setTbAcFundMaster(accountBudgetOpenBalanceEntity.getTbAcFundMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcFieldMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcFieldMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcFieldMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcFunctionMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcFunctionMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcFunctionMaster());
                                    }
                                    if (accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster() != null) {
                                        budgetNewOpenBalanceEntity.setTbAcPrimaryheadMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster());
                                    }
                                    tbAcBugopenBalanceJpaRepository.save(budgetNewOpenBalanceEntity);
                                } else {
                                    tbAcBugopenBalanceJpaRepository.updateYearEndProcessNextYearOpeningAmtData(
                                            dto.getUpdatedBy(), dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId,
                                            closingCrAmt.toString(), drCrId, dto.getOrgid(), newlyFaYearId);
                                }
                            }
                        }
                        if (bugOpnBalExist) {
                            tbAcBugopenBalanceJpaRepository.updateYearEndProcessClosingDrAmtData(dto.getUpdatedBy(),
                                    dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId, closingCrAmt, drCrId,
                                    dto.getOrgid(), dto.getFaYearid());
                        } else {
                            if (acHeadTypeCode.equals("A") || acHeadTypeCode.equals("L")) {
                                AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                budgetOpenBalanceEntity.setOpenbalAmt("0.00");
                                budgetOpenBalanceEntity.setOrgid(dto.getOrgid());
                                budgetOpenBalanceEntity.setUserId(dto.getUserId());
                                budgetOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                budgetOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());
                                budgetOpenBalanceEntity.setOpnBalType(acHeadTypeCode);
                                budgetOpenBalanceEntity.setFaYearid(dto.getFaYearid());
                                budgetOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                budgetOpenBalanceEntity.setCloBalAmt(closingCrAmt);
                                budgetOpenBalanceEntity.setCloBalType(drCrId);
                                AccountHeadSecondaryAccountCodeMasterEntity sacHead = new AccountHeadSecondaryAccountCodeMasterEntity();
                                sacHead.setSacHeadId(sacHeadId);
                                budgetOpenBalanceEntity.setTbAcSecondaryheadMaster(sacHead);
                                TbComparamDetEntity drCrType = new TbComparamDetEntity();
                                drCrType.setCpdId(drCrId);
                                budgetOpenBalanceEntity.setTbComparamDet(drCrType);
                                tbAcBugopenBalanceJpaRepository.save(budgetOpenBalanceEntity);
                            }
                        }
                    }

                    if ((closingDrAmt == null || closingDrAmt == BigDecimal.ZERO)
                            && (closingCrAmt == null || closingCrAmt == BigDecimal.ZERO)) {

                        if (openingDrAmount != null && openingDrAmount != BigDecimal.ZERO) {

                            drCrId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.DR.toString(),
                                    PrefixConstants.DCR, dto.getOrgid());
                            boolean bugOpnBalExist = false;
                            List<AccountBudgetOpenBalanceEntity> bugOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                    .checkBugOpenBalRecordExists(dto.getOrgid(), dto.getFaYearid(), sacHeadId);
                            if (bugOpnBalExistList == null || bugOpnBalExistList.isEmpty()) {
                                bugOpnBalExist = false;
                            } else {
                                bugOpnBalExist = true;

                                for (AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : bugOpnBalExistList) {

                                    Date fromDate = null;
                                    final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
                                            .getFinanceYearFrmDate(dto.getFaYearid());
                                    for (Object[] objects : faYearFromDate) {
                                        fromDate = (Date) objects[0];
                                    }
                                    String myDate = Utility.dateToString(fromDate);
                                    int year1 = getYearFromDate(fromDate);
                                    String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
                                    Long newlyFaYearId = tbFinancialyearJpaRepository
                                            .getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));

                                    List<AccountBudgetOpenBalanceEntity> bugNewOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                            .checkBugOpenBalRecordExists(dto.getOrgid(), newlyFaYearId, sacHeadId);
                                    if (bugNewOpnBalExistList == null || bugNewOpnBalExistList.isEmpty()) {

                                        AccountBudgetOpenBalanceEntity budgetNewOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                        budgetNewOpenBalanceEntity.setOpenbalAmt(openingDrAmount.toString());
                                        budgetNewOpenBalanceEntity.setOrgid(dto.getOrgid());
                                        budgetNewOpenBalanceEntity.setUserId(dto.getUserId());
                                        budgetNewOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                        budgetNewOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());

                                        budgetNewOpenBalanceEntity.setFaYearid(newlyFaYearId);
                                        budgetNewOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                        budgetNewOpenBalanceEntity.setCloBalAmt(BigDecimal.ZERO);
                                        budgetNewOpenBalanceEntity.setCloBalType(drCrId);
                                        budgetNewOpenBalanceEntity
                                                .setOpnBalType(accountBudgetOpenBalanceEntity.getOpnBalType());
                                        budgetNewOpenBalanceEntity.setTbAcSecondaryheadMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcSecondaryheadMaster());
                                        budgetNewOpenBalanceEntity
                                                .setTbComparamDet(accountBudgetOpenBalanceEntity.getTbComparamDet());

                                        if (accountBudgetOpenBalanceEntity.getTbAcFundMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFundMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFundMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcFieldMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFieldMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFieldMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcFunctionMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFunctionMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFunctionMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcPrimaryheadMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster());
                                        }
                                        tbAcBugopenBalanceJpaRepository.save(budgetNewOpenBalanceEntity);
                                    } else {
                                        tbAcBugopenBalanceJpaRepository.updateYearEndProcessNextYearOpeningAmtData(
                                                dto.getUpdatedBy(), dto.getUpdatedDate(), dto.getLgIpMacUpd(),
                                                sacHeadId, openingDrAmount.toString(), drCrId, dto.getOrgid(),
                                                newlyFaYearId);
                                    }
                                }
                            }
                            if (bugOpnBalExist) {
                                tbAcBugopenBalanceJpaRepository.updateYearEndProcessClosingDrAmtData(dto.getUpdatedBy(),
                                        dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId, openingDrAmount, drCrId,
                                        dto.getOrgid(), dto.getFaYearid());
                            } else {

                                if (acHeadTypeCode.equals("A") || acHeadTypeCode.equals("L")) {

                                    AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                    budgetOpenBalanceEntity.setOpenbalAmt("0.00");
                                    budgetOpenBalanceEntity.setOrgid(dto.getOrgid());
                                    budgetOpenBalanceEntity.setUserId(dto.getUserId());
                                    budgetOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                    budgetOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());

                                    budgetOpenBalanceEntity.setFaYearid(dto.getFaYearid());
                                    budgetOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                    budgetOpenBalanceEntity.setCloBalAmt(openingDrAmount);
                                    budgetOpenBalanceEntity.setCloBalType(drCrId);

                                    budgetOpenBalanceEntity.setOpnBalType(acHeadTypeCode);

                                    AccountHeadSecondaryAccountCodeMasterEntity sacHead = new AccountHeadSecondaryAccountCodeMasterEntity();
                                    sacHead.setSacHeadId(sacHeadId);
                                    budgetOpenBalanceEntity.setTbAcSecondaryheadMaster(sacHead);

                                    TbComparamDetEntity drCrType = new TbComparamDetEntity();
                                    drCrType.setCpdId(drCrId);
                                    budgetOpenBalanceEntity.setTbComparamDet(drCrType);

                                    tbAcBugopenBalanceJpaRepository.save(budgetOpenBalanceEntity);
                                }
                            }
                        }

                        if (openingCrAmount != null && openingCrAmount != BigDecimal.ZERO) {

                            drCrId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(AccountConstants.CR.toString(),
                                    PrefixConstants.DCR, dto.getOrgid());
                            boolean bugOpnBalExist = false;
                            List<AccountBudgetOpenBalanceEntity> bugOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                    .checkBugOpenBalRecordExists(dto.getOrgid(), dto.getFaYearid(), sacHeadId);
                            if (bugOpnBalExistList == null || bugOpnBalExistList.isEmpty()) {
                                bugOpnBalExist = false;
                            } else {
                                bugOpnBalExist = true;

                                for (AccountBudgetOpenBalanceEntity accountBudgetOpenBalanceEntity : bugOpnBalExistList) {

                                    Date fromDate = null;
                                    final List<Object[]> faYearFromDate = tbFinancialyearJpaRepository
                                            .getFinanceYearFrmDate(dto.getFaYearid());
                                    for (Object[] objects : faYearFromDate) {
                                        fromDate = (Date) objects[0];
                                    }
                                    String myDate = Utility.dateToString(fromDate);
                                    int year1 = getYearFromDate(fromDate);
                                    String fromDate1 = myDate.substring(0, 6) + (year1 + 1);
                                    Long newlyFaYearId = tbFinancialyearJpaRepository
                                            .getFinanciaYearIdByFromDate(Utility.stringToDate(fromDate1));

                                    List<AccountBudgetOpenBalanceEntity> bugNewOpnBalExistList = tbAcBugopenBalanceJpaRepository
                                            .checkBugOpenBalRecordExists(dto.getOrgid(), newlyFaYearId, sacHeadId);
                                    if (bugNewOpnBalExistList == null || bugNewOpnBalExistList.isEmpty()) {

                                        AccountBudgetOpenBalanceEntity budgetNewOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                        budgetNewOpenBalanceEntity.setOpenbalAmt(openingCrAmount.toString());
                                        budgetNewOpenBalanceEntity.setOrgid(dto.getOrgid());
                                        budgetNewOpenBalanceEntity.setUserId(dto.getUserId());
                                        budgetNewOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                        budgetNewOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());

                                        budgetNewOpenBalanceEntity.setFaYearid(newlyFaYearId);
                                        budgetNewOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                        budgetNewOpenBalanceEntity.setCloBalAmt(BigDecimal.ZERO);
                                        budgetNewOpenBalanceEntity.setCloBalType(drCrId);
                                        budgetNewOpenBalanceEntity
                                                .setOpnBalType(accountBudgetOpenBalanceEntity.getOpnBalType());
                                        budgetNewOpenBalanceEntity.setTbAcSecondaryheadMaster(
                                                accountBudgetOpenBalanceEntity.getTbAcSecondaryheadMaster());
                                        budgetNewOpenBalanceEntity
                                                .setTbComparamDet(accountBudgetOpenBalanceEntity.getTbComparamDet());

                                        if (accountBudgetOpenBalanceEntity.getTbAcFundMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFundMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFundMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcFieldMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFieldMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFieldMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcFunctionMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcFunctionMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcFunctionMaster());
                                        }
                                        if (accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster() != null) {
                                            budgetNewOpenBalanceEntity.setTbAcPrimaryheadMaster(
                                                    accountBudgetOpenBalanceEntity.getTbAcPrimaryheadMaster());
                                        }
                                        tbAcBugopenBalanceJpaRepository.save(budgetNewOpenBalanceEntity);
                                    } else {
                                        tbAcBugopenBalanceJpaRepository.updateYearEndProcessNextYearOpeningAmtData(
                                                dto.getUpdatedBy(), dto.getUpdatedDate(), dto.getLgIpMacUpd(),
                                                sacHeadId, openingCrAmount.toString(), drCrId, dto.getOrgid(),
                                                newlyFaYearId);
                                    }
                                }
                            }
                            if (bugOpnBalExist) {
                                tbAcBugopenBalanceJpaRepository.updateYearEndProcessClosingDrAmtData(dto.getUpdatedBy(),
                                        dto.getUpdatedDate(), dto.getLgIpMacUpd(), sacHeadId, openingCrAmount, drCrId,
                                        dto.getOrgid(), dto.getFaYearid());
                            } else {
                                if (acHeadTypeCode.equals("A") || acHeadTypeCode.equals("L")) {
                                    AccountBudgetOpenBalanceEntity budgetOpenBalanceEntity = new AccountBudgetOpenBalanceEntity();
                                    budgetOpenBalanceEntity.setOpenbalAmt("0.00");
                                    budgetOpenBalanceEntity.setOrgid(dto.getOrgid());
                                    budgetOpenBalanceEntity.setUserId(dto.getUserId());
                                    budgetOpenBalanceEntity.setLmoddate(dto.getLmoddate());
                                    budgetOpenBalanceEntity.setLgIpMac(dto.getLgIpMac());

                                    budgetOpenBalanceEntity.setOpnBalType(acHeadTypeCode);

                                    budgetOpenBalanceEntity.setFaYearid(dto.getFaYearid());
                                    budgetOpenBalanceEntity.setFlagFlzd(MainetConstants.Y_FLAG);
                                    budgetOpenBalanceEntity.setCloBalAmt(openingCrAmount);
                                    budgetOpenBalanceEntity.setCloBalType(drCrId);

                                    AccountHeadSecondaryAccountCodeMasterEntity sacHead = new AccountHeadSecondaryAccountCodeMasterEntity();
                                    sacHead.setSacHeadId(sacHeadId);
                                    budgetOpenBalanceEntity.setTbAcSecondaryheadMaster(sacHead);

                                    TbComparamDetEntity drCrType = new TbComparamDetEntity();
                                    drCrType.setCpdId(drCrId);
                                    budgetOpenBalanceEntity.setTbComparamDet(drCrType);

                                    tbAcBugopenBalanceJpaRepository.save(budgetOpenBalanceEntity);
                                }
                            }
                        }
                    }
                }
            }
            // Loop End

        }

        return dto;

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
}
