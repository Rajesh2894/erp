package com.abm.mainet.account.ui.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.PaymentEntryDto;
import com.abm.mainet.account.service.DeductionRegisterService;
import com.abm.mainet.account.ui.model.DeductionRegisterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/*
 * Object: Deduction Register(Story-99) Author By:- Ajay Kumar
 * Date: 10-03-2018
 */
@Controller
@RequestMapping("/DeductionRegister.html")
public class AccountDeductionRegisterController extends AbstractFormController<DeductionRegisterModel> {

    @Resource
    private DeductionRegisterService deductionRegisterService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setTdsLookUpList(
                CommonMasterUtility.getListLookup(PrefixConstants.TDS, UserSession.getCurrent().getOrganisation()));
        return index();
    }

    /*
     * Object: Deduction Register Report(Story-99) Author By:- Ajay Kumar Date: 10-03-2018
     */
    @ResponseBody
    @RequestMapping(params = "getAccountDeductionRegisterData", method = RequestMethod.POST)
    public ModelAndView viewAbstractreportSheet(@RequestParam("frmDate") String frmDate, @RequestParam("todate") String todate,
            @RequestParam("tdsTypeId") Long tdsTypeId) {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        PaymentEntryDto paymentEntryDto;
        paymentEntryDto = deductionRegisterService.getDeductionRegisterData(frmDate, todate, orgId, tdsTypeId);
        paymentEntryDto.setFromDate(frmDate);
        paymentEntryDto.setToDate(todate);
        String tdsTypeDesc = CommonMasterUtility.findLookUpDesc(PrefixConstants.TDS, orgId, tdsTypeId);
        paymentEntryDto.setTdsTypeDesc(tdsTypeDesc);
        // paymentEntryDto.setTdsTypeId(tdsTypeId);
        this.getModel().setPaymentEntryDto(paymentEntryDto);
        return new ModelAndView("AccountDeductionRegister/Report", MainetConstants.FORM_NAME, this.getModel());
    }

}
