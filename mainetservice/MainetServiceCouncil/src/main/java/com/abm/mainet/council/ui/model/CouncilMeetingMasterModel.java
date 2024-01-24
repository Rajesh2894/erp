package com.abm.mainet.council.ui.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.council.domain.CouncilAgendaMasterEntity;
import com.abm.mainet.council.domain.CouncilMeetingMasterEntity;
import com.abm.mainet.council.dto.CouncilAgendaMasterDto;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;
import com.abm.mainet.council.dto.CouncilMemberCommitteeMasterDto;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.repository.CouncilMeetingMasterRepository;
import com.abm.mainet.council.service.ICouncilMeetingMasterService;
import com.abm.mainet.council.service.ICouncilMemberCommitteeMasterService;
import com.abm.mainet.council.ui.validator.MeetingValidator;

@Component
@Scope("session")
public class CouncilMeetingMasterModel extends AbstractFormModel {

    private static final long serialVersionUID = -6270033092206912011L;
    private String saveMode;
    private CouncilMeetingMasterDto couMeetingMasterDto = new CouncilMeetingMasterDto();
    private List<CouncilMeetingMasterDto> councilMeetingMasterDtoList = new ArrayList<>();
    // this is use when meeting invitation form creation
    private CouncilAgendaMasterDto couAgendaMasterDto = new CouncilAgendaMasterDto();
    private List<CouncilAgendaMasterDto> couAgendaMasterDtoList = new ArrayList<>();
    private List<CouncilProposalMasterDto> agendaProposalDtoList = new ArrayList<>();
    private List<TbLocationMas> locationList = new ArrayList<TbLocationMas>();
    private List<CouncilMemberCommitteeMasterDto> memberList = new ArrayList<>();
    private Long committeeTypeId;
    private static Long printMeetingId;
    private String meetingMessage;

    @Autowired
    private ICouncilMeetingMasterService councilMeetingMasterService;

    @Autowired
    private CouncilMeetingMasterRepository councilMeetingMasterRepository;

    @Autowired
    private ICouncilMemberCommitteeMasterService councilMemberCommitteeService;

    public static long getPrintMeetingId() {
        return printMeetingId;
    }

    public static void setPrintMeetingId(long printMeetingId) {
        CouncilMeetingMasterModel.printMeetingId = printMeetingId;
    }

    public Long getCommitteeTypeId() {
        return committeeTypeId;
    }

    public void setCommitteeTypeId(Long committeeTypeId) {
        this.committeeTypeId = committeeTypeId;
    }

    public String getSaveMode() {
        return saveMode;
    }

    public void setSaveMode(String saveMode) {
        this.saveMode = saveMode;
    }

    public CouncilMeetingMasterDto getCouMeetingMasterDto() {
        return couMeetingMasterDto;
    }

    public void setCouMeetingMasterDto(CouncilMeetingMasterDto couMeetingMasterDto) {
        this.couMeetingMasterDto = couMeetingMasterDto;
    }

    public List<CouncilMeetingMasterDto> getCouncilMeetingMasterDtoList() {
        return councilMeetingMasterDtoList;
    }

    public void setCouncilMeetingMasterDtoList(List<CouncilMeetingMasterDto> councilMeetingMasterDtoList) {
        this.councilMeetingMasterDtoList = councilMeetingMasterDtoList;
    }

    public ICouncilMeetingMasterService getCouncilMeetingMasterService() {
        return councilMeetingMasterService;
    }

    public void setCouncilMeetingMasterService(ICouncilMeetingMasterService councilMeetingMasterService) {
        this.councilMeetingMasterService = councilMeetingMasterService;
    }

    public List<CouncilAgendaMasterDto> getCouAgendaMasterDtoList() {
        return couAgendaMasterDtoList;
    }

    public void setCouAgendaMasterDtoList(List<CouncilAgendaMasterDto> couAgendaMasterDtoList) {
        this.couAgendaMasterDtoList = couAgendaMasterDtoList;
    }

    public List<CouncilProposalMasterDto> getAgendaProposalDtoList() {
        return agendaProposalDtoList;
    }

    public void setAgendaProposalDtoList(List<CouncilProposalMasterDto> agendaProposalDtoList) {
        this.agendaProposalDtoList = agendaProposalDtoList;
    }

    public CouncilAgendaMasterDto getCouAgendaMasterDto() {
        return couAgendaMasterDto;
    }

    public void setCouAgendaMasterDto(CouncilAgendaMasterDto couAgendaMasterDto) {
        this.couAgendaMasterDto = couAgendaMasterDto;
    }

    public List<TbLocationMas> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<TbLocationMas> locationList) {
        this.locationList = locationList;
    }

    public List<CouncilMemberCommitteeMasterDto> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<CouncilMemberCommitteeMasterDto> memberList) {
        this.memberList = memberList;
    }

    @Override
    public boolean saveForm() {
        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			if (!Objects.isNull(meetingMessage)) {
				couMeetingMasterDto.setMeetingInvitationMsg(meetingMessage);
			}
		}
        boolean meetingPresent = false, committeeDissolveDate = false;
        validateBean(couMeetingMasterDto, MeetingValidator.class);
        // insertion in 1.MeetingMaster 2.MeetingMember
        Long meetingId = this.couMeetingMasterDto.getMeetingId();

        // date setup
        String meetingDateDesc = this.couMeetingMasterDto.getMeetingDateDesc();
        DateFormat formatter = new SimpleDateFormat(MainetConstants.Council.Meeting.MEETING_DATE_FORMATE);
        if (!meetingDateDesc.contains(" ")) {
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        }

        Date meetingDate = null;
        if (meetingDateDesc != null) {
            try {
                meetingDate = formatter.parse(meetingDateDesc);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        /* Long committeeTypeId = this.couAgendaMasterDto.getCommitteeTypeId(); */
        String memberIdByCommitteeType = this.couMeetingMasterDto.getMemberIdByCommitteeType();
        String splitIds[] = memberIdByCommitteeType.split(MainetConstants.operator.COMMA);
        // make List of memberId long array
        List<Long> memberIds = new ArrayList<>();
        for (int i = 0; i < splitIds.length; i++) {
            memberIds.add(Long.valueOf(splitIds[i]));
        }
        // double check memberIds in dissolve date or not
        List<String> errorMessage = councilMemberCommitteeService.fetchMemberNotExistInDissolveDate(memberIds, committeeTypeId,
                MainetConstants.Council.ACTIVE_STATUS, orgId);
        // error message iterate
        for (String error : errorMessage) {
            addValidationError(error + "\r\n");
            committeeDissolveDate = true;
        }
        // if true than return error message don't execute next code
        if (committeeDissolveDate || hasValidationErrors()) {
            return false;
        }
        if (meetingId == null) {
            // validation for meeting already exist or not
            // 1.check this meeting type already present or not
            // date create
            String today = new SimpleDateFormat(MainetConstants.Council.Meeting.MEETING_DATE_FORMATE).format(new Date());
            Date currentDate = null;
            try {
                currentDate = new SimpleDateFormat(MainetConstants.Council.Meeting.MEETING_DATE_FORMATE).parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // here is bug for tester if same AgendaId is assign to other meetingType and if meeting date cross than again same
            // agenda with same meetingType can create(only if meeting date cross than this scenario come)
            meetingPresent = councilMeetingMasterRepository.checkMeetingExist(couMeetingMasterDto.getMeetingTypeId(), currentDate,
                    couMeetingMasterDto.getAgendaId(), orgId);

            if (meetingPresent) {
                addValidationError(getAppSession().getMessage("council.meeting.validation.meetingType.alreadyPresent"));
            } else {
                // 2.check here agendaId already present in meeting in case of ADD
                CouncilAgendaMasterEntity entity = new CouncilAgendaMasterEntity();
                entity.setAgendaId(couMeetingMasterDto.getAgendaId());
                List<CouncilMeetingMasterEntity> meetingMaster = councilMeetingMasterRepository.findMeetingByAgendaId(entity);
                if (!meetingMaster.isEmpty()) {
                    // this agenda is already present
                    meetingPresent = true;
                    addValidationError(getAppSession().getMessage("council.meeting.validation.agenda.alreadyPresent"));
                }
            }

        } else {
            // check here also meeting present or not
            addValidationError(getAppSession().getMessage("council.meeting.validation.alreadyPresent"));
            // validate for meeting time not more than current

            // in this query ignore current row id and compare with other than this row
            meetingPresent = councilMeetingMasterRepository.checkMeetingPresent(meetingId, couMeetingMasterDto.getAgendaId());
        }
        if (meetingPresent && hasValidationErrors()) {
            return false;
        }

        couMeetingMasterDto.setMeetingDate(meetingDate);
        couMeetingMasterDto.setMeetingTypeName(CommonMasterUtility
                .getCPDDescription(couMeetingMasterDto.getMeetingTypeId().longValue(), MainetConstants.MENU.E));
        if (meetingId != null) {
            // update meeting
            couMeetingMasterDto.setUpdatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couMeetingMasterDto.setUpdatedDate(new Date());
            couMeetingMasterDto.setLgIpMacUpd(UserSession.getCurrent().getEmployee().getEmppiservername());
            councilMeetingMasterService.saveCouncilMeeting(couMeetingMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.meeting.updatesuccessmsg"));
        } else {
            // save meeting
            couMeetingMasterDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
            couMeetingMasterDto.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
            couMeetingMasterDto.setCreatedDate(new Date());
            couMeetingMasterDto.setLgIpMac(UserSession.getCurrent().getEmployee().getEmppiservername());
            if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
            	couMeetingMasterDto.setMeetingStatus(MainetConstants.ApplicationStatus.PENDING);
        	}
            councilMeetingMasterService.saveCouncilMeeting(couMeetingMasterDto);
            setSuccessMessage(ApplicationSession.getInstance().getMessage("council.meeting.savesuccessmsg") +MainetConstants.WHITE_SPACE +couMeetingMasterDto.getMeetingNo());

        }

        return true;
    }

	public String getMeetingMessage() {
		return meetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		this.meetingMessage = meetingMessage;
	}

}
