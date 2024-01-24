package com.abm.mainet.property.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.util.ApplicationSession;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

@Component
public class SelfAssessmentValidator extends BaseEntityValidator<ProvisionalAssesmentMstDto> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(ProvisionalAssesmentMstDto provDto,
            EntityValidationContext<ProvisionalAssesmentMstDto> entityValidationContext) {

        if (provDto.getBillPartialAmt() <= 0) {
            entityValidationContext.addOptionConstraint(session.getMessage("property.EnterAmountValid"));
        }

        if ((provDto.getDocs() != null) && !provDto.getDocs().isEmpty()) {
            for (final DocumentDetailsVO doc : provDto.getDocs()) {
                if (doc.getCheckkMANDATORY().equals(MainetConstants.YES)) {
                    if (doc.getDocumentByteCode() == null) {
                        entityValidationContext.addOptionConstraint(session.getMessage("rnl.estate.mandtory.docs"));
                        break;
                    }
                }
            }
        }
    }

}
