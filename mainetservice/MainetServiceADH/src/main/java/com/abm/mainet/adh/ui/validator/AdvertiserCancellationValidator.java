package com.abm.mainet.adh.ui.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.abm.mainet.adh.ui.model.AdvertiserCancellationModel;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author cherupelli.srikanth
 * @since 25 september 2019
 */
@Component
public class AdvertiserCancellationValidator extends BaseEntityValidator<AdvertiserCancellationModel> {

    @Override
    protected void performValidations(AdvertiserCancellationModel model,
	    EntityValidationContext<AdvertiserCancellationModel> entityValidationContext) {
	final ApplicationSession session = ApplicationSession.getInstance();

	if (StringUtils.isBlank(model.getAdvertiserDto().getAgencyLicNo())) {
	    entityValidationContext.addOptionConstraint(session.getMessage("advertiser.cancellation.validate.licNo"));
	}

	if (model.getAdvertiserDto().getCancellationDate() == null) {
	    entityValidationContext
		    .addOptionConstraint(session.getMessage("advertiser.cancellation.validate.cancellation.date"));
	} else {

	    try {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date today = new Date();
		Date currentDate = formatter.parse(formatter.format(today));
		Date cancelDate = formatter.parse(formatter.format(model.getAdvertiserDto().getCancellationDate()));

		if (cancelDate.compareTo(currentDate) != 0) {
		    entityValidationContext.addOptionConstraint(
			    session.getMessage("License cancellation date should be current date"));
		}
	    } catch (ParseException e) {
		e.printStackTrace();
	    }

	}
	if (StringUtils.isBlank(model.getAdvertiserDto().getCancellationReason())) {
	    entityValidationContext
		    .addOptionConstraint(session.getMessage("advertiser.cancellation.validate.cancellation.reason"));
	}

    }

}
