package com.abm.mainet.cms.ui.validator;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.OpinionPoll;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.Utility;

/**
 * @author swapnil.shirke
 *
 */
@Component
public class AdminOpinioMasterValidator extends BaseEntityValidator<OpinionPoll> {

    @Override
    protected void performValidations(final OpinionPoll entity,
            final EntityValidationContext<OpinionPoll> validationContext) {
       
    	 
        
        if ((entity.getPollSubEn() == null) || entity.getPollSubEn().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.noticesub1"));
        }
        if ((entity.getPollSubReg() == null) || entity.getPollSubReg().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("eippublicnotice.noticesub1reg"));
        }
        
        if (entity.getOptionSize()<2) {
        	validationContext.addOptionConstraint("eippublicnotice.min2opt");
        }
        
        
        validationContext.rejectIfNull(entity.getIssueDate(), getApplicationSession().getMessage("eippublicnotice.issueDate"));

        validationContext.rejectIfNull(entity.getValidityDate(), getApplicationSession().getMessage("eippublicnotice.validityDate"));        

        if ((entity.getIssueDate() != null) && (entity.getValidityDate() != null)) {
            if (entity.getValidityDate().before(entity.getIssueDate())) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("PublicNotices.date.validate"));
            }

        }
        
        if (entity.isCheckOpinionValidation()){
            validationContext.addOptionConstraint(entity.getActivePolls() + " : " +getApplicationSession().getMessage("opinion.poll.active.validation.error.msg"));
        }


    }

}
