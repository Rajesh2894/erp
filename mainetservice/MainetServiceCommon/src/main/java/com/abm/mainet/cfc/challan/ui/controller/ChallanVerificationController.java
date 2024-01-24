package com.abm.mainet.cfc.challan.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.model.ChallanVerificationModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping({ "/ChallanVerification.html", "/challanVerification.html" })
public class ChallanVerificationController extends AbstractFormController<ChallanVerificationModel> {

    @Autowired
    private IChallanService iChallanService;

    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView challanVerification(@RequestParam("appNo") final long applicationId,@RequestParam("actualTaskId") final long actualTaskId,
            final HttpServletRequest httpServletRequest) {
    	return setData(httpServletRequest, applicationId, actualTaskId,MainetConstants.FlagP);
    }
    
	// #106212
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) {
		return setData(httpServletRequest, Long.valueOf(applicationId), taskId,MainetConstants.FlagC);		
	}
	
	public ModelAndView setData(HttpServletRequest httpServletRequest, Long applicationId, long actualTaskId,
			String taskStatus) {
		bindModel(httpServletRequest);
		ModelAndView modelAndView = null;
		final long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final ChallanMaster challanType = iChallanService.getchallanOfflineType(applicationId, orgId);
		if (challanType != null) {
			final LookUp lookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(challanType.getOflPaymentMode(),
					UserSession.getCurrent().getOrganisation());
			if (lookUp != null) {
				httpServletRequest.getSession().setAttribute("appId", applicationId);
				httpServletRequest.getSession().setAttribute("actualTaskId", actualTaskId);
				if (taskStatus.equals(MainetConstants.FlagP)) {
					if (lookUp.getLookUpCode().equals(PrefixConstants.OFFLINE_TYPE.PAY_AT_BANK)) {
						modelAndView = new ModelAndView("redirect:/ChallanUpdate.html?challanVerificationDashBoard");
					} else if (lookUp.getLookUpCode().equals(PrefixConstants.OFFLINE_TYPE.PAY_At_ULB)) {
						modelAndView = new ModelAndView(
								"redirect:/ChallanAtULBCounter.html?challanVerificationDashBoard");
					}
				} else if (taskStatus.equals(MainetConstants.FlagC)) {
					if (lookUp.getLookUpCode().equals(PrefixConstants.OFFLINE_TYPE.PAY_AT_BANK)) {
						modelAndView = new ModelAndView("CommonViewDetails", MainetConstants.FORM_NAME, this.getModel());// Implementation yet to be provided 
					} else if (lookUp.getLookUpCode().equals(PrefixConstants.OFFLINE_TYPE.PAY_At_ULB)) {
						modelAndView = new ModelAndView(
								"redirect:/ChallanAtULBCounter.html?challanVerificationDashBoardDetails");
					}
				}
				return modelAndView;
			}
		}
		return defaultExceptionFormView();
	}

}
