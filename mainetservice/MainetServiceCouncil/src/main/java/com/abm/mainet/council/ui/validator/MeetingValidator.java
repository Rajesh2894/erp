package com.abm.mainet.council.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.council.dto.CouncilMeetingMasterDto;

@Component
public class MeetingValidator extends BaseEntityValidator<CouncilMeetingMasterDto>{

	@Override
	protected void performValidations(CouncilMeetingMasterDto dto,
			EntityValidationContext<CouncilMeetingMasterDto> validationContext) {
		
		if (dto.getAgendaId() == null ) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("council.meeting.validation.meetingType"));
        }
		if(dto.getMeetingTypeId() == null || dto.getMeetingTypeId() == 0) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("council.meeting.validation.agenda"));
		}
		if(dto.getMeetingDateDesc() == null || dto.getMeetingDateDesc().isEmpty()) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("council.meeting.validation.meetingDateTime"));
		}
		if(dto.getMeetingInvitationMsg() == null || dto.getMeetingInvitationMsg().isEmpty()) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("council.meeting.validation.msgInvitation"));
		}
		if(dto.getMemberIdByCommitteeType() == null || dto.getMemberIdByCommitteeType().isEmpty()) {
			validationContext.addOptionConstraint(getApplicationSession().getMessage("council.meeting.validation.members"));
		}		
	}
}