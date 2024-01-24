package com.abm.mainet.cms.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.EIPAnnouncement;
import com.abm.mainet.cms.ui.model.EipAnnouncementFormModel;
import com.abm.mainet.cms.ui.validator.AdminOpinioMasterValidator;
import com.abm.mainet.cms.ui.validator.EIPAnnouncementValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;

@Controller
@RequestMapping("/EipAnnouncementForm.html")
public class EipAnnouncementFormController extends AbstractEntryFormController<EipAnnouncementFormModel> {

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {

        sessionCleanup(httpServletRequest);

        return defaultMyResult();

    }
    
    @RequestMapping(params = "DeleteImage", method = RequestMethod.POST)
    public @ResponseBody String DeleteFile(@RequestParam("fileName") final String fileName, @RequestParam("del") final String del,
            final HttpServletRequest httpServletRequest) {
        getModel().deleteFiles(fileName, del);
        return MainetConstants.SUCCESS;
    }
    
    @RequestMapping(params = "validateSaveAnnouncement", method = { RequestMethod.POST })
    public @ResponseBody List<String> validateSavePool(final Model model, final HttpServletRequest request) {
    	 bindModel(request);
         final EipAnnouncementFormModel annModel = this.getModel();
         
         final EIPAnnouncement entity = getModel().getEntity();
         annModel.validateBean(entity,  EIPAnnouncementValidator.class);
 
         if (this.getModel().hasValidationErrors()) {
         	if(entity.getAttach() == null || entity.getAttach().isEmpty()) {//on UI that div is not getting removed
         		this.getModel().attachNameList.clear();
         	}
         }
         BindingResult bindingResult = annModel.getBindingResult();
         List<String> errList = new ArrayList<String>();
         for (ObjectError e : bindingResult.getAllErrors()) 
       	{ 
       		errList.add(e.getDefaultMessage());
       	}
           return errList;
    }
}
