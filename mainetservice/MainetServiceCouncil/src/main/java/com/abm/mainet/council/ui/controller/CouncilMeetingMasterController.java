package com.abm.mainet.council.ui.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.dto.PrintingDto;
import com.abm.mainet.council.service.ICouncilAgendaMasterService;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;
import com.abm.mainet.council.service.ICouncilMemberMasterService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilMeetingMasterModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * @author israt.ali
 *
 */
@Controller
@RequestMapping(MainetConstants.Council.MEETING_MASTER_URL)
public class CouncilMeetingMasterController extends AbstractFormController<CouncilMeetingMasterModel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMeetingMasterController.class);

    @Autowired
    private ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    private ICouncilAgendaMasterService councilAgendaMasterService;

    @Autowired
    private ICouncilProposalMasterService councilProposalMasterService;

    @Autowired
    private ICouncilMemberCommitteeMasterService councilMemberCommitteeMasterService;

    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;

    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        CouncilMeetingMasterModel model = this.getModel();
        model.setCommonHelpDocs("CouncilMeetingMaster.html");
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String orderBy = MainetConstants.Council.Meeting.ORDER_BY_MEETING_ID;
        model.setCouncilMeetingMasterDtoList(
                councilMeetingMasterService.searchCouncilMeetingMasterData(null, null, null, null, orgId, orderBy));
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchCouncilMeeting", method = RequestMethod.POST)
    public CouncilRestResponseDto getCouncilMeetingList(@RequestParam("meetingTypeId") final Long meetingTypeId,
            @RequestParam("meetingNumber") final String meetingNumber, @RequestParam("fromDate") final Date fromDate,
            @RequestParam("toDate") final Date toDate, final HttpServletRequest request) {
        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String orderBy = MainetConstants.Council.Meeting.ORDER_BY_MEETING_ID;
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
                .searchCouncilMeetingMasterData(meetingTypeId, meetingNumber, fromDate, toDate, orgId, orderBy);
        response.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        return response;
    }

    @RequestMapping(params = "addCouncilMeeting", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addCouncilMeeting(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilMeetingMasterModel model = this.getModel();
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        /* Long orgId = UserSession.getCurrent().getOrganisation().getOrgid(); */
        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
        model.setLocationList(locationList);
        return new ModelAndView("addCouncilMeetingMaster", MainetConstants.FORM_NAME, model);
    }

    /*
     * this is agenda report print and override the PrintReport handler of Abstract Form Controller
     */
    @SuppressWarnings("unused")
    @Override
    @RequestMapping(params = "PrintReport")
    public ModelAndView printRTIStatusReport(final HttpServletRequest request) {
        // sessionCleanup(request);
        Long meetingId = CouncilMeetingMasterModel.getPrintMeetingId();
        // Long meetingId = 29l;
        List<PrintingDto> printingEntities = new ArrayList<>();
        if (meetingId != null) {
            // get meeting data by using meeting Id
            CouncilMeetingMasterDto meetingDto = councilMeetingMasterService.getMeetingDataById(meetingId);
            String ulbName, muncipal;
            int languageId = UserSession.getCurrent().getLanguageId();
            Long cpdId = UserSession.getCurrent().getOrganisation().getOrgCpdId();
            TbComparamDet comparamDet = ApplicationContextProvider.getApplicationContext().getBean(TbComparamDetService.class)
                    .findById(cpdId);
            if (UserSession.getCurrent().getLanguageId() == 1) {
                ulbName = UserSession.getCurrent().getOrganisation().getONlsOrgname();
                muncipal = comparamDet.getCpdDesc();
            } else {
                ulbName = UserSession.getCurrent().getOrganisation().getoNlsOrgnameMar();
                muncipal = comparamDet.getCpdDescMar();
            }
            // set election ward dynamically

            Long agendaId = meetingDto.getAgendaId();
            // fetch proposals based on agendaId
            List<CouncilProposalMasterDto> proposalMasterDtos = councilProposalMasterService
                    .findProposalBasedOnStatusByAgendaId(MainetConstants.Council.DB_STATUS_APPROVED, agendaId);
            // find out members available in this meeting by meetingId
            // here true is printReport Boolean based on query execute
            List<CouncilMemberCommitteeMasterDto> members = councilMeetingMasterService
                    .fetchMeetingPresentMemberListByMeetingId(meetingId, MainetConstants.Council.ABSENT_STATUS, true,
                            UserSession.getCurrent().getOrganisation().getOrgid());

            // doing this because of INLINE resource in common code of EmailService line no 287
            String filePath = Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR
                    + UserSession.getCurrent().getOrgLogoPath();
            final File inlineFile = new File(filePath);
            // INLINE images
            Map<String, String> inlineImages = new HashMap<String, String>();
            if (inlineFile != null && inlineFile.length() > 0) {
                inlineImages.put("image1", inlineFile.toString());
            }

            // iterate and make data in AgendaPrintingDto
            for (CouncilMemberCommitteeMasterDto member : members) {
                PrintingDto printDto = new PrintingDto();
                BeanUtils.copyProperties(member, printDto);
                printDto.setCommitteeName(meetingDto.getAgendaMasterDto().getCommitteeType());
                printDto.setDesignation(member.getCouMemberTypeDesc());
                printDto.setLocation(meetingDto.getMeetingPlace());
                printDto.setMeetingDateDesc(meetingDto.getMeetingDateDesc());
                printDto.setMeetingName(meetingDto.getMeetingTypeName());// doubt ask to SAMADHAN sir and NILIMA Madam
                printDto.setMeetingNo(meetingDto.getMeetingNo());
                printDto.setMeetingTime(meetingDto.getMeetingTime());
                printDto.setMemberName(member.getMemberName());
                printDto.setMemberAddress(member.getMemberAddress());
                printDto.setMeetingInvitationMsg(meetingDto.getMeetingInvitationMsg());
                printDto.setElectionWard(member.getElecWardDesc());
                // Proposal List set here
                printDto.setSubjects(proposalMasterDtos);
                printDto.setUlbName(ulbName);
                printDto.setMuncipal(muncipal);
                if(meetingDto.getAgendaMasterDto().getCommitteeTypeId()!=null)
                printDto.setCommitteeCode(CommonMasterUtility
                        .getNonHierarchicalLookUpObject(meetingDto.getAgendaMasterDto().getCommitteeTypeId(),
                        		UserSession.getCurrent().getOrganisation())
                        .getLookUpCode());
                printingEntities.add(printDto);

                // find memberData for get emailId and mobile no Till Time
                CouncilMemberMasterDto memberDto = councilMemberMasterService.getCouncilMemberMasterByCouId(member.getMemberId());
                // sample code for agenda print at handler printReport
                // code for runtime send email to members
                // demo code for send mail for testing purpose
                String subjects = "";
                int count = 1;
                for (int i = 0; i < proposalMasterDtos.size(); i++) {
                    int srNo = count++;
                    String proposalDetails = proposalMasterDtos.get(i).getProposalDetails();
                    subjects += "<tr>\r\n" +
                            "    <td class=\"text-center\" style=\"padding: 3px;font-size: 12px;border: 1px solid #eaeaea;text-align: center;\">"
                            + srNo + "</td>\r\n" +
                            "    <td class=\"text-center\" style=\"padding: 3px;font-size: 12px;border: 1px solid #eaeaea;text-align: center;\">"
                            + proposalDetails + "</td>\r\n" +
                            "</tr>";
                }

                // ENGLISH data set runtime based on LANG ID
                String bodyStmt = "", belowAnnexureStmt = "", shortNoteStmt = "", meetingInviteMsg = "";

                if (UserSession.getCurrent().getLanguageId() == 1) {
                    // English Data
                    // D#76375
                    meetingInviteMsg = meetingDto.getMeetingInvitationMsg();
                    bodyStmt = "It is hereby informed that the General/Special meeting of " + muncipal
                            + " has been organised on " + printDto.getMeetingDateDesc() + " (date) at "
                            + printDto.getMeetingTime() + " Agenda Of Business of the meeting is enclosed in Annexure.";
                    belowAnnexureStmt = ApplicationSession.getInstance().getMessage("council.meeting.subject.list") + " "
                            + printDto.getMeetingDateDesc() + " at " + printDto.getMeetingTime() + " (Time)";
                    shortNoteStmt = ApplicationSession.getInstance().getMessage("council.meeting.shortNote");
                } else {
                    meetingInviteMsg = meetingDto.getMeetingInvitationMsg();
                    bodyStmt = ApplicationSession.getInstance().getMessage("council.meeting.informed") + " " + muncipal
                            + ApplicationSession.getInstance().getMessage("council.meeting.ki") + " "
                            + printDto.getCommitteeName() + " " + printDto.getMeetingTime()
                            + " " + ApplicationSession.getInstance().getMessage("council.meeting.dinank") + " "
                            + printDto.getMeetingDateDesc() + " "
                            + ApplicationSession.getInstance().getMessage("council.meeting.ko.time") + " "
                            + printDto.getMeetingTime()
                            + " " + ApplicationSession.getInstance().getMessage("council.meeting.baje") + " "
                            + printDto.getLocation() + " "
                            + ApplicationSession.getInstance().getMessage("council.meeting.aayojit");

                    belowAnnexureStmt = ApplicationSession.getInstance().getMessage("council.meeting.nigam.parisad") + " "
                            + printDto.getMeetingDateDesc()
                            + " " + ApplicationSession.getInstance().getMessage("council.meeting.samay") + " "
                            + printDto.getMeetingTime() + " "
                            + ApplicationSession.getInstance().getMessage("council.meeting.vishay");
                    shortNoteStmt = ApplicationSession.getInstance().getMessage("council.meeting.sanshipt");
                }

                // if member doesn't have email id than don't execute below line unnecessary
                if (!StringUtils.isEmpty(memberDto.getCouEmail())) {
                    StringBuilder emailBody = new StringBuilder();
                    emailBody.append(
                            "<div class=\"printer\" style=\"width: 21cm;padding: 20px 20px;border: 1px solid #000000;margin: 0px auto;font-family: verdana, sans-serif;font-size: 13px;position: relative;margin-bottom: 40px;background: #ffffff;page-break-after: always;\">")

                            // D#117235
                            .append("<div class=\"row\">")
                            .append("  <div style=\"display: flex;\">")
                            .append("          <div style=\"float: left;\">")
                            .append("      <img src='cid:image1' style='width:80px;'/>")
                            .append("          </div>")
                            .append("          ")
                            .append("          <div style=\"margin-left: 200px;margin-top: 20px;text-align: center;\">")
                            .append("        <strong>"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.info")
                                    + "</strong><br>")
                            .append("        <strong>" + ulbName + "</strong><br> <br>")
                            .append("           </div>")
                            .append("  </div>")
                            .append("</div>")

                            .append("    <br>")
                            .append("    <div class=\"content\">")
                            .append("        <p style=\"line-height: 21px;\">")
                            .append("            To,<br>")
                            .append("            Sir/Madam,<br>")
                            .append(printDto.getMemberName() + "<br>")
                            .append("            Councillor " + printDto.getDesignation() + ", Ward No "
                                    + printDto.getElectionWard()
                                    + "<br>")
                            .append("            Address " + printDto.getMemberAddress() + "<br><br>")
                            .append(meetingInviteMsg + "<br>")
                            .append(bodyStmt)
                            .append("        </p>")
                            .append("        <br>")
                            .append("        <p style=\"line-height: 21px;\">"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.upasthit") + "</p>")
                            .append("        <p style=\"text-align: right;line-height: 21px;\">"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.adhikari") + "</p>")
                            .append("        <br>")
                            .append("        <div class=\"header\" style=\"text-align: center;margin-bottom: 30px;\">")
                            .append("            <strong>"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.parishisht")
                                    + "</strong><br>")
                            .append("        </div>")
                            .append("        <p style=\"line-height: 21px;\">" + belowAnnexureStmt + "")
                            .append("        </p>")
                            .append("        <br>")
                            .append("        <div class=\"tablular-content\">")
                            .append("            <table style=\"border-collapse: collapse;width: 100%;\">")
                            .append("                <thead>")
                            .append("                    <tr>")
                            .append("                        <th style=\"width: 100px;-webkit-print-color-adjust: exact;font-size: 12px;padding: 5px;background-color: #eeeeee !important;\" class=\"text-center\">"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.anukramank") + " <br>(1)</th>")
                            .append("                        <th class=\"text-center\" style=\"-webkit-print-color-adjust: exact;font-size: 12px;padding: 5px;background-color: #eeeeee !important;\">"
                                    + ApplicationSession.getInstance().getMessage("council.report.proposal.subject")
                                    + " <br>(2)</th>")
                            .append("                    </tr>")
                            .append("                </thead>")
                            .append("                <tbody>")
                            .append(subjects)
                            .append("                </tbody>")
                            .append("            </table>")
                            .append("        </div>")
                            .append("        <!-- English Data -->")
                            .append("        <p style=\"line-height: 21px;\">2. " + shortNoteStmt + "</p>")
                            .append("        <br>")
                            .append("        <p style=\"text-align: right;line-height: 21px;\">"
                                    + ApplicationSession.getInstance().getMessage("council.meeting.signature") + "</p>")
                            .append("    </div>")
                            .append("</div>");

                    // Send Email and SMS

                    final SMSAndEmailDTO dto = new SMSAndEmailDTO();
                    dto.setMobnumber(String.valueOf(memberDto.getCouMobNo()));
                    dto.setAppNo(meetingDto.getMeetingNo());
                    dto.setServName(MainetConstants.Council.Meeting.MEETING_SERVICE_NAME);
                    dto.setAppName(MainetConstants.Council.Meeting.APPLICATION_MEETING_INVITATION);
                    dto.setEmail(memberDto.getCouEmail());
                    dto.setDate(meetingDto.getMeetingDate());
                    dto.setPlace(meetingDto.getMeetingPlace());
                    dto.setType(meetingDto.getMeetingTypeName());
                    dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                    dto.setEmailBody(emailBody);// this is for custom HTML BODY MSG
                    dto.setInlineImages(inlineImages);
                    String menuUrl = MainetConstants.Council.MEETING_MASTER_URL;
                    Organisation org = new Organisation();
                    org.setOrgid(meetingDto.getOrgId());
                    int langId = UserSession.getCurrent().getLanguageId();
                    iSMSAndEmailService.sendEmailSMS(MainetConstants.Council.COUNCIL_MANAGEMENT,
                            menuUrl, PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
                }
            }

        } else {
            LOGGER.error("meeting id not getting for print agenda");
        }
        ModelAndView mv = new ModelAndView("agendaPrintPage", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("printingEntities", printingEntities);
        return mv;
    }

    /**
     * 
     * @param agendaId
     * @param committeeTypeId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(params = "getMeetingDetails", method = RequestMethod.POST)
    public CouncilRestResponseDto getMeetingDetailsByAgendaId(@RequestParam("agendaId") final Long agendaId,
            @RequestParam("committeeTypeId") final Long committeeTypeId, final HttpServletRequest request) {
        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        // get meeting details record like proposalList by agenda Id and committeeTypeId
        // ask here to fetch proposal list with (proposalStatus) approved or not
        // currently only fetch agendaId wise not both wise
        List<CouncilProposalMasterDto> agendaProposalDtoList = councilProposalMasterService
                .findAllProposalByAgendaId(agendaId);
        // fetch record from meeting member table by committeeTypeId
        Boolean dataForMeetingCommitteeMember = true;
        List<CouncilMemberCommitteeMasterDto> memberList = councilMemberCommitteeMasterService
                .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, dataForMeetingCommitteeMember,
                        MainetConstants.Council.ACTIVE_STATUS, MainetConstants.STATUS.ACTIVE,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        response.setCouncilProposalDto(agendaProposalDtoList);
        response.setMemberList(memberList);
        return response;
    }

    @RequestMapping(params = "editCouncilMeetingData", method = RequestMethod.POST)
    public ModelAndView editCouncilMeetingMasterData(@RequestParam("meetingId") final Long meetingId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.EDIT);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // make data for edit meeting
        CouncilMeetingMasterDto meetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);

        // User Story #72221
        List<CouncilMeetingMasterDto> meetingDetList = councilMeetingMasterService.getMeetingDetFromHistById(meetingId, orgId);

        String pattern = "dd/MM/yyyy HH:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);

        String todayAsString = df.format(meetingMasterDto.getMeetingDate());
        meetingMasterDto.setMeetingDateDesc(todayAsString);
        meetingMasterDto.setPrevMeetingDateDesc(todayAsString);
        meetingMasterDto.setPrevMeetingPlace(meetingMasterDto.getMeetingPlace());
        meetingMasterDto.setPrevMessage(meetingMasterDto.getMeetingInvitationMsg());
        meetingDetList.forEach(mettingDto -> {
            String meetingdate = df.format(mettingDto.getMeetingDate());
            mettingDto.setMeetingDateDesc(meetingdate);
        });

        Long agendaId = meetingMasterDto.getAgendaMasterDto().getAgendaId();

        // set data for councilAgenda
        CouncilAgendaMasterDto couAgendaMasterDto = meetingMasterDto.getAgendaMasterDto();
        this.getModel().setCouAgendaMasterDto(couAgendaMasterDto);
        // using agendaId fetch list of agenda here getting only one list
        CouncilAgendaMasterDto dto = councilAgendaMasterService.getCouncilAgendaMasterByAgendaId(agendaId);
        List<CouncilAgendaMasterDto> couAgendaMasterDtoList = new ArrayList<>(Arrays.asList(dto));

        this.getModel().setCouAgendaMasterDtoList(couAgendaMasterDtoList);
        // fetch list of proposal by agendaId
        List<CouncilProposalMasterDto> agendaProposalDtoList = councilProposalMasterService
                .findAllProposalByAgendaId(agendaId);
        this.getModel().setAgendaProposalDtoList(agendaProposalDtoList);
        Long committeeTypeId = meetingMasterDto.getAgendaMasterDto().getCommitteeTypeId();
        // fetch memberList by committeeType
        Boolean dataForMeetingCommitteeMember = false;
        /*
         * List<CouncilMemberCommitteeMasterDto> memberList = councilMemberCommitteeMasterService
         * .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, dataForMeetingCommitteeMember,
         * MainetConstants.Council.ACTIVE_STATUS);
         */
        // fetch meeting members by meeting id from TB_CMT_COUNCIL_MEETING_MEMBER
        List<CouncilMemberCommitteeMasterDto> memberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());

        this.getModel().setMemberList(memberList);
        // get meetingMembers from meetingMemberTable by meetingId
        List<CouncilMemberCommitteeMasterDto> meetingMemberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        // append memberMappingId (councilMemberId)
        String memberIdByCommitteeType = "";
        for (int i = 0; i < meetingMemberList.size(); i++) {
            Long id = meetingMemberList.get(i).getMemberId();
            memberIdByCommitteeType += id + ",";
        }
        // remove comma from end side
        if (memberIdByCommitteeType != null) {
            if (memberIdByCommitteeType.endsWith(",")) {
                memberIdByCommitteeType = memberIdByCommitteeType.substring(0, memberIdByCommitteeType.length() - 1);
            }
        }
        // used when update agenda
        meetingMasterDto.setAgendaId(agendaId);
        meetingMasterDto.setMemberIdByCommitteeType(memberIdByCommitteeType);
        this.getModel().setCouMeetingMasterDto(meetingMasterDto);
        this.getModel().setCouncilMeetingMasterDtoList(meetingDetList);
        List<TbLocationMas> locationList = ApplicationContextProvider.getApplicationContext().getBean(ILocationMasService.class)
                .fillAllLocationMasterDetails(UserSession.getCurrent().getOrganisation());
        this.getModel().setLocationList(locationList);
        return new ModelAndView("addCouncilMeetingMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    @RequestMapping(params = "viewCouncilMeetingData", method = RequestMethod.POST)
    public ModelAndView viewCouncilMeetingMasterData(@RequestParam("meetingId") final Long meetingId) {
        this.getModel().setSaveMode(MainetConstants.CommonConstants.VIEW);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        // make data for view meeting
        CouncilMeetingMasterDto meetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
        Long agendaId = meetingMasterDto.getAgendaMasterDto().getAgendaId();

        List<CouncilMeetingMasterDto> meetingDetList = councilMeetingMasterService.getMeetingDetFromHistById(meetingId, orgId);

        String pattern = "dd/MM/yyyy HH:mm:ss";

        DateFormat df = new SimpleDateFormat(pattern);

        String todayAsString = df.format(meetingMasterDto.getMeetingDate());
        meetingMasterDto.setMeetingDateDesc(todayAsString);
        meetingDetList.forEach(mettingDto -> {
            String meetingdate = df.format(mettingDto.getMeetingDate());
            mettingDto.setMeetingDateDesc(meetingdate);
        });

        // set data for councilAgenda
        CouncilAgendaMasterDto couAgendaMasterDto = meetingMasterDto.getAgendaMasterDto();
        this.getModel().setCouAgendaMasterDto(couAgendaMasterDto);
        // using agendaId fetch list of agenda here getting only one list
        CouncilAgendaMasterDto dto = councilAgendaMasterService.getCouncilAgendaMasterByAgendaId(agendaId);
        List<CouncilAgendaMasterDto> couAgendaMasterDtoList = new ArrayList<>(Arrays.asList(dto));

        this.getModel().setCouAgendaMasterDtoList(couAgendaMasterDtoList);
        // fetch list of proposal by agendaId
        List<CouncilProposalMasterDto> agendaProposalDtoList = councilProposalMasterService
                .findAllProposalByAgendaId(agendaId);
        this.getModel().setAgendaProposalDtoList(agendaProposalDtoList);
        Long committeeTypeId = meetingMasterDto.getAgendaMasterDto().getCommitteeTypeId();
        // fetch memberList by committeeType
        Boolean dataForMeetingCommitteeMember = false;

        /*
         * List<CouncilMemberCommitteeMasterDto> memberList = councilMemberCommitteeMasterService
         * .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, dataForMeetingCommitteeMember,
         * MainetConstants.Council.ACTIVE_STATUS);
         */
        // fetch meeting members by meeting id from TB_CMT_COUNCIL_MEETING_MEMBER
        List<CouncilMemberCommitteeMasterDto> memberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        this.getModel().setMemberList(memberList);
        // get meetingMembers from meetingMemberTable by meetingId
        List<CouncilMemberCommitteeMasterDto> meetingMemberList = councilMeetingMasterService
                .fetchMeetingMemberListByMeetingId(meetingId, UserSession.getCurrent().getOrganisation().getOrgid());
        // append memberMappingId (councilMemberId)
        String memberIdByCommitteeType = "";
        for (int i = 0; i < meetingMemberList.size(); i++) {
            Long id = meetingMemberList.get(i).getMemberId();
            memberIdByCommitteeType += id + ",";
        }
        // remove comma from end side
        if (memberIdByCommitteeType != null) {
            if (memberIdByCommitteeType.endsWith(",")) {
                memberIdByCommitteeType = memberIdByCommitteeType.substring(0, memberIdByCommitteeType.length() - 1);
            }
        }
        // used when update agenda
        meetingMasterDto.setAgendaId(agendaId);
        meetingMasterDto.setMemberIdByCommitteeType(memberIdByCommitteeType);
        this.getModel().setCouMeetingMasterDto(meetingMasterDto);
        this.getModel().setCouncilMeetingMasterDtoList(meetingDetList);
        return new ModelAndView("addCouncilMeetingMaster", MainetConstants.FORM_NAME, this.getModel());
    }

    // send MSG handler
    @ResponseBody
    @RequestMapping(params = "sendMsg", method = RequestMethod.POST)
    public String sendMessage(@RequestParam("meetingId") Long meetingId) {
        String responseMsg = "Message Sent Successfully";
        // get meeting data by using meeting Id
        CouncilMeetingMasterDto meetingDto = councilMeetingMasterService.getMeetingDataById(meetingId);
        // find out members available in this meeting by meetingId
        // here true is printReport based on Boolean query execute
        List<CouncilMemberCommitteeMasterDto> members = councilMeetingMasterService
                .fetchMeetingPresentMemberListByMeetingId(meetingId, MainetConstants.Council.ABSENT_STATUS, true,
                        UserSession.getCurrent().getOrganisation().getOrgid());
        for (CouncilMemberCommitteeMasterDto member : members) {
            // find memberData for get mobile no Till Time
            CouncilMemberMasterDto memberDto = councilMemberMasterService.getCouncilMemberMasterByCouId(member.getMemberId());
            // Send Email and SMS
            final SMSAndEmailDTO dto = new SMSAndEmailDTO();
            dto.setMobnumber(String.valueOf(memberDto.getCouMobNo()));
            dto.setAppNo(meetingDto.getMeetingNo());
            dto.setServName(MainetConstants.Council.Meeting.MEETING_SERVICE_NAME);
            dto.setAppName(MainetConstants.Council.Meeting.APPLICATION_MEETING_INVITATION);
            dto.setDate(meetingDto.getMeetingDate());
            dto.setPlace(meetingDto.getMeetingPlace());
            dto.setType(meetingDto.getMeetingTypeName());
            dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
            String menuUrl = MainetConstants.Council.MEETING_MASTER_URL;
            Organisation org = new Organisation();
            org.setOrgid(meetingDto.getOrgId());
            int langId = UserSession.getCurrent().getLanguageId();
            Boolean messageSend = iSMSAndEmailService.sendEmailSMS(MainetConstants.Council.COUNCIL_MANAGEMENT, menuUrl,
                    PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);
            if (!messageSend) {
                responseMsg = "Something went wrong";
                break;
            }
        }
        return responseMsg;
    }
    
    @RequestMapping(params = "encyptData", method = RequestMethod.POST)
    public void encyptData(@RequestParam("meetingMessage") final String meetingMessage) {
    	try{
    		String decodedMeetingMessage = URLDecoder.decode(meetingMessage, "UTF-8");
    		
    		this.getModel().setMeetingMessage(decodedMeetingMessage);
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    	
    	
    }

}
