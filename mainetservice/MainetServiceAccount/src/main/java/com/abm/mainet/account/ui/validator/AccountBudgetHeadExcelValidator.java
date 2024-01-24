package com.abm.mainet.account.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.abm.mainet.account.dto.AccountBudgetCodeUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.domain.AccountBudgetCodeEntity;
import com.abm.mainet.common.utility.ApplicationSession;

public class AccountBudgetHeadExcelValidator {

    public int count = 0;

    public List<AccountBudgetCodeUploadDto> excelValidation(
            final List<AccountBudgetCodeUploadDto> accountBudgetCodeUploadDtos,
            final BindingResult bindingResult, final Map<Long, String> primaryHead,
            final Map<Long, String> functionHead, Map<String, String> primaryCompositeCodeAndDesc, Map<Long, String> objectHead,
            Organisation org, String primaryHeadFlag, String objectHeadFlag) {
        int rowNo = 0;
        final List<AccountBudgetCodeUploadDto> accountBudgetHeadList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<AccountBudgetCodeUploadDto> accountBudgetHeadExport = accountBudgetCodeUploadDtos.stream()
                .filter(dto -> Collections.frequency(accountBudgetCodeUploadDtos, dto) > 1)
                .collect(Collectors.toSet());

        if (accountBudgetHeadExport.isEmpty()) {

            for (AccountBudgetCodeUploadDto accountBudgetCodeUploadDto : accountBudgetCodeUploadDtos) {

                AccountBudgetCodeUploadDto dto = new AccountBudgetCodeUploadDto();
                rowNo++;
                int countPrimaryHead = 0;
                if (accountBudgetCodeUploadDto.getPrimaryHead() == null
                        || accountBudgetCodeUploadDto.getPrimaryHead().isEmpty()) {
                    countPrimaryHead++;
                }
                if (countPrimaryHead != 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("accounts.BudgetCode.empty.excel.primaryhead") + rowNo));
                    break;
                }

                int countFunctionHead = 0;
                if (accountBudgetCodeUploadDto.getFunction() == null || accountBudgetCodeUploadDto.getFunction().isEmpty()) {
                    countFunctionHead++;
                }
                if (countFunctionHead != 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("accounts.BudgetCode.empty.excel.function") + rowNo));
                    break;
                }

                int primaryHeadExist = 0;

                if (primaryHeadFlag != null && !primaryHeadFlag.isEmpty()) {
                    if (primaryHeadFlag.equals(MainetConstants.Y_FLAG)) {

                        for (Map.Entry<Long, String> entry : primaryHead.entrySet()) {
                            if (accountBudgetCodeUploadDto.getPrimaryHead().equals(entry.getValue())) {
                                dto.setPrimaryHead(entry.getKey().toString());
                                primaryHeadExist++;
                            }
                        }
                        String BudgetHeadCode = null;
                        for (Map.Entry<String, String> entry : primaryCompositeCodeAndDesc.entrySet()) {

                            if (accountBudgetCodeUploadDto.getPrimaryHead().equals(entry.getKey())) {
                                BudgetHeadCode = accountBudgetCodeUploadDto.getFunction() + " - "
                                        + accountBudgetCodeUploadDto.getPrimaryHead() + " - " + entry.getValue();
                                dto.setBudgetHeadCodeDescription(BudgetHeadCode);
                            }
                        }
                    }
                }

                if (objectHeadFlag != null && !objectHeadFlag.isEmpty()) {
                    if (objectHeadFlag.equals(MainetConstants.Y_FLAG)) {

                        for (Map.Entry<Long, String> entry : objectHead.entrySet()) {
                            String value = entry.getValue().replaceAll("(\\r|\\n)", "");
                            String value1 = value.replaceAll("\\s", "");
                            String accountHead =accountBudgetCodeUploadDto.getPrimaryHead().trim();
                            String acHeadCode = accountHead.replaceAll("\\s", "");
                            if (acHeadCode.equalsIgnoreCase(value1)) {
                                dto.setPrimaryHead(entry.getKey().toString());
                                primaryHeadExist++;
                            }
                        }
                        String BudgetHeadCode = null;
                        for (Map.Entry<Long, String> entry1 : objectHead.entrySet()) {

                            String value = entry1.getValue().replaceAll("(\\r|\\n)", "");
                            String value1 = value.replaceAll("\\s", "");
                            String accountHead =accountBudgetCodeUploadDto.getPrimaryHead().trim();
                            String acHeadCode = accountHead.replaceAll("\\s", "");
                            if (acHeadCode.equalsIgnoreCase(value1)) {
                                BudgetHeadCode =accountBudgetCodeUploadDto.getPrimaryHead().trim();
                                dto.setBudgetHeadCodeDescription(BudgetHeadCode);
                            }
                        }
                    }
                }

                if (primaryHeadExist == 0) {
                    count++;

                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("accounts.BudgetCode.db.primaryheadcode")));
                    break;
                }

                int functionHeadExist = 0;
                for (Map.Entry<Long, String> entry : functionHead.entrySet()) {
                    if (accountBudgetCodeUploadDto.getFunction().trim().equals(entry.getValue())) {
                        dto.setFunction(entry.getKey().toString());
                        functionHeadExist++;
                    }
                }
                if (functionHeadExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("accounts.BudgetCode.db.functioncode")));
                    break;
                }

                accountBudgetHeadList.add(dto);
            }
        }

        else {
            count++;
            bindingResult.addError(new org.springframework.validation.FieldError(
                    MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                    new String[] { MainetConstants.ERRORS }, null,
                    session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return accountBudgetHeadList;
    }

    public void priFunCombValdn(List<AccountBudgetCodeUploadDto> accountBudgetCodeUploadList,
            BindingResult bindingResult, List<AccountBudgetCodeEntity> budgetHeadCodes) {
        final ApplicationSession session = ApplicationSession.getInstance();
        for (AccountBudgetCodeUploadDto budgetCodeUploadDto : accountBudgetCodeUploadList) {

            int prifunExists = 0;
            for (AccountBudgetCodeEntity list : budgetHeadCodes) {
                Long pacHeadId = null;
                Long functionId = null;
                if (list.getTbAcPrimaryheadMaster() != null) {
                    pacHeadId = list.getTbAcPrimaryheadMaster().getPrimaryAcHeadId();
                }
                if (list.getTbAcFunctionMaster() != null) {
                    functionId = list.getTbAcFunctionMaster().getFunctionId();
                }
                if (pacHeadId != null && functionId != null) {
                    if (pacHeadId.equals(Long.valueOf(budgetCodeUploadDto.getPrimaryHead()))
                            && (functionId.equals(Long.valueOf(budgetCodeUploadDto.getFunction())))) {
                        prifunExists++;
                    }
                }
            }
            if (prifunExists != 0) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.BUDGET_CODE.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null, session.getMessage("accounts.BudgetCode.db.duplicate")));
                break;
            }
        }
    }
}
