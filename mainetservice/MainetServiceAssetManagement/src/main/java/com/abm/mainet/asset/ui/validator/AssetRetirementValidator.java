/**
 * 
 */
package com.abm.mainet.asset.ui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abm.mainet.asset.service.IAssetInformationService;
import com.abm.mainet.asset.service.IAssetValuationService;
import com.abm.mainet.asset.ui.dto.RetirementDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author satish.rathore
 *
 */
@Component
public class AssetRetirementValidator extends BaseEntityValidator<RetirementDTO> {

    @Autowired
    private IAssetInformationService assetInformationService;
    @Autowired
    private IAssetValuationService assetValuationService;
    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(RetirementDTO dto, EntityValidationContext<RetirementDTO> entityValidationContext) {
        if (dto != null) {
            if (dto.getNonfucDate() != null && dto.getDispositionDate() != null) {
                if (dto.getNonfucDate().after(dto.getDispositionDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.disposition.date"));
                }

                Date dateOfAcqui = assetInformationService.getPurchaseInfo(dto.getAssetId()).getDateOfAcquisition();
                if (dateOfAcqui != null && (dateOfAcqui.after(dto.getDispositionDate())
                        || dateOfAcqui.after(dto.getNonfucDate()))) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.nonfuction.dispositio.date"));
                }

            }
            // format is converted because it is using time stamp that is why validation fail in case of same day
            // input date does not have time stamp data base date has a time stamp
            Date bookEndDate = assetValuationService.findLatestBookEndDate(dto.getOrgId(), dto.getAssetId());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                bookEndDate = sdf.parse(sdf.format(bookEndDate));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (dto.getNonfucDate() != null && bookEndDate != null) {
                if (dto.getNonfucDate().before(bookEndDate)) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.nonfunandbookenddate"));
                }
            }
            if (dto.getDispositionDate() != null && bookEndDate != null) {
                if (dto.getDispositionDate().before(bookEndDate)) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.dispositionandbookenddate"));
                }
            }

            if (dto.getDispositionDate() != null && dto.getDisOrderDate() != null) {
                if (dto.getDispositionDate().before(dto.getDisOrderDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("vaid.retire.disposaldate"));
                }

            }

            if (dto.getDispositionDate() != null && dto.getChequeDate() != null) {
                if (validateChequeDate(dto.getDispositionDate(), dto.getChequeDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.retire.chequedate"));
                }

            }
            if (dto.getDispositionDate() != null && dto.getChequeDate() != null) {
                if (dto.getDispositionDate().before(dto.getChequeDate())) {
                    entityValidationContext.addOptionConstraint(session.getMessage("valid.retire.chequedate"));
                }
            }
        }
    }

    private boolean validateChequeDate(Date dispositionDate, Date chequeDate) {
        boolean isValidationError = true;
        Calendar currentDateBefore3Months = Calendar.getInstance();
        currentDateBefore3Months.setTime(dispositionDate);
        currentDateBefore3Months.add(Calendar.MONTH, -3);
        if (chequeDate.after(currentDateBefore3Months.getTime())) {
            isValidationError = false;
        }
        return isValidationError;
    }

}
