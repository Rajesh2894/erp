package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.water.ui.model.WaterBillGenerationModel;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/WaterBillGeneration.html")
public class WaterBillGenerationController extends
        AbstractFormController<WaterBillGenerationModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        getModel().setBillCcnType(MainetConstants.PAYMODE.MOBILE);
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillData")
    public ModelAndView searchWaterBillRecords(final HttpServletRequest request) {
        sessionCleanup(request);
        bindModel(request);
        final WaterBillGenerationModel model = getModel();
        model.setEntityList(null);
        model.searchWaterBillRecords();
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
