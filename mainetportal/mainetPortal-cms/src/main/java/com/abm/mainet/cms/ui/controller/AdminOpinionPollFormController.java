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

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.cms.ui.model.AdminOpinionPollFormModel;
import com.abm.mainet.cms.ui.validator.AdminOpinioMasterValidator;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author swapnil.shirke
 *
 */
@Controller
@RequestMapping("/AdminOpinionPollForm.html")
public class AdminOpinionPollFormController extends AbstractEntryFormController<AdminOpinionPollFormModel> {

    @RequestMapping(params = "delete", method = { RequestMethod.GET })
    public void deletelocal(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        FileUploadUtility.getCurrent().getFileMap().clear();

    }
    @RequestMapping(params = "validateSavePool", method = { RequestMethod.POST })
    public @ResponseBody List<String> validateSavePool(final Model model, final HttpServletRequest request) {
    	 bindModel(request);
         final AdminOpinionPollFormModel poolModel = this.getModel();
         
         final OpinionPoll entity = getModel().getEntity();
         entity.setOptionSize(poolModel.optionSizeValidation());
         entity.setCheckOpinionValidation(poolModel.checkOpinionPollExistOrNot(entity));
         if(entity.isCheckOpinionValidation()) {
	        	entity.setActivePolls(poolModel.getActiveOpinionPollList());
	     }
         getModel(). validateBean(entity, AdminOpinioMasterValidator.class);
         BindingResult bindingResult = poolModel.getBindingResult();
         List<String> errList = new ArrayList<String>();
         for (ObjectError e : bindingResult.getAllErrors()) 
       	{ 
       		errList.add(e.getDefaultMessage());
       	}
           return errList;
    }
    
	@RequestMapping(params = "DeleteImage", method = RequestMethod.POST)
    public @ResponseBody String DeleteFile(@RequestParam("fileName") final String fileName, @RequestParam("del") final String del,
            final HttpServletRequest httpServletRequest) {
        getModel().deleteFiles(fileName, del);
        return MainetConstants.SUCCESS;
    }
}
