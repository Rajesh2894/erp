package com.abm.mainet.common.master.ui.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.ICommonHelpDocsService;
import com.abm.mainet.common.master.ui.model.CommonHelpDocsModel;
import com.abm.mainet.common.master.ui.validator.CommonHelpDocsValidator;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.ui.model.JQGridResponse;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author vinay.jangir
 *
 */
@Controller
@RequestMapping("/CommonHelpDocs.html")
public class CommonHelpDocsController extends AbstractFormController<CommonHelpDocsModel> {

    @Autowired
    private ICommonHelpDocsService iCommonHelpDocsService;


    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
    	sessionCleanup(httpServletRequest);
    	this.getModel().setCommonHelpDocs("CommonHelpDocs.html");
        httpServletRequest.getSession().removeAttribute("helpDocModuleName");
        return super.index();
    }

    @RequestMapping(params = MainetConstants.URL_EVENT.SERACH, method = RequestMethod.POST)
    public ModelAndView getModelyByModelType(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);

        final CommonHelpDocsModel model = getModel();

        model.emptyGrid();

        model.validateBean(getModel().getEntity(), CommonHelpDocsValidator.class);

        if (!model.hasValidationErrors()) {
            final CommonHelpDocs docs = iCommonHelpDocsService.getUploadedFileByDept(model.getEntity().getModuleName(),UserSession.getCurrent().getOrganisation());
            model.setSelectedService(model.getEntity().getModuleName());
            if (docs != null) {
                model.getHelpDocs().add(docs);
            } else {
                getModel().addValidationError("There is no Help Doc For This Service... Please Upload In ADD Mode");
                httpServletRequest.getSession().setAttribute("helpDocModuleName", model.getEntity().getModuleName());
            }
        }

        return defaultResult();
    }

    @RequestMapping(params = "delete", method = RequestMethod.POST)
    public @ResponseBody JsonViewObject deleteRecord(@RequestParam final long rowId,
            final HttpServletRequest httpServletRequest) {

        final CommonHelpDocsModel model = getModel();

        model.deleteDoc(rowId);

        final JsonViewObject jsonViewObject = JsonViewObject.successResult();

        jsonViewObject.setMessage(getMessageText("Delete.SUCCESS"));

        model.getHelpDocs().clear();

        return jsonViewObject;

    }

    @RequestMapping(params = "List_Of_Services", produces = MainetConstants.URL_EVENT.JSON_APP, method = RequestMethod.POST)
    public @ResponseBody JQGridResponse<CommonHelpDocs> getQuickLink(final HttpServletRequest httpServletRequest,
            @RequestParam final String page, @RequestParam final String rows) {

        return getModel().paginate(httpServletRequest, page, rows, getModel().getHelpDocs());

    }

}
