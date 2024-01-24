package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.scrutiny.dto.ScrutinyLableValueDTO;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.MeterDetailsConnectionFormModel;

/**
 * @author deepika.pimpale
 *
 */
@Controller
@RequestMapping("/MeterDetailsConnectionForm.html")
public class MeterDetailsConnectionFormController extends AbstractFormController<MeterDetailsConnectionFormModel> {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MeterDetailsConnectionFormController.class);

    @Autowired
    ICFCApplicationMasterService cfcService;

    @Autowired
    private TbServicesMstService iTbServicesMstService;

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);
        bindModel(httpServletRequest);
        final MeterDetailsConnectionFormModel model = getModel();
        final ScrutinyLableValueDTO lableDTO = model.getLableValueDTO();
        final UserSession session = UserSession.getCurrent();
        final Long appId = Long.valueOf(httpServletRequest.getParameter("applId"));
        final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
        final Long lableId = Long.valueOf(httpServletRequest.getParameter("labelId"));
        final String lableValue = httpServletRequest.getParameter("labelVal");
        final Long level = Long.valueOf(httpServletRequest.getParameter("level"));
        model.setServiceId(serviceId);
        model.setApmApplicationId(appId);
        lableDTO.setApplicationId(appId);
        lableDTO.setUserId(session.getEmployee().getEmpId());
        lableDTO.setOrgId((session.getOrganisation().getOrgid()));
        lableDTO.setLangId((long) session.getLanguageId());
        lableDTO.setLableId(lableId);
        lableDTO.setLableValue(lableValue);
        lableDTO.setLevel(level);
        final String serviceShortCode = iTbServicesMstService.getServiceShortDescByServiceId(serviceId);
        switch (serviceShortCode) {
        case MainetConstants.WaterServiceShortCode.WATER_RECONN:
            model.setReconnectionMeterDetails(appId, serviceId, session.getOrganisation().getOrgid());
            break;
        default:
            model.setConnectionDetailsInfo(appId, serviceId, session.getOrganisation().getOrgid());
            break;
        }
        return super.index();
    }

    @Override
    @RequestMapping(params = "save", method = RequestMethod.POST)
    public ModelAndView saveform(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        final MeterDetailsConnectionFormModel model = getModel();

        try {
            if (model.validateInputs()) {
                if (model.saveForm()) {
                }
            }
        } catch (final Exception ex) {
            LOGGER.error("Error Occured during meter saving", ex);
            return jsonResult(JsonViewObject.failureResult(ex));
        }
        return defaultMyResult();
    }
}
