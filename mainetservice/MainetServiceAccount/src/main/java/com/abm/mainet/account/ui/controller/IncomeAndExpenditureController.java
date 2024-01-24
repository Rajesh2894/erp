package com.abm.mainet.account.ui.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.abm.mainet.account.dto.AccountIncomeAndExpenditureDto;
import com.abm.mainet.account.service.AccountBudgetProjectedExpenditureService;
import com.abm.mainet.account.ui.model.IncomeAndExpenditureModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Ajay.Kumar created Date :22/03/2019
 */
@Controller
@RequestMapping("/incomeAndExpenditureSheduleReport.html")
public class IncomeAndExpenditureController extends AbstractFormController<IncomeAndExpenditureModel> {
    @Resource
    AccountBudgetProjectedExpenditureService accountBudgetProjectedExpenditureService;
    @Resource
    private SecondaryheadMasterService secondaryheadMasterService;
    @Resource
    private SecondaryheadMasterService tbAcSecondaryheadMasterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.COA.name(),
                UserSession.getCurrent().getOrganisation());
        Long cpid = null;
        for (final LookUp lookUp : paymentModeList) {
            if (lookUp.getDefaultVal().equals("N") && lookUp.getLookUpDesc().equals("Income")) {
                cpid = lookUp.getLookUpId();
            } else if (lookUp.getDefaultVal().equals("Y") && lookUp.getLookUpDesc().equals("Income")) {
                cpid = lookUp.getLookUpId();
            }
        }
        final Long cpid1 = cpid;
        List<Object[]> listofPrimaryHeadId = accountBudgetProjectedExpenditureService.queryforPrimaryHead();
        Map<Long, String> primaryHead = new HashMap<Long, String>();
        listofPrimaryHeadId.parallelStream().forEach(curr -> {
            if (Long.valueOf(curr[2].toString()).equals(cpid1)) {
                primaryHead.put(Long.valueOf(curr[0].toString()), curr[1].toString());
            }
        });
        TreeMap<Long, String> sorted = new TreeMap<>();
        sorted.putAll(primaryHead);
        this.getModel().setListOfinalcialyear(secondaryheadMasterService.getAllFinincialYear(
                UserSession.getCurrent().getOrganisation().getOrgid(), UserSession.getCurrent().getLanguageId()));
        this.getModel().setListOfprimaryHead(sorted);
        return index();
    }

    @ResponseBody
    @RequestMapping(params = "incomereport", method = RequestMethod.POST)
    public ModelAndView IncomeAndExpenditureReportForm(final HttpServletRequest request,
            @RequestParam("reportType") String reportType, @RequestParam("primaryHeadId") Long primaryHeadId,
            @RequestParam("financialYearId") Long financialYearId, @RequestParam("primaryAcHeadDesc") String primaryAcHeadDesc,
            @RequestParam("financialYear") String financialYear) {
        sessionCleanup(request);
        AccountIncomeAndExpenditureDto inconeAndExpenditureDto = new AccountIncomeAndExpenditureDto();
        inconeAndExpenditureDto = accountBudgetProjectedExpenditureService.getIncomeAndExpenditureReportData(
                UserSession.getCurrent().getOrganisation().getOrgid(), primaryHeadId, financialYearId, reportType);
        inconeAndExpenditureDto.setFinancialYr(financialYear);
        String accHead=null;
        if(StringUtils.isNotBlank(primaryAcHeadDesc)) {
        	String arr[]=primaryAcHeadDesc.split("-");
        	if(arr.length>1) {
        		accHead=arr[1];
        	}
        }
        inconeAndExpenditureDto.setPrimaryAcHeadDesc(accHead);
        this.getModel().setAccountIEDto(inconeAndExpenditureDto);
        if(inconeAndExpenditureDto!=null && CollectionUtils.isNotEmpty(inconeAndExpenditureDto.getListOfIE())) {
        	this.getModel().setScheduleNo(inconeAndExpenditureDto.getListOfIE().stream().filter((ie) -> ie.getScheduleNo()!=null).findAny().get().getScheduleNo());
        	
        }
        return new ModelAndView("IExpenditureReport/form", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(method = { RequestMethod.POST }, params = "primaryHeadType")
    public @ResponseBody Map<Long, String> serchVehicleNo(@RequestParam("reportType") String reportType,
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.COA.name(),
                UserSession.getCurrent().getOrganisation());
        Long cpid = null;
        for (final LookUp lookUp : paymentModeList) {
            if (lookUp.getDefaultVal().equals("N") && lookUp.getLookUpDesc().equals(reportType)) {
                cpid = lookUp.getLookUpId();
            } else if (lookUp.getDefaultVal().equals("Y") && lookUp.getLookUpDesc().equals(reportType)) {
                cpid = lookUp.getLookUpId();
            }
        }
        final Long cpid1 = cpid;
        List<Object[]> listofPrimaryHeadId = accountBudgetProjectedExpenditureService.queryforPrimaryHead();
        Map<Long, String> primaryHead = new HashMap<Long, String>();
        listofPrimaryHeadId.parallelStream().forEach(curr -> {
            if (Long.valueOf(curr[2].toString()).equals(cpid1)) {
                primaryHead.put(Long.valueOf(curr[0].toString()), curr[1].toString());
            }
        });
        TreeMap<Long, String> sorted = new TreeMap<>();
        sorted.putAll(primaryHead);
        return sorted;
    }

}
