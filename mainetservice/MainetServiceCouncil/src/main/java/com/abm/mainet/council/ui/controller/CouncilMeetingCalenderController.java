package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.ui.model.CouncilMeetingMasterModel;

@Controller
@RequestMapping("/MeetingCalender.html")
public class CouncilMeetingCalenderController extends AbstractFormController<CouncilMeetingMasterModel> {

    @Autowired
    private ICouncilMeetingMasterService councilMeetingMasterService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(final HttpServletRequest httpServletRequest) {
        this.sessionCleanup(httpServletRequest);
        return defaultResult();
    }

    @RequestMapping(params = "fetchMeetingData", method = RequestMethod.POST)
    @ResponseBody
    public List<CouncilMeetingMasterDto> fetchMeetingData(final HttpServletRequest httpServletRequest) {
        bindModel(httpServletRequest);
        this.sessionCleanup(httpServletRequest);
        List<CouncilMeetingMasterDto> meetings = new ArrayList<>();
        meetings = councilMeetingMasterService.searchCouncilMeetingMasterData(null, null, null, null,
                UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.Council.Meeting.ORDER_BY_MEETING_DATE);
        return meetings;

    }

}
