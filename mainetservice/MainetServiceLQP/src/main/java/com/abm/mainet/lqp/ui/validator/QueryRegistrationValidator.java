package com.abm.mainet.lqp.ui.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.lqp.dto.QueryRegistrationMasterDto;

@Component
public class QueryRegistrationValidator extends BaseEntityValidator<QueryRegistrationMasterDto> {

    @Override
    protected void performValidations(QueryRegistrationMasterDto dto,
            EntityValidationContext<QueryRegistrationMasterDto> validationContext) {
        if (dto.getQuestionDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.questionDate"));
        }
        if (dto.getQuestionTypeId() == null || dto.getQuestionTypeId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.questionType"));
        }
        if (StringUtils.isEmpty(dto.getInwardNo())) {
            validationContext
                    .addOptionConstraint(getApplicationSession().getMessage("lqp.validation.inwardNo"));
        }
        if (dto.getDeptId() == null || dto.getDeptId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.deptName"));
        }
        if (dto.getRaisedByAssemblyId() == null || dto.getRaisedByAssemblyId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.raisedAssembly"));
        }
        if (StringUtils.isEmpty(dto.getQuestionSubject())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.subject"));
        }
        if (StringUtils.isEmpty(dto.getMlaName())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.mlaName"));
        }
        if (StringUtils.isEmpty(dto.getQuestion())) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.questionDetails"));
        }
        if (dto.getMeetingDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.meetingDate"));
        }
        if (dto.getDeadlineDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.deadlineDate"));
        }
        if (dto.getQuestionRaisedDate() == null) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.questionRaisedDate "));
        }
        // date comparison logic for query registration
        if (!(dto.getQuestionRaisedDate().compareTo(dto.getMeetingDate()) >= 0)) {
            // question raised date should be equal to meeting date
            validationContext
            		.addOptionConstraint(getApplicationSession().getMessage("lqp.validation.raisedDateWithMeetingDate"));
        }
        if (!(dto.getQuestionRaisedDate().compareTo(dto.getQuestionDate()) <= 0)) {
            // question raised date should be less than or equal question date
            validationContext
                    .addOptionConstraint(getApplicationSession().getMessage("lqp.validation.raisedDateWithQuestionDate"));
        }
        if (!(dto.getMeetingDate().compareTo(dto.getQuestionDate()) <= 0)) {
            // meeting date should be less than or equal to question date
            validationContext
                    .addOptionConstraint(getApplicationSession().getMessage("lqp.validation.MeetingDateWithQuestionDate"));
        }
        if (!(dto.getDeadlineDate().compareTo(dto.getQuestionDate()) >= 0)) {
            // deadline date should be greater than or equal to question date
            validationContext
                    .addOptionConstraint(getApplicationSession().getMessage("lqp.validation.deadineDateWithQuestionDate"));
        }
    }
}
