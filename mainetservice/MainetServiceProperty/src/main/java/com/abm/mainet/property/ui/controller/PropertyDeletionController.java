package com.abm.mainet.property.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.service.IPropertyDeletionService;
import com.abm.mainet.property.ui.model.PropertyDeletionModel;

@Controller
@RequestMapping({ "/PropertyDeletion.html" })
public class PropertyDeletionController extends AbstractFormController<PropertyDeletionModel> {

    @Autowired
    IPropertyDeletionService iPropertyDeletionService;

    @Autowired
    private TbFinancialyearService tbFinancialyearService;

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(HttpServletRequest request) {
        this.sessionCleanup(request);

        return index();
    }

    @SuppressWarnings("null")
    @RequestMapping(params = { "searchProperty" }, method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {

        this.getModel().bind(request);
        ProperySearchDto searchDto = this.getModel().getSearchDto();
        String proertyNo = searchDto.getProertyNo();
        PropertyDeletionModel model = this.getModel();

        long orgid = UserSession.getCurrent().getOrganisation().getOrgid();

        if (proertyNo != null || !(proertyNo.isEmpty())) {

            int[] validatePropertyForDeletion = iPropertyDeletionService.validatePropertyForDeletion(proertyNo, orgid);

            if (validatePropertyForDeletion[0] <= 0) {
                this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("Enter valid Property Number"));
                ModelAndView mv = new ModelAndView("PropertyDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }

            else if (validatePropertyForDeletion[1] > 0) {
                this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("Bill Exist For Property"));
                ModelAndView mv = new ModelAndView("PropertyDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
                mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
                return mv;
            }

            else {

                iPropertyDeletionService.deleteProperty(proertyNo);

                return jsonResult(JsonViewObject.successResult(
                        ApplicationSession.getInstance().getMessage("property deleted successfully")));
            }

        }

        else {
            this.getModel().addValidationError(ApplicationSession.getInstance().getMessage("Enter valid Property Number"));
            ModelAndView mv = new ModelAndView("PropertyDeletionValidn", MainetConstants.FORM_NAME, this.getModel());
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, getModel().getBindingResult());
            return mv;
        }
    }

}
