package com.abm.mainet.swm.ui.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.TripSheetDTO;

@Component
public class TripSheetMasterValidator extends BaseEntityValidator<TripSheetDTO> {

    @Override
    protected void performValidations(TripSheetDTO entity,
            EntityValidationContext<TripSheetDTO> entityValidationContext) {

        for (TripSheetDTO pastchk : entity.getTripSheetDTO()) {
            if (entity.getTripId() == null) {
                if (entity.getTripDate().equals(pastchk.getTripDate())) {
                    if (entity.getVeId().equals(pastchk.getVeId())) {
                        if (entity.getTripOuttimeDesc() != null && pastchk.getTripOuttimeDesc() != null) {

                            boolean chkbfrtym = (stringToTimeConvert(entity.getTripIntimeDesc())
                                    .before(pastchk.getTripIntime()))
                                    && (stringToTimeConvert(entity.getTripOuttimeDesc())
                                            .before(pastchk.getTripIntime()));
                            boolean chkaftrtym = (stringToTimeConvert(entity.getTripIntimeDesc())
                                    .after(pastchk.getTripOuttime()))
                                    && (stringToTimeConvert(entity.getTripOuttimeDesc())
                                            .after(pastchk.getTripOuttime()));
                            if (!(chkbfrtym || chkaftrtym)) {
                                entityValidationContext.addOptionConstraint("swm.timeoverlap");
                            }
                        }
                    }
                }
            }
        }
    }

    public String TimeToStringConvert(Date date) {
        String dateString = null;
        DateFormat sdf = new SimpleDateFormat("HH:mm");
        dateString = sdf.format(date);
        return dateString;
    }

    public Date stringToTimeConvert(String time) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Date timeValue = null;
        if (time != null) {
            try {
                timeValue = new Date(formatter.parse(time).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return timeValue;

    }
}
