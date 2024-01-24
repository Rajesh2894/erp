package com.abm.mainet.cms.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abm.mainet.cms.ui.model.AdminPublicNoticesFormModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractEntryFormController;
import com.abm.mainet.dms.utility.FileUploadUtility;

/**
 * @author swapnil.shirke
 *
 */
@Controller
@RequestMapping("/AdminPublicNoticesForm.html")
public class AdminPublicNoticesFormController extends AbstractEntryFormController<AdminPublicNoticesFormModel> {

    @RequestMapping(params = "delete", method = { RequestMethod.GET })
    public void deletelocal(
            final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        FileUploadUtility.getCurrent().getFileMap().clear();

    }
 
    @RequestMapping(params = "checkSequence", method = { RequestMethod.POST })
    public @ResponseBody String  checkSequence(final HttpServletRequest httpServletRequest, @RequestParam("sequenceNo") final long sequenceNo ) {
      //  sessionCleanup(httpServletRequest);
      if(getModel().checkSequence(sequenceNo)) {
    	  return "Y";
    }
      return "N";
    }

	@RequestMapping(params = "DeleteImage", method = RequestMethod.POST)
    public @ResponseBody String DeleteFile(@RequestParam("fileName") final String fileName, @RequestParam("del") final String del,
            final HttpServletRequest httpServletRequest) {
        getModel().deleteFiles(fileName, del);
        return MainetConstants.SUCCESS;
    }
}
