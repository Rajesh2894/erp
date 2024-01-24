package com.abm.mainet.property.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.abm.mainet.common.constant.ServiceEndpoints;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants.LookUp;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.service.PrimaryPropertyService;
import com.abm.mainet.property.ui.model.PropertyBillPaymentModel;
import com.abm.mainet.property.ui.model.ProvisionalDetailOutstandingRegisterModel;

@Controller
@RequestMapping("/penalInterestReport.html")
public class PenalInterestCalculationReportController extends AbstractFormController<PropertyBillPaymentModel> {

	@Autowired
	private PrimaryPropertyService primaryPropertyService;

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(final HttpServletRequest httpServletRequest, final Model models) {
		sessionCleanup(httpServletRequest);
		PropertyBillPaymentModel model = this.getModel();
		return index();
	}

	@RequestMapping(params = "getPenalInterest", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String viewDetailSheet(@RequestParam("propNumber") String propNumber,
			@RequestParam("flatNo") String flatNo, final HttpServletRequest request) {

		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
           
            if(flatNo.isEmpty()) {
            	flatNo=MainetConstants.FlagY;
            }
         

		return ServiceEndpoints.WATER_BIRT_REPORT_URL + "=RP_PenalInterestCalculationReport.rptdesign&RP_ORGID="
				+ currentOrgId + "&RP_PropNo=" + propNumber + "&RP_FlatNo=" + flatNo;

	}

	@ResponseBody
	@RequestMapping(params = "getBillingMethod", method = { RequestMethod.POST })
	public List<String> getBillingMethodAndFlatList(@RequestParam("propNo") String propNo, HttpServletRequest request) {
		this.getModel().bind(request);
		PropertyBillPaymentModel model = this.getModel();
		List<String> flatNoList = null;
		String billingMethod = null;
		Long billingMethodId = primaryPropertyService.getBillMethodIdByPropNo(propNo,
				UserSession.getCurrent().getOrganisation().getOrgid());
		com.abm.mainet.common.utility.LookUp billingMethodLookUp = null;
		try {
			billingMethodLookUp = CommonMasterUtility.getNonHierarchicalLookUpObject(billingMethodId,
					UserSession.getCurrent().getOrganisation());
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (billingMethodLookUp != null) {
			billingMethod = billingMethodLookUp.getLookUpCode();
		}
		this.getModel().setBillingMethod(billingMethod);
		if (StringUtils.isNotBlank(billingMethod) && StringUtils.equals(billingMethod, MainetConstants.FlagI)) {
			flatNoList = new ArrayList<String>();
			flatNoList = primaryPropertyService.getFlatNoIdByPropNo(propNo,
					UserSession.getCurrent().getOrganisation().getOrgid());
		}
		model.setFlatNoList(flatNoList);
		return flatNoList;
	}

}