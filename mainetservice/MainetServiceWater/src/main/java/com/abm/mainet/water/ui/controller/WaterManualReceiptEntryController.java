/**
 * 
 */
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.water.ui.model.WaterBillPaymentModel;

/**
 * @author cherupelli.srikanth
 *@since 29 june 2020
 */
@Controller
@RequestMapping("/WaterManualReceiptEntry.html")
public class WaterManualReceiptEntryController extends AbstractFormController<WaterBillPaymentModel>{

	@RequestMapping(method = { RequestMethod.POST })
	public ModelAndView index(HttpServletRequest request) {
		sessionCleanup(request);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		getModel().bind(request);
		getModel().setReceiptType(MainetConstants.FlagM);
		this.getModel().setShowMode(MainetConstants.FlagN);
		return new ModelAndView("WaterBillPayment", MainetConstants.FORM_NAME, this.getModel());
	}
}
