/**
 * @author Rahul S Chaubey
 * @since December 2019
 */

package com.abm.mainet.account.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.domain.AccountBillEntryMasterEnitity;
import com.abm.mainet.account.dto.AccountLoanMasterDto;
import com.abm.mainet.account.dto.AccountLoanReportDTO;
import com.abm.mainet.account.service.AccountFinancialReportService;
import com.abm.mainet.account.service.AccountLoanMasterService;
import com.abm.mainet.account.ui.model.LoanMasterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping("/loanreport.html")
public class LoanReportController extends AbstractFormController<LoanMasterModel> {

	@Resource
	SecondaryheadMasterService secondaryHeadMasterService;

	@Resource
	AccountFinancialReportService accountFinancialReportService;
	
	@Resource
	DepartmentService departmentService;
	
	@Resource
	AccountLoanMasterService accountLoanMasterService;
	
	@Resource
	IEmployeeService employeeService;

	
	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		loanCode(httpServletRequest);
		return index();
	}

	private String departmentName(Long deptId) {
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		String name=null;
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
			if(deptMap.containsKey(deptId))
			{
				name = deptMap.get(deptId);
			}
		}
		return name;
	}
	
	private void loanCode(final HttpServletRequest request)
	{
		List<String> id = accountFinancialReportService.getLoanCode(UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setId(id);
	}
	
	
	@ResponseBody
	@RequestMapping(params = "report", method = RequestMethod.POST)
	public ModelAndView LoanReport(final HttpServletRequest request, @RequestParam String loanCode ) throws IllegalAccessException, InvocationTargetException {
		sessionCleanup(request);
		List<AccountLoanMasterDto> listLoanData = new ArrayList<AccountLoanMasterDto>();
		AccountLoanMasterDto masterDto = new AccountLoanMasterDto();
		
		AccountLoanReportDTO finalDto = new AccountLoanReportDTO();
		listLoanData = accountLoanMasterService.findLoanMasterData(null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(), loanCode);
		BeanUtils.copyProperties(listLoanData.get(0), finalDto);
		List<TbServiceReceiptMasEntity> receiptEntity= accountFinancialReportService.receiptsForRegister(finalDto.getOrgId(), finalDto.getLoanId(),"LNR"); 
		List<AccountLoanReportDTO> reportListTemp = new ArrayList<AccountLoanReportDTO>();
		
		AccountLoanReportDTO reportDTO;
		int count = 0;
		int receiptDataCount = 0;
		boolean loanMasterData = true;
		while (count < listLoanData.get(0).getAccountLoanDetList().size()) {
			reportDTO = new AccountLoanReportDTO();
			BeanUtils.copyProperties(listLoanData.get(0).getAccountLoanDetList().get(count), reportDTO);
			 
			if(count<=receiptEntity.size()-1)
				BeanUtils.copyProperties(receiptEntity.get(count), reportDTO);
			AccountBillEntryMasterEnitity billEntity = accountFinancialReportService.amountPaidData(reportDTO.getOrgId(),reportDTO.getLnDetId());
		
			if(billEntity!=null)
			{	BeanUtils.copyProperties(billEntity,reportDTO);}
			
			reportListTemp.add(reportDTO);
			
			count++;
		}
		
		if(!reportListTemp.isEmpty() || reportListTemp!=null)
				finalDto.setAccountLoanReportDTOList(reportListTemp);
		Employee empl = employeeService.findEmployeeById(finalDto.getCreatedBy());
		finalDto.setLnDeptname(departmentName(Long.valueOf(finalDto.getLnDeptname())));
		finalDto.setEmployeeName(empl.getEmpname().toString()+" "+empl.getEmplname().toString());
		finalDto.getEmployeeName();
		this.getModel().setAccountLoanReportDto(finalDto);
		return new ModelAndView("loanReport/report", MainetConstants.FORM_NAME, this.getModel());
	}
	
	

	
	
	
}
