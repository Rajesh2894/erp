package com.abm.mainet.account.ui.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.validation.BindingResult;

import com.abm.mainet.account.dto.AccountBudgetOpenBalanceUploadDto;
import com.abm.mainet.account.service.AccountBudgetOpenBalanceService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

public class AccountBudgetOpenBalanceExcelValidator {

    @Resource
    private AccountBudgetOpenBalanceService tbAcBugopenBalanceService;
    public int count = 0;

    public List<AccountBudgetOpenBalanceUploadDto> excelValidation(
            final List<AccountBudgetOpenBalanceUploadDto> accountBudgetOpenBalanceUploadDtos,
            final BindingResult bindingResult, final Map<Long, String> financeMap, final List<LookUp> levelMap,
            Map<Long, String> secondaryHeadDescMap,
            final List<LookUp> drCrLevelMap) {
        BigDecimal openingbalance = new BigDecimal(0.00);
        int rowNo = 1;
        final List<AccountBudgetOpenBalanceUploadDto> accountBudgetOpenBalanceMasterList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<AccountBudgetOpenBalanceUploadDto> accountBudgetHeadExport = accountBudgetOpenBalanceUploadDtos.stream()
                .filter(dto -> Collections.frequency(accountBudgetOpenBalanceUploadDtos, dto) > 1)
                .collect(Collectors.toSet());
        if (accountBudgetHeadExport.isEmpty()) {

            for (AccountBudgetOpenBalanceUploadDto accountBudgetOpenBalanceUploadDto : accountBudgetOpenBalanceUploadDtos) {

                AccountBudgetOpenBalanceUploadDto dto = new AccountBudgetOpenBalanceUploadDto();
                int financialYear = 0;
                if (accountBudgetOpenBalanceUploadDto.getFinancialYear() == null
                        || accountBudgetOpenBalanceUploadDto.getFinancialYear().isEmpty()) {
                    financialYear++;
                }
                if (financialYear != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.financialYear")));
                    break;
                }
                int headCategory = 0;
                if (accountBudgetOpenBalanceUploadDto.getHeadCategory() == null
                        || accountBudgetOpenBalanceUploadDto.getHeadCategory().isEmpty()) {
                    headCategory++;
                }
                if (headCategory != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.Headcategory")));

                    break;
                }
                int accountHeads = 0;
                if (accountBudgetOpenBalanceUploadDto.getAccountHeads() == null
                        || accountBudgetOpenBalanceUploadDto.getAccountHeads().isEmpty()) {
                    accountHeads++;
                }
                if (accountHeads != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.AccountHeads")));
                    break;
                }
                try {
                    openingbalance = new BigDecimal(accountBudgetOpenBalanceUploadDto.getOpeningBal());
                } catch (Exception e) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("Row opening balance should be number")));
                    break;
                }
                int openingBal = 0;
                if (accountBudgetOpenBalanceUploadDto.getOpeningBal() == null) {
                    openingBal++;
                } else {
                    dto.setOpeningBal(accountBudgetOpenBalanceUploadDto.getOpeningBal());
                }
                if (openingBal != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.openingBal")));
                    break;
                }

                int crOrDr = 0;
                if (accountBudgetOpenBalanceUploadDto.getDrOrCr() == null
                        || accountBudgetOpenBalanceUploadDto.getDrOrCr().isEmpty()) {
                    crOrDr++;
                }

                if (crOrDr != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.DR/CR")));
                    break;
                }

                int financialYearExist = 0;
                for (Map.Entry<Long, String> entry : financeMap.entrySet()) {
                    String value = entry.getValue().trim();
                    if (accountBudgetOpenBalanceUploadDto.getFinancialYear().equals(value)) {
                        dto.setFinancialYear(entry.getKey().toString());
                        financialYearExist++;
                    }
                }

                if (financialYearExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.financialYearExist")));
                    break;
                }

                int headCategoryExist = 0;
                for (LookUp list : levelMap) {
                    if (accountBudgetOpenBalanceUploadDto.getHeadCategory().equalsIgnoreCase(list.getDescLangFirst())) {
                        dto.setHeadCategory(list.getLookUpCode());
                        dto.setHeadCategoryId(list.getLookUpId());
                        headCategoryExist++;
                    }

                }
                if (headCategoryExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.HeadcategoryExist")));
                    break;
                }

                int acocuntHeadsLiabiltyAssetExists = 0;
                for (Map.Entry<Long, String> entry : secondaryHeadDescMap.entrySet()) {
                    String value = entry.getValue().replaceAll("(\\r|\\n)", "");
                    String value1 = value.replaceAll("\\s", "");
                    String accountHead = accountBudgetOpenBalanceUploadDto.getAccountHeads().trim();
                    String acHeadCode = accountHead.replaceAll("\\s", "");
                    if (acHeadCode.equalsIgnoreCase(value1)) {
                        dto.setAccountHeads(entry.getKey().toString());
                        dto.setDupAccountHead(accountHead);
                        acocuntHeadsLiabiltyAssetExists++;
                        break;
                    }
                }

                if (acocuntHeadsLiabiltyAssetExists == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("bug.openbal.master.AccountHeadExist")));
                    break;
                }
                int crOrDrExists = 0;
                for (LookUp list : drCrLevelMap) {
                    if (accountBudgetOpenBalanceUploadDto.getDrOrCr().equalsIgnoreCase(list.getDescLangFirst())) {
                        dto.setDrOrCr(String.valueOf(list.getLookUpId()));
                        crOrDrExists++;
                    }
                }
                if (crOrDrExists == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null,
                            false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + " " + session.getMessage("budget.projected.expenditure.master.originalBudget")));
                    break;
                }
                accountBudgetOpenBalanceMasterList.add(dto);
                rowNo++;
            }
        } else {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER.OPN_BAL_MASTER, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return accountBudgetOpenBalanceMasterList;
    }

}
