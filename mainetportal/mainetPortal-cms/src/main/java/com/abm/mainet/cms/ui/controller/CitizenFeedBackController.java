package com.abm.mainet.cms.ui.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.cms.ui.model.CitizenFeedBackModel;
import com.abm.mainet.cms.ui.validator.CitizenFeedbackFormValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.ui.view.JsonViewObject;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("CitizenFeedBack.html")
public class CitizenFeedBackController extends AbstractEntryFormController<CitizenFeedBackModel> implements Serializable {
    private static final long serialVersionUID = 9115879029948860472L;

    @Autowired
    private IFeedBackService iFeedBackService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        getModel().setEntity(getModel().getcheckLoginStatus());
        getModel().setFeedback(getModel().getcheckLoginStatus());
        final Employee employee = UserSession.getCurrent().getEmployee();
        if (null != employee && null != employee.getLoggedIn()
                && employee.getLoggedIn().equalsIgnoreCase(MainetConstants.Common_Constant.NO)
                && employee.getEmploginname()
                        .equalsIgnoreCase(ApplicationSession.getInstance().getMessage("citizen.noUser.loginName"))) {
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        } else {
            return new ModelAndView("CitizenFeedbackLogin", MainetConstants.FORM_NAME, getModel());
        }
    }

    @RequestMapping(params = "sendfeedback", method = RequestMethod.POST)
    public ModelAndView sendFeedBack(final HttpServletRequest httpServletRequest) {
        final BindingResult bindingResult = bindModel(httpServletRequest);

        final CitizenFeedBackModel model = getModel();
        model.setEntity(model.getFeedback());

        model.validateBean(model.getEntity(), CitizenFeedbackFormValidator.class);

        String cookiesforaccessibility = null;
        final Cookie[] cookies = httpServletRequest.getCookies();

        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

        if (httpServletRequest.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility != null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {

        } else {

            return jsonResult(JsonViewObject
                    .failureResult(getApplicationSession()
                            .getMessage("citizen.login.reg.captha.valid.error")));
        }

        if (!bindingResult.hasErrors()) {
            iFeedBackService.saveFeedback(getModel().getEntity());

            sessionCleanup(httpServletRequest);
            return new ModelAndView(getViewName(), MainetConstants.FORM_NAME, getModel());
        }else {
        	model.getFeedback().setFeedBackDetails("");
        	return jsonResult(JsonViewObject
                    .failureResult(getApplicationSession()
                            .getMessage("Feedback.clientfeedBackDetails")));
        }
    }
    
    @RequestMapping(params = "saveInnovativeIdea", method = RequestMethod.POST)
    public ModelAndView saveInnovativeIdea(final HttpServletRequest httpServletRequest) {
        final BindingResult bindingResult = bindModel(httpServletRequest);

        final CitizenFeedBackModel model = getModel();
        model.setEntity(model.getFeedback());

        model.validateBean(model.getEntity(), CitizenFeedbackFormValidator.class);

        String cookiesforaccessibility = null;
        final Cookie[] cookies = httpServletRequest.getCookies();

        for (final Cookie cookie : cookies) {
            if ((cookie.getName() != null) && cookie.getName().equalsIgnoreCase("accessibility")) {
                cookiesforaccessibility = cookie.getValue();
            }
        }

        if (httpServletRequest.getSession().getAttribute("captcha").equals(model.getCaptchaSessionLoginValue())
                || (cookiesforaccessibility != null && cookiesforaccessibility.equalsIgnoreCase(MainetConstants.AUTH))) {

        } else {

            return jsonResult(JsonViewObject
                    .failureResult(getApplicationSession()
                            .getMessage("citizen.login.reg.captha.valid.error")));
        }

        if (!bindingResult.hasErrors()) {
        	 final List<File> files = new ArrayList<>(0);

             if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
                 for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                     final List<File> list = new ArrayList<>(entry.getValue());
                     for (final File file : list) {
                    	 model.getEntity().setAttPath(model.getDirectry() + File.separator + file.getName());
                         break;
                     }
                     files.addAll(list);
                 }
             }
             try {
            	 if(files!=null && !files.isEmpty()) {
                 model.getFileNetClient().uploadFileList(files, model.getDirectry());
            	 }
             } catch (final Exception e) {
                 throw new FrameworkException(e);
             }
            iFeedBackService.saveFeedback(getModel().getEntity());
            sessionCleanup(httpServletRequest);
            FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
            return new ModelAndView("CitizenFeedbackLogin", MainetConstants.FORM_NAME, getModel());
        }
        return defaultResult();
    }

    

    @RequestMapping(params = "publishFeedBack", method = RequestMethod.GET)
    public ModelAndView getFeedback(final HttpServletRequest httpServletRequest) {
        List<Feedback> publishFeedBackList = iFeedBackService
                .getAllPublishFeedback(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.PUBLISH);

        ModelAndView mv = new ModelAndView("CitizenPublishFeedback", MainetConstants.FORM_NAME, getModel());
        mv.addObject("publishFeedBackList", publishFeedBackList);
        return mv;
    }

}
