/**
 * @author  : Harshit kumar
 * @since   : 28 Mar 2018
 * @comment : Validation file for RTI PIO Form for Server Side Validation.
 * @method  : performValidations - check the mandatory validation of the field
 *              
 */
package com.abm.mainet.rti.ui.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rti.dto.RtiMediaListDTO;
import com.abm.mainet.rti.ui.model.RtiPioResponseModel;

@Component
public class RtiPioResponseValidator extends BaseEntityValidator<RtiPioResponseModel> {

    @Override
    protected void performValidations(RtiPioResponseModel model,
            EntityValidationContext<RtiPioResponseModel> entityValidationContext) {
        // TODO Auto-generated method stub
        final ApplicationSession session = ApplicationSession.getInstance();

        /* validating duplicate Media Type */

        List<RtiMediaListDTO> mediaList = model.getRtiMediaListDTO();
        List<String> mediaTypeList = new ArrayList<>();

        if (!mediaList.isEmpty()) {

            int index = mediaList.size();
            IntStream.range(0, index).forEach(idx -> {
                String strMediaType = getPrefixDesc(PrefixConstants.MEDIA_TYPE, Long.valueOf(mediaList.get(idx).getMediaType()));
                mediaTypeList.add(strMediaType);
            });

            if (mediaTypeList.stream().filter(i -> Collections.frequency(mediaTypeList, i) > 1).count() > 0) {
                entityValidationContext.addOptionConstraint(session.getMessage("rti.validation.mediaType"));
            }

        }

    }

    final String getPrefixDesc(String code, Long id) {
        String prefixDesc = MainetConstants.BLANK;
        List<LookUp> lookUps = CommonMasterUtility.getLookUps(code, UserSession.getCurrent().getOrganisation());
        for (LookUp lookUp : lookUps) {
            if (lookUp.getLookUpId() == id) {
                prefixDesc = lookUp.getLookUpDesc();
                break;
            }
        }
        return prefixDesc;
    }

}
