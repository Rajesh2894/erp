/**
 * @author  : Gayatri.Kokane
 * @since   : 20 Feb 2018
 * @comment : Validation file for Trade Application Form for Server Side Validation.
 * @method  : performValidations - check the mandatory validation of the field
 *              
 */
package com.abm.mainet.tradeLicense.ui.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.tradeLicense.service.ITradeLicenseApplicationService;
import com.abm.mainet.tradeLicense.ui.model.TradeApplicationFormModel;

@Component
public class TradeApplicationFormValidator extends BaseEntityValidator<TradeApplicationFormModel> {
	/*
	 * @Resource private IWorkflowTyepResolverService iWorkflowTyepResolverService;
	 */
	@Autowired
	private ITradeLicenseApplicationService tradeApplicationService;

	@Override
	protected void performValidations(TradeApplicationFormModel model,
			EntityValidationContext<TradeApplicationFormModel> entityValidationContext) {
		final ApplicationSession session = ApplicationSession.getInstance();

		/* checking manadatory documents */
		/*
		 * if ((model.getCheckList() != null) && !model.getCheckList().isEmpty()) { for
		 * (final DocumentDetailsVO doc : model.getCheckList()) { if
		 * (doc.getCheckkMANDATORY().equals(MainetConstants.MENU.Y) &&
		 * doc.getDocumentByteCode() == null) {
		 * entityValidationContext.addOptionConstraint(session.getMessage(
		 * "water.fileuplaod.validtnMsg")); break; } } }
		 */
		/* end */
		Boolean mas = false;
		try {
			mas = tradeApplicationService.resolveServiceWorkflowType(
					model.getTradeMasterDetailDTO());
		} catch (Exception e) {

		}
		if (mas == true) {
			entityValidationContext.addOptionConstraint(session.getMessage("ml.validation.workflow"));
		}
		/* end */

	}

}
