package com.abm.mainet.cfc.challan.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.challan.ui.model.ChallanUpdateModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 *
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/ChallanUpdate.html")
public class ChallanUpdateController extends AbstractFormController<ChallanUpdateModel> {

    @Autowired
    IChallanService iChallanService;

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView showStatusForm(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        getModel().setPageUrlFlag(MainetConstants.Common_Constant.NO);
        return index();
    }

    @RequestMapping(method = RequestMethod.POST, params = "search")
    public String searchApplicationForms(final HttpServletRequest httpServletRequest) {
        final BindingResult bindingResult = bindModel(httpServletRequest);

        final ChallanUpdateModel model = getModel();

        if ((null == model.getApplicationNo()) && (null == model.getChallanNo())) {
            model.addValidationError(ApplicationSession.getInstance().getMessage(
                    "search.criteria"));
        }
        model.setPageUrlFlag(MainetConstants.Common_Constant.NO);
        model.setImmeadiateService(false);
        if (!bindingResult.hasErrors()) {
            final ChallanMaster challanMasters = iChallanService.getChallanMasters(model.getChallanNo(),
                    model.getApplicationNo());
            model.setEntity(challanMasters);
            if (challanMasters == null) {
                model.addValidationError(ApplicationSession.getInstance().getMessage("challan.search.noresult"));
            }
        }
        return getViewName();
    }

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, params = "challanVerificationDashBoard")
    public ModelAndView challanVerificationDashBoard(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final Long applicationId = (Long) httpServletRequest.getSession().getAttribute("appId");
        final Long actualTaskId = (Long) httpServletRequest.getSession().getAttribute("actualTaskId");
        getModel().setTaskId(actualTaskId);
        getModel().editFormByApplicationId(applicationId);
        return new ModelAndView("ChallanUpdateFormDashBoard", MainetConstants.CommonConstants.COMMAND, getModel());
    }

}
