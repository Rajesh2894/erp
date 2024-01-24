/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.MeetingMasterDto;
import com.abm.mainet.sfac.service.MeetingMasterService;
import com.abm.mainet.sfac.ui.model.MeetingCalendarModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.MEETING_CALENDAR_FORM)
public class MeetingCalendarController extends AbstractFormController<MeetingCalendarModel> {

	@Autowired
	private MeetingMasterService meetingMasterService;

	@RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView index(final HttpServletRequest httpServletRequest) {
		this.sessionCleanup(httpServletRequest);
		return defaultResult();
	}

	@RequestMapping(params = "fetchMeetingData", method = RequestMethod.POST)
	@ResponseBody
	public List<MeetingMasterDto> fetchMeetingData(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		this.sessionCleanup(httpServletRequest);
		List<MeetingMasterDto> meetings = new ArrayList<>();
		meetings = meetingMasterService.searchMeetingMasterData(null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.Sfac.ORDER_BY_MEETING_DATE,
				null);
		return meetings;
	}

	@RequestMapping(params = { "viewCalender" }, method = RequestMethod.POST)
	public ModelAndView getDateCalender(final HttpServletRequest httpServletRequest) {
		bindModel(httpServletRequest);
		ModelAndView mv = new ModelAndView("eventDateCalender", MainetConstants.FORM_NAME, this.getModel());
		return mv;

	}

	@RequestMapping(params = { "getMeetingNoList" }, method = RequestMethod.POST)
	@ResponseBody
	public List<MeetingMasterDto> getMeetingNoList(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "meetingTypeId", required = false) Long meetingTypeId) {
		MeetingCalendarModel model = this.getModel();
		List<MeetingMasterDto> meetings = new ArrayList<>();
		meetings = meetingMasterService.searchMeetingMasterData(meetingTypeId, null, null, null, null);
		model.setMastDto(meetings);
		return model.getMastDto();
	}

	@RequestMapping(params = { "eventDate" }, method = RequestMethod.POST)
	@ResponseBody
	public List<MeetingMasterDto> getMeetingDate(final HttpServletRequest httpServletRequest,
			@RequestParam(value = "meetingTypeId", required = false) Long meetingTypeId,
			@RequestParam(value = "meetingId", required = false) Long meetingId) {
		bindModel(httpServletRequest);
		this.sessionCleanup(httpServletRequest);
		MeetingCalendarModel model = this.getModel();
		List<MeetingMasterDto> meetings = new ArrayList<>();
		meetings = meetingMasterService.searchMeetingMasterData(meetingTypeId, null, null, null, meetingId);
		model.setMastDto(meetings);
		return model.getMastDto();
	}

}
