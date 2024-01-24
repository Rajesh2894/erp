/**
 * 
 */
package com.abm.mainet.asset.ui.validator;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.integration.asset.dto.AssetClassificationDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDepreciationChartDTO;
import com.abm.mainet.common.integration.asset.dto.AssetDetailsDTO;
import com.abm.mainet.common.integration.asset.dto.AssetPurchaseInformationDTO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author satish.rathore
 *
 */
@Component
public class AssetAcquisitionValidator extends BaseEntityValidator<AssetDetailsDTO> {
    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(AssetDetailsDTO dto, EntityValidationContext<AssetDetailsDTO> entityValidationContext) {
        final AssetClassificationDTO astClDto = dto.getAssetClassificationDTO();
        final AssetPurchaseInformationDTO astpDto = dto.getAssetPurchaseInformationDTO();
        final AssetDepreciationChartDTO astdDto = dto.getAstDepreChartDTO();
        if ((astClDto == null || astClDto.getCostCenter() == null)
                && (astpDto != null && astpDto.getInitialBookValue() != null)) {
            if (astpDto != null && astpDto.getInitialBookValue() != null) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.acquisition.costcenter"));
            }
        }
        if (astpDto != null && astpDto.getAstCreationDate() != null && astpDto.getDateOfAcquisition() != null) {
            if (astpDto.getAstCreationDate().after(astpDto.getDateOfAcquisition())) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.astcreation.date"));
            }
        }

        if (astpDto != null && astpDto.getInitialBookDate() != null && astpDto.getDateOfAcquisition() != null) {
            if (astpDto.getInitialBookDate().before(astpDto.getDateOfAcquisition())) {
                entityValidationContext.addOptionConstraint(session.getMessage("valid.initial.bookdate"));
            }
        }

        if (astpDto != null && astdDto != null) {

            if (astpDto.getDateOfAcquisition() != null && astdDto.getOriUseYear() != null) {
                Date expireDate = findDate(astpDto.getDateOfAcquisition(), astdDto.getOriUseYear().intValue());
                if (expireDate != null && astpDto.getWarrantyTillDate() != null) {
                    if (expireDate.before(astpDto.getWarrantyTillDate())) {

                        entityValidationContext.addOptionConstraint(session.getMessage("valid.purchase.warrantyuselife"));
                    }
                }
            }

        }
    }

    /**
     * @param date
     * @param year
     * @return this method will give you the expire date according to you gives useful life and acquisition date
     */
    private Date findDate(Date date, Integer year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        Date YearFromNow = calendar.getTime();
        calendar.setTime(YearFromNow);
        return YearFromNow;

    }
}
