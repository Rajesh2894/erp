package com.abm.mainet.cfc.challan.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.service.IChallanAtULBCounterService;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.model.ChallanAtULBCounterModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dto.ApplicationFormChallanDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping(MainetConstants.WINDOWS_SLASH
        + MainetConstants.FORM_URL.PAY_AT_ULB_COUNTER)
public class ChallanAtULBCounterController extends
        AbstractFormController<ChallanAtULBCounterModel> {

    @Autowired
    private IChallanService iChallanService;
    
    @Resource
	private BankMasterService bankMasterService;

    static Logger logger = LoggerFactory
            .getLogger(ChallanAtULBCounterController.class);

    @Autowired
    IChallanAtULBCounterService challanAtULBCounterService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        getModel().setPageUrlFlag(MainetConstants.Common_Constant.NO);
        return index();
    }

    @RequestMapping(method = RequestMethod.GET, params = "clean")
    public ModelAndView clean(final HttpServletRequest httpServletRequest) {
        final ChallanAtULBCounterModel model = getModel();
        model.setExpiredFlag(false);
        model.setApplicationNo(null);
        model.setChallanDetails(null);
        model.setChallanNo(null);
        model.setOffline(new CommonChallanDTO());
        model.setPageUrlFlag(MainetConstants.Common_Constant.NO);
        return index();
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "search")
    public ModelAndView search(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ChallanAtULBCounterModel model = getModel();
        model.setExpiredFlag(false);
        model.setOffline(new CommonChallanDTO());
        model.setChallanDetails(null);
        model.searchChallanDetails(MainetConstants.PAY_STATUS.NOT_PAID);
        return index();
    }

    @RequestMapping(params = "PrintULBCounterReceipt", method = RequestMethod.GET)
    public ModelAndView printCounterReceipt(
            final HttpServletRequest httpServletRequest, final HttpServletResponse response) {
        bindModel(httpServletRequest);
        final ChallanAtULBCounterModel model = (ChallanAtULBCounterModel) httpServletRequest
                .getAttribute(MainetConstants.FORM_NAME);
        return new ModelAndView("ChallanAtULBReceiptPrint",
                MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "PrintCounterReceipt", method = RequestMethod.GET)
    public ModelAndView PrintCounterReceipt(
            final HttpServletRequest httpServletRequest, final HttpServletResponse response) {
        bindModel(httpServletRequest);
        final ChallanAtULBCounterModel model = (ChallanAtULBCounterModel) httpServletRequest
                .getAttribute(MainetConstants.FORM_NAME);

        if (model.getOffline().getChallanServiceType().equals(MainetConstants.CHALLAN_RECEIPT_TYPE.MIXED) || model
                .getOffline().getChallanServiceType().equals(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED)) {
            return new ModelAndView("revenueReceiptPrint",
                    MainetConstants.FORM_NAME, model);
        }
        return new ModelAndView("ChallanAtULBReceiptPrint",
                MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "bankCode", method = RequestMethod.POST)
    public @ResponseBody String getBankCode(
            @RequestParam("cbBankCode") final long cbBankId) {
    	//#134943 - to get newly added bank code also
        final String cbBankCode = bankMasterService.getIfscCodeById(cbBankId);
		/*
		 * String cbBankCode = MainetConstants.BLANK; for (final BankMasterEntity
		 * bankDetail : details) {
		 * 
		 * if (bankDetail.getBankId() == cbBankId) { cbBankCode = bankDetail.getIfsc();
		 * break; } }
		 */
        return cbBankCode;
    }

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "challanVerificationDashBoard")
	public ModelAndView challanVerificationDashBoard(final HttpServletRequest httpServletRequest) {
		setchallanVerificationDashBoardData(httpServletRequest,MainetConstants.PAY_STATUS.NOT_PAID);
		return new ModelAndView("ChallanVerificationDashBoardUlb", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "challanVerificationDashBoardDetails")
	public ModelAndView challanVerificationDashBoardDetails(final HttpServletRequest httpServletRequest) {
		setchallanVerificationDashBoardData(httpServletRequest,MainetConstants.PAY_STATUS.PAID);
		return new ModelAndView("ChallanVerificationDashBoardUlbDetails", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	public void setchallanVerificationDashBoardData(HttpServletRequest httpServletRequest,String payStatus) {
		bindModel(httpServletRequest);
		final Long applicationId = (Long) httpServletRequest.getSession().getAttribute("appId");
		final Long taskId = (Long) httpServletRequest.getSession().getAttribute("actualTaskId");
		final ChallanAtULBCounterModel model = getModel();
		model.setTaskId(taskId);
		model.setApplicationNo(applicationId);
		model.setChallanDetails(null);
		model.searchChallanDetails(payStatus);
		model.setExpiredFlag(false);
		model.setOffline(null);
		model.setPageUrlFlag(MainetConstants.FlagC);
	}
    
    @RequestMapping(method = RequestMethod.POST, params = "regenerateChallan")
    public ModelAndView regenerateChallan(final HttpServletRequest httpServletRequest)
            throws ClassNotFoundException, LinkageError {
        bindModel(httpServletRequest);
        if (getModel().regenerateChallan()) {
            return jsonResult(JsonViewObject.successResult(getModel().getSuccessMessage()));
        }
        logger.error("error in regenerate Challan charge not found for challan no: "
                + getModel().getChallanDetails().getChallanNo());
        return defaultExceptionFormView();
    }

    @Override
    @RequestMapping(params = "PrintRegenerateChallan")
    public ModelAndView printRTIStatusReport(final HttpServletRequest request) {
        logger.info("Start the PrintRegenerateReportULB()");
        bindModel(request);
        final ChallanAtULBCounterModel model = getModel();
        final CommonChallanDTO challanDTO = model.getOfflineDTO();
        final ApplicationFormChallanDTO challanDetails = iChallanService.getChallanData(challanDTO,
                UserSession.getCurrent().getOrganisation());
        model.setChallanDTO(challanDetails);
        return new ModelAndView("CommonChallanULBReport", MainetConstants.FORM_NAME, model);
    }

}
