package com.abm.mainet.cms.ui.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.FrequentlyAskedQuestions;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;

/**
 * @author rajendra.bhujbal
 *
 */
@Component
public class AdminFAQVlidator extends BaseEntityValidator<FrequentlyAskedQuestions> {

    @Override
    protected void performValidations(final FrequentlyAskedQuestions entity,
            final EntityValidationContext<FrequentlyAskedQuestions> validationContext) {

        final String check = ApplicationSession.getInstance().getMessage(MainetConstants.UNICODE);
        if (MainetConstants.NON.equals(check)) {

            if ((entity.getQuestionEn() == null) || entity.getQuestionEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.quetion1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getQuestionEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipfaq.quetion1en"));
                } else if ((entity.getQuestionEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.quetion1"));
                }
            }

            if ((entity.getAnswerEn() == null) || entity.getAnswerEn().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.answer1"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.ENGLISH_REG_EX).matcher(entity.getAnswerEn()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipfaq.answer1en"));
                } else if ((entity.getAnswerEn().replaceAll(MainetConstants.W, MainetConstants.BLANK).trim()).isEmpty()) {
                    validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.answer1"));
                }
            }

            if ((entity.getQuestionReg() == null) || entity.getQuestionReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.quetion2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getQuestionReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipfaq.quetion2mar"));
                }
            }

            if ((entity.getAnswerReg() == null) || entity.getAnswerReg().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("eipfaq.answer2"));
            } else {
                if (!Pattern.compile(MainetConstants.RegEx.MARATHI_REG_EX).matcher(entity.getAnswerReg()).matches()) {
                    validationContext.addOptionConstraint(getApplicationSession()
                            .getMessage("eipfaq.answer2mar"));
                }
            }
        } else {
            validationContext.rejectIfEmpty(entity.getQuestionEn(), "questionEn");

            validationContext.rejectIfEmpty(entity.getAnswerEn(), "answerEn");

            validationContext.rejectIfEmpty(entity.getQuestionReg(), "questionReg");

            validationContext.rejectIfEmpty(entity.getAnswerReg(), "answerReg");
        }
    }

}
