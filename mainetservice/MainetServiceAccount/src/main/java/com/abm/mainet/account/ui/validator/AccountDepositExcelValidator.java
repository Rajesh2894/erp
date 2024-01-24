package com.abm.mainet.account.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.abm.mainet.account.dto.AccountDepositUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

public class AccountDepositExcelValidator {

    public List<AccountDepositUploadDto> excelValidation(List<AccountDepositUploadDto> accountDepositUploadDtos,
            BindingResult bindingResult, List<LookUp> depositTypelookUp, Map<Long, String> vendorMap,
            Map<Long, String> deptMap, Map<Long, String> deptTyepsMap) throws Exception {
        int rowNo = 0;
        final List<AccountDepositUploadDto> accountDepositUploadDtoList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<AccountDepositUploadDto> accountDepositExport = accountDepositUploadDtos.stream()
                .filter(dto -> Collections.frequency(accountDepositUploadDtos, dto) > 1).collect(Collectors.toSet());
        if (accountDepositExport.isEmpty()) {

            for (AccountDepositUploadDto accountDepositUploadDto : accountDepositUploadDtos) {

                AccountDepositUploadDto dto = new AccountDepositUploadDto();
                rowNo++;
                int depositType = 0;
                if (accountDepositUploadDto.getTypeOfDeposit() == null) {
                    depositType++;
                }
                if (depositType != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositType") + rowNo));
                    break;
                }

                int depositDate = 0;
                if (accountDepositUploadDto.getDepositDate() == null) {
                    depositDate++;
                } else {
                    dto.setDepositDate(accountDepositUploadDto.getDepositDate());
                }
                if (depositDate != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositDate") + rowNo));
                    break;
                }

                int depositorName = 0;
                if (accountDepositUploadDto.getDepositerName() == null) {
                    depositorName++;
                }

                if (depositorName != 0) {
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositorName") + rowNo));
                    break;
                }

                int department = 0;
                if (accountDepositUploadDto.getDepartment() == null) {
                    department++;
                }
                if (department != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.dept") + rowNo));
                    break;
                }

                int depositHead = 0;
                if (accountDepositUploadDto.getDepositHead() == null) {
                    depositHead++;
                }

                if (depositHead != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositHead") + rowNo));
                    break;
                }

                int depositAmt = 0;
                if (accountDepositUploadDto.getDepositAmount() == null) {
                    depositAmt++;
                } else {
                    dto.setDepositAmount(accountDepositUploadDto.getDepositAmount());
                }

                if (depositAmt != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.depositAmt") + rowNo));
                    break;
                }

                int balanceAmt = 0;
                if (accountDepositUploadDto.getBalAmount() == null) {
                    balanceAmt++;
                } else {
                    dto.setBalAmount(accountDepositUploadDto.getBalAmount());
                }

                if (balanceAmt != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.balAmt") + rowNo));
                    break;
                }

                int refNo = 0;
                if (accountDepositUploadDto.getRefNo() == null) {
                    refNo++;
                } else {
                    dto.setRefNo((accountDepositUploadDto.getRefNo().replace(".0", "")));
                }

                if (refNo != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.refNo") + rowNo));
                    break;
                }

                int narration = 0;
                if (accountDepositUploadDto.getNarration() == null) {
                    narration++;
                } else {
                    dto.setNarration(accountDepositUploadDto.getNarration());
                }

                if (narration != 0) {

                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.DEPOSIT.ACCOUNT_DEPOSIT,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("account.deposit.excel.narration") + rowNo));
                    break;
                }

                int depositTypeExist = 0;
                for (LookUp list : depositTypelookUp) {
                    String accDepositType = accountDepositUploadDto.getTypeOfDeposit().trim();
                    if (accDepositType.equalsIgnoreCase(list.getLookUpDesc().trim())) {
                        dto.setTypeOfDeposit(String.valueOf(list.getLookUpId()));
                        depositTypeExist++;
                    }

                }
                if (depositTypeExist == 0) {

                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("account.deposit.excel.depositTypeExist")));
                    break;
                }

                int depositorNameExist = 0;
                for (Map.Entry<Long, String> entry : vendorMap.entrySet()) {
                    if (accountDepositUploadDto.getDepositerName().equalsIgnoreCase(entry.getValue().trim())) {
                        dto.setDepositerName(entry.getValue());
                        dto.setVendorId(entry.getKey());
                        depositorNameExist++;
                    }
                }
                if (depositorNameExist == 0) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("account.deposit.excel.depositorNameExist")));
                    break;
                }

                int departmentExist = 0;
                for (Map.Entry<Long, String> entry : deptMap.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        if (accountDepositUploadDto.getDepartment().equalsIgnoreCase(entry.getValue())) {
                            dto.setDepartment(entry.getKey().toString());
                            departmentExist++;
                        }
                    }
                }
                if (departmentExist == 0) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("account.deposit.excel.deptExist")));
                    break;
                }

                int depositHeadExist = 0;
                for (Map.Entry<Long, String> entry : deptTyepsMap.entrySet()) {
                    String depositHeads = accountDepositUploadDto.getDepositHead().trim();
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        if (depositHeads.equalsIgnoreCase(entry.getValue().trim())) {
                            dto.setDepositHead(entry.getKey().toString());
                            depositHeadExist++;
                        }
                    }
                }
                if (depositHeadExist == 0) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("account.deposit.excel.depositHeadExist")));
                    break;
                }

                int count = accountDepositUploadDto.getBalAmount()
                        .compareTo(accountDepositUploadDto.getDepositAmount());
                if (count == 1) {
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK,
                            null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("account.deposit.excel.balamt")));
                }
                accountDepositUploadDtoList.add(dto);
            }
        } else {
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                    false, new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return accountDepositUploadDtoList;
    }

}
