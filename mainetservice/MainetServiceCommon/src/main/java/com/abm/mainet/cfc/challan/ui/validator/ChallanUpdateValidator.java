package com.abm.mainet.cfc.challan.ui.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.UtilityService;

@Component
public class ChallanUpdateValidator extends
        BaseEntityValidator<ChallanMaster> {

    @Override
    protected void performValidations(final ChallanMaster entity,
            final EntityValidationContext<ChallanMaster> validationContext) {

        validationContext.rejectIfEmpty(entity.getBankTransId(), "bankTransId");

        if (entity.getChallanRcvdDate() != null) {

            final String date = UtilityService.convertDateToDDMMYYYY(entity.getChallanDate());

            final String fromdate = date + MainetConstants.WHITE_SPACE + "00:00:00";

            final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date challanDate = null;
            try {
                challanDate = formatter.parse(fromdate);
            } catch (final ParseException e) {
                e.printStackTrace();
            }

            final boolean days1 = UtilityService.compareDate(challanDate, entity.getChallanRcvdDate());

            if (!days1) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage("challanVerification.gtchallanDate"));
            }
        } else {
            validationContext
                    .addOptionConstraint(getApplicationSession().getMessage("challanVerification.validdate"));
        }
        if (entity.getChallanRcvdDate() != null) {

            final String date = UtilityService.convertDateToDDMMYYYY(entity.getChallanValiDate());

            final String fromdate = date + MainetConstants.WHITE_SPACE + "00:00:00";

            final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date challanValidDate = null;
            try {
                challanValidDate = formatter.parse(fromdate);
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            final boolean days2 = UtilityService.compareDate(entity.getChallanRcvdDate(), challanValidDate);

            if (!days2) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage("challanVerification.ltchallanDate"));
            }
        }
        if (entity.getChallanRcvdDate() != null) {

            final String date = UtilityService.convertDateToDDMMYYYY(new Date());

            final String fromdate = date + MainetConstants.WHITE_SPACE + "00:00:00";

            final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date todayDate = null;
            try {
                todayDate = formatter.parse(fromdate);
            } catch (final ParseException e) {
                e.printStackTrace();
            }
            final boolean days3 = UtilityService.compareDate(todayDate, entity.getChallanRcvdDate());

            if (!days3) {
                validationContext
                        .addOptionConstraint(getApplicationSession().getMessage("challanVerification.lechallanDate"));
            }
        }

    }
}
