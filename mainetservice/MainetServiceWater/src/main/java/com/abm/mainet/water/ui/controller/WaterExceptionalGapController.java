
package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.WaterExceptionalGapModel;

/**
 * @author Rahul.Yadav
 *
 */
@Controller
@RequestMapping("/WaterExceptionalGap.html")
public class WaterExceptionalGapController extends
        AbstractFormController<WaterExceptionalGapModel> {

    @RequestMapping(method = { RequestMethod.POST })
    public ModelAndView index(final HttpServletRequest request) {
        sessionCleanup(request);
        this.getModel().setCommonHelpDocs("WaterExceptionalGap.html");
        return defaultResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "serachWaterBillData")
    public ModelAndView searchWaterBillRecords(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ModelAndView mv = new ModelAndView();
        final WaterExceptionalGapModel model = getModel();
        model.searchWaterBillRecords();
        if (model.getBindingResult() != null) {
            mv.addObject(BindingResult.MODEL_KEY_PREFIX + MainetConstants.FORM_NAME, model.getBindingResult());
        }
        mv.setViewName("ExceptionalSearchAndSave");
        mv.addObject(MainetConstants.CommonConstants.COMMAND, model);
        return mv;
    }

    @RequestMapping(method = RequestMethod.POST, params = "nextPage")
    public ModelAndView nextPage(
            final HttpServletRequest httpServletRequest, @RequestParam("mode") final String addEdit,
            @RequestParam("meterType") final Long meterType) {
        bindModel(httpServletRequest);
        final WaterExceptionalGapModel model = getModel();
        model.setWaterDTO(null);
        model.setEditGap(null);
        model.setGapDto(null);
        model.setReason(null);
        model.setBillingFrequency(0);
        model.setAddEdit(addEdit);
        model.setMeterType(meterType);
        if ((addEdit == null) || addEdit.isEmpty()) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("water.exception.action"));
        }
        if ((meterType == null) || (meterType == 0d)) {
            model.addValidationError(ApplicationSession.getInstance().getMessage("water.exception.mrType"));
        }
        if (model.hasValidationErrors()) {
            return defaultMyResult();
        }
        final LookUp meterTypeLook = CommonMasterUtility.getNonHierarchicalLookUpObject(meterType,
                UserSession.getCurrent().getOrganisation());
        model.setMeterTypeDesc(meterTypeLook.getLookUpCode());
        return new ModelAndView("ExceptionalSearchAndSaveValidn", MainetConstants.CommonConstants.COMMAND, model);
    }

    @RequestMapping(method = RequestMethod.POST, params = "backPage")
    public ModelAndView backPage(
            final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        return defaultMyResult();
    }

    @RequestMapping(method = RequestMethod.POST, params = "resetPage")
    public ModelAndView resetPage(
            final HttpServletRequest httpServletRequest) {
        final WaterExceptionalGapModel model = getModel();
        model.setWaterDTO(null);
        model.setEditGap(null);
        model.setGapDto(null);
        model.setReason(null);
        model.setBillingFrequency(0);
        return new ModelAndView("ExceptionalSearchAndSaveValidn", MainetConstants.CommonConstants.COMMAND, model);
    }

}
