package com.abm.mainet.council.ui.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbComparamDet;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.TbComparamDetService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.Filepaths;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilMemberMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.dto.CouncilRestResponseDto;
import com.abm.mainet.council.dto.MOMResolutionDto;
import com.abm.mainet.council.dto.PrintingDto;
import com.abm.mainet.council.service.ICouncilMOMService;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;
import com.abm.mainet.council.service.ICouncilMemberMasterService;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilMOMModel;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

@Controller
@RequestMapping(MainetConstants.Council.MOM_URL)
public class CouncilMOMController extends AbstractFormController<CouncilMOMModel> {

    @Autowired
    ICouncilMOMService councilMOMService;

    @Autowired
    ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    ICouncilProposalMasterService proposalMasterService;

    @Autowired
    IFileUploadService fileUpload;

    @Autowired
    private ICouncilMemberMasterService councilMemberMasterService;

    @Autowired
    private ICouncilMemberCommitteeMasterService committeeMasterService;

    @Autowired
    ICouncilMOMService momService;

    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CouncilMOMController.class);

    @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView index(HttpServletRequest request) throws Exception {
        this.sessionCleanup(request);
        fileUpload.sessionCleanUpForFileUpload();
        CouncilMOMModel model = this.getModel();
        model.setCommonHelpDocs(MainetConstants.Council.MOM_URL);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String orderBy = MainetConstants.Council.Meeting.ORDER_BY_MEETING_DATE;
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();
        councilMeetingMasterDtoList = councilMOMService.searchCouncilMOMMasterData(null, null, null, null,
                orgId, UserSession.getCurrent().getLanguageId());
        sortCouncilMeetingDtoListDescending(councilMeetingMasterDtoList);
        model.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        return defaultResult();
    }

    @ResponseBody
    @RequestMapping(params = "searchCouncilMOM", method = RequestMethod.POST)
    public CouncilRestResponseDto searchCouncilMOM(@RequestParam("meetingTypeId") final Long meetingTypeId,
            @RequestParam("meetingNo") final String meetingNo, @RequestParam("fromDate") final Date fromDate,
            @RequestParam("toDate") final Date toDate, final HttpServletRequest request) {
        getModel().bind(request);
        CouncilRestResponseDto response = new CouncilRestResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        String orderBy = MainetConstants.Council.Meeting.ORDER_BY_MEETING_DATE;
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMOMService
                .searchCouncilMOMMasterData(meetingTypeId, meetingNo, fromDate, toDate, orgId,
                        UserSession.getCurrent().getLanguageId());
        sortCouncilMeetingDtoListDescending(councilMeetingMasterDtoList);
        response.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        return response;
    }

    @ResponseBody
    @RequestMapping(params = "checkMeetingAttend", method = RequestMethod.POST)
    public Boolean checkMeetingAttend(@RequestParam("meetingId") final Long meetingId) {
        // 1st check any single member attend meeting or not (TB_CMT_COUNCIL_MEETING_MEMBER) if any single member attend than
        // don't restrict for create MOM
        return councilMeetingMasterService.checkMemberAttendMeeting(meetingId, MainetConstants.Council.PRESENT_STATUS);
    }

    @RequestMapping(params = "addCouncilMOM", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView addCouncilMOM(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilMOMModel model = new CouncilMOMModel();
        fileUpload.sessionCleanUpForFileUpload();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        setOmmflag(model);
        /*
         * List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = councilMeetingMasterService
         * .searchCouncilMeetingMasterData(null, null, null, null, orgId, null); councilMeetingMasterDtoList.forEach(meeting -> {
         * meetingNoList.add(meeting.getMeetingNo()); });
         */
        return new ModelAndView("addCouncilMOM", MainetConstants.FORM_NAME, model);
    }

    @ResponseBody
    @RequestMapping(params = "getMeetingNo", method = RequestMethod.POST)
    public List<CouncilMeetingMasterDto> getMeetingNo(@RequestParam("meetingTypeId") final Long meetingTypeId,
            final HttpServletRequest request) {
        getModel().bind(request);
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();

        councilMeetingMasterDtoList = councilMeetingMasterService.fetchMeetingsByMeetingTypeId(meetingTypeId, orgId);

        return councilMeetingMasterDtoList;
    }

    // @ResponseBody
    @RequestMapping(params = "getMeetingData", method = RequestMethod.POST)
    public ModelAndView getMeetingData(@RequestParam("meetingNo") final String meetingNo,
            final HttpServletRequest request) {
        getModel().bind(request);
        // UNNECCESSORY code deleting by ISRAT

        // CouncilRestResponseDto response = new CouncilRestResponseDto();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();
        List<MOMResolutionDto> meetingMOMDtos = new ArrayList<>();
        CouncilMeetingMasterDto meetingMasterDto = null;
        // check meeting no present
        if (StringUtils.isNotEmpty(meetingNo)) {
            councilMeetingMasterDtoList = councilMeetingMasterService.searchCouncilMeetingMasterData(null, meetingNo, null, null,
                    orgId, null);
            // 1. find agendaId using meeting id in meeting master table
            // 2. fetch all proposals using agendaId
            Long meetingId = councilMeetingMasterDtoList.get(0).getMeetingId();
            Date meetingDate = councilMeetingMasterDtoList.get(0).getMeetingDate();
            meetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
            Long agendaId = meetingMasterDto.getAgendaId();
            List<CouncilProposalMasterDto> proposalDtoList = proposalMasterService.findAllProposalByAgendaId(agendaId);

            proposalDtoList.forEach(proposal -> {

                MOMResolutionDto dto = councilMOMService.getMeetingMOMDataById(proposal.getProposalId(), orgId);
                if (dto == null) {

                    MOMResolutionDto momDto = new MOMResolutionDto();
                    BeanUtils.copyProperties(proposal, momDto);
                    momDto.setMeetingDate(meetingDate);
                    momDto.setMeetingId(meetingId);
                    // momDto.setResolutionComments("resolutionComments");
                    // set null because at the time of saving this value coming because of copyProperties
                    momDto.setUpdatedBy(null);
                    momDto.setUpdatedDate(null);
                    momDto.setLgIpMacUpd(null);
                    meetingMOMDtos.add(momDto);
                }
            });
            // response.setCouMeetingMasterDto(meetingMasterDto);
            // response.setMeetingMOMDtos(meetingMOMDtos);
        }
        // response.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        // return response;

        CouncilMOMModel model = this.getModel();
        model.setCouMeetingMasterDto(meetingMasterDto);
        model.setMeetingMOMDtos(meetingMOMDtos);
        model.setCouncilMeetingMasterDtoList(councilMeetingMasterDtoList);
        model.setDisableSelect(true);
        setOmmflag(model);
        model.setSaveMode(MainetConstants.CommonConstants.ADD);
        return new ModelAndView("addCouncilMOM", MainetConstants.FORM_NAME, model);
    }

    /*
     * @RequestMapping(params = "getSumotoDetails", method = RequestMethod.POST) public ModelAndView
     * getSumotoDetails(@RequestParam("meetingId") final Long meetingId,
     * @RequestParam("momId") final Long momId, @RequestParam("proposalId") final Long proposalId, final HttpServletRequest
     * request) { bindModel(request); this.getModel().setDepartmentList(
     * departmentService.findMappedDepartments(UserSession.getCurrent().getOrganisation().getOrgid())); // set data of SUMOTO
     * resolution List<MeetingMOMSumotoDto> momSumotoDtos = new ArrayList<>(); // fetch data from SUMOTO resolution table // if
     * not getting than get data from proposal master by proposal Id if (momId != null) { momSumotoDtos =
     * councilMOMService.fetchMOMSumotoDataByMOMIdAndProposalId(momId, proposalId); } else { CouncilProposalMasterDto proposalDto
     * = proposalMasterService.getCouncilProposalMasterByproposalId(proposalId); MeetingMOMSumotoDto momDto = new
     * MeetingMOMSumotoDto(); BeanUtils.copyProperties(proposalDto, momDto); // get DEPT name and detailsOfProposal String
     * deptName = departmentService.findDepartmentById(proposalDto.getProposalDepId()).getDpDeptdesc(); // String proposalDetails
     * = // proposalMasterService.getCouncilProposalMasterByproposalId(proposalDto.getProposalId()).getProposalDetails();
     * momDto.setDepartment(deptName); // momDto.setDetailsOfReso(detailsOfReso); // department id set
     * momDto.setSumotoDepId(proposalDto.getProposalDepId()); momSumotoDtos.add(momDto); } // data set in main MeetingMOMDtos
     * List<MOMResolutionDto> meetingMomDtos = new ArrayList<>(); // here when back press form addSumoto details page than remove
     * last element // from this.getModel().getMeetingMOMDtos() like size()-1 for (MOMResolutionDto dto :
     * this.getModel().getMeetingMOMDtos()) { dto.setMomSumotoDtos(momSumotoDtos); meetingMomDtos.add(dto); }
     * this.getModel().setMomSumotoDtos(momSumotoDtos); this.getModel().setMeetingMOMDtos(meetingMomDtos); // this is imp because
     * using this we identify unique thing when data bind with model (used in saveSumotoDetailsForm method // in Model )
     * this.getModel().setProposalId(proposalId); return new ModelAndView("addSumotoDetails", MainetConstants.FORM_NAME,
     * this.getModel()); }
     */

    /*
     * @RequestMapping(params = "saveSumotoDetailsForm", method = RequestMethod.POST) public ModelAndView
     * saveSumotoDetailsForm(final HttpServletRequest request) { bindModel(request); this.getModel().saveSumotoDetailsForm();
     * return defaultResult(); }
     */

    @RequestMapping(params = "showCurrentForm", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView showCurrentForm(final HttpServletRequest request) {
        getModel().bind(request);
        CouncilMOMModel momModel = this.getModel();
        return new ModelAndView("addCouncilMOM", MainetConstants.FORM_NAME, momModel);
    }

    /*
     * @RequestMapping(params = "saveMeetingMOM", method = RequestMethod.POST) public ModelAndView saveMOM(final
     * HttpServletRequest request) { bindModel(request); this.getModel().createMOM(); return defaultResult(); }
     */
    @RequestMapping(params = "editViewMOMData", method = { RequestMethod.POST, RequestMethod.GET })
    public ModelAndView editViewMOMData(final HttpServletRequest request, @RequestParam("mode") final String mode,
            @RequestParam("meetingId") final Long meetingId) {
        bindModel(request);
        CouncilMOMModel model = this.getModel();
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        model.setSaveMode(mode);
        model.setCouncilMeetingMasterDtoList(councilMeetingMasterService.fetchMeetingDetailsById(meetingId,
                UserSession.getCurrent().getOrganisation().getOrgid()));
        // 1. find agendaId using meeting id in meeting master table
        // 2. fetch all proposals using agendaId
        CouncilMeetingMasterDto meetingMasterDto = councilMeetingMasterService.getMeetingDataById(meetingId);
        Long agendaId = meetingMasterDto.getAgendaId();
        List<CouncilProposalMasterDto> proposalDtoList = proposalMasterService.findAllProposalByAgendaId(agendaId);
        List<MOMResolutionDto> meetingMOMDtos = new ArrayList<>();
        proposalDtoList.forEach(proposal -> {
            MOMResolutionDto momDto = new MOMResolutionDto();
            // BeanUtils.copyProperties(proposal, momDto);

            // here resolution comment and action find from TB_CMT_COUNCIL_MEETING_MOM table
            // against proposalId
            MOMResolutionDto dto = councilMOMService.getMeetingMOMDataById(proposal.getProposalId(), orgId);

            if (dto == null) {
                momDto.setResolutionComments(MainetConstants.Council.Meeting.NO_COMMENTS);
            } else {
                BeanUtils.copyProperties(dto, momDto);
            }
            momDto.setProposalDetails(proposal.getProposalDetails());
            momDto.setProposalId(proposal.getProposalId());
            momDto.setMeetingId(meetingId);
            momDto.setProposalNo(proposal.getProposalNo());
            momDto.setMeetingDate(model.getCouncilMeetingMasterDtoList().get(0).getMeetingDate());
            

            meetingMOMDtos.add(momDto);
        });

        // get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        "MOM" + MainetConstants.WINDOWS_SLASH + meetingId);
        model.setAttachDocsList(attachDocs);
        model.setCouMeetingMasterDto(meetingMasterDto);
        model.setMeetingMOMDtos(meetingMOMDtos);
        if(meetingMOMDtos!=null && !meetingMOMDtos.isEmpty() && meetingMOMDtos.get(0).getResolutionComments()!=null)
        model.setResolutionComments(meetingMOMDtos.get(0).getResolutionComments());
        setOmmflag(model);
        model.setDisableSelect(true);
        return new ModelAndView("addCouncilMOM", MainetConstants.FORM_NAME, this.getModel());
    }

    /*
     * this is MOM report print and override the PrintReport handler of Abstract Form Controller
     */
    @SuppressWarnings("unused")
    @Override
    @RequestMapping(params = "PrintReport")
    public ModelAndView printRTIStatusReport(final HttpServletRequest request) {
        // sessionCleanup(request);

        // String requestUrl = request.getRequestURL().toString();
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        Long meetingId = this.getModel().getPrintMeetingId();
        // Long meetingId = 30l;

        PrintingDto printDto = new PrintingDto();
        if (meetingId != null) {
            // get meeting data by using meeting Id
            CouncilMeetingMasterDto meetingDto = councilMeetingMasterService.getMeetingDataById(meetingId);
            String ulbName, muncipal, departmentName;
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
            Long agendaId = meetingDto.getAgendaId();
            String agendaNo = String.valueOf(meetingDto.getAgendaMasterDto().getAgendaNo());
            // fetch present attendee details from TB_CMT_COUNCIL_MEETING_MEMBER Table
            // here true is printReport Boolean based on query execute
            List<CouncilMemberCommitteeMasterDto> members = councilMeetingMasterService
                    .fetchMeetingPresentMemberListByMeetingId(meetingId, MainetConstants.Council.PRESENT_STATUS, true,
                            UserSession.getCurrent().getOrganisation().getOrgid());
            // set data in printDto
            printDto.setUlbName(ulbName);
            TbDepartment department = ApplicationContextProvider.getApplicationContext().getBean(TbDepartmentService.class)
                    .findDeptByCode(orgId,
                            MainetConstants.FlagA, MainetConstants.Council.COUNCIL_MANAGEMENT);
            if (department == null) {
                LOGGER.error("department object not found");
            }
            if (UserSession.getCurrent().getLanguageId() == 1) {
                departmentName = department.getDpDeptdesc();
            } else {
                departmentName = department.getDpNameMar();
            }
            printDto.setDepartmentName(departmentName);// doubt ask to SAMADHAN SIR
            printDto.setCommitteeName(meetingDto.getAgendaMasterDto().getCommitteeType());
            printDto.setAgendaNo(agendaNo);
            printDto.setMeetingNo(meetingDto.getMeetingNo());
            printDto.setMeetingDateDesc(meetingDto.getMeetingDateDesc());
            // get MOM date
            MOMResolutionDto momData = momService.getMOMByMeetingId(meetingId);
            Date momDateTime = momData.getCreatedDate();
            String momDate = Utility.dateToString(momDateTime);
            printDto.setMomDate(momDate);
            // set resolution comment
            printDto.setMomResolutionDto(momData);
            List<CouncilMemberCommitteeMasterDto> attendees = new ArrayList<>();
            // set attendance details
            for (CouncilMemberCommitteeMasterDto member : members) {
                CouncilMemberCommitteeMasterDto attendee = new CouncilMemberCommitteeMasterDto();
                attendee.setMemberName(member.getMemberName());
                attendee.setDesignation(member.getCouMemberTypeDesc());
                attendee.setAttendanceStatus(member.getAttendanceStatus());
                attendees.add(attendee);
            }
            printDto.setAttendees(attendees);

            // get attached file
            List<File> filesForAttach = new ArrayList<File>();
            for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
                List<File> list = new ArrayList<>(entry.getValue());
                filesForAttach.addAll(list);
            }
            String PFA = "<p><strong>Please Find Attached MOM Document </strong></p>";
            if (filesForAttach.isEmpty()) {
                PFA = "";
            }

            // code for runtime send email to members
            // demo code for send mail for testing purpose
            String attendanceDetails = "";
            int count = 1;
            for (int i = 0; i < attendees.size(); i++) {
                int srNo = count++;
                String status = "PRESENT";
                if (attendees.get(i).getAttendanceStatus() == 0) {
                    status = "ABSENT";
                }
                attendanceDetails += "<tr>\r\n" +
                        "<td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">" + srNo
                        + "</td>\r\n" +
                        " <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                        + attendees.get(i).getMemberName() + "</td>\r\n" +
                        "<td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                        + attendees.get(i).getDesignation() + "</td>\r\n" +
                        " <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                        + status + "</td>\r\n" +
                        "</tr>";
            }

            StringBuilder emailBody = new StringBuilder();
            emailBody.append("<div class=\"printer\"")
                    .append("    style=\"width: 21cm;height: 30.4cm;padding: 20px 20px;border: 1px solid #eaeaea;margin: 0px auto;font-family: verdana, sans-serif;font-size: 13px;position: relative;\">")
                    .append("    <div class=\"tablular-content\">")
                    // D#117235
                    .append("<div class=\"row\">")
                    .append("  <div style=\"display: flex;\">")
                    .append("          <div style=\"float: left;\">")
                    .append("      <img src='cid:image1' style='width:80px;'/>")
                    .append("          </div>")
                    .append("          ")
                    .append("          <div style=\"margin-left: 220px;margin-top: 20px;text-align: center;\">")
                    .append("        <strong>"
                            + ApplicationSession.getInstance().getMessage("council.mom.print.header")
                            + "</strong><br>")

                    .append("           </div>")
                    .append("  </div>")
                    .append("</div>")

                    .append("        <table style=\"border-collapse: collapse;width: 100%;font-size: 13px;\">")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"width: 20%;-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    ULB Name</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getUlbName() + "</td>")
                    .append("            </tr>")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Department Name</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">COUNCIL</td>")
                    .append("            </tr>")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Date</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getMomDate() + "</td>")
                    .append("            </tr>")
                    .append("        </table><br>")
                    .append("        <table style=\"border-collapse: collapse;width: 100%;font-size: 13px;\">")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"width: 30%;-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Committee Name</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getCommitteeName() + "</td>")
                    .append("            </tr>")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Meeting Agenda Number</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getAgendaNo() + "</td>")
                    .append("            </tr>")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Meeting Number</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getMeetingNo() + "</td>")
                    .append("            </tr>")
                    .append("            <tr>")
                    .append("                <th")
                    .append("                    style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                    Meeting Date</th>")
                    .append("                <td style=\"padding: 5px;font-size: 12px;border: 1px solid #cccaca;\">"
                            + printDto.getMeetingDateDesc() + "</td>")
                    .append("            </tr>")
                    .append("        </table><br>")
                    .append("        <p><strong>Attendance Details: </strong></p>")
                    .append("        <table style=\"border-collapse: collapse;width: 100%;font-size: 13px;\">")
                    .append("            <thead>")
                    .append("                <tr>")
                    .append("                    <th")
                    .append("                        style=\"width: 10%;-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                        Sr. No</th>")
                    .append("                    <th")
                    .append("                        style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                        Member Name</th>")
                    .append("                    <th")
                    .append("                        style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                        Designation</th>")
                    .append("                    <th")
                    .append("                        style=\"-webkit-print-color-adjust: exact;text-align: left;padding: 5px;font-size: 12px;border: 1px solid #cccaca;background-color: #eeeeee!important;\">")
                    .append("                        Status</th>")
                    .append("                </tr>")
                    .append("            </thead>")
                    .append("            <tbody>")
                    .append(attendanceDetails)
                    .append("            </tbody>")
                    .append("        </table><br>")
                    .append("   <p><strong>Resolution Comment Details: </strong></p>")
                    .append(printDto.getMomResolutionDto().getResolutionComments())
                    .append(PFA)
                    .append("    </div>")
                    .append("</div>");

            // doing this because of INLINE resource in common code of EmailService line no 287
            String filePath = Filepaths.getfilepath() + MainetConstants.FILE_PATH_SEPARATOR
                    + UserSession.getCurrent().getOrgLogoPath();
            final File inlineFile = new File(filePath);
            // INLINE images
            Map<String, String> inlineImages = new HashMap<String, String>();
            if (inlineFile != null && inlineFile.length() > 0) {
                inlineImages.put("image1", inlineFile.toString());
            }

            // fetch committee members by committeeTypeId
            Long committeeTypeId = meetingDto.getAgendaMasterDto().getCommitteeTypeId();
            // Long committeeTypeId = 42168l;
            // here false is without dissolve date compare query
            List<CouncilMemberCommitteeMasterDto> committeeMembers = committeeMasterService
                    .fetchMappingMemberListByCommitteeTypeId(committeeTypeId, false, MainetConstants.Council.ACTIVE_STATUS,
                            MainetConstants.STATUS.INACTIVE, orgId);
            for (CouncilMemberCommitteeMasterDto committeeMember : committeeMembers) {
                // find memberData for get emailId Till Time
                Long memberId = committeeMember.getMemberId();
                CouncilMemberMasterDto memberDto = councilMemberMasterService.getCouncilMemberMasterByCouId(memberId);
                // Send Email and SMS
                final SMSAndEmailDTO dto = new SMSAndEmailDTO();
                dto.setAppNo(meetingDto.getMeetingNo());
                dto.setServName(MainetConstants.Council.MOM.SERVICE_NAME);
                dto.setAppName(MainetConstants.Council.MOM.APPLICATION_MOM_REPORTS);
                dto.setEmail(memberDto.getCouEmail());
                dto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
                dto.setEmailBody(emailBody);
                String menuUrl = MainetConstants.Council.MOM_URL;
                Organisation org = new Organisation();
                org.setOrgid(meetingDto.getOrgId());
                int langId = UserSession.getCurrent().getLanguageId();

                dto.setFilesForAttach(filesForAttach);
                dto.setInlineImages(inlineImages);

                iSMSAndEmailService.sendEmailSMS(MainetConstants.Council.COUNCIL_MANAGEMENT, menuUrl,
                        PrefixConstants.SMS_EMAIL_ALERT_TYPE.SUBMITTED, dto, org, langId);

                break;
            }
        } else {
            LOGGER.error("meeting id not found for print MOM");
        }
        ModelAndView mv = new ModelAndView("momPrintPage", MainetConstants.FORM_NAME, this.getModel());
        mv.addObject("printDto", printDto);
        return mv;
    }

    @ResponseBody
    @RequestMapping(params = "getPraposalDetails", method = { RequestMethod.POST })
    public ModelAndView viewCaseHearingHistory(HttpServletRequest request, @RequestParam("proposalId") final long proposalId) {

        List<MOMResolutionDto> meetingMOMDtos = this.getModel().getMeetingMOMDtos();

        MOMResolutionDto momResolutionDto = meetingMOMDtos.stream()
                .filter(x -> proposalId == x.getProposalId())
                .findAny()
                .orElse(null);

        MOMResolutionDto meetingMOMData = councilMOMService.getMeetingMOMDataById(proposalId,
                UserSession.getCurrent().getOrganisation().getOrgid());

        List<MOMResolutionDto> meetingMOMDtosList = new ArrayList<>();

        if (meetingMOMData != null) {
            momResolutionDto.setStatus(meetingMOMData.getStatus());
            momResolutionDto.setResolutionComments(meetingMOMData.getResolutionComments());
        }
        meetingMOMDtosList.add(momResolutionDto);
        this.getModel().setMeetingMOMDtos(meetingMOMDtosList);

        return new ModelAndView("TextEditor", MainetConstants.FORM_NAME, this.getModel());

    }

    @ResponseBody
    @RequestMapping(params = "getTextEditOmm", method = { RequestMethod.POST })
    public ModelAndView getTextEditOmm(HttpServletRequest request) {
    	this.getModel().bind(request);
        return new ModelAndView("TextEditor", MainetConstants.FORM_NAME, this.getModel());

    }
    
    @RequestMapping(params = "previousPage", method = RequestMethod.POST)
    public ModelAndView previousPage(final Model model, final HttpServletRequest httpServletRequest) {
        this.getModel().bind(httpServletRequest);
        return new ModelAndView("addCouncilMOM", MainetConstants.FORM_NAME, getModel());
    }
    
    public static void sortCouncilMeetingDtoListDescending(List<CouncilMeetingMasterDto> dtoList) {
        try {
            Collections.sort(dtoList, Comparator.comparingLong(CouncilMeetingMasterDto::getMeetingId).reversed());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    
    public static void setOmmflag(CouncilMOMModel model) {
    	LookUp lookUp = null;
        try {
        	lookUp = CommonMasterUtility.getLookUpFromPrefixLookUpValue("OMM", "ENV", 1,
					UserSession.getCurrent().getOrganisation());
		} catch (FrameworkException e) {

		}
		if (lookUp != null && lookUp.getOtherField() != null && lookUp.getOtherField().equals("Y")) {
			model.setoMMFlag(MainetConstants.FlagY);
		}
    }
    
    @RequestMapping(params = "encyptData", method = RequestMethod.POST)
    public void encyptData(@RequestParam("resolutionComments") final String resolutionComments) {
    	try{
    		String decodedResolutionComments = URLDecoder.decode(resolutionComments, "UTF-8");
    		
    		this.getModel().setResolutionComments(decodedResolutionComments);
    	} catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    	
    	
    }

}
