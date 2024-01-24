/**
 * 
 */
package com.abm.mainet.water.ui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.water.service.NewWaterConnectionService;
import com.abm.mainet.water.ui.model.IllegalConnectionNoticeGenerationModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
public class IllegalConnectionNoticeGenerationValidator
		extends BaseEntityValidator<IllegalConnectionNoticeGenerationModel> {

	@Autowired
	NewWaterConnectionService waterService;

	@Override
	protected void performValidations(IllegalConnectionNoticeGenerationModel model,
			EntityValidationContext<IllegalConnectionNoticeGenerationModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if ((model.getCsmrInfo().getTypeOfApplication() != null)
				&& model.getCsmrInfo().getTypeOfApplication().equals(MainetConstants.WHITE_SPACE)) {
			entityValidationContext.addOptionConstraint(session.getMessage("water.validation.typeApplication"));
		} else {
			if ((model.getCsmrInfo().getTypeOfApplication() != null) && model.getCsmrInfo().getTypeOfApplication()
					.equals(MainetConstants.NewWaterServiceConstants.APPLICATION_TYPE)) {

				if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
					entityValidationContext.rejectCompareDate(model.getCsmrInfo().getFromDate(), "fromDate",
							model.getCsmrInfo().getToDate(), "toDate");
				} else {
					if (model.getCsmrInfo().getFromDate() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("Please Select From Period"));
					}
					if (model.getCsmrInfo().getToDate() == null) {
						entityValidationContext.addOptionConstraint(session.getMessage("Please Select To Period"));
					}
				}
				try {
					if (model.getCsmrInfo().getFromDate() != null && model.getCsmrInfo().getToDate() != null) {
						waterService.findNoOfDaysCalculation(model.getCsmrInfo(),
								UserSession.getCurrent().getOrganisation());
						if (checkDate(model.getCsmrInfo().getFromDate(), model.getCsmrInfo().getToDate(),
								model.getCsmrInfo().getMaxNumberOfDay()))
							entityValidationContext.addOptionConstraint(
									session.getMessage("Temporary connection period should not be greater than "
											+ model.getCsmrInfo().getMaxNumberOfDay() + " Days"));
					}
				} catch (FrameworkException exp) {
					entityValidationContext
							.addOptionConstraint(session.getMessage("water.validation.prefix.not.found.noOfDays"));
				}

			}
		}

		entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsOname(), "csOname");
		if (model.getCsmrInfo().getCsOadd() == null || model.getCsmrInfo().getCsOadd().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerArea"));
		}
		if (model.getCsmrInfo().getOpincode() == null || model.getCsmrInfo().getOpincode().isEmpty()) {
			entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerPincode"));
		}

		if (model.getCsmrInfo().getCsCcnsize() == null || model.getCsmrInfo().getCsCcnsize() == 0) {
			entityValidationContext.addOptionConstraint(session.getMessage("Please Select Connection Size"));
		}

		performGroupValidation("csmrInfo.trmGroup");
		performGroupValidation("csmrInfo.csCcncategory");
		performGroupValidation("csmrInfo.codDwzid");

		if ((model.getCsmrInfo().getCsPtype() != null)) {
			if (model.getCsmrInfo().getPlumId() == null) {
				entityValidationContext.addOptionConstraint(session.getMessage("Please Select Plumber Name"));
			}
		}

	}

	public boolean checkDate(Date fromDate, Date toDate, Long noOfDays) {
		boolean flag = false;
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String fromD = df.format(fromDate);
		String toD = df.format(toDate);
		long diff;
		try {
			diff = df.parse(toD).getTime() - df.parse(fromD).getTime();
			Long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			long range = TimeUnit.DAYS.toDays(days);
			if (range > noOfDays)
				flag = true;
		} catch (ParseException e) {
			throw new FrameworkException("Exception While parsing Dates in checkDate()", e);
		}

		return flag;
	}

}
