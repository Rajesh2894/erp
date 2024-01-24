/**
 * 
 */
package com.abm.mainet.account.ui.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.account.dto.StopPaymemtReqDto;
import com.abm.mainet.account.service.IStopPaymentService;
import com.abm.mainet.account.ui.model.StopPaymentModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Anwarul.Hassan
 * @since 10-Dec-2019
 */
@Controller
@RequestMapping(MainetConstants.PaymentEntry.STOP_PAYMENT_HTML_URL)
public class StopPaymentController extends AbstractFormController<StopPaymentModel> {
    @Autowired
    private IStopPaymentService paymentService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final Model model, final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel()
                .setCpdIdStatusLookUp(CommonMasterUtility.getValueFromPrefixLookUp(PrefixConstants.ACCOUNT_MASTERS.STOP_PAYMENT,
                        PrefixConstants.ACCOUNT_MASTERS.CHEQUE_STATUS,
                        UserSession.getCurrent().getOrganisation()));
        return index();
    }

    @ResponseBody
    @RequestMapping(params = MainetConstants.PaymentEntry.SEARCH_PAYMENT_DETAILS, method = RequestMethod.POST)
    public StopPaymemtReqDto searchPaymentDetails(
            @RequestParam(value = MainetConstants.PaymentEntry.PAYMENT_NO, required = false) String paymentNo,
            @RequestParam(value = MainetConstants.PaymentEntry.INSTRUMENT_NUMBER, required = false) Long instrumentNumber,
            @RequestParam(value = MainetConstants.PaymentEntry.PAYMENT_DATE, required = false) Date paymentDate) {
        StopPaymemtReqDto stopPaymemtReqDto = paymentService.searchPaymentDetails(paymentNo, instrumentNumber, paymentDate,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (stopPaymemtReqDto != null) {
            this.getModel().setStopPaymemtReqDto(stopPaymemtReqDto);
            if(StringUtils.equals(stopPaymemtReqDto.getChecqueStatusCode(), "ISD")) {
            	this.getModel().setStopPaymemtReqDto(stopPaymemtReqDto);
            }
        }
        return stopPaymemtReqDto;

    }
}
