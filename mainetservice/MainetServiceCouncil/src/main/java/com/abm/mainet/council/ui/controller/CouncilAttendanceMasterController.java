package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.service.ICouncilMOMService;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.ui.model.CouncilAttendanceMasterModel;

@Controller
@RequestMapping(MainetConstants.Council.ATTENDANCE_MASTER_URL)
public class CouncilAttendanceMasterController extends AbstractFormController<CouncilAttendanceMasterModel> {

    @Autowired
    ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    ICouncilMOMService councilMomService;

    @Autowired
    private IFileUploadService fileUpload;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        CouncilAttendanceMasterModel model = this.getModel();
        model.setCommonHelpDocs("CouncilAttendanceMaster.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setCouncilMeetingMasterDtoList(
                councilMeetingMasterService.searchAttendanceMasterData(null, null, null, null, orgId));
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchMeetingAttendance", method = RequestMethod.POST)
    public CouncilRestResponseDto searchMeetingAttendance(@RequestParam("meetingTypeId") final Long meetingTypeId,
            @RequestParam("meetingNo") final String meetingNo, @RequestParam("fromDate") final Date fromDate,
            @RequestParam("toDate") final Date toDate, final HttpServletRequest request) {
        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
                .searchAttendanceMasterData(meetingTypeId, meetingNo, fromDate, toDate, orgId);
        response.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        return response;
    }

    @RequestMapping(params = "addMeetingAttendance", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addMeetingAttendance(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilAttendanceMasterModel model = new CouncilAttendanceMasterModel();
        model.setSaveMode(MainetConstants.CommonConstants.ADD);

        return new ModelAndView("addCouncilAttendanceMaster", MainetConstants.FORM_NAME, model);

    }

    @ResponseBody
    @RequestMapping(params = "getMeetingNo", method = RequestMethod.POST)
    public List<CouncilMeetingMasterDto> getMeetingNo(@RequestParam("meetingTypeId") final Long meetingTypeId,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();
        councilMeetingMasterDtoList = councilMeetingMasterService.fetchPendingMeetingsMeetingTypeId(meetingTypeId, orgId);
        return councilMeetingMasterDtoList;
    }

    @RequestMapping(params = "getMeetingMembers", method = RequestMethod.POST)
    public ModelAndView getMeetingData(@RequestParam("meetingId") final Long meetingId,
            final HttpServletRequest request) {
        getModel().bind(request);

        CouncilAttendanceMasterModel model = this.getModel();
        CouncilMeetingMasterDto couMeetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
        // fetch meeting members by meeting id from TB_CMT_COUNCIL_MEETING_MEMBER
        List<CouncilMemberCommitteeMasterDto> memberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        model.setMemberList(memberList);
        model.setCouMeetingMasterDto(couMeetingMasterDto);
        model.setDisableSelect(true);
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
                .fetchMeetingDetailsById(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        model.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        return new ModelAndView("addCouncilAttendanceMaster", MainetConstants.FORM_NAME, model);
    }

    @RequestMapping(params = "editAttendanceMasterData", method = RequestMethod.POST)
    public ModelAndView editAttendanceMasterData(@RequestParam("meetingId") final Long meetingId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);

        // fetch meetingId from TB_CMT_COUNCIL_RESOLUTION to check mom is created or not
        Boolean meetingIdAtted = councilMomService.findMeetingIdForAttendance(meetingId,
                UserSession.getCurrent().getOrganisation().getOrgid());
        if (!meetingIdAtted) {
            CouncilMeetingMasterDto couMeetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
            // fetch meeting members by meeting id from TB_CMT_COUNCIL_MEETING_MEMBER
            List<CouncilMemberCommitteeMasterDto> memberList = councilMeetingMasterService
                    .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
            this.getModel().setMemberList(memberList);
            this.getModel().setCouMeetingMasterDto(couMeetingMasterDto);
            this.getModel().setDisableSelect(true);
            List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
                    .fetchMeetingDetailsById(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
            this.getModel().setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);

            // get attached document
            final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                    .getBean(IAttachDocsService.class)
                    .findByCode(
                            UserSession.getCurrent().getOrganisation().getOrgid(),
                            MainetConstants.Council.Meeting.FILE_UPLOAD_IDENTIFIER_ATD + MainetConstants.WINDOWS_SLASH
                                    + meetingId);
            this.getModel().setAttachDocsList(attachDocs);

        } else {
            viewAttendanceMasterData(meetingId);
        }
        return new ModelAndView("addCouncilAttendanceMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewAttendanceMasterData", method = RequestMethod.POST)
    public ModelAndView viewAttendanceMasterData(@RequestParam("meetingId") final Long meetingId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        CouncilMeetingMasterDto couMeetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
        // fetch meeting members by meeting id from TB_CMT_COUNCIL_MEETING_MEMBER
        List<CouncilMemberCommitteeMasterDto> memberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setMemberList(memberList);
        this.getModel().setCouMeetingMasterDto(couMeetingMasterDto);
        this.getModel().setDisableSelect(true);
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
                .fetchMeetingDetailsById(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        // get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Meeting.FILE_UPLOAD_IDENTIFIER_ATD + MainetConstants.WINDOWS_SLASH + meetingId);
        this.getModel().setAttachDocsList(attachDocs);
        return new ModelAndView("addCouncilAttendanceMaster", MainetConstants.FORM_NAME, this.getModel());
    }

}
