package com.abm.mainet.tradeLicense.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.tradeLicense.ui.model.LicenseDemandGenerationModel;


/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/LicenseDemandGenerationController.html")
public class LicenseDemandGenerationController extends
        AbstractFormController<LicenseDemandGenerationModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);

        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachLicenseDemandlData")
    public ModelAndView searchWaterBillRecords(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "generateErrorLog")
    public ModelAndView generateErrorLog(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        return new ModelAndView("WaterBillGenError",
                MainetConstants.CommonConstants.COMMAND, getModel());
    }

}
