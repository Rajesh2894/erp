package com.abm.mainet.cms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.service.IEIPHomePageImageService;
import com.abm.mainet.cms.ui.model.CitizenContactUsModel;
import com.abm.mainet.cms.ui.model.EIPHomeImagesModel;
import com.abm.mainet.cms.ui.validator.EIPHomeImagesValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.dms.service.FileUploadServiceValidator;

/**
 * @author vinay.jangir
 *
 */
@Controller
@RequestMapping("/EIPHomeImages.html")
public class EIPHomeImagesController extends AbstractEntryFormController<EIPHomeImagesModel> {

	
	  @Autowired
	    private IEIPHomePageImageService iEIPHomePageImageService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        FileUploadServiceValidator.getCurrent().sessionCleanUpForFileUpload();
        sessionCleanup(httpServletRequest);
        return super.index();
    }

    @Override
    public ModelAndView addForm(final HttpServletRequest httpServletRequest) {

        bindModel(httpServletRequest);
        final EIPHomeImagesModel eipHomeImagesModel = getModel();
        eipHomeImagesModel.setFlag(MainetConstants.FlagS);
        if (eipHomeImagesModel.checkMaxRows(MainetConstants.FlagS)) {
            sessionCleanup(httpServletRequest);
            eipHomeImagesModel.addForm();
        } else {
            eipHomeImagesModel.addValidationError(getFieldLabel(MainetConstants.HOMEMAX_IMAGE_ERROR));
            eipHomeImagesModel.setMakkerchekkerflag("Y");
            return new ModelAndView(MainetConstants.EIP_IMAGEVALIDATE_VIEW);
        }

        return new ModelAndView(MainetConstants.EIP_HOMEIMAGES_VIEW, MainetConstants.FORM_NAME, eipHomeImagesModel);
    }

    private String getFieldLabel(final String field) {
        return ApplicationSession.getInstance().getMessage(
                MainetConstants.EIP_HOMEIMAGES_VIEW + MainetConstants.operator.DOT + field,
                new Object[] { getFieldLabel2("maxImages") });
    }

    private String getFieldLabel2(final String field) {
        return ApplicationSession.getInstance().getMessage("landingPage" + MainetConstants.operator.DOT + field);
    }
    
    @RequestMapping(params = "validateEipHomePage", method = { RequestMethod.POST })
	public @ResponseBody List<String> validateSaveITAstForm(final Model model, final HttpServletRequest request) {
		bindModel(request);
		final EIPHomeImagesModel homeModel = this.getModel();
		if (homeModel.getEntity().getImagePath() != null && homeModel.getEntity().getImagePath().equals("")) {
			homeModel.getEntity().setImagePath(null);
			homeModel.getEntity().setImageName(null);
		}
		homeModel.validateBean(homeModel.getEntity(), EIPHomeImagesValidator.class);

		// Check For Checker
		if (homeModel.getSequenceNo() == 0l || homeModel.getSequenceNo() != homeModel.getEntity().getHmImgOrder()) {
			final boolean result = iEIPHomePageImageService.checkExistingSequence(homeModel.getEntity().getHmImgOrder(),
					homeModel.getEntity().getModuleType(), UserSession.getCurrent().getOrganisation());
			if (!result) {
				homeModel.addValidationError(getFieldLabel("checkExistsImage"));
			}
		}
		List<String> errList = new ArrayList<String>();
		for (ObjectError e : homeModel.getBindingResult().getAllErrors()) {
			errList.add(e.getDefaultMessage());
		}
		return errList;
	}
    
}
