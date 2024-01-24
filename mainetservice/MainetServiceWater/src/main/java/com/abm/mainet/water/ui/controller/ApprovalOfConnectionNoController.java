package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.ApprovalOfConnectionNoModel;

/**
 * @author deepika.pimpale
 *
 */
@Controller
@RequestMapping("/ApprovalOfConnectionNo.html")
public class ApprovalOfConnectionNoController extends AbstractFormController<ApprovalOfConnectionNoModel> {

    @Autowired
    NewWaterConnectionService waterService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        final ApprovalOfConnectionNoModel model = getModel();
        final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
        final UserSession session = UserSession.getCurrent();
        final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
        final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
        final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
        final String lableValue = httpServletRequest.getParameter("labelVal");
        final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
        lableDTO.setApplicationId(appId);
        model.setServiceId(serviceId);
        lableDTO.setUserId(session.getEmployee().getEmpId());
        lableDTO.setOrgId((session.getOrganisation().getOrgid()));
        lableDTO.setLangId((long) session.getLanguageId());
        lableDTO.setLableId(lableId);
        lableDTO.setLableValue(lableValue);
        lableDTO.setLevel(level);
        model.setConnectionDetailsInfo(appId, serviceId, session.getOrganisation().getOrgid());
        return super.index();

    }

    @Override
    @ResponseBody
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final ApprovalOfConnectionNoModel model = getModel();

        if (model.validateInputs()) {
            if (model.saveForm()) {

            }
        }
        return defaultMyResult();
    }
}
