package com.abm.mainet.cfc.challan.ui.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.utility.UtilityService;

@Component
public class CommonOfflineMasterValidator extends BaseEntityValidator<CommonChallanDTO> {

	@Override
	protected void performValidations(final CommonChallanDTO entity,
			final EntityValidationContext<CommonChallanDTO> validationContext) {

		// Defect #32656

		if ((entity.getAmountToPay() != null && entity.getAmountToPay() != "0.0")
				|| (entity.getAmountToShow() != null && entity.getAmountToShow() > 0.0))
			if (entity.getOnlineOfflineCheck() == null) {
				validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.selPayMode"));
			}

		if ((entity.getOnlineOfflineCheck() != null)
				&& entity.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.OFFLINE)) {
			validationContext.rejectIfNotSelected(entity.getOflPaymentMode(), "oflPaymentMode");

			if ((entity.getOfflinePaymentText() != null)
					&& entity.getOfflinePaymentText().equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PPO_MODE)) {
				validationContext.rejectIfEmpty(entity.getPoNo(), "poNo");
				validationContext.rejectInvalidDate(entity.getPoDate(), "poDate");

			}
			if ((entity.getOfflinePaymentText() != null)
					&& entity.getOfflinePaymentText().equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PDD_MODE)) {
				validationContext.rejectInvalidDate(entity.getDdDate(), "ddDate");
				validationContext.rejectIfEmpty(entity.getDdNo(), "ddNo");

			}
			if ((entity.getOfflinePaymentText() != null)
					&& entity.getOfflinePaymentText().equalsIgnoreCase(MainetConstants.PAYMENT_MODES.PCB_MODE)) {
				if (entity.getBankaAccId() == null) {
					validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.bank.select"));
				} else {
					if ((entity.getBankaAccId() != null) && (entity.getBankaAccId() == 0)) {
						validationContext.addOptionConstraint(getApplicationSession().getMessage("pg.bank.select"));
					}
				}
			}

		}

		if ((entity.getOnlineOfflineCheck() != null)
				&& entity.getOnlineOfflineCheck().equals(MainetConstants.PAYMENT.PAY_AT_ULB_COUNTER)) {

			if ((entity.getPayModeIn() == null) || entity.getPayModeIn().equals(MainetConstants.BLANK)
					|| entity.getPayModeIn().equals(MainetConstants.TypeId.ZERO)) {

				validationContext.addOptionConstraint(getApplicationSession().getMessage("payment.paymentMode.error"));
			} else {
                 //User Story #147721
				 boolean lookUp = findLookUpInstance(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,entity.getPayModeIn());

				if (!lookUp) {

					if (((entity.getBmDrawOn() == null)
							|| entity.getBmDrawOn().equals(MainetConstants.Common_Constant.Isdeleted)
							|| entity.getBmDrawOn().equals(MainetConstants.BLANK) || entity.getBmDrawOn().isEmpty())&& !isPostalCardPayment(entity.getPayModeIn())) {

						validationContext
								.addOptionConstraint(getApplicationSession().getMessage("payment.bankName.error"));
					} else {
                        //Defect #137746
						if (((entity.getBmBankAccountId() == null)
								|| entity.getBmBankAccountId().equals(MainetConstants.TypeId.ZERO))&& !isPostalCardPayment(entity.getPayModeIn())) {
							validationContext.addOptionConstraint(
									getApplicationSession().getMessage("payment.bank.accountNo.error"));
						}

						if ((entity.getBmChqDDNo() == null)
								|| entity.getBmChqDDNo().equals(MainetConstants.TypeId.ZERO)) {
							validationContext.addOptionConstraint(
									getApplicationSession().getMessage("payment.bank.chqOrDDNo.error"));
						}

						if ((entity.getBmChqDDDate() == null)
								|| entity.getBmChqDDDate().equals(MainetConstants.BLANK)) {
							validationContext.addOptionConstraint(
									getApplicationSession().getMessage("payment.bank.chqOrDDDate.error"));
						} else {
							final String date = UtilityService.convertDateToDDMMYYYY(new Date());

							final String fromdate = date + " " + "00:00:00";

							final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
							Date todayDate = null;
							try {
								todayDate = formatter.parse(fromdate);
							} catch (final ParseException e) {
							}
							if (entity.getManualReeiptDate() != null) {
								todayDate = entity.getManualReeiptDate();
							}

							boolean isValid = false;
							// #133603
							if (entity != null
									&& (PrefixConstants.NewWaterServiceConstants.BPW).equals(entity.getServiceCode())
									&& Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(),
											MainetConstants.ENV_ASCL)) {
								isValid = true;
							} else {
								isValid = UtilityService.validateChequeDate(todayDate, entity.getBmChqDDDate());
							}

							if (!isValid) {
								validationContext.addOptionConstraint(
										ApplicationSession.getInstance().getMessage("cheque.date.current"));
							}

						}

					}
				}
				// added for reset all value in case of cash
				if (lookUp) {
					entity.setBmBankAccountId(null);
					entity.setBmChqDDNo(null);
					entity.setBmChqDDDate(null);
					entity.setBmDrawOn(MainetConstants.BLANK);
					entity.setCbBankId(null);
				}
			}
		}
	}
//User Story #147721
	private boolean findLookUpInstance(final String prefix,final long payId) {

		final List<LookUp> lookUps = CommonMasterUtility.getLookUps(prefix, UserSession.getCurrent().getOrganisation());
		for (final LookUp lookUp : lookUps) {

			if (lookUp.getLookUpId()==payId &&(lookUp.getLookUpCode().equals(PrefixConstants.PaymentMode.CASH) 
					||lookUp.getLookUpCode().equals(PrefixConstants.PaymentMode.POS) || lookUp.getLookUpCode().equals(PrefixConstants.PaymentMode.MCARD) ||lookUp.getLookUpCode().equals(PrefixConstants.PaymentMode.MCASH))) {

				return true;
			}
		}

		return false;
	}
	

	private boolean isPostalCardPayment(final long payMode) {
		try {
			final LookUp lookUps = CommonMasterUtility.getNonHierarchicalLookUpObject(payMode);

			if (lookUps.getLookUpCode().equals(PrefixConstants.PaymentMode.POSTALCARDPAYMENT)||lookUps.getLookUpCode().equals("POS")||lookUps.getLookUpCode().equals("CHL")||lookUps.getLookUpCode().equals("NJS")) {

				return true;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
}
