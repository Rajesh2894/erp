package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.property.ui.model.PropertyBillPaymentModel;

@Controller
@RequestMapping("/ManualReceiptEntry.html")
public class ManualReceiptEntryController extends AbstractFormController<PropertyBillPaymentModel> {

    @Autowired
    private IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().bind(request);
        PropertyBillPaymentModel model = this.getModel();

        model.setCommonHelpDocs("ManualReceiptEntry.html");
        fileUpload.sessionCleanUpForFileUpload();
        model.setReceiptType("M");
        return new ModelAndView("PropertyBillPaymentSearch", MainetConstants.FORM_NAME, model);

    }
    @RequestMapping(params = "backToManualReceipt", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.sessionCleanup(httpServletRequest);
        getModel().setCommonHelpDocs("ManualReceiptEntry.html");
        getModel().bind(httpServletRequest);
        PropertyBillPaymentModel model = this.getModel();
        model.setReceiptType("M");
        return new ModelAndView("BackToPropertyBillPayment", MainetConstants.FORM_NAME, model);
    }

}
