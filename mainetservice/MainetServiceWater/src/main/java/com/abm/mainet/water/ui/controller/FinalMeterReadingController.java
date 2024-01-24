package com.abm.mainet.water.ui.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.WaterServiceShortCode;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.ui.model.FinalMeterReadingModel;

@Controller
@RequestMapping("/FinalMeterReading.html")
public class FinalMeterReadingController extends
        AbstractFormController<FinalMeterReadingModel> {

    @Autowired
    private ServiceMasterService serviceMaster;

    @Autowired
    private ICFCApplicationMasterService iCFCApplicationMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        sessionCleanup(httpServletRequest);

        final Long applicationId = Long.valueOf(httpServletRequest.getParameter("applId"));
        getModel().setApplicationId(applicationId);
        final Long serviceId = Long.valueOf(httpServletRequest.getParameter("serviceId"));
        getModel().setServiceId(serviceId);
        getModel().setLevelId(Long.valueOf(httpServletRequest.getParameter("labelId")));
        getModel().setLevelValue(httpServletRequest.getParameter("labelVal"));
        getModel().setLevel(Long.valueOf(httpServletRequest.getParameter("level")));
        final UserSession session = UserSession.getCurrent();
        setServiceDetail(getModel(), session);
        setApplicantDetails(getModel(), session);
        getModel().search();
        return super.index();
    }

    private void setServiceDetail(final FinalMeterReadingModel model, final UserSession session) {
        final ServiceMaster serviceMst = serviceMaster.getServiceByShortName(WaterServiceShortCode.WATER_DISCONNECTION,
                session.getOrganisation().getOrgid());
        if (serviceMst != null) {
            model.setServiceId(serviceMst.getSmServiceId());
            if (session.getLanguageId() == MainetConstants.ENGLISH) {
                model.setServiceName(serviceMst.getSmServiceName());
            } else {
                model.setServiceName(serviceMst.getSmServiceNameMar());
            }
        }
    }

    private void setApplicantDetails(final FinalMeterReadingModel model,
            final UserSession session) {
        final TbCfcApplicationMstEntity applicationMaster = iCFCApplicationMasterService
                .getCFCApplicationByApplicationId(model.getApplicationId(), session.getOrganisation().getOrgid());
        if (applicationMaster != null) {

            String userName = (applicationMaster.getApmFname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmFname() + MainetConstants.WHITE_SPACE);
            userName += applicationMaster.getApmMname() == null ? MainetConstants.BLANK
                    : applicationMaster.getApmMname() + MainetConstants.WHITE_SPACE;
            userName += applicationMaster.getApmLname() == null ? MainetConstants.BLANK : applicationMaster.getApmLname();
            model.setApplicanttName(userName);
            model.setApplicationDate(applicationMaster.getApmApplicationDate());
        }
    }
}
