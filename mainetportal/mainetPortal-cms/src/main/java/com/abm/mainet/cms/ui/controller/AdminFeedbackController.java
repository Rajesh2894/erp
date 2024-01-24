package com.abm.mainet.cms.ui.controller;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cms.domain.Feedback;
import com.abm.mainet.cms.service.IFeedBackService;
import com.abm.mainet.cms.ui.model.AdminFeedbackModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.util.CommonMasterUtility;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.common.util.UserSession;

/**
 * @author swapnil.shirke
 */
@Controller
@RequestMapping("/AdminFeedback.html")
public class AdminFeedbackController extends AbstractFormController<AdminFeedbackModel> implements Serializable {

    private static final long serialVersionUID = -1286546384868368376L;

    @Autowired
    private IFeedBackService iFeedbackService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        getModel().setCommonHelpDocs("AdminFeedback.html");
        return super.index();
    }

    @RequestMapping(params = "Feedback_LIST", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<Feedback> getFeedback(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {

  	  final List<LookUp> innIdeaCategoryLookup = CommonMasterUtility.getLookUps(PrefixConstants.Prefix.IIC,
                UserSession.getCurrent().getOrganisation());
      
  	List<Feedback> feedBackList = getModel().generateFeedbackList();
  	feedBackList.forEach(feedback ->{
  		if(feedback.getCatagoryType() != null) {
  			if(innIdeaCategoryLookup !=null) {
  			innIdeaCategoryLookup.forEach(i->{
  				if(feedback.getCatagoryType().longValue() ==i.getLookUpId()) {
  					//if(UserSession.getCurrent().getLanguageId() == 1) {
  					feedback.setCategoryTypeName(i.getLookUpDesc());
  				}
  			});
  			}
  			}
  		
  			
  			
  		
  	});
        return getModel().paginate(httpServletRequest, page, rows, feedBackList);
    }

    public void delete(final long rowId) {
        iFeedbackService.delete(rowId);
    }

}
