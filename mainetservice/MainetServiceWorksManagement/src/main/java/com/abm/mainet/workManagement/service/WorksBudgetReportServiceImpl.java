package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbTaxMas;
import com.abm.mainet.common.master.service.TbTaxMasService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.workManagement.dao.WorksBudgetReportDao;
import com.abm.mainet.workManagement.dto.WorksBudgetReportDto;

@Service
public class WorksBudgetReportServiceImpl implements WorksBudgetReportService {
	
	 @Resource
	    private TbTaxMasService tbTaxMasService;

    @Override
    @Transactional(readOnly = true)
    public List<WorksBudgetReportDto> getBudgetVsProjExpensesReport(Date fromDate, Date toDate, Long orgId) {

        List<WorksBudgetReportDto> BudgetReportDtoList = new ArrayList<>();
        List<Object[]> budgetReport = ApplicationContextProvider.getApplicationContext().getBean(WorksBudgetReportDao.class)
                .getBudgetVsProjExpensesReport(fromDate, toDate, orgId);
       
        WorksBudgetReportDto budgetVsProjExpensesDto = null;
        for (Object[] budget : budgetReport) {
        	BigDecimal amount = null;
        	BigDecimal amounts = null;
        	BigDecimal billAmount = null;
        	
        	TbTaxMas taxMas = null ;
            budgetVsProjExpensesDto = new WorksBudgetReportDto();

            budgetVsProjExpensesDto.setProjId((Long) budget[0]);
            budgetVsProjExpensesDto.setProjCode((String) budget[1]);
            budgetVsProjExpensesDto.setProjNameEng((String) budget[2]);
            budgetVsProjExpensesDto.setProjNameReg((String) budget[3]);
            if (budget[4] != null) {
                budgetVsProjExpensesDto.setProjStartDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(budget[4]));
            }
            if (budget[5] != null) {
                budgetVsProjExpensesDto.setProjEndDateDesc(new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(budget[5]));
            }
            budgetVsProjExpensesDto.setWorkId((Long) budget[6]);
            budgetVsProjExpensesDto.setWorkcode((String) budget[7]);
            budgetVsProjExpensesDto.setWorkName((String) budget[8]);
          
            if (budget[9] != null) {
                budgetVsProjExpensesDto.setWorkStartDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(budget[9]));
            }
            if (budget[10] != null) {
                budgetVsProjExpensesDto.setWorkEndDateDesc(
                        new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(budget[10]));
            }

            budgetVsProjExpensesDto.setSacHeadId((Long) budget[11]);
          
            budgetVsProjExpensesDto.setAcHeadCode((String) budget[12]);
            budgetVsProjExpensesDto.setFinanceCodeDesc((String) budget[13]);          
            budgetVsProjExpensesDto.setContAmount(((BigDecimal) budget[14]).setScale(2, RoundingMode.HALF_EVEN));
            budgetVsProjExpensesDto.setMbTotalAmt((BigDecimal) budget[15]);
            budgetVsProjExpensesDto.setMbTotalAmtUnderApproval((BigDecimal) budget[16]);
             //Defect #92549
            budgetVsProjExpensesDto.setTaxId((Long) budget[17]);
            budgetVsProjExpensesDto.setRaBillTaxAmt((BigDecimal) budget[18]);
            if( budget[17] != null) 
            	taxMas = tbTaxMasService.findById((Long) budget[17], orgId);
   
            /*if(taxMas.getTaxDesc() != null && taxMas.getTaxDesc().equalsIgnoreCase("Withheld Deduction Tax")) { */
            	if(budgetVsProjExpensesDto.getMbTotalAmt() != null && budgetVsProjExpensesDto.getRaBillTaxAmt() != null) {
            	amounts= budgetVsProjExpensesDto.getMbTotalAmt().subtract(budgetVsProjExpensesDto.getRaBillTaxAmt());           	
            }else {
            	amounts=budgetVsProjExpensesDto.getMbTotalAmt();	
            }
            	if(budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() != null && budgetVsProjExpensesDto.getRaBillTaxAmt() != null) {
            	billAmount= budgetVsProjExpensesDto.getMbTotalAmtUnderApproval().subtract(budgetVsProjExpensesDto.getRaBillTaxAmt());
            }
            else {    	
            	billAmount=budgetVsProjExpensesDto.getMbTotalAmtUnderApproval();
             }
           /* }*/
            if(amounts != null)
            budgetVsProjExpensesDto.setMbTotalAmt(amounts);
            if(billAmount != null)
            budgetVsProjExpensesDto.setMbTotalAmtUnderApproval(billAmount);
          
            
            if(budgetVsProjExpensesDto.getContAmount()!=null && budgetVsProjExpensesDto.getMbTotalAmt() != null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() != null) {
            	amount = budgetVsProjExpensesDto.getContAmount().subtract(budgetVsProjExpensesDto.getMbTotalAmt().add(budgetVsProjExpensesDto.getMbTotalAmtUnderApproval()));
            }
            else if(budgetVsProjExpensesDto.getContAmount()!=null && budgetVsProjExpensesDto.getMbTotalAmt() != null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval()==null){
            	amount=budgetVsProjExpensesDto.getContAmount().subtract(budgetVsProjExpensesDto.getMbTotalAmt());
            }
            else if (budgetVsProjExpensesDto.getMbTotalAmt() != null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() != null && budgetVsProjExpensesDto.getContAmount()==null) {
				amount=budgetVsProjExpensesDto.getMbTotalAmt().subtract(budgetVsProjExpensesDto.getMbTotalAmtUnderApproval());
			}
            else if(budgetVsProjExpensesDto.getContAmount()!=null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() != null && budgetVsProjExpensesDto.getMbTotalAmt() == null) {
            	amount=budgetVsProjExpensesDto.getContAmount().subtract(budgetVsProjExpensesDto.getMbTotalAmtUnderApproval());
            }
            else if(budgetVsProjExpensesDto.getContAmount()!=null && budgetVsProjExpensesDto.getMbTotalAmt() == null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() == null) {
            	amount=budgetVsProjExpensesDto.getContAmount();
            }
            else if(budgetVsProjExpensesDto.getMbTotalAmt() != null && budgetVsProjExpensesDto.getContAmount()==null && budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() == null) {
            	amount=budgetVsProjExpensesDto.getMbTotalAmt();
            }
            else if(budgetVsProjExpensesDto.getMbTotalAmtUnderApproval() != null && budgetVsProjExpensesDto.getContAmount()==null && budgetVsProjExpensesDto.getMbTotalAmt() == null ) {
            	amount=budgetVsProjExpensesDto.getMbTotalAmtUnderApproval();
            }
                                     
            if (amount != null) {
            	
                budgetVsProjExpensesDto.setBudgetBalanceAmt(amount.setScale(2, RoundingMode.HALF_EVEN));
            }

            BudgetReportDtoList.add(budgetVsProjExpensesDto);

        }

        return BudgetReportDtoList;
    }

}
