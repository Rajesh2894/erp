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

import com.abm.mainet.account.domain.AccountBudgetProjectedRevenueEntryEntity;
import com.abm.mainet.account.dto.AccountBudgetProjectedRevenueEntryUploadDto;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.AccountFieldMasterBean;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

public class AccountBudgetProjectedRevenueEntryExcelValidator {

    public int count = 0;

    public List<AccountBudgetProjectedRevenueEntryUploadDto> excelValidation(
            final List<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetProjectedRevenueEntryUploadDtos,
            final BindingResult bindingResult, final Map<Long, String> financeMap, final Map<Long, String> deptMap,
            final Map<Long, String> budgetCode, List<LookUp> budgSubTypelist,List<AccountFieldMasterBean> filedlist) {
        int rowNo = 0;
        final List<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetProjectedRevenueList = new ArrayList<>();

        final ApplicationSession session = ApplicationSession.getInstance();

        Set<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetHeadExport = accountBudgetProjectedRevenueEntryUploadDtos
                .stream().filter(dto -> Collections.frequency(accountBudgetProjectedRevenueEntryUploadDtos, dto) > 1)
                .collect(Collectors.toSet());
        if (accountBudgetHeadExport.isEmpty()) {

            for (AccountBudgetProjectedRevenueEntryUploadDto accountBudgetProjectedRevenueUploadDto : accountBudgetProjectedRevenueEntryUploadDtos) {

                AccountBudgetProjectedRevenueEntryUploadDto dto = new AccountBudgetProjectedRevenueEntryUploadDto();
                rowNo++;
                int budgetYear = 0;
                if (accountBudgetProjectedRevenueUploadDto.getBudgetYear() == null) {
                    budgetYear++;
                }
                if (budgetYear != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.revenue.entry.master.faYearid1") + rowNo));
                    break;
                }

                int department = 0;
                if (accountBudgetProjectedRevenueUploadDto.getDepartment() == null
                        || accountBudgetProjectedRevenueUploadDto.getDepartment().isEmpty()) {
                    department++;
                }
                if (department != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.revenue.entry.master.department") + rowNo));
                    break;
                }

                int budgetHead = 0;
                if (accountBudgetProjectedRevenueUploadDto.getBudgetHead() == null
                        || accountBudgetProjectedRevenueUploadDto.getBudgetHead().isEmpty()) {
                    budgetHead++;
                }
                if (budgetHead != 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.revenue.entry.master.budgetHead") + rowNo));
                    break;
                }

                int originalBudget = 0;
                if (accountBudgetProjectedRevenueUploadDto.getOriginalBudget() == null
                        || accountBudgetProjectedRevenueUploadDto.getOriginalBudget().isEmpty()) {
                    originalBudget++;
                } else {
                    dto.setOriginalBudget(accountBudgetProjectedRevenueUploadDto.getOriginalBudget());
                }
                if (originalBudget != 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(
                                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    session.getMessage("budget.projected.revenue.entry.master.originalBudget") + rowNo));
                    break;
                }
                
                int filedExist=0;
                if(accountBudgetProjectedRevenueUploadDto.getField()==null
                		|| accountBudgetProjectedRevenueUploadDto.getField().isEmpty()) {
                	filedExist++;
                }else {
                	dto.setField(accountBudgetProjectedRevenueUploadDto.getField().trim());
                }
                
                if(filedExist!=0) {
                	  bindingResult.addError(
                              new org.springframework.validation.FieldError(
                                      MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME,
                                      MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                      session.getMessage("budget.projected.revenue.master.filed") + rowNo));
                }
                
                
                int budgetYearExist = 0;

                for (Map.Entry<Long, String> entry : financeMap.entrySet()) {
                    String value = entry.getValue().trim();
                    if (accountBudgetProjectedRevenueUploadDto.getBudgetYear().equals(value)) {
                        dto.setBudgetYear(entry.getKey().toString());
                        budgetYearExist++;

                    }

                }

                if (budgetYearExist == 0) {
                    count++;

                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.revenue.entry.master.budgetYearExist")));
                    break;
                }

                int departmentExist = 0;
                for (Map.Entry<Long, String> entry : deptMap.entrySet()) {
                    if (accountBudgetProjectedRevenueUploadDto.getDepartment().equalsIgnoreCase(entry.getValue())) {
                        dto.setDepartment(entry.getKey().toString());
                        departmentExist++;
                    }
                }
                if (departmentExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                            false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.revenue.entry.master.departmentExist")));
                    break;
                }

                int budgetHeadExist = 0;
                for (Map.Entry<Long, String> entry : budgetCode.entrySet()) {
                    if (accountBudgetProjectedRevenueUploadDto.getBudgetHead().equals(entry.getValue())) {
                        dto.setBudgetHead(entry.getKey().toString());
                        budgetHeadExist++;
                    }
                }
                if (budgetHeadExist == 0) {
                    count++;
                    bindingResult.addError(
                            new org.springframework.validation.FieldError(
                                    MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME,
                                    MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                                    rowNo + session.getMessage("budget.projected.revenue.entry.master.budgetHeadExist")));
                    break;
                }

                int budgetSubTypeExist = 0;
                for (LookUp lookUp : budgSubTypelist) {
                    if (accountBudgetProjectedRevenueUploadDto.getBudgetSubType()
                            .equalsIgnoreCase((lookUp.getLookUpDesc()))) {
                        dto.setBudgetSubType((String.valueOf(lookUp.getLookUpId())));
                        budgetSubTypeExist++;
                    }
                }

                if (budgetSubTypeExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            rowNo + session.getMessage("budget.projected.revenue.entry.master.budgetsubtype")));
                    break;
                }

                int fieldExist=0;
                for(AccountFieldMasterBean bean :filedlist) {
                	if(bean.getFieldCompcode().equalsIgnoreCase(accountBudgetProjectedRevenueUploadDto.getField().trim())) {
                		fieldExist++;
                	}
                }
                
                if (fieldExist == 0) {
                    count++;
                    bindingResult.addError(new org.springframework.validation.FieldError(
                            MainetConstants.SecondaryheadMaster.SECONDARY_HEAD, MainetConstants.BLANK, null, false,
                            new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("budget.projected.revenue.entry.master.filed.not.exist") + " "+rowNo));
                    break;
                }
                
                accountBudgetProjectedRevenueList.add(dto);
            }
        } else {
            count++;
            bindingResult
                    .addError(new org.springframework.validation.FieldError(
                            MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME,
                            MainetConstants.BLANK, null, false, new String[] { MainetConstants.ERRORS }, null,
                            session.getMessage("accounts.Secondaryhead.empty.excel.duplicate")));
        }
        return accountBudgetProjectedRevenueList;
    }

    public void budgetExpenditureComb(
            List<AccountBudgetProjectedRevenueEntryUploadDto> accountBudgetProjectedRevenueUploadList,
            BindingResult bindingResult, List<AccountBudgetProjectedRevenueEntryEntity> budgetProjectedRevenue) {

        final ApplicationSession session = ApplicationSession.getInstance();
        for (AccountBudgetProjectedRevenueEntryUploadDto accountBudgetRevenueDto : accountBudgetProjectedRevenueUploadList) {

            int budgetCombExist = 0;
            for (AccountBudgetProjectedRevenueEntryEntity list : budgetProjectedRevenue) {
                Long faYearid = list.getFaYearid();
                Long deptId = list.getTbDepartment().getDpDeptid();
                Long budgetId = list.getTbAcBudgetCodeMaster().getprBudgetCodeid();
                Long budgetSubTypeId = list.getCpdBugsubtypeId();
                BigDecimal orginalEstamt = list.getOrginalEstamt().setScale(2, RoundingMode.CEILING);

                if (faYearid != null && deptId != null && budgetSubTypeId != null && budgetId != null && orginalEstamt != null) {
                    if (faYearid.equals(Long.valueOf(accountBudgetRevenueDto.getBudgetYear()))
                            && deptId.equals(Long.valueOf(accountBudgetRevenueDto.getDepartment()))
                            && budgetId.equals(Long.valueOf(accountBudgetRevenueDto.getBudgetHead()))
                            && budgetSubTypeId.equals(Long.valueOf(accountBudgetRevenueDto.getBudgetSubType()))
                            && orginalEstamt.equals(new BigDecimal(accountBudgetRevenueDto.getOriginalBudget()).setScale(2,
                                    RoundingMode.CEILING))) {
                        budgetCombExist++;

                    }
                }
            }
            if (budgetCombExist != 0) {
                bindingResult.addError(new org.springframework.validation.FieldError(
                        MainetConstants.BUDGET_PROJECTED_REVENUE_ENTRY_MASTER.MAIN_ENTITY_NAME, MainetConstants.BLANK, null,
                        false,
                        new String[] { MainetConstants.ERRORS }, null,
                        session.getMessage("budget.projected.revenue.entry.master.duplicateExist")));
                break;
            }
            
            
        }
    }
}
