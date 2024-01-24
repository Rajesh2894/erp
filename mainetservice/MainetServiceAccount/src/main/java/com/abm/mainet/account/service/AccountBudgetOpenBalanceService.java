package com.abm.mainet.account.service;

import java.text.ParseException;
import java.util.List;

import com.abm.mainet.account.dto.AccountBudgetOpenBalanceBean;
import com.abm.mainet.account.dto.AccountBudgetOpenBalanceUploadDto;

public interface AccountBudgetOpenBalanceService {

    AccountBudgetOpenBalanceBean saveBudgetOpeningBalanceFormData(AccountBudgetOpenBalanceBean tbAcBudgetMaster)
            throws ParseException;

    AccountBudgetOpenBalanceBean getDetailsUsingOpnId(AccountBudgetOpenBalanceBean tbAcBugopenBalance, Long orgId);

    List<AccountBudgetOpenBalanceBean> findByFinancialId(Long faYearid, Long orgId);

    List<AccountBudgetOpenBalanceBean> findByGridDataFinancialId(Long faYearid, String cpdIdDrcr, Long sacHeadId, String status,
            Long orgId);

    public Boolean isCombinationExists(Long faYearid, Long fundId, Long fieldId, String cpdIdDrcr, Long sacHeadId, Long orgId);

    AccountBudgetOpenBalanceBean saveBudgetOpeningBalanceExcelSheetData(AccountBudgetOpenBalanceBean uploadList)
            throws ParseException;

    List<AccountBudgetOpenBalanceBean> findAllAccountHeadsData(Long orgId);

    public void saveAccountBudgetopeningBalance(AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto,
            Long orgId, int langId) throws ParseException;

    boolean isAcHeadWiseHeadCategoryExists(Long accountHeadId, Long headCategoryId, Long orgId);
}
