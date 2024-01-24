package com.abm.mainet.account.ui.validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

import com.abm.mainet.account.domain.AccountBudgetProjectedExpenditureEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedExpenditureUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;

public class AccountBudgetProjectedExpenditureExcelValidator {

    public int count = 0;

    public List<AccountBudgetProjectedExpenditureUploadDto> excelValidation(
            final List<AccountBudgetProjectedExpenditureUploadDto> accountBudgetProjectedExpenditureUploadDtos,
            final BindingResult bindingResult, final Map<Long, String> financeMap, final Map<Long, String> deptMap,
            final Map<Long, String> budgetCode, List<LookUp> budgSubTypelist,List<AccountFieldMasterBean> filedlist) {
        int rowNo = 0;
        final List<AccountBudgetProjectedExpenditureUploadDto> accountBudgetProjectedExpenditureList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<AccountBudgetProjectedExpenditureUploadDto> accountBudgetHeadExport = accountBudgetProjectedExpenditureUploadDtos
                .stream().filter(dto -> Collections.frequency(accountBudgetProjectedExpenditureUploadDtos, dto) > 1)
                .collect(Collectors.toSet());
        if (accountBudgetHeadExport.isEmpty()) {

            for (AccountBudgetProjectedExpenditureUploadDto accountBudgetProjectedExpenditureUploadDto : accountBudgetProjectedExpenditureUploadDtos) {

                AccountBudgetProjectedExpenditureUploadDto dto = new AccountBudgetProjectedExpenditureUploadDto();
                rowNo++;
                int budgetYear = 0;
                if (accountBudgetProjectedExpenditureUploadDto.getBudgetYear() == null
                        || accountBudgetProjectedExpenditureUploadDto.getBudgetYear().isEmpty()) {
                    budgetYear++;
                }
                if (budgetYear != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.expenditure.master.faYearid1") + rowNo));
                    break;
                }

                int department = 0;
                if (accountBudgetProjectedExpenditureUploadDto.getDepartment() == null
                        || accountBudgetProjectedExpenditureUploadDto.getDepartment().isEmpty()) {
                    department++;
                }
                if (department != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.expenditure.master.department") + rowNo));
                    break;
                }

                int budgetHead = 0;
                if (accountBudgetProjectedExpenditureUploadDto.getBudgetHead() == null
                        || accountBudgetProjectedExpenditureUploadDto.getBudgetHead().isEmpty()) {
                    budgetHead++;
                }
                if (budgetHead != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.expenditure.master.budgetHead") + rowNo));
                    break;
                }

                int filedExist=0;
                if(accountBudgetProjectedExpenditureUploadDto.getField()==null || accountBudgetProjectedExpenditureUploadDto.getField().isEmpty()) {
                	filedExist++;
                }else {
                	dto.setField(accountBudgetProjectedExpenditureUploadDto.getField().trim());
                }
                
                if (filedExist != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.expenditure.master.filed") + rowNo));
                    break;
                }
                
                int originalBudget = 0;
                if (accountBudgetProjectedExpenditureUploadDto.getOriginalBudget() == null
                        || accountBudgetProjectedExpenditureUploadDto.getOriginalBudget().isEmpty()) {
                    originalBudget++;
                } else {
                    dto.setOriginalBudget(accountBudgetProjectedExpenditureUploadDto.getOriginalBudget());
                }
                if (originalBudget != 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(
                                    MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("budget.projected.expenditure.master.originalBudget") + rowNo));
                    break;
                }
                int budgetYearExist = 0;

                for (Map.Entry<Long, String> entry : financeMap.entrySet()) {
                    String value = entry.getValue().trim();
                    if (accountBudgetProjectedExpenditureUploadDto.getBudgetYear().equals(value)) {
                        dto.setBudgetYear(entry.getKey().toString());
                        budgetYearExist++;

                    }

                }

                if (budgetYearExist == 0) {
                    count++;

                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.expenditure.master.budgetYearExist")));
                    break;
                }

                int departmentExist = 0;
                for (Map.Entry<Long, String> entry : deptMap.entrySet()) {
                    if (accountBudgetProjectedExpenditureUploadDto.getDepartment().equalsIgnoreCase(entry.getValue())) {
                        dto.setDepartment(entry.getKey().toString());
                        departmentExist++;
                    }
                }
                if (departmentExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.expenditure.master.departmentExist")));
                    break;
                }

                int budgetHeadExist = 0;
                for (Map.Entry<Long, String> entry : budgetCode.entrySet()) {
                    if (accountBudgetProjectedExpenditureUploadDto.getBudgetHead().equals(entry.getValue())) {
                        dto.setBudgetHead(entry.getKey().toString());
                        budgetHeadExist++;
                    }
                }
                if (budgetHeadExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.expenditure.master.budgetHeadExist")));
                    break;
                }
                int budgetSubTypeExist = 0;
                for (LookUp lookUp : budgSubTypelist) {
                    if (accountBudgetProjectedExpenditureUploadDto.getBudgetSubType().trim()
                            .equalsIgnoreCase((lookUp.getLookUpDesc().trim()))) {
                        dto.setBudgetSubType((String.valueOf(lookUp.getLookUpId())));
                        budgetSubTypeExist++;
                    }
                }
                if (budgetSubTypeExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.expenditure.master.budgetsubtype")));
                    break;
                }
                
                int fieldExist=0;
                for(AccountFieldMasterBean bean :filedlist) {
                	if(bean.getFieldCompcode().equalsIgnoreCase(accountBudgetProjectedExpenditureUploadDto.getField().trim())) {
                		fieldExist++;
                	}
                }
                
                if (fieldExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.revenue.entry.master.filed.not.exist") +" "+rowNo));
                    break;
                }
                
                accountBudgetProjectedExpenditureList.add(dto);
            }
        } else {
            count++;
            bindingResult
                    .addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return accountBudgetProjectedExpenditureList;
    }

    public void budgetExpenditureComb(
            List<AccountBudgetProjectedExpenditureUploadDto> accountBudgetProjectedExpenditureUploadList,
            BindingResult bindingResult, List<AccountBudgetProjectedExpenditureEntity> budgetProjectedExpenditure) {

        final ApplicationSession session = ApplicationSession.getInstance();
        for (AccountBudgetProjectedExpenditureUploadDto accountBudgetExpenditureDto : accountBudgetProjectedExpenditureUploadList) {

            int budgetCombExist = 0;
            for (AccountBudgetProjectedExpenditureEntity list : budgetProjectedExpenditure) {
                Long faYearid = list.getFaYearid();
                Long deptId = list.getTbDepartment().getDpDeptid();
                Long budgetSubTypeId = list.getCpdBugsubtypeId();
                Long budgetId = list.getTbAcBudgetCodeMaster().getprBudgetCodeid();
                BigDecimal orginalEstamt = list.getOrginalEstamt().setScale(2, RoundingMode.CEILING);

                if (faYearid != null && deptId != null && budgetSubTypeId != null && budgetId != null && orginalEstamt != null) {
                    if (faYearid.equals(Long.valueOf(accountBudgetExpenditureDto.getBudgetYear()))
                            && deptId.equals(Long.valueOf(accountBudgetExpenditureDto.getDepartment()))
                            && budgetSubTypeId.equals(Long.valueOf(accountBudgetExpenditureDto.getBudgetSubType()))
                            && budgetId.equals(Long.valueOf(accountBudgetExpenditureDto.getBudgetHead()))
                            && orginalEstamt.equals(new BigDecimal(accountBudgetExpenditureDto.getOriginalBudget()).setScale(2,
                                    RoundingMode.CEILING))) {
                        budgetCombExist++;

                    }
                }
            }
            if (budgetCombExist != 0) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null, false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("budget.projected.expenditure.master.duplicateExist")));
                break;
            }
        
        }
    }
}