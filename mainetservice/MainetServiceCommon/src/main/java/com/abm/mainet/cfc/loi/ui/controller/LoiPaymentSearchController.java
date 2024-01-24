/**
 *
 */
package com.abm.mainet.cfc.loi.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.loi.dto.LoiPaymentSearchDTO;
import com.abm.mainet.cfc.loi.ui.model.LoiPaymentModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/LoiPayment.html")
public class LoiPaymentSearchController extends AbstractFormController<LoiPaymentModel> {
	
	 @Autowired
	    private ICFCApplicationMasterService iCFCAppMasterService;

	private static final Logger logger = LoggerFactory.getLogger(LoiPaymentSearchController.class);
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        this.getModel().setCommonHelpDocs("LoiPayment.html");
        bindModel(httpServletRequest);
        getModel().setPageUrl(MainetConstants.FlagL);
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "searchLOIRecords")
    public ModelAndView searchLOIRecords(final HttpServletRequest httpServletRequest) throws ClassNotFoundException, LinkageError {
        bindModel(httpServletRequest);
        final LoiPaymentModel model = getModel();
        final boolean result = model.getLoiData(MainetConstants.PAY_STATUS.NOT_PAID);
        if(model.getSearchDto().getReferenceNo() != null && !model.getSearchDto().getReferenceNo().isEmpty()){
        model.setRefNo(model.getSearchDto().getReferenceNo());
        }
        if (!result) {
            getModel().addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
        }
        return index();
    }

	@RequestMapping(params = "showDetails", method = RequestMethod.POST)
	public ModelAndView populateViewData(@RequestParam final long appNo,
			@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest httpServletRequest) throws ClassNotFoundException, LinkageError {
		setData(httpServletRequest, appNo, actualTaskId, MainetConstants.PAY_STATUS.NOT_PAID);
		logger.info("Returning the JSP page");
		return new ModelAndView("LoiDetails", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	// #106212
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws ClassNotFoundException, LinkageError {
		setData(httpServletRequest, Long.valueOf(applicationId), taskId, MainetConstants.PAY_STATUS.PAID);
		return new ModelAndView("LoiViewDetails", MainetConstants.CommonConstants.COMMAND, getModel());
	}

	// #106212
	public void setData(HttpServletRequest httpServletRequest, Long appNo, long actualTaskId, String status) throws ClassNotFoundException, LinkageError {
		sessionCleanup(httpServletRequest);
		bindModel(httpServletRequest);
		if (appNo != 0) {
			final LoiPaymentModel model = getModel();
			model.setTaskId(actualTaskId);
			model.setStatus(status);
			final LoiPaymentSearchDTO searchdata = model.getSearchDto();
			searchdata.setApplicationId(appNo);
			final boolean result = model.getLoiData(status);
			searchdata.setReferenceNo("");
			TbCfcApplicationMstEntity appMst = iCFCAppMasterService.getCFCApplicationByRefNoOrAppNo(null, appNo, UserSession.getCurrent().getOrganisation().getOrgid());
			if(appMst.getRefNo() != null && !appMst.getRefNo().isEmpty()){
			searchdata.setReferenceNo(appMst.getRefNo());
			model.setRefNo(appMst.getRefNo());
			}
			getModel().setPageUrl(MainetConstants.FlagC);
			if (!result) {
				getModel().addValidationError(ApplicationSession.getInstance().getMessage("no.record.found"));
			}
		}
	}
}
