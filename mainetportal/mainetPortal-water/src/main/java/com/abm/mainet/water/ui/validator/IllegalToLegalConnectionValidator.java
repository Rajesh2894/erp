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
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.common.util.UserSession;
import com.abm.mainet.water.dto.TbKLinkCcnDTO;
import com.abm.mainet.water.service.INewWaterConnectionService;
import com.abm.mainet.water.ui.model.IllegalToLegalConnectionModel;

/**
 * @author Saiprasad.Vengurlekar
 *
 */

@Component
public class IllegalToLegalConnectionValidator extends BaseEntityValidator<IllegalToLegalConnectionModel> {

	@Autowired
	private INewWaterConnectionService iNewWaterConnectionService;

	@Override
	protected void performValidations(final IllegalToLegalConnectionModel model,
			final EntityValidationContext<IllegalToLegalConnectionModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		if ((model.getCsmrInfo().getTypeOfApplication() == null)
				|| model.getCsmrInfo().getTypeOfApplication().isEmpty()) {
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
						iNewWaterConnectionService.findNoOfDaysCalculation(model.getCsmrInfo(),
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

			if (model.getReqDTO().getIsConsumer() == null) {
				entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsName(), "csName");

				if (model.getCsmrInfo().getCsAdd() == null || model.getCsmrInfo().getCsAdd().isEmpty()) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ConsumerArea"));
				}
				if (model.getCsmrInfo().getCsCpinCode() == null) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.ConsumerPincode"));
				}
			}
			if (model.getReqDTO().getIsBillingAddressSame() == null) {

				if (model.getCsmrInfo().getCsBadd() == null || model.getCsmrInfo().getCsBadd().isEmpty()) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.billingArea"));
				}
				if (model.getCsmrInfo().getBpincode() == null || model.getCsmrInfo().getBpincode().isEmpty()) {
					entityValidationContext.addOptionConstraint(session.getMessage("water.validation.billingPincode"));
				}

			}

			entityValidationContext.rejectIfEmpty(model.getCsmrInfo().getCsOname(), "csOname");

			if (model.getCsmrInfo().getCsOadd() == null || model.getCsmrInfo().getCsOadd().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerArea"));
			}
			if (model.getCsmrInfo().getOpincode() == null || model.getCsmrInfo().getOpincode().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.OwnerPincode"));
			}
			if (model.getCsmrInfo().getBplFlag() == null || model.getCsmrInfo().getBplFlag().isEmpty()) {
				entityValidationContext.addOptionConstraint(session.getMessage("water.validation.isabovepovertyline"));
			} else {
				if (model.getCsmrInfo().getBplFlag().equals(MainetConstants.YES)
						&& (model.getCsmrInfo().getBplNo() == null || model.getCsmrInfo().getBplNo().isEmpty()))
					entityValidationContext.addOptionConstraint(session.getMessage("Please Enter BPL No."));
			}
			performGroupValidation("csmrInfo.trmGroup");
			performGroupValidation("csmrInfo.csCcncategory");

			if ((model.getReqDTO().getDocumentList() != null) && !model.getReqDTO().getDocumentList().isEmpty()) {
				for (final DocumentDetailsVO doc : model.getReqDTO().getDocumentList()) {
					if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
						if (doc.getDocumentByteCode() == null) {
							entityValidationContext.addOptionConstraint(session.getMessage("upload.doc"));
							break;
						}
					}
				}
			}
			if (model.getReqDTO().getExistingConsumerNumber() != null) {
				if ((model.getCsmrInfo().getLinkDetails() != null) && !model.getCsmrInfo().getLinkDetails().isEmpty()) {
					for (final TbKLinkCcnDTO link : model.getCsmrInfo().getLinkDetails()) {
						if ((link.getLcOldccn() != null) && link.getLcOldccn().equals(MainetConstants.BLANK)) {
							entityValidationContext.addOptionConstraint(session.getMessage("water.old.conNo"));
						}
					}
				}
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
			throw new FrameworkException("Error in Parsing Date in checkDate()", e);
		}

		return flag;
	}

}
