package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.cms.ui.model.AdminFeedbackFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 * @see This Controller for delete operation for Admin Feedback Section
 */
@Controller
@RequestMapping("/AdminFeedbackForm.html")
public class AdminFeedbackFormController extends AbstractEntryFormController<AdminFeedbackFormModel> implements Serializable {

    private static final long serialVersionUID = 6889891356585481467L;

    @Autowired
    private IFeedBackService iFeedbackService;

    @RequestMapping(params = "reply", method = RequestMethod.POST)
    public ModelAndView reply(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
      
            Feedback feedback = this.getModel().getEntity();
          if (!StringUtils.isBlank(getModel().getFeedBackDetails())) {   
            if (StringUtils.isBlank(feedback.getFeedBackAnswar())) {
                feedback.setFeedBackAnswar(
                        new StringBuilder(UserSession.getCurrent().getEmployee().getEmpname())
                                .append(MainetConstants.operator.ORR).append(Utility.dateToString(new Date()))
                                .append(MainetConstants.operator.ORR)
                                .append(getModel().getFeedBackDetails()).toString());
            } else {

                StringBuilder answar = new StringBuilder(getModel().getEntity().getFeedBackAnswar())
                        .append(MainetConstants.operator.SEPERATOR).append(UserSession.getCurrent().getEmployee().getEmpname())
                        .append(MainetConstants.operator.ORR).append(
                                Utility.dateToString(new Date()))
                        .append(MainetConstants.operator.ORR).append(getModel().getFeedBackDetails());

                feedback.setFeedBackAnswar(answar.toString());
            }
           }
            iFeedbackService.updateFeedback(feedback);
            return jsonResult(JsonViewObject.successResult());
       
    }

    @RequestMapping(params = "publish", method = RequestMethod.POST)
    public ModelAndView publish(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        Feedback feedback = this.getModel().getEntity();
        /*
         * if (StringUtils.isBlank(feedback.getFeedBackAnswar())) {
         * getModel().addValidationError(getApplicationSession().getMessage("feedback.accept.publish.validate")); return
         * super.defaultResult(); }
         */
        iFeedbackService.updateFeedback(feedback);
        return jsonResult(JsonViewObject.successResult());
    }

    @RequestMapping(params = "print", method = RequestMethod.POST)
    public ModelAndView printFeedback(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        iFeedbackService.getAllFeedBack();

        return super.defaultResult();
    }

}
